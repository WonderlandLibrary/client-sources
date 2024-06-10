/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterator;
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
/*      */ import java.util.function.ToDoubleFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Reference2FloatOpenCustomHashMap<K>
/*      */   extends AbstractReference2FloatMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient float[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected Hash.Strategy<? super K> strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Reference2FloatMap.FastEntrySet<K> entries;
/*      */   protected transient ReferenceSet<K> keys;
/*      */   protected transient FloatCollection values;
/*      */   
/*      */   public Reference2FloatOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
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
/*  116 */     this.value = new float[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2FloatOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
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
/*      */   public Reference2FloatOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
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
/*      */   public Reference2FloatOpenCustomHashMap(Map<? extends K, ? extends Float> m, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Reference2FloatOpenCustomHashMap(Map<? extends K, ? extends Float> m, Hash.Strategy<? super K> strategy) {
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
/*      */   public Reference2FloatOpenCustomHashMap(Reference2FloatMap<K> m, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Reference2FloatOpenCustomHashMap(Reference2FloatMap<K> m, Hash.Strategy<? super K> strategy) {
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
/*      */   public Reference2FloatOpenCustomHashMap(K[] k, float[] v, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Reference2FloatOpenCustomHashMap(K[] k, float[] v, Hash.Strategy<? super K> strategy) {
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
/*      */   private float removeEntry(int pos) {
/*  257 */     float oldValue = this.value[pos];
/*  258 */     this.size--;
/*  259 */     shiftKeys(pos);
/*  260 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  261 */       rehash(this.n / 2); 
/*  262 */     return oldValue;
/*      */   }
/*      */   private float removeNullEntry() {
/*  265 */     this.containsNullKey = false;
/*  266 */     this.key[this.n] = null;
/*  267 */     float oldValue = this.value[this.n];
/*  268 */     this.size--;
/*  269 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  270 */       rehash(this.n / 2); 
/*  271 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Float> m) {
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
/*      */   private void insert(int pos, K k, float v) {
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
/*      */   public float put(K k, float v) {
/*  314 */     int pos = find(k);
/*  315 */     if (pos < 0) {
/*  316 */       insert(-pos - 1, k, v);
/*  317 */       return this.defRetValue;
/*      */     } 
/*  319 */     float oldValue = this.value[pos];
/*  320 */     this.value[pos] = v;
/*  321 */     return oldValue;
/*      */   }
/*      */   private float addToValue(int pos, float incr) {
/*  324 */     float oldValue = this.value[pos];
/*  325 */     this.value[pos] = oldValue + incr;
/*  326 */     return oldValue;
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
/*      */   public float addTo(K k, float incr) {
/*      */     int pos;
/*  346 */     if (this.strategy.equals(k, null)) {
/*  347 */       if (this.containsNullKey)
/*  348 */         return addToValue(this.n, incr); 
/*  349 */       pos = this.n;
/*  350 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  353 */       K[] key = this.key;
/*      */       K curr;
/*  355 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  356 */         if (this.strategy.equals(curr, k))
/*  357 */           return addToValue(pos, incr); 
/*  358 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  359 */           if (this.strategy.equals(curr, k))
/*  360 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  363 */     }  this.key[pos] = k;
/*  364 */     this.value[pos] = this.defRetValue + incr;
/*  365 */     if (this.size++ >= this.maxFill) {
/*  366 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  369 */     return this.defRetValue;
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
/*  382 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  384 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  386 */         if ((curr = key[pos]) == null) {
/*  387 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  390 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  391 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  393 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  395 */       key[last] = curr;
/*  396 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float removeFloat(Object k) {
/*  402 */     if (this.strategy.equals(k, null)) {
/*  403 */       if (this.containsNullKey)
/*  404 */         return removeNullEntry(); 
/*  405 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  408 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  411 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  412 */       return this.defRetValue; 
/*  413 */     if (this.strategy.equals(k, curr))
/*  414 */       return removeEntry(pos); 
/*      */     while (true) {
/*  416 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  417 */         return this.defRetValue; 
/*  418 */       if (this.strategy.equals(k, curr)) {
/*  419 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public float getFloat(Object k) {
/*  425 */     if (this.strategy.equals(k, null)) {
/*  426 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  428 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  431 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  432 */       return this.defRetValue; 
/*  433 */     if (this.strategy.equals(k, curr)) {
/*  434 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  437 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  438 */         return this.defRetValue; 
/*  439 */       if (this.strategy.equals(k, curr)) {
/*  440 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  446 */     if (this.strategy.equals(k, null)) {
/*  447 */       return this.containsNullKey;
/*      */     }
/*  449 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  452 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  453 */       return false; 
/*  454 */     if (this.strategy.equals(k, curr)) {
/*  455 */       return true;
/*      */     }
/*      */     while (true) {
/*  458 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  459 */         return false; 
/*  460 */       if (this.strategy.equals(k, curr))
/*  461 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(float v) {
/*  466 */     float[] value = this.value;
/*  467 */     K[] key = this.key;
/*  468 */     if (this.containsNullKey && Float.floatToIntBits(value[this.n]) == Float.floatToIntBits(v))
/*  469 */       return true; 
/*  470 */     for (int i = this.n; i-- != 0;) {
/*  471 */       if (key[i] != null && Float.floatToIntBits(value[i]) == Float.floatToIntBits(v))
/*  472 */         return true; 
/*  473 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getOrDefault(Object k, float defaultValue) {
/*  479 */     if (this.strategy.equals(k, null)) {
/*  480 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  482 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  485 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  486 */       return defaultValue; 
/*  487 */     if (this.strategy.equals(k, curr)) {
/*  488 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  491 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  492 */         return defaultValue; 
/*  493 */       if (this.strategy.equals(k, curr)) {
/*  494 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public float putIfAbsent(K k, float v) {
/*  500 */     int pos = find(k);
/*  501 */     if (pos >= 0)
/*  502 */       return this.value[pos]; 
/*  503 */     insert(-pos - 1, k, v);
/*  504 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, float v) {
/*  510 */     if (this.strategy.equals(k, null)) {
/*  511 */       if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
/*  512 */         removeNullEntry();
/*  513 */         return true;
/*      */       } 
/*  515 */       return false;
/*      */     } 
/*      */     
/*  518 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  521 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  522 */       return false; 
/*  523 */     if (this.strategy.equals(k, curr) && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  524 */       removeEntry(pos);
/*  525 */       return true;
/*      */     } 
/*      */     while (true) {
/*  528 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  529 */         return false; 
/*  530 */       if (this.strategy.equals(k, curr) && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  531 */         removeEntry(pos);
/*  532 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, float oldValue, float v) {
/*  539 */     int pos = find(k);
/*  540 */     if (pos < 0 || Float.floatToIntBits(oldValue) != Float.floatToIntBits(this.value[pos]))
/*  541 */       return false; 
/*  542 */     this.value[pos] = v;
/*  543 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public float replace(K k, float v) {
/*  548 */     int pos = find(k);
/*  549 */     if (pos < 0)
/*  550 */       return this.defRetValue; 
/*  551 */     float oldValue = this.value[pos];
/*  552 */     this.value[pos] = v;
/*  553 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public float computeFloatIfAbsent(K k, ToDoubleFunction<? super K> mappingFunction) {
/*  558 */     Objects.requireNonNull(mappingFunction);
/*  559 */     int pos = find(k);
/*  560 */     if (pos >= 0)
/*  561 */       return this.value[pos]; 
/*  562 */     float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(k));
/*  563 */     insert(-pos - 1, k, newValue);
/*  564 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeFloatIfPresent(K k, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/*  570 */     Objects.requireNonNull(remappingFunction);
/*  571 */     int pos = find(k);
/*  572 */     if (pos < 0)
/*  573 */       return this.defRetValue; 
/*  574 */     Float newValue = remappingFunction.apply(k, Float.valueOf(this.value[pos]));
/*  575 */     if (newValue == null) {
/*  576 */       if (this.strategy.equals(k, null)) {
/*  577 */         removeNullEntry();
/*      */       } else {
/*  579 */         removeEntry(pos);
/*  580 */       }  return this.defRetValue;
/*      */     } 
/*  582 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeFloat(K k, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/*  588 */     Objects.requireNonNull(remappingFunction);
/*  589 */     int pos = find(k);
/*  590 */     Float newValue = remappingFunction.apply(k, (pos >= 0) ? Float.valueOf(this.value[pos]) : null);
/*  591 */     if (newValue == null) {
/*  592 */       if (pos >= 0)
/*  593 */         if (this.strategy.equals(k, null)) {
/*  594 */           removeNullEntry();
/*      */         } else {
/*  596 */           removeEntry(pos);
/*      */         }  
/*  598 */       return this.defRetValue;
/*      */     } 
/*  600 */     float newVal = newValue.floatValue();
/*  601 */     if (pos < 0) {
/*  602 */       insert(-pos - 1, k, newVal);
/*  603 */       return newVal;
/*      */     } 
/*  605 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float mergeFloat(K k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  611 */     Objects.requireNonNull(remappingFunction);
/*  612 */     int pos = find(k);
/*  613 */     if (pos < 0) {
/*  614 */       insert(-pos - 1, k, v);
/*  615 */       return v;
/*      */     } 
/*  617 */     Float newValue = remappingFunction.apply(Float.valueOf(this.value[pos]), Float.valueOf(v));
/*  618 */     if (newValue == null) {
/*  619 */       if (this.strategy.equals(k, null)) {
/*  620 */         removeNullEntry();
/*      */       } else {
/*  622 */         removeEntry(pos);
/*  623 */       }  return this.defRetValue;
/*      */     } 
/*  625 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
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
/*  636 */     if (this.size == 0)
/*      */       return; 
/*  638 */     this.size = 0;
/*  639 */     this.containsNullKey = false;
/*  640 */     Arrays.fill((Object[])this.key, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  644 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  648 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Reference2FloatMap.Entry<K>, Map.Entry<K, Float>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  660 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  666 */       return Reference2FloatOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public float getFloatValue() {
/*  670 */       return Reference2FloatOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public float setValue(float v) {
/*  674 */       float oldValue = Reference2FloatOpenCustomHashMap.this.value[this.index];
/*  675 */       Reference2FloatOpenCustomHashMap.this.value[this.index] = v;
/*  676 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getValue() {
/*  686 */       return Float.valueOf(Reference2FloatOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float setValue(Float v) {
/*  696 */       return Float.valueOf(setValue(v.floatValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  701 */       if (!(o instanceof Map.Entry))
/*  702 */         return false; 
/*  703 */       Map.Entry<K, Float> e = (Map.Entry<K, Float>)o;
/*  704 */       return (Reference2FloatOpenCustomHashMap.this.strategy.equals(Reference2FloatOpenCustomHashMap.this.key[this.index], e.getKey()) && 
/*  705 */         Float.floatToIntBits(Reference2FloatOpenCustomHashMap.this.value[this.index]) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  709 */       return Reference2FloatOpenCustomHashMap.this.strategy.hashCode(Reference2FloatOpenCustomHashMap.this.key[this.index]) ^ HashCommon.float2int(Reference2FloatOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  713 */       return (new StringBuilder()).append(Reference2FloatOpenCustomHashMap.this.key[this.index]).append("=>").append(Reference2FloatOpenCustomHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  723 */     int pos = Reference2FloatOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  730 */     int last = -1;
/*      */     
/*  732 */     int c = Reference2FloatOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  736 */     boolean mustReturnNullKey = Reference2FloatOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ReferenceArrayList<K> wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  743 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  746 */       if (!hasNext())
/*  747 */         throw new NoSuchElementException(); 
/*  748 */       this.c--;
/*  749 */       if (this.mustReturnNullKey) {
/*  750 */         this.mustReturnNullKey = false;
/*  751 */         return this.last = Reference2FloatOpenCustomHashMap.this.n;
/*      */       } 
/*  753 */       K[] key = Reference2FloatOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  755 */         if (--this.pos < 0) {
/*      */           
/*  757 */           this.last = Integer.MIN_VALUE;
/*  758 */           K k = this.wrapped.get(-this.pos - 1);
/*  759 */           int p = HashCommon.mix(Reference2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2FloatOpenCustomHashMap.this.mask;
/*  760 */           while (!Reference2FloatOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  761 */             p = p + 1 & Reference2FloatOpenCustomHashMap.this.mask; 
/*  762 */           return p;
/*      */         } 
/*  764 */         if (key[this.pos] != null) {
/*  765 */           return this.last = this.pos;
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
/*  779 */       K[] key = Reference2FloatOpenCustomHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  781 */         pos = (last = pos) + 1 & Reference2FloatOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  783 */           if ((curr = key[pos]) == null) {
/*  784 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  787 */           int slot = HashCommon.mix(Reference2FloatOpenCustomHashMap.this.strategy.hashCode(curr)) & Reference2FloatOpenCustomHashMap.this.mask;
/*  788 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  790 */           pos = pos + 1 & Reference2FloatOpenCustomHashMap.this.mask;
/*      */         } 
/*  792 */         if (pos < last) {
/*  793 */           if (this.wrapped == null)
/*  794 */             this.wrapped = new ReferenceArrayList<>(2); 
/*  795 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  797 */         key[last] = curr;
/*  798 */         Reference2FloatOpenCustomHashMap.this.value[last] = Reference2FloatOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  802 */       if (this.last == -1)
/*  803 */         throw new IllegalStateException(); 
/*  804 */       if (this.last == Reference2FloatOpenCustomHashMap.this.n) {
/*  805 */         Reference2FloatOpenCustomHashMap.this.containsNullKey = false;
/*  806 */         Reference2FloatOpenCustomHashMap.this.key[Reference2FloatOpenCustomHashMap.this.n] = null;
/*  807 */       } else if (this.pos >= 0) {
/*  808 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  811 */         Reference2FloatOpenCustomHashMap.this.removeFloat(this.wrapped.set(-this.pos - 1, null));
/*  812 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  815 */       Reference2FloatOpenCustomHashMap.this.size--;
/*  816 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  821 */       int i = n;
/*  822 */       while (i-- != 0 && hasNext())
/*  823 */         nextEntry(); 
/*  824 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Reference2FloatMap.Entry<K>> { private Reference2FloatOpenCustomHashMap<K>.MapEntry entry;
/*      */     
/*      */     public Reference2FloatOpenCustomHashMap<K>.MapEntry next() {
/*  831 */       return this.entry = new Reference2FloatOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  835 */       super.remove();
/*  836 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Reference2FloatMap.Entry<K>> { private FastEntryIterator() {
/*  840 */       this.entry = new Reference2FloatOpenCustomHashMap.MapEntry();
/*      */     } private final Reference2FloatOpenCustomHashMap<K>.MapEntry entry;
/*      */     public Reference2FloatOpenCustomHashMap<K>.MapEntry next() {
/*  843 */       this.entry.index = nextEntry();
/*  844 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Reference2FloatMap.Entry<K>> implements Reference2FloatMap.FastEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Reference2FloatMap.Entry<K>> iterator() {
/*  850 */       return new Reference2FloatOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Reference2FloatMap.Entry<K>> fastIterator() {
/*  854 */       return new Reference2FloatOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  859 */       if (!(o instanceof Map.Entry))
/*  860 */         return false; 
/*  861 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  862 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/*  863 */         return false; 
/*  864 */       K k = (K)e.getKey();
/*  865 */       float v = ((Float)e.getValue()).floatValue();
/*  866 */       if (Reference2FloatOpenCustomHashMap.this.strategy.equals(k, null)) {
/*  867 */         return (Reference2FloatOpenCustomHashMap.this.containsNullKey && 
/*  868 */           Float.floatToIntBits(Reference2FloatOpenCustomHashMap.this.value[Reference2FloatOpenCustomHashMap.this.n]) == Float.floatToIntBits(v));
/*      */       }
/*  870 */       K[] key = Reference2FloatOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  873 */       if ((curr = key[pos = HashCommon.mix(Reference2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2FloatOpenCustomHashMap.this.mask]) == null)
/*  874 */         return false; 
/*  875 */       if (Reference2FloatOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  876 */         return (Float.floatToIntBits(Reference2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       }
/*      */       while (true) {
/*  879 */         if ((curr = key[pos = pos + 1 & Reference2FloatOpenCustomHashMap.this.mask]) == null)
/*  880 */           return false; 
/*  881 */         if (Reference2FloatOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  882 */           return (Float.floatToIntBits(Reference2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  888 */       if (!(o instanceof Map.Entry))
/*  889 */         return false; 
/*  890 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  891 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/*  892 */         return false; 
/*  893 */       K k = (K)e.getKey();
/*  894 */       float v = ((Float)e.getValue()).floatValue();
/*  895 */       if (Reference2FloatOpenCustomHashMap.this.strategy.equals(k, null)) {
/*  896 */         if (Reference2FloatOpenCustomHashMap.this.containsNullKey && Float.floatToIntBits(Reference2FloatOpenCustomHashMap.this.value[Reference2FloatOpenCustomHashMap.this.n]) == Float.floatToIntBits(v)) {
/*  897 */           Reference2FloatOpenCustomHashMap.this.removeNullEntry();
/*  898 */           return true;
/*      */         } 
/*  900 */         return false;
/*      */       } 
/*      */       
/*  903 */       K[] key = Reference2FloatOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  906 */       if ((curr = key[pos = HashCommon.mix(Reference2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2FloatOpenCustomHashMap.this.mask]) == null)
/*  907 */         return false; 
/*  908 */       if (Reference2FloatOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  909 */         if (Float.floatToIntBits(Reference2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/*  910 */           Reference2FloatOpenCustomHashMap.this.removeEntry(pos);
/*  911 */           return true;
/*      */         } 
/*  913 */         return false;
/*      */       } 
/*      */       while (true) {
/*  916 */         if ((curr = key[pos = pos + 1 & Reference2FloatOpenCustomHashMap.this.mask]) == null)
/*  917 */           return false; 
/*  918 */         if (Reference2FloatOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  919 */           Float.floatToIntBits(Reference2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/*  920 */           Reference2FloatOpenCustomHashMap.this.removeEntry(pos);
/*  921 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  928 */       return Reference2FloatOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  932 */       Reference2FloatOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2FloatMap.Entry<K>> consumer) {
/*  937 */       if (Reference2FloatOpenCustomHashMap.this.containsNullKey)
/*  938 */         consumer.accept(new AbstractReference2FloatMap.BasicEntry<>(Reference2FloatOpenCustomHashMap.this.key[Reference2FloatOpenCustomHashMap.this.n], Reference2FloatOpenCustomHashMap.this.value[Reference2FloatOpenCustomHashMap.this.n])); 
/*  939 */       for (int pos = Reference2FloatOpenCustomHashMap.this.n; pos-- != 0;) {
/*  940 */         if (Reference2FloatOpenCustomHashMap.this.key[pos] != null)
/*  941 */           consumer.accept(new AbstractReference2FloatMap.BasicEntry<>(Reference2FloatOpenCustomHashMap.this.key[pos], Reference2FloatOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2FloatMap.Entry<K>> consumer) {
/*  946 */       AbstractReference2FloatMap.BasicEntry<K> entry = new AbstractReference2FloatMap.BasicEntry<>();
/*  947 */       if (Reference2FloatOpenCustomHashMap.this.containsNullKey) {
/*  948 */         entry.key = Reference2FloatOpenCustomHashMap.this.key[Reference2FloatOpenCustomHashMap.this.n];
/*  949 */         entry.value = Reference2FloatOpenCustomHashMap.this.value[Reference2FloatOpenCustomHashMap.this.n];
/*  950 */         consumer.accept(entry);
/*      */       } 
/*  952 */       for (int pos = Reference2FloatOpenCustomHashMap.this.n; pos-- != 0;) {
/*  953 */         if (Reference2FloatOpenCustomHashMap.this.key[pos] != null) {
/*  954 */           entry.key = Reference2FloatOpenCustomHashMap.this.key[pos];
/*  955 */           entry.value = Reference2FloatOpenCustomHashMap.this.value[pos];
/*  956 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Reference2FloatMap.FastEntrySet<K> reference2FloatEntrySet() {
/*  962 */     if (this.entries == null)
/*  963 */       this.entries = new MapEntrySet(); 
/*  964 */     return this.entries;
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
/*  981 */       return Reference2FloatOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/*  987 */       return new Reference2FloatOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/*  992 */       if (Reference2FloatOpenCustomHashMap.this.containsNullKey)
/*  993 */         consumer.accept(Reference2FloatOpenCustomHashMap.this.key[Reference2FloatOpenCustomHashMap.this.n]); 
/*  994 */       for (int pos = Reference2FloatOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  995 */         K k = Reference2FloatOpenCustomHashMap.this.key[pos];
/*  996 */         if (k != null)
/*  997 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1002 */       return Reference2FloatOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1006 */       return Reference2FloatOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1010 */       int oldSize = Reference2FloatOpenCustomHashMap.this.size;
/* 1011 */       Reference2FloatOpenCustomHashMap.this.removeFloat(k);
/* 1012 */       return (Reference2FloatOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1016 */       Reference2FloatOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ReferenceSet<K> keySet() {
/* 1021 */     if (this.keys == null)
/* 1022 */       this.keys = new KeySet(); 
/* 1023 */     return this.keys;
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
/*      */     implements FloatIterator
/*      */   {
/*      */     public float nextFloat() {
/* 1040 */       return Reference2FloatOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public FloatCollection values() {
/* 1045 */     if (this.values == null)
/* 1046 */       this.values = (FloatCollection)new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1049 */             return new Reference2FloatOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1053 */             return Reference2FloatOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(float v) {
/* 1057 */             return Reference2FloatOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1061 */             Reference2FloatOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(DoubleConsumer consumer)
/*      */           {
/* 1066 */             if (Reference2FloatOpenCustomHashMap.this.containsNullKey)
/* 1067 */               consumer.accept(Reference2FloatOpenCustomHashMap.this.value[Reference2FloatOpenCustomHashMap.this.n]); 
/* 1068 */             for (int pos = Reference2FloatOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1069 */               if (Reference2FloatOpenCustomHashMap.this.key[pos] != null)
/* 1070 */                 consumer.accept(Reference2FloatOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1073 */     return this.values;
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
/* 1090 */     return trim(this.size);
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
/* 1114 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1115 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1116 */       return true; 
/*      */     try {
/* 1118 */       rehash(l);
/* 1119 */     } catch (OutOfMemoryError cantDoIt) {
/* 1120 */       return false;
/*      */     } 
/* 1122 */     return true;
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
/* 1138 */     K[] key = this.key;
/* 1139 */     float[] value = this.value;
/* 1140 */     int mask = newN - 1;
/* 1141 */     K[] newKey = (K[])new Object[newN + 1];
/* 1142 */     float[] newValue = new float[newN + 1];
/* 1143 */     int i = this.n;
/* 1144 */     for (int j = realSize(); j-- != 0; ) {
/* 1145 */       while (key[--i] == null); int pos;
/* 1146 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != null)
/* 1147 */         while (newKey[pos = pos + 1 & mask] != null); 
/* 1148 */       newKey[pos] = key[i];
/* 1149 */       newValue[pos] = value[i];
/*      */     } 
/* 1151 */     newValue[newN] = value[this.n];
/* 1152 */     this.n = newN;
/* 1153 */     this.mask = mask;
/* 1154 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1155 */     this.key = newKey;
/* 1156 */     this.value = newValue;
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
/*      */   public Reference2FloatOpenCustomHashMap<K> clone() {
/*      */     Reference2FloatOpenCustomHashMap<K> c;
/*      */     try {
/* 1173 */       c = (Reference2FloatOpenCustomHashMap<K>)super.clone();
/* 1174 */     } catch (CloneNotSupportedException cantHappen) {
/* 1175 */       throw new InternalError();
/*      */     } 
/* 1177 */     c.keys = null;
/* 1178 */     c.values = null;
/* 1179 */     c.entries = null;
/* 1180 */     c.containsNullKey = this.containsNullKey;
/* 1181 */     c.key = (K[])this.key.clone();
/* 1182 */     c.value = (float[])this.value.clone();
/* 1183 */     c.strategy = this.strategy;
/* 1184 */     return c;
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
/* 1197 */     int h = 0;
/* 1198 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1199 */       while (this.key[i] == null)
/* 1200 */         i++; 
/* 1201 */       if (this != this.key[i])
/* 1202 */         t = this.strategy.hashCode(this.key[i]); 
/* 1203 */       t ^= HashCommon.float2int(this.value[i]);
/* 1204 */       h += t;
/* 1205 */       i++;
/*      */     } 
/*      */     
/* 1208 */     if (this.containsNullKey)
/* 1209 */       h += HashCommon.float2int(this.value[this.n]); 
/* 1210 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1213 */     K[] key = this.key;
/* 1214 */     float[] value = this.value;
/* 1215 */     MapIterator i = new MapIterator();
/* 1216 */     s.defaultWriteObject();
/* 1217 */     for (int j = this.size; j-- != 0; ) {
/* 1218 */       int e = i.nextEntry();
/* 1219 */       s.writeObject(key[e]);
/* 1220 */       s.writeFloat(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1225 */     s.defaultReadObject();
/* 1226 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1227 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1228 */     this.mask = this.n - 1;
/* 1229 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1230 */     float[] value = this.value = new float[this.n + 1];
/*      */ 
/*      */     
/* 1233 */     for (int i = this.size; i-- != 0; ) {
/* 1234 */       int pos; K k = (K)s.readObject();
/* 1235 */       float v = s.readFloat();
/* 1236 */       if (this.strategy.equals(k, null)) {
/* 1237 */         pos = this.n;
/* 1238 */         this.containsNullKey = true;
/*      */       } else {
/* 1240 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1241 */         while (key[pos] != null)
/* 1242 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1244 */       key[pos] = k;
/* 1245 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2FloatOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */