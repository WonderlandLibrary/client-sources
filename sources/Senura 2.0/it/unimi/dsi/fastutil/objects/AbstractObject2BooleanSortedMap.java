/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
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
/*     */ public abstract class AbstractObject2BooleanSortedMap<K>
/*     */   extends AbstractObject2BooleanMap<K>
/*     */   implements Object2BooleanSortedMap<K>
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public ObjectSortedSet<K> keySet() {
/*  48 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractObjectSortedSet<K> {
/*     */     public boolean contains(Object k) {
/*  54 */       return AbstractObject2BooleanSortedMap.this.containsKey(k);
/*     */     }
/*     */     
/*     */     public int size() {
/*  58 */       return AbstractObject2BooleanSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/*  62 */       AbstractObject2BooleanSortedMap.this.clear();
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/*  66 */       return AbstractObject2BooleanSortedMap.this.comparator();
/*     */     }
/*     */     
/*     */     public K first() {
/*  70 */       return AbstractObject2BooleanSortedMap.this.firstKey();
/*     */     }
/*     */     
/*     */     public K last() {
/*  74 */       return AbstractObject2BooleanSortedMap.this.lastKey();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> headSet(K to) {
/*  78 */       return AbstractObject2BooleanSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> tailSet(K from) {
/*  82 */       return AbstractObject2BooleanSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> subSet(K from, K to) {
/*  86 */       return AbstractObject2BooleanSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator(K from) {
/*  90 */       return new AbstractObject2BooleanSortedMap.KeySetIterator<>(AbstractObject2BooleanSortedMap.this.object2BooleanEntrySet().iterator(new AbstractObject2BooleanMap.BasicEntry<>(from, false)));
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator() {
/*  94 */       return new AbstractObject2BooleanSortedMap.KeySetIterator<>(Object2BooleanSortedMaps.fastIterator(AbstractObject2BooleanSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<K>
/*     */     implements ObjectBidirectionalIterator<K>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> i;
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> i) {
/* 107 */       this.i = i;
/*     */     }
/*     */     
/*     */     public K next() {
/* 111 */       return ((Object2BooleanMap.Entry<K>)this.i.next()).getKey();
/*     */     }
/*     */     
/*     */     public K previous() {
/* 115 */       return ((Object2BooleanMap.Entry<K>)this.i.previous()).getKey();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 119 */       return this.i.hasNext();
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 123 */       return this.i.hasPrevious();
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
/* 141 */     return (BooleanCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractBooleanCollection {
/*     */     public BooleanIterator iterator() {
/* 147 */       return new AbstractObject2BooleanSortedMap.ValuesIterator(Object2BooleanSortedMaps.fastIterator(AbstractObject2BooleanSortedMap.this));
/*     */     }
/*     */     
/*     */     public boolean contains(boolean k) {
/* 151 */       return AbstractObject2BooleanSortedMap.this.containsValue(k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 155 */       return AbstractObject2BooleanSortedMap.this.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 159 */       AbstractObject2BooleanSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<K>
/*     */     implements BooleanIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> i;
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> i) {
/* 172 */       this.i = i;
/*     */     }
/*     */     
/*     */     public boolean nextBoolean() {
/* 176 */       return ((Object2BooleanMap.Entry)this.i.next()).getBooleanValue();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 180 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractObject2BooleanSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */