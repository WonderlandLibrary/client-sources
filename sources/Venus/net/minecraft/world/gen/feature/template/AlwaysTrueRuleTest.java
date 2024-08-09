/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.template.IRuleTestType;
import net.minecraft.world.gen.feature.template.RuleTest;

public class AlwaysTrueRuleTest
extends RuleTest {
    public static final Codec<AlwaysTrueRuleTest> field_237043_a_;
    public static final AlwaysTrueRuleTest INSTANCE;

    private AlwaysTrueRuleTest() {
    }

    @Override
    public boolean test(BlockState blockState, Random random2) {
        return false;
    }

    @Override
    protected IRuleTestType<?> getType() {
        return IRuleTestType.ALWAYS_TRUE;
    }

    private static AlwaysTrueRuleTest lambda$static$0() {
        return INSTANCE;
    }

    static {
        INSTANCE = new AlwaysTrueRuleTest();
        field_237043_a_ = Codec.unit(AlwaysTrueRuleTest::lambda$static$0);
    }
}

