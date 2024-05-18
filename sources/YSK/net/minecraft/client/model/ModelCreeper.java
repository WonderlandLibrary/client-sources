package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelCreeper extends ModelBase
{
    public ModelRenderer leg1;
    public ModelRenderer leg3;
    public ModelRenderer leg4;
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer creeperArmor;
    public ModelRenderer leg2;
    
    public ModelCreeper() {
        this(0.0f);
    }
    
    public ModelCreeper(final float n) {
        final int n2 = 0xA ^ 0xC;
        (this.head = new ModelRenderer(this, "".length(), "".length())).addBox(-4.0f, -8.0f, -4.0f, 0xC8 ^ 0xC0, 0x44 ^ 0x4C, 0x18 ^ 0x10, n);
        this.head.setRotationPoint(0.0f, n2, 0.0f);
        (this.creeperArmor = new ModelRenderer(this, 0x7E ^ 0x5E, "".length())).addBox(-4.0f, -8.0f, -4.0f, 0x85 ^ 0x8D, 0xB9 ^ 0xB1, 0x94 ^ 0x9C, n + 0.5f);
        this.creeperArmor.setRotationPoint(0.0f, n2, 0.0f);
        (this.body = new ModelRenderer(this, 0x20 ^ 0x30, 0xA5 ^ 0xB5)).addBox(-4.0f, 0.0f, -2.0f, 0x90 ^ 0x98, 0x7A ^ 0x76, 0x60 ^ 0x64, n);
        this.body.setRotationPoint(0.0f, n2, 0.0f);
        (this.leg1 = new ModelRenderer(this, "".length(), 0x5B ^ 0x4B)).addBox(-2.0f, 0.0f, -2.0f, 0x23 ^ 0x27, 0x7C ^ 0x7A, 0x80 ^ 0x84, n);
        this.leg1.setRotationPoint(-2.0f, (0x4F ^ 0x43) + n2, 4.0f);
        (this.leg2 = new ModelRenderer(this, "".length(), 0xA3 ^ 0xB3)).addBox(-2.0f, 0.0f, -2.0f, 0x5C ^ 0x58, 0xB1 ^ 0xB7, 0x8 ^ 0xC, n);
        this.leg2.setRotationPoint(2.0f, (0x1D ^ 0x11) + n2, 4.0f);
        (this.leg3 = new ModelRenderer(this, "".length(), 0xA4 ^ 0xB4)).addBox(-2.0f, 0.0f, -2.0f, 0x53 ^ 0x57, 0x73 ^ 0x75, 0xAE ^ 0xAA, n);
        this.leg3.setRotationPoint(-2.0f, (0x65 ^ 0x69) + n2, -4.0f);
        (this.leg4 = new ModelRenderer(this, "".length(), 0x38 ^ 0x28)).addBox(-2.0f, 0.0f, -2.0f, 0x43 ^ 0x47, 0xBC ^ 0xBA, 0x73 ^ 0x77, n);
        this.leg4.setRotationPoint(2.0f, (0x2C ^ 0x20) + n2, -4.0f);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        this.head.rotateAngleY = n4 / 57.295776f;
        this.head.rotateAngleX = n5 / 57.295776f;
        this.leg1.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.4f * n2;
        this.leg2.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.4f * n2;
        this.leg3.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.4f * n2;
        this.leg4.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.4f * n2;
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.head.render(n6);
        this.body.render(n6);
        this.leg1.render(n6);
        this.leg2.render(n6);
        this.leg3.render(n6);
        this.leg4.render(n6);
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
}
