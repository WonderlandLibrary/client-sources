/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
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
/*     */ 
/*     */ public abstract class AbstractLong2LongMap
/*     */   extends AbstractLong2LongFunction
/*     */   implements Long2LongMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(long v) {
/*  49 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(long k) {
/*  53 */     ObjectIterator<Long2LongMap.Entry> i = long2LongEntrySet().iterator();
/*  54 */     while (i.hasNext()) {
/*  55 */       if (((Long2LongMap.Entry)i.next()).getLongKey() == k)
/*  56 */         return true; 
/*  57 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  61 */     return (size() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BasicEntry
/*     */     implements Long2LongMap.Entry
/*     */   {
/*     */     protected long key;
/*     */     
/*     */     protected long value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Long key, Long value) {
/*  78 */       this.key = key.longValue();
/*  79 */       this.value = value.longValue();
/*     */     }
/*     */     public BasicEntry(long key, long value) {
/*  82 */       this.key = key;
/*  83 */       this.value = value;
/*     */     }
/*     */     
/*     */     public long getLongKey() {
/*  87 */       return this.key;
/*     */     }
/*     */     
/*     */     public long getLongValue() {
/*  91 */       return this.value;
/*     */     }
/*     */     
/*     */     public long setValue(long value) {
/*  95 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 100 */       if (!(o instanceof Map.Entry))
/* 101 */         return false; 
/* 102 */       if (o instanceof Long2LongMap.Entry) {
/* 103 */         Long2LongMap.Entry entry = (Long2LongMap.Entry)o;
/* 104 */         return (this.key == entry.getLongKey() && this.value == entry.getLongValue());
/*     */       } 
/* 106 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 107 */       Object key = e.getKey();
/* 108 */       if (key == null || !(key instanceof Long))
/* 109 */         return false; 
/* 110 */       Object value = e.getValue();
/* 111 */       if (value == null || !(value instanceof Long))
/* 112 */         return false; 
/* 113 */       return (this.key == ((Long)key).longValue() && this.value == ((Long)value).longValue());
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 117 */       return HashCommon.long2int(this.key) ^ HashCommon.long2int(this.value);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 121 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet
/*     */     extends AbstractObjectSet<Long2LongMap.Entry>
/*     */   {
/*     */     protected final Long2LongMap map;
/*     */     
/*     */     public BasicEntrySet(Long2LongMap map) {
/* 131 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 136 */       if (!(o instanceof Map.Entry))
/* 137 */         return false; 
/* 138 */       if (o instanceof Long2LongMap.Entry) {
/* 139 */         Long2LongMap.Entry entry = (Long2LongMap.Entry)o;
/* 140 */         long l = entry.getLongKey();
/* 141 */         return (this.map.containsKey(l) && this.map.get(l) == entry.getLongValue());
/*     */       } 
/* 143 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 144 */       Object key = e.getKey();
/* 145 */       if (key == null || !(key instanceof Long))
/* 146 */         return false; 
/* 147 */       long k = ((Long)key).longValue();
/* 148 */       Object value = e.getValue();
/* 149 */       if (value == null || !(value instanceof Long))
/* 150 */         return false; 
/* 151 */       return (this.map.containsKey(k) && this.map.get(k) == ((Long)value).longValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 156 */       if (!(o instanceof Map.Entry))
/* 157 */         return false; 
/* 158 */       if (o instanceof Long2LongMap.Entry) {
/* 159 */         Long2LongMap.Entry entry = (Long2LongMap.Entry)o;
/* 160 */         return this.map.remove(entry.getLongKey(), entry.getLongValue());
/*     */       } 
/* 162 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 163 */       Object key = e.getKey();
/* 164 */       if (key == null || !(key instanceof Long))
/* 165 */         return false; 
/* 166 */       long k = ((Long)key).longValue();
/* 167 */       Object value = e.getValue();
/* 168 */       if (value == null || !(value instanceof Long))
/* 169 */         return false; 
/* 170 */       long v = ((Long)value).longValue();
/* 171 */       return this.map.remove(k, v);
/*     */     }
/*     */     
/*     */     public int size() {
/* 175 */       return this.map.size();
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
/* 194 */     return new AbstractLongSet()
/*     */       {
/*     */         public boolean contains(long k) {
/* 197 */           return AbstractLong2LongMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 201 */           return AbstractLong2LongMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 205 */           AbstractLong2LongMap.this.clear();
/*     */         }
/*     */         
/*     */         public LongIterator iterator() {
/* 209 */           return new LongIterator()
/*     */             {
/* 211 */               private final ObjectIterator<Long2LongMap.Entry> i = Long2LongMaps.fastIterator(AbstractLong2LongMap.this);
/*     */               
/*     */               public long nextLong() {
/* 214 */                 return ((Long2LongMap.Entry)this.i.next()).getLongKey();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 218 */                 return this.i.hasNext();
/*     */               }
/*     */               
/*     */               public void remove() {
/* 222 */                 this.i.remove();
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
/*     */   public LongCollection values() {
/* 244 */     return new AbstractLongCollection()
/*     */       {
/*     */         public boolean contains(long k) {
/* 247 */           return AbstractLong2LongMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 251 */           return AbstractLong2LongMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 255 */           AbstractLong2LongMap.this.clear();
/*     */         }
/*     */         
/*     */         public LongIterator iterator() {
/* 259 */           return new LongIterator()
/*     */             {
/* 261 */               private final ObjectIterator<Long2LongMap.Entry> i = Long2LongMaps.fastIterator(AbstractLong2LongMap.this);
/*     */               
/*     */               public long nextLong() {
/* 264 */                 return ((Long2LongMap.Entry)this.i.next()).getLongValue();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 268 */                 return this.i.hasNext();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Long, ? extends Long> m) {
/* 278 */     if (m instanceof Long2LongMap) {
/* 279 */       ObjectIterator<Long2LongMap.Entry> i = Long2LongMaps.fastIterator((Long2LongMap)m);
/* 280 */       while (i.hasNext()) {
/* 281 */         Long2LongMap.Entry e = (Long2LongMap.Entry)i.next();
/* 282 */         put(e.getLongKey(), e.getLongValue());
/*     */       } 
/*     */     } else {
/* 285 */       int n = m.size();
/* 286 */       Iterator<? extends Map.Entry<? extends Long, ? extends Long>> i = m.entrySet().iterator();
/*     */       
/* 288 */       while (n-- != 0) {
/* 289 */         Map.Entry<? extends Long, ? extends Long> e = i.next();
/* 290 */         put(e.getKey(), e.getValue());
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
/* 303 */     int h = 0, n = size();
/* 304 */     ObjectIterator<Long2LongMap.Entry> i = Long2LongMaps.fastIterator(this);
/* 305 */     while (n-- != 0)
/* 306 */       h += ((Long2LongMap.Entry)i.next()).hashCode(); 
/* 307 */     return h;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 311 */     if (o == this)
/* 312 */       return true; 
/* 313 */     if (!(o instanceof Map))
/* 314 */       return false; 
/* 315 */     Map<?, ?> m = (Map<?, ?>)o;
/* 316 */     if (m.size() != size())
/* 317 */       return false; 
/* 318 */     return long2LongEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 322 */     StringBuilder s = new StringBuilder();
/* 323 */     ObjectIterator<Long2LongMap.Entry> i = Long2LongMaps.fastIterator(this);
/* 324 */     int n = size();
/*     */     
/* 326 */     boolean first = true;
/* 327 */     s.append("{");
/* 328 */     while (n-- != 0) {
/* 329 */       if (first) {
/* 330 */         first = false;
/*     */       } else {
/* 332 */         s.append(", ");
/* 333 */       }  Long2LongMap.Entry e = (Long2LongMap.Entry)i.next();
/* 334 */       s.append(String.valueOf(e.getLongKey()));
/* 335 */       s.append("=>");
/* 336 */       s.append(String.valueOf(e.getLongValue()));
/*     */     } 
/* 338 */     s.append("}");
/* 339 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\AbstractLong2LongMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */