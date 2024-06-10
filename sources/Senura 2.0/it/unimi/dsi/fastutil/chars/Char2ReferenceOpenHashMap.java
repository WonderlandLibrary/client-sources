/*      */ package it.unimi.dsi.fastutil.chars;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
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
/*      */ import java.util.function.IntFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Char2ReferenceOpenHashMap<V>
/*      */   extends AbstractChar2ReferenceMap<V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient char[] key;
/*      */   protected transient V[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Char2ReferenceMap.FastEntrySet<V> entries;
/*      */   protected transient CharSet keys;
/*      */   protected transient ReferenceCollection<V> values;
/*      */   
/*      */   public Char2ReferenceOpenHashMap(int expected, float f) {
/*   99 */     if (f <= 0.0F || f > 1.0F)
/*  100 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  101 */     if (expected < 0)
/*  102 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  103 */     this.f = f;
/*  104 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  105 */     this.mask = this.n - 1;
/*  106 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  107 */     this.key = new char[this.n + 1];
/*  108 */     this.value = (V[])new Object[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ReferenceOpenHashMap(int expected) {
/*  117 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ReferenceOpenHashMap() {
/*  125 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ReferenceOpenHashMap(Map<? extends Character, ? extends V> m, float f) {
/*  136 */     this(m.size(), f);
/*  137 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ReferenceOpenHashMap(Map<? extends Character, ? extends V> m) {
/*  147 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ReferenceOpenHashMap(Char2ReferenceMap<V> m, float f) {
/*  158 */     this(m.size(), f);
/*  159 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ReferenceOpenHashMap(Char2ReferenceMap<V> m) {
/*  169 */     this(m, 0.75F);
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
/*      */   public Char2ReferenceOpenHashMap(char[] k, V[] v, float f) {
/*  184 */     this(k.length, f);
/*  185 */     if (k.length != v.length) {
/*  186 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  188 */     for (int i = 0; i < k.length; i++) {
/*  189 */       put(k[i], v[i]);
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
/*      */   public Char2ReferenceOpenHashMap(char[] k, V[] v) {
/*  203 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  206 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  209 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  210 */     if (needed > this.n)
/*  211 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  214 */     int needed = (int)Math.min(1073741824L, 
/*  215 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  216 */     if (needed > this.n)
/*  217 */       rehash(needed); 
/*      */   }
/*      */   private V removeEntry(int pos) {
/*  220 */     V oldValue = this.value[pos];
/*  221 */     this.value[pos] = null;
/*  222 */     this.size--;
/*  223 */     shiftKeys(pos);
/*  224 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  225 */       rehash(this.n / 2); 
/*  226 */     return oldValue;
/*      */   }
/*      */   private V removeNullEntry() {
/*  229 */     this.containsNullKey = false;
/*  230 */     V oldValue = this.value[this.n];
/*  231 */     this.value[this.n] = null;
/*  232 */     this.size--;
/*  233 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  234 */       rehash(this.n / 2); 
/*  235 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Character, ? extends V> m) {
/*  239 */     if (this.f <= 0.5D) {
/*  240 */       ensureCapacity(m.size());
/*      */     } else {
/*  242 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  244 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(char k) {
/*  248 */     if (k == '\000') {
/*  249 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  251 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  254 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  255 */       return -(pos + 1); 
/*  256 */     if (k == curr) {
/*  257 */       return pos;
/*      */     }
/*      */     while (true) {
/*  260 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  261 */         return -(pos + 1); 
/*  262 */       if (k == curr)
/*  263 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, char k, V v) {
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
/*      */   public V put(char k, V v) {
/*  278 */     int pos = find(k);
/*  279 */     if (pos < 0) {
/*  280 */       insert(-pos - 1, k, v);
/*  281 */       return this.defRetValue;
/*      */     } 
/*  283 */     V oldValue = this.value[pos];
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
/*  298 */     char[] key = this.key; while (true) {
/*      */       char curr; int last;
/*  300 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  302 */         if ((curr = key[pos]) == '\000') {
/*  303 */           key[last] = Character.MIN_VALUE;
/*  304 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  307 */         int slot = HashCommon.mix(curr) & this.mask;
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
/*      */   public V remove(char k) {
/*  319 */     if (k == '\000') {
/*  320 */       if (this.containsNullKey)
/*  321 */         return removeNullEntry(); 
/*  322 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  325 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  328 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  329 */       return this.defRetValue; 
/*  330 */     if (k == curr)
/*  331 */       return removeEntry(pos); 
/*      */     while (true) {
/*  333 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  334 */         return this.defRetValue; 
/*  335 */       if (k == curr) {
/*  336 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V get(char k) {
/*  342 */     if (k == '\000') {
/*  343 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  345 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  348 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  349 */       return this.defRetValue; 
/*  350 */     if (k == curr) {
/*  351 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  354 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  355 */         return this.defRetValue; 
/*  356 */       if (k == curr) {
/*  357 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(char k) {
/*  363 */     if (k == '\000') {
/*  364 */       return this.containsNullKey;
/*      */     }
/*  366 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  369 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  370 */       return false; 
/*  371 */     if (k == curr) {
/*  372 */       return true;
/*      */     }
/*      */     while (true) {
/*  375 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  376 */         return false; 
/*  377 */       if (k == curr)
/*  378 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  383 */     V[] value = this.value;
/*  384 */     char[] key = this.key;
/*  385 */     if (this.containsNullKey && value[this.n] == v)
/*  386 */       return true; 
/*  387 */     for (int i = this.n; i-- != 0;) {
/*  388 */       if (key[i] != '\000' && value[i] == v)
/*  389 */         return true; 
/*  390 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V getOrDefault(char k, V defaultValue) {
/*  396 */     if (k == '\000') {
/*  397 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  399 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  402 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  403 */       return defaultValue; 
/*  404 */     if (k == curr) {
/*  405 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  408 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  409 */         return defaultValue; 
/*  410 */       if (k == curr) {
/*  411 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V putIfAbsent(char k, V v) {
/*  417 */     int pos = find(k);
/*  418 */     if (pos >= 0)
/*  419 */       return this.value[pos]; 
/*  420 */     insert(-pos - 1, k, v);
/*  421 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(char k, Object v) {
/*  427 */     if (k == '\000') {
/*  428 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  429 */         removeNullEntry();
/*  430 */         return true;
/*      */       } 
/*  432 */       return false;
/*      */     } 
/*      */     
/*  435 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  438 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  439 */       return false; 
/*  440 */     if (k == curr && v == this.value[pos]) {
/*  441 */       removeEntry(pos);
/*  442 */       return true;
/*      */     } 
/*      */     while (true) {
/*  445 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  446 */         return false; 
/*  447 */       if (k == curr && v == this.value[pos]) {
/*  448 */         removeEntry(pos);
/*  449 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(char k, V oldValue, V v) {
/*  456 */     int pos = find(k);
/*  457 */     if (pos < 0 || oldValue != this.value[pos])
/*  458 */       return false; 
/*  459 */     this.value[pos] = v;
/*  460 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public V replace(char k, V v) {
/*  465 */     int pos = find(k);
/*  466 */     if (pos < 0)
/*  467 */       return this.defRetValue; 
/*  468 */     V oldValue = this.value[pos];
/*  469 */     this.value[pos] = v;
/*  470 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(char k, IntFunction<? extends V> mappingFunction) {
/*  475 */     Objects.requireNonNull(mappingFunction);
/*  476 */     int pos = find(k);
/*  477 */     if (pos >= 0)
/*  478 */       return this.value[pos]; 
/*  479 */     V newValue = mappingFunction.apply(k);
/*  480 */     insert(-pos - 1, k, newValue);
/*  481 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfPresent(char k, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/*  487 */     Objects.requireNonNull(remappingFunction);
/*  488 */     int pos = find(k);
/*  489 */     if (pos < 0)
/*  490 */       return this.defRetValue; 
/*  491 */     V newValue = remappingFunction.apply(Character.valueOf(k), this.value[pos]);
/*  492 */     if (newValue == null) {
/*  493 */       if (k == '\000') {
/*  494 */         removeNullEntry();
/*      */       } else {
/*  496 */         removeEntry(pos);
/*  497 */       }  return this.defRetValue;
/*      */     } 
/*  499 */     this.value[pos] = newValue; return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V compute(char k, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/*  505 */     Objects.requireNonNull(remappingFunction);
/*  506 */     int pos = find(k);
/*  507 */     V newValue = remappingFunction.apply(Character.valueOf(k), (pos >= 0) ? this.value[pos] : null);
/*  508 */     if (newValue == null) {
/*  509 */       if (pos >= 0)
/*  510 */         if (k == '\000') {
/*  511 */           removeNullEntry();
/*      */         } else {
/*  513 */           removeEntry(pos);
/*      */         }  
/*  515 */       return this.defRetValue;
/*      */     } 
/*  517 */     V newVal = newValue;
/*  518 */     if (pos < 0) {
/*  519 */       insert(-pos - 1, k, newVal);
/*  520 */       return newVal;
/*      */     } 
/*  522 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V merge(char k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/*  528 */     Objects.requireNonNull(remappingFunction);
/*  529 */     int pos = find(k);
/*  530 */     if (pos < 0 || this.value[pos] == null) {
/*  531 */       if (v == null)
/*  532 */         return this.defRetValue; 
/*  533 */       insert(-pos - 1, k, v);
/*  534 */       return v;
/*      */     } 
/*  536 */     V newValue = remappingFunction.apply(this.value[pos], v);
/*  537 */     if (newValue == null) {
/*  538 */       if (k == '\000') {
/*  539 */         removeNullEntry();
/*      */       } else {
/*  541 */         removeEntry(pos);
/*  542 */       }  return this.defRetValue;
/*      */     } 
/*  544 */     this.value[pos] = newValue; return newValue;
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
/*  555 */     if (this.size == 0)
/*      */       return; 
/*  557 */     this.size = 0;
/*  558 */     this.containsNullKey = false;
/*  559 */     Arrays.fill(this.key, false);
/*  560 */     Arrays.fill((Object[])this.value, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  564 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  568 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Char2ReferenceMap.Entry<V>, Map.Entry<Character, V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  580 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public char getCharKey() {
/*  586 */       return Char2ReferenceOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  590 */       return Char2ReferenceOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  594 */       V oldValue = Char2ReferenceOpenHashMap.this.value[this.index];
/*  595 */       Char2ReferenceOpenHashMap.this.value[this.index] = v;
/*  596 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getKey() {
/*  606 */       return Character.valueOf(Char2ReferenceOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  611 */       if (!(o instanceof Map.Entry))
/*  612 */         return false; 
/*  613 */       Map.Entry<Character, V> e = (Map.Entry<Character, V>)o;
/*  614 */       return (Char2ReferenceOpenHashMap.this.key[this.index] == ((Character)e.getKey()).charValue() && Char2ReferenceOpenHashMap.this.value[this.index] == e.getValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  618 */       return Char2ReferenceOpenHashMap.this.key[this.index] ^ ((Char2ReferenceOpenHashMap.this.value[this.index] == null) ? 0 : System.identityHashCode(Char2ReferenceOpenHashMap.this.value[this.index]));
/*      */     }
/*      */     
/*      */     public String toString() {
/*  622 */       return Char2ReferenceOpenHashMap.this.key[this.index] + "=>" + Char2ReferenceOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  632 */     int pos = Char2ReferenceOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  639 */     int last = -1;
/*      */     
/*  641 */     int c = Char2ReferenceOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  645 */     boolean mustReturnNullKey = Char2ReferenceOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     CharArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  652 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  655 */       if (!hasNext())
/*  656 */         throw new NoSuchElementException(); 
/*  657 */       this.c--;
/*  658 */       if (this.mustReturnNullKey) {
/*  659 */         this.mustReturnNullKey = false;
/*  660 */         return this.last = Char2ReferenceOpenHashMap.this.n;
/*      */       } 
/*  662 */       char[] key = Char2ReferenceOpenHashMap.this.key;
/*      */       while (true) {
/*  664 */         if (--this.pos < 0) {
/*      */           
/*  666 */           this.last = Integer.MIN_VALUE;
/*  667 */           char k = this.wrapped.getChar(-this.pos - 1);
/*  668 */           int p = HashCommon.mix(k) & Char2ReferenceOpenHashMap.this.mask;
/*  669 */           while (k != key[p])
/*  670 */             p = p + 1 & Char2ReferenceOpenHashMap.this.mask; 
/*  671 */           return p;
/*      */         } 
/*  673 */         if (key[this.pos] != '\000') {
/*  674 */           return this.last = this.pos;
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
/*  688 */       char[] key = Char2ReferenceOpenHashMap.this.key; while (true) {
/*      */         char curr; int last;
/*  690 */         pos = (last = pos) + 1 & Char2ReferenceOpenHashMap.this.mask;
/*      */         while (true) {
/*  692 */           if ((curr = key[pos]) == '\000') {
/*  693 */             key[last] = Character.MIN_VALUE;
/*  694 */             Char2ReferenceOpenHashMap.this.value[last] = null;
/*      */             return;
/*      */           } 
/*  697 */           int slot = HashCommon.mix(curr) & Char2ReferenceOpenHashMap.this.mask;
/*  698 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  700 */           pos = pos + 1 & Char2ReferenceOpenHashMap.this.mask;
/*      */         } 
/*  702 */         if (pos < last) {
/*  703 */           if (this.wrapped == null)
/*  704 */             this.wrapped = new CharArrayList(2); 
/*  705 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  707 */         key[last] = curr;
/*  708 */         Char2ReferenceOpenHashMap.this.value[last] = Char2ReferenceOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  712 */       if (this.last == -1)
/*  713 */         throw new IllegalStateException(); 
/*  714 */       if (this.last == Char2ReferenceOpenHashMap.this.n) {
/*  715 */         Char2ReferenceOpenHashMap.this.containsNullKey = false;
/*  716 */         Char2ReferenceOpenHashMap.this.value[Char2ReferenceOpenHashMap.this.n] = null;
/*  717 */       } else if (this.pos >= 0) {
/*  718 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  721 */         Char2ReferenceOpenHashMap.this.remove(this.wrapped.getChar(-this.pos - 1));
/*  722 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  725 */       Char2ReferenceOpenHashMap.this.size--;
/*  726 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  731 */       int i = n;
/*  732 */       while (i-- != 0 && hasNext())
/*  733 */         nextEntry(); 
/*  734 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Char2ReferenceMap.Entry<V>> { private Char2ReferenceOpenHashMap<V>.MapEntry entry;
/*      */     
/*      */     public Char2ReferenceOpenHashMap<V>.MapEntry next() {
/*  741 */       return this.entry = new Char2ReferenceOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  745 */       super.remove();
/*  746 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Char2ReferenceMap.Entry<V>> { private FastEntryIterator() {
/*  750 */       this.entry = new Char2ReferenceOpenHashMap.MapEntry();
/*      */     } private final Char2ReferenceOpenHashMap<V>.MapEntry entry;
/*      */     public Char2ReferenceOpenHashMap<V>.MapEntry next() {
/*  753 */       this.entry.index = nextEntry();
/*  754 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Char2ReferenceMap.Entry<V>> implements Char2ReferenceMap.FastEntrySet<V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Char2ReferenceMap.Entry<V>> iterator() {
/*  760 */       return new Char2ReferenceOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Char2ReferenceMap.Entry<V>> fastIterator() {
/*  764 */       return new Char2ReferenceOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  769 */       if (!(o instanceof Map.Entry))
/*  770 */         return false; 
/*  771 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  772 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/*  773 */         return false; 
/*  774 */       char k = ((Character)e.getKey()).charValue();
/*  775 */       V v = (V)e.getValue();
/*  776 */       if (k == '\000') {
/*  777 */         return (Char2ReferenceOpenHashMap.this.containsNullKey && Char2ReferenceOpenHashMap.this.value[Char2ReferenceOpenHashMap.this.n] == v);
/*      */       }
/*  779 */       char[] key = Char2ReferenceOpenHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/*  782 */       if ((curr = key[pos = HashCommon.mix(k) & Char2ReferenceOpenHashMap.this.mask]) == '\000')
/*  783 */         return false; 
/*  784 */       if (k == curr) {
/*  785 */         return (Char2ReferenceOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  788 */         if ((curr = key[pos = pos + 1 & Char2ReferenceOpenHashMap.this.mask]) == '\000')
/*  789 */           return false; 
/*  790 */         if (k == curr) {
/*  791 */           return (Char2ReferenceOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  797 */       if (!(o instanceof Map.Entry))
/*  798 */         return false; 
/*  799 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  800 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/*  801 */         return false; 
/*  802 */       char k = ((Character)e.getKey()).charValue();
/*  803 */       V v = (V)e.getValue();
/*  804 */       if (k == '\000') {
/*  805 */         if (Char2ReferenceOpenHashMap.this.containsNullKey && Char2ReferenceOpenHashMap.this.value[Char2ReferenceOpenHashMap.this.n] == v) {
/*  806 */           Char2ReferenceOpenHashMap.this.removeNullEntry();
/*  807 */           return true;
/*      */         } 
/*  809 */         return false;
/*      */       } 
/*      */       
/*  812 */       char[] key = Char2ReferenceOpenHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/*  815 */       if ((curr = key[pos = HashCommon.mix(k) & Char2ReferenceOpenHashMap.this.mask]) == '\000')
/*  816 */         return false; 
/*  817 */       if (curr == k) {
/*  818 */         if (Char2ReferenceOpenHashMap.this.value[pos] == v) {
/*  819 */           Char2ReferenceOpenHashMap.this.removeEntry(pos);
/*  820 */           return true;
/*      */         } 
/*  822 */         return false;
/*      */       } 
/*      */       while (true) {
/*  825 */         if ((curr = key[pos = pos + 1 & Char2ReferenceOpenHashMap.this.mask]) == '\000')
/*  826 */           return false; 
/*  827 */         if (curr == k && 
/*  828 */           Char2ReferenceOpenHashMap.this.value[pos] == v) {
/*  829 */           Char2ReferenceOpenHashMap.this.removeEntry(pos);
/*  830 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  837 */       return Char2ReferenceOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  841 */       Char2ReferenceOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Char2ReferenceMap.Entry<V>> consumer) {
/*  846 */       if (Char2ReferenceOpenHashMap.this.containsNullKey)
/*  847 */         consumer.accept(new AbstractChar2ReferenceMap.BasicEntry<>(Char2ReferenceOpenHashMap.this.key[Char2ReferenceOpenHashMap.this.n], Char2ReferenceOpenHashMap.this.value[Char2ReferenceOpenHashMap.this.n])); 
/*  848 */       for (int pos = Char2ReferenceOpenHashMap.this.n; pos-- != 0;) {
/*  849 */         if (Char2ReferenceOpenHashMap.this.key[pos] != '\000')
/*  850 */           consumer.accept(new AbstractChar2ReferenceMap.BasicEntry<>(Char2ReferenceOpenHashMap.this.key[pos], Char2ReferenceOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Char2ReferenceMap.Entry<V>> consumer) {
/*  855 */       AbstractChar2ReferenceMap.BasicEntry<V> entry = new AbstractChar2ReferenceMap.BasicEntry<>();
/*  856 */       if (Char2ReferenceOpenHashMap.this.containsNullKey) {
/*  857 */         entry.key = Char2ReferenceOpenHashMap.this.key[Char2ReferenceOpenHashMap.this.n];
/*  858 */         entry.value = Char2ReferenceOpenHashMap.this.value[Char2ReferenceOpenHashMap.this.n];
/*  859 */         consumer.accept(entry);
/*      */       } 
/*  861 */       for (int pos = Char2ReferenceOpenHashMap.this.n; pos-- != 0;) {
/*  862 */         if (Char2ReferenceOpenHashMap.this.key[pos] != '\000') {
/*  863 */           entry.key = Char2ReferenceOpenHashMap.this.key[pos];
/*  864 */           entry.value = Char2ReferenceOpenHashMap.this.value[pos];
/*  865 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Char2ReferenceMap.FastEntrySet<V> char2ReferenceEntrySet() {
/*  871 */     if (this.entries == null)
/*  872 */       this.entries = new MapEntrySet(); 
/*  873 */     return this.entries;
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
/*      */     implements CharIterator
/*      */   {
/*      */     public char nextChar() {
/*  890 */       return Char2ReferenceOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractCharSet { private KeySet() {}
/*      */     
/*      */     public CharIterator iterator() {
/*  896 */       return new Char2ReferenceOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/*  901 */       if (Char2ReferenceOpenHashMap.this.containsNullKey)
/*  902 */         consumer.accept(Char2ReferenceOpenHashMap.this.key[Char2ReferenceOpenHashMap.this.n]); 
/*  903 */       for (int pos = Char2ReferenceOpenHashMap.this.n; pos-- != 0; ) {
/*  904 */         char k = Char2ReferenceOpenHashMap.this.key[pos];
/*  905 */         if (k != '\000')
/*  906 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  911 */       return Char2ReferenceOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(char k) {
/*  915 */       return Char2ReferenceOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(char k) {
/*  919 */       int oldSize = Char2ReferenceOpenHashMap.this.size;
/*  920 */       Char2ReferenceOpenHashMap.this.remove(k);
/*  921 */       return (Char2ReferenceOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  925 */       Char2ReferenceOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public CharSet keySet() {
/*  930 */     if (this.keys == null)
/*  931 */       this.keys = new KeySet(); 
/*  932 */     return this.keys;
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
/*      */     implements ObjectIterator<V>
/*      */   {
/*      */     public V next() {
/*  949 */       return Char2ReferenceOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ReferenceCollection<V> values() {
/*  954 */     if (this.values == null)
/*  955 */       this.values = (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/*  958 */             return new Char2ReferenceOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/*  962 */             return Char2ReferenceOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/*  966 */             return Char2ReferenceOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/*  970 */             Char2ReferenceOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer)
/*      */           {
/*  975 */             if (Char2ReferenceOpenHashMap.this.containsNullKey)
/*  976 */               consumer.accept(Char2ReferenceOpenHashMap.this.value[Char2ReferenceOpenHashMap.this.n]); 
/*  977 */             for (int pos = Char2ReferenceOpenHashMap.this.n; pos-- != 0;) {
/*  978 */               if (Char2ReferenceOpenHashMap.this.key[pos] != '\000')
/*  979 */                 consumer.accept(Char2ReferenceOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/*  982 */     return this.values;
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
/*  999 */     return trim(this.size);
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
/* 1023 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1024 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1025 */       return true; 
/*      */     try {
/* 1027 */       rehash(l);
/* 1028 */     } catch (OutOfMemoryError cantDoIt) {
/* 1029 */       return false;
/*      */     } 
/* 1031 */     return true;
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
/* 1047 */     char[] key = this.key;
/* 1048 */     V[] value = this.value;
/* 1049 */     int mask = newN - 1;
/* 1050 */     char[] newKey = new char[newN + 1];
/* 1051 */     V[] newValue = (V[])new Object[newN + 1];
/* 1052 */     int i = this.n;
/* 1053 */     for (int j = realSize(); j-- != 0; ) {
/* 1054 */       while (key[--i] == '\000'); int pos;
/* 1055 */       if (newKey[pos = HashCommon.mix(key[i]) & mask] != '\000')
/* 1056 */         while (newKey[pos = pos + 1 & mask] != '\000'); 
/* 1057 */       newKey[pos] = key[i];
/* 1058 */       newValue[pos] = value[i];
/*      */     } 
/* 1060 */     newValue[newN] = value[this.n];
/* 1061 */     this.n = newN;
/* 1062 */     this.mask = mask;
/* 1063 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1064 */     this.key = newKey;
/* 1065 */     this.value = newValue;
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
/*      */   public Char2ReferenceOpenHashMap<V> clone() {
/*      */     Char2ReferenceOpenHashMap<V> c;
/*      */     try {
/* 1082 */       c = (Char2ReferenceOpenHashMap<V>)super.clone();
/* 1083 */     } catch (CloneNotSupportedException cantHappen) {
/* 1084 */       throw new InternalError();
/*      */     } 
/* 1086 */     c.keys = null;
/* 1087 */     c.values = null;
/* 1088 */     c.entries = null;
/* 1089 */     c.containsNullKey = this.containsNullKey;
/* 1090 */     c.key = (char[])this.key.clone();
/* 1091 */     c.value = (V[])this.value.clone();
/* 1092 */     return c;
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
/* 1105 */     int h = 0;
/* 1106 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1107 */       while (this.key[i] == '\000')
/* 1108 */         i++; 
/* 1109 */       t = this.key[i];
/* 1110 */       if (this != this.value[i])
/* 1111 */         t ^= (this.value[i] == null) ? 0 : System.identityHashCode(this.value[i]); 
/* 1112 */       h += t;
/* 1113 */       i++;
/*      */     } 
/*      */     
/* 1116 */     if (this.containsNullKey)
/* 1117 */       h += (this.value[this.n] == null) ? 0 : System.identityHashCode(this.value[this.n]); 
/* 1118 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1121 */     char[] key = this.key;
/* 1122 */     V[] value = this.value;
/* 1123 */     MapIterator i = new MapIterator();
/* 1124 */     s.defaultWriteObject();
/* 1125 */     for (int j = this.size; j-- != 0; ) {
/* 1126 */       int e = i.nextEntry();
/* 1127 */       s.writeChar(key[e]);
/* 1128 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1133 */     s.defaultReadObject();
/* 1134 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1135 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1136 */     this.mask = this.n - 1;
/* 1137 */     char[] key = this.key = new char[this.n + 1];
/* 1138 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*      */ 
/*      */     
/* 1141 */     for (int i = this.size; i-- != 0; ) {
/* 1142 */       int pos; char k = s.readChar();
/* 1143 */       V v = (V)s.readObject();
/* 1144 */       if (k == '\000') {
/* 1145 */         pos = this.n;
/* 1146 */         this.containsNullKey = true;
/*      */       } else {
/* 1148 */         pos = HashCommon.mix(k) & this.mask;
/* 1149 */         while (key[pos] != '\000')
/* 1150 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1152 */       key[pos] = k;
/* 1153 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2ReferenceOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */