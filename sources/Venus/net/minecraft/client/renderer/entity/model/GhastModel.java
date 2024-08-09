/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import java.util.Random;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class GhastModel<T extends Entity>
extends SegmentedModel<T> {
    private final ModelRenderer[] tentacles = new ModelRenderer[9];
    private final ImmutableList<ModelRenderer> field_228260_b_;

    public GhastModel() {
        ImmutableList.Builder builder = ImmutableList.builder();
        ModelRenderer modelRenderer = new ModelRenderer(this, 0, 0);
        modelRenderer.addBox(-8.0f, -8.0f, -8.0f, 16.0f, 16.0f, 16.0f);
        modelRenderer.rotationPointY = 17.6f;
        builder.add(modelRenderer);
        Random random2 = new Random(1660L);
        for (int i = 0; i < this.tentacles.length; ++i) {
            this.tentacles[i] = new ModelRenderer(this, 0, 0);
            float f = (((float)(i % 3) - (float)(i / 3 % 2) * 0.5f + 0.25f) / 2.0f * 2.0f - 1.0f) * 5.0f;
            float f2 = ((float)(i / 3) / 2.0f * 2.0f - 1.0f) * 5.0f;
            int n = random2.nextInt(7) + 8;
            this.tentacles[i].addBox(-1.0f, 0.0f, -1.0f, 2.0f, n, 2.0f);
            this.tentacles[i].rotationPointX = f;
            this.tentacles[i].rotationPointZ = f2;
            this.tentacles[i].rotationPointY = 24.6f;
            builder.add(this.tentacles[i]);
        }
        this.field_228260_b_ = builder.build();
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        for (int i = 0; i < this.tentacles.length; ++i) {
            this.tentacles[i].rotateAngleX = 0.2f * MathHelper.sin(f3 * 0.3f + (float)i) + 0.4f;
        }
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return this.field_228260_b_;
    }
}

