/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
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
/*     */ public abstract class AbstractChar2DoubleSortedMap
/*     */   extends AbstractChar2DoubleMap
/*     */   implements Char2DoubleSortedMap
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public CharSortedSet keySet() {
/*  45 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractCharSortedSet {
/*     */     public boolean contains(char k) {
/*  51 */       return AbstractChar2DoubleSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractChar2DoubleSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractChar2DoubleSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public CharComparator comparator() {
/*  63 */       return AbstractChar2DoubleSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public char firstChar() {
/*  67 */       return AbstractChar2DoubleSortedMap.this.firstCharKey();
/*     */     }
/*     */     
/*     */     public char lastChar() {
/*  71 */       return AbstractChar2DoubleSortedMap.this.lastCharKey();
/*     */     }
/*     */     
/*     */     public CharSortedSet headSet(char to) {
/*  75 */       return AbstractChar2DoubleSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public CharSortedSet tailSet(char from) {
/*  79 */       return AbstractChar2DoubleSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public CharSortedSet subSet(char from, char to) {
/*  83 */       return AbstractChar2DoubleSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public CharBidirectionalIterator iterator(char from) {
/*  87 */       return new AbstractChar2DoubleSortedMap.KeySetIterator(AbstractChar2DoubleSortedMap.this.char2DoubleEntrySet().iterator(new AbstractChar2DoubleMap.BasicEntry(from, 0.0D)));
/*     */     }
/*     */     
/*     */     public CharBidirectionalIterator iterator() {
/*  91 */       return new AbstractChar2DoubleSortedMap.KeySetIterator(Char2DoubleSortedMaps.fastIterator(AbstractChar2DoubleSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements CharBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Char2DoubleMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Char2DoubleMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public char nextChar() {
/* 108 */       return ((Char2DoubleMap.Entry)this.i.next()).getCharKey();
/*     */     }
/*     */     
/*     */     public char previousChar() {
/* 112 */       return ((Char2DoubleMap.Entry)this.i.previous()).getCharKey();
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
/*     */   public DoubleCollection values() {
/* 138 */     return (DoubleCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractDoubleCollection {
/*     */     public DoubleIterator iterator() {
/* 144 */       return new AbstractChar2DoubleSortedMap.ValuesIterator(Char2DoubleSortedMaps.fastIterator(AbstractChar2DoubleSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(double k) {
/* 148 */       return AbstractChar2DoubleSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractChar2DoubleSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractChar2DoubleSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements DoubleIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Char2DoubleMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Char2DoubleMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public double nextDouble() {
/* 173 */       return ((Char2DoubleMap.Entry)this.i.next()).getDoubleValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\AbstractChar2DoubleSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */