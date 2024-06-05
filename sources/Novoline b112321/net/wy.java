package net;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import net.n5;

public class wy extends Type {
   public wy() {
      super("Vector", n5.class);
   }

   public n5 a(ByteBuf var1) throws Exception {
      int var2 = ((Integer)Type.I.read(var1)).intValue();
      int var3 = ((Integer)Type.I.read(var1)).intValue();
      int var4 = ((Integer)Type.I.read(var1)).intValue();
      return new n5(var2, var3, var4);
   }

   public void a(ByteBuf var1, n5 var2) throws Exception {
      Type.I.write(var1, Integer.valueOf(var2.c()));
      Type.I.write(var1, Integer.valueOf(var2.a()));
      Type.I.write(var1, Integer.valueOf(var2.b()));
   }
}
