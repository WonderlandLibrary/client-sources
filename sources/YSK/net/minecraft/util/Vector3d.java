package net.minecraft.util;

public class Vector3d
{
    public double field_181061_c;
    public double field_181060_b;
    public double field_181059_a;
    
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
            if (3 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public Vector3d() {
        final double field_181059_a = 0.0;
        this.field_181061_c = field_181059_a;
        this.field_181060_b = field_181059_a;
        this.field_181059_a = field_181059_a;
    }
}
