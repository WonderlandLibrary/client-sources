/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongIterator;
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
/*     */ public abstract class AbstractShort2LongSortedMap
/*     */   extends AbstractShort2LongMap
/*     */   implements Short2LongSortedMap
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public ShortSortedSet keySet() {
/*  45 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractShortSortedSet {
/*     */     public boolean contains(short k) {
/*  51 */       return AbstractShort2LongSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractShort2LongSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractShort2LongSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public ShortComparator comparator() {
/*  63 */       return AbstractShort2LongSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public short firstShort() {
/*  67 */       return AbstractShort2LongSortedMap.this.firstShortKey();
/*     */     }
/*     */     
/*     */     public short lastShort() {
/*  71 */       return AbstractShort2LongSortedMap.this.lastShortKey();
/*     */     }
/*     */     
/*     */     public ShortSortedSet headSet(short to) {
/*  75 */       return AbstractShort2LongSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public ShortSortedSet tailSet(short from) {
/*  79 */       return AbstractShort2LongSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public ShortSortedSet subSet(short from, short to) {
/*  83 */       return AbstractShort2LongSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public ShortBidirectionalIterator iterator(short from) {
/*  87 */       return new AbstractShort2LongSortedMap.KeySetIterator(AbstractShort2LongSortedMap.this.short2LongEntrySet().iterator(new AbstractShort2LongMap.BasicEntry(from, 0L)));
/*     */     }
/*     */     
/*     */     public ShortBidirectionalIterator iterator() {
/*  91 */       return new AbstractShort2LongSortedMap.KeySetIterator(Short2LongSortedMaps.fastIterator(AbstractShort2LongSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements ShortBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Short2LongMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Short2LongMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public short nextShort() {
/* 108 */       return ((Short2LongMap.Entry)this.i.next()).getShortKey();
/*     */     }
/*     */     
/*     */     public short previousShort() {
/* 112 */       return ((Short2LongMap.Entry)this.i.previous()).getShortKey();
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
/*     */   public LongCollection values() {
/* 138 */     return (LongCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractLongCollection {
/*     */     public LongIterator iterator() {
/* 144 */       return new AbstractShort2LongSortedMap.ValuesIterator(Short2LongSortedMaps.fastIterator(AbstractShort2LongSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(long k) {
/* 148 */       return AbstractShort2LongSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractShort2LongSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractShort2LongSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements LongIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Short2LongMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Short2LongMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public long nextLong() {
/* 173 */       return ((Short2LongMap.Entry)this.i.next()).getLongValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\AbstractShort2LongSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */