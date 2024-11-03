package com.viaversion.viaversion.libs.opennbt.conversion.converter;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteArrayTag;

public class ByteArrayTagConverter implements TagConverter<ByteArrayTag, byte[]> {
   public byte[] convert(ByteArrayTag tag) {
      return tag.getValue();
   }

   public ByteArrayTag convert(byte[] value) {
      return new ByteArrayTag(value);
   }
}
