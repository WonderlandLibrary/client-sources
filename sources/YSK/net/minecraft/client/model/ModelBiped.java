package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public class ModelBiped extends ModelBase
{
    public ModelRenderer bipedBody;
    public ModelRenderer bipedRightArm;
    public ModelRenderer bipedHeadwear;
    public boolean isSneak;
    public int heldItemLeft;
    public int heldItemRight;
    public ModelRenderer bipedLeftArm;
    public ModelRenderer bipedLeftLeg;
    public ModelRenderer bipedHead;
    public boolean aimedBow;
    public ModelRenderer bipedRightLeg;
    
    public void postRenderArm(final float n) {
        this.bipedRightArm.postRender(n);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        this.bipedHead.rotateAngleY = n4 / 57.295776f;
        this.bipedHead.rotateAngleX = n5 / 57.295776f;
        this.bipedRightArm.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 2.0f * n2 * 0.5f;
        this.bipedLeftArm.rotateAngleX = MathHelper.cos(n * 0.6662f) * 2.0f * n2 * 0.5f;
        this.bipedRightArm.rotateAngleZ = 0.0f;
        this.bipedLeftArm.rotateAngleZ = 0.0f;
        this.bipedRightLeg.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.4f * n2;
        this.bipedLeftLeg.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.4f * n2;
        this.bipedRightLeg.rotateAngleY = 0.0f;
        this.bipedLeftLeg.rotateAngleY = 0.0f;
        if (this.isRiding) {
            final ModelRenderer bipedRightArm = this.bipedRightArm;
            bipedRightArm.rotateAngleX -= 0.62831855f;
            final ModelRenderer bipedLeftArm = this.bipedLeftArm;
            bipedLeftArm.rotateAngleX -= 0.62831855f;
            this.bipedRightLeg.rotateAngleX = -1.2566371f;
            this.bipedLeftLeg.rotateAngleX = -1.2566371f;
            this.bipedRightLeg.rotateAngleY = 0.31415927f;
            this.bipedLeftLeg.rotateAngleY = -0.31415927f;
        }
        if (this.heldItemLeft != 0) {
            this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5f - 0.31415927f * this.heldItemLeft;
        }
        this.bipedRightArm.rotateAngleY = 0.0f;
        this.bipedRightArm.rotateAngleZ = 0.0f;
        switch (this.heldItemRight) {
            default: {
                "".length();
                if (0 < -1) {
                    throw null;
                }
                break;
            }
            case 1: {
                this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5f - 0.31415927f * this.heldItemRight;
                "".length();
                if (0 < 0) {
                    throw null;
                }
                break;
            }
            case 3: {
                this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5f - 0.31415927f * this.heldItemRight;
                this.bipedRightArm.rotateAngleY = -0.5235988f;
                break;
            }
        }
        this.bipedLeftArm.rotateAngleY = 0.0f;
        if (this.swingProgress > -9990.0f) {
            this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(this.swingProgress) * 3.1415927f * 2.0f) * 0.2f;
            this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0f;
            final ModelRenderer bipedRightArm2 = this.bipedRightArm;
            bipedRightArm2.rotateAngleY += this.bipedBody.rotateAngleY;
            final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
            bipedLeftArm2.rotateAngleY += this.bipedBody.rotateAngleY;
            final ModelRenderer bipedLeftArm3 = this.bipedLeftArm;
            bipedLeftArm3.rotateAngleX += this.bipedBody.rotateAngleY;
            final float n7 = 1.0f - this.swingProgress;
            final float n8 = n7 * n7;
            this.bipedRightArm.rotateAngleX -= (float)(MathHelper.sin((1.0f - n8 * n8) * 3.1415927f) * 1.2 + MathHelper.sin(this.swingProgress * 3.1415927f) * -(this.bipedHead.rotateAngleX - 0.7f) * 0.75f);
            final ModelRenderer bipedRightArm3 = this.bipedRightArm;
            bipedRightArm3.rotateAngleY += this.bipedBody.rotateAngleY * 2.0f;
            final ModelRenderer bipedRightArm4 = this.bipedRightArm;
            bipedRightArm4.rotateAngleZ += MathHelper.sin(this.swingProgress * 3.1415927f) * -0.4f;
        }
        if (this.isSneak) {
            this.bipedBody.rotateAngleX = 0.5f;
            final ModelRenderer bipedRightArm5 = this.bipedRightArm;
            bipedRightArm5.rotateAngleX += 0.4f;
            final ModelRenderer bipedLeftArm4 = this.bipedLeftArm;
            bipedLeftArm4.rotateAngleX += 0.4f;
            this.bipedRightLeg.rotationPointZ = 4.0f;
            this.bipedLeftLeg.rotationPointZ = 4.0f;
            this.bipedRightLeg.rotationPointY = 9.0f;
            this.bipedLeftLeg.rotationPointY = 9.0f;
            this.bipedHead.rotationPointY = 1.0f;
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else {
            this.bipedBody.rotateAngleX = 0.0f;
            this.bipedRightLeg.rotationPointZ = 0.1f;
            this.bipedLeftLeg.rotationPointZ = 0.1f;
            this.bipedRightLeg.rotationPointY = 12.0f;
            this.bipedLeftLeg.rotationPointY = 12.0f;
            this.bipedHead.rotationPointY = 0.0f;
        }
        final ModelRenderer bipedRightArm6 = this.bipedRightArm;
        bipedRightArm6.rotateAngleZ += MathHelper.cos(n3 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedLeftArm5 = this.bipedLeftArm;
        bipedLeftArm5.rotateAngleZ -= MathHelper.cos(n3 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedRightArm7 = this.bipedRightArm;
        bipedRightArm7.rotateAngleX += MathHelper.sin(n3 * 0.067f) * 0.05f;
        final ModelRenderer bipedLeftArm6 = this.bipedLeftArm;
        bipedLeftArm6.rotateAngleX -= MathHelper.sin(n3 * 0.067f) * 0.05f;
        if (this.aimedBow) {
            final float n9 = 0.0f;
            final float n10 = 0.0f;
            this.bipedRightArm.rotateAngleZ = 0.0f;
            this.bipedLeftArm.rotateAngleZ = 0.0f;
            this.bipedRightArm.rotateAngleY = -(0.1f - n9 * 0.6f) + this.bipedHead.rotateAngleY;
            this.bipedLeftArm.rotateAngleY = 0.1f - n9 * 0.6f + this.bipedHead.rotateAngleY + 0.4f;
            this.bipedRightArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
            this.bipedLeftArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
            final ModelRenderer bipedRightArm8 = this.bipedRightArm;
            bipedRightArm8.rotateAngleX -= n9 * 1.2f - n10 * 0.4f;
            final ModelRenderer bipedLeftArm7 = this.bipedLeftArm;
            bipedLeftArm7.rotateAngleX -= n9 * 1.2f - n10 * 0.4f;
            final ModelRenderer bipedRightArm9 = this.bipedRightArm;
            bipedRightArm9.rotateAngleZ += MathHelper.cos(n3 * 0.09f) * 0.05f + 0.05f;
            final ModelRenderer bipedLeftArm8 = this.bipedLeftArm;
            bipedLeftArm8.rotateAngleZ -= MathHelper.cos(n3 * 0.09f) * 0.05f + 0.05f;
            final ModelRenderer bipedRightArm10 = this.bipedRightArm;
            bipedRightArm10.rotateAngleX += MathHelper.sin(n3 * 0.067f) * 0.05f;
            final ModelRenderer bipedLeftArm9 = this.bipedLeftArm;
            bipedLeftArm9.rotateAngleX -= MathHelper.sin(n3 * 0.067f) * 0.05f;
        }
        ModelBase.copyModelAngles(this.bipedHead, this.bipedHeadwear);
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
            if (false == true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ModelBiped(final float n) {
        this(n, 0.0f, 0x2E ^ 0x6E, 0x9D ^ 0xBD);
    }
    
    public ModelBiped(final float n, final float n2, final int textureWidth, final int textureHeight) {
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        (this.bipedHead = new ModelRenderer(this, "".length(), "".length())).addBox(-4.0f, -8.0f, -4.0f, 0xB5 ^ 0xBD, 0x2F ^ 0x27, 0xB4 ^ 0xBC, n);
        this.bipedHead.setRotationPoint(0.0f, 0.0f + n2, 0.0f);
        (this.bipedHeadwear = new ModelRenderer(this, 0x33 ^ 0x13, "".length())).addBox(-4.0f, -8.0f, -4.0f, 0x5D ^ 0x55, 0x93 ^ 0x9B, 0x11 ^ 0x19, n + 0.5f);
        this.bipedHeadwear.setRotationPoint(0.0f, 0.0f + n2, 0.0f);
        (this.bipedBody = new ModelRenderer(this, 0x95 ^ 0x85, 0xB ^ 0x1B)).addBox(-4.0f, 0.0f, -2.0f, 0x79 ^ 0x71, 0xCA ^ 0xC6, 0x0 ^ 0x4, n);
        this.bipedBody.setRotationPoint(0.0f, 0.0f + n2, 0.0f);
        (this.bipedRightArm = new ModelRenderer(this, 0x39 ^ 0x11, 0x44 ^ 0x54)).addBox(-3.0f, -2.0f, -2.0f, 0x26 ^ 0x22, 0xA3 ^ 0xAF, 0x63 ^ 0x67, n);
        this.bipedRightArm.setRotationPoint(-5.0f, 2.0f + n2, 0.0f);
        this.bipedLeftArm = new ModelRenderer(this, 0xA ^ 0x22, 0x65 ^ 0x75);
        this.bipedLeftArm.mirror = (" ".length() != 0);
        this.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 0x2F ^ 0x2B, 0x28 ^ 0x24, 0x3 ^ 0x7, n);
        this.bipedLeftArm.setRotationPoint(5.0f, 2.0f + n2, 0.0f);
        (this.bipedRightLeg = new ModelRenderer(this, "".length(), 0x52 ^ 0x42)).addBox(-2.0f, 0.0f, -2.0f, 0x0 ^ 0x4, 0x40 ^ 0x4C, 0x7D ^ 0x79, n);
        this.bipedRightLeg.setRotationPoint(-1.9f, 12.0f + n2, 0.0f);
        this.bipedLeftLeg = new ModelRenderer(this, "".length(), 0x72 ^ 0x62);
        this.bipedLeftLeg.mirror = (" ".length() != 0);
        this.bipedLeftLeg.addBox(-2.0f, 0.0f, -2.0f, 0x81 ^ 0x85, 0x6C ^ 0x60, 0xB5 ^ 0xB1, n);
        this.bipedLeftLeg.setRotationPoint(1.9f, 12.0f + n2, 0.0f);
    }
    
    public void setInvisible(final boolean showModel) {
        this.bipedHead.showModel = showModel;
        this.bipedHeadwear.showModel = showModel;
        this.bipedBody.showModel = showModel;
        this.bipedRightArm.showModel = showModel;
        this.bipedLeftArm.showModel = showModel;
        this.bipedRightLeg.showModel = showModel;
        this.bipedLeftLeg.showModel = showModel;
    }
    
    @Override
    public void setModelAttributes(final ModelBase modelAttributes) {
        super.setModelAttributes(modelAttributes);
        if (modelAttributes instanceof ModelBiped) {
            final ModelBiped modelBiped = (ModelBiped)modelAttributes;
            this.heldItemLeft = modelBiped.heldItemLeft;
            this.heldItemRight = modelBiped.heldItemRight;
            this.isSneak = modelBiped.isSneak;
            this.aimedBow = modelBiped.aimedBow;
        }
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        GlStateManager.pushMatrix();
        if (this.isChild) {
            final float n7 = 2.0f;
            GlStateManager.scale(1.5f / n7, 1.5f / n7, 1.5f / n7);
            GlStateManager.translate(0.0f, 16.0f * n6, 0.0f);
            this.bipedHead.render(n6);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0f / n7, 1.0f / n7, 1.0f / n7);
            GlStateManager.translate(0.0f, 24.0f * n6, 0.0f);
            this.bipedBody.render(n6);
            this.bipedRightArm.render(n6);
            this.bipedLeftArm.render(n6);
            this.bipedRightLeg.render(n6);
            this.bipedLeftLeg.render(n6);
            this.bipedHeadwear.render(n6);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            if (entity.isSneaking()) {
                GlStateManager.translate(0.0f, 0.2f, 0.0f);
            }
            this.bipedHead.render(n6);
            this.bipedBody.render(n6);
            this.bipedRightArm.render(n6);
            this.bipedLeftArm.render(n6);
            this.bipedRightLeg.render(n6);
            this.bipedLeftLeg.render(n6);
            this.bipedHeadwear.render(n6);
        }
        GlStateManager.popMatrix();
    }
    
    public ModelBiped() {
        this(0.0f);
    }
}
