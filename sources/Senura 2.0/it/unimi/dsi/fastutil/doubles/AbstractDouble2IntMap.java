/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractDouble2IntMap
/*     */   extends AbstractDouble2IntFunction
/*     */   implements Double2IntMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(int v) {
/*  49 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(double k) {
/*  53 */     ObjectIterator<Double2IntMap.Entry> i = double2IntEntrySet().iterator();
/*  54 */     while (i.hasNext()) {
/*  55 */       if (((Double2IntMap.Entry)i.next()).getDoubleKey() == k)
/*  56 */         return true; 
/*  57 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  61 */     return (size() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BasicEntry
/*     */     implements Double2IntMap.Entry
/*     */   {
/*     */     protected double key;
/*     */     
/*     */     protected int value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Double key, Integer value) {
/*  78 */       this.key = key.doubleValue();
/*  79 */       this.value = value.intValue();
/*     */     }
/*     */     public BasicEntry(double key, int value) {
/*  82 */       this.key = key;
/*  83 */       this.value = value;
/*     */     }
/*     */     
/*     */     public double getDoubleKey() {
/*  87 */       return this.key;
/*     */     }
/*     */     
/*     */     public int getIntValue() {
/*  91 */       return this.value;
/*     */     }
/*     */     
/*     */     public int setValue(int value) {
/*  95 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 100 */       if (!(o instanceof Map.Entry))
/* 101 */         return false; 
/* 102 */       if (o instanceof Double2IntMap.Entry) {
/* 103 */         Double2IntMap.Entry entry = (Double2IntMap.Entry)o;
/* 104 */         return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(entry.getDoubleKey()) && this.value == entry
/* 105 */           .getIntValue());
/*     */       } 
/* 107 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 108 */       Object key = e.getKey();
/* 109 */       if (key == null || !(key instanceof Double))
/* 110 */         return false; 
/* 111 */       Object value = e.getValue();
/* 112 */       if (value == null || !(value instanceof Integer))
/* 113 */         return false; 
/* 114 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(((Double)key).doubleValue()) && this.value == ((Integer)value)
/* 115 */         .intValue());
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 119 */       return HashCommon.double2int(this.key) ^ this.value;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 123 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet
/*     */     extends AbstractObjectSet<Double2IntMap.Entry>
/*     */   {
/*     */     protected final Double2IntMap map;
/*     */     
/*     */     public BasicEntrySet(Double2IntMap map) {
/* 133 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 138 */       if (!(o instanceof Map.Entry))
/* 139 */         return false; 
/* 140 */       if (o instanceof Double2IntMap.Entry) {
/* 141 */         Double2IntMap.Entry entry = (Double2IntMap.Entry)o;
/* 142 */         double d = entry.getDoubleKey();
/* 143 */         return (this.map.containsKey(d) && this.map.get(d) == entry.getIntValue());
/*     */       } 
/* 145 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 146 */       Object key = e.getKey();
/* 147 */       if (key == null || !(key instanceof Double))
/* 148 */         return false; 
/* 149 */       double k = ((Double)key).doubleValue();
/* 150 */       Object value = e.getValue();
/* 151 */       if (value == null || !(value instanceof Integer))
/* 152 */         return false; 
/* 153 */       return (this.map.containsKey(k) && this.map.get(k) == ((Integer)value).intValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 158 */       if (!(o instanceof Map.Entry))
/* 159 */         return false; 
/* 160 */       if (o instanceof Double2IntMap.Entry) {
/* 161 */         Double2IntMap.Entry entry = (Double2IntMap.Entry)o;
/* 162 */         return this.map.remove(entry.getDoubleKey(), entry.getIntValue());
/*     */       } 
/* 164 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 165 */       Object key = e.getKey();
/* 166 */       if (key == null || !(key instanceof Double))
/* 167 */         return false; 
/* 168 */       double k = ((Double)key).doubleValue();
/* 169 */       Object value = e.getValue();
/* 170 */       if (value == null || !(value instanceof Integer))
/* 171 */         return false; 
/* 172 */       int v = ((Integer)value).intValue();
/* 173 */       return this.map.remove(k, v);
/*     */     }
/*     */     
/*     */     public int size() {
/* 177 */       return this.map.size();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleSet keySet() {
/* 196 */     return new AbstractDoubleSet()
/*     */       {
/*     */         public boolean contains(double k) {
/* 199 */           return AbstractDouble2IntMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 203 */           return AbstractDouble2IntMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 207 */           AbstractDouble2IntMap.this.clear();
/*     */         }
/*     */         
/*     */         public DoubleIterator iterator() {
/* 211 */           return new DoubleIterator()
/*     */             {
/* 213 */               private final ObjectIterator<Double2IntMap.Entry> i = Double2IntMaps.fastIterator(AbstractDouble2IntMap.this);
/*     */               
/*     */               public double nextDouble() {
/* 216 */                 return ((Double2IntMap.Entry)this.i.next()).getDoubleKey();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 220 */                 return this.i.hasNext();
/*     */               }
/*     */               
/*     */               public void remove() {
/* 224 */                 this.i.remove();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntCollection values() {
/* 246 */     return (IntCollection)new AbstractIntCollection()
/*     */       {
/*     */         public boolean contains(int k) {
/* 249 */           return AbstractDouble2IntMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 253 */           return AbstractDouble2IntMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 257 */           AbstractDouble2IntMap.this.clear();
/*     */         }
/*     */         
/*     */         public IntIterator iterator() {
/* 261 */           return new IntIterator()
/*     */             {
/* 263 */               private final ObjectIterator<Double2IntMap.Entry> i = Double2IntMaps.fastIterator(AbstractDouble2IntMap.this);
/*     */               
/*     */               public int nextInt() {
/* 266 */                 return ((Double2IntMap.Entry)this.i.next()).getIntValue();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 270 */                 return this.i.hasNext();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Double, ? extends Integer> m) {
/* 280 */     if (m instanceof Double2IntMap) {
/* 281 */       ObjectIterator<Double2IntMap.Entry> i = Double2IntMaps.fastIterator((Double2IntMap)m);
/* 282 */       while (i.hasNext()) {
/* 283 */         Double2IntMap.Entry e = (Double2IntMap.Entry)i.next();
/* 284 */         put(e.getDoubleKey(), e.getIntValue());
/*     */       } 
/*     */     } else {
/* 287 */       int n = m.size();
/* 288 */       Iterator<? extends Map.Entry<? extends Double, ? extends Integer>> i = m.entrySet().iterator();
/*     */       
/* 290 */       while (n-- != 0) {
/* 291 */         Map.Entry<? extends Double, ? extends Integer> e = i.next();
/* 292 */         put(e.getKey(), e.getValue());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 305 */     int h = 0, n = size();
/* 306 */     ObjectIterator<Double2IntMap.Entry> i = Double2IntMaps.fastIterator(this);
/* 307 */     while (n-- != 0)
/* 308 */       h += ((Double2IntMap.Entry)i.next()).hashCode(); 
/* 309 */     return h;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 313 */     if (o == this)
/* 314 */       return true; 
/* 315 */     if (!(o instanceof Map))
/* 316 */       return false; 
/* 317 */     Map<?, ?> m = (Map<?, ?>)o;
/* 318 */     if (m.size() != size())
/* 319 */       return false; 
/* 320 */     return double2IntEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 324 */     StringBuilder s = new StringBuilder();
/* 325 */     ObjectIterator<Double2IntMap.Entry> i = Double2IntMaps.fastIterator(this);
/* 326 */     int n = size();
/*     */     
/* 328 */     boolean first = true;
/* 329 */     s.append("{");
/* 330 */     while (n-- != 0) {
/* 331 */       if (first) {
/* 332 */         first = false;
/*     */       } else {
/* 334 */         s.append(", ");
/* 335 */       }  Double2IntMap.Entry e = (Double2IntMap.Entry)i.next();
/* 336 */       s.append(String.valueOf(e.getDoubleKey()));
/* 337 */       s.append("=>");
/* 338 */       s.append(String.valueOf(e.getIntValue()));
/*     */     } 
/* 340 */     s.append("}");
/* 341 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\AbstractDouble2IntMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */