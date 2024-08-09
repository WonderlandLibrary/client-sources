/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.feature.template.IPosRuleTests;
import net.minecraft.world.gen.feature.template.PosRuleTest;

public class LinearPosTest
extends PosRuleTest {
    public static final Codec<LinearPosTest> field_237087_a_ = RecordCodecBuilder.create(LinearPosTest::lambda$static$4);
    private final float field_237088_b_;
    private final float field_237089_d_;
    private final int field_237090_e_;
    private final int field_237091_f_;

    public LinearPosTest(float f, float f2, int n, int n2) {
        if (n >= n2) {
            throw new IllegalArgumentException("Invalid range: [" + n + "," + n2 + "]");
        }
        this.field_237088_b_ = f;
        this.field_237089_d_ = f2;
        this.field_237090_e_ = n;
        this.field_237091_f_ = n2;
    }

    @Override
    public boolean func_230385_a_(BlockPos blockPos, BlockPos blockPos2, BlockPos blockPos3, Random random2) {
        int n = blockPos2.manhattanDistance(blockPos3);
        float f = random2.nextFloat();
        return (double)f <= MathHelper.clampedLerp(this.field_237088_b_, this.field_237089_d_, MathHelper.func_233020_c_(n, this.field_237090_e_, this.field_237091_f_));
    }

    @Override
    protected IPosRuleTests<?> func_230384_a_() {
        return IPosRuleTests.field_237104_b_;
    }

    private static App lambda$static$4(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.FLOAT.fieldOf("min_chance")).orElse(Float.valueOf(0.0f)).forGetter(LinearPosTest::lambda$static$0), ((MapCodec)Codec.FLOAT.fieldOf("max_chance")).orElse(Float.valueOf(0.0f)).forGetter(LinearPosTest::lambda$static$1), ((MapCodec)Codec.INT.fieldOf("min_dist")).orElse(0).forGetter(LinearPosTest::lambda$static$2), ((MapCodec)Codec.INT.fieldOf("max_dist")).orElse(0).forGetter(LinearPosTest::lambda$static$3)).apply(instance, LinearPosTest::new);
    }

    private static Integer lambda$static$3(LinearPosTest linearPosTest) {
        return linearPosTest.field_237091_f_;
    }

    private static Integer lambda$static$2(LinearPosTest linearPosTest) {
        return linearPosTest.field_237090_e_;
    }

    private static Float lambda$static$1(LinearPosTest linearPosTest) {
        return Float.valueOf(linearPosTest.field_237089_d_);
    }

    private static Float lambda$static$0(LinearPosTest linearPosTest) {
        return Float.valueOf(linearPosTest.field_237088_b_);
    }
}

