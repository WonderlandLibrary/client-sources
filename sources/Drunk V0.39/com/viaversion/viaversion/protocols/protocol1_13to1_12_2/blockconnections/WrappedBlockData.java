/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class WrappedBlockData {
    private final String minecraftKey;
    private final int savedBlockStateId;
    private final LinkedHashMap<String, String> blockData = new LinkedHashMap();

    public static WrappedBlockData fromString(String s) {
        String[] data;
        String[] array = s.split("\\[");
        String key = array[0];
        WrappedBlockData wrappedBlockdata = new WrappedBlockData(key, ConnectionData.getId(s));
        if (array.length <= 1) return wrappedBlockdata;
        String blockData = array[1];
        blockData = blockData.replace("]", "");
        String[] stringArray = data = blockData.split(",");
        int n = stringArray.length;
        int n2 = 0;
        while (n2 < n) {
            String d = stringArray[n2];
            String[] a = d.split("=");
            wrappedBlockdata.blockData.put(a[0], a[1]);
            ++n2;
        }
        return wrappedBlockdata;
    }

    public static WrappedBlockData fromStateId(int id) {
        String blockData = ConnectionData.getKey(id);
        if (blockData != null) {
            return WrappedBlockData.fromString(blockData);
        }
        Via.getPlatform().getLogger().info("Unable to get blockdata from " + id);
        return WrappedBlockData.fromString("minecraft:air");
    }

    private WrappedBlockData(String minecraftKey, int savedBlockStateId) {
        this.minecraftKey = minecraftKey;
        this.savedBlockStateId = savedBlockStateId;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.minecraftKey + "[");
        Iterator<Map.Entry<String, String>> iterator = this.blockData.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            sb.append(entry.getKey()).append('=').append(entry.getValue()).append(',');
        }
        return sb.substring(0, sb.length() - 1) + "]";
    }

    public String getMinecraftKey() {
        return this.minecraftKey;
    }

    public int getSavedBlockStateId() {
        return this.savedBlockStateId;
    }

    public int getBlockStateId() {
        return ConnectionData.getId(this.toString());
    }

    public WrappedBlockData set(String data, Object value) {
        if (!this.hasData(data)) {
            throw new UnsupportedOperationException("No blockdata found for " + data + " at " + this.minecraftKey);
        }
        this.blockData.put(data, value.toString());
        return this;
    }

    public String getValue(String data) {
        return this.blockData.get(data);
    }

    public boolean hasData(String key) {
        return this.blockData.containsKey(key);
    }
}

