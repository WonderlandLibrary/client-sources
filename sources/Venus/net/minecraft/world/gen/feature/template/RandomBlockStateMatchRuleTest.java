/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.template.IRuleTestType;
import net.minecraft.world.gen.feature.template.RuleTest;

public class RandomBlockStateMatchRuleTest
extends RuleTest {
    public static final Codec<RandomBlockStateMatchRuleTest> field_237121_a_ = RecordCodecBuilder.create(RandomBlockStateMatchRuleTest::lambda$static$2);
    private final BlockState state;
    private final float probability;

    public RandomBlockStateMatchRuleTest(BlockState blockState, float f) {
        this.state = blockState;
        this.probability = f;
    }

    @Override
    public boolean test(BlockState blockState, Random random2) {
        return blockState == this.state && random2.nextFloat() < this.probability;
    }

    @Override
    protected IRuleTestType<?> getType() {
        return IRuleTestType.RANDOM_BLOCKSTATE_MATCH;
    }

    private static App lambda$static$2(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)BlockState.CODEC.fieldOf("block_state")).forGetter(RandomBlockStateMatchRuleTest::lambda$static$0), ((MapCodec)Codec.FLOAT.fieldOf("probability")).forGetter(RandomBlockStateMatchRuleTest::lambda$static$1)).apply(instance, RandomBlockStateMatchRuleTest::new);
    }

    private static Float lambda$static$1(RandomBlockStateMatchRuleTest randomBlockStateMatchRuleTest) {
        return Float.valueOf(randomBlockStateMatchRuleTest.probability);
    }

    private static BlockState lambda$static$0(RandomBlockStateMatchRuleTest randomBlockStateMatchRuleTest) {
        return randomBlockStateMatchRuleTest.state;
    }
}

