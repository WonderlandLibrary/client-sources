/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractDouble2DoubleSortedMap
/*     */   extends AbstractDouble2DoubleMap
/*     */   implements Double2DoubleSortedMap
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public DoubleSortedSet keySet() {
/*  47 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractDoubleSortedSet {
/*     */     public boolean contains(double k) {
/*  53 */       return AbstractDouble2DoubleSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  57 */       return AbstractDouble2DoubleSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  61 */       AbstractDouble2DoubleSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public DoubleComparator comparator() {
/*  65 */       return AbstractDouble2DoubleSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public double firstDouble() {
/*  69 */       return AbstractDouble2DoubleSortedMap.this.firstDoubleKey();
/*     */     }
/*     */     
/*     */     public double lastDouble() {
/*  73 */       return AbstractDouble2DoubleSortedMap.this.lastDoubleKey();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet headSet(double to) {
/*  77 */       return AbstractDouble2DoubleSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet tailSet(double from) {
/*  81 */       return AbstractDouble2DoubleSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet subSet(double from, double to) {
/*  85 */       return AbstractDouble2DoubleSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public DoubleBidirectionalIterator iterator(double from) {
/*  89 */       return new AbstractDouble2DoubleSortedMap.KeySetIterator(AbstractDouble2DoubleSortedMap.this.double2DoubleEntrySet().iterator(new AbstractDouble2DoubleMap.BasicEntry(from, 0.0D)));
/*     */     }
/*     */     
/*     */     public DoubleBidirectionalIterator iterator() {
/*  93 */       return new AbstractDouble2DoubleSortedMap.KeySetIterator(Double2DoubleSortedMaps.fastIterator(AbstractDouble2DoubleSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements DoubleBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Double2DoubleMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Double2DoubleMap.Entry> i) {
/* 106 */       this.i = i;
/*     */     }
/*     */     
/*     */     public double nextDouble() {
/* 110 */       return ((Double2DoubleMap.Entry)this.i.next()).getDoubleKey();
/*     */     }
/*     */     
/*     */     public double previousDouble() {
/* 114 */       return ((Double2DoubleMap.Entry)this.i.previous()).getDoubleKey();
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
/*     */   public DoubleCollection values() {
/* 140 */     return new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractDoubleCollection {
/*     */     public DoubleIterator iterator() {
/* 146 */       return new AbstractDouble2DoubleSortedMap.ValuesIterator(Double2DoubleSortedMaps.fastIterator(AbstractDouble2DoubleSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(double k) {
/* 150 */       return AbstractDouble2DoubleSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 154 */       return AbstractDouble2DoubleSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 158 */       AbstractDouble2DoubleSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements DoubleIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Double2DoubleMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Double2DoubleMap.Entry> i) {
/* 171 */       this.i = i;
/*     */     }
/*     */     
/*     */     public double nextDouble() {
/* 175 */       return ((Double2DoubleMap.Entry)this.i.next()).getDoubleValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 179 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\AbstractDouble2DoubleSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */