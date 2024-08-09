/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math;

import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;

public class ChunkPos {
    public static final long SENTINEL = ChunkPos.asLong(1875016, 1875016);
    public final int x;
    public final int z;
    private int cachedHashCode = 0;

    public ChunkPos(int n, int n2) {
        this.x = n;
        this.z = n2;
    }

    public ChunkPos(BlockPos blockPos) {
        this.x = blockPos.getX() >> 4;
        this.z = blockPos.getZ() >> 4;
    }

    public ChunkPos(long l) {
        this.x = (int)l;
        this.z = (int)(l >> 32);
    }

    public long asLong() {
        return ChunkPos.asLong(this.x, this.z);
    }

    public static long asLong(int n, int n2) {
        return (long)n & 0xFFFFFFFFL | ((long)n2 & 0xFFFFFFFFL) << 32;
    }

    public static int getX(long l) {
        return (int)(l & 0xFFFFFFFFL);
    }

    public static int getZ(long l) {
        return (int)(l >>> 32 & 0xFFFFFFFFL);
    }

    public int hashCode() {
        if (this.cachedHashCode != 0) {
            return this.cachedHashCode;
        }
        int n = 1664525 * this.x + 1013904223;
        int n2 = 1664525 * (this.z ^ 0xDEADBEEF) + 1013904223;
        this.cachedHashCode = n ^ n2;
        return this.cachedHashCode;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof ChunkPos)) {
            return true;
        }
        ChunkPos chunkPos = (ChunkPos)object;
        return this.x == chunkPos.x && this.z == chunkPos.z;
    }

    public int getXStart() {
        return this.x << 4;
    }

    public int getZStart() {
        return this.z << 4;
    }

    public int getXEnd() {
        return (this.x << 4) + 15;
    }

    public int getZEnd() {
        return (this.z << 4) + 15;
    }

    public int getRegionCoordX() {
        return this.x >> 5;
    }

    public int getRegionCoordZ() {
        return this.z >> 5;
    }

    public int getRegionPositionX() {
        return this.x & 0x1F;
    }

    public int getRegionPositionZ() {
        return this.z & 0x1F;
    }

    public String toString() {
        return "[" + this.x + ", " + this.z + "]";
    }

    public BlockPos asBlockPos() {
        return new BlockPos(this.getXStart(), 0, this.getZStart());
    }

    public int getChessboardDistance(ChunkPos chunkPos) {
        return Math.max(Math.abs(this.x - chunkPos.x), Math.abs(this.z - chunkPos.z));
    }

    public static Stream<ChunkPos> getAllInBox(ChunkPos chunkPos, int n) {
        return ChunkPos.getAllInBox(new ChunkPos(chunkPos.x - n, chunkPos.z - n), new ChunkPos(chunkPos.x + n, chunkPos.z + n));
    }

    public static Stream<ChunkPos> getAllInBox(ChunkPos chunkPos, ChunkPos chunkPos2) {
        int n = Math.abs(chunkPos.x - chunkPos2.x) + 1;
        int n2 = Math.abs(chunkPos.z - chunkPos2.z) + 1;
        int n3 = chunkPos.x < chunkPos2.x ? 1 : -1;
        int n4 = chunkPos.z < chunkPos2.z ? 1 : -1;
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<ChunkPos>((long)(n * n2), 64, chunkPos, chunkPos2, n4, n3){
            @Nullable
            private ChunkPos current;
            final ChunkPos val$start;
            final ChunkPos val$end;
            final int val$l;
            final int val$k;
            {
                this.val$start = chunkPos;
                this.val$end = chunkPos2;
                this.val$l = n2;
                this.val$k = n3;
                super(l, n);
            }

            @Override
            public boolean tryAdvance(Consumer<? super ChunkPos> consumer) {
                if (this.current == null) {
                    this.current = this.val$start;
                } else {
                    int n = this.current.x;
                    int n2 = this.current.z;
                    if (n == this.val$end.x) {
                        if (n2 == this.val$end.z) {
                            return true;
                        }
                        this.current = new ChunkPos(this.val$start.x, n2 + this.val$l);
                    } else {
                        this.current = new ChunkPos(n + this.val$k, n2);
                    }
                }
                consumer.accept(this.current);
                return false;
            }
        }, false);
    }
}

