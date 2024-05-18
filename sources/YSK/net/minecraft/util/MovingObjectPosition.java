package net.minecraft.util;

import net.minecraft.entity.*;

public class MovingObjectPosition
{
    private static final String[] I;
    private BlockPos blockPos;
    public MovingObjectType typeOfHit;
    public Vec3 hitVec;
    public EnumFacing sideHit;
    public Entity entityHit;
    
    static {
        I();
    }
    
    public MovingObjectPosition(final Vec3 vec3, final EnumFacing enumFacing) {
        this(MovingObjectType.BLOCK, vec3, enumFacing, BlockPos.ORIGIN);
    }
    
    public MovingObjectPosition(final Entity entity) {
        this(entity, new Vec3(entity.posX, entity.posY, entity.posZ));
    }
    
    public BlockPos getBlockPos() {
        return this.blockPos;
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
            if (3 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public MovingObjectPosition(final MovingObjectType typeOfHit, final Vec3 vec3, final EnumFacing sideHit, final BlockPos blockPos) {
        this.typeOfHit = typeOfHit;
        this.blockPos = blockPos;
        this.sideHit = sideHit;
        this.hitVec = new Vec3(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }
    
    @Override
    public String toString() {
        return MovingObjectPosition.I["".length()] + this.typeOfHit + MovingObjectPosition.I[" ".length()] + this.blockPos + MovingObjectPosition.I["  ".length()] + this.sideHit + MovingObjectPosition.I["   ".length()] + this.hitVec + MovingObjectPosition.I[0x3A ^ 0x3E] + this.entityHit + (char)(0xF2 ^ 0x8F);
    }
    
    private static void I() {
        (I = new String[0x96 ^ 0x93])["".length()] = I("\u0002\u000e00$9\u0012(\u0016:>\u001e4\u0007|", "JgDbA");
        MovingObjectPosition.I[" ".length()] = I("iG\u000b5$&\f\u001968x", "EgiYK");
        MovingObjectPosition.I["  ".length()] = I("Ky\u0016Y", "gYpdJ");
        MovingObjectPosition.I["   ".length()] = I("yf*8\u001dh", "UFZWn");
        MovingObjectPosition.I[0x40 ^ 0x44] = I("jB*/\u0002/\u00166|", "FbOAv");
    }
    
    public MovingObjectPosition(final Vec3 vec3, final EnumFacing enumFacing, final BlockPos blockPos) {
        this(MovingObjectType.BLOCK, vec3, enumFacing, blockPos);
    }
    
    public MovingObjectPosition(final Entity entityHit, final Vec3 hitVec) {
        this.typeOfHit = MovingObjectType.ENTITY;
        this.entityHit = entityHit;
        this.hitVec = hitVec;
    }
    
    public enum MovingObjectType
    {
        ENTITY(MovingObjectType.I["  ".length()], "  ".length());
        
        private static final String[] I;
        
        BLOCK(MovingObjectType.I[" ".length()], " ".length());
        
        private static final MovingObjectType[] ENUM$VALUES;
        
        MISS(MovingObjectType.I["".length()], "".length());
        
        private MovingObjectType(final String s, final int n) {
        }
        
        static {
            I();
            final MovingObjectType[] enum$VALUES = new MovingObjectType["   ".length()];
            enum$VALUES["".length()] = MovingObjectType.MISS;
            enum$VALUES[" ".length()] = MovingObjectType.BLOCK;
            enum$VALUES["  ".length()] = MovingObjectType.ENTITY;
            ENUM$VALUES = enum$VALUES;
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
        
        private static void I() {
            (I = new String["   ".length()])["".length()] = I("\u001d\u001c*\n", "PUyYE");
            MovingObjectType.I[" ".length()] = I("\u0012=\n&=", "PqEev");
            MovingObjectType.I["  ".length()] = I("+:9\u0007\r7", "ntmNY");
        }
    }
}
