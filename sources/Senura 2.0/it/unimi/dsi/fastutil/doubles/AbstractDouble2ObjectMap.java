/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
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
/*     */ public abstract class AbstractDouble2ObjectMap<V>
/*     */   extends AbstractDouble2ObjectFunction<V>
/*     */   implements Double2ObjectMap<V>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(Object v) {
/*  48 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(double k) {
/*  52 */     ObjectIterator<Double2ObjectMap.Entry<V>> i = double2ObjectEntrySet().iterator();
/*  53 */     while (i.hasNext()) {
/*  54 */       if (((Double2ObjectMap.Entry)i.next()).getDoubleKey() == k)
/*  55 */         return true; 
/*  56 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  60 */     return (size() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BasicEntry<V>
/*     */     implements Double2ObjectMap.Entry<V>
/*     */   {
/*     */     protected double key;
/*     */     
/*     */     protected V value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Double key, V value) {
/*  77 */       this.key = key.doubleValue();
/*  78 */       this.value = value;
/*     */     }
/*     */     public BasicEntry(double key, V value) {
/*  81 */       this.key = key;
/*  82 */       this.value = value;
/*     */     }
/*     */     
/*     */     public double getDoubleKey() {
/*  86 */       return this.key;
/*     */     }
/*     */     
/*     */     public V getValue() {
/*  90 */       return this.value;
/*     */     }
/*     */     
/*     */     public V setValue(V value) {
/*  94 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  99 */       if (!(o instanceof Map.Entry))
/* 100 */         return false; 
/* 101 */       if (o instanceof Double2ObjectMap.Entry) {
/* 102 */         Double2ObjectMap.Entry<V> entry = (Double2ObjectMap.Entry<V>)o;
/* 103 */         return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(entry.getDoubleKey()) && 
/* 104 */           Objects.equals(this.value, entry.getValue()));
/*     */       } 
/* 106 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 107 */       Object key = e.getKey();
/* 108 */       if (key == null || !(key instanceof Double))
/* 109 */         return false; 
/* 110 */       Object value = e.getValue();
/* 111 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(((Double)key).doubleValue()) && 
/* 112 */         Objects.equals(this.value, value));
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 116 */       return HashCommon.double2int(this.key) ^ ((this.value == null) ? 0 : this.value.hashCode());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 120 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet<V>
/*     */     extends AbstractObjectSet<Double2ObjectMap.Entry<V>>
/*     */   {
/*     */     protected final Double2ObjectMap<V> map;
/*     */     
/*     */     public BasicEntrySet(Double2ObjectMap<V> map) {
/* 130 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 135 */       if (!(o instanceof Map.Entry))
/* 136 */         return false; 
/* 137 */       if (o instanceof Double2ObjectMap.Entry) {
/* 138 */         Double2ObjectMap.Entry<V> entry = (Double2ObjectMap.Entry<V>)o;
/* 139 */         double d = entry.getDoubleKey();
/* 140 */         return (this.map.containsKey(d) && Objects.equals(this.map.get(d), entry.getValue()));
/*     */       } 
/* 142 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 143 */       Object key = e.getKey();
/* 144 */       if (key == null || !(key instanceof Double))
/* 145 */         return false; 
/* 146 */       double k = ((Double)key).doubleValue();
/* 147 */       Object value = e.getValue();
/* 148 */       return (this.map.containsKey(k) && Objects.equals(this.map.get(k), value));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 153 */       if (!(o instanceof Map.Entry))
/* 154 */         return false; 
/* 155 */       if (o instanceof Double2ObjectMap.Entry) {
/* 156 */         Double2ObjectMap.Entry<V> entry = (Double2ObjectMap.Entry<V>)o;
/* 157 */         return this.map.remove(entry.getDoubleKey(), entry.getValue());
/*     */       } 
/* 159 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 160 */       Object key = e.getKey();
/* 161 */       if (key == null || !(key instanceof Double))
/* 162 */         return false; 
/* 163 */       double k = ((Double)key).doubleValue();
/* 164 */       Object v = e.getValue();
/* 165 */       return this.map.remove(k, v);
/*     */     }
/*     */     
/*     */     public int size() {
/* 169 */       return this.map.size();
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
/* 188 */     return new AbstractDoubleSet()
/*     */       {
/*     */         public boolean contains(double k) {
/* 191 */           return AbstractDouble2ObjectMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 195 */           return AbstractDouble2ObjectMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 199 */           AbstractDouble2ObjectMap.this.clear();
/*     */         }
/*     */         
/*     */         public DoubleIterator iterator() {
/* 203 */           return new DoubleIterator()
/*     */             {
/* 205 */               private final ObjectIterator<Double2ObjectMap.Entry<V>> i = Double2ObjectMaps.fastIterator(AbstractDouble2ObjectMap.this);
/*     */               
/*     */               public double nextDouble() {
/* 208 */                 return ((Double2ObjectMap.Entry)this.i.next()).getDoubleKey();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 212 */                 return this.i.hasNext();
/*     */               }
/*     */               
/*     */               public void remove() {
/* 216 */                 this.i.remove();
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
/*     */   public ObjectCollection<V> values() {
/* 238 */     return (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 241 */           return AbstractDouble2ObjectMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 245 */           return AbstractDouble2ObjectMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 249 */           AbstractDouble2ObjectMap.this.clear();
/*     */         }
/*     */         
/*     */         public ObjectIterator<V> iterator() {
/* 253 */           return new ObjectIterator<V>()
/*     */             {
/* 255 */               private final ObjectIterator<Double2ObjectMap.Entry<V>> i = Double2ObjectMaps.fastIterator(AbstractDouble2ObjectMap.this);
/*     */               
/*     */               public V next() {
/* 258 */                 return ((Double2ObjectMap.Entry<V>)this.i.next()).getValue();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 262 */                 return this.i.hasNext();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Double, ? extends V> m) {
/* 272 */     if (m instanceof Double2ObjectMap) {
/* 273 */       ObjectIterator<Double2ObjectMap.Entry<V>> i = Double2ObjectMaps.fastIterator((Double2ObjectMap)m);
/* 274 */       while (i.hasNext()) {
/* 275 */         Double2ObjectMap.Entry<? extends V> e = (Double2ObjectMap.Entry<? extends V>)i.next();
/* 276 */         put(e.getDoubleKey(), e.getValue());
/*     */       } 
/*     */     } else {
/* 279 */       int n = m.size();
/* 280 */       Iterator<? extends Map.Entry<? extends Double, ? extends V>> i = m.entrySet().iterator();
/*     */       
/* 282 */       while (n-- != 0) {
/* 283 */         Map.Entry<? extends Double, ? extends V> e = i.next();
/* 284 */         put(e.getKey(), e.getValue());
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
/* 297 */     int h = 0, n = size();
/* 298 */     ObjectIterator<Double2ObjectMap.Entry<V>> i = Double2ObjectMaps.fastIterator(this);
/* 299 */     while (n-- != 0)
/* 300 */       h += ((Double2ObjectMap.Entry)i.next()).hashCode(); 
/* 301 */     return h;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 305 */     if (o == this)
/* 306 */       return true; 
/* 307 */     if (!(o instanceof Map))
/* 308 */       return false; 
/* 309 */     Map<?, ?> m = (Map<?, ?>)o;
/* 310 */     if (m.size() != size())
/* 311 */       return false; 
/* 312 */     return double2ObjectEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 316 */     StringBuilder s = new StringBuilder();
/* 317 */     ObjectIterator<Double2ObjectMap.Entry<V>> i = Double2ObjectMaps.fastIterator(this);
/* 318 */     int n = size();
/*     */     
/* 320 */     boolean first = true;
/* 321 */     s.append("{");
/* 322 */     while (n-- != 0) {
/* 323 */       if (first) {
/* 324 */         first = false;
/*     */       } else {
/* 326 */         s.append(", ");
/* 327 */       }  Double2ObjectMap.Entry<V> e = (Double2ObjectMap.Entry<V>)i.next();
/* 328 */       s.append(String.valueOf(e.getDoubleKey()));
/* 329 */       s.append("=>");
/* 330 */       if (this == e.getValue()) {
/* 331 */         s.append("(this map)"); continue;
/*     */       } 
/* 333 */       s.append(String.valueOf(e.getValue()));
/*     */     } 
/* 335 */     s.append("}");
/* 336 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\AbstractDouble2ObjectMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */