/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
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
/*     */ public abstract class AbstractLong2IntSortedMap
/*     */   extends AbstractLong2IntMap
/*     */   implements Long2IntSortedMap
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public LongSortedSet keySet() {
/*  45 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractLongSortedSet {
/*     */     public boolean contains(long k) {
/*  51 */       return AbstractLong2IntSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractLong2IntSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractLong2IntSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public LongComparator comparator() {
/*  63 */       return AbstractLong2IntSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public long firstLong() {
/*  67 */       return AbstractLong2IntSortedMap.this.firstLongKey();
/*     */     }
/*     */     
/*     */     public long lastLong() {
/*  71 */       return AbstractLong2IntSortedMap.this.lastLongKey();
/*     */     }
/*     */     
/*     */     public LongSortedSet headSet(long to) {
/*  75 */       return AbstractLong2IntSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public LongSortedSet tailSet(long from) {
/*  79 */       return AbstractLong2IntSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public LongSortedSet subSet(long from, long to) {
/*  83 */       return AbstractLong2IntSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public LongBidirectionalIterator iterator(long from) {
/*  87 */       return new AbstractLong2IntSortedMap.KeySetIterator(AbstractLong2IntSortedMap.this.long2IntEntrySet().iterator(new AbstractLong2IntMap.BasicEntry(from, 0)));
/*     */     }
/*     */     
/*     */     public LongBidirectionalIterator iterator() {
/*  91 */       return new AbstractLong2IntSortedMap.KeySetIterator(Long2IntSortedMaps.fastIterator(AbstractLong2IntSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements LongBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Long2IntMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Long2IntMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public long nextLong() {
/* 108 */       return ((Long2IntMap.Entry)this.i.next()).getLongKey();
/*     */     }
/*     */     
/*     */     public long previousLong() {
/* 112 */       return ((Long2IntMap.Entry)this.i.previous()).getLongKey();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 116 */       return this.i.hasNext();
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 120 */       return this.i.hasPrevious();
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
/* 138 */     return (IntCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractIntCollection {
/*     */     public IntIterator iterator() {
/* 144 */       return new AbstractLong2IntSortedMap.ValuesIterator(Long2IntSortedMaps.fastIterator(AbstractLong2IntSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(int k) {
/* 148 */       return AbstractLong2IntSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractLong2IntSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractLong2IntSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements IntIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Long2IntMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Long2IntMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public int nextInt() {
/* 173 */       return ((Long2IntMap.Entry)this.i.next()).getIntValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\AbstractLong2IntSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */