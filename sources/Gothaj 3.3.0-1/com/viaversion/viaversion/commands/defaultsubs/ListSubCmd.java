package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class ListSubCmd extends ViaSubCommand {
   @Override
   public String name() {
      return "list";
   }

   @Override
   public String description() {
      return "Shows lists of the versions from logged in players.";
   }

   @Override
   public String usage() {
      return "list";
   }

   @Override
   public boolean execute(ViaCommandSender sender, String[] args) {
      Map<ProtocolVersion, Set<String>> playerVersions = new TreeMap<>((o1, o2) -> ProtocolVersion.getIndex(o2) - ProtocolVersion.getIndex(o1));

      for (ViaCommandSender p : Via.getPlatform().getOnlinePlayers()) {
         int playerVersion = Via.getAPI().getPlayerVersion(p.getUUID());
         ProtocolVersion key = ProtocolVersion.getProtocol(playerVersion);
         playerVersions.computeIfAbsent(key, s -> new HashSet<>()).add(p.getName());
      }

      for (Entry<ProtocolVersion, Set<String>> entry : playerVersions.entrySet()) {
         sendMessage(sender, "&8[&6%s&8] (&7%d&8): &b%s", new Object[]{entry.getKey().getName(), entry.getValue().size(), entry.getValue()});
      }

      playerVersions.clear();
      return true;
   }
}
