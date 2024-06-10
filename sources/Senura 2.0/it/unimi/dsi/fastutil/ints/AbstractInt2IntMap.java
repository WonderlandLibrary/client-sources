/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
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
/*     */ public abstract class AbstractInt2IntMap
/*     */   extends AbstractInt2IntFunction
/*     */   implements Int2IntMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(int v) {
/*  46 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(int k) {
/*  50 */     ObjectIterator<Int2IntMap.Entry> i = int2IntEntrySet().iterator();
/*  51 */     while (i.hasNext()) {
/*  52 */       if (((Int2IntMap.Entry)i.next()).getIntKey() == k)
/*  53 */         return true; 
/*  54 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  58 */     return (size() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BasicEntry
/*     */     implements Int2IntMap.Entry
/*     */   {
/*     */     protected int key;
/*     */     
/*     */     protected int value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Integer key, Integer value) {
/*  75 */       this.key = key.intValue();
/*  76 */       this.value = value.intValue();
/*     */     }
/*     */     public BasicEntry(int key, int value) {
/*  79 */       this.key = key;
/*  80 */       this.value = value;
/*     */     }
/*     */     
/*     */     public int getIntKey() {
/*  84 */       return this.key;
/*     */     }
/*     */     
/*     */     public int getIntValue() {
/*  88 */       return this.value;
/*     */     }
/*     */     
/*     */     public int setValue(int value) {
/*  92 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  97 */       if (!(o instanceof Map.Entry))
/*  98 */         return false; 
/*  99 */       if (o instanceof Int2IntMap.Entry) {
/* 100 */         Int2IntMap.Entry entry = (Int2IntMap.Entry)o;
/* 101 */         return (this.key == entry.getIntKey() && this.value == entry.getIntValue());
/*     */       } 
/* 103 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 104 */       Object key = e.getKey();
/* 105 */       if (key == null || !(key instanceof Integer))
/* 106 */         return false; 
/* 107 */       Object value = e.getValue();
/* 108 */       if (value == null || !(value instanceof Integer))
/* 109 */         return false; 
/* 110 */       return (this.key == ((Integer)key).intValue() && this.value == ((Integer)value).intValue());
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 114 */       return this.key ^ this.value;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 118 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet
/*     */     extends AbstractObjectSet<Int2IntMap.Entry>
/*     */   {
/*     */     protected final Int2IntMap map;
/*     */     
/*     */     public BasicEntrySet(Int2IntMap map) {
/* 128 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 133 */       if (!(o instanceof Map.Entry))
/* 134 */         return false; 
/* 135 */       if (o instanceof Int2IntMap.Entry) {
/* 136 */         Int2IntMap.Entry entry = (Int2IntMap.Entry)o;
/* 137 */         int i = entry.getIntKey();
/* 138 */         return (this.map.containsKey(i) && this.map.get(i) == entry.getIntValue());
/*     */       } 
/* 140 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 141 */       Object key = e.getKey();
/* 142 */       if (key == null || !(key instanceof Integer))
/* 143 */         return false; 
/* 144 */       int k = ((Integer)key).intValue();
/* 145 */       Object value = e.getValue();
/* 146 */       if (value == null || !(value instanceof Integer))
/* 147 */         return false; 
/* 148 */       return (this.map.containsKey(k) && this.map.get(k) == ((Integer)value).intValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 153 */       if (!(o instanceof Map.Entry))
/* 154 */         return false; 
/* 155 */       if (o instanceof Int2IntMap.Entry) {
/* 156 */         Int2IntMap.Entry entry = (Int2IntMap.Entry)o;
/* 157 */         return this.map.remove(entry.getIntKey(), entry.getIntValue());
/*     */       } 
/* 159 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 160 */       Object key = e.getKey();
/* 161 */       if (key == null || !(key instanceof Integer))
/* 162 */         return false; 
/* 163 */       int k = ((Integer)key).intValue();
/* 164 */       Object value = e.getValue();
/* 165 */       if (value == null || !(value instanceof Integer))
/* 166 */         return false; 
/* 167 */       int v = ((Integer)value).intValue();
/* 168 */       return this.map.remove(k, v);
/*     */     }
/*     */     
/*     */     public int size() {
/* 172 */       return this.map.size();
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
/* 191 */     return new AbstractIntSet()
/*     */       {
/*     */         public boolean contains(int k) {
/* 194 */           return AbstractInt2IntMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 198 */           return AbstractInt2IntMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 202 */           AbstractInt2IntMap.this.clear();
/*     */         }
/*     */         
/*     */         public IntIterator iterator() {
/* 206 */           return new IntIterator()
/*     */             {
/* 208 */               private final ObjectIterator<Int2IntMap.Entry> i = Int2IntMaps.fastIterator(AbstractInt2IntMap.this);
/*     */               
/*     */               public int nextInt() {
/* 211 */                 return ((Int2IntMap.Entry)this.i.next()).getIntKey();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 215 */                 return this.i.hasNext();
/*     */               }
/*     */               
/*     */               public void remove() {
/* 219 */                 this.i.remove();
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
/* 241 */     return new AbstractIntCollection()
/*     */       {
/*     */         public boolean contains(int k) {
/* 244 */           return AbstractInt2IntMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 248 */           return AbstractInt2IntMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 252 */           AbstractInt2IntMap.this.clear();
/*     */         }
/*     */         
/*     */         public IntIterator iterator() {
/* 256 */           return new IntIterator()
/*     */             {
/* 258 */               private final ObjectIterator<Int2IntMap.Entry> i = Int2IntMaps.fastIterator(AbstractInt2IntMap.this);
/*     */               
/*     */               public int nextInt() {
/* 261 */                 return ((Int2IntMap.Entry)this.i.next()).getIntValue();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 265 */                 return this.i.hasNext();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Integer, ? extends Integer> m) {
/* 275 */     if (m instanceof Int2IntMap) {
/* 276 */       ObjectIterator<Int2IntMap.Entry> i = Int2IntMaps.fastIterator((Int2IntMap)m);
/* 277 */       while (i.hasNext()) {
/* 278 */         Int2IntMap.Entry e = (Int2IntMap.Entry)i.next();
/* 279 */         put(e.getIntKey(), e.getIntValue());
/*     */       } 
/*     */     } else {
/* 282 */       int n = m.size();
/* 283 */       Iterator<? extends Map.Entry<? extends Integer, ? extends Integer>> i = m.entrySet().iterator();
/*     */       
/* 285 */       while (n-- != 0) {
/* 286 */         Map.Entry<? extends Integer, ? extends Integer> e = i.next();
/* 287 */         put(e.getKey(), e.getValue());
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
/* 300 */     int h = 0, n = size();
/* 301 */     ObjectIterator<Int2IntMap.Entry> i = Int2IntMaps.fastIterator(this);
/* 302 */     while (n-- != 0)
/* 303 */       h += ((Int2IntMap.Entry)i.next()).hashCode(); 
/* 304 */     return h;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 308 */     if (o == this)
/* 309 */       return true; 
/* 310 */     if (!(o instanceof Map))
/* 311 */       return false; 
/* 312 */     Map<?, ?> m = (Map<?, ?>)o;
/* 313 */     if (m.size() != size())
/* 314 */       return false; 
/* 315 */     return int2IntEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 319 */     StringBuilder s = new StringBuilder();
/* 320 */     ObjectIterator<Int2IntMap.Entry> i = Int2IntMaps.fastIterator(this);
/* 321 */     int n = size();
/*     */     
/* 323 */     boolean first = true;
/* 324 */     s.append("{");
/* 325 */     while (n-- != 0) {
/* 326 */       if (first) {
/* 327 */         first = false;
/*     */       } else {
/* 329 */         s.append(", ");
/* 330 */       }  Int2IntMap.Entry e = (Int2IntMap.Entry)i.next();
/* 331 */       s.append(String.valueOf(e.getIntKey()));
/* 332 */       s.append("=>");
/* 333 */       s.append(String.valueOf(e.getIntValue()));
/*     */     } 
/* 335 */     s.append("}");
/* 336 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\AbstractInt2IntMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */