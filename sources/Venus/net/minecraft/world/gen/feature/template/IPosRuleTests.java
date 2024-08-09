/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.template.AlwaysTrueTest;
import net.minecraft.world.gen.feature.template.AxisAlignedLinearPosTest;
import net.minecraft.world.gen.feature.template.LinearPosTest;
import net.minecraft.world.gen.feature.template.PosRuleTest;

public interface IPosRuleTests<P extends PosRuleTest> {
    public static final IPosRuleTests<AlwaysTrueTest> field_237103_a_ = IPosRuleTests.func_237107_a_("always_true", AlwaysTrueTest.field_237099_a_);
    public static final IPosRuleTests<LinearPosTest> field_237104_b_ = IPosRuleTests.func_237107_a_("linear_pos", LinearPosTest.field_237087_a_);
    public static final IPosRuleTests<AxisAlignedLinearPosTest> field_237105_c_ = IPosRuleTests.func_237107_a_("axis_aligned_linear_pos", AxisAlignedLinearPosTest.field_237045_a_);

    public Codec<P> codec();

    public static <P extends PosRuleTest> IPosRuleTests<P> func_237107_a_(String string, Codec<P> codec) {
        return Registry.register(Registry.POS_RULE_TEST, string, () -> IPosRuleTests.lambda$func_237107_a_$0(codec));
    }

    private static Codec lambda$func_237107_a_$0(Codec codec) {
        return codec;
    }
}

