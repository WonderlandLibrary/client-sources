/*     */ package it.unimi.dsi.fastutil.io;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FastByteArrayInputStream
/*     */   extends MeasurableInputStream
/*     */   implements RepositionableStream
/*     */ {
/*     */   public byte[] array;
/*     */   public int offset;
/*     */   public int length;
/*     */   private int position;
/*     */   private int mark;
/*     */   
/*     */   public FastByteArrayInputStream(byte[] array, int offset, int length) {
/*  53 */     this.array = array;
/*  54 */     this.offset = offset;
/*  55 */     this.length = length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastByteArrayInputStream(byte[] array) {
/*  63 */     this(array, 0, array.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/*  68 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/*  73 */     this.position = this.mark;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */ 
/*     */   
/*     */   public void mark(int dummy) {
/*  82 */     this.mark = this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public int available() {
/*  87 */     return this.length - this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public long skip(long n) {
/*  92 */     if (n <= (this.length - this.position)) {
/*  93 */       this.position += (int)n;
/*  94 */       return n;
/*     */     } 
/*  96 */     n = (this.length - this.position);
/*  97 */     this.position = this.length;
/*  98 */     return n;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read() {
/* 103 */     if (this.length == this.position) return -1; 
/* 104 */     return this.array[this.offset + this.position++] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] b, int offset, int length) {
/* 115 */     if (this.length == this.position) return (length == 0) ? 0 : -1; 
/* 116 */     int n = Math.min(length, this.length - this.position);
/* 117 */     System.arraycopy(this.array, this.offset + this.position, b, offset, n);
/* 118 */     this.position += n;
/* 119 */     return n;
/*     */   }
/*     */ 
/*     */   
/*     */   public long position() {
/* 124 */     return this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public void position(long newPosition) {
/* 129 */     this.position = (int)Math.min(newPosition, this.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public long length() {
/* 134 */     return this.length;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\io\FastByteArrayInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */