/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatIterator;
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
/*     */ public abstract class AbstractDouble2FloatMap
/*     */   extends AbstractDouble2FloatFunction
/*     */   implements Double2FloatMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(float v) {
/*  49 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(double k) {
/*  53 */     ObjectIterator<Double2FloatMap.Entry> i = double2FloatEntrySet().iterator();
/*  54 */     while (i.hasNext()) {
/*  55 */       if (((Double2FloatMap.Entry)i.next()).getDoubleKey() == k)
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
/*     */     implements Double2FloatMap.Entry
/*     */   {
/*     */     protected double key;
/*     */     
/*     */     protected float value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Double key, Float value) {
/*  78 */       this.key = key.doubleValue();
/*  79 */       this.value = value.floatValue();
/*     */     }
/*     */     public BasicEntry(double key, float value) {
/*  82 */       this.key = key;
/*  83 */       this.value = value;
/*     */     }
/*     */     
/*     */     public double getDoubleKey() {
/*  87 */       return this.key;
/*     */     }
/*     */     
/*     */     public float getFloatValue() {
/*  91 */       return this.value;
/*     */     }
/*     */     
/*     */     public float setValue(float value) {
/*  95 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 100 */       if (!(o instanceof Map.Entry))
/* 101 */         return false; 
/* 102 */       if (o instanceof Double2FloatMap.Entry) {
/* 103 */         Double2FloatMap.Entry entry = (Double2FloatMap.Entry)o;
/* 104 */         return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(entry.getDoubleKey()) && 
/* 105 */           Float.floatToIntBits(this.value) == Float.floatToIntBits(entry.getFloatValue()));
/*     */       } 
/* 107 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 108 */       Object key = e.getKey();
/* 109 */       if (key == null || !(key instanceof Double))
/* 110 */         return false; 
/* 111 */       Object value = e.getValue();
/* 112 */       if (value == null || !(value instanceof Float))
/* 113 */         return false; 
/* 114 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(((Double)key).doubleValue()) && 
/* 115 */         Float.floatToIntBits(this.value) == Float.floatToIntBits(((Float)value).floatValue()));
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 119 */       return HashCommon.double2int(this.key) ^ HashCommon.float2int(this.value);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 123 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet
/*     */     extends AbstractObjectSet<Double2FloatMap.Entry>
/*     */   {
/*     */     protected final Double2FloatMap map;
/*     */     
/*     */     public BasicEntrySet(Double2FloatMap map) {
/* 133 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 138 */       if (!(o instanceof Map.Entry))
/* 139 */         return false; 
/* 140 */       if (o instanceof Double2FloatMap.Entry) {
/* 141 */         Double2FloatMap.Entry entry = (Double2FloatMap.Entry)o;
/* 142 */         double d = entry.getDoubleKey();
/* 143 */         return (this.map.containsKey(d) && 
/* 144 */           Float.floatToIntBits(this.map.get(d)) == Float.floatToIntBits(entry.getFloatValue()));
/*     */       } 
/* 146 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 147 */       Object key = e.getKey();
/* 148 */       if (key == null || !(key instanceof Double))
/* 149 */         return false; 
/* 150 */       double k = ((Double)key).doubleValue();
/* 151 */       Object value = e.getValue();
/* 152 */       if (value == null || !(value instanceof Float))
/* 153 */         return false; 
/* 154 */       return (this.map.containsKey(k) && 
/* 155 */         Float.floatToIntBits(this.map.get(k)) == Float.floatToIntBits(((Float)value).floatValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 160 */       if (!(o instanceof Map.Entry))
/* 161 */         return false; 
/* 162 */       if (o instanceof Double2FloatMap.Entry) {
/* 163 */         Double2FloatMap.Entry entry = (Double2FloatMap.Entry)o;
/* 164 */         return this.map.remove(entry.getDoubleKey(), entry.getFloatValue());
/*     */       } 
/* 166 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 167 */       Object key = e.getKey();
/* 168 */       if (key == null || !(key instanceof Double))
/* 169 */         return false; 
/* 170 */       double k = ((Double)key).doubleValue();
/* 171 */       Object value = e.getValue();
/* 172 */       if (value == null || !(value instanceof Float))
/* 173 */         return false; 
/* 174 */       float v = ((Float)value).floatValue();
/* 175 */       return this.map.remove(k, v);
/*     */     }
/*     */     
/*     */     public int size() {
/* 179 */       return this.map.size();
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
/* 198 */     return new AbstractDoubleSet()
/*     */       {
/*     */         public boolean contains(double k) {
/* 201 */           return AbstractDouble2FloatMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 205 */           return AbstractDouble2FloatMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 209 */           AbstractDouble2FloatMap.this.clear();
/*     */         }
/*     */         
/*     */         public DoubleIterator iterator() {
/* 213 */           return new DoubleIterator()
/*     */             {
/* 215 */               private final ObjectIterator<Double2FloatMap.Entry> i = Double2FloatMaps.fastIterator(AbstractDouble2FloatMap.this);
/*     */               
/*     */               public double nextDouble() {
/* 218 */                 return ((Double2FloatMap.Entry)this.i.next()).getDoubleKey();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 222 */                 return this.i.hasNext();
/*     */               }
/*     */               
/*     */               public void remove() {
/* 226 */                 this.i.remove();
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
/*     */   public FloatCollection values() {
/* 248 */     return (FloatCollection)new AbstractFloatCollection()
/*     */       {
/*     */         public boolean contains(float k) {
/* 251 */           return AbstractDouble2FloatMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 255 */           return AbstractDouble2FloatMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 259 */           AbstractDouble2FloatMap.this.clear();
/*     */         }
/*     */         
/*     */         public FloatIterator iterator() {
/* 263 */           return new FloatIterator()
/*     */             {
/* 265 */               private final ObjectIterator<Double2FloatMap.Entry> i = Double2FloatMaps.fastIterator(AbstractDouble2FloatMap.this);
/*     */               
/*     */               public float nextFloat() {
/* 268 */                 return ((Double2FloatMap.Entry)this.i.next()).getFloatValue();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 272 */                 return this.i.hasNext();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Double, ? extends Float> m) {
/* 282 */     if (m instanceof Double2FloatMap) {
/* 283 */       ObjectIterator<Double2FloatMap.Entry> i = Double2FloatMaps.fastIterator((Double2FloatMap)m);
/* 284 */       while (i.hasNext()) {
/* 285 */         Double2FloatMap.Entry e = (Double2FloatMap.Entry)i.next();
/* 286 */         put(e.getDoubleKey(), e.getFloatValue());
/*     */       } 
/*     */     } else {
/* 289 */       int n = m.size();
/* 290 */       Iterator<? extends Map.Entry<? extends Double, ? extends Float>> i = m.entrySet().iterator();
/*     */       
/* 292 */       while (n-- != 0) {
/* 293 */         Map.Entry<? extends Double, ? extends Float> e = i.next();
/* 294 */         put(e.getKey(), e.getValue());
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
/* 307 */     int h = 0, n = size();
/* 308 */     ObjectIterator<Double2FloatMap.Entry> i = Double2FloatMaps.fastIterator(this);
/* 309 */     while (n-- != 0)
/* 310 */       h += ((Double2FloatMap.Entry)i.next()).hashCode(); 
/* 311 */     return h;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 315 */     if (o == this)
/* 316 */       return true; 
/* 317 */     if (!(o instanceof Map))
/* 318 */       return false; 
/* 319 */     Map<?, ?> m = (Map<?, ?>)o;
/* 320 */     if (m.size() != size())
/* 321 */       return false; 
/* 322 */     return double2FloatEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 326 */     StringBuilder s = new StringBuilder();
/* 327 */     ObjectIterator<Double2FloatMap.Entry> i = Double2FloatMaps.fastIterator(this);
/* 328 */     int n = size();
/*     */     
/* 330 */     boolean first = true;
/* 331 */     s.append("{");
/* 332 */     while (n-- != 0) {
/* 333 */       if (first) {
/* 334 */         first = false;
/*     */       } else {
/* 336 */         s.append(", ");
/* 337 */       }  Double2FloatMap.Entry e = (Double2FloatMap.Entry)i.next();
/* 338 */       s.append(String.valueOf(e.getDoubleKey()));
/* 339 */       s.append("=>");
/* 340 */       s.append(String.valueOf(e.getFloatValue()));
/*     */     } 
/* 342 */     s.append("}");
/* 343 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\AbstractDouble2FloatMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */