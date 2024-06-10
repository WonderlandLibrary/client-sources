/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public abstract class AbstractFloat2LongSortedMap
/*     */   extends AbstractFloat2LongMap
/*     */   implements Float2LongSortedMap
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
/*  51 */       return AbstractFloat2LongSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractFloat2LongSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractFloat2LongSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public FloatComparator comparator() {
/*  63 */       return AbstractFloat2LongSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public float firstFloat() {
/*  67 */       return AbstractFloat2LongSortedMap.this.firstFloatKey();
/*     */     }
/*     */     
/*     */     public float lastFloat() {
/*  71 */       return AbstractFloat2LongSortedMap.this.lastFloatKey();
/*     */     }
/*     */     
/*     */     public FloatSortedSet headSet(float to) {
/*  75 */       return AbstractFloat2LongSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public FloatSortedSet tailSet(float from) {
/*  79 */       return AbstractFloat2LongSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public FloatSortedSet subSet(float from, float to) {
/*  83 */       return AbstractFloat2LongSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public FloatBidirectionalIterator iterator(float from) {
/*  87 */       return new AbstractFloat2LongSortedMap.KeySetIterator(AbstractFloat2LongSortedMap.this.float2LongEntrySet().iterator(new AbstractFloat2LongMap.BasicEntry(from, 0L)));
/*     */     }
/*     */     
/*     */     public FloatBidirectionalIterator iterator() {
/*  91 */       return new AbstractFloat2LongSortedMap.KeySetIterator(Float2LongSortedMaps.fastIterator(AbstractFloat2LongSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements FloatBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Float2LongMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Float2LongMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public float nextFloat() {
/* 108 */       return ((Float2LongMap.Entry)this.i.next()).getFloatKey();
/*     */     }
/*     */     
/*     */     public float previousFloat() {
/* 112 */       return ((Float2LongMap.Entry)this.i.previous()).getFloatKey();
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
/* 144 */       return new AbstractFloat2LongSortedMap.ValuesIterator(Float2LongSortedMaps.fastIterator(AbstractFloat2LongSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(long k) {
/* 148 */       return AbstractFloat2LongSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractFloat2LongSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractFloat2LongSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements LongIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Float2LongMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Float2LongMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public long nextLong() {
/* 173 */       return ((Float2LongMap.Entry)this.i.next()).getLongValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\AbstractFloat2LongSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */