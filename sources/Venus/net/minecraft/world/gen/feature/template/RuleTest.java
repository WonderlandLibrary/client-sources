/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.template.IRuleTestType;

public abstract class RuleTest {
    public static final Codec<RuleTest> field_237127_c_ = Registry.RULE_TEST.dispatch("predicate_type", RuleTest::getType, IRuleTestType::codec);

    public abstract boolean test(BlockState var1, Random var2);

    protected abstract IRuleTestType<?> getType();
}

