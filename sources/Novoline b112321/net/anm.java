package net;

import com.google.gson.JsonElement;
import net.acE;
import net.aqX;
import viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.EntityPackets1_16;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class anm extends acE {
   final EntityPackets1_16 c;

   anm(EntityPackets1_16 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapper var1) throws Exception {
      aqX.a();
      int var3 = ((Integer)var1.passthrough(Type.VAR_INT)).intValue();
      int var4 = ((Integer)var1.passthrough(Type.VAR_INT)).intValue();
      int var5 = 0;
      if(var5 < var4) {
         var1.passthrough(Type.UUID);
         if(var3 == 0) {
            var1.passthrough(Type.STRING);
            int var6 = ((Integer)var1.passthrough(Type.VAR_INT)).intValue();
            int var7 = 0;
            if(var7 < var6) {
               var1.passthrough(Type.STRING);
               var1.passthrough(Type.STRING);
               if(((Boolean)var1.passthrough(Type.BOOLEAN)).booleanValue()) {
                  var1.passthrough(Type.STRING);
               }

               ++var7;
            }

            var1.passthrough(Type.VAR_INT);
            var1.passthrough(Type.VAR_INT);
            if(((Boolean)var1.passthrough(Type.BOOLEAN)).booleanValue()) {
               ((Protocol1_15_2To1_16)EntityPackets1_16.b(this.c)).getTranslatableRewriter().processText((JsonElement)var1.passthrough(Type.COMPONENT));
            }
         }

         if(var3 == 1) {
            var1.passthrough(Type.VAR_INT);
         }

         if(var3 == 2) {
            var1.passthrough(Type.VAR_INT);
         }

         if(var3 == 3 && ((Boolean)var1.passthrough(Type.BOOLEAN)).booleanValue()) {
            ((Protocol1_15_2To1_16)EntityPackets1_16.d(this.c)).getTranslatableRewriter().processText((JsonElement)var1.passthrough(Type.COMPONENT));
         }

         ++var5;
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
