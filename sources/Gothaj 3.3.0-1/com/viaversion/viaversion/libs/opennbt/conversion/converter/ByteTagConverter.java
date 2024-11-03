package com.viaversion.viaversion.libs.opennbt.conversion.converter;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;

public class ByteTagConverter implements TagConverter<ByteTag, Byte> {
   public Byte convert(ByteTag tag) {
      return tag.getValue();
   }

   public ByteTag convert(Byte value) {
      return new ByteTag(value);
   }
}
