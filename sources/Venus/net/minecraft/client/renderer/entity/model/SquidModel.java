/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class SquidModel<T extends Entity>
extends SegmentedModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer[] legs = new ModelRenderer[8];
    private final ImmutableList<ModelRenderer> field_228296_f_;

    public SquidModel() {
        int n = -16;
        this.body = new ModelRenderer(this, 0, 0);
        this.body.addBox(-6.0f, -8.0f, -6.0f, 12.0f, 16.0f, 12.0f);
        this.body.rotationPointY += 8.0f;
        for (int i = 0; i < this.legs.length; ++i) {
            this.legs[i] = new ModelRenderer(this, 48, 0);
            double d = (double)i * Math.PI * 2.0 / (double)this.legs.length;
            float f = (float)Math.cos(d) * 5.0f;
            float f2 = (float)Math.sin(d) * 5.0f;
            this.legs[i].addBox(-1.0f, 0.0f, -1.0f, 2.0f, 18.0f, 2.0f);
            this.legs[i].rotationPointX = f;
            this.legs[i].rotationPointZ = f2;
            this.legs[i].rotationPointY = 15.0f;
            d = (double)i * Math.PI * -2.0 / (double)this.legs.length + 1.5707963267948966;
            this.legs[i].rotateAngleY = (float)d;
        }
        ImmutableList.Builder builder = ImmutableList.builder();
        builder.add(this.body);
        builder.addAll(Arrays.asList(this.legs));
        this.field_228296_f_ = builder.build();
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        for (ModelRenderer modelRenderer : this.legs) {
            modelRenderer.rotateAngleX = f3;
        }
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return this.field_228296_f_;
    }
}

