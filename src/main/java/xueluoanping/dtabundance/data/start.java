package xueluoanping.dtabundance.data;

import com.ferreusveritas.dynamictrees.data.provider.DTBlockTagsProvider;
import com.ferreusveritas.dynamictrees.data.provider.DTItemTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import xueluoanping.dtabundance.DTAbundance;
import xueluoanping.dtabundance.data.lang.Lang_EN;
import xueluoanping.dtabundance.data.lang.Lang_ZH;
import xueluoanping.dtabundance.data.loot.DTFLootFunctions;
import xueluoanping.dtabundance.data.loot.DTFTLootTableProvider;


public class start {
    public final static String MODID = DTAbundance.MOD_ID;

    public static void dataGen(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        if (event.includeServer()) {
            DTAbundance.logger("Generate recipe");

            generator.addProvider(new Lang_EN(generator, helper,MODID));
            generator.addProvider(new Lang_ZH(generator, helper,MODID));
            // generator.addProvider(new RecipeDataProvider(generator));

            DTBlockTagsProvider blockTags = new DTBlockTagsProvider(generator, MODID, helper);
            generator.addProvider(blockTags);
            generator.addProvider(new DTItemTagsProvider(generator, MODID, blockTags, helper));

            DTFLootFunctions.load();
            generator.addProvider(new DTFTLootTableProvider(generator,MODID,helper));
            // generator.addProvider(new GLMProvider(generator, MODID));



            // generator.addProvider(new SimpleMP(generator));

        }
        if (event.includeClient()) {
            // generator.addProvider(new BlockStatesDataProvider(generator, helper));
            // generator.addProvider(new ItemModelProvider(generator, helper));
        }


    }
}
