/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
    public boolean execute(ViaCommandSender viaCommandSender, String[] stringArray) {
        TreeMap<ProtocolVersion, Set> treeMap = new TreeMap<ProtocolVersion, Set>(ListSubCmd::lambda$execute$0);
        for (ViaCommandSender viaCommandSender2 : Via.getPlatform().getOnlinePlayers()) {
            int n = Via.getAPI().getPlayerVersion(viaCommandSender2.getUUID());
            ProtocolVersion protocolVersion = ProtocolVersion.getProtocol(n);
            treeMap.computeIfAbsent(protocolVersion, ListSubCmd::lambda$execute$1).add(viaCommandSender2.getName());
        }
        for (Map.Entry entry : treeMap.entrySet()) {
            ListSubCmd.sendMessage(viaCommandSender, "&8[&6%s&8] (&7%d&8): &b%s", ((ProtocolVersion)entry.getKey()).getName(), ((Set)entry.getValue()).size(), entry.getValue());
        }
        treeMap.clear();
        return false;
    }

    private static Set lambda$execute$1(ProtocolVersion protocolVersion) {
        return new HashSet();
    }

    private static int lambda$execute$0(ProtocolVersion protocolVersion, ProtocolVersion protocolVersion2) {
        return ProtocolVersion.getIndex(protocolVersion2) - ProtocolVersion.getIndex(protocolVersion);
    }
}

