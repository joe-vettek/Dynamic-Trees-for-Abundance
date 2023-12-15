package xueluoanping.dtabundance;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.network.MapSignal;
import com.ferreusveritas.dynamictrees.api.network.NodeInspector;
import com.ferreusveritas.dynamictrees.blocks.branches.BranchBlock;
import com.ferreusveritas.dynamictrees.blocks.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.compat.seasons.SeasonHelper;
import com.ferreusveritas.dynamictrees.event.SpeciesPostGenerationEvent;
import com.ferreusveritas.dynamictrees.systems.genfeatures.context.PostGenerationContext;
import com.ferreusveritas.dynamictrees.systems.nodemappers.FindEndsNode;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.util.SafeChunkBounds;
import com.ferreusveritas.dynamictrees.util.SimpleVoxmap;
import com.ferreusveritas.dynamictrees.util.WorldContext;
import com.ferreusveritas.dynamictrees.worldgen.JoCode;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xueluoanping.dtabundance.systems.species.FloweringJacarandaSpecies;

import java.util.List;
import java.util.Random;

public class CJoCode extends JoCode {
    public CJoCode(String code) {
        super(code);
    }

    /**
     * Generate a tree from this {@link JoCode} instruction list.
     *
     * @param worldContext      The {@link World} instance.
     * @param rootPosIn         The position of what will become the {@link com.ferreusveritas.dynamictrees.blocks.rootyblocks.RootyBlock}.
     * @param biome             The {@link Biome} at {@code rootPosIn}.
     * @param facing            The {@link Direction} of the tree.
     * @param radius            The radius constraint.
     * @param secondChanceRegen Ensures second chance regen doesn't recurse too far.
     */
    public void generate(WorldContext worldContext, Species species, BlockPos rootPosIn, Biome biome, Direction facing, int radius, SafeChunkBounds safeBounds, boolean secondChanceRegen) {
        // super.generate(worldContext, species, rootPosIn, biome, facing, radius, safeBounds, secondChanceRegen);
        // if (true)
        //     return;
        final IWorld world = worldContext.access();
        final boolean worldGen = safeBounds != SafeChunkBounds.ANY;

        // A Tree generation boundary radius is at least 2 and at most 8.
        radius = MathHelper.clamp(radius, 2, 8);

        this.setFacing(facing);
        final BlockPos rootPos = species.preGeneration(world, rootPosIn, radius, facing, safeBounds, this);

        if (rootPos == BlockPos.ZERO) {
            return;
        }

        final BlockState initialDirtState = world.getBlockState(rootPos); // Save the initial state of the dirt in case this fails.
        species.placeRootyDirtBlock(world, rootPos, 0); // Set to unfertilized rooty dirt.

        // Make the tree branch structure.
        this.generateFork(world, species, 0, rootPos, false);

        // Establish a position for the bottom block of the trunk.
        final BlockPos treePos = rootPos.above();

        // Fix branch thicknesses and map out leaf locations.
        final BlockState treeState = world.getBlockState(treePos);
        final BranchBlock firstBranch = TreeHelper.getBranch(treeState);

        // If a branch doesn't exist the growth failed.. turn the soil back to what it was.
        if (firstBranch == null) {
            world.setBlock(rootPos, initialDirtState, this.careful ? 3 : 2);
            return;
        }

        // If a branch exists then the growth was successful.

        // this cast can't remove
        final LeavesProperties leavesProperties = species.getLeavesProperties();
        final SimpleVoxmap leafMap = new SimpleVoxmap(radius * 2 + 1, species.getWorldGenLeafMapHeight(), radius * 2 + 1).setMapAndCenter(treePos, new BlockPos(radius, 0, radius));
        final NodeInspector inflator = species.getNodeInflator(leafMap); // This is responsible for thickening the branches.
        final FindEndsNode endFinder = new FindEndsNode(); // This is responsible for gathering a list of branch end points.
        final MapSignal signal = new MapSignal(inflator, endFinder); // The inflator signal will "paint" a temporary voxmap of all of the leaves and branches.
        signal.destroyLoopedNodes = this.careful;

        firstBranch.analyse(treeState, world, treePos, Direction.DOWN, signal);

        if (signal.foundRoot || signal.overflow) { // Something went terribly wrong.
            this.tryGenerateAgain(worldContext, species, rootPosIn, biome, facing, radius, safeBounds, worldGen, treePos, treeState, endFinder, secondChanceRegen);
            return;
        }

        final List<BlockPos> endPoints = endFinder.getEnds();

        this.smother(leafMap, leavesProperties); // Use the voxmap to precompute leaf smothering so we don't have to age it as many times.

        // Place Growing Leaves Blocks from voxmap.
        for (final SimpleVoxmap.Cell cell : leafMap.getAllNonZeroCells((byte) 0x0F)) { // Iterate through all of the cells that are leaves (not air or branches).
            final BlockPos.Mutable cellPos = cell.getPos();

            if (safeBounds.inBounds(cellPos, false)) {
                final BlockState testBlockState = world.getBlockState(cellPos);
                if (testBlockState.canBeReplacedByLeaves(world, cellPos)) {
                    world.setBlock(cellPos, leavesProperties.getDynamicLeavesState(cell.getValue()), worldGen ? 16 : 2); // Flag 16 to prevent observers from causing cascading lag.
                }
            } else {
                leafMap.setVoxel(cellPos, (byte) 0);
            }
        }

        // Shrink the leafMap down by the safeBounds object so that the aging process won't look for neighbors outside of the bounds.
        for (final SimpleVoxmap.Cell cell : leafMap.getAllNonZeroCells()) {
            final BlockPos.Mutable cellPos = cell.getPos();
            if (!safeBounds.inBounds(cellPos, true)) {
                leafMap.setVoxel(cellPos, (byte) 0);
            }
        }

        // Age volume for 3 cycles using a leafmap.
        TreeHelper.ageVolume(world, leafMap, species.getWorldGenAgeIterations(), safeBounds);

        // Rot the unsupported branches.
        if (species.handleRot(world, endPoints, rootPos, treePos, 0, safeBounds)) {
            return; // The entire tree rotted away before it had a chance.
        }

        // Allow for special decorations by the tree itself.
        species.postGeneration(new PostGenerationContext(world, rootPos, species, biome, radius, endPoints,
                safeBounds, initialDirtState, SeasonHelper.getSeasonValue(worldContext, rootPos),
                species.seasonalFruitProductionFactor(worldContext, rootPos)));
        MinecraftForge.EVENT_BUS.post(new SpeciesPostGenerationEvent(world, species, rootPos, endPoints, safeBounds, initialDirtState));

        // Add snow to parts of the tree in chunks where snow was already placed.
        this.addSnow(leafMap, world, rootPos, biome);
    }

    private static final Logger LOGGER = DTAbundance.LOGGER;

    private void tryGenerateAgain(WorldContext worldContext, Species species, BlockPos rootPosIn, Biome biome, Direction facing, int radius, SafeChunkBounds safeBounds, boolean worldGen, BlockPos treePos, BlockState treeState, FindEndsNode endFinder, boolean secondChanceRegen) {
        // Don't log the error if it didn't happen during world gen (so we don't fill the logs if players spam the staff in cramped positions).
        if (worldGen) {
            if (!secondChanceRegen) {
                LOGGER.debug("Non-viable branch network detected during world generation @ {}", treePos);
                LOGGER.debug("Species: {}", species);
                LOGGER.debug("Radius: {}", radius);
                LOGGER.debug("JoCode: {}", this);
            } else {
                LOGGER.debug("Second attempt for code {} has also failed", this);
            }

        }

        // Completely blow away any improperly defined network nodes.
        this.cleanupFrankentree(worldContext.access(), treePos, treeState, endFinder.getEnds(), safeBounds);

        // Now that everything is clear we may as well regenerate the tree that screwed everything up.
        if (!secondChanceRegen) {
            this.generate(worldContext, species, rootPosIn, biome, facing, radius, safeBounds, true);
        }
    }

}
