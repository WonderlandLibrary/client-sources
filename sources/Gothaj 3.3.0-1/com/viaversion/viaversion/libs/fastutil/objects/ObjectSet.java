package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Size64;
import java.util.Set;

public interface ObjectSet<K> extends ObjectCollection<K>, Set<K> {
   @Override
   ObjectIterator<K> iterator();

   @Override
   default ObjectSpliterator<K> spliterator() {
      return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this), 65);
   }

   static <K> ObjectSet<K> of() {
      return ObjectSets.UNMODIFIABLE_EMPTY_SET;
   }

   static <K> ObjectSet<K> of(K e) {
      return ObjectSets.singleton(e);
   }

   static <K> ObjectSet<K> of(K e0, K e1) {
      ObjectArraySet<K> innerSet = new ObjectArraySet<>(2);
      innerSet.add(e0);
      if (!innerSet.add(e1)) {
         throw new IllegalArgumentException("Duplicate element: " + e1);
      } else {
         return ObjectSets.unmodifiable(innerSet);
      }
   }

   static <K> ObjectSet<K> of(K e0, K e1, K e2) {
      ObjectArraySet<K> innerSet = new ObjectArraySet<>(3);
      innerSet.add(e0);
      if (!innerSet.add(e1)) {
         throw new IllegalArgumentException("Duplicate element: " + e1);
      } else if (!innerSet.add(e2)) {
         throw new IllegalArgumentException("Duplicate element: " + e2);
      } else {
         return ObjectSets.unmodifiable(innerSet);
      }
   }

   @SafeVarargs
   static <K> ObjectSet<K> of(K... a) {
      switch (a.length) {
         case 0:
            return of();
         case 1:
            return of(a[0]);
         case 2:
            return of(a[0], a[1]);
         case 3:
            return of(a[0], a[1], a[2]);
         default:
            ObjectSet<K> innerSet = (ObjectSet<K>)(a.length <= 4 ? new ObjectArraySet<>(a.length) : new ObjectOpenHashSet<>(a.length));

            for (K element : a) {
               if (!innerSet.add(element)) {
                  throw new IllegalArgumentException("Duplicate element: " + element);
               }
            }

            return ObjectSets.unmodifiable(innerSet);
      }
   }
}
