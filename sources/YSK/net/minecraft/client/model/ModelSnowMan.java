package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelSnowMan extends ModelBase
{
    public ModelRenderer rightHand;
    public ModelRenderer body;
    public ModelRenderer leftHand;
    public ModelRenderer bottomBody;
    public ModelRenderer head;
    
    public ModelSnowMan() {
        final float n = 4.0f;
        final float n2 = 0.0f;
        (this.head = new ModelRenderer(this, "".length(), "".length()).setTextureSize(0x17 ^ 0x57, 0xD ^ 0x4D)).addBox(-4.0f, -8.0f, -4.0f, 0x5F ^ 0x57, 0x6B ^ 0x63, 0xB8 ^ 0xB0, n2 - 0.5f);
        this.head.setRotationPoint(0.0f, 0.0f + n, 0.0f);
        (this.rightHand = new ModelRenderer(this, 0x53 ^ 0x73, "".length()).setTextureSize(0x86 ^ 0xC6, 0x5D ^ 0x1D)).addBox(-1.0f, 0.0f, -1.0f, 0x5F ^ 0x53, "  ".length(), "  ".length(), n2 - 0.5f);
        this.rightHand.setRotationPoint(0.0f, 0.0f + n + 9.0f - 7.0f, 0.0f);
        (this.leftHand = new ModelRenderer(this, 0x8E ^ 0xAE, "".length()).setTextureSize(0x72 ^ 0x32, 0x29 ^ 0x69)).addBox(-1.0f, 0.0f, -1.0f, 0x60 ^ 0x6C, "  ".length(), "  ".length(), n2 - 0.5f);
        this.leftHand.setRotationPoint(0.0f, 0.0f + n + 9.0f - 7.0f, 0.0f);
        (this.body = new ModelRenderer(this, "".length(), 0x37 ^ 0x27).setTextureSize(0xD5 ^ 0x95, 0xF2 ^ 0xB2)).addBox(-5.0f, -10.0f, -5.0f, 0x3E ^ 0x34, 0xC ^ 0x6, 0x40 ^ 0x4A, n2 - 0.5f);
        this.body.setRotationPoint(0.0f, 0.0f + n + 9.0f, 0.0f);
        (this.bottomBody = new ModelRenderer(this, "".length(), 0xAE ^ 0x8A).setTextureSize(0x4C ^ 0xC, 0xF6 ^ 0xB6)).addBox(-6.0f, -12.0f, -6.0f, 0x47 ^ 0x4B, 0x17 ^ 0x1B, 0x68 ^ 0x64, n2 - 0.5f);
        this.bottomBody.setRotationPoint(0.0f, 0.0f + n + 20.0f, 0.0f);
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
            if (1 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.body.render(n6);
        this.bottomBody.render(n6);
        this.head.render(n6);
        this.rightHand.render(n6);
        this.leftHand.render(n6);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        super.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.head.rotateAngleY = n4 / 57.295776f;
        this.head.rotateAngleX = n5 / 57.295776f;
        this.body.rotateAngleY = n4 / 57.295776f * 0.25f;
        final float sin = MathHelper.sin(this.body.rotateAngleY);
        final float cos = MathHelper.cos(this.body.rotateAngleY);
        this.rightHand.rotateAngleZ = 1.0f;
        this.leftHand.rotateAngleZ = -1.0f;
        this.rightHand.rotateAngleY = 0.0f + this.body.rotateAngleY;
        this.leftHand.rotateAngleY = 3.1415927f + this.body.rotateAngleY;
        this.rightHand.rotationPointX = cos * 5.0f;
        this.rightHand.rotationPointZ = -sin * 5.0f;
        this.leftHand.rotationPointX = -cos * 5.0f;
        this.leftHand.rotationPointZ = sin * 5.0f;
    }
}
