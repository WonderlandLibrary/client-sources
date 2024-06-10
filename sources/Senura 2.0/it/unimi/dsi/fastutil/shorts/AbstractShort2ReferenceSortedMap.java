/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public abstract class AbstractShort2ReferenceSortedMap<V>
/*     */   extends AbstractShort2ReferenceMap<V>
/*     */   implements Short2ReferenceSortedMap<V>
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public ShortSortedSet keySet() {
/*  47 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractShortSortedSet {
/*     */     public boolean contains(short k) {
/*  53 */       return AbstractShort2ReferenceSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  57 */       return AbstractShort2ReferenceSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  61 */       AbstractShort2ReferenceSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public ShortComparator comparator() {
/*  65 */       return AbstractShort2ReferenceSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public short firstShort() {
/*  69 */       return AbstractShort2ReferenceSortedMap.this.firstShortKey();
/*     */     }
/*     */     
/*     */     public short lastShort() {
/*  73 */       return AbstractShort2ReferenceSortedMap.this.lastShortKey();
/*     */     }
/*     */     
/*     */     public ShortSortedSet headSet(short to) {
/*  77 */       return AbstractShort2ReferenceSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public ShortSortedSet tailSet(short from) {
/*  81 */       return AbstractShort2ReferenceSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public ShortSortedSet subSet(short from, short to) {
/*  85 */       return AbstractShort2ReferenceSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public ShortBidirectionalIterator iterator(short from) {
/*  89 */       return new AbstractShort2ReferenceSortedMap.KeySetIterator(AbstractShort2ReferenceSortedMap.this.short2ReferenceEntrySet().iterator(new AbstractShort2ReferenceMap.BasicEntry(from, null)));
/*     */     }
/*     */     
/*     */     public ShortBidirectionalIterator iterator() {
/*  93 */       return new AbstractShort2ReferenceSortedMap.KeySetIterator(Short2ReferenceSortedMaps.fastIterator(AbstractShort2ReferenceSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<V>
/*     */     implements ShortBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Short2ReferenceMap.Entry<V>> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Short2ReferenceMap.Entry<V>> i) {
/* 106 */       this.i = i;
/*     */     }
/*     */     
/*     */     public short nextShort() {
/* 110 */       return ((Short2ReferenceMap.Entry)this.i.next()).getShortKey();
/*     */     }
/*     */     
/*     */     public short previousShort() {
/* 114 */       return ((Short2ReferenceMap.Entry)this.i.previous()).getShortKey();
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
/* 146 */       return new AbstractShort2ReferenceSortedMap.ValuesIterator<>(Short2ReferenceSortedMaps.fastIterator(AbstractShort2ReferenceSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(Object k) {
/* 150 */       return AbstractShort2ReferenceSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 154 */       return AbstractShort2ReferenceSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 158 */       AbstractShort2ReferenceSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<V>
/*     */     implements ObjectIterator<V>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Short2ReferenceMap.Entry<V>> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Short2ReferenceMap.Entry<V>> i) {
/* 171 */       this.i = i;
/*     */     }
/*     */     
/*     */     public V next() {
/* 175 */       return ((Short2ReferenceMap.Entry<V>)this.i.next()).getValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 179 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\AbstractShort2ReferenceSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */