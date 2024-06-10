/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
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
/*     */ public abstract class AbstractByte2BooleanSortedMap
/*     */   extends AbstractByte2BooleanMap
/*     */   implements Byte2BooleanSortedMap
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
/*  51 */       return AbstractByte2BooleanSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractByte2BooleanSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractByte2BooleanSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public ByteComparator comparator() {
/*  63 */       return AbstractByte2BooleanSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public byte firstByte() {
/*  67 */       return AbstractByte2BooleanSortedMap.this.firstByteKey();
/*     */     }
/*     */     
/*     */     public byte lastByte() {
/*  71 */       return AbstractByte2BooleanSortedMap.this.lastByteKey();
/*     */     }
/*     */     
/*     */     public ByteSortedSet headSet(byte to) {
/*  75 */       return AbstractByte2BooleanSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public ByteSortedSet tailSet(byte from) {
/*  79 */       return AbstractByte2BooleanSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public ByteSortedSet subSet(byte from, byte to) {
/*  83 */       return AbstractByte2BooleanSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public ByteBidirectionalIterator iterator(byte from) {
/*  87 */       return new AbstractByte2BooleanSortedMap.KeySetIterator(AbstractByte2BooleanSortedMap.this.byte2BooleanEntrySet().iterator(new AbstractByte2BooleanMap.BasicEntry(from, false)));
/*     */     }
/*     */     
/*     */     public ByteBidirectionalIterator iterator() {
/*  91 */       return new AbstractByte2BooleanSortedMap.KeySetIterator(Byte2BooleanSortedMaps.fastIterator(AbstractByte2BooleanSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements ByteBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Byte2BooleanMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Byte2BooleanMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public byte nextByte() {
/* 108 */       return ((Byte2BooleanMap.Entry)this.i.next()).getByteKey();
/*     */     }
/*     */     
/*     */     public byte previousByte() {
/* 112 */       return ((Byte2BooleanMap.Entry)this.i.previous()).getByteKey();
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
/*     */   public BooleanCollection values() {
/* 138 */     return (BooleanCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractBooleanCollection {
/*     */     public BooleanIterator iterator() {
/* 144 */       return new AbstractByte2BooleanSortedMap.ValuesIterator(Byte2BooleanSortedMaps.fastIterator(AbstractByte2BooleanSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(boolean k) {
/* 148 */       return AbstractByte2BooleanSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractByte2BooleanSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractByte2BooleanSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements BooleanIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Byte2BooleanMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Byte2BooleanMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public boolean nextBoolean() {
/* 173 */       return ((Byte2BooleanMap.Entry)this.i.next()).getBooleanValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\AbstractByte2BooleanSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */