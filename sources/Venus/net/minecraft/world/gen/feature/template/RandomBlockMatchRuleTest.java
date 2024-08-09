/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.template.IRuleTestType;
import net.minecraft.world.gen.feature.template.RuleTest;

public class RandomBlockMatchRuleTest
extends RuleTest {
    public static final Codec<RandomBlockMatchRuleTest> field_237117_a_ = RecordCodecBuilder.create(RandomBlockMatchRuleTest::lambda$static$2);
    private final Block block;
    private final float probability;

    public RandomBlockMatchRuleTest(Block block, float f) {
        this.block = block;
        this.probability = f;
    }

    @Override
    public boolean test(BlockState blockState, Random random2) {
        return blockState.isIn(this.block) && random2.nextFloat() < this.probability;
    }

    @Override
    protected IRuleTestType<?> getType() {
        return IRuleTestType.RANDOM_BLOCK_MATCH;
    }

    private static App lambda$static$2(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Registry.BLOCK.fieldOf("block")).forGetter(RandomBlockMatchRuleTest::lambda$static$0), ((MapCodec)Codec.FLOAT.fieldOf("probability")).forGetter(RandomBlockMatchRuleTest::lambda$static$1)).apply(instance, RandomBlockMatchRuleTest::new);
    }

    private static Float lambda$static$1(RandomBlockMatchRuleTest randomBlockMatchRuleTest) {
        return Float.valueOf(randomBlockMatchRuleTest.probability);
    }

    private static Block lambda$static$0(RandomBlockMatchRuleTest randomBlockMatchRuleTest) {
        return randomBlockMatchRuleTest.block;
    }
}

