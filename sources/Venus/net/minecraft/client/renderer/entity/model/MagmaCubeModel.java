/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.math.MathHelper;

public class MagmaCubeModel<T extends SlimeEntity>
extends SegmentedModel<T> {
    private final ModelRenderer[] segments = new ModelRenderer[8];
    private final ModelRenderer core;
    private final ImmutableList<ModelRenderer> field_228271_f_;

    public MagmaCubeModel() {
        for (int i = 0; i < this.segments.length; ++i) {
            int n = 0;
            int n2 = i;
            if (i == 2) {
                n = 24;
                n2 = 10;
            } else if (i == 3) {
                n = 24;
                n2 = 19;
            }
            this.segments[i] = new ModelRenderer(this, n, n2);
            this.segments[i].addBox(-4.0f, 16 + i, -4.0f, 8.0f, 1.0f, 8.0f);
        }
        this.core = new ModelRenderer(this, 0, 16);
        this.core.addBox(-2.0f, 18.0f, -2.0f, 4.0f, 4.0f, 4.0f);
        ImmutableList.Builder builder = ImmutableList.builder();
        builder.add(this.core);
        builder.addAll(Arrays.asList(this.segments));
        this.field_228271_f_ = builder.build();
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
    }

    @Override
    public void setLivingAnimations(T t, float f, float f2, float f3) {
        float f4 = MathHelper.lerp(f3, ((SlimeEntity)t).prevSquishFactor, ((SlimeEntity)t).squishFactor);
        if (f4 < 0.0f) {
            f4 = 0.0f;
        }
        for (int i = 0; i < this.segments.length; ++i) {
            this.segments[i].rotationPointY = (float)(-(4 - i)) * f4 * 1.7f;
        }
    }

    public ImmutableList<ModelRenderer> getParts() {
        return this.field_228271_f_;
    }

    @Override
    public Iterable getParts() {
        return this.getParts();
    }

    @Override
    public void setLivingAnimations(Entity entity2, float f, float f2, float f3) {
        this.setLivingAnimations((T)((SlimeEntity)entity2), f, f2, f3);
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((SlimeEntity)entity2), f, f2, f3, f4, f5);
    }
}

