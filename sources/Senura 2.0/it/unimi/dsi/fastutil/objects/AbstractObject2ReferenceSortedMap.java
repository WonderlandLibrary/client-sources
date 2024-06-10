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
/*     */ public abstract class AbstractObject2ReferenceSortedMap<K, V>
/*     */   extends AbstractObject2ReferenceMap<K, V>
/*     */   implements Object2ReferenceSortedMap<K, V>
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public ObjectSortedSet<K> keySet() {
/*  48 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractObjectSortedSet<K> {
/*     */     public boolean contains(Object k) {
/*  54 */       return AbstractObject2ReferenceSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  58 */       return AbstractObject2ReferenceSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  62 */       AbstractObject2ReferenceSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/*  66 */       return AbstractObject2ReferenceSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public K first() {
/*  70 */       return (K)AbstractObject2ReferenceSortedMap.this.firstKey();
/*     */     }
/*     */     
/*     */     public K last() {
/*  74 */       return (K)AbstractObject2ReferenceSortedMap.this.lastKey();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> headSet(K to) {
/*  78 */       return AbstractObject2ReferenceSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> tailSet(K from) {
/*  82 */       return AbstractObject2ReferenceSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> subSet(K from, K to) {
/*  86 */       return AbstractObject2ReferenceSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator(K from) {
/*  90 */       return new AbstractObject2ReferenceSortedMap.KeySetIterator<>(AbstractObject2ReferenceSortedMap.this.object2ReferenceEntrySet().iterator(new AbstractObject2ReferenceMap.BasicEntry<>(from, null)));
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator() {
/*  94 */       return new AbstractObject2ReferenceSortedMap.KeySetIterator<>(
/*  95 */           Object2ReferenceSortedMaps.fastIterator(AbstractObject2ReferenceSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<K, V>
/*     */     implements ObjectBidirectionalIterator<K>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Object2ReferenceMap.Entry<K, V>> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Object2ReferenceMap.Entry<K, V>> i) {
/* 108 */       this.i = i;
/*     */     }
/*     */     
/*     */     public K next() {
/* 112 */       return (K)((Object2ReferenceMap.Entry)this.i.next()).getKey();
/*     */     }
/*     */     
/*     */     public K previous() {
/* 116 */       return (K)((Object2ReferenceMap.Entry)this.i.previous()).getKey();
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
/*     */   public ReferenceCollection<V> values() {
/* 142 */     return new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractReferenceCollection<V> {
/*     */     public ObjectIterator<V> iterator() {
/* 148 */       return new AbstractObject2ReferenceSortedMap.ValuesIterator<>(
/* 149 */           Object2ReferenceSortedMaps.fastIterator(AbstractObject2ReferenceSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(Object k) {
/* 153 */       return AbstractObject2ReferenceSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 157 */       return AbstractObject2ReferenceSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 161 */       AbstractObject2ReferenceSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<K, V>
/*     */     implements ObjectIterator<V>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Object2ReferenceMap.Entry<K, V>> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Object2ReferenceMap.Entry<K, V>> i) {
/* 174 */       this.i = i;
/*     */     }
/*     */     
/*     */     public V next() {
/* 178 */       return (V)((Object2ReferenceMap.Entry)this.i.next()).getValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 182 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractObject2ReferenceSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */