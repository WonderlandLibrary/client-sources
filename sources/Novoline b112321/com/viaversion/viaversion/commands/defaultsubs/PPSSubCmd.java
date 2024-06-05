package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import net.abo;
import net.bgR;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;

public class PPSSubCmd extends ViaSubCommand {
   public String name() {
      return "pps";
   }

   public String description() {
      return "Shows the packets per second of online players";
   }

   public String usage() {
      return "pps";
   }

   public boolean execute(ViaCommandSender var1, String[] var2) {
      abo.c();
      HashMap var4 = new HashMap();
      int var5 = 0;
      int var6 = 0;
      long var7 = 0L;
      ViaCommandSender[] var9 = Via.d().getOnlinePlayers();
      int var10 = var9.length;
      int var11 = 0;
      if(var11 < var10) {
         ViaCommandSender var12 = var9[var11];
         int var13 = Via.a().getPlayerVersion(var12.getUUID());
         if(!var4.containsKey(Integer.valueOf(var13))) {
            var4.put(Integer.valueOf(var13), new HashSet());
         }

         bgR var14 = Via.b().a(var12.getUUID());
         if(var14.k() > -1L) {
            ((Set)var4.get(Integer.valueOf(var13))).add(var12.getName() + " (" + var14.k() + " PPS)");
            var5 = (int)((long)var5 + var14.k());
            if(var14.k() > var7) {
               var7 = var14.k();
            }

            ++var6;
         }

         ++var11;
      }

      TreeMap var15 = new TreeMap(var4);
      this.a(var1, "&4Live Packets Per Second", new Object[0]);
      if(var6 > 1) {
         this.a(var1, "&cAverage: &f" + var5 / var6, new Object[0]);
         this.a(var1, "&cHighest: &f" + var7, new Object[0]);
      }

      if(var6 == 0) {
         this.a(var1, "&cNo clients to display.", new Object[0]);
      }

      Iterator var16 = var15.entrySet().iterator();
      if(var16.hasNext()) {
         Entry var18 = (Entry)var16.next();
         this.a(var1, "&8[&6%s&8]: &b%s", new Object[]{ProtocolVersion.b(((Integer)var18.getKey()).intValue()).getName(), var18.getValue()});
      }

      var15.clear();
      return true;
   }
}
