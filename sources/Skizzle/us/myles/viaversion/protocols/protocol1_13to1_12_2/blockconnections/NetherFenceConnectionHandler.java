/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections;

import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections.AbstractFenceConnectionHandler;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;

public class NetherFenceConnectionHandler
extends AbstractFenceConnectionHandler {
    static ConnectionData.ConnectorInitAction init() {
        return new NetherFenceConnectionHandler("netherFenceConnections").getInitAction("minecraft:nether_brick_fence");
    }

    public NetherFenceConnectionHandler(String blockConnections) {
        super(blockConnections);
    }
}

