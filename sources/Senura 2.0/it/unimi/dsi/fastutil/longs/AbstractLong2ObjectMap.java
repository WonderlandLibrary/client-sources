/*     */ package it.unimi.dsi.fastutil.longs;
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
/*     */ public abstract class AbstractLong2ObjectMap<V>
/*     */   extends AbstractLong2ObjectFunction<V>
/*     */   implements Long2ObjectMap<V>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(Object v) {
/*  48 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(long k) {
/*  52 */     ObjectIterator<Long2ObjectMap.Entry<V>> i = long2ObjectEntrySet().iterator();
/*  53 */     while (i.hasNext()) {
/*  54 */       if (((Long2ObjectMap.Entry)i.next()).getLongKey() == k)
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
/*     */     implements Long2ObjectMap.Entry<V>
/*     */   {
/*     */     protected long key;
/*     */     
/*     */     protected V value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Long key, V value) {
/*  77 */       this.key = key.longValue();
/*  78 */       this.value = value;
/*     */     }
/*     */     public BasicEntry(long key, V value) {
/*  81 */       this.key = key;
/*  82 */       this.value = value;
/*     */     }
/*     */     
/*     */     public long getLongKey() {
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
/* 101 */       if (o instanceof Long2ObjectMap.Entry) {
/* 102 */         Long2ObjectMap.Entry<V> entry = (Long2ObjectMap.Entry<V>)o;
/* 103 */         return (this.key == entry.getLongKey() && Objects.equals(this.value, entry.getValue()));
/*     */       } 
/* 105 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 106 */       Object key = e.getKey();
/* 107 */       if (key == null || !(key instanceof Long))
/* 108 */         return false; 
/* 109 */       Object value = e.getValue();
/* 110 */       return (this.key == ((Long)key).longValue() && Objects.equals(this.value, value));
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 114 */       return HashCommon.long2int(this.key) ^ ((this.value == null) ? 0 : this.value.hashCode());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 118 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet<V>
/*     */     extends AbstractObjectSet<Long2ObjectMap.Entry<V>>
/*     */   {
/*     */     protected final Long2ObjectMap<V> map;
/*     */     
/*     */     public BasicEntrySet(Long2ObjectMap<V> map) {
/* 128 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 133 */       if (!(o instanceof Map.Entry))
/* 134 */         return false; 
/* 135 */       if (o instanceof Long2ObjectMap.Entry) {
/* 136 */         Long2ObjectMap.Entry<V> entry = (Long2ObjectMap.Entry<V>)o;
/* 137 */         long l = entry.getLongKey();
/* 138 */         return (this.map.containsKey(l) && Objects.equals(this.map.get(l), entry.getValue()));
/*     */       } 
/* 140 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 141 */       Object key = e.getKey();
/* 142 */       if (key == null || !(key instanceof Long))
/* 143 */         return false; 
/* 144 */       long k = ((Long)key).longValue();
/* 145 */       Object value = e.getValue();
/* 146 */       return (this.map.containsKey(k) && Objects.equals(this.map.get(k), value));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 151 */       if (!(o instanceof Map.Entry))
/* 152 */         return false; 
/* 153 */       if (o instanceof Long2ObjectMap.Entry) {
/* 154 */         Long2ObjectMap.Entry<V> entry = (Long2ObjectMap.Entry<V>)o;
/* 155 */         return this.map.remove(entry.getLongKey(), entry.getValue());
/*     */       } 
/* 157 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 158 */       Object key = e.getKey();
/* 159 */       if (key == null || !(key instanceof Long))
/* 160 */         return false; 
/* 161 */       long k = ((Long)key).longValue();
/* 162 */       Object v = e.getValue();
/* 163 */       return this.map.remove(k, v);
/*     */     }
/*     */     
/*     */     public int size() {
/* 167 */       return this.map.size();
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
/*     */   public LongSet keySet() {
/* 186 */     return new AbstractLongSet()
/*     */       {
/*     */         public boolean contains(long k) {
/* 189 */           return AbstractLong2ObjectMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 193 */           return AbstractLong2ObjectMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 197 */           AbstractLong2ObjectMap.this.clear();
/*     */         }
/*     */         
/*     */         public LongIterator iterator() {
/* 201 */           return new LongIterator()
/*     */             {
/* 203 */               private final ObjectIterator<Long2ObjectMap.Entry<V>> i = Long2ObjectMaps.fastIterator(AbstractLong2ObjectMap.this);
/*     */               
/*     */               public long nextLong() {
/* 206 */                 return ((Long2ObjectMap.Entry)this.i.next()).getLongKey();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 210 */                 return this.i.hasNext();
/*     */               }
/*     */               
/*     */               public void remove() {
/* 214 */                 this.i.remove();
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
/* 236 */     return (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 239 */           return AbstractLong2ObjectMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 243 */           return AbstractLong2ObjectMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 247 */           AbstractLong2ObjectMap.this.clear();
/*     */         }
/*     */         
/*     */         public ObjectIterator<V> iterator() {
/* 251 */           return new ObjectIterator<V>()
/*     */             {
/* 253 */               private final ObjectIterator<Long2ObjectMap.Entry<V>> i = Long2ObjectMaps.fastIterator(AbstractLong2ObjectMap.this);
/*     */               
/*     */               public V next() {
/* 256 */                 return ((Long2ObjectMap.Entry<V>)this.i.next()).getValue();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 260 */                 return this.i.hasNext();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Long, ? extends V> m) {
/* 270 */     if (m instanceof Long2ObjectMap) {
/* 271 */       ObjectIterator<Long2ObjectMap.Entry<V>> i = Long2ObjectMaps.fastIterator((Long2ObjectMap)m);
/* 272 */       while (i.hasNext()) {
/* 273 */         Long2ObjectMap.Entry<? extends V> e = (Long2ObjectMap.Entry<? extends V>)i.next();
/* 274 */         put(e.getLongKey(), e.getValue());
/*     */       } 
/*     */     } else {
/* 277 */       int n = m.size();
/* 278 */       Iterator<? extends Map.Entry<? extends Long, ? extends V>> i = m.entrySet().iterator();
/*     */       
/* 280 */       while (n-- != 0) {
/* 281 */         Map.Entry<? extends Long, ? extends V> e = i.next();
/* 282 */         put(e.getKey(), e.getValue());
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
/* 295 */     int h = 0, n = size();
/* 296 */     ObjectIterator<Long2ObjectMap.Entry<V>> i = Long2ObjectMaps.fastIterator(this);
/* 297 */     while (n-- != 0)
/* 298 */       h += ((Long2ObjectMap.Entry)i.next()).hashCode(); 
/* 299 */     return h;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 303 */     if (o == this)
/* 304 */       return true; 
/* 305 */     if (!(o instanceof Map))
/* 306 */       return false; 
/* 307 */     Map<?, ?> m = (Map<?, ?>)o;
/* 308 */     if (m.size() != size())
/* 309 */       return false; 
/* 310 */     return long2ObjectEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 314 */     StringBuilder s = new StringBuilder();
/* 315 */     ObjectIterator<Long2ObjectMap.Entry<V>> i = Long2ObjectMaps.fastIterator(this);
/* 316 */     int n = size();
/*     */     
/* 318 */     boolean first = true;
/* 319 */     s.append("{");
/* 320 */     while (n-- != 0) {
/* 321 */       if (first) {
/* 322 */         first = false;
/*     */       } else {
/* 324 */         s.append(", ");
/* 325 */       }  Long2ObjectMap.Entry<V> e = (Long2ObjectMap.Entry<V>)i.next();
/* 326 */       s.append(String.valueOf(e.getLongKey()));
/* 327 */       s.append("=>");
/* 328 */       if (this == e.getValue()) {
/* 329 */         s.append("(this map)"); continue;
/*     */       } 
/* 331 */       s.append(String.valueOf(e.getValue()));
/*     */     } 
/* 333 */     s.append("}");
/* 334 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\AbstractLong2ObjectMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */