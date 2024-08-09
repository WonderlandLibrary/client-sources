/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class PPSSubCmd
extends ViaSubCommand {
    @Override
    public String name() {
        return "pps";
    }

    @Override
    public String description() {
        return "Shows the packets per second of online players";
    }

    @Override
    public String usage() {
        return "pps";
    }

    @Override
    public boolean execute(ViaCommandSender viaCommandSender, String[] stringArray) {
        HashMap hashMap = new HashMap();
        int n = 0;
        int n2 = 0;
        long l = 0L;
        for (ViaCommandSender viaCommandSender2 : Via.getPlatform().getOnlinePlayers()) {
            UserConnection userConnection;
            int n3 = Via.getAPI().getPlayerVersion(viaCommandSender2.getUUID());
            if (!hashMap.containsKey(n3)) {
                hashMap.put(n3, new HashSet());
            }
            if ((userConnection = Via.getManager().getConnectionManager().getConnectedClient(viaCommandSender2.getUUID())) == null || userConnection.getPacketTracker().getPacketsPerSecond() <= -1L) continue;
            ((Set)hashMap.get(n3)).add(viaCommandSender2.getName() + " (" + userConnection.getPacketTracker().getPacketsPerSecond() + " PPS)");
            n = (int)((long)n + userConnection.getPacketTracker().getPacketsPerSecond());
            if (userConnection.getPacketTracker().getPacketsPerSecond() > l) {
                l = userConnection.getPacketTracker().getPacketsPerSecond();
            }
            ++n2;
        }
        TreeMap treeMap = new TreeMap(hashMap);
        PPSSubCmd.sendMessage(viaCommandSender, "&4Live Packets Per Second", new Object[0]);
        if (n2 > 1) {
            PPSSubCmd.sendMessage(viaCommandSender, "&cAverage: &f" + n / n2, new Object[0]);
            PPSSubCmd.sendMessage(viaCommandSender, "&cHighest: &f" + l, new Object[0]);
        }
        if (n2 == 0) {
            PPSSubCmd.sendMessage(viaCommandSender, "&cNo clients to display.", new Object[0]);
        }
        for (Map.Entry entry : treeMap.entrySet()) {
            PPSSubCmd.sendMessage(viaCommandSender, "&8[&6%s&8]: &b%s", ProtocolVersion.getProtocol((Integer)entry.getKey()).getName(), entry.getValue());
        }
        treeMap.clear();
        return false;
    }
}

