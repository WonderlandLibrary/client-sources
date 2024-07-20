/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectArrayMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WrappedBlockData;
import java.util.Arrays;
import java.util.Locale;

public class TripwireConnectionHandler
extends ConnectionHandler {
    private static final Int2ObjectMap<TripwireData> TRIPWIRE_DATA_MAP = new Int2ObjectOpenHashMap<TripwireData>();
    private static final Int2ObjectMap<BlockFace> TRIPWIRE_HOOKS = new Int2ObjectArrayMap<BlockFace>();
    private static final int[] CONNECTED_BLOCKS = new int[128];

    static ConnectionData.ConnectorInitAction init() {
        Arrays.fill(CONNECTED_BLOCKS, -1);
        TripwireConnectionHandler connectionHandler = new TripwireConnectionHandler();
        return blockData -> {
            if (blockData.getMinecraftKey().equals("minecraft:tripwire_hook")) {
                TRIPWIRE_HOOKS.put(blockData.getSavedBlockStateId(), BlockFace.valueOf(blockData.getValue("facing").toUpperCase(Locale.ROOT)));
            } else if (blockData.getMinecraftKey().equals("minecraft:tripwire")) {
                TripwireData tripwireData = new TripwireData(blockData.getValue("attached").equals("true"), blockData.getValue("disarmed").equals("true"), blockData.getValue("powered").equals("true"));
                TRIPWIRE_DATA_MAP.put(blockData.getSavedBlockStateId(), tripwireData);
                TripwireConnectionHandler.CONNECTED_BLOCKS[TripwireConnectionHandler.getStates((WrappedBlockData)blockData)] = blockData.getSavedBlockStateId();
                ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), (ConnectionHandler)connectionHandler);
            }
        };
    }

    private static byte getStates(WrappedBlockData blockData) {
        byte b = 0;
        if (blockData.getValue("attached").equals("true")) {
            b = (byte)(b | 1);
        }
        if (blockData.getValue("disarmed").equals("true")) {
            b = (byte)(b | 2);
        }
        if (blockData.getValue("powered").equals("true")) {
            b = (byte)(b | 4);
        }
        if (blockData.getValue("east").equals("true")) {
            b = (byte)(b | 8);
        }
        if (blockData.getValue("north").equals("true")) {
            b = (byte)(b | 0x10);
        }
        if (blockData.getValue("south").equals("true")) {
            b = (byte)(b | 0x20);
        }
        if (blockData.getValue("west").equals("true")) {
            b = (byte)(b | 0x40);
        }
        return b;
    }

    @Override
    public int connect(UserConnection user, Position position, int blockState) {
        int newBlockState;
        TripwireData tripwireData = (TripwireData)TRIPWIRE_DATA_MAP.get(blockState);
        if (tripwireData == null) {
            return blockState;
        }
        byte b = 0;
        if (tripwireData.isAttached()) {
            b = (byte)(b | 1);
        }
        if (tripwireData.isDisarmed()) {
            b = (byte)(b | 2);
        }
        if (tripwireData.isPowered()) {
            b = (byte)(b | 4);
        }
        int east = this.getBlockData(user, position.getRelative(BlockFace.EAST));
        int north = this.getBlockData(user, position.getRelative(BlockFace.NORTH));
        int south = this.getBlockData(user, position.getRelative(BlockFace.SOUTH));
        int west = this.getBlockData(user, position.getRelative(BlockFace.WEST));
        if (TRIPWIRE_DATA_MAP.containsKey(east) || TRIPWIRE_HOOKS.get(east) == BlockFace.WEST) {
            b = (byte)(b | 8);
        }
        if (TRIPWIRE_DATA_MAP.containsKey(north) || TRIPWIRE_HOOKS.get(north) == BlockFace.SOUTH) {
            b = (byte)(b | 0x10);
        }
        if (TRIPWIRE_DATA_MAP.containsKey(south) || TRIPWIRE_HOOKS.get(south) == BlockFace.NORTH) {
            b = (byte)(b | 0x20);
        }
        if (TRIPWIRE_DATA_MAP.containsKey(west) || TRIPWIRE_HOOKS.get(west) == BlockFace.EAST) {
            b = (byte)(b | 0x40);
        }
        return (newBlockState = CONNECTED_BLOCKS[b]) == -1 ? blockState : newBlockState;
    }

    private static final class TripwireData {
        private final boolean attached;
        private final boolean disarmed;
        private final boolean powered;

        private TripwireData(boolean attached, boolean disarmed, boolean powered) {
            this.attached = attached;
            this.disarmed = disarmed;
            this.powered = powered;
        }

        public boolean isAttached() {
            return this.attached;
        }

        public boolean isDisarmed() {
            return this.disarmed;
        }

        public boolean isPowered() {
            return this.powered;
        }
    }
}

