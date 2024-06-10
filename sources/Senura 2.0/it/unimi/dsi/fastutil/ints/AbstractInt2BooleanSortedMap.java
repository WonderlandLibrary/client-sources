/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ public abstract class AbstractInt2BooleanSortedMap
/*     */   extends AbstractInt2BooleanMap
/*     */   implements Int2BooleanSortedMap
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public IntSortedSet keySet() {
/*  45 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractIntSortedSet {
/*     */     public boolean contains(int k) {
/*  51 */       return AbstractInt2BooleanSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractInt2BooleanSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractInt2BooleanSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public IntComparator comparator() {
/*  63 */       return AbstractInt2BooleanSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public int firstInt() {
/*  67 */       return AbstractInt2BooleanSortedMap.this.firstIntKey();
/*     */     }
/*     */     
/*     */     public int lastInt() {
/*  71 */       return AbstractInt2BooleanSortedMap.this.lastIntKey();
/*     */     }
/*     */     
/*     */     public IntSortedSet headSet(int to) {
/*  75 */       return AbstractInt2BooleanSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public IntSortedSet tailSet(int from) {
/*  79 */       return AbstractInt2BooleanSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public IntSortedSet subSet(int from, int to) {
/*  83 */       return AbstractInt2BooleanSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public IntBidirectionalIterator iterator(int from) {
/*  87 */       return new AbstractInt2BooleanSortedMap.KeySetIterator(AbstractInt2BooleanSortedMap.this.int2BooleanEntrySet().iterator(new AbstractInt2BooleanMap.BasicEntry(from, false)));
/*     */     }
/*     */     
/*     */     public IntBidirectionalIterator iterator() {
/*  91 */       return new AbstractInt2BooleanSortedMap.KeySetIterator(Int2BooleanSortedMaps.fastIterator(AbstractInt2BooleanSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements IntBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Int2BooleanMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Int2BooleanMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public int nextInt() {
/* 108 */       return ((Int2BooleanMap.Entry)this.i.next()).getIntKey();
/*     */     }
/*     */     
/*     */     public int previousInt() {
/* 112 */       return ((Int2BooleanMap.Entry)this.i.previous()).getIntKey();
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
/*     */   public BooleanCollection values() {
/* 138 */     return (BooleanCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractBooleanCollection {
/*     */     public BooleanIterator iterator() {
/* 144 */       return new AbstractInt2BooleanSortedMap.ValuesIterator(Int2BooleanSortedMaps.fastIterator(AbstractInt2BooleanSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(boolean k) {
/* 148 */       return AbstractInt2BooleanSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractInt2BooleanSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractInt2BooleanSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements BooleanIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Int2BooleanMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Int2BooleanMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public boolean nextBoolean() {
/* 173 */       return ((Int2BooleanMap.Entry)this.i.next()).getBooleanValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\AbstractInt2BooleanSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */