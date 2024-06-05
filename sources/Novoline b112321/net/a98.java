package net;

import net.S3;
import net.acE;
import net.t4;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import viaversion.viarewind.replacement.EntityReplacement;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class a98 extends acE {
   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.BYTE);
      this.a(Type.BYTE);
      this.a(Type.BOOLEAN);
      this.a(a98::lambda$registerMap$0);
      this.a(a98::lambda$registerMap$1);
   }

   private static void lambda$registerMap$1(PacketWrapper var0) throws Exception {
      S3.b();
      int var2 = ((Integer)var0.get(Type.VAR_INT, 0)).intValue();
      t4 var3 = (t4)((EntityTracker)var0.user().b(EntityTracker.class)).getClientEntityTypes().get(Integer.valueOf(var2));
      if(var3 == t4.BOAT) {
         byte var4 = ((Byte)var0.get(Type.BYTE, 0)).byteValue();
         var4 = (byte)(var4 - 64);
         var0.set(Type.BYTE, 0, Byte.valueOf(var4));
      }

   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      int var2 = ((Integer)var0.get(Type.VAR_INT, 0)).intValue();
      EntityTracker var3 = (EntityTracker)var0.user().b(EntityTracker.class);
      S3.b();
      EntityReplacement var4 = var3.getEntityReplacement(var2);
      if(var4 != null) {
         var0.cancel();
         byte var5 = ((Byte)var0.get(Type.BYTE, 0)).byteValue();
         byte var6 = ((Byte)var0.get(Type.BYTE, 1)).byteValue();
         var4.setYawPitch((float)var5 * 360.0F / 256.0F, (float)var6 * 360.0F / 256.0F);
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
