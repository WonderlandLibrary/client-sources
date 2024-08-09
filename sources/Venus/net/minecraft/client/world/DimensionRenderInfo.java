/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.world;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DimensionType;

public abstract class DimensionRenderInfo {
    private static final Object2ObjectMap<ResourceLocation, DimensionRenderInfo> field_239208_a_ = Util.make(new Object2ObjectArrayMap(), DimensionRenderInfo::lambda$static$0);
    private final float[] field_239209_b_ = new float[4];
    private final float field_239210_c_;
    private final boolean field_239211_d_;
    private final FogType field_241680_e_;
    private final boolean field_241681_f_;
    private final boolean field_239212_e_;

    public DimensionRenderInfo(float f, boolean bl, FogType fogType, boolean bl2, boolean bl3) {
        this.field_239210_c_ = f;
        this.field_239211_d_ = bl;
        this.field_241680_e_ = fogType;
        this.field_241681_f_ = bl2;
        this.field_239212_e_ = bl3;
    }

    public static DimensionRenderInfo func_243495_a(DimensionType dimensionType) {
        return (DimensionRenderInfo)field_239208_a_.get(dimensionType.getEffects());
    }

    @Nullable
    public float[] func_230492_a_(float f, float f2) {
        float f3 = 0.4f;
        float f4 = MathHelper.cos(f * ((float)Math.PI * 2)) - 0.0f;
        float f5 = -0.0f;
        if (f4 >= -0.4f && f4 <= 0.4f) {
            float f6 = (f4 - -0.0f) / 0.4f * 0.5f + 0.5f;
            float f7 = 1.0f - (1.0f - MathHelper.sin(f6 * (float)Math.PI)) * 0.99f;
            f7 *= f7;
            this.field_239209_b_[0] = f6 * 0.3f + 0.7f;
            this.field_239209_b_[1] = f6 * f6 * 0.7f + 0.2f;
            this.field_239209_b_[2] = f6 * f6 * 0.0f + 0.2f;
            this.field_239209_b_[3] = f7;
            return this.field_239209_b_;
        }
        return null;
    }

    public float func_239213_a_() {
        return this.field_239210_c_;
    }

    public boolean func_239216_b_() {
        return this.field_239211_d_;
    }

    public abstract Vector3d func_230494_a_(Vector3d var1, float var2);

    public abstract boolean func_230493_a_(int var1, int var2);

    public FogType func_241683_c_() {
        return this.field_241680_e_;
    }

    public boolean func_241684_d_() {
        return this.field_241681_f_;
    }

    public boolean func_239217_c_() {
        return this.field_239212_e_;
    }

    private static void lambda$static$0(Object2ObjectArrayMap object2ObjectArrayMap) {
        Overworld overworld = new Overworld();
        object2ObjectArrayMap.defaultReturnValue(overworld);
        object2ObjectArrayMap.put(DimensionType.OVERWORLD_ID, overworld);
        object2ObjectArrayMap.put(DimensionType.THE_NETHER_ID, new Nether());
        object2ObjectArrayMap.put(DimensionType.THE_END_ID, new End());
    }

    public static enum FogType {
        NONE,
        NORMAL,
        END;

    }

    public static class Overworld
    extends DimensionRenderInfo {
        public Overworld() {
            super(128.0f, true, FogType.NORMAL, false, false);
        }

        @Override
        public Vector3d func_230494_a_(Vector3d vector3d, float f) {
            return vector3d.mul(f * 0.94f + 0.06f, f * 0.94f + 0.06f, f * 0.91f + 0.09f);
        }

        @Override
        public boolean func_230493_a_(int n, int n2) {
            return true;
        }
    }

    public static class Nether
    extends DimensionRenderInfo {
        public Nether() {
            super(Float.NaN, true, FogType.NONE, false, true);
        }

        @Override
        public Vector3d func_230494_a_(Vector3d vector3d, float f) {
            return vector3d;
        }

        @Override
        public boolean func_230493_a_(int n, int n2) {
            return false;
        }
    }

    public static class End
    extends DimensionRenderInfo {
        public End() {
            super(Float.NaN, false, FogType.END, true, false);
        }

        @Override
        public Vector3d func_230494_a_(Vector3d vector3d, float f) {
            return vector3d.scale(0.15f);
        }

        @Override
        public boolean func_230493_a_(int n, int n2) {
            return true;
        }

        @Override
        @Nullable
        public float[] func_230492_a_(float f, float f2) {
            return null;
        }
    }
}

