package net;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.VV;
import net.acE;
import net.aq6;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;

class aoW extends acE {
   final aq6 c;

   aoW(aq6 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(aoW::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      int var2 = ((Integer)var0.read(Type.VAR_INT)).intValue();
      aq6.a();
      var0.write(Type.UNSIGNED_BYTE, Short.valueOf((short)var2));
      int var3 = ((Integer)var0.read(Type.VAR_INT)).intValue();
      String var4 = null;
      String var5 = null;
      int var6 = 0;
      if(var3 < 6) {
         if(var3 == 2) {
            var5 = "Barrel";
         }

         var4 = "minecraft:container";
         var6 = (var3 + 1) * 9;
      } else {
         switch(var3) {
         case 6:
            var4 = "minecraft:dropper";
            var6 = 9;
            break;
         case 7:
         case 21:
            if(var3 == 21) {
               var5 = "Cartography Table";
            }

            var4 = "minecraft:anvil";
            break;
         case 8:
            var4 = "minecraft:beacon";
            var6 = 1;
            break;
         case 9:
         case 13:
         case 14:
         case 20:
            if(var3 == 9) {
               var5 = "Blast Furnace";
            } else if(var3 == 20) {
               var5 = "Smoker";
            } else if(var3 == 14) {
               var5 = "Grindstone";
            }

            var4 = "minecraft:furnace";
            var6 = 3;
            break;
         case 10:
            var4 = "minecraft:brewing_stand";
            var6 = 5;
            break;
         case 11:
            var4 = "minecraft:crafting_table";
            break;
         case 12:
            var4 = "minecraft:enchanting_table";
            break;
         case 15:
            var4 = "minecraft:hopper";
            var6 = 5;
         case 16:
         case 17:
         default:
            break;
         case 18:
            var4 = "minecraft:villager";
            break;
         case 19:
            var4 = "minecraft:shulker_box";
            var6 = 27;
         }
      }

      if(var4 == null) {
         VV.d().getLogger().warning("Can\'t open inventory for 1.13 player! Type: " + var3);
         var0.cancel();
      } else {
         var0.write(Type.STRING, var4);
         JsonElement var7 = (JsonElement)var0.read(Type.COMPONENT);
         JsonObject var8;
         if(var7.isJsonObject() && (var8 = var7.getAsJsonObject()).has("translate") && (var3 != 2 || var8.getAsJsonPrimitive("translate").getAsString().equals("container.barrel"))) {
            var7 = ChatRewriter.legacyTextToJson(var5);
         }

         var0.write(Type.COMPONENT, var7);
         var0.write(Type.UNSIGNED_BYTE, Short.valueOf((short)var6));
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
