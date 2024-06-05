package net;

import net.BS;
import net.aWF;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.Position;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;

class E6 implements PacketHandler {
   final aWF a;

   E6(aWF var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      BS.b();
      Position var3 = (Position)var1.get(Type.POSITION, 0);
      short var4 = ((Short)var1.get(Type.UNSIGNED_BYTE, 0)).shortValue();
      short var5 = ((Short)var1.get(Type.UNSIGNED_BYTE, 1)).shortValue();
      int var6 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      if(var6 == 25) {
         var6 = 73;
      }

      if(var6 == 33) {
         var6 = 99;
      }

      if(var6 == 29) {
         var6 = 92;
      }

      if(var6 == 54) {
         var6 = 142;
      }

      if(var6 == 146) {
         var6 = 305;
      }

      if(var6 == 130) {
         var6 = 249;
      }

      if(var6 == 138) {
         var6 = 257;
      }

      if(var6 == 52) {
         var6 = 140;
      }

      if(var6 == 209) {
         var6 = 472;
      }

      if(var6 >= 219 && var6 <= 234) {
         var6 = var6 - 219 + 483;
      }

      if(var6 == 73) {
         PacketWrapper var7 = var1.create(11);
         var7.write(Type.POSITION, new Position(var3));
         var7.write(Type.VAR_INT, Integer.valueOf(249 + var4 * 24 * 2 + var5 * 2));
         var7.send(Protocol1_13To1_12_2.class, true, true);
      }

      var1.set(Type.VAR_INT, 0, Integer.valueOf(var6));
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
