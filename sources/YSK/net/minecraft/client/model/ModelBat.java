package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;

public class ModelBat extends ModelBase
{
    private ModelRenderer batOuterLeftWing;
    private ModelRenderer batBody;
    private ModelRenderer batLeftWing;
    private ModelRenderer batOuterRightWing;
    private ModelRenderer batRightWing;
    private ModelRenderer batHead;
    
    public ModelBat() {
        this.textureWidth = (0x67 ^ 0x27);
        this.textureHeight = (0x82 ^ 0xC2);
        (this.batHead = new ModelRenderer(this, "".length(), "".length())).addBox(-3.0f, -3.0f, -3.0f, 0x50 ^ 0x56, 0x6D ^ 0x6B, 0x61 ^ 0x67);
        final ModelRenderer modelRenderer = new ModelRenderer(this, 0xDB ^ 0xC3, "".length());
        modelRenderer.addBox(-4.0f, -6.0f, -2.0f, "   ".length(), 0xA5 ^ 0xA1, " ".length());
        this.batHead.addChild(modelRenderer);
        final ModelRenderer modelRenderer2 = new ModelRenderer(this, 0x2B ^ 0x33, "".length());
        modelRenderer2.mirror = (" ".length() != 0);
        modelRenderer2.addBox(1.0f, -6.0f, -2.0f, "   ".length(), 0xB9 ^ 0xBD, " ".length());
        this.batHead.addChild(modelRenderer2);
        (this.batBody = new ModelRenderer(this, "".length(), 0x75 ^ 0x65)).addBox(-3.0f, 4.0f, -3.0f, 0x7B ^ 0x7D, 0x3F ^ 0x33, 0x5B ^ 0x5D);
        this.batBody.setTextureOffset("".length(), 0xB5 ^ 0x97).addBox(-5.0f, 16.0f, 0.0f, 0x4F ^ 0x45, 0x3 ^ 0x5, " ".length());
        (this.batRightWing = new ModelRenderer(this, 0xD ^ 0x27, "".length())).addBox(-12.0f, 1.0f, 1.5f, 0x1A ^ 0x10, 0x26 ^ 0x36, " ".length());
        (this.batOuterRightWing = new ModelRenderer(this, 0x26 ^ 0x3E, 0x7E ^ 0x6E)).setRotationPoint(-12.0f, 1.0f, 1.5f);
        this.batOuterRightWing.addBox(-8.0f, 1.0f, 0.0f, 0x8B ^ 0x83, 0xCA ^ 0xC6, " ".length());
        this.batLeftWing = new ModelRenderer(this, 0x4C ^ 0x66, "".length());
        this.batLeftWing.mirror = (" ".length() != 0);
        this.batLeftWing.addBox(2.0f, 1.0f, 1.5f, 0xAA ^ 0xA0, 0x62 ^ 0x72, " ".length());
        this.batOuterLeftWing = new ModelRenderer(this, 0x69 ^ 0x71, 0x3E ^ 0x2E);
        this.batOuterLeftWing.mirror = (" ".length() != 0);
        this.batOuterLeftWing.setRotationPoint(12.0f, 1.0f, 1.5f);
        this.batOuterLeftWing.addBox(0.0f, 1.0f, 0.0f, 0x75 ^ 0x7D, 0xA1 ^ 0xAD, " ".length());
        this.batBody.addChild(this.batRightWing);
        this.batBody.addChild(this.batLeftWing);
        this.batRightWing.addChild(this.batOuterRightWing);
        this.batLeftWing.addChild(this.batOuterLeftWing);
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
            if (4 < 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.batHead.render(n6);
        this.batBody.render(n6);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        if (((EntityBat)entity).getIsBatHanging()) {
            this.batHead.rotateAngleX = n5 / 57.295776f;
            this.batHead.rotateAngleY = 3.1415927f - n4 / 57.295776f;
            this.batHead.rotateAngleZ = 3.1415927f;
            this.batHead.setRotationPoint(0.0f, -2.0f, 0.0f);
            this.batRightWing.setRotationPoint(-3.0f, 0.0f, 3.0f);
            this.batLeftWing.setRotationPoint(3.0f, 0.0f, 3.0f);
            this.batBody.rotateAngleX = 3.1415927f;
            this.batRightWing.rotateAngleX = -0.15707964f;
            this.batRightWing.rotateAngleY = -1.2566371f;
            this.batOuterRightWing.rotateAngleY = -1.7278761f;
            this.batLeftWing.rotateAngleX = this.batRightWing.rotateAngleX;
            this.batLeftWing.rotateAngleY = -this.batRightWing.rotateAngleY;
            this.batOuterLeftWing.rotateAngleY = -this.batOuterRightWing.rotateAngleY;
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else {
            this.batHead.rotateAngleX = n5 / 57.295776f;
            this.batHead.rotateAngleY = n4 / 57.295776f;
            this.batHead.rotateAngleZ = 0.0f;
            this.batHead.setRotationPoint(0.0f, 0.0f, 0.0f);
            this.batRightWing.setRotationPoint(0.0f, 0.0f, 0.0f);
            this.batLeftWing.setRotationPoint(0.0f, 0.0f, 0.0f);
            this.batBody.rotateAngleX = 0.7853982f + MathHelper.cos(n3 * 0.1f) * 0.15f;
            this.batBody.rotateAngleY = 0.0f;
            this.batRightWing.rotateAngleY = MathHelper.cos(n3 * 1.3f) * 3.1415927f * 0.25f;
            this.batLeftWing.rotateAngleY = -this.batRightWing.rotateAngleY;
            this.batOuterRightWing.rotateAngleY = this.batRightWing.rotateAngleY * 0.5f;
            this.batOuterLeftWing.rotateAngleY = -this.batRightWing.rotateAngleY * 0.5f;
        }
    }
}
