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
/*      */ public class Object2CharOpenHashMap<K>
/*      */   extends AbstractObject2CharMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient char[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Object2CharMap.FastEntrySet<K> entries;
/*      */   protected transient ObjectSet<K> keys;
/*      */   protected transient CharCollection values;
/*      */   
/*      */   public Object2CharOpenHashMap(int expected, float f) {
/*  100 */     if (f <= 0.0F || f > 1.0F)
/*  101 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  102 */     if (expected < 0)
/*  103 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  104 */     this.f = f;
/*  105 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  106 */     this.mask = this.n - 1;
/*  107 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  108 */     this.key = (K[])new Object[this.n + 1];
/*  109 */     this.value = new char[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2CharOpenHashMap(int expected) {
/*  118 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2CharOpenHashMap() {
/*  126 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2CharOpenHashMap(Map<? extends K, ? extends Character> m, float f) {
/*  137 */     this(m.size(), f);
/*  138 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2CharOpenHashMap(Map<? extends K, ? extends Character> m) {
/*  148 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2CharOpenHashMap(Object2CharMap<K> m, float f) {
/*  159 */     this(m.size(), f);
/*  160 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2CharOpenHashMap(Object2CharMap<K> m) {
/*  170 */     this(m, 0.75F);
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
/*      */   public Object2CharOpenHashMap(K[] k, char[] v, float f) {
/*  185 */     this(k.length, f);
/*  186 */     if (k.length != v.length) {
/*  187 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  189 */     for (int i = 0; i < k.length; i++) {
/*  190 */       put(k[i], v[i]);
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
/*      */   public Object2CharOpenHashMap(K[] k, char[] v) {
/*  204 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  207 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  210 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  211 */     if (needed > this.n)
/*  212 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  215 */     int needed = (int)Math.min(1073741824L, 
/*  216 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  217 */     if (needed > this.n)
/*  218 */       rehash(needed); 
/*      */   }
/*      */   private char removeEntry(int pos) {
/*  221 */     char oldValue = this.value[pos];
/*  222 */     this.size--;
/*  223 */     shiftKeys(pos);
/*  224 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  225 */       rehash(this.n / 2); 
/*  226 */     return oldValue;
/*      */   }
/*      */   private char removeNullEntry() {
/*  229 */     this.containsNullKey = false;
/*  230 */     this.key[this.n] = null;
/*  231 */     char oldValue = this.value[this.n];
/*  232 */     this.size--;
/*  233 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  234 */       rehash(this.n / 2); 
/*  235 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Character> m) {
/*  239 */     if (this.f <= 0.5D) {
/*  240 */       ensureCapacity(m.size());
/*      */     } else {
/*  242 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  244 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(K k) {
/*  248 */     if (k == null) {
/*  249 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  251 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  254 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  255 */       return -(pos + 1); 
/*  256 */     if (k.equals(curr)) {
/*  257 */       return pos;
/*      */     }
/*      */     while (true) {
/*  260 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  261 */         return -(pos + 1); 
/*  262 */       if (k.equals(curr))
/*  263 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, K k, char v) {
/*  267 */     if (pos == this.n)
/*  268 */       this.containsNullKey = true; 
/*  269 */     this.key[pos] = k;
/*  270 */     this.value[pos] = v;
/*  271 */     if (this.size++ >= this.maxFill) {
/*  272 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public char put(K k, char v) {
/*  278 */     int pos = find(k);
/*  279 */     if (pos < 0) {
/*  280 */       insert(-pos - 1, k, v);
/*  281 */       return this.defRetValue;
/*      */     } 
/*  283 */     char oldValue = this.value[pos];
/*  284 */     this.value[pos] = v;
/*  285 */     return oldValue;
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
/*  298 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  300 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  302 */         if ((curr = key[pos]) == null) {
/*  303 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  306 */         int slot = HashCommon.mix(curr.hashCode()) & this.mask;
/*  307 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  309 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  311 */       key[last] = curr;
/*  312 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public char removeChar(Object k) {
/*  318 */     if (k == null) {
/*  319 */       if (this.containsNullKey)
/*  320 */         return removeNullEntry(); 
/*  321 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  324 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  327 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  328 */       return this.defRetValue; 
/*  329 */     if (k.equals(curr))
/*  330 */       return removeEntry(pos); 
/*      */     while (true) {
/*  332 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  333 */         return this.defRetValue; 
/*  334 */       if (k.equals(curr)) {
/*  335 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char getChar(Object k) {
/*  341 */     if (k == null) {
/*  342 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  344 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  347 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  348 */       return this.defRetValue; 
/*  349 */     if (k.equals(curr)) {
/*  350 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  353 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  354 */         return this.defRetValue; 
/*  355 */       if (k.equals(curr)) {
/*  356 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  362 */     if (k == null) {
/*  363 */       return this.containsNullKey;
/*      */     }
/*  365 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  368 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  369 */       return false; 
/*  370 */     if (k.equals(curr)) {
/*  371 */       return true;
/*      */     }
/*      */     while (true) {
/*  374 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  375 */         return false; 
/*  376 */       if (k.equals(curr))
/*  377 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(char v) {
/*  382 */     char[] value = this.value;
/*  383 */     K[] key = this.key;
/*  384 */     if (this.containsNullKey && value[this.n] == v)
/*  385 */       return true; 
/*  386 */     for (int i = this.n; i-- != 0;) {
/*  387 */       if (key[i] != null && value[i] == v)
/*  388 */         return true; 
/*  389 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char getOrDefault(Object k, char defaultValue) {
/*  395 */     if (k == null) {
/*  396 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  398 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  401 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  402 */       return defaultValue; 
/*  403 */     if (k.equals(curr)) {
/*  404 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  407 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  408 */         return defaultValue; 
/*  409 */       if (k.equals(curr)) {
/*  410 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char putIfAbsent(K k, char v) {
/*  416 */     int pos = find(k);
/*  417 */     if (pos >= 0)
/*  418 */       return this.value[pos]; 
/*  419 */     insert(-pos - 1, k, v);
/*  420 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, char v) {
/*  426 */     if (k == null) {
/*  427 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  428 */         removeNullEntry();
/*  429 */         return true;
/*      */       } 
/*  431 */       return false;
/*      */     } 
/*      */     
/*  434 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  437 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  438 */       return false; 
/*  439 */     if (k.equals(curr) && v == this.value[pos]) {
/*  440 */       removeEntry(pos);
/*  441 */       return true;
/*      */     } 
/*      */     while (true) {
/*  444 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  445 */         return false; 
/*  446 */       if (k.equals(curr) && v == this.value[pos]) {
/*  447 */         removeEntry(pos);
/*  448 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, char oldValue, char v) {
/*  455 */     int pos = find(k);
/*  456 */     if (pos < 0 || oldValue != this.value[pos])
/*  457 */       return false; 
/*  458 */     this.value[pos] = v;
/*  459 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public char replace(K k, char v) {
/*  464 */     int pos = find(k);
/*  465 */     if (pos < 0)
/*  466 */       return this.defRetValue; 
/*  467 */     char oldValue = this.value[pos];
/*  468 */     this.value[pos] = v;
/*  469 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char computeCharIfAbsent(K k, ToIntFunction<? super K> mappingFunction) {
/*  474 */     Objects.requireNonNull(mappingFunction);
/*  475 */     int pos = find(k);
/*  476 */     if (pos >= 0)
/*  477 */       return this.value[pos]; 
/*  478 */     char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(k));
/*  479 */     insert(-pos - 1, k, newValue);
/*  480 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeCharIfPresent(K k, BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) {
/*  486 */     Objects.requireNonNull(remappingFunction);
/*  487 */     int pos = find(k);
/*  488 */     if (pos < 0)
/*  489 */       return this.defRetValue; 
/*  490 */     Character newValue = remappingFunction.apply(k, Character.valueOf(this.value[pos]));
/*  491 */     if (newValue == null) {
/*  492 */       if (k == null) {
/*  493 */         removeNullEntry();
/*      */       } else {
/*  495 */         removeEntry(pos);
/*  496 */       }  return this.defRetValue;
/*      */     } 
/*  498 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeChar(K k, BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) {
/*  504 */     Objects.requireNonNull(remappingFunction);
/*  505 */     int pos = find(k);
/*  506 */     Character newValue = remappingFunction.apply(k, (pos >= 0) ? Character.valueOf(this.value[pos]) : null);
/*  507 */     if (newValue == null) {
/*  508 */       if (pos >= 0)
/*  509 */         if (k == null) {
/*  510 */           removeNullEntry();
/*      */         } else {
/*  512 */           removeEntry(pos);
/*      */         }  
/*  514 */       return this.defRetValue;
/*      */     } 
/*  516 */     char newVal = newValue.charValue();
/*  517 */     if (pos < 0) {
/*  518 */       insert(-pos - 1, k, newVal);
/*  519 */       return newVal;
/*      */     } 
/*  521 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char mergeChar(K k, char v, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*  527 */     Objects.requireNonNull(remappingFunction);
/*  528 */     int pos = find(k);
/*  529 */     if (pos < 0) {
/*  530 */       insert(-pos - 1, k, v);
/*  531 */       return v;
/*      */     } 
/*  533 */     Character newValue = remappingFunction.apply(Character.valueOf(this.value[pos]), Character.valueOf(v));
/*  534 */     if (newValue == null) {
/*  535 */       if (k == null) {
/*  536 */         removeNullEntry();
/*      */       } else {
/*  538 */         removeEntry(pos);
/*  539 */       }  return this.defRetValue;
/*      */     } 
/*  541 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
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
/*  552 */     if (this.size == 0)
/*      */       return; 
/*  554 */     this.size = 0;
/*  555 */     this.containsNullKey = false;
/*  556 */     Arrays.fill((Object[])this.key, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  560 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  564 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2CharMap.Entry<K>, Map.Entry<K, Character>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  576 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  582 */       return Object2CharOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public char getCharValue() {
/*  586 */       return Object2CharOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public char setValue(char v) {
/*  590 */       char oldValue = Object2CharOpenHashMap.this.value[this.index];
/*  591 */       Object2CharOpenHashMap.this.value[this.index] = v;
/*  592 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getValue() {
/*  602 */       return Character.valueOf(Object2CharOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character setValue(Character v) {
/*  612 */       return Character.valueOf(setValue(v.charValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  617 */       if (!(o instanceof Map.Entry))
/*  618 */         return false; 
/*  619 */       Map.Entry<K, Character> e = (Map.Entry<K, Character>)o;
/*  620 */       return (Objects.equals(Object2CharOpenHashMap.this.key[this.index], e.getKey()) && Object2CharOpenHashMap.this.value[this.index] == ((Character)e
/*  621 */         .getValue()).charValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  625 */       return ((Object2CharOpenHashMap.this.key[this.index] == null) ? 0 : Object2CharOpenHashMap.this.key[this.index].hashCode()) ^ Object2CharOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  629 */       return (new StringBuilder()).append(Object2CharOpenHashMap.this.key[this.index]).append("=>").append(Object2CharOpenHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  639 */     int pos = Object2CharOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  646 */     int last = -1;
/*      */     
/*  648 */     int c = Object2CharOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  652 */     boolean mustReturnNullKey = Object2CharOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ObjectArrayList<K> wrapped;
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
/*  667 */         return this.last = Object2CharOpenHashMap.this.n;
/*      */       } 
/*  669 */       K[] key = Object2CharOpenHashMap.this.key;
/*      */       while (true) {
/*  671 */         if (--this.pos < 0) {
/*      */           
/*  673 */           this.last = Integer.MIN_VALUE;
/*  674 */           K k = this.wrapped.get(-this.pos - 1);
/*  675 */           int p = HashCommon.mix(k.hashCode()) & Object2CharOpenHashMap.this.mask;
/*  676 */           while (!k.equals(key[p]))
/*  677 */             p = p + 1 & Object2CharOpenHashMap.this.mask; 
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
/*  695 */       K[] key = Object2CharOpenHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  697 */         pos = (last = pos) + 1 & Object2CharOpenHashMap.this.mask;
/*      */         while (true) {
/*  699 */           if ((curr = key[pos]) == null) {
/*  700 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  703 */           int slot = HashCommon.mix(curr.hashCode()) & Object2CharOpenHashMap.this.mask;
/*  704 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  706 */           pos = pos + 1 & Object2CharOpenHashMap.this.mask;
/*      */         } 
/*  708 */         if (pos < last) {
/*  709 */           if (this.wrapped == null)
/*  710 */             this.wrapped = new ObjectArrayList<>(2); 
/*  711 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  713 */         key[last] = curr;
/*  714 */         Object2CharOpenHashMap.this.value[last] = Object2CharOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  718 */       if (this.last == -1)
/*  719 */         throw new IllegalStateException(); 
/*  720 */       if (this.last == Object2CharOpenHashMap.this.n) {
/*  721 */         Object2CharOpenHashMap.this.containsNullKey = false;
/*  722 */         Object2CharOpenHashMap.this.key[Object2CharOpenHashMap.this.n] = null;
/*  723 */       } else if (this.pos >= 0) {
/*  724 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  727 */         Object2CharOpenHashMap.this.removeChar(this.wrapped.set(-this.pos - 1, null));
/*  728 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  731 */       Object2CharOpenHashMap.this.size--;
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
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Object2CharMap.Entry<K>> { private Object2CharOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public Object2CharOpenHashMap<K>.MapEntry next() {
/*  747 */       return this.entry = new Object2CharOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  751 */       super.remove();
/*  752 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Object2CharMap.Entry<K>> { private FastEntryIterator() {
/*  756 */       this.entry = new Object2CharOpenHashMap.MapEntry();
/*      */     } private final Object2CharOpenHashMap<K>.MapEntry entry;
/*      */     public Object2CharOpenHashMap<K>.MapEntry next() {
/*  759 */       this.entry.index = nextEntry();
/*  760 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Object2CharMap.Entry<K>> implements Object2CharMap.FastEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Object2CharMap.Entry<K>> iterator() {
/*  766 */       return new Object2CharOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Object2CharMap.Entry<K>> fastIterator() {
/*  770 */       return new Object2CharOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  775 */       if (!(o instanceof Map.Entry))
/*  776 */         return false; 
/*  777 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  778 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/*  779 */         return false; 
/*  780 */       K k = (K)e.getKey();
/*  781 */       char v = ((Character)e.getValue()).charValue();
/*  782 */       if (k == null) {
/*  783 */         return (Object2CharOpenHashMap.this.containsNullKey && Object2CharOpenHashMap.this.value[Object2CharOpenHashMap.this.n] == v);
/*      */       }
/*  785 */       K[] key = Object2CharOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  788 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2CharOpenHashMap.this.mask]) == null)
/*  789 */         return false; 
/*  790 */       if (k.equals(curr)) {
/*  791 */         return (Object2CharOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  794 */         if ((curr = key[pos = pos + 1 & Object2CharOpenHashMap.this.mask]) == null)
/*  795 */           return false; 
/*  796 */         if (k.equals(curr)) {
/*  797 */           return (Object2CharOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  803 */       if (!(o instanceof Map.Entry))
/*  804 */         return false; 
/*  805 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  806 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/*  807 */         return false; 
/*  808 */       K k = (K)e.getKey();
/*  809 */       char v = ((Character)e.getValue()).charValue();
/*  810 */       if (k == null) {
/*  811 */         if (Object2CharOpenHashMap.this.containsNullKey && Object2CharOpenHashMap.this.value[Object2CharOpenHashMap.this.n] == v) {
/*  812 */           Object2CharOpenHashMap.this.removeNullEntry();
/*  813 */           return true;
/*      */         } 
/*  815 */         return false;
/*      */       } 
/*      */       
/*  818 */       K[] key = Object2CharOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  821 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2CharOpenHashMap.this.mask]) == null)
/*  822 */         return false; 
/*  823 */       if (curr.equals(k)) {
/*  824 */         if (Object2CharOpenHashMap.this.value[pos] == v) {
/*  825 */           Object2CharOpenHashMap.this.removeEntry(pos);
/*  826 */           return true;
/*      */         } 
/*  828 */         return false;
/*      */       } 
/*      */       while (true) {
/*  831 */         if ((curr = key[pos = pos + 1 & Object2CharOpenHashMap.this.mask]) == null)
/*  832 */           return false; 
/*  833 */         if (curr.equals(k) && 
/*  834 */           Object2CharOpenHashMap.this.value[pos] == v) {
/*  835 */           Object2CharOpenHashMap.this.removeEntry(pos);
/*  836 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  843 */       return Object2CharOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  847 */       Object2CharOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2CharMap.Entry<K>> consumer) {
/*  852 */       if (Object2CharOpenHashMap.this.containsNullKey)
/*  853 */         consumer.accept(new AbstractObject2CharMap.BasicEntry<>(Object2CharOpenHashMap.this.key[Object2CharOpenHashMap.this.n], Object2CharOpenHashMap.this.value[Object2CharOpenHashMap.this.n])); 
/*  854 */       for (int pos = Object2CharOpenHashMap.this.n; pos-- != 0;) {
/*  855 */         if (Object2CharOpenHashMap.this.key[pos] != null)
/*  856 */           consumer.accept(new AbstractObject2CharMap.BasicEntry<>(Object2CharOpenHashMap.this.key[pos], Object2CharOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2CharMap.Entry<K>> consumer) {
/*  861 */       AbstractObject2CharMap.BasicEntry<K> entry = new AbstractObject2CharMap.BasicEntry<>();
/*  862 */       if (Object2CharOpenHashMap.this.containsNullKey) {
/*  863 */         entry.key = Object2CharOpenHashMap.this.key[Object2CharOpenHashMap.this.n];
/*  864 */         entry.value = Object2CharOpenHashMap.this.value[Object2CharOpenHashMap.this.n];
/*  865 */         consumer.accept(entry);
/*      */       } 
/*  867 */       for (int pos = Object2CharOpenHashMap.this.n; pos-- != 0;) {
/*  868 */         if (Object2CharOpenHashMap.this.key[pos] != null) {
/*  869 */           entry.key = Object2CharOpenHashMap.this.key[pos];
/*  870 */           entry.value = Object2CharOpenHashMap.this.value[pos];
/*  871 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Object2CharMap.FastEntrySet<K> object2CharEntrySet() {
/*  877 */     if (this.entries == null)
/*  878 */       this.entries = new MapEntrySet(); 
/*  879 */     return this.entries;
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
/*  896 */       return Object2CharOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/*  902 */       return new Object2CharOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/*  907 */       if (Object2CharOpenHashMap.this.containsNullKey)
/*  908 */         consumer.accept(Object2CharOpenHashMap.this.key[Object2CharOpenHashMap.this.n]); 
/*  909 */       for (int pos = Object2CharOpenHashMap.this.n; pos-- != 0; ) {
/*  910 */         K k = Object2CharOpenHashMap.this.key[pos];
/*  911 */         if (k != null)
/*  912 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  917 */       return Object2CharOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/*  921 */       return Object2CharOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/*  925 */       int oldSize = Object2CharOpenHashMap.this.size;
/*  926 */       Object2CharOpenHashMap.this.removeChar(k);
/*  927 */       return (Object2CharOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  931 */       Object2CharOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSet<K> keySet() {
/*  936 */     if (this.keys == null)
/*  937 */       this.keys = new KeySet(); 
/*  938 */     return this.keys;
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
/*  955 */       return Object2CharOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public CharCollection values() {
/*  960 */     if (this.values == null)
/*  961 */       this.values = (CharCollection)new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/*  964 */             return new Object2CharOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/*  968 */             return Object2CharOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(char v) {
/*  972 */             return Object2CharOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/*  976 */             Object2CharOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/*  981 */             if (Object2CharOpenHashMap.this.containsNullKey)
/*  982 */               consumer.accept(Object2CharOpenHashMap.this.value[Object2CharOpenHashMap.this.n]); 
/*  983 */             for (int pos = Object2CharOpenHashMap.this.n; pos-- != 0;) {
/*  984 */               if (Object2CharOpenHashMap.this.key[pos] != null)
/*  985 */                 consumer.accept(Object2CharOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/*  988 */     return this.values;
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
/* 1005 */     return trim(this.size);
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
/* 1029 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1030 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1031 */       return true; 
/*      */     try {
/* 1033 */       rehash(l);
/* 1034 */     } catch (OutOfMemoryError cantDoIt) {
/* 1035 */       return false;
/*      */     } 
/* 1037 */     return true;
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
/* 1053 */     K[] key = this.key;
/* 1054 */     char[] value = this.value;
/* 1055 */     int mask = newN - 1;
/* 1056 */     K[] newKey = (K[])new Object[newN + 1];
/* 1057 */     char[] newValue = new char[newN + 1];
/* 1058 */     int i = this.n;
/* 1059 */     for (int j = realSize(); j-- != 0; ) {
/* 1060 */       while (key[--i] == null); int pos;
/* 1061 */       if (newKey[pos = HashCommon.mix(key[i].hashCode()) & mask] != null)
/* 1062 */         while (newKey[pos = pos + 1 & mask] != null); 
/* 1063 */       newKey[pos] = key[i];
/* 1064 */       newValue[pos] = value[i];
/*      */     } 
/* 1066 */     newValue[newN] = value[this.n];
/* 1067 */     this.n = newN;
/* 1068 */     this.mask = mask;
/* 1069 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1070 */     this.key = newKey;
/* 1071 */     this.value = newValue;
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
/*      */   public Object2CharOpenHashMap<K> clone() {
/*      */     Object2CharOpenHashMap<K> c;
/*      */     try {
/* 1088 */       c = (Object2CharOpenHashMap<K>)super.clone();
/* 1089 */     } catch (CloneNotSupportedException cantHappen) {
/* 1090 */       throw new InternalError();
/*      */     } 
/* 1092 */     c.keys = null;
/* 1093 */     c.values = null;
/* 1094 */     c.entries = null;
/* 1095 */     c.containsNullKey = this.containsNullKey;
/* 1096 */     c.key = (K[])this.key.clone();
/* 1097 */     c.value = (char[])this.value.clone();
/* 1098 */     return c;
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
/* 1111 */     int h = 0;
/* 1112 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1113 */       while (this.key[i] == null)
/* 1114 */         i++; 
/* 1115 */       if (this != this.key[i])
/* 1116 */         t = this.key[i].hashCode(); 
/* 1117 */       t ^= this.value[i];
/* 1118 */       h += t;
/* 1119 */       i++;
/*      */     } 
/*      */     
/* 1122 */     if (this.containsNullKey)
/* 1123 */       h += this.value[this.n]; 
/* 1124 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1127 */     K[] key = this.key;
/* 1128 */     char[] value = this.value;
/* 1129 */     MapIterator i = new MapIterator();
/* 1130 */     s.defaultWriteObject();
/* 1131 */     for (int j = this.size; j-- != 0; ) {
/* 1132 */       int e = i.nextEntry();
/* 1133 */       s.writeObject(key[e]);
/* 1134 */       s.writeChar(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1139 */     s.defaultReadObject();
/* 1140 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1141 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1142 */     this.mask = this.n - 1;
/* 1143 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1144 */     char[] value = this.value = new char[this.n + 1];
/*      */ 
/*      */     
/* 1147 */     for (int i = this.size; i-- != 0; ) {
/* 1148 */       int pos; K k = (K)s.readObject();
/* 1149 */       char v = s.readChar();
/* 1150 */       if (k == null) {
/* 1151 */         pos = this.n;
/* 1152 */         this.containsNullKey = true;
/*      */       } else {
/* 1154 */         pos = HashCommon.mix(k.hashCode()) & this.mask;
/* 1155 */         while (key[pos] != null)
/* 1156 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1158 */       key[pos] = k;
/* 1159 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2CharOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */