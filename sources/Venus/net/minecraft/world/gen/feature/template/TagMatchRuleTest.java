/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.world.gen.feature.template.IRuleTestType;
import net.minecraft.world.gen.feature.template.RuleTest;

public class TagMatchRuleTest
extends RuleTest {
    public static final Codec<TagMatchRuleTest> field_237161_a_ = ((MapCodec)ITag.getTagCodec(TagMatchRuleTest::lambda$static$0).fieldOf("tag")).xmap(TagMatchRuleTest::new, TagMatchRuleTest::lambda$static$1).codec();
    private final ITag<Block> tag;

    public TagMatchRuleTest(ITag<Block> iTag) {
        this.tag = iTag;
    }

    @Override
    public boolean test(BlockState blockState, Random random2) {
        return blockState.isIn(this.tag);
    }

    @Override
    protected IRuleTestType<?> getType() {
        return IRuleTestType.TAG_MATCH;
    }

    private static ITag lambda$static$1(TagMatchRuleTest tagMatchRuleTest) {
        return tagMatchRuleTest.tag;
    }

    private static ITagCollection lambda$static$0() {
        return TagCollectionManager.getManager().getBlockTags();
    }
}

