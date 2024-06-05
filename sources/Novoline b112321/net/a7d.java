package net;

import net.a4D;
import net.acE;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Pair;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_11to1_10.EntityIdRewriter;
import viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10;

class a7d extends acE {
   final Protocol1_11To1_10 c;

   a7d(Protocol1_11To1_10 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.INT);
      this.a(Type.POSITION);
      this.a(Type.INT);
      this.a(Type.BOOLEAN);
      this.a(a7d::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      EntityIdRewriter.b();
      int var2 = ((Integer)var0.get(Type.INT, 0)).intValue();
      if(var2 == 2002) {
         int var3 = ((Integer)var0.get(Type.INT, 1)).intValue();
         boolean var4 = false;
         Pair var5 = a4D.a(var3);
         if(var5 == null) {
            Via.getPlatform().getLogger().warning("Received unknown 1.11 -> 1.10.2 potion data (" + var3 + ")");
            var3 = 0;
         }

         var3 = ((Integer)var5.getKey()).intValue();
         var4 = ((Boolean)var5.getValue()).booleanValue();
         var0.set(Type.INT, 0, Integer.valueOf(2007));
         var0.set(Type.INT, 1, Integer.valueOf(var3));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
