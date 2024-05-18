package net.minecraft.pathfinding;

import net.minecraft.util.*;

public class PathPoint
{
    private static final String[] I;
    public final int xCoord;
    private final int hash;
    PathPoint previous;
    public final int yCoord;
    public boolean visited;
    float totalPathDistance;
    public final int zCoord;
    float distanceToNext;
    int index;
    float distanceToTarget;
    
    public static int makeHash(final int n, final int n2, final int n3) {
        final int n4 = (n2 & 224 + 53 - 53 + 31) | (n & 6733 + 3389 + 5136 + 17509) << (0x11 ^ 0x19) | (n3 & 14566 + 16566 - 6988 + 8623) << (0x30 ^ 0x28);
        int length;
        if (n < 0) {
            length = -"".length();
            "".length();
            if (2 == 0) {
                throw null;
            }
        }
        else {
            length = "".length();
        }
        final int n5 = n4 | length;
        int length2;
        if (n3 < 0) {
            length2 = 19857 + 26853 - 44797 + 30855;
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            length2 = "".length();
        }
        return n5 | length2;
    }
    
    public float distanceToSquared(final PathPoint pathPoint) {
        final float n = pathPoint.xCoord - this.xCoord;
        final float n2 = pathPoint.yCoord - this.yCoord;
        final float n3 = pathPoint.zCoord - this.zCoord;
        return n * n + n2 * n2 + n3 * n3;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof PathPoint)) {
            return "".length() != 0;
        }
        final PathPoint pathPoint = (PathPoint)o;
        if (this.hash == pathPoint.hash && this.xCoord == pathPoint.xCoord && this.yCoord == pathPoint.yCoord && this.zCoord == pathPoint.zCoord) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public PathPoint(final int xCoord, final int yCoord, final int zCoord) {
        this.index = -" ".length();
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
        this.hash = makeHash(xCoord, yCoord, zCoord);
    }
    
    @Override
    public int hashCode() {
        return this.hash;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("^M", "rmBOi");
        PathPoint.I[" ".length()] = I("ty", "XYGLh");
    }
    
    static {
        I();
    }
    
    public boolean isAssigned() {
        if (this.index >= 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.xCoord) + PathPoint.I["".length()] + this.yCoord + PathPoint.I[" ".length()] + this.zCoord;
    }
    
    public float distanceTo(final PathPoint pathPoint) {
        final float n = pathPoint.xCoord - this.xCoord;
        final float n2 = pathPoint.yCoord - this.yCoord;
        final float n3 = pathPoint.zCoord - this.zCoord;
        return MathHelper.sqrt_float(n * n + n2 * n2 + n3 * n3);
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
            if (4 <= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
