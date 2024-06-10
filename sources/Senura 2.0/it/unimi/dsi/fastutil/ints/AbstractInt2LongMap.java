/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ public abstract class AbstractInt2LongMap
/*     */   extends AbstractInt2LongFunction
/*     */   implements Int2LongMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(long v) {
/*  49 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(int k) {
/*  53 */     ObjectIterator<Int2LongMap.Entry> i = int2LongEntrySet().iterator();
/*  54 */     while (i.hasNext()) {
/*  55 */       if (((Int2LongMap.Entry)i.next()).getIntKey() == k)
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
/*     */     implements Int2LongMap.Entry
/*     */   {
/*     */     protected int key;
/*     */     
/*     */     protected long value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Integer key, Long value) {
/*  78 */       this.key = key.intValue();
/*  79 */       this.value = value.longValue();
/*     */     }
/*     */     public BasicEntry(int key, long value) {
/*  82 */       this.key = key;
/*  83 */       this.value = value;
/*     */     }
/*     */     
/*     */     public int getIntKey() {
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
/* 102 */       if (o instanceof Int2LongMap.Entry) {
/* 103 */         Int2LongMap.Entry entry = (Int2LongMap.Entry)o;
/* 104 */         return (this.key == entry.getIntKey() && this.value == entry.getLongValue());
/*     */       } 
/* 106 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 107 */       Object key = e.getKey();
/* 108 */       if (key == null || !(key instanceof Integer))
/* 109 */         return false; 
/* 110 */       Object value = e.getValue();
/* 111 */       if (value == null || !(value instanceof Long))
/* 112 */         return false; 
/* 113 */       return (this.key == ((Integer)key).intValue() && this.value == ((Long)value).longValue());
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
/*     */     extends AbstractObjectSet<Int2LongMap.Entry>
/*     */   {
/*     */     protected final Int2LongMap map;
/*     */     
/*     */     public BasicEntrySet(Int2LongMap map) {
/* 131 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 136 */       if (!(o instanceof Map.Entry))
/* 137 */         return false; 
/* 138 */       if (o instanceof Int2LongMap.Entry) {
/* 139 */         Int2LongMap.Entry entry = (Int2LongMap.Entry)o;
/* 140 */         int i = entry.getIntKey();
/* 141 */         return (this.map.containsKey(i) && this.map.get(i) == entry.getLongValue());
/*     */       } 
/* 143 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 144 */       Object key = e.getKey();
/* 145 */       if (key == null || !(key instanceof Integer))
/* 146 */         return false; 
/* 147 */       int k = ((Integer)key).intValue();
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
/* 158 */       if (o instanceof Int2LongMap.Entry) {
/* 159 */         Int2LongMap.Entry entry = (Int2LongMap.Entry)o;
/* 160 */         return this.map.remove(entry.getIntKey(), entry.getLongValue());
/*     */       } 
/* 162 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 163 */       Object key = e.getKey();
/* 164 */       if (key == null || !(key instanceof Integer))
/* 165 */         return false; 
/* 166 */       int k = ((Integer)key).intValue();
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
/*     */   public IntSet keySet() {
/* 194 */     return new AbstractIntSet()
/*     */       {
/*     */         public boolean contains(int k) {
/* 197 */           return AbstractInt2LongMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 201 */           return AbstractInt2LongMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 205 */           AbstractInt2LongMap.this.clear();
/*     */         }
/*     */         
/*     */         public IntIterator iterator() {
/* 209 */           return new IntIterator()
/*     */             {
/* 211 */               private final ObjectIterator<Int2LongMap.Entry> i = Int2LongMaps.fastIterator(AbstractInt2LongMap.this);
/*     */               
/*     */               public int nextInt() {
/* 214 */                 return ((Int2LongMap.Entry)this.i.next()).getIntKey();
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
/* 247 */           return AbstractInt2LongMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 251 */           return AbstractInt2LongMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 255 */           AbstractInt2LongMap.this.clear();
/*     */         }
/*     */         
/*     */         public LongIterator iterator() {
/* 259 */           return new LongIterator()
/*     */             {
/* 261 */               private final ObjectIterator<Int2LongMap.Entry> i = Int2LongMaps.fastIterator(AbstractInt2LongMap.this);
/*     */               
/*     */               public long nextLong() {
/* 264 */                 return ((Int2LongMap.Entry)this.i.next()).getLongValue();
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
/*     */   public void putAll(Map<? extends Integer, ? extends Long> m) {
/* 278 */     if (m instanceof Int2LongMap) {
/* 279 */       ObjectIterator<Int2LongMap.Entry> i = Int2LongMaps.fastIterator((Int2LongMap)m);
/* 280 */       while (i.hasNext()) {
/* 281 */         Int2LongMap.Entry e = (Int2LongMap.Entry)i.next();
/* 282 */         put(e.getIntKey(), e.getLongValue());
/*     */       } 
/*     */     } else {
/* 285 */       int n = m.size();
/* 286 */       Iterator<? extends Map.Entry<? extends Integer, ? extends Long>> i = m.entrySet().iterator();
/*     */       
/* 288 */       while (n-- != 0) {
/* 289 */         Map.Entry<? extends Integer, ? extends Long> e = i.next();
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
/* 304 */     ObjectIterator<Int2LongMap.Entry> i = Int2LongMaps.fastIterator(this);
/* 305 */     while (n-- != 0)
/* 306 */       h += ((Int2LongMap.Entry)i.next()).hashCode(); 
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
/* 318 */     return int2LongEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 322 */     StringBuilder s = new StringBuilder();
/* 323 */     ObjectIterator<Int2LongMap.Entry> i = Int2LongMaps.fastIterator(this);
/* 324 */     int n = size();
/*     */     
/* 326 */     boolean first = true;
/* 327 */     s.append("{");
/* 328 */     while (n-- != 0) {
/* 329 */       if (first) {
/* 330 */         first = false;
/*     */       } else {
/* 332 */         s.append(", ");
/* 333 */       }  Int2LongMap.Entry e = (Int2LongMap.Entry)i.next();
/* 334 */       s.append(String.valueOf(e.getIntKey()));
/* 335 */       s.append("=>");
/* 336 */       s.append(String.valueOf(e.getLongValue()));
/*     */     } 
/* 338 */     s.append("}");
/* 339 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\AbstractInt2LongMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */