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
/*      */ public class Object2CharOpenCustomHashMap<K>
/*      */   extends AbstractObject2CharMap<K>
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
/*      */   protected transient Object2CharMap.FastEntrySet<K> entries;
/*      */   protected transient ObjectSet<K> keys;
/*      */   protected transient CharCollection values;
/*      */   
/*      */   public Object2CharOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2CharOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2CharOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2CharOpenCustomHashMap(Map<? extends K, ? extends Character> m, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2CharOpenCustomHashMap(Map<? extends K, ? extends Character> m, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2CharOpenCustomHashMap(Object2CharMap<K> m, float f, Hash.Strategy<? super K> strategy) {
/*  179 */     this(m.size(), f, strategy);
/*  180 */     putAll(m);
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
/*      */   public Object2CharOpenCustomHashMap(Object2CharMap<K> m, Hash.Strategy<? super K> strategy) {
/*  192 */     this(m, 0.75F, strategy);
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
/*      */   public Object2CharOpenCustomHashMap(K[] k, char[] v, float f, Hash.Strategy<? super K> strategy) {
/*  210 */     this(k.length, f, strategy);
/*  211 */     if (k.length != v.length) {
/*  212 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  214 */     for (int i = 0; i < k.length; i++) {
/*  215 */       put(k[i], v[i]);
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
/*      */   public Object2CharOpenCustomHashMap(K[] k, char[] v, Hash.Strategy<? super K> strategy) {
/*  231 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hash.Strategy<? super K> strategy() {
/*  239 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  242 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  245 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  246 */     if (needed > this.n)
/*  247 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  250 */     int needed = (int)Math.min(1073741824L, 
/*  251 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  252 */     if (needed > this.n)
/*  253 */       rehash(needed); 
/*      */   }
/*      */   private char removeEntry(int pos) {
/*  256 */     char oldValue = this.value[pos];
/*  257 */     this.size--;
/*  258 */     shiftKeys(pos);
/*  259 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  260 */       rehash(this.n / 2); 
/*  261 */     return oldValue;
/*      */   }
/*      */   private char removeNullEntry() {
/*  264 */     this.containsNullKey = false;
/*  265 */     this.key[this.n] = null;
/*  266 */     char oldValue = this.value[this.n];
/*  267 */     this.size--;
/*  268 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  269 */       rehash(this.n / 2); 
/*  270 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Character> m) {
/*  274 */     if (this.f <= 0.5D) {
/*  275 */       ensureCapacity(m.size());
/*      */     } else {
/*  277 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  279 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(K k) {
/*  283 */     if (this.strategy.equals(k, null)) {
/*  284 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  286 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  289 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  290 */       return -(pos + 1); 
/*  291 */     if (this.strategy.equals(k, curr)) {
/*  292 */       return pos;
/*      */     }
/*      */     while (true) {
/*  295 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  296 */         return -(pos + 1); 
/*  297 */       if (this.strategy.equals(k, curr))
/*  298 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, K k, char v) {
/*  302 */     if (pos == this.n)
/*  303 */       this.containsNullKey = true; 
/*  304 */     this.key[pos] = k;
/*  305 */     this.value[pos] = v;
/*  306 */     if (this.size++ >= this.maxFill) {
/*  307 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public char put(K k, char v) {
/*  313 */     int pos = find(k);
/*  314 */     if (pos < 0) {
/*  315 */       insert(-pos - 1, k, v);
/*  316 */       return this.defRetValue;
/*      */     } 
/*  318 */     char oldValue = this.value[pos];
/*  319 */     this.value[pos] = v;
/*  320 */     return oldValue;
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
/*  333 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  335 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  337 */         if ((curr = key[pos]) == null) {
/*  338 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  341 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  342 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  344 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  346 */       key[last] = curr;
/*  347 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public char removeChar(Object k) {
/*  353 */     if (this.strategy.equals(k, null)) {
/*  354 */       if (this.containsNullKey)
/*  355 */         return removeNullEntry(); 
/*  356 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  359 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  362 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  363 */       return this.defRetValue; 
/*  364 */     if (this.strategy.equals(k, curr))
/*  365 */       return removeEntry(pos); 
/*      */     while (true) {
/*  367 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  368 */         return this.defRetValue; 
/*  369 */       if (this.strategy.equals(k, curr)) {
/*  370 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char getChar(Object k) {
/*  376 */     if (this.strategy.equals(k, null)) {
/*  377 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  379 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  382 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  383 */       return this.defRetValue; 
/*  384 */     if (this.strategy.equals(k, curr)) {
/*  385 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  388 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  389 */         return this.defRetValue; 
/*  390 */       if (this.strategy.equals(k, curr)) {
/*  391 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  397 */     if (this.strategy.equals(k, null)) {
/*  398 */       return this.containsNullKey;
/*      */     }
/*  400 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  403 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  404 */       return false; 
/*  405 */     if (this.strategy.equals(k, curr)) {
/*  406 */       return true;
/*      */     }
/*      */     while (true) {
/*  409 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  410 */         return false; 
/*  411 */       if (this.strategy.equals(k, curr))
/*  412 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(char v) {
/*  417 */     char[] value = this.value;
/*  418 */     K[] key = this.key;
/*  419 */     if (this.containsNullKey && value[this.n] == v)
/*  420 */       return true; 
/*  421 */     for (int i = this.n; i-- != 0;) {
/*  422 */       if (key[i] != null && value[i] == v)
/*  423 */         return true; 
/*  424 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char getOrDefault(Object k, char defaultValue) {
/*  430 */     if (this.strategy.equals(k, null)) {
/*  431 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  433 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  436 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  437 */       return defaultValue; 
/*  438 */     if (this.strategy.equals(k, curr)) {
/*  439 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  442 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  443 */         return defaultValue; 
/*  444 */       if (this.strategy.equals(k, curr)) {
/*  445 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char putIfAbsent(K k, char v) {
/*  451 */     int pos = find(k);
/*  452 */     if (pos >= 0)
/*  453 */       return this.value[pos]; 
/*  454 */     insert(-pos - 1, k, v);
/*  455 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, char v) {
/*  461 */     if (this.strategy.equals(k, null)) {
/*  462 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  463 */         removeNullEntry();
/*  464 */         return true;
/*      */       } 
/*  466 */       return false;
/*      */     } 
/*      */     
/*  469 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  472 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  473 */       return false; 
/*  474 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  475 */       removeEntry(pos);
/*  476 */       return true;
/*      */     } 
/*      */     while (true) {
/*  479 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  480 */         return false; 
/*  481 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  482 */         removeEntry(pos);
/*  483 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, char oldValue, char v) {
/*  490 */     int pos = find(k);
/*  491 */     if (pos < 0 || oldValue != this.value[pos])
/*  492 */       return false; 
/*  493 */     this.value[pos] = v;
/*  494 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public char replace(K k, char v) {
/*  499 */     int pos = find(k);
/*  500 */     if (pos < 0)
/*  501 */       return this.defRetValue; 
/*  502 */     char oldValue = this.value[pos];
/*  503 */     this.value[pos] = v;
/*  504 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char computeCharIfAbsent(K k, ToIntFunction<? super K> mappingFunction) {
/*  509 */     Objects.requireNonNull(mappingFunction);
/*  510 */     int pos = find(k);
/*  511 */     if (pos >= 0)
/*  512 */       return this.value[pos]; 
/*  513 */     char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(k));
/*  514 */     insert(-pos - 1, k, newValue);
/*  515 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeCharIfPresent(K k, BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) {
/*  521 */     Objects.requireNonNull(remappingFunction);
/*  522 */     int pos = find(k);
/*  523 */     if (pos < 0)
/*  524 */       return this.defRetValue; 
/*  525 */     Character newValue = remappingFunction.apply(k, Character.valueOf(this.value[pos]));
/*  526 */     if (newValue == null) {
/*  527 */       if (this.strategy.equals(k, null)) {
/*  528 */         removeNullEntry();
/*      */       } else {
/*  530 */         removeEntry(pos);
/*  531 */       }  return this.defRetValue;
/*      */     } 
/*  533 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeChar(K k, BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) {
/*  539 */     Objects.requireNonNull(remappingFunction);
/*  540 */     int pos = find(k);
/*  541 */     Character newValue = remappingFunction.apply(k, (pos >= 0) ? Character.valueOf(this.value[pos]) : null);
/*  542 */     if (newValue == null) {
/*  543 */       if (pos >= 0)
/*  544 */         if (this.strategy.equals(k, null)) {
/*  545 */           removeNullEntry();
/*      */         } else {
/*  547 */           removeEntry(pos);
/*      */         }  
/*  549 */       return this.defRetValue;
/*      */     } 
/*  551 */     char newVal = newValue.charValue();
/*  552 */     if (pos < 0) {
/*  553 */       insert(-pos - 1, k, newVal);
/*  554 */       return newVal;
/*      */     } 
/*  556 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char mergeChar(K k, char v, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*  562 */     Objects.requireNonNull(remappingFunction);
/*  563 */     int pos = find(k);
/*  564 */     if (pos < 0) {
/*  565 */       insert(-pos - 1, k, v);
/*  566 */       return v;
/*      */     } 
/*  568 */     Character newValue = remappingFunction.apply(Character.valueOf(this.value[pos]), Character.valueOf(v));
/*  569 */     if (newValue == null) {
/*  570 */       if (this.strategy.equals(k, null)) {
/*  571 */         removeNullEntry();
/*      */       } else {
/*  573 */         removeEntry(pos);
/*  574 */       }  return this.defRetValue;
/*      */     } 
/*  576 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
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
/*  587 */     if (this.size == 0)
/*      */       return; 
/*  589 */     this.size = 0;
/*  590 */     this.containsNullKey = false;
/*  591 */     Arrays.fill((Object[])this.key, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  595 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  599 */     return (this.size == 0);
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
/*  611 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  617 */       return Object2CharOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public char getCharValue() {
/*  621 */       return Object2CharOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public char setValue(char v) {
/*  625 */       char oldValue = Object2CharOpenCustomHashMap.this.value[this.index];
/*  626 */       Object2CharOpenCustomHashMap.this.value[this.index] = v;
/*  627 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getValue() {
/*  637 */       return Character.valueOf(Object2CharOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character setValue(Character v) {
/*  647 */       return Character.valueOf(setValue(v.charValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  652 */       if (!(o instanceof Map.Entry))
/*  653 */         return false; 
/*  654 */       Map.Entry<K, Character> e = (Map.Entry<K, Character>)o;
/*  655 */       return (Object2CharOpenCustomHashMap.this.strategy.equals(Object2CharOpenCustomHashMap.this.key[this.index], e.getKey()) && Object2CharOpenCustomHashMap.this.value[this.index] == ((Character)e.getValue()).charValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  659 */       return Object2CharOpenCustomHashMap.this.strategy.hashCode(Object2CharOpenCustomHashMap.this.key[this.index]) ^ Object2CharOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  663 */       return (new StringBuilder()).append(Object2CharOpenCustomHashMap.this.key[this.index]).append("=>").append(Object2CharOpenCustomHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  673 */     int pos = Object2CharOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  680 */     int last = -1;
/*      */     
/*  682 */     int c = Object2CharOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  686 */     boolean mustReturnNullKey = Object2CharOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ObjectArrayList<K> wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  693 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  696 */       if (!hasNext())
/*  697 */         throw new NoSuchElementException(); 
/*  698 */       this.c--;
/*  699 */       if (this.mustReturnNullKey) {
/*  700 */         this.mustReturnNullKey = false;
/*  701 */         return this.last = Object2CharOpenCustomHashMap.this.n;
/*      */       } 
/*  703 */       K[] key = Object2CharOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  705 */         if (--this.pos < 0) {
/*      */           
/*  707 */           this.last = Integer.MIN_VALUE;
/*  708 */           K k = this.wrapped.get(-this.pos - 1);
/*  709 */           int p = HashCommon.mix(Object2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Object2CharOpenCustomHashMap.this.mask;
/*  710 */           while (!Object2CharOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  711 */             p = p + 1 & Object2CharOpenCustomHashMap.this.mask; 
/*  712 */           return p;
/*      */         } 
/*  714 */         if (key[this.pos] != null) {
/*  715 */           return this.last = this.pos;
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
/*  729 */       K[] key = Object2CharOpenCustomHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  731 */         pos = (last = pos) + 1 & Object2CharOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  733 */           if ((curr = key[pos]) == null) {
/*  734 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  737 */           int slot = HashCommon.mix(Object2CharOpenCustomHashMap.this.strategy.hashCode(curr)) & Object2CharOpenCustomHashMap.this.mask;
/*  738 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  740 */           pos = pos + 1 & Object2CharOpenCustomHashMap.this.mask;
/*      */         } 
/*  742 */         if (pos < last) {
/*  743 */           if (this.wrapped == null)
/*  744 */             this.wrapped = new ObjectArrayList<>(2); 
/*  745 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  747 */         key[last] = curr;
/*  748 */         Object2CharOpenCustomHashMap.this.value[last] = Object2CharOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  752 */       if (this.last == -1)
/*  753 */         throw new IllegalStateException(); 
/*  754 */       if (this.last == Object2CharOpenCustomHashMap.this.n) {
/*  755 */         Object2CharOpenCustomHashMap.this.containsNullKey = false;
/*  756 */         Object2CharOpenCustomHashMap.this.key[Object2CharOpenCustomHashMap.this.n] = null;
/*  757 */       } else if (this.pos >= 0) {
/*  758 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  761 */         Object2CharOpenCustomHashMap.this.removeChar(this.wrapped.set(-this.pos - 1, null));
/*  762 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  765 */       Object2CharOpenCustomHashMap.this.size--;
/*  766 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  771 */       int i = n;
/*  772 */       while (i-- != 0 && hasNext())
/*  773 */         nextEntry(); 
/*  774 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Object2CharMap.Entry<K>> { private Object2CharOpenCustomHashMap<K>.MapEntry entry;
/*      */     
/*      */     public Object2CharOpenCustomHashMap<K>.MapEntry next() {
/*  781 */       return this.entry = new Object2CharOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  785 */       super.remove();
/*  786 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Object2CharMap.Entry<K>> { private FastEntryIterator() {
/*  790 */       this.entry = new Object2CharOpenCustomHashMap.MapEntry();
/*      */     } private final Object2CharOpenCustomHashMap<K>.MapEntry entry;
/*      */     public Object2CharOpenCustomHashMap<K>.MapEntry next() {
/*  793 */       this.entry.index = nextEntry();
/*  794 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Object2CharMap.Entry<K>> implements Object2CharMap.FastEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Object2CharMap.Entry<K>> iterator() {
/*  800 */       return new Object2CharOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Object2CharMap.Entry<K>> fastIterator() {
/*  804 */       return new Object2CharOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  809 */       if (!(o instanceof Map.Entry))
/*  810 */         return false; 
/*  811 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  812 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/*  813 */         return false; 
/*  814 */       K k = (K)e.getKey();
/*  815 */       char v = ((Character)e.getValue()).charValue();
/*  816 */       if (Object2CharOpenCustomHashMap.this.strategy.equals(k, null)) {
/*  817 */         return (Object2CharOpenCustomHashMap.this.containsNullKey && Object2CharOpenCustomHashMap.this.value[Object2CharOpenCustomHashMap.this.n] == v);
/*      */       }
/*  819 */       K[] key = Object2CharOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  822 */       if ((curr = key[pos = HashCommon.mix(Object2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Object2CharOpenCustomHashMap.this.mask]) == null)
/*  823 */         return false; 
/*  824 */       if (Object2CharOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  825 */         return (Object2CharOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  828 */         if ((curr = key[pos = pos + 1 & Object2CharOpenCustomHashMap.this.mask]) == null)
/*  829 */           return false; 
/*  830 */         if (Object2CharOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  831 */           return (Object2CharOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  837 */       if (!(o instanceof Map.Entry))
/*  838 */         return false; 
/*  839 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  840 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/*  841 */         return false; 
/*  842 */       K k = (K)e.getKey();
/*  843 */       char v = ((Character)e.getValue()).charValue();
/*  844 */       if (Object2CharOpenCustomHashMap.this.strategy.equals(k, null)) {
/*  845 */         if (Object2CharOpenCustomHashMap.this.containsNullKey && Object2CharOpenCustomHashMap.this.value[Object2CharOpenCustomHashMap.this.n] == v) {
/*  846 */           Object2CharOpenCustomHashMap.this.removeNullEntry();
/*  847 */           return true;
/*      */         } 
/*  849 */         return false;
/*      */       } 
/*      */       
/*  852 */       K[] key = Object2CharOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  855 */       if ((curr = key[pos = HashCommon.mix(Object2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Object2CharOpenCustomHashMap.this.mask]) == null)
/*  856 */         return false; 
/*  857 */       if (Object2CharOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  858 */         if (Object2CharOpenCustomHashMap.this.value[pos] == v) {
/*  859 */           Object2CharOpenCustomHashMap.this.removeEntry(pos);
/*  860 */           return true;
/*      */         } 
/*  862 */         return false;
/*      */       } 
/*      */       while (true) {
/*  865 */         if ((curr = key[pos = pos + 1 & Object2CharOpenCustomHashMap.this.mask]) == null)
/*  866 */           return false; 
/*  867 */         if (Object2CharOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  868 */           Object2CharOpenCustomHashMap.this.value[pos] == v) {
/*  869 */           Object2CharOpenCustomHashMap.this.removeEntry(pos);
/*  870 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  877 */       return Object2CharOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  881 */       Object2CharOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2CharMap.Entry<K>> consumer) {
/*  886 */       if (Object2CharOpenCustomHashMap.this.containsNullKey)
/*  887 */         consumer.accept(new AbstractObject2CharMap.BasicEntry<>(Object2CharOpenCustomHashMap.this.key[Object2CharOpenCustomHashMap.this.n], Object2CharOpenCustomHashMap.this.value[Object2CharOpenCustomHashMap.this.n])); 
/*  888 */       for (int pos = Object2CharOpenCustomHashMap.this.n; pos-- != 0;) {
/*  889 */         if (Object2CharOpenCustomHashMap.this.key[pos] != null)
/*  890 */           consumer.accept(new AbstractObject2CharMap.BasicEntry<>(Object2CharOpenCustomHashMap.this.key[pos], Object2CharOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2CharMap.Entry<K>> consumer) {
/*  895 */       AbstractObject2CharMap.BasicEntry<K> entry = new AbstractObject2CharMap.BasicEntry<>();
/*  896 */       if (Object2CharOpenCustomHashMap.this.containsNullKey) {
/*  897 */         entry.key = Object2CharOpenCustomHashMap.this.key[Object2CharOpenCustomHashMap.this.n];
/*  898 */         entry.value = Object2CharOpenCustomHashMap.this.value[Object2CharOpenCustomHashMap.this.n];
/*  899 */         consumer.accept(entry);
/*      */       } 
/*  901 */       for (int pos = Object2CharOpenCustomHashMap.this.n; pos-- != 0;) {
/*  902 */         if (Object2CharOpenCustomHashMap.this.key[pos] != null) {
/*  903 */           entry.key = Object2CharOpenCustomHashMap.this.key[pos];
/*  904 */           entry.value = Object2CharOpenCustomHashMap.this.value[pos];
/*  905 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Object2CharMap.FastEntrySet<K> object2CharEntrySet() {
/*  911 */     if (this.entries == null)
/*  912 */       this.entries = new MapEntrySet(); 
/*  913 */     return this.entries;
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
/*  930 */       return Object2CharOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/*  936 */       return new Object2CharOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/*  941 */       if (Object2CharOpenCustomHashMap.this.containsNullKey)
/*  942 */         consumer.accept(Object2CharOpenCustomHashMap.this.key[Object2CharOpenCustomHashMap.this.n]); 
/*  943 */       for (int pos = Object2CharOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  944 */         K k = Object2CharOpenCustomHashMap.this.key[pos];
/*  945 */         if (k != null)
/*  946 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  951 */       return Object2CharOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/*  955 */       return Object2CharOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/*  959 */       int oldSize = Object2CharOpenCustomHashMap.this.size;
/*  960 */       Object2CharOpenCustomHashMap.this.removeChar(k);
/*  961 */       return (Object2CharOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  965 */       Object2CharOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSet<K> keySet() {
/*  970 */     if (this.keys == null)
/*  971 */       this.keys = new KeySet(); 
/*  972 */     return this.keys;
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
/*  989 */       return Object2CharOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public CharCollection values() {
/*  994 */     if (this.values == null)
/*  995 */       this.values = (CharCollection)new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/*  998 */             return new Object2CharOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1002 */             return Object2CharOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(char v) {
/* 1006 */             return Object2CharOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1010 */             Object2CharOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1015 */             if (Object2CharOpenCustomHashMap.this.containsNullKey)
/* 1016 */               consumer.accept(Object2CharOpenCustomHashMap.this.value[Object2CharOpenCustomHashMap.this.n]); 
/* 1017 */             for (int pos = Object2CharOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1018 */               if (Object2CharOpenCustomHashMap.this.key[pos] != null)
/* 1019 */                 consumer.accept(Object2CharOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1022 */     return this.values;
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
/* 1039 */     return trim(this.size);
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
/* 1063 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1064 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1065 */       return true; 
/*      */     try {
/* 1067 */       rehash(l);
/* 1068 */     } catch (OutOfMemoryError cantDoIt) {
/* 1069 */       return false;
/*      */     } 
/* 1071 */     return true;
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
/* 1087 */     K[] key = this.key;
/* 1088 */     char[] value = this.value;
/* 1089 */     int mask = newN - 1;
/* 1090 */     K[] newKey = (K[])new Object[newN + 1];
/* 1091 */     char[] newValue = new char[newN + 1];
/* 1092 */     int i = this.n;
/* 1093 */     for (int j = realSize(); j-- != 0; ) {
/* 1094 */       while (key[--i] == null); int pos;
/* 1095 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != null)
/* 1096 */         while (newKey[pos = pos + 1 & mask] != null); 
/* 1097 */       newKey[pos] = key[i];
/* 1098 */       newValue[pos] = value[i];
/*      */     } 
/* 1100 */     newValue[newN] = value[this.n];
/* 1101 */     this.n = newN;
/* 1102 */     this.mask = mask;
/* 1103 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1104 */     this.key = newKey;
/* 1105 */     this.value = newValue;
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
/*      */   public Object2CharOpenCustomHashMap<K> clone() {
/*      */     Object2CharOpenCustomHashMap<K> c;
/*      */     try {
/* 1122 */       c = (Object2CharOpenCustomHashMap<K>)super.clone();
/* 1123 */     } catch (CloneNotSupportedException cantHappen) {
/* 1124 */       throw new InternalError();
/*      */     } 
/* 1126 */     c.keys = null;
/* 1127 */     c.values = null;
/* 1128 */     c.entries = null;
/* 1129 */     c.containsNullKey = this.containsNullKey;
/* 1130 */     c.key = (K[])this.key.clone();
/* 1131 */     c.value = (char[])this.value.clone();
/* 1132 */     c.strategy = this.strategy;
/* 1133 */     return c;
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
/* 1146 */     int h = 0;
/* 1147 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1148 */       while (this.key[i] == null)
/* 1149 */         i++; 
/* 1150 */       if (this != this.key[i])
/* 1151 */         t = this.strategy.hashCode(this.key[i]); 
/* 1152 */       t ^= this.value[i];
/* 1153 */       h += t;
/* 1154 */       i++;
/*      */     } 
/*      */     
/* 1157 */     if (this.containsNullKey)
/* 1158 */       h += this.value[this.n]; 
/* 1159 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1162 */     K[] key = this.key;
/* 1163 */     char[] value = this.value;
/* 1164 */     MapIterator i = new MapIterator();
/* 1165 */     s.defaultWriteObject();
/* 1166 */     for (int j = this.size; j-- != 0; ) {
/* 1167 */       int e = i.nextEntry();
/* 1168 */       s.writeObject(key[e]);
/* 1169 */       s.writeChar(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1174 */     s.defaultReadObject();
/* 1175 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1176 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1177 */     this.mask = this.n - 1;
/* 1178 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1179 */     char[] value = this.value = new char[this.n + 1];
/*      */ 
/*      */     
/* 1182 */     for (int i = this.size; i-- != 0; ) {
/* 1183 */       int pos; K k = (K)s.readObject();
/* 1184 */       char v = s.readChar();
/* 1185 */       if (this.strategy.equals(k, null)) {
/* 1186 */         pos = this.n;
/* 1187 */         this.containsNullKey = true;
/*      */       } else {
/* 1189 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1190 */         while (key[pos] != null)
/* 1191 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1193 */       key[pos] = k;
/* 1194 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2CharOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */