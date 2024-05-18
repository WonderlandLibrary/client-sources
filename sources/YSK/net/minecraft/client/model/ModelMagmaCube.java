package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;

public class ModelMagmaCube extends ModelBase
{
    ModelRenderer[] segments;
    ModelRenderer core;
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.core.render(n6);
        int i = "".length();
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (i < this.segments.length) {
            this.segments[i].render(n6);
            ++i;
        }
    }
    
    public ModelMagmaCube() {
        this.segments = new ModelRenderer[0x3A ^ 0x32];
        int i = "".length();
        "".length();
        if (-1 >= 1) {
            throw null;
        }
        while (i < this.segments.length) {
            int length = "".length();
            int n;
            if ((n = i) == "  ".length()) {
                length = (0x3 ^ 0x1B);
                n = (0x5A ^ 0x50);
                "".length();
                if (3 == 1) {
                    throw null;
                }
            }
            else if (i == "   ".length()) {
                length = (0x42 ^ 0x5A);
                n = (0x25 ^ 0x36);
            }
            (this.segments[i] = new ModelRenderer(this, length, n)).addBox(-4.0f, (0xB6 ^ 0xA6) + i, -4.0f, 0xCA ^ 0xC2, " ".length(), 0xF ^ 0x7);
            ++i;
        }
        (this.core = new ModelRenderer(this, "".length(), 0x31 ^ 0x21)).addBox(-2.0f, 18.0f, -2.0f, 0x72 ^ 0x76, 0x0 ^ 0x4, 0x3C ^ 0x38);
    }
    
    @Override
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        final EntityMagmaCube entityMagmaCube = (EntityMagmaCube)entityLivingBase;
        float n4 = entityMagmaCube.prevSquishFactor + (entityMagmaCube.squishFactor - entityMagmaCube.prevSquishFactor) * n3;
        if (n4 < 0.0f) {
            n4 = 0.0f;
        }
        int i = "".length();
        "".length();
        if (4 == 3) {
            throw null;
        }
        while (i < this.segments.length) {
            this.segments[i].rotationPointY = -((0xF ^ 0xB) - i) * n4 * 1.7f;
            ++i;
        }
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
