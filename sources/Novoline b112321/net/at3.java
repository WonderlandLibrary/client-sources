package net;

import net.aYz;
import net.acE;
import net.anS;
import net.lx;
import viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.Protocol1_16_1To1_16_2;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class at3 implements PacketHandler {
   final anS a;

   at3(anS var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aYz.b();
      int var3 = ((Integer)var1.read(Type.VAR_INT)).intValue();
      if(var3 == 0) {
         var1.passthrough(Type.STRING);
         var1.setId(lx.SEEN_RECIPE.ordinal());
      }

      var1.cancel();
      int var4 = 0;
      if(var4 < 3) {
         this.a(var4, var1);
         ++var4;
      }

      if(acE.b() == null) {
         aYz.b(new String[2]);
      }

   }

   private void a(int var1, PacketWrapper var2) throws Exception {
      boolean var3 = ((Boolean)var2.read(Type.BOOLEAN)).booleanValue();
      boolean var4 = ((Boolean)var2.read(Type.BOOLEAN)).booleanValue();
      PacketWrapper var5 = var2.create(lx.RECIPE_BOOK_DATA.ordinal());
      var5.write(Type.VAR_INT, Integer.valueOf(var1));
      var5.write(Type.BOOLEAN, Boolean.valueOf(var3));
      var5.write(Type.BOOLEAN, Boolean.valueOf(var4));
      var5.sendToServer(Protocol1_16_1To1_16_2.class);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
