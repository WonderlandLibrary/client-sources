/*     */ package it.unimi.dsi.fastutil.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.nio.channels.FileChannel;
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
/*     */ public class FastBufferedOutputStream
/*     */   extends MeasurableOutputStream
/*     */   implements RepositionableStream
/*     */ {
/*     */   private static final boolean ASSERTS = false;
/*     */   public static final int DEFAULT_BUFFER_SIZE = 8192;
/*     */   protected byte[] buffer;
/*     */   protected int pos;
/*     */   protected int avail;
/*     */   protected OutputStream os;
/*     */   private FileChannel fileChannel;
/*     */   private RepositionableStream repositionableStream;
/*     */   private MeasurableStream measurableStream;
/*     */   
/*     */   private static int ensureBufferSize(int bufferSize) {
/*  78 */     if (bufferSize <= 0) throw new IllegalArgumentException("Illegal buffer size: " + bufferSize); 
/*  79 */     return bufferSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastBufferedOutputStream(OutputStream os, byte[] buffer) {
/*  88 */     this.os = os;
/*  89 */     ensureBufferSize(buffer.length);
/*  90 */     this.buffer = buffer;
/*  91 */     this.avail = buffer.length;
/*     */     
/*  93 */     if (os instanceof RepositionableStream) this.repositionableStream = (RepositionableStream)os; 
/*  94 */     if (os instanceof MeasurableStream) this.measurableStream = (MeasurableStream)os;
/*     */     
/*  96 */     if (this.repositionableStream == null) {
/*     */ 
/*     */       
/*  99 */       try { this.fileChannel = (FileChannel)os.getClass().getMethod("getChannel", new Class[0]).invoke(os, new Object[0]); }
/*     */       
/* 101 */       catch (IllegalAccessException illegalAccessException) {  }
/* 102 */       catch (IllegalArgumentException illegalArgumentException) {  }
/* 103 */       catch (NoSuchMethodException noSuchMethodException) {  }
/* 104 */       catch (InvocationTargetException invocationTargetException) {  }
/* 105 */       catch (ClassCastException classCastException) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastBufferedOutputStream(OutputStream os, int bufferSize) {
/* 116 */     this(os, new byte[ensureBufferSize(bufferSize)]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastBufferedOutputStream(OutputStream os) {
/* 124 */     this(os, 8192);
/*     */   }
/*     */   
/*     */   private void dumpBuffer(boolean ifFull) throws IOException {
/* 128 */     if (!ifFull || this.avail == 0) {
/* 129 */       this.os.write(this.buffer, 0, this.pos);
/* 130 */       this.pos = 0;
/* 131 */       this.avail = this.buffer.length;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int b) throws IOException {
/* 138 */     this.avail--;
/* 139 */     this.buffer[this.pos++] = (byte)b;
/* 140 */     dumpBuffer(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] b, int offset, int length) throws IOException {
/* 145 */     if (length >= this.buffer.length) {
/* 146 */       dumpBuffer(false);
/* 147 */       this.os.write(b, offset, length);
/*     */       
/*     */       return;
/*     */     } 
/* 151 */     if (length <= this.avail) {
/*     */       
/* 153 */       System.arraycopy(b, offset, this.buffer, this.pos, length);
/* 154 */       this.pos += length;
/* 155 */       this.avail -= length;
/* 156 */       dumpBuffer(true);
/*     */       
/*     */       return;
/*     */     } 
/* 160 */     dumpBuffer(false);
/* 161 */     System.arraycopy(b, offset, this.buffer, 0, length);
/* 162 */     this.pos = length;
/* 163 */     this.avail -= length;
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/* 168 */     dumpBuffer(false);
/* 169 */     this.os.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 174 */     if (this.os == null)
/* 175 */       return;  flush();
/* 176 */     if (this.os != System.out) this.os.close(); 
/* 177 */     this.os = null;
/* 178 */     this.buffer = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public long position() throws IOException {
/* 183 */     if (this.repositionableStream != null) return this.repositionableStream.position() + this.pos; 
/* 184 */     if (this.measurableStream != null) return this.measurableStream.position() + this.pos; 
/* 185 */     if (this.fileChannel != null) return this.fileChannel.position() + this.pos; 
/* 186 */     throw new UnsupportedOperationException("position() can only be called if the underlying byte stream implements the MeasurableStream or RepositionableStream interface or if the getChannel() method of the underlying byte stream exists and returns a FileChannel");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void position(long newPosition) throws IOException {
/* 196 */     flush();
/* 197 */     if (this.repositionableStream != null) { this.repositionableStream.position(newPosition); }
/* 198 */     else if (this.fileChannel != null) { this.fileChannel.position(newPosition); }
/* 199 */     else { throw new UnsupportedOperationException("position() can only be called if the underlying byte stream implements the RepositionableStream interface or if the getChannel() method of the underlying byte stream exists and returns a FileChannel"); }
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
/*     */   public long length() throws IOException {
/* 213 */     flush();
/* 214 */     if (this.measurableStream != null) return this.measurableStream.length(); 
/* 215 */     if (this.fileChannel != null) return this.fileChannel.size(); 
/* 216 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\io\FastBufferedOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */