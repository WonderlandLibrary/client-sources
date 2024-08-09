/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ChunkLightStorage
extends StoredObject {
    public static final byte[] FULL_LIGHT = new byte[2048];
    public static final byte[] EMPTY_LIGHT = new byte[2048];
    private static Constructor<?> fastUtilLongObjectHashMap;
    private final Map<Long, ChunkLight> storedLight = this.createLongObjectMap();

    public ChunkLightStorage(UserConnection userConnection) {
        super(userConnection);
    }

    public void setStoredLight(byte[][] byArray, byte[][] byArray2, int n, int n2) {
        this.storedLight.put(this.getChunkSectionIndex(n, n2), new ChunkLight(byArray, byArray2));
    }

    public ChunkLight getStoredLight(int n, int n2) {
        return this.storedLight.get(this.getChunkSectionIndex(n, n2));
    }

    public void clear() {
        this.storedLight.clear();
    }

    public void unloadChunk(int n, int n2) {
        this.storedLight.remove(this.getChunkSectionIndex(n, n2));
    }

    private long getChunkSectionIndex(int n, int n2) {
        return ((long)n & 0x3FFFFFFL) << 38 | (long)n2 & 0x3FFFFFFL;
    }

    private Map<Long, ChunkLight> createLongObjectMap() {
        if (fastUtilLongObjectHashMap != null) {
            try {
                return (Map)fastUtilLongObjectHashMap.newInstance(new Object[0]);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException reflectiveOperationException) {
                reflectiveOperationException.printStackTrace();
            }
        }
        return new HashMap<Long, ChunkLight>();
    }

    static {
        Arrays.fill(FULL_LIGHT, (byte)-1);
        Arrays.fill(EMPTY_LIGHT, (byte)0);
        try {
            fastUtilLongObjectHashMap = Class.forName("com.viaversion.viaversion.libs.fastutil.longs.Long2ObjectOpenHashMap").getConstructor(new Class[0]);
        } catch (ClassNotFoundException | NoSuchMethodException reflectiveOperationException) {
            // empty catch block
        }
    }

    public static class ChunkLight {
        private final byte[][] skyLight;
        private final byte[][] blockLight;

        public ChunkLight(byte[][] byArray, byte[][] byArray2) {
            this.skyLight = byArray;
            this.blockLight = byArray2;
        }

        public byte[][] getSkyLight() {
            return this.skyLight;
        }

        public byte[][] getBlockLight() {
            return this.blockLight;
        }
    }
}

