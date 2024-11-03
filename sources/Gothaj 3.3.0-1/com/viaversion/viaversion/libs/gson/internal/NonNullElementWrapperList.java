package com.viaversion.viaversion.libs.gson.internal;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.RandomAccess;

public class NonNullElementWrapperList<E> extends AbstractList<E> implements RandomAccess {
   private final ArrayList<E> delegate;

   public NonNullElementWrapperList(ArrayList<E> delegate) {
      this.delegate = Objects.requireNonNull(delegate);
   }

   @Override
   public E get(int index) {
      return this.delegate.get(index);
   }

   @Override
   public int size() {
      return this.delegate.size();
   }

   private E nonNull(E element) {
      if (element == null) {
         throw new NullPointerException("Element must be non-null");
      } else {
         return element;
      }
   }

   @Override
   public E set(int index, E element) {
      return this.delegate.set(index, this.nonNull(element));
   }

   @Override
   public void add(int index, E element) {
      this.delegate.add(index, this.nonNull(element));
   }

   @Override
   public E remove(int index) {
      return this.delegate.remove(index);
   }

   @Override
   public void clear() {
      this.delegate.clear();
   }

   @Override
   public boolean remove(Object o) {
      return this.delegate.remove(o);
   }

   @Override
   public boolean removeAll(Collection<?> c) {
      return this.delegate.removeAll(c);
   }

   @Override
   public boolean retainAll(Collection<?> c) {
      return this.delegate.retainAll(c);
   }

   @Override
   public boolean contains(Object o) {
      return this.delegate.contains(o);
   }

   @Override
   public int indexOf(Object o) {
      return this.delegate.indexOf(o);
   }

   @Override
   public int lastIndexOf(Object o) {
      return this.delegate.lastIndexOf(o);
   }

   @Override
   public Object[] toArray() {
      return this.delegate.toArray();
   }

   @Override
   public <T> T[] toArray(T[] a) {
      return (T[])this.delegate.toArray(a);
   }

   @Override
   public boolean equals(Object o) {
      return this.delegate.equals(o);
   }

   @Override
   public int hashCode() {
      return this.delegate.hashCode();
   }
}
