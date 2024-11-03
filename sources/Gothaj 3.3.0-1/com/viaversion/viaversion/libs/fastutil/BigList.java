package com.viaversion.viaversion.libs.fastutil;

import java.util.Collection;

public interface BigList<K> extends Collection<K>, Size64 {
   K get(long var1);

   K remove(long var1);

   K set(long var1, K var3);

   void add(long var1, K var3);

   void size(long var1);

   boolean addAll(long var1, Collection<? extends K> var3);

   long indexOf(Object var1);

   long lastIndexOf(Object var1);

   BigListIterator<K> listIterator();

   BigListIterator<K> listIterator(long var1);

   BigList<K> subList(long var1, long var3);

   @Deprecated
   @Override
   default int size() {
      return Size64.super.size();
   }
}
