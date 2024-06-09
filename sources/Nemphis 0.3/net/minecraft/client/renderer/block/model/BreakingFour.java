/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.client.renderer.block.model;

import java.util.Arrays;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class BreakingFour
extends BakedQuad {
    private final TextureAtlasSprite texture;
    private static final String __OBFID = "CL_00002492";

    public BreakingFour(BakedQuad p_i46217_1_, TextureAtlasSprite p_i46217_2_) {
        super(Arrays.copyOf(p_i46217_1_.func_178209_a(), p_i46217_1_.func_178209_a().length), p_i46217_1_.field_178213_b, FaceBakery.func_178410_a(p_i46217_1_.func_178209_a()));
        this.texture = p_i46217_2_;
        this.func_178217_e();
    }

    private void func_178217_e() {
        int var1 = 0;
        while (var1 < 4) {
            this.func_178216_a(var1);
            ++var1;
        }
    }

    private void func_178216_a(int p_178216_1_) {
        int step = this.field_178215_a.length / 4;
        int var2 = step * p_178216_1_;
        float var3 = Float.intBitsToFloat(this.field_178215_a[var2]);
        float var4 = Float.intBitsToFloat(this.field_178215_a[var2 + 1]);
        float var5 = Float.intBitsToFloat(this.field_178215_a[var2 + 2]);
        float var6 = 0.0f;
        float var7 = 0.0f;
        switch (SwitchEnumFacing.field_178419_a[this.face.ordinal()]) {
            case 1: {
                var6 = var3 * 16.0f;
                var7 = (1.0f - var5) * 16.0f;
                break;
            }
            case 2: {
                var6 = var3 * 16.0f;
                var7 = var5 * 16.0f;
                break;
            }
            case 3: {
                var6 = (1.0f - var3) * 16.0f;
                var7 = (1.0f - var4) * 16.0f;
                break;
            }
            case 4: {
                var6 = var3 * 16.0f;
                var7 = (1.0f - var4) * 16.0f;
                break;
            }
            case 5: {
                var6 = var5 * 16.0f;
                var7 = (1.0f - var4) * 16.0f;
                break;
            }
            case 6: {
                var6 = (1.0f - var5) * 16.0f;
                var7 = (1.0f - var4) * 16.0f;
            }
        }
        this.field_178215_a[var2 + 4] = Float.floatToRawIntBits(this.texture.getInterpolatedU(var6));
        this.field_178215_a[var2 + 4 + 1] = Float.floatToRawIntBits(this.texture.getInterpolatedV(var7));
    }

    static final class SwitchEnumFacing {
        static final int[] field_178419_a = new int[EnumFacing.values().length];
        private static final String __OBFID = "CL_00002491";

        static {
            try {
                SwitchEnumFacing.field_178419_a[EnumFacing.DOWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError var0) {
                // empty catch block
            }
            try {
                SwitchEnumFacing.field_178419_a[EnumFacing.UP.ordinal()] = 2;
            }
            catch (NoSuchFieldError var0_1) {
                // empty catch block
            }
            try {
                SwitchEnumFacing.field_178419_a[EnumFacing.NORTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError var0_2) {
                // empty catch block
            }
            try {
                SwitchEnumFacing.field_178419_a[EnumFacing.SOUTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError var0_3) {
                // empty catch block
            }
            try {
                SwitchEnumFacing.field_178419_a[EnumFacing.WEST.ordinal()] = 5;
            }
            catch (NoSuchFieldError var0_4) {
                // empty catch block
            }
            try {
                SwitchEnumFacing.field_178419_a[EnumFacing.EAST.ordinal()] = 6;
            }
            catch (NoSuchFieldError var0_5) {
                // empty catch block
            }
        }

        SwitchEnumFacing() {
        }
    }

}

