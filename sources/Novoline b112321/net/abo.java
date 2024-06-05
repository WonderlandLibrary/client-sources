package net;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.command.ViaCommandSender;
import viaversion.viaversion.api.command.ViaSubCommand;
import viaversion.viaversion.api.protocol.ProtocolVersion;
import viaversion.viaversion.commands.defaultsubs.ListSubCmd$1;

public class abo extends ViaSubCommand {
   private static boolean a;

   public String name() {
      return "list";
   }

   public String description() {
      return "Shows lists of the versions from logged in players";
   }

   public String usage() {
      return "list";
   }

   public boolean execute(ViaCommandSender var1, String[] var2) {
      TreeMap var4 = new TreeMap(new ListSubCmd$1(this));
      ViaCommandSender[] var5 = Via.getPlatform().getOnlinePlayers();
      int var6 = var5.length;
      a();
      int var7 = 0;
      if(var7 < var6) {
         ViaCommandSender var8 = var5[var7];
         int var9 = Via.getAPI().getPlayerVersion(var8.getUUID());
         ProtocolVersion var10 = ProtocolVersion.getProtocol(var9);
         if(!var4.containsKey(var10)) {
            var4.put(var10, new HashSet());
         }

         ((Set)var4.get(var10)).add(var8.getName());
         ++var7;
      }

      Iterator var11 = var4.entrySet().iterator();
      if(var11.hasNext()) {
         Entry var12 = (Entry)var11.next();
         this.sendMessage(var1, "&8[&6%s&8] (&7%d&8): &b%s", new Object[]{((ProtocolVersion)var12.getKey()).getName(), Integer.valueOf(((Set)var12.getValue()).size()), var12.getValue()});
      }

      var4.clear();
      return true;
   }

   public static void b(boolean var0) {
      a = var0;
   }

   public static boolean c() {
      return a;
   }

   public static boolean a() {
      boolean var0 = c();
      return true;
   }

   static {
      b(false);
   }
}
