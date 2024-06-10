/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public abstract class AbstractFloat2ObjectMap<V>
/*     */   extends AbstractFloat2ObjectFunction<V>
/*     */   implements Float2ObjectMap<V>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(Object v) {
/*  48 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(float k) {
/*  52 */     ObjectIterator<Float2ObjectMap.Entry<V>> i = float2ObjectEntrySet().iterator();
/*  53 */     while (i.hasNext()) {
/*  54 */       if (((Float2ObjectMap.Entry)i.next()).getFloatKey() == k)
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
/*     */     implements Float2ObjectMap.Entry<V>
/*     */   {
/*     */     protected float key;
/*     */     
/*     */     protected V value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Float key, V value) {
/*  77 */       this.key = key.floatValue();
/*  78 */       this.value = value;
/*     */     }
/*     */     public BasicEntry(float key, V value) {
/*  81 */       this.key = key;
/*  82 */       this.value = value;
/*     */     }
/*     */     
/*     */     public float getFloatKey() {
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
/* 101 */       if (o instanceof Float2ObjectMap.Entry) {
/* 102 */         Float2ObjectMap.Entry<V> entry = (Float2ObjectMap.Entry<V>)o;
/* 103 */         return (Float.floatToIntBits(this.key) == Float.floatToIntBits(entry.getFloatKey()) && 
/* 104 */           Objects.equals(this.value, entry.getValue()));
/*     */       } 
/* 106 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 107 */       Object key = e.getKey();
/* 108 */       if (key == null || !(key instanceof Float))
/* 109 */         return false; 
/* 110 */       Object value = e.getValue();
/* 111 */       return (Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)key).floatValue()) && 
/* 112 */         Objects.equals(this.value, value));
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 116 */       return HashCommon.float2int(this.key) ^ ((this.value == null) ? 0 : this.value.hashCode());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 120 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet<V>
/*     */     extends AbstractObjectSet<Float2ObjectMap.Entry<V>>
/*     */   {
/*     */     protected final Float2ObjectMap<V> map;
/*     */     
/*     */     public BasicEntrySet(Float2ObjectMap<V> map) {
/* 130 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 135 */       if (!(o instanceof Map.Entry))
/* 136 */         return false; 
/* 137 */       if (o instanceof Float2ObjectMap.Entry) {
/* 138 */         Float2ObjectMap.Entry<V> entry = (Float2ObjectMap.Entry<V>)o;
/* 139 */         float f = entry.getFloatKey();
/* 140 */         return (this.map.containsKey(f) && Objects.equals(this.map.get(f), entry.getValue()));
/*     */       } 
/* 142 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 143 */       Object key = e.getKey();
/* 144 */       if (key == null || !(key instanceof Float))
/* 145 */         return false; 
/* 146 */       float k = ((Float)key).floatValue();
/* 147 */       Object value = e.getValue();
/* 148 */       return (this.map.containsKey(k) && Objects.equals(this.map.get(k), value));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 153 */       if (!(o instanceof Map.Entry))
/* 154 */         return false; 
/* 155 */       if (o instanceof Float2ObjectMap.Entry) {
/* 156 */         Float2ObjectMap.Entry<V> entry = (Float2ObjectMap.Entry<V>)o;
/* 157 */         return this.map.remove(entry.getFloatKey(), entry.getValue());
/*     */       } 
/* 159 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 160 */       Object key = e.getKey();
/* 161 */       if (key == null || !(key instanceof Float))
/* 162 */         return false; 
/* 163 */       float k = ((Float)key).floatValue();
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
/*     */   public FloatSet keySet() {
/* 188 */     return new AbstractFloatSet()
/*     */       {
/*     */         public boolean contains(float k) {
/* 191 */           return AbstractFloat2ObjectMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 195 */           return AbstractFloat2ObjectMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 199 */           AbstractFloat2ObjectMap.this.clear();
/*     */         }
/*     */         
/*     */         public FloatIterator iterator() {
/* 203 */           return new FloatIterator()
/*     */             {
/* 205 */               private final ObjectIterator<Float2ObjectMap.Entry<V>> i = Float2ObjectMaps.fastIterator(AbstractFloat2ObjectMap.this);
/*     */               
/*     */               public float nextFloat() {
/* 208 */                 return ((Float2ObjectMap.Entry)this.i.next()).getFloatKey();
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
/* 241 */           return AbstractFloat2ObjectMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 245 */           return AbstractFloat2ObjectMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 249 */           AbstractFloat2ObjectMap.this.clear();
/*     */         }
/*     */         
/*     */         public ObjectIterator<V> iterator() {
/* 253 */           return new ObjectIterator<V>()
/*     */             {
/* 255 */               private final ObjectIterator<Float2ObjectMap.Entry<V>> i = Float2ObjectMaps.fastIterator(AbstractFloat2ObjectMap.this);
/*     */               
/*     */               public V next() {
/* 258 */                 return ((Float2ObjectMap.Entry<V>)this.i.next()).getValue();
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
/*     */   public void putAll(Map<? extends Float, ? extends V> m) {
/* 272 */     if (m instanceof Float2ObjectMap) {
/* 273 */       ObjectIterator<Float2ObjectMap.Entry<V>> i = Float2ObjectMaps.fastIterator((Float2ObjectMap)m);
/* 274 */       while (i.hasNext()) {
/* 275 */         Float2ObjectMap.Entry<? extends V> e = (Float2ObjectMap.Entry<? extends V>)i.next();
/* 276 */         put(e.getFloatKey(), e.getValue());
/*     */       } 
/*     */     } else {
/* 279 */       int n = m.size();
/* 280 */       Iterator<? extends Map.Entry<? extends Float, ? extends V>> i = m.entrySet().iterator();
/*     */       
/* 282 */       while (n-- != 0) {
/* 283 */         Map.Entry<? extends Float, ? extends V> e = i.next();
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
/* 298 */     ObjectIterator<Float2ObjectMap.Entry<V>> i = Float2ObjectMaps.fastIterator(this);
/* 299 */     while (n-- != 0)
/* 300 */       h += ((Float2ObjectMap.Entry)i.next()).hashCode(); 
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
/* 312 */     return float2ObjectEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 316 */     StringBuilder s = new StringBuilder();
/* 317 */     ObjectIterator<Float2ObjectMap.Entry<V>> i = Float2ObjectMaps.fastIterator(this);
/* 318 */     int n = size();
/*     */     
/* 320 */     boolean first = true;
/* 321 */     s.append("{");
/* 322 */     while (n-- != 0) {
/* 323 */       if (first) {
/* 324 */         first = false;
/*     */       } else {
/* 326 */         s.append(", ");
/* 327 */       }  Float2ObjectMap.Entry<V> e = (Float2ObjectMap.Entry<V>)i.next();
/* 328 */       s.append(String.valueOf(e.getFloatKey()));
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\AbstractFloat2ObjectMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */