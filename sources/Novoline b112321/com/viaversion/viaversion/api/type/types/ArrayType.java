package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.lang.reflect.Array;
import net.Gh;

public class ArrayType extends Type {
   private final Type elementType;

   public ArrayType(Type var1) {
      super(var1.getTypeName() + " Array", getArrayClass(var1.getOutputClass()));
      this.elementType = var1;
   }

   public static Class getArrayClass(Class var0) {
      return Array.newInstance(var0, 0).getClass();
   }

   public Object[] read(ByteBuf var1) throws Exception {
      Gh.b();
      int var3 = Type.VAR_INT.readPrimitive(var1);
      Object[] var4 = (Object[])((Object[])Array.newInstance(this.elementType.getOutputClass(), var3));
      int var5 = 0;
      if(var5 < var3) {
         var4[var5] = this.elementType.read(var1);
         ++var5;
      }

      return var4;
   }

   public void write(ByteBuf var1, Object[] var2) throws Exception {
      Gh.b();
      Type.VAR_INT.writePrimitive(var1, var2.length);
      int var5 = var2.length;
      int var6 = 0;
      if(var6 < var5) {
         Object var7 = var2[var6];
         this.elementType.write(var1, var7);
         ++var6;
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
