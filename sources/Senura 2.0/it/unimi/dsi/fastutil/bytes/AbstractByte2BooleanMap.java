/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public abstract class AbstractByte2BooleanMap
/*     */   extends AbstractByte2BooleanFunction
/*     */   implements Byte2BooleanMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsValue(boolean v) {
/*  49 */     return values().contains(v);
/*     */   }
/*     */   
/*     */   public boolean containsKey(byte k) {
/*  53 */     ObjectIterator<Byte2BooleanMap.Entry> i = byte2BooleanEntrySet().iterator();
/*  54 */     while (i.hasNext()) {
/*  55 */       if (((Byte2BooleanMap.Entry)i.next()).getByteKey() == k)
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
/*     */     implements Byte2BooleanMap.Entry
/*     */   {
/*     */     protected byte key;
/*     */     
/*     */     protected boolean value;
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Byte key, Boolean value) {
/*  78 */       this.key = key.byteValue();
/*  79 */       this.value = value.booleanValue();
/*     */     }
/*     */     public BasicEntry(byte key, boolean value) {
/*  82 */       this.key = key;
/*  83 */       this.value = value;
/*     */     }
/*     */     
/*     */     public byte getByteKey() {
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
/* 102 */       if (o instanceof Byte2BooleanMap.Entry) {
/* 103 */         Byte2BooleanMap.Entry entry = (Byte2BooleanMap.Entry)o;
/* 104 */         return (this.key == entry.getByteKey() && this.value == entry.getBooleanValue());
/*     */       } 
/* 106 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 107 */       Object key = e.getKey();
/* 108 */       if (key == null || !(key instanceof Byte))
/* 109 */         return false; 
/* 110 */       Object value = e.getValue();
/* 111 */       if (value == null || !(value instanceof Boolean))
/* 112 */         return false; 
/* 113 */       return (this.key == ((Byte)key).byteValue() && this.value == ((Boolean)value)
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
/*     */     extends AbstractObjectSet<Byte2BooleanMap.Entry>
/*     */   {
/*     */     protected final Byte2BooleanMap map;
/*     */     
/*     */     public BasicEntrySet(Byte2BooleanMap map) {
/* 132 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 137 */       if (!(o instanceof Map.Entry))
/* 138 */         return false; 
/* 139 */       if (o instanceof Byte2BooleanMap.Entry) {
/* 140 */         Byte2BooleanMap.Entry entry = (Byte2BooleanMap.Entry)o;
/* 141 */         byte b = entry.getByteKey();
/* 142 */         return (this.map.containsKey(b) && this.map.get(b) == entry.getBooleanValue());
/*     */       } 
/* 144 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 145 */       Object key = e.getKey();
/* 146 */       if (key == null || !(key instanceof Byte))
/* 147 */         return false; 
/* 148 */       byte k = ((Byte)key).byteValue();
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
/* 159 */       if (o instanceof Byte2BooleanMap.Entry) {
/* 160 */         Byte2BooleanMap.Entry entry = (Byte2BooleanMap.Entry)o;
/* 161 */         return this.map.remove(entry.getByteKey(), entry.getBooleanValue());
/*     */       } 
/* 163 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 164 */       Object key = e.getKey();
/* 165 */       if (key == null || !(key instanceof Byte))
/* 166 */         return false; 
/* 167 */       byte k = ((Byte)key).byteValue();
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
/*     */   public ByteSet keySet() {
/* 195 */     return new AbstractByteSet()
/*     */       {
/*     */         public boolean contains(byte k) {
/* 198 */           return AbstractByte2BooleanMap.this.containsKey(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 202 */           return AbstractByte2BooleanMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 206 */           AbstractByte2BooleanMap.this.clear();
/*     */         }
/*     */         
/*     */         public ByteIterator iterator() {
/* 210 */           return new ByteIterator()
/*     */             {
/* 212 */               private final ObjectIterator<Byte2BooleanMap.Entry> i = Byte2BooleanMaps.fastIterator(AbstractByte2BooleanMap.this);
/*     */               
/*     */               public byte nextByte() {
/* 215 */                 return ((Byte2BooleanMap.Entry)this.i.next()).getByteKey();
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
/* 248 */           return AbstractByte2BooleanMap.this.containsValue(k);
/*     */         }
/*     */         
/*     */         public int size() {
/* 252 */           return AbstractByte2BooleanMap.this.size();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 256 */           AbstractByte2BooleanMap.this.clear();
/*     */         }
/*     */         
/*     */         public BooleanIterator iterator() {
/* 260 */           return new BooleanIterator()
/*     */             {
/* 262 */               private final ObjectIterator<Byte2BooleanMap.Entry> i = Byte2BooleanMaps.fastIterator(AbstractByte2BooleanMap.this);
/*     */               
/*     */               public boolean nextBoolean() {
/* 265 */                 return ((Byte2BooleanMap.Entry)this.i.next()).getBooleanValue();
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
/*     */   public void putAll(Map<? extends Byte, ? extends Boolean> m) {
/* 279 */     if (m instanceof Byte2BooleanMap) {
/* 280 */       ObjectIterator<Byte2BooleanMap.Entry> i = Byte2BooleanMaps.fastIterator((Byte2BooleanMap)m);
/* 281 */       while (i.hasNext()) {
/* 282 */         Byte2BooleanMap.Entry e = (Byte2BooleanMap.Entry)i.next();
/* 283 */         put(e.getByteKey(), e.getBooleanValue());
/*     */       } 
/*     */     } else {
/* 286 */       int n = m.size();
/* 287 */       Iterator<? extends Map.Entry<? extends Byte, ? extends Boolean>> i = m.entrySet().iterator();
/*     */       
/* 289 */       while (n-- != 0) {
/* 290 */         Map.Entry<? extends Byte, ? extends Boolean> e = i.next();
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
/* 305 */     ObjectIterator<Byte2BooleanMap.Entry> i = Byte2BooleanMaps.fastIterator(this);
/* 306 */     while (n-- != 0)
/* 307 */       h += ((Byte2BooleanMap.Entry)i.next()).hashCode(); 
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
/* 319 */     return byte2BooleanEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 323 */     StringBuilder s = new StringBuilder();
/* 324 */     ObjectIterator<Byte2BooleanMap.Entry> i = Byte2BooleanMaps.fastIterator(this);
/* 325 */     int n = size();
/*     */     
/* 327 */     boolean first = true;
/* 328 */     s.append("{");
/* 329 */     while (n-- != 0) {
/* 330 */       if (first) {
/* 331 */         first = false;
/*     */       } else {
/* 333 */         s.append(", ");
/* 334 */       }  Byte2BooleanMap.Entry e = (Byte2BooleanMap.Entry)i.next();
/* 335 */       s.append(String.valueOf(e.getByteKey()));
/* 336 */       s.append("=>");
/* 337 */       s.append(String.valueOf(e.getBooleanValue()));
/*     */     } 
/* 339 */     s.append("}");
/* 340 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\AbstractByte2BooleanMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */