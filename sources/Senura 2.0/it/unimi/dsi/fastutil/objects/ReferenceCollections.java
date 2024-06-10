/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
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
/*     */ 
/*     */ public final class ReferenceCollections
/*     */ {
/*     */   public static abstract class EmptyCollection<K>
/*     */     extends AbstractReferenceCollection<K>
/*     */   {
/*     */     public boolean contains(Object k) {
/*  40 */       return false;
/*     */     }
/*     */     
/*     */     public Object[] toArray() {
/*  44 */       return ObjectArrays.EMPTY_ARRAY;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator() {
/*  49 */       return ObjectIterators.EMPTY_ITERATOR;
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
/*     */     public boolean addAll(Collection<? extends K> c) {
/*  72 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/*  76 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/*  80 */       throw new UnsupportedOperationException();
/*     */     } }
/*     */   
/*     */   public static class SynchronizedCollection<K> implements ReferenceCollection<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ReferenceCollection<K> collection;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedCollection(ReferenceCollection<K> c, Object sync) {
/*  89 */       if (c == null)
/*  90 */         throw new NullPointerException(); 
/*  91 */       this.collection = c;
/*  92 */       this.sync = sync;
/*     */     }
/*     */     protected SynchronizedCollection(ReferenceCollection<K> c) {
/*  95 */       if (c == null)
/*  96 */         throw new NullPointerException(); 
/*  97 */       this.collection = c;
/*  98 */       this.sync = this;
/*     */     }
/*     */     
/*     */     public boolean add(K k) {
/* 102 */       synchronized (this.sync) {
/* 103 */         return this.collection.add(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean contains(Object k) {
/* 108 */       synchronized (this.sync) {
/* 109 */         return this.collection.contains(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(Object k) {
/* 114 */       synchronized (this.sync) {
/* 115 */         return this.collection.remove(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/* 120 */       synchronized (this.sync) {
/* 121 */         return this.collection.size();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 126 */       synchronized (this.sync) {
/* 127 */         return this.collection.isEmpty();
/*     */       } 
/*     */     }
/*     */     
/*     */     public Object[] toArray() {
/* 132 */       synchronized (this.sync) {
/* 133 */         return this.collection.toArray();
/*     */       } 
/*     */     }
/*     */     
/*     */     public <T> T[] toArray(T[] a) {
/* 138 */       synchronized (this.sync) {
/* 139 */         return (T[])this.collection.toArray((Object[])a);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectIterator<K> iterator() {
/* 144 */       return this.collection.iterator();
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends K> c) {
/* 148 */       synchronized (this.sync) {
/* 149 */         return this.collection.addAll(c);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 154 */       synchronized (this.sync) {
/* 155 */         return this.collection.containsAll(c);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 160 */       synchronized (this.sync) {
/* 161 */         return this.collection.removeAll(c);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 166 */       synchronized (this.sync) {
/* 167 */         return this.collection.retainAll(c);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void clear() {
/* 172 */       synchronized (this.sync) {
/* 173 */         this.collection.clear();
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 178 */       synchronized (this.sync) {
/* 179 */         return this.collection.toString();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 184 */       synchronized (this.sync) {
/* 185 */         return this.collection.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 190 */       if (o == this)
/* 191 */         return true; 
/* 192 */       synchronized (this.sync) {
/* 193 */         return this.collection.equals(o);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 197 */       synchronized (this.sync) {
/* 198 */         s.defaultWriteObject();
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
/*     */   public static <K> ReferenceCollection<K> synchronize(ReferenceCollection<K> c) {
/* 211 */     return new SynchronizedCollection<>(c);
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
/*     */   public static <K> ReferenceCollection<K> synchronize(ReferenceCollection<K> c, Object sync) {
/* 225 */     return new SynchronizedCollection<>(c, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableCollection<K> implements ReferenceCollection<K>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ReferenceCollection<K> collection;
/*     */     
/*     */     protected UnmodifiableCollection(ReferenceCollection<K> c) {
/* 232 */       if (c == null)
/* 233 */         throw new NullPointerException(); 
/* 234 */       this.collection = c;
/*     */     }
/*     */     
/*     */     public boolean add(K k) {
/* 238 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(Object k) {
/* 242 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int size() {
/* 246 */       return this.collection.size();
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 250 */       return this.collection.isEmpty();
/*     */     }
/*     */     
/*     */     public boolean contains(Object o) {
/* 254 */       return this.collection.contains(o);
/*     */     }
/*     */     
/*     */     public ObjectIterator<K> iterator() {
/* 258 */       return ObjectIterators.unmodifiable(this.collection.iterator());
/*     */     }
/*     */     
/*     */     public void clear() {
/* 262 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public <T> T[] toArray(T[] a) {
/* 266 */       return (T[])this.collection.toArray((Object[])a);
/*     */     }
/*     */     
/*     */     public Object[] toArray() {
/* 270 */       return this.collection.toArray();
/*     */     }
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 274 */       return this.collection.containsAll(c);
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends K> c) {
/* 278 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 282 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 286 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 290 */       return this.collection.toString();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 294 */       return this.collection.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 298 */       if (o == this)
/* 299 */         return true; 
/* 300 */       return this.collection.equals(o);
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
/*     */   public static <K> ReferenceCollection<K> unmodifiable(ReferenceCollection<K> c) {
/* 312 */     return new UnmodifiableCollection<>(c);
/*     */   }
/*     */   
/*     */   public static class IterableCollection<K> extends AbstractReferenceCollection<K> implements Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ObjectIterable<K> iterable;
/*     */     
/*     */     protected IterableCollection(ObjectIterable<K> iterable) {
/* 319 */       if (iterable == null)
/* 320 */         throw new NullPointerException(); 
/* 321 */       this.iterable = iterable;
/*     */     }
/*     */     
/*     */     public int size() {
/* 325 */       int c = 0;
/* 326 */       ObjectIterator<K> iterator = iterator();
/* 327 */       while (iterator.hasNext()) {
/* 328 */         iterator.next();
/* 329 */         c++;
/*     */       } 
/* 331 */       return c;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 335 */       return !this.iterable.iterator().hasNext();
/*     */     }
/*     */     
/*     */     public ObjectIterator<K> iterator() {
/* 339 */       return this.iterable.iterator();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ReferenceCollection<K> asCollection(ObjectIterable<K> iterable) {
/* 350 */     if (iterable instanceof ReferenceCollection)
/* 351 */       return (ReferenceCollection<K>)iterable; 
/* 352 */     return new IterableCollection<>(iterable);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ReferenceCollections.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */