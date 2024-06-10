/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ public abstract class AbstractDouble2ShortSortedMap
/*     */   extends AbstractDouble2ShortMap
/*     */   implements Double2ShortSortedMap
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
/*  51 */       return AbstractDouble2ShortSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractDouble2ShortSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractDouble2ShortSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public DoubleComparator comparator() {
/*  63 */       return AbstractDouble2ShortSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public double firstDouble() {
/*  67 */       return AbstractDouble2ShortSortedMap.this.firstDoubleKey();
/*     */     }
/*     */     
/*     */     public double lastDouble() {
/*  71 */       return AbstractDouble2ShortSortedMap.this.lastDoubleKey();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet headSet(double to) {
/*  75 */       return AbstractDouble2ShortSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet tailSet(double from) {
/*  79 */       return AbstractDouble2ShortSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet subSet(double from, double to) {
/*  83 */       return AbstractDouble2ShortSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public DoubleBidirectionalIterator iterator(double from) {
/*  87 */       return new AbstractDouble2ShortSortedMap.KeySetIterator(AbstractDouble2ShortSortedMap.this.double2ShortEntrySet().iterator(new AbstractDouble2ShortMap.BasicEntry(from, (short)0)));
/*     */     }
/*     */     
/*     */     public DoubleBidirectionalIterator iterator() {
/*  91 */       return new AbstractDouble2ShortSortedMap.KeySetIterator(Double2ShortSortedMaps.fastIterator(AbstractDouble2ShortSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements DoubleBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Double2ShortMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Double2ShortMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public double nextDouble() {
/* 108 */       return ((Double2ShortMap.Entry)this.i.next()).getDoubleKey();
/*     */     }
/*     */     
/*     */     public double previousDouble() {
/* 112 */       return ((Double2ShortMap.Entry)this.i.previous()).getDoubleKey();
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
/* 144 */       return new AbstractDouble2ShortSortedMap.ValuesIterator(Double2ShortSortedMaps.fastIterator(AbstractDouble2ShortSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(short k) {
/* 148 */       return AbstractDouble2ShortSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractDouble2ShortSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractDouble2ShortSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements ShortIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Double2ShortMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Double2ShortMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public short nextShort() {
/* 173 */       return ((Double2ShortMap.Entry)this.i.next()).getShortValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\AbstractDouble2ShortSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */