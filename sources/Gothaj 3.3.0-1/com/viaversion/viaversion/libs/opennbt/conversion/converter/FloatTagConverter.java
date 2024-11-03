package com.viaversion.viaversion.libs.opennbt.conversion.converter;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;

public class FloatTagConverter implements TagConverter<FloatTag, Float> {
   public Float convert(FloatTag tag) {
      return tag.getValue();
   }

   public FloatTag convert(Float value) {
      return new FloatTag(value);
   }
}
