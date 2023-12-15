package xueluoanping.dtabundance.systems.species;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.blocks.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.systems.nodemappers.NetVolumeNode;
import com.ferreusveritas.dynamictrees.trees.Family;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictreesplus.trees.CactusSpecies;
import com.teamaurora.abundance.core.registry.AbundanceBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SaguaroCactusSpecies extends CactusSpecies {
    public static final TypedRegistry.EntryType<Species> TYPE = createDefaultType(SaguaroCactusSpecies::new);

    public SaguaroCactusSpecies(ResourceLocation name, Family family, LeavesProperties leavesProperties) {
        super(name, family, leavesProperties);
    }


    @Override
    public LogsAndSticks getLogsAndSticks(NetVolumeNode.Volume volume) {
        LogsAndSticks logsAndSticks = super.getLogsAndSticks(volume);
        AtomicInteger logCount = new AtomicInteger(0);
        logsAndSticks.logs.forEach(itemStack -> logCount.addAndGet(itemStack.getCount()));
        List<ItemStack> logsList = new LinkedList<>();
        ItemStack stack1 = AbundanceBlocks.SAGUARO_CACTUS.get().asItem().getDefaultInstance();
        ItemStack stack2 = AbundanceBlocks.SMALL_SAGUARO_CACTUS.get().asItem().getDefaultInstance();
        stack1.setCount((int) (logCount.get() * 0.65));
        stack2.setCount((int) (logCount.get() * 0.35));
        if (stack1.getCount() > 0)
            logsList.add(stack1);
        if (stack2.getCount() > 0)
            logsList.add(stack2);
        logsAndSticks.logs=logsList;
        return logsAndSticks;
    }
}
