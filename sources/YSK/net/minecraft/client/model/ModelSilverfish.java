package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelSilverfish extends ModelBase
{
    private float[] field_78170_c;
    private ModelRenderer[] silverfishBodyParts;
    private static final int[][] silverfishBoxLength;
    private static final int[][] silverfishTexturePositions;
    private ModelRenderer[] silverfishWings;
    
    public ModelSilverfish() {
        this.silverfishBodyParts = new ModelRenderer[0xAE ^ 0xA9];
        this.field_78170_c = new float[0x2D ^ 0x2A];
        float n = -3.5f;
        int i = "".length();
        "".length();
        if (1 == 3) {
            throw null;
        }
        while (i < this.silverfishBodyParts.length) {
            (this.silverfishBodyParts[i] = new ModelRenderer(this, ModelSilverfish.silverfishTexturePositions[i]["".length()], ModelSilverfish.silverfishTexturePositions[i][" ".length()])).addBox(ModelSilverfish.silverfishBoxLength[i]["".length()] * -0.5f, 0.0f, ModelSilverfish.silverfishBoxLength[i]["  ".length()] * -0.5f, ModelSilverfish.silverfishBoxLength[i]["".length()], ModelSilverfish.silverfishBoxLength[i][" ".length()], ModelSilverfish.silverfishBoxLength[i]["  ".length()]);
            this.silverfishBodyParts[i].setRotationPoint(0.0f, (0x8F ^ 0x97) - ModelSilverfish.silverfishBoxLength[i][" ".length()], n);
            this.field_78170_c[i] = n;
            if (i < this.silverfishBodyParts.length - " ".length()) {
                n += (ModelSilverfish.silverfishBoxLength[i]["  ".length()] + ModelSilverfish.silverfishBoxLength[i + " ".length()]["  ".length()]) * 0.5f;
            }
            ++i;
        }
        this.silverfishWings = new ModelRenderer["   ".length()];
        (this.silverfishWings["".length()] = new ModelRenderer(this, 0xB1 ^ 0xA5, "".length())).addBox(-5.0f, 0.0f, ModelSilverfish.silverfishBoxLength["  ".length()]["  ".length()] * -0.5f, 0x98 ^ 0x92, 0x7 ^ 0xF, ModelSilverfish.silverfishBoxLength["  ".length()]["  ".length()]);
        this.silverfishWings["".length()].setRotationPoint(0.0f, 16.0f, this.field_78170_c["  ".length()]);
        (this.silverfishWings[" ".length()] = new ModelRenderer(this, 0x6C ^ 0x78, 0xB8 ^ 0xB3)).addBox(-3.0f, 0.0f, ModelSilverfish.silverfishBoxLength[0x97 ^ 0x93]["  ".length()] * -0.5f, 0x60 ^ 0x66, 0x8A ^ 0x8E, ModelSilverfish.silverfishBoxLength[0xF ^ 0xB]["  ".length()]);
        this.silverfishWings[" ".length()].setRotationPoint(0.0f, 20.0f, this.field_78170_c[0x62 ^ 0x66]);
        (this.silverfishWings["  ".length()] = new ModelRenderer(this, 0x81 ^ 0x95, 0x58 ^ 0x4A)).addBox(-3.0f, 0.0f, ModelSilverfish.silverfishBoxLength[0xAD ^ 0xA9]["  ".length()] * -0.5f, 0x15 ^ 0x13, 0x3A ^ 0x3F, ModelSilverfish.silverfishBoxLength[" ".length()]["  ".length()]);
        this.silverfishWings["  ".length()].setRotationPoint(0.0f, 19.0f, this.field_78170_c[" ".length()]);
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
            if (2 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < this.silverfishBodyParts.length) {
            this.silverfishBodyParts[i].render(n6);
            ++i;
        }
        int j = "".length();
        "".length();
        if (1 <= 0) {
            throw null;
        }
        while (j < this.silverfishWings.length) {
            this.silverfishWings[j].render(n6);
            ++j;
        }
    }
    
    static {
        final int[][] silverfishBoxLength2 = new int[0xE ^ 0x9][];
        final int length = "".length();
        final int[] array = new int["   ".length()];
        array["".length()] = "   ".length();
        array[" ".length()] = "  ".length();
        array["  ".length()] = "  ".length();
        silverfishBoxLength2[length] = array;
        final int length2 = " ".length();
        final int[] array2 = new int["   ".length()];
        array2["".length()] = (0x91 ^ 0x95);
        array2[" ".length()] = "   ".length();
        array2["  ".length()] = "  ".length();
        silverfishBoxLength2[length2] = array2;
        final int length3 = "  ".length();
        final int[] array3 = new int["   ".length()];
        array3["".length()] = (0x6D ^ 0x6B);
        array3[" ".length()] = (0x30 ^ 0x34);
        array3["  ".length()] = "   ".length();
        silverfishBoxLength2[length3] = array3;
        final int length4 = "   ".length();
        final int[] array4 = new int["   ".length()];
        array4["".length()] = "   ".length();
        array4[" ".length()] = "   ".length();
        array4["  ".length()] = "   ".length();
        silverfishBoxLength2[length4] = array4;
        final int n = 0x24 ^ 0x20;
        final int[] array5 = new int["   ".length()];
        array5["".length()] = "  ".length();
        array5[" ".length()] = "  ".length();
        array5["  ".length()] = "   ".length();
        silverfishBoxLength2[n] = array5;
        final int n2 = 0x32 ^ 0x37;
        final int[] array6 = new int["   ".length()];
        array6["".length()] = "  ".length();
        array6[" ".length()] = " ".length();
        array6["  ".length()] = "  ".length();
        silverfishBoxLength2[n2] = array6;
        final int n3 = 0xB2 ^ 0xB4;
        final int[] array7 = new int["   ".length()];
        array7["".length()] = " ".length();
        array7[" ".length()] = " ".length();
        array7["  ".length()] = "  ".length();
        silverfishBoxLength2[n3] = array7;
        silverfishBoxLength = silverfishBoxLength2;
        final int[][] silverfishTexturePositions2 = new int[0x9A ^ 0x9D][];
        silverfishTexturePositions2["".length()] = new int["  ".length()];
        final int length5 = " ".length();
        final int[] array8 = new int["  ".length()];
        array8[" ".length()] = (0x5F ^ 0x5B);
        silverfishTexturePositions2[length5] = array8;
        final int length6 = "  ".length();
        final int[] array9 = new int["  ".length()];
        array9[" ".length()] = (0x41 ^ 0x48);
        silverfishTexturePositions2[length6] = array9;
        final int length7 = "   ".length();
        final int[] array10 = new int["  ".length()];
        array10[" ".length()] = (0x39 ^ 0x29);
        silverfishTexturePositions2[length7] = array10;
        final int n4 = 0x74 ^ 0x70;
        final int[] array11 = new int["  ".length()];
        array11[" ".length()] = (0xBF ^ 0xA9);
        silverfishTexturePositions2[n4] = array11;
        final int n5 = 0xA0 ^ 0xA5;
        final int[] array12 = new int["  ".length()];
        array12["".length()] = (0xC9 ^ 0xC2);
        silverfishTexturePositions2[n5] = array12;
        final int n6 = 0xAC ^ 0xAA;
        final int[] array13 = new int["  ".length()];
        array13["".length()] = (0x7A ^ 0x77);
        array13[" ".length()] = (0x9C ^ 0x98);
        silverfishTexturePositions2[n6] = array13;
        silverfishTexturePositions = silverfishTexturePositions2;
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        int i = "".length();
        "".length();
        if (-1 >= 2) {
            throw null;
        }
        while (i < this.silverfishBodyParts.length) {
            this.silverfishBodyParts[i].rotateAngleY = MathHelper.cos(n3 * 0.9f + i * 0.15f * 3.1415927f) * 3.1415927f * 0.05f * (" ".length() + Math.abs(i - "  ".length()));
            this.silverfishBodyParts[i].rotationPointX = MathHelper.sin(n3 * 0.9f + i * 0.15f * 3.1415927f) * 3.1415927f * 0.2f * Math.abs(i - "  ".length());
            ++i;
        }
        this.silverfishWings["".length()].rotateAngleY = this.silverfishBodyParts["  ".length()].rotateAngleY;
        this.silverfishWings[" ".length()].rotateAngleY = this.silverfishBodyParts[0xC0 ^ 0xC4].rotateAngleY;
        this.silverfishWings[" ".length()].rotationPointX = this.silverfishBodyParts[0x70 ^ 0x74].rotationPointX;
        this.silverfishWings["  ".length()].rotateAngleY = this.silverfishBodyParts[" ".length()].rotateAngleY;
        this.silverfishWings["  ".length()].rotationPointX = this.silverfishBodyParts[" ".length()].rotationPointX;
    }
}
