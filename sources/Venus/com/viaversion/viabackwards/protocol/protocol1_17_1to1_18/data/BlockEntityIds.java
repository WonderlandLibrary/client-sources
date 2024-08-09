/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.data;

import java.util.Arrays;

public final class BlockEntityIds {
    private static final int[] IDS;

    public static int mappedId(int n) {
        if (n < 0 || n > IDS.length) {
            return 1;
        }
        return IDS[n];
    }

    static {
        int[] nArray = com.viaversion.viaversion.protocols.protocol1_18to1_17_1.BlockEntityIds.getIds();
        IDS = new int[Arrays.stream(nArray).max().getAsInt() + 1];
        Arrays.fill(IDS, -1);
        for (int i = 0; i < nArray.length; ++i) {
            int n = nArray[i];
            if (n == -1) continue;
            BlockEntityIds.IDS[n] = i;
        }
    }
}

