/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongIterator;
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
/*     */ public abstract class AbstractShort2LongMap
/*     */   extends AbstractShort2LongFunction
/*     */   implements Short2LongMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(long v) {
/*  49 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(short k) {
/*  53 */     ObjectIterator<Short2LongMap.Entry> i = short2LongEntrySet().iterator();
/*  54 */     while (i.hasNext()) {
/*  55 */       if (((Short2LongMap.Entry)i.next()).getShortKey() == k)
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
/*     */     implements Short2LongMap.Entry
/*     */   {
/*     */     protected short key;
/*     */     
/*     */     protected long value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Short key, Long value) {
/*  78 */       this.key = key.shortValue();
/*  79 */       this.value = value.longValue();
/*     */     }
/*     */     public BasicEntry(short key, long value) {
/*  82 */       this.key = key;
/*  83 */       this.value = value;
/*     */     }
/*     */     
/*     */     public short getShortKey() {
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
/* 102 */       if (o instanceof Short2LongMap.Entry) {
/* 103 */         Short2LongMap.Entry entry = (Short2LongMap.Entry)o;
/* 104 */         return (this.key == entry.getShortKey() && this.value == entry.getLongValue());
/*     */       } 
/* 106 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 107 */       Object key = e.getKey();
/* 108 */       if (key == null || !(key instanceof Short))
/* 109 */         return false; 
/* 110 */       Object value = e.getValue();
/* 111 */       if (value == null || !(value instanceof Long))
/* 112 */         return false; 
/* 113 */       return (this.key == ((Short)key).shortValue() && this.value == ((Long)value).longValue());
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 117 */       return this.key ^ HashCommon.long2int(this.value);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 121 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet
/*     */     extends AbstractObjectSet<Short2LongMap.Entry>
/*     */   {
/*     */     protected final Short2LongMap map;
/*     */     
/*     */     public BasicEntrySet(Short2LongMap map) {
/* 131 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 136 */       if (!(o instanceof Map.Entry))
/* 137 */         return false; 
/* 138 */       if (o instanceof Short2LongMap.Entry) {
/* 139 */         Short2LongMap.Entry entry = (Short2LongMap.Entry)o;
/* 140 */         short s = entry.getShortKey();
/* 141 */         return (this.map.containsKey(s) && this.map.get(s) == entry.getLongValue());
/*     */       } 
/* 143 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 144 */       Object key = e.getKey();
/* 145 */       if (key == null || !(key instanceof Short))
/* 146 */         return false; 
/* 147 */       short k = ((Short)key).shortValue();
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
/* 158 */       if (o instanceof Short2LongMap.Entry) {
/* 159 */         Short2LongMap.Entry entry = (Short2LongMap.Entry)o;
/* 160 */         return this.map.remove(entry.getShortKey(), entry.getLongValue());
/*     */       } 
/* 162 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 163 */       Object key = e.getKey();
/* 164 */       if (key == null || !(key instanceof Short))
/* 165 */         return false; 
/* 166 */       short k = ((Short)key).shortValue();
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
/*     */   public ShortSet keySet() {
/* 194 */     return new AbstractShortSet()
/*     */       {
/*     */         public boolean contains(short k) {
/* 197 */           return AbstractShort2LongMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 201 */           return AbstractShort2LongMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 205 */           AbstractShort2LongMap.this.clear();
/*     */         }
/*     */         
/*     */         public ShortIterator iterator() {
/* 209 */           return new ShortIterator()
/*     */             {
/* 211 */               private final ObjectIterator<Short2LongMap.Entry> i = Short2LongMaps.fastIterator(AbstractShort2LongMap.this);
/*     */               
/*     */               public short nextShort() {
/* 214 */                 return ((Short2LongMap.Entry)this.i.next()).getShortKey();
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
/* 244 */     return (LongCollection)new AbstractLongCollection()
/*     */       {
/*     */         public boolean contains(long k) {
/* 247 */           return AbstractShort2LongMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 251 */           return AbstractShort2LongMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 255 */           AbstractShort2LongMap.this.clear();
/*     */         }
/*     */         
/*     */         public LongIterator iterator() {
/* 259 */           return new LongIterator()
/*     */             {
/* 261 */               private final ObjectIterator<Short2LongMap.Entry> i = Short2LongMaps.fastIterator(AbstractShort2LongMap.this);
/*     */               
/*     */               public long nextLong() {
/* 264 */                 return ((Short2LongMap.Entry)this.i.next()).getLongValue();
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
/*     */   public void putAll(Map<? extends Short, ? extends Long> m) {
/* 278 */     if (m instanceof Short2LongMap) {
/* 279 */       ObjectIterator<Short2LongMap.Entry> i = Short2LongMaps.fastIterator((Short2LongMap)m);
/* 280 */       while (i.hasNext()) {
/* 281 */         Short2LongMap.Entry e = (Short2LongMap.Entry)i.next();
/* 282 */         put(e.getShortKey(), e.getLongValue());
/*     */       } 
/*     */     } else {
/* 285 */       int n = m.size();
/* 286 */       Iterator<? extends Map.Entry<? extends Short, ? extends Long>> i = m.entrySet().iterator();
/*     */       
/* 288 */       while (n-- != 0) {
/* 289 */         Map.Entry<? extends Short, ? extends Long> e = i.next();
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
/* 304 */     ObjectIterator<Short2LongMap.Entry> i = Short2LongMaps.fastIterator(this);
/* 305 */     while (n-- != 0)
/* 306 */       h += ((Short2LongMap.Entry)i.next()).hashCode(); 
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
/* 318 */     return short2LongEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 322 */     StringBuilder s = new StringBuilder();
/* 323 */     ObjectIterator<Short2LongMap.Entry> i = Short2LongMaps.fastIterator(this);
/* 324 */     int n = size();
/*     */     
/* 326 */     boolean first = true;
/* 327 */     s.append("{");
/* 328 */     while (n-- != 0) {
/* 329 */       if (first) {
/* 330 */         first = false;
/*     */       } else {
/* 332 */         s.append(", ");
/* 333 */       }  Short2LongMap.Entry e = (Short2LongMap.Entry)i.next();
/* 334 */       s.append(String.valueOf(e.getShortKey()));
/* 335 */       s.append("=>");
/* 336 */       s.append(String.valueOf(e.getLongValue()));
/*     */     } 
/* 338 */     s.append("}");
/* 339 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\AbstractShort2LongMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */