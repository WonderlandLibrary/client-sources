package net.minecraft.client.model;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class ModelOcelot extends ModelBase
{
    ModelRenderer ocelotHead;
    ModelRenderer ocelotTail2;
    private static final String[] I;
    ModelRenderer ocelotBackRightLeg;
    ModelRenderer ocelotTail;
    ModelRenderer ocelotFrontRightLeg;
    ModelRenderer ocelotFrontLeftLeg;
    ModelRenderer ocelotBody;
    ModelRenderer ocelotBackLeftLeg;
    int field_78163_i;
    
    public ModelOcelot() {
        this.field_78163_i = " ".length();
        this.setTextureOffset(ModelOcelot.I["".length()], "".length(), "".length());
        this.setTextureOffset(ModelOcelot.I[" ".length()], "".length(), 0x64 ^ 0x7C);
        this.setTextureOffset(ModelOcelot.I["  ".length()], "".length(), 0x72 ^ 0x78);
        this.setTextureOffset(ModelOcelot.I["   ".length()], 0x16 ^ 0x10, 0xB4 ^ 0xBE);
        (this.ocelotHead = new ModelRenderer(this, ModelOcelot.I[0xC3 ^ 0xC7])).addBox(ModelOcelot.I[0x5F ^ 0x5A], -2.5f, -2.0f, -3.0f, 0x27 ^ 0x22, 0xB ^ 0xF, 0x7C ^ 0x79);
        this.ocelotHead.addBox(ModelOcelot.I[0x83 ^ 0x85], -1.5f, 0.0f, -4.0f, "   ".length(), "  ".length(), "  ".length());
        this.ocelotHead.addBox(ModelOcelot.I[0x25 ^ 0x22], -2.0f, -3.0f, 0.0f, " ".length(), " ".length(), "  ".length());
        this.ocelotHead.addBox(ModelOcelot.I[0x88 ^ 0x80], 1.0f, -3.0f, 0.0f, " ".length(), " ".length(), "  ".length());
        this.ocelotHead.setRotationPoint(0.0f, 15.0f, -9.0f);
        (this.ocelotBody = new ModelRenderer(this, 0x6E ^ 0x7A, "".length())).addBox(-2.0f, 3.0f, -8.0f, 0x78 ^ 0x7C, 0xAA ^ 0xBA, 0x7 ^ 0x1, 0.0f);
        this.ocelotBody.setRotationPoint(0.0f, 12.0f, -10.0f);
        (this.ocelotTail = new ModelRenderer(this, "".length(), 0x7D ^ 0x72)).addBox(-0.5f, 0.0f, 0.0f, " ".length(), 0xBD ^ 0xB5, " ".length());
        this.ocelotTail.rotateAngleX = 0.9f;
        this.ocelotTail.setRotationPoint(0.0f, 15.0f, 8.0f);
        (this.ocelotTail2 = new ModelRenderer(this, 0xBF ^ 0xBB, 0x5F ^ 0x50)).addBox(-0.5f, 0.0f, 0.0f, " ".length(), 0x66 ^ 0x6E, " ".length());
        this.ocelotTail2.setRotationPoint(0.0f, 20.0f, 14.0f);
        (this.ocelotBackLeftLeg = new ModelRenderer(this, 0x24 ^ 0x2C, 0x4A ^ 0x47)).addBox(-1.0f, 0.0f, 1.0f, "  ".length(), 0x5F ^ 0x59, "  ".length());
        this.ocelotBackLeftLeg.setRotationPoint(1.1f, 18.0f, 5.0f);
        (this.ocelotBackRightLeg = new ModelRenderer(this, 0x81 ^ 0x89, 0x99 ^ 0x94)).addBox(-1.0f, 0.0f, 1.0f, "  ".length(), 0xBC ^ 0xBA, "  ".length());
        this.ocelotBackRightLeg.setRotationPoint(-1.1f, 18.0f, 5.0f);
        (this.ocelotFrontLeftLeg = new ModelRenderer(this, 0x62 ^ 0x4A, "".length())).addBox(-1.0f, 0.0f, 0.0f, "  ".length(), 0x15 ^ 0x1F, "  ".length());
        this.ocelotFrontLeftLeg.setRotationPoint(1.2f, 13.8f, -5.0f);
        (this.ocelotFrontRightLeg = new ModelRenderer(this, 0x7C ^ 0x54, "".length())).addBox(-1.0f, 0.0f, 0.0f, "  ".length(), 0x55 ^ 0x5F, "  ".length());
        this.ocelotFrontRightLeg.setRotationPoint(-1.2f, 13.8f, -5.0f);
    }
    
    @Override
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        final EntityOcelot entityOcelot = (EntityOcelot)entityLivingBase;
        this.ocelotBody.rotationPointY = 12.0f;
        this.ocelotBody.rotationPointZ = -10.0f;
        this.ocelotHead.rotationPointY = 15.0f;
        this.ocelotHead.rotationPointZ = -9.0f;
        this.ocelotTail.rotationPointY = 15.0f;
        this.ocelotTail.rotationPointZ = 8.0f;
        this.ocelotTail2.rotationPointY = 20.0f;
        this.ocelotTail2.rotationPointZ = 14.0f;
        final ModelRenderer ocelotFrontLeftLeg = this.ocelotFrontLeftLeg;
        final ModelRenderer ocelotFrontRightLeg = this.ocelotFrontRightLeg;
        final float n4 = 13.8f;
        ocelotFrontRightLeg.rotationPointY = n4;
        ocelotFrontLeftLeg.rotationPointY = n4;
        final ModelRenderer ocelotFrontLeftLeg2 = this.ocelotFrontLeftLeg;
        final ModelRenderer ocelotFrontRightLeg2 = this.ocelotFrontRightLeg;
        final float n5 = -5.0f;
        ocelotFrontRightLeg2.rotationPointZ = n5;
        ocelotFrontLeftLeg2.rotationPointZ = n5;
        final ModelRenderer ocelotBackLeftLeg = this.ocelotBackLeftLeg;
        final ModelRenderer ocelotBackRightLeg = this.ocelotBackRightLeg;
        final float n6 = 18.0f;
        ocelotBackRightLeg.rotationPointY = n6;
        ocelotBackLeftLeg.rotationPointY = n6;
        final ModelRenderer ocelotBackLeftLeg2 = this.ocelotBackLeftLeg;
        final ModelRenderer ocelotBackRightLeg2 = this.ocelotBackRightLeg;
        final float n7 = 5.0f;
        ocelotBackRightLeg2.rotationPointZ = n7;
        ocelotBackLeftLeg2.rotationPointZ = n7;
        this.ocelotTail.rotateAngleX = 0.9f;
        if (entityOcelot.isSneaking()) {
            final ModelRenderer ocelotBody = this.ocelotBody;
            ++ocelotBody.rotationPointY;
            final ModelRenderer ocelotHead = this.ocelotHead;
            ocelotHead.rotationPointY += 2.0f;
            final ModelRenderer ocelotTail = this.ocelotTail;
            ++ocelotTail.rotationPointY;
            final ModelRenderer ocelotTail2 = this.ocelotTail2;
            ocelotTail2.rotationPointY -= 4.0f;
            final ModelRenderer ocelotTail3 = this.ocelotTail2;
            ocelotTail3.rotationPointZ += 2.0f;
            this.ocelotTail.rotateAngleX = 1.5707964f;
            this.ocelotTail2.rotateAngleX = 1.5707964f;
            this.field_78163_i = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (entityOcelot.isSprinting()) {
            this.ocelotTail2.rotationPointY = this.ocelotTail.rotationPointY;
            final ModelRenderer ocelotTail4 = this.ocelotTail2;
            ocelotTail4.rotationPointZ += 2.0f;
            this.ocelotTail.rotateAngleX = 1.5707964f;
            this.ocelotTail2.rotateAngleX = 1.5707964f;
            this.field_78163_i = "  ".length();
            "".length();
            if (3 == 4) {
                throw null;
            }
        }
        else if (entityOcelot.isSitting()) {
            this.ocelotBody.rotateAngleX = 0.7853982f;
            final ModelRenderer ocelotBody2 = this.ocelotBody;
            ocelotBody2.rotationPointY -= 4.0f;
            final ModelRenderer ocelotBody3 = this.ocelotBody;
            ocelotBody3.rotationPointZ += 5.0f;
            final ModelRenderer ocelotHead2 = this.ocelotHead;
            ocelotHead2.rotationPointY -= 3.3f;
            final ModelRenderer ocelotHead3 = this.ocelotHead;
            ++ocelotHead3.rotationPointZ;
            final ModelRenderer ocelotTail5 = this.ocelotTail;
            ocelotTail5.rotationPointY += 8.0f;
            final ModelRenderer ocelotTail6 = this.ocelotTail;
            ocelotTail6.rotationPointZ -= 2.0f;
            final ModelRenderer ocelotTail7 = this.ocelotTail2;
            ocelotTail7.rotationPointY += 2.0f;
            final ModelRenderer ocelotTail8 = this.ocelotTail2;
            ocelotTail8.rotationPointZ -= 0.8f;
            this.ocelotTail.rotateAngleX = 1.7278761f;
            this.ocelotTail2.rotateAngleX = 2.670354f;
            final ModelRenderer ocelotFrontLeftLeg3 = this.ocelotFrontLeftLeg;
            final ModelRenderer ocelotFrontRightLeg3 = this.ocelotFrontRightLeg;
            final float n8 = -0.15707964f;
            ocelotFrontRightLeg3.rotateAngleX = n8;
            ocelotFrontLeftLeg3.rotateAngleX = n8;
            final ModelRenderer ocelotFrontLeftLeg4 = this.ocelotFrontLeftLeg;
            final ModelRenderer ocelotFrontRightLeg4 = this.ocelotFrontRightLeg;
            final float n9 = 15.8f;
            ocelotFrontRightLeg4.rotationPointY = n9;
            ocelotFrontLeftLeg4.rotationPointY = n9;
            final ModelRenderer ocelotFrontLeftLeg5 = this.ocelotFrontLeftLeg;
            final ModelRenderer ocelotFrontRightLeg5 = this.ocelotFrontRightLeg;
            final float n10 = -7.0f;
            ocelotFrontRightLeg5.rotationPointZ = n10;
            ocelotFrontLeftLeg5.rotationPointZ = n10;
            final ModelRenderer ocelotBackLeftLeg3 = this.ocelotBackLeftLeg;
            final ModelRenderer ocelotBackRightLeg3 = this.ocelotBackRightLeg;
            final float n11 = -1.5707964f;
            ocelotBackRightLeg3.rotateAngleX = n11;
            ocelotBackLeftLeg3.rotateAngleX = n11;
            final ModelRenderer ocelotBackLeftLeg4 = this.ocelotBackLeftLeg;
            final ModelRenderer ocelotBackRightLeg4 = this.ocelotBackRightLeg;
            final float n12 = 21.0f;
            ocelotBackRightLeg4.rotationPointY = n12;
            ocelotBackLeftLeg4.rotationPointY = n12;
            final ModelRenderer ocelotBackLeftLeg5 = this.ocelotBackLeftLeg;
            final ModelRenderer ocelotBackRightLeg5 = this.ocelotBackRightLeg;
            final float n13 = 1.0f;
            ocelotBackRightLeg5.rotationPointZ = n13;
            ocelotBackLeftLeg5.rotationPointZ = n13;
            this.field_78163_i = "   ".length();
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else {
            this.field_78163_i = " ".length();
        }
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        if (this.isChild) {
            final float n7 = 2.0f;
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.5f / n7, 1.5f / n7, 1.5f / n7);
            GlStateManager.translate(0.0f, 10.0f * n6, 4.0f * n6);
            this.ocelotHead.render(n6);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0f / n7, 1.0f / n7, 1.0f / n7);
            GlStateManager.translate(0.0f, 24.0f * n6, 0.0f);
            this.ocelotBody.render(n6);
            this.ocelotBackLeftLeg.render(n6);
            this.ocelotBackRightLeg.render(n6);
            this.ocelotFrontLeftLeg.render(n6);
            this.ocelotFrontRightLeg.render(n6);
            this.ocelotTail.render(n6);
            this.ocelotTail2.render(n6);
            GlStateManager.popMatrix();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            this.ocelotHead.render(n6);
            this.ocelotBody.render(n6);
            this.ocelotTail.render(n6);
            this.ocelotTail2.render(n6);
            this.ocelotBackLeftLeg.render(n6);
            this.ocelotBackRightLeg.render(n6);
            this.ocelotFrontLeftLeg.render(n6);
            this.ocelotFrontRightLeg.render(n6);
        }
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        this.ocelotHead.rotateAngleX = n5 / 57.295776f;
        this.ocelotHead.rotateAngleY = n4 / 57.295776f;
        if (this.field_78163_i != "   ".length()) {
            this.ocelotBody.rotateAngleX = 1.5707964f;
            if (this.field_78163_i == "  ".length()) {
                this.ocelotBackLeftLeg.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.0f * n2;
                this.ocelotBackRightLeg.rotateAngleX = MathHelper.cos(n * 0.6662f + 0.3f) * 1.0f * n2;
                this.ocelotFrontLeftLeg.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f + 0.3f) * 1.0f * n2;
                this.ocelotFrontRightLeg.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.0f * n2;
                this.ocelotTail2.rotateAngleX = 1.7278761f + 0.31415927f * MathHelper.cos(n) * n2;
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            else {
                this.ocelotBackLeftLeg.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.0f * n2;
                this.ocelotBackRightLeg.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.0f * n2;
                this.ocelotFrontLeftLeg.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.0f * n2;
                this.ocelotFrontRightLeg.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.0f * n2;
                if (this.field_78163_i == " ".length()) {
                    this.ocelotTail2.rotateAngleX = 1.7278761f + 0.7853982f * MathHelper.cos(n) * n2;
                    "".length();
                    if (3 <= 2) {
                        throw null;
                    }
                }
                else {
                    this.ocelotTail2.rotateAngleX = 1.7278761f + 0.47123894f * MathHelper.cos(n) * n2;
                }
            }
        }
    }
    
    private static void I() {
        (I = new String[0x60 ^ 0x69])["".length()] = I("\u0012\r\u0000\u001eg\u0017\t\b\u0014", "zhazI");
        ModelOcelot.I[" ".length()] = I("\u0004)\u0002\u0006C\u0002#\u0010\u0007", "lLcbm");
        ModelOcelot.I["  ".length()] = I("\u001c\u0011\u00025a\u0011\u0015\u0011`", "ttcQO");
        ModelOcelot.I["   ".length()] = I("\u0002\"%\bi\u000f&6^", "jGDlG");
        ModelOcelot.I[0x31 ^ 0x35] = I("\u0007\u000e\u0007)", "okfMH");
        ModelOcelot.I[0x2C ^ 0x29] = I("\u000f\u001b.;", "bzGUv");
        ModelOcelot.I[0x21 ^ 0x27] = I("\u0000\u0005\u00120", "njaUY");
        ModelOcelot.I[0x6B ^ 0x6C] = I("\t\u0003\bi", "lbzXh");
        ModelOcelot.I[0x44 ^ 0x4C] = I("\u0001\r5P", "dlGbw");
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
}
