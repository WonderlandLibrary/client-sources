/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.TintedAgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.math.MathHelper;

public class WolfModel<T extends WolfEntity>
extends TintedAgeableModel<T> {
    private final ModelRenderer head;
    private final ModelRenderer headChild;
    private final ModelRenderer body;
    private final ModelRenderer legBackRight;
    private final ModelRenderer legBackLeft;
    private final ModelRenderer legFrontRight;
    private final ModelRenderer legFrontLeft;
    private final ModelRenderer tail;
    private final ModelRenderer tailChild;
    private final ModelRenderer mane;

    public WolfModel() {
        float f = 0.0f;
        float f2 = 13.5f;
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(-1.0f, 13.5f, -7.0f);
        this.headChild = new ModelRenderer(this, 0, 0);
        this.headChild.addBox(-2.0f, -3.0f, -2.0f, 6.0f, 6.0f, 4.0f, 0.0f);
        this.head.addChild(this.headChild);
        this.body = new ModelRenderer(this, 18, 14);
        this.body.addBox(-3.0f, -2.0f, -3.0f, 6.0f, 9.0f, 6.0f, 0.0f);
        this.body.setRotationPoint(0.0f, 14.0f, 2.0f);
        this.mane = new ModelRenderer(this, 21, 0);
        this.mane.addBox(-3.0f, -3.0f, -3.0f, 8.0f, 6.0f, 7.0f, 0.0f);
        this.mane.setRotationPoint(-1.0f, 14.0f, 2.0f);
        this.legBackRight = new ModelRenderer(this, 0, 18);
        this.legBackRight.addBox(0.0f, 0.0f, -1.0f, 2.0f, 8.0f, 2.0f, 0.0f);
        this.legBackRight.setRotationPoint(-2.5f, 16.0f, 7.0f);
        this.legBackLeft = new ModelRenderer(this, 0, 18);
        this.legBackLeft.addBox(0.0f, 0.0f, -1.0f, 2.0f, 8.0f, 2.0f, 0.0f);
        this.legBackLeft.setRotationPoint(0.5f, 16.0f, 7.0f);
        this.legFrontRight = new ModelRenderer(this, 0, 18);
        this.legFrontRight.addBox(0.0f, 0.0f, -1.0f, 2.0f, 8.0f, 2.0f, 0.0f);
        this.legFrontRight.setRotationPoint(-2.5f, 16.0f, -4.0f);
        this.legFrontLeft = new ModelRenderer(this, 0, 18);
        this.legFrontLeft.addBox(0.0f, 0.0f, -1.0f, 2.0f, 8.0f, 2.0f, 0.0f);
        this.legFrontLeft.setRotationPoint(0.5f, 16.0f, -4.0f);
        this.tail = new ModelRenderer(this, 9, 18);
        this.tail.setRotationPoint(-1.0f, 12.0f, 8.0f);
        this.tailChild = new ModelRenderer(this, 9, 18);
        this.tailChild.addBox(0.0f, 0.0f, -1.0f, 2.0f, 8.0f, 2.0f, 0.0f);
        this.tail.addChild(this.tailChild);
        this.headChild.setTextureOffset(16, 14).addBox(-2.0f, -5.0f, 0.0f, 2.0f, 2.0f, 1.0f, 0.0f);
        this.headChild.setTextureOffset(16, 14).addBox(2.0f, -5.0f, 0.0f, 2.0f, 2.0f, 1.0f, 0.0f);
        this.headChild.setTextureOffset(0, 10).addBox(-0.5f, 0.0f, -5.0f, 3.0f, 3.0f, 4.0f, 0.0f);
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(this.body, this.legBackRight, this.legBackLeft, this.legFrontRight, this.legFrontLeft, this.tail, this.mane);
    }

    @Override
    public void setLivingAnimations(T t, float f, float f2, float f3) {
        this.tail.rotateAngleY = t.func_233678_J__() ? 0.0f : MathHelper.cos(f * 0.6662f) * 1.4f * f2;
        if (((TameableEntity)t).isSleeping()) {
            this.mane.setRotationPoint(-1.0f, 16.0f, -3.0f);
            this.mane.rotateAngleX = 1.2566371f;
            this.mane.rotateAngleY = 0.0f;
            this.body.setRotationPoint(0.0f, 18.0f, 0.0f);
            this.body.rotateAngleX = 0.7853982f;
            this.tail.setRotationPoint(-1.0f, 21.0f, 6.0f);
            this.legBackRight.setRotationPoint(-2.5f, 22.7f, 2.0f);
            this.legBackRight.rotateAngleX = 4.712389f;
            this.legBackLeft.setRotationPoint(0.5f, 22.7f, 2.0f);
            this.legBackLeft.rotateAngleX = 4.712389f;
            this.legFrontRight.rotateAngleX = 5.811947f;
            this.legFrontRight.setRotationPoint(-2.49f, 17.0f, -4.0f);
            this.legFrontLeft.rotateAngleX = 5.811947f;
            this.legFrontLeft.setRotationPoint(0.51f, 17.0f, -4.0f);
        } else {
            this.body.setRotationPoint(0.0f, 14.0f, 2.0f);
            this.body.rotateAngleX = 1.5707964f;
            this.mane.setRotationPoint(-1.0f, 14.0f, -3.0f);
            this.mane.rotateAngleX = this.body.rotateAngleX;
            this.tail.setRotationPoint(-1.0f, 12.0f, 8.0f);
            this.legBackRight.setRotationPoint(-2.5f, 16.0f, 7.0f);
            this.legBackLeft.setRotationPoint(0.5f, 16.0f, 7.0f);
            this.legFrontRight.setRotationPoint(-2.5f, 16.0f, -4.0f);
            this.legFrontLeft.setRotationPoint(0.5f, 16.0f, -4.0f);
            this.legBackRight.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f2;
            this.legBackLeft.rotateAngleX = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * f2;
            this.legFrontRight.rotateAngleX = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * f2;
            this.legFrontLeft.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f2;
        }
        this.headChild.rotateAngleZ = ((WolfEntity)t).getInterestedAngle(f3) + ((WolfEntity)t).getShakeAngle(f3, 0.0f);
        this.mane.rotateAngleZ = ((WolfEntity)t).getShakeAngle(f3, -0.08f);
        this.body.rotateAngleZ = ((WolfEntity)t).getShakeAngle(f3, -0.16f);
        this.tailChild.rotateAngleZ = ((WolfEntity)t).getShakeAngle(f3, -0.2f);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        this.head.rotateAngleX = f5 * ((float)Math.PI / 180);
        this.head.rotateAngleY = f4 * ((float)Math.PI / 180);
        this.tail.rotateAngleX = f3;
    }

    @Override
    public void setLivingAnimations(Entity entity2, float f, float f2, float f3) {
        this.setLivingAnimations((T)((WolfEntity)entity2), f, f2, f3);
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((WolfEntity)entity2), f, f2, f3, f4, f5);
    }
}

