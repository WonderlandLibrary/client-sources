package com.viaversion.viaversion.libs.opennbt.conversion.converter;

import com.viaversion.viaversion.libs.opennbt.conversion.ConverterRegistry;
import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.ArrayList;
import java.util.List;

public class ListTagConverter implements TagConverter<ListTag, List> {
   public List convert(ListTag tag) {
      List<Object> ret = new ArrayList<>();

      for (Tag t : tag.getValue()) {
         ret.add(ConverterRegistry.convertToValue(t));
      }

      return ret;
   }

   public ListTag convert(List value) {
      List<Tag> tags = new ArrayList<>();

      for (Object o : value) {
         tags.add(ConverterRegistry.convertToTag(o));
      }

      return new ListTag(tags);
   }
}
