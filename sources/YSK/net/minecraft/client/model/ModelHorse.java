package net.minecraft.client.model;

import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;

public class ModelHorse extends ModelBase
{
    private ModelRenderer horseLeftFaceMetal;
    private ModelRenderer muleLeftEar;
    private ModelRenderer muleRightEar;
    private ModelRenderer frontLeftHoof;
    private ModelRenderer field_178711_b;
    private ModelRenderer frontRightHoof;
    private ModelRenderer frontRightLeg;
    private ModelRenderer backRightHoof;
    private ModelRenderer horseRightSaddleRope;
    private ModelRenderer muleRightChest;
    private ModelRenderer horseRightRein;
    private ModelRenderer horseRightEar;
    private ModelRenderer backLeftHoof;
    private ModelRenderer tailTip;
    private ModelRenderer horseLeftRein;
    private ModelRenderer horseLeftEar;
    private ModelRenderer horseFaceRopes;
    private ModelRenderer field_178712_c;
    private ModelRenderer mane;
    private ModelRenderer horseRightSaddleMetal;
    private ModelRenderer backLeftLeg;
    private ModelRenderer horseRightFaceMetal;
    private ModelRenderer horseLeftSaddleRope;
    private ModelRenderer horseSaddleBack;
    private ModelRenderer tailMiddle;
    private ModelRenderer horseSaddleBottom;
    private ModelRenderer frontRightShin;
    private ModelRenderer head;
    private ModelRenderer backLeftShin;
    private ModelRenderer body;
    private ModelRenderer backRightLeg;
    private ModelRenderer neck;
    private ModelRenderer frontLeftLeg;
    private ModelRenderer horseSaddleFront;
    private ModelRenderer backRightShin;
    private ModelRenderer frontLeftShin;
    private ModelRenderer muleLeftChest;
    private ModelRenderer tailBase;
    private ModelRenderer horseLeftSaddleMetal;
    
