/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ public abstract class AbstractInt2IntSortedMap
/*     */   extends AbstractInt2IntMap
/*     */   implements Int2IntSortedMap
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
/*  51 */       return AbstractInt2IntSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractInt2IntSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractInt2IntSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public IntComparator comparator() {
/*  63 */       return AbstractInt2IntSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public int firstInt() {
/*  67 */       return AbstractInt2IntSortedMap.this.firstIntKey();
/*     */     }
/*     */     
/*     */     public int lastInt() {
/*  71 */       return AbstractInt2IntSortedMap.this.lastIntKey();
/*     */     }
/*     */     
/*     */     public IntSortedSet headSet(int to) {
/*  75 */       return AbstractInt2IntSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public IntSortedSet tailSet(int from) {
/*  79 */       return AbstractInt2IntSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public IntSortedSet subSet(int from, int to) {
/*  83 */       return AbstractInt2IntSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public IntBidirectionalIterator iterator(int from) {
/*  87 */       return new AbstractInt2IntSortedMap.KeySetIterator(AbstractInt2IntSortedMap.this.int2IntEntrySet().iterator(new AbstractInt2IntMap.BasicEntry(from, 0)));
/*     */     }
/*     */     
/*     */     public IntBidirectionalIterator iterator() {
/*  91 */       return new AbstractInt2IntSortedMap.KeySetIterator(Int2IntSortedMaps.fastIterator(AbstractInt2IntSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements IntBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Int2IntMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Int2IntMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public int nextInt() {
/* 108 */       return ((Int2IntMap.Entry)this.i.next()).getIntKey();
/*     */     }
/*     */     
/*     */     public int previousInt() {
/* 112 */       return ((Int2IntMap.Entry)this.i.previous()).getIntKey();
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
/* 138 */     return new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractIntCollection {
/*     */     public IntIterator iterator() {
/* 144 */       return new AbstractInt2IntSortedMap.ValuesIterator(Int2IntSortedMaps.fastIterator(AbstractInt2IntSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(int k) {
/* 148 */       return AbstractInt2IntSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractInt2IntSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractInt2IntSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements IntIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Int2IntMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Int2IntMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public int nextInt() {
/* 173 */       return ((Int2IntMap.Entry)this.i.next()).getIntValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\AbstractInt2IntSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */