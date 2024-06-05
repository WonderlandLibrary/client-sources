package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;
import net.Gh;

public class VarIntType extends Type implements TypeConverter {
   public VarIntType() {
      super("VarInt", Integer.class);
   }

   public int readPrimitive(ByteBuf var1) {
      int var2 = 0;
      int var3 = 0;

      while(true) {
         byte var4 = var1.readByte();
         var2 |= (var4 & 127) << var3++ * 7;
         if(var3 > 5) {
            throw new RuntimeException("VarInt too big");
         }

         if((var4 & 128) != 128) {
            break;
         }
      }

      return var2;
   }

   public void writePrimitive(ByteBuf var1, int var2) {
      String var3 = Gh.b();
      int var4 = var2 & 127;
      var2 = var2 >>> 7;
      var4 = var4 | 128;
      var1.writeByte(var4);
   }

   /** @deprecated */
   @Deprecated
   public Integer read(ByteBuf var1) {
      return Integer.valueOf(this.readPrimitive(var1));
   }

   /** @deprecated */
   @Deprecated
   public void write(ByteBuf var1, Integer var2) {
      this.writePrimitive(var1, var2.intValue());
   }

   public Integer from(Object var1) {
      String var2 = Gh.b();
      return var1 instanceof Number?Integer.valueOf(((Number)var1).intValue()):(var1 instanceof Boolean?Integer.valueOf(((Boolean)var1).booleanValue()?1:0):(Integer)var1);
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}
