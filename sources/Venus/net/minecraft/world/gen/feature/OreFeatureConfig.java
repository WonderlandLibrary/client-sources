/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;

public class OreFeatureConfig
implements IFeatureConfig {
    public static final Codec<OreFeatureConfig> field_236566_a_ = RecordCodecBuilder.create(OreFeatureConfig::lambda$static$3);
    public final RuleTest target;
    public final int size;
    public final BlockState state;

    public OreFeatureConfig(RuleTest ruleTest, BlockState blockState, int n) {
        this.size = n;
        this.state = blockState;
        this.target = ruleTest;
    }

    private static App lambda$static$3(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)RuleTest.field_237127_c_.fieldOf("target")).forGetter(OreFeatureConfig::lambda$static$0), ((MapCodec)BlockState.CODEC.fieldOf("state")).forGetter(OreFeatureConfig::lambda$static$1), ((MapCodec)Codec.intRange(0, 64).fieldOf("size")).forGetter(OreFeatureConfig::lambda$static$2)).apply(instance, OreFeatureConfig::new);
    }

    private static Integer lambda$static$2(OreFeatureConfig oreFeatureConfig) {
        return oreFeatureConfig.size;
    }

    private static BlockState lambda$static$1(OreFeatureConfig oreFeatureConfig) {
        return oreFeatureConfig.state;
    }

    private static RuleTest lambda$static$0(OreFeatureConfig oreFeatureConfig) {
        return oreFeatureConfig.target;
    }

    public static final class FillerBlockType {
        public static final RuleTest field_241882_a = new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD);
        public static final RuleTest field_241883_b = new BlockMatchRuleTest(Blocks.NETHERRACK);
        public static final RuleTest field_241884_c = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
    }
}

