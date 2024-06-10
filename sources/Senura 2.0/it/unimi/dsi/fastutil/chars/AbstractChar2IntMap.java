/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public abstract class AbstractChar2IntMap
/*     */   extends AbstractChar2IntFunction
/*     */   implements Char2IntMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(int v) {
/*  49 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(char k) {
/*  53 */     ObjectIterator<Char2IntMap.Entry> i = char2IntEntrySet().iterator();
/*  54 */     while (i.hasNext()) {
/*  55 */       if (((Char2IntMap.Entry)i.next()).getCharKey() == k)
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
/*     */     implements Char2IntMap.Entry
/*     */   {
/*     */     protected char key;
/*     */     
/*     */     protected int value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Character key, Integer value) {
/*  78 */       this.key = key.charValue();
/*  79 */       this.value = value.intValue();
/*     */     }
/*     */     public BasicEntry(char key, int value) {
/*  82 */       this.key = key;
/*  83 */       this.value = value;
/*     */     }
/*     */     
/*     */     public char getCharKey() {
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
/* 102 */       if (o instanceof Char2IntMap.Entry) {
/* 103 */         Char2IntMap.Entry entry = (Char2IntMap.Entry)o;
/* 104 */         return (this.key == entry.getCharKey() && this.value == entry.getIntValue());
/*     */       } 
/* 106 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 107 */       Object key = e.getKey();
/* 108 */       if (key == null || !(key instanceof Character))
/* 109 */         return false; 
/* 110 */       Object value = e.getValue();
/* 111 */       if (value == null || !(value instanceof Integer))
/* 112 */         return false; 
/* 113 */       return (this.key == ((Character)key).charValue() && this.value == ((Integer)value)
/* 114 */         .intValue());
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
/*     */     extends AbstractObjectSet<Char2IntMap.Entry>
/*     */   {
/*     */     protected final Char2IntMap map;
/*     */     
/*     */     public BasicEntrySet(Char2IntMap map) {
/* 132 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 137 */       if (!(o instanceof Map.Entry))
/* 138 */         return false; 
/* 139 */       if (o instanceof Char2IntMap.Entry) {
/* 140 */         Char2IntMap.Entry entry = (Char2IntMap.Entry)o;
/* 141 */         char c = entry.getCharKey();
/* 142 */         return (this.map.containsKey(c) && this.map.get(c) == entry.getIntValue());
/*     */       } 
/* 144 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 145 */       Object key = e.getKey();
/* 146 */       if (key == null || !(key instanceof Character))
/* 147 */         return false; 
/* 148 */       char k = ((Character)key).charValue();
/* 149 */       Object value = e.getValue();
/* 150 */       if (value == null || !(value instanceof Integer))
/* 151 */         return false; 
/* 152 */       return (this.map.containsKey(k) && this.map.get(k) == ((Integer)value).intValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 157 */       if (!(o instanceof Map.Entry))
/* 158 */         return false; 
/* 159 */       if (o instanceof Char2IntMap.Entry) {
/* 160 */         Char2IntMap.Entry entry = (Char2IntMap.Entry)o;
/* 161 */         return this.map.remove(entry.getCharKey(), entry.getIntValue());
/*     */       } 
/* 163 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 164 */       Object key = e.getKey();
/* 165 */       if (key == null || !(key instanceof Character))
/* 166 */         return false; 
/* 167 */       char k = ((Character)key).charValue();
/* 168 */       Object value = e.getValue();
/* 169 */       if (value == null || !(value instanceof Integer))
/* 170 */         return false; 
/* 171 */       int v = ((Integer)value).intValue();
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
/*     */   public CharSet keySet() {
/* 195 */     return new AbstractCharSet()
/*     */       {
/*     */         public boolean contains(char k) {
/* 198 */           return AbstractChar2IntMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 202 */           return AbstractChar2IntMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 206 */           AbstractChar2IntMap.this.clear();
/*     */         }
/*     */         
/*     */         public CharIterator iterator() {
/* 210 */           return new CharIterator()
/*     */             {
/* 212 */               private final ObjectIterator<Char2IntMap.Entry> i = Char2IntMaps.fastIterator(AbstractChar2IntMap.this);
/*     */               
/*     */               public char nextChar() {
/* 215 */                 return ((Char2IntMap.Entry)this.i.next()).getCharKey();
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
/*     */   public IntCollection values() {
/* 245 */     return (IntCollection)new AbstractIntCollection()
/*     */       {
/*     */         public boolean contains(int k) {
/* 248 */           return AbstractChar2IntMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 252 */           return AbstractChar2IntMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 256 */           AbstractChar2IntMap.this.clear();
/*     */         }
/*     */         
/*     */         public IntIterator iterator() {
/* 260 */           return new IntIterator()
/*     */             {
/* 262 */               private final ObjectIterator<Char2IntMap.Entry> i = Char2IntMaps.fastIterator(AbstractChar2IntMap.this);
/*     */               
/*     */               public int nextInt() {
/* 265 */                 return ((Char2IntMap.Entry)this.i.next()).getIntValue();
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
/*     */   public void putAll(Map<? extends Character, ? extends Integer> m) {
/* 279 */     if (m instanceof Char2IntMap) {
/* 280 */       ObjectIterator<Char2IntMap.Entry> i = Char2IntMaps.fastIterator((Char2IntMap)m);
/* 281 */       while (i.hasNext()) {
/* 282 */         Char2IntMap.Entry e = (Char2IntMap.Entry)i.next();
/* 283 */         put(e.getCharKey(), e.getIntValue());
/*     */       } 
/*     */     } else {
/* 286 */       int n = m.size();
/* 287 */       Iterator<? extends Map.Entry<? extends Character, ? extends Integer>> i = m.entrySet().iterator();
/*     */       
/* 289 */       while (n-- != 0) {
/* 290 */         Map.Entry<? extends Character, ? extends Integer> e = i.next();
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
/* 305 */     ObjectIterator<Char2IntMap.Entry> i = Char2IntMaps.fastIterator(this);
/* 306 */     while (n-- != 0)
/* 307 */       h += ((Char2IntMap.Entry)i.next()).hashCode(); 
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
/* 319 */     return char2IntEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 323 */     StringBuilder s = new StringBuilder();
/* 324 */     ObjectIterator<Char2IntMap.Entry> i = Char2IntMaps.fastIterator(this);
/* 325 */     int n = size();
/*     */     
/* 327 */     boolean first = true;
/* 328 */     s.append("{");
/* 329 */     while (n-- != 0) {
/* 330 */       if (first) {
/* 331 */         first = false;
/*     */       } else {
/* 333 */         s.append(", ");
/* 334 */       }  Char2IntMap.Entry e = (Char2IntMap.Entry)i.next();
/* 335 */       s.append(String.valueOf(e.getCharKey()));
/* 336 */       s.append("=>");
/* 337 */       s.append(String.valueOf(e.getIntValue()));
/*     */     } 
/* 339 */     s.append("}");
/* 340 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\AbstractChar2IntMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */