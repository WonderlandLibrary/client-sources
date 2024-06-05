package net;

import io.netty.buffer.ByteBuf;
import java.util.Optional;
import net.aDY;
import net.aRi;
import net.afz;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.Scoreboard;
import viaversion.viarewind.utils.PacketUtil;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.base.ProtocolInfo;

class Am implements PacketHandler {
   final aDY a;

   Am(aDY var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      afz.a();
      String var3 = (String)var1.passthrough(Type.STRING);
      if(var3.length() > 16) {
         var1.set(Type.STRING, 0, var3 = var3.substring(0, 16));
      }

      byte var4 = ((Byte)var1.read(Type.BYTE)).byteValue();
      Scoreboard var5 = (Scoreboard)var1.user().b(Scoreboard.class);
      if(var4 == 0) {
         if(var5.objectiveExists(var3)) {
            var1.cancel();
            return;
         }

         var5.addObjective(var3);
      }

      if(var4 == 1) {
         if(!var5.objectiveExists(var3)) {
            var1.cancel();
            return;
         }

         if(var5.getColorIndependentSidebar() != null) {
            String var6 = ((ProtocolInfo)var1.user().b(ProtocolInfo.class)).getUsername();
            Optional var7 = var5.getPlayerTeamColor(var6);
            if(var7.isPresent()) {
               String var8 = (String)var5.getColorDependentSidebar().get(var7.get());
               if(var3.equals(var8)) {
                  PacketWrapper var9 = new PacketWrapper(61, (ByteBuf)null, var1.user());
                  var9.write(Type.BYTE, Byte.valueOf((byte)1));
                  var9.write(Type.STRING, var5.getColorIndependentSidebar());
                  PacketUtil.sendPacket(var9, aRi.class);
               }
            }
         }

         var5.removeObjective(var3);
      }

      if(var4 == 2 && !var5.objectiveExists(var3)) {
         var1.cancel();
      } else {
         if(var4 == 0 || var4 == 2) {
            String var10 = (String)var1.passthrough(Type.STRING);
            if(var10.length() > 32) {
               var1.set(Type.STRING, 1, var10.substring(0, 32));
            }

            var1.read(Type.STRING);
         }

         var1.write(Type.STRING, "");
         var1.write(Type.BYTE, Byte.valueOf(var4));
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
