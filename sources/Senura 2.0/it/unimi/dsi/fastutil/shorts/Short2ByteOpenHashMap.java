/*      */ package it.unimi.dsi.fastutil.shorts;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
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
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.IntFunction;
/*      */ import java.util.function.IntUnaryOperator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Short2ByteOpenHashMap
/*      */   extends AbstractShort2ByteMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient short[] key;
/*      */   protected transient byte[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Short2ByteMap.FastEntrySet entries;
/*      */   protected transient ShortSet keys;
/*      */   protected transient ByteCollection values;
/*      */   
/*      */   public Short2ByteOpenHashMap(int expected, float f) {
/*   96 */     if (f <= 0.0F || f > 1.0F)
/*   97 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*   98 */     if (expected < 0)
/*   99 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  100 */     this.f = f;
/*  101 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  102 */     this.mask = this.n - 1;
/*  103 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  104 */     this.key = new short[this.n + 1];
/*  105 */     this.value = new byte[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ByteOpenHashMap(int expected) {
/*  114 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ByteOpenHashMap() {
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
/*      */   public Short2ByteOpenHashMap(Map<? extends Short, ? extends Byte> m, float f) {
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
/*      */   public Short2ByteOpenHashMap(Map<? extends Short, ? extends Byte> m) {
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
/*      */   public Short2ByteOpenHashMap(Short2ByteMap m, float f) {
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
/*      */   public Short2ByteOpenHashMap(Short2ByteMap m) {
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
/*      */   public Short2ByteOpenHashMap(short[] k, byte[] v, float f) {
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
/*      */   public Short2ByteOpenHashMap(short[] k, byte[] v) {
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
/*      */   private byte removeEntry(int pos) {
/*  217 */     byte oldValue = this.value[pos];
/*  218 */     this.size--;
/*  219 */     shiftKeys(pos);
/*  220 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  221 */       rehash(this.n / 2); 
/*  222 */     return oldValue;
/*      */   }
/*      */   private byte removeNullEntry() {
/*  225 */     this.containsNullKey = false;
/*  226 */     byte oldValue = this.value[this.n];
/*  227 */     this.size--;
/*  228 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  229 */       rehash(this.n / 2); 
/*  230 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Short, ? extends Byte> m) {
/*  234 */     if (this.f <= 0.5D) {
/*  235 */       ensureCapacity(m.size());
/*      */     } else {
/*  237 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  239 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(short k) {
/*  243 */     if (k == 0) {
/*  244 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  246 */     short[] key = this.key;
/*      */     short curr;
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
/*      */   private void insert(int pos, short k, byte v) {
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
/*      */   public byte put(short k, byte v) {
/*  273 */     int pos = find(k);
/*  274 */     if (pos < 0) {
/*  275 */       insert(-pos - 1, k, v);
/*  276 */       return this.defRetValue;
/*      */     } 
/*  278 */     byte oldValue = this.value[pos];
/*  279 */     this.value[pos] = v;
/*  280 */     return oldValue;
/*      */   }
/*      */   private byte addToValue(int pos, byte incr) {
/*  283 */     byte oldValue = this.value[pos];
/*  284 */     this.value[pos] = (byte)(oldValue + incr);
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
/*      */   public byte addTo(short k, byte incr) {
/*      */     int pos;
/*  305 */     if (k == 0) {
/*  306 */       if (this.containsNullKey)
/*  307 */         return addToValue(this.n, incr); 
/*  308 */       pos = this.n;
/*  309 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  312 */       short[] key = this.key;
/*      */       short curr;
/*  314 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  315 */         if (curr == k)
/*  316 */           return addToValue(pos, incr); 
/*  317 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  318 */           if (curr == k)
/*  319 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  322 */     }  this.key[pos] = k;
/*  323 */     this.value[pos] = (byte)(this.defRetValue + incr);
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
/*  341 */     short[] key = this.key; while (true) {
/*      */       short curr; int last;
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
/*      */   public byte remove(short k) {
/*  361 */     if (k == 0) {
/*  362 */       if (this.containsNullKey)
/*  363 */         return removeNullEntry(); 
/*  364 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  367 */     short[] key = this.key;
/*      */     short curr;
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
/*      */   public byte get(short k) {
/*  384 */     if (k == 0) {
/*  385 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  387 */     short[] key = this.key;
/*      */     short curr;
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
/*      */   public boolean containsKey(short k) {
/*  405 */     if (k == 0) {
/*  406 */       return this.containsNullKey;
/*      */     }
/*  408 */     short[] key = this.key;
/*      */     short curr;
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
/*      */   public boolean containsValue(byte v) {
/*  425 */     byte[] value = this.value;
/*  426 */     short[] key = this.key;
/*  427 */     if (this.containsNullKey && value[this.n] == v)
/*  428 */       return true; 
/*  429 */     for (int i = this.n; i-- != 0;) {
/*  430 */       if (key[i] != 0 && value[i] == v)
/*  431 */         return true; 
/*  432 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getOrDefault(short k, byte defaultValue) {
/*  438 */     if (k == 0) {
/*  439 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  441 */     short[] key = this.key;
/*      */     short curr;
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
/*      */   public byte putIfAbsent(short k, byte v) {
/*  459 */     int pos = find(k);
/*  460 */     if (pos >= 0)
/*  461 */       return this.value[pos]; 
/*  462 */     insert(-pos - 1, k, v);
/*  463 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(short k, byte v) {
/*  469 */     if (k == 0) {
/*  470 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  471 */         removeNullEntry();
/*  472 */         return true;
/*      */       } 
/*  474 */       return false;
/*      */     } 
/*      */     
/*  477 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  480 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  481 */       return false; 
/*  482 */     if (k == curr && v == this.value[pos]) {
/*  483 */       removeEntry(pos);
/*  484 */       return true;
/*      */     } 
/*      */     while (true) {
/*  487 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  488 */         return false; 
/*  489 */       if (k == curr && v == this.value[pos]) {
/*  490 */         removeEntry(pos);
/*  491 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(short k, byte oldValue, byte v) {
/*  498 */     int pos = find(k);
/*  499 */     if (pos < 0 || oldValue != this.value[pos])
/*  500 */       return false; 
/*  501 */     this.value[pos] = v;
/*  502 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte replace(short k, byte v) {
/*  507 */     int pos = find(k);
/*  508 */     if (pos < 0)
/*  509 */       return this.defRetValue; 
/*  510 */     byte oldValue = this.value[pos];
/*  511 */     this.value[pos] = v;
/*  512 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte computeIfAbsent(short k, IntUnaryOperator mappingFunction) {
/*  517 */     Objects.requireNonNull(mappingFunction);
/*  518 */     int pos = find(k);
/*  519 */     if (pos >= 0)
/*  520 */       return this.value[pos]; 
/*  521 */     byte newValue = SafeMath.safeIntToByte(mappingFunction.applyAsInt(k));
/*  522 */     insert(-pos - 1, k, newValue);
/*  523 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsentNullable(short k, IntFunction<? extends Byte> mappingFunction) {
/*  529 */     Objects.requireNonNull(mappingFunction);
/*  530 */     int pos = find(k);
/*  531 */     if (pos >= 0)
/*  532 */       return this.value[pos]; 
/*  533 */     Byte newValue = mappingFunction.apply(k);
/*  534 */     if (newValue == null)
/*  535 */       return this.defRetValue; 
/*  536 */     byte v = newValue.byteValue();
/*  537 */     insert(-pos - 1, k, v);
/*  538 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfPresent(short k, BiFunction<? super Short, ? super Byte, ? extends Byte> remappingFunction) {
/*  544 */     Objects.requireNonNull(remappingFunction);
/*  545 */     int pos = find(k);
/*  546 */     if (pos < 0)
/*  547 */       return this.defRetValue; 
/*  548 */     Byte newValue = remappingFunction.apply(Short.valueOf(k), Byte.valueOf(this.value[pos]));
/*  549 */     if (newValue == null) {
/*  550 */       if (k == 0) {
/*  551 */         removeNullEntry();
/*      */       } else {
/*  553 */         removeEntry(pos);
/*  554 */       }  return this.defRetValue;
/*      */     } 
/*  556 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte compute(short k, BiFunction<? super Short, ? super Byte, ? extends Byte> remappingFunction) {
/*  562 */     Objects.requireNonNull(remappingFunction);
/*  563 */     int pos = find(k);
/*  564 */     Byte newValue = remappingFunction.apply(Short.valueOf(k), (pos >= 0) ? Byte.valueOf(this.value[pos]) : null);
/*  565 */     if (newValue == null) {
/*  566 */       if (pos >= 0)
/*  567 */         if (k == 0) {
/*  568 */           removeNullEntry();
/*      */         } else {
/*  570 */           removeEntry(pos);
/*      */         }  
/*  572 */       return this.defRetValue;
/*      */     } 
/*  574 */     byte newVal = newValue.byteValue();
/*  575 */     if (pos < 0) {
/*  576 */       insert(-pos - 1, k, newVal);
/*  577 */       return newVal;
/*      */     } 
/*  579 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte merge(short k, byte v, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/*  585 */     Objects.requireNonNull(remappingFunction);
/*  586 */     int pos = find(k);
/*  587 */     if (pos < 0) {
/*  588 */       insert(-pos - 1, k, v);
/*  589 */       return v;
/*      */     } 
/*  591 */     Byte newValue = remappingFunction.apply(Byte.valueOf(this.value[pos]), Byte.valueOf(v));
/*  592 */     if (newValue == null) {
/*  593 */       if (k == 0) {
/*  594 */         removeNullEntry();
/*      */       } else {
/*  596 */         removeEntry(pos);
/*  597 */       }  return this.defRetValue;
/*      */     } 
/*  599 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
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
/*  614 */     Arrays.fill(this.key, (short)0);
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
/*      */     implements Short2ByteMap.Entry, Map.Entry<Short, Byte>
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
/*      */     public short getShortKey() {
/*  640 */       return Short2ByteOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public byte getByteValue() {
/*  644 */       return Short2ByteOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public byte setValue(byte v) {
/*  648 */       byte oldValue = Short2ByteOpenHashMap.this.value[this.index];
/*  649 */       Short2ByteOpenHashMap.this.value[this.index] = v;
/*  650 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getKey() {
/*  660 */       return Short.valueOf(Short2ByteOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte getValue() {
/*  670 */       return Byte.valueOf(Short2ByteOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte setValue(Byte v) {
/*  680 */       return Byte.valueOf(setValue(v.byteValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  685 */       if (!(o instanceof Map.Entry))
/*  686 */         return false; 
/*  687 */       Map.Entry<Short, Byte> e = (Map.Entry<Short, Byte>)o;
/*  688 */       return (Short2ByteOpenHashMap.this.key[this.index] == ((Short)e.getKey()).shortValue() && Short2ByteOpenHashMap.this.value[this.index] == ((Byte)e.getValue()).byteValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  692 */       return Short2ByteOpenHashMap.this.key[this.index] ^ Short2ByteOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  696 */       return Short2ByteOpenHashMap.this.key[this.index] + "=>" + Short2ByteOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  706 */     int pos = Short2ByteOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  713 */     int last = -1;
/*      */     
/*  715 */     int c = Short2ByteOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  719 */     boolean mustReturnNullKey = Short2ByteOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ShortArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  726 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  729 */       if (!hasNext())
/*  730 */         throw new NoSuchElementException(); 
/*  731 */       this.c--;
/*  732 */       if (this.mustReturnNullKey) {
/*  733 */         this.mustReturnNullKey = false;
/*  734 */         return this.last = Short2ByteOpenHashMap.this.n;
/*      */       } 
/*  736 */       short[] key = Short2ByteOpenHashMap.this.key;
/*      */       while (true) {
/*  738 */         if (--this.pos < 0) {
/*      */           
/*  740 */           this.last = Integer.MIN_VALUE;
/*  741 */           short k = this.wrapped.getShort(-this.pos - 1);
/*  742 */           int p = HashCommon.mix(k) & Short2ByteOpenHashMap.this.mask;
/*  743 */           while (k != key[p])
/*  744 */             p = p + 1 & Short2ByteOpenHashMap.this.mask; 
/*  745 */           return p;
/*      */         } 
/*  747 */         if (key[this.pos] != 0) {
/*  748 */           return this.last = this.pos;
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
/*  762 */       short[] key = Short2ByteOpenHashMap.this.key; while (true) {
/*      */         short curr; int last;
/*  764 */         pos = (last = pos) + 1 & Short2ByteOpenHashMap.this.mask;
/*      */         while (true) {
/*  766 */           if ((curr = key[pos]) == 0) {
/*  767 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  770 */           int slot = HashCommon.mix(curr) & Short2ByteOpenHashMap.this.mask;
/*  771 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  773 */           pos = pos + 1 & Short2ByteOpenHashMap.this.mask;
/*      */         } 
/*  775 */         if (pos < last) {
/*  776 */           if (this.wrapped == null)
/*  777 */             this.wrapped = new ShortArrayList(2); 
/*  778 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  780 */         key[last] = curr;
/*  781 */         Short2ByteOpenHashMap.this.value[last] = Short2ByteOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  785 */       if (this.last == -1)
/*  786 */         throw new IllegalStateException(); 
/*  787 */       if (this.last == Short2ByteOpenHashMap.this.n) {
/*  788 */         Short2ByteOpenHashMap.this.containsNullKey = false;
/*  789 */       } else if (this.pos >= 0) {
/*  790 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  793 */         Short2ByteOpenHashMap.this.remove(this.wrapped.getShort(-this.pos - 1));
/*  794 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  797 */       Short2ByteOpenHashMap.this.size--;
/*  798 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  803 */       int i = n;
/*  804 */       while (i-- != 0 && hasNext())
/*  805 */         nextEntry(); 
/*  806 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Short2ByteMap.Entry> { private Short2ByteOpenHashMap.MapEntry entry;
/*      */     
/*      */     public Short2ByteOpenHashMap.MapEntry next() {
/*  813 */       return this.entry = new Short2ByteOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  817 */       super.remove();
/*  818 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Short2ByteMap.Entry> { private FastEntryIterator() {
/*  822 */       this.entry = new Short2ByteOpenHashMap.MapEntry();
/*      */     } private final Short2ByteOpenHashMap.MapEntry entry;
/*      */     public Short2ByteOpenHashMap.MapEntry next() {
/*  825 */       this.entry.index = nextEntry();
/*  826 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Short2ByteMap.Entry> implements Short2ByteMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Short2ByteMap.Entry> iterator() {
/*  832 */       return new Short2ByteOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Short2ByteMap.Entry> fastIterator() {
/*  836 */       return new Short2ByteOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  841 */       if (!(o instanceof Map.Entry))
/*  842 */         return false; 
/*  843 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  844 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/*  845 */         return false; 
/*  846 */       if (e.getValue() == null || !(e.getValue() instanceof Byte))
/*  847 */         return false; 
/*  848 */       short k = ((Short)e.getKey()).shortValue();
/*  849 */       byte v = ((Byte)e.getValue()).byteValue();
/*  850 */       if (k == 0) {
/*  851 */         return (Short2ByteOpenHashMap.this.containsNullKey && Short2ByteOpenHashMap.this.value[Short2ByteOpenHashMap.this.n] == v);
/*      */       }
/*  853 */       short[] key = Short2ByteOpenHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/*  856 */       if ((curr = key[pos = HashCommon.mix(k) & Short2ByteOpenHashMap.this.mask]) == 0)
/*  857 */         return false; 
/*  858 */       if (k == curr) {
/*  859 */         return (Short2ByteOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  862 */         if ((curr = key[pos = pos + 1 & Short2ByteOpenHashMap.this.mask]) == 0)
/*  863 */           return false; 
/*  864 */         if (k == curr) {
/*  865 */           return (Short2ByteOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  871 */       if (!(o instanceof Map.Entry))
/*  872 */         return false; 
/*  873 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  874 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/*  875 */         return false; 
/*  876 */       if (e.getValue() == null || !(e.getValue() instanceof Byte))
/*  877 */         return false; 
/*  878 */       short k = ((Short)e.getKey()).shortValue();
/*  879 */       byte v = ((Byte)e.getValue()).byteValue();
/*  880 */       if (k == 0) {
/*  881 */         if (Short2ByteOpenHashMap.this.containsNullKey && Short2ByteOpenHashMap.this.value[Short2ByteOpenHashMap.this.n] == v) {
/*  882 */           Short2ByteOpenHashMap.this.removeNullEntry();
/*  883 */           return true;
/*      */         } 
/*  885 */         return false;
/*      */       } 
/*      */       
/*  888 */       short[] key = Short2ByteOpenHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/*  891 */       if ((curr = key[pos = HashCommon.mix(k) & Short2ByteOpenHashMap.this.mask]) == 0)
/*  892 */         return false; 
/*  893 */       if (curr == k) {
/*  894 */         if (Short2ByteOpenHashMap.this.value[pos] == v) {
/*  895 */           Short2ByteOpenHashMap.this.removeEntry(pos);
/*  896 */           return true;
/*      */         } 
/*  898 */         return false;
/*      */       } 
/*      */       while (true) {
/*  901 */         if ((curr = key[pos = pos + 1 & Short2ByteOpenHashMap.this.mask]) == 0)
/*  902 */           return false; 
/*  903 */         if (curr == k && 
/*  904 */           Short2ByteOpenHashMap.this.value[pos] == v) {
/*  905 */           Short2ByteOpenHashMap.this.removeEntry(pos);
/*  906 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  913 */       return Short2ByteOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  917 */       Short2ByteOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Short2ByteMap.Entry> consumer) {
/*  922 */       if (Short2ByteOpenHashMap.this.containsNullKey)
/*  923 */         consumer.accept(new AbstractShort2ByteMap.BasicEntry(Short2ByteOpenHashMap.this.key[Short2ByteOpenHashMap.this.n], Short2ByteOpenHashMap.this.value[Short2ByteOpenHashMap.this.n])); 
/*  924 */       for (int pos = Short2ByteOpenHashMap.this.n; pos-- != 0;) {
/*  925 */         if (Short2ByteOpenHashMap.this.key[pos] != 0)
/*  926 */           consumer.accept(new AbstractShort2ByteMap.BasicEntry(Short2ByteOpenHashMap.this.key[pos], Short2ByteOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Short2ByteMap.Entry> consumer) {
/*  931 */       AbstractShort2ByteMap.BasicEntry entry = new AbstractShort2ByteMap.BasicEntry();
/*  932 */       if (Short2ByteOpenHashMap.this.containsNullKey) {
/*  933 */         entry.key = Short2ByteOpenHashMap.this.key[Short2ByteOpenHashMap.this.n];
/*  934 */         entry.value = Short2ByteOpenHashMap.this.value[Short2ByteOpenHashMap.this.n];
/*  935 */         consumer.accept(entry);
/*      */       } 
/*  937 */       for (int pos = Short2ByteOpenHashMap.this.n; pos-- != 0;) {
/*  938 */         if (Short2ByteOpenHashMap.this.key[pos] != 0) {
/*  939 */           entry.key = Short2ByteOpenHashMap.this.key[pos];
/*  940 */           entry.value = Short2ByteOpenHashMap.this.value[pos];
/*  941 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Short2ByteMap.FastEntrySet short2ByteEntrySet() {
/*  947 */     if (this.entries == null)
/*  948 */       this.entries = new MapEntrySet(); 
/*  949 */     return this.entries;
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
/*      */     implements ShortIterator
/*      */   {
/*      */     public short nextShort() {
/*  966 */       return Short2ByteOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractShortSet { private KeySet() {}
/*      */     
/*      */     public ShortIterator iterator() {
/*  972 */       return new Short2ByteOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/*  977 */       if (Short2ByteOpenHashMap.this.containsNullKey)
/*  978 */         consumer.accept(Short2ByteOpenHashMap.this.key[Short2ByteOpenHashMap.this.n]); 
/*  979 */       for (int pos = Short2ByteOpenHashMap.this.n; pos-- != 0; ) {
/*  980 */         short k = Short2ByteOpenHashMap.this.key[pos];
/*  981 */         if (k != 0)
/*  982 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  987 */       return Short2ByteOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(short k) {
/*  991 */       return Short2ByteOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(short k) {
/*  995 */       int oldSize = Short2ByteOpenHashMap.this.size;
/*  996 */       Short2ByteOpenHashMap.this.remove(k);
/*  997 */       return (Short2ByteOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1001 */       Short2ByteOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ShortSet keySet() {
/* 1006 */     if (this.keys == null)
/* 1007 */       this.keys = new KeySet(); 
/* 1008 */     return this.keys;
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
/*      */     implements ByteIterator
/*      */   {
/*      */     public byte nextByte() {
/* 1025 */       return Short2ByteOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ByteCollection values() {
/* 1030 */     if (this.values == null)
/* 1031 */       this.values = (ByteCollection)new AbstractByteCollection()
/*      */         {
/*      */           public ByteIterator iterator() {
/* 1034 */             return new Short2ByteOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1038 */             return Short2ByteOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(byte v) {
/* 1042 */             return Short2ByteOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1046 */             Short2ByteOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1051 */             if (Short2ByteOpenHashMap.this.containsNullKey)
/* 1052 */               consumer.accept(Short2ByteOpenHashMap.this.value[Short2ByteOpenHashMap.this.n]); 
/* 1053 */             for (int pos = Short2ByteOpenHashMap.this.n; pos-- != 0;) {
/* 1054 */               if (Short2ByteOpenHashMap.this.key[pos] != 0)
/* 1055 */                 consumer.accept(Short2ByteOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1058 */     return this.values;
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
/* 1075 */     return trim(this.size);
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
/* 1099 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1100 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1101 */       return true; 
/*      */     try {
/* 1103 */       rehash(l);
/* 1104 */     } catch (OutOfMemoryError cantDoIt) {
/* 1105 */       return false;
/*      */     } 
/* 1107 */     return true;
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
/* 1123 */     short[] key = this.key;
/* 1124 */     byte[] value = this.value;
/* 1125 */     int mask = newN - 1;
/* 1126 */     short[] newKey = new short[newN + 1];
/* 1127 */     byte[] newValue = new byte[newN + 1];
/* 1128 */     int i = this.n;
/* 1129 */     for (int j = realSize(); j-- != 0; ) {
/* 1130 */       while (key[--i] == 0); int pos;
/* 1131 */       if (newKey[pos = HashCommon.mix(key[i]) & mask] != 0)
/* 1132 */         while (newKey[pos = pos + 1 & mask] != 0); 
/* 1133 */       newKey[pos] = key[i];
/* 1134 */       newValue[pos] = value[i];
/*      */     } 
/* 1136 */     newValue[newN] = value[this.n];
/* 1137 */     this.n = newN;
/* 1138 */     this.mask = mask;
/* 1139 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1140 */     this.key = newKey;
/* 1141 */     this.value = newValue;
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
/*      */   public Short2ByteOpenHashMap clone() {
/*      */     Short2ByteOpenHashMap c;
/*      */     try {
/* 1158 */       c = (Short2ByteOpenHashMap)super.clone();
/* 1159 */     } catch (CloneNotSupportedException cantHappen) {
/* 1160 */       throw new InternalError();
/*      */     } 
/* 1162 */     c.keys = null;
/* 1163 */     c.values = null;
/* 1164 */     c.entries = null;
/* 1165 */     c.containsNullKey = this.containsNullKey;
/* 1166 */     c.key = (short[])this.key.clone();
/* 1167 */     c.value = (byte[])this.value.clone();
/* 1168 */     return c;
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
/* 1181 */     int h = 0;
/* 1182 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1183 */       while (this.key[i] == 0)
/* 1184 */         i++; 
/* 1185 */       t = this.key[i];
/* 1186 */       t ^= this.value[i];
/* 1187 */       h += t;
/* 1188 */       i++;
/*      */     } 
/*      */     
/* 1191 */     if (this.containsNullKey)
/* 1192 */       h += this.value[this.n]; 
/* 1193 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1196 */     short[] key = this.key;
/* 1197 */     byte[] value = this.value;
/* 1198 */     MapIterator i = new MapIterator();
/* 1199 */     s.defaultWriteObject();
/* 1200 */     for (int j = this.size; j-- != 0; ) {
/* 1201 */       int e = i.nextEntry();
/* 1202 */       s.writeShort(key[e]);
/* 1203 */       s.writeByte(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1208 */     s.defaultReadObject();
/* 1209 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1210 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1211 */     this.mask = this.n - 1;
/* 1212 */     short[] key = this.key = new short[this.n + 1];
/* 1213 */     byte[] value = this.value = new byte[this.n + 1];
/*      */ 
/*      */     
/* 1216 */     for (int i = this.size; i-- != 0; ) {
/* 1217 */       int pos; short k = s.readShort();
/* 1218 */       byte v = s.readByte();
/* 1219 */       if (k == 0) {
/* 1220 */         pos = this.n;
/* 1221 */         this.containsNullKey = true;
/*      */       } else {
/* 1223 */         pos = HashCommon.mix(k) & this.mask;
/* 1224 */         while (key[pos] != 0)
/* 1225 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1227 */       key[pos] = k;
/* 1228 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2ByteOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */