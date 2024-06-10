/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
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
/*     */ public abstract class AbstractShort2ObjectSortedMap<V>
/*     */   extends AbstractShort2ObjectMap<V>
/*     */   implements Short2ObjectSortedMap<V>
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public ShortSortedSet keySet() {
/*  47 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractShortSortedSet {
/*     */     public boolean contains(short k) {
/*  53 */       return AbstractShort2ObjectSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  57 */       return AbstractShort2ObjectSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  61 */       AbstractShort2ObjectSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public ShortComparator comparator() {
/*  65 */       return AbstractShort2ObjectSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public short firstShort() {
/*  69 */       return AbstractShort2ObjectSortedMap.this.firstShortKey();
/*     */     }
/*     */     
/*     */     public short lastShort() {
/*  73 */       return AbstractShort2ObjectSortedMap.this.lastShortKey();
/*     */     }
/*     */     
/*     */     public ShortSortedSet headSet(short to) {
/*  77 */       return AbstractShort2ObjectSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public ShortSortedSet tailSet(short from) {
/*  81 */       return AbstractShort2ObjectSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public ShortSortedSet subSet(short from, short to) {
/*  85 */       return AbstractShort2ObjectSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public ShortBidirectionalIterator iterator(short from) {
/*  89 */       return new AbstractShort2ObjectSortedMap.KeySetIterator(AbstractShort2ObjectSortedMap.this.short2ObjectEntrySet().iterator(new AbstractShort2ObjectMap.BasicEntry(from, null)));
/*     */     }
/*     */     
/*     */     public ShortBidirectionalIterator iterator() {
/*  93 */       return new AbstractShort2ObjectSortedMap.KeySetIterator(Short2ObjectSortedMaps.fastIterator(AbstractShort2ObjectSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<V>
/*     */     implements ShortBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Short2ObjectMap.Entry<V>> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Short2ObjectMap.Entry<V>> i) {
/* 106 */       this.i = i;
/*     */     }
/*     */     
/*     */     public short nextShort() {
/* 110 */       return ((Short2ObjectMap.Entry)this.i.next()).getShortKey();
/*     */     }
/*     */     
/*     */     public short previousShort() {
/* 114 */       return ((Short2ObjectMap.Entry)this.i.previous()).getShortKey();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 118 */       return this.i.hasNext();
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 122 */       return this.i.hasPrevious();
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
/*     */   public ObjectCollection<V> values() {
/* 140 */     return (ObjectCollection<V>)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractObjectCollection<V> {
/*     */     public ObjectIterator<V> iterator() {
/* 146 */       return new AbstractShort2ObjectSortedMap.ValuesIterator<>(Short2ObjectSortedMaps.fastIterator(AbstractShort2ObjectSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(Object k) {
/* 150 */       return AbstractShort2ObjectSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 154 */       return AbstractShort2ObjectSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 158 */       AbstractShort2ObjectSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<V>
/*     */     implements ObjectIterator<V>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Short2ObjectMap.Entry<V>> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Short2ObjectMap.Entry<V>> i) {
/* 171 */       this.i = i;
/*     */     }
/*     */     
/*     */     public V next() {
/* 175 */       return ((Short2ObjectMap.Entry<V>)this.i.next()).getValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 179 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\AbstractShort2ObjectSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */