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
/*     */ public abstract class AbstractObject2ObjectSortedMap<K, V>
/*     */   extends AbstractObject2ObjectMap<K, V>
/*     */   implements Object2ObjectSortedMap<K, V>
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
/*  54 */       return AbstractObject2ObjectSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  58 */       return AbstractObject2ObjectSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  62 */       AbstractObject2ObjectSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/*  66 */       return AbstractObject2ObjectSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public K first() {
/*  70 */       return (K)AbstractObject2ObjectSortedMap.this.firstKey();
/*     */     }
/*     */     
/*     */     public K last() {
/*  74 */       return (K)AbstractObject2ObjectSortedMap.this.lastKey();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> headSet(K to) {
/*  78 */       return AbstractObject2ObjectSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> tailSet(K from) {
/*  82 */       return AbstractObject2ObjectSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> subSet(K from, K to) {
/*  86 */       return AbstractObject2ObjectSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator(K from) {
/*  90 */       return new AbstractObject2ObjectSortedMap.KeySetIterator<>(AbstractObject2ObjectSortedMap.this.object2ObjectEntrySet().iterator(new AbstractObject2ObjectMap.BasicEntry<>(from, null)));
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator() {
/*  94 */       return new AbstractObject2ObjectSortedMap.KeySetIterator<>(Object2ObjectSortedMaps.fastIterator(AbstractObject2ObjectSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<K, V>
/*     */     implements ObjectBidirectionalIterator<K>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> i) {
/* 107 */       this.i = i;
/*     */     }
/*     */     
/*     */     public K next() {
/* 111 */       return (K)((Object2ObjectMap.Entry)this.i.next()).getKey();
/*     */     }
/*     */     
/*     */     public K previous() {
/* 115 */       return (K)((Object2ObjectMap.Entry)this.i.previous()).getKey();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 119 */       return this.i.hasNext();
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 123 */       return this.i.hasPrevious();
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
/* 141 */     return new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractObjectCollection<V> {
/*     */     public ObjectIterator<V> iterator() {
/* 147 */       return new AbstractObject2ObjectSortedMap.ValuesIterator<>(Object2ObjectSortedMaps.fastIterator(AbstractObject2ObjectSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(Object k) {
/* 151 */       return AbstractObject2ObjectSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 155 */       return AbstractObject2ObjectSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 159 */       AbstractObject2ObjectSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<K, V>
/*     */     implements ObjectIterator<V>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> i) {
/* 172 */       this.i = i;
/*     */     }
/*     */     
/*     */     public V next() {
/* 176 */       return (V)((Object2ObjectMap.Entry)this.i.next()).getValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 180 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractObject2ObjectSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */