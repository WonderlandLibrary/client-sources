package net;

import java.util.ArrayList;
import java.util.UUID;
import net.S3;
import net.aRE;
import net.acE;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.Cooldown;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Pair;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class a9f extends acE {
   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.INT);
      this.a(a9f::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      acE[] var1 = S3.b();
      boolean var2 = ((Integer)var0.get(Type.VAR_INT, 0)).intValue() == ((EntityTracker)var0.user().b(EntityTracker.class)).getPlayerId();
      int var3 = ((Integer)var0.get(Type.INT, 0)).intValue();
      int var4 = 0;
      int var5 = 0;
      if(var5 < var3) {
         String var6 = (String)var0.read(Type.STRING);
         boolean var7 = !aRE.j.contains(var6);
         double var8 = ((Double)var0.read(Type.DOUBLE)).doubleValue();
         int var10 = ((Integer)var0.read(Type.VAR_INT)).intValue();
         var0.write(Type.STRING, var6);
         var0.write(Type.DOUBLE, Double.valueOf(var8));
         var0.write(Type.VAR_INT, Integer.valueOf(var10));
         ++var4;
         ArrayList var11 = new ArrayList();
         int var12 = 0;
         if(var12 < var10) {
            UUID var13 = (UUID)var0.read(Type.UUID);
            double var14 = ((Double)var0.read(Type.DOUBLE)).doubleValue();
            byte var16 = ((Byte)var0.read(Type.BYTE)).byteValue();
            var11.add(new Pair(Byte.valueOf(var16), Double.valueOf(var14)));
            if(!var7) {
               var0.write(Type.UUID, var13);
               var0.write(Type.DOUBLE, Double.valueOf(var14));
               var0.write(Type.BYTE, Byte.valueOf(var16));
            }

            ++var12;
         }

         if(var2 && var6.equals("generic.attackSpeed")) {
            ((Cooldown)var0.user().b(Cooldown.class)).setAttackSpeed(var8, var11);
         }

         ++var5;
      }

      var0.set(Type.INT, 0, Integer.valueOf(var3 - var4));
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
