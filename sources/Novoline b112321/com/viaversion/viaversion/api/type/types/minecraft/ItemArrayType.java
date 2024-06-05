package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import net.aMz;
import net.nP;
import net.w7;

public class ItemArrayType extends w7 {
   public ItemArrayType() {
      super("Item Array");
   }

   public aMz[] a(ByteBuf var1) throws Exception {
      nP.b();
      short var3 = Type.SHORT.readPrimitive(var1);
      aMz[] var4 = new aMz[var3];
      int var5 = 0;
      if(var5 < var3) {
         var4[var5] = (aMz)Type.ITEM.read(var1);
         ++var5;
      }

      return var4;
   }

   public void a(ByteBuf var1, aMz[] var2) throws Exception {
      nP.b();
      Type.SHORT.writePrimitive(var1, (short)var2.length);
      int var5 = var2.length;
      int var6 = 0;
      if(var6 < var5) {
         aMz var7 = var2[var6];
         Type.ITEM.write(var1, var7);
         ++var6;
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
