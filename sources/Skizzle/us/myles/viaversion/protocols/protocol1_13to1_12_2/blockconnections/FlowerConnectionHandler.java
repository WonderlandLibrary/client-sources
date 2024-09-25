/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections;

import java.util.HashSet;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.minecraft.BlockFace;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler;
import us.myles.viaversion.libs.fastutil.ints.Int2IntMap;
import us.myles.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;

public class FlowerConnectionHandler
extends ConnectionHandler {
    private static final Int2IntMap flowers = new Int2IntOpenHashMap();

    static ConnectionData.ConnectorInitAction init() {
        HashSet<String> baseFlower = new HashSet<String>();
        baseFlower.add("minecraft:rose_bush");
        baseFlower.add("minecraft:sunflower");
        baseFlower.add("minecraft:peony");
        baseFlower.add("minecraft:tall_grass");
        baseFlower.add("minecraft:large_fern");
        baseFlower.add("minecraft:lilac");
        FlowerConnectionHandler handler = new FlowerConnectionHandler();
        return blockData -> {
            if (baseFlower.contains(blockData.getMinecraftKey())) {
                ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), (ConnectionHandler)handler);
                if (blockData.getValue("half").equals("lower")) {
                    blockData.set("half", "upper");
                    flowers.put(blockData.getSavedBlockStateId(), blockData.getBlockStateId());
                }
            }
        };
    }

    @Override
    public int connect(UserConnection user, Position position, int blockState) {
        int blockBelowId = this.getBlockData(user, position.getRelative(BlockFace.BOTTOM));
        int connectBelow = flowers.get(blockBelowId);
        if (connectBelow != 0) {
            int blockAboveId = this.getBlockData(user, position.getRelative(BlockFace.TOP));
            if (Via.getConfig().isStemWhenBlockAbove() ? blockAboveId == 0 : !flowers.containsKey(blockAboveId)) {
                return connectBelow;
            }
        }
        return blockState;
    }
}

