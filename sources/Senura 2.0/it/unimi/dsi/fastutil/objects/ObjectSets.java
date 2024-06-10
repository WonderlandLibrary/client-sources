/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
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
/*     */ public final class ObjectSets
/*     */ {
/*     */   public static class EmptySet<K>
/*     */     extends ObjectCollections.EmptyCollection<K>
/*     */     implements ObjectSet<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public boolean remove(Object ok) {
/*  46 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public Object clone() {
/*  50 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  55 */       return (o instanceof Set && ((Set)o).isEmpty());
/*     */     }
/*     */     private Object readResolve() {
/*  58 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public static final EmptySet EMPTY_SET = new EmptySet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ObjectSet<K> emptySet() {
/*  76 */     return EMPTY_SET;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends AbstractObjectSet<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final K element;
/*     */     
/*     */     protected Singleton(K element) {
/*  89 */       this.element = element;
/*     */     }
/*     */     
/*     */     public boolean contains(Object k) {
/*  93 */       return Objects.equals(k, this.element);
/*     */     }
/*     */     
/*     */     public boolean remove(Object k) {
/*  97 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectListIterator<K> iterator() {
/* 101 */       return ObjectIterators.singleton(this.element);
/*     */     }
/*     */     
/*     */     public int size() {
/* 105 */       return 1;
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends K> c) {
/* 109 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 113 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 117 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 121 */       return this;
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
/*     */   public static <K> ObjectSet<K> singleton(K element) {
/* 133 */     return new Singleton<>(element);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSet<K>
/*     */     extends ObjectCollections.SynchronizedCollection<K>
/*     */     implements ObjectSet<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected SynchronizedSet(ObjectSet<K> s, Object sync) {
/* 142 */       super(s, sync);
/*     */     }
/*     */     protected SynchronizedSet(ObjectSet<K> s) {
/* 145 */       super(s);
/*     */     }
/*     */     
/*     */     public boolean remove(Object k) {
/* 149 */       synchronized (this.sync) {
/* 150 */         return this.collection.remove(k);
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
/*     */   
/*     */   public static <K> ObjectSet<K> synchronize(ObjectSet<K> s) {
/* 164 */     return new SynchronizedSet<>(s);
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
/*     */   public static <K> ObjectSet<K> synchronize(ObjectSet<K> s, Object sync) {
/* 178 */     return new SynchronizedSet<>(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSet<K>
/*     */     extends ObjectCollections.UnmodifiableCollection<K>
/*     */     implements ObjectSet<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected UnmodifiableSet(ObjectSet<K> s) {
/* 187 */       super(s);
/*     */     }
/*     */     
/*     */     public boolean remove(Object k) {
/* 191 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 195 */       if (o == this)
/* 196 */         return true; 
/* 197 */       return this.collection.equals(o);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 201 */       return this.collection.hashCode();
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
/*     */   public static <K> ObjectSet<K> unmodifiable(ObjectSet<K> s) {
/* 214 */     return new UnmodifiableSet<>(s);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */