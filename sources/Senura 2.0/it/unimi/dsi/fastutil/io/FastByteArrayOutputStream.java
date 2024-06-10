/*     */ package it.unimi.dsi.fastutil.io;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.bytes.ByteArrays;
/*     */ import java.io.IOException;
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
/*     */ public class FastByteArrayOutputStream
/*     */   extends MeasurableOutputStream
/*     */   implements RepositionableStream
/*     */ {
/*     */   public static final int DEFAULT_INITIAL_CAPACITY = 16;
/*     */   public byte[] array;
/*     */   public int length;
/*     */   private int position;
/*     */   
/*     */   public FastByteArrayOutputStream() {
/*  52 */     this(16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastByteArrayOutputStream(int initialCapacity) {
/*  60 */     this.array = new byte[initialCapacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastByteArrayOutputStream(byte[] a) {
/*  68 */     this.array = a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/*  73 */     this.length = 0;
/*  74 */     this.position = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trim() {
/*  79 */     this.array = ByteArrays.trim(this.array, this.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(int b) {
/*  84 */     if (this.position >= this.array.length) this.array = ByteArrays.grow(this.array, this.position + 1, this.length); 
/*  85 */     this.array[this.position++] = (byte)b;
/*  86 */     if (this.length < this.position) this.length = this.position;
/*     */   
/*     */   }
/*     */   
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/*  91 */     ByteArrays.ensureOffsetLength(b, off, len);
/*  92 */     if (this.position + len > this.array.length) this.array = ByteArrays.grow(this.array, this.position + len, this.position); 
/*  93 */     System.arraycopy(b, off, this.array, this.position, len);
/*  94 */     if (this.position + len > this.length) this.length = this.position += len;
/*     */   
/*     */   }
/*     */   
/*     */   public void position(long newPosition) {
/*  99 */     if (this.position > Integer.MAX_VALUE) throw new IllegalArgumentException("Position too large: " + newPosition); 
/* 100 */     this.position = (int)newPosition;
/*     */   }
/*     */ 
/*     */   
/*     */   public long position() {
/* 105 */     return this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public long length() throws IOException {
/* 110 */     return this.length;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\io\FastByteArrayOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */