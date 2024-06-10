/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public abstract class AbstractCharCollection
/*     */   extends AbstractCollection<Character>
/*     */   implements CharCollection
/*     */ {
/*     */   public boolean add(char k) {
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
/*     */   public boolean contains(char k) {
/*  58 */     CharIterator iterator = iterator();
/*  59 */     while (iterator.hasNext()) {
/*  60 */       if (k == iterator.nextChar())
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
/*     */   public boolean rem(char k) {
/*  73 */     CharIterator iterator = iterator();
/*  74 */     while (iterator.hasNext()) {
/*  75 */       if (k == iterator.nextChar()) {
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
/*     */   public boolean add(Character key) {
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
/*     */   public char[] toArray(char[] a) {
/* 114 */     if (a == null || a.length < size())
/* 115 */       a = new char[size()]; 
/* 116 */     CharIterators.unwrap(iterator(), a);
/* 117 */     return a;
/*     */   }
/*     */   
/*     */   public char[] toCharArray() {
/* 121 */     return toArray((char[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public char[] toCharArray(char[] a) {
/* 132 */     return toArray(a);
/*     */   }
/*     */   
/*     */   public boolean addAll(CharCollection c) {
/* 136 */     boolean retVal = false;
/* 137 */     for (CharIterator i = c.iterator(); i.hasNext();) {
/* 138 */       if (add(i.nextChar()))
/* 139 */         retVal = true; 
/* 140 */     }  return retVal;
/*     */   }
/*     */   
/*     */   public boolean containsAll(CharCollection c) {
/* 144 */     for (CharIterator i = c.iterator(); i.hasNext();) {
/* 145 */       if (!contains(i.nextChar()))
/* 146 */         return false; 
/* 147 */     }  return true;
/*     */   }
/*     */   
/*     */   public boolean removeAll(CharCollection c) {
/* 151 */     boolean retVal = false;
/* 152 */     for (CharIterator i = c.iterator(); i.hasNext();) {
/* 153 */       if (rem(i.nextChar()))
/* 154 */         retVal = true; 
/* 155 */     }  return retVal;
/*     */   }
/*     */   
/*     */   public boolean retainAll(CharCollection c) {
/* 159 */     boolean retVal = false;
/* 160 */     for (CharIterator i = iterator(); i.hasNext();) {
/* 161 */       if (!c.contains(i.nextChar())) {
/* 162 */         i.remove();
/* 163 */         retVal = true;
/*     */       } 
/* 165 */     }  return retVal;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 169 */     StringBuilder s = new StringBuilder();
/* 170 */     CharIterator i = iterator();
/* 171 */     int n = size();
/*     */     
/* 173 */     boolean first = true;
/* 174 */     s.append("{");
/* 175 */     while (n-- != 0) {
/* 176 */       if (first) {
/* 177 */         first = false;
/*     */       } else {
/* 179 */         s.append(", ");
/* 180 */       }  char k = i.nextChar();
/* 181 */       s.append(String.valueOf(k));
/*     */     } 
/* 183 */     s.append("}");
/* 184 */     return s.toString();
/*     */   }
/*     */   
/*     */   public abstract CharIterator iterator();
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\AbstractCharCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */