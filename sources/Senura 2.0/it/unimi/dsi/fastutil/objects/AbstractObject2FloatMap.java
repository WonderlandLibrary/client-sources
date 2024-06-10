/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatIterator;
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
/*     */ 
/*     */ public abstract class AbstractObject2FloatMap<K>
/*     */   extends AbstractObject2FloatFunction<K>
/*     */   implements Object2FloatMap<K>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(float v) {
/*  48 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object k) {
/*  52 */     ObjectIterator<Object2FloatMap.Entry<K>> i = object2FloatEntrySet().iterator();
/*  53 */     while (i.hasNext()) {
/*  54 */       if (((Object2FloatMap.Entry)i.next()).getKey() == k)
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
/*     */   public static class BasicEntry<K>
/*     */     implements Object2FloatMap.Entry<K>
/*     */   {
/*     */     protected K key;
/*     */     
/*     */     protected float value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(K key, Float value) {
/*  77 */       this.key = key;
/*  78 */       this.value = value.floatValue();
/*     */     }
/*     */     public BasicEntry(K key, float value) {
/*  81 */       this.key = key;
/*  82 */       this.value = value;
/*     */     }
/*     */     
/*     */     public K getKey() {
/*  86 */       return this.key;
/*     */     }
/*     */     
/*     */     public float getFloatValue() {
/*  90 */       return this.value;
/*     */     }
/*     */     
/*     */     public float setValue(float value) {
/*  94 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  99 */       if (!(o instanceof Map.Entry))
/* 100 */         return false; 
/* 101 */       if (o instanceof Object2FloatMap.Entry) {
/* 102 */         Object2FloatMap.Entry<K> entry = (Object2FloatMap.Entry<K>)o;
/* 103 */         return (Objects.equals(this.key, entry.getKey()) && 
/* 104 */           Float.floatToIntBits(this.value) == Float.floatToIntBits(entry.getFloatValue()));
/*     */       } 
/* 106 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 107 */       Object key = e.getKey();
/* 108 */       Object value = e.getValue();
/* 109 */       if (value == null || !(value instanceof Float))
/* 110 */         return false; 
/* 111 */       return (Objects.equals(this.key, key) && 
/* 112 */         Float.floatToIntBits(this.value) == Float.floatToIntBits(((Float)value).floatValue()));
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 116 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ HashCommon.float2int(this.value);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 120 */       return (new StringBuilder()).append(this.key).append("->").append(this.value).toString();
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet<K>
/*     */     extends AbstractObjectSet<Object2FloatMap.Entry<K>>
/*     */   {
/*     */     protected final Object2FloatMap<K> map;
/*     */     
/*     */     public BasicEntrySet(Object2FloatMap<K> map) {
/* 130 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 135 */       if (!(o instanceof Map.Entry))
/* 136 */         return false; 
/* 137 */       if (o instanceof Object2FloatMap.Entry) {
/* 138 */         Object2FloatMap.Entry<K> entry = (Object2FloatMap.Entry<K>)o;
/* 139 */         K k1 = entry.getKey();
/* 140 */         return (this.map.containsKey(k1) && 
/* 141 */           Float.floatToIntBits(this.map.getFloat(k1)) == Float.floatToIntBits(entry.getFloatValue()));
/*     */       } 
/* 143 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 144 */       Object k = e.getKey();
/* 145 */       Object value = e.getValue();
/* 146 */       if (value == null || !(value instanceof Float))
/* 147 */         return false; 
/* 148 */       return (this.map.containsKey(k) && 
/* 149 */         Float.floatToIntBits(this.map.getFloat(k)) == Float.floatToIntBits(((Float)value).floatValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 154 */       if (!(o instanceof Map.Entry))
/* 155 */         return false; 
/* 156 */       if (o instanceof Object2FloatMap.Entry) {
/* 157 */         Object2FloatMap.Entry<K> entry = (Object2FloatMap.Entry<K>)o;
/* 158 */         return this.map.remove(entry.getKey(), entry.getFloatValue());
/*     */       } 
/* 160 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 161 */       Object k = e.getKey();
/* 162 */       Object value = e.getValue();
/* 163 */       if (value == null || !(value instanceof Float))
/* 164 */         return false; 
/* 165 */       float v = ((Float)value).floatValue();
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
/*     */   public ObjectSet<K> keySet() {
/* 189 */     return new AbstractObjectSet<K>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 192 */           return AbstractObject2FloatMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 196 */           return AbstractObject2FloatMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 200 */           AbstractObject2FloatMap.this.clear();
/*     */         }
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 204 */           return new ObjectIterator<K>()
/*     */             {
/* 206 */               private final ObjectIterator<Object2FloatMap.Entry<K>> i = Object2FloatMaps.fastIterator(AbstractObject2FloatMap.this);
/*     */               
/*     */               public K next() {
/* 209 */                 return ((Object2FloatMap.Entry<K>)this.i.next()).getKey();
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
/*     */   public FloatCollection values() {
/* 239 */     return (FloatCollection)new AbstractFloatCollection()
/*     */       {
/*     */         public boolean contains(float k) {
/* 242 */           return AbstractObject2FloatMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 246 */           return AbstractObject2FloatMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 250 */           AbstractObject2FloatMap.this.clear();
/*     */         }
/*     */         
/*     */         public FloatIterator iterator() {
/* 254 */           return new FloatIterator()
/*     */             {
/* 256 */               private final ObjectIterator<Object2FloatMap.Entry<K>> i = Object2FloatMaps.fastIterator(AbstractObject2FloatMap.this);
/*     */               
/*     */               public float nextFloat() {
/* 259 */                 return ((Object2FloatMap.Entry)this.i.next()).getFloatValue();
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
/*     */   public void putAll(Map<? extends K, ? extends Float> m) {
/* 273 */     if (m instanceof Object2FloatMap) {
/* 274 */       ObjectIterator<Object2FloatMap.Entry<K>> i = Object2FloatMaps.fastIterator((Object2FloatMap)m);
/* 275 */       while (i.hasNext()) {
/* 276 */         Object2FloatMap.Entry<? extends K> e = i.next();
/* 277 */         put(e.getKey(), e.getFloatValue());
/*     */       } 
/*     */     } else {
/* 280 */       int n = m.size();
/* 281 */       Iterator<? extends Map.Entry<? extends K, ? extends Float>> i = m.entrySet().iterator();
/*     */       
/* 283 */       while (n-- != 0) {
/* 284 */         Map.Entry<? extends K, ? extends Float> e = i.next();
/* 285 */         put(e.getKey(), e.getValue());
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
/* 298 */     int h = 0, n = size();
/* 299 */     ObjectIterator<Object2FloatMap.Entry<K>> i = Object2FloatMaps.fastIterator(this);
/* 300 */     while (n-- != 0)
/* 301 */       h += ((Object2FloatMap.Entry)i.next()).hashCode(); 
/* 302 */     return h;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 306 */     if (o == this)
/* 307 */       return true; 
/* 308 */     if (!(o instanceof Map))
/* 309 */       return false; 
/* 310 */     Map<?, ?> m = (Map<?, ?>)o;
/* 311 */     if (m.size() != size())
/* 312 */       return false; 
/* 313 */     return object2FloatEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 317 */     StringBuilder s = new StringBuilder();
/* 318 */     ObjectIterator<Object2FloatMap.Entry<K>> i = Object2FloatMaps.fastIterator(this);
/* 319 */     int n = size();
/*     */     
/* 321 */     boolean first = true;
/* 322 */     s.append("{");
/* 323 */     while (n-- != 0) {
/* 324 */       if (first) {
/* 325 */         first = false;
/*     */       } else {
/* 327 */         s.append(", ");
/* 328 */       }  Object2FloatMap.Entry<K> e = i.next();
/* 329 */       if (this == e.getKey()) {
/* 330 */         s.append("(this map)");
/*     */       } else {
/* 332 */         s.append(String.valueOf(e.getKey()));
/* 333 */       }  s.append("=>");
/* 334 */       s.append(String.valueOf(e.getFloatValue()));
/*     */     } 
/* 336 */     s.append("}");
/* 337 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractObject2FloatMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */