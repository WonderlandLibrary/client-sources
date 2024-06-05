package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;
import net.Gh;

public class VarLongType extends Type implements TypeConverter {
   public VarLongType() {
      super("VarLong", Long.class);
   }

   public long readPrimitive(ByteBuf var1) {
      long var2 = 0L;
      int var4 = 0;

      while(true) {
         byte var5 = var1.readByte();
         var2 |= (long)(var5 & 127) << var4++ * 7;
         if(var4 > 10) {
            throw new RuntimeException("VarLong too big");
         }

         if((var5 & 128) != 128) {
            break;
         }
      }

      return var2;
   }

   public void writePrimitive(ByteBuf var1, long var2) {
      String var4 = Gh.b();

      while(true) {
         int var5 = (int)(var2 & 127L);
         var2 >>>= 7;
         if(var2 != 0L) {
            var5 |= 128;
         }

         var1.writeByte(var5);
         if(var2 == 0L) {
            break;
         }
      }

   }

   /** @deprecated */
   @Deprecated
   public Long read(ByteBuf var1) {
      return Long.valueOf(this.readPrimitive(var1));
   }

   /** @deprecated */
   @Deprecated
   public void write(ByteBuf var1, Long var2) {
      this.writePrimitive(var1, var2.longValue());
   }

   public Long from(Object var1) {
      String var2 = Gh.b();
      return var1 instanceof Number?Long.valueOf(((Number)var1).longValue()):(var1 instanceof Boolean?Long.valueOf(((Boolean)var1).booleanValue()?1L:0L):(Long)var1);
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}
