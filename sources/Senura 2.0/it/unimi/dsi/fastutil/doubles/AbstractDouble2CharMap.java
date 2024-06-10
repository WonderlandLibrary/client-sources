/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
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
/*     */ public abstract class AbstractDouble2CharMap
/*     */   extends AbstractDouble2CharFunction
/*     */   implements Double2CharMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(char v) {
/*  49 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(double k) {
/*  53 */     ObjectIterator<Double2CharMap.Entry> i = double2CharEntrySet().iterator();
/*  54 */     while (i.hasNext()) {
/*  55 */       if (((Double2CharMap.Entry)i.next()).getDoubleKey() == k)
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
/*     */     implements Double2CharMap.Entry
/*     */   {
/*     */     protected double key;
/*     */     
/*     */     protected char value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Double key, Character value) {
/*  78 */       this.key = key.doubleValue();
/*  79 */       this.value = value.charValue();
/*     */     }
/*     */     public BasicEntry(double key, char value) {
/*  82 */       this.key = key;
/*  83 */       this.value = value;
/*     */     }
/*     */     
/*     */     public double getDoubleKey() {
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
/* 102 */       if (o instanceof Double2CharMap.Entry) {
/* 103 */         Double2CharMap.Entry entry = (Double2CharMap.Entry)o;
/* 104 */         return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(entry.getDoubleKey()) && this.value == entry
/* 105 */           .getCharValue());
/*     */       } 
/* 107 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 108 */       Object key = e.getKey();
/* 109 */       if (key == null || !(key instanceof Double))
/* 110 */         return false; 
/* 111 */       Object value = e.getValue();
/* 112 */       if (value == null || !(value instanceof Character))
/* 113 */         return false; 
/* 114 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(((Double)key).doubleValue()) && this.value == ((Character)value)
/* 115 */         .charValue());
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 119 */       return HashCommon.double2int(this.key) ^ this.value;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 123 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet
/*     */     extends AbstractObjectSet<Double2CharMap.Entry>
/*     */   {
/*     */     protected final Double2CharMap map;
/*     */     
/*     */     public BasicEntrySet(Double2CharMap map) {
/* 133 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 138 */       if (!(o instanceof Map.Entry))
/* 139 */         return false; 
/* 140 */       if (o instanceof Double2CharMap.Entry) {
/* 141 */         Double2CharMap.Entry entry = (Double2CharMap.Entry)o;
/* 142 */         double d = entry.getDoubleKey();
/* 143 */         return (this.map.containsKey(d) && this.map.get(d) == entry.getCharValue());
/*     */       } 
/* 145 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 146 */       Object key = e.getKey();
/* 147 */       if (key == null || !(key instanceof Double))
/* 148 */         return false; 
/* 149 */       double k = ((Double)key).doubleValue();
/* 150 */       Object value = e.getValue();
/* 151 */       if (value == null || !(value instanceof Character))
/* 152 */         return false; 
/* 153 */       return (this.map.containsKey(k) && this.map.get(k) == ((Character)value).charValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 158 */       if (!(o instanceof Map.Entry))
/* 159 */         return false; 
/* 160 */       if (o instanceof Double2CharMap.Entry) {
/* 161 */         Double2CharMap.Entry entry = (Double2CharMap.Entry)o;
/* 162 */         return this.map.remove(entry.getDoubleKey(), entry.getCharValue());
/*     */       } 
/* 164 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 165 */       Object key = e.getKey();
/* 166 */       if (key == null || !(key instanceof Double))
/* 167 */         return false; 
/* 168 */       double k = ((Double)key).doubleValue();
/* 169 */       Object value = e.getValue();
/* 170 */       if (value == null || !(value instanceof Character))
/* 171 */         return false; 
/* 172 */       char v = ((Character)value).charValue();
/* 173 */       return this.map.remove(k, v);
/*     */     }
/*     */     
/*     */     public int size() {
/* 177 */       return this.map.size();
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
/*     */   public DoubleSet keySet() {
/* 196 */     return new AbstractDoubleSet()
/*     */       {
/*     */         public boolean contains(double k) {
/* 199 */           return AbstractDouble2CharMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 203 */           return AbstractDouble2CharMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 207 */           AbstractDouble2CharMap.this.clear();
/*     */         }
/*     */         
/*     */         public DoubleIterator iterator() {
/* 211 */           return new DoubleIterator()
/*     */             {
/* 213 */               private final ObjectIterator<Double2CharMap.Entry> i = Double2CharMaps.fastIterator(AbstractDouble2CharMap.this);
/*     */               
/*     */               public double nextDouble() {
/* 216 */                 return ((Double2CharMap.Entry)this.i.next()).getDoubleKey();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 220 */                 return this.i.hasNext();
/*     */               }
/*     */               
/*     */               public void remove() {
/* 224 */                 this.i.remove();
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
/* 246 */     return (CharCollection)new AbstractCharCollection()
/*     */       {
/*     */         public boolean contains(char k) {
/* 249 */           return AbstractDouble2CharMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 253 */           return AbstractDouble2CharMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 257 */           AbstractDouble2CharMap.this.clear();
/*     */         }
/*     */         
/*     */         public CharIterator iterator() {
/* 261 */           return new CharIterator()
/*     */             {
/* 263 */               private final ObjectIterator<Double2CharMap.Entry> i = Double2CharMaps.fastIterator(AbstractDouble2CharMap.this);
/*     */               
/*     */               public char nextChar() {
/* 266 */                 return ((Double2CharMap.Entry)this.i.next()).getCharValue();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 270 */                 return this.i.hasNext();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Double, ? extends Character> m) {
/* 280 */     if (m instanceof Double2CharMap) {
/* 281 */       ObjectIterator<Double2CharMap.Entry> i = Double2CharMaps.fastIterator((Double2CharMap)m);
/* 282 */       while (i.hasNext()) {
/* 283 */         Double2CharMap.Entry e = (Double2CharMap.Entry)i.next();
/* 284 */         put(e.getDoubleKey(), e.getCharValue());
/*     */       } 
/*     */     } else {
/* 287 */       int n = m.size();
/* 288 */       Iterator<? extends Map.Entry<? extends Double, ? extends Character>> i = m.entrySet().iterator();
/*     */       
/* 290 */       while (n-- != 0) {
/* 291 */         Map.Entry<? extends Double, ? extends Character> e = i.next();
/* 292 */         put(e.getKey(), e.getValue());
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
/* 305 */     int h = 0, n = size();
/* 306 */     ObjectIterator<Double2CharMap.Entry> i = Double2CharMaps.fastIterator(this);
/* 307 */     while (n-- != 0)
/* 308 */       h += ((Double2CharMap.Entry)i.next()).hashCode(); 
/* 309 */     return h;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 313 */     if (o == this)
/* 314 */       return true; 
/* 315 */     if (!(o instanceof Map))
/* 316 */       return false; 
/* 317 */     Map<?, ?> m = (Map<?, ?>)o;
/* 318 */     if (m.size() != size())
/* 319 */       return false; 
/* 320 */     return double2CharEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 324 */     StringBuilder s = new StringBuilder();
/* 325 */     ObjectIterator<Double2CharMap.Entry> i = Double2CharMaps.fastIterator(this);
/* 326 */     int n = size();
/*     */     
/* 328 */     boolean first = true;
/* 329 */     s.append("{");
/* 330 */     while (n-- != 0) {
/* 331 */       if (first) {
/* 332 */         first = false;
/*     */       } else {
/* 334 */         s.append(", ");
/* 335 */       }  Double2CharMap.Entry e = (Double2CharMap.Entry)i.next();
/* 336 */       s.append(String.valueOf(e.getDoubleKey()));
/* 337 */       s.append("=>");
/* 338 */       s.append(String.valueOf(e.getCharValue()));
/*     */     } 
/* 340 */     s.append("}");
/* 341 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\AbstractDouble2CharMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */