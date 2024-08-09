/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AbstractTropicalFishModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class TropicalFishAModel<T extends Entity>
extends AbstractTropicalFishModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer tail;
    private final ModelRenderer finRight;
    private final ModelRenderer finLeft;
    private final ModelRenderer finTop;

    public TropicalFishAModel(float f) {
        this.textureWidth = 32;
        this.textureHeight = 32;
        int n = 22;
        this.body = new ModelRenderer(this, 0, 0);
        this.body.addBox(-1.0f, -1.5f, -3.0f, 2.0f, 3.0f, 6.0f, f);
        this.body.setRotationPoint(0.0f, 22.0f, 0.0f);
        this.tail = new ModelRenderer(this, 22, -6);
        this.tail.addBox(0.0f, -1.5f, 0.0f, 0.0f, 3.0f, 6.0f, f);
        this.tail.setRotationPoint(0.0f, 22.0f, 3.0f);
        this.finRight = new ModelRenderer(this, 2, 16);
        this.finRight.addBox(-2.0f, -1.0f, 0.0f, 2.0f, 2.0f, 0.0f, f);
        this.finRight.setRotationPoint(-1.0f, 22.5f, 0.0f);
        this.finRight.rotateAngleY = 0.7853982f;
        this.finLeft = new ModelRenderer(this, 2, 12);
        this.finLeft.addBox(0.0f, -1.0f, 0.0f, 2.0f, 2.0f, 0.0f, f);
        this.finLeft.setRotationPoint(1.0f, 22.5f, 0.0f);
        this.finLeft.rotateAngleY = -0.7853982f;
        this.finTop = new ModelRenderer(this, 10, -5);
        this.finTop.addBox(0.0f, -3.0f, 0.0f, 0.0f, 3.0f, 6.0f, f);
        this.finTop.setRotationPoint(0.0f, 20.5f, -3.0f);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.body, this.tail, this.finRight, this.finLeft, this.finTop);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        float f6 = 1.0f;
        if (!((Entity)t).isInWater()) {
            f6 = 1.5f;
        }
        this.tail.rotateAngleY = -f6 * 0.45f * MathHelper.sin(0.6f * f3);
    }
}

