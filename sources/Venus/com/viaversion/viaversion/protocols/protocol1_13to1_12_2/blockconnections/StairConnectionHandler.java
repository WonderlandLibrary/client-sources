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

public class StairConnectionHandler
extends ConnectionHandler {
    private static final Int2ObjectMap<StairData> STAIR_DATA_MAP = new Int2ObjectOpenHashMap<StairData>();
    private static final Map<Short, Integer> CONNECTED_BLOCKS = new HashMap<Short, Integer>();

    static ConnectionData.ConnectorInitAction init() {
        LinkedList<String> linkedList = new LinkedList<String>();
        linkedList.add("minecraft:oak_stairs");
        linkedList.add("minecraft:cobblestone_stairs");
        linkedList.add("minecraft:brick_stairs");
        linkedList.add("minecraft:stone_brick_stairs");
        linkedList.add("minecraft:nether_brick_stairs");
        linkedList.add("minecraft:sandstone_stairs");
        linkedList.add("minecraft:spruce_stairs");
        linkedList.add("minecraft:birch_stairs");
        linkedList.add("minecraft:jungle_stairs");
        linkedList.add("minecraft:quartz_stairs");
        linkedList.add("minecraft:acacia_stairs");
        linkedList.add("minecraft:dark_oak_stairs");
        linkedList.add("minecraft:red_sandstone_stairs");
        linkedList.add("minecraft:purpur_stairs");
        linkedList.add("minecraft:prismarine_stairs");
        linkedList.add("minecraft:prismarine_brick_stairs");
        linkedList.add("minecraft:dark_prismarine_stairs");
        StairConnectionHandler stairConnectionHandler = new StairConnectionHandler();
        return arg_0 -> StairConnectionHandler.lambda$init$0(linkedList, stairConnectionHandler, arg_0);
    }

    private static short getStates(StairData stairData) {
        short s = 0;
        if (stairData.isBottom()) {
            s = (short)(s | 1);
        }
        s = (short)(s | stairData.getShape() << 1);
        s = (short)(s | stairData.getType() << 4);
        s = (short)(s | stairData.getFacing().ordinal() << 9);
        return s;
    }

    @Override
    public int connect(UserConnection userConnection, Position position, int n) {
        StairData stairData = (StairData)STAIR_DATA_MAP.get(n);
        if (stairData == null) {
            return n;
        }
        short s = 0;
        if (stairData.isBottom()) {
            s = (short)(s | 1);
        }
        s = (short)(s | this.getShape(userConnection, position, stairData) << 1);
        s = (short)(s | stairData.getType() << 4);
        Integer n2 = CONNECTED_BLOCKS.get(s = (short)(s | stairData.getFacing().ordinal() << 9));
        return n2 == null ? n : n2;
    }

    private int getShape(UserConnection userConnection, Position position, StairData stairData) {
        BlockFace blockFace;
        BlockFace blockFace2 = stairData.getFacing();
        StairData stairData2 = (StairData)STAIR_DATA_MAP.get(this.getBlockData(userConnection, position.getRelative(blockFace2)));
        if (stairData2 != null && stairData2.isBottom() == stairData.isBottom()) {
            blockFace = stairData2.getFacing();
            if (blockFace2.axis() != blockFace.axis() && this.checkOpposite(userConnection, stairData, position, blockFace.opposite())) {
                return blockFace == this.rotateAntiClockwise(blockFace2) ? 3 : 4;
            }
        }
        if ((stairData2 = (StairData)STAIR_DATA_MAP.get(this.getBlockData(userConnection, position.getRelative(blockFace2.opposite())))) != null && stairData2.isBottom() == stairData.isBottom()) {
            blockFace = stairData2.getFacing();
            if (blockFace2.axis() != blockFace.axis() && this.checkOpposite(userConnection, stairData, position, blockFace)) {
                return blockFace == this.rotateAntiClockwise(blockFace2) ? 1 : 2;
            }
        }
        return 1;
    }

    private boolean checkOpposite(UserConnection userConnection, StairData stairData, Position position, BlockFace blockFace) {
        StairData stairData2 = (StairData)STAIR_DATA_MAP.get(this.getBlockData(userConnection, position.getRelative(blockFace)));
        return stairData2 == null || stairData2.getFacing() != stairData.getFacing() || stairData2.isBottom() != stairData.isBottom();
    }

    private BlockFace rotateAntiClockwise(BlockFace blockFace) {
        switch (1.$SwitchMap$com$viaversion$viaversion$api$minecraft$BlockFace[blockFace.ordinal()]) {
            case 1: {
                return BlockFace.WEST;
            }
            case 2: {
                return BlockFace.EAST;
            }
            case 3: {
                return BlockFace.NORTH;
            }
            case 4: {
                return BlockFace.SOUTH;
            }
        }
        return blockFace;
    }

    private static void lambda$init$0(List list, StairConnectionHandler stairConnectionHandler, WrappedBlockData wrappedBlockData) {
        byte by;
        int n = list.indexOf(wrappedBlockData.getMinecraftKey());
        if (n == -1) {
            return;
        }
        if (wrappedBlockData.getValue("waterlogged").equals("true")) {
            return;
        }
        switch (wrappedBlockData.getValue("shape")) {
            case "straight": {
                by = 0;
                break;
            }
            case "inner_left": {
                by = 1;
                break;
            }
            case "inner_right": {
                by = 2;
                break;
            }
            case "outer_left": {
                by = 3;
                break;
            }
            case "outer_right": {
                by = 4;
                break;
            }
            default: {
                return;
            }
        }
        Object object = new StairData(wrappedBlockData.getValue("half").equals("bottom"), by, (byte)n, BlockFace.valueOf(wrappedBlockData.getValue("facing").toUpperCase(Locale.ROOT)), null);
        STAIR_DATA_MAP.put(wrappedBlockData.getSavedBlockStateId(), (StairData)object);
        CONNECTED_BLOCKS.put(StairConnectionHandler.getStates((StairData)object), wrappedBlockData.getSavedBlockStateId());
        ConnectionData.connectionHandlerMap.put(wrappedBlockData.getSavedBlockStateId(), (ConnectionHandler)stairConnectionHandler);
    }

    private static final class StairData {
        private final boolean bottom;
        private final byte shape;
        private final byte type;
        private final BlockFace facing;

        private StairData(boolean bl, byte by, byte by2, BlockFace blockFace) {
            this.bottom = bl;
            this.shape = by;
            this.type = by2;
            this.facing = blockFace;
        }

        public boolean isBottom() {
            return this.bottom;
        }

        public byte getShape() {
            return this.shape;
        }

        public byte getType() {
            return this.type;
        }

        public BlockFace getFacing() {
            return this.facing;
        }

        StairData(boolean bl, byte by, byte by2, BlockFace blockFace, 1 var5_5) {
            this(bl, by, by2, blockFace);
        }
    }
}

