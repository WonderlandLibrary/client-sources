/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.MovementTracker;
import java.util.Iterator;

public class ViaIdleThread
implements Runnable {
    @Override
    public void run() {
        Iterator<UserConnection> iterator = Via.getManager().getConnectionManager().getConnections().iterator();
        while (iterator.hasNext()) {
            long nextIdleUpdate;
            MovementTracker movementTracker;
            UserConnection info = iterator.next();
            ProtocolInfo protocolInfo = info.getProtocolInfo();
            if (protocolInfo == null || !protocolInfo.getPipeline().contains(Protocol1_9To1_8.class) || (movementTracker = info.get(MovementTracker.class)) == null || (nextIdleUpdate = movementTracker.getNextIdlePacket()) > System.currentTimeMillis() || !info.getChannel().isOpen()) continue;
            Via.getManager().getProviders().get(MovementTransmitterProvider.class).sendPlayer(info);
        }
    }
}

