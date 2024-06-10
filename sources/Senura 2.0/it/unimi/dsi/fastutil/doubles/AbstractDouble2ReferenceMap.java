/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
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
/*     */ public abstract class AbstractDouble2ReferenceMap<V>
/*     */   extends AbstractDouble2ReferenceFunction<V>
/*     */   implements Double2ReferenceMap<V>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(Object v) {
/*  48 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(double k) {
/*  52 */     ObjectIterator<Double2ReferenceMap.Entry<V>> i = double2ReferenceEntrySet().iterator();
/*  53 */     while (i.hasNext()) {
/*  54 */       if (((Double2ReferenceMap.Entry)i.next()).getDoubleKey() == k)
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
/*     */     implements Double2ReferenceMap.Entry<V>
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
/* 101 */       if (o instanceof Double2ReferenceMap.Entry) {
/* 102 */         Double2ReferenceMap.Entry<V> entry = (Double2ReferenceMap.Entry<V>)o;
/* 103 */         return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(entry.getDoubleKey()) && this.value == entry
/* 104 */           .getValue());
/*     */       } 
/* 106 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 107 */       Object key = e.getKey();
/* 108 */       if (key == null || !(key instanceof Double))
/* 109 */         return false; 
/* 110 */       Object value = e.getValue();
/* 111 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(((Double)key).doubleValue()) && this.value == value);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 116 */       return HashCommon.double2int(this.key) ^ (
/* 117 */         (this.value == null) ? 0 : System.identityHashCode(this.value));
/*     */     }
/*     */     
/*     */     public String toString() {
/* 121 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet<V>
/*     */     extends AbstractObjectSet<Double2ReferenceMap.Entry<V>>
/*     */   {
/*     */     protected final Double2ReferenceMap<V> map;
/*     */     
/*     */     public BasicEntrySet(Double2ReferenceMap<V> map) {
/* 131 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 136 */       if (!(o instanceof Map.Entry))
/* 137 */         return false; 
/* 138 */       if (o instanceof Double2ReferenceMap.Entry) {
/* 139 */         Double2ReferenceMap.Entry<V> entry = (Double2ReferenceMap.Entry<V>)o;
/* 140 */         double d = entry.getDoubleKey();
/* 141 */         return (this.map.containsKey(d) && this.map.get(d) == entry.getValue());
/*     */       } 
/* 143 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 144 */       Object key = e.getKey();
/* 145 */       if (key == null || !(key instanceof Double))
/* 146 */         return false; 
/* 147 */       double k = ((Double)key).doubleValue();
/* 148 */       Object value = e.getValue();
/* 149 */       return (this.map.containsKey(k) && this.map.get(k) == value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 154 */       if (!(o instanceof Map.Entry))
/* 155 */         return false; 
/* 156 */       if (o instanceof Double2ReferenceMap.Entry) {
/* 157 */         Double2ReferenceMap.Entry<V> entry = (Double2ReferenceMap.Entry<V>)o;
/* 158 */         return this.map.remove(entry.getDoubleKey(), entry.getValue());
/*     */       } 
/* 160 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 161 */       Object key = e.getKey();
/* 162 */       if (key == null || !(key instanceof Double))
/* 163 */         return false; 
/* 164 */       double k = ((Double)key).doubleValue();
/* 165 */       Object v = e.getValue();
/* 166 */       return this.map.remove(k, v);
/*     */     }
/*     */     
/*     */     public int size() {
/* 170 */       return this.map.size();
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
/* 189 */     return new AbstractDoubleSet()
/*     */       {
/*     */         public boolean contains(double k) {
/* 192 */           return AbstractDouble2ReferenceMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 196 */           return AbstractDouble2ReferenceMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 200 */           AbstractDouble2ReferenceMap.this.clear();
/*     */         }
/*     */         
/*     */         public DoubleIterator iterator() {
/* 204 */           return new DoubleIterator()
/*     */             {
/* 206 */               private final ObjectIterator<Double2ReferenceMap.Entry<V>> i = Double2ReferenceMaps.fastIterator(AbstractDouble2ReferenceMap.this);
/*     */               
/*     */               public double nextDouble() {
/* 209 */                 return ((Double2ReferenceMap.Entry)this.i.next()).getDoubleKey();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 213 */                 return this.i.hasNext();
/*     */               }
/*     */               
/*     */               public void remove() {
/* 217 */                 this.i.remove();
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
/*     */   public ReferenceCollection<V> values() {
/* 239 */     return (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 242 */           return AbstractDouble2ReferenceMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 246 */           return AbstractDouble2ReferenceMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 250 */           AbstractDouble2ReferenceMap.this.clear();
/*     */         }
/*     */         
/*     */         public ObjectIterator<V> iterator() {
/* 254 */           return new ObjectIterator<V>()
/*     */             {
/* 256 */               private final ObjectIterator<Double2ReferenceMap.Entry<V>> i = Double2ReferenceMaps.fastIterator(AbstractDouble2ReferenceMap.this);
/*     */               
/*     */               public V next() {
/* 259 */                 return ((Double2ReferenceMap.Entry<V>)this.i.next()).getValue();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 263 */                 return this.i.hasNext();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Double, ? extends V> m) {
/* 273 */     if (m instanceof Double2ReferenceMap) {
/*     */       
/* 275 */       ObjectIterator<Double2ReferenceMap.Entry<V>> i = Double2ReferenceMaps.fastIterator((Double2ReferenceMap)m);
/* 276 */       while (i.hasNext()) {
/* 277 */         Double2ReferenceMap.Entry<? extends V> e = (Double2ReferenceMap.Entry<? extends V>)i.next();
/* 278 */         put(e.getDoubleKey(), e.getValue());
/*     */       } 
/*     */     } else {
/* 281 */       int n = m.size();
/* 282 */       Iterator<? extends Map.Entry<? extends Double, ? extends V>> i = m.entrySet().iterator();
/*     */       
/* 284 */       while (n-- != 0) {
/* 285 */         Map.Entry<? extends Double, ? extends V> e = i.next();
/* 286 */         put(e.getKey(), e.getValue());
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
/* 299 */     int h = 0, n = size();
/* 300 */     ObjectIterator<Double2ReferenceMap.Entry<V>> i = Double2ReferenceMaps.fastIterator(this);
/* 301 */     while (n-- != 0)
/* 302 */       h += ((Double2ReferenceMap.Entry)i.next()).hashCode(); 
/* 303 */     return h;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 307 */     if (o == this)
/* 308 */       return true; 
/* 309 */     if (!(o instanceof Map))
/* 310 */       return false; 
/* 311 */     Map<?, ?> m = (Map<?, ?>)o;
/* 312 */     if (m.size() != size())
/* 313 */       return false; 
/* 314 */     return double2ReferenceEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 318 */     StringBuilder s = new StringBuilder();
/* 319 */     ObjectIterator<Double2ReferenceMap.Entry<V>> i = Double2ReferenceMaps.fastIterator(this);
/* 320 */     int n = size();
/*     */     
/* 322 */     boolean first = true;
/* 323 */     s.append("{");
/* 324 */     while (n-- != 0) {
/* 325 */       if (first) {
/* 326 */         first = false;
/*     */       } else {
/* 328 */         s.append(", ");
/* 329 */       }  Double2ReferenceMap.Entry<V> e = (Double2ReferenceMap.Entry<V>)i.next();
/* 330 */       s.append(String.valueOf(e.getDoubleKey()));
/* 331 */       s.append("=>");
/* 332 */       if (this == e.getValue()) {
/* 333 */         s.append("(this map)"); continue;
/*     */       } 
/* 335 */       s.append(String.valueOf(e.getValue()));
/*     */     } 
/* 337 */     s.append("}");
/* 338 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\AbstractDouble2ReferenceMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */