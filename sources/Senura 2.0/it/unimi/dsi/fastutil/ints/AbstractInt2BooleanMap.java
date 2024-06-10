/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
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
/*     */ public abstract class AbstractInt2BooleanMap
/*     */   extends AbstractInt2BooleanFunction
/*     */   implements Int2BooleanMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(boolean v) {
/*  49 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(int k) {
/*  53 */     ObjectIterator<Int2BooleanMap.Entry> i = int2BooleanEntrySet().iterator();
/*  54 */     while (i.hasNext()) {
/*  55 */       if (((Int2BooleanMap.Entry)i.next()).getIntKey() == k)
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
/*     */     implements Int2BooleanMap.Entry
/*     */   {
/*     */     protected int key;
/*     */     
/*     */     protected boolean value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Integer key, Boolean value) {
/*  78 */       this.key = key.intValue();
/*  79 */       this.value = value.booleanValue();
/*     */     }
/*     */     public BasicEntry(int key, boolean value) {
/*  82 */       this.key = key;
/*  83 */       this.value = value;
/*     */     }
/*     */     
/*     */     public int getIntKey() {
/*  87 */       return this.key;
/*     */     }
/*     */     
/*     */     public boolean getBooleanValue() {
/*  91 */       return this.value;
/*     */     }
/*     */     
/*     */     public boolean setValue(boolean value) {
/*  95 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 100 */       if (!(o instanceof Map.Entry))
/* 101 */         return false; 
/* 102 */       if (o instanceof Int2BooleanMap.Entry) {
/* 103 */         Int2BooleanMap.Entry entry = (Int2BooleanMap.Entry)o;
/* 104 */         return (this.key == entry.getIntKey() && this.value == entry.getBooleanValue());
/*     */       } 
/* 106 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 107 */       Object key = e.getKey();
/* 108 */       if (key == null || !(key instanceof Integer))
/* 109 */         return false; 
/* 110 */       Object value = e.getValue();
/* 111 */       if (value == null || !(value instanceof Boolean))
/* 112 */         return false; 
/* 113 */       return (this.key == ((Integer)key).intValue() && this.value == ((Boolean)value)
/* 114 */         .booleanValue());
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 118 */       return this.key ^ (this.value ? 1231 : 1237);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 122 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet
/*     */     extends AbstractObjectSet<Int2BooleanMap.Entry>
/*     */   {
/*     */     protected final Int2BooleanMap map;
/*     */     
/*     */     public BasicEntrySet(Int2BooleanMap map) {
/* 132 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 137 */       if (!(o instanceof Map.Entry))
/* 138 */         return false; 
/* 139 */       if (o instanceof Int2BooleanMap.Entry) {
/* 140 */         Int2BooleanMap.Entry entry = (Int2BooleanMap.Entry)o;
/* 141 */         int i = entry.getIntKey();
/* 142 */         return (this.map.containsKey(i) && this.map.get(i) == entry.getBooleanValue());
/*     */       } 
/* 144 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 145 */       Object key = e.getKey();
/* 146 */       if (key == null || !(key instanceof Integer))
/* 147 */         return false; 
/* 148 */       int k = ((Integer)key).intValue();
/* 149 */       Object value = e.getValue();
/* 150 */       if (value == null || !(value instanceof Boolean))
/* 151 */         return false; 
/* 152 */       return (this.map.containsKey(k) && this.map.get(k) == ((Boolean)value).booleanValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 157 */       if (!(o instanceof Map.Entry))
/* 158 */         return false; 
/* 159 */       if (o instanceof Int2BooleanMap.Entry) {
/* 160 */         Int2BooleanMap.Entry entry = (Int2BooleanMap.Entry)o;
/* 161 */         return this.map.remove(entry.getIntKey(), entry.getBooleanValue());
/*     */       } 
/* 163 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 164 */       Object key = e.getKey();
/* 165 */       if (key == null || !(key instanceof Integer))
/* 166 */         return false; 
/* 167 */       int k = ((Integer)key).intValue();
/* 168 */       Object value = e.getValue();
/* 169 */       if (value == null || !(value instanceof Boolean))
/* 170 */         return false; 
/* 171 */       boolean v = ((Boolean)value).booleanValue();
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
/* 198 */           return AbstractInt2BooleanMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 202 */           return AbstractInt2BooleanMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 206 */           AbstractInt2BooleanMap.this.clear();
/*     */         }
/*     */         
/*     */         public IntIterator iterator() {
/* 210 */           return new IntIterator()
/*     */             {
/* 212 */               private final ObjectIterator<Int2BooleanMap.Entry> i = Int2BooleanMaps.fastIterator(AbstractInt2BooleanMap.this);
/*     */               
/*     */               public int nextInt() {
/* 215 */                 return ((Int2BooleanMap.Entry)this.i.next()).getIntKey();
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
/*     */   public BooleanCollection values() {
/* 245 */     return (BooleanCollection)new AbstractBooleanCollection()
/*     */       {
/*     */         public boolean contains(boolean k) {
/* 248 */           return AbstractInt2BooleanMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 252 */           return AbstractInt2BooleanMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 256 */           AbstractInt2BooleanMap.this.clear();
/*     */         }
/*     */         
/*     */         public BooleanIterator iterator() {
/* 260 */           return new BooleanIterator()
/*     */             {
/* 262 */               private final ObjectIterator<Int2BooleanMap.Entry> i = Int2BooleanMaps.fastIterator(AbstractInt2BooleanMap.this);
/*     */               
/*     */               public boolean nextBoolean() {
/* 265 */                 return ((Int2BooleanMap.Entry)this.i.next()).getBooleanValue();
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
/*     */   public void putAll(Map<? extends Integer, ? extends Boolean> m) {
/* 279 */     if (m instanceof Int2BooleanMap) {
/* 280 */       ObjectIterator<Int2BooleanMap.Entry> i = Int2BooleanMaps.fastIterator((Int2BooleanMap)m);
/* 281 */       while (i.hasNext()) {
/* 282 */         Int2BooleanMap.Entry e = (Int2BooleanMap.Entry)i.next();
/* 283 */         put(e.getIntKey(), e.getBooleanValue());
/*     */       } 
/*     */     } else {
/* 286 */       int n = m.size();
/* 287 */       Iterator<? extends Map.Entry<? extends Integer, ? extends Boolean>> i = m.entrySet().iterator();
/*     */       
/* 289 */       while (n-- != 0) {
/* 290 */         Map.Entry<? extends Integer, ? extends Boolean> e = i.next();
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
/* 305 */     ObjectIterator<Int2BooleanMap.Entry> i = Int2BooleanMaps.fastIterator(this);
/* 306 */     while (n-- != 0)
/* 307 */       h += ((Int2BooleanMap.Entry)i.next()).hashCode(); 
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
/* 319 */     return int2BooleanEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 323 */     StringBuilder s = new StringBuilder();
/* 324 */     ObjectIterator<Int2BooleanMap.Entry> i = Int2BooleanMaps.fastIterator(this);
/* 325 */     int n = size();
/*     */     
/* 327 */     boolean first = true;
/* 328 */     s.append("{");
/* 329 */     while (n-- != 0) {
/* 330 */       if (first) {
/* 331 */         first = false;
/*     */       } else {
/* 333 */         s.append(", ");
/* 334 */       }  Int2BooleanMap.Entry e = (Int2BooleanMap.Entry)i.next();
/* 335 */       s.append(String.valueOf(e.getIntKey()));
/* 336 */       s.append("=>");
/* 337 */       s.append(String.valueOf(e.getBooleanValue()));
/*     */     } 
/* 339 */     s.append("}");
/* 340 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\AbstractInt2BooleanMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */