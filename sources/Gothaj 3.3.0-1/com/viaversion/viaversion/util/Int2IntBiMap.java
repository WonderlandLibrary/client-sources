package com.viaversion.viaversion.util;

import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;

public interface Int2IntBiMap extends Int2IntMap {
   Int2IntBiMap inverse();

   @Override
   int put(int var1, int var2);
}
