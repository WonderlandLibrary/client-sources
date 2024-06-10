/*     */ package it.unimi.dsi.fastutil.booleans;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrays;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
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
/*     */ public final class BooleanCollections
/*     */ {
/*     */   public static abstract class EmptyCollection
/*     */     extends AbstractBooleanCollection
/*     */   {
/*     */     public boolean contains(boolean k) {
/*  40 */       return false;
/*     */     }
/*     */     
/*     */     public Object[] toArray() {
/*  44 */       return ObjectArrays.EMPTY_ARRAY;
/*     */     }
/*     */ 
/*     */     
/*     */     public BooleanBidirectionalIterator iterator() {
/*  49 */       return BooleanIterators.EMPTY_ITERATOR;
/*     */     }
/*     */     
/*     */     public int size() {
/*  53 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {}
/*     */     
/*     */     public int hashCode() {
/*  60 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/*  64 */       if (o == this)
/*  65 */         return true; 
/*  66 */       if (!(o instanceof Collection))
/*  67 */         return false; 
/*  68 */       return ((Collection)o).isEmpty();
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends Boolean> c) {
/*  72 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/*  76 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/*  80 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean addAll(BooleanCollection c) {
/*  84 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean removeAll(BooleanCollection c) {
/*  88 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean retainAll(BooleanCollection c) {
/*  92 */       throw new UnsupportedOperationException();
/*     */     } }
/*     */   
/*     */   public static class SynchronizedCollection implements BooleanCollection, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final BooleanCollection collection;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedCollection(BooleanCollection c, Object sync) {
/* 101 */       if (c == null)
/* 102 */         throw new NullPointerException(); 
/* 103 */       this.collection = c;
/* 104 */       this.sync = sync;
/*     */     }
/*     */     protected SynchronizedCollection(BooleanCollection c) {
/* 107 */       if (c == null)
/* 108 */         throw new NullPointerException(); 
/* 109 */       this.collection = c;
/* 110 */       this.sync = this;
/*     */     }
/*     */     
/*     */     public boolean add(boolean k) {
/* 114 */       synchronized (this.sync) {
/* 115 */         return this.collection.add(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean contains(boolean k) {
/* 120 */       synchronized (this.sync) {
/* 121 */         return this.collection.contains(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean rem(boolean k) {
/* 126 */       synchronized (this.sync) {
/* 127 */         return this.collection.rem(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/* 132 */       synchronized (this.sync) {
/* 133 */         return this.collection.size();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 138 */       synchronized (this.sync) {
/* 139 */         return this.collection.isEmpty();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean[] toBooleanArray() {
/* 144 */       synchronized (this.sync) {
/* 145 */         return this.collection.toBooleanArray();
/*     */       } 
/*     */     }
/*     */     
/*     */     public Object[] toArray() {
/* 150 */       synchronized (this.sync) {
/* 151 */         return this.collection.toArray();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean[] toBooleanArray(boolean[] a) {
/* 163 */       return toArray(a);
/*     */     }
/*     */     
/*     */     public boolean[] toArray(boolean[] a) {
/* 167 */       synchronized (this.sync) {
/* 168 */         return this.collection.toArray(a);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean addAll(BooleanCollection c) {
/* 173 */       synchronized (this.sync) {
/* 174 */         return this.collection.addAll(c);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean containsAll(BooleanCollection c) {
/* 179 */       synchronized (this.sync) {
/* 180 */         return this.collection.containsAll(c);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean removeAll(BooleanCollection c) {
/* 185 */       synchronized (this.sync) {
/* 186 */         return this.collection.removeAll(c);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean retainAll(BooleanCollection c) {
/* 191 */       synchronized (this.sync) {
/* 192 */         return this.collection.retainAll(c);
/*     */       } 
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean add(Boolean k) {
/* 198 */       synchronized (this.sync) {
/* 199 */         return this.collection.add(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean contains(Object k) {
/* 205 */       synchronized (this.sync) {
/* 206 */         return this.collection.contains(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object k) {
/* 212 */       synchronized (this.sync) {
/* 213 */         return this.collection.remove(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public <T> T[] toArray(T[] a) {
/* 218 */       synchronized (this.sync) {
/* 219 */         return (T[])this.collection.toArray((Object[])a);
/*     */       } 
/*     */     }
/*     */     
/*     */     public BooleanIterator iterator() {
/* 224 */       return this.collection.iterator();
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends Boolean> c) {
/* 228 */       synchronized (this.sync) {
/* 229 */         return this.collection.addAll(c);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 234 */       synchronized (this.sync) {
/* 235 */         return this.collection.containsAll(c);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 240 */       synchronized (this.sync) {
/* 241 */         return this.collection.removeAll(c);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 246 */       synchronized (this.sync) {
/* 247 */         return this.collection.retainAll(c);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void clear() {
/* 252 */       synchronized (this.sync) {
/* 253 */         this.collection.clear();
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 258 */       synchronized (this.sync) {
/* 259 */         return this.collection.toString();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 264 */       synchronized (this.sync) {
/* 265 */         return this.collection.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 270 */       if (o == this)
/* 271 */         return true; 
/* 272 */       synchronized (this.sync) {
/* 273 */         return this.collection.equals(o);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 277 */       synchronized (this.sync) {
/* 278 */         s.defaultWriteObject();
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
/*     */   
/*     */   public static BooleanCollection synchronize(BooleanCollection c) {
/* 291 */     return new SynchronizedCollection(c);
/*     */   }
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
/*     */   public static BooleanCollection synchronize(BooleanCollection c, Object sync) {
/* 305 */     return new SynchronizedCollection(c, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableCollection implements BooleanCollection, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final BooleanCollection collection;
/*     */     
/*     */     protected UnmodifiableCollection(BooleanCollection c) {
/* 312 */       if (c == null)
/* 313 */         throw new NullPointerException(); 
/* 314 */       this.collection = c;
/*     */     }
/*     */     
/*     */     public boolean add(boolean k) {
/* 318 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean rem(boolean k) {
/* 322 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int size() {
/* 326 */       return this.collection.size();
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 330 */       return this.collection.isEmpty();
/*     */     }
/*     */     
/*     */     public boolean contains(boolean o) {
/* 334 */       return this.collection.contains(o);
/*     */     }
/*     */     
/*     */     public BooleanIterator iterator() {
/* 338 */       return BooleanIterators.unmodifiable(this.collection.iterator());
/*     */     }
/*     */     
/*     */     public void clear() {
/* 342 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public <T> T[] toArray(T[] a) {
/* 346 */       return (T[])this.collection.toArray((Object[])a);
/*     */     }
/*     */     
/*     */     public Object[] toArray() {
/* 350 */       return this.collection.toArray();
/*     */     }
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 354 */       return this.collection.containsAll(c);
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends Boolean> c) {
/* 358 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 362 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 366 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean add(Boolean k) {
/* 371 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean contains(Object k) {
/* 376 */       return this.collection.contains(k);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object k) {
/* 381 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean[] toBooleanArray() {
/* 385 */       return this.collection.toBooleanArray();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean[] toBooleanArray(boolean[] a) {
/* 396 */       return toArray(a);
/*     */     }
/*     */     
/*     */     public boolean[] toArray(boolean[] a) {
/* 400 */       return this.collection.toArray(a);
/*     */     }
/*     */     
/*     */     public boolean containsAll(BooleanCollection c) {
/* 404 */       return this.collection.containsAll(c);
/*     */     }
/*     */     
/*     */     public boolean addAll(BooleanCollection c) {
/* 408 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean removeAll(BooleanCollection c) {
/* 412 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean retainAll(BooleanCollection c) {
/* 416 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 420 */       return this.collection.toString();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 424 */       return this.collection.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 428 */       if (o == this)
/* 429 */         return true; 
/* 430 */       return this.collection.equals(o);
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BooleanCollection unmodifiable(BooleanCollection c) {
/* 442 */     return new UnmodifiableCollection(c);
/*     */   }
/*     */   
/*     */   public static class IterableCollection extends AbstractBooleanCollection implements Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final BooleanIterable iterable;
/*     */     
/*     */     protected IterableCollection(BooleanIterable iterable) {
/* 449 */       if (iterable == null)
/* 450 */         throw new NullPointerException(); 
/* 451 */       this.iterable = iterable;
/*     */     }
/*     */     
/*     */     public int size() {
/* 455 */       int c = 0;
/* 456 */       BooleanIterator iterator = iterator();
/* 457 */       while (iterator.hasNext()) {
/* 458 */         iterator.nextBoolean();
/* 459 */         c++;
/*     */       } 
/* 461 */       return c;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 465 */       return !this.iterable.iterator().hasNext();
/*     */     }
/*     */     
/*     */     public BooleanIterator iterator() {
/* 469 */       return this.iterable.iterator();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BooleanCollection asCollection(BooleanIterable iterable) {
/* 480 */     if (iterable instanceof BooleanCollection)
/* 481 */       return (BooleanCollection)iterable; 
/* 482 */     return new IterableCollection(iterable);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\booleans\BooleanCollections.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */