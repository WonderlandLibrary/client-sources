package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;

public interface IntBidirectionalIterator extends IntIterator, ObjectBidirectionalIterator<Integer> {
   int previousInt();

   @Deprecated
   default Integer previous() {
      return this.previousInt();
   }

   @Override
   default int back(int n) {
      int i = n;

      while (i-- != 0 && this.hasPrevious()) {
         this.previousInt();
      }

      return n - i - 1;
   }

   @Override
   default int skip(int n) {
      return IntIterator.super.skip(n);
   }
}
