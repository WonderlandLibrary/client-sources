package net.minecraft.util;

public class AxisAlignedBB
{
    public final double maxZ;
    public final double minX;
    public final double maxX;
    public final double maxY;
    public final double minY;
    private static final String[] I;
    public final double minZ;
    
    public AxisAlignedBB expand(final double n, final double n2, final double n3) {
        return new AxisAlignedBB(this.minX - n, this.minY - n2, this.minZ - n3, this.maxX + n, this.maxY + n2, this.maxZ + n3);
    }
    
    public static AxisAlignedBB fromBounds(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        return new AxisAlignedBB(Math.min(n, n4), Math.min(n2, n5), Math.min(n3, n6), Math.max(n, n4), Math.max(n2, n5), Math.max(n3, n6));
    }
    
    private boolean isVecInXY(final Vec3 vec3) {
        int n;
        if (vec3 == null) {
            n = "".length();
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else if (vec3.xCoord >= this.minX && vec3.xCoord <= this.maxX && vec3.yCoord >= this.minY && vec3.yCoord <= this.maxY) {
            n = " ".length();
            "".length();
            if (false == true) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    private static void I() {
        (I = new String[0x4C ^ 0x4B])["".length()] = I("$<*0", "FSRkn");
        AxisAlignedBB.I[" ".length()] = I("bW", "NwoAV");
        AxisAlignedBB.I["  ".length()] = I("[Q", "wqLCi");
        AxisAlignedBB.I["   ".length()] = I("c_\\C", "Crbcx");
        AxisAlignedBB.I[0x9C ^ 0x98] = I("\\j", "pJRrh");
        AxisAlignedBB.I[0x7A ^ 0x7F] = I("iC", "EcgXp");
        AxisAlignedBB.I[0xB ^ 0xD] = I("\u0015", "HRJWU");
    }
    
    public AxisAlignedBB(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        this.minX = Math.min(n, n4);
        this.minY = Math.min(n2, n5);
        this.minZ = Math.min(n3, n6);
        this.maxX = Math.max(n, n4);
        this.maxY = Math.max(n2, n5);
        this.maxZ = Math.max(n3, n6);
    }
    
    public MovingObjectPosition calculateIntercept(final Vec3 vec3, final Vec3 vec4) {
        Vec3 intermediateWithXValue = vec3.getIntermediateWithXValue(vec4, this.minX);
        Vec3 intermediateWithXValue2 = vec3.getIntermediateWithXValue(vec4, this.maxX);
        Vec3 intermediateWithYValue = vec3.getIntermediateWithYValue(vec4, this.minY);
        Vec3 intermediateWithYValue2 = vec3.getIntermediateWithYValue(vec4, this.maxY);
        Vec3 intermediateWithZValue = vec3.getIntermediateWithZValue(vec4, this.minZ);
        Vec3 intermediateWithZValue2 = vec3.getIntermediateWithZValue(vec4, this.maxZ);
        if (!this.isVecInYZ(intermediateWithXValue)) {
            intermediateWithXValue = null;
        }
        if (!this.isVecInYZ(intermediateWithXValue2)) {
            intermediateWithXValue2 = null;
        }
        if (!this.isVecInXZ(intermediateWithYValue)) {
            intermediateWithYValue = null;
        }
        if (!this.isVecInXZ(intermediateWithYValue2)) {
            intermediateWithYValue2 = null;
        }
        if (!this.isVecInXY(intermediateWithZValue)) {
            intermediateWithZValue = null;
        }
        if (!this.isVecInXY(intermediateWithZValue2)) {
            intermediateWithZValue2 = null;
        }
        Vec3 vec5 = null;
        if (intermediateWithXValue != null) {
            vec5 = intermediateWithXValue;
        }
        if (intermediateWithXValue2 != null && (vec5 == null || vec3.squareDistanceTo(intermediateWithXValue2) < vec3.squareDistanceTo(vec5))) {
            vec5 = intermediateWithXValue2;
        }
        if (intermediateWithYValue != null && (vec5 == null || vec3.squareDistanceTo(intermediateWithYValue) < vec3.squareDistanceTo(vec5))) {
            vec5 = intermediateWithYValue;
        }
        if (intermediateWithYValue2 != null && (vec5 == null || vec3.squareDistanceTo(intermediateWithYValue2) < vec3.squareDistanceTo(vec5))) {
            vec5 = intermediateWithYValue2;
        }
        if (intermediateWithZValue != null && (vec5 == null || vec3.squareDistanceTo(intermediateWithZValue) < vec3.squareDistanceTo(vec5))) {
            vec5 = intermediateWithZValue;
        }
        if (intermediateWithZValue2 != null && (vec5 == null || vec3.squareDistanceTo(intermediateWithZValue2) < vec3.squareDistanceTo(vec5))) {
            vec5 = intermediateWithZValue2;
        }
        if (vec5 == null) {
            return null;
        }
        EnumFacing enumFacing;
        if (vec5 == intermediateWithXValue) {
            enumFacing = EnumFacing.WEST;
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else if (vec5 == intermediateWithXValue2) {
            enumFacing = EnumFacing.EAST;
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else if (vec5 == intermediateWithYValue) {
            enumFacing = EnumFacing.DOWN;
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else if (vec5 == intermediateWithYValue2) {
            enumFacing = EnumFacing.UP;
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else if (vec5 == intermediateWithZValue) {
            enumFacing = EnumFacing.NORTH;
            "".length();
            if (!true) {
                throw null;
            }
        }
        else {
            enumFacing = EnumFacing.SOUTH;
        }
        return new MovingObjectPosition(vec5, enumFacing);
    }
    
    public AxisAlignedBB addCoord(final double n, final double n2, final double n3) {
        double minX = this.minX;
        double minY = this.minY;
        double minZ = this.minZ;
        double maxX = this.maxX;
        double maxY = this.maxY;
        double maxZ = this.maxZ;
        if (n < 0.0) {
            minX += n;
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else if (n > 0.0) {
            maxX += n;
        }
        if (n2 < 0.0) {
            minY += n2;
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        else if (n2 > 0.0) {
            maxY += n2;
        }
        if (n3 < 0.0) {
            minZ += n3;
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        else if (n3 > 0.0) {
            maxZ += n3;
        }
        return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    public AxisAlignedBB contract(final double n, final double n2, final double n3) {
        return new AxisAlignedBB(this.minX + n, this.minY + n2, this.minZ + n3, this.maxX - n, this.maxY - n2, this.maxZ - n3);
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
    
    public boolean isVecInside(final Vec3 vec3) {
        int n;
        if (vec3.xCoord > this.minX && vec3.xCoord < this.maxX) {
            if (vec3.yCoord > this.minY && vec3.yCoord < this.maxY) {
                if (vec3.zCoord > this.minZ && vec3.zCoord < this.maxZ) {
                    n = " ".length();
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                }
                else {
                    n = "".length();
                    "".length();
                    if (3 == 1) {
                        throw null;
                    }
                }
            }
            else {
                n = "".length();
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public double getAverageEdgeLength() {
        return (this.maxX - this.minX + (this.maxY - this.minY) + (this.maxZ - this.minZ)) / 3.0;
    }
    
    public AxisAlignedBB union(final AxisAlignedBB axisAlignedBB) {
        return new AxisAlignedBB(Math.min(this.minX, axisAlignedBB.minX), Math.min(this.minY, axisAlignedBB.minY), Math.min(this.minZ, axisAlignedBB.minZ), Math.max(this.maxX, axisAlignedBB.maxX), Math.max(this.maxY, axisAlignedBB.maxY), Math.max(this.maxZ, axisAlignedBB.maxZ));
    }
    
    public double calculateYOffset(final AxisAlignedBB axisAlignedBB, double n) {
        if (axisAlignedBB.maxX > this.minX && axisAlignedBB.minX < this.maxX && axisAlignedBB.maxZ > this.minZ && axisAlignedBB.minZ < this.maxZ) {
            if (n > 0.0 && axisAlignedBB.maxY <= this.minY) {
                final double n2 = this.minY - axisAlignedBB.maxY;
                if (n2 < n) {
                    n = n2;
                    "".length();
                    if (0 >= 3) {
                        throw null;
                    }
                }
            }
            else if (n < 0.0 && axisAlignedBB.minY >= this.maxY) {
                final double n3 = this.maxY - axisAlignedBB.minY;
                if (n3 > n) {
                    n = n3;
                }
            }
            return n;
        }
        return n;
    }
    
    public double calculateZOffset(final AxisAlignedBB axisAlignedBB, double n) {
        if (axisAlignedBB.maxX > this.minX && axisAlignedBB.minX < this.maxX && axisAlignedBB.maxY > this.minY && axisAlignedBB.minY < this.maxY) {
            if (n > 0.0 && axisAlignedBB.maxZ <= this.minZ) {
                final double n2 = this.minZ - axisAlignedBB.maxZ;
                if (n2 < n) {
                    n = n2;
                    "".length();
                    if (0 <= -1) {
                        throw null;
                    }
                }
            }
            else if (n < 0.0 && axisAlignedBB.minZ >= this.maxZ) {
                final double n3 = this.maxZ - axisAlignedBB.minZ;
                if (n3 > n) {
                    n = n3;
                }
            }
            return n;
        }
        return n;
    }
    
    @Override
    public String toString() {
        return AxisAlignedBB.I["".length()] + this.minX + AxisAlignedBB.I[" ".length()] + this.minY + AxisAlignedBB.I["  ".length()] + this.minZ + AxisAlignedBB.I["   ".length()] + this.maxX + AxisAlignedBB.I[0x73 ^ 0x77] + this.maxY + AxisAlignedBB.I[0x1E ^ 0x1B] + this.maxZ + AxisAlignedBB.I[0xBE ^ 0xB8];
    }
    
    static {
        I();
    }
    
    public boolean func_181656_b() {
        if (!Double.isNaN(this.minX) && !Double.isNaN(this.minY) && !Double.isNaN(this.minZ) && !Double.isNaN(this.maxX) && !Double.isNaN(this.maxY) && !Double.isNaN(this.maxZ)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public AxisAlignedBB offset(final double n, final double n2, final double n3) {
        return new AxisAlignedBB(this.minX + n, this.minY + n2, this.minZ + n3, this.maxX + n, this.maxY + n2, this.maxZ + n3);
    }
    
    public boolean intersectsWith(final AxisAlignedBB axisAlignedBB) {
        int n;
        if (axisAlignedBB.maxX > this.minX && axisAlignedBB.minX < this.maxX) {
            if (axisAlignedBB.maxY > this.minY && axisAlignedBB.minY < this.maxY) {
                if (axisAlignedBB.maxZ > this.minZ && axisAlignedBB.minZ < this.maxZ) {
                    n = " ".length();
                    "".length();
                    if (4 <= 1) {
                        throw null;
                    }
                }
                else {
                    n = "".length();
                    "".length();
                    if (4 <= 1) {
                        throw null;
                    }
                }
            }
            else {
                n = "".length();
                "".length();
                if (4 < -1) {
                    throw null;
                }
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    private boolean isVecInYZ(final Vec3 vec3) {
        int n;
        if (vec3 == null) {
            n = "".length();
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else if (vec3.yCoord >= this.minY && vec3.yCoord <= this.maxY && vec3.zCoord >= this.minZ && vec3.zCoord <= this.maxZ) {
            n = " ".length();
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public AxisAlignedBB(final BlockPos blockPos, final BlockPos blockPos2) {
        this.minX = blockPos.getX();
        this.minY = blockPos.getY();
        this.minZ = blockPos.getZ();
        this.maxX = blockPos2.getX();
        this.maxY = blockPos2.getY();
        this.maxZ = blockPos2.getZ();
    }
    
    private boolean isVecInXZ(final Vec3 vec3) {
        int n;
        if (vec3 == null) {
            n = "".length();
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else if (vec3.xCoord >= this.minX && vec3.xCoord <= this.maxX && vec3.zCoord >= this.minZ && vec3.zCoord <= this.maxZ) {
            n = " ".length();
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public double calculateXOffset(final AxisAlignedBB axisAlignedBB, double n) {
        if (axisAlignedBB.maxY > this.minY && axisAlignedBB.minY < this.maxY && axisAlignedBB.maxZ > this.minZ && axisAlignedBB.minZ < this.maxZ) {
            if (n > 0.0 && axisAlignedBB.maxX <= this.minX) {
                final double n2 = this.minX - axisAlignedBB.maxX;
                if (n2 < n) {
                    n = n2;
                    "".length();
                    if (1 == 3) {
                        throw null;
                    }
                }
            }
            else if (n < 0.0 && axisAlignedBB.minX >= this.maxX) {
                final double n3 = this.maxX - axisAlignedBB.minX;
                if (n3 > n) {
                    n = n3;
                }
            }
            return n;
        }
        return n;
    }
}
