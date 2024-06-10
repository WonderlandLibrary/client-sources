/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
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
/*     */ public abstract class AbstractChar2ByteSortedMap
/*     */   extends AbstractChar2ByteMap
/*     */   implements Char2ByteSortedMap
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
/*  51 */       return AbstractChar2ByteSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractChar2ByteSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractChar2ByteSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public CharComparator comparator() {
/*  63 */       return AbstractChar2ByteSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public char firstChar() {
/*  67 */       return AbstractChar2ByteSortedMap.this.firstCharKey();
/*     */     }
/*     */     
/*     */     public char lastChar() {
/*  71 */       return AbstractChar2ByteSortedMap.this.lastCharKey();
/*     */     }
/*     */     
/*     */     public CharSortedSet headSet(char to) {
/*  75 */       return AbstractChar2ByteSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public CharSortedSet tailSet(char from) {
/*  79 */       return AbstractChar2ByteSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public CharSortedSet subSet(char from, char to) {
/*  83 */       return AbstractChar2ByteSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public CharBidirectionalIterator iterator(char from) {
/*  87 */       return new AbstractChar2ByteSortedMap.KeySetIterator(AbstractChar2ByteSortedMap.this.char2ByteEntrySet().iterator(new AbstractChar2ByteMap.BasicEntry(from, (byte)0)));
/*     */     }
/*     */     
/*     */     public CharBidirectionalIterator iterator() {
/*  91 */       return new AbstractChar2ByteSortedMap.KeySetIterator(Char2ByteSortedMaps.fastIterator(AbstractChar2ByteSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements CharBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Char2ByteMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Char2ByteMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public char nextChar() {
/* 108 */       return ((Char2ByteMap.Entry)this.i.next()).getCharKey();
/*     */     }
/*     */     
/*     */     public char previousChar() {
/* 112 */       return ((Char2ByteMap.Entry)this.i.previous()).getCharKey();
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
/*     */   public ByteCollection values() {
/* 138 */     return (ByteCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractByteCollection {
/*     */     public ByteIterator iterator() {
/* 144 */       return new AbstractChar2ByteSortedMap.ValuesIterator(Char2ByteSortedMaps.fastIterator(AbstractChar2ByteSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(byte k) {
/* 148 */       return AbstractChar2ByteSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractChar2ByteSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractChar2ByteSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements ByteIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Char2ByteMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Char2ByteMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public byte nextByte() {
/* 173 */       return ((Char2ByteMap.Entry)this.i.next()).getByteValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\AbstractChar2ByteSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */