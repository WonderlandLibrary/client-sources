/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*     */ import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortCollection;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
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
/*     */ public abstract class AbstractFloat2ShortSortedMap
/*     */   extends AbstractFloat2ShortMap
/*     */   implements Float2ShortSortedMap
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
/*  51 */       return AbstractFloat2ShortSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractFloat2ShortSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractFloat2ShortSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public FloatComparator comparator() {
/*  63 */       return AbstractFloat2ShortSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public float firstFloat() {
/*  67 */       return AbstractFloat2ShortSortedMap.this.firstFloatKey();
/*     */     }
/*     */     
/*     */     public float lastFloat() {
/*  71 */       return AbstractFloat2ShortSortedMap.this.lastFloatKey();
/*     */     }
/*     */     
/*     */     public FloatSortedSet headSet(float to) {
/*  75 */       return AbstractFloat2ShortSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public FloatSortedSet tailSet(float from) {
/*  79 */       return AbstractFloat2ShortSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public FloatSortedSet subSet(float from, float to) {
/*  83 */       return AbstractFloat2ShortSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public FloatBidirectionalIterator iterator(float from) {
/*  87 */       return new AbstractFloat2ShortSortedMap.KeySetIterator(AbstractFloat2ShortSortedMap.this.float2ShortEntrySet().iterator(new AbstractFloat2ShortMap.BasicEntry(from, (short)0)));
/*     */     }
/*     */     
/*     */     public FloatBidirectionalIterator iterator() {
/*  91 */       return new AbstractFloat2ShortSortedMap.KeySetIterator(Float2ShortSortedMaps.fastIterator(AbstractFloat2ShortSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements FloatBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Float2ShortMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Float2ShortMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public float nextFloat() {
/* 108 */       return ((Float2ShortMap.Entry)this.i.next()).getFloatKey();
/*     */     }
/*     */     
/*     */     public float previousFloat() {
/* 112 */       return ((Float2ShortMap.Entry)this.i.previous()).getFloatKey();
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
/*     */   public ShortCollection values() {
/* 138 */     return (ShortCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractShortCollection {
/*     */     public ShortIterator iterator() {
/* 144 */       return new AbstractFloat2ShortSortedMap.ValuesIterator(Float2ShortSortedMaps.fastIterator(AbstractFloat2ShortSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(short k) {
/* 148 */       return AbstractFloat2ShortSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractFloat2ShortSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractFloat2ShortSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements ShortIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Float2ShortMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Float2ShortMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public short nextShort() {
/* 173 */       return ((Float2ShortMap.Entry)this.i.next()).getShortValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\AbstractFloat2ShortSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */