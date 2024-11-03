package com.viaversion.viaversion.libs.opennbt.conversion.converter;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;

public class IntTagConverter implements TagConverter<IntTag, Integer> {
   public Integer convert(IntTag tag) {
      return tag.getValue();
   }

   public IntTag convert(Integer value) {
      return new IntTag(value);
   }
}
