package net;

import io.netty.buffer.ByteBuf;
import net.S3;
import net.UF;
import net.aRE;
import net.acE;
import net.t4;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import viaversion.viarewind.replacement.EntityReplacement;
import viaversion.viarewind.utils.PacketUtil;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.Vector;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class a9l extends acE {
   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(a9l::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      int var2 = ((Integer)var0.get(Type.VAR_INT, 0)).intValue();
      short var3 = ((Short)var0.read(Type.SHORT)).shortValue();
      short var4 = ((Short)var0.read(Type.SHORT)).shortValue();
      short var5 = ((Short)var0.read(Type.SHORT)).shortValue();
      S3.b();
      EntityTracker var6 = (EntityTracker)var0.user().b(EntityTracker.class);
      EntityReplacement var7 = var6.getEntityReplacement(var2);
      if(var7 != null) {
         var0.cancel();
         var7.relMove((double)var3 / 4096.0D, (double)var4 / 4096.0D, (double)var5 / 4096.0D);
         var7.setYawPitch((float)((Byte)var0.read(Type.BYTE)).byteValue() * 360.0F / 256.0F, (float)((Byte)var0.read(Type.BYTE)).byteValue() * 360.0F / 256.0F);
      } else {
         Vector[] var8 = UF.a(var0.user(), var2, var3, var4, var5);
         var0.write(Type.BYTE, Byte.valueOf((byte)var8[0].c()));
         var0.write(Type.BYTE, Byte.valueOf((byte)var8[0].a()));
         var0.write(Type.BYTE, Byte.valueOf((byte)var8[0].b()));
         byte var9 = ((Byte)var0.passthrough(Type.BYTE)).byteValue();
         byte var10 = ((Byte)var0.passthrough(Type.BYTE)).byteValue();
         boolean var11 = ((Boolean)var0.passthrough(Type.BOOLEAN)).booleanValue();
         t4 var12 = (t4)((EntityTracker)var0.user().b(EntityTracker.class)).getClientEntityTypes().get(Integer.valueOf(var2));
         if(var12 == t4.BOAT) {
            var9 = (byte)(var9 - 64);
            var0.set(Type.BYTE, 3, Byte.valueOf(var9));
         }

         if(var8.length > 1) {
            PacketWrapper var13 = new PacketWrapper(23, (ByteBuf)null, var0.user());
            var13.write(Type.VAR_INT, var0.get(Type.VAR_INT, 0));
            var13.write(Type.BYTE, Byte.valueOf((byte)var8[1].c()));
            var13.write(Type.BYTE, Byte.valueOf((byte)var8[1].a()));
            var13.write(Type.BYTE, Byte.valueOf((byte)var8[1].b()));
            var13.write(Type.BYTE, Byte.valueOf(var9));
            var13.write(Type.BYTE, Byte.valueOf(var10));
            var13.write(Type.BOOLEAN, Boolean.valueOf(var11));
            PacketUtil.sendPacket(var13, aRE.class);
         }

      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
