/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public abstract class AbstractByte2IntSortedMap
/*     */   extends AbstractByte2IntMap
/*     */   implements Byte2IntSortedMap
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public ByteSortedSet keySet() {
/*  45 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractByteSortedSet {
/*     */     public boolean contains(byte k) {
/*  51 */       return AbstractByte2IntSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractByte2IntSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractByte2IntSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public ByteComparator comparator() {
/*  63 */       return AbstractByte2IntSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public byte firstByte() {
/*  67 */       return AbstractByte2IntSortedMap.this.firstByteKey();
/*     */     }
/*     */     
/*     */     public byte lastByte() {
/*  71 */       return AbstractByte2IntSortedMap.this.lastByteKey();
/*     */     }
/*     */     
/*     */     public ByteSortedSet headSet(byte to) {
/*  75 */       return AbstractByte2IntSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public ByteSortedSet tailSet(byte from) {
/*  79 */       return AbstractByte2IntSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public ByteSortedSet subSet(byte from, byte to) {
/*  83 */       return AbstractByte2IntSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public ByteBidirectionalIterator iterator(byte from) {
/*  87 */       return new AbstractByte2IntSortedMap.KeySetIterator(AbstractByte2IntSortedMap.this.byte2IntEntrySet().iterator(new AbstractByte2IntMap.BasicEntry(from, 0)));
/*     */     }
/*     */     
/*     */     public ByteBidirectionalIterator iterator() {
/*  91 */       return new AbstractByte2IntSortedMap.KeySetIterator(Byte2IntSortedMaps.fastIterator(AbstractByte2IntSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements ByteBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Byte2IntMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Byte2IntMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public byte nextByte() {
/* 108 */       return ((Byte2IntMap.Entry)this.i.next()).getByteKey();
/*     */     }
/*     */     
/*     */     public byte previousByte() {
/* 112 */       return ((Byte2IntMap.Entry)this.i.previous()).getByteKey();
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
/* 144 */       return new AbstractByte2IntSortedMap.ValuesIterator(Byte2IntSortedMaps.fastIterator(AbstractByte2IntSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(int k) {
/* 148 */       return AbstractByte2IntSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractByte2IntSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractByte2IntSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements IntIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Byte2IntMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Byte2IntMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public int nextInt() {
/* 173 */       return ((Byte2IntMap.Entry)this.i.next()).getIntValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\AbstractByte2IntSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */