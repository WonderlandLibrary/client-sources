package net;

import com.google.common.base.Joiner;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import net.BS;
import net.aW8;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets;

class bgX implements PacketHandler {
   final aW8 a;

   bgX(aW8 var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      BS.b();
      String var3 = (String)var1.get(Type.STRING, 0);
      var3 = InventoryPackets.getOldPluginChannelId(var3);
      if(var3 == null) {
         if(!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
            Via.getPlatform().getLogger().warning("Ignoring incoming plugin message with channel: " + var3);
         }

         var1.cancel();
      } else {
         if(var3.equals("REGISTER") || var3.equals("UNREGISTER")) {
            String[] var5 = (new String((byte[])var1.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8)).split("\u0000");
            ArrayList var6 = new ArrayList();
            int var7 = 0;
            if(var7 < var5.length) {
               String var8 = InventoryPackets.getOldPluginChannelId(var5[var7]);
               var6.add(var8);
               if(!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                  Via.getPlatform().getLogger().warning("Ignoring plugin channel in incoming REGISTER: " + var5[var7]);
               }

               ++var7;
            }

            var1.write(Type.REMAINING_BYTES, Joiner.on('\u0000').join(var6).getBytes(StandardCharsets.UTF_8));
         }

         var1.set(Type.STRING, 0, var3);
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
