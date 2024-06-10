/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractObject2ObjectMap<K, V>
/*     */   extends AbstractObject2ObjectFunction<K, V>
/*     */   implements Object2ObjectMap<K, V>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(Object v) {
/*  48 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object k) {
/*  52 */     ObjectIterator<Object2ObjectMap.Entry<K, V>> i = object2ObjectEntrySet().iterator();
/*  53 */     while (i.hasNext()) {
/*  54 */       if (((Object2ObjectMap.Entry)i.next()).getKey() == k)
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
/*     */   public static class BasicEntry<K, V>
/*     */     implements Object2ObjectMap.Entry<K, V>
/*     */   {
/*     */     protected K key;
/*     */     
/*     */     protected V value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(K key, V value) {
/*  77 */       this.key = key;
/*  78 */       this.value = value;
/*     */     }
/*     */     
/*     */     public K getKey() {
/*  82 */       return this.key;
/*     */     }
/*     */     
/*     */     public V getValue() {
/*  86 */       return this.value;
/*     */     }
/*     */     
/*     */     public V setValue(V value) {
/*  90 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  95 */       if (!(o instanceof Map.Entry))
/*  96 */         return false; 
/*  97 */       if (o instanceof Object2ObjectMap.Entry) {
/*  98 */         Object2ObjectMap.Entry<K, V> entry = (Object2ObjectMap.Entry<K, V>)o;
/*  99 */         return (Objects.equals(this.key, entry.getKey()) && Objects.equals(this.value, entry.getValue()));
/*     */       } 
/* 101 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 102 */       Object key = e.getKey();
/* 103 */       Object value = e.getValue();
/* 104 */       return (Objects.equals(this.key, key) && Objects.equals(this.value, value));
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 108 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ ((this.value == null) ? 0 : this.value.hashCode());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 112 */       return (new StringBuilder()).append(this.key).append("->").append(this.value).toString();
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet<K, V>
/*     */     extends AbstractObjectSet<Object2ObjectMap.Entry<K, V>>
/*     */   {
/*     */     protected final Object2ObjectMap<K, V> map;
/*     */     
/*     */     public BasicEntrySet(Object2ObjectMap<K, V> map) {
/* 122 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 127 */       if (!(o instanceof Map.Entry))
/* 128 */         return false; 
/* 129 */       if (o instanceof Object2ObjectMap.Entry) {
/* 130 */         Object2ObjectMap.Entry<K, V> entry = (Object2ObjectMap.Entry<K, V>)o;
/* 131 */         K k1 = entry.getKey();
/* 132 */         return (this.map.containsKey(k1) && Objects.equals(this.map.get(k1), entry.getValue()));
/*     */       } 
/* 134 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 135 */       Object k = e.getKey();
/* 136 */       Object value = e.getValue();
/* 137 */       return (this.map.containsKey(k) && Objects.equals(this.map.get(k), value));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 142 */       if (!(o instanceof Map.Entry))
/* 143 */         return false; 
/* 144 */       if (o instanceof Object2ObjectMap.Entry) {
/* 145 */         Object2ObjectMap.Entry<K, V> entry = (Object2ObjectMap.Entry<K, V>)o;
/* 146 */         return this.map.remove(entry.getKey(), entry.getValue());
/*     */       } 
/* 148 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 149 */       Object k = e.getKey();
/* 150 */       Object v = e.getValue();
/* 151 */       return this.map.remove(k, v);
/*     */     }
/*     */     
/*     */     public int size() {
/* 155 */       return this.map.size();
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
/* 174 */     return new AbstractObjectSet<K>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 177 */           return AbstractObject2ObjectMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 181 */           return AbstractObject2ObjectMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 185 */           AbstractObject2ObjectMap.this.clear();
/*     */         }
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 189 */           return new ObjectIterator<K>()
/*     */             {
/* 191 */               private final ObjectIterator<Object2ObjectMap.Entry<K, V>> i = Object2ObjectMaps.fastIterator(AbstractObject2ObjectMap.this);
/*     */               
/*     */               public K next() {
/* 194 */                 return (K)((Object2ObjectMap.Entry)this.i.next()).getKey();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 198 */                 return this.i.hasNext();
/*     */               }
/*     */               
/*     */               public void remove() {
/* 202 */                 this.i.remove();
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
/* 224 */     return new AbstractObjectCollection<V>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 227 */           return AbstractObject2ObjectMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 231 */           return AbstractObject2ObjectMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 235 */           AbstractObject2ObjectMap.this.clear();
/*     */         }
/*     */         
/*     */         public ObjectIterator<V> iterator() {
/* 239 */           return new ObjectIterator<V>()
/*     */             {
/* 241 */               private final ObjectIterator<Object2ObjectMap.Entry<K, V>> i = Object2ObjectMaps.fastIterator(AbstractObject2ObjectMap.this);
/*     */               
/*     */               public V next() {
/* 244 */                 return (V)((Object2ObjectMap.Entry)this.i.next()).getValue();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 248 */                 return this.i.hasNext();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends K, ? extends V> m) {
/* 258 */     if (m instanceof Object2ObjectMap) {
/* 259 */       ObjectIterator<Object2ObjectMap.Entry<K, V>> i = Object2ObjectMaps.fastIterator((Object2ObjectMap)m);
/* 260 */       while (i.hasNext()) {
/* 261 */         Object2ObjectMap.Entry<? extends K, ? extends V> e = i.next();
/* 262 */         put(e.getKey(), e.getValue());
/*     */       } 
/*     */     } else {
/* 265 */       int n = m.size();
/* 266 */       Iterator<? extends Map.Entry<? extends K, ? extends V>> i = m.entrySet().iterator();
/*     */       
/* 268 */       while (n-- != 0) {
/* 269 */         Map.Entry<? extends K, ? extends V> e = i.next();
/* 270 */         put(e.getKey(), e.getValue());
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
/* 283 */     int h = 0, n = size();
/* 284 */     ObjectIterator<Object2ObjectMap.Entry<K, V>> i = Object2ObjectMaps.fastIterator(this);
/* 285 */     while (n-- != 0)
/* 286 */       h += ((Object2ObjectMap.Entry)i.next()).hashCode(); 
/* 287 */     return h;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 291 */     if (o == this)
/* 292 */       return true; 
/* 293 */     if (!(o instanceof Map))
/* 294 */       return false; 
/* 295 */     Map<?, ?> m = (Map<?, ?>)o;
/* 296 */     if (m.size() != size())
/* 297 */       return false; 
/* 298 */     return object2ObjectEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 302 */     StringBuilder s = new StringBuilder();
/* 303 */     ObjectIterator<Object2ObjectMap.Entry<K, V>> i = Object2ObjectMaps.fastIterator(this);
/* 304 */     int n = size();
/*     */     
/* 306 */     boolean first = true;
/* 307 */     s.append("{");
/* 308 */     while (n-- != 0) {
/* 309 */       if (first) {
/* 310 */         first = false;
/*     */       } else {
/* 312 */         s.append(", ");
/* 313 */       }  Object2ObjectMap.Entry<K, V> e = i.next();
/* 314 */       if (this == e.getKey()) {
/* 315 */         s.append("(this map)");
/*     */       } else {
/* 317 */         s.append(String.valueOf(e.getKey()));
/* 318 */       }  s.append("=>");
/* 319 */       if (this == e.getValue()) {
/* 320 */         s.append("(this map)"); continue;
/*     */       } 
/* 322 */       s.append(String.valueOf(e.getValue()));
/*     */     } 
/* 324 */     s.append("}");
/* 325 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractObject2ObjectMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */