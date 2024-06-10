/*     */ package it.unimi.dsi.fastutil.booleans;
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
/*     */ public final class BooleanBigListIterators
/*     */ {
/*     */   public static class EmptyBigListIterator
/*     */     implements BooleanBigListIterator, Serializable, Cloneable
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
/*     */     public boolean nextBoolean() {
/*  50 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public boolean previousBoolean() {
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
/*  74 */       return BooleanBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*     */     }
/*     */     private Object readResolve() {
/*  77 */       return BooleanBigListIterators.EMPTY_BIG_LIST_ITERATOR;
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
/*     */   private static class SingletonBigListIterator implements BooleanBigListIterator { private final boolean element;
/*     */     private int curr;
/*     */     
/*     */     public SingletonBigListIterator(boolean element) {
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
/*     */     public boolean nextBoolean() {
/* 106 */       if (!hasNext())
/* 107 */         throw new NoSuchElementException(); 
/* 108 */       this.curr = 1;
/* 109 */       return this.element;
/*     */     }
/*     */     
/*     */     public boolean previousBoolean() {
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
/*     */   public static BooleanBigListIterator singleton(boolean element) {
/* 135 */     return new SingletonBigListIterator(element);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableBigListIterator implements BooleanBigListIterator { protected final BooleanBigListIterator i;
/*     */     
/*     */     public UnmodifiableBigListIterator(BooleanBigListIterator i) {
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
/*     */     public boolean nextBoolean() {
/* 153 */       return this.i.nextBoolean();
/*     */     }
/*     */     
/*     */     public boolean previousBoolean() {
/* 157 */       return this.i.previousBoolean();
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
/*     */   public static BooleanBigListIterator unmodifiable(BooleanBigListIterator i) {
/* 176 */     return new UnmodifiableBigListIterator(i);
/*     */   }
/*     */   
/*     */   public static class BigListIteratorListIterator implements BooleanBigListIterator { protected final BooleanListIterator i;
/*     */     
/*     */     protected BigListIteratorListIterator(BooleanListIterator i) {
/* 182 */       this.i = i;
/*     */     }
/*     */     private int intDisplacement(long n) {
/* 185 */       if (n < -2147483648L || n > 2147483647L)
/* 186 */         throw new IndexOutOfBoundsException("This big iterator is restricted to 32-bit displacements"); 
/* 187 */       return (int)n;
/*     */     }
/*     */     
/*     */     public void set(boolean ok) {
/* 191 */       this.i.set(ok);
/*     */     }
/*     */     
/*     */     public void add(boolean ok) {
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
/*     */     public boolean nextBoolean() {
/* 227 */       return this.i.nextBoolean();
/*     */     }
/*     */     
/*     */     public boolean previousBoolean() {
/* 231 */       return this.i.previousBoolean();
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
/*     */   public static BooleanBigListIterator asBigListIterator(BooleanListIterator i) {
/* 250 */     return new BigListIteratorListIterator(i);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\booleans\BooleanBigListIterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */