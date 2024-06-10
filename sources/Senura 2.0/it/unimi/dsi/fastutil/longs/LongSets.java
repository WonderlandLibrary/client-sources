/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
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
/*     */ public final class LongSets
/*     */ {
/*     */   public static class EmptySet
/*     */     extends LongCollections.EmptyCollection
/*     */     implements LongSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public boolean remove(long ok) {
/*  46 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public Object clone() {
/*  50 */       return LongSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  55 */       return (o instanceof Set && ((Set)o).isEmpty());
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(long k) {
/*  60 */       return super.rem(k);
/*     */     }
/*     */     private Object readResolve() {
/*  63 */       return LongSets.EMPTY_SET;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   public static final EmptySet EMPTY_SET = new EmptySet();
/*     */ 
/*     */   
/*     */   public static class Singleton
/*     */     extends AbstractLongSet
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final long element;
/*     */     
/*     */     protected Singleton(long element) {
/*  82 */       this.element = element;
/*     */     }
/*     */     
/*     */     public boolean contains(long k) {
/*  86 */       return (k == this.element);
/*     */     }
/*     */     
/*     */     public boolean remove(long k) {
/*  90 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public LongListIterator iterator() {
/*  94 */       return LongIterators.singleton(this.element);
/*     */     }
/*     */     
/*     */     public int size() {
/*  98 */       return 1;
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends Long> c) {
/* 102 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 106 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 110 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean addAll(LongCollection c) {
/* 114 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean removeAll(LongCollection c) {
/* 118 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean retainAll(LongCollection c) {
/* 122 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 126 */       return this;
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
/*     */   public static LongSet singleton(long element) {
/* 138 */     return new Singleton(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LongSet singleton(Long element) {
/* 149 */     return new Singleton(element.longValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedSet
/*     */     extends LongCollections.SynchronizedCollection
/*     */     implements LongSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected SynchronizedSet(LongSet s, Object sync) {
/* 158 */       super(s, sync);
/*     */     }
/*     */     protected SynchronizedSet(LongSet s) {
/* 161 */       super(s);
/*     */     }
/*     */     
/*     */     public boolean remove(long k) {
/* 165 */       synchronized (this.sync) {
/* 166 */         return this.collection.rem(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(long k) {
/* 172 */       return super.rem(k);
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
/*     */   
/*     */   public static LongSet synchronize(LongSet s) {
/* 185 */     return new SynchronizedSet(s);
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
/*     */   public static LongSet synchronize(LongSet s, Object sync) {
/* 199 */     return new SynchronizedSet(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSet
/*     */     extends LongCollections.UnmodifiableCollection
/*     */     implements LongSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected UnmodifiableSet(LongSet s) {
/* 208 */       super(s);
/*     */     }
/*     */     
/*     */     public boolean remove(long k) {
/* 212 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 216 */       if (o == this)
/* 217 */         return true; 
/* 218 */       return this.collection.equals(o);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 222 */       return this.collection.hashCode();
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(long k) {
/* 227 */       return super.rem(k);
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
/*     */   
/*     */   public static LongSet unmodifiable(LongSet s) {
/* 240 */     return new UnmodifiableSet(s);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\LongSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */