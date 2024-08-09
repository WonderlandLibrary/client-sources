/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectArrayMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import java.util.Arrays;
import java.util.List;

public final class BlockData {
    private static final List<String> CONNECTION_TYPES = Arrays.asList("fence", "netherFence", "pane", "cobbleWall", "redstone", "allFalseIfStairPre1_12");
    private static final int MAGIC_STAIRS_ID = BlockData.connectionTypeId("allFalseIfStairPre1_12");
    private final Int2ObjectMap<boolean[]> connectData = new Int2ObjectArrayMap<boolean[]>();

    public void put(int n, boolean[] blArray) {
        this.connectData.put(n, blArray);
    }

    public boolean connectsTo(int n, BlockFace blockFace, boolean bl) {
        if (bl && this.connectData.containsKey(MAGIC_STAIRS_ID)) {
            return true;
        }
        boolean[] blArray = (boolean[])this.connectData.get(n);
        return blArray != null && blArray[blockFace.ordinal()];
    }

    public static int connectionTypeId(String string) {
        int n = CONNECTION_TYPES.indexOf(string);
        Preconditions.checkArgument(n != -1, "Unknown connection type: " + string);
        return n;
    }
}

