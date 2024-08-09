/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class DolphinModel<T extends Entity>
extends SegmentedModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer tail;
    private final ModelRenderer tailFin;

    public DolphinModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        float f = 18.0f;
        float f2 = -8.0f;
        this.body = new ModelRenderer(this, 22, 0);
        this.body.addBox(-4.0f, -7.0f, 0.0f, 8.0f, 7.0f, 13.0f);
        this.body.setRotationPoint(0.0f, 22.0f, -5.0f);
        ModelRenderer modelRenderer = new ModelRenderer(this, 51, 0);
        modelRenderer.addBox(-0.5f, 0.0f, 8.0f, 1.0f, 4.0f, 5.0f);
        modelRenderer.rotateAngleX = 1.0471976f;
        this.body.addChild(modelRenderer);
        ModelRenderer modelRenderer2 = new ModelRenderer(this, 48, 20);
        modelRenderer2.mirror = true;
        modelRenderer2.addBox(-0.5f, -4.0f, 0.0f, 1.0f, 4.0f, 7.0f);
        modelRenderer2.setRotationPoint(2.0f, -2.0f, 4.0f);
        modelRenderer2.rotateAngleX = 1.0471976f;
        modelRenderer2.rotateAngleZ = 2.0943952f;
        this.body.addChild(modelRenderer2);
        ModelRenderer modelRenderer3 = new ModelRenderer(this, 48, 20);
        modelRenderer3.addBox(-0.5f, -4.0f, 0.0f, 1.0f, 4.0f, 7.0f);
        modelRenderer3.setRotationPoint(-2.0f, -2.0f, 4.0f);
        modelRenderer3.rotateAngleX = 1.0471976f;
        modelRenderer3.rotateAngleZ = -2.0943952f;
        this.body.addChild(modelRenderer3);
        this.tail = new ModelRenderer(this, 0, 19);
        this.tail.addBox(-2.0f, -2.5f, 0.0f, 4.0f, 5.0f, 11.0f);
        this.tail.setRotationPoint(0.0f, -2.5f, 11.0f);
        this.tail.rotateAngleX = -0.10471976f;
        this.body.addChild(this.tail);
        this.tailFin = new ModelRenderer(this, 19, 20);
        this.tailFin.addBox(-5.0f, -0.5f, 0.0f, 10.0f, 1.0f, 6.0f);
        this.tailFin.setRotationPoint(0.0f, 0.0f, 9.0f);
        this.tailFin.rotateAngleX = 0.0f;
        this.tail.addChild(this.tailFin);
        ModelRenderer modelRenderer4 = new ModelRenderer(this, 0, 0);
        modelRenderer4.addBox(-4.0f, -3.0f, -3.0f, 8.0f, 7.0f, 6.0f);
        modelRenderer4.setRotationPoint(0.0f, -4.0f, -3.0f);
        ModelRenderer modelRenderer5 = new ModelRenderer(this, 0, 13);
        modelRenderer5.addBox(-1.0f, 2.0f, -7.0f, 2.0f, 2.0f, 4.0f);
        modelRenderer4.addChild(modelRenderer5);
        this.body.addChild(modelRenderer4);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        this.body.rotateAngleX = f5 * ((float)Math.PI / 180);
        this.body.rotateAngleY = f4 * ((float)Math.PI / 180);
        if (Entity.horizontalMag(((Entity)t).getMotion()) > 1.0E-7) {
            this.body.rotateAngleX += -0.05f + -0.05f * MathHelper.cos(f3 * 0.3f);
            this.tail.rotateAngleX = -0.1f * MathHelper.cos(f3 * 0.3f);
            this.tailFin.rotateAngleX = -0.2f * MathHelper.cos(f3 * 0.3f);
        }
    }
}

