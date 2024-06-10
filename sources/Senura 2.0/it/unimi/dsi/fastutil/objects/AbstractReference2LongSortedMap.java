/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongIterator;
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
/*     */ public abstract class AbstractReference2LongSortedMap<K>
/*     */   extends AbstractReference2LongMap<K>
/*     */   implements Reference2LongSortedMap<K>
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
/*  54 */       return AbstractReference2LongSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  58 */       return AbstractReference2LongSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  62 */       AbstractReference2LongSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/*  66 */       return AbstractReference2LongSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public K first() {
/*  70 */       return AbstractReference2LongSortedMap.this.firstKey();
/*     */     }
/*     */     
/*     */     public K last() {
/*  74 */       return AbstractReference2LongSortedMap.this.lastKey();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> headSet(K to) {
/*  78 */       return AbstractReference2LongSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> tailSet(K from) {
/*  82 */       return AbstractReference2LongSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> subSet(K from, K to) {
/*  86 */       return AbstractReference2LongSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator(K from) {
/*  90 */       return new AbstractReference2LongSortedMap.KeySetIterator<>(AbstractReference2LongSortedMap.this.reference2LongEntrySet().iterator(new AbstractReference2LongMap.BasicEntry<>(from, 0L)));
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator() {
/*  94 */       return new AbstractReference2LongSortedMap.KeySetIterator<>(Reference2LongSortedMaps.fastIterator(AbstractReference2LongSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<K>
/*     */     implements ObjectBidirectionalIterator<K>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Reference2LongMap.Entry<K>> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Reference2LongMap.Entry<K>> i) {
/* 107 */       this.i = i;
/*     */     }
/*     */     
/*     */     public K next() {
/* 111 */       return ((Reference2LongMap.Entry<K>)this.i.next()).getKey();
/*     */     }
/*     */     
/*     */     public K previous() {
/* 115 */       return ((Reference2LongMap.Entry<K>)this.i.previous()).getKey();
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
/*     */   public LongCollection values() {
/* 141 */     return (LongCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractLongCollection {
/*     */     public LongIterator iterator() {
/* 147 */       return new AbstractReference2LongSortedMap.ValuesIterator(Reference2LongSortedMaps.fastIterator(AbstractReference2LongSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(long k) {
/* 151 */       return AbstractReference2LongSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 155 */       return AbstractReference2LongSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 159 */       AbstractReference2LongSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<K>
/*     */     implements LongIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Reference2LongMap.Entry<K>> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Reference2LongMap.Entry<K>> i) {
/* 172 */       this.i = i;
/*     */     }
/*     */     
/*     */     public long nextLong() {
/* 176 */       return ((Reference2LongMap.Entry)this.i.next()).getLongValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 180 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractReference2LongSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */