/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.PlayerLookTargetProvider;

public class TabCompleteTracker
implements StorableObject {
    private int transactionId;
    private String input;
    private String lastTabComplete;
    private long timeToSend;

    public void sendPacketToServer(UserConnection userConnection) {
        if (this.lastTabComplete == null || this.timeToSend > System.currentTimeMillis()) {
            return;
        }
        PacketWrapper packetWrapper = PacketWrapper.create(ServerboundPackets1_12_1.TAB_COMPLETE, null, userConnection);
        packetWrapper.write(Type.STRING, this.lastTabComplete);
        packetWrapper.write(Type.BOOLEAN, false);
        Position position = Via.getManager().getProviders().get(PlayerLookTargetProvider.class).getPlayerLookTarget(userConnection);
        packetWrapper.write(Type.OPTIONAL_POSITION, position);
        try {
            packetWrapper.scheduleSendToServer(Protocol1_13To1_12_2.class);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        this.lastTabComplete = null;
    }

    public int getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(int n) {
        this.transactionId = n;
    }

    public String getInput() {
        return this.input;
    }

    public void setInput(String string) {
        this.input = string;
    }

    public String getLastTabComplete() {
        return this.lastTabComplete;
    }

    public void setLastTabComplete(String string) {
        this.lastTabComplete = string;
    }

    public long getTimeToSend() {
        return this.timeToSend;
    }

    public void setTimeToSend(long l) {
        this.timeToSend = l;
    }
}

