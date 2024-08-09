/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class WitchModel<T extends Entity>
extends VillagerModel<T> {
    private boolean holdingItem;
    private final ModelRenderer mole = new ModelRenderer(this).setTextureSize(64, 128);

    public WitchModel(float f) {
        super(f, 64, 128);
        this.mole.setRotationPoint(0.0f, -2.0f, 0.0f);
        this.mole.setTextureOffset(0, 0).addBox(0.0f, 3.0f, -6.75f, 1.0f, 1.0f, 1.0f, -0.25f);
        this.villagerNose.addChild(this.mole);
        this.villagerHead = new ModelRenderer(this).setTextureSize(64, 128);
        this.villagerHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.villagerHead.setTextureOffset(0, 0).addBox(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f, f);
        this.hat = new ModelRenderer(this).setTextureSize(64, 128);
        this.hat.setRotationPoint(-5.0f, -10.03125f, -5.0f);
        this.hat.setTextureOffset(0, 64).addBox(0.0f, 0.0f, 0.0f, 10.0f, 2.0f, 10.0f);
        this.villagerHead.addChild(this.hat);
        this.villagerHead.addChild(this.villagerNose);
        ModelRenderer modelRenderer = new ModelRenderer(this).setTextureSize(64, 128);
        modelRenderer.setRotationPoint(1.75f, -4.0f, 2.0f);
        modelRenderer.setTextureOffset(0, 76).addBox(0.0f, 0.0f, 0.0f, 7.0f, 4.0f, 7.0f);
        modelRenderer.rotateAngleX = -0.05235988f;
        modelRenderer.rotateAngleZ = 0.02617994f;
        this.hat.addChild(modelRenderer);
        ModelRenderer modelRenderer2 = new ModelRenderer(this).setTextureSize(64, 128);
        modelRenderer2.setRotationPoint(1.75f, -4.0f, 2.0f);
        modelRenderer2.setTextureOffset(0, 87).addBox(0.0f, 0.0f, 0.0f, 4.0f, 4.0f, 4.0f);
        modelRenderer2.rotateAngleX = -0.10471976f;
        modelRenderer2.rotateAngleZ = 0.05235988f;
        modelRenderer.addChild(modelRenderer2);
        ModelRenderer modelRenderer3 = new ModelRenderer(this).setTextureSize(64, 128);
        modelRenderer3.setRotationPoint(1.75f, -2.0f, 2.0f);
        modelRenderer3.setTextureOffset(0, 95).addBox(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 1.0f, 0.25f);
        modelRenderer3.rotateAngleX = -0.20943952f;
        modelRenderer3.rotateAngleZ = 0.10471976f;
        modelRenderer2.addChild(modelRenderer3);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        super.setRotationAngles(t, f, f2, f3, f4, f5);
        this.villagerNose.setRotationPoint(0.0f, -2.0f, 0.0f);
        float f6 = 0.01f * (float)(((Entity)t).getEntityId() % 10);
        this.villagerNose.rotateAngleX = MathHelper.sin((float)((Entity)t).ticksExisted * f6) * 4.5f * ((float)Math.PI / 180);
        this.villagerNose.rotateAngleY = 0.0f;
        this.villagerNose.rotateAngleZ = MathHelper.cos((float)((Entity)t).ticksExisted * f6) * 2.5f * ((float)Math.PI / 180);
        if (this.holdingItem) {
            this.villagerNose.setRotationPoint(0.0f, 1.0f, -1.5f);
            this.villagerNose.rotateAngleX = -0.9f;
        }
    }

    public ModelRenderer func_205073_b() {
        return this.villagerNose;
    }

    public void func_205074_a(boolean bl) {
        this.holdingItem = bl;
    }
}

