package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSets.SynchronizedSortedSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSets.UnmodifiableSortedSet;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

public final class ObjectSortedSets {
   public static final ObjectSortedSets.EmptySet EMPTY_SET = new ObjectSortedSets.EmptySet();

   private ObjectSortedSets() {
   }

   public static <K> ObjectSet<K> emptySet() {
      return EMPTY_SET;
   }

   public static <K> ObjectSortedSet<K> singleton(K element) {
      return new ObjectSortedSets.Singleton<>(element);
   }

   public static <K> ObjectSortedSet<K> singleton(K element, Comparator<? super K> comparator) {
      return new ObjectSortedSets.Singleton<>(element, comparator);
   }

   public static <K> ObjectSortedSet<K> synchronize(ObjectSortedSet<K> s) {
      return new SynchronizedSortedSet(s);
   }

   public static <K> ObjectSortedSet<K> synchronize(ObjectSortedSet<K> s, Object sync) {
      return new SynchronizedSortedSet(s, sync);
   }

   public static <K> ObjectSortedSet<K> unmodifiable(ObjectSortedSet<K> s) {
      return new UnmodifiableSortedSet(s);
   }

   public static class EmptySet<K> extends ObjectSets.EmptySet<K> implements ObjectSortedSet<K>, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptySet() {
      }

      @Override
      public ObjectBidirectionalIterator<K> iterator(K from) {
         return ObjectIterators.EMPTY_ITERATOR;
      }

      @Override
      public ObjectSortedSet<K> subSet(K from, K to) {
         return ObjectSortedSets.EMPTY_SET;
      }

      @Override
      public ObjectSortedSet<K> headSet(K from) {
         return ObjectSortedSets.EMPTY_SET;
      }

      @Override
      public ObjectSortedSet<K> tailSet(K to) {
         return ObjectSortedSets.EMPTY_SET;
      }

      @Override
      public K first() {
         throw new NoSuchElementException();
      }

      @Override
      public K last() {
         throw new NoSuchElementException();
      }

      @Override
      public Comparator<? super K> comparator() {
         return null;
      }

      @Override
      public Object clone() {
         return ObjectSortedSets.EMPTY_SET;
      }

      private Object readResolve() {
         return ObjectSortedSets.EMPTY_SET;
      }
   }

   public static class Singleton<K> extends ObjectSets.Singleton<K> implements ObjectSortedSet<K>, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      final Comparator<? super K> comparator;

      protected Singleton(K element, Comparator<? super K> comparator) {
         super(element);
         this.comparator = comparator;
      }

      Singleton(K element) {
         this(element, null);
      }

      final int compare(K k1, K k2) {
         return this.comparator == null ? ((Comparable)k1).compareTo(k2) : this.comparator.compare(k1, k2);
      }

      @Override
      public ObjectBidirectionalIterator<K> iterator(K from) {
         ObjectBidirectionalIterator<K> i = this.iterator();
         if (this.compare(this.element, from) <= 0) {
            i.next();
         }

         return i;
      }

      @Override
      public Comparator<? super K> comparator() {
         return this.comparator;
      }

      @Override
      public ObjectSpliterator<K> spliterator() {
         return ObjectSpliterators.singleton(this.element, this.comparator);
      }

      @Override
      public ObjectSortedSet<K> subSet(K from, K to) {
         return (ObjectSortedSet<K>)(this.compare(from, this.element) <= 0 && this.compare(this.element, to) < 0 ? this : ObjectSortedSets.EMPTY_SET);
      }

      @Override
      public ObjectSortedSet<K> headSet(K to) {
         return (ObjectSortedSet<K>)(this.compare(this.element, to) < 0 ? this : ObjectSortedSets.EMPTY_SET);
      }

      @Override
      public ObjectSortedSet<K> tailSet(K from) {
         return (ObjectSortedSet<K>)(this.compare(from, this.element) <= 0 ? this : ObjectSortedSets.EMPTY_SET);
      }

      @Override
      public K first() {
         return this.element;
      }

      @Override
      public K last() {
         return this.element;
      }
   }
}
