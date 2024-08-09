/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class BlazeModel<T extends Entity>
extends SegmentedModel<T> {
    private final ModelRenderer[] blazeSticks;
    private final ModelRenderer blazeHead = new ModelRenderer(this, 0, 0);
    private final ImmutableList<ModelRenderer> field_228242_f_;

    public BlazeModel() {
        this.blazeHead.addBox(-4.0f, -4.0f, -4.0f, 8.0f, 8.0f, 8.0f);
        this.blazeSticks = new ModelRenderer[12];
        for (int i = 0; i < this.blazeSticks.length; ++i) {
            this.blazeSticks[i] = new ModelRenderer(this, 0, 16);
            this.blazeSticks[i].addBox(0.0f, 0.0f, 0.0f, 2.0f, 8.0f, 2.0f);
        }
        ImmutableList.Builder builder = ImmutableList.builder();
        builder.add(this.blazeHead);
        builder.addAll(Arrays.asList(this.blazeSticks));
        this.field_228242_f_ = builder.build();
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return this.field_228242_f_;
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        int n;
        float f6 = f3 * (float)Math.PI * -0.1f;
        for (n = 0; n < 4; ++n) {
            this.blazeSticks[n].rotationPointY = -2.0f + MathHelper.cos(((float)(n * 2) + f3) * 0.25f);
            this.blazeSticks[n].rotationPointX = MathHelper.cos(f6) * 9.0f;
            this.blazeSticks[n].rotationPointZ = MathHelper.sin(f6) * 9.0f;
            f6 += 1.0f;
        }
        f6 = 0.7853982f + f3 * (float)Math.PI * 0.03f;
        for (n = 4; n < 8; ++n) {
            this.blazeSticks[n].rotationPointY = 2.0f + MathHelper.cos(((float)(n * 2) + f3) * 0.25f);
            this.blazeSticks[n].rotationPointX = MathHelper.cos(f6) * 7.0f;
            this.blazeSticks[n].rotationPointZ = MathHelper.sin(f6) * 7.0f;
            f6 += 1.0f;
        }
        f6 = 0.47123894f + f3 * (float)Math.PI * -0.05f;
        for (n = 8; n < 12; ++n) {
            this.blazeSticks[n].rotationPointY = 11.0f + MathHelper.cos(((float)n * 1.5f + f3) * 0.5f);
            this.blazeSticks[n].rotationPointX = MathHelper.cos(f6) * 5.0f;
            this.blazeSticks[n].rotationPointZ = MathHelper.sin(f6) * 5.0f;
            f6 += 1.0f;
        }
        this.blazeHead.rotateAngleY = f4 * ((float)Math.PI / 180);
        this.blazeHead.rotateAngleX = f5 * ((float)Math.PI / 180);
    }
}

