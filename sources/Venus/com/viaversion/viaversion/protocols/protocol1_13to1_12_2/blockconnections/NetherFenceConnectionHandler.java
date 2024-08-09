/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.AbstractFenceConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;

public class NetherFenceConnectionHandler
extends AbstractFenceConnectionHandler {
    static ConnectionData.ConnectorInitAction init() {
        return new NetherFenceConnectionHandler("netherFence").getInitAction("minecraft:nether_brick_fence");
    }

    public NetherFenceConnectionHandler(String string) {
        super(string);
    }
}

