/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WrappedBlockData;
import java.util.HashSet;
import java.util.Set;

public class FlowerConnectionHandler
extends ConnectionHandler {
    private static final Int2IntMap FLOWERS = new Int2IntOpenHashMap();

    static ConnectionData.ConnectorInitAction init() {
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.add("minecraft:rose_bush");
        hashSet.add("minecraft:sunflower");
        hashSet.add("minecraft:peony");
        hashSet.add("minecraft:tall_grass");
        hashSet.add("minecraft:large_fern");
        hashSet.add("minecraft:lilac");
        FlowerConnectionHandler flowerConnectionHandler = new FlowerConnectionHandler();
        return arg_0 -> FlowerConnectionHandler.lambda$init$0(hashSet, flowerConnectionHandler, arg_0);
    }

    @Override
    public int connect(UserConnection userConnection, Position position, int n) {
        int n2 = this.getBlockData(userConnection, position.getRelative(BlockFace.BOTTOM));
        int n3 = FLOWERS.get(n2);
        if (n3 != 0) {
            int n4 = this.getBlockData(userConnection, position.getRelative(BlockFace.TOP));
            if (Via.getConfig().isStemWhenBlockAbove() ? n4 == 0 : !FLOWERS.containsKey(n4)) {
                return n3;
            }
        }
        return n;
    }

    private static void lambda$init$0(Set set, FlowerConnectionHandler flowerConnectionHandler, WrappedBlockData wrappedBlockData) {
        if (set.contains(wrappedBlockData.getMinecraftKey())) {
            ConnectionData.connectionHandlerMap.put(wrappedBlockData.getSavedBlockStateId(), (ConnectionHandler)flowerConnectionHandler);
            if (wrappedBlockData.getValue("half").equals("lower")) {
                wrappedBlockData.set("half", "upper");
                FLOWERS.put(wrappedBlockData.getSavedBlockStateId(), wrappedBlockData.getBlockStateId());
            }
        }
    }
}

