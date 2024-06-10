/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
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
/*     */ public abstract class AbstractShort2IntMap
/*     */   extends AbstractShort2IntFunction
/*     */   implements Short2IntMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(int v) {
/*  49 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(short k) {
/*  53 */     ObjectIterator<Short2IntMap.Entry> i = short2IntEntrySet().iterator();
/*  54 */     while (i.hasNext()) {
/*  55 */       if (((Short2IntMap.Entry)i.next()).getShortKey() == k)
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
/*     */     implements Short2IntMap.Entry
/*     */   {
/*     */     protected short key;
/*     */     
/*     */     protected int value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Short key, Integer value) {
/*  78 */       this.key = key.shortValue();
/*  79 */       this.value = value.intValue();
/*     */     }
/*     */     public BasicEntry(short key, int value) {
/*  82 */       this.key = key;
/*  83 */       this.value = value;
/*     */     }
/*     */     
/*     */     public short getShortKey() {
/*  87 */       return this.key;
/*     */     }
/*     */     
/*     */     public int getIntValue() {
/*  91 */       return this.value;
/*     */     }
/*     */     
/*     */     public int setValue(int value) {
/*  95 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 100 */       if (!(o instanceof Map.Entry))
/* 101 */         return false; 
/* 102 */       if (o instanceof Short2IntMap.Entry) {
/* 103 */         Short2IntMap.Entry entry = (Short2IntMap.Entry)o;
/* 104 */         return (this.key == entry.getShortKey() && this.value == entry.getIntValue());
/*     */       } 
/* 106 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 107 */       Object key = e.getKey();
/* 108 */       if (key == null || !(key instanceof Short))
/* 109 */         return false; 
/* 110 */       Object value = e.getValue();
/* 111 */       if (value == null || !(value instanceof Integer))
/* 112 */         return false; 
/* 113 */       return (this.key == ((Short)key).shortValue() && this.value == ((Integer)value).intValue());
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
/*     */     extends AbstractObjectSet<Short2IntMap.Entry>
/*     */   {
/*     */     protected final Short2IntMap map;
/*     */     
/*     */     public BasicEntrySet(Short2IntMap map) {
/* 131 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 136 */       if (!(o instanceof Map.Entry))
/* 137 */         return false; 
/* 138 */       if (o instanceof Short2IntMap.Entry) {
/* 139 */         Short2IntMap.Entry entry = (Short2IntMap.Entry)o;
/* 140 */         short s = entry.getShortKey();
/* 141 */         return (this.map.containsKey(s) && this.map.get(s) == entry.getIntValue());
/*     */       } 
/* 143 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 144 */       Object key = e.getKey();
/* 145 */       if (key == null || !(key instanceof Short))
/* 146 */         return false; 
/* 147 */       short k = ((Short)key).shortValue();
/* 148 */       Object value = e.getValue();
/* 149 */       if (value == null || !(value instanceof Integer))
/* 150 */         return false; 
/* 151 */       return (this.map.containsKey(k) && this.map.get(k) == ((Integer)value).intValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 156 */       if (!(o instanceof Map.Entry))
/* 157 */         return false; 
/* 158 */       if (o instanceof Short2IntMap.Entry) {
/* 159 */         Short2IntMap.Entry entry = (Short2IntMap.Entry)o;
/* 160 */         return this.map.remove(entry.getShortKey(), entry.getIntValue());
/*     */       } 
/* 162 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 163 */       Object key = e.getKey();
/* 164 */       if (key == null || !(key instanceof Short))
/* 165 */         return false; 
/* 166 */       short k = ((Short)key).shortValue();
/* 167 */       Object value = e.getValue();
/* 168 */       if (value == null || !(value instanceof Integer))
/* 169 */         return false; 
/* 170 */       int v = ((Integer)value).intValue();
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
/* 197 */           return AbstractShort2IntMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 201 */           return AbstractShort2IntMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 205 */           AbstractShort2IntMap.this.clear();
/*     */         }
/*     */         
/*     */         public ShortIterator iterator() {
/* 209 */           return new ShortIterator()
/*     */             {
/* 211 */               private final ObjectIterator<Short2IntMap.Entry> i = Short2IntMaps.fastIterator(AbstractShort2IntMap.this);
/*     */               
/*     */               public short nextShort() {
/* 214 */                 return ((Short2IntMap.Entry)this.i.next()).getShortKey();
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
/*     */   public IntCollection values() {
/* 244 */     return (IntCollection)new AbstractIntCollection()
/*     */       {
/*     */         public boolean contains(int k) {
/* 247 */           return AbstractShort2IntMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 251 */           return AbstractShort2IntMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 255 */           AbstractShort2IntMap.this.clear();
/*     */         }
/*     */         
/*     */         public IntIterator iterator() {
/* 259 */           return new IntIterator()
/*     */             {
/* 261 */               private final ObjectIterator<Short2IntMap.Entry> i = Short2IntMaps.fastIterator(AbstractShort2IntMap.this);
/*     */               
/*     */               public int nextInt() {
/* 264 */                 return ((Short2IntMap.Entry)this.i.next()).getIntValue();
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
/*     */   public void putAll(Map<? extends Short, ? extends Integer> m) {
/* 278 */     if (m instanceof Short2IntMap) {
/* 279 */       ObjectIterator<Short2IntMap.Entry> i = Short2IntMaps.fastIterator((Short2IntMap)m);
/* 280 */       while (i.hasNext()) {
/* 281 */         Short2IntMap.Entry e = (Short2IntMap.Entry)i.next();
/* 282 */         put(e.getShortKey(), e.getIntValue());
/*     */       } 
/*     */     } else {
/* 285 */       int n = m.size();
/* 286 */       Iterator<? extends Map.Entry<? extends Short, ? extends Integer>> i = m.entrySet().iterator();
/*     */       
/* 288 */       while (n-- != 0) {
/* 289 */         Map.Entry<? extends Short, ? extends Integer> e = i.next();
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
/* 304 */     ObjectIterator<Short2IntMap.Entry> i = Short2IntMaps.fastIterator(this);
/* 305 */     while (n-- != 0)
/* 306 */       h += ((Short2IntMap.Entry)i.next()).hashCode(); 
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
/* 318 */     return short2IntEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 322 */     StringBuilder s = new StringBuilder();
/* 323 */     ObjectIterator<Short2IntMap.Entry> i = Short2IntMaps.fastIterator(this);
/* 324 */     int n = size();
/*     */     
/* 326 */     boolean first = true;
/* 327 */     s.append("{");
/* 328 */     while (n-- != 0) {
/* 329 */       if (first) {
/* 330 */         first = false;
/*     */       } else {
/* 332 */         s.append(", ");
/* 333 */       }  Short2IntMap.Entry e = (Short2IntMap.Entry)i.next();
/* 334 */       s.append(String.valueOf(e.getShortKey()));
/* 335 */       s.append("=>");
/* 336 */       s.append(String.valueOf(e.getIntValue()));
/*     */     } 
/* 338 */     s.append("}");
/* 339 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\AbstractShort2IntMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */