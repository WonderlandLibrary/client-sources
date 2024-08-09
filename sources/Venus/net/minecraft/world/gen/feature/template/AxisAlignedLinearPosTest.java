/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.feature.template.IPosRuleTests;
import net.minecraft.world.gen.feature.template.PosRuleTest;

public class AxisAlignedLinearPosTest
extends PosRuleTest {
    public static final Codec<AxisAlignedLinearPosTest> field_237045_a_ = RecordCodecBuilder.create(AxisAlignedLinearPosTest::lambda$static$5);
    private final float field_237046_b_;
    private final float field_237047_d_;
    private final int field_237048_e_;
    private final int field_237049_f_;
    private final Direction.Axis field_237050_g_;

    public AxisAlignedLinearPosTest(float f, float f2, int n, int n2, Direction.Axis axis) {
        if (n >= n2) {
            throw new IllegalArgumentException("Invalid range: [" + n + "," + n2 + "]");
        }
        this.field_237046_b_ = f;
        this.field_237047_d_ = f2;
        this.field_237048_e_ = n;
        this.field_237049_f_ = n2;
        this.field_237050_g_ = axis;
    }

    @Override
    public boolean func_230385_a_(BlockPos blockPos, BlockPos blockPos2, BlockPos blockPos3, Random random2) {
        Direction direction = Direction.getFacingFromAxis(Direction.AxisDirection.POSITIVE, this.field_237050_g_);
        float f = Math.abs((blockPos2.getX() - blockPos3.getX()) * direction.getXOffset());
        float f2 = Math.abs((blockPos2.getY() - blockPos3.getY()) * direction.getYOffset());
        float f3 = Math.abs((blockPos2.getZ() - blockPos3.getZ()) * direction.getZOffset());
        int n = (int)(f + f2 + f3);
        float f4 = random2.nextFloat();
        return (double)f4 <= MathHelper.clampedLerp(this.field_237046_b_, this.field_237047_d_, MathHelper.func_233020_c_(n, this.field_237048_e_, this.field_237049_f_));
    }

    @Override
    protected IPosRuleTests<?> func_230384_a_() {
        return IPosRuleTests.field_237105_c_;
    }

    private static App lambda$static$5(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.FLOAT.fieldOf("min_chance")).orElse(Float.valueOf(0.0f)).forGetter(AxisAlignedLinearPosTest::lambda$static$0), ((MapCodec)Codec.FLOAT.fieldOf("max_chance")).orElse(Float.valueOf(0.0f)).forGetter(AxisAlignedLinearPosTest::lambda$static$1), ((MapCodec)Codec.INT.fieldOf("min_dist")).orElse(0).forGetter(AxisAlignedLinearPosTest::lambda$static$2), ((MapCodec)Codec.INT.fieldOf("max_dist")).orElse(0).forGetter(AxisAlignedLinearPosTest::lambda$static$3), ((MapCodec)Direction.Axis.CODEC.fieldOf("axis")).orElse(Direction.Axis.Y).forGetter(AxisAlignedLinearPosTest::lambda$static$4)).apply(instance, AxisAlignedLinearPosTest::new);
    }

    private static Direction.Axis lambda$static$4(AxisAlignedLinearPosTest axisAlignedLinearPosTest) {
        return axisAlignedLinearPosTest.field_237050_g_;
    }

    private static Integer lambda$static$3(AxisAlignedLinearPosTest axisAlignedLinearPosTest) {
        return axisAlignedLinearPosTest.field_237049_f_;
    }

    private static Integer lambda$static$2(AxisAlignedLinearPosTest axisAlignedLinearPosTest) {
        return axisAlignedLinearPosTest.field_237048_e_;
    }

    private static Float lambda$static$1(AxisAlignedLinearPosTest axisAlignedLinearPosTest) {
        return Float.valueOf(axisAlignedLinearPosTest.field_237047_d_);
    }

    private static Float lambda$static$0(AxisAlignedLinearPosTest axisAlignedLinearPosTest) {
        return Float.valueOf(axisAlignedLinearPosTest.field_237046_b_);
    }
}

