/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.bukkit.tasks.protocol1_19to1_18_2;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ClientboundPackets1_19;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.Protocol1_19To1_18_2;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.storage.SequenceStorage;

public final class AckSequenceTask
implements Runnable {
    private final UserConnection connection;
    private final SequenceStorage sequenceStorage;

    public AckSequenceTask(UserConnection userConnection, SequenceStorage sequenceStorage) {
        this.connection = userConnection;
        this.sequenceStorage = sequenceStorage;
    }

    @Override
    public void run() {
        int n = this.sequenceStorage.setSequenceId(-1);
        try {
            PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_19.BLOCK_CHANGED_ACK, this.connection);
            packetWrapper.write(Type.VAR_INT, n);
            packetWrapper.scheduleSend(Protocol1_19To1_18_2.class);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

