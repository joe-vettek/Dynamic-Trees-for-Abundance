package xueluoanping.dtabundance.systems.family;

import com.ferreusveritas.dynamictrees.api.registry.RegistryHandler;
import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.blocks.branches.BranchBlock;
import com.ferreusveritas.dynamictrees.trees.Family;
import com.teamaurora.abundance.core.registry.AbundanceBlocks;
import net.minecraft.util.ResourceLocation;
import xueluoanping.dtabundance.DTAbundance;

import java.util.Optional;

import static com.ferreusveritas.dynamictrees.util.ResourceLocationUtils.suffix;

public class RedBudFamily extends Family {

    public static final TypedRegistry.EntryType<Family> TYPE = TypedRegistry.newType(RedBudFamily::new);

    public RedBudFamily(ResourceLocation name) {
        super(name);
    }

    private static BranchBlock floweringBranchBlock;

    @Override
    public void setupBlocks() {
        super.setupBlocks();
        if (floweringBranchBlock == null) {
            floweringBranchBlock = this.createFloweringBranchBlock(DTAbundance.rl("flowering_redbud_branch"));
            floweringBranchBlock.setFamily(this);
            addValidBranches(RegistryHandler.addBlock(DTAbundance.rl("flowering_redbud_branch"), floweringBranchBlock));
        }
    }

    // @Override
    // protected BranchBlock createBranch(ResourceLocation name) {
    //
    //     return super.createBranch(name);
    // }

    protected BranchBlock createFloweringBranchBlock(ResourceLocation name) {
        return new BlockFloweringRedBudBranch(name, this.getProperties());
    }

    public static Optional<BranchBlock>  getFloweringBranchBlock() {
        return Optional.of(floweringBranchBlock);
    }

    @Override
    protected BranchBlock createBranchBlock(ResourceLocation name) {
        return new BlockRedBudBranch(name, this.getProperties());
    }

    // @Override
    // public void addValidBranches(BranchBlock... branches) {
    //     super.addValidBranches(branches);
    //     // super.addValidBranches();
    // }
}
