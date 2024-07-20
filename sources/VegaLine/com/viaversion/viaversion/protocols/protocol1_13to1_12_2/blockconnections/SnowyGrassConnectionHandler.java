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
import java.util.HashSet;

public class SnowyGrassConnectionHandler
extends ConnectionHandler {
    private static final Object2IntMap<GrassBlock> GRASS_BLOCKS = new Object2IntOpenHashMap<GrassBlock>();
    private static final IntSet SNOWY_GRASS_BLOCKS = new IntOpenHashSet();

    static ConnectionData.ConnectorInitAction init() {
        HashSet<String> snowyGrassBlocks = new HashSet<String>();
        snowyGrassBlocks.add("minecraft:grass_block");
        snowyGrassBlocks.add("minecraft:podzol");
        snowyGrassBlocks.add("minecraft:mycelium");
        GRASS_BLOCKS.defaultReturnValue(-1);
        SnowyGrassConnectionHandler handler = new SnowyGrassConnectionHandler();
        return blockData -> {
            if (snowyGrassBlocks.contains(blockData.getMinecraftKey())) {
                ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), (ConnectionHandler)handler);
                blockData.set("snowy", "true");
                GRASS_BLOCKS.put(new GrassBlock(blockData.getSavedBlockStateId(), true), blockData.getBlockStateId());
                blockData.set("snowy", "false");
                GRASS_BLOCKS.put(new GrassBlock(blockData.getSavedBlockStateId(), false), blockData.getBlockStateId());
            }
            if (blockData.getMinecraftKey().equals("minecraft:snow") || blockData.getMinecraftKey().equals("minecraft:snow_block")) {
                ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), (ConnectionHandler)handler);
                SNOWY_GRASS_BLOCKS.add(blockData.getSavedBlockStateId());
            }
        };
    }

    @Override
    public int connect(UserConnection user, Position position, int blockState) {
        int blockUpId = this.getBlockData(user, position.getRelative(BlockFace.TOP));
        int newId = GRASS_BLOCKS.getInt(new GrassBlock(blockState, SNOWY_GRASS_BLOCKS.contains(blockUpId)));
        return newId != -1 ? newId : blockState;
    }

    private static final class GrassBlock {
        private final int blockStateId;
        private final boolean snowy;

        private GrassBlock(int blockStateId, boolean snowy) {
            this.blockStateId = blockStateId;
            this.snowy = snowy;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            GrassBlock that = (GrassBlock)o;
            if (this.blockStateId != that.blockStateId) {
                return false;
            }
            return this.snowy == that.snowy;
        }

        public int hashCode() {
            int result = this.blockStateId;
            result = 31 * result + (this.snowy ? 1 : 0);
            return result;
        }
    }
}

