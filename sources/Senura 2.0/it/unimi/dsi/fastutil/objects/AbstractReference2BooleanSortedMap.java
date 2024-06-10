/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
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
/*     */ public abstract class AbstractReference2BooleanSortedMap<K>
/*     */   extends AbstractReference2BooleanMap<K>
/*     */   implements Reference2BooleanSortedMap<K>
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
/*  54 */       return AbstractReference2BooleanSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  58 */       return AbstractReference2BooleanSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  62 */       AbstractReference2BooleanSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/*  66 */       return AbstractReference2BooleanSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public K first() {
/*  70 */       return AbstractReference2BooleanSortedMap.this.firstKey();
/*     */     }
/*     */     
/*     */     public K last() {
/*  74 */       return AbstractReference2BooleanSortedMap.this.lastKey();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> headSet(K to) {
/*  78 */       return AbstractReference2BooleanSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> tailSet(K from) {
/*  82 */       return AbstractReference2BooleanSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> subSet(K from, K to) {
/*  86 */       return AbstractReference2BooleanSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator(K from) {
/*  90 */       return new AbstractReference2BooleanSortedMap.KeySetIterator<>(AbstractReference2BooleanSortedMap.this.reference2BooleanEntrySet().iterator(new AbstractReference2BooleanMap.BasicEntry<>(from, false)));
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator() {
/*  94 */       return new AbstractReference2BooleanSortedMap.KeySetIterator<>(
/*  95 */           Reference2BooleanSortedMaps.fastIterator(AbstractReference2BooleanSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<K>
/*     */     implements ObjectBidirectionalIterator<K>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Reference2BooleanMap.Entry<K>> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Reference2BooleanMap.Entry<K>> i) {
/* 108 */       this.i = i;
/*     */     }
/*     */     
/*     */     public K next() {
/* 112 */       return ((Reference2BooleanMap.Entry<K>)this.i.next()).getKey();
/*     */     }
/*     */     
/*     */     public K previous() {
/* 116 */       return ((Reference2BooleanMap.Entry<K>)this.i.previous()).getKey();
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
/*     */   public BooleanCollection values() {
/* 142 */     return (BooleanCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractBooleanCollection {
/*     */     public BooleanIterator iterator() {
/* 148 */       return new AbstractReference2BooleanSortedMap.ValuesIterator(
/* 149 */           Reference2BooleanSortedMaps.fastIterator(AbstractReference2BooleanSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(boolean k) {
/* 153 */       return AbstractReference2BooleanSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 157 */       return AbstractReference2BooleanSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 161 */       AbstractReference2BooleanSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<K>
/*     */     implements BooleanIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Reference2BooleanMap.Entry<K>> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Reference2BooleanMap.Entry<K>> i) {
/* 174 */       this.i = i;
/*     */     }
/*     */     
/*     */     public boolean nextBoolean() {
/* 178 */       return ((Reference2BooleanMap.Entry)this.i.next()).getBooleanValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 182 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractReference2BooleanSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */