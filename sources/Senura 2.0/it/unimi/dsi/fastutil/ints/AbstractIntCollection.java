/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ public abstract class AbstractIntCollection
/*     */   extends AbstractCollection<Integer>
/*     */   implements IntCollection
/*     */ {
/*     */   public boolean add(int k) {
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
/*     */   public boolean contains(int k) {
/*  58 */     IntIterator iterator = iterator();
/*  59 */     while (iterator.hasNext()) {
/*  60 */       if (k == iterator.nextInt())
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
/*     */   public boolean rem(int k) {
/*  73 */     IntIterator iterator = iterator();
/*  74 */     while (iterator.hasNext()) {
/*  75 */       if (k == iterator.nextInt()) {
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
/*     */   public boolean add(Integer key) {
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
/*     */   public int[] toArray(int[] a) {
/* 114 */     if (a == null || a.length < size())
/* 115 */       a = new int[size()]; 
/* 116 */     IntIterators.unwrap(iterator(), a);
/* 117 */     return a;
/*     */   }
/*     */   
/*     */   public int[] toIntArray() {
/* 121 */     return toArray((int[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int[] toIntArray(int[] a) {
/* 132 */     return toArray(a);
/*     */   }
/*     */   
/*     */   public boolean addAll(IntCollection c) {
/* 136 */     boolean retVal = false;
/* 137 */     for (IntIterator i = c.iterator(); i.hasNext();) {
/* 138 */       if (add(i.nextInt()))
/* 139 */         retVal = true; 
/* 140 */     }  return retVal;
/*     */   }
/*     */   
/*     */   public boolean containsAll(IntCollection c) {
/* 144 */     for (IntIterator i = c.iterator(); i.hasNext();) {
/* 145 */       if (!contains(i.nextInt()))
/* 146 */         return false; 
/* 147 */     }  return true;
/*     */   }
/*     */   
/*     */   public boolean removeAll(IntCollection c) {
/* 151 */     boolean retVal = false;
/* 152 */     for (IntIterator i = c.iterator(); i.hasNext();) {
/* 153 */       if (rem(i.nextInt()))
/* 154 */         retVal = true; 
/* 155 */     }  return retVal;
/*     */   }
/*     */   
/*     */   public boolean retainAll(IntCollection c) {
/* 159 */     boolean retVal = false;
/* 160 */     for (IntIterator i = iterator(); i.hasNext();) {
/* 161 */       if (!c.contains(i.nextInt())) {
/* 162 */         i.remove();
/* 163 */         retVal = true;
/*     */       } 
/* 165 */     }  return retVal;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 169 */     StringBuilder s = new StringBuilder();
/* 170 */     IntIterator i = iterator();
/* 171 */     int n = size();
/*     */     
/* 173 */     boolean first = true;
/* 174 */     s.append("{");
/* 175 */     while (n-- != 0) {
/* 176 */       if (first) {
/* 177 */         first = false;
/*     */       } else {
/* 179 */         s.append(", ");
/* 180 */       }  int k = i.nextInt();
/* 181 */       s.append(String.valueOf(k));
/*     */     } 
/* 183 */     s.append("}");
/* 184 */     return s.toString();
/*     */   }
/*     */   
/*     */   public abstract IntIterator iterator();
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\AbstractIntCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */