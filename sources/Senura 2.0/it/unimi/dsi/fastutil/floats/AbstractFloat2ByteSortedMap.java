/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public abstract class AbstractFloat2ByteSortedMap
/*     */   extends AbstractFloat2ByteMap
/*     */   implements Float2ByteSortedMap
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public FloatSortedSet keySet() {
/*  45 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractFloatSortedSet {
/*     */     public boolean contains(float k) {
/*  51 */       return AbstractFloat2ByteSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractFloat2ByteSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractFloat2ByteSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public FloatComparator comparator() {
/*  63 */       return AbstractFloat2ByteSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public float firstFloat() {
/*  67 */       return AbstractFloat2ByteSortedMap.this.firstFloatKey();
/*     */     }
/*     */     
/*     */     public float lastFloat() {
/*  71 */       return AbstractFloat2ByteSortedMap.this.lastFloatKey();
/*     */     }
/*     */     
/*     */     public FloatSortedSet headSet(float to) {
/*  75 */       return AbstractFloat2ByteSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public FloatSortedSet tailSet(float from) {
/*  79 */       return AbstractFloat2ByteSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public FloatSortedSet subSet(float from, float to) {
/*  83 */       return AbstractFloat2ByteSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public FloatBidirectionalIterator iterator(float from) {
/*  87 */       return new AbstractFloat2ByteSortedMap.KeySetIterator(AbstractFloat2ByteSortedMap.this.float2ByteEntrySet().iterator(new AbstractFloat2ByteMap.BasicEntry(from, (byte)0)));
/*     */     }
/*     */     
/*     */     public FloatBidirectionalIterator iterator() {
/*  91 */       return new AbstractFloat2ByteSortedMap.KeySetIterator(Float2ByteSortedMaps.fastIterator(AbstractFloat2ByteSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements FloatBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Float2ByteMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Float2ByteMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public float nextFloat() {
/* 108 */       return ((Float2ByteMap.Entry)this.i.next()).getFloatKey();
/*     */     }
/*     */     
/*     */     public float previousFloat() {
/* 112 */       return ((Float2ByteMap.Entry)this.i.previous()).getFloatKey();
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
/* 144 */       return new AbstractFloat2ByteSortedMap.ValuesIterator(Float2ByteSortedMaps.fastIterator(AbstractFloat2ByteSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(byte k) {
/* 148 */       return AbstractFloat2ByteSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractFloat2ByteSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractFloat2ByteSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements ByteIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Float2ByteMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Float2ByteMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public byte nextByte() {
/* 173 */       return ((Float2ByteMap.Entry)this.i.next()).getByteValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\AbstractFloat2ByteSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */