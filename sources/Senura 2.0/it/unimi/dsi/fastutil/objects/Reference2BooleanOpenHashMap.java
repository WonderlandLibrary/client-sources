/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Predicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Reference2BooleanOpenHashMap<K>
/*      */   extends AbstractReference2BooleanMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Reference2BooleanMap.FastEntrySet<K> entries;
/*      */   protected transient ReferenceSet<K> keys;
/*      */   protected transient BooleanCollection values;
/*      */   
/*      */   public Reference2BooleanOpenHashMap(int expected, float f) {
/*  101 */     if (f <= 0.0F || f > 1.0F)
/*  102 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  103 */     if (expected < 0)
/*  104 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  105 */     this.f = f;
/*  106 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  107 */     this.mask = this.n - 1;
/*  108 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  109 */     this.key = (K[])new Object[this.n + 1];
/*  110 */     this.value = new boolean[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanOpenHashMap(int expected) {
/*  119 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanOpenHashMap() {
/*  127 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanOpenHashMap(Map<? extends K, ? extends Boolean> m, float f) {
/*  138 */     this(m.size(), f);
/*  139 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanOpenHashMap(Map<? extends K, ? extends Boolean> m) {
/*  149 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanOpenHashMap(Reference2BooleanMap<K> m, float f) {
/*  160 */     this(m.size(), f);
/*  161 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanOpenHashMap(Reference2BooleanMap<K> m) {
/*  171 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanOpenHashMap(K[] k, boolean[] v, float f) {
/*  186 */     this(k.length, f);
/*  187 */     if (k.length != v.length) {
/*  188 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  190 */     for (int i = 0; i < k.length; i++) {
/*  191 */       put(k[i], v[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanOpenHashMap(K[] k, boolean[] v) {
/*  205 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  208 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  211 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  212 */     if (needed > this.n)
/*  213 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  216 */     int needed = (int)Math.min(1073741824L, 
/*  217 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  218 */     if (needed > this.n)
/*  219 */       rehash(needed); 
/*      */   }
/*      */   private boolean removeEntry(int pos) {
/*  222 */     boolean oldValue = this.value[pos];
/*  223 */     this.size--;
/*  224 */     shiftKeys(pos);
/*  225 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  226 */       rehash(this.n / 2); 
/*  227 */     return oldValue;
/*      */   }
/*      */   private boolean removeNullEntry() {
/*  230 */     this.containsNullKey = false;
/*  231 */     this.key[this.n] = null;
/*  232 */     boolean oldValue = this.value[this.n];
/*  233 */     this.size--;
/*  234 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  235 */       rehash(this.n / 2); 
/*  236 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Boolean> m) {
/*  240 */     if (this.f <= 0.5D) {
/*  241 */       ensureCapacity(m.size());
/*      */     } else {
/*  243 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  245 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(K k) {
/*  249 */     if (k == null) {
/*  250 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  252 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  255 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  256 */       return -(pos + 1); 
/*  257 */     if (k == curr) {
/*  258 */       return pos;
/*      */     }
/*      */     while (true) {
/*  261 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  262 */         return -(pos + 1); 
/*  263 */       if (k == curr)
/*  264 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, K k, boolean v) {
/*  268 */     if (pos == this.n)
/*  269 */       this.containsNullKey = true; 
/*  270 */     this.key[pos] = k;
/*  271 */     this.value[pos] = v;
/*  272 */     if (this.size++ >= this.maxFill) {
/*  273 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean put(K k, boolean v) {
/*  279 */     int pos = find(k);
/*  280 */     if (pos < 0) {
/*  281 */       insert(-pos - 1, k, v);
/*  282 */       return this.defRetValue;
/*      */     } 
/*  284 */     boolean oldValue = this.value[pos];
/*  285 */     this.value[pos] = v;
/*  286 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void shiftKeys(int pos) {
/*  299 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  301 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  303 */         if ((curr = key[pos]) == null) {
/*  304 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  307 */         int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
/*  308 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  310 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  312 */       key[last] = curr;
/*  313 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean removeBoolean(Object k) {
/*  319 */     if (k == null) {
/*  320 */       if (this.containsNullKey)
/*  321 */         return removeNullEntry(); 
/*  322 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  325 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  328 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  329 */       return this.defRetValue; 
/*  330 */     if (k == curr)
/*  331 */       return removeEntry(pos); 
/*      */     while (true) {
/*  333 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  334 */         return this.defRetValue; 
/*  335 */       if (k == curr) {
/*  336 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getBoolean(Object k) {
/*  342 */     if (k == null) {
/*  343 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  345 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  348 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  349 */       return this.defRetValue; 
/*  350 */     if (k == curr) {
/*  351 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  354 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  355 */         return this.defRetValue; 
/*  356 */       if (k == curr) {
/*  357 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  363 */     if (k == null) {
/*  364 */       return this.containsNullKey;
/*      */     }
/*  366 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  369 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  370 */       return false; 
/*  371 */     if (k == curr) {
/*  372 */       return true;
/*      */     }
/*      */     while (true) {
/*  375 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  376 */         return false; 
/*  377 */       if (k == curr)
/*  378 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  383 */     boolean[] value = this.value;
/*  384 */     K[] key = this.key;
/*  385 */     if (this.containsNullKey && value[this.n] == v)
/*  386 */       return true; 
/*  387 */     for (int i = this.n; i-- != 0;) {
/*  388 */       if (key[i] != null && value[i] == v)
/*  389 */         return true; 
/*  390 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(Object k, boolean defaultValue) {
/*  396 */     if (k == null) {
/*  397 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  399 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  402 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  403 */       return defaultValue; 
/*  404 */     if (k == curr) {
/*  405 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  408 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  409 */         return defaultValue; 
/*  410 */       if (k == curr) {
/*  411 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean putIfAbsent(K k, boolean v) {
/*  417 */     int pos = find(k);
/*  418 */     if (pos >= 0)
/*  419 */       return this.value[pos]; 
/*  420 */     insert(-pos - 1, k, v);
/*  421 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, boolean v) {
/*  427 */     if (k == null) {
/*  428 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  429 */         removeNullEntry();
/*  430 */         return true;
/*      */       } 
/*  432 */       return false;
/*      */     } 
/*      */     
/*  435 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  438 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  439 */       return false; 
/*  440 */     if (k == curr && v == this.value[pos]) {
/*  441 */       removeEntry(pos);
/*  442 */       return true;
/*      */     } 
/*      */     while (true) {
/*  445 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  446 */         return false; 
/*  447 */       if (k == curr && v == this.value[pos]) {
/*  448 */         removeEntry(pos);
/*  449 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, boolean oldValue, boolean v) {
/*  456 */     int pos = find(k);
/*  457 */     if (pos < 0 || oldValue != this.value[pos])
/*  458 */       return false; 
/*  459 */     this.value[pos] = v;
/*  460 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, boolean v) {
/*  465 */     int pos = find(k);
/*  466 */     if (pos < 0)
/*  467 */       return this.defRetValue; 
/*  468 */     boolean oldValue = this.value[pos];
/*  469 */     this.value[pos] = v;
/*  470 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean computeBooleanIfAbsent(K k, Predicate<? super K> mappingFunction) {
/*  475 */     Objects.requireNonNull(mappingFunction);
/*  476 */     int pos = find(k);
/*  477 */     if (pos >= 0)
/*  478 */       return this.value[pos]; 
/*  479 */     boolean newValue = mappingFunction.test(k);
/*  480 */     insert(-pos - 1, k, newValue);
/*  481 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeBooleanIfPresent(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  487 */     Objects.requireNonNull(remappingFunction);
/*  488 */     int pos = find(k);
/*  489 */     if (pos < 0)
/*  490 */       return this.defRetValue; 
/*  491 */     Boolean newValue = remappingFunction.apply(k, Boolean.valueOf(this.value[pos]));
/*  492 */     if (newValue == null) {
/*  493 */       if (k == null) {
/*  494 */         removeNullEntry();
/*      */       } else {
/*  496 */         removeEntry(pos);
/*  497 */       }  return this.defRetValue;
/*      */     } 
/*  499 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeBoolean(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  505 */     Objects.requireNonNull(remappingFunction);
/*  506 */     int pos = find(k);
/*  507 */     Boolean newValue = remappingFunction.apply(k, (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  508 */     if (newValue == null) {
/*  509 */       if (pos >= 0)
/*  510 */         if (k == null) {
/*  511 */           removeNullEntry();
/*      */         } else {
/*  513 */           removeEntry(pos);
/*      */         }  
/*  515 */       return this.defRetValue;
/*      */     } 
/*  517 */     boolean newVal = newValue.booleanValue();
/*  518 */     if (pos < 0) {
/*  519 */       insert(-pos - 1, k, newVal);
/*  520 */       return newVal;
/*      */     } 
/*  522 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean mergeBoolean(K k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  528 */     Objects.requireNonNull(remappingFunction);
/*  529 */     int pos = find(k);
/*  530 */     if (pos < 0) {
/*  531 */       insert(-pos - 1, k, v);
/*  532 */       return v;
/*      */     } 
/*  534 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  535 */     if (newValue == null) {
/*  536 */       if (k == null) {
/*  537 */         removeNullEntry();
/*      */       } else {
/*  539 */         removeEntry(pos);
/*  540 */       }  return this.defRetValue;
/*      */     } 
/*  542 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  553 */     if (this.size == 0)
/*      */       return; 
/*  555 */     this.size = 0;
/*  556 */     this.containsNullKey = false;
/*  557 */     Arrays.fill((Object[])this.key, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  561 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  565 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Reference2BooleanMap.Entry<K>, Map.Entry<K, Boolean>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  577 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  583 */       return Reference2BooleanOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public boolean getBooleanValue() {
/*  587 */       return Reference2BooleanOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  591 */       boolean oldValue = Reference2BooleanOpenHashMap.this.value[this.index];
/*  592 */       Reference2BooleanOpenHashMap.this.value[this.index] = v;
/*  593 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean getValue() {
/*  603 */       return Boolean.valueOf(Reference2BooleanOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean setValue(Boolean v) {
/*  613 */       return Boolean.valueOf(setValue(v.booleanValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  618 */       if (!(o instanceof Map.Entry))
/*  619 */         return false; 
/*  620 */       Map.Entry<K, Boolean> e = (Map.Entry<K, Boolean>)o;
/*  621 */       return (Reference2BooleanOpenHashMap.this.key[this.index] == e.getKey() && Reference2BooleanOpenHashMap.this.value[this.index] == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  625 */       return System.identityHashCode(Reference2BooleanOpenHashMap.this.key[this.index]) ^ (Reference2BooleanOpenHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  629 */       return (new StringBuilder()).append(Reference2BooleanOpenHashMap.this.key[this.index]).append("=>").append(Reference2BooleanOpenHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  639 */     int pos = Reference2BooleanOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  646 */     int last = -1;
/*      */     
/*  648 */     int c = Reference2BooleanOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  652 */     boolean mustReturnNullKey = Reference2BooleanOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ReferenceArrayList<K> wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  659 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  662 */       if (!hasNext())
/*  663 */         throw new NoSuchElementException(); 
/*  664 */       this.c--;
/*  665 */       if (this.mustReturnNullKey) {
/*  666 */         this.mustReturnNullKey = false;
/*  667 */         return this.last = Reference2BooleanOpenHashMap.this.n;
/*      */       } 
/*  669 */       K[] key = Reference2BooleanOpenHashMap.this.key;
/*      */       while (true) {
/*  671 */         if (--this.pos < 0) {
/*      */           
/*  673 */           this.last = Integer.MIN_VALUE;
/*  674 */           K k = this.wrapped.get(-this.pos - 1);
/*  675 */           int p = HashCommon.mix(System.identityHashCode(k)) & Reference2BooleanOpenHashMap.this.mask;
/*  676 */           while (k != key[p])
/*  677 */             p = p + 1 & Reference2BooleanOpenHashMap.this.mask; 
/*  678 */           return p;
/*      */         } 
/*  680 */         if (key[this.pos] != null) {
/*  681 */           return this.last = this.pos;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void shiftKeys(int pos) {
/*  695 */       K[] key = Reference2BooleanOpenHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  697 */         pos = (last = pos) + 1 & Reference2BooleanOpenHashMap.this.mask;
/*      */         while (true) {
/*  699 */           if ((curr = key[pos]) == null) {
/*  700 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  703 */           int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2BooleanOpenHashMap.this.mask;
/*  704 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  706 */           pos = pos + 1 & Reference2BooleanOpenHashMap.this.mask;
/*      */         } 
/*  708 */         if (pos < last) {
/*  709 */           if (this.wrapped == null)
/*  710 */             this.wrapped = new ReferenceArrayList<>(2); 
/*  711 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  713 */         key[last] = curr;
/*  714 */         Reference2BooleanOpenHashMap.this.value[last] = Reference2BooleanOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  718 */       if (this.last == -1)
/*  719 */         throw new IllegalStateException(); 
/*  720 */       if (this.last == Reference2BooleanOpenHashMap.this.n) {
/*  721 */         Reference2BooleanOpenHashMap.this.containsNullKey = false;
/*  722 */         Reference2BooleanOpenHashMap.this.key[Reference2BooleanOpenHashMap.this.n] = null;
/*  723 */       } else if (this.pos >= 0) {
/*  724 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  727 */         Reference2BooleanOpenHashMap.this.removeBoolean(this.wrapped.set(-this.pos - 1, null));
/*  728 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  731 */       Reference2BooleanOpenHashMap.this.size--;
/*  732 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  737 */       int i = n;
/*  738 */       while (i-- != 0 && hasNext())
/*  739 */         nextEntry(); 
/*  740 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Reference2BooleanMap.Entry<K>> { private Reference2BooleanOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public Reference2BooleanOpenHashMap<K>.MapEntry next() {
/*  747 */       return this.entry = new Reference2BooleanOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  751 */       super.remove();
/*  752 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Reference2BooleanMap.Entry<K>> { private FastEntryIterator() {
/*  756 */       this.entry = new Reference2BooleanOpenHashMap.MapEntry();
/*      */     } private final Reference2BooleanOpenHashMap<K>.MapEntry entry;
/*      */     public Reference2BooleanOpenHashMap<K>.MapEntry next() {
/*  759 */       this.entry.index = nextEntry();
/*  760 */       return this.entry;
/*      */     } }
/*      */ 
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Reference2BooleanMap.Entry<K>> implements Reference2BooleanMap.FastEntrySet<K> {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Reference2BooleanMap.Entry<K>> iterator() {
/*  768 */       return new Reference2BooleanOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Reference2BooleanMap.Entry<K>> fastIterator() {
/*  772 */       return new Reference2BooleanOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  777 */       if (!(o instanceof Map.Entry))
/*  778 */         return false; 
/*  779 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  780 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  781 */         return false; 
/*  782 */       K k = (K)e.getKey();
/*  783 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  784 */       if (k == null) {
/*  785 */         return (Reference2BooleanOpenHashMap.this.containsNullKey && Reference2BooleanOpenHashMap.this.value[Reference2BooleanOpenHashMap.this.n] == v);
/*      */       }
/*  787 */       K[] key = Reference2BooleanOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  790 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2BooleanOpenHashMap.this.mask]) == null)
/*      */       {
/*  792 */         return false; } 
/*  793 */       if (k == curr) {
/*  794 */         return (Reference2BooleanOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  797 */         if ((curr = key[pos = pos + 1 & Reference2BooleanOpenHashMap.this.mask]) == null)
/*  798 */           return false; 
/*  799 */         if (k == curr) {
/*  800 */           return (Reference2BooleanOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  806 */       if (!(o instanceof Map.Entry))
/*  807 */         return false; 
/*  808 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  809 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  810 */         return false; 
/*  811 */       K k = (K)e.getKey();
/*  812 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  813 */       if (k == null) {
/*  814 */         if (Reference2BooleanOpenHashMap.this.containsNullKey && Reference2BooleanOpenHashMap.this.value[Reference2BooleanOpenHashMap.this.n] == v) {
/*  815 */           Reference2BooleanOpenHashMap.this.removeNullEntry();
/*  816 */           return true;
/*      */         } 
/*  818 */         return false;
/*      */       } 
/*      */       
/*  821 */       K[] key = Reference2BooleanOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  824 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2BooleanOpenHashMap.this.mask]) == null)
/*      */       {
/*  826 */         return false; } 
/*  827 */       if (curr == k) {
/*  828 */         if (Reference2BooleanOpenHashMap.this.value[pos] == v) {
/*  829 */           Reference2BooleanOpenHashMap.this.removeEntry(pos);
/*  830 */           return true;
/*      */         } 
/*  832 */         return false;
/*      */       } 
/*      */       while (true) {
/*  835 */         if ((curr = key[pos = pos + 1 & Reference2BooleanOpenHashMap.this.mask]) == null)
/*  836 */           return false; 
/*  837 */         if (curr == k && 
/*  838 */           Reference2BooleanOpenHashMap.this.value[pos] == v) {
/*  839 */           Reference2BooleanOpenHashMap.this.removeEntry(pos);
/*  840 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  847 */       return Reference2BooleanOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  851 */       Reference2BooleanOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2BooleanMap.Entry<K>> consumer) {
/*  856 */       if (Reference2BooleanOpenHashMap.this.containsNullKey)
/*  857 */         consumer.accept(new AbstractReference2BooleanMap.BasicEntry<>(Reference2BooleanOpenHashMap.this.key[Reference2BooleanOpenHashMap.this.n], Reference2BooleanOpenHashMap.this.value[Reference2BooleanOpenHashMap.this.n])); 
/*  858 */       for (int pos = Reference2BooleanOpenHashMap.this.n; pos-- != 0;) {
/*  859 */         if (Reference2BooleanOpenHashMap.this.key[pos] != null)
/*  860 */           consumer.accept(new AbstractReference2BooleanMap.BasicEntry<>(Reference2BooleanOpenHashMap.this.key[pos], Reference2BooleanOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2BooleanMap.Entry<K>> consumer) {
/*  865 */       AbstractReference2BooleanMap.BasicEntry<K> entry = new AbstractReference2BooleanMap.BasicEntry<>();
/*  866 */       if (Reference2BooleanOpenHashMap.this.containsNullKey) {
/*  867 */         entry.key = Reference2BooleanOpenHashMap.this.key[Reference2BooleanOpenHashMap.this.n];
/*  868 */         entry.value = Reference2BooleanOpenHashMap.this.value[Reference2BooleanOpenHashMap.this.n];
/*  869 */         consumer.accept(entry);
/*      */       } 
/*  871 */       for (int pos = Reference2BooleanOpenHashMap.this.n; pos-- != 0;) {
/*  872 */         if (Reference2BooleanOpenHashMap.this.key[pos] != null) {
/*  873 */           entry.key = Reference2BooleanOpenHashMap.this.key[pos];
/*  874 */           entry.value = Reference2BooleanOpenHashMap.this.value[pos];
/*  875 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Reference2BooleanMap.FastEntrySet<K> reference2BooleanEntrySet() {
/*  881 */     if (this.entries == null)
/*  882 */       this.entries = new MapEntrySet(); 
/*  883 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends MapIterator
/*      */     implements ObjectIterator<K>
/*      */   {
/*      */     public K next() {
/*  900 */       return Reference2BooleanOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/*  906 */       return new Reference2BooleanOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/*  911 */       if (Reference2BooleanOpenHashMap.this.containsNullKey)
/*  912 */         consumer.accept(Reference2BooleanOpenHashMap.this.key[Reference2BooleanOpenHashMap.this.n]); 
/*  913 */       for (int pos = Reference2BooleanOpenHashMap.this.n; pos-- != 0; ) {
/*  914 */         K k = Reference2BooleanOpenHashMap.this.key[pos];
/*  915 */         if (k != null)
/*  916 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  921 */       return Reference2BooleanOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/*  925 */       return Reference2BooleanOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/*  929 */       int oldSize = Reference2BooleanOpenHashMap.this.size;
/*  930 */       Reference2BooleanOpenHashMap.this.removeBoolean(k);
/*  931 */       return (Reference2BooleanOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  935 */       Reference2BooleanOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ReferenceSet<K> keySet() {
/*  940 */     if (this.keys == null)
/*  941 */       this.keys = new KeySet(); 
/*  942 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends MapIterator
/*      */     implements BooleanIterator
/*      */   {
/*      */     public boolean nextBoolean() {
/*  959 */       return Reference2BooleanOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public BooleanCollection values() {
/*  964 */     if (this.values == null)
/*  965 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/*  968 */             return new Reference2BooleanOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/*  972 */             return Reference2BooleanOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(boolean v) {
/*  976 */             return Reference2BooleanOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/*  980 */             Reference2BooleanOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(BooleanConsumer consumer)
/*      */           {
/*  985 */             if (Reference2BooleanOpenHashMap.this.containsNullKey)
/*  986 */               consumer.accept(Reference2BooleanOpenHashMap.this.value[Reference2BooleanOpenHashMap.this.n]); 
/*  987 */             for (int pos = Reference2BooleanOpenHashMap.this.n; pos-- != 0;) {
/*  988 */               if (Reference2BooleanOpenHashMap.this.key[pos] != null)
/*  989 */                 consumer.accept(Reference2BooleanOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/*  992 */     return this.values;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean trim() {
/* 1009 */     return trim(this.size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean trim(int n) {
/* 1033 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1034 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1035 */       return true; 
/*      */     try {
/* 1037 */       rehash(l);
/* 1038 */     } catch (OutOfMemoryError cantDoIt) {
/* 1039 */       return false;
/*      */     } 
/* 1041 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void rehash(int newN) {
/* 1057 */     K[] key = this.key;
/* 1058 */     boolean[] value = this.value;
/* 1059 */     int mask = newN - 1;
/* 1060 */     K[] newKey = (K[])new Object[newN + 1];
/* 1061 */     boolean[] newValue = new boolean[newN + 1];
/* 1062 */     int i = this.n;
/* 1063 */     for (int j = realSize(); j-- != 0; ) {
/* 1064 */       while (key[--i] == null); int pos;
/* 1065 */       if (newKey[pos = HashCommon.mix(System.identityHashCode(key[i])) & mask] != null)
/*      */       {
/* 1067 */         while (newKey[pos = pos + 1 & mask] != null); } 
/* 1068 */       newKey[pos] = key[i];
/* 1069 */       newValue[pos] = value[i];
/*      */     } 
/* 1071 */     newValue[newN] = value[this.n];
/* 1072 */     this.n = newN;
/* 1073 */     this.mask = mask;
/* 1074 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1075 */     this.key = newKey;
/* 1076 */     this.value = newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanOpenHashMap<K> clone() {
/*      */     Reference2BooleanOpenHashMap<K> c;
/*      */     try {
/* 1093 */       c = (Reference2BooleanOpenHashMap<K>)super.clone();
/* 1094 */     } catch (CloneNotSupportedException cantHappen) {
/* 1095 */       throw new InternalError();
/*      */     } 
/* 1097 */     c.keys = null;
/* 1098 */     c.values = null;
/* 1099 */     c.entries = null;
/* 1100 */     c.containsNullKey = this.containsNullKey;
/* 1101 */     c.key = (K[])this.key.clone();
/* 1102 */     c.value = (boolean[])this.value.clone();
/* 1103 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1116 */     int h = 0;
/* 1117 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1118 */       while (this.key[i] == null)
/* 1119 */         i++; 
/* 1120 */       if (this != this.key[i])
/* 1121 */         t = System.identityHashCode(this.key[i]); 
/* 1122 */       t ^= this.value[i] ? 1231 : 1237;
/* 1123 */       h += t;
/* 1124 */       i++;
/*      */     } 
/*      */     
/* 1127 */     if (this.containsNullKey)
/* 1128 */       h += this.value[this.n] ? 1231 : 1237; 
/* 1129 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1132 */     K[] key = this.key;
/* 1133 */     boolean[] value = this.value;
/* 1134 */     MapIterator i = new MapIterator();
/* 1135 */     s.defaultWriteObject();
/* 1136 */     for (int j = this.size; j-- != 0; ) {
/* 1137 */       int e = i.nextEntry();
/* 1138 */       s.writeObject(key[e]);
/* 1139 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1144 */     s.defaultReadObject();
/* 1145 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1146 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1147 */     this.mask = this.n - 1;
/* 1148 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1149 */     boolean[] value = this.value = new boolean[this.n + 1];
/*      */ 
/*      */     
/* 1152 */     for (int i = this.size; i-- != 0; ) {
/* 1153 */       int pos; K k = (K)s.readObject();
/* 1154 */       boolean v = s.readBoolean();
/* 1155 */       if (k == null) {
/* 1156 */         pos = this.n;
/* 1157 */         this.containsNullKey = true;
/*      */       } else {
/* 1159 */         pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/* 1160 */         while (key[pos] != null)
/* 1161 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1163 */       key[pos] = k;
/* 1164 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2BooleanOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */