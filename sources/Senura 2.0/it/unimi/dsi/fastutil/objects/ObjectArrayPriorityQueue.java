/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.PriorityQueue;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
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
/*     */ public class ObjectArrayPriorityQueue<K>
/*     */   implements PriorityQueue<K>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  37 */   protected transient K[] array = (K[])ObjectArrays.EMPTY_ARRAY;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int size;
/*     */ 
/*     */ 
/*     */   
/*     */   protected Comparator<? super K> c;
/*     */ 
/*     */ 
/*     */   
/*     */   protected transient int firstIndex;
/*     */ 
/*     */ 
/*     */   
/*     */   protected transient boolean firstIndexValid;
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayPriorityQueue(int capacity, Comparator<? super K> c) {
/*  58 */     if (capacity > 0)
/*  59 */       this.array = (K[])new Object[capacity]; 
/*  60 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayPriorityQueue(int capacity) {
/*  69 */     this(capacity, (Comparator<? super K>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayPriorityQueue(Comparator<? super K> c) {
/*  79 */     this(0, c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayPriorityQueue() {
/*  85 */     this(0, (Comparator<? super K>)null);
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
/*     */   public ObjectArrayPriorityQueue(K[] a, int size, Comparator<? super K> c) {
/* 102 */     this(c);
/* 103 */     this.array = a;
/* 104 */     this.size = size;
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
/*     */   public ObjectArrayPriorityQueue(K[] a, Comparator<? super K> c) {
/* 119 */     this(a, a.length, c);
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
/*     */   public ObjectArrayPriorityQueue(K[] a, int size) {
/* 133 */     this(a, size, null);
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
/*     */   public ObjectArrayPriorityQueue(K[] a) {
/* 145 */     this(a, a.length);
/*     */   }
/*     */ 
/*     */   
/*     */   private int findFirst() {
/* 150 */     if (this.firstIndexValid)
/* 151 */       return this.firstIndex; 
/* 152 */     this.firstIndexValid = true;
/* 153 */     int i = this.size;
/* 154 */     int firstIndex = --i;
/* 155 */     K first = this.array[firstIndex];
/* 156 */     if (this.c == null)
/* 157 */     { while (i-- != 0) {
/* 158 */         if (((Comparable<K>)this.array[i]).compareTo(first) < 0)
/* 159 */           first = this.array[firstIndex = i]; 
/*     */       }  }
/* 161 */     else { while (i-- != 0) {
/* 162 */         if (this.c.compare(this.array[i], first) < 0)
/* 163 */           first = this.array[firstIndex = i]; 
/*     */       }  }
/* 165 */      return this.firstIndex = firstIndex;
/*     */   }
/*     */   private void ensureNonEmpty() {
/* 168 */     if (this.size == 0) {
/* 169 */       throw new NoSuchElementException();
/*     */     }
/*     */   }
/*     */   
/*     */   public void enqueue(K x) {
/* 174 */     if (this.size == this.array.length)
/* 175 */       this.array = ObjectArrays.grow(this.array, this.size + 1); 
/* 176 */     if (this.firstIndexValid)
/* 177 */     { if (this.c == null) {
/* 178 */         if (((Comparable<K>)x).compareTo(this.array[this.firstIndex]) < 0)
/* 179 */           this.firstIndex = this.size; 
/* 180 */       } else if (this.c.compare(x, this.array[this.firstIndex]) < 0) {
/* 181 */         this.firstIndex = this.size;
/*     */       }  }
/* 183 */     else { this.firstIndexValid = false; }
/* 184 */      this.array[this.size++] = x;
/*     */   }
/*     */   
/*     */   public K dequeue() {
/* 188 */     ensureNonEmpty();
/* 189 */     int first = findFirst();
/* 190 */     K result = this.array[first];
/* 191 */     System.arraycopy(this.array, first + 1, this.array, first, --this.size - first);
/* 192 */     this.array[this.size] = null;
/* 193 */     this.firstIndexValid = false;
/* 194 */     return result;
/*     */   }
/*     */   
/*     */   public K first() {
/* 198 */     ensureNonEmpty();
/* 199 */     return this.array[findFirst()];
/*     */   }
/*     */   
/*     */   public void changed() {
/* 203 */     ensureNonEmpty();
/* 204 */     this.firstIndexValid = false;
/*     */   }
/*     */   
/*     */   public int size() {
/* 208 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 212 */     Arrays.fill((Object[])this.array, 0, this.size, (Object)null);
/* 213 */     this.size = 0;
/* 214 */     this.firstIndexValid = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void trim() {
/* 220 */     this.array = ObjectArrays.trim(this.array, this.size);
/*     */   }
/*     */   
/*     */   public Comparator<? super K> comparator() {
/* 224 */     return this.c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 227 */     s.defaultWriteObject();
/* 228 */     s.writeInt(this.array.length);
/* 229 */     for (int i = 0; i < this.size; i++)
/* 230 */       s.writeObject(this.array[i]); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 234 */     s.defaultReadObject();
/* 235 */     this.array = (K[])new Object[s.readInt()];
/* 236 */     for (int i = 0; i < this.size; i++)
/* 237 */       this.array[i] = (K)s.readObject(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectArrayPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */