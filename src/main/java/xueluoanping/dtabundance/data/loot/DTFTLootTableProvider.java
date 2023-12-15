package xueluoanping.dtabundance.data.loot;

import com.ferreusveritas.dynamictrees.blocks.branches.BranchBlock;
import com.ferreusveritas.dynamictrees.data.provider.DTLootTableProvider;
import com.ferreusveritas.dynamictrees.loot.DTLootParameterSets;
import com.ferreusveritas.dynamictrees.loot.entry.SeedItemLootEntry;
import com.ferreusveritas.dynamictrees.loot.function.MultiplyLogsCount;
import com.ferreusveritas.dynamictrees.loot.function.MultiplySticksCount;
import com.ferreusveritas.dynamictrees.util.ResourceLocationUtils;
import com.ferreusveritas.dynamictreesplus.blocks.CactusBranchBlock;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teamaurora.abundance.core.registry.AbundanceBlocks;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.functions.ExplosionDecay;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import xueluoanping.dtabundance.DTAbundance;
import xueluoanping.dtabundance.systems.family.BlockRedBudBranch;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

//  I inherited DTLootTableProvider, but many of its functions are private,
//  so I have to copy them to facilitate modification.

public class DTFTLootTableProvider extends DTLootTableProvider {

    private static final ILootCondition.IBuilder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item()
            .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
    private static final ILootCondition.IBuilder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
    private static final ILootCondition.IBuilder HAS_SHEARS =
            MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    private static final ILootCondition.IBuilder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    private static final ILootCondition.IBuilder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();

    private final DataGenerator generator;
    private final String modId;
    private final ExistingFileHelper existingFileHelper;

    public DTFTLootTableProvider(DataGenerator generator, String modId, ExistingFileHelper existingFileHelper) {
        super(generator, modId, existingFileHelper);
        this.generator = generator;
        this.modId = modId;
        this.existingFileHelper = existingFileHelper;
    }

    // The reason why these functions appear is that
    // the loot table of the leaves block needs to be overwritten.
    @Override
    public void run(DirectoryCache cache) {

        // First generate the default
        super.run(cache);

        // Now overwrite and generate the parts that need to be customized.
        addTables();
        writeTables(cache);
    }


    private void addTables() {


        ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> block instanceof BranchBlock)
                .map(block -> (BranchBlock) block)
                .filter(block -> block.getRegistryName().getNamespace().equals(modId))
                .filter(branchBlock -> (branchBlock.getRegistryName() + "").equals(DTAbundance.rl("saguaro_cactus_branch").toString()))
                .forEach(this::addBranchTable);
    }

    private void writeTables(DirectoryCache cache) {
        Path outputFolder = this.generator.getOutputFolder();
        lootTables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/" + key.getPath());
            try {
                IDataProvider.save(GSON, cache, LootTableManager.serialize(lootTable.build()), path);
            } catch (IOException e) {
                DTAbundance.LOGGER.error("Couldn't write loot table {}", path, e);
            }
        });
    }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private final Map<ResourceLocation, LootTable.Builder> lootTables = new HashMap<>();

    private ResourceLocation getFullDropsPath(ResourceLocation path) {
        return ResourceLocationUtils.surround(path, "loot_tables/", ".json");
    }

    // // The only changed behavior is here.
    private void addBranchTable(BranchBlock branchBlock) {

        if (branchBlock.shouldGenerateBranchDrops()) {
            final ResourceLocation branchTablePath = getFullDropsPath(branchBlock.getLootTableName());
            if (!existingFileHelper.exists(branchTablePath, ResourcePackType.SERVER_DATA)) {
                lootTables.put(branchTablePath, createBlockRedBudDrops(((CactusBranchBlock) branchBlock), branchBlock.getFamily().getStick(1).getItem()));
            }
        }
    }

    public static LootTable.Builder createBlockRedBudDrops(CactusBranchBlock primitiveLogBlock, Item stickItem) {
        return LootTable.lootTable().withPool(
                        LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(
                                ItemLootEntry.lootTableItem(AbundanceBlocks.SAGUARO_CACTUS.get())
                                        .apply(MultiplyLogsCount.multiplyLogsCount())
                                        .apply(HalfCount.HalfCount(0.65f))
                                        .apply(ExplosionDecay.explosionDecay())
                                // .when(BlockStateProperty.hasBlockStateProperties(primitiveLogBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockRedBud.IS_FLOWERING, true)))

                        )
                )
                .withPool(
                        LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(
                                ItemLootEntry.lootTableItem(AbundanceBlocks.SMALL_SAGUARO_CACTUS.get())
                                        .apply(MultiplyLogsCount.multiplyLogsCount())
                                        .apply(HalfCount.HalfCount(0.35f))
                                        .apply(ExplosionDecay.explosionDecay())
                                // .when(BlockStateProperty.hasBlockStateProperties(primitiveLogBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockRedBud.IS_FLOWERING, true)))
                        )
                )
                .withPool(
                        LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(
                                SeedItemLootEntry.lootTableSeedItem()
                                        .apply(MultiplySticksCount.multiplySticksCount())
                                        .apply(ExplosionDecay.explosionDecay())
                        )
                ).setParamSet(DTLootParameterSets.BRANCHES);
    }
}
