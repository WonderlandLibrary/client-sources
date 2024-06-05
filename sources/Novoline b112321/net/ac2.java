package net;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.acE;
import net.agN;
import net.aqp;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import viaversion.viabackwards.protocol.protocol1_10to1_11.packets.PlayerPackets1_11;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class ac2 extends acE {
   final PlayerPackets1_11 c;

   ac2(PlayerPackets1_11 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(ac2::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      aqp.d();
      int var2 = ((Integer)var0.get(Type.VAR_INT, 0)).intValue();
      if(var2 == 2) {
         JsonElement var3 = (JsonElement)var0.read(Type.COMPONENT);
         var0.clearPacket();
         var0.setId(agN.CHAT_MESSAGE.ordinal());
         BaseComponent[] var4 = ComponentSerializer.parse(var3.toString());
         String var5 = TextComponent.toLegacyText(var4);
         JsonObject var6 = new JsonObject();
         var6.getAsJsonObject().addProperty("text", var5);
         var0.write(Type.COMPONENT, var6);
         var0.write(Type.BYTE, Byte.valueOf((byte)2));
      }

      if(var2 > 2) {
         var0.set(Type.VAR_INT, 0, Integer.valueOf(var2 - 1));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
