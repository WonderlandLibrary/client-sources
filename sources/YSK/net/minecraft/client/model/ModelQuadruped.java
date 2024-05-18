package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public class ModelQuadruped extends ModelBase
{
    public ModelRenderer head;
    public ModelRenderer leg3;
    protected float childYOffset;
    public ModelRenderer body;
    protected float childZOffset;
    public ModelRenderer leg1;
    public ModelRenderer leg4;
    public ModelRenderer leg2;
    
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        this.head.rotateAngleX = n5 / 57.295776f;
        this.head.rotateAngleY = n4 / 57.295776f;
        this.body.rotateAngleX = 1.5707964f;
        this.leg1.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.4f * n2;
        this.leg2.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.4f * n2;
        this.leg3.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.4f * n2;
        this.leg4.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.4f * n2;
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        if (this.isChild) {
            final float n7 = 2.0f;
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0f, this.childYOffset * n6, this.childZOffset * n6);
            this.head.render(n6);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0f / n7, 1.0f / n7, 1.0f / n7);
            GlStateManager.translate(0.0f, 24.0f * n6, 0.0f);
            this.body.render(n6);
            this.leg1.render(n6);
            this.leg2.render(n6);
            this.leg3.render(n6);
            this.leg4.render(n6);
            GlStateManager.popMatrix();
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else {
            this.head.render(n6);
            this.body.render(n6);
            this.leg1.render(n6);
            this.leg2.render(n6);
            this.leg3.render(n6);
            this.leg4.render(n6);
        }
    }
    
    public ModelQuadruped(final int n, final float n2) {
        this.head = new ModelRenderer(this, "".length(), "".length());
        this.childYOffset = 8.0f;
        this.childZOffset = 4.0f;
        this.head.addBox(-4.0f, -4.0f, -8.0f, 0x39 ^ 0x31, 0xC ^ 0x4, 0xA5 ^ 0xAD, n2);
        this.head.setRotationPoint(0.0f, (0x12 ^ 0x0) - n, -6.0f);
        (this.body = new ModelRenderer(this, 0x88 ^ 0x94, 0x3E ^ 0x36)).addBox(-5.0f, -10.0f, -7.0f, 0x24 ^ 0x2E, 0x20 ^ 0x30, 0x95 ^ 0x9D, n2);
        this.body.setRotationPoint(0.0f, (0xB5 ^ 0xA4) - n, 2.0f);
        (this.leg1 = new ModelRenderer(this, "".length(), 0x21 ^ 0x31)).addBox(-2.0f, 0.0f, -2.0f, 0x4B ^ 0x4F, n, 0x2D ^ 0x29, n2);
        this.leg1.setRotationPoint(-3.0f, (0x4B ^ 0x53) - n, 7.0f);
        (this.leg2 = new ModelRenderer(this, "".length(), 0x32 ^ 0x22)).addBox(-2.0f, 0.0f, -2.0f, 0x44 ^ 0x40, n, 0x1E ^ 0x1A, n2);
        this.leg2.setRotationPoint(3.0f, (0x30 ^ 0x28) - n, 7.0f);
        (this.leg3 = new ModelRenderer(this, "".length(), 0x45 ^ 0x55)).addBox(-2.0f, 0.0f, -2.0f, 0x8 ^ 0xC, n, 0x1F ^ 0x1B, n2);
        this.leg3.setRotationPoint(-3.0f, (0x7E ^ 0x66) - n, -5.0f);
        (this.leg4 = new ModelRenderer(this, "".length(), 0x5E ^ 0x4E)).addBox(-2.0f, 0.0f, -2.0f, 0x84 ^ 0x80, n, 0x3D ^ 0x39, n2);
        this.leg4.setRotationPoint(3.0f, (0xB5 ^ 0xAD) - n, -5.0f);
    }
}
