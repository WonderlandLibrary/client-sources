/*     */ package nightmare.event;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class ArrayHelper<T>
/*     */   implements Iterable<T>
/*     */ {
/*     */   private T[] elements;
/*     */   
/*     */   public ArrayHelper(T[] array) {
/*  11 */     this.elements = array;
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayHelper() {
/*  16 */     this.elements = (T[])new Object[0];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(T t) {
/*  22 */     if (t != null) {
/*  23 */       Object[] array = new Object[size() + 1];
/*     */       
/*  25 */       for (int i = 0; i < array.length; i++) {
/*  26 */         if (i < size()) {
/*  27 */           array[i] = get(i);
/*     */         } else {
/*  29 */           array[i] = t;
/*     */         } 
/*     */       } 
/*     */       
/*  33 */       set((T[])array);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(T t) {
/*     */     Object[] array;
/*  42 */     for (int lenght = (array = (Object[])array()).length, i = 0; i < lenght; i++) {
/*  43 */       T entry = (T)array[i];
/*  44 */       if (entry.equals(t)) {
/*  45 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  49 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(T t) {
/*  55 */     if (contains(t)) {
/*  56 */       Object[] array = new Object[size() - 1];
/*  57 */       boolean b = true;
/*     */       
/*  59 */       for (int i = 0; i < size(); i++) {
/*  60 */         if (b && get(i).equals(t)) {
/*  61 */           b = false;
/*     */         } else {
/*  63 */           array[b ? i : (i - 1)] = get(i);
/*     */         } 
/*     */       } 
/*     */       
/*  67 */       set((T[])array);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public T[] array() {
/*  73 */     return this.elements;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  78 */     return (array()).length;
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(T[] array) {
/*  83 */     this.elements = array;
/*     */   }
/*     */ 
/*     */   
/*     */   public T get(int index) {
/*  88 */     return array()[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  93 */     this.elements = (T[])new Object[0];
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  97 */     return (size() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<T> iterator() {
/* 103 */     return new Iterator<T>()
/*     */       {
/* 105 */         private int index = 0;
/*     */ 
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 110 */           return (this.index < ArrayHelper.this.size() && ArrayHelper.this.get(this.index) != null);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public T next() {
/* 116 */           return ArrayHelper.this.get(this.index++);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void remove() {
/* 122 */           ArrayHelper.this.remove(ArrayHelper.this.get(this.index));
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\event\ArrayHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */