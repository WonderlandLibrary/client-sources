package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

public class VarLongType extends Type<Long> implements TypeConverter<Long> {
   public VarLongType() {
      super("VarLong", Long.class);
   }

   public long readPrimitive(ByteBuf buffer) {
      long out = 0L;
      int bytes = 0;

      byte in;
      do {
         in = buffer.readByte();
         out |= (long)(in & 127) << bytes++ * 7;
         if (bytes > 10) {
            throw new RuntimeException("VarLong too big");
         }
      } while ((in & 128) == 128);

      return out;
   }

   public void writePrimitive(ByteBuf buffer, long object) {
      do {
         int part = (int)(object & 127L);
         object >>>= 7;
         if (object != 0L) {
            part |= 128;
         }

         buffer.writeByte(part);
      } while (object != 0L);
   }

   @Deprecated
   public Long read(ByteBuf buffer) {
      return this.readPrimitive(buffer);
   }

   @Deprecated
   public void write(ByteBuf buffer, Long object) {
      this.writePrimitive(buffer, object);
   }

   public Long from(Object o) {
      if (o instanceof Number) {
         return ((Number)o).longValue();
      } else {
         return o instanceof Boolean ? (Boolean)o ? 1L : 0L : (Long)o;
      }
   }
}
