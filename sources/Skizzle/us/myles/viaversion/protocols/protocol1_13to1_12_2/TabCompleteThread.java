/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_13to1_12_2;

import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.storage.TabCompleteTracker;

public class TabCompleteThread
implements Runnable {
    @Override
    public void run() {
        for (UserConnection info : Via.getManager().getConnections()) {
            if (info.getProtocolInfo() == null || !info.getProtocolInfo().getPipeline().contains(Protocol1_13To1_12_2.class) || !info.getChannel().isOpen()) continue;
            info.get(TabCompleteTracker.class).sendPacketToServer();
        }
    }
}

