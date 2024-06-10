/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DoubleArraySet
/*     */   extends AbstractDoubleSet
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient double[] a;
/*     */   private int size;
/*     */   
/*     */   public DoubleArraySet(double[] a) {
/*  45 */     this.a = a;
/*  46 */     this.size = a.length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleArraySet() {
/*  52 */     this.a = DoubleArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleArraySet(int capacity) {
/*  61 */     this.a = new double[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleArraySet(DoubleCollection c) {
/*  70 */     this(c.size());
/*  71 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleArraySet(Collection<? extends Double> c) {
/*  80 */     this(c.size());
/*  81 */     addAll(c);
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
/*     */   public DoubleArraySet(double[] a, int size) {
/*  97 */     this.a = a;
/*  98 */     this.size = size;
/*  99 */     if (size > a.length)
/* 100 */       throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the array size (" + a.length + ")"); 
/*     */   }
/*     */   
/*     */   private int findKey(double o) {
/* 104 */     for (int i = this.size; i-- != 0;) {
/* 105 */       if (Double.doubleToLongBits(this.a[i]) == Double.doubleToLongBits(o))
/* 106 */         return i; 
/* 107 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public DoubleIterator iterator() {
/* 112 */     return new DoubleIterator() {
/* 113 */         int next = 0;
/*     */         
/*     */         public boolean hasNext() {
/* 116 */           return (this.next < DoubleArraySet.this.size);
/*     */         }
/*     */         
/*     */         public double nextDouble() {
/* 120 */           if (!hasNext())
/* 121 */             throw new NoSuchElementException(); 
/* 122 */           return DoubleArraySet.this.a[this.next++];
/*     */         }
/*     */         
/*     */         public void remove() {
/* 126 */           int tail = DoubleArraySet.this.size-- - this.next--;
/* 127 */           System.arraycopy(DoubleArraySet.this.a, this.next + 1, DoubleArraySet.this.a, this.next, tail);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public boolean contains(double k) {
/* 133 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public int size() {
/* 137 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean remove(double k) {
/* 141 */     int pos = findKey(k);
/* 142 */     if (pos == -1)
/* 143 */       return false; 
/* 144 */     int tail = this.size - pos - 1;
/* 145 */     for (int i = 0; i < tail; i++)
/* 146 */       this.a[pos + i] = this.a[pos + i + 1]; 
/* 147 */     this.size--;
/* 148 */     return true;
/*     */   }
/*     */   
/*     */   public boolean add(double k) {
/* 152 */     int pos = findKey(k);
/* 153 */     if (pos != -1)
/* 154 */       return false; 
/* 155 */     if (this.size == this.a.length) {
/* 156 */       double[] b = new double[(this.size == 0) ? 2 : (this.size * 2)];
/* 157 */       for (int i = this.size; i-- != 0;)
/* 158 */         b[i] = this.a[i]; 
/* 159 */       this.a = b;
/*     */     } 
/* 161 */     this.a[this.size++] = k;
/* 162 */     return true;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 166 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 170 */     return (this.size == 0);
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
/*     */   public DoubleArraySet clone() {
/*     */     DoubleArraySet c;
/*     */     try {
/* 187 */       c = (DoubleArraySet)super.clone();
/* 188 */     } catch (CloneNotSupportedException cantHappen) {
/* 189 */       throw new InternalError();
/*     */     } 
/* 191 */     c.a = (double[])this.a.clone();
/* 192 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 195 */     s.defaultWriteObject();
/* 196 */     for (int i = 0; i < this.size; i++)
/* 197 */       s.writeDouble(this.a[i]); 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 200 */     s.defaultReadObject();
/* 201 */     this.a = new double[this.size];
/* 202 */     for (int i = 0; i < this.size; i++)
/* 203 */       this.a[i] = s.readDouble(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleArraySet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */