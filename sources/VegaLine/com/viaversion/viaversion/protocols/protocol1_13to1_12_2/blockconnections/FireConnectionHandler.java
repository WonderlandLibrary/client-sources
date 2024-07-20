/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WrappedBlockData;
import java.util.HashSet;
import java.util.Set;

public class FireConnectionHandler
extends ConnectionHandler {
    private static final String[] WOOD_TYPES = new String[]{"oak", "spruce", "birch", "jungle", "acacia", "dark_oak"};
    private static final int[] CONNECTED_BLOCKS = new int[32];
    private static final IntSet FLAMMABLE_BLOCKS = new IntOpenHashSet();

    private static void addWoodTypes(Set<String> set, String suffix) {
        for (String woodType : WOOD_TYPES) {
            set.add("minecraft:" + woodType + suffix);
        }
    }

    static ConnectionData.ConnectorInitAction init() {
        HashSet<String> flammabeIds = new HashSet<String>();
        flammabeIds.add("minecraft:tnt");
        flammabeIds.add("minecraft:vine");
        flammabeIds.add("minecraft:bookshelf");
        flammabeIds.add("minecraft:hay_block");
        flammabeIds.add("minecraft:deadbush");
        FireConnectionHandler.addWoodTypes(flammabeIds, "_slab");
        FireConnectionHandler.addWoodTypes(flammabeIds, "_log");
        FireConnectionHandler.addWoodTypes(flammabeIds, "_planks");
        FireConnectionHandler.addWoodTypes(flammabeIds, "_leaves");
        FireConnectionHandler.addWoodTypes(flammabeIds, "_fence");
        FireConnectionHandler.addWoodTypes(flammabeIds, "_fence_gate");
        FireConnectionHandler.addWoodTypes(flammabeIds, "_stairs");
        FireConnectionHandler connectionHandler = new FireConnectionHandler();
        return blockData -> {
            String key = blockData.getMinecraftKey();
            if (key.contains("_wool") || key.contains("_carpet") || flammabeIds.contains(key)) {
                FLAMMABLE_BLOCKS.add(blockData.getSavedBlockStateId());
            } else if (key.equals("minecraft:fire")) {
                int id;
                FireConnectionHandler.CONNECTED_BLOCKS[FireConnectionHandler.getStates((WrappedBlockData)blockData)] = id = blockData.getSavedBlockStateId();
                ConnectionData.connectionHandlerMap.put(id, (ConnectionHandler)connectionHandler);
            }
        };
    }

    private static byte getStates(WrappedBlockData blockData) {
        byte states = 0;
        if (blockData.getValue("east").equals("true")) {
            states = (byte)(states | 1);
        }
        if (blockData.getValue("north").equals("true")) {
            states = (byte)(states | 2);
        }
        if (blockData.getValue("south").equals("true")) {
            states = (byte)(states | 4);
        }
        if (blockData.getValue("up").equals("true")) {
            states = (byte)(states | 8);
        }
        if (blockData.getValue("west").equals("true")) {
            states = (byte)(states | 0x10);
        }
        return states;
    }

    @Override
    public int connect(UserConnection user, Position position, int blockState) {
        byte states = 0;
        if (FLAMMABLE_BLOCKS.contains(this.getBlockData(user, position.getRelative(BlockFace.EAST)))) {
            states = (byte)(states | 1);
        }
        if (FLAMMABLE_BLOCKS.contains(this.getBlockData(user, position.getRelative(BlockFace.NORTH)))) {
            states = (byte)(states | 2);
        }
        if (FLAMMABLE_BLOCKS.contains(this.getBlockData(user, position.getRelative(BlockFace.SOUTH)))) {
            states = (byte)(states | 4);
        }
        if (FLAMMABLE_BLOCKS.contains(this.getBlockData(user, position.getRelative(BlockFace.TOP)))) {
            states = (byte)(states | 8);
        }
        if (FLAMMABLE_BLOCKS.contains(this.getBlockData(user, position.getRelative(BlockFace.WEST)))) {
            states = (byte)(states | 0x10);
        }
        return CONNECTED_BLOCKS[states];
    }
}

