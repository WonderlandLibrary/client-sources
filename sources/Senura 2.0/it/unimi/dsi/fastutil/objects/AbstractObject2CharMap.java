/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*     */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*     */ import it.unimi.dsi.fastutil.chars.CharIterator;
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
/*     */ public abstract class AbstractObject2CharMap<K>
/*     */   extends AbstractObject2CharFunction<K>
/*     */   implements Object2CharMap<K>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(char v) {
/*  48 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object k) {
/*  52 */     ObjectIterator<Object2CharMap.Entry<K>> i = object2CharEntrySet().iterator();
/*  53 */     while (i.hasNext()) {
/*  54 */       if (((Object2CharMap.Entry)i.next()).getKey() == k)
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
/*     */     implements Object2CharMap.Entry<K>
/*     */   {
/*     */     protected K key;
/*     */     
/*     */     protected char value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(K key, Character value) {
/*  77 */       this.key = key;
/*  78 */       this.value = value.charValue();
/*     */     }
/*     */     public BasicEntry(K key, char value) {
/*  81 */       this.key = key;
/*  82 */       this.value = value;
/*     */     }
/*     */     
/*     */     public K getKey() {
/*  86 */       return this.key;
/*     */     }
/*     */     
/*     */     public char getCharValue() {
/*  90 */       return this.value;
/*     */     }
/*     */     
/*     */     public char setValue(char value) {
/*  94 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  99 */       if (!(o instanceof Map.Entry))
/* 100 */         return false; 
/* 101 */       if (o instanceof Object2CharMap.Entry) {
/* 102 */         Object2CharMap.Entry<K> entry = (Object2CharMap.Entry<K>)o;
/* 103 */         return (Objects.equals(this.key, entry.getKey()) && this.value == entry.getCharValue());
/*     */       } 
/* 105 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 106 */       Object key = e.getKey();
/* 107 */       Object value = e.getValue();
/* 108 */       if (value == null || !(value instanceof Character))
/* 109 */         return false; 
/* 110 */       return (Objects.equals(this.key, key) && this.value == ((Character)value).charValue());
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 114 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ this.value;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 118 */       return (new StringBuilder()).append(this.key).append("->").append(this.value).toString();
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet<K>
/*     */     extends AbstractObjectSet<Object2CharMap.Entry<K>>
/*     */   {
/*     */     protected final Object2CharMap<K> map;
/*     */     
/*     */     public BasicEntrySet(Object2CharMap<K> map) {
/* 128 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 133 */       if (!(o instanceof Map.Entry))
/* 134 */         return false; 
/* 135 */       if (o instanceof Object2CharMap.Entry) {
/* 136 */         Object2CharMap.Entry<K> entry = (Object2CharMap.Entry<K>)o;
/* 137 */         K k1 = entry.getKey();
/* 138 */         return (this.map.containsKey(k1) && this.map.getChar(k1) == entry.getCharValue());
/*     */       } 
/* 140 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 141 */       Object k = e.getKey();
/* 142 */       Object value = e.getValue();
/* 143 */       if (value == null || !(value instanceof Character))
/* 144 */         return false; 
/* 145 */       return (this.map.containsKey(k) && this.map.getChar(k) == ((Character)value).charValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 150 */       if (!(o instanceof Map.Entry))
/* 151 */         return false; 
/* 152 */       if (o instanceof Object2CharMap.Entry) {
/* 153 */         Object2CharMap.Entry<K> entry = (Object2CharMap.Entry<K>)o;
/* 154 */         return this.map.remove(entry.getKey(), entry.getCharValue());
/*     */       } 
/* 156 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 157 */       Object k = e.getKey();
/* 158 */       Object value = e.getValue();
/* 159 */       if (value == null || !(value instanceof Character))
/* 160 */         return false; 
/* 161 */       char v = ((Character)value).charValue();
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
/* 188 */           return AbstractObject2CharMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 192 */           return AbstractObject2CharMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 196 */           AbstractObject2CharMap.this.clear();
/*     */         }
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 200 */           return new ObjectIterator<K>()
/*     */             {
/* 202 */               private final ObjectIterator<Object2CharMap.Entry<K>> i = Object2CharMaps.fastIterator(AbstractObject2CharMap.this);
/*     */               
/*     */               public K next() {
/* 205 */                 return ((Object2CharMap.Entry<K>)this.i.next()).getKey();
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
/*     */   public CharCollection values() {
/* 235 */     return (CharCollection)new AbstractCharCollection()
/*     */       {
/*     */         public boolean contains(char k) {
/* 238 */           return AbstractObject2CharMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 242 */           return AbstractObject2CharMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 246 */           AbstractObject2CharMap.this.clear();
/*     */         }
/*     */         
/*     */         public CharIterator iterator() {
/* 250 */           return new CharIterator()
/*     */             {
/* 252 */               private final ObjectIterator<Object2CharMap.Entry<K>> i = Object2CharMaps.fastIterator(AbstractObject2CharMap.this);
/*     */               
/*     */               public char nextChar() {
/* 255 */                 return ((Object2CharMap.Entry)this.i.next()).getCharValue();
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
/*     */   public void putAll(Map<? extends K, ? extends Character> m) {
/* 269 */     if (m instanceof Object2CharMap) {
/* 270 */       ObjectIterator<Object2CharMap.Entry<K>> i = Object2CharMaps.fastIterator((Object2CharMap)m);
/* 271 */       while (i.hasNext()) {
/* 272 */         Object2CharMap.Entry<? extends K> e = i.next();
/* 273 */         put(e.getKey(), e.getCharValue());
/*     */       } 
/*     */     } else {
/* 276 */       int n = m.size();
/* 277 */       Iterator<? extends Map.Entry<? extends K, ? extends Character>> i = m.entrySet().iterator();
/*     */       
/* 279 */       while (n-- != 0) {
/* 280 */         Map.Entry<? extends K, ? extends Character> e = i.next();
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
/* 295 */     ObjectIterator<Object2CharMap.Entry<K>> i = Object2CharMaps.fastIterator(this);
/* 296 */     while (n-- != 0)
/* 297 */       h += ((Object2CharMap.Entry)i.next()).hashCode(); 
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
/* 309 */     return object2CharEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 313 */     StringBuilder s = new StringBuilder();
/* 314 */     ObjectIterator<Object2CharMap.Entry<K>> i = Object2CharMaps.fastIterator(this);
/* 315 */     int n = size();
/*     */     
/* 317 */     boolean first = true;
/* 318 */     s.append("{");
/* 319 */     while (n-- != 0) {
/* 320 */       if (first) {
/* 321 */         first = false;
/*     */       } else {
/* 323 */         s.append(", ");
/* 324 */       }  Object2CharMap.Entry<K> e = i.next();
/* 325 */       if (this == e.getKey()) {
/* 326 */         s.append("(this map)");
/*     */       } else {
/* 328 */         s.append(String.valueOf(e.getKey()));
/* 329 */       }  s.append("=>");
/* 330 */       s.append(String.valueOf(e.getCharValue()));
/*     */     } 
/* 332 */     s.append("}");
/* 333 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractObject2CharMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */