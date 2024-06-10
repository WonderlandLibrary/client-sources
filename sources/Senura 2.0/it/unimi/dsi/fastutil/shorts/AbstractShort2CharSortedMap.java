/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public abstract class AbstractShort2CharSortedMap
/*     */   extends AbstractShort2CharMap
/*     */   implements Short2CharSortedMap
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public ShortSortedSet keySet() {
/*  45 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractShortSortedSet {
/*     */     public boolean contains(short k) {
/*  51 */       return AbstractShort2CharSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  55 */       return AbstractShort2CharSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  59 */       AbstractShort2CharSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public ShortComparator comparator() {
/*  63 */       return AbstractShort2CharSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public short firstShort() {
/*  67 */       return AbstractShort2CharSortedMap.this.firstShortKey();
/*     */     }
/*     */     
/*     */     public short lastShort() {
/*  71 */       return AbstractShort2CharSortedMap.this.lastShortKey();
/*     */     }
/*     */     
/*     */     public ShortSortedSet headSet(short to) {
/*  75 */       return AbstractShort2CharSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public ShortSortedSet tailSet(short from) {
/*  79 */       return AbstractShort2CharSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public ShortSortedSet subSet(short from, short to) {
/*  83 */       return AbstractShort2CharSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public ShortBidirectionalIterator iterator(short from) {
/*  87 */       return new AbstractShort2CharSortedMap.KeySetIterator(AbstractShort2CharSortedMap.this.short2CharEntrySet().iterator(new AbstractShort2CharMap.BasicEntry(from, false)));
/*     */     }
/*     */     
/*     */     public ShortBidirectionalIterator iterator() {
/*  91 */       return new AbstractShort2CharSortedMap.KeySetIterator(Short2CharSortedMaps.fastIterator(AbstractShort2CharSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements ShortBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Short2CharMap.Entry> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Short2CharMap.Entry> i) {
/* 104 */       this.i = i;
/*     */     }
/*     */     
/*     */     public short nextShort() {
/* 108 */       return ((Short2CharMap.Entry)this.i.next()).getShortKey();
/*     */     }
/*     */     
/*     */     public short previousShort() {
/* 112 */       return ((Short2CharMap.Entry)this.i.previous()).getShortKey();
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
/* 144 */       return new AbstractShort2CharSortedMap.ValuesIterator(Short2CharSortedMaps.fastIterator(AbstractShort2CharSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(char k) {
/* 148 */       return AbstractShort2CharSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 152 */       return AbstractShort2CharSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 156 */       AbstractShort2CharSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements CharIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Short2CharMap.Entry> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Short2CharMap.Entry> i) {
/* 169 */       this.i = i;
/*     */     }
/*     */     
/*     */     public char nextChar() {
/* 173 */       return ((Short2CharMap.Entry)this.i.next()).getCharValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\AbstractShort2CharSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */