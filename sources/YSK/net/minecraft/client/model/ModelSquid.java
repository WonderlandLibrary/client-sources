package net.minecraft.client.model;

import net.minecraft.entity.*;

public class ModelSquid extends ModelBase
{
    ModelRenderer[] squidTentacles;
    ModelRenderer squidBody;
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float rotateAngleX, final float n3, final float n4, final float n5, final Entity entity) {
        final ModelRenderer[] squidTentacles;
        final int length = (squidTentacles = this.squidTentacles).length;
        int i = "".length();
        "".length();
        if (2 < 0) {
            throw null;
        }
        while (i < length) {
            squidTentacles[i].rotateAngleX = rotateAngleX;
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ModelSquid() {
        this.squidTentacles = new ModelRenderer[0x7 ^ 0xF];
        final int n = -(0xD7 ^ 0xC7);
        (this.squidBody = new ModelRenderer(this, "".length(), "".length())).addBox(-6.0f, -8.0f, -6.0f, 0xC ^ 0x0, 0x79 ^ 0x69, 0x86 ^ 0x8A);
        final ModelRenderer squidBody = this.squidBody;
        squidBody.rotationPointY += (0xBC ^ 0xA4) + n;
        int i = "".length();
        "".length();
        if (-1 >= 1) {
            throw null;
        }
        while (i < this.squidTentacles.length) {
            this.squidTentacles[i] = new ModelRenderer(this, 0x3C ^ 0xC, "".length());
            final double n2 = i * 3.141592653589793 * 2.0 / this.squidTentacles.length;
            final float rotationPointX = (float)Math.cos(n2) * 5.0f;
            final float rotationPointZ = (float)Math.sin(n2) * 5.0f;
            this.squidTentacles[i].addBox(-1.0f, 0.0f, -1.0f, "  ".length(), 0xA0 ^ 0xB2, "  ".length());
            this.squidTentacles[i].rotationPointX = rotationPointX;
            this.squidTentacles[i].rotationPointZ = rotationPointZ;
            this.squidTentacles[i].rotationPointY = (0xD8 ^ 0xC7) + n;
            this.squidTentacles[i].rotateAngleY = (float)(i * 3.141592653589793 * -2.0 / this.squidTentacles.length + 1.5707963267948966);
            ++i;
        }
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.squidBody.render(n6);
        int i = "".length();
        "".length();
        if (4 <= 0) {
            throw null;
        }
        while (i < this.squidTentacles.length) {
            this.squidTentacles[i].render(n6);
            ++i;
        }
    }
}
