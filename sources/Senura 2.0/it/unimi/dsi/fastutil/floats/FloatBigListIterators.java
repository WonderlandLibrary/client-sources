/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FloatBigListIterators
/*     */ {
/*     */   public static class EmptyBigListIterator
/*     */     implements FloatBigListIterator, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public boolean hasNext() {
/*  42 */       return false;
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/*  46 */       return false;
/*     */     }
/*     */     
/*     */     public float nextFloat() {
/*  50 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public float previousFloat() {
/*  54 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public long nextIndex() {
/*  58 */       return 0L;
/*     */     }
/*     */     
/*     */     public long previousIndex() {
/*  62 */       return -1L;
/*     */     }
/*     */     
/*     */     public long skip(long n) {
/*  66 */       return 0L;
/*     */     }
/*     */     
/*     */     public long back(long n) {
/*  70 */       return 0L;
/*     */     }
/*     */     
/*     */     public Object clone() {
/*  74 */       return FloatBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*     */     }
/*     */     private Object readResolve() {
/*  77 */       return FloatBigListIterators.EMPTY_BIG_LIST_ITERATOR;
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
/*  88 */   public static final EmptyBigListIterator EMPTY_BIG_LIST_ITERATOR = new EmptyBigListIterator();
/*     */   
/*     */   private static class SingletonBigListIterator implements FloatBigListIterator { private final float element;
/*     */     private int curr;
/*     */     
/*     */     public SingletonBigListIterator(float element) {
/*  94 */       this.element = element;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/*  98 */       return (this.curr == 0);
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 102 */       return (this.curr == 1);
/*     */     }
/*     */     
/*     */     public float nextFloat() {
/* 106 */       if (!hasNext())
/* 107 */         throw new NoSuchElementException(); 
/* 108 */       this.curr = 1;
/* 109 */       return this.element;
/*     */     }
/*     */     
/*     */     public float previousFloat() {
/* 113 */       if (!hasPrevious())
/* 114 */         throw new NoSuchElementException(); 
/* 115 */       this.curr = 0;
/* 116 */       return this.element;
/*     */     }
/*     */     
/*     */     public long nextIndex() {
/* 120 */       return this.curr;
/*     */     }
/*     */     
/*     */     public long previousIndex() {
/* 124 */       return (this.curr - 1);
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FloatBigListIterator singleton(float element) {
/* 135 */     return new SingletonBigListIterator(element);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableBigListIterator implements FloatBigListIterator { protected final FloatBigListIterator i;
/*     */     
/*     */     public UnmodifiableBigListIterator(FloatBigListIterator i) {
/* 141 */       this.i = i;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 145 */       return this.i.hasNext();
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 149 */       return this.i.hasPrevious();
/*     */     }
/*     */     
/*     */     public float nextFloat() {
/* 153 */       return this.i.nextFloat();
/*     */     }
/*     */     
/*     */     public float previousFloat() {
/* 157 */       return this.i.previousFloat();
/*     */     }
/*     */     
/*     */     public long nextIndex() {
/* 161 */       return this.i.nextIndex();
/*     */     }
/*     */     
/*     */     public long previousIndex() {
/* 165 */       return this.i.previousIndex();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FloatBigListIterator unmodifiable(FloatBigListIterator i) {
/* 176 */     return new UnmodifiableBigListIterator(i);
/*     */   }
/*     */   
/*     */   public static class BigListIteratorListIterator implements FloatBigListIterator { protected final FloatListIterator i;
/*     */     
/*     */     protected BigListIteratorListIterator(FloatListIterator i) {
/* 182 */       this.i = i;
/*     */     }
/*     */     private int intDisplacement(long n) {
/* 185 */       if (n < -2147483648L || n > 2147483647L)
/* 186 */         throw new IndexOutOfBoundsException("This big iterator is restricted to 32-bit displacements"); 
/* 187 */       return (int)n;
/*     */     }
/*     */     
/*     */     public void set(float ok) {
/* 191 */       this.i.set(ok);
/*     */     }
/*     */     
/*     */     public void add(float ok) {
/* 195 */       this.i.add(ok);
/*     */     }
/*     */     
/*     */     public int back(int n) {
/* 199 */       return this.i.back(n);
/*     */     }
/*     */     
/*     */     public long back(long n) {
/* 203 */       return this.i.back(intDisplacement(n));
/*     */     }
/*     */     
/*     */     public void remove() {
/* 207 */       this.i.remove();
/*     */     }
/*     */     
/*     */     public int skip(int n) {
/* 211 */       return this.i.skip(n);
/*     */     }
/*     */     
/*     */     public long skip(long n) {
/* 215 */       return this.i.skip(intDisplacement(n));
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 219 */       return this.i.hasNext();
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 223 */       return this.i.hasPrevious();
/*     */     }
/*     */     
/*     */     public float nextFloat() {
/* 227 */       return this.i.nextFloat();
/*     */     }
/*     */     
/*     */     public float previousFloat() {
/* 231 */       return this.i.previousFloat();
/*     */     }
/*     */     
/*     */     public long nextIndex() {
/* 235 */       return this.i.nextIndex();
/*     */     }
/*     */     
/*     */     public long previousIndex() {
/* 239 */       return this.i.previousIndex();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FloatBigListIterator asBigListIterator(FloatListIterator i) {
/* 250 */     return new BigListIteratorListIterator(i);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatBigListIterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */