package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.Serializable;
import java.util.Comparator;

public final class ObjectComparators {
   public static final Comparator NATURAL_COMPARATOR = new ObjectComparators.NaturalImplicitComparator();
   public static final Comparator OPPOSITE_COMPARATOR = new ObjectComparators.OppositeImplicitComparator();

   private ObjectComparators() {
   }

   public static <K> Comparator<K> oppositeComparator(Comparator<K> c) {
      return (Comparator<K>)(c instanceof ObjectComparators.OppositeComparator
         ? ((ObjectComparators.OppositeComparator)c).comparator
         : new ObjectComparators.OppositeComparator<>(c));
   }

   public static <K> Comparator<K> asObjectComparator(Comparator<K> c) {
      return c;
   }

   protected static class NaturalImplicitComparator implements Comparator, Serializable {
      private static final long serialVersionUID = 1L;

      @Override
      public final int compare(Object a, Object b) {
         return ((Comparable)a).compareTo(b);
      }

      @Override
      public Comparator reversed() {
         return ObjectComparators.OPPOSITE_COMPARATOR;
      }

      private Object readResolve() {
         return ObjectComparators.NATURAL_COMPARATOR;
      }
   }

   protected static class OppositeComparator<K> implements Comparator<K>, Serializable {
      private static final long serialVersionUID = 1L;
      final Comparator<K> comparator;

      protected OppositeComparator(Comparator<K> c) {
         this.comparator = c;
      }

      @Override
      public final int compare(K a, K b) {
         return this.comparator.compare(b, a);
      }

      @Override
      public final Comparator<K> reversed() {
         return this.comparator;
      }
   }

   protected static class OppositeImplicitComparator implements Comparator, Serializable {
      private static final long serialVersionUID = 1L;

      @Override
      public final int compare(Object a, Object b) {
         return ((Comparable)b).compareTo(a);
      }

      @Override
      public Comparator reversed() {
         return ObjectComparators.NATURAL_COMPARATOR;
      }

      private Object readResolve() {
         return ObjectComparators.OPPOSITE_COMPARATOR;
      }
   }
}
