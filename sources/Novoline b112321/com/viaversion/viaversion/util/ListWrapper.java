package com.viaversion.viaversion.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/** @deprecated */
@Deprecated
public abstract class ListWrapper implements List {
   private final List list;

   public ListWrapper(List var1) {
      this.list = var1;
   }

   public abstract void handleAdd(Object var1);

   public List getOriginalList() {
      return this.list;
   }

   public int size() {
      // $FF: Couldn't be decompiled
   }

   public boolean isEmpty() {
      // $FF: Couldn't be decompiled
   }

   public boolean contains(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public Iterator iterator() {
      // $FF: Couldn't be decompiled
   }

   public Object[] toArray() {
      // $FF: Couldn't be decompiled
   }

   public boolean add(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean remove(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean addAll(Collection param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean addAll(int param1, Collection param2) {
      // $FF: Couldn't be decompiled
   }

   public void clear() {
      // $FF: Couldn't be decompiled
   }

   public Object get(int param1) {
      // $FF: Couldn't be decompiled
   }

   public Object set(int param1, Object param2) {
      // $FF: Couldn't be decompiled
   }

   public void add(int param1, Object param2) {
      // $FF: Couldn't be decompiled
   }

   public Object remove(int param1) {
      // $FF: Couldn't be decompiled
   }

   public int indexOf(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public int lastIndexOf(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public ListIterator listIterator() {
      // $FF: Couldn't be decompiled
   }

   public ListIterator listIterator(int param1) {
      // $FF: Couldn't be decompiled
   }

   public List subList(int param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public boolean retainAll(Collection param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean removeAll(Collection param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean containsAll(Collection param1) {
      // $FF: Couldn't be decompiled
   }

   public Object[] toArray(Object[] param1) {
      // $FF: Couldn't be decompiled
   }
}