    @Override
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        super.setLivingAnimations(entityLivingBase, n, n2, n3);
        final float updateHorseRotation = this.updateHorseRotation(entityLivingBase.prevRenderYawOffset, entityLivingBase.renderYawOffset, n3);
        final float updateHorseRotation2 = this.updateHorseRotation(entityLivingBase.prevRotationYawHead, entityLivingBase.rotationYawHead, n3);
        final float n4 = entityLivingBase.prevRotationPitch + (entityLivingBase.rotationPitch - entityLivingBase.prevRotationPitch) * n3;
        float n5 = updateHorseRotation2 - updateHorseRotation;
        float n6 = n4 / 57.295776f;
        if (n5 > 20.0f) {
            n5 = 20.0f;
        }
        if (n5 < -20.0f) {
            n5 = -20.0f;
        }
        if (n2 > 0.2f) {
            n6 += MathHelper.cos(n * 0.4f) * 0.15f * n2;
        }
        final EntityHorse entityHorse = (EntityHorse)entityLivingBase;
        final float grassEatingAmount = entityHorse.getGrassEatingAmount(n3);
        final float rearingAmount = entityHorse.getRearingAmount(n3);
        final float n7 = 1.0f - rearingAmount;
        final float mouthOpennessAngle = entityHorse.getMouthOpennessAngle(n3);
        int n8;
        if (entityHorse.field_110278_bp != 0) {
            n8 = " ".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            n8 = "".length();
        }
        final int n9 = n8;
        final boolean horseSaddled = entityHorse.isHorseSaddled();
        int n10;
        if (entityHorse.riddenByEntity != null) {
            n10 = " ".length();
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else {
            n10 = "".length();
        }
        final int n11 = n10;
        final float n12 = entityLivingBase.ticksExisted + n3;
        final float cos = MathHelper.cos(n * 0.6662f + 3.1415927f);
        final float n13 = cos * 0.8f * n2;
        this.head.rotationPointY = 4.0f;
        this.head.rotationPointZ = -10.0f;
        this.tailBase.rotationPointY = 3.0f;
        this.tailMiddle.rotationPointZ = 14.0f;
        this.muleRightChest.rotationPointY = 3.0f;
        this.muleRightChest.rotationPointZ = 10.0f;
        this.body.rotateAngleX = 0.0f;
        this.head.rotateAngleX = 0.5235988f + n6;
        this.head.rotateAngleY = n5 / 57.295776f;
        this.head.rotateAngleX = rearingAmount * (0.2617994f + n6) + grassEatingAmount * 2.18166f + (1.0f - Math.max(rearingAmount, grassEatingAmount)) * this.head.rotateAngleX;
        this.head.rotateAngleY = rearingAmount * n5 / 57.295776f + (1.0f - Math.max(rearingAmount, grassEatingAmount)) * this.head.rotateAngleY;
        this.head.rotationPointY = rearingAmount * -6.0f + grassEatingAmount * 11.0f + (1.0f - Math.max(rearingAmount, grassEatingAmount)) * this.head.rotationPointY;
        this.head.rotationPointZ = rearingAmount * -1.0f + grassEatingAmount * -10.0f + (1.0f - Math.max(rearingAmount, grassEatingAmount)) * this.head.rotationPointZ;
        this.tailBase.rotationPointY = rearingAmount * 9.0f + n7 * this.tailBase.rotationPointY;
        this.tailMiddle.rotationPointZ = rearingAmount * 18.0f + n7 * this.tailMiddle.rotationPointZ;
        this.muleRightChest.rotationPointY = rearingAmount * 5.5f + n7 * this.muleRightChest.rotationPointY;
        this.muleRightChest.rotationPointZ = rearingAmount * 15.0f + n7 * this.muleRightChest.rotationPointZ;
        this.body.rotateAngleX = rearingAmount * -45.0f / 57.295776f + n7 * this.body.rotateAngleX;
        this.horseLeftEar.rotationPointY = this.head.rotationPointY;
        this.horseRightEar.rotationPointY = this.head.rotationPointY;
        this.muleLeftEar.rotationPointY = this.head.rotationPointY;
        this.muleRightEar.rotationPointY = this.head.rotationPointY;
        this.neck.rotationPointY = this.head.rotationPointY;
        this.field_178711_b.rotationPointY = 0.02f;
        this.field_178712_c.rotationPointY = 0.0f;
        this.mane.rotationPointY = this.head.rotationPointY;
        this.horseLeftEar.rotationPointZ = this.head.rotationPointZ;
        this.horseRightEar.rotationPointZ = this.head.rotationPointZ;
        this.muleLeftEar.rotationPointZ = this.head.rotationPointZ;
        this.muleRightEar.rotationPointZ = this.head.rotationPointZ;
        this.neck.rotationPointZ = this.head.rotationPointZ;
        this.field_178711_b.rotationPointZ = 0.02f - mouthOpennessAngle * 1.0f;
        this.field_178712_c.rotationPointZ = 0.0f + mouthOpennessAngle * 1.0f;
        this.mane.rotationPointZ = this.head.rotationPointZ;
        this.horseLeftEar.rotateAngleX = this.head.rotateAngleX;
        this.horseRightEar.rotateAngleX = this.head.rotateAngleX;
        this.muleLeftEar.rotateAngleX = this.head.rotateAngleX;
        this.muleRightEar.rotateAngleX = this.head.rotateAngleX;
        this.neck.rotateAngleX = this.head.rotateAngleX;
        this.field_178711_b.rotateAngleX = 0.0f - 0.09424778f * mouthOpennessAngle;
        this.field_178712_c.rotateAngleX = 0.0f + 0.15707964f * mouthOpennessAngle;
        this.mane.rotateAngleX = this.head.rotateAngleX;
        this.horseLeftEar.rotateAngleY = this.head.rotateAngleY;
        this.horseRightEar.rotateAngleY = this.head.rotateAngleY;
        this.muleLeftEar.rotateAngleY = this.head.rotateAngleY;
        this.muleRightEar.rotateAngleY = this.head.rotateAngleY;
        this.neck.rotateAngleY = this.head.rotateAngleY;
        this.field_178711_b.rotateAngleY = 0.0f;
        this.field_178712_c.rotateAngleY = 0.0f;
        this.mane.rotateAngleY = this.head.rotateAngleY;
        this.muleLeftChest.rotateAngleX = n13 / 5.0f;
        this.muleRightChest.rotateAngleX = -n13 / 5.0f;
        final float n14 = 0.2617994f * rearingAmount;
        final float cos2 = MathHelper.cos(n12 * 0.6f + 3.1415927f);
        this.frontLeftLeg.rotationPointY = -2.0f * rearingAmount + 9.0f * n7;
        this.frontLeftLeg.rotationPointZ = -2.0f * rearingAmount + -8.0f * n7;
        this.frontRightLeg.rotationPointY = this.frontLeftLeg.rotationPointY;
        this.frontRightLeg.rotationPointZ = this.frontLeftLeg.rotationPointZ;
        this.backLeftShin.rotationPointY = this.backLeftLeg.rotationPointY + MathHelper.sin(1.5707964f + n14 + n7 * -cos * 0.5f * n2) * 7.0f;
        this.backLeftShin.rotationPointZ = this.backLeftLeg.rotationPointZ + MathHelper.cos(4.712389f + n14 + n7 * -cos * 0.5f * n2) * 7.0f;
        this.backRightShin.rotationPointY = this.backRightLeg.rotationPointY + MathHelper.sin(1.5707964f + n14 + n7 * cos * 0.5f * n2) * 7.0f;
        this.backRightShin.rotationPointZ = this.backRightLeg.rotationPointZ + MathHelper.cos(4.712389f + n14 + n7 * cos * 0.5f * n2) * 7.0f;
        final float rotateAngleX = (-1.0471976f + cos2) * rearingAmount + n13 * n7;
        final float rotateAngleX2 = (-1.0471976f + -cos2) * rearingAmount + -n13 * n7;
        this.frontLeftShin.rotationPointY = this.frontLeftLeg.rotationPointY + MathHelper.sin(1.5707964f + rotateAngleX) * 7.0f;
        this.frontLeftShin.rotationPointZ = this.frontLeftLeg.rotationPointZ + MathHelper.cos(4.712389f + rotateAngleX) * 7.0f;
        this.frontRightShin.rotationPointY = this.frontRightLeg.rotationPointY + MathHelper.sin(1.5707964f + rotateAngleX2) * 7.0f;
        this.frontRightShin.rotationPointZ = this.frontRightLeg.rotationPointZ + MathHelper.cos(4.712389f + rotateAngleX2) * 7.0f;
        this.backLeftLeg.rotateAngleX = n14 + -cos * 0.5f * n2 * n7;
        this.backLeftShin.rotateAngleX = -0.08726646f * rearingAmount + (-cos * 0.5f * n2 - Math.max(0.0f, cos * 0.5f * n2)) * n7;
        this.backLeftHoof.rotateAngleX = this.backLeftShin.rotateAngleX;
        this.backRightLeg.rotateAngleX = n14 + cos * 0.5f * n2 * n7;
        this.backRightShin.rotateAngleX = -0.08726646f * rearingAmount + (cos * 0.5f * n2 - Math.max(0.0f, -cos * 0.5f * n2)) * n7;
        this.backRightHoof.rotateAngleX = this.backRightShin.rotateAngleX;
        this.frontLeftLeg.rotateAngleX = rotateAngleX;
        this.frontLeftShin.rotateAngleX = (this.frontLeftLeg.rotateAngleX + 3.1415927f * Math.max(0.0f, 0.2f + cos2 * 0.2f)) * rearingAmount + (n13 + Math.max(0.0f, cos * 0.5f * n2)) * n7;
        this.frontLeftHoof.rotateAngleX = this.frontLeftShin.rotateAngleX;
        this.frontRightLeg.rotateAngleX = rotateAngleX2;
        this.frontRightShin.rotateAngleX = (this.frontRightLeg.rotateAngleX + 3.1415927f * Math.max(0.0f, 0.2f - cos2 * 0.2f)) * rearingAmount + (-n13 + Math.max(0.0f, -cos * 0.5f * n2)) * n7;
        this.frontRightHoof.rotateAngleX = this.frontRightShin.rotateAngleX;
        this.backLeftHoof.rotationPointY = this.backLeftShin.rotationPointY;
        this.backLeftHoof.rotationPointZ = this.backLeftShin.rotationPointZ;
        this.backRightHoof.rotationPointY = this.backRightShin.rotationPointY;
        this.backRightHoof.rotationPointZ = this.backRightShin.rotationPointZ;
        this.frontLeftHoof.rotationPointY = this.frontLeftShin.rotationPointY;
        this.frontLeftHoof.rotationPointZ = this.frontLeftShin.rotationPointZ;
        this.frontRightHoof.rotationPointY = this.frontRightShin.rotationPointY;
        this.frontRightHoof.rotationPointZ = this.frontRightShin.rotationPointZ;
        if (horseSaddled) {
            this.horseSaddleBottom.rotationPointY = rearingAmount * 0.5f + n7 * 2.0f;
            this.horseSaddleBottom.rotationPointZ = rearingAmount * 11.0f + n7 * 2.0f;
            this.horseSaddleFront.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.horseSaddleBack.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.horseLeftSaddleRope.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.horseRightSaddleRope.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.horseLeftSaddleMetal.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.horseRightSaddleMetal.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.muleLeftChest.rotationPointY = this.muleRightChest.rotationPointY;
            this.horseSaddleFront.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.horseSaddleBack.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.horseLeftSaddleRope.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.horseRightSaddleRope.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.horseLeftSaddleMetal.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.horseRightSaddleMetal.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.muleLeftChest.rotationPointZ = this.muleRightChest.rotationPointZ;
            this.horseSaddleBottom.rotateAngleX = this.body.rotateAngleX;
            this.horseSaddleFront.rotateAngleX = this.body.rotateAngleX;
            this.horseSaddleBack.rotateAngleX = this.body.rotateAngleX;
            this.horseLeftRein.rotationPointY = this.head.rotationPointY;
            this.horseRightRein.rotationPointY = this.head.rotationPointY;
            this.horseFaceRopes.rotationPointY = this.head.rotationPointY;
            this.horseLeftFaceMetal.rotationPointY = this.head.rotationPointY;
            this.horseRightFaceMetal.rotationPointY = this.head.rotationPointY;
            this.horseLeftRein.rotationPointZ = this.head.rotationPointZ;
            this.horseRightRein.rotationPointZ = this.head.rotationPointZ;
            this.horseFaceRopes.rotationPointZ = this.head.rotationPointZ;
            this.horseLeftFaceMetal.rotationPointZ = this.head.rotationPointZ;
            this.horseRightFaceMetal.rotationPointZ = this.head.rotationPointZ;
            this.horseLeftRein.rotateAngleX = n6;
            this.horseRightRein.rotateAngleX = n6;
            this.horseFaceRopes.rotateAngleX = this.head.rotateAngleX;
            this.horseLeftFaceMetal.rotateAngleX = this.head.rotateAngleX;
            this.horseRightFaceMetal.rotateAngleX = this.head.rotateAngleX;
            this.horseFaceRopes.rotateAngleY = this.head.rotateAngleY;
            this.horseLeftFaceMetal.rotateAngleY = this.head.rotateAngleY;
            this.horseLeftRein.rotateAngleY = this.head.rotateAngleY;
            this.horseRightFaceMetal.rotateAngleY = this.head.rotateAngleY;
            this.horseRightRein.rotateAngleY = this.head.rotateAngleY;
            if (n11 != 0) {
                this.horseLeftSaddleRope.rotateAngleX = -1.0471976f;
                this.horseLeftSaddleMetal.rotateAngleX = -1.0471976f;
                this.horseRightSaddleRope.rotateAngleX = -1.0471976f;
                this.horseRightSaddleMetal.rotateAngleX = -1.0471976f;
                this.horseLeftSaddleRope.rotateAngleZ = 0.0f;
                this.horseLeftSaddleMetal.rotateAngleZ = 0.0f;
                this.horseRightSaddleRope.rotateAngleZ = 0.0f;
                this.horseRightSaddleMetal.rotateAngleZ = 0.0f;
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            else {
                this.horseLeftSaddleRope.rotateAngleX = n13 / 3.0f;
                this.horseLeftSaddleMetal.rotateAngleX = n13 / 3.0f;
                this.horseRightSaddleRope.rotateAngleX = n13 / 3.0f;
                this.horseRightSaddleMetal.rotateAngleX = n13 / 3.0f;
                this.horseLeftSaddleRope.rotateAngleZ = n13 / 5.0f;
                this.horseLeftSaddleMetal.rotateAngleZ = n13 / 5.0f;
                this.horseRightSaddleRope.rotateAngleZ = -n13 / 5.0f;
                this.horseRightSaddleMetal.rotateAngleZ = -n13 / 5.0f;
            }
        }
        float n15 = -1.3089f + n2 * 1.5f;
        if (n15 > 0.0f) {
            n15 = 0.0f;
        }
        if (n9 != 0) {
            this.tailBase.rotateAngleY = MathHelper.cos(n12 * 0.7f);
            n15 = 0.0f;
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else {
            this.tailBase.rotateAngleY = 0.0f;
        }
        this.tailMiddle.rotateAngleY = this.tailBase.rotateAngleY;
        this.tailTip.rotateAngleY = this.tailBase.rotateAngleY;
        this.tailMiddle.rotationPointY = this.tailBase.rotationPointY;
        this.tailTip.rotationPointY = this.tailBase.rotationPointY;
        this.tailMiddle.rotationPointZ = this.tailBase.rotationPointZ;
        this.tailTip.rotationPointZ = this.tailBase.rotationPointZ;
        this.tailBase.rotateAngleX = n15;
        this.tailMiddle.rotateAngleX = n15;
        this.tailTip.rotateAngleX = -0.2618f + n15;
    }
    
    private void setBoxRotation(final ModelRenderer modelRenderer, final float rotateAngleX, final float rotateAngleY, final float rotateAngleZ) {
        modelRenderer.rotateAngleX = rotateAngleX;
        modelRenderer.rotateAngleY = rotateAngleY;
        modelRenderer.rotateAngleZ = rotateAngleZ;
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final EntityHorse entityHorse = (EntityHorse)entity;
        final int horseType = entityHorse.getHorseType();
        final float grassEatingAmount = entityHorse.getGrassEatingAmount(0.0f);
        final boolean adultHorse = entityHorse.isAdultHorse();
        int n7;
        if (adultHorse && entityHorse.isHorseSaddled()) {
            n7 = " ".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n7 = "".length();
        }
        final int n8 = n7;
        int n9;
        if (adultHorse && entityHorse.isChested()) {
            n9 = " ".length();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else {
            n9 = "".length();
        }
        final int n10 = n9;
        int n11;
        if (horseType != " ".length() && horseType != "  ".length()) {
            n11 = "".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            n11 = " ".length();
        }
        final int n12 = n11;
        final float horseSize = entityHorse.getHorseSize();
        int n13;
        if (entityHorse.riddenByEntity != null) {
            n13 = " ".length();
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            n13 = "".length();
        }
        final int n14 = n13;
        if (n8 != 0) {
            this.horseFaceRopes.render(n6);
            this.horseSaddleBottom.render(n6);
            this.horseSaddleFront.render(n6);
            this.horseSaddleBack.render(n6);
            this.horseLeftSaddleRope.render(n6);
            this.horseLeftSaddleMetal.render(n6);
            this.horseRightSaddleRope.render(n6);
            this.horseRightSaddleMetal.render(n6);
            this.horseLeftFaceMetal.render(n6);
            this.horseRightFaceMetal.render(n6);
            if (n14 != 0) {
                this.horseLeftRein.render(n6);
                this.horseRightRein.render(n6);
            }
        }
        if (!adultHorse) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(horseSize, 0.5f + horseSize * 0.5f, horseSize);
            GlStateManager.translate(0.0f, 0.95f * (1.0f - horseSize), 0.0f);
        }
        this.backLeftLeg.render(n6);
        this.backLeftShin.render(n6);
        this.backLeftHoof.render(n6);
        this.backRightLeg.render(n6);
        this.backRightShin.render(n6);
        this.backRightHoof.render(n6);
        this.frontLeftLeg.render(n6);
        this.frontLeftShin.render(n6);
        this.frontLeftHoof.render(n6);
        this.frontRightLeg.render(n6);
        this.frontRightShin.render(n6);
        this.frontRightHoof.render(n6);
        if (!adultHorse) {
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(horseSize, horseSize, horseSize);
            GlStateManager.translate(0.0f, 1.35f * (1.0f - horseSize), 0.0f);
        }
        this.body.render(n6);
        this.tailBase.render(n6);
        this.tailMiddle.render(n6);
        this.tailTip.render(n6);
        this.neck.render(n6);
        this.mane.render(n6);
        if (!adultHorse) {
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            final float n15 = 0.5f + horseSize * horseSize * 0.5f;
            GlStateManager.scale(n15, n15, n15);
            if (grassEatingAmount <= 0.0f) {
                GlStateManager.translate(0.0f, 1.35f * (1.0f - horseSize), 0.0f);
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
            else {
                GlStateManager.translate(0.0f, 0.9f * (1.0f - horseSize) * grassEatingAmount + 1.35f * (1.0f - horseSize) * (1.0f - grassEatingAmount), 0.15f * (1.0f - horseSize) * grassEatingAmount);
            }
        }
        if (n12 != 0) {
            this.muleLeftEar.render(n6);
            this.muleRightEar.render(n6);
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        else {
            this.horseLeftEar.render(n6);
            this.horseRightEar.render(n6);
        }
        this.head.render(n6);
        if (!adultHorse) {
            GlStateManager.popMatrix();
        }
        if (n10 != 0) {
            this.muleLeftChest.render(n6);
            this.muleRightChest.render(n6);
        }
    }
    
    private float updateHorseRotation(final float n, final float n2, final float n3) {
        float n4 = n2 - n;
        "".length();
        if (2 == 1) {
            throw null;
        }
        while (n4 < -180.0f) {
            n4 += 360.0f;
        }
        "".length();
        if (1 >= 4) {
            throw null;
        }
        while (n4 >= 180.0f) {
            n4 -= 360.0f;
        }
        return n + n3 * n4;
    }
    
    public ModelHorse() {
        this.textureWidth = 44 + 115 - 113 + 82;
        this.textureHeight = 102 + 83 - 107 + 50;
        (this.body = new ModelRenderer(this, "".length(), 0x9D ^ 0xBF)).addBox(-5.0f, -8.0f, -19.0f, 0xAC ^ 0xA6, 0xB0 ^ 0xBA, 0x45 ^ 0x5D);
        this.body.setRotationPoint(0.0f, 11.0f, 9.0f);
        (this.tailBase = new ModelRenderer(this, 0x75 ^ 0x59, "".length())).addBox(-1.0f, -1.0f, 0.0f, "  ".length(), "  ".length(), "   ".length());
        this.tailBase.setRotationPoint(0.0f, 3.0f, 14.0f);
        this.setBoxRotation(this.tailBase, -1.134464f, 0.0f, 0.0f);
        (this.tailMiddle = new ModelRenderer(this, 0xE ^ 0x28, 0x28 ^ 0x2F)).addBox(-1.5f, -2.0f, 3.0f, "   ".length(), 0x36 ^ 0x32, 0x1E ^ 0x19);
        this.tailMiddle.setRotationPoint(0.0f, 3.0f, 14.0f);
        this.setBoxRotation(this.tailMiddle, -1.134464f, 0.0f, 0.0f);
        (this.tailTip = new ModelRenderer(this, 0x4C ^ 0x54, "   ".length())).addBox(-1.5f, -4.5f, 9.0f, "   ".length(), 0x68 ^ 0x6C, 0x1B ^ 0x1C);
        this.tailTip.setRotationPoint(0.0f, 3.0f, 14.0f);
        this.setBoxRotation(this.tailTip, -1.40215f, 0.0f, 0.0f);
        (this.backLeftLeg = new ModelRenderer(this, 0x26 ^ 0x68, 0x86 ^ 0x9B)).addBox(-2.5f, -2.0f, -2.5f, 0x47 ^ 0x43, 0x40 ^ 0x49, 0x7 ^ 0x2);
        this.backLeftLeg.setRotationPoint(4.0f, 9.0f, 11.0f);
        (this.backLeftShin = new ModelRenderer(this, 0xD ^ 0x43, 0x4A ^ 0x61)).addBox(-2.0f, 0.0f, -1.5f, "   ".length(), 0x3F ^ 0x3A, "   ".length());
        this.backLeftShin.setRotationPoint(4.0f, 16.0f, 11.0f);
        (this.backLeftHoof = new ModelRenderer(this, 0xC ^ 0x42, 0xB3 ^ 0x80)).addBox(-2.5f, 5.1f, -2.0f, 0xA7 ^ 0xA3, "   ".length(), 0x2B ^ 0x2F);
        this.backLeftHoof.setRotationPoint(4.0f, 16.0f, 11.0f);
        (this.backRightLeg = new ModelRenderer(this, 0xD6 ^ 0xB6, 0x84 ^ 0x99)).addBox(-1.5f, -2.0f, -2.5f, 0x61 ^ 0x65, 0x9 ^ 0x0, 0x11 ^ 0x14);
        this.backRightLeg.setRotationPoint(-4.0f, 9.0f, 11.0f);
        (this.backRightShin = new ModelRenderer(this, 0xA3 ^ 0xC3, 0x5 ^ 0x2E)).addBox(-1.0f, 0.0f, -1.5f, "   ".length(), 0x8C ^ 0x89, "   ".length());
        this.backRightShin.setRotationPoint(-4.0f, 16.0f, 11.0f);
        (this.backRightHoof = new ModelRenderer(this, 0xA1 ^ 0xC1, 0x20 ^ 0x13)).addBox(-1.5f, 5.1f, -2.0f, 0x57 ^ 0x53, "   ".length(), 0xA9 ^ 0xAD);
        this.backRightHoof.setRotationPoint(-4.0f, 16.0f, 11.0f);
        (this.frontLeftLeg = new ModelRenderer(this, 0x25 ^ 0x9, 0x89 ^ 0x94)).addBox(-1.9f, -1.0f, -2.1f, "   ".length(), 0xAF ^ 0xA7, 0x87 ^ 0x83);
        this.frontLeftLeg.setRotationPoint(4.0f, 9.0f, -8.0f);
        (this.frontLeftShin = new ModelRenderer(this, 0x15 ^ 0x39, 0x61 ^ 0x48)).addBox(-1.9f, 0.0f, -1.6f, "   ".length(), 0x99 ^ 0x9C, "   ".length());
        this.frontLeftShin.setRotationPoint(4.0f, 16.0f, -8.0f);
        (this.frontLeftHoof = new ModelRenderer(this, 0xA2 ^ 0x8E, 0x2F ^ 0x1C)).addBox(-2.4f, 5.1f, -2.1f, 0xA7 ^ 0xA3, "   ".length(), 0x9D ^ 0x99);
        this.frontLeftHoof.setRotationPoint(4.0f, 16.0f, -8.0f);
        (this.frontRightLeg = new ModelRenderer(this, 0x4A ^ 0x76, 0x28 ^ 0x35)).addBox(-1.1f, -1.0f, -2.1f, "   ".length(), 0x6B ^ 0x63, 0x34 ^ 0x30);
        this.frontRightLeg.setRotationPoint(-4.0f, 9.0f, -8.0f);
        (this.frontRightShin = new ModelRenderer(this, 0x88 ^ 0xB4, 0x83 ^ 0xAA)).addBox(-1.1f, 0.0f, -1.6f, "   ".length(), 0x39 ^ 0x3C, "   ".length());
        this.frontRightShin.setRotationPoint(-4.0f, 16.0f, -8.0f);
        (this.frontRightHoof = new ModelRenderer(this, 0x77 ^ 0x4B, 0x9 ^ 0x3A)).addBox(-1.6f, 5.1f, -2.1f, 0x9E ^ 0x9A, "   ".length(), 0x35 ^ 0x31);
        this.frontRightHoof.setRotationPoint(-4.0f, 16.0f, -8.0f);
        (this.head = new ModelRenderer(this, "".length(), "".length())).addBox(-2.5f, -10.0f, -1.5f, 0x8F ^ 0x8A, 0x9C ^ 0x99, 0x24 ^ 0x23);
        this.head.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.head, 0.5235988f, 0.0f, 0.0f);
        (this.field_178711_b = new ModelRenderer(this, 0xDD ^ 0xC5, 0x21 ^ 0x33)).addBox(-2.0f, -10.0f, -7.0f, 0x80 ^ 0x84, "   ".length(), 0xBC ^ 0xBA);
        this.field_178711_b.setRotationPoint(0.0f, 3.95f, -10.0f);
        this.setBoxRotation(this.field_178711_b, 0.5235988f, 0.0f, 0.0f);
        (this.field_178712_c = new ModelRenderer(this, 0x77 ^ 0x6F, 0x15 ^ 0xE)).addBox(-2.0f, -7.0f, -6.5f, 0x6E ^ 0x6A, "  ".length(), 0x5 ^ 0x0);
        this.field_178712_c.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.field_178712_c, 0.5235988f, 0.0f, 0.0f);
        this.head.addChild(this.field_178711_b);
        this.head.addChild(this.field_178712_c);
        (this.horseLeftEar = new ModelRenderer(this, "".length(), "".length())).addBox(0.45f, -12.0f, 4.0f, "  ".length(), "   ".length(), " ".length());
        this.horseLeftEar.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.horseLeftEar, 0.5235988f, 0.0f, 0.0f);
        (this.horseRightEar = new ModelRenderer(this, "".length(), "".length())).addBox(-2.45f, -12.0f, 4.0f, "  ".length(), "   ".length(), " ".length());
        this.horseRightEar.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.horseRightEar, 0.5235988f, 0.0f, 0.0f);
        (this.muleLeftEar = new ModelRenderer(this, "".length(), 0x57 ^ 0x5B)).addBox(-2.0f, -16.0f, 4.0f, "  ".length(), 0x3B ^ 0x3C, " ".length());
        this.muleLeftEar.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.muleLeftEar, 0.5235988f, 0.0f, 0.2617994f);
        (this.muleRightEar = new ModelRenderer(this, "".length(), 0x3D ^ 0x31)).addBox(0.0f, -16.0f, 4.0f, "  ".length(), 0x62 ^ 0x65, " ".length());
        this.muleRightEar.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.muleRightEar, 0.5235988f, 0.0f, -0.2617994f);
        (this.neck = new ModelRenderer(this, "".length(), 0x10 ^ 0x1C)).addBox(-2.05f, -9.8f, -2.0f, 0x98 ^ 0x9C, 0x88 ^ 0x86, 0x93 ^ 0x9B);
        this.neck.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.neck, 0.5235988f, 0.0f, 0.0f);
        (this.muleLeftChest = new ModelRenderer(this, "".length(), 0x12 ^ 0x30)).addBox(-3.0f, 0.0f, 0.0f, 0x69 ^ 0x61, 0xA9 ^ 0xA1, "   ".length());
        this.muleLeftChest.setRotationPoint(-7.5f, 3.0f, 10.0f);
        this.setBoxRotation(this.muleLeftChest, 0.0f, 1.5707964f, 0.0f);
        (this.muleRightChest = new ModelRenderer(this, "".length(), 0xED ^ 0xC2)).addBox(-3.0f, 0.0f, 0.0f, 0xAD ^ 0xA5, 0x8C ^ 0x84, "   ".length());
        this.muleRightChest.setRotationPoint(4.5f, 3.0f, 10.0f);
        this.setBoxRotation(this.muleRightChest, 0.0f, 1.5707964f, 0.0f);
        (this.horseSaddleBottom = new ModelRenderer(this, 0xDC ^ 0x8C, "".length())).addBox(-5.0f, 0.0f, -3.0f, 0x69 ^ 0x63, " ".length(), 0x7 ^ 0xF);
        this.horseSaddleBottom.setRotationPoint(0.0f, 2.0f, 2.0f);
        (this.horseSaddleFront = new ModelRenderer(this, 0x63 ^ 0x9, 0x2D ^ 0x24)).addBox(-1.5f, -1.0f, -3.0f, "   ".length(), " ".length(), "  ".length());
        this.horseSaddleFront.setRotationPoint(0.0f, 2.0f, 2.0f);
        (this.horseSaddleBack = new ModelRenderer(this, 0xFC ^ 0xAC, 0xAA ^ 0xA3)).addBox(-4.0f, -1.0f, 3.0f, 0x0 ^ 0x8, " ".length(), "  ".length());
        this.horseSaddleBack.setRotationPoint(0.0f, 2.0f, 2.0f);
        (this.horseLeftSaddleMetal = new ModelRenderer(this, 0xDB ^ 0x91, "".length())).addBox(-0.5f, 6.0f, -1.0f, " ".length(), "  ".length(), "  ".length());
        this.horseLeftSaddleMetal.setRotationPoint(5.0f, 3.0f, 2.0f);
        (this.horseLeftSaddleRope = new ModelRenderer(this, 0x59 ^ 0x1F, "".length())).addBox(-0.5f, 0.0f, -0.5f, " ".length(), 0xAB ^ 0xAD, " ".length());
        this.horseLeftSaddleRope.setRotationPoint(5.0f, 3.0f, 2.0f);
        (this.horseRightSaddleMetal = new ModelRenderer(this, 0x21 ^ 0x6B, 0x44 ^ 0x40)).addBox(-0.5f, 6.0f, -1.0f, " ".length(), "  ".length(), "  ".length());
        this.horseRightSaddleMetal.setRotationPoint(-5.0f, 3.0f, 2.0f);
        (this.horseRightSaddleRope = new ModelRenderer(this, 0x6F ^ 0x3F, "".length())).addBox(-0.5f, 0.0f, -0.5f, " ".length(), 0x98 ^ 0x9E, " ".length());
        this.horseRightSaddleRope.setRotationPoint(-5.0f, 3.0f, 2.0f);
        (this.horseLeftFaceMetal = new ModelRenderer(this, 0x35 ^ 0x7F, 0x3A ^ 0x37)).addBox(1.5f, -8.0f, -4.0f, " ".length(), "  ".length(), "  ".length());
        this.horseLeftFaceMetal.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.horseLeftFaceMetal, 0.5235988f, 0.0f, 0.0f);
        (this.horseRightFaceMetal = new ModelRenderer(this, 0x2 ^ 0x48, 0x17 ^ 0x1A)).addBox(-2.5f, -8.0f, -4.0f, " ".length(), "  ".length(), "  ".length());
        this.horseRightFaceMetal.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.horseRightFaceMetal, 0.5235988f, 0.0f, 0.0f);
        (this.horseLeftRein = new ModelRenderer(this, 0x45 ^ 0x69, 0x92 ^ 0x98)).addBox(2.6f, -6.0f, -6.0f, "".length(), "   ".length(), 0x99 ^ 0x89);
        this.horseLeftRein.setRotationPoint(0.0f, 4.0f, -10.0f);
        (this.horseRightRein = new ModelRenderer(this, 0x89 ^ 0xA5, 0x6B ^ 0x6E)).addBox(-2.6f, -6.0f, -6.0f, "".length(), "   ".length(), 0x4F ^ 0x5F);
        this.horseRightRein.setRotationPoint(0.0f, 4.0f, -10.0f);
        (this.mane = new ModelRenderer(this, 0x63 ^ 0x59, "".length())).addBox(-1.0f, -11.5f, 5.0f, "  ".length(), 0x76 ^ 0x66, 0xB2 ^ 0xB6);
        this.mane.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.mane, 0.5235988f, 0.0f, 0.0f);
        (this.horseFaceRopes = new ModelRenderer(this, 0xFD ^ 0xAD, 0x67 ^ 0x6B)).addBox(-2.5f, -10.1f, -7.0f, 0x36 ^ 0x33, 0xBA ^ 0xBF, 0x73 ^ 0x7F, 0.2f);
        this.horseFaceRopes.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.horseFaceRopes, 0.5235988f, 0.0f, 0.0f);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
