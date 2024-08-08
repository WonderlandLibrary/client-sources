package org.yaml.snakeyaml.util;

import java.util.AbstractList;
import java.util.Collections;
import java.util.List;

public class ArrayUtils {
   private ArrayUtils() {
   }

   public static List toUnmodifiableList(Object... var0) {
      return (List)(var0.length == 0 ? Collections.emptyList() : new ArrayUtils.UnmodifiableArrayList(var0));
   }

   public static List toUnmodifiableCompositeList(Object[] var0, Object[] var1) {
      Object var2;
      if (var0.length == 0) {
         var2 = toUnmodifiableList(var1);
      } else if (var1.length == 0) {
         var2 = toUnmodifiableList(var0);
      } else {
         var2 = new ArrayUtils.CompositeUnmodifiableArrayList(var0, var1);
      }

      return (List)var2;
   }

   private static class CompositeUnmodifiableArrayList extends AbstractList {
      private final Object[] array1;
      private final Object[] array2;

      CompositeUnmodifiableArrayList(Object[] var1, Object[] var2) {
         this.array1 = var1;
         this.array2 = var2;
      }

      public Object get(int var1) {
         Object var2;
         if (var1 < this.array1.length) {
            var2 = this.array1[var1];
         } else {
            if (var1 - this.array1.length >= this.array2.length) {
               throw new IndexOutOfBoundsException("Index: " + var1 + ", Size: " + this.size());
            }

            var2 = this.array2[var1 - this.array1.length];
         }

         return var2;
      }

      public int size() {
         return this.array1.length + this.array2.length;
      }
   }

   private static class UnmodifiableArrayList extends AbstractList {
      private final Object[] array;

      UnmodifiableArrayList(Object[] var1) {
         this.array = var1;
      }

      public Object get(int var1) {
         if (var1 >= this.array.length) {
            throw new IndexOutOfBoundsException("Index: " + var1 + ", Size: " + this.size());
         } else {
            return this.array[var1];
         }
      }

      public int size() {
         return this.array.length;
      }
   }
}
