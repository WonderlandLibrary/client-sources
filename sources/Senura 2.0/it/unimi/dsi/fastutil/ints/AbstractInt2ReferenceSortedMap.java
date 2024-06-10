/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ public abstract class AbstractInt2ReferenceSortedMap<V>
/*     */   extends AbstractInt2ReferenceMap<V>
/*     */   implements Int2ReferenceSortedMap<V>
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public IntSortedSet keySet() {
/*  47 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractIntSortedSet {
/*     */     public boolean contains(int k) {
/*  53 */       return AbstractInt2ReferenceSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  57 */       return AbstractInt2ReferenceSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  61 */       AbstractInt2ReferenceSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public IntComparator comparator() {
/*  65 */       return AbstractInt2ReferenceSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public int firstInt() {
/*  69 */       return AbstractInt2ReferenceSortedMap.this.firstIntKey();
/*     */     }
/*     */     
/*     */     public int lastInt() {
/*  73 */       return AbstractInt2ReferenceSortedMap.this.lastIntKey();
/*     */     }
/*     */     
/*     */     public IntSortedSet headSet(int to) {
/*  77 */       return AbstractInt2ReferenceSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public IntSortedSet tailSet(int from) {
/*  81 */       return AbstractInt2ReferenceSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public IntSortedSet subSet(int from, int to) {
/*  85 */       return AbstractInt2ReferenceSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public IntBidirectionalIterator iterator(int from) {
/*  89 */       return new AbstractInt2ReferenceSortedMap.KeySetIterator(AbstractInt2ReferenceSortedMap.this.int2ReferenceEntrySet().iterator(new AbstractInt2ReferenceMap.BasicEntry(from, null)));
/*     */     }
/*     */     
/*     */     public IntBidirectionalIterator iterator() {
/*  93 */       return new AbstractInt2ReferenceSortedMap.KeySetIterator(Int2ReferenceSortedMaps.fastIterator(AbstractInt2ReferenceSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<V>
/*     */     implements IntBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Int2ReferenceMap.Entry<V>> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Int2ReferenceMap.Entry<V>> i) {
/* 106 */       this.i = i;
/*     */     }
/*     */     
/*     */     public int nextInt() {
/* 110 */       return ((Int2ReferenceMap.Entry)this.i.next()).getIntKey();
/*     */     }
/*     */     
/*     */     public int previousInt() {
/* 114 */       return ((Int2ReferenceMap.Entry)this.i.previous()).getIntKey();
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
/* 146 */       return new AbstractInt2ReferenceSortedMap.ValuesIterator<>(Int2ReferenceSortedMaps.fastIterator(AbstractInt2ReferenceSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(Object k) {
/* 150 */       return AbstractInt2ReferenceSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 154 */       return AbstractInt2ReferenceSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 158 */       AbstractInt2ReferenceSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<V>
/*     */     implements ObjectIterator<V>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Int2ReferenceMap.Entry<V>> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Int2ReferenceMap.Entry<V>> i) {
/* 171 */       this.i = i;
/*     */     }
/*     */     
/*     */     public V next() {
/* 175 */       return ((Int2ReferenceMap.Entry<V>)this.i.next()).getValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 179 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\AbstractInt2ReferenceSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */