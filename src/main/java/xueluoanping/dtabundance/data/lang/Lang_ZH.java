package xueluoanping.dtabundance.data.lang;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import xueluoanping.dtabundance.util.RegisterFinderUtil;


public class Lang_ZH extends LangHelper {
	public Lang_ZH(DataGenerator gen, ExistingFileHelper helper,String modid) {
		super(gen, helper,modid, "zh_cn");
	}


	@Override
	protected void addTranslations() {
		// add(modid, "动态的树：长沼蓝调附属");
		add(RegisterFinderUtil.getBlock("dtabundance:saguaro_flower"), "树形仙人掌花");

		add(RegisterFinderUtil.getBlock("dtabundance:jacaranda_sapling"), "蓝花楹树苗");
		add(RegisterFinderUtil.getBlock("dtabundance:redbud_sapling"), "紫荆树苗");
		add(RegisterFinderUtil.getBlock("dtabundance:flowering_jacaranda_sapling"), "开花蓝花楹树苗");
		add(RegisterFinderUtil.getBlock("dtabundance:flowering_redbud_sapling"), "开花紫荆树苗");
		add(RegisterFinderUtil.getBlock("dtabundance:saguaro_cactus_sapling"), "树形仙人掌幼苗");
		add(RegisterFinderUtil.getBlock("dtabundance:jacaranda_branch"), "蓝花楹木");
		add(RegisterFinderUtil.getBlock("dtabundance:redbud_branch"), "紫荆木");
		add(RegisterFinderUtil.getBlock("dtabundance:saguaro_cactus_branch"), "树形仙人掌");
		// add(RegisterFinderUtil.getBlock("dtabundance:flowering_jacaranda_branch"), "开花蓝花楹木");
		// add(RegisterFinderUtil.getBlock("dtabundance:flowering_redbud_branch"), "开花紫荆木");
		add(RegisterFinderUtil.getItem("dtabundance:jacaranda_seed"), "蓝花楹种子");
		add(RegisterFinderUtil.getItem("dtabundance:redbud_seed"), "紫荆种子");
		add(RegisterFinderUtil.getItem("dtabundance:saguaro_cactus_seed"), "树形仙人掌种子");
		// add(RegisterFinderUtil.getItem("dtabundance:flowering_jacaranda_seed"), "开花蓝花楹种子");
		add(RegisterFinderUtil.getItem("dtabundance:flowering_redbud_seed"), "开花紫荆种子");
		addSpecie("jacaranda","蓝花楹树");
		addSpecie("redbud","紫荆树");
		addSpecie("flowering_jacaranda","开花蓝花楹树");
		addSpecie("flowering_redbud","开花紫荆树");
		addSpecie("saguaro_cactus","树形仙人掌");





	}


}
