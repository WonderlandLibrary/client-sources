package com.viaversion.viaversion.libs.opennbt.conversion.converter;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.DoubleTag;

public class DoubleTagConverter implements TagConverter<DoubleTag, Double> {
   public Double convert(DoubleTag tag) {
      return tag.getValue();
   }

   public DoubleTag convert(Double value) {
      return new DoubleTag(value);
   }
}
