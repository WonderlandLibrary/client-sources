/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
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
/*     */ 
/*     */ public abstract class AbstractChar2ReferenceMap<V>
/*     */   extends AbstractChar2ReferenceFunction<V>
/*     */   implements Char2ReferenceMap<V>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(Object v) {
/*  48 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(char k) {
/*  52 */     ObjectIterator<Char2ReferenceMap.Entry<V>> i = char2ReferenceEntrySet().iterator();
/*  53 */     while (i.hasNext()) {
/*  54 */       if (((Char2ReferenceMap.Entry)i.next()).getCharKey() == k)
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
/*     */     implements Char2ReferenceMap.Entry<V>
/*     */   {
/*     */     protected char key;
/*     */     
/*     */     protected V value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Character key, V value) {
/*  77 */       this.key = key.charValue();
/*  78 */       this.value = value;
/*     */     }
/*     */     public BasicEntry(char key, V value) {
/*  81 */       this.key = key;
/*  82 */       this.value = value;
/*     */     }
/*     */     
/*     */     public char getCharKey() {
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
/* 101 */       if (o instanceof Char2ReferenceMap.Entry) {
/* 102 */         Char2ReferenceMap.Entry<V> entry = (Char2ReferenceMap.Entry<V>)o;
/* 103 */         return (this.key == entry.getCharKey() && this.value == entry.getValue());
/*     */       } 
/* 105 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 106 */       Object key = e.getKey();
/* 107 */       if (key == null || !(key instanceof Character))
/* 108 */         return false; 
/* 109 */       Object value = e.getValue();
/* 110 */       return (this.key == ((Character)key).charValue() && this.value == value);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 114 */       return this.key ^ ((this.value == null) ? 0 : System.identityHashCode(this.value));
/*     */     }
/*     */     
/*     */     public String toString() {
/* 118 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet<V>
/*     */     extends AbstractObjectSet<Char2ReferenceMap.Entry<V>>
/*     */   {
/*     */     protected final Char2ReferenceMap<V> map;
/*     */     
/*     */     public BasicEntrySet(Char2ReferenceMap<V> map) {
/* 128 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 133 */       if (!(o instanceof Map.Entry))
/* 134 */         return false; 
/* 135 */       if (o instanceof Char2ReferenceMap.Entry) {
/* 136 */         Char2ReferenceMap.Entry<V> entry = (Char2ReferenceMap.Entry<V>)o;
/* 137 */         char c = entry.getCharKey();
/* 138 */         return (this.map.containsKey(c) && this.map.get(c) == entry.getValue());
/*     */       } 
/* 140 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 141 */       Object key = e.getKey();
/* 142 */       if (key == null || !(key instanceof Character))
/* 143 */         return false; 
/* 144 */       char k = ((Character)key).charValue();
/* 145 */       Object value = e.getValue();
/* 146 */       return (this.map.containsKey(k) && this.map.get(k) == value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 151 */       if (!(o instanceof Map.Entry))
/* 152 */         return false; 
/* 153 */       if (o instanceof Char2ReferenceMap.Entry) {
/* 154 */         Char2ReferenceMap.Entry<V> entry = (Char2ReferenceMap.Entry<V>)o;
/* 155 */         return this.map.remove(entry.getCharKey(), entry.getValue());
/*     */       } 
/* 157 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 158 */       Object key = e.getKey();
/* 159 */       if (key == null || !(key instanceof Character))
/* 160 */         return false; 
/* 161 */       char k = ((Character)key).charValue();
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
/*     */   public CharSet keySet() {
/* 186 */     return new AbstractCharSet()
/*     */       {
/*     */         public boolean contains(char k) {
/* 189 */           return AbstractChar2ReferenceMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 193 */           return AbstractChar2ReferenceMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 197 */           AbstractChar2ReferenceMap.this.clear();
/*     */         }
/*     */         
/*     */         public CharIterator iterator() {
/* 201 */           return new CharIterator()
/*     */             {
/* 203 */               private final ObjectIterator<Char2ReferenceMap.Entry<V>> i = Char2ReferenceMaps.fastIterator(AbstractChar2ReferenceMap.this);
/*     */               
/*     */               public char nextChar() {
/* 206 */                 return ((Char2ReferenceMap.Entry)this.i.next()).getCharKey();
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
/*     */   public ReferenceCollection<V> values() {
/* 236 */     return (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 239 */           return AbstractChar2ReferenceMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 243 */           return AbstractChar2ReferenceMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 247 */           AbstractChar2ReferenceMap.this.clear();
/*     */         }
/*     */         
/*     */         public ObjectIterator<V> iterator() {
/* 251 */           return new ObjectIterator<V>()
/*     */             {
/* 253 */               private final ObjectIterator<Char2ReferenceMap.Entry<V>> i = Char2ReferenceMaps.fastIterator(AbstractChar2ReferenceMap.this);
/*     */               
/*     */               public V next() {
/* 256 */                 return ((Char2ReferenceMap.Entry<V>)this.i.next()).getValue();
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
/*     */   public void putAll(Map<? extends Character, ? extends V> m) {
/* 270 */     if (m instanceof Char2ReferenceMap) {
/* 271 */       ObjectIterator<Char2ReferenceMap.Entry<V>> i = Char2ReferenceMaps.fastIterator((Char2ReferenceMap)m);
/* 272 */       while (i.hasNext()) {
/* 273 */         Char2ReferenceMap.Entry<? extends V> e = (Char2ReferenceMap.Entry<? extends V>)i.next();
/* 274 */         put(e.getCharKey(), e.getValue());
/*     */       } 
/*     */     } else {
/* 277 */       int n = m.size();
/* 278 */       Iterator<? extends Map.Entry<? extends Character, ? extends V>> i = m.entrySet().iterator();
/*     */       
/* 280 */       while (n-- != 0) {
/* 281 */         Map.Entry<? extends Character, ? extends V> e = i.next();
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
/* 296 */     ObjectIterator<Char2ReferenceMap.Entry<V>> i = Char2ReferenceMaps.fastIterator(this);
/* 297 */     while (n-- != 0)
/* 298 */       h += ((Char2ReferenceMap.Entry)i.next()).hashCode(); 
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
/* 310 */     return char2ReferenceEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 314 */     StringBuilder s = new StringBuilder();
/* 315 */     ObjectIterator<Char2ReferenceMap.Entry<V>> i = Char2ReferenceMaps.fastIterator(this);
/* 316 */     int n = size();
/*     */     
/* 318 */     boolean first = true;
/* 319 */     s.append("{");
/* 320 */     while (n-- != 0) {
/* 321 */       if (first) {
/* 322 */         first = false;
/*     */       } else {
/* 324 */         s.append(", ");
/* 325 */       }  Char2ReferenceMap.Entry<V> e = (Char2ReferenceMap.Entry<V>)i.next();
/* 326 */       s.append(String.valueOf(e.getCharKey()));
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\AbstractChar2ReferenceMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */