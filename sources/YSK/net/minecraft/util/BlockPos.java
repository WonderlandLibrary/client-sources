package net.minecraft.util;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;

public class BlockPos extends Vec3i
{
    private static final int NUM_Z_BITS;
    public static final BlockPos ORIGIN;
    private static final long X_MASK;
    private static final long Y_MASK;
    private static final int Y_SHIFT;
    private static final int NUM_X_BITS;
    private static final int X_SHIFT;
    private static final int NUM_Y_BITS;
    private static final long Z_MASK;
    
    @Override
    public BlockPos crossProduct(final Vec3i vec3i) {
        return new BlockPos(this.getY() * vec3i.getZ() - this.getZ() * vec3i.getY(), this.getZ() * vec3i.getX() - this.getX() * vec3i.getZ(), this.getX() * vec3i.getY() - this.getY() * vec3i.getX());
    }
    
    public BlockPos down(final int n) {
        return this.offset(EnumFacing.DOWN, n);
    }
    
    public BlockPos up() {
        return this.up(" ".length());
    }
    
    public BlockPos(final Vec3i vec3i) {
        this(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }
    
    public BlockPos add(final int n, final int n2, final int n3) {
        BlockPos blockPos;
        if (n == 0 && n2 == 0 && n3 == 0) {
            blockPos = this;
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            blockPos = new BlockPos(this.getX() + n, this.getY() + n2, this.getZ() + n3);
        }
        return blockPos;
    }
    
    public BlockPos subtract(final Vec3i vec3i) {
        BlockPos blockPos;
        if (vec3i.getX() == 0 && vec3i.getY() == 0 && vec3i.getZ() == 0) {
            blockPos = this;
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            blockPos = new BlockPos(this.getX() - vec3i.getX(), this.getY() - vec3i.getY(), this.getZ() - vec3i.getZ());
        }
        return blockPos;
    }
    
    public BlockPos offset(final EnumFacing enumFacing) {
        return this.offset(enumFacing, " ".length());
    }
    
    static {
        ORIGIN = new BlockPos("".length(), "".length(), "".length());
        NUM_X_BITS = " ".length() + MathHelper.calculateLogBaseTwo(MathHelper.roundUpToPowerOfTwo(10311393 + 26320821 - 22573338 + 15941124));
        NUM_Z_BITS = BlockPos.NUM_X_BITS;
        NUM_Y_BITS = (0x44 ^ 0x4) - BlockPos.NUM_X_BITS - BlockPos.NUM_Z_BITS;
        Y_SHIFT = "".length() + BlockPos.NUM_Z_BITS;
        X_SHIFT = BlockPos.Y_SHIFT + BlockPos.NUM_Y_BITS;
        X_MASK = (1L << BlockPos.NUM_X_BITS) - 1L;
        Y_MASK = (1L << BlockPos.NUM_Y_BITS) - 1L;
        Z_MASK = (1L << BlockPos.NUM_Z_BITS) - 1L;
    }
    
    public static Iterable<BlockPos> getAllInBox(final BlockPos blockPos, final BlockPos blockPos2) {
        return new Iterable<BlockPos>(new BlockPos(Math.min(blockPos.getX(), blockPos2.getX()), Math.min(blockPos.getY(), blockPos2.getY()), Math.min(blockPos.getZ(), blockPos2.getZ())), new BlockPos(Math.max(blockPos.getX(), blockPos2.getX()), Math.max(blockPos.getY(), blockPos2.getY()), Math.max(blockPos.getZ(), blockPos2.getZ()))) {
            private final BlockPos val$blockpos;
            private final BlockPos val$blockpos1;
            
            @Override
            public Iterator<BlockPos> iterator() {
                return (Iterator<BlockPos>)new AbstractIterator<BlockPos>(this, this.val$blockpos, this.val$blockpos1) {
                    private final BlockPos val$blockpos1;
                    private final BlockPos val$blockpos;
                    private BlockPos lastReturned = null;
                    final BlockPos$1 this$1;
                    
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
                            if (false) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                    
                    protected BlockPos computeNext() {
                        if (this.lastReturned == null) {
                            return this.lastReturned = this.val$blockpos;
                        }
                        if (this.lastReturned.equals(this.val$blockpos1)) {
                            return (BlockPos)this.endOfData();
                        }
                        int n = this.lastReturned.getX();
                        int n2 = this.lastReturned.getY();
                        int z = this.lastReturned.getZ();
                        if (n < this.val$blockpos1.getX()) {
                            ++n;
                            "".length();
                            if (-1 != -1) {
                                throw null;
                            }
                        }
                        else if (n2 < this.val$blockpos1.getY()) {
                            n = this.val$blockpos.getX();
                            ++n2;
                            "".length();
                            if (2 < 0) {
                                throw null;
                            }
                        }
                        else if (z < this.val$blockpos1.getZ()) {
                            n = this.val$blockpos.getX();
                            n2 = this.val$blockpos.getY();
                            ++z;
                        }
                        return this.lastReturned = new BlockPos(n, n2, z);
                    }
                    
                    protected Object computeNext() {
                        return this.computeNext();
                    }
                };
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
                    if (4 != 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
    }
    
    @Override
    public Vec3i crossProduct(final Vec3i vec3i) {
        return this.crossProduct(vec3i);
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
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public BlockPos add(final double n, final double n2, final double n3) {
        BlockPos blockPos;
        if (n == 0.0 && n2 == 0.0 && n3 == 0.0) {
            blockPos = this;
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            blockPos = new BlockPos(this.getX() + n, this.getY() + n2, this.getZ() + n3);
        }
        return blockPos;
    }
    
    public BlockPos east(final int n) {
        return this.offset(EnumFacing.EAST, n);
    }
    
    public BlockPos south(final int n) {
        return this.offset(EnumFacing.SOUTH, n);
    }
    
    public BlockPos down() {
        return this.down(" ".length());
    }
    
    public BlockPos south() {
        return this.south(" ".length());
    }
    
    public BlockPos(final int n, final int n2, final int n3) {
        super(n, n2, n3);
    }
    
    public BlockPos(final double n, final double n2, final double n3) {
        super(n, n2, n3);
    }
    
    public BlockPos north(final int n) {
        return this.offset(EnumFacing.NORTH, n);
    }
    
    public BlockPos up(final int n) {
        return this.offset(EnumFacing.UP, n);
    }
    
    public BlockPos(final Entity entity) {
        this(entity.posX, entity.posY, entity.posZ);
    }
    
    public BlockPos offset(final EnumFacing enumFacing, final int n) {
        BlockPos blockPos;
        if (n == 0) {
            blockPos = this;
            "".length();
            if (0 == -1) {
                throw null;
            }
        }
        else {
            blockPos = new BlockPos(this.getX() + enumFacing.getFrontOffsetX() * n, this.getY() + enumFacing.getFrontOffsetY() * n, this.getZ() + enumFacing.getFrontOffsetZ() * n);
        }
        return blockPos;
    }
    
    public BlockPos west(final int n) {
        return this.offset(EnumFacing.WEST, n);
    }
    
    public static BlockPos fromLong(final long n) {
        return new BlockPos((int)(n << (0x5D ^ 0x1D) - BlockPos.X_SHIFT - BlockPos.NUM_X_BITS >> (0x23 ^ 0x63) - BlockPos.NUM_X_BITS), (int)(n << (0xDD ^ 0x9D) - BlockPos.Y_SHIFT - BlockPos.NUM_Y_BITS >> (0x6E ^ 0x2E) - BlockPos.NUM_Y_BITS), (int)(n << (0x4 ^ 0x44) - BlockPos.NUM_Z_BITS >> (0x7C ^ 0x3C) - BlockPos.NUM_Z_BITS));
    }
    
    public BlockPos(final Vec3 vec3) {
        this(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }
    
    public BlockPos east() {
        return this.east(" ".length());
    }
    
    public BlockPos add(final Vec3i vec3i) {
        BlockPos blockPos;
        if (vec3i.getX() == 0 && vec3i.getY() == 0 && vec3i.getZ() == 0) {
            blockPos = this;
            "".length();
            if (!true) {
                throw null;
            }
        }
        else {
            blockPos = new BlockPos(this.getX() + vec3i.getX(), this.getY() + vec3i.getY(), this.getZ() + vec3i.getZ());
        }
        return blockPos;
    }
    
    public BlockPos north() {
        return this.north(" ".length());
    }
    
    public long toLong() {
        return (this.getX() & BlockPos.X_MASK) << BlockPos.X_SHIFT | (this.getY() & BlockPos.Y_MASK) << BlockPos.Y_SHIFT | (this.getZ() & BlockPos.Z_MASK) << "".length();
    }
    
    public static Iterable<MutableBlockPos> getAllInBoxMutable(final BlockPos blockPos, final BlockPos blockPos2) {
        return new Iterable<MutableBlockPos>(new BlockPos(Math.min(blockPos.getX(), blockPos2.getX()), Math.min(blockPos.getY(), blockPos2.getY()), Math.min(blockPos.getZ(), blockPos2.getZ())), new BlockPos(Math.max(blockPos.getX(), blockPos2.getX()), Math.max(blockPos.getY(), blockPos2.getY()), Math.max(blockPos.getZ(), blockPos2.getZ()))) {
            private final BlockPos val$blockpos1;
            private final BlockPos val$blockpos;
            
            @Override
            public Iterator<MutableBlockPos> iterator() {
                return (Iterator<MutableBlockPos>)new AbstractIterator<MutableBlockPos>(this, this.val$blockpos, this.val$blockpos1) {
                    private MutableBlockPos theBlockPos = null;
                    private final BlockPos val$blockpos;
                    final BlockPos$2 this$1;
                    private final BlockPos val$blockpos1;
                    
                    protected Object computeNext() {
                        return this.computeNext();
                    }
                    
                    protected MutableBlockPos computeNext() {
                        if (this.theBlockPos == null) {
                            return this.theBlockPos = new MutableBlockPos(this.val$blockpos.getX(), this.val$blockpos.getY(), this.val$blockpos.getZ());
                        }
                        if (this.theBlockPos.equals(this.val$blockpos1)) {
                            return (MutableBlockPos)this.endOfData();
                        }
                        int n = this.theBlockPos.getX();
                        int n2 = this.theBlockPos.getY();
                        int z = this.theBlockPos.getZ();
                        if (n < this.val$blockpos1.getX()) {
                            ++n;
                            "".length();
                            if (4 < -1) {
                                throw null;
                            }
                        }
                        else if (n2 < this.val$blockpos1.getY()) {
                            n = this.val$blockpos.getX();
                            ++n2;
                            "".length();
                            if (false) {
                                throw null;
                            }
                        }
                        else if (z < this.val$blockpos1.getZ()) {
                            n = this.val$blockpos.getX();
                            n2 = this.val$blockpos.getY();
                            ++z;
                        }
                        MutableBlockPos.access$0(this.theBlockPos, n);
                        MutableBlockPos.access$1(this.theBlockPos, n2);
                        MutableBlockPos.access$2(this.theBlockPos, z);
                        return this.theBlockPos;
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
                            if (0 >= 4) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                };
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
                    if (1 < 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
    }
    
    public BlockPos west() {
        return this.west(" ".length());
    }
    
    public static final class MutableBlockPos extends BlockPos
    {
        private int y;
        private int z;
        private int x;
        
        public MutableBlockPos func_181079_c(final int x, final int y, final int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }
        
        @Override
        public int getX() {
            return this.x;
        }
        
        public MutableBlockPos(final int x, final int y, final int z) {
            super("".length(), "".length(), "".length());
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        static void access$0(final MutableBlockPos mutableBlockPos, final int x) {
            mutableBlockPos.x = x;
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
                if (0 == 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public int getZ() {
            return this.z;
        }
        
        static void access$1(final MutableBlockPos mutableBlockPos, final int y) {
            mutableBlockPos.y = y;
        }
        
        @Override
        public int getY() {
            return this.y;
        }
        
        public MutableBlockPos() {
            this("".length(), "".length(), "".length());
        }
        
        static void access$2(final MutableBlockPos mutableBlockPos, final int z) {
            mutableBlockPos.z = z;
        }
    }
}
