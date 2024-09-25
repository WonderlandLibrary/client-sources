/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.commands.defaultsubs;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.command.ViaCommandSender;
import us.myles.ViaVersion.api.command.ViaSubCommand;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;

public class ListSubCmd
extends ViaSubCommand {
    @Override
    public String name() {
        return "list";
    }

    @Override
    public String description() {
        return "Shows lists of the versions from logged in players";
    }

    @Override
    public String usage() {
        return "list";
    }

    @Override
    public boolean execute(ViaCommandSender sender, String[] args) {
        TreeMap playerVersions = new TreeMap(new Comparator<ProtocolVersion>(){

            @Override
            public int compare(ProtocolVersion o1, ProtocolVersion o2) {
                return ProtocolVersion.getIndex(o2) - ProtocolVersion.getIndex(o1);
            }
        });
        for (ViaCommandSender p : Via.getPlatform().getOnlinePlayers()) {
            int playerVersion = Via.getAPI().getPlayerVersion(p.getUUID());
            ProtocolVersion key = ProtocolVersion.getProtocol(playerVersion);
            if (!playerVersions.containsKey(key)) {
                playerVersions.put(key, new HashSet());
            }
            ((Set)playerVersions.get(key)).add(p.getName());
        }
        for (Map.Entry entry : playerVersions.entrySet()) {
            this.sendMessage(sender, "&8[&6%s&8] (&7%d&8): &b%s", ((ProtocolVersion)entry.getKey()).getName(), ((Set)entry.getValue()).size(), entry.getValue());
        }
        playerVersions.clear();
        return true;
    }
}

