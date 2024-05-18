package net.minecraft.world.gen.structure;

import net.minecraft.nbt.*;
import com.google.common.base.*;
import net.minecraft.util.*;

public class StructureBoundingBox
{
    public int maxY;
    public int minY;
    public int minZ;
    public int maxX;
    private static final String[] I;
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
    public int minX;
    public int maxZ;
    
    public StructureBoundingBox(final int[] array) {
        if (array.length == (0x6 ^ 0x0)) {
            this.minX = array["".length()];
            this.minY = array[" ".length()];
            this.minZ = array["  ".length()];
            this.maxX = array["   ".length()];
            this.maxY = array[0x5A ^ 0x5E];
            this.maxZ = array[0x7F ^ 0x7A];
        }
    }
    
    public StructureBoundingBox(final int minX, final int minY, final int minZ, final int maxX, final int maxY, final int maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }
    
    public int getXSize() {
        return this.maxX - this.minX + " ".length();
    }
    
    static {
        I();
    }
    
    public Vec3i func_175896_b() {
        return new Vec3i(this.maxX - this.minX, this.maxY - this.minY, this.maxZ - this.minZ);
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
            if (3 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void expandTo(final StructureBoundingBox structureBoundingBox) {
        this.minX = Math.min(this.minX, structureBoundingBox.minX);
        this.minY = Math.min(this.minY, structureBoundingBox.minY);
        this.minZ = Math.min(this.minZ, structureBoundingBox.minZ);
        this.maxX = Math.max(this.maxX, structureBoundingBox.maxX);
        this.maxY = Math.max(this.maxY, structureBoundingBox.maxY);
        this.maxZ = Math.max(this.maxZ, structureBoundingBox.maxZ);
    }
    
    public StructureBoundingBox(final int minX, final int minZ, final int maxX, final int maxZ) {
        this.minX = minX;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxZ = maxZ;
        this.minY = " ".length();
        this.maxY = 411 + 179 - 565 + 487;
    }
    
    public static StructureBoundingBox getComponentToAddBoundingBox(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final EnumFacing enumFacing) {
        switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
            case 3: {
                return new StructureBoundingBox(n + n4, n2 + n5, n3 - n9 + " ".length() + n6, n + n7 - " ".length() + n4, n2 + n8 - " ".length() + n5, n3 + n6);
            }
            case 4: {
                return new StructureBoundingBox(n + n4, n2 + n5, n3 + n6, n + n7 - " ".length() + n4, n2 + n8 - " ".length() + n5, n3 + n9 - " ".length() + n6);
            }
            case 5: {
                return new StructureBoundingBox(n - n9 + " ".length() + n6, n2 + n5, n3 + n4, n + n6, n2 + n8 - " ".length() + n5, n3 + n7 - " ".length() + n4);
            }
            case 6: {
                return new StructureBoundingBox(n + n6, n2 + n5, n3 + n4, n + n9 - " ".length() + n6, n2 + n8 - " ".length() + n5, n3 + n7 - " ".length() + n4);
            }
            default: {
                return new StructureBoundingBox(n + n4, n2 + n5, n3 + n6, n + n7 - " ".length() + n4, n2 + n8 - " ".length() + n5, n3 + n9 - " ".length() + n6);
            }
        }
    }
    
    public static StructureBoundingBox func_175899_a(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        return new StructureBoundingBox(Math.min(n, n4), Math.min(n2, n5), Math.min(n3, n6), Math.max(n, n4), Math.max(n2, n5), Math.max(n3, n6));
    }
    
    public NBTTagIntArray toNBTTagIntArray() {
        final int[] array = new int[0x73 ^ 0x75];
        array["".length()] = this.minX;
        array[" ".length()] = this.minY;
        array["  ".length()] = this.minZ;
        array["   ".length()] = this.maxX;
        array[0x23 ^ 0x27] = this.maxY;
        array[0x48 ^ 0x4D] = this.maxZ;
        return new NBTTagIntArray(array);
    }
    
    public boolean intersectsWith(final StructureBoundingBox structureBoundingBox) {
        if (this.maxX >= structureBoundingBox.minX && this.minX <= structureBoundingBox.maxX && this.maxZ >= structureBoundingBox.minZ && this.minZ <= structureBoundingBox.maxZ && this.maxY >= structureBoundingBox.minY && this.minY <= structureBoundingBox.maxY) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing = StructureBoundingBox.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x9 ^ 0xF);
            "".length();
            if (false) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x7E ^ 0x7A);
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x28 ^ 0x2D);
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return StructureBoundingBox.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
    }
    
    public void offset(final int n, final int n2, final int n3) {
        this.minX += n;
        this.minY += n2;
        this.minZ += n3;
        this.maxX += n;
        this.maxY += n2;
        this.maxZ += n3;
    }
    
    public StructureBoundingBox(final StructureBoundingBox structureBoundingBox) {
        this.minX = structureBoundingBox.minX;
        this.minY = structureBoundingBox.minY;
        this.minZ = structureBoundingBox.minZ;
        this.maxX = structureBoundingBox.maxX;
        this.maxY = structureBoundingBox.maxY;
        this.maxZ = structureBoundingBox.maxZ;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper((Object)this).add(StructureBoundingBox.I["".length()], this.minX).add(StructureBoundingBox.I[" ".length()], this.minY).add(StructureBoundingBox.I["  ".length()], this.minZ).add(StructureBoundingBox.I["   ".length()], this.maxX).add(StructureBoundingBox.I[0x89 ^ 0x8D], this.maxY).add(StructureBoundingBox.I[0x81 ^ 0x84], this.maxZ).toString();
    }
    
    public int getZSize() {
        return this.maxZ - this.minZ + " ".length();
    }
    
    public StructureBoundingBox() {
    }
    
    public int getYSize() {
        return this.maxY - this.minY + " ".length();
    }
    
    public StructureBoundingBox(final Vec3i vec3i, final Vec3i vec3i2) {
        this.minX = Math.min(vec3i.getX(), vec3i2.getX());
        this.minY = Math.min(vec3i.getY(), vec3i2.getY());
        this.minZ = Math.min(vec3i.getZ(), vec3i2.getZ());
        this.maxX = Math.max(vec3i.getX(), vec3i2.getX());
        this.maxY = Math.max(vec3i.getY(), vec3i2.getY());
        this.maxZ = Math.max(vec3i.getZ(), vec3i2.getZ());
    }
    
    public boolean intersectsWith(final int n, final int n2, final int n3, final int n4) {
        if (this.maxX >= n && this.minX <= n3 && this.maxZ >= n2 && this.minZ <= n4) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean isVecInside(final Vec3i vec3i) {
        if (vec3i.getX() >= this.minX && vec3i.getX() <= this.maxX && vec3i.getZ() >= this.minZ && vec3i.getZ() <= this.maxZ && vec3i.getY() >= this.minY && vec3i.getY() <= this.maxY) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static StructureBoundingBox getNewBoundingBox() {
        return new StructureBoundingBox(1274057018 + 78562242 - 1273041644 + 2067906031, 599228298 + 1537914089 - 701996458 + 712337718, 2020751536 + 1729663764 + 1842675503 + 849360140, -"".length(), -"".length(), -"".length());
    }
    
    public Vec3i getCenter() {
        return new BlockPos(this.minX + (this.maxX - this.minX + " ".length()) / "  ".length(), this.minY + (this.maxY - this.minY + " ".length()) / "  ".length(), this.minZ + (this.maxZ - this.minZ + " ".length()) / "  ".length());
    }
    
    private static void I() {
        (I = new String[0x6F ^ 0x69])["".length()] = I("\u0014a", "lQwPB");
        StructureBoundingBox.I[" ".length()] = I("\u000bY", "rigGk");
        StructureBoundingBox.I["  ".length()] = I("\u001cB", "friPi");
        StructureBoundingBox.I["   ".length()] = I("2k", "JZGVR");
        StructureBoundingBox.I[0x11 ^ 0x15] = I("<s", "EBPqI");
        StructureBoundingBox.I[0x1F ^ 0x1A] = I("#H", "YycVU");
    }
}
