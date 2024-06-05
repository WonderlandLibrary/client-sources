package net.minecraft.util;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import net.aZA;
import net.minecraft.util.Cartesian;

class Cartesian$Product$ProductIterator extends UnmodifiableIterator {
   private int index;
   private final Iterable[] iterables;
   private final Iterator[] iterators;
   private final Object[] results;

   private Cartesian$Product$ProductIterator(Class var1, Iterable[] var2) {
      this.index = -2;
      this.iterables = var2;
      this.iterators = (Iterator[])Cartesian.access$200(Iterator.class, this.iterables.length);

      for(int var3 = 0; var3 < this.iterables.length; ++var3) {
         this.iterators[var3] = var2[var3].iterator();
      }

      this.results = Cartesian.access$200(var1, this.iterators.length);
   }

   private void endOfData() {
      this.index = -1;
      Arrays.fill(this.iterators, (Object)null);
      Arrays.fill(this.results, (Object)null);
   }

   public boolean hasNext() {
      if(this.index == -2) {
         this.index = 0;

         for(Iterator var4 : this.iterators) {
            if(!var4.hasNext()) {
               this.endOfData();
               break;
            }
         }

         return true;
      } else {
         if(this.index >= this.iterators.length) {
            for(this.index = this.iterators.length - 1; this.index >= 0; --this.index) {
               Iterator var1 = this.iterators[this.index];
               if(var1.hasNext()) {
                  break;
               }

               if(this.index == 0) {
                  this.endOfData();
                  break;
               }

               var1 = this.iterables[this.index].iterator();
               this.iterators[this.index] = var1;
               if(!var1.hasNext()) {
                  this.endOfData();
                  break;
               }
            }
         }

         return this.index >= 0;
      }
   }

   public Object[] next() {
      if(!this.hasNext()) {
         throw new NoSuchElementException();
      } else {
         while(this.index < this.iterators.length) {
            this.results[this.index] = this.iterators[this.index].next();
            ++this.index;
         }

         return (Object[])this.results.clone();
      }
   }

   Cartesian$Product$ProductIterator(Class var1, Iterable[] var2, aZA var3) {
      this(var1, var2);
   }

   private static NoSuchElementException a(NoSuchElementException var0) {
      return var0;
   }
}
