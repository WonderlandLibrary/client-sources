package com.viaversion.viaversion.libs.opennbt.conversion;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

public interface TagConverter<T extends Tag, V> {
   V convert(T var1);

   T convert(V var1);
}
