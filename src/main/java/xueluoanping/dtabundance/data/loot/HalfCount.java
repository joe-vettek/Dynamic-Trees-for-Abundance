package xueluoanping.dtabundance.data.loot;


import com.ferreusveritas.dynamictrees.loot.function.DTLootFunctions;
import com.ferreusveritas.dynamictrees.loot.function.MultiplyLogsCount;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.JSONUtils;


public final class HalfCount extends LootFunction {

    private final float percent;

    public HalfCount(ILootCondition[] conditions, float percent) {
        super(conditions);
        this.percent = percent;
    }

    @Override
    public LootFunctionType getType() {
        return DTFLootFunctions.HALF_COUNT;
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        stack.setCount((int) (stack.getCount() * percent));
        return stack;
    }
    public static ILootFunction.IBuilder HalfCount(float percent) {
        return () -> new HalfCount(new ILootCondition[0],percent);
    }
    public static class Serializer extends LootFunction.Serializer<HalfCount> {
        @Override
        public void serialize(JsonObject json, HalfCount value, JsonSerializationContext context) {
            super.serialize(json, value, context);
            json.addProperty("percent", value.percent);
        }

        @Override
        public HalfCount deserialize(JsonObject json, JsonDeserializationContext context, ILootCondition[] conditions) {
            return new HalfCount(conditions, JSONUtils.getAsFloat(json, "percent"));
        }
    }

}
