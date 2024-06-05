package net;

import net.aK2;
import net.q1;
import net.md_5.bungee.api.ChatColor;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;

class aju implements PacketHandler {
   final aK2 a;

   aju(aK2 var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      q1.b();
      byte var3 = ((Byte)var1.get(Type.BYTE, 0)).byteValue();
      if(var3 == 0 || var3 == 2) {
         String var4 = (String)var1.read(Type.STRING);
         var1.write(Type.COMPONENT, ChatRewriter.legacyTextToJson(var4));
         String var5 = (String)var1.read(Type.STRING);
         String var6 = (String)var1.read(Type.STRING);
         var1.passthrough(Type.BYTE);
         var1.passthrough(Type.STRING);
         var1.passthrough(Type.STRING);
         int var7 = ((Byte)var1.read(Type.BYTE)).intValue();
         if(var7 == -1) {
            var7 = 21;
         }

         if(Via.getConfig().is1_13TeamColourFix()) {
            ChatColor var8 = this.a.c.getLastColor(var5);
            var7 = var8.ordinal();
            var6 = var8.toString() + var6;
         }

         var1.write(Type.VAR_INT, Integer.valueOf(var7));
         var1.write(Type.COMPONENT, ChatRewriter.legacyTextToJson(var5));
         var1.write(Type.COMPONENT, ChatRewriter.legacyTextToJson(var6));
      }

      if(var3 == 0 || var3 == 3 || var3 == 4) {
         String[] var9 = (String[])var1.read(Type.STRING_ARRAY);
         int var10 = 0;
         if(var10 < var9.length) {
            var9[var10] = this.a.c.rewriteTeamMemberName(var9[var10]);
            ++var10;
         }

         var1.write(Type.STRING_ARRAY, var9);
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
