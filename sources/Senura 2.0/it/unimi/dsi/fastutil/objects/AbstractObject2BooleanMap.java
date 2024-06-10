/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
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
/*     */ public abstract class AbstractObject2BooleanMap<K>
/*     */   extends AbstractObject2BooleanFunction<K>
/*     */   implements Object2BooleanMap<K>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(boolean v) {
/*  48 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object k) {
/*  52 */     ObjectIterator<Object2BooleanMap.Entry<K>> i = object2BooleanEntrySet().iterator();
/*  53 */     while (i.hasNext()) {
/*  54 */       if (((Object2BooleanMap.Entry)i.next()).getKey() == k)
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
/*     */     implements Object2BooleanMap.Entry<K>
/*     */   {
/*     */     protected K key;
/*     */     
/*     */     protected boolean value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(K key, Boolean value) {
/*  77 */       this.key = key;
/*  78 */       this.value = value.booleanValue();
/*     */     }
/*     */     public BasicEntry(K key, boolean value) {
/*  81 */       this.key = key;
/*  82 */       this.value = value;
/*     */     }
/*     */     
/*     */     public K getKey() {
/*  86 */       return this.key;
/*     */     }
/*     */     
/*     */     public boolean getBooleanValue() {
/*  90 */       return this.value;
/*     */     }
/*     */     
/*     */     public boolean setValue(boolean value) {
/*  94 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  99 */       if (!(o instanceof Map.Entry))
/* 100 */         return false; 
/* 101 */       if (o instanceof Object2BooleanMap.Entry) {
/* 102 */         Object2BooleanMap.Entry<K> entry = (Object2BooleanMap.Entry<K>)o;
/* 103 */         return (Objects.equals(this.key, entry.getKey()) && this.value == entry.getBooleanValue());
/*     */       } 
/* 105 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 106 */       Object key = e.getKey();
/* 107 */       Object value = e.getValue();
/* 108 */       if (value == null || !(value instanceof Boolean))
/* 109 */         return false; 
/* 110 */       return (Objects.equals(this.key, key) && this.value == ((Boolean)value).booleanValue());
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 114 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ (this.value ? 1231 : 1237);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 118 */       return (new StringBuilder()).append(this.key).append("->").append(this.value).toString();
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet<K>
/*     */     extends AbstractObjectSet<Object2BooleanMap.Entry<K>>
/*     */   {
/*     */     protected final Object2BooleanMap<K> map;
/*     */     
/*     */     public BasicEntrySet(Object2BooleanMap<K> map) {
/* 128 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 133 */       if (!(o instanceof Map.Entry))
/* 134 */         return false; 
/* 135 */       if (o instanceof Object2BooleanMap.Entry) {
/* 136 */         Object2BooleanMap.Entry<K> entry = (Object2BooleanMap.Entry<K>)o;
/* 137 */         K k1 = entry.getKey();
/* 138 */         return (this.map.containsKey(k1) && this.map.getBoolean(k1) == entry.getBooleanValue());
/*     */       } 
/* 140 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 141 */       Object k = e.getKey();
/* 142 */       Object value = e.getValue();
/* 143 */       if (value == null || !(value instanceof Boolean))
/* 144 */         return false; 
/* 145 */       return (this.map.containsKey(k) && this.map.getBoolean(k) == ((Boolean)value).booleanValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 150 */       if (!(o instanceof Map.Entry))
/* 151 */         return false; 
/* 152 */       if (o instanceof Object2BooleanMap.Entry) {
/* 153 */         Object2BooleanMap.Entry<K> entry = (Object2BooleanMap.Entry<K>)o;
/* 154 */         return this.map.remove(entry.getKey(), entry.getBooleanValue());
/*     */       } 
/* 156 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 157 */       Object k = e.getKey();
/* 158 */       Object value = e.getValue();
/* 159 */       if (value == null || !(value instanceof Boolean))
/* 160 */         return false; 
/* 161 */       boolean v = ((Boolean)value).booleanValue();
/* 162 */       return this.map.remove(k, v);
/*     */     }
/*     */     
/*     */     public int size() {
/* 166 */       return this.map.size();
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
/* 185 */     return new AbstractObjectSet<K>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 188 */           return AbstractObject2BooleanMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 192 */           return AbstractObject2BooleanMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 196 */           AbstractObject2BooleanMap.this.clear();
/*     */         }
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 200 */           return new ObjectIterator<K>()
/*     */             {
/* 202 */               private final ObjectIterator<Object2BooleanMap.Entry<K>> i = Object2BooleanMaps.fastIterator(AbstractObject2BooleanMap.this);
/*     */               
/*     */               public K next() {
/* 205 */                 return ((Object2BooleanMap.Entry<K>)this.i.next()).getKey();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 209 */                 return this.i.hasNext();
/*     */               }
/*     */               
/*     */               public void remove() {
/* 213 */                 this.i.remove();
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
/*     */   public BooleanCollection values() {
/* 235 */     return (BooleanCollection)new AbstractBooleanCollection()
/*     */       {
/*     */         public boolean contains(boolean k) {
/* 238 */           return AbstractObject2BooleanMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 242 */           return AbstractObject2BooleanMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 246 */           AbstractObject2BooleanMap.this.clear();
/*     */         }
/*     */         
/*     */         public BooleanIterator iterator() {
/* 250 */           return new BooleanIterator()
/*     */             {
/* 252 */               private final ObjectIterator<Object2BooleanMap.Entry<K>> i = Object2BooleanMaps.fastIterator(AbstractObject2BooleanMap.this);
/*     */               
/*     */               public boolean nextBoolean() {
/* 255 */                 return ((Object2BooleanMap.Entry)this.i.next()).getBooleanValue();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 259 */                 return this.i.hasNext();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends K, ? extends Boolean> m) {
/* 269 */     if (m instanceof Object2BooleanMap) {
/* 270 */       ObjectIterator<Object2BooleanMap.Entry<K>> i = Object2BooleanMaps.fastIterator((Object2BooleanMap)m);
/* 271 */       while (i.hasNext()) {
/* 272 */         Object2BooleanMap.Entry<? extends K> e = i.next();
/* 273 */         put(e.getKey(), e.getBooleanValue());
/*     */       } 
/*     */     } else {
/* 276 */       int n = m.size();
/* 277 */       Iterator<? extends Map.Entry<? extends K, ? extends Boolean>> i = m.entrySet().iterator();
/*     */       
/* 279 */       while (n-- != 0) {
/* 280 */         Map.Entry<? extends K, ? extends Boolean> e = i.next();
/* 281 */         put(e.getKey(), e.getValue());
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
/* 294 */     int h = 0, n = size();
/* 295 */     ObjectIterator<Object2BooleanMap.Entry<K>> i = Object2BooleanMaps.fastIterator(this);
/* 296 */     while (n-- != 0)
/* 297 */       h += ((Object2BooleanMap.Entry)i.next()).hashCode(); 
/* 298 */     return h;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 302 */     if (o == this)
/* 303 */       return true; 
/* 304 */     if (!(o instanceof Map))
/* 305 */       return false; 
/* 306 */     Map<?, ?> m = (Map<?, ?>)o;
/* 307 */     if (m.size() != size())
/* 308 */       return false; 
/* 309 */     return object2BooleanEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 313 */     StringBuilder s = new StringBuilder();
/* 314 */     ObjectIterator<Object2BooleanMap.Entry<K>> i = Object2BooleanMaps.fastIterator(this);
/* 315 */     int n = size();
/*     */     
/* 317 */     boolean first = true;
/* 318 */     s.append("{");
/* 319 */     while (n-- != 0) {
/* 320 */       if (first) {
/* 321 */         first = false;
/*     */       } else {
/* 323 */         s.append(", ");
/* 324 */       }  Object2BooleanMap.Entry<K> e = i.next();
/* 325 */       if (this == e.getKey()) {
/* 326 */         s.append("(this map)");
/*     */       } else {
/* 328 */         s.append(String.valueOf(e.getKey()));
/* 329 */       }  s.append("=>");
/* 330 */       s.append(String.valueOf(e.getBooleanValue()));
/*     */     } 
/* 332 */     s.append("}");
/* 333 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractObject2BooleanMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */