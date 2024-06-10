/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ 
/*     */ 
/*     */ public abstract class AbstractFloat2BooleanSortedMap
/*     */   extends AbstractFloat2BooleanMap
/*     */   implements Float2BooleanSortedMap
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public FloatSortedSet keySet() {
/*  47 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractFloatSortedSet {
/*     */     public boolean contains(float k) {
/*  53 */       return AbstractFloat2BooleanSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  57 */       return AbstractFloat2BooleanSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  61 */       AbstractFloat2BooleanSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public FloatComparator comparator() {
/*  65 */       return AbstractFloat2BooleanSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public float firstFloat() {
/*  69 */       return AbstractFloat2BooleanSortedMap.this.firstFloatKey();
/*     */     }
/*     */     
/*     */     public float lastFloat() {
/*  73 */       return AbstractFloat2BooleanSortedMap.this.lastFloatKey();
/*     */     }
/*     */     
/*     */     public FloatSortedSet headSet(float to) {
/*  77 */       return AbstractFloat2BooleanSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public FloatSortedSet tailSet(float from) {
/*  81 */       return AbstractFloat2BooleanSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public FloatSortedSet subSet(float from, float to) {
/*  85 */       return AbstractFloat2BooleanSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public FloatBidirectionalIterator iterator(float from) {
/*  89 */       return new AbstractFloat2BooleanSortedMap.KeySetIterator(AbstractFloat2BooleanSortedMap.this.float2BooleanEntrySet().iterator(new AbstractFloat2BooleanMap.BasicEntry(from, false)));
/*     */     }
/*     */     
/*     */     public FloatBidirectionalIterator iterator() {
/*  93 */       return new AbstractFloat2BooleanSortedMap.KeySetIterator(Float2BooleanSortedMaps.fastIterator(AbstractFloat2BooleanSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements FloatBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Float2BooleanMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Float2BooleanMap.Entry> i) {
/* 106 */       this.i = i;
/*     */     }
/*     */     
/*     */     public float nextFloat() {
/* 110 */       return ((Float2BooleanMap.Entry)this.i.next()).getFloatKey();
/*     */     }
/*     */     
/*     */     public float previousFloat() {
/* 114 */       return ((Float2BooleanMap.Entry)this.i.previous()).getFloatKey();
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
/*     */   public BooleanCollection values() {
/* 140 */     return (BooleanCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractBooleanCollection {
/*     */     public BooleanIterator iterator() {
/* 146 */       return new AbstractFloat2BooleanSortedMap.ValuesIterator(Float2BooleanSortedMaps.fastIterator(AbstractFloat2BooleanSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(boolean k) {
/* 150 */       return AbstractFloat2BooleanSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 154 */       return AbstractFloat2BooleanSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 158 */       AbstractFloat2BooleanSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements BooleanIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Float2BooleanMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Float2BooleanMap.Entry> i) {
/* 171 */       this.i = i;
/*     */     }
/*     */     
/*     */     public boolean nextBoolean() {
/* 175 */       return ((Float2BooleanMap.Entry)this.i.next()).getBooleanValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 179 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\AbstractFloat2BooleanSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */