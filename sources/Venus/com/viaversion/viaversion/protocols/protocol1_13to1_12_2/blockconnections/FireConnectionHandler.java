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

    private static void addWoodTypes(Set<String> set, String string) {
        for (String string2 : WOOD_TYPES) {
            set.add("minecraft:" + string2 + string);
        }
    }

    static ConnectionData.ConnectorInitAction init() {
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.add("minecraft:tnt");
        hashSet.add("minecraft:vine");
        hashSet.add("minecraft:bookshelf");
        hashSet.add("minecraft:hay_block");
        hashSet.add("minecraft:deadbush");
        FireConnectionHandler.addWoodTypes(hashSet, "_slab");
        FireConnectionHandler.addWoodTypes(hashSet, "_log");
        FireConnectionHandler.addWoodTypes(hashSet, "_planks");
        FireConnectionHandler.addWoodTypes(hashSet, "_leaves");
        FireConnectionHandler.addWoodTypes(hashSet, "_fence");
        FireConnectionHandler.addWoodTypes(hashSet, "_fence_gate");
        FireConnectionHandler.addWoodTypes(hashSet, "_stairs");
        FireConnectionHandler fireConnectionHandler = new FireConnectionHandler();
        return arg_0 -> FireConnectionHandler.lambda$init$0(hashSet, fireConnectionHandler, arg_0);
    }

    private static byte getStates(WrappedBlockData wrappedBlockData) {
        byte by = 0;
        if (wrappedBlockData.getValue("east").equals("true")) {
            by = (byte)(by | 1);
        }
        if (wrappedBlockData.getValue("north").equals("true")) {
            by = (byte)(by | 2);
        }
        if (wrappedBlockData.getValue("south").equals("true")) {
            by = (byte)(by | 4);
        }
        if (wrappedBlockData.getValue("up").equals("true")) {
            by = (byte)(by | 8);
        }
        if (wrappedBlockData.getValue("west").equals("true")) {
            by = (byte)(by | 0x10);
        }
        return by;
    }

    @Override
    public int connect(UserConnection userConnection, Position position, int n) {
        byte by = 0;
        if (FLAMMABLE_BLOCKS.contains(this.getBlockData(userConnection, position.getRelative(BlockFace.EAST)))) {
            by = (byte)(by | 1);
        }
        if (FLAMMABLE_BLOCKS.contains(this.getBlockData(userConnection, position.getRelative(BlockFace.NORTH)))) {
            by = (byte)(by | 2);
        }
        if (FLAMMABLE_BLOCKS.contains(this.getBlockData(userConnection, position.getRelative(BlockFace.SOUTH)))) {
            by = (byte)(by | 4);
        }
        if (FLAMMABLE_BLOCKS.contains(this.getBlockData(userConnection, position.getRelative(BlockFace.TOP)))) {
            by = (byte)(by | 8);
        }
        if (FLAMMABLE_BLOCKS.contains(this.getBlockData(userConnection, position.getRelative(BlockFace.WEST)))) {
            by = (byte)(by | 0x10);
        }
        return CONNECTED_BLOCKS[by];
    }

    private static void lambda$init$0(Set set, FireConnectionHandler fireConnectionHandler, WrappedBlockData wrappedBlockData) {
        String string = wrappedBlockData.getMinecraftKey();
        if (string.contains("_wool") || string.contains("_carpet") || set.contains(string)) {
            FLAMMABLE_BLOCKS.add(wrappedBlockData.getSavedBlockStateId());
        } else if (string.equals("minecraft:fire")) {
            int n;
            FireConnectionHandler.CONNECTED_BLOCKS[FireConnectionHandler.getStates((WrappedBlockData)wrappedBlockData)] = n = wrappedBlockData.getSavedBlockStateId();
            ConnectionData.connectionHandlerMap.put(n, (ConnectionHandler)fireConnectionHandler);
        }
    }
}

