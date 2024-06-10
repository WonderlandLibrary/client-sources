/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
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
/*     */ public abstract class AbstractReference2BooleanMap<K>
/*     */   extends AbstractReference2BooleanFunction<K>
/*     */   implements Reference2BooleanMap<K>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(boolean v) {
/*  48 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object k) {
/*  52 */     ObjectIterator<Reference2BooleanMap.Entry<K>> i = reference2BooleanEntrySet().iterator();
/*  53 */     while (i.hasNext()) {
/*  54 */       if (((Reference2BooleanMap.Entry)i.next()).getKey() == k)
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
/*     */     implements Reference2BooleanMap.Entry<K>
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
/* 101 */       if (o instanceof Reference2BooleanMap.Entry) {
/* 102 */         Reference2BooleanMap.Entry<K> entry = (Reference2BooleanMap.Entry<K>)o;
/* 103 */         return (this.key == entry.getKey() && this.value == entry.getBooleanValue());
/*     */       } 
/* 105 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 106 */       Object key = e.getKey();
/* 107 */       Object value = e.getValue();
/* 108 */       if (value == null || !(value instanceof Boolean))
/* 109 */         return false; 
/* 110 */       return (this.key == key && this.value == ((Boolean)value).booleanValue());
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 114 */       return System.identityHashCode(this.key) ^ (this.value ? 1231 : 1237);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 118 */       return (new StringBuilder()).append(this.key).append("->").append(this.value).toString();
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet<K>
/*     */     extends AbstractObjectSet<Reference2BooleanMap.Entry<K>>
/*     */   {
/*     */     protected final Reference2BooleanMap<K> map;
/*     */     
/*     */     public BasicEntrySet(Reference2BooleanMap<K> map) {
/* 128 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 133 */       if (!(o instanceof Map.Entry))
/* 134 */         return false; 
/* 135 */       if (o instanceof Reference2BooleanMap.Entry) {
/* 136 */         Reference2BooleanMap.Entry<K> entry = (Reference2BooleanMap.Entry<K>)o;
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
/* 152 */       if (o instanceof Reference2BooleanMap.Entry) {
/* 153 */         Reference2BooleanMap.Entry<K> entry = (Reference2BooleanMap.Entry<K>)o;
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
/*     */   public ReferenceSet<K> keySet() {
/* 185 */     return new AbstractReferenceSet<K>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 188 */           return AbstractReference2BooleanMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 192 */           return AbstractReference2BooleanMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 196 */           AbstractReference2BooleanMap.this.clear();
/*     */         }
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 200 */           return new ObjectIterator<K>()
/*     */             {
/* 202 */               private final ObjectIterator<Reference2BooleanMap.Entry<K>> i = Reference2BooleanMaps.fastIterator(AbstractReference2BooleanMap.this);
/*     */               
/*     */               public K next() {
/* 205 */                 return ((Reference2BooleanMap.Entry<K>)this.i.next()).getKey();
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
/* 238 */           return AbstractReference2BooleanMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 242 */           return AbstractReference2BooleanMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 246 */           AbstractReference2BooleanMap.this.clear();
/*     */         }
/*     */         
/*     */         public BooleanIterator iterator() {
/* 250 */           return new BooleanIterator()
/*     */             {
/* 252 */               private final ObjectIterator<Reference2BooleanMap.Entry<K>> i = Reference2BooleanMaps.fastIterator(AbstractReference2BooleanMap.this);
/*     */               
/*     */               public boolean nextBoolean() {
/* 255 */                 return ((Reference2BooleanMap.Entry)this.i.next()).getBooleanValue();
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
/* 269 */     if (m instanceof Reference2BooleanMap) {
/*     */       
/* 271 */       ObjectIterator<Reference2BooleanMap.Entry<K>> i = Reference2BooleanMaps.fastIterator((Reference2BooleanMap)m);
/* 272 */       while (i.hasNext()) {
/* 273 */         Reference2BooleanMap.Entry<? extends K> e = i.next();
/* 274 */         put(e.getKey(), e.getBooleanValue());
/*     */       } 
/*     */     } else {
/* 277 */       int n = m.size();
/* 278 */       Iterator<? extends Map.Entry<? extends K, ? extends Boolean>> i = m.entrySet().iterator();
/*     */       
/* 280 */       while (n-- != 0) {
/* 281 */         Map.Entry<? extends K, ? extends Boolean> e = i.next();
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
/* 296 */     ObjectIterator<Reference2BooleanMap.Entry<K>> i = Reference2BooleanMaps.fastIterator(this);
/* 297 */     while (n-- != 0)
/* 298 */       h += ((Reference2BooleanMap.Entry)i.next()).hashCode(); 
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
/* 310 */     return reference2BooleanEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 314 */     StringBuilder s = new StringBuilder();
/* 315 */     ObjectIterator<Reference2BooleanMap.Entry<K>> i = Reference2BooleanMaps.fastIterator(this);
/* 316 */     int n = size();
/*     */     
/* 318 */     boolean first = true;
/* 319 */     s.append("{");
/* 320 */     while (n-- != 0) {
/* 321 */       if (first) {
/* 322 */         first = false;
/*     */       } else {
/* 324 */         s.append(", ");
/* 325 */       }  Reference2BooleanMap.Entry<K> e = i.next();
/* 326 */       if (this == e.getKey()) {
/* 327 */         s.append("(this map)");
/*     */       } else {
/* 329 */         s.append(String.valueOf(e.getKey()));
/* 330 */       }  s.append("=>");
/* 331 */       s.append(String.valueOf(e.getBooleanValue()));
/*     */     } 
/* 333 */     s.append("}");
/* 334 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractReference2BooleanMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */