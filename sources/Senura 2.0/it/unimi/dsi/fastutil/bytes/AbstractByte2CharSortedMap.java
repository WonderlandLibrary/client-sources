/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*     */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*     */ import it.unimi.dsi.fastutil.chars.CharIterator;
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
/*     */ public abstract class AbstractByte2CharSortedMap
/*     */   extends AbstractByte2CharMap
/*     */   implements Byte2CharSortedMap
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
/*  51 */       return AbstractByte2CharSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractByte2CharSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractByte2CharSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public ByteComparator comparator() {
/*  63 */       return AbstractByte2CharSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public byte firstByte() {
/*  67 */       return AbstractByte2CharSortedMap.this.firstByteKey();
/*     */     }
/*     */     
/*     */     public byte lastByte() {
/*  71 */       return AbstractByte2CharSortedMap.this.lastByteKey();
/*     */     }
/*     */     
/*     */     public ByteSortedSet headSet(byte to) {
/*  75 */       return AbstractByte2CharSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public ByteSortedSet tailSet(byte from) {
/*  79 */       return AbstractByte2CharSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public ByteSortedSet subSet(byte from, byte to) {
/*  83 */       return AbstractByte2CharSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public ByteBidirectionalIterator iterator(byte from) {
/*  87 */       return new AbstractByte2CharSortedMap.KeySetIterator(AbstractByte2CharSortedMap.this.byte2CharEntrySet().iterator(new AbstractByte2CharMap.BasicEntry(from, false)));
/*     */     }
/*     */     
/*     */     public ByteBidirectionalIterator iterator() {
/*  91 */       return new AbstractByte2CharSortedMap.KeySetIterator(Byte2CharSortedMaps.fastIterator(AbstractByte2CharSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements ByteBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Byte2CharMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Byte2CharMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public byte nextByte() {
/* 108 */       return ((Byte2CharMap.Entry)this.i.next()).getByteKey();
/*     */     }
/*     */     
/*     */     public byte previousByte() {
/* 112 */       return ((Byte2CharMap.Entry)this.i.previous()).getByteKey();
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
/*     */   public CharCollection values() {
/* 138 */     return (CharCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractCharCollection {
/*     */     public CharIterator iterator() {
/* 144 */       return new AbstractByte2CharSortedMap.ValuesIterator(Byte2CharSortedMaps.fastIterator(AbstractByte2CharSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(char k) {
/* 148 */       return AbstractByte2CharSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractByte2CharSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractByte2CharSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements CharIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Byte2CharMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Byte2CharMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public char nextChar() {
/* 173 */       return ((Byte2CharMap.Entry)this.i.next()).getCharValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\AbstractByte2CharSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */