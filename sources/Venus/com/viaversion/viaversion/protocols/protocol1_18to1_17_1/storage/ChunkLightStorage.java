/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class ChunkLightStorage
implements StorableObject {
    private final Map<Long, ChunkLight> lightPackets = new HashMap<Long, ChunkLight>();
    private final Set<Long> loadedChunks = new HashSet<Long>();

    public void storeLight(int n, int n2, ChunkLight chunkLight) {
        this.lightPackets.put(this.getChunkSectionIndex(n, n2), chunkLight);
    }

    public @Nullable ChunkLight removeLight(int n, int n2) {
        return this.lightPackets.remove(this.getChunkSectionIndex(n, n2));
    }

    public @Nullable ChunkLight getLight(int n, int n2) {
        return this.lightPackets.get(this.getChunkSectionIndex(n, n2));
    }

    public boolean addLoadedChunk(int n, int n2) {
        return this.loadedChunks.add(this.getChunkSectionIndex(n, n2));
    }

    public boolean isLoaded(int n, int n2) {
        return this.loadedChunks.contains(this.getChunkSectionIndex(n, n2));
    }

    public void clear(int n, int n2) {
        long l = this.getChunkSectionIndex(n, n2);
        this.lightPackets.remove(l);
        this.loadedChunks.remove(l);
    }

    public void clear() {
        this.loadedChunks.clear();
        this.lightPackets.clear();
    }

    private long getChunkSectionIndex(int n, int n2) {
        return ((long)n & 0x3FFFFFFL) << 38 | (long)n2 & 0x3FFFFFFL;
    }

    public static final class ChunkLight {
        private final boolean trustEdges;
        private final long[] skyLightMask;
        private final long[] blockLightMask;
        private final long[] emptySkyLightMask;
        private final long[] emptyBlockLightMask;
        private final byte[][] skyLight;
        private final byte[][] blockLight;

        public ChunkLight(boolean bl, long[] lArray, long[] lArray2, long[] lArray3, long[] lArray4, byte[][] byArray, byte[][] byArray2) {
            this.trustEdges = bl;
            this.skyLightMask = lArray;
            this.emptySkyLightMask = lArray3;
            this.blockLightMask = lArray2;
            this.emptyBlockLightMask = lArray4;
            this.skyLight = byArray;
            this.blockLight = byArray2;
        }

        public boolean trustEdges() {
            return this.trustEdges;
        }

        public long[] skyLightMask() {
            return this.skyLightMask;
        }

        public long[] emptySkyLightMask() {
            return this.emptySkyLightMask;
        }

        public long[] blockLightMask() {
            return this.blockLightMask;
        }

        public long[] emptyBlockLightMask() {
            return this.emptyBlockLightMask;
        }

        public byte[][] skyLight() {
            return this.skyLight;
        }

        public byte[][] blockLight() {
            return this.blockLight;
        }
    }
}

