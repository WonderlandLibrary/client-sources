/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ public abstract class AbstractInt2ReferenceMap<V>
/*     */   extends AbstractInt2ReferenceFunction<V>
/*     */   implements Int2ReferenceMap<V>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(Object v) {
/*  48 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(int k) {
/*  52 */     ObjectIterator<Int2ReferenceMap.Entry<V>> i = int2ReferenceEntrySet().iterator();
/*  53 */     while (i.hasNext()) {
/*  54 */       if (((Int2ReferenceMap.Entry)i.next()).getIntKey() == k)
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
/*     */     implements Int2ReferenceMap.Entry<V>
/*     */   {
/*     */     protected int key;
/*     */     
/*     */     protected V value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Integer key, V value) {
/*  77 */       this.key = key.intValue();
/*  78 */       this.value = value;
/*     */     }
/*     */     public BasicEntry(int key, V value) {
/*  81 */       this.key = key;
/*  82 */       this.value = value;
/*     */     }
/*     */     
/*     */     public int getIntKey() {
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
/* 101 */       if (o instanceof Int2ReferenceMap.Entry) {
/* 102 */         Int2ReferenceMap.Entry<V> entry = (Int2ReferenceMap.Entry<V>)o;
/* 103 */         return (this.key == entry.getIntKey() && this.value == entry.getValue());
/*     */       } 
/* 105 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 106 */       Object key = e.getKey();
/* 107 */       if (key == null || !(key instanceof Integer))
/* 108 */         return false; 
/* 109 */       Object value = e.getValue();
/* 110 */       return (this.key == ((Integer)key).intValue() && this.value == value);
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
/*     */     extends AbstractObjectSet<Int2ReferenceMap.Entry<V>>
/*     */   {
/*     */     protected final Int2ReferenceMap<V> map;
/*     */     
/*     */     public BasicEntrySet(Int2ReferenceMap<V> map) {
/* 128 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 133 */       if (!(o instanceof Map.Entry))
/* 134 */         return false; 
/* 135 */       if (o instanceof Int2ReferenceMap.Entry) {
/* 136 */         Int2ReferenceMap.Entry<V> entry = (Int2ReferenceMap.Entry<V>)o;
/* 137 */         int i = entry.getIntKey();
/* 138 */         return (this.map.containsKey(i) && this.map.get(i) == entry.getValue());
/*     */       } 
/* 140 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 141 */       Object key = e.getKey();
/* 142 */       if (key == null || !(key instanceof Integer))
/* 143 */         return false; 
/* 144 */       int k = ((Integer)key).intValue();
/* 145 */       Object value = e.getValue();
/* 146 */       return (this.map.containsKey(k) && this.map.get(k) == value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 151 */       if (!(o instanceof Map.Entry))
/* 152 */         return false; 
/* 153 */       if (o instanceof Int2ReferenceMap.Entry) {
/* 154 */         Int2ReferenceMap.Entry<V> entry = (Int2ReferenceMap.Entry<V>)o;
/* 155 */         return this.map.remove(entry.getIntKey(), entry.getValue());
/*     */       } 
/* 157 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 158 */       Object key = e.getKey();
/* 159 */       if (key == null || !(key instanceof Integer))
/* 160 */         return false; 
/* 161 */       int k = ((Integer)key).intValue();
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
/*     */   public IntSet keySet() {
/* 186 */     return new AbstractIntSet()
/*     */       {
/*     */         public boolean contains(int k) {
/* 189 */           return AbstractInt2ReferenceMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 193 */           return AbstractInt2ReferenceMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 197 */           AbstractInt2ReferenceMap.this.clear();
/*     */         }
/*     */         
/*     */         public IntIterator iterator() {
/* 201 */           return new IntIterator()
/*     */             {
/* 203 */               private final ObjectIterator<Int2ReferenceMap.Entry<V>> i = Int2ReferenceMaps.fastIterator(AbstractInt2ReferenceMap.this);
/*     */               
/*     */               public int nextInt() {
/* 206 */                 return ((Int2ReferenceMap.Entry)this.i.next()).getIntKey();
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
/* 239 */           return AbstractInt2ReferenceMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 243 */           return AbstractInt2ReferenceMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 247 */           AbstractInt2ReferenceMap.this.clear();
/*     */         }
/*     */         
/*     */         public ObjectIterator<V> iterator() {
/* 251 */           return new ObjectIterator<V>()
/*     */             {
/* 253 */               private final ObjectIterator<Int2ReferenceMap.Entry<V>> i = Int2ReferenceMaps.fastIterator(AbstractInt2ReferenceMap.this);
/*     */               
/*     */               public V next() {
/* 256 */                 return ((Int2ReferenceMap.Entry<V>)this.i.next()).getValue();
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
/*     */   public void putAll(Map<? extends Integer, ? extends V> m) {
/* 270 */     if (m instanceof Int2ReferenceMap) {
/* 271 */       ObjectIterator<Int2ReferenceMap.Entry<V>> i = Int2ReferenceMaps.fastIterator((Int2ReferenceMap)m);
/* 272 */       while (i.hasNext()) {
/* 273 */         Int2ReferenceMap.Entry<? extends V> e = (Int2ReferenceMap.Entry<? extends V>)i.next();
/* 274 */         put(e.getIntKey(), e.getValue());
/*     */       } 
/*     */     } else {
/* 277 */       int n = m.size();
/* 278 */       Iterator<? extends Map.Entry<? extends Integer, ? extends V>> i = m.entrySet().iterator();
/*     */       
/* 280 */       while (n-- != 0) {
/* 281 */         Map.Entry<? extends Integer, ? extends V> e = i.next();
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
/* 296 */     ObjectIterator<Int2ReferenceMap.Entry<V>> i = Int2ReferenceMaps.fastIterator(this);
/* 297 */     while (n-- != 0)
/* 298 */       h += ((Int2ReferenceMap.Entry)i.next()).hashCode(); 
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
/* 310 */     return int2ReferenceEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 314 */     StringBuilder s = new StringBuilder();
/* 315 */     ObjectIterator<Int2ReferenceMap.Entry<V>> i = Int2ReferenceMaps.fastIterator(this);
/* 316 */     int n = size();
/*     */     
/* 318 */     boolean first = true;
/* 319 */     s.append("{");
/* 320 */     while (n-- != 0) {
/* 321 */       if (first) {
/* 322 */         first = false;
/*     */       } else {
/* 324 */         s.append(", ");
/* 325 */       }  Int2ReferenceMap.Entry<V> e = (Int2ReferenceMap.Entry<V>)i.next();
/* 326 */       s.append(String.valueOf(e.getIntKey()));
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\AbstractInt2ReferenceMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */