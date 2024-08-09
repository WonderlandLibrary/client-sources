/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.IHeadToggle;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.util.math.MathHelper;

public class VillagerModel<T extends Entity>
extends SegmentedModel<T>
implements IHasHead,
IHeadToggle {
    protected ModelRenderer villagerHead;
    protected ModelRenderer hat;
    protected final ModelRenderer hatBrim;
    protected final ModelRenderer villagerBody;
    protected final ModelRenderer clothing;
    protected final ModelRenderer villagerArms;
    protected final ModelRenderer rightVillagerLeg;
    protected final ModelRenderer leftVillagerLeg;
    protected final ModelRenderer villagerNose;

    public VillagerModel(float f) {
        this(f, 64, 64);
    }

    public VillagerModel(float f, int n, int n2) {
        float f2 = 0.5f;
        this.villagerHead = new ModelRenderer(this).setTextureSize(n, n2);
        this.villagerHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.villagerHead.setTextureOffset(0, 0).addBox(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f, f);
        this.hat = new ModelRenderer(this).setTextureSize(n, n2);
        this.hat.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.hat.setTextureOffset(32, 0).addBox(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f, f + 0.5f);
        this.villagerHead.addChild(this.hat);
        this.hatBrim = new ModelRenderer(this).setTextureSize(n, n2);
        this.hatBrim.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.hatBrim.setTextureOffset(30, 47).addBox(-8.0f, -8.0f, -6.0f, 16.0f, 16.0f, 1.0f, f);
        this.hatBrim.rotateAngleX = -1.5707964f;
        this.hat.addChild(this.hatBrim);
        this.villagerNose = new ModelRenderer(this).setTextureSize(n, n2);
        this.villagerNose.setRotationPoint(0.0f, -2.0f, 0.0f);
        this.villagerNose.setTextureOffset(24, 0).addBox(-1.0f, -1.0f, -6.0f, 2.0f, 4.0f, 2.0f, f);
        this.villagerHead.addChild(this.villagerNose);
        this.villagerBody = new ModelRenderer(this).setTextureSize(n, n2);
        this.villagerBody.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.villagerBody.setTextureOffset(16, 20).addBox(-4.0f, 0.0f, -3.0f, 8.0f, 12.0f, 6.0f, f);
        this.clothing = new ModelRenderer(this).setTextureSize(n, n2);
        this.clothing.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.clothing.setTextureOffset(0, 38).addBox(-4.0f, 0.0f, -3.0f, 8.0f, 18.0f, 6.0f, f + 0.5f);
        this.villagerBody.addChild(this.clothing);
        this.villagerArms = new ModelRenderer(this).setTextureSize(n, n2);
        this.villagerArms.setRotationPoint(0.0f, 2.0f, 0.0f);
        this.villagerArms.setTextureOffset(44, 22).addBox(-8.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, f);
        this.villagerArms.setTextureOffset(44, 22).addBox(4.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, f, false);
        this.villagerArms.setTextureOffset(40, 38).addBox(-4.0f, 2.0f, -2.0f, 8.0f, 4.0f, 4.0f, f);
        this.rightVillagerLeg = new ModelRenderer(this, 0, 22).setTextureSize(n, n2);
        this.rightVillagerLeg.setRotationPoint(-2.0f, 12.0f, 0.0f);
        this.rightVillagerLeg.addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, f);
        this.leftVillagerLeg = new ModelRenderer(this, 0, 22).setTextureSize(n, n2);
        this.leftVillagerLeg.mirror = true;
        this.leftVillagerLeg.setRotationPoint(2.0f, 12.0f, 0.0f);
        this.leftVillagerLeg.addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, f);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.villagerHead, this.villagerBody, this.rightVillagerLeg, this.leftVillagerLeg, this.villagerArms);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        boolean bl = false;
        if (t instanceof AbstractVillagerEntity) {
            bl = ((AbstractVillagerEntity)t).getShakeHeadTicks() > 0;
        }
        this.villagerHead.rotateAngleY = f4 * ((float)Math.PI / 180);
        this.villagerHead.rotateAngleX = f5 * ((float)Math.PI / 180);
        if (bl) {
            this.villagerHead.rotateAngleZ = 0.3f * MathHelper.sin(0.45f * f3);
            this.villagerHead.rotateAngleX = 0.4f;
        } else {
            this.villagerHead.rotateAngleZ = 0.0f;
        }
        this.villagerArms.rotationPointY = 3.0f;
        this.villagerArms.rotationPointZ = -1.0f;
        this.villagerArms.rotateAngleX = -0.75f;
        this.rightVillagerLeg.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f2 * 0.5f;
        this.leftVillagerLeg.rotateAngleX = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * f2 * 0.5f;
        this.rightVillagerLeg.rotateAngleY = 0.0f;
        this.leftVillagerLeg.rotateAngleY = 0.0f;
    }

    @Override
    public ModelRenderer getModelHead() {
        return this.villagerHead;
    }

    @Override
    public void func_217146_a(boolean bl) {
        this.villagerHead.showModel = bl;
        this.hat.showModel = bl;
        this.hatBrim.showModel = bl;
    }
}

