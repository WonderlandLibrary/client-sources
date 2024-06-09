/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Queue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class MpscLinkedQueue<E>
/*     */   extends MpscLinkedQueueTailRef<E>
/*     */   implements Queue<E>
/*     */ {
/*     */   private static final long serialVersionUID = -1878402552271506449L;
/*     */   long p00;
/*     */   long p01;
/*     */   long p02;
/*     */   long p03;
/*     */   long p04;
/*     */   long p05;
/*     */   long p06;
/*     */   long p07;
/*     */   long p30;
/*     */   long p31;
/*     */   long p32;
/*     */   long p33;
/*     */   long p34;
/*     */   long p35;
/*     */   long p36;
/*     */   long p37;
/*     */   
/*     */   MpscLinkedQueue() {
/*  91 */     MpscLinkedQueueNode<E> tombstone = new DefaultNode<E>(null);
/*  92 */     setHeadRef(tombstone);
/*  93 */     setTailRef(tombstone);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MpscLinkedQueueNode<E> peekNode() {
/*     */     while (true) {
/* 101 */       MpscLinkedQueueNode<E> head = headRef();
/* 102 */       MpscLinkedQueueNode<E> next = head.next();
/* 103 */       if (next != null) {
/* 104 */         return next;
/*     */       }
/* 106 */       if (head == tailRef()) {
/* 107 */         return null;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean offer(E value) {
/*     */     MpscLinkedQueueNode<E> newTail;
/* 120 */     if (value == null) {
/* 121 */       throw new NullPointerException("value");
/*     */     }
/*     */ 
/*     */     
/* 125 */     if (value instanceof MpscLinkedQueueNode) {
/* 126 */       newTail = (MpscLinkedQueueNode<E>)value;
/* 127 */       newTail.setNext(null);
/*     */     } else {
/* 129 */       newTail = new DefaultNode<E>(value);
/*     */     } 
/*     */     
/* 132 */     MpscLinkedQueueNode<E> oldTail = getAndSetTailRef(newTail);
/* 133 */     oldTail.setNext(newTail);
/* 134 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public E poll() {
/* 139 */     MpscLinkedQueueNode<E> next = peekNode();
/* 140 */     if (next == null) {
/* 141 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 145 */     MpscLinkedQueueNode<E> oldHead = headRef();
/*     */ 
/*     */ 
/*     */     
/* 149 */     lazySetHeadRef(next);
/*     */ 
/*     */     
/* 152 */     oldHead.unlink();
/*     */     
/* 154 */     return next.clearMaybe();
/*     */   }
/*     */ 
/*     */   
/*     */   public E peek() {
/* 159 */     MpscLinkedQueueNode<E> next = peekNode();
/* 160 */     if (next == null) {
/* 161 */       return null;
/*     */     }
/* 163 */     return next.value();
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 168 */     int count = 0;
/* 169 */     MpscLinkedQueueNode<E> n = peekNode();
/*     */     
/* 171 */     while (n != null) {
/*     */ 
/*     */       
/* 174 */       count++;
/* 175 */       n = n.next();
/*     */     } 
/* 177 */     return count;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 182 */     return (peekNode() == null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object o) {
/* 187 */     MpscLinkedQueueNode<E> n = peekNode();
/*     */     
/* 189 */     while (n != null) {
/*     */ 
/*     */       
/* 192 */       if (n.value() == o) {
/* 193 */         return true;
/*     */       }
/* 195 */       n = n.next();
/*     */     } 
/* 197 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<E> iterator() {
/* 202 */     return new Iterator<E>() {
/* 203 */         private MpscLinkedQueueNode<E> node = MpscLinkedQueue.this.peekNode();
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 207 */           return (this.node != null);
/*     */         }
/*     */ 
/*     */         
/*     */         public E next() {
/* 212 */           MpscLinkedQueueNode<E> node = this.node;
/* 213 */           if (node == null) {
/* 214 */             throw new NoSuchElementException();
/*     */           }
/* 216 */           E value = node.value();
/* 217 */           this.node = node.next();
/* 218 */           return value;
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 223 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(E e) {
/* 230 */     if (offer(e)) {
/* 231 */       return true;
/*     */     }
/* 233 */     throw new IllegalStateException("queue full");
/*     */   }
/*     */ 
/*     */   
/*     */   public E remove() {
/* 238 */     E e = poll();
/* 239 */     if (e != null) {
/* 240 */       return e;
/*     */     }
/* 242 */     throw new NoSuchElementException();
/*     */   }
/*     */ 
/*     */   
/*     */   public E element() {
/* 247 */     E e = peek();
/* 248 */     if (e != null) {
/* 249 */       return e;
/*     */     }
/* 251 */     throw new NoSuchElementException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/* 256 */     Object[] array = new Object[size()];
/* 257 */     Iterator<E> it = iterator();
/* 258 */     for (int i = 0; i < array.length; i++) {
/* 259 */       if (it.hasNext()) {
/* 260 */         array[i] = it.next();
/*     */       } else {
/* 262 */         return Arrays.copyOf(array, i);
/*     */       } 
/*     */     } 
/* 265 */     return array;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T[] toArray(T[] a) {
/*     */     T[] array;
/* 271 */     int size = size();
/*     */     
/* 273 */     if (a.length >= size) {
/* 274 */       array = a;
/*     */     } else {
/* 276 */       array = (T[])Array.newInstance(a.getClass().getComponentType(), size);
/*     */     } 
/*     */     
/* 279 */     Iterator<E> it = iterator();
/* 280 */     for (int i = 0; i < array.length; i++) {
/* 281 */       if (it.hasNext()) {
/* 282 */         array[i] = (T)it.next();
/*     */       } else {
/* 284 */         if (a == array) {
/* 285 */           array[i] = null;
/* 286 */           return array;
/*     */         } 
/*     */         
/* 289 */         if (a.length < i) {
/* 290 */           return Arrays.copyOf(array, i);
/*     */         }
/*     */         
/* 293 */         System.arraycopy(array, 0, a, 0, i);
/* 294 */         if (a.length > i) {
/* 295 */           a[i] = null;
/*     */         }
/* 297 */         return a;
/*     */       } 
/*     */     } 
/* 300 */     return array;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object o) {
/* 305 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAll(Collection<?> c) {
/* 310 */     for (Object e : c) {
/* 311 */       if (!contains(e)) {
/* 312 */         return false;
/*     */       }
/*     */     } 
/* 315 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends E> c) {
/* 320 */     if (c == null) {
/* 321 */       throw new NullPointerException("c");
/*     */     }
/* 323 */     if (c == this) {
/* 324 */       throw new IllegalArgumentException("c == this");
/*     */     }
/*     */     
/* 327 */     boolean modified = false;
/* 328 */     for (E e : c) {
/* 329 */       add(e);
/* 330 */       modified = true;
/*     */     } 
/* 332 */     return modified;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/* 337 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(Collection<?> c) {
/* 342 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 347 */     while (poll() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 353 */     out.defaultWriteObject();
/* 354 */     for (E e : this) {
/* 355 */       out.writeObject(e);
/*     */     }
/* 357 */     out.writeObject(null);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 361 */     in.defaultReadObject();
/*     */     
/* 363 */     MpscLinkedQueueNode<E> tombstone = new DefaultNode<E>(null);
/* 364 */     setHeadRef(tombstone);
/* 365 */     setTailRef(tombstone);
/*     */ 
/*     */     
/*     */     while (true) {
/* 369 */       E e = (E)in.readObject();
/* 370 */       if (e == null) {
/*     */         break;
/*     */       }
/* 373 */       add(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class DefaultNode<T>
/*     */     extends MpscLinkedQueueNode<T> {
/*     */     private T value;
/*     */     
/*     */     DefaultNode(T value) {
/* 382 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public T value() {
/* 387 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     protected T clearMaybe() {
/* 392 */       T value = this.value;
/* 393 */       this.value = null;
/* 394 */       return value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\internal\MpscLinkedQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */