/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public abstract class AbstractChar2CharSortedMap
/*     */   extends AbstractChar2CharMap
/*     */   implements Char2CharSortedMap
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
/*  51 */       return AbstractChar2CharSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractChar2CharSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractChar2CharSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public CharComparator comparator() {
/*  63 */       return AbstractChar2CharSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public char firstChar() {
/*  67 */       return AbstractChar2CharSortedMap.this.firstCharKey();
/*     */     }
/*     */     
/*     */     public char lastChar() {
/*  71 */       return AbstractChar2CharSortedMap.this.lastCharKey();
/*     */     }
/*     */     
/*     */     public CharSortedSet headSet(char to) {
/*  75 */       return AbstractChar2CharSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public CharSortedSet tailSet(char from) {
/*  79 */       return AbstractChar2CharSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public CharSortedSet subSet(char from, char to) {
/*  83 */       return AbstractChar2CharSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public CharBidirectionalIterator iterator(char from) {
/*  87 */       return new AbstractChar2CharSortedMap.KeySetIterator(AbstractChar2CharSortedMap.this.char2CharEntrySet().iterator(new AbstractChar2CharMap.BasicEntry(from, false)));
/*     */     }
/*     */     
/*     */     public CharBidirectionalIterator iterator() {
/*  91 */       return new AbstractChar2CharSortedMap.KeySetIterator(Char2CharSortedMaps.fastIterator(AbstractChar2CharSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements CharBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Char2CharMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Char2CharMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public char nextChar() {
/* 108 */       return ((Char2CharMap.Entry)this.i.next()).getCharKey();
/*     */     }
/*     */     
/*     */     public char previousChar() {
/* 112 */       return ((Char2CharMap.Entry)this.i.previous()).getCharKey();
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
/* 138 */     return new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractCharCollection {
/*     */     public CharIterator iterator() {
/* 144 */       return new AbstractChar2CharSortedMap.ValuesIterator(Char2CharSortedMaps.fastIterator(AbstractChar2CharSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(char k) {
/* 148 */       return AbstractChar2CharSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractChar2CharSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractChar2CharSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements CharIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Char2CharMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Char2CharMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public char nextChar() {
/* 173 */       return ((Char2CharMap.Entry)this.i.next()).getCharValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\AbstractChar2CharSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */