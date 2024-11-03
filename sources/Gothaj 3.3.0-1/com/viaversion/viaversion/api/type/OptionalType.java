package com.viaversion.viaversion.api.type;

import io.netty.buffer.ByteBuf;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class OptionalType<T> extends Type<T> {
   private final Type<T> type;

   protected OptionalType(Type<T> type) {
      super(type.getOutputClass());
      this.type = type;
   }

   @Nullable
   @Override
   public T read(ByteBuf buffer) throws Exception {
      return buffer.readBoolean() ? this.type.read(buffer) : null;
   }

   @Override
   public void write(ByteBuf buffer, @Nullable T value) throws Exception {
      if (value == null) {
         buffer.writeBoolean(false);
      } else {
         buffer.writeBoolean(true);
         this.type.write(buffer, value);
      }
   }
}
