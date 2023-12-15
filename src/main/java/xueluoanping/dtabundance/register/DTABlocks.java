package xueluoanping.dtabundance.register;

import com.ferreusveritas.dynamictrees.DynamicTrees;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xueluoanping.dtabundance.DTAbundance;
import xueluoanping.dtabundance.systems.family.BlockFloweringRedBudBranch;

public class DTABlocks {
    public static final DeferredRegister<Item> DRBlockItems = DeferredRegister.create(ForgeRegistries.ITEMS, DTAbundance.MOD_ID);
    public static final DeferredRegister<Block> DRBlocks = DeferredRegister.create(ForgeRegistries.BLOCKS, DTAbundance.MOD_ID);

    // public static final RegistryObject<Block> bamboo =
    //         DRBlocks.register("flowering_redbud_branch", ()->new BlockFloweringRedBudBranch(DTAbundance.rl("flowering_redbud_branch"), Blocks.OAK_LOG.));
    // public static final RegistryObject<Item> bamboo_item =
    //         DRBlockItems.register("flowering_redbud_branch", ()->new BlockItem(bamboo.get(),(new Item.Properties())));


}
