/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WrappedBlockData;
import java.util.Arrays;
import java.util.Locale;

class ChestConnectionHandler
extends ConnectionHandler {
    private static final Int2ObjectMap<BlockFace> CHEST_FACINGS = new Int2ObjectOpenHashMap<BlockFace>();
    private static final int[] CONNECTED_STATES = new int[32];
    private static final IntSet TRAPPED_CHESTS = new IntOpenHashSet();

    ChestConnectionHandler() {
    }

    static ConnectionData.ConnectorInitAction init() {
        Arrays.fill(CONNECTED_STATES, -1);
        ChestConnectionHandler connectionHandler = new ChestConnectionHandler();
        return blockData -> {
            if (!blockData.getMinecraftKey().equals("minecraft:chest") && !blockData.getMinecraftKey().equals("minecraft:trapped_chest")) {
                return;
            }
            if (blockData.getValue("waterlogged").equals("true")) {
                return;
            }
            CHEST_FACINGS.put(blockData.getSavedBlockStateId(), BlockFace.valueOf(blockData.getValue("facing").toUpperCase(Locale.ROOT)));
            if (blockData.getMinecraftKey().equalsIgnoreCase("minecraft:trapped_chest")) {
                TRAPPED_CHESTS.add(blockData.getSavedBlockStateId());
            }
            ChestConnectionHandler.CONNECTED_STATES[ChestConnectionHandler.getStates((WrappedBlockData)blockData).byteValue()] = blockData.getSavedBlockStateId();
            ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), (ConnectionHandler)connectionHandler);
        };
    }

    private static Byte getStates(WrappedBlockData blockData) {
        byte states = 0;
        String type2 = blockData.getValue("type");
        if (type2.equals("left")) {
            states = (byte)(states | 1);
        }
        if (type2.equals("right")) {
            states = (byte)(states | 2);
        }
        states = (byte)(states | BlockFace.valueOf(blockData.getValue("facing").toUpperCase(Locale.ROOT)).ordinal() << 2);
        if (blockData.getMinecraftKey().equals("minecraft:trapped_chest")) {
            states = (byte)(states | 0x10);
        }
        return states;
    }

    @Override
    public int connect(UserConnection user, Position position, int blockState) {
        int relative;
        BlockFace facing = (BlockFace)((Object)CHEST_FACINGS.get(blockState));
        int states = 0;
        states = (byte)(states | facing.ordinal() << 2);
        boolean trapped = TRAPPED_CHESTS.contains(blockState);
        if (trapped) {
            states = (byte)(states | 0x10);
        }
        if (CHEST_FACINGS.containsKey(relative = this.getBlockData(user, position.getRelative(BlockFace.NORTH))) && trapped == TRAPPED_CHESTS.contains(relative)) {
            states = (byte)(states | (facing == BlockFace.WEST ? 1 : 2));
        } else {
            relative = this.getBlockData(user, position.getRelative(BlockFace.SOUTH));
            if (CHEST_FACINGS.containsKey(relative) && trapped == TRAPPED_CHESTS.contains(relative)) {
                states = (byte)(states | (facing == BlockFace.EAST ? 1 : 2));
            } else {
                relative = this.getBlockData(user, position.getRelative(BlockFace.WEST));
                if (CHEST_FACINGS.containsKey(relative) && trapped == TRAPPED_CHESTS.contains(relative)) {
                    states = (byte)(states | (facing == BlockFace.NORTH ? 2 : 1));
                } else {
                    relative = this.getBlockData(user, position.getRelative(BlockFace.EAST));
                    if (CHEST_FACINGS.containsKey(relative) && trapped == TRAPPED_CHESTS.contains(relative)) {
                        states = (byte)(states | (facing == BlockFace.SOUTH ? 2 : 1));
                    }
                }
            }
        }
        int newBlockState = CONNECTED_STATES[states];
        return newBlockState == -1 ? blockState : newBlockState;
    }
}

