package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectSets.SynchronizedSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSets.UnmodifiableSet;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class ObjectSets {
   static final int ARRAY_SET_CUTOFF = 4;
   public static final ObjectSets.EmptySet EMPTY_SET = new ObjectSets.EmptySet();
   static final ObjectSet UNMODIFIABLE_EMPTY_SET = unmodifiable(new ObjectArraySet(ObjectArrays.EMPTY_ARRAY));

   private ObjectSets() {
   }

   public static <K> ObjectSet<K> emptySet() {
      return EMPTY_SET;
   }

   public static <K> ObjectSet<K> singleton(K element) {
      return new ObjectSets.Singleton<>(element);
   }

   public static <K> ObjectSet<K> synchronize(ObjectSet<K> s) {
      return new SynchronizedSet(s);
   }

   public static <K> ObjectSet<K> synchronize(ObjectSet<K> s, Object sync) {
      return new SynchronizedSet(s, sync);
   }

   public static <K> ObjectSet<K> unmodifiable(ObjectSet<? extends K> s) {
      return new UnmodifiableSet(s);
   }

   public static class EmptySet<K> extends ObjectCollections.EmptyCollection<K> implements ObjectSet<K>, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptySet() {
      }

      @Override
      public boolean remove(Object ok) {
         throw new UnsupportedOperationException();
      }

      @Override
      public Object clone() {
         return ObjectSets.EMPTY_SET;
      }

      @Override
      public boolean equals(Object o) {
         return o instanceof Set && ((Set)o).isEmpty();
      }

      private Object readResolve() {
         return ObjectSets.EMPTY_SET;
      }
   }

   public static class Singleton<K> extends AbstractObjectSet<K> implements Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected final K element;

      protected Singleton(K element) {
         this.element = element;
      }

      @Override
      public boolean contains(Object k) {
         return Objects.equals(k, this.element);
      }

      @Override
      public boolean remove(Object k) {
         throw new UnsupportedOperationException();
      }

      public ObjectListIterator<K> iterator() {
         return ObjectIterators.singleton(this.element);
      }

      @Override
      public ObjectSpliterator<K> spliterator() {
         return ObjectSpliterators.singleton(this.element);
      }

      @Override
      public int size() {
         return 1;
      }

      @Override
      public Object[] toArray() {
         return new Object[]{this.element};
      }

      @Override
      public void forEach(Consumer<? super K> action) {
         action.accept(this.element);
      }

      @Override
      public boolean addAll(Collection<? extends K> c) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean removeAll(Collection<?> c) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean retainAll(Collection<?> c) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean removeIf(Predicate<? super K> filter) {
         throw new UnsupportedOperationException();
      }

      @Override
      public Object clone() {
         return this;
      }
   }
}
