/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.data;

import java.util.Arrays;

public final class BlockEntityIds {
    private static final int[] IDS;

    public static int mappedId(int id) {
        if (id < 0) return -1;
        if (id <= IDS.length) return IDS[id];
        return -1;
    }

    static {
        int[] ids = com.viaversion.viaversion.protocols.protocol1_18to1_17_1.BlockEntityIds.getIds();
        IDS = new int[Arrays.stream(ids).max().getAsInt() + 1];
        Arrays.fill(IDS, -1);
        int i = 0;
        while (i < ids.length) {
            int id = ids[i];
            if (id != -1) {
                BlockEntityIds.IDS[id] = i;
            }
            ++i;
        }
    }
}

