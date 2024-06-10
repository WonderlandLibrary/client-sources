/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharIterator;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
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
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.DoubleFunction;
/*      */ import java.util.function.DoubleToIntFunction;
/*      */ import java.util.function.IntConsumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Float2CharOpenHashMap
/*      */   extends AbstractFloat2CharMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
/*      */   protected transient char[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Float2CharMap.FastEntrySet entries;
/*      */   protected transient FloatSet keys;
/*      */   protected transient CharCollection values;
/*      */   
/*      */   public Float2CharOpenHashMap(int expected, float f) {
/*   96 */     if (f <= 0.0F || f > 1.0F)
/*   97 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*   98 */     if (expected < 0)
/*   99 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  100 */     this.f = f;
/*  101 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  102 */     this.mask = this.n - 1;
/*  103 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  104 */     this.key = new float[this.n + 1];
/*  105 */     this.value = new char[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2CharOpenHashMap(int expected) {
/*  114 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2CharOpenHashMap() {
/*  122 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2CharOpenHashMap(Map<? extends Float, ? extends Character> m, float f) {
/*  133 */     this(m.size(), f);
/*  134 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2CharOpenHashMap(Map<? extends Float, ? extends Character> m) {
/*  144 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2CharOpenHashMap(Float2CharMap m, float f) {
/*  155 */     this(m.size(), f);
/*  156 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2CharOpenHashMap(Float2CharMap m) {
/*  166 */     this(m, 0.75F);
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
/*      */   public Float2CharOpenHashMap(float[] k, char[] v, float f) {
/*  181 */     this(k.length, f);
/*  182 */     if (k.length != v.length) {
/*  183 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  185 */     for (int i = 0; i < k.length; i++) {
/*  186 */       put(k[i], v[i]);
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
/*      */   public Float2CharOpenHashMap(float[] k, char[] v) {
/*  200 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  203 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  206 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  207 */     if (needed > this.n)
/*  208 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  211 */     int needed = (int)Math.min(1073741824L, 
/*  212 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  213 */     if (needed > this.n)
/*  214 */       rehash(needed); 
/*      */   }
/*      */   private char removeEntry(int pos) {
/*  217 */     char oldValue = this.value[pos];
/*  218 */     this.size--;
/*  219 */     shiftKeys(pos);
/*  220 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  221 */       rehash(this.n / 2); 
/*  222 */     return oldValue;
/*      */   }
/*      */   private char removeNullEntry() {
/*  225 */     this.containsNullKey = false;
/*  226 */     char oldValue = this.value[this.n];
/*  227 */     this.size--;
/*  228 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  229 */       rehash(this.n / 2); 
/*  230 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Float, ? extends Character> m) {
/*  234 */     if (this.f <= 0.5D) {
/*  235 */       ensureCapacity(m.size());
/*      */     } else {
/*  237 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  239 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(float k) {
/*  243 */     if (Float.floatToIntBits(k) == 0) {
/*  244 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  246 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  249 */     if (Float.floatToIntBits(
/*  250 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  252 */       return -(pos + 1); } 
/*  253 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  254 */       return pos;
/*      */     }
/*      */     while (true) {
/*  257 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  258 */         return -(pos + 1); 
/*  259 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  260 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, float k, char v) {
/*  264 */     if (pos == this.n)
/*  265 */       this.containsNullKey = true; 
/*  266 */     this.key[pos] = k;
/*  267 */     this.value[pos] = v;
/*  268 */     if (this.size++ >= this.maxFill) {
/*  269 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public char put(float k, char v) {
/*  275 */     int pos = find(k);
/*  276 */     if (pos < 0) {
/*  277 */       insert(-pos - 1, k, v);
/*  278 */       return this.defRetValue;
/*      */     } 
/*  280 */     char oldValue = this.value[pos];
/*  281 */     this.value[pos] = v;
/*  282 */     return oldValue;
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
/*  295 */     float[] key = this.key; while (true) {
/*      */       float curr; int last;
/*  297 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  299 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  300 */           key[last] = 0.0F;
/*      */           return;
/*      */         } 
/*  303 */         int slot = HashCommon.mix(HashCommon.float2int(curr)) & this.mask;
/*  304 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  306 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  308 */       key[last] = curr;
/*  309 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public char remove(float k) {
/*  315 */     if (Float.floatToIntBits(k) == 0) {
/*  316 */       if (this.containsNullKey)
/*  317 */         return removeNullEntry(); 
/*  318 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  321 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  324 */     if (Float.floatToIntBits(
/*  325 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  327 */       return this.defRetValue; } 
/*  328 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  329 */       return removeEntry(pos); 
/*      */     while (true) {
/*  331 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  332 */         return this.defRetValue; 
/*  333 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  334 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char get(float k) {
/*  340 */     if (Float.floatToIntBits(k) == 0) {
/*  341 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  343 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  346 */     if (Float.floatToIntBits(
/*  347 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  349 */       return this.defRetValue; } 
/*  350 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  351 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  354 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  355 */         return this.defRetValue; 
/*  356 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  357 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(float k) {
/*  363 */     if (Float.floatToIntBits(k) == 0) {
/*  364 */       return this.containsNullKey;
/*      */     }
/*  366 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  369 */     if (Float.floatToIntBits(
/*  370 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  372 */       return false; } 
/*  373 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  374 */       return true;
/*      */     }
/*      */     while (true) {
/*  377 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  378 */         return false; 
/*  379 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  380 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(char v) {
/*  385 */     char[] value = this.value;
/*  386 */     float[] key = this.key;
/*  387 */     if (this.containsNullKey && value[this.n] == v)
/*  388 */       return true; 
/*  389 */     for (int i = this.n; i-- != 0;) {
/*  390 */       if (Float.floatToIntBits(key[i]) != 0 && value[i] == v)
/*  391 */         return true; 
/*  392 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char getOrDefault(float k, char defaultValue) {
/*  398 */     if (Float.floatToIntBits(k) == 0) {
/*  399 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  401 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  404 */     if (Float.floatToIntBits(
/*  405 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  407 */       return defaultValue; } 
/*  408 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  409 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  412 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  413 */         return defaultValue; 
/*  414 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  415 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char putIfAbsent(float k, char v) {
/*  421 */     int pos = find(k);
/*  422 */     if (pos >= 0)
/*  423 */       return this.value[pos]; 
/*  424 */     insert(-pos - 1, k, v);
/*  425 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, char v) {
/*  431 */     if (Float.floatToIntBits(k) == 0) {
/*  432 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  433 */         removeNullEntry();
/*  434 */         return true;
/*      */       } 
/*  436 */       return false;
/*      */     } 
/*      */     
/*  439 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  442 */     if (Float.floatToIntBits(
/*  443 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  445 */       return false; } 
/*  446 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  447 */       removeEntry(pos);
/*  448 */       return true;
/*      */     } 
/*      */     while (true) {
/*  451 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  452 */         return false; 
/*  453 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  454 */         removeEntry(pos);
/*  455 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(float k, char oldValue, char v) {
/*  462 */     int pos = find(k);
/*  463 */     if (pos < 0 || oldValue != this.value[pos])
/*  464 */       return false; 
/*  465 */     this.value[pos] = v;
/*  466 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public char replace(float k, char v) {
/*  471 */     int pos = find(k);
/*  472 */     if (pos < 0)
/*  473 */       return this.defRetValue; 
/*  474 */     char oldValue = this.value[pos];
/*  475 */     this.value[pos] = v;
/*  476 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char computeIfAbsent(float k, DoubleToIntFunction mappingFunction) {
/*  481 */     Objects.requireNonNull(mappingFunction);
/*  482 */     int pos = find(k);
/*  483 */     if (pos >= 0)
/*  484 */       return this.value[pos]; 
/*  485 */     char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(k));
/*  486 */     insert(-pos - 1, k, newValue);
/*  487 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfAbsentNullable(float k, DoubleFunction<? extends Character> mappingFunction) {
/*  493 */     Objects.requireNonNull(mappingFunction);
/*  494 */     int pos = find(k);
/*  495 */     if (pos >= 0)
/*  496 */       return this.value[pos]; 
/*  497 */     Character newValue = mappingFunction.apply(k);
/*  498 */     if (newValue == null)
/*  499 */       return this.defRetValue; 
/*  500 */     char v = newValue.charValue();
/*  501 */     insert(-pos - 1, k, v);
/*  502 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfPresent(float k, BiFunction<? super Float, ? super Character, ? extends Character> remappingFunction) {
/*  508 */     Objects.requireNonNull(remappingFunction);
/*  509 */     int pos = find(k);
/*  510 */     if (pos < 0)
/*  511 */       return this.defRetValue; 
/*  512 */     Character newValue = remappingFunction.apply(Float.valueOf(k), Character.valueOf(this.value[pos]));
/*  513 */     if (newValue == null) {
/*  514 */       if (Float.floatToIntBits(k) == 0) {
/*  515 */         removeNullEntry();
/*      */       } else {
/*  517 */         removeEntry(pos);
/*  518 */       }  return this.defRetValue;
/*      */     } 
/*  520 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char compute(float k, BiFunction<? super Float, ? super Character, ? extends Character> remappingFunction) {
/*  526 */     Objects.requireNonNull(remappingFunction);
/*  527 */     int pos = find(k);
/*  528 */     Character newValue = remappingFunction.apply(Float.valueOf(k), 
/*  529 */         (pos >= 0) ? Character.valueOf(this.value[pos]) : null);
/*  530 */     if (newValue == null) {
/*  531 */       if (pos >= 0)
/*  532 */         if (Float.floatToIntBits(k) == 0) {
/*  533 */           removeNullEntry();
/*      */         } else {
/*  535 */           removeEntry(pos);
/*      */         }  
/*  537 */       return this.defRetValue;
/*      */     } 
/*  539 */     char newVal = newValue.charValue();
/*  540 */     if (pos < 0) {
/*  541 */       insert(-pos - 1, k, newVal);
/*  542 */       return newVal;
/*      */     } 
/*  544 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char merge(float k, char v, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*  550 */     Objects.requireNonNull(remappingFunction);
/*  551 */     int pos = find(k);
/*  552 */     if (pos < 0) {
/*  553 */       insert(-pos - 1, k, v);
/*  554 */       return v;
/*      */     } 
/*  556 */     Character newValue = remappingFunction.apply(Character.valueOf(this.value[pos]), Character.valueOf(v));
/*  557 */     if (newValue == null) {
/*  558 */       if (Float.floatToIntBits(k) == 0) {
/*  559 */         removeNullEntry();
/*      */       } else {
/*  561 */         removeEntry(pos);
/*  562 */       }  return this.defRetValue;
/*      */     } 
/*  564 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
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
/*  575 */     if (this.size == 0)
/*      */       return; 
/*  577 */     this.size = 0;
/*  578 */     this.containsNullKey = false;
/*  579 */     Arrays.fill(this.key, 0.0F);
/*      */   }
/*      */   
/*      */   public int size() {
/*  583 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  587 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Float2CharMap.Entry, Map.Entry<Float, Character>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  599 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public float getFloatKey() {
/*  605 */       return Float2CharOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public char getCharValue() {
/*  609 */       return Float2CharOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public char setValue(char v) {
/*  613 */       char oldValue = Float2CharOpenHashMap.this.value[this.index];
/*  614 */       Float2CharOpenHashMap.this.value[this.index] = v;
/*  615 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getKey() {
/*  625 */       return Float.valueOf(Float2CharOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getValue() {
/*  635 */       return Character.valueOf(Float2CharOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character setValue(Character v) {
/*  645 */       return Character.valueOf(setValue(v.charValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  650 */       if (!(o instanceof Map.Entry))
/*  651 */         return false; 
/*  652 */       Map.Entry<Float, Character> e = (Map.Entry<Float, Character>)o;
/*  653 */       return (Float.floatToIntBits(Float2CharOpenHashMap.this.key[this.index]) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && Float2CharOpenHashMap.this.value[this.index] == ((Character)e
/*  654 */         .getValue()).charValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  658 */       return HashCommon.float2int(Float2CharOpenHashMap.this.key[this.index]) ^ Float2CharOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  662 */       return Float2CharOpenHashMap.this.key[this.index] + "=>" + Float2CharOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  672 */     int pos = Float2CharOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  679 */     int last = -1;
/*      */     
/*  681 */     int c = Float2CharOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  685 */     boolean mustReturnNullKey = Float2CharOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     FloatArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  692 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  695 */       if (!hasNext())
/*  696 */         throw new NoSuchElementException(); 
/*  697 */       this.c--;
/*  698 */       if (this.mustReturnNullKey) {
/*  699 */         this.mustReturnNullKey = false;
/*  700 */         return this.last = Float2CharOpenHashMap.this.n;
/*      */       } 
/*  702 */       float[] key = Float2CharOpenHashMap.this.key;
/*      */       while (true) {
/*  704 */         if (--this.pos < 0) {
/*      */           
/*  706 */           this.last = Integer.MIN_VALUE;
/*  707 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  708 */           int p = HashCommon.mix(HashCommon.float2int(k)) & Float2CharOpenHashMap.this.mask;
/*  709 */           while (Float.floatToIntBits(k) != Float.floatToIntBits(key[p]))
/*  710 */             p = p + 1 & Float2CharOpenHashMap.this.mask; 
/*  711 */           return p;
/*      */         } 
/*  713 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
/*  714 */           return this.last = this.pos;
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
/*  728 */       float[] key = Float2CharOpenHashMap.this.key; while (true) {
/*      */         float curr; int last;
/*  730 */         pos = (last = pos) + 1 & Float2CharOpenHashMap.this.mask;
/*      */         while (true) {
/*  732 */           if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  733 */             key[last] = 0.0F;
/*      */             return;
/*      */           } 
/*  736 */           int slot = HashCommon.mix(HashCommon.float2int(curr)) & Float2CharOpenHashMap.this.mask;
/*      */           
/*  738 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  740 */           pos = pos + 1 & Float2CharOpenHashMap.this.mask;
/*      */         } 
/*  742 */         if (pos < last) {
/*  743 */           if (this.wrapped == null)
/*  744 */             this.wrapped = new FloatArrayList(2); 
/*  745 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  747 */         key[last] = curr;
/*  748 */         Float2CharOpenHashMap.this.value[last] = Float2CharOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  752 */       if (this.last == -1)
/*  753 */         throw new IllegalStateException(); 
/*  754 */       if (this.last == Float2CharOpenHashMap.this.n) {
/*  755 */         Float2CharOpenHashMap.this.containsNullKey = false;
/*  756 */       } else if (this.pos >= 0) {
/*  757 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  760 */         Float2CharOpenHashMap.this.remove(this.wrapped.getFloat(-this.pos - 1));
/*  761 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  764 */       Float2CharOpenHashMap.this.size--;
/*  765 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  770 */       int i = n;
/*  771 */       while (i-- != 0 && hasNext())
/*  772 */         nextEntry(); 
/*  773 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Float2CharMap.Entry> { private Float2CharOpenHashMap.MapEntry entry;
/*      */     
/*      */     public Float2CharOpenHashMap.MapEntry next() {
/*  780 */       return this.entry = new Float2CharOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  784 */       super.remove();
/*  785 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Float2CharMap.Entry> { private FastEntryIterator() {
/*  789 */       this.entry = new Float2CharOpenHashMap.MapEntry();
/*      */     } private final Float2CharOpenHashMap.MapEntry entry;
/*      */     public Float2CharOpenHashMap.MapEntry next() {
/*  792 */       this.entry.index = nextEntry();
/*  793 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Float2CharMap.Entry> implements Float2CharMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Float2CharMap.Entry> iterator() {
/*  799 */       return new Float2CharOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Float2CharMap.Entry> fastIterator() {
/*  803 */       return new Float2CharOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  808 */       if (!(o instanceof Map.Entry))
/*  809 */         return false; 
/*  810 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  811 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/*  812 */         return false; 
/*  813 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/*  814 */         return false; 
/*  815 */       float k = ((Float)e.getKey()).floatValue();
/*  816 */       char v = ((Character)e.getValue()).charValue();
/*  817 */       if (Float.floatToIntBits(k) == 0) {
/*  818 */         return (Float2CharOpenHashMap.this.containsNullKey && Float2CharOpenHashMap.this.value[Float2CharOpenHashMap.this.n] == v);
/*      */       }
/*  820 */       float[] key = Float2CharOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  823 */       if (Float.floatToIntBits(
/*  824 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2CharOpenHashMap.this.mask]) == 0)
/*      */       {
/*  826 */         return false; } 
/*  827 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  828 */         return (Float2CharOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  831 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2CharOpenHashMap.this.mask]) == 0)
/*  832 */           return false; 
/*  833 */         if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  834 */           return (Float2CharOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  840 */       if (!(o instanceof Map.Entry))
/*  841 */         return false; 
/*  842 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  843 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/*  844 */         return false; 
/*  845 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/*  846 */         return false; 
/*  847 */       float k = ((Float)e.getKey()).floatValue();
/*  848 */       char v = ((Character)e.getValue()).charValue();
/*  849 */       if (Float.floatToIntBits(k) == 0) {
/*  850 */         if (Float2CharOpenHashMap.this.containsNullKey && Float2CharOpenHashMap.this.value[Float2CharOpenHashMap.this.n] == v) {
/*  851 */           Float2CharOpenHashMap.this.removeNullEntry();
/*  852 */           return true;
/*      */         } 
/*  854 */         return false;
/*      */       } 
/*      */       
/*  857 */       float[] key = Float2CharOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  860 */       if (Float.floatToIntBits(
/*  861 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2CharOpenHashMap.this.mask]) == 0)
/*      */       {
/*  863 */         return false; } 
/*  864 */       if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/*  865 */         if (Float2CharOpenHashMap.this.value[pos] == v) {
/*  866 */           Float2CharOpenHashMap.this.removeEntry(pos);
/*  867 */           return true;
/*      */         } 
/*  869 */         return false;
/*      */       } 
/*      */       while (true) {
/*  872 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2CharOpenHashMap.this.mask]) == 0)
/*  873 */           return false; 
/*  874 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k) && 
/*  875 */           Float2CharOpenHashMap.this.value[pos] == v) {
/*  876 */           Float2CharOpenHashMap.this.removeEntry(pos);
/*  877 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  884 */       return Float2CharOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  888 */       Float2CharOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2CharMap.Entry> consumer) {
/*  893 */       if (Float2CharOpenHashMap.this.containsNullKey)
/*  894 */         consumer.accept(new AbstractFloat2CharMap.BasicEntry(Float2CharOpenHashMap.this.key[Float2CharOpenHashMap.this.n], Float2CharOpenHashMap.this.value[Float2CharOpenHashMap.this.n])); 
/*  895 */       for (int pos = Float2CharOpenHashMap.this.n; pos-- != 0;) {
/*  896 */         if (Float.floatToIntBits(Float2CharOpenHashMap.this.key[pos]) != 0)
/*  897 */           consumer.accept(new AbstractFloat2CharMap.BasicEntry(Float2CharOpenHashMap.this.key[pos], Float2CharOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2CharMap.Entry> consumer) {
/*  902 */       AbstractFloat2CharMap.BasicEntry entry = new AbstractFloat2CharMap.BasicEntry();
/*  903 */       if (Float2CharOpenHashMap.this.containsNullKey) {
/*  904 */         entry.key = Float2CharOpenHashMap.this.key[Float2CharOpenHashMap.this.n];
/*  905 */         entry.value = Float2CharOpenHashMap.this.value[Float2CharOpenHashMap.this.n];
/*  906 */         consumer.accept(entry);
/*      */       } 
/*  908 */       for (int pos = Float2CharOpenHashMap.this.n; pos-- != 0;) {
/*  909 */         if (Float.floatToIntBits(Float2CharOpenHashMap.this.key[pos]) != 0) {
/*  910 */           entry.key = Float2CharOpenHashMap.this.key[pos];
/*  911 */           entry.value = Float2CharOpenHashMap.this.value[pos];
/*  912 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Float2CharMap.FastEntrySet float2CharEntrySet() {
/*  918 */     if (this.entries == null)
/*  919 */       this.entries = new MapEntrySet(); 
/*  920 */     return this.entries;
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
/*      */     implements FloatIterator
/*      */   {
/*      */     public float nextFloat() {
/*  937 */       return Float2CharOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSet { private KeySet() {}
/*      */     
/*      */     public FloatIterator iterator() {
/*  943 */       return new Float2CharOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/*  948 */       if (Float2CharOpenHashMap.this.containsNullKey)
/*  949 */         consumer.accept(Float2CharOpenHashMap.this.key[Float2CharOpenHashMap.this.n]); 
/*  950 */       for (int pos = Float2CharOpenHashMap.this.n; pos-- != 0; ) {
/*  951 */         float k = Float2CharOpenHashMap.this.key[pos];
/*  952 */         if (Float.floatToIntBits(k) != 0)
/*  953 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  958 */       return Float2CharOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(float k) {
/*  962 */       return Float2CharOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(float k) {
/*  966 */       int oldSize = Float2CharOpenHashMap.this.size;
/*  967 */       Float2CharOpenHashMap.this.remove(k);
/*  968 */       return (Float2CharOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  972 */       Float2CharOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public FloatSet keySet() {
/*  977 */     if (this.keys == null)
/*  978 */       this.keys = new KeySet(); 
/*  979 */     return this.keys;
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
/*  996 */       return Float2CharOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public CharCollection values() {
/* 1001 */     if (this.values == null)
/* 1002 */       this.values = (CharCollection)new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/* 1005 */             return new Float2CharOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1009 */             return Float2CharOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(char v) {
/* 1013 */             return Float2CharOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1017 */             Float2CharOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1022 */             if (Float2CharOpenHashMap.this.containsNullKey)
/* 1023 */               consumer.accept(Float2CharOpenHashMap.this.value[Float2CharOpenHashMap.this.n]); 
/* 1024 */             for (int pos = Float2CharOpenHashMap.this.n; pos-- != 0;) {
/* 1025 */               if (Float.floatToIntBits(Float2CharOpenHashMap.this.key[pos]) != 0)
/* 1026 */                 consumer.accept(Float2CharOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1029 */     return this.values;
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
/* 1046 */     return trim(this.size);
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
/* 1070 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1071 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1072 */       return true; 
/*      */     try {
/* 1074 */       rehash(l);
/* 1075 */     } catch (OutOfMemoryError cantDoIt) {
/* 1076 */       return false;
/*      */     } 
/* 1078 */     return true;
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
/* 1094 */     float[] key = this.key;
/* 1095 */     char[] value = this.value;
/* 1096 */     int mask = newN - 1;
/* 1097 */     float[] newKey = new float[newN + 1];
/* 1098 */     char[] newValue = new char[newN + 1];
/* 1099 */     int i = this.n;
/* 1100 */     for (int j = realSize(); j-- != 0; ) {
/* 1101 */       while (Float.floatToIntBits(key[--i]) == 0); int pos;
/* 1102 */       if (Float.floatToIntBits(newKey[
/* 1103 */             pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask]) != 0)
/* 1104 */         while (Float.floatToIntBits(newKey[pos = pos + 1 & mask]) != 0); 
/* 1105 */       newKey[pos] = key[i];
/* 1106 */       newValue[pos] = value[i];
/*      */     } 
/* 1108 */     newValue[newN] = value[this.n];
/* 1109 */     this.n = newN;
/* 1110 */     this.mask = mask;
/* 1111 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1112 */     this.key = newKey;
/* 1113 */     this.value = newValue;
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
/*      */   public Float2CharOpenHashMap clone() {
/*      */     Float2CharOpenHashMap c;
/*      */     try {
/* 1130 */       c = (Float2CharOpenHashMap)super.clone();
/* 1131 */     } catch (CloneNotSupportedException cantHappen) {
/* 1132 */       throw new InternalError();
/*      */     } 
/* 1134 */     c.keys = null;
/* 1135 */     c.values = null;
/* 1136 */     c.entries = null;
/* 1137 */     c.containsNullKey = this.containsNullKey;
/* 1138 */     c.key = (float[])this.key.clone();
/* 1139 */     c.value = (char[])this.value.clone();
/* 1140 */     return c;
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
/* 1153 */     int h = 0;
/* 1154 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1155 */       while (Float.floatToIntBits(this.key[i]) == 0)
/* 1156 */         i++; 
/* 1157 */       t = HashCommon.float2int(this.key[i]);
/* 1158 */       t ^= this.value[i];
/* 1159 */       h += t;
/* 1160 */       i++;
/*      */     } 
/*      */     
/* 1163 */     if (this.containsNullKey)
/* 1164 */       h += this.value[this.n]; 
/* 1165 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1168 */     float[] key = this.key;
/* 1169 */     char[] value = this.value;
/* 1170 */     MapIterator i = new MapIterator();
/* 1171 */     s.defaultWriteObject();
/* 1172 */     for (int j = this.size; j-- != 0; ) {
/* 1173 */       int e = i.nextEntry();
/* 1174 */       s.writeFloat(key[e]);
/* 1175 */       s.writeChar(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1180 */     s.defaultReadObject();
/* 1181 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1182 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1183 */     this.mask = this.n - 1;
/* 1184 */     float[] key = this.key = new float[this.n + 1];
/* 1185 */     char[] value = this.value = new char[this.n + 1];
/*      */ 
/*      */     
/* 1188 */     for (int i = this.size; i-- != 0; ) {
/* 1189 */       int pos; float k = s.readFloat();
/* 1190 */       char v = s.readChar();
/* 1191 */       if (Float.floatToIntBits(k) == 0) {
/* 1192 */         pos = this.n;
/* 1193 */         this.containsNullKey = true;
/*      */       } else {
/* 1195 */         pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
/* 1196 */         while (Float.floatToIntBits(key[pos]) != 0)
/* 1197 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1199 */       key[pos] = k;
/* 1200 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2CharOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */