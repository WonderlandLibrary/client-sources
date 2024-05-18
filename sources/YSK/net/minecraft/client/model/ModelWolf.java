package net.minecraft.client.model;

import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;

public class ModelWolf extends ModelBase
{
    public ModelRenderer wolfHeadMain;
    ModelRenderer wolfMane;
    public ModelRenderer wolfLeg3;
    public ModelRenderer wolfLeg4;
    public ModelRenderer wolfBody;
    ModelRenderer wolfTail;
    public ModelRenderer wolfLeg1;
    public ModelRenderer wolfLeg2;
    
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
            if (3 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float rotateAngleX, final float n3, final float n4, final float n5, final Entity entity) {
        super.setRotationAngles(n, n2, rotateAngleX, n3, n4, n5, entity);
        this.wolfHeadMain.rotateAngleX = n4 / 57.295776f;
        this.wolfHeadMain.rotateAngleY = n3 / 57.295776f;
        this.wolfTail.rotateAngleX = rotateAngleX;
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        super.render(entity, n, n2, n3, n4, n5, n6);
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        if (this.isChild) {
            final float n7 = 2.0f;
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0f, 5.0f * n6, 2.0f * n6);
            this.wolfHeadMain.renderWithRotation(n6);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0f / n7, 1.0f / n7, 1.0f / n7);
            GlStateManager.translate(0.0f, 24.0f * n6, 0.0f);
            this.wolfBody.render(n6);
            this.wolfLeg1.render(n6);
            this.wolfLeg2.render(n6);
            this.wolfLeg3.render(n6);
            this.wolfLeg4.render(n6);
            this.wolfTail.renderWithRotation(n6);
            this.wolfMane.render(n6);
            GlStateManager.popMatrix();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            this.wolfHeadMain.renderWithRotation(n6);
            this.wolfBody.render(n6);
            this.wolfLeg1.render(n6);
            this.wolfLeg2.render(n6);
            this.wolfLeg3.render(n6);
            this.wolfLeg4.render(n6);
            this.wolfTail.renderWithRotation(n6);
            this.wolfMane.render(n6);
        }
    }
    
    public ModelWolf() {
        final float n = 0.0f;
        final float n2 = 13.5f;
        (this.wolfHeadMain = new ModelRenderer(this, "".length(), "".length())).addBox(-3.0f, -3.0f, -2.0f, 0xB0 ^ 0xB6, 0x44 ^ 0x42, 0x75 ^ 0x71, n);
        this.wolfHeadMain.setRotationPoint(-1.0f, n2, -7.0f);
        (this.wolfBody = new ModelRenderer(this, 0xD3 ^ 0xC1, 0xF ^ 0x1)).addBox(-4.0f, -2.0f, -3.0f, 0x6B ^ 0x6D, 0x60 ^ 0x69, 0x13 ^ 0x15, n);
        this.wolfBody.setRotationPoint(0.0f, 14.0f, 2.0f);
        (this.wolfMane = new ModelRenderer(this, 0x85 ^ 0x90, "".length())).addBox(-4.0f, -3.0f, -3.0f, 0x42 ^ 0x4A, 0x9 ^ 0xF, 0x1C ^ 0x1B, n);
        this.wolfMane.setRotationPoint(-1.0f, 14.0f, 2.0f);
        (this.wolfLeg1 = new ModelRenderer(this, "".length(), 0x1B ^ 0x9)).addBox(-1.0f, 0.0f, -1.0f, "  ".length(), 0x73 ^ 0x7B, "  ".length(), n);
        this.wolfLeg1.setRotationPoint(-2.5f, 16.0f, 7.0f);
        (this.wolfLeg2 = new ModelRenderer(this, "".length(), 0x2D ^ 0x3F)).addBox(-1.0f, 0.0f, -1.0f, "  ".length(), 0x38 ^ 0x30, "  ".length(), n);
        this.wolfLeg2.setRotationPoint(0.5f, 16.0f, 7.0f);
        (this.wolfLeg3 = new ModelRenderer(this, "".length(), 0x13 ^ 0x1)).addBox(-1.0f, 0.0f, -1.0f, "  ".length(), 0x5F ^ 0x57, "  ".length(), n);
        this.wolfLeg3.setRotationPoint(-2.5f, 16.0f, -4.0f);
        (this.wolfLeg4 = new ModelRenderer(this, "".length(), 0x10 ^ 0x2)).addBox(-1.0f, 0.0f, -1.0f, "  ".length(), 0xCE ^ 0xC6, "  ".length(), n);
        this.wolfLeg4.setRotationPoint(0.5f, 16.0f, -4.0f);
        (this.wolfTail = new ModelRenderer(this, 0x99 ^ 0x90, 0x6F ^ 0x7D)).addBox(-1.0f, 0.0f, -1.0f, "  ".length(), 0x8F ^ 0x87, "  ".length(), n);
        this.wolfTail.setRotationPoint(-1.0f, 12.0f, 8.0f);
        this.wolfHeadMain.setTextureOffset(0x9F ^ 0x8F, 0x1D ^ 0x13).addBox(-3.0f, -5.0f, 0.0f, "  ".length(), "  ".length(), " ".length(), n);
        this.wolfHeadMain.setTextureOffset(0x16 ^ 0x6, 0x59 ^ 0x57).addBox(1.0f, -5.0f, 0.0f, "  ".length(), "  ".length(), " ".length(), n);
        this.wolfHeadMain.setTextureOffset("".length(), 0x93 ^ 0x99).addBox(-1.5f, 0.0f, -5.0f, "   ".length(), "   ".length(), 0xB6 ^ 0xB2, n);
    }
    
    @Override
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        final EntityWolf entityWolf = (EntityWolf)entityLivingBase;
        if (entityWolf.isAngry()) {
            this.wolfTail.rotateAngleY = 0.0f;
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else {
            this.wolfTail.rotateAngleY = MathHelper.cos(n * 0.6662f) * 1.4f * n2;
        }
        if (entityWolf.isSitting()) {
            this.wolfMane.setRotationPoint(-1.0f, 16.0f, -3.0f);
            this.wolfMane.rotateAngleX = 1.2566371f;
            this.wolfMane.rotateAngleY = 0.0f;
            this.wolfBody.setRotationPoint(0.0f, 18.0f, 0.0f);
            this.wolfBody.rotateAngleX = 0.7853982f;
            this.wolfTail.setRotationPoint(-1.0f, 21.0f, 6.0f);
            this.wolfLeg1.setRotationPoint(-2.5f, 22.0f, 2.0f);
            this.wolfLeg1.rotateAngleX = 4.712389f;
            this.wolfLeg2.setRotationPoint(0.5f, 22.0f, 2.0f);
            this.wolfLeg2.rotateAngleX = 4.712389f;
            this.wolfLeg3.rotateAngleX = 5.811947f;
            this.wolfLeg3.setRotationPoint(-2.49f, 17.0f, -4.0f);
            this.wolfLeg4.rotateAngleX = 5.811947f;
            this.wolfLeg4.setRotationPoint(0.51f, 17.0f, -4.0f);
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            this.wolfBody.setRotationPoint(0.0f, 14.0f, 2.0f);
            this.wolfBody.rotateAngleX = 1.5707964f;
            this.wolfMane.setRotationPoint(-1.0f, 14.0f, -3.0f);
            this.wolfMane.rotateAngleX = this.wolfBody.rotateAngleX;
            this.wolfTail.setRotationPoint(-1.0f, 12.0f, 8.0f);
            this.wolfLeg1.setRotationPoint(-2.5f, 16.0f, 7.0f);
            this.wolfLeg2.setRotationPoint(0.5f, 16.0f, 7.0f);
            this.wolfLeg3.setRotationPoint(-2.5f, 16.0f, -4.0f);
            this.wolfLeg4.setRotationPoint(0.5f, 16.0f, -4.0f);
            this.wolfLeg1.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.4f * n2;
            this.wolfLeg2.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.4f * n2;
            this.wolfLeg3.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.4f * n2;
            this.wolfLeg4.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.4f * n2;
        }
        this.wolfHeadMain.rotateAngleZ = entityWolf.getInterestedAngle(n3) + entityWolf.getShakeAngle(n3, 0.0f);
        this.wolfMane.rotateAngleZ = entityWolf.getShakeAngle(n3, -0.08f);
        this.wolfBody.rotateAngleZ = entityWolf.getShakeAngle(n3, -0.16f);
        this.wolfTail.rotateAngleZ = entityWolf.getShakeAngle(n3, -0.2f);
    }
}
