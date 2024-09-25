/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections;

import java.util.ArrayList;
import java.util.List;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections.AbstractFenceConnectionHandler;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;

public class BasicFenceConnectionHandler
extends AbstractFenceConnectionHandler {
    static List<ConnectionData.ConnectorInitAction> init() {
        ArrayList<ConnectionData.ConnectorInitAction> actions = new ArrayList<ConnectionData.ConnectorInitAction>();
        actions.add(new BasicFenceConnectionHandler("fenceConnections").getInitAction("minecraft:oak_fence"));
        actions.add(new BasicFenceConnectionHandler("fenceConnections").getInitAction("minecraft:birch_fence"));
        actions.add(new BasicFenceConnectionHandler("fenceConnections").getInitAction("minecraft:jungle_fence"));
        actions.add(new BasicFenceConnectionHandler("fenceConnections").getInitAction("minecraft:dark_oak_fence"));
        actions.add(new BasicFenceConnectionHandler("fenceConnections").getInitAction("minecraft:acacia_fence"));
        actions.add(new BasicFenceConnectionHandler("fenceConnections").getInitAction("minecraft:spruce_fence"));
        return actions;
    }

    public BasicFenceConnectionHandler(String blockConnections) {
        super(blockConnections);
    }
}

