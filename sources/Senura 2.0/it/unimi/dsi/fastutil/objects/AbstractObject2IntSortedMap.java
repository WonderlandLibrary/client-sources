/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
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
/*     */ public abstract class AbstractObject2IntSortedMap<K>
/*     */   extends AbstractObject2IntMap<K>
/*     */   implements Object2IntSortedMap<K>
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
/*  54 */       return AbstractObject2IntSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  58 */       return AbstractObject2IntSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  62 */       AbstractObject2IntSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/*  66 */       return AbstractObject2IntSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public K first() {
/*  70 */       return AbstractObject2IntSortedMap.this.firstKey();
/*     */     }
/*     */     
/*     */     public K last() {
/*  74 */       return AbstractObject2IntSortedMap.this.lastKey();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> headSet(K to) {
/*  78 */       return AbstractObject2IntSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> tailSet(K from) {
/*  82 */       return AbstractObject2IntSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> subSet(K from, K to) {
/*  86 */       return AbstractObject2IntSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator(K from) {
/*  90 */       return new AbstractObject2IntSortedMap.KeySetIterator<>(AbstractObject2IntSortedMap.this.object2IntEntrySet().iterator(new AbstractObject2IntMap.BasicEntry<>(from, 0)));
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator() {
/*  94 */       return new AbstractObject2IntSortedMap.KeySetIterator<>(Object2IntSortedMaps.fastIterator(AbstractObject2IntSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<K>
/*     */     implements ObjectBidirectionalIterator<K>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Object2IntMap.Entry<K>> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Object2IntMap.Entry<K>> i) {
/* 107 */       this.i = i;
/*     */     }
/*     */     
/*     */     public K next() {
/* 111 */       return ((Object2IntMap.Entry<K>)this.i.next()).getKey();
/*     */     }
/*     */     
/*     */     public K previous() {
/* 115 */       return ((Object2IntMap.Entry<K>)this.i.previous()).getKey();
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
/*     */   public IntCollection values() {
/* 141 */     return (IntCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractIntCollection {
/*     */     public IntIterator iterator() {
/* 147 */       return new AbstractObject2IntSortedMap.ValuesIterator(Object2IntSortedMaps.fastIterator(AbstractObject2IntSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(int k) {
/* 151 */       return AbstractObject2IntSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 155 */       return AbstractObject2IntSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 159 */       AbstractObject2IntSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<K>
/*     */     implements IntIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Object2IntMap.Entry<K>> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Object2IntMap.Entry<K>> i) {
/* 172 */       this.i = i;
/*     */     }
/*     */     
/*     */     public int nextInt() {
/* 176 */       return ((Object2IntMap.Entry)this.i.next()).getIntValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 180 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractObject2IntSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */