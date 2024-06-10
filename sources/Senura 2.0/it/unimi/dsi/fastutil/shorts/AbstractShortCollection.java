/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public abstract class AbstractShortCollection
/*     */   extends AbstractCollection<Short>
/*     */   implements ShortCollection
/*     */ {
/*     */   public boolean add(short k) {
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
/*     */   public boolean contains(short k) {
/*  58 */     ShortIterator iterator = iterator();
/*  59 */     while (iterator.hasNext()) {
/*  60 */       if (k == iterator.nextShort())
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
/*     */   public boolean rem(short k) {
/*  73 */     ShortIterator iterator = iterator();
/*  74 */     while (iterator.hasNext()) {
/*  75 */       if (k == iterator.nextShort()) {
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
/*     */   public boolean add(Short key) {
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
/*     */   public short[] toArray(short[] a) {
/* 114 */     if (a == null || a.length < size())
/* 115 */       a = new short[size()]; 
/* 116 */     ShortIterators.unwrap(iterator(), a);
/* 117 */     return a;
/*     */   }
/*     */   
/*     */   public short[] toShortArray() {
/* 121 */     return toArray((short[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public short[] toShortArray(short[] a) {
/* 132 */     return toArray(a);
/*     */   }
/*     */   
/*     */   public boolean addAll(ShortCollection c) {
/* 136 */     boolean retVal = false;
/* 137 */     for (ShortIterator i = c.iterator(); i.hasNext();) {
/* 138 */       if (add(i.nextShort()))
/* 139 */         retVal = true; 
/* 140 */     }  return retVal;
/*     */   }
/*     */   
/*     */   public boolean containsAll(ShortCollection c) {
/* 144 */     for (ShortIterator i = c.iterator(); i.hasNext();) {
/* 145 */       if (!contains(i.nextShort()))
/* 146 */         return false; 
/* 147 */     }  return true;
/*     */   }
/*     */   
/*     */   public boolean removeAll(ShortCollection c) {
/* 151 */     boolean retVal = false;
/* 152 */     for (ShortIterator i = c.iterator(); i.hasNext();) {
/* 153 */       if (rem(i.nextShort()))
/* 154 */         retVal = true; 
/* 155 */     }  return retVal;
/*     */   }
/*     */   
/*     */   public boolean retainAll(ShortCollection c) {
/* 159 */     boolean retVal = false;
/* 160 */     for (ShortIterator i = iterator(); i.hasNext();) {
/* 161 */       if (!c.contains(i.nextShort())) {
/* 162 */         i.remove();
/* 163 */         retVal = true;
/*     */       } 
/* 165 */     }  return retVal;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 169 */     StringBuilder s = new StringBuilder();
/* 170 */     ShortIterator i = iterator();
/* 171 */     int n = size();
/*     */     
/* 173 */     boolean first = true;
/* 174 */     s.append("{");
/* 175 */     while (n-- != 0) {
/* 176 */       if (first) {
/* 177 */         first = false;
/*     */       } else {
/* 179 */         s.append(", ");
/* 180 */       }  short k = i.nextShort();
/* 181 */       s.append(String.valueOf(k));
/*     */     } 
/* 183 */     s.append("}");
/* 184 */     return s.toString();
/*     */   }
/*     */   
/*     */   public abstract ShortIterator iterator();
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\AbstractShortCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */