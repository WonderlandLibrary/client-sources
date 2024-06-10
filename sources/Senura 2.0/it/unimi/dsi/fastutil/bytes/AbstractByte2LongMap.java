/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongIterator;
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
/*     */ public abstract class AbstractByte2LongMap
/*     */   extends AbstractByte2LongFunction
/*     */   implements Byte2LongMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(long v) {
/*  49 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(byte k) {
/*  53 */     ObjectIterator<Byte2LongMap.Entry> i = byte2LongEntrySet().iterator();
/*  54 */     while (i.hasNext()) {
/*  55 */       if (((Byte2LongMap.Entry)i.next()).getByteKey() == k)
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
/*     */     implements Byte2LongMap.Entry
/*     */   {
/*     */     protected byte key;
/*     */     
/*     */     protected long value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Byte key, Long value) {
/*  78 */       this.key = key.byteValue();
/*  79 */       this.value = value.longValue();
/*     */     }
/*     */     public BasicEntry(byte key, long value) {
/*  82 */       this.key = key;
/*  83 */       this.value = value;
/*     */     }
/*     */     
/*     */     public byte getByteKey() {
/*  87 */       return this.key;
/*     */     }
/*     */     
/*     */     public long getLongValue() {
/*  91 */       return this.value;
/*     */     }
/*     */     
/*     */     public long setValue(long value) {
/*  95 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 100 */       if (!(o instanceof Map.Entry))
/* 101 */         return false; 
/* 102 */       if (o instanceof Byte2LongMap.Entry) {
/* 103 */         Byte2LongMap.Entry entry = (Byte2LongMap.Entry)o;
/* 104 */         return (this.key == entry.getByteKey() && this.value == entry.getLongValue());
/*     */       } 
/* 106 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 107 */       Object key = e.getKey();
/* 108 */       if (key == null || !(key instanceof Byte))
/* 109 */         return false; 
/* 110 */       Object value = e.getValue();
/* 111 */       if (value == null || !(value instanceof Long))
/* 112 */         return false; 
/* 113 */       return (this.key == ((Byte)key).byteValue() && this.value == ((Long)value).longValue());
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 117 */       return this.key ^ HashCommon.long2int(this.value);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 121 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BasicEntrySet
/*     */     extends AbstractObjectSet<Byte2LongMap.Entry>
/*     */   {
/*     */     protected final Byte2LongMap map;
/*     */     
/*     */     public BasicEntrySet(Byte2LongMap map) {
/* 131 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 136 */       if (!(o instanceof Map.Entry))
/* 137 */         return false; 
/* 138 */       if (o instanceof Byte2LongMap.Entry) {
/* 139 */         Byte2LongMap.Entry entry = (Byte2LongMap.Entry)o;
/* 140 */         byte b = entry.getByteKey();
/* 141 */         return (this.map.containsKey(b) && this.map.get(b) == entry.getLongValue());
/*     */       } 
/* 143 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 144 */       Object key = e.getKey();
/* 145 */       if (key == null || !(key instanceof Byte))
/* 146 */         return false; 
/* 147 */       byte k = ((Byte)key).byteValue();
/* 148 */       Object value = e.getValue();
/* 149 */       if (value == null || !(value instanceof Long))
/* 150 */         return false; 
/* 151 */       return (this.map.containsKey(k) && this.map.get(k) == ((Long)value).longValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 156 */       if (!(o instanceof Map.Entry))
/* 157 */         return false; 
/* 158 */       if (o instanceof Byte2LongMap.Entry) {
/* 159 */         Byte2LongMap.Entry entry = (Byte2LongMap.Entry)o;
/* 160 */         return this.map.remove(entry.getByteKey(), entry.getLongValue());
/*     */       } 
/* 162 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 163 */       Object key = e.getKey();
/* 164 */       if (key == null || !(key instanceof Byte))
/* 165 */         return false; 
/* 166 */       byte k = ((Byte)key).byteValue();
/* 167 */       Object value = e.getValue();
/* 168 */       if (value == null || !(value instanceof Long))
/* 169 */         return false; 
/* 170 */       long v = ((Long)value).longValue();
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
/* 197 */           return AbstractByte2LongMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 201 */           return AbstractByte2LongMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 205 */           AbstractByte2LongMap.this.clear();
/*     */         }
/*     */         
/*     */         public ByteIterator iterator() {
/* 209 */           return new ByteIterator()
/*     */             {
/* 211 */               private final ObjectIterator<Byte2LongMap.Entry> i = Byte2LongMaps.fastIterator(AbstractByte2LongMap.this);
/*     */               
/*     */               public byte nextByte() {
/* 214 */                 return ((Byte2LongMap.Entry)this.i.next()).getByteKey();
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
/*     */   public LongCollection values() {
/* 244 */     return (LongCollection)new AbstractLongCollection()
/*     */       {
/*     */         public boolean contains(long k) {
/* 247 */           return AbstractByte2LongMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 251 */           return AbstractByte2LongMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 255 */           AbstractByte2LongMap.this.clear();
/*     */         }
/*     */         
/*     */         public LongIterator iterator() {
/* 259 */           return new LongIterator()
/*     */             {
/* 261 */               private final ObjectIterator<Byte2LongMap.Entry> i = Byte2LongMaps.fastIterator(AbstractByte2LongMap.this);
/*     */               
/*     */               public long nextLong() {
/* 264 */                 return ((Byte2LongMap.Entry)this.i.next()).getLongValue();
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
/*     */   public void putAll(Map<? extends Byte, ? extends Long> m) {
/* 278 */     if (m instanceof Byte2LongMap) {
/* 279 */       ObjectIterator<Byte2LongMap.Entry> i = Byte2LongMaps.fastIterator((Byte2LongMap)m);
/* 280 */       while (i.hasNext()) {
/* 281 */         Byte2LongMap.Entry e = (Byte2LongMap.Entry)i.next();
/* 282 */         put(e.getByteKey(), e.getLongValue());
/*     */       } 
/*     */     } else {
/* 285 */       int n = m.size();
/* 286 */       Iterator<? extends Map.Entry<? extends Byte, ? extends Long>> i = m.entrySet().iterator();
/*     */       
/* 288 */       while (n-- != 0) {
/* 289 */         Map.Entry<? extends Byte, ? extends Long> e = i.next();
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
/* 304 */     ObjectIterator<Byte2LongMap.Entry> i = Byte2LongMaps.fastIterator(this);
/* 305 */     while (n-- != 0)
/* 306 */       h += ((Byte2LongMap.Entry)i.next()).hashCode(); 
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
/* 318 */     return byte2LongEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 322 */     StringBuilder s = new StringBuilder();
/* 323 */     ObjectIterator<Byte2LongMap.Entry> i = Byte2LongMaps.fastIterator(this);
/* 324 */     int n = size();
/*     */     
/* 326 */     boolean first = true;
/* 327 */     s.append("{");
/* 328 */     while (n-- != 0) {
/* 329 */       if (first) {
/* 330 */         first = false;
/*     */       } else {
/* 332 */         s.append(", ");
/* 333 */       }  Byte2LongMap.Entry e = (Byte2LongMap.Entry)i.next();
/* 334 */       s.append(String.valueOf(e.getByteKey()));
/* 335 */       s.append("=>");
/* 336 */       s.append(String.valueOf(e.getLongValue()));
/*     */     } 
/* 338 */     s.append("}");
/* 339 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\AbstractByte2LongMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */