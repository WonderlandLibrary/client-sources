/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public abstract class AbstractByte2ByteMap
/*     */   extends AbstractByte2ByteFunction
/*     */   implements Byte2ByteMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(byte v) {
/*  49 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(byte k) {
/*  53 */     ObjectIterator<Byte2ByteMap.Entry> i = byte2ByteEntrySet().iterator();
/*  54 */     while (i.hasNext()) {
/*  55 */       if (((Byte2ByteMap.Entry)i.next()).getByteKey() == k)
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
/*     */     implements Byte2ByteMap.Entry
/*     */   {
/*     */     protected byte key;
/*     */     
/*     */     protected byte value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Byte key, Byte value) {
/*  78 */       this.key = key.byteValue();
/*  79 */       this.value = value.byteValue();
/*     */     }
/*     */     public BasicEntry(byte key, byte value) {
/*  82 */       this.key = key;
/*  83 */       this.value = value;
/*     */     }
/*     */     
/*     */     public byte getByteKey() {
/*  87 */       return this.key;
/*     */     }
/*     */     
/*     */     public byte getByteValue() {
/*  91 */       return this.value;
/*     */     }
/*     */     
/*     */     public byte setValue(byte value) {
/*  95 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 100 */       if (!(o instanceof Map.Entry))
/* 101 */         return false; 
/* 102 */       if (o instanceof Byte2ByteMap.Entry) {
/* 103 */         Byte2ByteMap.Entry entry = (Byte2ByteMap.Entry)o;
/* 104 */         return (this.key == entry.getByteKey() && this.value == entry.getByteValue());
/*     */       } 
/* 106 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 107 */       Object key = e.getKey();
/* 108 */       if (key == null || !(key instanceof Byte))
/* 109 */         return false; 
/* 110 */       Object value = e.getValue();
/* 111 */       if (value == null || !(value instanceof Byte))
/* 112 */         return false; 
/* 113 */       return (this.key == ((Byte)key).byteValue() && this.value == ((Byte)value).byteValue());
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
/*     */     extends AbstractObjectSet<Byte2ByteMap.Entry>
/*     */   {
/*     */     protected final Byte2ByteMap map;
/*     */     
/*     */     public BasicEntrySet(Byte2ByteMap map) {
/* 131 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 136 */       if (!(o instanceof Map.Entry))
/* 137 */         return false; 
/* 138 */       if (o instanceof Byte2ByteMap.Entry) {
/* 139 */         Byte2ByteMap.Entry entry = (Byte2ByteMap.Entry)o;
/* 140 */         byte b = entry.getByteKey();
/* 141 */         return (this.map.containsKey(b) && this.map.get(b) == entry.getByteValue());
/*     */       } 
/* 143 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 144 */       Object key = e.getKey();
/* 145 */       if (key == null || !(key instanceof Byte))
/* 146 */         return false; 
/* 147 */       byte k = ((Byte)key).byteValue();
/* 148 */       Object value = e.getValue();
/* 149 */       if (value == null || !(value instanceof Byte))
/* 150 */         return false; 
/* 151 */       return (this.map.containsKey(k) && this.map.get(k) == ((Byte)value).byteValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 156 */       if (!(o instanceof Map.Entry))
/* 157 */         return false; 
/* 158 */       if (o instanceof Byte2ByteMap.Entry) {
/* 159 */         Byte2ByteMap.Entry entry = (Byte2ByteMap.Entry)o;
/* 160 */         return this.map.remove(entry.getByteKey(), entry.getByteValue());
/*     */       } 
/* 162 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 163 */       Object key = e.getKey();
/* 164 */       if (key == null || !(key instanceof Byte))
/* 165 */         return false; 
/* 166 */       byte k = ((Byte)key).byteValue();
/* 167 */       Object value = e.getValue();
/* 168 */       if (value == null || !(value instanceof Byte))
/* 169 */         return false; 
/* 170 */       byte v = ((Byte)value).byteValue();
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
/*     */   public ByteSet keySet() {
/* 194 */     return new AbstractByteSet()
/*     */       {
/*     */         public boolean contains(byte k) {
/* 197 */           return AbstractByte2ByteMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 201 */           return AbstractByte2ByteMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 205 */           AbstractByte2ByteMap.this.clear();
/*     */         }
/*     */         
/*     */         public ByteIterator iterator() {
/* 209 */           return new ByteIterator()
/*     */             {
/* 211 */               private final ObjectIterator<Byte2ByteMap.Entry> i = Byte2ByteMaps.fastIterator(AbstractByte2ByteMap.this);
/*     */               
/*     */               public byte nextByte() {
/* 214 */                 return ((Byte2ByteMap.Entry)this.i.next()).getByteKey();
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
/*     */   public ByteCollection values() {
/* 244 */     return new AbstractByteCollection()
/*     */       {
/*     */         public boolean contains(byte k) {
/* 247 */           return AbstractByte2ByteMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 251 */           return AbstractByte2ByteMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 255 */           AbstractByte2ByteMap.this.clear();
/*     */         }
/*     */         
/*     */         public ByteIterator iterator() {
/* 259 */           return new ByteIterator()
/*     */             {
/* 261 */               private final ObjectIterator<Byte2ByteMap.Entry> i = Byte2ByteMaps.fastIterator(AbstractByte2ByteMap.this);
/*     */               
/*     */               public byte nextByte() {
/* 264 */                 return ((Byte2ByteMap.Entry)this.i.next()).getByteValue();
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
/*     */   public void putAll(Map<? extends Byte, ? extends Byte> m) {
/* 278 */     if (m instanceof Byte2ByteMap) {
/* 279 */       ObjectIterator<Byte2ByteMap.Entry> i = Byte2ByteMaps.fastIterator((Byte2ByteMap)m);
/* 280 */       while (i.hasNext()) {
/* 281 */         Byte2ByteMap.Entry e = (Byte2ByteMap.Entry)i.next();
/* 282 */         put(e.getByteKey(), e.getByteValue());
/*     */       } 
/*     */     } else {
/* 285 */       int n = m.size();
/* 286 */       Iterator<? extends Map.Entry<? extends Byte, ? extends Byte>> i = m.entrySet().iterator();
/*     */       
/* 288 */       while (n-- != 0) {
/* 289 */         Map.Entry<? extends Byte, ? extends Byte> e = i.next();
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
/* 304 */     ObjectIterator<Byte2ByteMap.Entry> i = Byte2ByteMaps.fastIterator(this);
/* 305 */     while (n-- != 0)
/* 306 */       h += ((Byte2ByteMap.Entry)i.next()).hashCode(); 
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
/* 318 */     return byte2ByteEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 322 */     StringBuilder s = new StringBuilder();
/* 323 */     ObjectIterator<Byte2ByteMap.Entry> i = Byte2ByteMaps.fastIterator(this);
/* 324 */     int n = size();
/*     */     
/* 326 */     boolean first = true;
/* 327 */     s.append("{");
/* 328 */     while (n-- != 0) {
/* 329 */       if (first) {
/* 330 */         first = false;
/*     */       } else {
/* 332 */         s.append(", ");
/* 333 */       }  Byte2ByteMap.Entry e = (Byte2ByteMap.Entry)i.next();
/* 334 */       s.append(String.valueOf(e.getByteKey()));
/* 335 */       s.append("=>");
/* 336 */       s.append(String.valueOf(e.getByteValue()));
/*     */     } 
/* 338 */     s.append("}");
/* 339 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\AbstractByte2ByteMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */