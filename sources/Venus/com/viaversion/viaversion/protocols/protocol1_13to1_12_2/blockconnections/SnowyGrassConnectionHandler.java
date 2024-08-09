/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WrappedBlockData;
import java.util.HashSet;
import java.util.Set;

public class SnowyGrassConnectionHandler
extends ConnectionHandler {
    private static final Object2IntMap<GrassBlock> GRASS_BLOCKS = new Object2IntOpenHashMap<GrassBlock>();
    private static final IntSet SNOWY_GRASS_BLOCKS = new IntOpenHashSet();

    static ConnectionData.ConnectorInitAction init() {
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.add("minecraft:grass_block");
        hashSet.add("minecraft:podzol");
        hashSet.add("minecraft:mycelium");
        GRASS_BLOCKS.defaultReturnValue(-1);
        SnowyGrassConnectionHandler snowyGrassConnectionHandler = new SnowyGrassConnectionHandler();
        return arg_0 -> SnowyGrassConnectionHandler.lambda$init$0(hashSet, snowyGrassConnectionHandler, arg_0);
    }

    @Override
    public int connect(UserConnection userConnection, Position position, int n) {
        int n2 = this.getBlockData(userConnection, position.getRelative(BlockFace.TOP));
        int n3 = GRASS_BLOCKS.getInt(new GrassBlock(n, SNOWY_GRASS_BLOCKS.contains(n2), null));
        return n3 != -1 ? n3 : n;
    }

    private static void lambda$init$0(Set set, SnowyGrassConnectionHandler snowyGrassConnectionHandler, WrappedBlockData wrappedBlockData) {
        if (set.contains(wrappedBlockData.getMinecraftKey())) {
            ConnectionData.connectionHandlerMap.put(wrappedBlockData.getSavedBlockStateId(), (ConnectionHandler)snowyGrassConnectionHandler);
            wrappedBlockData.set("snowy", "true");
            GRASS_BLOCKS.put(new GrassBlock(wrappedBlockData.getSavedBlockStateId(), true, null), wrappedBlockData.getBlockStateId());
            wrappedBlockData.set("snowy", "false");
            GRASS_BLOCKS.put(new GrassBlock(wrappedBlockData.getSavedBlockStateId(), false, null), wrappedBlockData.getBlockStateId());
        }
        if (wrappedBlockData.getMinecraftKey().equals("minecraft:snow") || wrappedBlockData.getMinecraftKey().equals("minecraft:snow_block")) {
            ConnectionData.connectionHandlerMap.put(wrappedBlockData.getSavedBlockStateId(), (ConnectionHandler)snowyGrassConnectionHandler);
            SNOWY_GRASS_BLOCKS.add(wrappedBlockData.getSavedBlockStateId());
        }
    }

    private static final class GrassBlock {
        private final int blockStateId;
        private final boolean snowy;

        private GrassBlock(int n, boolean bl) {
            this.blockStateId = n;
            this.snowy = bl;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return true;
            }
            GrassBlock grassBlock = (GrassBlock)object;
            if (this.blockStateId != grassBlock.blockStateId) {
                return true;
            }
            return this.snowy == grassBlock.snowy;
        }

        public int hashCode() {
            int n = this.blockStateId;
            n = 31 * n + (this.snowy ? 1 : 0);
            return n;
        }

        GrassBlock(int n, boolean bl, 1 var3_3) {
            this(n, bl);
        }
    }
}

