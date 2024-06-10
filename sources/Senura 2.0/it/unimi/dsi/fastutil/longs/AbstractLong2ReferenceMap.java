/*     */ package it.unimi.dsi.fastutil.longs;
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
/*     */ public abstract class AbstractLong2ReferenceMap<V>
/*     */   extends AbstractLong2ReferenceFunction<V>
/*     */   implements Long2ReferenceMap<V>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(Object v) {
/*  48 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(long k) {
/*  52 */     ObjectIterator<Long2ReferenceMap.Entry<V>> i = long2ReferenceEntrySet().iterator();
/*  53 */     while (i.hasNext()) {
/*  54 */       if (((Long2ReferenceMap.Entry)i.next()).getLongKey() == k)
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
/*     */     implements Long2ReferenceMap.Entry<V>
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
/* 101 */       if (o instanceof Long2ReferenceMap.Entry) {
/* 102 */         Long2ReferenceMap.Entry<V> entry = (Long2ReferenceMap.Entry<V>)o;
/* 103 */         return (this.key == entry.getLongKey() && this.value == entry.getValue());
/*     */       } 
/* 105 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 106 */       Object key = e.getKey();
/* 107 */       if (key == null || !(key instanceof Long))
/* 108 */         return false; 
/* 109 */       Object value = e.getValue();
/* 110 */       return (this.key == ((Long)key).longValue() && this.value == value);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 114 */       return HashCommon.long2int(this.key) ^ (
/* 115 */         (this.value == null) ? 0 : System.identityHashCode(this.value));
/*     */     }
/*     */     
/*     */     public String toString() {
/* 119 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet<V>
/*     */     extends AbstractObjectSet<Long2ReferenceMap.Entry<V>>
/*     */   {
/*     */     protected final Long2ReferenceMap<V> map;
/*     */     
/*     */     public BasicEntrySet(Long2ReferenceMap<V> map) {
/* 129 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 134 */       if (!(o instanceof Map.Entry))
/* 135 */         return false; 
/* 136 */       if (o instanceof Long2ReferenceMap.Entry) {
/* 137 */         Long2ReferenceMap.Entry<V> entry = (Long2ReferenceMap.Entry<V>)o;
/* 138 */         long l = entry.getLongKey();
/* 139 */         return (this.map.containsKey(l) && this.map.get(l) == entry.getValue());
/*     */       } 
/* 141 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 142 */       Object key = e.getKey();
/* 143 */       if (key == null || !(key instanceof Long))
/* 144 */         return false; 
/* 145 */       long k = ((Long)key).longValue();
/* 146 */       Object value = e.getValue();
/* 147 */       return (this.map.containsKey(k) && this.map.get(k) == value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 152 */       if (!(o instanceof Map.Entry))
/* 153 */         return false; 
/* 154 */       if (o instanceof Long2ReferenceMap.Entry) {
/* 155 */         Long2ReferenceMap.Entry<V> entry = (Long2ReferenceMap.Entry<V>)o;
/* 156 */         return this.map.remove(entry.getLongKey(), entry.getValue());
/*     */       } 
/* 158 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 159 */       Object key = e.getKey();
/* 160 */       if (key == null || !(key instanceof Long))
/* 161 */         return false; 
/* 162 */       long k = ((Long)key).longValue();
/* 163 */       Object v = e.getValue();
/* 164 */       return this.map.remove(k, v);
/*     */     }
/*     */     
/*     */     public int size() {
/* 168 */       return this.map.size();
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
/* 187 */     return new AbstractLongSet()
/*     */       {
/*     */         public boolean contains(long k) {
/* 190 */           return AbstractLong2ReferenceMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 194 */           return AbstractLong2ReferenceMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 198 */           AbstractLong2ReferenceMap.this.clear();
/*     */         }
/*     */         
/*     */         public LongIterator iterator() {
/* 202 */           return new LongIterator()
/*     */             {
/* 204 */               private final ObjectIterator<Long2ReferenceMap.Entry<V>> i = Long2ReferenceMaps.fastIterator(AbstractLong2ReferenceMap.this);
/*     */               
/*     */               public long nextLong() {
/* 207 */                 return ((Long2ReferenceMap.Entry)this.i.next()).getLongKey();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 211 */                 return this.i.hasNext();
/*     */               }
/*     */               
/*     */               public void remove() {
/* 215 */                 this.i.remove();
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
/* 237 */     return (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 240 */           return AbstractLong2ReferenceMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 244 */           return AbstractLong2ReferenceMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 248 */           AbstractLong2ReferenceMap.this.clear();
/*     */         }
/*     */         
/*     */         public ObjectIterator<V> iterator() {
/* 252 */           return new ObjectIterator<V>()
/*     */             {
/* 254 */               private final ObjectIterator<Long2ReferenceMap.Entry<V>> i = Long2ReferenceMaps.fastIterator(AbstractLong2ReferenceMap.this);
/*     */               
/*     */               public V next() {
/* 257 */                 return ((Long2ReferenceMap.Entry<V>)this.i.next()).getValue();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 261 */                 return this.i.hasNext();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Long, ? extends V> m) {
/* 271 */     if (m instanceof Long2ReferenceMap) {
/* 272 */       ObjectIterator<Long2ReferenceMap.Entry<V>> i = Long2ReferenceMaps.fastIterator((Long2ReferenceMap)m);
/* 273 */       while (i.hasNext()) {
/* 274 */         Long2ReferenceMap.Entry<? extends V> e = (Long2ReferenceMap.Entry<? extends V>)i.next();
/* 275 */         put(e.getLongKey(), e.getValue());
/*     */       } 
/*     */     } else {
/* 278 */       int n = m.size();
/* 279 */       Iterator<? extends Map.Entry<? extends Long, ? extends V>> i = m.entrySet().iterator();
/*     */       
/* 281 */       while (n-- != 0) {
/* 282 */         Map.Entry<? extends Long, ? extends V> e = i.next();
/* 283 */         put(e.getKey(), e.getValue());
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
/* 296 */     int h = 0, n = size();
/* 297 */     ObjectIterator<Long2ReferenceMap.Entry<V>> i = Long2ReferenceMaps.fastIterator(this);
/* 298 */     while (n-- != 0)
/* 299 */       h += ((Long2ReferenceMap.Entry)i.next()).hashCode(); 
/* 300 */     return h;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 304 */     if (o == this)
/* 305 */       return true; 
/* 306 */     if (!(o instanceof Map))
/* 307 */       return false; 
/* 308 */     Map<?, ?> m = (Map<?, ?>)o;
/* 309 */     if (m.size() != size())
/* 310 */       return false; 
/* 311 */     return long2ReferenceEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 315 */     StringBuilder s = new StringBuilder();
/* 316 */     ObjectIterator<Long2ReferenceMap.Entry<V>> i = Long2ReferenceMaps.fastIterator(this);
/* 317 */     int n = size();
/*     */     
/* 319 */     boolean first = true;
/* 320 */     s.append("{");
/* 321 */     while (n-- != 0) {
/* 322 */       if (first) {
/* 323 */         first = false;
/*     */       } else {
/* 325 */         s.append(", ");
/* 326 */       }  Long2ReferenceMap.Entry<V> e = (Long2ReferenceMap.Entry<V>)i.next();
/* 327 */       s.append(String.valueOf(e.getLongKey()));
/* 328 */       s.append("=>");
/* 329 */       if (this == e.getValue()) {
/* 330 */         s.append("(this map)"); continue;
/*     */       } 
/* 332 */       s.append(String.valueOf(e.getValue()));
/*     */     } 
/* 334 */     s.append("}");
/* 335 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\AbstractLong2ReferenceMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */