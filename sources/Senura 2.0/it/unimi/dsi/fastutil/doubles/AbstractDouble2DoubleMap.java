/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractDouble2DoubleMap
/*     */   extends AbstractDouble2DoubleFunction
/*     */   implements Double2DoubleMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(double v) {
/*  49 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(double k) {
/*  53 */     ObjectIterator<Double2DoubleMap.Entry> i = double2DoubleEntrySet().iterator();
/*  54 */     while (i.hasNext()) {
/*  55 */       if (((Double2DoubleMap.Entry)i.next()).getDoubleKey() == k)
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
/*     */     implements Double2DoubleMap.Entry
/*     */   {
/*     */     protected double key;
/*     */     
/*     */     protected double value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Double key, Double value) {
/*  78 */       this.key = key.doubleValue();
/*  79 */       this.value = value.doubleValue();
/*     */     }
/*     */     public BasicEntry(double key, double value) {
/*  82 */       this.key = key;
/*  83 */       this.value = value;
/*     */     }
/*     */     
/*     */     public double getDoubleKey() {
/*  87 */       return this.key;
/*     */     }
/*     */     
/*     */     public double getDoubleValue() {
/*  91 */       return this.value;
/*     */     }
/*     */     
/*     */     public double setValue(double value) {
/*  95 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 100 */       if (!(o instanceof Map.Entry))
/* 101 */         return false; 
/* 102 */       if (o instanceof Double2DoubleMap.Entry) {
/* 103 */         Double2DoubleMap.Entry entry = (Double2DoubleMap.Entry)o;
/* 104 */         return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(entry.getDoubleKey()) && 
/* 105 */           Double.doubleToLongBits(this.value) == Double.doubleToLongBits(entry.getDoubleValue()));
/*     */       } 
/* 107 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 108 */       Object key = e.getKey();
/* 109 */       if (key == null || !(key instanceof Double))
/* 110 */         return false; 
/* 111 */       Object value = e.getValue();
/* 112 */       if (value == null || !(value instanceof Double))
/* 113 */         return false; 
/* 114 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(((Double)key).doubleValue()) && 
/* 115 */         Double.doubleToLongBits(this.value) == 
/* 116 */         Double.doubleToLongBits(((Double)value).doubleValue()));
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 120 */       return HashCommon.double2int(this.key) ^ 
/* 121 */         HashCommon.double2int(this.value);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 125 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet
/*     */     extends AbstractObjectSet<Double2DoubleMap.Entry>
/*     */   {
/*     */     protected final Double2DoubleMap map;
/*     */     
/*     */     public BasicEntrySet(Double2DoubleMap map) {
/* 135 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 140 */       if (!(o instanceof Map.Entry))
/* 141 */         return false; 
/* 142 */       if (o instanceof Double2DoubleMap.Entry) {
/* 143 */         Double2DoubleMap.Entry entry = (Double2DoubleMap.Entry)o;
/* 144 */         double d = entry.getDoubleKey();
/* 145 */         return (this.map.containsKey(d) && 
/* 146 */           Double.doubleToLongBits(this.map.get(d)) == Double.doubleToLongBits(entry.getDoubleValue()));
/*     */       } 
/* 148 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 149 */       Object key = e.getKey();
/* 150 */       if (key == null || !(key instanceof Double))
/* 151 */         return false; 
/* 152 */       double k = ((Double)key).doubleValue();
/* 153 */       Object value = e.getValue();
/* 154 */       if (value == null || !(value instanceof Double))
/* 155 */         return false; 
/* 156 */       return (this.map.containsKey(k) && Double.doubleToLongBits(this.map.get(k)) == 
/* 157 */         Double.doubleToLongBits(((Double)value).doubleValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 162 */       if (!(o instanceof Map.Entry))
/* 163 */         return false; 
/* 164 */       if (o instanceof Double2DoubleMap.Entry) {
/* 165 */         Double2DoubleMap.Entry entry = (Double2DoubleMap.Entry)o;
/* 166 */         return this.map.remove(entry.getDoubleKey(), entry.getDoubleValue());
/*     */       } 
/* 168 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 169 */       Object key = e.getKey();
/* 170 */       if (key == null || !(key instanceof Double))
/* 171 */         return false; 
/* 172 */       double k = ((Double)key).doubleValue();
/* 173 */       Object value = e.getValue();
/* 174 */       if (value == null || !(value instanceof Double))
/* 175 */         return false; 
/* 176 */       double v = ((Double)value).doubleValue();
/* 177 */       return this.map.remove(k, v);
/*     */     }
/*     */     
/*     */     public int size() {
/* 181 */       return this.map.size();
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
/* 200 */     return new AbstractDoubleSet()
/*     */       {
/*     */         public boolean contains(double k) {
/* 203 */           return AbstractDouble2DoubleMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 207 */           return AbstractDouble2DoubleMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 211 */           AbstractDouble2DoubleMap.this.clear();
/*     */         }
/*     */         
/*     */         public DoubleIterator iterator() {
/* 215 */           return new DoubleIterator()
/*     */             {
/* 217 */               private final ObjectIterator<Double2DoubleMap.Entry> i = Double2DoubleMaps.fastIterator(AbstractDouble2DoubleMap.this);
/*     */               
/*     */               public double nextDouble() {
/* 220 */                 return ((Double2DoubleMap.Entry)this.i.next()).getDoubleKey();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 224 */                 return this.i.hasNext();
/*     */               }
/*     */               
/*     */               public void remove() {
/* 228 */                 this.i.remove();
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
/*     */   public DoubleCollection values() {
/* 250 */     return new AbstractDoubleCollection()
/*     */       {
/*     */         public boolean contains(double k) {
/* 253 */           return AbstractDouble2DoubleMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 257 */           return AbstractDouble2DoubleMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 261 */           AbstractDouble2DoubleMap.this.clear();
/*     */         }
/*     */         
/*     */         public DoubleIterator iterator() {
/* 265 */           return new DoubleIterator()
/*     */             {
/* 267 */               private final ObjectIterator<Double2DoubleMap.Entry> i = Double2DoubleMaps.fastIterator(AbstractDouble2DoubleMap.this);
/*     */               
/*     */               public double nextDouble() {
/* 270 */                 return ((Double2DoubleMap.Entry)this.i.next()).getDoubleValue();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 274 */                 return this.i.hasNext();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Double, ? extends Double> m) {
/* 284 */     if (m instanceof Double2DoubleMap) {
/* 285 */       ObjectIterator<Double2DoubleMap.Entry> i = Double2DoubleMaps.fastIterator((Double2DoubleMap)m);
/* 286 */       while (i.hasNext()) {
/* 287 */         Double2DoubleMap.Entry e = (Double2DoubleMap.Entry)i.next();
/* 288 */         put(e.getDoubleKey(), e.getDoubleValue());
/*     */       } 
/*     */     } else {
/* 291 */       int n = m.size();
/* 292 */       Iterator<? extends Map.Entry<? extends Double, ? extends Double>> i = m.entrySet().iterator();
/*     */       
/* 294 */       while (n-- != 0) {
/* 295 */         Map.Entry<? extends Double, ? extends Double> e = i.next();
/* 296 */         put(e.getKey(), e.getValue());
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
/* 309 */     int h = 0, n = size();
/* 310 */     ObjectIterator<Double2DoubleMap.Entry> i = Double2DoubleMaps.fastIterator(this);
/* 311 */     while (n-- != 0)
/* 312 */       h += ((Double2DoubleMap.Entry)i.next()).hashCode(); 
/* 313 */     return h;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 317 */     if (o == this)
/* 318 */       return true; 
/* 319 */     if (!(o instanceof Map))
/* 320 */       return false; 
/* 321 */     Map<?, ?> m = (Map<?, ?>)o;
/* 322 */     if (m.size() != size())
/* 323 */       return false; 
/* 324 */     return double2DoubleEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 328 */     StringBuilder s = new StringBuilder();
/* 329 */     ObjectIterator<Double2DoubleMap.Entry> i = Double2DoubleMaps.fastIterator(this);
/* 330 */     int n = size();
/*     */     
/* 332 */     boolean first = true;
/* 333 */     s.append("{");
/* 334 */     while (n-- != 0) {
/* 335 */       if (first) {
/* 336 */         first = false;
/*     */       } else {
/* 338 */         s.append(", ");
/* 339 */       }  Double2DoubleMap.Entry e = (Double2DoubleMap.Entry)i.next();
/* 340 */       s.append(String.valueOf(e.getDoubleKey()));
/* 341 */       s.append("=>");
/* 342 */       s.append(String.valueOf(e.getDoubleValue()));
/*     */     } 
/* 344 */     s.append("}");
/* 345 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\AbstractDouble2DoubleMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */