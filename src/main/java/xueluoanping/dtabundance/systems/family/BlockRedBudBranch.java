package xueluoanping.dtabundance.systems.family;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.network.MapSignal;
import com.ferreusveritas.dynamictrees.blocks.branches.BasicBranchBlock;
import com.ferreusveritas.dynamictrees.blocks.rootyblocks.RootyBlock;
import com.ferreusveritas.dynamictrees.systems.nodemappers.FindEndsNode;
import com.ferreusveritas.dynamictrees.systems.nodemappers.NetVolumeNode;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.teamaurora.abundance.core.registry.AbundanceBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockRedBudBranch extends BasicBranchBlock {

    public static final BooleanProperty IS_FLOWERING = BooleanProperty.create("is_flowering");

    public BlockRedBudBranch(ResourceLocation name, Properties properties) {
        super(name, properties);

        for (int i = 0; i < this.branchStates.length; i++) {
            if (this.branchStates[i].is(this))
                this.branchStates[i] = this.branchStates[i].setValue(IS_FLOWERING, false);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(IS_FLOWERING));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(RADIUS) > 6;
        // return state.getValue(IS_FLOWERING) ;
    }

    // here plan when the tree can grow then try leflowering
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        if (random.nextInt(25) == 0) {
            // BlockPos rootPos = TreeHelper.findRootNode(world, pos);
            // if (world.getBlockState(rootPos).getValue(RootyBlock.FERTILITY) > 0)
            //     world.setBlockAndUpdate(pos, RedBudFamily.getFloweringBranchBlock().get().defaultBlockState().setValue(RADIUS, state.getValue(RADIUS)).setValue(WATERLOGGED, state.getValue(WATERLOGGED)));
        }
    }


}
