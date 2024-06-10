/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
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
/*     */ public abstract class AbstractChar2IntSortedMap
/*     */   extends AbstractChar2IntMap
/*     */   implements Char2IntSortedMap
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
/*  51 */       return AbstractChar2IntSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractChar2IntSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractChar2IntSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public CharComparator comparator() {
/*  63 */       return AbstractChar2IntSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public char firstChar() {
/*  67 */       return AbstractChar2IntSortedMap.this.firstCharKey();
/*     */     }
/*     */     
/*     */     public char lastChar() {
/*  71 */       return AbstractChar2IntSortedMap.this.lastCharKey();
/*     */     }
/*     */     
/*     */     public CharSortedSet headSet(char to) {
/*  75 */       return AbstractChar2IntSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public CharSortedSet tailSet(char from) {
/*  79 */       return AbstractChar2IntSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public CharSortedSet subSet(char from, char to) {
/*  83 */       return AbstractChar2IntSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public CharBidirectionalIterator iterator(char from) {
/*  87 */       return new AbstractChar2IntSortedMap.KeySetIterator(AbstractChar2IntSortedMap.this.char2IntEntrySet().iterator(new AbstractChar2IntMap.BasicEntry(from, 0)));
/*     */     }
/*     */     
/*     */     public CharBidirectionalIterator iterator() {
/*  91 */       return new AbstractChar2IntSortedMap.KeySetIterator(Char2IntSortedMaps.fastIterator(AbstractChar2IntSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements CharBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Char2IntMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Char2IntMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public char nextChar() {
/* 108 */       return ((Char2IntMap.Entry)this.i.next()).getCharKey();
/*     */     }
/*     */     
/*     */     public char previousChar() {
/* 112 */       return ((Char2IntMap.Entry)this.i.previous()).getCharKey();
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
/* 138 */     return (IntCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractIntCollection {
/*     */     public IntIterator iterator() {
/* 144 */       return new AbstractChar2IntSortedMap.ValuesIterator(Char2IntSortedMaps.fastIterator(AbstractChar2IntSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(int k) {
/* 148 */       return AbstractChar2IntSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractChar2IntSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractChar2IntSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements IntIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Char2IntMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Char2IntMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public int nextInt() {
/* 173 */       return ((Char2IntMap.Entry)this.i.next()).getIntValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\AbstractChar2IntSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */