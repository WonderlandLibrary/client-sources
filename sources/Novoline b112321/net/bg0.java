package net;

import com.google.common.base.Joiner;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;
import net.BS;
import net.aWi;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.data.SoundSource;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets;

class bg0 implements PacketHandler {
   final aWi a;

   bg0(aWi var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      BS.b();
      String var3 = (String)var1.get(Type.STRING, 0);
      if(var3.equalsIgnoreCase("MC|StopSound")) {
         String var12 = (String)var1.read(Type.STRING);
         String var15 = (String)var1.read(Type.STRING);
         var1.clearPacket();
         var1.setId(76);
         byte var17 = 0;
         var1.write(Type.BYTE, Byte.valueOf(var17));
         if(!var12.isEmpty()) {
            var17 = (byte)(var17 | 1);
            Optional var20 = SoundSource.findBySource(var12);
            if(!var20.isPresent()) {
               if(!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                  Via.getPlatform().getLogger().info("Could not handle unknown sound source " + var12 + " falling back to default: master");
               }

               var20 = Optional.of(SoundSource.MASTER);
            }

            var1.write(Type.VAR_INT, Integer.valueOf(((SoundSource)var20.get()).getId()));
         }

         if(!var15.isEmpty()) {
            var17 = (byte)(var17 | 2);
            var1.write(Type.STRING, var15);
         }

         var1.set(Type.BYTE, 0, Byte.valueOf(var17));
      } else {
         if(var3.equalsIgnoreCase("MC|TrList")) {
            var3 = "minecraft:trader_list";
            var1.passthrough(Type.INT);
            short var4 = ((Short)var1.passthrough(Type.UNSIGNED_BYTE)).shortValue();
            int var5 = 0;
            if(var5 < var4) {
               Item var6 = (Item)var1.read(Type.ITEM);
               InventoryPackets.toClient(var6);
               var1.write(Type.FLAT_ITEM, var6);
               Item var7 = (Item)var1.read(Type.ITEM);
               InventoryPackets.toClient(var7);
               var1.write(Type.FLAT_ITEM, var7);
               boolean var8 = ((Boolean)var1.passthrough(Type.BOOLEAN)).booleanValue();
               Item var9 = (Item)var1.read(Type.ITEM);
               InventoryPackets.toClient(var9);
               var1.write(Type.FLAT_ITEM, var9);
               var1.passthrough(Type.BOOLEAN);
               var1.passthrough(Type.INT);
               var1.passthrough(Type.INT);
               ++var5;
            }
         }

         var3 = InventoryPackets.getNewPluginChannelId(var3);
         if(var3 == null) {
            if(!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
               Via.getPlatform().getLogger().warning("Ignoring outgoing plugin message with channel: " + var3);
            }

            var1.cancel();
         } else if(!var3.equals("minecraft:register") && !var3.equals("minecraft:unregister")) {
            var1.set(Type.STRING, 0, var3);
         } else {
            String[] var14 = (new String((byte[])var1.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8)).split("\u0000");
            ArrayList var16 = new ArrayList();
            int var18 = 0;
            if(var18 < var14.length) {
               String var21 = InventoryPackets.getNewPluginChannelId(var14[var18]);
               var16.add(var21);
               if(!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                  Via.getPlatform().getLogger().warning("Ignoring plugin channel in outgoing REGISTER: " + var14[var18]);
               }

               ++var18;
            }

            if(!var16.isEmpty()) {
               var1.write(Type.REMAINING_BYTES, Joiner.on('\u0000').join(var16).getBytes(StandardCharsets.UTF_8));
            }

            var1.cancel();
         }
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
