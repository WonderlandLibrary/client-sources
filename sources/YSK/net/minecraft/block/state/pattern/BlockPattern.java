package net.minecraft.block.state.pattern;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import com.google.common.cache.*;
import net.minecraft.util.*;
import java.util.*;
import com.google.common.base.*;

public class BlockPattern
{
    private final int palmLength;
    private static final String[] I;
    private final int fingerLength;
    private final Predicate<BlockWorldState>[][][] blockMatches;
    private final int thumbLength;
    
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
            if (4 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getPalmLength() {
        return this.palmLength;
    }
    
    public BlockPattern(final Predicate<BlockWorldState>[][][] blockMatches) {
        this.blockMatches = blockMatches;
        this.fingerLength = blockMatches.length;
        if (this.fingerLength > 0) {
            this.thumbLength = blockMatches["".length()].length;
            if (this.thumbLength > 0) {
                this.palmLength = blockMatches["".length()]["".length()].length;
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                this.palmLength = "".length();
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
        }
        else {
            this.thumbLength = "".length();
            this.palmLength = "".length();
        }
    }
    
    static {
        I();
    }
    
    private PatternHelper checkPatternAt(final BlockPos blockPos, final EnumFacing enumFacing, final EnumFacing enumFacing2, final LoadingCache<BlockPos, BlockWorldState> loadingCache) {
        int i = "".length();
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (i < this.palmLength) {
            int j = "".length();
            "".length();
            if (3 == -1) {
                throw null;
            }
            while (j < this.thumbLength) {
                int k = "".length();
                "".length();
                if (1 < 0) {
                    throw null;
                }
                while (k < this.fingerLength) {
                    if (!this.blockMatches[k][j][i].apply((Object)loadingCache.getUnchecked((Object)translateOffset(blockPos, enumFacing, enumFacing2, i, j, k)))) {
                        return null;
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        return new PatternHelper(blockPos, enumFacing, enumFacing2, loadingCache, this.palmLength, this.thumbLength, this.fingerLength);
    }
    
    public static LoadingCache<BlockPos, BlockWorldState> func_181627_a(final World world, final boolean b) {
        return (LoadingCache<BlockPos, BlockWorldState>)CacheBuilder.newBuilder().build((com.google.common.cache.CacheLoader)new CacheLoader(world, b));
    }
    
    public int getThumbLength() {
        return this.thumbLength;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("8\u001e\u001f4\u000b\u0018\u0014I3\b\u0003\u0007\b'\u0003\u0002POu\u0012\u0001P\n:\n\u0013\u0019\u00074\u0013\u0018\u001f\u0007", "qpiUg");
    }
    
    protected static BlockPos translateOffset(final BlockPos blockPos, final EnumFacing enumFacing, final EnumFacing enumFacing2, final int n, final int n2, final int n3) {
        if (enumFacing != enumFacing2 && enumFacing != enumFacing2.getOpposite()) {
            final Vec3i vec3i = new Vec3i(enumFacing.getFrontOffsetX(), enumFacing.getFrontOffsetY(), enumFacing.getFrontOffsetZ());
            final Vec3i vec3i2 = new Vec3i(enumFacing2.getFrontOffsetX(), enumFacing2.getFrontOffsetY(), enumFacing2.getFrontOffsetZ());
            final Vec3i crossProduct = vec3i.crossProduct(vec3i2);
            return blockPos.add(vec3i2.getX() * -n2 + crossProduct.getX() * n + vec3i.getX() * n3, vec3i2.getY() * -n2 + crossProduct.getY() * n + vec3i.getY() * n3, vec3i2.getZ() * -n2 + crossProduct.getZ() * n + vec3i.getZ() * n3);
        }
        throw new IllegalArgumentException(BlockPattern.I["".length()]);
    }
    
    public PatternHelper match(final World world, final BlockPos blockPos) {
        final LoadingCache<BlockPos, BlockWorldState> func_181627_a = func_181627_a(world, "".length() != 0);
        final int max = Math.max(Math.max(this.palmLength, this.thumbLength), this.fingerLength);
        final Iterator<BlockPos> iterator = BlockPos.getAllInBox(blockPos, blockPos.add(max - " ".length(), max - " ".length(), max - " ".length())).iterator();
        "".length();
        if (4 == 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final BlockPos blockPos2 = iterator.next();
            final EnumFacing[] values;
            final int length = (values = EnumFacing.values()).length;
            int i = "".length();
            "".length();
            if (3 < 3) {
                throw null;
            }
            while (i < length) {
                final EnumFacing enumFacing = values[i];
                final EnumFacing[] values2;
                final int length2 = (values2 = EnumFacing.values()).length;
                int j = "".length();
                "".length();
                if (3 < 0) {
                    throw null;
                }
                while (j < length2) {
                    final EnumFacing enumFacing2 = values2[j];
                    if (enumFacing2 != enumFacing && enumFacing2 != enumFacing.getOpposite()) {
                        final PatternHelper checkPattern = this.checkPatternAt(blockPos2, enumFacing, enumFacing2, func_181627_a);
                        if (checkPattern != null) {
                            return checkPattern;
                        }
                    }
                    ++j;
                }
                ++i;
            }
        }
        return null;
    }
    
    public static class PatternHelper
    {
        private final LoadingCache<BlockPos, BlockWorldState> lcache;
        private final EnumFacing finger;
        private final BlockPos pos;
        private final int field_181121_f;
        private static final String[] I;
        private final int field_181120_e;
        private final EnumFacing thumb;
        private final int field_181122_g;
        
        public int func_181118_d() {
            return this.field_181120_e;
        }
        
        public int func_181119_e() {
            return this.field_181121_f;
        }
        
        private static void I() {
            (I = new String["   ".length()])["".length()] = I("\u0017\u0012", "bbHZQ");
            PatternHelper.I[" ".length()] = I("\u0007(\u001c-;\u0013#\u001d", "aGnZZ");
            PatternHelper.I["  ".length()] = I("(7#7.\u001a*<\u0015?(1", "NELYZ");
        }
        
        static {
            I();
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
                if (-1 >= 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public BlockPos func_181117_a() {
            return this.pos;
        }
        
        @Override
        public String toString() {
            return Objects.toStringHelper((Object)this).add(PatternHelper.I["".length()], (Object)this.thumb).add(PatternHelper.I[" ".length()], (Object)this.finger).add(PatternHelper.I["  ".length()], (Object)this.pos).toString();
        }
        
        public BlockWorldState translateOffset(final int n, final int n2, final int n3) {
            return (BlockWorldState)this.lcache.getUnchecked((Object)BlockPattern.translateOffset(this.pos, this.getFinger(), this.getThumb(), n, n2, n3));
        }
        
        public PatternHelper(final BlockPos pos, final EnumFacing finger, final EnumFacing thumb, final LoadingCache<BlockPos, BlockWorldState> lcache, final int field_181120_e, final int field_181121_f, final int field_181122_g) {
            this.pos = pos;
            this.finger = finger;
            this.thumb = thumb;
            this.lcache = lcache;
            this.field_181120_e = field_181120_e;
            this.field_181121_f = field_181121_f;
            this.field_181122_g = field_181122_g;
        }
        
        public EnumFacing getFinger() {
            return this.finger;
        }
        
        public EnumFacing getThumb() {
            return this.thumb;
        }
    }
    
    static class CacheLoader extends com.google.common.cache.CacheLoader<BlockPos, BlockWorldState>
    {
        private final boolean field_181626_b;
        private final World world;
        
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
                if (-1 != -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public Object load(final Object o) throws Exception {
            return this.load((BlockPos)o);
        }
        
        public BlockWorldState load(final BlockPos blockPos) throws Exception {
            return new BlockWorldState(this.world, blockPos, this.field_181626_b);
        }
        
        public CacheLoader(final World world, final boolean field_181626_b) {
            this.world = world;
            this.field_181626_b = field_181626_b;
        }
    }
}
