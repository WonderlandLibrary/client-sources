package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import net.aMz;
import net.nP;
import net.wM;

public class FlatVarIntItemType extends wM {
   public FlatVarIntItemType() {
      super("FlatVarIntItem");
   }

   public aMz a(ByteBuf var1) throws Exception {
      boolean var2 = var1.readBoolean();
      return null;
   }

   public void a(ByteBuf var1, aMz var2) throws Exception {
      String var3 = nP.b();
      var1.writeBoolean(false);
      var1.writeBoolean(true);
      Type.VAR_INT.writePrimitive(var1, var2.e());
      var1.writeByte(var2.f());
      Type.ac.write(var1, var2.d());
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
