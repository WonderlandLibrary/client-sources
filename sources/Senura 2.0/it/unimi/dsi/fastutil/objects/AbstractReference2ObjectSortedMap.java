/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
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
/*     */ public abstract class AbstractReference2ObjectSortedMap<K, V>
/*     */   extends AbstractReference2ObjectMap<K, V>
/*     */   implements Reference2ObjectSortedMap<K, V>
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public ReferenceSortedSet<K> keySet() {
/*  48 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractReferenceSortedSet<K> {
/*     */     public boolean contains(Object k) {
/*  54 */       return AbstractReference2ObjectSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  58 */       return AbstractReference2ObjectSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  62 */       AbstractReference2ObjectSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/*  66 */       return AbstractReference2ObjectSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public K first() {
/*  70 */       return (K)AbstractReference2ObjectSortedMap.this.firstKey();
/*     */     }
/*     */     
/*     */     public K last() {
/*  74 */       return (K)AbstractReference2ObjectSortedMap.this.lastKey();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> headSet(K to) {
/*  78 */       return AbstractReference2ObjectSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> tailSet(K from) {
/*  82 */       return AbstractReference2ObjectSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> subSet(K from, K to) {
/*  86 */       return AbstractReference2ObjectSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator(K from) {
/*  90 */       return new AbstractReference2ObjectSortedMap.KeySetIterator<>(AbstractReference2ObjectSortedMap.this.reference2ObjectEntrySet().iterator(new AbstractReference2ObjectMap.BasicEntry<>(from, null)));
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator() {
/*  94 */       return new AbstractReference2ObjectSortedMap.KeySetIterator<>(
/*  95 */           Reference2ObjectSortedMaps.fastIterator(AbstractReference2ObjectSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<K, V>
/*     */     implements ObjectBidirectionalIterator<K>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Reference2ObjectMap.Entry<K, V>> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Reference2ObjectMap.Entry<K, V>> i) {
/* 108 */       this.i = i;
/*     */     }
/*     */     
/*     */     public K next() {
/* 112 */       return (K)((Reference2ObjectMap.Entry)this.i.next()).getKey();
/*     */     }
/*     */     
/*     */     public K previous() {
/* 116 */       return (K)((Reference2ObjectMap.Entry)this.i.previous()).getKey();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 120 */       return this.i.hasNext();
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 124 */       return this.i.hasPrevious();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectCollection<V> values() {
/* 142 */     return new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractObjectCollection<V> {
/*     */     public ObjectIterator<V> iterator() {
/* 148 */       return new AbstractReference2ObjectSortedMap.ValuesIterator<>(
/* 149 */           Reference2ObjectSortedMaps.fastIterator(AbstractReference2ObjectSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(Object k) {
/* 153 */       return AbstractReference2ObjectSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 157 */       return AbstractReference2ObjectSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 161 */       AbstractReference2ObjectSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<K, V>
/*     */     implements ObjectIterator<V>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Reference2ObjectMap.Entry<K, V>> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Reference2ObjectMap.Entry<K, V>> i) {
/* 174 */       this.i = i;
/*     */     }
/*     */     
/*     */     public V next() {
/* 178 */       return (V)((Reference2ObjectMap.Entry)this.i.next()).getValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 182 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractReference2ObjectSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */