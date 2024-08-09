/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_18to1_17_1;

import com.viaversion.viaversion.api.Via;
import java.util.Arrays;

public final class BlockEntityIds {
    private static final int[] IDS = new int[14];

    public static int newId(int n) {
        int n2;
        if (n < 0 || n > IDS.length || (n2 = IDS[n]) == -1) {
            Via.getPlatform().getLogger().warning("Received out of bounds block entity id: " + n);
            return 1;
        }
        return n2;
    }

    public static int[] getIds() {
        return IDS;
    }

    static {
        Arrays.fill(IDS, -1);
        BlockEntityIds.IDS[1] = 8;
        BlockEntityIds.IDS[2] = 21;
        BlockEntityIds.IDS[3] = 13;
        BlockEntityIds.IDS[4] = 14;
        BlockEntityIds.IDS[5] = 24;
        BlockEntityIds.IDS[6] = 18;
        BlockEntityIds.IDS[7] = 19;
        BlockEntityIds.IDS[8] = 20;
        BlockEntityIds.IDS[9] = 7;
        BlockEntityIds.IDS[10] = 22;
        BlockEntityIds.IDS[11] = 23;
        BlockEntityIds.IDS[12] = 30;
        BlockEntityIds.IDS[13] = 31;
    }
}

