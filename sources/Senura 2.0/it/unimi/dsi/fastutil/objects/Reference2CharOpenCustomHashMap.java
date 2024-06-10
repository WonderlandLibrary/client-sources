/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharIterator;
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
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.ToIntFunction;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Reference2CharOpenCustomHashMap<K>
/*      */   extends AbstractReference2CharMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient char[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected Hash.Strategy<? super K> strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Reference2CharMap.FastEntrySet<K> entries;
/*      */   protected transient ReferenceSet<K> keys;
/*      */   protected transient CharCollection values;
/*      */   
/*      */   public Reference2CharOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
/*  106 */     this.strategy = strategy;
/*  107 */     if (f <= 0.0F || f > 1.0F)
/*  108 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  109 */     if (expected < 0)
/*  110 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  111 */     this.f = f;
/*  112 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  113 */     this.mask = this.n - 1;
/*  114 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  115 */     this.key = (K[])new Object[this.n + 1];
/*  116 */     this.value = new char[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2CharOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
/*  127 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2CharOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
/*  138 */     this(16, 0.75F, strategy);
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
/*      */   public Reference2CharOpenCustomHashMap(Map<? extends K, ? extends Character> m, float f, Hash.Strategy<? super K> strategy) {
/*  152 */     this(m.size(), f, strategy);
/*  153 */     putAll(m);
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
/*      */   public Reference2CharOpenCustomHashMap(Map<? extends K, ? extends Character> m, Hash.Strategy<? super K> strategy) {
/*  166 */     this(m, 0.75F, strategy);
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
/*      */   public Reference2CharOpenCustomHashMap(Reference2CharMap<K> m, float f, Hash.Strategy<? super K> strategy) {
/*  180 */     this(m.size(), f, strategy);
/*  181 */     putAll(m);
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
/*      */   public Reference2CharOpenCustomHashMap(Reference2CharMap<K> m, Hash.Strategy<? super K> strategy) {
/*  193 */     this(m, 0.75F, strategy);
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
/*      */   public Reference2CharOpenCustomHashMap(K[] k, char[] v, float f, Hash.Strategy<? super K> strategy) {
/*  211 */     this(k.length, f, strategy);
/*  212 */     if (k.length != v.length) {
/*  213 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  215 */     for (int i = 0; i < k.length; i++) {
/*  216 */       put(k[i], v[i]);
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
/*      */ 
/*      */   
/*      */   public Reference2CharOpenCustomHashMap(K[] k, char[] v, Hash.Strategy<? super K> strategy) {
/*  232 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hash.Strategy<? super K> strategy() {
/*  240 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  243 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  246 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  247 */     if (needed > this.n)
/*  248 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  251 */     int needed = (int)Math.min(1073741824L, 
/*  252 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  253 */     if (needed > this.n)
/*  254 */       rehash(needed); 
/*      */   }
/*      */   private char removeEntry(int pos) {
/*  257 */     char oldValue = this.value[pos];
/*  258 */     this.size--;
/*  259 */     shiftKeys(pos);
/*  260 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  261 */       rehash(this.n / 2); 
/*  262 */     return oldValue;
/*      */   }
/*      */   private char removeNullEntry() {
/*  265 */     this.containsNullKey = false;
/*  266 */     this.key[this.n] = null;
/*  267 */     char oldValue = this.value[this.n];
/*  268 */     this.size--;
/*  269 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  270 */       rehash(this.n / 2); 
/*  271 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Character> m) {
/*  275 */     if (this.f <= 0.5D) {
/*  276 */       ensureCapacity(m.size());
/*      */     } else {
/*  278 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  280 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(K k) {
/*  284 */     if (this.strategy.equals(k, null)) {
/*  285 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  287 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  290 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  291 */       return -(pos + 1); 
/*  292 */     if (this.strategy.equals(k, curr)) {
/*  293 */       return pos;
/*      */     }
/*      */     while (true) {
/*  296 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  297 */         return -(pos + 1); 
/*  298 */       if (this.strategy.equals(k, curr))
/*  299 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, K k, char v) {
/*  303 */     if (pos == this.n)
/*  304 */       this.containsNullKey = true; 
/*  305 */     this.key[pos] = k;
/*  306 */     this.value[pos] = v;
/*  307 */     if (this.size++ >= this.maxFill) {
/*  308 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public char put(K k, char v) {
/*  314 */     int pos = find(k);
/*  315 */     if (pos < 0) {
/*  316 */       insert(-pos - 1, k, v);
/*  317 */       return this.defRetValue;
/*      */     } 
/*  319 */     char oldValue = this.value[pos];
/*  320 */     this.value[pos] = v;
/*  321 */     return oldValue;
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
/*  334 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  336 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  338 */         if ((curr = key[pos]) == null) {
/*  339 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  342 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  343 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  345 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  347 */       key[last] = curr;
/*  348 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public char removeChar(Object k) {
/*  354 */     if (this.strategy.equals(k, null)) {
/*  355 */       if (this.containsNullKey)
/*  356 */         return removeNullEntry(); 
/*  357 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  360 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  363 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  364 */       return this.defRetValue; 
/*  365 */     if (this.strategy.equals(k, curr))
/*  366 */       return removeEntry(pos); 
/*      */     while (true) {
/*  368 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  369 */         return this.defRetValue; 
/*  370 */       if (this.strategy.equals(k, curr)) {
/*  371 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char getChar(Object k) {
/*  377 */     if (this.strategy.equals(k, null)) {
/*  378 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  380 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  383 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  384 */       return this.defRetValue; 
/*  385 */     if (this.strategy.equals(k, curr)) {
/*  386 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  389 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  390 */         return this.defRetValue; 
/*  391 */       if (this.strategy.equals(k, curr)) {
/*  392 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  398 */     if (this.strategy.equals(k, null)) {
/*  399 */       return this.containsNullKey;
/*      */     }
/*  401 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  404 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  405 */       return false; 
/*  406 */     if (this.strategy.equals(k, curr)) {
/*  407 */       return true;
/*      */     }
/*      */     while (true) {
/*  410 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  411 */         return false; 
/*  412 */       if (this.strategy.equals(k, curr))
/*  413 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(char v) {
/*  418 */     char[] value = this.value;
/*  419 */     K[] key = this.key;
/*  420 */     if (this.containsNullKey && value[this.n] == v)
/*  421 */       return true; 
/*  422 */     for (int i = this.n; i-- != 0;) {
/*  423 */       if (key[i] != null && value[i] == v)
/*  424 */         return true; 
/*  425 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char getOrDefault(Object k, char defaultValue) {
/*  431 */     if (this.strategy.equals(k, null)) {
/*  432 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  434 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  437 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  438 */       return defaultValue; 
/*  439 */     if (this.strategy.equals(k, curr)) {
/*  440 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  443 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  444 */         return defaultValue; 
/*  445 */       if (this.strategy.equals(k, curr)) {
/*  446 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char putIfAbsent(K k, char v) {
/*  452 */     int pos = find(k);
/*  453 */     if (pos >= 0)
/*  454 */       return this.value[pos]; 
/*  455 */     insert(-pos - 1, k, v);
/*  456 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, char v) {
/*  462 */     if (this.strategy.equals(k, null)) {
/*  463 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  464 */         removeNullEntry();
/*  465 */         return true;
/*      */       } 
/*  467 */       return false;
/*      */     } 
/*      */     
/*  470 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  473 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  474 */       return false; 
/*  475 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  476 */       removeEntry(pos);
/*  477 */       return true;
/*      */     } 
/*      */     while (true) {
/*  480 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  481 */         return false; 
/*  482 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  483 */         removeEntry(pos);
/*  484 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, char oldValue, char v) {
/*  491 */     int pos = find(k);
/*  492 */     if (pos < 0 || oldValue != this.value[pos])
/*  493 */       return false; 
/*  494 */     this.value[pos] = v;
/*  495 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public char replace(K k, char v) {
/*  500 */     int pos = find(k);
/*  501 */     if (pos < 0)
/*  502 */       return this.defRetValue; 
/*  503 */     char oldValue = this.value[pos];
/*  504 */     this.value[pos] = v;
/*  505 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char computeCharIfAbsent(K k, ToIntFunction<? super K> mappingFunction) {
/*  510 */     Objects.requireNonNull(mappingFunction);
/*  511 */     int pos = find(k);
/*  512 */     if (pos >= 0)
/*  513 */       return this.value[pos]; 
/*  514 */     char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(k));
/*  515 */     insert(-pos - 1, k, newValue);
/*  516 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeCharIfPresent(K k, BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) {
/*  522 */     Objects.requireNonNull(remappingFunction);
/*  523 */     int pos = find(k);
/*  524 */     if (pos < 0)
/*  525 */       return this.defRetValue; 
/*  526 */     Character newValue = remappingFunction.apply(k, Character.valueOf(this.value[pos]));
/*  527 */     if (newValue == null) {
/*  528 */       if (this.strategy.equals(k, null)) {
/*  529 */         removeNullEntry();
/*      */       } else {
/*  531 */         removeEntry(pos);
/*  532 */       }  return this.defRetValue;
/*      */     } 
/*  534 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeChar(K k, BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) {
/*  540 */     Objects.requireNonNull(remappingFunction);
/*  541 */     int pos = find(k);
/*  542 */     Character newValue = remappingFunction.apply(k, (pos >= 0) ? Character.valueOf(this.value[pos]) : null);
/*  543 */     if (newValue == null) {
/*  544 */       if (pos >= 0)
/*  545 */         if (this.strategy.equals(k, null)) {
/*  546 */           removeNullEntry();
/*      */         } else {
/*  548 */           removeEntry(pos);
/*      */         }  
/*  550 */       return this.defRetValue;
/*      */     } 
/*  552 */     char newVal = newValue.charValue();
/*  553 */     if (pos < 0) {
/*  554 */       insert(-pos - 1, k, newVal);
/*  555 */       return newVal;
/*      */     } 
/*  557 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char mergeChar(K k, char v, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*  563 */     Objects.requireNonNull(remappingFunction);
/*  564 */     int pos = find(k);
/*  565 */     if (pos < 0) {
/*  566 */       insert(-pos - 1, k, v);
/*  567 */       return v;
/*      */     } 
/*  569 */     Character newValue = remappingFunction.apply(Character.valueOf(this.value[pos]), Character.valueOf(v));
/*  570 */     if (newValue == null) {
/*  571 */       if (this.strategy.equals(k, null)) {
/*  572 */         removeNullEntry();
/*      */       } else {
/*  574 */         removeEntry(pos);
/*  575 */       }  return this.defRetValue;
/*      */     } 
/*  577 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
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
/*  588 */     if (this.size == 0)
/*      */       return; 
/*  590 */     this.size = 0;
/*  591 */     this.containsNullKey = false;
/*  592 */     Arrays.fill((Object[])this.key, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  596 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  600 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Reference2CharMap.Entry<K>, Map.Entry<K, Character>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  612 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  618 */       return Reference2CharOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public char getCharValue() {
/*  622 */       return Reference2CharOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public char setValue(char v) {
/*  626 */       char oldValue = Reference2CharOpenCustomHashMap.this.value[this.index];
/*  627 */       Reference2CharOpenCustomHashMap.this.value[this.index] = v;
/*  628 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getValue() {
/*  638 */       return Character.valueOf(Reference2CharOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character setValue(Character v) {
/*  648 */       return Character.valueOf(setValue(v.charValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  653 */       if (!(o instanceof Map.Entry))
/*  654 */         return false; 
/*  655 */       Map.Entry<K, Character> e = (Map.Entry<K, Character>)o;
/*  656 */       return (Reference2CharOpenCustomHashMap.this.strategy.equals(Reference2CharOpenCustomHashMap.this.key[this.index], e.getKey()) && Reference2CharOpenCustomHashMap.this.value[this.index] == ((Character)e.getValue()).charValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  660 */       return Reference2CharOpenCustomHashMap.this.strategy.hashCode(Reference2CharOpenCustomHashMap.this.key[this.index]) ^ Reference2CharOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  664 */       return (new StringBuilder()).append(Reference2CharOpenCustomHashMap.this.key[this.index]).append("=>").append(Reference2CharOpenCustomHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  674 */     int pos = Reference2CharOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  681 */     int last = -1;
/*      */     
/*  683 */     int c = Reference2CharOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  687 */     boolean mustReturnNullKey = Reference2CharOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ReferenceArrayList<K> wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  694 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  697 */       if (!hasNext())
/*  698 */         throw new NoSuchElementException(); 
/*  699 */       this.c--;
/*  700 */       if (this.mustReturnNullKey) {
/*  701 */         this.mustReturnNullKey = false;
/*  702 */         return this.last = Reference2CharOpenCustomHashMap.this.n;
/*      */       } 
/*  704 */       K[] key = Reference2CharOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  706 */         if (--this.pos < 0) {
/*      */           
/*  708 */           this.last = Integer.MIN_VALUE;
/*  709 */           K k = this.wrapped.get(-this.pos - 1);
/*  710 */           int p = HashCommon.mix(Reference2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2CharOpenCustomHashMap.this.mask;
/*  711 */           while (!Reference2CharOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  712 */             p = p + 1 & Reference2CharOpenCustomHashMap.this.mask; 
/*  713 */           return p;
/*      */         } 
/*  715 */         if (key[this.pos] != null) {
/*  716 */           return this.last = this.pos;
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
/*  730 */       K[] key = Reference2CharOpenCustomHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  732 */         pos = (last = pos) + 1 & Reference2CharOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  734 */           if ((curr = key[pos]) == null) {
/*  735 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  738 */           int slot = HashCommon.mix(Reference2CharOpenCustomHashMap.this.strategy.hashCode(curr)) & Reference2CharOpenCustomHashMap.this.mask;
/*  739 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  741 */           pos = pos + 1 & Reference2CharOpenCustomHashMap.this.mask;
/*      */         } 
/*  743 */         if (pos < last) {
/*  744 */           if (this.wrapped == null)
/*  745 */             this.wrapped = new ReferenceArrayList<>(2); 
/*  746 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  748 */         key[last] = curr;
/*  749 */         Reference2CharOpenCustomHashMap.this.value[last] = Reference2CharOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  753 */       if (this.last == -1)
/*  754 */         throw new IllegalStateException(); 
/*  755 */       if (this.last == Reference2CharOpenCustomHashMap.this.n) {
/*  756 */         Reference2CharOpenCustomHashMap.this.containsNullKey = false;
/*  757 */         Reference2CharOpenCustomHashMap.this.key[Reference2CharOpenCustomHashMap.this.n] = null;
/*  758 */       } else if (this.pos >= 0) {
/*  759 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  762 */         Reference2CharOpenCustomHashMap.this.removeChar(this.wrapped.set(-this.pos - 1, null));
/*  763 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  766 */       Reference2CharOpenCustomHashMap.this.size--;
/*  767 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  772 */       int i = n;
/*  773 */       while (i-- != 0 && hasNext())
/*  774 */         nextEntry(); 
/*  775 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Reference2CharMap.Entry<K>> { private Reference2CharOpenCustomHashMap<K>.MapEntry entry;
/*      */     
/*      */     public Reference2CharOpenCustomHashMap<K>.MapEntry next() {
/*  782 */       return this.entry = new Reference2CharOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  786 */       super.remove();
/*  787 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Reference2CharMap.Entry<K>> { private FastEntryIterator() {
/*  791 */       this.entry = new Reference2CharOpenCustomHashMap.MapEntry();
/*      */     } private final Reference2CharOpenCustomHashMap<K>.MapEntry entry;
/*      */     public Reference2CharOpenCustomHashMap<K>.MapEntry next() {
/*  794 */       this.entry.index = nextEntry();
/*  795 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Reference2CharMap.Entry<K>> implements Reference2CharMap.FastEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Reference2CharMap.Entry<K>> iterator() {
/*  801 */       return new Reference2CharOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Reference2CharMap.Entry<K>> fastIterator() {
/*  805 */       return new Reference2CharOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  810 */       if (!(o instanceof Map.Entry))
/*  811 */         return false; 
/*  812 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  813 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/*  814 */         return false; 
/*  815 */       K k = (K)e.getKey();
/*  816 */       char v = ((Character)e.getValue()).charValue();
/*  817 */       if (Reference2CharOpenCustomHashMap.this.strategy.equals(k, null)) {
/*  818 */         return (Reference2CharOpenCustomHashMap.this.containsNullKey && Reference2CharOpenCustomHashMap.this.value[Reference2CharOpenCustomHashMap.this.n] == v);
/*      */       }
/*  820 */       K[] key = Reference2CharOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  823 */       if ((curr = key[pos = HashCommon.mix(Reference2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2CharOpenCustomHashMap.this.mask]) == null)
/*  824 */         return false; 
/*  825 */       if (Reference2CharOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  826 */         return (Reference2CharOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  829 */         if ((curr = key[pos = pos + 1 & Reference2CharOpenCustomHashMap.this.mask]) == null)
/*  830 */           return false; 
/*  831 */         if (Reference2CharOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  832 */           return (Reference2CharOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  838 */       if (!(o instanceof Map.Entry))
/*  839 */         return false; 
/*  840 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  841 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/*  842 */         return false; 
/*  843 */       K k = (K)e.getKey();
/*  844 */       char v = ((Character)e.getValue()).charValue();
/*  845 */       if (Reference2CharOpenCustomHashMap.this.strategy.equals(k, null)) {
/*  846 */         if (Reference2CharOpenCustomHashMap.this.containsNullKey && Reference2CharOpenCustomHashMap.this.value[Reference2CharOpenCustomHashMap.this.n] == v) {
/*  847 */           Reference2CharOpenCustomHashMap.this.removeNullEntry();
/*  848 */           return true;
/*      */         } 
/*  850 */         return false;
/*      */       } 
/*      */       
/*  853 */       K[] key = Reference2CharOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  856 */       if ((curr = key[pos = HashCommon.mix(Reference2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2CharOpenCustomHashMap.this.mask]) == null)
/*  857 */         return false; 
/*  858 */       if (Reference2CharOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  859 */         if (Reference2CharOpenCustomHashMap.this.value[pos] == v) {
/*  860 */           Reference2CharOpenCustomHashMap.this.removeEntry(pos);
/*  861 */           return true;
/*      */         } 
/*  863 */         return false;
/*      */       } 
/*      */       while (true) {
/*  866 */         if ((curr = key[pos = pos + 1 & Reference2CharOpenCustomHashMap.this.mask]) == null)
/*  867 */           return false; 
/*  868 */         if (Reference2CharOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  869 */           Reference2CharOpenCustomHashMap.this.value[pos] == v) {
/*  870 */           Reference2CharOpenCustomHashMap.this.removeEntry(pos);
/*  871 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  878 */       return Reference2CharOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  882 */       Reference2CharOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2CharMap.Entry<K>> consumer) {
/*  887 */       if (Reference2CharOpenCustomHashMap.this.containsNullKey)
/*  888 */         consumer.accept(new AbstractReference2CharMap.BasicEntry<>(Reference2CharOpenCustomHashMap.this.key[Reference2CharOpenCustomHashMap.this.n], Reference2CharOpenCustomHashMap.this.value[Reference2CharOpenCustomHashMap.this.n])); 
/*  889 */       for (int pos = Reference2CharOpenCustomHashMap.this.n; pos-- != 0;) {
/*  890 */         if (Reference2CharOpenCustomHashMap.this.key[pos] != null)
/*  891 */           consumer.accept(new AbstractReference2CharMap.BasicEntry<>(Reference2CharOpenCustomHashMap.this.key[pos], Reference2CharOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2CharMap.Entry<K>> consumer) {
/*  896 */       AbstractReference2CharMap.BasicEntry<K> entry = new AbstractReference2CharMap.BasicEntry<>();
/*  897 */       if (Reference2CharOpenCustomHashMap.this.containsNullKey) {
/*  898 */         entry.key = Reference2CharOpenCustomHashMap.this.key[Reference2CharOpenCustomHashMap.this.n];
/*  899 */         entry.value = Reference2CharOpenCustomHashMap.this.value[Reference2CharOpenCustomHashMap.this.n];
/*  900 */         consumer.accept(entry);
/*      */       } 
/*  902 */       for (int pos = Reference2CharOpenCustomHashMap.this.n; pos-- != 0;) {
/*  903 */         if (Reference2CharOpenCustomHashMap.this.key[pos] != null) {
/*  904 */           entry.key = Reference2CharOpenCustomHashMap.this.key[pos];
/*  905 */           entry.value = Reference2CharOpenCustomHashMap.this.value[pos];
/*  906 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Reference2CharMap.FastEntrySet<K> reference2CharEntrySet() {
/*  912 */     if (this.entries == null)
/*  913 */       this.entries = new MapEntrySet(); 
/*  914 */     return this.entries;
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
/*  931 */       return Reference2CharOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/*  937 */       return new Reference2CharOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/*  942 */       if (Reference2CharOpenCustomHashMap.this.containsNullKey)
/*  943 */         consumer.accept(Reference2CharOpenCustomHashMap.this.key[Reference2CharOpenCustomHashMap.this.n]); 
/*  944 */       for (int pos = Reference2CharOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  945 */         K k = Reference2CharOpenCustomHashMap.this.key[pos];
/*  946 */         if (k != null)
/*  947 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  952 */       return Reference2CharOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/*  956 */       return Reference2CharOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/*  960 */       int oldSize = Reference2CharOpenCustomHashMap.this.size;
/*  961 */       Reference2CharOpenCustomHashMap.this.removeChar(k);
/*  962 */       return (Reference2CharOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  966 */       Reference2CharOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ReferenceSet<K> keySet() {
/*  971 */     if (this.keys == null)
/*  972 */       this.keys = new KeySet(); 
/*  973 */     return this.keys;
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
/*      */     implements CharIterator
/*      */   {
/*      */     public char nextChar() {
/*  990 */       return Reference2CharOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public CharCollection values() {
/*  995 */     if (this.values == null)
/*  996 */       this.values = (CharCollection)new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/*  999 */             return new Reference2CharOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1003 */             return Reference2CharOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(char v) {
/* 1007 */             return Reference2CharOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1011 */             Reference2CharOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1016 */             if (Reference2CharOpenCustomHashMap.this.containsNullKey)
/* 1017 */               consumer.accept(Reference2CharOpenCustomHashMap.this.value[Reference2CharOpenCustomHashMap.this.n]); 
/* 1018 */             for (int pos = Reference2CharOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1019 */               if (Reference2CharOpenCustomHashMap.this.key[pos] != null)
/* 1020 */                 consumer.accept(Reference2CharOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1023 */     return this.values;
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
/* 1040 */     return trim(this.size);
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
/* 1064 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1065 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1066 */       return true; 
/*      */     try {
/* 1068 */       rehash(l);
/* 1069 */     } catch (OutOfMemoryError cantDoIt) {
/* 1070 */       return false;
/*      */     } 
/* 1072 */     return true;
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
/* 1088 */     K[] key = this.key;
/* 1089 */     char[] value = this.value;
/* 1090 */     int mask = newN - 1;
/* 1091 */     K[] newKey = (K[])new Object[newN + 1];
/* 1092 */     char[] newValue = new char[newN + 1];
/* 1093 */     int i = this.n;
/* 1094 */     for (int j = realSize(); j-- != 0; ) {
/* 1095 */       while (key[--i] == null); int pos;
/* 1096 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != null)
/* 1097 */         while (newKey[pos = pos + 1 & mask] != null); 
/* 1098 */       newKey[pos] = key[i];
/* 1099 */       newValue[pos] = value[i];
/*      */     } 
/* 1101 */     newValue[newN] = value[this.n];
/* 1102 */     this.n = newN;
/* 1103 */     this.mask = mask;
/* 1104 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1105 */     this.key = newKey;
/* 1106 */     this.value = newValue;
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
/*      */   public Reference2CharOpenCustomHashMap<K> clone() {
/*      */     Reference2CharOpenCustomHashMap<K> c;
/*      */     try {
/* 1123 */       c = (Reference2CharOpenCustomHashMap<K>)super.clone();
/* 1124 */     } catch (CloneNotSupportedException cantHappen) {
/* 1125 */       throw new InternalError();
/*      */     } 
/* 1127 */     c.keys = null;
/* 1128 */     c.values = null;
/* 1129 */     c.entries = null;
/* 1130 */     c.containsNullKey = this.containsNullKey;
/* 1131 */     c.key = (K[])this.key.clone();
/* 1132 */     c.value = (char[])this.value.clone();
/* 1133 */     c.strategy = this.strategy;
/* 1134 */     return c;
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
/* 1147 */     int h = 0;
/* 1148 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1149 */       while (this.key[i] == null)
/* 1150 */         i++; 
/* 1151 */       if (this != this.key[i])
/* 1152 */         t = this.strategy.hashCode(this.key[i]); 
/* 1153 */       t ^= this.value[i];
/* 1154 */       h += t;
/* 1155 */       i++;
/*      */     } 
/*      */     
/* 1158 */     if (this.containsNullKey)
/* 1159 */       h += this.value[this.n]; 
/* 1160 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1163 */     K[] key = this.key;
/* 1164 */     char[] value = this.value;
/* 1165 */     MapIterator i = new MapIterator();
/* 1166 */     s.defaultWriteObject();
/* 1167 */     for (int j = this.size; j-- != 0; ) {
/* 1168 */       int e = i.nextEntry();
/* 1169 */       s.writeObject(key[e]);
/* 1170 */       s.writeChar(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1175 */     s.defaultReadObject();
/* 1176 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1177 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1178 */     this.mask = this.n - 1;
/* 1179 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1180 */     char[] value = this.value = new char[this.n + 1];
/*      */ 
/*      */     
/* 1183 */     for (int i = this.size; i-- != 0; ) {
/* 1184 */       int pos; K k = (K)s.readObject();
/* 1185 */       char v = s.readChar();
/* 1186 */       if (this.strategy.equals(k, null)) {
/* 1187 */         pos = this.n;
/* 1188 */         this.containsNullKey = true;
/*      */       } else {
/* 1190 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1191 */         while (key[pos] != null)
/* 1192 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1194 */       key[pos] = k;
/* 1195 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2CharOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */