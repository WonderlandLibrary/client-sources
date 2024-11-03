package org.jsoup.helper;

import java.util.ArrayList;
import java.util.Collection;

public abstract class ChangeNotifyingArrayList<E> extends ArrayList<E> {
   public ChangeNotifyingArrayList(int initialCapacity) {
      super(initialCapacity);
   }

   public abstract void onContentsChanged();

   @Override
   public E set(int index, E element) {
      this.onContentsChanged();
      return super.set(index, element);
   }

   @Override
   public boolean add(E e) {
      this.onContentsChanged();
      return super.add(e);
   }

   @Override
   public void add(int index, E element) {
      this.onContentsChanged();
      super.add(index, element);
   }

   @Override
   public E remove(int index) {
      this.onContentsChanged();
      return super.remove(index);
   }

   @Override
   public boolean remove(Object o) {
      this.onContentsChanged();
      return super.remove(o);
   }

   @Override
   public void clear() {
      this.onContentsChanged();
      super.clear();
   }

   @Override
   public boolean addAll(Collection<? extends E> c) {
      this.onContentsChanged();
      return super.addAll(c);
   }

   @Override
   public boolean addAll(int index, Collection<? extends E> c) {
      this.onContentsChanged();
      return super.addAll(index, c);
   }

   @Override
   protected void removeRange(int fromIndex, int toIndex) {
      this.onContentsChanged();
      super.removeRange(fromIndex, toIndex);
   }

   @Override
   public boolean removeAll(Collection<?> c) {
      this.onContentsChanged();
      return super.removeAll(c);
   }

   @Override
   public boolean retainAll(Collection<?> c) {
      this.onContentsChanged();
      return super.retainAll(c);
   }
}
