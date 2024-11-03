package com.viaversion.viaversion.libs.opennbt.conversion.converter;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;

public class LongArrayTagConverter implements TagConverter<LongArrayTag, long[]> {
   public long[] convert(LongArrayTag tag) {
      return tag.getValue();
   }

   public LongArrayTag convert(long[] value) {
      return new LongArrayTag(value);
   }
}
