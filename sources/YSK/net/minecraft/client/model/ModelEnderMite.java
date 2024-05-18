package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelEnderMite extends ModelBase
{
    private static final int field_178715_c;
    private static final int[][] field_178714_b;
    private static final int[][] field_178716_a;
    private final ModelRenderer[] field_178713_d;
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        int i = "".length();
        "".length();
        if (4 < -1) {
            throw null;
        }
        while (i < this.field_178713_d.length) {
            this.field_178713_d[i].rotateAngleY = MathHelper.cos(n3 * 0.9f + i * 0.15f * 3.1415927f) * 3.1415927f * 0.01f * (" ".length() + Math.abs(i - "  ".length()));
            this.field_178713_d[i].rotationPointX = MathHelper.sin(n3 * 0.9f + i * 0.15f * 3.1415927f) * 3.1415927f * 0.1f * Math.abs(i - "  ".length());
            ++i;
        }
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i < this.field_178713_d.length) {
            this.field_178713_d[i].render(n6);
            ++i;
        }
    }
    
    public ModelEnderMite() {
        this.field_178713_d = new ModelRenderer[ModelEnderMite.field_178715_c];
        float n = -3.5f;
        int i = "".length();
        "".length();
        if (2 <= 0) {
            throw null;
        }
        while (i < this.field_178713_d.length) {
            (this.field_178713_d[i] = new ModelRenderer(this, ModelEnderMite.field_178714_b[i]["".length()], ModelEnderMite.field_178714_b[i][" ".length()])).addBox(ModelEnderMite.field_178716_a[i]["".length()] * -0.5f, 0.0f, ModelEnderMite.field_178716_a[i]["  ".length()] * -0.5f, ModelEnderMite.field_178716_a[i]["".length()], ModelEnderMite.field_178716_a[i][" ".length()], ModelEnderMite.field_178716_a[i]["  ".length()]);
            this.field_178713_d[i].setRotationPoint(0.0f, (0x2F ^ 0x37) - ModelEnderMite.field_178716_a[i][" ".length()], n);
            if (i < this.field_178713_d.length - " ".length()) {
                n += (ModelEnderMite.field_178716_a[i]["  ".length()] + ModelEnderMite.field_178716_a[i + " ".length()]["  ".length()]) * 0.5f;
            }
            ++i;
        }
    }
    
    static {
        final int[][] field_178716_a2 = new int[0x4D ^ 0x49][];
        final int length = "".length();
        final int[] array = new int["   ".length()];
        array["".length()] = (0xA ^ 0xE);
        array[" ".length()] = "   ".length();
        array["  ".length()] = "  ".length();
        field_178716_a2[length] = array;
        final int length2 = " ".length();
        final int[] array2 = new int["   ".length()];
        array2["".length()] = (0x93 ^ 0x95);
        array2[" ".length()] = (0x65 ^ 0x61);
        array2["  ".length()] = (0x1C ^ 0x19);
        field_178716_a2[length2] = array2;
        final int length3 = "  ".length();
        final int[] array3 = new int["   ".length()];
        array3["".length()] = "   ".length();
        array3[" ".length()] = "   ".length();
        array3["  ".length()] = " ".length();
        field_178716_a2[length3] = array3;
        final int length4 = "   ".length();
        final int[] array4 = new int["   ".length()];
        array4["".length()] = " ".length();
        array4[" ".length()] = "  ".length();
        array4["  ".length()] = " ".length();
        field_178716_a2[length4] = array4;
        field_178716_a = field_178716_a2;
        final int[][] field_178714_b2 = new int[0x46 ^ 0x42][];
        field_178714_b2["".length()] = new int["  ".length()];
        final int length5 = " ".length();
        final int[] array5 = new int["  ".length()];
        array5[" ".length()] = (0x11 ^ 0x14);
        field_178714_b2[length5] = array5;
        final int length6 = "  ".length();
        final int[] array6 = new int["  ".length()];
        array6[" ".length()] = (0x62 ^ 0x6C);
        field_178714_b2[length6] = array6;
        final int length7 = "   ".length();
        final int[] array7 = new int["  ".length()];
        array7[" ".length()] = (0x57 ^ 0x45);
        field_178714_b2[length7] = array7;
        field_178714_b = field_178714_b2;
        field_178715_c = ModelEnderMite.field_178716_a.length;
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
            if (2 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
