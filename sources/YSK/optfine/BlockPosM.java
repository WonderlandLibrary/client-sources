package optfine;

import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;

public class BlockPosM extends BlockPos
{
    private int my;
    private BlockPosM[] facings;
    private int mx;
    private int level;
    private boolean needsUpdate;
    private int mz;
    
    private void update() {
        int i = "".length();
        "".length();
        if (-1 < -1) {
            throw null;
        }
        while (i < (0xBC ^ 0xBA)) {
            final BlockPosM blockPosM = this.facings[i];
            if (blockPosM != null) {
                final EnumFacing enumFacing = EnumFacing.VALUES[i];
                blockPosM.setXyz(this.mx + enumFacing.getFrontOffsetX(), this.my + enumFacing.getFrontOffsetY(), this.mz + enumFacing.getFrontOffsetZ());
            }
            ++i;
        }
        this.needsUpdate = ("".length() != 0);
    }
    
    @Override
    public BlockPos offset(final EnumFacing enumFacing, final int n) {
        BlockPos blockPos;
        if (n == " ".length()) {
            blockPos = this.offset(enumFacing);
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            blockPos = super.offset(enumFacing, n);
        }
        return blockPos;
    }
    
    @Override
    public BlockPos offset(final EnumFacing enumFacing) {
        if (this.level <= 0) {
            return super.offset(enumFacing, " ".length());
        }
        if (this.facings == null) {
            this.facings = new BlockPosM[EnumFacing.VALUES.length];
        }
        if (this.needsUpdate) {
            this.update();
        }
        final int index = enumFacing.getIndex();
        BlockPosM blockPosM = this.facings[index];
        if (blockPosM == null) {
            blockPosM = new BlockPosM(this.mx + enumFacing.getFrontOffsetX(), this.my + enumFacing.getFrontOffsetY(), this.mz + enumFacing.getFrontOffsetZ(), this.level - " ".length());
            this.facings[index] = blockPosM;
        }
        return blockPosM;
    }
    
    public BlockPosM(final int mx, final int my, final int mz, final int level) {
        super("".length(), "".length(), "".length());
        this.mx = mx;
        this.my = my;
        this.mz = mz;
        this.level = level;
    }
    
    @Override
    public int getZ() {
        return this.mz;
    }
    
    public void setXyz(final int mx, final int my, final int mz) {
        this.mx = mx;
        this.my = my;
        this.mz = mz;
        this.needsUpdate = (" ".length() != 0);
    }
    
    public BlockPosM(final int n, final int n2, final int n3) {
        this(n, n2, n3, "".length());
    }
    
    public BlockPosM(final double n, final double n2, final double n3) {
        this(MathHelper.floor_double(n), MathHelper.floor_double(n2), MathHelper.floor_double(n3));
    }
    
    @Override
    public int getX() {
        return this.mx;
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
            if (-1 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getY() {
        return this.my;
    }
    
    public void setXyz(final double n, final double n2, final double n3) {
        this.setXyz(MathHelper.floor_double(n), MathHelper.floor_double(n2), MathHelper.floor_double(n3));
    }
    
    public static Iterable getAllInBoxMutable(final BlockPos blockPos, final BlockPos blockPos2) {
        return new Iterable(new BlockPos(Math.min(blockPos.getX(), blockPos2.getX()), Math.min(blockPos.getY(), blockPos2.getY()), Math.min(blockPos.getZ(), blockPos2.getZ())), new BlockPos(Math.max(blockPos.getX(), blockPos2.getX()), Math.max(blockPos.getY(), blockPos2.getY()), Math.max(blockPos.getZ(), blockPos2.getZ()))) {
            private final BlockPos val$blockpos;
            private final BlockPos val$blockpos1;
            
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
            public Iterator iterator() {
                return (Iterator)new AbstractIterator(this, this.val$blockpos, this.val$blockpos1) {
                    private final BlockPos val$blockpos1;
                    final BlockPosM$1 this$1;
                    private BlockPosM theBlockPosM = null;
                    private final BlockPos val$blockpos;
                    
                    protected BlockPosM computeNext0() {
                        if (this.theBlockPosM == null) {
                            return this.theBlockPosM = new BlockPosM(this.val$blockpos.getX(), this.val$blockpos.getY(), this.val$blockpos.getZ(), "   ".length());
                        }
                        if (this.theBlockPosM.equals(this.val$blockpos1)) {
                            return (BlockPosM)this.endOfData();
                        }
                        int n = this.theBlockPosM.getX();
                        int n2 = this.theBlockPosM.getY();
                        int z = this.theBlockPosM.getZ();
                        if (n < this.val$blockpos1.getX()) {
                            ++n;
                            "".length();
                            if (-1 == 2) {
                                throw null;
                            }
                        }
                        else if (n2 < this.val$blockpos1.getY()) {
                            n = this.val$blockpos.getX();
                            ++n2;
                            "".length();
                            if (1 == -1) {
                                throw null;
                            }
                        }
                        else if (z < this.val$blockpos1.getZ()) {
                            n = this.val$blockpos.getX();
                            n2 = this.val$blockpos.getY();
                            ++z;
                        }
                        this.theBlockPosM.setXyz(n, n2, z);
                        return this.theBlockPosM;
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
                    
                    protected Object computeNext() {
                        return this.computeNext0();
                    }
                };
            }
        };
    }
}
