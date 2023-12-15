package xueluoanping.dtabundance.systems.species;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.blocks.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.blocks.rootyblocks.RootyBlock;
import com.ferreusveritas.dynamictrees.systems.SeedSaplingRecipe;
import com.ferreusveritas.dynamictrees.systems.genfeatures.GenFeature;
import com.ferreusveritas.dynamictrees.systems.genfeatures.context.FullGenerationContext;
import com.ferreusveritas.dynamictrees.systems.nodemappers.NetVolumeNode;
import com.ferreusveritas.dynamictrees.trees.Family;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.util.CoordUtils;
import com.ferreusveritas.dynamictrees.util.SafeChunkBounds;
import com.ferreusveritas.dynamictrees.util.WorldContext;
import com.ferreusveritas.dynamictrees.worldgen.JoCode;
import com.ferreusveritas.dynamictrees.worldgen.JoCodeRegistry;
import com.ferreusveritas.dynamictreesplus.trees.CactusSpecies;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.TreeFeature;
import xueluoanping.dtabundance.CJoCode;
import xueluoanping.dtabundance.DTAbundance;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class FloweringJacarandaSpecies extends Species {


    // protected CommonOverride commonOverride;

    public static final TypedRegistry.EntryType<Species> TYPE = createDefaultType(FloweringJacarandaSpecies::new);
    // public static final TypedRegistry.EntryType<Randombudding> TYPE = createDefaultType(Randombudding::new);

    public FloweringJacarandaSpecies(ResourceLocation name, Family family, LeavesProperties leavesProperties) {
        this.setRegistryName(name);
        this.setUnlocalizedName(name.toString());
        this.family = family;
        this.family.addSpecies(this);
        this.setLeavesProperties(leavesProperties.isValid() ? leavesProperties : family.getCommonLeaves());
        this.setCommonOverride(commonOverride);
    }


    @Override
    public boolean shouldOverrideCommon(IBlockReader world, BlockPos trunkPos) {
        // world when it's worldgen.region then it's can getblockstate
        BlockPos rootPos =
                // !(world.getBlockState(trunkPos).getBlock() instanceof RootyBlock) ?
                // TreeHelper.findRootNode((World) world, trunkPos) :
                trunkPos;
        return new Random(rootPos.asLong()).nextBoolean();
    }

    @Override
    public boolean hasCommonOverride() {
        return true;
    }



    @Override
    public void setCommonOverride(CommonOverride commonOverride) {
        commonOverride = (world, trunkPos) -> {
            BlockPos rootPos =
                    // !(world.getBlockState(trunkPos).getBlock() instanceof RootyBlock) ?
                    // TreeHelper.findRootNode((World) world, trunkPos) :
                    trunkPos;
            return new Random(rootPos.asLong()).nextBoolean();
        };

        this.commonOverride = commonOverride;
    }

    // @Override
    // public JoCode getJoCode(String joCodeString) {
    //     return new CJoCode(joCodeString);
    // }



    //////////////////////////////
    // WORLDGEN
    //////////////////////////////

    /**
     * Default worldgen spawn mechanism. This method uses JoCodes to generate tree models. Override to use other
     * methods.
     *
     * @param rootPos The position of {@link RootyBlock} this tree is planted in
     * @param biome   The biome this tree is generating in
     * @param radius  The radius of the tree generation boundary
     * @return true if tree was generated. false otherwise.
     */
    public boolean generate(WorldContext worldContext, BlockPos rootPos, Biome biome, Random random, int radius,
                            SafeChunkBounds safeBounds) {
        final AtomicBoolean fullGen = new AtomicBoolean(false);
        final FullGenerationContext context =
                new FullGenerationContext(worldContext.access(), rootPos, this, biome, radius, safeBounds);

        this.genFeatures.forEach(configuration ->
                fullGen.set(fullGen.get() || configuration.generate(GenFeature.Type.FULL, context))
        );

        if (fullGen.get()) {
            return true;
        }
        if (!shouldGenerate(worldContext, rootPos)) {
            return false;
        }

        final Direction facing = CoordUtils.getRandomDir(random);
        if (!JoCodeRegistry.getCodes(this.getRegistryName()).isEmpty()) {
            Optional<JoCode> code = getRandomJoCode(radius, random);
            if (code.isPresent()) {
                // CJoCode cJoCode = (CJoCode) code.get();
                final Species species = shouldOverrideCommon(worldContext.level(), rootPos) ?
                        Species.REGISTRY.get(DTAbundance.rl("flowering_jacaranda")) : this;

                code.get().generate(worldContext, species, rootPos, biome, facing, radius, safeBounds, false);
                return true;
            }
        }

        return false;
    }

    private boolean shouldGenerate(WorldContext worldContext, BlockPos rootPos) {
        // World gen would be slowed down if we did as extensive a check as vanilla. This is good enough to at least
        // prevent trees generating if there's a structure/mountain overhang above.
        BlockPos.Mutable pos = rootPos.above().mutable();
        for (int i = 0; i < signalEnergy; i++) {
            if (!TreeFeature.validTreePos(worldContext.access(), pos)) {
                return false;
            }
            pos.move(Direction.UP);
        }
        return true;
    }
}
