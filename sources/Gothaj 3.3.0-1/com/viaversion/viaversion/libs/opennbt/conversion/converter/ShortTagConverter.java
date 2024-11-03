package com.viaversion.viaversion.libs.opennbt.conversion.converter;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;

public class ShortTagConverter implements TagConverter<ShortTag, Short> {
   public Short convert(ShortTag tag) {
      return tag.getValue();
   }

   public ShortTag convert(Short value) {
      return new ShortTag(value);
   }
}
