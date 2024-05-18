package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelBook extends ModelBase
{
    public ModelRenderer coverLeft;
    public ModelRenderer coverRight;
    public ModelRenderer bookSpine;
    public ModelRenderer flippingPageLeft;
    public ModelRenderer pagesLeft;
    public ModelRenderer pagesRight;
    public ModelRenderer flippingPageRight;
    
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        final float rotateAngleY = (MathHelper.sin(n * 0.02f) * 0.1f + 1.25f) * n4;
        this.coverRight.rotateAngleY = 3.1415927f + rotateAngleY;
        this.coverLeft.rotateAngleY = -rotateAngleY;
        this.pagesRight.rotateAngleY = rotateAngleY;
        this.pagesLeft.rotateAngleY = -rotateAngleY;
        this.flippingPageRight.rotateAngleY = rotateAngleY - rotateAngleY * 2.0f * n2;
        this.flippingPageLeft.rotateAngleY = rotateAngleY - rotateAngleY * 2.0f * n3;
        this.pagesRight.rotationPointX = MathHelper.sin(rotateAngleY);
        this.pagesLeft.rotationPointX = MathHelper.sin(rotateAngleY);
        this.flippingPageRight.rotationPointX = MathHelper.sin(rotateAngleY);
        this.flippingPageLeft.rotationPointX = MathHelper.sin(rotateAngleY);
    }
    
    public ModelBook() {
        this.coverRight = new ModelRenderer(this).setTextureOffset("".length(), "".length()).addBox(-6.0f, -5.0f, 0.0f, 0xAB ^ 0xAD, 0xCC ^ 0xC6, "".length());
        this.coverLeft = new ModelRenderer(this).setTextureOffset(0x77 ^ 0x67, "".length()).addBox(0.0f, -5.0f, 0.0f, 0x4C ^ 0x4A, 0x9F ^ 0x95, "".length());
        this.pagesRight = new ModelRenderer(this).setTextureOffset("".length(), 0x93 ^ 0x99).addBox(0.0f, -4.0f, -0.99f, 0x2 ^ 0x7, 0x17 ^ 0x1F, " ".length());
        this.pagesLeft = new ModelRenderer(this).setTextureOffset(0x71 ^ 0x7D, 0x6 ^ 0xC).addBox(0.0f, -4.0f, -0.01f, 0x17 ^ 0x12, 0x8A ^ 0x82, " ".length());
        this.flippingPageRight = new ModelRenderer(this).setTextureOffset(0xA3 ^ 0xBB, 0x5E ^ 0x54).addBox(0.0f, -4.0f, 0.0f, 0x9A ^ 0x9F, 0x1 ^ 0x9, "".length());
        this.flippingPageLeft = new ModelRenderer(this).setTextureOffset(0x28 ^ 0x30, 0x79 ^ 0x73).addBox(0.0f, -4.0f, 0.0f, 0x4 ^ 0x1, 0xBD ^ 0xB5, "".length());
        this.bookSpine = new ModelRenderer(this).setTextureOffset(0xAB ^ 0xA7, "".length()).addBox(-1.0f, -5.0f, 0.0f, "  ".length(), 0x77 ^ 0x7D, "".length());
        this.coverRight.setRotationPoint(0.0f, 0.0f, -1.0f);
        this.coverLeft.setRotationPoint(0.0f, 0.0f, 1.0f);
        this.bookSpine.rotateAngleY = 1.5707964f;
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.coverRight.render(n6);
        this.coverLeft.render(n6);
        this.bookSpine.render(n6);
        this.pagesRight.render(n6);
        this.pagesLeft.render(n6);
        this.flippingPageRight.render(n6);
        this.flippingPageLeft.render(n6);
    }
}
