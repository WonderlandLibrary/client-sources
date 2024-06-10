/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*     */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*     */ import it.unimi.dsi.fastutil.chars.CharIterator;
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
/*     */ public abstract class AbstractDouble2CharSortedMap
/*     */   extends AbstractDouble2CharMap
/*     */   implements Double2CharSortedMap
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
/*  51 */       return AbstractDouble2CharSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractDouble2CharSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractDouble2CharSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public DoubleComparator comparator() {
/*  63 */       return AbstractDouble2CharSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public double firstDouble() {
/*  67 */       return AbstractDouble2CharSortedMap.this.firstDoubleKey();
/*     */     }
/*     */     
/*     */     public double lastDouble() {
/*  71 */       return AbstractDouble2CharSortedMap.this.lastDoubleKey();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet headSet(double to) {
/*  75 */       return AbstractDouble2CharSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet tailSet(double from) {
/*  79 */       return AbstractDouble2CharSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet subSet(double from, double to) {
/*  83 */       return AbstractDouble2CharSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public DoubleBidirectionalIterator iterator(double from) {
/*  87 */       return new AbstractDouble2CharSortedMap.KeySetIterator(AbstractDouble2CharSortedMap.this.double2CharEntrySet().iterator(new AbstractDouble2CharMap.BasicEntry(from, false)));
/*     */     }
/*     */     
/*     */     public DoubleBidirectionalIterator iterator() {
/*  91 */       return new AbstractDouble2CharSortedMap.KeySetIterator(Double2CharSortedMaps.fastIterator(AbstractDouble2CharSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements DoubleBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Double2CharMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Double2CharMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public double nextDouble() {
/* 108 */       return ((Double2CharMap.Entry)this.i.next()).getDoubleKey();
/*     */     }
/*     */     
/*     */     public double previousDouble() {
/* 112 */       return ((Double2CharMap.Entry)this.i.previous()).getDoubleKey();
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
/*     */   public CharCollection values() {
/* 138 */     return (CharCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractCharCollection {
/*     */     public CharIterator iterator() {
/* 144 */       return new AbstractDouble2CharSortedMap.ValuesIterator(Double2CharSortedMaps.fastIterator(AbstractDouble2CharSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(char k) {
/* 148 */       return AbstractDouble2CharSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractDouble2CharSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractDouble2CharSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements CharIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Double2CharMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Double2CharMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public char nextChar() {
/* 173 */       return ((Double2CharMap.Entry)this.i.next()).getCharValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\AbstractDouble2CharSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */