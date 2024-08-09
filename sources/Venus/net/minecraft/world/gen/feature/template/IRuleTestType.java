/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.template.AlwaysTrueRuleTest;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.BlockStateMatchRuleTest;
import net.minecraft.world.gen.feature.template.RandomBlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RandomBlockStateMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;

public interface IRuleTestType<P extends RuleTest> {
    public static final IRuleTestType<AlwaysTrueRuleTest> ALWAYS_TRUE = IRuleTestType.func_237129_a_("always_true", AlwaysTrueRuleTest.field_237043_a_);
    public static final IRuleTestType<BlockMatchRuleTest> BLOCK_MATCH = IRuleTestType.func_237129_a_("block_match", BlockMatchRuleTest.field_237075_a_);
    public static final IRuleTestType<BlockStateMatchRuleTest> BLOCKSTATE_MATCH = IRuleTestType.func_237129_a_("blockstate_match", BlockStateMatchRuleTest.field_237079_a_);
    public static final IRuleTestType<TagMatchRuleTest> TAG_MATCH = IRuleTestType.func_237129_a_("tag_match", TagMatchRuleTest.field_237161_a_);
    public static final IRuleTestType<RandomBlockMatchRuleTest> RANDOM_BLOCK_MATCH = IRuleTestType.func_237129_a_("random_block_match", RandomBlockMatchRuleTest.field_237117_a_);
    public static final IRuleTestType<RandomBlockStateMatchRuleTest> RANDOM_BLOCKSTATE_MATCH = IRuleTestType.func_237129_a_("random_blockstate_match", RandomBlockStateMatchRuleTest.field_237121_a_);

    public Codec<P> codec();

    public static <P extends RuleTest> IRuleTestType<P> func_237129_a_(String string, Codec<P> codec) {
        return Registry.register(Registry.RULE_TEST, string, () -> IRuleTestType.lambda$func_237129_a_$0(codec));
    }

    private static Codec lambda$func_237129_a_$0(Codec codec) {
        return codec;
    }
}

