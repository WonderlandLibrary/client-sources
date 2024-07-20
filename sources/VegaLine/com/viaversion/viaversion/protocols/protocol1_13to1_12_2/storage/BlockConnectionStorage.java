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
    private long lastIndex = -1L;
    private SectionData lastSection;

    public static void init() {
    }

    public void store(int x, int y, int z, int blockState) {
        long index = BlockConnectionStorage.getChunkSectionIndex(x, y, z);
        SectionData section = this.getSection(index);
        if (section == null) {
            if (blockState == 0) {
                return;
            }
            section = new SectionData();
            this.blockStorage.put(index, section);
            this.lastSection = section;
            this.lastIndex = index;
        }
        section.setBlockAt(x, y, z, blockState);
    }

    public int get(int x, int y, int z) {
        long pair = BlockConnectionStorage.getChunkSectionIndex(x, y, z);
        SectionData section = this.getSection(pair);
        if (section == null) {
            return 0;
        }
        return section.blockAt(x, y, z);
    }

    public void remove(int x, int y, int z) {
        long index = BlockConnectionStorage.getChunkSectionIndex(x, y, z);
        SectionData section = this.getSection(index);
        if (section == null) {
            return;
        }
        section.setBlockAt(x, y, z, 0);
        if (section.nonEmptyBlocks() == 0) {
            this.removeSection(index);
        }
    }

    public void markModified(Position pos) {
        if (!this.modified.contains(pos)) {
            this.modified.add(pos);
        }
    }

    public boolean recentlyModified(Position pos) {
        for (Position p : this.modified) {
            if (Math.abs(pos.x() - p.x()) + Math.abs(pos.y() - p.y()) + Math.abs(pos.z() - p.z()) > 2) continue;
            return true;
        }
        return false;
    }

    public void clear() {
        this.blockStorage.clear();
        this.lastSection = null;
        this.lastIndex = -1L;
        this.modified.clear();
    }

    public void unloadChunk(int x, int z) {
        for (int y = 0; y < 16; ++y) {
            this.unloadSection(x, y, z);
        }
    }

    public void unloadSection(int x, int y, int z) {
        this.removeSection(BlockConnectionStorage.getChunkSectionIndex(x << 4, y << 4, z << 4));
    }

    private @Nullable SectionData getSection(long index) {
        if (this.lastIndex == index) {
            return this.lastSection;
        }
        this.lastIndex = index;
        this.lastSection = this.blockStorage.get(index);
        return this.lastSection;
    }

    private void removeSection(long index) {
        this.blockStorage.remove(index);
        if (this.lastIndex == index) {
            this.lastIndex = -1L;
            this.lastSection = null;
        }
    }

    private static long getChunkSectionIndex(int x, int y, int z) {
        return ((long)(x >> 4) & 0x3FFFFFFL) << 38 | ((long)(y >> 4) & 0xFFFL) << 26 | (long)(z >> 4) & 0x3FFFFFFL;
    }

    private <T> Map<Long, T> createLongObjectMap() {
        if (fastUtilLongObjectHashMap != null) {
            try {
                return (Map)fastUtilLongObjectHashMap.newInstance(new Object[0]);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return new HashMap();
    }

    static {
        try {
            String className = "it" + ".unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap";
            fastUtilLongObjectHashMap = Class.forName(className).getConstructor(new Class[0]);
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

        public int blockAt(int x, int y, int z) {
            return this.blockStates[SectionData.encodeBlockPos(x, y, z)];
        }

        public void setBlockAt(int x, int y, int z, int blockState) {
            int index = SectionData.encodeBlockPos(x, y, z);
            if (blockState == this.blockStates[index]) {
                return;
            }
            this.blockStates[index] = (short)blockState;
            this.nonEmptyBlocks = blockState == 0 ? (short)(this.nonEmptyBlocks - 1) : (short)(this.nonEmptyBlocks + 1);
        }

        public short nonEmptyBlocks() {
            return this.nonEmptyBlocks;
        }

        private static int encodeBlockPos(int x, int y, int z) {
            return (y & 0xF) << 8 | (x & 0xF) << 4 | z & 0xF;
        }
    }
}

