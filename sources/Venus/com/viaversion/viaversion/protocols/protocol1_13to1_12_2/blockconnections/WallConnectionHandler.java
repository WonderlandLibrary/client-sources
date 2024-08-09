/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.AbstractFenceConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WrappedBlockData;
import java.util.ArrayList;
import java.util.List;

public class WallConnectionHandler
extends AbstractFenceConnectionHandler {
    private static final BlockFace[] BLOCK_FACES = new BlockFace[]{BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST};
    private static final int[] OPPOSITES = new int[]{3, 2, 1, 0};

    static List<ConnectionData.ConnectorInitAction> init() {
        ArrayList<ConnectionData.ConnectorInitAction> arrayList = new ArrayList<ConnectionData.ConnectorInitAction>(2);
        arrayList.add(new WallConnectionHandler("cobbleWall").getInitAction("minecraft:cobblestone_wall"));
        arrayList.add(new WallConnectionHandler("cobbleWall").getInitAction("minecraft:mossy_cobblestone_wall"));
        return arrayList;
    }

    public WallConnectionHandler(String string) {
        super(string);
    }

    @Override
    protected byte getStates(WrappedBlockData wrappedBlockData) {
        byte by = super.getStates(wrappedBlockData);
        if (wrappedBlockData.getValue("up").equals("true")) {
            by = (byte)(by | 0x10);
        }
        return by;
    }

    @Override
    protected byte getStates(UserConnection userConnection, Position position, int n) {
        byte by = super.getStates(userConnection, position, n);
        if (this.up(userConnection, position)) {
            by = (byte)(by | 0x10);
        }
        return by;
    }

    @Override
    protected byte statesSize() {
        return 1;
    }

    public boolean up(UserConnection userConnection, Position position) {
        if (this.isWall(this.getBlockData(userConnection, position.getRelative(BlockFace.BOTTOM))) || this.isWall(this.getBlockData(userConnection, position.getRelative(BlockFace.TOP)))) {
            return false;
        }
        int n = this.getBlockFaces(userConnection, position);
        if (n == 0 || n == 15) {
            return false;
        }
        for (int i = 0; i < BLOCK_FACES.length; ++i) {
            if ((n & 1 << i) == 0 || (n & 1 << OPPOSITES[i]) != 0) continue;
            return false;
        }
        return true;
    }

    private int getBlockFaces(UserConnection userConnection, Position position) {
        int n = 0;
        for (int i = 0; i < BLOCK_FACES.length; ++i) {
            if (!this.isWall(this.getBlockData(userConnection, position.getRelative(BLOCK_FACES[i])))) continue;
            n |= 1 << i;
        }
        return n;
    }

    private boolean isWall(int n) {
        return this.getBlockStates().contains(n);
    }
}

