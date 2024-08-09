/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.template.IPosRuleTests;
import net.minecraft.world.gen.feature.template.PosRuleTest;

public class AlwaysTrueTest
extends PosRuleTest {
    public static final Codec<AlwaysTrueTest> field_237099_a_;
    public static final AlwaysTrueTest field_237100_b_;

    private AlwaysTrueTest() {
    }

    @Override
    public boolean func_230385_a_(BlockPos blockPos, BlockPos blockPos2, BlockPos blockPos3, Random random2) {
        return false;
    }

    @Override
    protected IPosRuleTests<?> func_230384_a_() {
        return IPosRuleTests.field_237103_a_;
    }

    private static AlwaysTrueTest lambda$static$0() {
        return field_237100_b_;
    }

    static {
        field_237100_b_ = new AlwaysTrueTest();
        field_237099_a_ = Codec.unit(AlwaysTrueTest::lambda$static$0);
    }
}

