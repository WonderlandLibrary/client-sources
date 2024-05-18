package net.minecraft.util;

public class FrameTimer
{
    private final long[] field_181752_a;
    private int field_181755_d;
    private int field_181753_b;
    private int field_181754_c;
    
    public void func_181747_a(final long n) {
        this.field_181752_a[this.field_181755_d] = n;
        this.field_181755_d += " ".length();
        if (this.field_181755_d == 82 + 104 - 107 + 161) {
            this.field_181755_d = "".length();
        }
        if (this.field_181754_c < 176 + 13 - 32 + 83) {
            this.field_181753_b = "".length();
            this.field_181754_c += " ".length();
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            this.field_181753_b = this.func_181751_b(this.field_181755_d + " ".length());
        }
    }
    
    public int func_181751_b(final int n) {
        return n % (228 + 56 - 231 + 187);
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int func_181750_b() {
        return this.field_181755_d;
    }
    
    public int func_181749_a() {
        return this.field_181753_b;
    }
    
    public long[] func_181746_c() {
        return this.field_181752_a;
    }
    
    public FrameTimer() {
        this.field_181752_a = new long[216 + 174 - 208 + 58];
    }
    
    public int func_181748_a(final long n, final int n2) {
        return (int)(n / 1.6666666E7 * n2);
    }
}
