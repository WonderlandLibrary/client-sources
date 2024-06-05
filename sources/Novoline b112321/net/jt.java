package net;

import net.aD2;
import net.afz;
import net.md_5.bungee.api.ChatColor;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.Scoreboard;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class jt implements PacketHandler {
   final aD2 a;

   jt(aD2 var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      afz.b();
      Scoreboard var3 = (Scoreboard)var1.user().b(Scoreboard.class);
      String var4 = (String)var1.passthrough(Type.STRING);
      byte var5 = ((Byte)var1.passthrough(Type.BYTE)).byteValue();
      if(var5 == 1) {
         var4 = var3.k(var4);
      }

      var4 = var3.sendTeamForScore(var4);
      if(var4.length() > 16) {
         var4 = ChatColor.stripColor(var4);
         if(var4.length() > 16) {
            var4 = var4.substring(0, 16);
         }
      }

      var1.set(Type.STRING, 0, var4);
      String var6 = (String)var1.read(Type.STRING);
      if(var6.length() > 16) {
         var6 = var6.substring(0, 16);
      }

      if(var5 != 1) {
         int var7 = ((Integer)var1.read(Type.VAR_INT)).intValue();
         var1.write(Type.STRING, var6);
         var1.write(Type.INT, Integer.valueOf(var7));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
