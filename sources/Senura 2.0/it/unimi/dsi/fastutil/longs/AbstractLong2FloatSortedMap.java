/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatIterator;
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
/*     */ public abstract class AbstractLong2FloatSortedMap
/*     */   extends AbstractLong2FloatMap
/*     */   implements Long2FloatSortedMap
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
/*  51 */       return AbstractLong2FloatSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractLong2FloatSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractLong2FloatSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public LongComparator comparator() {
/*  63 */       return AbstractLong2FloatSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public long firstLong() {
/*  67 */       return AbstractLong2FloatSortedMap.this.firstLongKey();
/*     */     }
/*     */     
/*     */     public long lastLong() {
/*  71 */       return AbstractLong2FloatSortedMap.this.lastLongKey();
/*     */     }
/*     */     
/*     */     public LongSortedSet headSet(long to) {
/*  75 */       return AbstractLong2FloatSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public LongSortedSet tailSet(long from) {
/*  79 */       return AbstractLong2FloatSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public LongSortedSet subSet(long from, long to) {
/*  83 */       return AbstractLong2FloatSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public LongBidirectionalIterator iterator(long from) {
/*  87 */       return new AbstractLong2FloatSortedMap.KeySetIterator(AbstractLong2FloatSortedMap.this.long2FloatEntrySet().iterator(new AbstractLong2FloatMap.BasicEntry(from, 0.0F)));
/*     */     }
/*     */     
/*     */     public LongBidirectionalIterator iterator() {
/*  91 */       return new AbstractLong2FloatSortedMap.KeySetIterator(Long2FloatSortedMaps.fastIterator(AbstractLong2FloatSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements LongBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Long2FloatMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Long2FloatMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public long nextLong() {
/* 108 */       return ((Long2FloatMap.Entry)this.i.next()).getLongKey();
/*     */     }
/*     */     
/*     */     public long previousLong() {
/* 112 */       return ((Long2FloatMap.Entry)this.i.previous()).getLongKey();
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
/*     */   public FloatCollection values() {
/* 138 */     return (FloatCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractFloatCollection {
/*     */     public FloatIterator iterator() {
/* 144 */       return new AbstractLong2FloatSortedMap.ValuesIterator(Long2FloatSortedMaps.fastIterator(AbstractLong2FloatSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(float k) {
/* 148 */       return AbstractLong2FloatSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractLong2FloatSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractLong2FloatSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements FloatIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Long2FloatMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Long2FloatMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public float nextFloat() {
/* 173 */       return ((Long2FloatMap.Entry)this.i.next()).getFloatValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\AbstractLong2FloatSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */