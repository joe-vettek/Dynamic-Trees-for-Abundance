package xueluoanping.dtabundance.systems.family;

import com.ferreusveritas.dynamictreesplus.blocks.CactusBranchBlock;
import com.teamaurora.abundance.common.block.SaguaroCactusBlock;
import com.teamaurora.abundance.core.registry.AbundanceEffects;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SaguaroCactusBranch extends CactusBranchBlock {
    public SaguaroCactusBranch(ResourceLocation name, Properties properties) {
        super(name, properties);
    }

    @Override
    public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        getPrimitiveLog().ifPresent(block -> {
            if (block instanceof SaguaroCactusBlock)
                ((SaguaroCactusBlock) block).entityInside(state, worldIn, pos, entityIn);
        });
        // entityIn.hurt(DamageSource.CACTUS, 1.0F);
        // if (entityIn instanceof LivingEntity) {
        //     LivingEntity living = (LivingEntity)entityIn;
        //     living.addEffect(new EffectInstance((Effect) AbundanceEffects.SUCCUMBING.get(), 1200, 0, false, true, true));
        // }

    }
}
