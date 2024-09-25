/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.commands.defaultsubs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.command.ViaCommandSender;
import us.myles.ViaVersion.api.command.ViaSubCommand;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;

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
    public boolean execute(ViaCommandSender sender, String[] args) {
        HashMap playerVersions = new HashMap();
        int totalPackets = 0;
        int clients = 0;
        long max = 0L;
        for (ViaCommandSender p : Via.getPlatform().getOnlinePlayers()) {
            UserConnection uc;
            int playerVersion = Via.getAPI().getPlayerVersion(p.getUUID());
            if (!playerVersions.containsKey(playerVersion)) {
                playerVersions.put(playerVersion, new HashSet());
            }
            if ((uc = Via.getManager().getConnection(p.getUUID())) == null || uc.getPacketsPerSecond() <= -1L) continue;
            ((Set)playerVersions.get(playerVersion)).add(p.getName() + " (" + uc.getPacketsPerSecond() + " PPS)");
            totalPackets = (int)((long)totalPackets + uc.getPacketsPerSecond());
            if (uc.getPacketsPerSecond() > max) {
                max = uc.getPacketsPerSecond();
            }
            ++clients;
        }
        TreeMap sorted = new TreeMap(playerVersions);
        this.sendMessage(sender, "&4Live Packets Per Second", new Object[0]);
        if (clients > 1) {
            this.sendMessage(sender, "&cAverage: &f" + totalPackets / clients, new Object[0]);
            this.sendMessage(sender, "&cHighest: &f" + max, new Object[0]);
        }
        if (clients == 0) {
            this.sendMessage(sender, "&cNo clients to display.", new Object[0]);
        }
        for (Map.Entry entry : sorted.entrySet()) {
            this.sendMessage(sender, "&8[&6%s&8]: &b%s", ProtocolVersion.getProtocol((Integer)entry.getKey()).getName(), entry.getValue());
        }
        sorted.clear();
        return true;
    }
}

