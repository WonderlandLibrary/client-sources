package net.minecraft.util;

import com.google.common.base.*;

public class Vec3i implements Comparable<Vec3i>
{
    private static final String[] I;
    private final int y;
    private final int x;
    public static final Vec3i NULL_VECTOR;
    private final int z;
    
    @Override
    public int hashCode() {
        return (this.getY() + this.getZ() * (0xBA ^ 0xA5)) * (0x8E ^ 0x91) + this.getX();
    }
    
    public int getZ() {
        return this.z;
    }
    
    public int getX() {
        return this.x;
    }
    
    public double distanceSq(final Vec3i vec3i) {
        return this.distanceSq(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }
    
    static {
        I();
        NULL_VECTOR = new Vec3i("".length(), "".length(), "".length());
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return " ".length() != 0;
        }
        if (!(o instanceof Vec3i)) {
            return "".length() != 0;
        }
        final Vec3i vec3i = (Vec3i)o;
        int n;
        if (this.getX() != vec3i.getX()) {
            n = "".length();
            "".length();
            if (2 == 0) {
                throw null;
            }
        }
        else if (this.getY() != vec3i.getY()) {
            n = "".length();
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else if (this.getZ() == vec3i.getZ()) {
            n = " ".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public int getY() {
        return this.y;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper((Object)this).add(Vec3i.I["".length()], this.getX()).add(Vec3i.I[" ".length()], this.getY()).add(Vec3i.I["  ".length()], this.getZ()).toString();
    }
    
    @Override
    public int compareTo(final Vec3i vec3i) {
        int n;
        if (this.getY() == vec3i.getY()) {
            if (this.getZ() == vec3i.getZ()) {
                n = this.getX() - vec3i.getX();
                "".length();
                if (2 < 1) {
                    throw null;
                }
            }
            else {
                n = this.getZ() - vec3i.getZ();
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
            }
        }
        else {
            n = this.getY() - vec3i.getY();
        }
        return n;
    }
    
    public double distanceSq(final double n, final double n2, final double n3) {
        final double n4 = this.getX() - n;
        final double n5 = this.getY() - n2;
        final double n6 = this.getZ() - n3;
        return n4 * n4 + n5 * n5 + n6 * n6;
    }
    
    public Vec3i(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vec3i crossProduct(final Vec3i vec3i) {
        return new Vec3i(this.getY() * vec3i.getZ() - this.getZ() * vec3i.getY(), this.getZ() * vec3i.getX() - this.getX() * vec3i.getZ(), this.getX() * vec3i.getY() - this.getY() * vec3i.getX());
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("2", "Jnyrv");
        Vec3i.I[" ".length()] = I("\u0015", "lNVZe");
        Vec3i.I["  ".length()] = I("1", "KELeJ");
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
            if (2 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((Vec3i)o);
    }
    
    public double distanceSqToCenter(final double n, final double n2, final double n3) {
        final double n4 = this.getX() + 0.5 - n;
        final double n5 = this.getY() + 0.5 - n2;
        final double n6 = this.getZ() + 0.5 - n3;
        return n4 * n4 + n5 * n5 + n6 * n6;
    }
    
    public Vec3i(final double n, final double n2, final double n3) {
        this(MathHelper.floor_double(n), MathHelper.floor_double(n2), MathHelper.floor_double(n3));
    }
}
