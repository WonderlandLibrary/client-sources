package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelBlaze extends ModelBase
{
    private ModelRenderer blazeHead;
    private ModelRenderer[] blazeSticks;
    
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.blazeHead.render(n6);
        int i = "".length();
        "".length();
        if (3 == 4) {
            throw null;
        }
        while (i < this.blazeSticks.length) {
            this.blazeSticks[i].render(n6);
            ++i;
        }
    }
    
    public ModelBlaze() {
        this.blazeSticks = new ModelRenderer[0x62 ^ 0x6E];
        int i = "".length();
        "".length();
        if (1 >= 2) {
            throw null;
        }
        while (i < this.blazeSticks.length) {
            (this.blazeSticks[i] = new ModelRenderer(this, "".length(), 0x78 ^ 0x68)).addBox(0.0f, 0.0f, 0.0f, "  ".length(), 0x99 ^ 0x91, "  ".length());
            ++i;
        }
        (this.blazeHead = new ModelRenderer(this, "".length(), "".length())).addBox(-4.0f, -4.0f, -4.0f, 0xB9 ^ 0xB1, 0x79 ^ 0x71, 0x41 ^ 0x49);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        float n7 = n3 * 3.1415927f * -0.1f;
        int i = "".length();
        "".length();
        if (4 <= 2) {
            throw null;
        }
        while (i < (0x7D ^ 0x79)) {
            this.blazeSticks[i].rotationPointY = -2.0f + MathHelper.cos((i * "  ".length() + n3) * 0.25f);
            this.blazeSticks[i].rotationPointX = MathHelper.cos(n7) * 9.0f;
            this.blazeSticks[i].rotationPointZ = MathHelper.sin(n7) * 9.0f;
            ++n7;
            ++i;
        }
        float n8 = 0.7853982f + n3 * 3.1415927f * 0.03f;
        int j = 0x23 ^ 0x27;
        "".length();
        if (-1 == 4) {
            throw null;
        }
        while (j < (0x6D ^ 0x65)) {
            this.blazeSticks[j].rotationPointY = 2.0f + MathHelper.cos((j * "  ".length() + n3) * 0.25f);
            this.blazeSticks[j].rotationPointX = MathHelper.cos(n8) * 7.0f;
            this.blazeSticks[j].rotationPointZ = MathHelper.sin(n8) * 7.0f;
            ++n8;
            ++j;
        }
        float n9 = 0.47123894f + n3 * 3.1415927f * -0.05f;
        int k = 0x19 ^ 0x11;
        "".length();
        if (3 < 0) {
            throw null;
        }
        while (k < (0x43 ^ 0x4F)) {
            this.blazeSticks[k].rotationPointY = 11.0f + MathHelper.cos((k * 1.5f + n3) * 0.5f);
            this.blazeSticks[k].rotationPointX = MathHelper.cos(n9) * 5.0f;
            this.blazeSticks[k].rotationPointZ = MathHelper.sin(n9) * 5.0f;
            ++n9;
            ++k;
        }
        this.blazeHead.rotateAngleY = n4 / 57.295776f;
        this.blazeHead.rotateAngleX = n5 / 57.295776f;
    }
}
