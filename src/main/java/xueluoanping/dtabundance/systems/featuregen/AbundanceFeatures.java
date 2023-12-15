package xueluoanping.dtabundance.systems.featuregen;

import com.ferreusveritas.dynamictrees.api.registry.Registry;
import com.ferreusveritas.dynamictrees.systems.genfeatures.GenFeature;
import net.minecraft.util.ResourceLocation;

import xueluoanping.dtabundance.DTAbundance;

public class AbundanceFeatures {
    public static final GenFeature ALT_BRANCH = new FeatureGenFloweringBranch(regName("alt_branch"));
    // public static final GenFeature VINES = new VinesGenFeature(regName("vines"));
    // public static final GenFeature SPINES = new FeatureGenSpine(regName("spines"));
    private static ResourceLocation regName(String name) {
        return new ResourceLocation(DTAbundance.MOD_ID, name);
    }

    public static void register(final Registry<GenFeature> registry) {
        registry.registerAll(ALT_BRANCH);
    }
}
