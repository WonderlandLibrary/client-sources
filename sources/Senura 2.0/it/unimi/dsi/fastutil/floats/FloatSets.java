/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public final class FloatSets
/*     */ {
/*     */   public static class EmptySet
/*     */     extends FloatCollections.EmptyCollection
/*     */     implements FloatSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public boolean remove(float ok) {
/*  46 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public Object clone() {
/*  50 */       return FloatSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  55 */       return (o instanceof Set && ((Set)o).isEmpty());
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(float k) {
/*  60 */       return super.rem(k);
/*     */     }
/*     */     private Object readResolve() {
/*  63 */       return FloatSets.EMPTY_SET;
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
/*     */     extends AbstractFloatSet
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final float element;
/*     */     
/*     */     protected Singleton(float element) {
/*  82 */       this.element = element;
/*     */     }
/*     */     
/*     */     public boolean contains(float k) {
/*  86 */       return (Float.floatToIntBits(k) == Float.floatToIntBits(this.element));
/*     */     }
/*     */     
/*     */     public boolean remove(float k) {
/*  90 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public FloatListIterator iterator() {
/*  94 */       return FloatIterators.singleton(this.element);
/*     */     }
/*     */     
/*     */     public int size() {
/*  98 */       return 1;
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends Float> c) {
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
/*     */     public boolean addAll(FloatCollection c) {
/* 114 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean removeAll(FloatCollection c) {
/* 118 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean retainAll(FloatCollection c) {
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
/*     */   public static FloatSet singleton(float element) {
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
/*     */   public static FloatSet singleton(Float element) {
/* 149 */     return new Singleton(element.floatValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedSet
/*     */     extends FloatCollections.SynchronizedCollection
/*     */     implements FloatSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected SynchronizedSet(FloatSet s, Object sync) {
/* 158 */       super(s, sync);
/*     */     }
/*     */     protected SynchronizedSet(FloatSet s) {
/* 161 */       super(s);
/*     */     }
/*     */     
/*     */     public boolean remove(float k) {
/* 165 */       synchronized (this.sync) {
/* 166 */         return this.collection.rem(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(float k) {
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
/*     */   public static FloatSet synchronize(FloatSet s) {
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
/*     */   public static FloatSet synchronize(FloatSet s, Object sync) {
/* 199 */     return new SynchronizedSet(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSet
/*     */     extends FloatCollections.UnmodifiableCollection
/*     */     implements FloatSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected UnmodifiableSet(FloatSet s) {
/* 208 */       super(s);
/*     */     }
/*     */     
/*     */     public boolean remove(float k) {
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
/*     */     public boolean rem(float k) {
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
/*     */   public static FloatSet unmodifiable(FloatSet s) {
/* 240 */     return new UnmodifiableSet(s);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */