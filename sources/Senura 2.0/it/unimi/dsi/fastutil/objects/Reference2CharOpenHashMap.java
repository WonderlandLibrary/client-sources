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
/*      */ public class Reference2CharOpenHashMap<K>
/*      */   extends AbstractReference2CharMap<K>
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
/*      */   protected transient Reference2CharMap.FastEntrySet<K> entries;
/*      */   protected transient ReferenceSet<K> keys;
/*      */   protected transient CharCollection values;
/*      */   
/*      */   public Reference2CharOpenHashMap(int expected, float f) {
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
/*      */   public Reference2CharOpenHashMap(int expected) {
/*  118 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2CharOpenHashMap() {
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
/*      */   public Reference2CharOpenHashMap(Map<? extends K, ? extends Character> m, float f) {
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
/*      */   public Reference2CharOpenHashMap(Map<? extends K, ? extends Character> m) {
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
/*      */   public Reference2CharOpenHashMap(Reference2CharMap<K> m, float f) {
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
/*      */   public Reference2CharOpenHashMap(Reference2CharMap<K> m) {
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
/*      */   public Reference2CharOpenHashMap(K[] k, char[] v, float f) {
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
/*      */   public Reference2CharOpenHashMap(K[] k, char[] v) {
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
/*  254 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  255 */       return -(pos + 1); 
/*  256 */     if (k == curr) {
/*  257 */       return pos;
/*      */     }
/*      */     while (true) {
/*  260 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  261 */         return -(pos + 1); 
/*  262 */       if (k == curr)
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
/*  306 */         int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
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
/*  327 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  328 */       return this.defRetValue; 
/*  329 */     if (k == curr)
/*  330 */       return removeEntry(pos); 
/*      */     while (true) {
/*  332 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  333 */         return this.defRetValue; 
/*  334 */       if (k == curr) {
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
/*  347 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  348 */       return this.defRetValue; 
/*  349 */     if (k == curr) {
/*  350 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  353 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  354 */         return this.defRetValue; 
/*  355 */       if (k == curr) {
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
/*  368 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  369 */       return false; 
/*  370 */     if (k == curr) {
/*  371 */       return true;
/*      */     }
/*      */     while (true) {
/*  374 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  375 */         return false; 
/*  376 */       if (k == curr)
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
/*  401 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  402 */       return defaultValue; 
/*  403 */     if (k == curr) {
/*  404 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  407 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  408 */         return defaultValue; 
/*  409 */       if (k == curr) {
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
/*  437 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  438 */       return false; 
/*  439 */     if (k == curr && v == this.value[pos]) {
/*  440 */       removeEntry(pos);
/*  441 */       return true;
/*      */     } 
/*      */     while (true) {
/*  444 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  445 */         return false; 
/*  446 */       if (k == curr && v == this.value[pos]) {
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
/*      */     implements Reference2CharMap.Entry<K>, Map.Entry<K, Character>
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
/*  582 */       return Reference2CharOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public char getCharValue() {
/*  586 */       return Reference2CharOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public char setValue(char v) {
/*  590 */       char oldValue = Reference2CharOpenHashMap.this.value[this.index];
/*  591 */       Reference2CharOpenHashMap.this.value[this.index] = v;
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
/*  602 */       return Character.valueOf(Reference2CharOpenHashMap.this.value[this.index]);
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
/*  620 */       return (Reference2CharOpenHashMap.this.key[this.index] == e.getKey() && Reference2CharOpenHashMap.this.value[this.index] == ((Character)e.getValue()).charValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  624 */       return System.identityHashCode(Reference2CharOpenHashMap.this.key[this.index]) ^ Reference2CharOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  628 */       return (new StringBuilder()).append(Reference2CharOpenHashMap.this.key[this.index]).append("=>").append(Reference2CharOpenHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  638 */     int pos = Reference2CharOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  645 */     int last = -1;
/*      */     
/*  647 */     int c = Reference2CharOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  651 */     boolean mustReturnNullKey = Reference2CharOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ReferenceArrayList<K> wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  658 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  661 */       if (!hasNext())
/*  662 */         throw new NoSuchElementException(); 
/*  663 */       this.c--;
/*  664 */       if (this.mustReturnNullKey) {
/*  665 */         this.mustReturnNullKey = false;
/*  666 */         return this.last = Reference2CharOpenHashMap.this.n;
/*      */       } 
/*  668 */       K[] key = Reference2CharOpenHashMap.this.key;
/*      */       while (true) {
/*  670 */         if (--this.pos < 0) {
/*      */           
/*  672 */           this.last = Integer.MIN_VALUE;
/*  673 */           K k = this.wrapped.get(-this.pos - 1);
/*  674 */           int p = HashCommon.mix(System.identityHashCode(k)) & Reference2CharOpenHashMap.this.mask;
/*  675 */           while (k != key[p])
/*  676 */             p = p + 1 & Reference2CharOpenHashMap.this.mask; 
/*  677 */           return p;
/*      */         } 
/*  679 */         if (key[this.pos] != null) {
/*  680 */           return this.last = this.pos;
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
/*  694 */       K[] key = Reference2CharOpenHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  696 */         pos = (last = pos) + 1 & Reference2CharOpenHashMap.this.mask;
/*      */         while (true) {
/*  698 */           if ((curr = key[pos]) == null) {
/*  699 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  702 */           int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2CharOpenHashMap.this.mask;
/*  703 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  705 */           pos = pos + 1 & Reference2CharOpenHashMap.this.mask;
/*      */         } 
/*  707 */         if (pos < last) {
/*  708 */           if (this.wrapped == null)
/*  709 */             this.wrapped = new ReferenceArrayList<>(2); 
/*  710 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  712 */         key[last] = curr;
/*  713 */         Reference2CharOpenHashMap.this.value[last] = Reference2CharOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  717 */       if (this.last == -1)
/*  718 */         throw new IllegalStateException(); 
/*  719 */       if (this.last == Reference2CharOpenHashMap.this.n) {
/*  720 */         Reference2CharOpenHashMap.this.containsNullKey = false;
/*  721 */         Reference2CharOpenHashMap.this.key[Reference2CharOpenHashMap.this.n] = null;
/*  722 */       } else if (this.pos >= 0) {
/*  723 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  726 */         Reference2CharOpenHashMap.this.removeChar(this.wrapped.set(-this.pos - 1, null));
/*  727 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  730 */       Reference2CharOpenHashMap.this.size--;
/*  731 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  736 */       int i = n;
/*  737 */       while (i-- != 0 && hasNext())
/*  738 */         nextEntry(); 
/*  739 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Reference2CharMap.Entry<K>> { private Reference2CharOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public Reference2CharOpenHashMap<K>.MapEntry next() {
/*  746 */       return this.entry = new Reference2CharOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  750 */       super.remove();
/*  751 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Reference2CharMap.Entry<K>> { private FastEntryIterator() {
/*  755 */       this.entry = new Reference2CharOpenHashMap.MapEntry();
/*      */     } private final Reference2CharOpenHashMap<K>.MapEntry entry;
/*      */     public Reference2CharOpenHashMap<K>.MapEntry next() {
/*  758 */       this.entry.index = nextEntry();
/*  759 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Reference2CharMap.Entry<K>> implements Reference2CharMap.FastEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Reference2CharMap.Entry<K>> iterator() {
/*  765 */       return new Reference2CharOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Reference2CharMap.Entry<K>> fastIterator() {
/*  769 */       return new Reference2CharOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  774 */       if (!(o instanceof Map.Entry))
/*  775 */         return false; 
/*  776 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  777 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/*  778 */         return false; 
/*  779 */       K k = (K)e.getKey();
/*  780 */       char v = ((Character)e.getValue()).charValue();
/*  781 */       if (k == null) {
/*  782 */         return (Reference2CharOpenHashMap.this.containsNullKey && Reference2CharOpenHashMap.this.value[Reference2CharOpenHashMap.this.n] == v);
/*      */       }
/*  784 */       K[] key = Reference2CharOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  787 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2CharOpenHashMap.this.mask]) == null)
/*      */       {
/*  789 */         return false; } 
/*  790 */       if (k == curr) {
/*  791 */         return (Reference2CharOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  794 */         if ((curr = key[pos = pos + 1 & Reference2CharOpenHashMap.this.mask]) == null)
/*  795 */           return false; 
/*  796 */         if (k == curr) {
/*  797 */           return (Reference2CharOpenHashMap.this.value[pos] == v);
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
/*  811 */         if (Reference2CharOpenHashMap.this.containsNullKey && Reference2CharOpenHashMap.this.value[Reference2CharOpenHashMap.this.n] == v) {
/*  812 */           Reference2CharOpenHashMap.this.removeNullEntry();
/*  813 */           return true;
/*      */         } 
/*  815 */         return false;
/*      */       } 
/*      */       
/*  818 */       K[] key = Reference2CharOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  821 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2CharOpenHashMap.this.mask]) == null)
/*      */       {
/*  823 */         return false; } 
/*  824 */       if (curr == k) {
/*  825 */         if (Reference2CharOpenHashMap.this.value[pos] == v) {
/*  826 */           Reference2CharOpenHashMap.this.removeEntry(pos);
/*  827 */           return true;
/*      */         } 
/*  829 */         return false;
/*      */       } 
/*      */       while (true) {
/*  832 */         if ((curr = key[pos = pos + 1 & Reference2CharOpenHashMap.this.mask]) == null)
/*  833 */           return false; 
/*  834 */         if (curr == k && 
/*  835 */           Reference2CharOpenHashMap.this.value[pos] == v) {
/*  836 */           Reference2CharOpenHashMap.this.removeEntry(pos);
/*  837 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  844 */       return Reference2CharOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  848 */       Reference2CharOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2CharMap.Entry<K>> consumer) {
/*  853 */       if (Reference2CharOpenHashMap.this.containsNullKey)
/*  854 */         consumer.accept(new AbstractReference2CharMap.BasicEntry<>(Reference2CharOpenHashMap.this.key[Reference2CharOpenHashMap.this.n], Reference2CharOpenHashMap.this.value[Reference2CharOpenHashMap.this.n])); 
/*  855 */       for (int pos = Reference2CharOpenHashMap.this.n; pos-- != 0;) {
/*  856 */         if (Reference2CharOpenHashMap.this.key[pos] != null)
/*  857 */           consumer.accept(new AbstractReference2CharMap.BasicEntry<>(Reference2CharOpenHashMap.this.key[pos], Reference2CharOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2CharMap.Entry<K>> consumer) {
/*  862 */       AbstractReference2CharMap.BasicEntry<K> entry = new AbstractReference2CharMap.BasicEntry<>();
/*  863 */       if (Reference2CharOpenHashMap.this.containsNullKey) {
/*  864 */         entry.key = Reference2CharOpenHashMap.this.key[Reference2CharOpenHashMap.this.n];
/*  865 */         entry.value = Reference2CharOpenHashMap.this.value[Reference2CharOpenHashMap.this.n];
/*  866 */         consumer.accept(entry);
/*      */       } 
/*  868 */       for (int pos = Reference2CharOpenHashMap.this.n; pos-- != 0;) {
/*  869 */         if (Reference2CharOpenHashMap.this.key[pos] != null) {
/*  870 */           entry.key = Reference2CharOpenHashMap.this.key[pos];
/*  871 */           entry.value = Reference2CharOpenHashMap.this.value[pos];
/*  872 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Reference2CharMap.FastEntrySet<K> reference2CharEntrySet() {
/*  878 */     if (this.entries == null)
/*  879 */       this.entries = new MapEntrySet(); 
/*  880 */     return this.entries;
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
/*  897 */       return Reference2CharOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/*  903 */       return new Reference2CharOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/*  908 */       if (Reference2CharOpenHashMap.this.containsNullKey)
/*  909 */         consumer.accept(Reference2CharOpenHashMap.this.key[Reference2CharOpenHashMap.this.n]); 
/*  910 */       for (int pos = Reference2CharOpenHashMap.this.n; pos-- != 0; ) {
/*  911 */         K k = Reference2CharOpenHashMap.this.key[pos];
/*  912 */         if (k != null)
/*  913 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  918 */       return Reference2CharOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/*  922 */       return Reference2CharOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/*  926 */       int oldSize = Reference2CharOpenHashMap.this.size;
/*  927 */       Reference2CharOpenHashMap.this.removeChar(k);
/*  928 */       return (Reference2CharOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  932 */       Reference2CharOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ReferenceSet<K> keySet() {
/*  937 */     if (this.keys == null)
/*  938 */       this.keys = new KeySet(); 
/*  939 */     return this.keys;
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
/*  956 */       return Reference2CharOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public CharCollection values() {
/*  961 */     if (this.values == null)
/*  962 */       this.values = (CharCollection)new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/*  965 */             return new Reference2CharOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/*  969 */             return Reference2CharOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(char v) {
/*  973 */             return Reference2CharOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/*  977 */             Reference2CharOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/*  982 */             if (Reference2CharOpenHashMap.this.containsNullKey)
/*  983 */               consumer.accept(Reference2CharOpenHashMap.this.value[Reference2CharOpenHashMap.this.n]); 
/*  984 */             for (int pos = Reference2CharOpenHashMap.this.n; pos-- != 0;) {
/*  985 */               if (Reference2CharOpenHashMap.this.key[pos] != null)
/*  986 */                 consumer.accept(Reference2CharOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/*  989 */     return this.values;
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
/* 1006 */     return trim(this.size);
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
/* 1030 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1031 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1032 */       return true; 
/*      */     try {
/* 1034 */       rehash(l);
/* 1035 */     } catch (OutOfMemoryError cantDoIt) {
/* 1036 */       return false;
/*      */     } 
/* 1038 */     return true;
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
/* 1054 */     K[] key = this.key;
/* 1055 */     char[] value = this.value;
/* 1056 */     int mask = newN - 1;
/* 1057 */     K[] newKey = (K[])new Object[newN + 1];
/* 1058 */     char[] newValue = new char[newN + 1];
/* 1059 */     int i = this.n;
/* 1060 */     for (int j = realSize(); j-- != 0; ) {
/* 1061 */       while (key[--i] == null); int pos;
/* 1062 */       if (newKey[pos = HashCommon.mix(System.identityHashCode(key[i])) & mask] != null)
/*      */       {
/* 1064 */         while (newKey[pos = pos + 1 & mask] != null); } 
/* 1065 */       newKey[pos] = key[i];
/* 1066 */       newValue[pos] = value[i];
/*      */     } 
/* 1068 */     newValue[newN] = value[this.n];
/* 1069 */     this.n = newN;
/* 1070 */     this.mask = mask;
/* 1071 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1072 */     this.key = newKey;
/* 1073 */     this.value = newValue;
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
/*      */   public Reference2CharOpenHashMap<K> clone() {
/*      */     Reference2CharOpenHashMap<K> c;
/*      */     try {
/* 1090 */       c = (Reference2CharOpenHashMap<K>)super.clone();
/* 1091 */     } catch (CloneNotSupportedException cantHappen) {
/* 1092 */       throw new InternalError();
/*      */     } 
/* 1094 */     c.keys = null;
/* 1095 */     c.values = null;
/* 1096 */     c.entries = null;
/* 1097 */     c.containsNullKey = this.containsNullKey;
/* 1098 */     c.key = (K[])this.key.clone();
/* 1099 */     c.value = (char[])this.value.clone();
/* 1100 */     return c;
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
/* 1113 */     int h = 0;
/* 1114 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1115 */       while (this.key[i] == null)
/* 1116 */         i++; 
/* 1117 */       if (this != this.key[i])
/* 1118 */         t = System.identityHashCode(this.key[i]); 
/* 1119 */       t ^= this.value[i];
/* 1120 */       h += t;
/* 1121 */       i++;
/*      */     } 
/*      */     
/* 1124 */     if (this.containsNullKey)
/* 1125 */       h += this.value[this.n]; 
/* 1126 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1129 */     K[] key = this.key;
/* 1130 */     char[] value = this.value;
/* 1131 */     MapIterator i = new MapIterator();
/* 1132 */     s.defaultWriteObject();
/* 1133 */     for (int j = this.size; j-- != 0; ) {
/* 1134 */       int e = i.nextEntry();
/* 1135 */       s.writeObject(key[e]);
/* 1136 */       s.writeChar(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1141 */     s.defaultReadObject();
/* 1142 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1143 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1144 */     this.mask = this.n - 1;
/* 1145 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1146 */     char[] value = this.value = new char[this.n + 1];
/*      */ 
/*      */     
/* 1149 */     for (int i = this.size; i-- != 0; ) {
/* 1150 */       int pos; K k = (K)s.readObject();
/* 1151 */       char v = s.readChar();
/* 1152 */       if (k == null) {
/* 1153 */         pos = this.n;
/* 1154 */         this.containsNullKey = true;
/*      */       } else {
/* 1156 */         pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/* 1157 */         while (key[pos] != null)
/* 1158 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1160 */       key[pos] = k;
/* 1161 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2CharOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */