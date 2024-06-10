/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortCollection;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
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
/*     */ public abstract class AbstractInt2ShortMap
/*     */   extends AbstractInt2ShortFunction
/*     */   implements Int2ShortMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(short v) {
/*  49 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(int k) {
/*  53 */     ObjectIterator<Int2ShortMap.Entry> i = int2ShortEntrySet().iterator();
/*  54 */     while (i.hasNext()) {
/*  55 */       if (((Int2ShortMap.Entry)i.next()).getIntKey() == k)
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
/*     */     implements Int2ShortMap.Entry
/*     */   {
/*     */     protected int key;
/*     */     
/*     */     protected short value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Integer key, Short value) {
/*  78 */       this.key = key.intValue();
/*  79 */       this.value = value.shortValue();
/*     */     }
/*     */     public BasicEntry(int key, short value) {
/*  82 */       this.key = key;
/*  83 */       this.value = value;
/*     */     }
/*     */     
/*     */     public int getIntKey() {
/*  87 */       return this.key;
/*     */     }
/*     */     
/*     */     public short getShortValue() {
/*  91 */       return this.value;
/*     */     }
/*     */     
/*     */     public short setValue(short value) {
/*  95 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 100 */       if (!(o instanceof Map.Entry))
/* 101 */         return false; 
/* 102 */       if (o instanceof Int2ShortMap.Entry) {
/* 103 */         Int2ShortMap.Entry entry = (Int2ShortMap.Entry)o;
/* 104 */         return (this.key == entry.getIntKey() && this.value == entry.getShortValue());
/*     */       } 
/* 106 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 107 */       Object key = e.getKey();
/* 108 */       if (key == null || !(key instanceof Integer))
/* 109 */         return false; 
/* 110 */       Object value = e.getValue();
/* 111 */       if (value == null || !(value instanceof Short))
/* 112 */         return false; 
/* 113 */       return (this.key == ((Integer)key).intValue() && this.value == ((Short)value).shortValue());
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 117 */       return this.key ^ this.value;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 121 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet
/*     */     extends AbstractObjectSet<Int2ShortMap.Entry>
/*     */   {
/*     */     protected final Int2ShortMap map;
/*     */     
/*     */     public BasicEntrySet(Int2ShortMap map) {
/* 131 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 136 */       if (!(o instanceof Map.Entry))
/* 137 */         return false; 
/* 138 */       if (o instanceof Int2ShortMap.Entry) {
/* 139 */         Int2ShortMap.Entry entry = (Int2ShortMap.Entry)o;
/* 140 */         int i = entry.getIntKey();
/* 141 */         return (this.map.containsKey(i) && this.map.get(i) == entry.getShortValue());
/*     */       } 
/* 143 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 144 */       Object key = e.getKey();
/* 145 */       if (key == null || !(key instanceof Integer))
/* 146 */         return false; 
/* 147 */       int k = ((Integer)key).intValue();
/* 148 */       Object value = e.getValue();
/* 149 */       if (value == null || !(value instanceof Short))
/* 150 */         return false; 
/* 151 */       return (this.map.containsKey(k) && this.map.get(k) == ((Short)value).shortValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 156 */       if (!(o instanceof Map.Entry))
/* 157 */         return false; 
/* 158 */       if (o instanceof Int2ShortMap.Entry) {
/* 159 */         Int2ShortMap.Entry entry = (Int2ShortMap.Entry)o;
/* 160 */         return this.map.remove(entry.getIntKey(), entry.getShortValue());
/*     */       } 
/* 162 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 163 */       Object key = e.getKey();
/* 164 */       if (key == null || !(key instanceof Integer))
/* 165 */         return false; 
/* 166 */       int k = ((Integer)key).intValue();
/* 167 */       Object value = e.getValue();
/* 168 */       if (value == null || !(value instanceof Short))
/* 169 */         return false; 
/* 170 */       short v = ((Short)value).shortValue();
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
/* 197 */           return AbstractInt2ShortMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 201 */           return AbstractInt2ShortMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 205 */           AbstractInt2ShortMap.this.clear();
/*     */         }
/*     */         
/*     */         public IntIterator iterator() {
/* 209 */           return new IntIterator()
/*     */             {
/* 211 */               private final ObjectIterator<Int2ShortMap.Entry> i = Int2ShortMaps.fastIterator(AbstractInt2ShortMap.this);
/*     */               
/*     */               public int nextInt() {
/* 214 */                 return ((Int2ShortMap.Entry)this.i.next()).getIntKey();
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
/*     */   public ShortCollection values() {
/* 244 */     return (ShortCollection)new AbstractShortCollection()
/*     */       {
/*     */         public boolean contains(short k) {
/* 247 */           return AbstractInt2ShortMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 251 */           return AbstractInt2ShortMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 255 */           AbstractInt2ShortMap.this.clear();
/*     */         }
/*     */         
/*     */         public ShortIterator iterator() {
/* 259 */           return new ShortIterator()
/*     */             {
/* 261 */               private final ObjectIterator<Int2ShortMap.Entry> i = Int2ShortMaps.fastIterator(AbstractInt2ShortMap.this);
/*     */               
/*     */               public short nextShort() {
/* 264 */                 return ((Int2ShortMap.Entry)this.i.next()).getShortValue();
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
/*     */   public void putAll(Map<? extends Integer, ? extends Short> m) {
/* 278 */     if (m instanceof Int2ShortMap) {
/* 279 */       ObjectIterator<Int2ShortMap.Entry> i = Int2ShortMaps.fastIterator((Int2ShortMap)m);
/* 280 */       while (i.hasNext()) {
/* 281 */         Int2ShortMap.Entry e = (Int2ShortMap.Entry)i.next();
/* 282 */         put(e.getIntKey(), e.getShortValue());
/*     */       } 
/*     */     } else {
/* 285 */       int n = m.size();
/* 286 */       Iterator<? extends Map.Entry<? extends Integer, ? extends Short>> i = m.entrySet().iterator();
/*     */       
/* 288 */       while (n-- != 0) {
/* 289 */         Map.Entry<? extends Integer, ? extends Short> e = i.next();
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
/* 304 */     ObjectIterator<Int2ShortMap.Entry> i = Int2ShortMaps.fastIterator(this);
/* 305 */     while (n-- != 0)
/* 306 */       h += ((Int2ShortMap.Entry)i.next()).hashCode(); 
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
/* 318 */     return int2ShortEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 322 */     StringBuilder s = new StringBuilder();
/* 323 */     ObjectIterator<Int2ShortMap.Entry> i = Int2ShortMaps.fastIterator(this);
/* 324 */     int n = size();
/*     */     
/* 326 */     boolean first = true;
/* 327 */     s.append("{");
/* 328 */     while (n-- != 0) {
/* 329 */       if (first) {
/* 330 */         first = false;
/*     */       } else {
/* 332 */         s.append(", ");
/* 333 */       }  Int2ShortMap.Entry e = (Int2ShortMap.Entry)i.next();
/* 334 */       s.append(String.valueOf(e.getIntKey()));
/* 335 */       s.append("=>");
/* 336 */       s.append(String.valueOf(e.getShortValue()));
/*     */     } 
/* 338 */     s.append("}");
/* 339 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\AbstractInt2ShortMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */