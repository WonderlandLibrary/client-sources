/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections;

import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections.AbstractStempConnectionHandler;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;

public class PumpkinConnectionHandler
extends AbstractStempConnectionHandler {
    static ConnectionData.ConnectorInitAction init() {
        return new PumpkinConnectionHandler("minecraft:pumpkin_stem[age=7]").getInitAction("minecraft:carved_pumpkin", "minecraft:attached_pumpkin_stem");
    }

    public PumpkinConnectionHandler(String baseStateId) {
        super(baseStateId);
    }
}

