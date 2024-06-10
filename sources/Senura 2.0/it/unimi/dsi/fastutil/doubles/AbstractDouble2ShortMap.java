/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortCollection;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
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
/*     */ public abstract class AbstractDouble2ShortMap
/*     */   extends AbstractDouble2ShortFunction
/*     */   implements Double2ShortMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(short v) {
/*  49 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(double k) {
/*  53 */     ObjectIterator<Double2ShortMap.Entry> i = double2ShortEntrySet().iterator();
/*  54 */     while (i.hasNext()) {
/*  55 */       if (((Double2ShortMap.Entry)i.next()).getDoubleKey() == k)
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
/*     */     implements Double2ShortMap.Entry
/*     */   {
/*     */     protected double key;
/*     */     
/*     */     protected short value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Double key, Short value) {
/*  78 */       this.key = key.doubleValue();
/*  79 */       this.value = value.shortValue();
/*     */     }
/*     */     public BasicEntry(double key, short value) {
/*  82 */       this.key = key;
/*  83 */       this.value = value;
/*     */     }
/*     */     
/*     */     public double getDoubleKey() {
/*  87 */       return this.key;
/*     */     }
/*     */     
/*     */     public short getShortValue() {
/*  91 */       return this.value;
/*     */     }
/*     */     
/*     */     public short setValue(short value) {
/*  95 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 100 */       if (!(o instanceof Map.Entry))
/* 101 */         return false; 
/* 102 */       if (o instanceof Double2ShortMap.Entry) {
/* 103 */         Double2ShortMap.Entry entry = (Double2ShortMap.Entry)o;
/* 104 */         return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(entry.getDoubleKey()) && this.value == entry
/* 105 */           .getShortValue());
/*     */       } 
/* 107 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 108 */       Object key = e.getKey();
/* 109 */       if (key == null || !(key instanceof Double))
/* 110 */         return false; 
/* 111 */       Object value = e.getValue();
/* 112 */       if (value == null || !(value instanceof Short))
/* 113 */         return false; 
/* 114 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(((Double)key).doubleValue()) && this.value == ((Short)value)
/* 115 */         .shortValue());
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
/*     */     extends AbstractObjectSet<Double2ShortMap.Entry>
/*     */   {
/*     */     protected final Double2ShortMap map;
/*     */     
/*     */     public BasicEntrySet(Double2ShortMap map) {
/* 133 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 138 */       if (!(o instanceof Map.Entry))
/* 139 */         return false; 
/* 140 */       if (o instanceof Double2ShortMap.Entry) {
/* 141 */         Double2ShortMap.Entry entry = (Double2ShortMap.Entry)o;
/* 142 */         double d = entry.getDoubleKey();
/* 143 */         return (this.map.containsKey(d) && this.map.get(d) == entry.getShortValue());
/*     */       } 
/* 145 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 146 */       Object key = e.getKey();
/* 147 */       if (key == null || !(key instanceof Double))
/* 148 */         return false; 
/* 149 */       double k = ((Double)key).doubleValue();
/* 150 */       Object value = e.getValue();
/* 151 */       if (value == null || !(value instanceof Short))
/* 152 */         return false; 
/* 153 */       return (this.map.containsKey(k) && this.map.get(k) == ((Short)value).shortValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 158 */       if (!(o instanceof Map.Entry))
/* 159 */         return false; 
/* 160 */       if (o instanceof Double2ShortMap.Entry) {
/* 161 */         Double2ShortMap.Entry entry = (Double2ShortMap.Entry)o;
/* 162 */         return this.map.remove(entry.getDoubleKey(), entry.getShortValue());
/*     */       } 
/* 164 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 165 */       Object key = e.getKey();
/* 166 */       if (key == null || !(key instanceof Double))
/* 167 */         return false; 
/* 168 */       double k = ((Double)key).doubleValue();
/* 169 */       Object value = e.getValue();
/* 170 */       if (value == null || !(value instanceof Short))
/* 171 */         return false; 
/* 172 */       short v = ((Short)value).shortValue();
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
/* 199 */           return AbstractDouble2ShortMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 203 */           return AbstractDouble2ShortMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 207 */           AbstractDouble2ShortMap.this.clear();
/*     */         }
/*     */         
/*     */         public DoubleIterator iterator() {
/* 211 */           return new DoubleIterator()
/*     */             {
/* 213 */               private final ObjectIterator<Double2ShortMap.Entry> i = Double2ShortMaps.fastIterator(AbstractDouble2ShortMap.this);
/*     */               
/*     */               public double nextDouble() {
/* 216 */                 return ((Double2ShortMap.Entry)this.i.next()).getDoubleKey();
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
/*     */   public ShortCollection values() {
/* 246 */     return (ShortCollection)new AbstractShortCollection()
/*     */       {
/*     */         public boolean contains(short k) {
/* 249 */           return AbstractDouble2ShortMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 253 */           return AbstractDouble2ShortMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 257 */           AbstractDouble2ShortMap.this.clear();
/*     */         }
/*     */         
/*     */         public ShortIterator iterator() {
/* 261 */           return new ShortIterator()
/*     */             {
/* 263 */               private final ObjectIterator<Double2ShortMap.Entry> i = Double2ShortMaps.fastIterator(AbstractDouble2ShortMap.this);
/*     */               
/*     */               public short nextShort() {
/* 266 */                 return ((Double2ShortMap.Entry)this.i.next()).getShortValue();
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
/*     */   public void putAll(Map<? extends Double, ? extends Short> m) {
/* 280 */     if (m instanceof Double2ShortMap) {
/* 281 */       ObjectIterator<Double2ShortMap.Entry> i = Double2ShortMaps.fastIterator((Double2ShortMap)m);
/* 282 */       while (i.hasNext()) {
/* 283 */         Double2ShortMap.Entry e = (Double2ShortMap.Entry)i.next();
/* 284 */         put(e.getDoubleKey(), e.getShortValue());
/*     */       } 
/*     */     } else {
/* 287 */       int n = m.size();
/* 288 */       Iterator<? extends Map.Entry<? extends Double, ? extends Short>> i = m.entrySet().iterator();
/*     */       
/* 290 */       while (n-- != 0) {
/* 291 */         Map.Entry<? extends Double, ? extends Short> e = i.next();
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
/* 306 */     ObjectIterator<Double2ShortMap.Entry> i = Double2ShortMaps.fastIterator(this);
/* 307 */     while (n-- != 0)
/* 308 */       h += ((Double2ShortMap.Entry)i.next()).hashCode(); 
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
/* 320 */     return double2ShortEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 324 */     StringBuilder s = new StringBuilder();
/* 325 */     ObjectIterator<Double2ShortMap.Entry> i = Double2ShortMaps.fastIterator(this);
/* 326 */     int n = size();
/*     */     
/* 328 */     boolean first = true;
/* 329 */     s.append("{");
/* 330 */     while (n-- != 0) {
/* 331 */       if (first) {
/* 332 */         first = false;
/*     */       } else {
/* 334 */         s.append(", ");
/* 335 */       }  Double2ShortMap.Entry e = (Double2ShortMap.Entry)i.next();
/* 336 */       s.append(String.valueOf(e.getDoubleKey()));
/* 337 */       s.append("=>");
/* 338 */       s.append(String.valueOf(e.getShortValue()));
/*     */     } 
/* 340 */     s.append("}");
/* 341 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\AbstractDouble2ShortMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */