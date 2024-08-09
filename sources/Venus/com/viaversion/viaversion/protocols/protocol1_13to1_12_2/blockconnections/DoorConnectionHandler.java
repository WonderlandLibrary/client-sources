/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WrappedBlockData;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DoorConnectionHandler
extends ConnectionHandler {
    private static final Int2ObjectMap<DoorData> DOOR_DATA_MAP = new Int2ObjectOpenHashMap<DoorData>();
    private static final Map<Short, Integer> CONNECTED_STATES = new HashMap<Short, Integer>();

    static ConnectionData.ConnectorInitAction init() {
        LinkedList<String> linkedList = new LinkedList<String>();
        linkedList.add("minecraft:oak_door");
        linkedList.add("minecraft:birch_door");
        linkedList.add("minecraft:jungle_door");
        linkedList.add("minecraft:dark_oak_door");
        linkedList.add("minecraft:acacia_door");
        linkedList.add("minecraft:spruce_door");
        linkedList.add("minecraft:iron_door");
        DoorConnectionHandler doorConnectionHandler = new DoorConnectionHandler();
        return arg_0 -> DoorConnectionHandler.lambda$init$0(linkedList, doorConnectionHandler, arg_0);
    }

    private static short getStates(DoorData doorData) {
        short s = 0;
        if (doorData.isLower()) {
            s = (short)(s | 1);
        }
        if (doorData.isOpen()) {
            s = (short)(s | 2);
        }
        if (doorData.isPowered()) {
            s = (short)(s | 4);
        }
        if (doorData.isRightHinge()) {
            s = (short)(s | 8);
        }
        s = (short)(s | doorData.getFacing().ordinal() << 4);
        s = (short)(s | (doorData.getType() & 7) << 6);
        return s;
    }

    @Override
    public int connect(UserConnection userConnection, Position position, int n) {
        Object object;
        DoorData doorData = (DoorData)DOOR_DATA_MAP.get(n);
        if (doorData == null) {
            return n;
        }
        short s = 0;
        s = (short)(s | (doorData.getType() & 7) << 6);
        if (doorData.isLower()) {
            object = (DoorData)DOOR_DATA_MAP.get(this.getBlockData(userConnection, position.getRelative(BlockFace.TOP)));
            if (object == null) {
                return n;
            }
            s = (short)(s | 1);
            if (doorData.isOpen()) {
                s = (short)(s | 2);
            }
            if (((DoorData)object).isPowered()) {
                s = (short)(s | 4);
            }
            if (((DoorData)object).isRightHinge()) {
                s = (short)(s | 8);
            }
            s = (short)(s | doorData.getFacing().ordinal() << 4);
        } else {
            object = (DoorData)DOOR_DATA_MAP.get(this.getBlockData(userConnection, position.getRelative(BlockFace.BOTTOM)));
            if (object == null) {
                return n;
            }
            if (((DoorData)object).isOpen()) {
                s = (short)(s | 2);
            }
            if (doorData.isPowered()) {
                s = (short)(s | 4);
            }
            if (doorData.isRightHinge()) {
                s = (short)(s | 8);
            }
            s = (short)(s | ((DoorData)object).getFacing().ordinal() << 4);
        }
        object = CONNECTED_STATES.get(s);
        return object == null ? n : (Integer)object;
    }

    private static void lambda$init$0(List list, DoorConnectionHandler doorConnectionHandler, WrappedBlockData wrappedBlockData) {
        int n = list.indexOf(wrappedBlockData.getMinecraftKey());
        if (n == -1) {
            return;
        }
        int n2 = wrappedBlockData.getSavedBlockStateId();
        DoorData doorData = new DoorData(wrappedBlockData.getValue("half").equals("lower"), wrappedBlockData.getValue("hinge").equals("right"), wrappedBlockData.getValue("powered").equals("true"), wrappedBlockData.getValue("open").equals("true"), BlockFace.valueOf(wrappedBlockData.getValue("facing").toUpperCase(Locale.ROOT)), n, null);
        DOOR_DATA_MAP.put(n2, doorData);
        CONNECTED_STATES.put(DoorConnectionHandler.getStates(doorData), n2);
        ConnectionData.connectionHandlerMap.put(n2, (ConnectionHandler)doorConnectionHandler);
    }

    private static final class DoorData {
        private final boolean lower;
        private final boolean rightHinge;
        private final boolean powered;
        private final boolean open;
        private final BlockFace facing;
        private final int type;

        private DoorData(boolean bl, boolean bl2, boolean bl3, boolean bl4, BlockFace blockFace, int n) {
            this.lower = bl;
            this.rightHinge = bl2;
            this.powered = bl3;
            this.open = bl4;
            this.facing = blockFace;
            this.type = n;
        }

        public boolean isLower() {
            return this.lower;
        }

        public boolean isRightHinge() {
            return this.rightHinge;
        }

        public boolean isPowered() {
            return this.powered;
        }

        public boolean isOpen() {
            return this.open;
        }

        public BlockFace getFacing() {
            return this.facing;
        }

        public int getType() {
            return this.type;
        }

        DoorData(boolean bl, boolean bl2, boolean bl3, boolean bl4, BlockFace blockFace, int n, 1 var7_7) {
            this(bl, bl2, bl3, bl4, blockFace, n);
        }
    }
}

