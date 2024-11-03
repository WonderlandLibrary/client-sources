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
import java.util.Map.Entry;

public class PPSSubCmd extends ViaSubCommand {
   @Override
   public String name() {
      return "pps";
   }

   @Override
   public String description() {
      return "Shows the packets per second of online players.";
   }

   @Override
   public String usage() {
      return "pps";
   }

   @Override
   public boolean execute(ViaCommandSender sender, String[] args) {
      Map<Integer, Set<String>> playerVersions = new HashMap<>();
      int totalPackets = 0;
      int clients = 0;
      long max = 0L;

      for (ViaCommandSender p : Via.getPlatform().getOnlinePlayers()) {
         int playerVersion = Via.getAPI().getPlayerVersion(p.getUUID());
         if (!playerVersions.containsKey(playerVersion)) {
            playerVersions.put(playerVersion, new HashSet<>());
         }

         UserConnection uc = Via.getManager().getConnectionManager().getConnectedClient(p.getUUID());
         if (uc != null && uc.getPacketTracker().getPacketsPerSecond() > -1L) {
            playerVersions.get(playerVersion).add(p.getName() + " (" + uc.getPacketTracker().getPacketsPerSecond() + " PPS)");
            totalPackets = (int)((long)totalPackets + uc.getPacketTracker().getPacketsPerSecond());
            if (uc.getPacketTracker().getPacketsPerSecond() > max) {
               max = uc.getPacketTracker().getPacketsPerSecond();
            }

            clients++;
         }
      }

      Map<Integer, Set<String>> sorted = new TreeMap<>(playerVersions);
      sendMessage(sender, "&4Live Packets Per Second", new Object[0]);
      if (clients > 1) {
         sendMessage(sender, "&cAverage: &f" + totalPackets / clients, new Object[0]);
         sendMessage(sender, "&cHighest: &f" + max, new Object[0]);
      }

      if (clients == 0) {
         sendMessage(sender, "&cNo clients to display.", new Object[0]);
      }

      for (Entry<Integer, Set<String>> entry : sorted.entrySet()) {
         sendMessage(sender, "&8[&6%s&8]: &b%s", new Object[]{ProtocolVersion.getProtocol(entry.getKey()).getName(), entry.getValue()});
      }

      sorted.clear();
      return true;
   }
}
