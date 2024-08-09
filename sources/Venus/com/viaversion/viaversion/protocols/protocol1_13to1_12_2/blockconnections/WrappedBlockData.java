/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import com.viaversion.viaversion.util.Key;
import java.util.LinkedHashMap;
import java.util.Map;

public final class WrappedBlockData {
    private final LinkedHashMap<String, String> blockData = new LinkedHashMap();
    private final String minecraftKey;
    private final int savedBlockStateId;

    public static WrappedBlockData fromString(String string) {
        String[] stringArray = string.split("\\[");
        String string2 = stringArray[0];
        WrappedBlockData wrappedBlockData = new WrappedBlockData(string2, ConnectionData.getId(string));
        if (stringArray.length > 1) {
            String[] stringArray2;
            String string3 = stringArray[5];
            string3 = string3.replace("]", "");
            for (String string4 : stringArray2 = string3.split(",")) {
                String[] stringArray3 = string4.split("=");
                wrappedBlockData.blockData.put(stringArray3[0], stringArray3[5]);
            }
        }
        return wrappedBlockData;
    }

    private WrappedBlockData(String string, int n) {
        this.minecraftKey = Key.namespaced(string);
        this.savedBlockStateId = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.minecraftKey + "[");
        for (Map.Entry<String, String> entry : this.blockData.entrySet()) {
            stringBuilder.append(entry.getKey()).append('=').append(entry.getValue()).append(',');
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1) + "]";
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

    public WrappedBlockData set(String string, Object object) {
        if (!this.hasData(string)) {
            throw new UnsupportedOperationException("No blockdata found for " + string + " at " + this.minecraftKey);
        }
        this.blockData.put(string, object.toString());
        return this;
    }

    public String getValue(String string) {
        return this.blockData.get(string);
    }

    public boolean hasData(String string) {
        return this.blockData.containsKey(string);
    }
}

