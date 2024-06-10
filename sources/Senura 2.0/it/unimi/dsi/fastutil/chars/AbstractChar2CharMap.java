/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractChar2CharMap
/*     */   extends AbstractChar2CharFunction
/*     */   implements Char2CharMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(char v) {
/*  49 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(char k) {
/*  53 */     ObjectIterator<Char2CharMap.Entry> i = char2CharEntrySet().iterator();
/*  54 */     while (i.hasNext()) {
/*  55 */       if (((Char2CharMap.Entry)i.next()).getCharKey() == k)
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
/*     */     implements Char2CharMap.Entry
/*     */   {
/*     */     protected char key;
/*     */     
/*     */     protected char value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Character key, Character value) {
/*  78 */       this.key = key.charValue();
/*  79 */       this.value = value.charValue();
/*     */     }
/*     */     public BasicEntry(char key, char value) {
/*  82 */       this.key = key;
/*  83 */       this.value = value;
/*     */     }
/*     */     
/*     */     public char getCharKey() {
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
/* 102 */       if (o instanceof Char2CharMap.Entry) {
/* 103 */         Char2CharMap.Entry entry = (Char2CharMap.Entry)o;
/* 104 */         return (this.key == entry.getCharKey() && this.value == entry.getCharValue());
/*     */       } 
/* 106 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 107 */       Object key = e.getKey();
/* 108 */       if (key == null || !(key instanceof Character))
/* 109 */         return false; 
/* 110 */       Object value = e.getValue();
/* 111 */       if (value == null || !(value instanceof Character))
/* 112 */         return false; 
/* 113 */       return (this.key == ((Character)key).charValue() && this.value == ((Character)value)
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
/*     */     extends AbstractObjectSet<Char2CharMap.Entry>
/*     */   {
/*     */     protected final Char2CharMap map;
/*     */     
/*     */     public BasicEntrySet(Char2CharMap map) {
/* 132 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 137 */       if (!(o instanceof Map.Entry))
/* 138 */         return false; 
/* 139 */       if (o instanceof Char2CharMap.Entry) {
/* 140 */         Char2CharMap.Entry entry = (Char2CharMap.Entry)o;
/* 141 */         char c = entry.getCharKey();
/* 142 */         return (this.map.containsKey(c) && this.map.get(c) == entry.getCharValue());
/*     */       } 
/* 144 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 145 */       Object key = e.getKey();
/* 146 */       if (key == null || !(key instanceof Character))
/* 147 */         return false; 
/* 148 */       char k = ((Character)key).charValue();
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
/* 159 */       if (o instanceof Char2CharMap.Entry) {
/* 160 */         Char2CharMap.Entry entry = (Char2CharMap.Entry)o;
/* 161 */         return this.map.remove(entry.getCharKey(), entry.getCharValue());
/*     */       } 
/* 163 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 164 */       Object key = e.getKey();
/* 165 */       if (key == null || !(key instanceof Character))
/* 166 */         return false; 
/* 167 */       char k = ((Character)key).charValue();
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
/*     */   public CharSet keySet() {
/* 195 */     return new AbstractCharSet()
/*     */       {
/*     */         public boolean contains(char k) {
/* 198 */           return AbstractChar2CharMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 202 */           return AbstractChar2CharMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 206 */           AbstractChar2CharMap.this.clear();
/*     */         }
/*     */         
/*     */         public CharIterator iterator() {
/* 210 */           return new CharIterator()
/*     */             {
/* 212 */               private final ObjectIterator<Char2CharMap.Entry> i = Char2CharMaps.fastIterator(AbstractChar2CharMap.this);
/*     */               
/*     */               public char nextChar() {
/* 215 */                 return ((Char2CharMap.Entry)this.i.next()).getCharKey();
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
/* 245 */     return new AbstractCharCollection()
/*     */       {
/*     */         public boolean contains(char k) {
/* 248 */           return AbstractChar2CharMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 252 */           return AbstractChar2CharMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 256 */           AbstractChar2CharMap.this.clear();
/*     */         }
/*     */         
/*     */         public CharIterator iterator() {
/* 260 */           return new CharIterator()
/*     */             {
/* 262 */               private final ObjectIterator<Char2CharMap.Entry> i = Char2CharMaps.fastIterator(AbstractChar2CharMap.this);
/*     */               
/*     */               public char nextChar() {
/* 265 */                 return ((Char2CharMap.Entry)this.i.next()).getCharValue();
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
/*     */   public void putAll(Map<? extends Character, ? extends Character> m) {
/* 279 */     if (m instanceof Char2CharMap) {
/* 280 */       ObjectIterator<Char2CharMap.Entry> i = Char2CharMaps.fastIterator((Char2CharMap)m);
/* 281 */       while (i.hasNext()) {
/* 282 */         Char2CharMap.Entry e = (Char2CharMap.Entry)i.next();
/* 283 */         put(e.getCharKey(), e.getCharValue());
/*     */       } 
/*     */     } else {
/* 286 */       int n = m.size();
/* 287 */       Iterator<? extends Map.Entry<? extends Character, ? extends Character>> i = m.entrySet().iterator();
/*     */       
/* 289 */       while (n-- != 0) {
/* 290 */         Map.Entry<? extends Character, ? extends Character> e = i.next();
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
/* 305 */     ObjectIterator<Char2CharMap.Entry> i = Char2CharMaps.fastIterator(this);
/* 306 */     while (n-- != 0)
/* 307 */       h += ((Char2CharMap.Entry)i.next()).hashCode(); 
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
/* 319 */     return char2CharEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 323 */     StringBuilder s = new StringBuilder();
/* 324 */     ObjectIterator<Char2CharMap.Entry> i = Char2CharMaps.fastIterator(this);
/* 325 */     int n = size();
/*     */     
/* 327 */     boolean first = true;
/* 328 */     s.append("{");
/* 329 */     while (n-- != 0) {
/* 330 */       if (first) {
/* 331 */         first = false;
/*     */       } else {
/* 333 */         s.append(", ");
/* 334 */       }  Char2CharMap.Entry e = (Char2CharMap.Entry)i.next();
/* 335 */       s.append(String.valueOf(e.getCharKey()));
/* 336 */       s.append("=>");
/* 337 */       s.append(String.valueOf(e.getCharValue()));
/*     */     } 
/* 339 */     s.append("}");
/* 340 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\AbstractChar2CharMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */