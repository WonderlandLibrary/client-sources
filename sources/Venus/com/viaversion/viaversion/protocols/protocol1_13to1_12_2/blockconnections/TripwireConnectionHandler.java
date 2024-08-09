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
        TripwireConnectionHandler tripwireConnectionHandler = new TripwireConnectionHandler();
        return arg_0 -> TripwireConnectionHandler.lambda$init$0(tripwireConnectionHandler, arg_0);
    }

    private static byte getStates(WrappedBlockData wrappedBlockData) {
        byte by = 0;
        if (wrappedBlockData.getValue("attached").equals("true")) {
            by = (byte)(by | 1);
        }
        if (wrappedBlockData.getValue("disarmed").equals("true")) {
            by = (byte)(by | 2);
        }
        if (wrappedBlockData.getValue("powered").equals("true")) {
            by = (byte)(by | 4);
        }
        if (wrappedBlockData.getValue("east").equals("true")) {
            by = (byte)(by | 8);
        }
        if (wrappedBlockData.getValue("north").equals("true")) {
            by = (byte)(by | 0x10);
        }
        if (wrappedBlockData.getValue("south").equals("true")) {
            by = (byte)(by | 0x20);
        }
        if (wrappedBlockData.getValue("west").equals("true")) {
            by = (byte)(by | 0x40);
        }
        return by;
    }

    @Override
    public int connect(UserConnection userConnection, Position position, int n) {
        int n2;
        TripwireData tripwireData = (TripwireData)TRIPWIRE_DATA_MAP.get(n);
        if (tripwireData == null) {
            return n;
        }
        byte by = 0;
        if (tripwireData.isAttached()) {
            by = (byte)(by | 1);
        }
        if (tripwireData.isDisarmed()) {
            by = (byte)(by | 2);
        }
        if (tripwireData.isPowered()) {
            by = (byte)(by | 4);
        }
        int n3 = this.getBlockData(userConnection, position.getRelative(BlockFace.EAST));
        int n4 = this.getBlockData(userConnection, position.getRelative(BlockFace.NORTH));
        int n5 = this.getBlockData(userConnection, position.getRelative(BlockFace.SOUTH));
        int n6 = this.getBlockData(userConnection, position.getRelative(BlockFace.WEST));
        if (TRIPWIRE_DATA_MAP.containsKey(n3) || TRIPWIRE_HOOKS.get(n3) == BlockFace.WEST) {
            by = (byte)(by | 8);
        }
        if (TRIPWIRE_DATA_MAP.containsKey(n4) || TRIPWIRE_HOOKS.get(n4) == BlockFace.SOUTH) {
            by = (byte)(by | 0x10);
        }
        if (TRIPWIRE_DATA_MAP.containsKey(n5) || TRIPWIRE_HOOKS.get(n5) == BlockFace.NORTH) {
            by = (byte)(by | 0x20);
        }
        if (TRIPWIRE_DATA_MAP.containsKey(n6) || TRIPWIRE_HOOKS.get(n6) == BlockFace.EAST) {
            by = (byte)(by | 0x40);
        }
        return (n2 = CONNECTED_BLOCKS[by]) == -1 ? n : n2;
    }

    private static void lambda$init$0(TripwireConnectionHandler tripwireConnectionHandler, WrappedBlockData wrappedBlockData) {
        if (wrappedBlockData.getMinecraftKey().equals("minecraft:tripwire_hook")) {
            TRIPWIRE_HOOKS.put(wrappedBlockData.getSavedBlockStateId(), BlockFace.valueOf(wrappedBlockData.getValue("facing").toUpperCase(Locale.ROOT)));
        } else if (wrappedBlockData.getMinecraftKey().equals("minecraft:tripwire")) {
            TripwireData tripwireData = new TripwireData(wrappedBlockData.getValue("attached").equals("true"), wrappedBlockData.getValue("disarmed").equals("true"), wrappedBlockData.getValue("powered").equals("true"), null);
            TRIPWIRE_DATA_MAP.put(wrappedBlockData.getSavedBlockStateId(), tripwireData);
            TripwireConnectionHandler.CONNECTED_BLOCKS[TripwireConnectionHandler.getStates((WrappedBlockData)wrappedBlockData)] = wrappedBlockData.getSavedBlockStateId();
            ConnectionData.connectionHandlerMap.put(wrappedBlockData.getSavedBlockStateId(), (ConnectionHandler)tripwireConnectionHandler);
        }
    }

    private static final class TripwireData {
        private final boolean attached;
        private final boolean disarmed;
        private final boolean powered;

        private TripwireData(boolean bl, boolean bl2, boolean bl3) {
            this.attached = bl;
            this.disarmed = bl2;
            this.powered = bl3;
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

        TripwireData(boolean bl, boolean bl2, boolean bl3, 1 var4_4) {
            this(bl, bl2, bl3);
        }
    }
}

