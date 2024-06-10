/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatIterator;
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
/*     */ public abstract class AbstractDouble2FloatSortedMap
/*     */   extends AbstractDouble2FloatMap
/*     */   implements Double2FloatSortedMap
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public DoubleSortedSet keySet() {
/*  45 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractDoubleSortedSet {
/*     */     public boolean contains(double k) {
/*  51 */       return AbstractDouble2FloatSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractDouble2FloatSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractDouble2FloatSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public DoubleComparator comparator() {
/*  63 */       return AbstractDouble2FloatSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public double firstDouble() {
/*  67 */       return AbstractDouble2FloatSortedMap.this.firstDoubleKey();
/*     */     }
/*     */     
/*     */     public double lastDouble() {
/*  71 */       return AbstractDouble2FloatSortedMap.this.lastDoubleKey();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet headSet(double to) {
/*  75 */       return AbstractDouble2FloatSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet tailSet(double from) {
/*  79 */       return AbstractDouble2FloatSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet subSet(double from, double to) {
/*  83 */       return AbstractDouble2FloatSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public DoubleBidirectionalIterator iterator(double from) {
/*  87 */       return new AbstractDouble2FloatSortedMap.KeySetIterator(AbstractDouble2FloatSortedMap.this.double2FloatEntrySet().iterator(new AbstractDouble2FloatMap.BasicEntry(from, 0.0F)));
/*     */     }
/*     */     
/*     */     public DoubleBidirectionalIterator iterator() {
/*  91 */       return new AbstractDouble2FloatSortedMap.KeySetIterator(Double2FloatSortedMaps.fastIterator(AbstractDouble2FloatSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements DoubleBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Double2FloatMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Double2FloatMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public double nextDouble() {
/* 108 */       return ((Double2FloatMap.Entry)this.i.next()).getDoubleKey();
/*     */     }
/*     */     
/*     */     public double previousDouble() {
/* 112 */       return ((Double2FloatMap.Entry)this.i.previous()).getDoubleKey();
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
/*     */   public FloatCollection values() {
/* 138 */     return (FloatCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractFloatCollection {
/*     */     public FloatIterator iterator() {
/* 144 */       return new AbstractDouble2FloatSortedMap.ValuesIterator(Double2FloatSortedMaps.fastIterator(AbstractDouble2FloatSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(float k) {
/* 148 */       return AbstractDouble2FloatSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractDouble2FloatSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractDouble2FloatSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements FloatIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Double2FloatMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Double2FloatMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public float nextFloat() {
/* 173 */       return ((Double2FloatMap.Entry)this.i.next()).getFloatValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\AbstractDouble2FloatSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */