package com.viaversion.viaversion.util;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public class Triple<A, B, C> {
   private final A first;
   private final B second;
   private final C third;

   public Triple(@Nullable A first, @Nullable B second, @Nullable C third) {
      this.first = first;
      this.second = second;
      this.third = third;
   }

   @Nullable
   public A first() {
      return this.first;
   }

   @Nullable
   public B second() {
      return this.second;
   }

   @Nullable
   public C third() {
      return this.third;
   }

   @Deprecated
   @Nullable
   public A getFirst() {
      return this.first;
   }

   @Deprecated
   @Nullable
   public B getSecond() {
      return this.second;
   }

   @Deprecated
   @Nullable
   public C getThird() {
      return this.third;
   }

   @Override
   public String toString() {
      return "Triple{" + this.first + ", " + this.second + ", " + this.third + '}';
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Triple<?, ?, ?> triple = (Triple<?, ?, ?>)o;
         if (!Objects.equals(this.first, triple.first)) {
            return false;
         } else {
            return !Objects.equals(this.second, triple.second) ? false : Objects.equals(this.third, triple.third);
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int result = this.first != null ? this.first.hashCode() : 0;
      result = 31 * result + (this.second != null ? this.second.hashCode() : 0);
      return 31 * result + (this.third != null ? this.third.hashCode() : 0);
   }
}
