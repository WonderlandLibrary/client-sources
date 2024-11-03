package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Size64;
import java.util.SortedSet;

public interface IntSortedSet extends IntSet, SortedSet<Integer>, IntBidirectionalIterable {
   IntBidirectionalIterator iterator(int var1);

   @Override
   IntBidirectionalIterator iterator();

   @Override
   default IntSpliterator spliterator() {
      return IntSpliterators.asSpliteratorFromSorted(this.iterator(), Size64.sizeOf(this), 341, this.comparator());
   }

   IntSortedSet subSet(int var1, int var2);

   IntSortedSet headSet(int var1);

   IntSortedSet tailSet(int var1);

   IntComparator comparator();

   int firstInt();

   int lastInt();

   @Deprecated
   default IntSortedSet subSet(Integer from, Integer to) {
      return this.subSet(from.intValue(), to.intValue());
   }

   @Deprecated
   default IntSortedSet headSet(Integer to) {
      return this.headSet(to.intValue());
   }

   @Deprecated
   default IntSortedSet tailSet(Integer from) {
      return this.tailSet(from.intValue());
   }

   @Deprecated
   default Integer first() {
      return this.firstInt();
   }

   @Deprecated
   default Integer last() {
      return this.lastInt();
   }
}
