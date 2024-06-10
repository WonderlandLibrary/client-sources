/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractByteCollection
/*     */   extends AbstractCollection<Byte>
/*     */   implements ByteCollection
/*     */ {
/*     */   public boolean add(byte k) {
/*  47 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(byte k) {
/*  58 */     ByteIterator iterator = iterator();
/*  59 */     while (iterator.hasNext()) {
/*  60 */       if (k == iterator.nextByte())
/*  61 */         return true; 
/*  62 */     }  return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean rem(byte k) {
/*  73 */     ByteIterator iterator = iterator();
/*  74 */     while (iterator.hasNext()) {
/*  75 */       if (k == iterator.nextByte()) {
/*  76 */         iterator.remove();
/*  77 */         return true;
/*     */       } 
/*  79 */     }  return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean add(Byte key) {
/*  90 */     return super.add(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean contains(Object key) {
/* 100 */     return super.contains(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean remove(Object key) {
/* 110 */     return super.remove(key);
/*     */   }
/*     */   
/*     */   public byte[] toArray(byte[] a) {
/* 114 */     if (a == null || a.length < size())
/* 115 */       a = new byte[size()]; 
/* 116 */     ByteIterators.unwrap(iterator(), a);
/* 117 */     return a;
/*     */   }
/*     */   
/*     */   public byte[] toByteArray() {
/* 121 */     return toArray((byte[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public byte[] toByteArray(byte[] a) {
/* 132 */     return toArray(a);
/*     */   }
/*     */   
/*     */   public boolean addAll(ByteCollection c) {
/* 136 */     boolean retVal = false;
/* 137 */     for (ByteIterator i = c.iterator(); i.hasNext();) {
/* 138 */       if (add(i.nextByte()))
/* 139 */         retVal = true; 
/* 140 */     }  return retVal;
/*     */   }
/*     */   
/*     */   public boolean containsAll(ByteCollection c) {
/* 144 */     for (ByteIterator i = c.iterator(); i.hasNext();) {
/* 145 */       if (!contains(i.nextByte()))
/* 146 */         return false; 
/* 147 */     }  return true;
/*     */   }
/*     */   
/*     */   public boolean removeAll(ByteCollection c) {
/* 151 */     boolean retVal = false;
/* 152 */     for (ByteIterator i = c.iterator(); i.hasNext();) {
/* 153 */       if (rem(i.nextByte()))
/* 154 */         retVal = true; 
/* 155 */     }  return retVal;
/*     */   }
/*     */   
/*     */   public boolean retainAll(ByteCollection c) {
/* 159 */     boolean retVal = false;
/* 160 */     for (ByteIterator i = iterator(); i.hasNext();) {
/* 161 */       if (!c.contains(i.nextByte())) {
/* 162 */         i.remove();
/* 163 */         retVal = true;
/*     */       } 
/* 165 */     }  return retVal;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 169 */     StringBuilder s = new StringBuilder();
/* 170 */     ByteIterator i = iterator();
/* 171 */     int n = size();
/*     */     
/* 173 */     boolean first = true;
/* 174 */     s.append("{");
/* 175 */     while (n-- != 0) {
/* 176 */       if (first) {
/* 177 */         first = false;
/*     */       } else {
/* 179 */         s.append(", ");
/* 180 */       }  byte k = i.nextByte();
/* 181 */       s.append(String.valueOf(k));
/*     */     } 
/* 183 */     s.append("}");
/* 184 */     return s.toString();
/*     */   }
/*     */   
/*     */   public abstract ByteIterator iterator();
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\AbstractByteCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */