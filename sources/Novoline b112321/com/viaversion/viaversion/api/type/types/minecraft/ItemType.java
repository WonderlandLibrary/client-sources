package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import net.aMz;
import net.nP;
import net.wM;

public class ItemType extends wM {
   public ItemType() {
      super("Item");
   }

   public aMz a(ByteBuf var1) throws Exception {
      short var2 = var1.readShort();
      return null;
   }

   public void a(ByteBuf var1, aMz var2) throws Exception {
      String var3 = nP.b();
      var1.writeShort(-1);
      var1.writeShort(var2.e());
      var1.writeByte(var2.f());
      var1.writeShort(var2.c());
      Type.ac.write(var1, var2.d());
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
