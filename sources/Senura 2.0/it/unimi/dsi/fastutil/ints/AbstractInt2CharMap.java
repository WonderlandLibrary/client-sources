/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*     */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*     */ import it.unimi.dsi.fastutil.chars.CharIterator;
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
/*     */ public abstract class AbstractInt2CharMap
/*     */   extends AbstractInt2CharFunction
/*     */   implements Int2CharMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(char v) {
/*  49 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(int k) {
/*  53 */     ObjectIterator<Int2CharMap.Entry> i = int2CharEntrySet().iterator();
/*  54 */     while (i.hasNext()) {
/*  55 */       if (((Int2CharMap.Entry)i.next()).getIntKey() == k)
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
/*     */     implements Int2CharMap.Entry
/*     */   {
/*     */     protected int key;
/*     */     
/*     */     protected char value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Integer key, Character value) {
/*  78 */       this.key = key.intValue();
/*  79 */       this.value = value.charValue();
/*     */     }
/*     */     public BasicEntry(int key, char value) {
/*  82 */       this.key = key;
/*  83 */       this.value = value;
/*     */     }
/*     */     
/*     */     public int getIntKey() {
/*  87 */       return this.key;
/*     */     }
/*     */     
/*     */     public char getCharValue() {
/*  91 */       return this.value;
/*     */     }
/*     */     
/*     */     public char setValue(char value) {
/*  95 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 100 */       if (!(o instanceof Map.Entry))
/* 101 */         return false; 
/* 102 */       if (o instanceof Int2CharMap.Entry) {
/* 103 */         Int2CharMap.Entry entry = (Int2CharMap.Entry)o;
/* 104 */         return (this.key == entry.getIntKey() && this.value == entry.getCharValue());
/*     */       } 
/* 106 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 107 */       Object key = e.getKey();
/* 108 */       if (key == null || !(key instanceof Integer))
/* 109 */         return false; 
/* 110 */       Object value = e.getValue();
/* 111 */       if (value == null || !(value instanceof Character))
/* 112 */         return false; 
/* 113 */       return (this.key == ((Integer)key).intValue() && this.value == ((Character)value)
/* 114 */         .charValue());
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 118 */       return this.key ^ this.value;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 122 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet
/*     */     extends AbstractObjectSet<Int2CharMap.Entry>
/*     */   {
/*     */     protected final Int2CharMap map;
/*     */     
/*     */     public BasicEntrySet(Int2CharMap map) {
/* 132 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 137 */       if (!(o instanceof Map.Entry))
/* 138 */         return false; 
/* 139 */       if (o instanceof Int2CharMap.Entry) {
/* 140 */         Int2CharMap.Entry entry = (Int2CharMap.Entry)o;
/* 141 */         int i = entry.getIntKey();
/* 142 */         return (this.map.containsKey(i) && this.map.get(i) == entry.getCharValue());
/*     */       } 
/* 144 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 145 */       Object key = e.getKey();
/* 146 */       if (key == null || !(key instanceof Integer))
/* 147 */         return false; 
/* 148 */       int k = ((Integer)key).intValue();
/* 149 */       Object value = e.getValue();
/* 150 */       if (value == null || !(value instanceof Character))
/* 151 */         return false; 
/* 152 */       return (this.map.containsKey(k) && this.map.get(k) == ((Character)value).charValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 157 */       if (!(o instanceof Map.Entry))
/* 158 */         return false; 
/* 159 */       if (o instanceof Int2CharMap.Entry) {
/* 160 */         Int2CharMap.Entry entry = (Int2CharMap.Entry)o;
/* 161 */         return this.map.remove(entry.getIntKey(), entry.getCharValue());
/*     */       } 
/* 163 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 164 */       Object key = e.getKey();
/* 165 */       if (key == null || !(key instanceof Integer))
/* 166 */         return false; 
/* 167 */       int k = ((Integer)key).intValue();
/* 168 */       Object value = e.getValue();
/* 169 */       if (value == null || !(value instanceof Character))
/* 170 */         return false; 
/* 171 */       char v = ((Character)value).charValue();
/* 172 */       return this.map.remove(k, v);
/*     */     }
/*     */     
/*     */     public int size() {
/* 176 */       return this.map.size();
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
/* 195 */     return new AbstractIntSet()
/*     */       {
/*     */         public boolean contains(int k) {
/* 198 */           return AbstractInt2CharMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 202 */           return AbstractInt2CharMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 206 */           AbstractInt2CharMap.this.clear();
/*     */         }
/*     */         
/*     */         public IntIterator iterator() {
/* 210 */           return new IntIterator()
/*     */             {
/* 212 */               private final ObjectIterator<Int2CharMap.Entry> i = Int2CharMaps.fastIterator(AbstractInt2CharMap.this);
/*     */               
/*     */               public int nextInt() {
/* 215 */                 return ((Int2CharMap.Entry)this.i.next()).getIntKey();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 219 */                 return this.i.hasNext();
/*     */               }
/*     */               
/*     */               public void remove() {
/* 223 */                 this.i.remove();
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
/*     */   public CharCollection values() {
/* 245 */     return (CharCollection)new AbstractCharCollection()
/*     */       {
/*     */         public boolean contains(char k) {
/* 248 */           return AbstractInt2CharMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 252 */           return AbstractInt2CharMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 256 */           AbstractInt2CharMap.this.clear();
/*     */         }
/*     */         
/*     */         public CharIterator iterator() {
/* 260 */           return new CharIterator()
/*     */             {
/* 262 */               private final ObjectIterator<Int2CharMap.Entry> i = Int2CharMaps.fastIterator(AbstractInt2CharMap.this);
/*     */               
/*     */               public char nextChar() {
/* 265 */                 return ((Int2CharMap.Entry)this.i.next()).getCharValue();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 269 */                 return this.i.hasNext();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Integer, ? extends Character> m) {
/* 279 */     if (m instanceof Int2CharMap) {
/* 280 */       ObjectIterator<Int2CharMap.Entry> i = Int2CharMaps.fastIterator((Int2CharMap)m);
/* 281 */       while (i.hasNext()) {
/* 282 */         Int2CharMap.Entry e = (Int2CharMap.Entry)i.next();
/* 283 */         put(e.getIntKey(), e.getCharValue());
/*     */       } 
/*     */     } else {
/* 286 */       int n = m.size();
/* 287 */       Iterator<? extends Map.Entry<? extends Integer, ? extends Character>> i = m.entrySet().iterator();
/*     */       
/* 289 */       while (n-- != 0) {
/* 290 */         Map.Entry<? extends Integer, ? extends Character> e = i.next();
/* 291 */         put(e.getKey(), e.getValue());
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
/* 304 */     int h = 0, n = size();
/* 305 */     ObjectIterator<Int2CharMap.Entry> i = Int2CharMaps.fastIterator(this);
/* 306 */     while (n-- != 0)
/* 307 */       h += ((Int2CharMap.Entry)i.next()).hashCode(); 
/* 308 */     return h;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 312 */     if (o == this)
/* 313 */       return true; 
/* 314 */     if (!(o instanceof Map))
/* 315 */       return false; 
/* 316 */     Map<?, ?> m = (Map<?, ?>)o;
/* 317 */     if (m.size() != size())
/* 318 */       return false; 
/* 319 */     return int2CharEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 323 */     StringBuilder s = new StringBuilder();
/* 324 */     ObjectIterator<Int2CharMap.Entry> i = Int2CharMaps.fastIterator(this);
/* 325 */     int n = size();
/*     */     
/* 327 */     boolean first = true;
/* 328 */     s.append("{");
/* 329 */     while (n-- != 0) {
/* 330 */       if (first) {
/* 331 */         first = false;
/*     */       } else {
/* 333 */         s.append(", ");
/* 334 */       }  Int2CharMap.Entry e = (Int2CharMap.Entry)i.next();
/* 335 */       s.append(String.valueOf(e.getIntKey()));
/* 336 */       s.append("=>");
/* 337 */       s.append(String.valueOf(e.getCharValue()));
/*     */     } 
/* 339 */     s.append("}");
/* 340 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\AbstractInt2CharMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */