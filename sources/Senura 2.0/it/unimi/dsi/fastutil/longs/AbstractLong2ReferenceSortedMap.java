/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
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
/*     */ public abstract class AbstractLong2ReferenceSortedMap<V>
/*     */   extends AbstractLong2ReferenceMap<V>
/*     */   implements Long2ReferenceSortedMap<V>
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
/*  53 */       return AbstractLong2ReferenceSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  57 */       return AbstractLong2ReferenceSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  61 */       AbstractLong2ReferenceSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public LongComparator comparator() {
/*  65 */       return AbstractLong2ReferenceSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public long firstLong() {
/*  69 */       return AbstractLong2ReferenceSortedMap.this.firstLongKey();
/*     */     }
/*     */     
/*     */     public long lastLong() {
/*  73 */       return AbstractLong2ReferenceSortedMap.this.lastLongKey();
/*     */     }
/*     */     
/*     */     public LongSortedSet headSet(long to) {
/*  77 */       return AbstractLong2ReferenceSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public LongSortedSet tailSet(long from) {
/*  81 */       return AbstractLong2ReferenceSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public LongSortedSet subSet(long from, long to) {
/*  85 */       return AbstractLong2ReferenceSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public LongBidirectionalIterator iterator(long from) {
/*  89 */       return new AbstractLong2ReferenceSortedMap.KeySetIterator(AbstractLong2ReferenceSortedMap.this.long2ReferenceEntrySet().iterator(new AbstractLong2ReferenceMap.BasicEntry(from, null)));
/*     */     }
/*     */     
/*     */     public LongBidirectionalIterator iterator() {
/*  93 */       return new AbstractLong2ReferenceSortedMap.KeySetIterator(Long2ReferenceSortedMaps.fastIterator(AbstractLong2ReferenceSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<V>
/*     */     implements LongBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Long2ReferenceMap.Entry<V>> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Long2ReferenceMap.Entry<V>> i) {
/* 106 */       this.i = i;
/*     */     }
/*     */     
/*     */     public long nextLong() {
/* 110 */       return ((Long2ReferenceMap.Entry)this.i.next()).getLongKey();
/*     */     }
/*     */     
/*     */     public long previousLong() {
/* 114 */       return ((Long2ReferenceMap.Entry)this.i.previous()).getLongKey();
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
/*     */   public ReferenceCollection<V> values() {
/* 140 */     return (ReferenceCollection<V>)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractReferenceCollection<V> {
/*     */     public ObjectIterator<V> iterator() {
/* 146 */       return new AbstractLong2ReferenceSortedMap.ValuesIterator<>(Long2ReferenceSortedMaps.fastIterator(AbstractLong2ReferenceSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(Object k) {
/* 150 */       return AbstractLong2ReferenceSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 154 */       return AbstractLong2ReferenceSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 158 */       AbstractLong2ReferenceSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<V>
/*     */     implements ObjectIterator<V>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Long2ReferenceMap.Entry<V>> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Long2ReferenceMap.Entry<V>> i) {
/* 171 */       this.i = i;
/*     */     }
/*     */     
/*     */     public V next() {
/* 175 */       return ((Long2ReferenceMap.Entry<V>)this.i.next()).getValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 179 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\AbstractLong2ReferenceSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */