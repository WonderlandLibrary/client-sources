package net.minecraft.util;

public class MouseFilter
{
    private float field_76335_c;
    private float field_76336_a;
    private float field_76334_b;
    
    public void reset() {
        this.field_76336_a = 0.0f;
        this.field_76334_b = 0.0f;
        this.field_76335_c = 0.0f;
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
            if (2 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public float smooth(float field_76335_c, final float n) {
        this.field_76336_a += field_76335_c;
        field_76335_c = (this.field_76336_a - this.field_76334_b) * n;
        this.field_76335_c += (field_76335_c - this.field_76335_c) * 0.5f;
        if ((field_76335_c > 0.0f && field_76335_c > this.field_76335_c) || (field_76335_c < 0.0f && field_76335_c < this.field_76335_c)) {
            field_76335_c = this.field_76335_c;
        }
        this.field_76334_b += field_76335_c;
        return field_76335_c;
    }
}
