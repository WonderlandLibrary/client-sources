/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrays;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.function.DoublePredicate;
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
/*     */ public final class FloatCollections
/*     */ {
/*     */   public static abstract class EmptyCollection
/*     */     extends AbstractFloatCollection
/*     */   {
/*     */     public boolean contains(float k) {
/*  40 */       return false;
/*     */     }
/*     */     
/*     */     public Object[] toArray() {
/*  44 */       return ObjectArrays.EMPTY_ARRAY;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatBidirectionalIterator iterator() {
/*  49 */       return FloatIterators.EMPTY_ITERATOR;
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
/*     */     public boolean addAll(Collection<? extends Float> c) {
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
/*     */     public boolean addAll(FloatCollection c) {
/*  84 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean removeAll(FloatCollection c) {
/*  88 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean retainAll(FloatCollection c) {
/*  92 */       throw new UnsupportedOperationException();
/*     */     } }
/*     */   
/*     */   public static class SynchronizedCollection implements FloatCollection, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final FloatCollection collection;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedCollection(FloatCollection c, Object sync) {
/* 101 */       if (c == null)
/* 102 */         throw new NullPointerException(); 
/* 103 */       this.collection = c;
/* 104 */       this.sync = sync;
/*     */     }
/*     */     protected SynchronizedCollection(FloatCollection c) {
/* 107 */       if (c == null)
/* 108 */         throw new NullPointerException(); 
/* 109 */       this.collection = c;
/* 110 */       this.sync = this;
/*     */     }
/*     */     
/*     */     public boolean add(float k) {
/* 114 */       synchronized (this.sync) {
/* 115 */         return this.collection.add(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean contains(float k) {
/* 120 */       synchronized (this.sync) {
/* 121 */         return this.collection.contains(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean rem(float k) {
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
/*     */     public float[] toFloatArray() {
/* 144 */       synchronized (this.sync) {
/* 145 */         return this.collection.toFloatArray();
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
/*     */     public float[] toFloatArray(float[] a) {
/* 163 */       return toArray(a);
/*     */     }
/*     */     
/*     */     public float[] toArray(float[] a) {
/* 167 */       synchronized (this.sync) {
/* 168 */         return this.collection.toArray(a);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean addAll(FloatCollection c) {
/* 173 */       synchronized (this.sync) {
/* 174 */         return this.collection.addAll(c);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean containsAll(FloatCollection c) {
/* 179 */       synchronized (this.sync) {
/* 180 */         return this.collection.containsAll(c);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean removeAll(FloatCollection c) {
/* 185 */       synchronized (this.sync) {
/* 186 */         return this.collection.removeAll(c);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean removeIf(DoublePredicate filter) {
/* 191 */       synchronized (this.sync) {
/* 192 */         return this.collection.removeIf(filter);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean retainAll(FloatCollection c) {
/* 197 */       synchronized (this.sync) {
/* 198 */         return this.collection.retainAll(c);
/*     */       } 
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean add(Float k) {
/* 204 */       synchronized (this.sync) {
/* 205 */         return this.collection.add(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean contains(Object k) {
/* 211 */       synchronized (this.sync) {
/* 212 */         return this.collection.contains(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object k) {
/* 218 */       synchronized (this.sync) {
/* 219 */         return this.collection.remove(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public <T> T[] toArray(T[] a) {
/* 224 */       synchronized (this.sync) {
/* 225 */         return (T[])this.collection.toArray((Object[])a);
/*     */       } 
/*     */     }
/*     */     
/*     */     public FloatIterator iterator() {
/* 230 */       return this.collection.iterator();
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends Float> c) {
/* 234 */       synchronized (this.sync) {
/* 235 */         return this.collection.addAll(c);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 240 */       synchronized (this.sync) {
/* 241 */         return this.collection.containsAll(c);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 246 */       synchronized (this.sync) {
/* 247 */         return this.collection.removeAll(c);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 252 */       synchronized (this.sync) {
/* 253 */         return this.collection.retainAll(c);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void clear() {
/* 258 */       synchronized (this.sync) {
/* 259 */         this.collection.clear();
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 264 */       synchronized (this.sync) {
/* 265 */         return this.collection.toString();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 270 */       synchronized (this.sync) {
/* 271 */         return this.collection.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 276 */       if (o == this)
/* 277 */         return true; 
/* 278 */       synchronized (this.sync) {
/* 279 */         return this.collection.equals(o);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 283 */       synchronized (this.sync) {
/* 284 */         s.defaultWriteObject();
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
/*     */   public static FloatCollection synchronize(FloatCollection c) {
/* 297 */     return new SynchronizedCollection(c);
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
/*     */   public static FloatCollection synchronize(FloatCollection c, Object sync) {
/* 311 */     return new SynchronizedCollection(c, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableCollection implements FloatCollection, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final FloatCollection collection;
/*     */     
/*     */     protected UnmodifiableCollection(FloatCollection c) {
/* 318 */       if (c == null)
/* 319 */         throw new NullPointerException(); 
/* 320 */       this.collection = c;
/*     */     }
/*     */     
/*     */     public boolean add(float k) {
/* 324 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean rem(float k) {
/* 328 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int size() {
/* 332 */       return this.collection.size();
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 336 */       return this.collection.isEmpty();
/*     */     }
/*     */     
/*     */     public boolean contains(float o) {
/* 340 */       return this.collection.contains(o);
/*     */     }
/*     */     
/*     */     public FloatIterator iterator() {
/* 344 */       return FloatIterators.unmodifiable(this.collection.iterator());
/*     */     }
/*     */     
/*     */     public void clear() {
/* 348 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public <T> T[] toArray(T[] a) {
/* 352 */       return (T[])this.collection.toArray((Object[])a);
/*     */     }
/*     */     
/*     */     public Object[] toArray() {
/* 356 */       return this.collection.toArray();
/*     */     }
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 360 */       return this.collection.containsAll(c);
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends Float> c) {
/* 364 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 368 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 372 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean add(Float k) {
/* 377 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean contains(Object k) {
/* 382 */       return this.collection.contains(k);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object k) {
/* 387 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public float[] toFloatArray() {
/* 391 */       return this.collection.toFloatArray();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public float[] toFloatArray(float[] a) {
/* 402 */       return toArray(a);
/*     */     }
/*     */     
/*     */     public float[] toArray(float[] a) {
/* 406 */       return this.collection.toArray(a);
/*     */     }
/*     */     
/*     */     public boolean containsAll(FloatCollection c) {
/* 410 */       return this.collection.containsAll(c);
/*     */     }
/*     */     
/*     */     public boolean addAll(FloatCollection c) {
/* 414 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean removeAll(FloatCollection c) {
/* 418 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean retainAll(FloatCollection c) {
/* 422 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 426 */       return this.collection.toString();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 430 */       return this.collection.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 434 */       if (o == this)
/* 435 */         return true; 
/* 436 */       return this.collection.equals(o);
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
/*     */   public static FloatCollection unmodifiable(FloatCollection c) {
/* 448 */     return new UnmodifiableCollection(c);
/*     */   }
/*     */   
/*     */   public static class IterableCollection extends AbstractFloatCollection implements Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final FloatIterable iterable;
/*     */     
/*     */     protected IterableCollection(FloatIterable iterable) {
/* 455 */       if (iterable == null)
/* 456 */         throw new NullPointerException(); 
/* 457 */       this.iterable = iterable;
/*     */     }
/*     */     
/*     */     public int size() {
/* 461 */       int c = 0;
/* 462 */       FloatIterator iterator = iterator();
/* 463 */       while (iterator.hasNext()) {
/* 464 */         iterator.nextFloat();
/* 465 */         c++;
/*     */       } 
/* 467 */       return c;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 471 */       return !this.iterable.iterator().hasNext();
/*     */     }
/*     */     
/*     */     public FloatIterator iterator() {
/* 475 */       return this.iterable.iterator();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FloatCollection asCollection(FloatIterable iterable) {
/* 486 */     if (iterable instanceof FloatCollection)
/* 487 */       return (FloatCollection)iterable; 
/* 488 */     return new IterableCollection(iterable);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatCollections.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */