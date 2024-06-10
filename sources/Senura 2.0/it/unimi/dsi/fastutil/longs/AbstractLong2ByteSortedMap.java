/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
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
/*     */ public abstract class AbstractLong2ByteSortedMap
/*     */   extends AbstractLong2ByteMap
/*     */   implements Long2ByteSortedMap
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
/*  51 */       return AbstractLong2ByteSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractLong2ByteSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractLong2ByteSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public LongComparator comparator() {
/*  63 */       return AbstractLong2ByteSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public long firstLong() {
/*  67 */       return AbstractLong2ByteSortedMap.this.firstLongKey();
/*     */     }
/*     */     
/*     */     public long lastLong() {
/*  71 */       return AbstractLong2ByteSortedMap.this.lastLongKey();
/*     */     }
/*     */     
/*     */     public LongSortedSet headSet(long to) {
/*  75 */       return AbstractLong2ByteSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public LongSortedSet tailSet(long from) {
/*  79 */       return AbstractLong2ByteSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public LongSortedSet subSet(long from, long to) {
/*  83 */       return AbstractLong2ByteSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public LongBidirectionalIterator iterator(long from) {
/*  87 */       return new AbstractLong2ByteSortedMap.KeySetIterator(AbstractLong2ByteSortedMap.this.long2ByteEntrySet().iterator(new AbstractLong2ByteMap.BasicEntry(from, (byte)0)));
/*     */     }
/*     */     
/*     */     public LongBidirectionalIterator iterator() {
/*  91 */       return new AbstractLong2ByteSortedMap.KeySetIterator(Long2ByteSortedMaps.fastIterator(AbstractLong2ByteSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements LongBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Long2ByteMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Long2ByteMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public long nextLong() {
/* 108 */       return ((Long2ByteMap.Entry)this.i.next()).getLongKey();
/*     */     }
/*     */     
/*     */     public long previousLong() {
/* 112 */       return ((Long2ByteMap.Entry)this.i.previous()).getLongKey();
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
/*     */   public ByteCollection values() {
/* 138 */     return (ByteCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractByteCollection {
/*     */     public ByteIterator iterator() {
/* 144 */       return new AbstractLong2ByteSortedMap.ValuesIterator(Long2ByteSortedMaps.fastIterator(AbstractLong2ByteSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(byte k) {
/* 148 */       return AbstractLong2ByteSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractLong2ByteSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractLong2ByteSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements ByteIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Long2ByteMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Long2ByteMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public byte nextByte() {
/* 173 */       return ((Long2ByteMap.Entry)this.i.next()).getByteValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\AbstractLong2ByteSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */