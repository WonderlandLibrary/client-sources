/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public abstract class AbstractChar2ShortSortedMap
/*     */   extends AbstractChar2ShortMap
/*     */   implements Char2ShortSortedMap
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
/*  51 */       return AbstractChar2ShortSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractChar2ShortSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractChar2ShortSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public CharComparator comparator() {
/*  63 */       return AbstractChar2ShortSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public char firstChar() {
/*  67 */       return AbstractChar2ShortSortedMap.this.firstCharKey();
/*     */     }
/*     */     
/*     */     public char lastChar() {
/*  71 */       return AbstractChar2ShortSortedMap.this.lastCharKey();
/*     */     }
/*     */     
/*     */     public CharSortedSet headSet(char to) {
/*  75 */       return AbstractChar2ShortSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public CharSortedSet tailSet(char from) {
/*  79 */       return AbstractChar2ShortSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public CharSortedSet subSet(char from, char to) {
/*  83 */       return AbstractChar2ShortSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public CharBidirectionalIterator iterator(char from) {
/*  87 */       return new AbstractChar2ShortSortedMap.KeySetIterator(AbstractChar2ShortSortedMap.this.char2ShortEntrySet().iterator(new AbstractChar2ShortMap.BasicEntry(from, (short)0)));
/*     */     }
/*     */     
/*     */     public CharBidirectionalIterator iterator() {
/*  91 */       return new AbstractChar2ShortSortedMap.KeySetIterator(Char2ShortSortedMaps.fastIterator(AbstractChar2ShortSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements CharBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Char2ShortMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Char2ShortMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public char nextChar() {
/* 108 */       return ((Char2ShortMap.Entry)this.i.next()).getCharKey();
/*     */     }
/*     */     
/*     */     public char previousChar() {
/* 112 */       return ((Char2ShortMap.Entry)this.i.previous()).getCharKey();
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
/* 144 */       return new AbstractChar2ShortSortedMap.ValuesIterator(Char2ShortSortedMaps.fastIterator(AbstractChar2ShortSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(short k) {
/* 148 */       return AbstractChar2ShortSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractChar2ShortSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractChar2ShortSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements ShortIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Char2ShortMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Char2ShortMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public short nextShort() {
/* 173 */       return ((Char2ShortMap.Entry)this.i.next()).getShortValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\AbstractChar2ShortSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */