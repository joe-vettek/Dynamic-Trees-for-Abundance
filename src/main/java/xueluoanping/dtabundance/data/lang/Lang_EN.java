package xueluoanping.dtabundance.data.lang;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import xueluoanping.dtabundance.util.RegisterFinderUtil;

public class Lang_EN extends LangHelper {
    public Lang_EN(DataGenerator gen, ExistingFileHelper helper,String modid) {
        super(gen, helper,modid, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(modid, "Dynamic Trees for Abundance");
        add(RegisterFinderUtil.getBlock("dtabundance:saguaro_flower"), "Saguaro Flower");

        add(RegisterFinderUtil.getBlock("dtabundance:jacaranda_sapling"), "Jacaranda Sapling");
        add(RegisterFinderUtil.getBlock("dtabundance:redbud_sapling"), "Redbud Sapling");
        add(RegisterFinderUtil.getBlock("dtabundance:flowering_jacaranda_sapling"), "Flowering Jacaranda Sapling");
        add(RegisterFinderUtil.getBlock("dtabundance:flowering_redbud_sapling"), "Flowering Redbud Sapling");
        add(RegisterFinderUtil.getBlock("dtabundance:saguaro_cactus_sapling"), "Saguaro Seedling");
        add(RegisterFinderUtil.getBlock("dtabundance:jacaranda_branch"), "Jacaranda Tree");
        add(RegisterFinderUtil.getBlock("dtabundance:redbud_branch"), "Redbud Tree");
        add(RegisterFinderUtil.getBlock("dtabundance:saguaro_cactus_branch"), "Saguaro Cactus");
        // add(RegisterFinderUtil.getBlock("dtabundance:flowering_jacaranda_branch"), "Flowering Jacaranda Tree");
        // add(RegisterFinderUtil.getBlock("dtabundance:flowering_redbud_branch"), "Flowering Redbud Tree");
        add(RegisterFinderUtil.getItem("dtabundance:jacaranda_seed"), "Jacaranda Seed");
        add(RegisterFinderUtil.getItem("dtabundance:redbud_seed"), "Redbud Seed");
        add(RegisterFinderUtil.getItem("dtabundance:saguaro_cactus_seed"), "Saguaro Cactus Seed");
        // add(RegisterFinderUtil.getItem("dtabundance:flowering_jacaranda_seed"), "Flowering Jacaranda Seed");
        add(RegisterFinderUtil.getItem("dtabundance:flowering_redbud_seed"), "Flowering Redbud Seed");
        addSpecie("jacaranda","Jacaranda");
        addSpecie("redbud","Redbud");
        addSpecie("flowering_jacaranda","Flowering Jacaranda");
        addSpecie("flowering_redbud","Flowering Redbud");
        addSpecie("saguaro_cactus","Saguaro Cactus");





    }
}
