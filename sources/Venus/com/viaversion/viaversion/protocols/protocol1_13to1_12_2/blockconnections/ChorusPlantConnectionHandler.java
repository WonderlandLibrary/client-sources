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

public class ChorusPlantConnectionHandler
extends AbstractFenceConnectionHandler {
    private final int endstone = ConnectionData.getId("minecraft:end_stone");

    static List<ConnectionData.ConnectorInitAction> init() {
        ArrayList<ConnectionData.ConnectorInitAction> arrayList = new ArrayList<ConnectionData.ConnectorInitAction>(2);
        ChorusPlantConnectionHandler chorusPlantConnectionHandler = new ChorusPlantConnectionHandler();
        arrayList.add(chorusPlantConnectionHandler.getInitAction("minecraft:chorus_plant"));
        arrayList.add(chorusPlantConnectionHandler.getExtraAction());
        return arrayList;
    }

    public ChorusPlantConnectionHandler() {
        super(null);
    }

    public ConnectionData.ConnectorInitAction getExtraAction() {
        return this::lambda$getExtraAction$0;
    }

    @Override
    protected byte getStates(WrappedBlockData wrappedBlockData) {
        byte by = super.getStates(wrappedBlockData);
        if (wrappedBlockData.getValue("up").equals("true")) {
            by = (byte)(by | 0x10);
        }
        if (wrappedBlockData.getValue("down").equals("true")) {
            by = (byte)(by | 0x20);
        }
        return by;
    }

    @Override
    protected byte statesSize() {
        return 1;
    }

    @Override
    protected byte getStates(UserConnection userConnection, Position position, int n) {
        byte by = super.getStates(userConnection, position, n);
        if (this.connects(BlockFace.TOP, this.getBlockData(userConnection, position.getRelative(BlockFace.TOP)), true)) {
            by = (byte)(by | 0x10);
        }
        if (this.connects(BlockFace.BOTTOM, this.getBlockData(userConnection, position.getRelative(BlockFace.BOTTOM)), true)) {
            by = (byte)(by | 0x20);
        }
        return by;
    }

    @Override
    protected boolean connects(BlockFace blockFace, int n, boolean bl) {
        return this.getBlockStates().contains(n) || blockFace == BlockFace.BOTTOM && n == this.endstone;
    }

    private void lambda$getExtraAction$0(WrappedBlockData wrappedBlockData) {
        if (wrappedBlockData.getMinecraftKey().equals("minecraft:chorus_flower")) {
            this.getBlockStates().add(wrappedBlockData.getSavedBlockStateId());
        }
    }
}

