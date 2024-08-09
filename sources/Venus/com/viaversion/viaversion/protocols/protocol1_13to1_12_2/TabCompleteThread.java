/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.TabCompleteTracker;

public class TabCompleteThread
implements Runnable {
    @Override
    public void run() {
        for (UserConnection userConnection : Via.getManager().getConnectionManager().getConnections()) {
            if (userConnection.getProtocolInfo() == null || !userConnection.getProtocolInfo().getPipeline().contains(Protocol1_13To1_12_2.class) || !userConnection.getChannel().isOpen()) continue;
            userConnection.get(TabCompleteTracker.class).sendPacketToServer(userConnection);
        }
    }
}

