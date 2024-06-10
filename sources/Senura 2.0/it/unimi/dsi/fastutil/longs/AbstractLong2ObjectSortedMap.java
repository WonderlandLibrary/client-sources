/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
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
/*     */ public abstract class AbstractLong2ObjectSortedMap<V>
/*     */   extends AbstractLong2ObjectMap<V>
/*     */   implements Long2ObjectSortedMap<V>
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public LongSortedSet keySet() {
/*  47 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractLongSortedSet {
/*     */     public boolean contains(long k) {
/*  53 */       return AbstractLong2ObjectSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  57 */       return AbstractLong2ObjectSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  61 */       AbstractLong2ObjectSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public LongComparator comparator() {
/*  65 */       return AbstractLong2ObjectSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public long firstLong() {
/*  69 */       return AbstractLong2ObjectSortedMap.this.firstLongKey();
/*     */     }
/*     */     
/*     */     public long lastLong() {
/*  73 */       return AbstractLong2ObjectSortedMap.this.lastLongKey();
/*     */     }
/*     */     
/*     */     public LongSortedSet headSet(long to) {
/*  77 */       return AbstractLong2ObjectSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public LongSortedSet tailSet(long from) {
/*  81 */       return AbstractLong2ObjectSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public LongSortedSet subSet(long from, long to) {
/*  85 */       return AbstractLong2ObjectSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public LongBidirectionalIterator iterator(long from) {
/*  89 */       return new AbstractLong2ObjectSortedMap.KeySetIterator(AbstractLong2ObjectSortedMap.this.long2ObjectEntrySet().iterator(new AbstractLong2ObjectMap.BasicEntry(from, null)));
/*     */     }
/*     */     
/*     */     public LongBidirectionalIterator iterator() {
/*  93 */       return new AbstractLong2ObjectSortedMap.KeySetIterator(Long2ObjectSortedMaps.fastIterator(AbstractLong2ObjectSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<V>
/*     */     implements LongBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Long2ObjectMap.Entry<V>> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Long2ObjectMap.Entry<V>> i) {
/* 106 */       this.i = i;
/*     */     }
/*     */     
/*     */     public long nextLong() {
/* 110 */       return ((Long2ObjectMap.Entry)this.i.next()).getLongKey();
/*     */     }
/*     */     
/*     */     public long previousLong() {
/* 114 */       return ((Long2ObjectMap.Entry)this.i.previous()).getLongKey();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 118 */       return this.i.hasNext();
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 122 */       return this.i.hasPrevious();
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
/* 140 */     return (ObjectCollection<V>)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractObjectCollection<V> {
/*     */     public ObjectIterator<V> iterator() {
/* 146 */       return new AbstractLong2ObjectSortedMap.ValuesIterator<>(Long2ObjectSortedMaps.fastIterator(AbstractLong2ObjectSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(Object k) {
/* 150 */       return AbstractLong2ObjectSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 154 */       return AbstractLong2ObjectSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 158 */       AbstractLong2ObjectSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<V>
/*     */     implements ObjectIterator<V>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Long2ObjectMap.Entry<V>> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Long2ObjectMap.Entry<V>> i) {
/* 171 */       this.i = i;
/*     */     }
/*     */     
/*     */     public V next() {
/* 175 */       return ((Long2ObjectMap.Entry<V>)this.i.next()).getValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 179 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\AbstractLong2ObjectSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */