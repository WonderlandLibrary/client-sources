/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
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
/*     */ public abstract class AbstractByte2DoubleMap
/*     */   extends AbstractByte2DoubleFunction
/*     */   implements Byte2DoubleMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(double v) {
/*  49 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(byte k) {
/*  53 */     ObjectIterator<Byte2DoubleMap.Entry> i = byte2DoubleEntrySet().iterator();
/*  54 */     while (i.hasNext()) {
/*  55 */       if (((Byte2DoubleMap.Entry)i.next()).getByteKey() == k)
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
/*     */     implements Byte2DoubleMap.Entry
/*     */   {
/*     */     protected byte key;
/*     */     
/*     */     protected double value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Byte key, Double value) {
/*  78 */       this.key = key.byteValue();
/*  79 */       this.value = value.doubleValue();
/*     */     }
/*     */     public BasicEntry(byte key, double value) {
/*  82 */       this.key = key;
/*  83 */       this.value = value;
/*     */     }
/*     */     
/*     */     public byte getByteKey() {
/*  87 */       return this.key;
/*     */     }
/*     */     
/*     */     public double getDoubleValue() {
/*  91 */       return this.value;
/*     */     }
/*     */     
/*     */     public double setValue(double value) {
/*  95 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 100 */       if (!(o instanceof Map.Entry))
/* 101 */         return false; 
/* 102 */       if (o instanceof Byte2DoubleMap.Entry) {
/* 103 */         Byte2DoubleMap.Entry entry = (Byte2DoubleMap.Entry)o;
/* 104 */         return (this.key == entry.getByteKey() && 
/* 105 */           Double.doubleToLongBits(this.value) == Double.doubleToLongBits(entry.getDoubleValue()));
/*     */       } 
/* 107 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 108 */       Object key = e.getKey();
/* 109 */       if (key == null || !(key instanceof Byte))
/* 110 */         return false; 
/* 111 */       Object value = e.getValue();
/* 112 */       if (value == null || !(value instanceof Double))
/* 113 */         return false; 
/* 114 */       return (this.key == ((Byte)key).byteValue() && Double.doubleToLongBits(this.value) == 
/* 115 */         Double.doubleToLongBits(((Double)value).doubleValue()));
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 119 */       return this.key ^ HashCommon.double2int(this.value);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 123 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet
/*     */     extends AbstractObjectSet<Byte2DoubleMap.Entry>
/*     */   {
/*     */     protected final Byte2DoubleMap map;
/*     */     
/*     */     public BasicEntrySet(Byte2DoubleMap map) {
/* 133 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 138 */       if (!(o instanceof Map.Entry))
/* 139 */         return false; 
/* 140 */       if (o instanceof Byte2DoubleMap.Entry) {
/* 141 */         Byte2DoubleMap.Entry entry = (Byte2DoubleMap.Entry)o;
/* 142 */         byte b = entry.getByteKey();
/* 143 */         return (this.map.containsKey(b) && 
/* 144 */           Double.doubleToLongBits(this.map.get(b)) == Double.doubleToLongBits(entry.getDoubleValue()));
/*     */       } 
/* 146 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 147 */       Object key = e.getKey();
/* 148 */       if (key == null || !(key instanceof Byte))
/* 149 */         return false; 
/* 150 */       byte k = ((Byte)key).byteValue();
/* 151 */       Object value = e.getValue();
/* 152 */       if (value == null || !(value instanceof Double))
/* 153 */         return false; 
/* 154 */       return (this.map.containsKey(k) && Double.doubleToLongBits(this.map.get(k)) == 
/* 155 */         Double.doubleToLongBits(((Double)value).doubleValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 160 */       if (!(o instanceof Map.Entry))
/* 161 */         return false; 
/* 162 */       if (o instanceof Byte2DoubleMap.Entry) {
/* 163 */         Byte2DoubleMap.Entry entry = (Byte2DoubleMap.Entry)o;
/* 164 */         return this.map.remove(entry.getByteKey(), entry.getDoubleValue());
/*     */       } 
/* 166 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 167 */       Object key = e.getKey();
/* 168 */       if (key == null || !(key instanceof Byte))
/* 169 */         return false; 
/* 170 */       byte k = ((Byte)key).byteValue();
/* 171 */       Object value = e.getValue();
/* 172 */       if (value == null || !(value instanceof Double))
/* 173 */         return false; 
/* 174 */       double v = ((Double)value).doubleValue();
/* 175 */       return this.map.remove(k, v);
/*     */     }
/*     */     
/*     */     public int size() {
/* 179 */       return this.map.size();
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
/*     */   public ByteSet keySet() {
/* 198 */     return new AbstractByteSet()
/*     */       {
/*     */         public boolean contains(byte k) {
/* 201 */           return AbstractByte2DoubleMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 205 */           return AbstractByte2DoubleMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 209 */           AbstractByte2DoubleMap.this.clear();
/*     */         }
/*     */         
/*     */         public ByteIterator iterator() {
/* 213 */           return new ByteIterator()
/*     */             {
/* 215 */               private final ObjectIterator<Byte2DoubleMap.Entry> i = Byte2DoubleMaps.fastIterator(AbstractByte2DoubleMap.this);
/*     */               
/*     */               public byte nextByte() {
/* 218 */                 return ((Byte2DoubleMap.Entry)this.i.next()).getByteKey();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 222 */                 return this.i.hasNext();
/*     */               }
/*     */               
/*     */               public void remove() {
/* 226 */                 this.i.remove();
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
/*     */   public DoubleCollection values() {
/* 248 */     return (DoubleCollection)new AbstractDoubleCollection()
/*     */       {
/*     */         public boolean contains(double k) {
/* 251 */           return AbstractByte2DoubleMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 255 */           return AbstractByte2DoubleMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 259 */           AbstractByte2DoubleMap.this.clear();
/*     */         }
/*     */         
/*     */         public DoubleIterator iterator() {
/* 263 */           return new DoubleIterator()
/*     */             {
/* 265 */               private final ObjectIterator<Byte2DoubleMap.Entry> i = Byte2DoubleMaps.fastIterator(AbstractByte2DoubleMap.this);
/*     */               
/*     */               public double nextDouble() {
/* 268 */                 return ((Byte2DoubleMap.Entry)this.i.next()).getDoubleValue();
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 272 */                 return this.i.hasNext();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Byte, ? extends Double> m) {
/* 282 */     if (m instanceof Byte2DoubleMap) {
/* 283 */       ObjectIterator<Byte2DoubleMap.Entry> i = Byte2DoubleMaps.fastIterator((Byte2DoubleMap)m);
/* 284 */       while (i.hasNext()) {
/* 285 */         Byte2DoubleMap.Entry e = (Byte2DoubleMap.Entry)i.next();
/* 286 */         put(e.getByteKey(), e.getDoubleValue());
/*     */       } 
/*     */     } else {
/* 289 */       int n = m.size();
/* 290 */       Iterator<? extends Map.Entry<? extends Byte, ? extends Double>> i = m.entrySet().iterator();
/*     */       
/* 292 */       while (n-- != 0) {
/* 293 */         Map.Entry<? extends Byte, ? extends Double> e = i.next();
/* 294 */         put(e.getKey(), e.getValue());
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
/* 307 */     int h = 0, n = size();
/* 308 */     ObjectIterator<Byte2DoubleMap.Entry> i = Byte2DoubleMaps.fastIterator(this);
/* 309 */     while (n-- != 0)
/* 310 */       h += ((Byte2DoubleMap.Entry)i.next()).hashCode(); 
/* 311 */     return h;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 315 */     if (o == this)
/* 316 */       return true; 
/* 317 */     if (!(o instanceof Map))
/* 318 */       return false; 
/* 319 */     Map<?, ?> m = (Map<?, ?>)o;
/* 320 */     if (m.size() != size())
/* 321 */       return false; 
/* 322 */     return byte2DoubleEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 326 */     StringBuilder s = new StringBuilder();
/* 327 */     ObjectIterator<Byte2DoubleMap.Entry> i = Byte2DoubleMaps.fastIterator(this);
/* 328 */     int n = size();
/*     */     
/* 330 */     boolean first = true;
/* 331 */     s.append("{");
/* 332 */     while (n-- != 0) {
/* 333 */       if (first) {
/* 334 */         first = false;
/*     */       } else {
/* 336 */         s.append(", ");
/* 337 */       }  Byte2DoubleMap.Entry e = (Byte2DoubleMap.Entry)i.next();
/* 338 */       s.append(String.valueOf(e.getByteKey()));
/* 339 */       s.append("=>");
/* 340 */       s.append(String.valueOf(e.getDoubleValue()));
/*     */     } 
/* 342 */     s.append("}");
/* 343 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\AbstractByte2DoubleMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */