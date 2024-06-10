/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public abstract class AbstractShort2ByteSortedMap
/*     */   extends AbstractShort2ByteMap
/*     */   implements Short2ByteSortedMap
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
/*  51 */       return AbstractShort2ByteSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractShort2ByteSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractShort2ByteSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public ShortComparator comparator() {
/*  63 */       return AbstractShort2ByteSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public short firstShort() {
/*  67 */       return AbstractShort2ByteSortedMap.this.firstShortKey();
/*     */     }
/*     */     
/*     */     public short lastShort() {
/*  71 */       return AbstractShort2ByteSortedMap.this.lastShortKey();
/*     */     }
/*     */     
/*     */     public ShortSortedSet headSet(short to) {
/*  75 */       return AbstractShort2ByteSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public ShortSortedSet tailSet(short from) {
/*  79 */       return AbstractShort2ByteSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public ShortSortedSet subSet(short from, short to) {
/*  83 */       return AbstractShort2ByteSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public ShortBidirectionalIterator iterator(short from) {
/*  87 */       return new AbstractShort2ByteSortedMap.KeySetIterator(AbstractShort2ByteSortedMap.this.short2ByteEntrySet().iterator(new AbstractShort2ByteMap.BasicEntry(from, (byte)0)));
/*     */     }
/*     */     
/*     */     public ShortBidirectionalIterator iterator() {
/*  91 */       return new AbstractShort2ByteSortedMap.KeySetIterator(Short2ByteSortedMaps.fastIterator(AbstractShort2ByteSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements ShortBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Short2ByteMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Short2ByteMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public short nextShort() {
/* 108 */       return ((Short2ByteMap.Entry)this.i.next()).getShortKey();
/*     */     }
/*     */     
/*     */     public short previousShort() {
/* 112 */       return ((Short2ByteMap.Entry)this.i.previous()).getShortKey();
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
/* 144 */       return new AbstractShort2ByteSortedMap.ValuesIterator(Short2ByteSortedMaps.fastIterator(AbstractShort2ByteSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(byte k) {
/* 148 */       return AbstractShort2ByteSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractShort2ByteSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractShort2ByteSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements ByteIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Short2ByteMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Short2ByteMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public byte nextByte() {
/* 173 */       return ((Short2ByteMap.Entry)this.i.next()).getByteValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\AbstractShort2ByteSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */