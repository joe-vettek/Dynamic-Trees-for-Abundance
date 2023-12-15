package xueluoanping.dtabundance;

import com.ferreusveritas.dynamictrees.api.registry.RegistryEvent;
import com.ferreusveritas.dynamictrees.api.registry.TypeRegistryEvent;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors;
import com.ferreusveritas.dynamictrees.api.worldgen.FeatureCanceller;
import com.ferreusveritas.dynamictrees.blocks.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.systems.genfeatures.GenFeature;
import com.ferreusveritas.dynamictrees.trees.Family;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictreesplus.DynamicTreesPlus;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import xueluoanping.dtabundance.systems.family.RedBudFamily;
import xueluoanping.dtabundance.systems.family.SaguaroCactusFamily;
import xueluoanping.dtabundance.systems.featuregen.AbundanceFeatures;
import xueluoanping.dtabundance.systems.species.FloweringJacarandaSpecies;
import xueluoanping.dtabundance.systems.species.SaguaroCactusSpecies;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DTDTAbundanceRegistries {

    @SubscribeEvent
    public static void registerLeavesPropertiesTypes(final TypeRegistryEvent<LeavesProperties> event) {
        // event.registerType(new ResourceLocation(DTAbundance.MOD_ID, "rankine_trees"), RankineLeavesProperties.TYPE);
    }

    @SubscribeEvent
    public static void registerFamily(final TypeRegistryEvent<Family> event) {
        event.registerType(new ResourceLocation(DTAbundance.MOD_ID, "redbud"), RedBudFamily.TYPE);
        if (ModList.get().isLoaded(DynamicTreesPlus.MOD_ID))
            event.registerType(new ResourceLocation(DTAbundance.MOD_ID, "saguaro_cactus"), SaguaroCactusFamily.TYPE);
    }

    @SubscribeEvent
    public static void registerSpecies(final TypeRegistryEvent<Species> event) {
        event.registerType(new ResourceLocation(DTAbundance.MOD_ID, "flowering_jacaranda"), FloweringJacarandaSpecies.TYPE);
        if (ModList.get().isLoaded(DynamicTreesPlus.MOD_ID))
            event.registerType(new ResourceLocation(DTAbundance.MOD_ID, "saguaro_cactus"), SaguaroCactusSpecies.TYPE);

    }

    // public static final FeatureCanceller FRUIT_TREES_CANCELLER = new FeatureCanceller(new ResourceLocation(DTAbundance.MOD_ID, "saguaro_cactus")) {
    //     @Override
    //     public boolean shouldCancel(ConfiguredFeature<?, ?> configuredFeature, BiomePropertySelectors.FeatureCancellations featureCancellations) {
    //         // Note it not in ForgeRegistries.FEATURES
    //         final ResourceLocation featureName = WorldGenRegistries.CONFIGURED_FEATURE.getKey(configuredFeature);
    //         if (featureName == null) {
    //             return false;
    //         }
    //         // DTAbundance.logger("TESTAS",featureName);
    //         return featureCancellations.shouldCancelNamespace(featureName.getNamespace())
    //                 && (WorldGenRegistries.CONFIGURED_FEATURE.getKey(configuredFeature) + "").startsWith("fruittrees");
    //     }
    //
    // };
    //
    // @SubscribeEvent
    // public static void onFeatureCancellerRegistry(final RegistryEvent<FeatureCanceller> event) {
    //     event.getRegistry().registerAll(FRUIT_TREES_CANCELLER);
    // }

    @SubscribeEvent
    public static void onGenFeatureRegistry(final RegistryEvent<GenFeature> event) {
        AbundanceFeatures.register(event.getRegistry());
    }

    // @SubscribeEvent
    // public static void onGrowthLogicKitRegistry(final RegistryEvent<GrowthLogicKit> event) {
    //     DTRankineGrowthLogicKits.register(event.getRegistry());
    // }
}
