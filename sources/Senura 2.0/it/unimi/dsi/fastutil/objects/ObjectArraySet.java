/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectArraySet<K>
/*     */   extends AbstractObjectSet<K>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient Object[] a;
/*     */   private int size;
/*     */   
/*     */   public ObjectArraySet(Object[] a) {
/*  45 */     this.a = a;
/*  46 */     this.size = a.length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArraySet() {
/*  52 */     this.a = ObjectArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArraySet(int capacity) {
/*  61 */     this.a = new Object[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArraySet(ObjectCollection<K> c) {
/*  70 */     this(c.size());
/*  71 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArraySet(Collection<? extends K> c) {
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
/*     */   public ObjectArraySet(Object[] a, int size) {
/*  97 */     this.a = a;
/*  98 */     this.size = size;
/*  99 */     if (size > a.length)
/* 100 */       throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the array size (" + a.length + ")"); 
/*     */   }
/*     */   
/*     */   private int findKey(Object o) {
/* 104 */     for (int i = this.size; i-- != 0;) {
/* 105 */       if (Objects.equals(this.a[i], o))
/* 106 */         return i; 
/* 107 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectIterator<K> iterator() {
/* 112 */     return new ObjectIterator<K>() {
/* 113 */         int next = 0;
/*     */         
/*     */         public boolean hasNext() {
/* 116 */           return (this.next < ObjectArraySet.this.size);
/*     */         }
/*     */         
/*     */         public K next() {
/* 120 */           if (!hasNext())
/* 121 */             throw new NoSuchElementException(); 
/* 122 */           return (K)ObjectArraySet.this.a[this.next++];
/*     */         }
/*     */         
/*     */         public void remove() {
/* 126 */           int tail = ObjectArraySet.this.size-- - this.next--;
/* 127 */           System.arraycopy(ObjectArraySet.this.a, this.next + 1, ObjectArraySet.this.a, this.next, tail);
/* 128 */           ObjectArraySet.this.a[ObjectArraySet.this.size] = null;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public boolean contains(Object k) {
/* 134 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public int size() {
/* 138 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean remove(Object k) {
/* 142 */     int pos = findKey(k);
/* 143 */     if (pos == -1)
/* 144 */       return false; 
/* 145 */     int tail = this.size - pos - 1;
/* 146 */     for (int i = 0; i < tail; i++)
/* 147 */       this.a[pos + i] = this.a[pos + i + 1]; 
/* 148 */     this.size--;
/* 149 */     this.a[this.size] = null;
/* 150 */     return true;
/*     */   }
/*     */   
/*     */   public boolean add(K k) {
/* 154 */     int pos = findKey(k);
/* 155 */     if (pos != -1)
/* 156 */       return false; 
/* 157 */     if (this.size == this.a.length) {
/* 158 */       Object[] b = new Object[(this.size == 0) ? 2 : (this.size * 2)];
/* 159 */       for (int i = this.size; i-- != 0;)
/* 160 */         b[i] = this.a[i]; 
/* 161 */       this.a = b;
/*     */     } 
/* 163 */     this.a[this.size++] = k;
/* 164 */     return true;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 168 */     Arrays.fill(this.a, 0, this.size, (Object)null);
/* 169 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 173 */     return (this.size == 0);
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
/*     */   public ObjectArraySet<K> clone() {
/*     */     ObjectArraySet<K> c;
/*     */     try {
/* 190 */       c = (ObjectArraySet<K>)super.clone();
/* 191 */     } catch (CloneNotSupportedException cantHappen) {
/* 192 */       throw new InternalError();
/*     */     } 
/* 194 */     c.a = (Object[])this.a.clone();
/* 195 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 198 */     s.defaultWriteObject();
/* 199 */     for (int i = 0; i < this.size; i++)
/* 200 */       s.writeObject(this.a[i]); 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 203 */     s.defaultReadObject();
/* 204 */     this.a = new Object[this.size];
/* 205 */     for (int i = 0; i < this.size; i++)
/* 206 */       this.a[i] = s.readObject(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectArraySet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */