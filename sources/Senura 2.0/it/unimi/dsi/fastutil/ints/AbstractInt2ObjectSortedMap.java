/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ public abstract class AbstractInt2ObjectSortedMap<V>
/*     */   extends AbstractInt2ObjectMap<V>
/*     */   implements Int2ObjectSortedMap<V>
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public IntSortedSet keySet() {
/*  47 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractIntSortedSet {
/*     */     public boolean contains(int k) {
/*  53 */       return AbstractInt2ObjectSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  57 */       return AbstractInt2ObjectSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  61 */       AbstractInt2ObjectSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public IntComparator comparator() {
/*  65 */       return AbstractInt2ObjectSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public int firstInt() {
/*  69 */       return AbstractInt2ObjectSortedMap.this.firstIntKey();
/*     */     }
/*     */     
/*     */     public int lastInt() {
/*  73 */       return AbstractInt2ObjectSortedMap.this.lastIntKey();
/*     */     }
/*     */     
/*     */     public IntSortedSet headSet(int to) {
/*  77 */       return AbstractInt2ObjectSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public IntSortedSet tailSet(int from) {
/*  81 */       return AbstractInt2ObjectSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public IntSortedSet subSet(int from, int to) {
/*  85 */       return AbstractInt2ObjectSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public IntBidirectionalIterator iterator(int from) {
/*  89 */       return new AbstractInt2ObjectSortedMap.KeySetIterator(AbstractInt2ObjectSortedMap.this.int2ObjectEntrySet().iterator(new AbstractInt2ObjectMap.BasicEntry(from, null)));
/*     */     }
/*     */     
/*     */     public IntBidirectionalIterator iterator() {
/*  93 */       return new AbstractInt2ObjectSortedMap.KeySetIterator(Int2ObjectSortedMaps.fastIterator(AbstractInt2ObjectSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<V>
/*     */     implements IntBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> i) {
/* 106 */       this.i = i;
/*     */     }
/*     */     
/*     */     public int nextInt() {
/* 110 */       return ((Int2ObjectMap.Entry)this.i.next()).getIntKey();
/*     */     }
/*     */     
/*     */     public int previousInt() {
/* 114 */       return ((Int2ObjectMap.Entry)this.i.previous()).getIntKey();
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
/* 146 */       return new AbstractInt2ObjectSortedMap.ValuesIterator<>(Int2ObjectSortedMaps.fastIterator(AbstractInt2ObjectSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(Object k) {
/* 150 */       return AbstractInt2ObjectSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 154 */       return AbstractInt2ObjectSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 158 */       AbstractInt2ObjectSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<V>
/*     */     implements ObjectIterator<V>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> i) {
/* 171 */       this.i = i;
/*     */     }
/*     */     
/*     */     public V next() {
/* 175 */       return ((Int2ObjectMap.Entry<V>)this.i.next()).getValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 179 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\AbstractInt2ObjectSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */