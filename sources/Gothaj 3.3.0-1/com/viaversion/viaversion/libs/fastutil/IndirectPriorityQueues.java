package com.viaversion.viaversion.libs.fastutil;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class IndirectPriorityQueues {
   public static final IndirectPriorityQueues.EmptyIndirectPriorityQueue EMPTY_QUEUE = new IndirectPriorityQueues.EmptyIndirectPriorityQueue();

   private IndirectPriorityQueues() {
   }

   public static <K> IndirectPriorityQueue<K> synchronize(IndirectPriorityQueue<K> q) {
      return new IndirectPriorityQueues.SynchronizedIndirectPriorityQueue<>(q);
   }

   public static <K> IndirectPriorityQueue<K> synchronize(IndirectPriorityQueue<K> q, Object sync) {
      return new IndirectPriorityQueues.SynchronizedIndirectPriorityQueue<>(q, sync);
   }

   public static class EmptyIndirectPriorityQueue implements IndirectPriorityQueue {
      protected EmptyIndirectPriorityQueue() {
      }

      @Override
      public void enqueue(int i) {
         throw new UnsupportedOperationException();
      }

      @Override
      public int dequeue() {
         throw new NoSuchElementException();
      }

      @Override
      public boolean isEmpty() {
         return true;
      }

      @Override
      public int size() {
         return 0;
      }

      @Override
      public boolean contains(int index) {
         return false;
      }

      @Override
      public void clear() {
      }

      @Override
      public int first() {
         throw new NoSuchElementException();
      }

      @Override
      public int last() {
         throw new NoSuchElementException();
      }

      @Override
      public void changed() {
         throw new NoSuchElementException();
      }

      @Override
      public void allChanged() {
      }

      @Override
      public Comparator<?> comparator() {
         return null;
      }

      @Override
      public void changed(int i) {
         throw new IllegalArgumentException("Index " + i + " is not in the queue");
      }

      @Override
      public boolean remove(int i) {
         return false;
      }

      @Override
      public int front(int[] a) {
         return 0;
      }
   }

   public static class SynchronizedIndirectPriorityQueue<K> implements IndirectPriorityQueue<K> {
      public static final long serialVersionUID = -7046029254386353129L;
      protected final IndirectPriorityQueue<K> q;
      protected final Object sync;

      protected SynchronizedIndirectPriorityQueue(IndirectPriorityQueue<K> q, Object sync) {
         this.q = q;
         this.sync = sync;
      }

      protected SynchronizedIndirectPriorityQueue(IndirectPriorityQueue<K> q) {
         this.q = q;
         this.sync = this;
      }

      @Override
      public void enqueue(int x) {
         synchronized (this.sync) {
            this.q.enqueue(x);
         }
      }

      @Override
      public int dequeue() {
         synchronized (this.sync) {
            return this.q.dequeue();
         }
      }

      @Override
      public boolean contains(int index) {
         synchronized (this.sync) {
            return this.q.contains(index);
         }
      }

      @Override
      public int first() {
         synchronized (this.sync) {
            return this.q.first();
         }
      }

      @Override
      public int last() {
         synchronized (this.sync) {
            return this.q.last();
         }
      }

      @Override
      public boolean isEmpty() {
         synchronized (this.sync) {
            return this.q.isEmpty();
         }
      }

      @Override
      public int size() {
         synchronized (this.sync) {
            return this.q.size();
         }
      }

      @Override
      public void clear() {
         synchronized (this.sync) {
            this.q.clear();
         }
      }

      @Override
      public void changed() {
         synchronized (this.sync) {
            this.q.changed();
         }
      }

      @Override
      public void allChanged() {
         synchronized (this.sync) {
            this.q.allChanged();
         }
      }

      @Override
      public void changed(int i) {
         synchronized (this.sync) {
            this.q.changed(i);
         }
      }

      @Override
      public boolean remove(int i) {
         synchronized (this.sync) {
            return this.q.remove(i);
         }
      }

      @Override
      public Comparator<? super K> comparator() {
         synchronized (this.sync) {
            return this.q.comparator();
         }
      }

      @Override
      public int front(int[] a) {
         return this.q.front(a);
      }
   }
}
