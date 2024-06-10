/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
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
/*     */ public abstract class AbstractFloat2IntMap
/*     */   extends AbstractFloat2IntFunction
/*     */   implements Float2IntMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(int v) {
/*  49 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(float k) {
/*  53 */     ObjectIterator<Float2IntMap.Entry> i = float2IntEntrySet().iterator();
/*  54 */     while (i.hasNext()) {
/*  55 */       if (((Float2IntMap.Entry)i.next()).getFloatKey() == k)
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
/*     */     implements Float2IntMap.Entry
/*     */   {
/*     */     protected float key;
/*     */     
/*     */     protected int value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Float key, Integer value) {
/*  78 */       this.key = key.floatValue();
/*  79 */       this.value = value.intValue();
/*     */     }
/*     */     public BasicEntry(float key, int value) {
/*  82 */       this.key = key;
/*  83 */       this.value = value;
/*     */     }
/*     */     
/*     */     public float getFloatKey() {
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
/* 102 */       if (o instanceof Float2IntMap.Entry) {
/* 103 */         Float2IntMap.Entry entry = (Float2IntMap.Entry)o;
/* 104 */         return (Float.floatToIntBits(this.key) == Float.floatToIntBits(entry.getFloatKey()) && this.value == entry
/* 105 */           .getIntValue());
/*     */       } 
/* 107 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 108 */       Object key = e.getKey();
/* 109 */       if (key == null || !(key instanceof Float))
/* 110 */         return false; 
/* 111 */       Object value = e.getValue();
/* 112 */       if (value == null || !(value instanceof Integer))
/* 113 */         return false; 
/* 114 */       return (Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)key).floatValue()) && this.value == ((Integer)value)
/* 115 */         .intValue());
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 119 */       return HashCommon.float2int(this.key) ^ this.value;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 123 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet
/*     */     extends AbstractObjectSet<Float2IntMap.Entry>
/*     */   {
/*     */     protected final Float2IntMap map;
/*     */     
/*     */     public BasicEntrySet(Float2IntMap map) {
/* 133 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 138 */       if (!(o instanceof Map.Entry))
/* 139 */         return false; 
/* 140 */       if (o instanceof Float2IntMap.Entry) {
/* 141 */         Float2IntMap.Entry entry = (Float2IntMap.Entry)o;
/* 142 */         float f = entry.getFloatKey();
/* 143 */         return (this.map.containsKey(f) && this.map.get(f) == entry.getIntValue());
/*     */       } 
/* 145 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 146 */       Object key = e.getKey();
/* 147 */       if (key == null || !(key instanceof Float))
/* 148 */         return false; 
/* 149 */       float k = ((Float)key).floatValue();
/* 150 */       Object value = e.getValue();
/* 151 */       if (value == null || !(value instanceof Integer))
/* 152 */         return false; 
/* 153 */       return (this.map.containsKey(k) && this.map.get(k) == ((Integer)value).intValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 158 */       if (!(o instanceof Map.Entry))
/* 159 */         return false; 
/* 160 */       if (o instanceof Float2IntMap.Entry) {
/* 161 */         Float2IntMap.Entry entry = (Float2IntMap.Entry)o;
/* 162 */         return this.map.remove(entry.getFloatKey(), entry.getIntValue());
/*     */       } 
/* 164 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 165 */       Object key = e.getKey();
/* 166 */       if (key == null || !(key instanceof Float))
/* 167 */         return false; 
/* 168 */       float k = ((Float)key).floatValue();
/* 169 */       Object value = e.getValue();
/* 170 */       if (value == null || !(value instanceof Integer))
/* 171 */         return false; 
/* 172 */       int v = ((Integer)value).intValue();
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
/*     */   public FloatSet keySet() {
/* 196 */     return new AbstractFloatSet()
/*     */       {
/*     */         public boolean contains(float k) {
/* 199 */           return AbstractFloat2IntMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 203 */           return AbstractFloat2IntMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 207 */           AbstractFloat2IntMap.this.clear();
/*     */         }
/*     */         
/*     */         public FloatIterator iterator() {
/* 211 */           return new FloatIterator()
/*     */             {
/* 213 */               private final ObjectIterator<Float2IntMap.Entry> i = Float2IntMaps.fastIterator(AbstractFloat2IntMap.this);
/*     */               
/*     */               public float nextFloat() {
/* 216 */                 return ((Float2IntMap.Entry)this.i.next()).getFloatKey();
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
/*     */   public IntCollection values() {
/* 246 */     return (IntCollection)new AbstractIntCollection()
/*     */       {
/*     */         public boolean contains(int k) {
/* 249 */           return AbstractFloat2IntMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 253 */           return AbstractFloat2IntMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 257 */           AbstractFloat2IntMap.this.clear();
/*     */         }
/*     */         
/*     */         public IntIterator iterator() {
/* 261 */           return new IntIterator()
/*     */             {
/* 263 */               private final ObjectIterator<Float2IntMap.Entry> i = Float2IntMaps.fastIterator(AbstractFloat2IntMap.this);
/*     */               
/*     */               public int nextInt() {
/* 266 */                 return ((Float2IntMap.Entry)this.i.next()).getIntValue();
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
/*     */   public void putAll(Map<? extends Float, ? extends Integer> m) {
/* 280 */     if (m instanceof Float2IntMap) {
/* 281 */       ObjectIterator<Float2IntMap.Entry> i = Float2IntMaps.fastIterator((Float2IntMap)m);
/* 282 */       while (i.hasNext()) {
/* 283 */         Float2IntMap.Entry e = (Float2IntMap.Entry)i.next();
/* 284 */         put(e.getFloatKey(), e.getIntValue());
/*     */       } 
/*     */     } else {
/* 287 */       int n = m.size();
/* 288 */       Iterator<? extends Map.Entry<? extends Float, ? extends Integer>> i = m.entrySet().iterator();
/*     */       
/* 290 */       while (n-- != 0) {
/* 291 */         Map.Entry<? extends Float, ? extends Integer> e = i.next();
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
/* 306 */     ObjectIterator<Float2IntMap.Entry> i = Float2IntMaps.fastIterator(this);
/* 307 */     while (n-- != 0)
/* 308 */       h += ((Float2IntMap.Entry)i.next()).hashCode(); 
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
/* 320 */     return float2IntEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 324 */     StringBuilder s = new StringBuilder();
/* 325 */     ObjectIterator<Float2IntMap.Entry> i = Float2IntMaps.fastIterator(this);
/* 326 */     int n = size();
/*     */     
/* 328 */     boolean first = true;
/* 329 */     s.append("{");
/* 330 */     while (n-- != 0) {
/* 331 */       if (first) {
/* 332 */         first = false;
/*     */       } else {
/* 334 */         s.append(", ");
/* 335 */       }  Float2IntMap.Entry e = (Float2IntMap.Entry)i.next();
/* 336 */       s.append(String.valueOf(e.getFloatKey()));
/* 337 */       s.append("=>");
/* 338 */       s.append(String.valueOf(e.getIntValue()));
/*     */     } 
/* 340 */     s.append("}");
/* 341 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\AbstractFloat2IntMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */