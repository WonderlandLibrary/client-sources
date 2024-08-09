/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.AbstractFenceConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import java.util.ArrayList;
import java.util.List;

public class GlassConnectionHandler
extends AbstractFenceConnectionHandler {
    static List<ConnectionData.ConnectorInitAction> init() {
        ArrayList<ConnectionData.ConnectorInitAction> arrayList = new ArrayList<ConnectionData.ConnectorInitAction>(18);
        arrayList.add(new GlassConnectionHandler("pane").getInitAction("minecraft:white_stained_glass_pane"));
        arrayList.add(new GlassConnectionHandler("pane").getInitAction("minecraft:orange_stained_glass_pane"));
        arrayList.add(new GlassConnectionHandler("pane").getInitAction("minecraft:magenta_stained_glass_pane"));
        arrayList.add(new GlassConnectionHandler("pane").getInitAction("minecraft:light_blue_stained_glass_pane"));
        arrayList.add(new GlassConnectionHandler("pane").getInitAction("minecraft:yellow_stained_glass_pane"));
        arrayList.add(new GlassConnectionHandler("pane").getInitAction("minecraft:lime_stained_glass_pane"));
        arrayList.add(new GlassConnectionHandler("pane").getInitAction("minecraft:pink_stained_glass_pane"));
        arrayList.add(new GlassConnectionHandler("pane").getInitAction("minecraft:gray_stained_glass_pane"));
        arrayList.add(new GlassConnectionHandler("pane").getInitAction("minecraft:light_gray_stained_glass_pane"));
        arrayList.add(new GlassConnectionHandler("pane").getInitAction("minecraft:cyan_stained_glass_pane"));
        arrayList.add(new GlassConnectionHandler("pane").getInitAction("minecraft:purple_stained_glass_pane"));
        arrayList.add(new GlassConnectionHandler("pane").getInitAction("minecraft:blue_stained_glass_pane"));
        arrayList.add(new GlassConnectionHandler("pane").getInitAction("minecraft:brown_stained_glass_pane"));
        arrayList.add(new GlassConnectionHandler("pane").getInitAction("minecraft:green_stained_glass_pane"));
        arrayList.add(new GlassConnectionHandler("pane").getInitAction("minecraft:red_stained_glass_pane"));
        arrayList.add(new GlassConnectionHandler("pane").getInitAction("minecraft:black_stained_glass_pane"));
        arrayList.add(new GlassConnectionHandler("pane").getInitAction("minecraft:glass_pane"));
        arrayList.add(new GlassConnectionHandler("pane").getInitAction("minecraft:iron_bars"));
        return arrayList;
    }

    public GlassConnectionHandler(String string) {
        super(string);
    }

    @Override
    protected byte getStates(UserConnection userConnection, Position position, int n) {
        int n2 = super.getStates(userConnection, position, n);
        if (n2 != 0) {
            return (byte)n2;
        }
        ProtocolInfo protocolInfo = userConnection.getProtocolInfo();
        return (byte)(protocolInfo.getServerProtocolVersion() <= 47 && protocolInfo.getServerProtocolVersion() != -1 ? 15 : n2);
    }
}

