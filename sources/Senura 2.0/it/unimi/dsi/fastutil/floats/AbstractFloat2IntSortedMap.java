/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public abstract class AbstractFloat2IntSortedMap
/*     */   extends AbstractFloat2IntMap
/*     */   implements Float2IntSortedMap
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
/*  51 */       return AbstractFloat2IntSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractFloat2IntSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractFloat2IntSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public FloatComparator comparator() {
/*  63 */       return AbstractFloat2IntSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public float firstFloat() {
/*  67 */       return AbstractFloat2IntSortedMap.this.firstFloatKey();
/*     */     }
/*     */     
/*     */     public float lastFloat() {
/*  71 */       return AbstractFloat2IntSortedMap.this.lastFloatKey();
/*     */     }
/*     */     
/*     */     public FloatSortedSet headSet(float to) {
/*  75 */       return AbstractFloat2IntSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public FloatSortedSet tailSet(float from) {
/*  79 */       return AbstractFloat2IntSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public FloatSortedSet subSet(float from, float to) {
/*  83 */       return AbstractFloat2IntSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public FloatBidirectionalIterator iterator(float from) {
/*  87 */       return new AbstractFloat2IntSortedMap.KeySetIterator(AbstractFloat2IntSortedMap.this.float2IntEntrySet().iterator(new AbstractFloat2IntMap.BasicEntry(from, 0)));
/*     */     }
/*     */     
/*     */     public FloatBidirectionalIterator iterator() {
/*  91 */       return new AbstractFloat2IntSortedMap.KeySetIterator(Float2IntSortedMaps.fastIterator(AbstractFloat2IntSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements FloatBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Float2IntMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Float2IntMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public float nextFloat() {
/* 108 */       return ((Float2IntMap.Entry)this.i.next()).getFloatKey();
/*     */     }
/*     */     
/*     */     public float previousFloat() {
/* 112 */       return ((Float2IntMap.Entry)this.i.previous()).getFloatKey();
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
/* 144 */       return new AbstractFloat2IntSortedMap.ValuesIterator(Float2IntSortedMaps.fastIterator(AbstractFloat2IntSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(int k) {
/* 148 */       return AbstractFloat2IntSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractFloat2IntSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractFloat2IntSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements IntIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Float2IntMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Float2IntMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public int nextInt() {
/* 173 */       return ((Float2IntMap.Entry)this.i.next()).getIntValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\AbstractFloat2IntSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */