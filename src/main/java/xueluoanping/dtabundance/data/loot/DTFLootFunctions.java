package xueluoanping.dtabundance.data.loot;

import com.ferreusveritas.dynamictrees.loot.function.MultiplyCount;
import com.ferreusveritas.dynamictrees.loot.function.MultiplyLogsCount;
import com.ferreusveritas.dynamictrees.loot.function.MultiplySticksCount;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.BiomeDictionary;
import xueluoanping.dtabundance.DTAbundance;

public class DTFLootFunctions {
    public static LootFunctionType HALF_COUNT ;

    private static LootFunctionType register(String name, ILootSerializer<? extends ILootFunction> serializer) {
        return Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(name), new LootFunctionType(serializer));
    }

    /** Invoked to initialise static fields. */
    public static void load() {

        HALF_COUNT = register(DTAbundance.rl("half_count").toString(), new HalfCount.Serializer());
    }
}
