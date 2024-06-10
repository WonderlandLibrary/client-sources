/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
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
/*     */ public abstract class AbstractReference2DoubleSortedMap<K>
/*     */   extends AbstractReference2DoubleMap<K>
/*     */   implements Reference2DoubleSortedMap<K>
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
/*  54 */       return AbstractReference2DoubleSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  58 */       return AbstractReference2DoubleSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  62 */       AbstractReference2DoubleSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/*  66 */       return AbstractReference2DoubleSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public K first() {
/*  70 */       return AbstractReference2DoubleSortedMap.this.firstKey();
/*     */     }
/*     */     
/*     */     public K last() {
/*  74 */       return AbstractReference2DoubleSortedMap.this.lastKey();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> headSet(K to) {
/*  78 */       return AbstractReference2DoubleSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> tailSet(K from) {
/*  82 */       return AbstractReference2DoubleSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> subSet(K from, K to) {
/*  86 */       return AbstractReference2DoubleSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator(K from) {
/*  90 */       return new AbstractReference2DoubleSortedMap.KeySetIterator<>(AbstractReference2DoubleSortedMap.this.reference2DoubleEntrySet().iterator(new AbstractReference2DoubleMap.BasicEntry<>(from, 0.0D)));
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator() {
/*  94 */       return new AbstractReference2DoubleSortedMap.KeySetIterator<>(
/*  95 */           Reference2DoubleSortedMaps.fastIterator(AbstractReference2DoubleSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<K>
/*     */     implements ObjectBidirectionalIterator<K>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Reference2DoubleMap.Entry<K>> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Reference2DoubleMap.Entry<K>> i) {
/* 108 */       this.i = i;
/*     */     }
/*     */     
/*     */     public K next() {
/* 112 */       return ((Reference2DoubleMap.Entry<K>)this.i.next()).getKey();
/*     */     }
/*     */     
/*     */     public K previous() {
/* 116 */       return ((Reference2DoubleMap.Entry<K>)this.i.previous()).getKey();
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
/*     */   public DoubleCollection values() {
/* 142 */     return (DoubleCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractDoubleCollection {
/*     */     public DoubleIterator iterator() {
/* 148 */       return new AbstractReference2DoubleSortedMap.ValuesIterator(
/* 149 */           Reference2DoubleSortedMaps.fastIterator(AbstractReference2DoubleSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(double k) {
/* 153 */       return AbstractReference2DoubleSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 157 */       return AbstractReference2DoubleSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 161 */       AbstractReference2DoubleSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<K>
/*     */     implements DoubleIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Reference2DoubleMap.Entry<K>> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Reference2DoubleMap.Entry<K>> i) {
/* 174 */       this.i = i;
/*     */     }
/*     */     
/*     */     public double nextDouble() {
/* 178 */       return ((Reference2DoubleMap.Entry)this.i.next()).getDoubleValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 182 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractReference2DoubleSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */