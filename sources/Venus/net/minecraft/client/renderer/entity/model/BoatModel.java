/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.util.math.MathHelper;

public class BoatModel
extends SegmentedModel<BoatEntity> {
    private final ModelRenderer[] paddles = new ModelRenderer[2];
    private final ModelRenderer noWater;
    private final ImmutableList<ModelRenderer> field_228243_f_;

    public BoatModel() {
        ModelRenderer[] modelRendererArray = new ModelRenderer[]{new ModelRenderer(this, 0, 0).setTextureSize(128, 64), new ModelRenderer(this, 0, 19).setTextureSize(128, 64), new ModelRenderer(this, 0, 27).setTextureSize(128, 64), new ModelRenderer(this, 0, 35).setTextureSize(128, 64), new ModelRenderer(this, 0, 43).setTextureSize(128, 64)};
        int n = 32;
        int n2 = 6;
        int n3 = 20;
        int n4 = 4;
        int n5 = 28;
        modelRendererArray[0].addBox(-14.0f, -9.0f, -3.0f, 28.0f, 16.0f, 3.0f, 0.0f);
        modelRendererArray[0].setRotationPoint(0.0f, 3.0f, 1.0f);
        modelRendererArray[5].addBox(-13.0f, -7.0f, -1.0f, 18.0f, 6.0f, 2.0f, 0.0f);
        modelRendererArray[5].setRotationPoint(-15.0f, 4.0f, 4.0f);
        modelRendererArray[5].addBox(-8.0f, -7.0f, -1.0f, 16.0f, 6.0f, 2.0f, 0.0f);
        modelRendererArray[5].setRotationPoint(15.0f, 4.0f, 0.0f);
        modelRendererArray[5].addBox(-14.0f, -7.0f, -1.0f, 28.0f, 6.0f, 2.0f, 0.0f);
        modelRendererArray[5].setRotationPoint(0.0f, 4.0f, -9.0f);
        modelRendererArray[5].addBox(-14.0f, -7.0f, -1.0f, 28.0f, 6.0f, 2.0f, 0.0f);
        modelRendererArray[5].setRotationPoint(0.0f, 4.0f, 9.0f);
        modelRendererArray[0].rotateAngleX = 1.5707964f;
        modelRendererArray[5].rotateAngleY = 4.712389f;
        modelRendererArray[5].rotateAngleY = 1.5707964f;
        modelRendererArray[5].rotateAngleY = (float)Math.PI;
        this.paddles[0] = this.makePaddle(false);
        this.paddles[0].setRotationPoint(3.0f, -5.0f, 9.0f);
        this.paddles[1] = this.makePaddle(true);
        this.paddles[1].setRotationPoint(3.0f, -5.0f, -9.0f);
        this.paddles[1].rotateAngleY = (float)Math.PI;
        this.paddles[0].rotateAngleZ = 0.19634955f;
        this.paddles[1].rotateAngleZ = 0.19634955f;
        this.noWater = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
        this.noWater.addBox(-14.0f, -9.0f, -3.0f, 28.0f, 16.0f, 3.0f, 0.0f);
        this.noWater.setRotationPoint(0.0f, -3.0f, 1.0f);
        this.noWater.rotateAngleX = 1.5707964f;
        ImmutableList.Builder builder = ImmutableList.builder();
        builder.addAll(Arrays.asList(modelRendererArray));
        builder.addAll(Arrays.asList(this.paddles));
        this.field_228243_f_ = builder.build();
    }

    @Override
    public void setRotationAngles(BoatEntity boatEntity, float f, float f2, float f3, float f4, float f5) {
        this.func_228244_a_(boatEntity, 0, f);
        this.func_228244_a_(boatEntity, 1, f);
    }

    public ImmutableList<ModelRenderer> getParts() {
        return this.field_228243_f_;
    }

    public ModelRenderer func_228245_c_() {
        return this.noWater;
    }

    protected ModelRenderer makePaddle(boolean bl) {
        ModelRenderer modelRenderer = new ModelRenderer(this, 62, bl ? 0 : 20).setTextureSize(128, 64);
        int n = 20;
        int n2 = 7;
        int n3 = 6;
        float f = -5.0f;
        modelRenderer.addBox(-1.0f, 0.0f, -5.0f, 2.0f, 2.0f, 18.0f);
        modelRenderer.addBox(bl ? -1.001f : 0.001f, -3.0f, 8.0f, 1.0f, 6.0f, 7.0f);
        return modelRenderer;
    }

    protected void func_228244_a_(BoatEntity boatEntity, int n, float f) {
        float f2 = boatEntity.getRowingTime(n, f);
        ModelRenderer modelRenderer = this.paddles[n];
        modelRenderer.rotateAngleX = (float)MathHelper.clampedLerp(-1.0471975803375244, -0.2617993950843811, (MathHelper.sin(-f2) + 1.0f) / 2.0f);
        modelRenderer.rotateAngleY = (float)MathHelper.clampedLerp(-0.7853981852531433, 0.7853981852531433, (MathHelper.sin(-f2 + 1.0f) + 1.0f) / 2.0f);
        if (n == 1) {
            modelRenderer.rotateAngleY = (float)Math.PI - modelRenderer.rotateAngleY;
        }
    }

    @Override
    public Iterable getParts() {
        return this.getParts();
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((BoatEntity)entity2, f, f2, f3, f4, f5);
    }
}

