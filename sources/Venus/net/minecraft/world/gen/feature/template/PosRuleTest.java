/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.template.IPosRuleTests;

public abstract class PosRuleTest {
    public static final Codec<PosRuleTest> field_237102_c_ = Registry.POS_RULE_TEST.dispatch("predicate_type", PosRuleTest::func_230384_a_, IPosRuleTests::codec);

    public abstract boolean func_230385_a_(BlockPos var1, BlockPos var2, BlockPos var3, Random var4);

    protected abstract IPosRuleTests<?> func_230384_a_();
}

