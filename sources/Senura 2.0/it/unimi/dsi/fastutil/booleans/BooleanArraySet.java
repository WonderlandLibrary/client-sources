/*     */ package it.unimi.dsi.fastutil.booleans;
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
/*     */ public class BooleanArraySet
/*     */   extends AbstractBooleanSet
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient boolean[] a;
/*     */   private int size;
/*     */   
/*     */   public BooleanArraySet(boolean[] a) {
/*  45 */     this.a = a;
/*  46 */     this.size = a.length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanArraySet() {
/*  52 */     this.a = BooleanArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanArraySet(int capacity) {
/*  61 */     this.a = new boolean[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanArraySet(BooleanCollection c) {
/*  70 */     this(c.size());
/*  71 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanArraySet(Collection<? extends Boolean> c) {
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
/*     */   public BooleanArraySet(boolean[] a, int size) {
/*  97 */     this.a = a;
/*  98 */     this.size = size;
/*  99 */     if (size > a.length)
/* 100 */       throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the array size (" + a.length + ")"); 
/*     */   }
/*     */   
/*     */   private int findKey(boolean o) {
/* 104 */     for (int i = this.size; i-- != 0;) {
/* 105 */       if (this.a[i] == o)
/* 106 */         return i; 
/* 107 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public BooleanIterator iterator() {
/* 112 */     return new BooleanIterator() {
/* 113 */         int next = 0;
/*     */         
/*     */         public boolean hasNext() {
/* 116 */           return (this.next < BooleanArraySet.this.size);
/*     */         }
/*     */         
/*     */         public boolean nextBoolean() {
/* 120 */           if (!hasNext())
/* 121 */             throw new NoSuchElementException(); 
/* 122 */           return BooleanArraySet.this.a[this.next++];
/*     */         }
/*     */         
/*     */         public void remove() {
/* 126 */           int tail = BooleanArraySet.this.size-- - this.next--;
/* 127 */           System.arraycopy(BooleanArraySet.this.a, this.next + 1, BooleanArraySet.this.a, this.next, tail);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public boolean contains(boolean k) {
/* 133 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public int size() {
/* 137 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean remove(boolean k) {
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
/*     */   public boolean add(boolean k) {
/* 152 */     int pos = findKey(k);
/* 153 */     if (pos != -1)
/* 154 */       return false; 
/* 155 */     if (this.size == this.a.length) {
/* 156 */       boolean[] b = new boolean[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public BooleanArraySet clone() {
/*     */     BooleanArraySet c;
/*     */     try {
/* 187 */       c = (BooleanArraySet)super.clone();
/* 188 */     } catch (CloneNotSupportedException cantHappen) {
/* 189 */       throw new InternalError();
/*     */     } 
/* 191 */     c.a = (boolean[])this.a.clone();
/* 192 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 195 */     s.defaultWriteObject();
/* 196 */     for (int i = 0; i < this.size; i++)
/* 197 */       s.writeBoolean(this.a[i]); 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 200 */     s.defaultReadObject();
/* 201 */     this.a = new boolean[this.size];
/* 202 */     for (int i = 0; i < this.size; i++)
/* 203 */       this.a[i] = s.readBoolean(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\booleans\BooleanArraySet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */