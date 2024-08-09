/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class OcelotModel<T extends Entity>
extends AgeableModel<T> {
    protected final ModelRenderer ocelotBackLeftLeg;
    protected final ModelRenderer ocelotBackRightLeg;
    protected final ModelRenderer ocelotFrontLeftLeg;
    protected final ModelRenderer ocelotFrontRightLeg;
    protected final ModelRenderer ocelotTail;
    protected final ModelRenderer ocelotTail2;
    protected final ModelRenderer ocelotHead = new ModelRenderer(this);
    protected final ModelRenderer ocelotBody;
    protected int state = 1;

    public OcelotModel(float f) {
        super(true, 10.0f, 4.0f);
        this.ocelotHead.addBox("main", -2.5f, -2.0f, -3.0f, 5, 4, 5, f, 0, 0);
        this.ocelotHead.addBox("nose", -1.5f, 0.0f, -4.0f, 3, 2, 2, f, 0, 24);
        this.ocelotHead.addBox("ear1", -2.0f, -3.0f, 0.0f, 1, 1, 2, f, 0, 10);
        this.ocelotHead.addBox("ear2", 1.0f, -3.0f, 0.0f, 1, 1, 2, f, 6, 10);
        this.ocelotHead.setRotationPoint(0.0f, 15.0f, -9.0f);
        this.ocelotBody = new ModelRenderer(this, 20, 0);
        this.ocelotBody.addBox(-2.0f, 3.0f, -8.0f, 4.0f, 16.0f, 6.0f, f);
        this.ocelotBody.setRotationPoint(0.0f, 12.0f, -10.0f);
        this.ocelotTail = new ModelRenderer(this, 0, 15);
        this.ocelotTail.addBox(-0.5f, 0.0f, 0.0f, 1.0f, 8.0f, 1.0f, f);
        this.ocelotTail.rotateAngleX = 0.9f;
        this.ocelotTail.setRotationPoint(0.0f, 15.0f, 8.0f);
        this.ocelotTail2 = new ModelRenderer(this, 4, 15);
        this.ocelotTail2.addBox(-0.5f, 0.0f, 0.0f, 1.0f, 8.0f, 1.0f, f);
        this.ocelotTail2.setRotationPoint(0.0f, 20.0f, 14.0f);
        this.ocelotBackLeftLeg = new ModelRenderer(this, 8, 13);
        this.ocelotBackLeftLeg.addBox(-1.0f, 0.0f, 1.0f, 2.0f, 6.0f, 2.0f, f);
        this.ocelotBackLeftLeg.setRotationPoint(1.1f, 18.0f, 5.0f);
        this.ocelotBackRightLeg = new ModelRenderer(this, 8, 13);
        this.ocelotBackRightLeg.addBox(-1.0f, 0.0f, 1.0f, 2.0f, 6.0f, 2.0f, f);
        this.ocelotBackRightLeg.setRotationPoint(-1.1f, 18.0f, 5.0f);
        this.ocelotFrontLeftLeg = new ModelRenderer(this, 40, 0);
        this.ocelotFrontLeftLeg.addBox(-1.0f, 0.0f, 0.0f, 2.0f, 10.0f, 2.0f, f);
        this.ocelotFrontLeftLeg.setRotationPoint(1.2f, 14.1f, -5.0f);
        this.ocelotFrontRightLeg = new ModelRenderer(this, 40, 0);
        this.ocelotFrontRightLeg.addBox(-1.0f, 0.0f, 0.0f, 2.0f, 10.0f, 2.0f, f);
        this.ocelotFrontRightLeg.setRotationPoint(-1.2f, 14.1f, -5.0f);
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of(this.ocelotHead);
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(this.ocelotBody, this.ocelotBackLeftLeg, this.ocelotBackRightLeg, this.ocelotFrontLeftLeg, this.ocelotFrontRightLeg, this.ocelotTail, this.ocelotTail2);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        this.ocelotHead.rotateAngleX = f5 * ((float)Math.PI / 180);
        this.ocelotHead.rotateAngleY = f4 * ((float)Math.PI / 180);
        if (this.state != 3) {
            this.ocelotBody.rotateAngleX = 1.5707964f;
            if (this.state == 2) {
                this.ocelotBackLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662f) * f2;
                this.ocelotBackRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662f + 0.3f) * f2;
                this.ocelotFrontLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662f + (float)Math.PI + 0.3f) * f2;
                this.ocelotFrontRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662f + (float)Math.PI) * f2;
                this.ocelotTail2.rotateAngleX = 1.7278761f + 0.31415927f * MathHelper.cos(f) * f2;
            } else {
                this.ocelotBackLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662f) * f2;
                this.ocelotBackRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662f + (float)Math.PI) * f2;
                this.ocelotFrontLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662f + (float)Math.PI) * f2;
                this.ocelotFrontRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662f) * f2;
                this.ocelotTail2.rotateAngleX = this.state == 1 ? 1.7278761f + 0.7853982f * MathHelper.cos(f) * f2 : 1.7278761f + 0.47123894f * MathHelper.cos(f) * f2;
            }
        }
    }

    @Override
    public void setLivingAnimations(T t, float f, float f2, float f3) {
        this.ocelotBody.rotationPointY = 12.0f;
        this.ocelotBody.rotationPointZ = -10.0f;
        this.ocelotHead.rotationPointY = 15.0f;
        this.ocelotHead.rotationPointZ = -9.0f;
        this.ocelotTail.rotationPointY = 15.0f;
        this.ocelotTail.rotationPointZ = 8.0f;
        this.ocelotTail2.rotationPointY = 20.0f;
        this.ocelotTail2.rotationPointZ = 14.0f;
        this.ocelotFrontLeftLeg.rotationPointY = 14.1f;
        this.ocelotFrontLeftLeg.rotationPointZ = -5.0f;
        this.ocelotFrontRightLeg.rotationPointY = 14.1f;
        this.ocelotFrontRightLeg.rotationPointZ = -5.0f;
        this.ocelotBackLeftLeg.rotationPointY = 18.0f;
        this.ocelotBackLeftLeg.rotationPointZ = 5.0f;
        this.ocelotBackRightLeg.rotationPointY = 18.0f;
        this.ocelotBackRightLeg.rotationPointZ = 5.0f;
        this.ocelotTail.rotateAngleX = 0.9f;
        if (((Entity)t).isCrouching()) {
            this.ocelotBody.rotationPointY += 1.0f;
            this.ocelotHead.rotationPointY += 2.0f;
            this.ocelotTail.rotationPointY += 1.0f;
            this.ocelotTail2.rotationPointY += -4.0f;
            this.ocelotTail2.rotationPointZ += 2.0f;
            this.ocelotTail.rotateAngleX = 1.5707964f;
            this.ocelotTail2.rotateAngleX = 1.5707964f;
            this.state = 0;
        } else if (((Entity)t).isSprinting()) {
            this.ocelotTail2.rotationPointY = this.ocelotTail.rotationPointY;
            this.ocelotTail2.rotationPointZ += 2.0f;
            this.ocelotTail.rotateAngleX = 1.5707964f;
            this.ocelotTail2.rotateAngleX = 1.5707964f;
            this.state = 2;
        } else {
            this.state = 1;
        }
    }
}

