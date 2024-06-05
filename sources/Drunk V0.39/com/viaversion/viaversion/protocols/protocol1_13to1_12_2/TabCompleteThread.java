/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.TabCompleteTracker;
import java.util.Iterator;

public class TabCompleteThread
implements Runnable {
    @Override
    public void run() {
        Iterator<UserConnection> iterator = Via.getManager().getConnectionManager().getConnections().iterator();
        while (iterator.hasNext()) {
            UserConnection info = iterator.next();
            if (info.getProtocolInfo() == null || !info.getProtocolInfo().getPipeline().contains(Protocol1_13To1_12_2.class) || !info.getChannel().isOpen()) continue;
            info.get(TabCompleteTracker.class).sendPacketToServer(info);
        }
    }
}

