/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block.pattern;

import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IWorldReader;

public class BlockPattern {
    private final Predicate<CachedBlockInfo>[][][] blockMatches;
    private final int fingerLength;
    private final int thumbLength;
    private final int palmLength;

    public BlockPattern(Predicate<CachedBlockInfo>[][][] predicateArray) {
        this.blockMatches = predicateArray;
        this.fingerLength = predicateArray.length;
        if (this.fingerLength > 0) {
            this.thumbLength = predicateArray[0].length;
            this.palmLength = this.thumbLength > 0 ? predicateArray[0][0].length : 0;
        } else {
            this.thumbLength = 0;
            this.palmLength = 0;
        }
    }

    public int getFingerLength() {
        return this.fingerLength;
    }

    public int getThumbLength() {
        return this.thumbLength;
    }

    public int getPalmLength() {
        return this.palmLength;
    }

    @Nullable
    private PatternHelper checkPatternAt(BlockPos blockPos, Direction direction, Direction direction2, LoadingCache<BlockPos, CachedBlockInfo> loadingCache) {
        for (int i = 0; i < this.palmLength; ++i) {
            for (int j = 0; j < this.thumbLength; ++j) {
                for (int k = 0; k < this.fingerLength; ++k) {
                    if (this.blockMatches[k][j][i].test(loadingCache.getUnchecked(BlockPattern.translateOffset(blockPos, direction, direction2, i, j, k)))) continue;
                    return null;
                }
            }
        }
        return new PatternHelper(blockPos, direction, direction2, loadingCache, this.palmLength, this.thumbLength, this.fingerLength);
    }

    @Nullable
    public PatternHelper match(IWorldReader iWorldReader, BlockPos blockPos) {
        LoadingCache<BlockPos, CachedBlockInfo> loadingCache = BlockPattern.createLoadingCache(iWorldReader, false);
        int n = Math.max(Math.max(this.palmLength, this.thumbLength), this.fingerLength);
        for (BlockPos blockPos2 : BlockPos.getAllInBoxMutable(blockPos, blockPos.add(n - 1, n - 1, n - 1))) {
            for (Direction direction : Direction.values()) {
                for (Direction direction2 : Direction.values()) {
                    PatternHelper patternHelper;
                    if (direction2 == direction || direction2 == direction.getOpposite() || (patternHelper = this.checkPatternAt(blockPos2, direction, direction2, loadingCache)) == null) continue;
                    return patternHelper;
                }
            }
        }
        return null;
    }

    public static LoadingCache<BlockPos, CachedBlockInfo> createLoadingCache(IWorldReader iWorldReader, boolean bl) {
        return CacheBuilder.newBuilder().build(new CacheLoader(iWorldReader, bl));
    }

    protected static BlockPos translateOffset(BlockPos blockPos, Direction direction, Direction direction2, int n, int n2, int n3) {
        if (direction != direction2 && direction != direction2.getOpposite()) {
            Vector3i vector3i = new Vector3i(direction.getXOffset(), direction.getYOffset(), direction.getZOffset());
            Vector3i vector3i2 = new Vector3i(direction2.getXOffset(), direction2.getYOffset(), direction2.getZOffset());
            Vector3i vector3i3 = vector3i.crossProduct(vector3i2);
            return blockPos.add(vector3i2.getX() * -n2 + vector3i3.getX() * n + vector3i.getX() * n3, vector3i2.getY() * -n2 + vector3i3.getY() * n + vector3i.getY() * n3, vector3i2.getZ() * -n2 + vector3i3.getZ() * n + vector3i.getZ() * n3);
        }
        throw new IllegalArgumentException("Invalid forwards & up combination");
    }

    public static class PatternHelper {
        private final BlockPos frontTopLeft;
        private final Direction forwards;
        private final Direction up;
        private final LoadingCache<BlockPos, CachedBlockInfo> lcache;
        private final int width;
        private final int height;
        private final int depth;

        public PatternHelper(BlockPos blockPos, Direction direction, Direction direction2, LoadingCache<BlockPos, CachedBlockInfo> loadingCache, int n, int n2, int n3) {
            this.frontTopLeft = blockPos;
            this.forwards = direction;
            this.up = direction2;
            this.lcache = loadingCache;
            this.width = n;
            this.height = n2;
            this.depth = n3;
        }

        public BlockPos getFrontTopLeft() {
            return this.frontTopLeft;
        }

        public Direction getForwards() {
            return this.forwards;
        }

        public Direction getUp() {
            return this.up;
        }

        public CachedBlockInfo translateOffset(int n, int n2, int n3) {
            return this.lcache.getUnchecked(BlockPattern.translateOffset(this.frontTopLeft, this.getForwards(), this.getUp(), n, n2, n3));
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("up", this.up).add("forwards", this.forwards).add("frontTopLeft", this.frontTopLeft).toString();
        }
    }

    static class CacheLoader
    extends com.google.common.cache.CacheLoader<BlockPos, CachedBlockInfo> {
        private final IWorldReader world;
        private final boolean forceLoad;

        public CacheLoader(IWorldReader iWorldReader, boolean bl) {
            this.world = iWorldReader;
            this.forceLoad = bl;
        }

        @Override
        public CachedBlockInfo load(BlockPos blockPos) throws Exception {
            return new CachedBlockInfo(this.world, blockPos, this.forceLoad);
        }

        @Override
        public Object load(Object object) throws Exception {
            return this.load((BlockPos)object);
        }
    }
}

