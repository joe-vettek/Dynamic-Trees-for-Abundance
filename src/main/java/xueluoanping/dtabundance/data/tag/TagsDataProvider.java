package xueluoanping.dtabundance.data.tag;

import javax.annotation.Nullable;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;

import net.minecraftforge.common.data.ExistingFileHelper;


public class TagsDataProvider extends BlockTagsProvider {
    public TagsDataProvider(DataGenerator generatorIn, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {

    }

}
