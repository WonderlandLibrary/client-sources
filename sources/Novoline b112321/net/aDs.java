package net;

import net.acE;
import net.afz;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.ArmorStandReplacement;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerPosition;
import viaversion.viarewind.replacement.EntityReplacement;
import viaversion.viarewind.utils.math.AABB;
import viaversion.viarewind.utils.math.Ray3d;
import viaversion.viarewind.utils.math.RayTracing;
import viaversion.viarewind.utils.math.Vector3d;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class aDs extends acE {
   public void registerMap() {
      this.a(Type.INT, Type.VAR_INT);
      this.a(Type.BYTE, Type.VAR_INT);
      this.a(aDs::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      afz.b();
      int var2 = ((Integer)var0.get(Type.VAR_INT, 1)).intValue();
      if(var2 == 0) {
         int var3 = ((Integer)var0.get(Type.VAR_INT, 0)).intValue();
         EntityTracker var4 = (EntityTracker)var0.user().b(EntityTracker.class);
         EntityReplacement var5 = var4.getEntityReplacement(var3);
         if(var5 instanceof ArmorStandReplacement) {
            ArmorStandReplacement var6 = (ArmorStandReplacement)var5;
            AABB var7 = var6.getBoundingBox();
            PlayerPosition var8 = (PlayerPosition)var0.user().b(PlayerPosition.class);
            Vector3d var9 = new Vector3d(var8.g(), var8.f() + 1.8D, var8.e());
            double var10 = Math.toRadians((double)var8.c());
            double var12 = Math.toRadians((double)var8.a());
            Vector3d var14 = new Vector3d(-Math.cos(var12) * Math.sin(var10), -Math.sin(var12), Math.cos(var12) * Math.cos(var10));
            Ray3d var15 = new Ray3d(var9, var14);
            Vector3d var16 = RayTracing.trace(var15, var7, 5.0D);
            if(var16 != null) {
               var16.b(var7.getMin());
               var2 = 2;
               var0.set(Type.VAR_INT, 1, Integer.valueOf(var2));
               var0.write(Type.FLOAT, Float.valueOf((float)var16.getX()));
               var0.write(Type.FLOAT, Float.valueOf((float)var16.getY()));
               var0.write(Type.FLOAT, Float.valueOf((float)var16.getZ()));
            }
         }
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
