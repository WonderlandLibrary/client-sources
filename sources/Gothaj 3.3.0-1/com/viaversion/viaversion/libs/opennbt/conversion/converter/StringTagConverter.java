package com.viaversion.viaversion.libs.opennbt.conversion.converter;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;

public class StringTagConverter implements TagConverter<StringTag, String> {
   public String convert(StringTag tag) {
      return tag.getValue();
   }

   public StringTag convert(String value) {
      return new StringTag(value);
   }
}
