/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
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
/*     */ 
/*     */ 
/*     */ public abstract class AbstractReference2IntMap<K>
/*     */   extends AbstractReference2IntFunction<K>
/*     */   implements Reference2IntMap<K>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(int v) {
/*  48 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object k) {
/*  52 */     ObjectIterator<Reference2IntMap.Entry<K>> i = reference2IntEntrySet().iterator();
/*  53 */     while (i.hasNext()) {
/*  54 */       if (((Reference2IntMap.Entry)i.next()).getKey() == k)
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
/*     */     implements Reference2IntMap.Entry<K>
/*     */   {
/*     */     protected K key;
/*     */     
/*     */     protected int value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(K key, Integer value) {
/*  77 */       this.key = key;
/*  78 */       this.value = value.intValue();
/*     */     }
/*     */     public BasicEntry(K key, int value) {
/*  81 */       this.key = key;
/*  82 */       this.value = value;
/*     */     }
/*     */     
/*     */     public K getKey() {
/*  86 */       return this.key;
/*     */     }
/*     */     
/*     */     public int getIntValue() {
/*  90 */       return this.value;
/*     */     }
/*     */     
/*     */     public int setValue(int value) {
/*  94 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  99 */       if (!(o instanceof Map.Entry))
/* 100 */         return false; 
/* 101 */       if (o instanceof Reference2IntMap.Entry) {
/* 102 */         Reference2IntMap.Entry<K> entry = (Reference2IntMap.Entry<K>)o;
/* 103 */         return (this.key == entry.getKey() && this.value == entry.getIntValue());
/*     */       } 
/* 105 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 106 */       Object key = e.getKey();
/* 107 */       Object value = e.getValue();
/* 108 */       if (value == null || !(value instanceof Integer))
/* 109 */         return false; 
/* 110 */       return (this.key == key && this.value == ((Integer)value).intValue());
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 114 */       return System.identityHashCode(this.key) ^ this.value;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 118 */       return (new StringBuilder()).append(this.key).append("->").append(this.value).toString();
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet<K>
/*     */     extends AbstractObjectSet<Reference2IntMap.Entry<K>>
/*     */   {
/*     */     protected final Reference2IntMap<K> map;
/*     */     
/*     */     public BasicEntrySet(Reference2IntMap<K> map) {
/* 128 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 133 */       if (!(o instanceof Map.Entry))
/* 134 */         return false; 
/* 135 */       if (o instanceof Reference2IntMap.Entry) {
/* 136 */         Reference2IntMap.Entry<K> entry = (Reference2IntMap.Entry<K>)o;
/* 137 */         K k1 = entry.getKey();
/* 138 */         return (this.map.containsKey(k1) && this.map.getInt(k1) == entry.getIntValue());
/*     */       } 
/* 140 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 141 */       Object k = e.getKey();
/* 142 */       Object value = e.getValue();
/* 143 */       if (value == null || !(value instanceof Integer))
/* 144 */         return false; 
/* 145 */       return (this.map.containsKey(k) && this.map.getInt(k) == ((Integer)value).intValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 150 */       if (!(o instanceof Map.Entry))
/* 151 */         return false; 
/* 152 */       if (o instanceof Reference2IntMap.Entry) {
/* 153 */         Reference2IntMap.Entry<K> entry = (Reference2IntMap.Entry<K>)o;
/* 154 */         return this.map.remove(entry.getKey(), entry.getIntValue());
/*     */       } 
/* 156 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 157 */       Object k = e.getKey();
/* 158 */       Object value = e.getValue();
/* 159 */       if (value == null || !(value instanceof Integer))
/* 160 */         return false; 
/* 161 */       int v = ((Integer)value).intValue();
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
/*     */   public ReferenceSet<K> keySet() {
/* 185 */     return new AbstractReferenceSet<K>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 188 */           return AbstractReference2IntMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 192 */           return AbstractReference2IntMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 196 */           AbstractReference2IntMap.this.clear();
/*     */         }
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 200 */           return new ObjectIterator<K>()
/*     */             {
/* 202 */               private final ObjectIterator<Reference2IntMap.Entry<K>> i = Reference2IntMaps.fastIterator(AbstractReference2IntMap.this);
/*     */               
/*     */               public K next() {
/* 205 */                 return ((Reference2IntMap.Entry<K>)this.i.next()).getKey();
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
/*     */   public IntCollection values() {
/* 235 */     return (IntCollection)new AbstractIntCollection()
/*     */       {
/*     */         public boolean contains(int k) {
/* 238 */           return AbstractReference2IntMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 242 */           return AbstractReference2IntMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 246 */           AbstractReference2IntMap.this.clear();
/*     */         }
/*     */         
/*     */         public IntIterator iterator() {
/* 250 */           return new IntIterator()
/*     */             {
/* 252 */               private final ObjectIterator<Reference2IntMap.Entry<K>> i = Reference2IntMaps.fastIterator(AbstractReference2IntMap.this);
/*     */               
/*     */               public int nextInt() {
/* 255 */                 return ((Reference2IntMap.Entry)this.i.next()).getIntValue();
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
/*     */   public void putAll(Map<? extends K, ? extends Integer> m) {
/* 269 */     if (m instanceof Reference2IntMap) {
/* 270 */       ObjectIterator<Reference2IntMap.Entry<K>> i = Reference2IntMaps.fastIterator((Reference2IntMap)m);
/* 271 */       while (i.hasNext()) {
/* 272 */         Reference2IntMap.Entry<? extends K> e = i.next();
/* 273 */         put(e.getKey(), e.getIntValue());
/*     */       } 
/*     */     } else {
/* 276 */       int n = m.size();
/* 277 */       Iterator<? extends Map.Entry<? extends K, ? extends Integer>> i = m.entrySet().iterator();
/*     */       
/* 279 */       while (n-- != 0) {
/* 280 */         Map.Entry<? extends K, ? extends Integer> e = i.next();
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
/* 295 */     ObjectIterator<Reference2IntMap.Entry<K>> i = Reference2IntMaps.fastIterator(this);
/* 296 */     while (n-- != 0)
/* 297 */       h += ((Reference2IntMap.Entry)i.next()).hashCode(); 
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
/* 309 */     return reference2IntEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 313 */     StringBuilder s = new StringBuilder();
/* 314 */     ObjectIterator<Reference2IntMap.Entry<K>> i = Reference2IntMaps.fastIterator(this);
/* 315 */     int n = size();
/*     */     
/* 317 */     boolean first = true;
/* 318 */     s.append("{");
/* 319 */     while (n-- != 0) {
/* 320 */       if (first) {
/* 321 */         first = false;
/*     */       } else {
/* 323 */         s.append(", ");
/* 324 */       }  Reference2IntMap.Entry<K> e = i.next();
/* 325 */       if (this == e.getKey()) {
/* 326 */         s.append("(this map)");
/*     */       } else {
/* 328 */         s.append(String.valueOf(e.getKey()));
/* 329 */       }  s.append("=>");
/* 330 */       s.append(String.valueOf(e.getIntValue()));
/*     */     } 
/* 332 */     s.append("}");
/* 333 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractReference2IntMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */