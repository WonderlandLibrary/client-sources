/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.MovementTracker;

public class ViaIdleThread
implements Runnable {
    @Override
    public void run() {
        for (UserConnection userConnection : Via.getManager().getConnectionManager().getConnections()) {
            long l;
            MovementTracker movementTracker;
            ProtocolInfo protocolInfo = userConnection.getProtocolInfo();
            if (protocolInfo == null || !protocolInfo.getPipeline().contains(Protocol1_9To1_8.class) || (movementTracker = userConnection.get(MovementTracker.class)) == null || (l = movementTracker.getNextIdlePacket()) > System.currentTimeMillis() || !userConnection.getChannel().isOpen()) continue;
            Via.getManager().getProviders().get(MovementTransmitterProvider.class).sendPlayer(userConnection);
        }
    }
}

