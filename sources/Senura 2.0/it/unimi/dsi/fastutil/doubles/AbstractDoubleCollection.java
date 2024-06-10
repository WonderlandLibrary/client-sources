/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ public abstract class AbstractDoubleCollection
/*     */   extends AbstractCollection<Double>
/*     */   implements DoubleCollection
/*     */ {
/*     */   public boolean add(double k) {
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
/*     */   public boolean contains(double k) {
/*  58 */     DoubleIterator iterator = iterator();
/*  59 */     while (iterator.hasNext()) {
/*  60 */       if (k == iterator.nextDouble())
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
/*     */   public boolean rem(double k) {
/*  73 */     DoubleIterator iterator = iterator();
/*  74 */     while (iterator.hasNext()) {
/*  75 */       if (k == iterator.nextDouble()) {
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
/*     */   public boolean add(Double key) {
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
/*     */   public double[] toArray(double[] a) {
/* 114 */     if (a == null || a.length < size())
/* 115 */       a = new double[size()]; 
/* 116 */     DoubleIterators.unwrap(iterator(), a);
/* 117 */     return a;
/*     */   }
/*     */   
/*     */   public double[] toDoubleArray() {
/* 121 */     return toArray((double[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public double[] toDoubleArray(double[] a) {
/* 132 */     return toArray(a);
/*     */   }
/*     */   
/*     */   public boolean addAll(DoubleCollection c) {
/* 136 */     boolean retVal = false;
/* 137 */     for (DoubleIterator i = c.iterator(); i.hasNext();) {
/* 138 */       if (add(i.nextDouble()))
/* 139 */         retVal = true; 
/* 140 */     }  return retVal;
/*     */   }
/*     */   
/*     */   public boolean containsAll(DoubleCollection c) {
/* 144 */     for (DoubleIterator i = c.iterator(); i.hasNext();) {
/* 145 */       if (!contains(i.nextDouble()))
/* 146 */         return false; 
/* 147 */     }  return true;
/*     */   }
/*     */   
/*     */   public boolean removeAll(DoubleCollection c) {
/* 151 */     boolean retVal = false;
/* 152 */     for (DoubleIterator i = c.iterator(); i.hasNext();) {
/* 153 */       if (rem(i.nextDouble()))
/* 154 */         retVal = true; 
/* 155 */     }  return retVal;
/*     */   }
/*     */   
/*     */   public boolean retainAll(DoubleCollection c) {
/* 159 */     boolean retVal = false;
/* 160 */     for (DoubleIterator i = iterator(); i.hasNext();) {
/* 161 */       if (!c.contains(i.nextDouble())) {
/* 162 */         i.remove();
/* 163 */         retVal = true;
/*     */       } 
/* 165 */     }  return retVal;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 169 */     StringBuilder s = new StringBuilder();
/* 170 */     DoubleIterator i = iterator();
/* 171 */     int n = size();
/*     */     
/* 173 */     boolean first = true;
/* 174 */     s.append("{");
/* 175 */     while (n-- != 0) {
/* 176 */       if (first) {
/* 177 */         first = false;
/*     */       } else {
/* 179 */         s.append(", ");
/* 180 */       }  double k = i.nextDouble();
/* 181 */       s.append(String.valueOf(k));
/*     */     } 
/* 183 */     s.append("}");
/* 184 */     return s.toString();
/*     */   }
/*     */   
/*     */   public abstract DoubleIterator iterator();
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\AbstractDoubleCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */