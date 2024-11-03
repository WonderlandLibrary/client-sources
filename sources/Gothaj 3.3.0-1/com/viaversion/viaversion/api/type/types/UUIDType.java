package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.UUID;

public class UUIDType extends Type<UUID> {
   public UUIDType() {
      super(UUID.class);
   }

   public UUID read(ByteBuf buffer) {
      return new UUID(buffer.readLong(), buffer.readLong());
   }

   public void write(ByteBuf buffer, UUID object) {
      buffer.writeLong(object.getMostSignificantBits());
      buffer.writeLong(object.getLeastSignificantBits());
   }

   public static final class OptionalUUIDType extends OptionalType<UUID> {
      public OptionalUUIDType() {
         super(Type.UUID);
      }
   }
}
