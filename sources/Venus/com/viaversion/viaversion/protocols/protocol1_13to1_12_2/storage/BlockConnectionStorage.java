/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage;

import com.google.common.collect.EvictingQueue;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.Position;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BlockConnectionStorage
implements StorableObject {
    private static Constructor<?> fastUtilLongObjectHashMap;
    private final Map<Long, SectionData> blockStorage = this.createLongObjectMap();
    private final Queue<Position> modified = EvictingQueue.create(5);
    private Long lastIndex;
    private SectionData lastSection;

    public static void init() {
    }

    public void store(int n, int n2, int n3, int n4) {
        long l = BlockConnectionStorage.getChunkSectionIndex(n, n2, n3);
        SectionData sectionData = this.getSection(l);
        if (sectionData == null) {
            if (n4 == 0) {
                return;
            }
            sectionData = new SectionData(null);
            this.blockStorage.put(l, sectionData);
            this.lastSection = sectionData;
            this.lastIndex = l;
        }
        sectionData.setBlockAt(n, n2, n3, n4);
    }

    public int get(int n, int n2, int n3) {
        long l = BlockConnectionStorage.getChunkSectionIndex(n, n2, n3);
        SectionData sectionData = this.getSection(l);
        if (sectionData == null) {
            return 1;
        }
        return sectionData.blockAt(n, n2, n3);
    }

    public void remove(int n, int n2, int n3) {
        long l = BlockConnectionStorage.getChunkSectionIndex(n, n2, n3);
        SectionData sectionData = this.getSection(l);
        if (sectionData == null) {
            return;
        }
        sectionData.setBlockAt(n, n2, n3, 0);
        if (sectionData.nonEmptyBlocks() == 0) {
            this.removeSection(l);
        }
    }

    public void markModified(Position position) {
        if (!this.modified.contains(position)) {
            this.modified.add(position);
        }
    }

    public boolean recentlyModified(Position position) {
        for (Position position2 : this.modified) {
            if (Math.abs(position.x() - position2.x()) + Math.abs(position.y() - position2.y()) + Math.abs(position.z() - position2.z()) > 2) continue;
            return false;
        }
        return true;
    }

    public void clear() {
        this.blockStorage.clear();
        this.lastSection = null;
        this.lastIndex = null;
        this.modified.clear();
    }

    public void unloadChunk(int n, int n2) {
        for (int i = 0; i < 16; ++i) {
            this.unloadSection(n, i, n2);
        }
    }

    public void unloadSection(int n, int n2, int n3) {
        this.removeSection(BlockConnectionStorage.getChunkSectionIndex(n << 4, n2 << 4, n3 << 4));
    }

    private @Nullable SectionData getSection(long l) {
        if (this.lastIndex != null && this.lastIndex == l) {
            return this.lastSection;
        }
        this.lastIndex = l;
        this.lastSection = this.blockStorage.get(l);
        return this.lastSection;
    }

    private void removeSection(long l) {
        this.blockStorage.remove(l);
        if (this.lastIndex != null && this.lastIndex == l) {
            this.lastIndex = null;
            this.lastSection = null;
        }
    }

    private static long getChunkSectionIndex(int n, int n2, int n3) {
        return ((long)(n >> 4) & 0x3FFFFFFL) << 38 | ((long)(n2 >> 4) & 0xFFFL) << 26 | (long)(n3 >> 4) & 0x3FFFFFFL;
    }

    private <T> Map<Long, T> createLongObjectMap() {
        if (fastUtilLongObjectHashMap != null) {
            try {
                return (Map)fastUtilLongObjectHashMap.newInstance(new Object[0]);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException reflectiveOperationException) {
                reflectiveOperationException.printStackTrace();
            }
        }
        return new HashMap();
    }

    static {
        try {
            String string = "it" + ".unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap";
            fastUtilLongObjectHashMap = Class.forName(string).getConstructor(new Class[0]);
            Via.getPlatform().getLogger().info("Using FastUtil Long2ObjectOpenHashMap for block connections");
        } catch (ClassNotFoundException | NoSuchMethodException reflectiveOperationException) {
            // empty catch block
        }
    }

    private static final class SectionData {
        private final short[] blockStates = new short[4096];
        private short nonEmptyBlocks;

        private SectionData() {
        }

        public int blockAt(int n, int n2, int n3) {
            return this.blockStates[SectionData.encodeBlockPos(n, n2, n3)];
        }

        public void setBlockAt(int n, int n2, int n3, int n4) {
            int n5 = SectionData.encodeBlockPos(n, n2, n3);
            if (n4 == this.blockStates[n5]) {
                return;
            }
            this.blockStates[n5] = (short)n4;
            this.nonEmptyBlocks = n4 == 0 ? (short)(this.nonEmptyBlocks - 1) : (short)(this.nonEmptyBlocks + 1);
        }

        public short nonEmptyBlocks() {
            return this.nonEmptyBlocks;
        }

        private static int encodeBlockPos(int n, int n2, int n3) {
            return (n2 & 0xF) << 8 | (n & 0xF) << 4 | n3 & 0xF;
        }

        SectionData(1 var1_1) {
            this();
        }
    }
}

