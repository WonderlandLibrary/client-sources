package net.minecraft.util;

public class Matrix4f extends org.lwjgl.util.vector.Matrix4f
{
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
            if (4 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public Matrix4f(final float[] array) {
        this.m00 = array["".length()];
        this.m01 = array[" ".length()];
        this.m02 = array["  ".length()];
        this.m03 = array["   ".length()];
        this.m10 = array[0x2C ^ 0x28];
        this.m11 = array[0xAB ^ 0xAE];
        this.m12 = array[0xB1 ^ 0xB7];
        this.m13 = array[0x90 ^ 0x97];
        this.m20 = array[0xCC ^ 0xC4];
        this.m21 = array[0xAD ^ 0xA4];
        this.m22 = array[0x5 ^ 0xF];
        this.m23 = array[0xB2 ^ 0xB9];
        this.m30 = array[0xB2 ^ 0xBE];
        this.m31 = array[0xAA ^ 0xA7];
        this.m32 = array[0x96 ^ 0x98];
        this.m33 = array[0x20 ^ 0x2F];
    }
    
    public Matrix4f() {
        final float n = 0.0f;
        this.m33 = n;
        this.m32 = n;
        this.m31 = n;
        this.m30 = n;
        this.m23 = n;
        this.m22 = n;
        this.m21 = n;
        this.m20 = n;
        this.m13 = n;
        this.m12 = n;
        this.m11 = n;
        this.m10 = n;
        this.m03 = n;
        this.m02 = n;
        this.m01 = n;
        this.m00 = n;
    }
}
