package net;

import com.viaversion.viaversion.api.type.Type;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Particle;
import io.netty.buffer.ByteBuf;
import net.aMz;
import net.axZ;

public class v1 extends Type {
   private final boolean ah;

   public v1(boolean var1) {
      super("Item[]", aMz[].class);
      this.ah = var1;
   }

   public aMz[] a(ByteBuf var1) throws Exception {
      Particle.b();
      short var3 = Type.SHORT.read(var1).shortValue();
      aMz[] var4 = new aMz[var3];
      int var5 = 0;
      if(var5 < var3) {
         var4[var5] = (aMz)(this.ah?axZ.c:axZ.h).read(var1);
         ++var5;
      }

      return var4;
   }

   public void a(ByteBuf var1, aMz[] var2) throws Exception {
      Type.SHORT.write(var1, Short.valueOf((short)var2.length));
      Particle.b();
      int var5 = var2.length;
      int var6 = 0;
      if(var6 < var5) {
         aMz var7 = var2[var6];
         (this.ah?axZ.c:axZ.h).write(var1, var7);
         ++var6;
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
