/*      */ package it.unimi.dsi.fastutil.bytes;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
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
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.IntFunction;
/*      */ import java.util.function.IntToDoubleFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Byte2DoubleOpenHashMap
/*      */   extends AbstractByte2DoubleMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient byte[] key;
/*      */   protected transient double[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Byte2DoubleMap.FastEntrySet entries;
/*      */   protected transient ByteSet keys;
/*      */   protected transient DoubleCollection values;
/*      */   
/*      */   public Byte2DoubleOpenHashMap(int expected, float f) {
/*   96 */     if (f <= 0.0F || f > 1.0F)
/*   97 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*   98 */     if (expected < 0)
/*   99 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  100 */     this.f = f;
/*  101 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  102 */     this.mask = this.n - 1;
/*  103 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  104 */     this.key = new byte[this.n + 1];
/*  105 */     this.value = new double[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2DoubleOpenHashMap(int expected) {
/*  114 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2DoubleOpenHashMap() {
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
/*      */   public Byte2DoubleOpenHashMap(Map<? extends Byte, ? extends Double> m, float f) {
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
/*      */   public Byte2DoubleOpenHashMap(Map<? extends Byte, ? extends Double> m) {
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
/*      */   public Byte2DoubleOpenHashMap(Byte2DoubleMap m, float f) {
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
/*      */   public Byte2DoubleOpenHashMap(Byte2DoubleMap m) {
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
/*      */   public Byte2DoubleOpenHashMap(byte[] k, double[] v, float f) {
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
/*      */   public Byte2DoubleOpenHashMap(byte[] k, double[] v) {
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
/*      */   private double removeEntry(int pos) {
/*  217 */     double oldValue = this.value[pos];
/*  218 */     this.size--;
/*  219 */     shiftKeys(pos);
/*  220 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  221 */       rehash(this.n / 2); 
/*  222 */     return oldValue;
/*      */   }
/*      */   private double removeNullEntry() {
/*  225 */     this.containsNullKey = false;
/*  226 */     double oldValue = this.value[this.n];
/*  227 */     this.size--;
/*  228 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  229 */       rehash(this.n / 2); 
/*  230 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Byte, ? extends Double> m) {
/*  234 */     if (this.f <= 0.5D) {
/*  235 */       ensureCapacity(m.size());
/*      */     } else {
/*  237 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  239 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(byte k) {
/*  243 */     if (k == 0) {
/*  244 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  246 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  249 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  250 */       return -(pos + 1); 
/*  251 */     if (k == curr) {
/*  252 */       return pos;
/*      */     }
/*      */     while (true) {
/*  255 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  256 */         return -(pos + 1); 
/*  257 */       if (k == curr)
/*  258 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, byte k, double v) {
/*  262 */     if (pos == this.n)
/*  263 */       this.containsNullKey = true; 
/*  264 */     this.key[pos] = k;
/*  265 */     this.value[pos] = v;
/*  266 */     if (this.size++ >= this.maxFill) {
/*  267 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public double put(byte k, double v) {
/*  273 */     int pos = find(k);
/*  274 */     if (pos < 0) {
/*  275 */       insert(-pos - 1, k, v);
/*  276 */       return this.defRetValue;
/*      */     } 
/*  278 */     double oldValue = this.value[pos];
/*  279 */     this.value[pos] = v;
/*  280 */     return oldValue;
/*      */   }
/*      */   private double addToValue(int pos, double incr) {
/*  283 */     double oldValue = this.value[pos];
/*  284 */     this.value[pos] = oldValue + incr;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double addTo(byte k, double incr) {
/*      */     int pos;
/*  305 */     if (k == 0) {
/*  306 */       if (this.containsNullKey)
/*  307 */         return addToValue(this.n, incr); 
/*  308 */       pos = this.n;
/*  309 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  312 */       byte[] key = this.key;
/*      */       byte curr;
/*  314 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  315 */         if (curr == k)
/*  316 */           return addToValue(pos, incr); 
/*  317 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  318 */           if (curr == k)
/*  319 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  322 */     }  this.key[pos] = k;
/*  323 */     this.value[pos] = this.defRetValue + incr;
/*  324 */     if (this.size++ >= this.maxFill) {
/*  325 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  328 */     return this.defRetValue;
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
/*  341 */     byte[] key = this.key; while (true) {
/*      */       byte curr; int last;
/*  343 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  345 */         if ((curr = key[pos]) == 0) {
/*  346 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  349 */         int slot = HashCommon.mix(curr) & this.mask;
/*  350 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  352 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  354 */       key[last] = curr;
/*  355 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double remove(byte k) {
/*  361 */     if (k == 0) {
/*  362 */       if (this.containsNullKey)
/*  363 */         return removeNullEntry(); 
/*  364 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  367 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  370 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  371 */       return this.defRetValue; 
/*  372 */     if (k == curr)
/*  373 */       return removeEntry(pos); 
/*      */     while (true) {
/*  375 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  376 */         return this.defRetValue; 
/*  377 */       if (k == curr) {
/*  378 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double get(byte k) {
/*  384 */     if (k == 0) {
/*  385 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  387 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  390 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  391 */       return this.defRetValue; 
/*  392 */     if (k == curr) {
/*  393 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  396 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  397 */         return this.defRetValue; 
/*  398 */       if (k == curr) {
/*  399 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(byte k) {
/*  405 */     if (k == 0) {
/*  406 */       return this.containsNullKey;
/*      */     }
/*  408 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  411 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  412 */       return false; 
/*  413 */     if (k == curr) {
/*  414 */       return true;
/*      */     }
/*      */     while (true) {
/*  417 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  418 */         return false; 
/*  419 */       if (k == curr)
/*  420 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(double v) {
/*  425 */     double[] value = this.value;
/*  426 */     byte[] key = this.key;
/*  427 */     if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v))
/*  428 */       return true; 
/*  429 */     for (int i = this.n; i-- != 0;) {
/*  430 */       if (key[i] != 0 && Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v))
/*  431 */         return true; 
/*  432 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrDefault(byte k, double defaultValue) {
/*  438 */     if (k == 0) {
/*  439 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  441 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  444 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  445 */       return defaultValue; 
/*  446 */     if (k == curr) {
/*  447 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  450 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  451 */         return defaultValue; 
/*  452 */       if (k == curr) {
/*  453 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double putIfAbsent(byte k, double v) {
/*  459 */     int pos = find(k);
/*  460 */     if (pos >= 0)
/*  461 */       return this.value[pos]; 
/*  462 */     insert(-pos - 1, k, v);
/*  463 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(byte k, double v) {
/*  469 */     if (k == 0) {
/*  470 */       if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
/*  471 */         removeNullEntry();
/*  472 */         return true;
/*      */       } 
/*  474 */       return false;
/*      */     } 
/*      */     
/*  477 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  480 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  481 */       return false; 
/*  482 */     if (k == curr && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  483 */       removeEntry(pos);
/*  484 */       return true;
/*      */     } 
/*      */     while (true) {
/*  487 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  488 */         return false; 
/*  489 */       if (k == curr && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  490 */         removeEntry(pos);
/*  491 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(byte k, double oldValue, double v) {
/*  498 */     int pos = find(k);
/*  499 */     if (pos < 0 || Double.doubleToLongBits(oldValue) != Double.doubleToLongBits(this.value[pos]))
/*  500 */       return false; 
/*  501 */     this.value[pos] = v;
/*  502 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public double replace(byte k, double v) {
/*  507 */     int pos = find(k);
/*  508 */     if (pos < 0)
/*  509 */       return this.defRetValue; 
/*  510 */     double oldValue = this.value[pos];
/*  511 */     this.value[pos] = v;
/*  512 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public double computeIfAbsent(byte k, IntToDoubleFunction mappingFunction) {
/*  517 */     Objects.requireNonNull(mappingFunction);
/*  518 */     int pos = find(k);
/*  519 */     if (pos >= 0)
/*  520 */       return this.value[pos]; 
/*  521 */     double newValue = mappingFunction.applyAsDouble(k);
/*  522 */     insert(-pos - 1, k, newValue);
/*  523 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfAbsentNullable(byte k, IntFunction<? extends Double> mappingFunction) {
/*  529 */     Objects.requireNonNull(mappingFunction);
/*  530 */     int pos = find(k);
/*  531 */     if (pos >= 0)
/*  532 */       return this.value[pos]; 
/*  533 */     Double newValue = mappingFunction.apply(k);
/*  534 */     if (newValue == null)
/*  535 */       return this.defRetValue; 
/*  536 */     double v = newValue.doubleValue();
/*  537 */     insert(-pos - 1, k, v);
/*  538 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfPresent(byte k, BiFunction<? super Byte, ? super Double, ? extends Double> remappingFunction) {
/*  544 */     Objects.requireNonNull(remappingFunction);
/*  545 */     int pos = find(k);
/*  546 */     if (pos < 0)
/*  547 */       return this.defRetValue; 
/*  548 */     Double newValue = remappingFunction.apply(Byte.valueOf(k), Double.valueOf(this.value[pos]));
/*  549 */     if (newValue == null) {
/*  550 */       if (k == 0) {
/*  551 */         removeNullEntry();
/*      */       } else {
/*  553 */         removeEntry(pos);
/*  554 */       }  return this.defRetValue;
/*      */     } 
/*  556 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double compute(byte k, BiFunction<? super Byte, ? super Double, ? extends Double> remappingFunction) {
/*  562 */     Objects.requireNonNull(remappingFunction);
/*  563 */     int pos = find(k);
/*  564 */     Double newValue = remappingFunction.apply(Byte.valueOf(k), (pos >= 0) ? Double.valueOf(this.value[pos]) : null);
/*  565 */     if (newValue == null) {
/*  566 */       if (pos >= 0)
/*  567 */         if (k == 0) {
/*  568 */           removeNullEntry();
/*      */         } else {
/*  570 */           removeEntry(pos);
/*      */         }  
/*  572 */       return this.defRetValue;
/*      */     } 
/*  574 */     double newVal = newValue.doubleValue();
/*  575 */     if (pos < 0) {
/*  576 */       insert(-pos - 1, k, newVal);
/*  577 */       return newVal;
/*      */     } 
/*  579 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double merge(byte k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  585 */     Objects.requireNonNull(remappingFunction);
/*  586 */     int pos = find(k);
/*  587 */     if (pos < 0) {
/*  588 */       insert(-pos - 1, k, v);
/*  589 */       return v;
/*      */     } 
/*  591 */     Double newValue = remappingFunction.apply(Double.valueOf(this.value[pos]), Double.valueOf(v));
/*  592 */     if (newValue == null) {
/*  593 */       if (k == 0) {
/*  594 */         removeNullEntry();
/*      */       } else {
/*  596 */         removeEntry(pos);
/*  597 */       }  return this.defRetValue;
/*      */     } 
/*  599 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
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
/*  610 */     if (this.size == 0)
/*      */       return; 
/*  612 */     this.size = 0;
/*  613 */     this.containsNullKey = false;
/*  614 */     Arrays.fill(this.key, (byte)0);
/*      */   }
/*      */   
/*      */   public int size() {
/*  618 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  622 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Byte2DoubleMap.Entry, Map.Entry<Byte, Double>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  634 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public byte getByteKey() {
/*  640 */       return Byte2DoubleOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public double getDoubleValue() {
/*  644 */       return Byte2DoubleOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public double setValue(double v) {
/*  648 */       double oldValue = Byte2DoubleOpenHashMap.this.value[this.index];
/*  649 */       Byte2DoubleOpenHashMap.this.value[this.index] = v;
/*  650 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte getKey() {
/*  660 */       return Byte.valueOf(Byte2DoubleOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getValue() {
/*  670 */       return Double.valueOf(Byte2DoubleOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double setValue(Double v) {
/*  680 */       return Double.valueOf(setValue(v.doubleValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  685 */       if (!(o instanceof Map.Entry))
/*  686 */         return false; 
/*  687 */       Map.Entry<Byte, Double> e = (Map.Entry<Byte, Double>)o;
/*  688 */       return (Byte2DoubleOpenHashMap.this.key[this.index] == ((Byte)e.getKey()).byteValue() && 
/*  689 */         Double.doubleToLongBits(Byte2DoubleOpenHashMap.this.value[this.index]) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  693 */       return Byte2DoubleOpenHashMap.this.key[this.index] ^ HashCommon.double2int(Byte2DoubleOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  697 */       return Byte2DoubleOpenHashMap.this.key[this.index] + "=>" + Byte2DoubleOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  707 */     int pos = Byte2DoubleOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  714 */     int last = -1;
/*      */     
/*  716 */     int c = Byte2DoubleOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  720 */     boolean mustReturnNullKey = Byte2DoubleOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ByteArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  727 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  730 */       if (!hasNext())
/*  731 */         throw new NoSuchElementException(); 
/*  732 */       this.c--;
/*  733 */       if (this.mustReturnNullKey) {
/*  734 */         this.mustReturnNullKey = false;
/*  735 */         return this.last = Byte2DoubleOpenHashMap.this.n;
/*      */       } 
/*  737 */       byte[] key = Byte2DoubleOpenHashMap.this.key;
/*      */       while (true) {
/*  739 */         if (--this.pos < 0) {
/*      */           
/*  741 */           this.last = Integer.MIN_VALUE;
/*  742 */           byte k = this.wrapped.getByte(-this.pos - 1);
/*  743 */           int p = HashCommon.mix(k) & Byte2DoubleOpenHashMap.this.mask;
/*  744 */           while (k != key[p])
/*  745 */             p = p + 1 & Byte2DoubleOpenHashMap.this.mask; 
/*  746 */           return p;
/*      */         } 
/*  748 */         if (key[this.pos] != 0) {
/*  749 */           return this.last = this.pos;
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
/*  763 */       byte[] key = Byte2DoubleOpenHashMap.this.key; while (true) {
/*      */         byte curr; int last;
/*  765 */         pos = (last = pos) + 1 & Byte2DoubleOpenHashMap.this.mask;
/*      */         while (true) {
/*  767 */           if ((curr = key[pos]) == 0) {
/*  768 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  771 */           int slot = HashCommon.mix(curr) & Byte2DoubleOpenHashMap.this.mask;
/*  772 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  774 */           pos = pos + 1 & Byte2DoubleOpenHashMap.this.mask;
/*      */         } 
/*  776 */         if (pos < last) {
/*  777 */           if (this.wrapped == null)
/*  778 */             this.wrapped = new ByteArrayList(2); 
/*  779 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  781 */         key[last] = curr;
/*  782 */         Byte2DoubleOpenHashMap.this.value[last] = Byte2DoubleOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  786 */       if (this.last == -1)
/*  787 */         throw new IllegalStateException(); 
/*  788 */       if (this.last == Byte2DoubleOpenHashMap.this.n) {
/*  789 */         Byte2DoubleOpenHashMap.this.containsNullKey = false;
/*  790 */       } else if (this.pos >= 0) {
/*  791 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  794 */         Byte2DoubleOpenHashMap.this.remove(this.wrapped.getByte(-this.pos - 1));
/*  795 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  798 */       Byte2DoubleOpenHashMap.this.size--;
/*  799 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  804 */       int i = n;
/*  805 */       while (i-- != 0 && hasNext())
/*  806 */         nextEntry(); 
/*  807 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Byte2DoubleMap.Entry> { private Byte2DoubleOpenHashMap.MapEntry entry;
/*      */     
/*      */     public Byte2DoubleOpenHashMap.MapEntry next() {
/*  814 */       return this.entry = new Byte2DoubleOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  818 */       super.remove();
/*  819 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Byte2DoubleMap.Entry> { private FastEntryIterator() {
/*  823 */       this.entry = new Byte2DoubleOpenHashMap.MapEntry();
/*      */     } private final Byte2DoubleOpenHashMap.MapEntry entry;
/*      */     public Byte2DoubleOpenHashMap.MapEntry next() {
/*  826 */       this.entry.index = nextEntry();
/*  827 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Byte2DoubleMap.Entry> implements Byte2DoubleMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Byte2DoubleMap.Entry> iterator() {
/*  833 */       return new Byte2DoubleOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Byte2DoubleMap.Entry> fastIterator() {
/*  837 */       return new Byte2DoubleOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  842 */       if (!(o instanceof Map.Entry))
/*  843 */         return false; 
/*  844 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  845 */       if (e.getKey() == null || !(e.getKey() instanceof Byte))
/*  846 */         return false; 
/*  847 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/*  848 */         return false; 
/*  849 */       byte k = ((Byte)e.getKey()).byteValue();
/*  850 */       double v = ((Double)e.getValue()).doubleValue();
/*  851 */       if (k == 0) {
/*  852 */         return (Byte2DoubleOpenHashMap.this.containsNullKey && 
/*  853 */           Double.doubleToLongBits(Byte2DoubleOpenHashMap.this.value[Byte2DoubleOpenHashMap.this.n]) == Double.doubleToLongBits(v));
/*      */       }
/*  855 */       byte[] key = Byte2DoubleOpenHashMap.this.key;
/*      */       byte curr;
/*      */       int pos;
/*  858 */       if ((curr = key[pos = HashCommon.mix(k) & Byte2DoubleOpenHashMap.this.mask]) == 0)
/*  859 */         return false; 
/*  860 */       if (k == curr) {
/*  861 */         return (Double.doubleToLongBits(Byte2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       }
/*      */       while (true) {
/*  864 */         if ((curr = key[pos = pos + 1 & Byte2DoubleOpenHashMap.this.mask]) == 0)
/*  865 */           return false; 
/*  866 */         if (k == curr) {
/*  867 */           return (Double.doubleToLongBits(Byte2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  873 */       if (!(o instanceof Map.Entry))
/*  874 */         return false; 
/*  875 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  876 */       if (e.getKey() == null || !(e.getKey() instanceof Byte))
/*  877 */         return false; 
/*  878 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/*  879 */         return false; 
/*  880 */       byte k = ((Byte)e.getKey()).byteValue();
/*  881 */       double v = ((Double)e.getValue()).doubleValue();
/*  882 */       if (k == 0) {
/*  883 */         if (Byte2DoubleOpenHashMap.this.containsNullKey && Double.doubleToLongBits(Byte2DoubleOpenHashMap.this.value[Byte2DoubleOpenHashMap.this.n]) == Double.doubleToLongBits(v)) {
/*  884 */           Byte2DoubleOpenHashMap.this.removeNullEntry();
/*  885 */           return true;
/*      */         } 
/*  887 */         return false;
/*      */       } 
/*      */       
/*  890 */       byte[] key = Byte2DoubleOpenHashMap.this.key;
/*      */       byte curr;
/*      */       int pos;
/*  893 */       if ((curr = key[pos = HashCommon.mix(k) & Byte2DoubleOpenHashMap.this.mask]) == 0)
/*  894 */         return false; 
/*  895 */       if (curr == k) {
/*  896 */         if (Double.doubleToLongBits(Byte2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/*  897 */           Byte2DoubleOpenHashMap.this.removeEntry(pos);
/*  898 */           return true;
/*      */         } 
/*  900 */         return false;
/*      */       } 
/*      */       while (true) {
/*  903 */         if ((curr = key[pos = pos + 1 & Byte2DoubleOpenHashMap.this.mask]) == 0)
/*  904 */           return false; 
/*  905 */         if (curr == k && 
/*  906 */           Double.doubleToLongBits(Byte2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/*  907 */           Byte2DoubleOpenHashMap.this.removeEntry(pos);
/*  908 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  915 */       return Byte2DoubleOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  919 */       Byte2DoubleOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Byte2DoubleMap.Entry> consumer) {
/*  924 */       if (Byte2DoubleOpenHashMap.this.containsNullKey)
/*  925 */         consumer.accept(new AbstractByte2DoubleMap.BasicEntry(Byte2DoubleOpenHashMap.this.key[Byte2DoubleOpenHashMap.this.n], Byte2DoubleOpenHashMap.this.value[Byte2DoubleOpenHashMap.this.n])); 
/*  926 */       for (int pos = Byte2DoubleOpenHashMap.this.n; pos-- != 0;) {
/*  927 */         if (Byte2DoubleOpenHashMap.this.key[pos] != 0)
/*  928 */           consumer.accept(new AbstractByte2DoubleMap.BasicEntry(Byte2DoubleOpenHashMap.this.key[pos], Byte2DoubleOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Byte2DoubleMap.Entry> consumer) {
/*  933 */       AbstractByte2DoubleMap.BasicEntry entry = new AbstractByte2DoubleMap.BasicEntry();
/*  934 */       if (Byte2DoubleOpenHashMap.this.containsNullKey) {
/*  935 */         entry.key = Byte2DoubleOpenHashMap.this.key[Byte2DoubleOpenHashMap.this.n];
/*  936 */         entry.value = Byte2DoubleOpenHashMap.this.value[Byte2DoubleOpenHashMap.this.n];
/*  937 */         consumer.accept(entry);
/*      */       } 
/*  939 */       for (int pos = Byte2DoubleOpenHashMap.this.n; pos-- != 0;) {
/*  940 */         if (Byte2DoubleOpenHashMap.this.key[pos] != 0) {
/*  941 */           entry.key = Byte2DoubleOpenHashMap.this.key[pos];
/*  942 */           entry.value = Byte2DoubleOpenHashMap.this.value[pos];
/*  943 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Byte2DoubleMap.FastEntrySet byte2DoubleEntrySet() {
/*  949 */     if (this.entries == null)
/*  950 */       this.entries = new MapEntrySet(); 
/*  951 */     return this.entries;
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
/*      */     implements ByteIterator
/*      */   {
/*      */     public byte nextByte() {
/*  968 */       return Byte2DoubleOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractByteSet { private KeySet() {}
/*      */     
/*      */     public ByteIterator iterator() {
/*  974 */       return new Byte2DoubleOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/*  979 */       if (Byte2DoubleOpenHashMap.this.containsNullKey)
/*  980 */         consumer.accept(Byte2DoubleOpenHashMap.this.key[Byte2DoubleOpenHashMap.this.n]); 
/*  981 */       for (int pos = Byte2DoubleOpenHashMap.this.n; pos-- != 0; ) {
/*  982 */         byte k = Byte2DoubleOpenHashMap.this.key[pos];
/*  983 */         if (k != 0)
/*  984 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  989 */       return Byte2DoubleOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(byte k) {
/*  993 */       return Byte2DoubleOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(byte k) {
/*  997 */       int oldSize = Byte2DoubleOpenHashMap.this.size;
/*  998 */       Byte2DoubleOpenHashMap.this.remove(k);
/*  999 */       return (Byte2DoubleOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1003 */       Byte2DoubleOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ByteSet keySet() {
/* 1008 */     if (this.keys == null)
/* 1009 */       this.keys = new KeySet(); 
/* 1010 */     return this.keys;
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
/*      */     implements DoubleIterator
/*      */   {
/*      */     public double nextDouble() {
/* 1027 */       return Byte2DoubleOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public DoubleCollection values() {
/* 1032 */     if (this.values == null)
/* 1033 */       this.values = (DoubleCollection)new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1036 */             return new Byte2DoubleOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1040 */             return Byte2DoubleOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(double v) {
/* 1044 */             return Byte2DoubleOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1048 */             Byte2DoubleOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(DoubleConsumer consumer)
/*      */           {
/* 1053 */             if (Byte2DoubleOpenHashMap.this.containsNullKey)
/* 1054 */               consumer.accept(Byte2DoubleOpenHashMap.this.value[Byte2DoubleOpenHashMap.this.n]); 
/* 1055 */             for (int pos = Byte2DoubleOpenHashMap.this.n; pos-- != 0;) {
/* 1056 */               if (Byte2DoubleOpenHashMap.this.key[pos] != 0)
/* 1057 */                 consumer.accept(Byte2DoubleOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1060 */     return this.values;
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
/* 1077 */     return trim(this.size);
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
/* 1101 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1102 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1103 */       return true; 
/*      */     try {
/* 1105 */       rehash(l);
/* 1106 */     } catch (OutOfMemoryError cantDoIt) {
/* 1107 */       return false;
/*      */     } 
/* 1109 */     return true;
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
/* 1125 */     byte[] key = this.key;
/* 1126 */     double[] value = this.value;
/* 1127 */     int mask = newN - 1;
/* 1128 */     byte[] newKey = new byte[newN + 1];
/* 1129 */     double[] newValue = new double[newN + 1];
/* 1130 */     int i = this.n;
/* 1131 */     for (int j = realSize(); j-- != 0; ) {
/* 1132 */       while (key[--i] == 0); int pos;
/* 1133 */       if (newKey[pos = HashCommon.mix(key[i]) & mask] != 0)
/* 1134 */         while (newKey[pos = pos + 1 & mask] != 0); 
/* 1135 */       newKey[pos] = key[i];
/* 1136 */       newValue[pos] = value[i];
/*      */     } 
/* 1138 */     newValue[newN] = value[this.n];
/* 1139 */     this.n = newN;
/* 1140 */     this.mask = mask;
/* 1141 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1142 */     this.key = newKey;
/* 1143 */     this.value = newValue;
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
/*      */   public Byte2DoubleOpenHashMap clone() {
/*      */     Byte2DoubleOpenHashMap c;
/*      */     try {
/* 1160 */       c = (Byte2DoubleOpenHashMap)super.clone();
/* 1161 */     } catch (CloneNotSupportedException cantHappen) {
/* 1162 */       throw new InternalError();
/*      */     } 
/* 1164 */     c.keys = null;
/* 1165 */     c.values = null;
/* 1166 */     c.entries = null;
/* 1167 */     c.containsNullKey = this.containsNullKey;
/* 1168 */     c.key = (byte[])this.key.clone();
/* 1169 */     c.value = (double[])this.value.clone();
/* 1170 */     return c;
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
/* 1183 */     int h = 0;
/* 1184 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1185 */       while (this.key[i] == 0)
/* 1186 */         i++; 
/* 1187 */       t = this.key[i];
/* 1188 */       t ^= HashCommon.double2int(this.value[i]);
/* 1189 */       h += t;
/* 1190 */       i++;
/*      */     } 
/*      */     
/* 1193 */     if (this.containsNullKey)
/* 1194 */       h += HashCommon.double2int(this.value[this.n]); 
/* 1195 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1198 */     byte[] key = this.key;
/* 1199 */     double[] value = this.value;
/* 1200 */     MapIterator i = new MapIterator();
/* 1201 */     s.defaultWriteObject();
/* 1202 */     for (int j = this.size; j-- != 0; ) {
/* 1203 */       int e = i.nextEntry();
/* 1204 */       s.writeByte(key[e]);
/* 1205 */       s.writeDouble(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1210 */     s.defaultReadObject();
/* 1211 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1212 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1213 */     this.mask = this.n - 1;
/* 1214 */     byte[] key = this.key = new byte[this.n + 1];
/* 1215 */     double[] value = this.value = new double[this.n + 1];
/*      */ 
/*      */     
/* 1218 */     for (int i = this.size; i-- != 0; ) {
/* 1219 */       int pos; byte k = s.readByte();
/* 1220 */       double v = s.readDouble();
/* 1221 */       if (k == 0) {
/* 1222 */         pos = this.n;
/* 1223 */         this.containsNullKey = true;
/*      */       } else {
/* 1225 */         pos = HashCommon.mix(k) & this.mask;
/* 1226 */         while (key[pos] != 0)
/* 1227 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1229 */       key[pos] = k;
/* 1230 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\Byte2DoubleOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */