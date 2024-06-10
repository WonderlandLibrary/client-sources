/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ public abstract class AbstractDouble2BooleanSortedMap
/*     */   extends AbstractDouble2BooleanMap
/*     */   implements Double2BooleanSortedMap
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
/*  53 */       return AbstractDouble2BooleanSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  57 */       return AbstractDouble2BooleanSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  61 */       AbstractDouble2BooleanSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public DoubleComparator comparator() {
/*  65 */       return AbstractDouble2BooleanSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public double firstDouble() {
/*  69 */       return AbstractDouble2BooleanSortedMap.this.firstDoubleKey();
/*     */     }
/*     */     
/*     */     public double lastDouble() {
/*  73 */       return AbstractDouble2BooleanSortedMap.this.lastDoubleKey();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet headSet(double to) {
/*  77 */       return AbstractDouble2BooleanSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet tailSet(double from) {
/*  81 */       return AbstractDouble2BooleanSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet subSet(double from, double to) {
/*  85 */       return AbstractDouble2BooleanSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public DoubleBidirectionalIterator iterator(double from) {
/*  89 */       return new AbstractDouble2BooleanSortedMap.KeySetIterator(AbstractDouble2BooleanSortedMap.this.double2BooleanEntrySet().iterator(new AbstractDouble2BooleanMap.BasicEntry(from, false)));
/*     */     }
/*     */     
/*     */     public DoubleBidirectionalIterator iterator() {
/*  93 */       return new AbstractDouble2BooleanSortedMap.KeySetIterator(Double2BooleanSortedMaps.fastIterator(AbstractDouble2BooleanSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements DoubleBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Double2BooleanMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Double2BooleanMap.Entry> i) {
/* 106 */       this.i = i;
/*     */     }
/*     */     
/*     */     public double nextDouble() {
/* 110 */       return ((Double2BooleanMap.Entry)this.i.next()).getDoubleKey();
/*     */     }
/*     */     
/*     */     public double previousDouble() {
/* 114 */       return ((Double2BooleanMap.Entry)this.i.previous()).getDoubleKey();
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
/* 146 */       return new AbstractDouble2BooleanSortedMap.ValuesIterator(Double2BooleanSortedMaps.fastIterator(AbstractDouble2BooleanSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(boolean k) {
/* 150 */       return AbstractDouble2BooleanSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 154 */       return AbstractDouble2BooleanSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 158 */       AbstractDouble2BooleanSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements BooleanIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Double2BooleanMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Double2BooleanMap.Entry> i) {
/* 171 */       this.i = i;
/*     */     }
/*     */     
/*     */     public boolean nextBoolean() {
/* 175 */       return ((Double2BooleanMap.Entry)this.i.next()).getBooleanValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 179 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\AbstractDouble2BooleanSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */