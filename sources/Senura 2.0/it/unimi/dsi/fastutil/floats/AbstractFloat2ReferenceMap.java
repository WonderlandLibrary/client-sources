/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public abstract class AbstractFloat2ReferenceMap<V>
/*     */   extends AbstractFloat2ReferenceFunction<V>
/*     */   implements Float2ReferenceMap<V>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(Object v) {
/*  48 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(float k) {
/*  52 */     ObjectIterator<Float2ReferenceMap.Entry<V>> i = float2ReferenceEntrySet().iterator();
/*  53 */     while (i.hasNext()) {
/*  54 */       if (((Float2ReferenceMap.Entry)i.next()).getFloatKey() == k)
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
/*     */     implements Float2ReferenceMap.Entry<V>
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
/* 101 */       if (o instanceof Float2ReferenceMap.Entry) {
/* 102 */         Float2ReferenceMap.Entry<V> entry = (Float2ReferenceMap.Entry<V>)o;
/* 103 */         return (Float.floatToIntBits(this.key) == Float.floatToIntBits(entry.getFloatKey()) && this.value == entry
/* 104 */           .getValue());
/*     */       } 
/* 106 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 107 */       Object key = e.getKey();
/* 108 */       if (key == null || !(key instanceof Float))
/* 109 */         return false; 
/* 110 */       Object value = e.getValue();
/* 111 */       return (Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)key).floatValue()) && this.value == value);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 116 */       return HashCommon.float2int(this.key) ^ (
/* 117 */         (this.value == null) ? 0 : System.identityHashCode(this.value));
/*     */     }
/*     */     
/*     */     public String toString() {
/* 121 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet<V>
/*     */     extends AbstractObjectSet<Float2ReferenceMap.Entry<V>>
/*     */   {
/*     */     protected final Float2ReferenceMap<V> map;
/*     */     
/*     */     public BasicEntrySet(Float2ReferenceMap<V> map) {
/* 131 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 136 */       if (!(o instanceof Map.Entry))
/* 137 */         return false; 
/* 138 */       if (o instanceof Float2ReferenceMap.Entry) {
/* 139 */         Float2ReferenceMap.Entry<V> entry = (Float2ReferenceMap.Entry<V>)o;
/* 140 */         float f = entry.getFloatKey();
/* 141 */         return (this.map.containsKey(f) && this.map.get(f) == entry.getValue());
/*     */       } 
/* 143 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 144 */       Object key = e.getKey();
/* 145 */       if (key == null || !(key instanceof Float))
/* 146 */         return false; 
/* 147 */       float k = ((Float)key).floatValue();
/* 148 */       Object value = e.getValue();
/* 149 */       return (this.map.containsKey(k) && this.map.get(k) == value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 154 */       if (!(o instanceof Map.Entry))
/* 155 */         return false; 
/* 156 */       if (o instanceof Float2ReferenceMap.Entry) {
/* 157 */         Float2ReferenceMap.Entry<V> entry = (Float2ReferenceMap.Entry<V>)o;
/* 158 */         return this.map.remove(entry.getFloatKey(), entry.getValue());
/*     */       } 
/* 160 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 161 */       Object key = e.getKey();
/* 162 */       if (key == null || !(key instanceof Float))
/* 163 */         return false; 
/* 164 */       float k = ((Float)key).floatValue();
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
/*     */   public FloatSet keySet() {
/* 189 */     return new AbstractFloatSet()
/*     */       {
/*     */         public boolean contains(float k) {
/* 192 */           return AbstractFloat2ReferenceMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 196 */           return AbstractFloat2ReferenceMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 200 */           AbstractFloat2ReferenceMap.this.clear();
/*     */         }
/*     */         
/*     */         public FloatIterator iterator() {
/* 204 */           return new FloatIterator()
/*     */             {
/* 206 */               private final ObjectIterator<Float2ReferenceMap.Entry<V>> i = Float2ReferenceMaps.fastIterator(AbstractFloat2ReferenceMap.this);
/*     */               
/*     */               public float nextFloat() {
/* 209 */                 return ((Float2ReferenceMap.Entry)this.i.next()).getFloatKey();
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
/* 242 */           return AbstractFloat2ReferenceMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 246 */           return AbstractFloat2ReferenceMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 250 */           AbstractFloat2ReferenceMap.this.clear();
/*     */         }
/*     */         
/*     */         public ObjectIterator<V> iterator() {
/* 254 */           return new ObjectIterator<V>()
/*     */             {
/* 256 */               private final ObjectIterator<Float2ReferenceMap.Entry<V>> i = Float2ReferenceMaps.fastIterator(AbstractFloat2ReferenceMap.this);
/*     */               
/*     */               public V next() {
/* 259 */                 return ((Float2ReferenceMap.Entry<V>)this.i.next()).getValue();
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
/*     */   public void putAll(Map<? extends Float, ? extends V> m) {
/* 273 */     if (m instanceof Float2ReferenceMap) {
/* 274 */       ObjectIterator<Float2ReferenceMap.Entry<V>> i = Float2ReferenceMaps.fastIterator((Float2ReferenceMap)m);
/* 275 */       while (i.hasNext()) {
/* 276 */         Float2ReferenceMap.Entry<? extends V> e = (Float2ReferenceMap.Entry<? extends V>)i.next();
/* 277 */         put(e.getFloatKey(), e.getValue());
/*     */       } 
/*     */     } else {
/* 280 */       int n = m.size();
/* 281 */       Iterator<? extends Map.Entry<? extends Float, ? extends V>> i = m.entrySet().iterator();
/*     */       
/* 283 */       while (n-- != 0) {
/* 284 */         Map.Entry<? extends Float, ? extends V> e = i.next();
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
/* 299 */     ObjectIterator<Float2ReferenceMap.Entry<V>> i = Float2ReferenceMaps.fastIterator(this);
/* 300 */     while (n-- != 0)
/* 301 */       h += ((Float2ReferenceMap.Entry)i.next()).hashCode(); 
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
/* 313 */     return float2ReferenceEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 317 */     StringBuilder s = new StringBuilder();
/* 318 */     ObjectIterator<Float2ReferenceMap.Entry<V>> i = Float2ReferenceMaps.fastIterator(this);
/* 319 */     int n = size();
/*     */     
/* 321 */     boolean first = true;
/* 322 */     s.append("{");
/* 323 */     while (n-- != 0) {
/* 324 */       if (first) {
/* 325 */         first = false;
/*     */       } else {
/* 327 */         s.append(", ");
/* 328 */       }  Float2ReferenceMap.Entry<V> e = (Float2ReferenceMap.Entry<V>)i.next();
/* 329 */       s.append(String.valueOf(e.getFloatKey()));
/* 330 */       s.append("=>");
/* 331 */       if (this == e.getValue()) {
/* 332 */         s.append("(this map)"); continue;
/*     */       } 
/* 334 */       s.append(String.valueOf(e.getValue()));
/*     */     } 
/* 336 */     s.append("}");
/* 337 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\AbstractFloat2ReferenceMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */