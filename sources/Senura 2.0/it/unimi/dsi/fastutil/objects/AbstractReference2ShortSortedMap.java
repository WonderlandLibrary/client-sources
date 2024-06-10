/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortCollection;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
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
/*     */ public abstract class AbstractReference2ShortSortedMap<K>
/*     */   extends AbstractReference2ShortMap<K>
/*     */   implements Reference2ShortSortedMap<K>
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
/*  54 */       return AbstractReference2ShortSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  58 */       return AbstractReference2ShortSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  62 */       AbstractReference2ShortSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/*  66 */       return AbstractReference2ShortSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public K first() {
/*  70 */       return AbstractReference2ShortSortedMap.this.firstKey();
/*     */     }
/*     */     
/*     */     public K last() {
/*  74 */       return AbstractReference2ShortSortedMap.this.lastKey();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> headSet(K to) {
/*  78 */       return AbstractReference2ShortSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> tailSet(K from) {
/*  82 */       return AbstractReference2ShortSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> subSet(K from, K to) {
/*  86 */       return AbstractReference2ShortSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator(K from) {
/*  90 */       return new AbstractReference2ShortSortedMap.KeySetIterator<>(AbstractReference2ShortSortedMap.this.reference2ShortEntrySet().iterator(new AbstractReference2ShortMap.BasicEntry<>(from, (short)0)));
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator() {
/*  94 */       return new AbstractReference2ShortSortedMap.KeySetIterator<>(Reference2ShortSortedMaps.fastIterator(AbstractReference2ShortSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<K>
/*     */     implements ObjectBidirectionalIterator<K>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Reference2ShortMap.Entry<K>> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Reference2ShortMap.Entry<K>> i) {
/* 107 */       this.i = i;
/*     */     }
/*     */     
/*     */     public K next() {
/* 111 */       return ((Reference2ShortMap.Entry<K>)this.i.next()).getKey();
/*     */     }
/*     */     
/*     */     public K previous() {
/* 115 */       return ((Reference2ShortMap.Entry<K>)this.i.previous()).getKey();
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
/*     */   public ShortCollection values() {
/* 141 */     return (ShortCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractShortCollection {
/*     */     public ShortIterator iterator() {
/* 147 */       return new AbstractReference2ShortSortedMap.ValuesIterator(Reference2ShortSortedMaps.fastIterator(AbstractReference2ShortSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(short k) {
/* 151 */       return AbstractReference2ShortSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 155 */       return AbstractReference2ShortSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 159 */       AbstractReference2ShortSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<K>
/*     */     implements ShortIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Reference2ShortMap.Entry<K>> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Reference2ShortMap.Entry<K>> i) {
/* 172 */       this.i = i;
/*     */     }
/*     */     
/*     */     public short nextShort() {
/* 176 */       return ((Reference2ShortMap.Entry)this.i.next()).getShortValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 180 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractReference2ShortSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */