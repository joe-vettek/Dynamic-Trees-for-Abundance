package xueluoanping.dtabundance.systems.family;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.blocks.branches.BranchBlock;
import com.ferreusveritas.dynamictrees.trees.Family;
import com.ferreusveritas.dynamictreesplus.trees.CactusFamily;
import net.minecraft.util.ResourceLocation;

public class SaguaroCactusFamily extends CactusFamily {
    public static final TypedRegistry.EntryType<Family> TYPE = TypedRegistry.newType(SaguaroCactusFamily::new);

    public SaguaroCactusFamily(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected BranchBlock createBranchBlock(ResourceLocation name) {
        return new SaguaroCactusBranch(name, this.getProperties());
    }
}
