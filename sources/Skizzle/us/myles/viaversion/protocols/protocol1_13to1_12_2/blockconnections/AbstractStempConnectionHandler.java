/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.minecraft.BlockFace;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler;

public abstract class AbstractStempConnectionHandler
extends ConnectionHandler {
    private static final BlockFace[] BLOCK_FACES = new BlockFace[]{BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST};
    private final int baseStateId;
    private final Set<Integer> blockId = new HashSet<Integer>();
    private final Map<BlockFace, Integer> stemps = new HashMap<BlockFace, Integer>();

    protected AbstractStempConnectionHandler(String baseStateId) {
        this.baseStateId = ConnectionData.getId(baseStateId);
    }

    public ConnectionData.ConnectorInitAction getInitAction(String blockId, String toKey) {
        AbstractStempConnectionHandler handler = this;
        return blockData -> {
            if (blockData.getSavedBlockStateId() == this.baseStateId || blockId.equals(blockData.getMinecraftKey())) {
                if (blockData.getSavedBlockStateId() != this.baseStateId) {
                    handler.blockId.add(blockData.getSavedBlockStateId());
                }
                ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), (ConnectionHandler)handler);
            }
            if (blockData.getMinecraftKey().equals(toKey)) {
                String facing = blockData.getValue("facing").toUpperCase(Locale.ROOT);
                this.stemps.put(BlockFace.valueOf(facing), blockData.getSavedBlockStateId());
            }
        };
    }

    @Override
    public int connect(UserConnection user, Position position, int blockState) {
        if (blockState != this.baseStateId) {
            return blockState;
        }
        for (BlockFace blockFace : BLOCK_FACES) {
            if (!this.blockId.contains(this.getBlockData(user, position.getRelative(blockFace)))) continue;
            return this.stemps.get((Object)blockFace);
        }
        return this.baseStateId;
    }
}

