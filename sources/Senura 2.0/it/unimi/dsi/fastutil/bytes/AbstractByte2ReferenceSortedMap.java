/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public abstract class AbstractByte2ReferenceSortedMap<V>
/*     */   extends AbstractByte2ReferenceMap<V>
/*     */   implements Byte2ReferenceSortedMap<V>
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public ByteSortedSet keySet() {
/*  47 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractByteSortedSet {
/*     */     public boolean contains(byte k) {
/*  53 */       return AbstractByte2ReferenceSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  57 */       return AbstractByte2ReferenceSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  61 */       AbstractByte2ReferenceSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public ByteComparator comparator() {
/*  65 */       return AbstractByte2ReferenceSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public byte firstByte() {
/*  69 */       return AbstractByte2ReferenceSortedMap.this.firstByteKey();
/*     */     }
/*     */     
/*     */     public byte lastByte() {
/*  73 */       return AbstractByte2ReferenceSortedMap.this.lastByteKey();
/*     */     }
/*     */     
/*     */     public ByteSortedSet headSet(byte to) {
/*  77 */       return AbstractByte2ReferenceSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public ByteSortedSet tailSet(byte from) {
/*  81 */       return AbstractByte2ReferenceSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public ByteSortedSet subSet(byte from, byte to) {
/*  85 */       return AbstractByte2ReferenceSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public ByteBidirectionalIterator iterator(byte from) {
/*  89 */       return new AbstractByte2ReferenceSortedMap.KeySetIterator(AbstractByte2ReferenceSortedMap.this.byte2ReferenceEntrySet().iterator(new AbstractByte2ReferenceMap.BasicEntry(from, null)));
/*     */     }
/*     */     
/*     */     public ByteBidirectionalIterator iterator() {
/*  93 */       return new AbstractByte2ReferenceSortedMap.KeySetIterator(Byte2ReferenceSortedMaps.fastIterator(AbstractByte2ReferenceSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<V>
/*     */     implements ByteBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Byte2ReferenceMap.Entry<V>> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Byte2ReferenceMap.Entry<V>> i) {
/* 106 */       this.i = i;
/*     */     }
/*     */     
/*     */     public byte nextByte() {
/* 110 */       return ((Byte2ReferenceMap.Entry)this.i.next()).getByteKey();
/*     */     }
/*     */     
/*     */     public byte previousByte() {
/* 114 */       return ((Byte2ReferenceMap.Entry)this.i.previous()).getByteKey();
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
/* 146 */       return new AbstractByte2ReferenceSortedMap.ValuesIterator<>(Byte2ReferenceSortedMaps.fastIterator(AbstractByte2ReferenceSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(Object k) {
/* 150 */       return AbstractByte2ReferenceSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 154 */       return AbstractByte2ReferenceSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 158 */       AbstractByte2ReferenceSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<V>
/*     */     implements ObjectIterator<V>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Byte2ReferenceMap.Entry<V>> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Byte2ReferenceMap.Entry<V>> i) {
/* 171 */       this.i = i;
/*     */     }
/*     */     
/*     */     public V next() {
/* 175 */       return ((Byte2ReferenceMap.Entry<V>)this.i.next()).getValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 179 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\AbstractByte2ReferenceSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */