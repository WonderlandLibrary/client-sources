package net.minecraft.util;

public class Vec4b
{
    private byte field_176114_d;
    private byte field_176117_a;
    private byte field_176116_c;
    private byte field_176115_b;
    
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
            if (2 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public byte func_176112_b() {
        return this.field_176115_b;
    }
    
    public Vec4b(final byte field_176117_a, final byte field_176115_b, final byte field_176116_c, final byte field_176114_d) {
        this.field_176117_a = field_176117_a;
        this.field_176115_b = field_176115_b;
        this.field_176116_c = field_176116_c;
        this.field_176114_d = field_176114_d;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return " ".length() != 0;
        }
        if (!(o instanceof Vec4b)) {
            return "".length() != 0;
        }
        final Vec4b vec4b = (Vec4b)o;
        int n;
        if (this.field_176117_a != vec4b.field_176117_a) {
            n = "".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (this.field_176114_d != vec4b.field_176114_d) {
            n = "".length();
            "".length();
            if (0 == 2) {
                throw null;
            }
        }
        else if (this.field_176115_b != vec4b.field_176115_b) {
            n = "".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else if (this.field_176116_c == vec4b.field_176116_c) {
            n = " ".length();
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public byte func_176113_c() {
        return this.field_176116_c;
    }
    
    public byte func_176111_d() {
        return this.field_176114_d;
    }
    
    @Override
    public int hashCode() {
        return (0x29 ^ 0x36) * ((0x2B ^ 0x34) * ((0x44 ^ 0x5B) * this.field_176117_a + this.field_176115_b) + this.field_176116_c) + this.field_176114_d;
    }
    
    public byte func_176110_a() {
        return this.field_176117_a;
    }
    
    public Vec4b(final Vec4b vec4b) {
        this.field_176117_a = vec4b.field_176117_a;
        this.field_176115_b = vec4b.field_176115_b;
        this.field_176116_c = vec4b.field_176116_c;
        this.field_176114_d = vec4b.field_176114_d;
    }
}
