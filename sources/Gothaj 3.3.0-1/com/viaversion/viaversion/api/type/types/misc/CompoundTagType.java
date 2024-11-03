package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;
import java.io.IOException;

public class CompoundTagType extends Type<CompoundTag> {
   public CompoundTagType() {
      super(CompoundTag.class);
   }

   public CompoundTag read(ByteBuf buffer) throws IOException {
      return NamedCompoundTagType.read(buffer, false);
   }

   public void write(ByteBuf buffer, CompoundTag object) throws IOException {
      NamedCompoundTagType.write(buffer, object, null);
   }

   public static final class OptionalCompoundTagType extends OptionalType<CompoundTag> {
      public OptionalCompoundTagType() {
         super(Type.COMPOUND_TAG);
      }
   }
}
