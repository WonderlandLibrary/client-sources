package net.minecraft.util;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Cartesian {
   private static final String __OBFID = "CL_00002327";

   private static Object[] createArray(Class var0, int var1) {
      return (Object[])Array.newInstance(var0, var1);
   }

   public static Iterable cartesianProduct(Class var0, Iterable var1) {
      return new Cartesian.Product(var0, (Iterable[])toArray(Iterable.class, var1), (Object)null);
   }

   public static Iterable cartesianProduct(Iterable var0) {
      return arraysAsLists(cartesianProduct(Object.class, var0));
   }

   static Object[] access$0(Class var0, int var1) {
      return createArray(var0, var1);
   }

   private static Iterable arraysAsLists(Iterable var0) {
      return Iterables.transform(var0, new Cartesian.GetList((Object)null));
   }

   private static Object[] toArray(Class var0, Iterable var1) {
      ArrayList var2 = Lists.newArrayList();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         var2.add(var4);
      }

      return var2.toArray(createArray(var0, var2.size()));
   }

   static class Product implements Iterable {
      private final Iterable[] iterables;
      private static final String __OBFID = "CL_00002324";
      private final Class clazz;

      private Product(Class var1, Iterable[] var2) {
         this.clazz = var1;
         this.iterables = var2;
      }

      Product(Class var1, Iterable[] var2, Object var3) {
         this(var1, var2);
      }

      public Iterator iterator() {
         return (Iterator)(this.iterables.length <= 0 ? Collections.singletonList(Cartesian.access$0(this.clazz, 0)).iterator() : new Cartesian.Product.ProductIterator(this.clazz, this.iterables, (Object)null));
      }

      static class ProductIterator extends UnmodifiableIterator {
         private final Iterable[] iterables;
         private final Iterator[] iterators;
         private final Object[] results;
         private static final String __OBFID = "CL_00002323";
         private int index;

         public boolean hasNext() {
            if (this.index == -2) {
               this.index = 0;
               Iterator[] var5 = this.iterators;
               int var2 = var5.length;

               for(int var3 = 0; var3 < var2; ++var3) {
                  Iterator var4 = var5[var3];
                  if (!var4.hasNext()) {
                     this.endOfData();
                     break;
                  }
               }

               return true;
            } else {
               if (this.index >= this.iterators.length) {
                  for(this.index = this.iterators.length - 1; this.index >= 0; --this.index) {
                     Iterator var1 = this.iterators[this.index];
                     if (var1.hasNext()) {
                        break;
                     }

                     if (this.index == 0) {
                        this.endOfData();
                        break;
                     }

                     var1 = this.iterables[this.index].iterator();
                     this.iterators[this.index] = var1;
                     if (!var1.hasNext()) {
                        this.endOfData();
                        break;
                     }
                  }
               }

               return this.index >= 0;
            }
         }

         ProductIterator(Class var1, Iterable[] var2, Object var3) {
            this(var1, var2);
         }

         public Object next() {
            return this.next0();
         }

         private void endOfData() {
            this.index = -1;
            Arrays.fill(this.iterators, (Object)null);
            Arrays.fill(this.results, (Object)null);
         }

         private ProductIterator(Class var1, Iterable[] var2) {
            this.index = -2;
            this.iterables = var2;
            this.iterators = (Iterator[])Cartesian.access$0(Iterator.class, this.iterables.length);

            for(int var3 = 0; var3 < this.iterables.length; ++var3) {
               this.iterators[var3] = var2[var3].iterator();
            }

            this.results = Cartesian.access$0(var1, this.iterators.length);
         }

         public Object[] next0() {
            if (!this.hasNext()) {
               throw new NoSuchElementException();
            } else {
               while(this.index < this.iterators.length) {
                  this.results[this.index] = this.iterators[this.index].next();
                  ++this.index;
               }

               return (Object[])this.results.clone();
            }
         }
      }
   }

   static class GetList implements Function {
      private static final String __OBFID = "CL_00002325";

      GetList(Object var1) {
         this();
      }

      private GetList() {
      }

      public Object apply(Object var1) {
         return this.apply((Object[])var1);
      }

      public List apply(Object[] var1) {
         return Arrays.asList(var1);
      }
   }
}
