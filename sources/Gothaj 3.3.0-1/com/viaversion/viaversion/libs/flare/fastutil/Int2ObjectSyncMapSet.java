package com.viaversion.viaversion.libs.flare.fastutil;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

final class Int2ObjectSyncMapSet extends AbstractIntSet implements IntSet {
   private static final long serialVersionUID = 1L;
   private final Int2ObjectSyncMap<Boolean> map;
   private final IntSet set;

   Int2ObjectSyncMapSet(@NonNull final Int2ObjectSyncMap<Boolean> map) {
      this.map = map;
      this.set = map.keySet();
   }

   @Override
   public void clear() {
      this.map.clear();
   }

   @Override
   public int size() {
      return this.map.size();
   }

   @Override
   public boolean isEmpty() {
      return this.map.isEmpty();
   }

   @Override
   public boolean contains(final int key) {
      return this.map.containsKey(key);
   }

   @Override
   public boolean remove(final int key) {
      return this.map.remove(key) != null;
   }

   @Override
   public boolean add(final int key) {
      return this.map.put(key, Boolean.TRUE) == null;
   }

   @Override
   public boolean containsAll(@NonNull final IntCollection collection) {
      return this.set.containsAll(collection);
   }

   @Override
   public boolean removeAll(@NonNull final IntCollection collection) {
      return this.set.removeAll(collection);
   }

   @Override
   public boolean retainAll(@NonNull final IntCollection collection) {
      return this.set.retainAll(collection);
   }

   @NonNull
   @Override
   public IntIterator iterator() {
      return this.set.iterator();
   }

   @NonNull
   @Override
   public IntSpliterator spliterator() {
      return this.set.spliterator();
   }

   @Override
   public int[] toArray(int[] original) {
      return this.set.toArray(original);
   }

   @Override
   public int[] toIntArray() {
      return this.set.toIntArray();
   }

   @Override
   public boolean equals(@Nullable final Object other) {
      return other == this || this.set.equals(other);
   }

   @NonNull
   @Override
   public String toString() {
      return this.set.toString();
   }

   @Override
   public int hashCode() {
      return this.set.hashCode();
   }
}
