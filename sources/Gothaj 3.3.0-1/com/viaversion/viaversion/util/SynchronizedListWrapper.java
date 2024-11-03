package com.viaversion.viaversion.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class SynchronizedListWrapper<E> implements List<E> {
   private final List<E> list;
   private final Consumer<E> addHandler;

   public SynchronizedListWrapper(List<E> inputList, Consumer<E> addHandler) {
      this.list = inputList;
      this.addHandler = addHandler;
   }

   public List<E> originalList() {
      return this.list;
   }

   private void handleAdd(E o) {
      this.addHandler.accept(o);
   }

   @Override
   public int size() {
      synchronized (this) {
         return this.list.size();
      }
   }

   @Override
   public boolean isEmpty() {
      synchronized (this) {
         return this.list.isEmpty();
      }
   }

   @Override
   public boolean contains(Object o) {
      synchronized (this) {
         return this.list.contains(o);
      }
   }

   @NonNull
   @Override
   public Iterator<E> iterator() {
      return this.listIterator();
   }

   @NonNull
   @Override
   public Object[] toArray() {
      synchronized (this) {
         return this.list.toArray();
      }
   }

   @Override
   public boolean add(E o) {
      synchronized (this) {
         this.handleAdd(o);
         return this.list.add(o);
      }
   }

   @Override
   public boolean remove(Object o) {
      synchronized (this) {
         return this.list.remove(o);
      }
   }

   @Override
   public boolean addAll(Collection<? extends E> c) {
      synchronized (this) {
         for (E o : c) {
            this.handleAdd(o);
         }

         return this.list.addAll(c);
      }
   }

   @Override
   public boolean addAll(int index, Collection<? extends E> c) {
      synchronized (this) {
         for (E o : c) {
            this.handleAdd(o);
         }

         return this.list.addAll(index, c);
      }
   }

   @Override
   public void clear() {
      synchronized (this) {
         this.list.clear();
      }
   }

   @Override
   public E get(int index) {
      synchronized (this) {
         return this.list.get(index);
      }
   }

   @Override
   public E set(int index, E element) {
      synchronized (this) {
         return this.list.set(index, element);
      }
   }

   @Override
   public void add(int index, E element) {
      synchronized (this) {
         this.list.add(index, element);
      }
   }

   @Override
   public E remove(int index) {
      synchronized (this) {
         return this.list.remove(index);
      }
   }

   @Override
   public int indexOf(Object o) {
      synchronized (this) {
         return this.list.indexOf(o);
      }
   }

   @Override
   public int lastIndexOf(Object o) {
      synchronized (this) {
         return this.list.lastIndexOf(o);
      }
   }

   @NonNull
   @Override
   public ListIterator<E> listIterator() {
      return this.list.listIterator();
   }

   @NonNull
   @Override
   public ListIterator<E> listIterator(int index) {
      return this.list.listIterator(index);
   }

   @NonNull
   @Override
   public List<E> subList(int fromIndex, int toIndex) {
      synchronized (this) {
         return this.list.subList(fromIndex, toIndex);
      }
   }

   @Override
   public boolean retainAll(@NonNull Collection<?> c) {
      synchronized (this) {
         return this.list.retainAll(c);
      }
   }

   @Override
   public boolean removeAll(@NonNull Collection<?> c) {
      synchronized (this) {
         return this.list.removeAll(c);
      }
   }

   @Override
   public boolean containsAll(@NonNull Collection<?> c) {
      synchronized (this) {
         return this.list.containsAll(c);
      }
   }

   @NonNull
   @Override
   public <T> T[] toArray(@NonNull T[] a) {
      synchronized (this) {
         return (T[])this.list.toArray(a);
      }
   }

   @Override
   public void sort(Comparator<? super E> c) {
      synchronized (this) {
         this.list.sort(c);
      }
   }

   @Override
   public void forEach(Consumer<? super E> consumer) {
      synchronized (this) {
         this.list.forEach(consumer);
      }
   }

   @Override
   public boolean removeIf(Predicate<? super E> filter) {
      synchronized (this) {
         return this.list.removeIf(filter);
      }
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else {
         synchronized (this) {
            return this.list.equals(o);
         }
      }
   }

   @Override
   public int hashCode() {
      synchronized (this) {
         return this.list.hashCode();
      }
   }

   @Override
   public String toString() {
      synchronized (this) {
         return this.list.toString();
      }
   }
}
