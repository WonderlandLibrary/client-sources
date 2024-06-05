package net;

import net.ao4;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.Position;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class a0q implements PacketHandler {
   final ao4 a;

   a0q(ao4 var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      Position var2 = (Position)var1.read(Type.POSITION);
      int var3 = ((Integer)var1.read(Type.VAR_INT)).intValue();
      int var4 = ((Integer)var1.read(Type.VAR_INT)).intValue();
      float var5 = ((Float)var1.read(Type.FLOAT)).floatValue();
      float var6 = ((Float)var1.read(Type.FLOAT)).floatValue();
      float var7 = ((Float)var1.read(Type.FLOAT)).floatValue();
      var1.write(Type.VAR_INT, Integer.valueOf(var4));
      var1.write(Type.POSITION1_14, var2);
      var1.write(Type.VAR_INT, Integer.valueOf(var3));
      var1.write(Type.FLOAT, Float.valueOf(var5));
      var1.write(Type.FLOAT, Float.valueOf(var6));
      var1.write(Type.FLOAT, Float.valueOf(var7));
      var1.write(Type.BOOLEAN, Boolean.valueOf(false));
   }
}
