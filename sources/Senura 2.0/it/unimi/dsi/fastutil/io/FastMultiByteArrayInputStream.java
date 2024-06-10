/*     */ package it.unimi.dsi.fastutil.io;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FastMultiByteArrayInputStream
/*     */   extends MeasurableInputStream
/*     */   implements RepositionableStream
/*     */ {
/*     */   public static final int SLICE_BITS = 30;
/*     */   public static final int SLICE_SIZE = 1073741824;
/*     */   public static final int SLICE_MASK = 1073741823;
/*     */   public byte[][] array;
/*     */   public byte[] current;
/*     */   public long length;
/*     */   private long position;
/*     */   
/*     */   public FastMultiByteArrayInputStream(MeasurableInputStream is) throws IOException {
/*  63 */     this(is, is.length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastMultiByteArrayInputStream(InputStream is, long size) throws IOException {
/*  73 */     this.length = size;
/*  74 */     this.array = new byte[(int)((size + 1073741824L - 1L) / 1073741824L) + 1][];
/*     */     
/*  76 */     for (int i = 0; i < this.array.length - 1; i++) {
/*  77 */       this.array[i] = new byte[(size >= 1073741824L) ? 1073741824 : (int)size];
/*     */       
/*  79 */       if (BinIO.loadBytes(is, this.array[i]) != (this.array[i]).length) throw new EOFException(); 
/*  80 */       size -= (this.array[i]).length;
/*     */     } 
/*     */     
/*  83 */     this.current = this.array[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastMultiByteArrayInputStream(FastMultiByteArrayInputStream is) {
/*  91 */     this.array = is.array;
/*  92 */     this.length = is.length;
/*  93 */     this.current = this.array[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastMultiByteArrayInputStream(byte[] array) {
/* 102 */     if (array.length == 0) { this.array = new byte[1][]; }
/*     */     else
/* 104 */     { this.array = new byte[2][];
/* 105 */       this.array[0] = array;
/* 106 */       this.length = array.length;
/* 107 */       this.current = array; }
/*     */   
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
/*     */   public int available() {
/* 121 */     return (int)Math.min(2147483647L, this.length - this.position);
/*     */   }
/*     */ 
/*     */   
/*     */   public long skip(long n) {
/* 126 */     if (n > this.length - this.position) n = this.length - this.position; 
/* 127 */     this.position += n;
/* 128 */     updateCurrent();
/* 129 */     return n;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read() {
/* 134 */     if (this.length == this.position) return -1; 
/* 135 */     int disp = (int)(this.position++ & 0x3FFFFFFFL);
/* 136 */     if (disp == 0) updateCurrent(); 
/* 137 */     return this.current[disp] & 0xFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(byte[] b, int offset, int length) {
/* 142 */     long remaining = this.length - this.position;
/* 143 */     if (remaining == 0L) return (length == 0) ? 0 : -1; 
/* 144 */     int n = (int)Math.min(length, remaining);
/* 145 */     int m = n;
/*     */     
/*     */     while (true) {
/* 148 */       int disp = (int)(this.position & 0x3FFFFFFFL);
/* 149 */       if (disp == 0) updateCurrent(); 
/* 150 */       int res = Math.min(n, this.current.length - disp);
/* 151 */       System.arraycopy(this.current, disp, b, offset, res);
/*     */       
/* 153 */       n -= res;
/* 154 */       this.position += res;
/* 155 */       if (n == 0) return m; 
/* 156 */       offset += res;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateCurrent() {
/* 161 */     this.current = this.array[(int)(this.position >>> 30L)];
/*     */   }
/*     */ 
/*     */   
/*     */   public long position() {
/* 166 */     return this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public void position(long newPosition) {
/* 171 */     this.position = Math.min(newPosition, this.length);
/* 172 */     updateCurrent();
/*     */   }
/*     */ 
/*     */   
/*     */   public long length() throws IOException {
/* 177 */     return this.length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 186 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void mark(int dummy) {
/* 191 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 196 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\io\FastMultiByteArrayInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */