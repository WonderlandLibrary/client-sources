/*     */ package it.unimi.dsi.fastutil.io;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.bytes.ByteArrays;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.util.EnumSet;
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
/*     */ public class FastBufferedInputStream
/*     */   extends MeasurableInputStream
/*     */   implements RepositionableStream
/*     */ {
/*     */   public static final int DEFAULT_BUFFER_SIZE = 8192;
/*     */   
/*     */   public enum LineTerminator
/*     */   {
/*  86 */     CR,
/*     */     
/*  88 */     LF,
/*     */     
/*  90 */     CR_LF;
/*     */   }
/*     */ 
/*     */   
/*  94 */   public static final EnumSet<LineTerminator> ALL_TERMINATORS = EnumSet.allOf(LineTerminator.class);
/*     */ 
/*     */   
/*     */   protected InputStream is;
/*     */ 
/*     */   
/*     */   protected byte[] buffer;
/*     */ 
/*     */   
/*     */   protected int pos;
/*     */ 
/*     */   
/*     */   protected long readBytes;
/*     */ 
/*     */   
/*     */   protected int avail;
/*     */ 
/*     */   
/*     */   private FileChannel fileChannel;
/*     */ 
/*     */   
/*     */   private RepositionableStream repositionableStream;
/*     */ 
/*     */   
/*     */   private MeasurableStream measurableStream;
/*     */ 
/*     */ 
/*     */   
/*     */   private static int ensureBufferSize(int bufferSize) {
/* 123 */     if (bufferSize <= 0) throw new IllegalArgumentException("Illegal buffer size: " + bufferSize); 
/* 124 */     return bufferSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastBufferedInputStream(InputStream is, byte[] buffer) {
/* 133 */     this.is = is;
/* 134 */     ensureBufferSize(buffer.length);
/* 135 */     this.buffer = buffer;
/*     */     
/* 137 */     if (is instanceof RepositionableStream) this.repositionableStream = (RepositionableStream)is; 
/* 138 */     if (is instanceof MeasurableStream) this.measurableStream = (MeasurableStream)is;
/*     */     
/* 140 */     if (this.repositionableStream == null) {
/*     */ 
/*     */       
/* 143 */       try { this.fileChannel = (FileChannel)is.getClass().getMethod("getChannel", new Class[0]).invoke(is, new Object[0]); }
/*     */       
/* 145 */       catch (IllegalAccessException illegalAccessException) {  }
/* 146 */       catch (IllegalArgumentException illegalArgumentException) {  }
/* 147 */       catch (NoSuchMethodException noSuchMethodException) {  }
/* 148 */       catch (InvocationTargetException invocationTargetException) {  }
/* 149 */       catch (ClassCastException classCastException) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastBufferedInputStream(InputStream is, int bufferSize) {
/* 159 */     this(is, new byte[ensureBufferSize(bufferSize)]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastBufferedInputStream(InputStream is) {
/* 167 */     this(is, 8192);
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
/*     */   protected boolean noMoreCharacters() throws IOException {
/* 179 */     if (this.avail == 0) {
/* 180 */       this.avail = this.is.read(this.buffer);
/* 181 */       if (this.avail <= 0) {
/* 182 */         this.avail = 0;
/* 183 */         return true;
/*     */       } 
/* 185 */       this.pos = 0;
/*     */     } 
/* 187 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/* 192 */     if (noMoreCharacters()) return -1; 
/* 193 */     this.avail--;
/* 194 */     this.readBytes++;
/* 195 */     return this.buffer[this.pos++] & 0xFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(byte[] b, int offset, int length) throws IOException {
/* 200 */     if (length <= this.avail) {
/* 201 */       System.arraycopy(this.buffer, this.pos, b, offset, length);
/* 202 */       this.pos += length;
/* 203 */       this.avail -= length;
/* 204 */       this.readBytes += length;
/* 205 */       return length;
/*     */     } 
/*     */     
/* 208 */     int head = this.avail;
/*     */     
/* 210 */     System.arraycopy(this.buffer, this.pos, b, offset, head);
/* 211 */     this.pos = this.avail = 0;
/* 212 */     this.readBytes += head;
/*     */     
/* 214 */     if (length > this.buffer.length) {
/*     */       
/* 216 */       int result = this.is.read(b, offset + head, length - head);
/* 217 */       if (result > 0) this.readBytes += result; 
/* 218 */       return (result < 0) ? ((head == 0) ? -1 : head) : (result + head);
/*     */     } 
/*     */     
/* 221 */     if (noMoreCharacters()) return (head == 0) ? -1 : head;
/*     */     
/* 223 */     int toRead = Math.min(length - head, this.avail);
/* 224 */     this.readBytes += toRead;
/* 225 */     System.arraycopy(this.buffer, 0, b, offset + head, toRead);
/* 226 */     this.pos = toRead;
/* 227 */     this.avail -= toRead;
/*     */ 
/*     */     
/* 230 */     return toRead + head;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int readLine(byte[] array) throws IOException {
/* 241 */     return readLine(array, 0, array.length, ALL_TERMINATORS);
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
/*     */   public int readLine(byte[] array, EnumSet<LineTerminator> terminators) throws IOException {
/* 254 */     return readLine(array, 0, array.length, terminators);
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
/*     */   public int readLine(byte[] array, int off, int len) throws IOException {
/* 266 */     return readLine(array, off, len, ALL_TERMINATORS);
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
/*     */   public int readLine(byte[] array, int off, int len, EnumSet<LineTerminator> terminators) throws IOException {
/* 317 */     ByteArrays.ensureOffsetLength(array, off, len);
/* 318 */     if (len == 0) return 0; 
/* 319 */     if (noMoreCharacters()) return -1; 
/* 320 */     int k = 0, remaining = len, read = 0; while (true) {
/*     */       int i;
/* 322 */       for (i = 0; i < this.avail && i < remaining && (k = this.buffer[this.pos + i]) != 10 && k != 13; i++);
/* 323 */       System.arraycopy(this.buffer, this.pos, array, off + read, i);
/* 324 */       this.pos += i;
/* 325 */       this.avail -= i;
/* 326 */       read += i;
/* 327 */       remaining -= i;
/* 328 */       if (remaining == 0) {
/* 329 */         this.readBytes += read;
/* 330 */         return read;
/*     */       } 
/*     */       
/* 333 */       if (this.avail > 0) {
/* 334 */         if (k == 10) {
/* 335 */           this.pos++;
/* 336 */           this.avail--;
/* 337 */           if (terminators.contains(LineTerminator.LF)) {
/* 338 */             this.readBytes += (read + 1);
/* 339 */             return read;
/*     */           } 
/*     */           
/* 342 */           array[off + read++] = 10;
/* 343 */           remaining--;
/*     */           continue;
/*     */         } 
/* 346 */         if (k == 13) {
/* 347 */           this.pos++;
/* 348 */           this.avail--;
/*     */           
/* 350 */           if (terminators.contains(LineTerminator.CR_LF)) {
/* 351 */             if (this.avail > 0) {
/* 352 */               if (this.buffer[this.pos] == 10) {
/* 353 */                 this.pos++;
/* 354 */                 this.avail--;
/* 355 */                 this.readBytes += (read + 2);
/* 356 */                 return read;
/*     */               } 
/*     */             } else {
/*     */               
/* 360 */               if (noMoreCharacters()) {
/*     */ 
/*     */                 
/* 363 */                 if (!terminators.contains(LineTerminator.CR)) {
/* 364 */                   array[off + read++] = 13;
/* 365 */                   remaining--;
/* 366 */                   this.readBytes += read;
/*     */                 } else {
/* 368 */                   this.readBytes += (read + 1);
/*     */                 } 
/* 370 */                 return read;
/*     */               } 
/* 372 */               if (this.buffer[0] == 10) {
/*     */                 
/* 374 */                 this.pos++;
/* 375 */                 this.avail--;
/* 376 */                 this.readBytes += (read + 2);
/* 377 */                 return read;
/*     */               } 
/*     */             } 
/*     */           }
/*     */           
/* 382 */           if (terminators.contains(LineTerminator.CR)) {
/* 383 */             this.readBytes += (read + 1);
/* 384 */             return read;
/*     */           } 
/*     */           
/* 387 */           array[off + read++] = 13;
/* 388 */           remaining--;
/*     */         }  continue;
/*     */       } 
/* 391 */       if (noMoreCharacters()) {
/* 392 */         this.readBytes += read;
/* 393 */         return read;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void position(long newPosition) throws IOException {
/* 401 */     long position = this.readBytes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 408 */     if (newPosition <= position + this.avail && newPosition >= position - this.pos) {
/* 409 */       this.pos = (int)(this.pos + newPosition - position);
/* 410 */       this.avail = (int)(this.avail - newPosition - position);
/* 411 */       this.readBytes = newPosition;
/*     */       
/*     */       return;
/*     */     } 
/* 415 */     if (this.repositionableStream != null) { this.repositionableStream.position(newPosition); }
/* 416 */     else if (this.fileChannel != null) { this.fileChannel.position(newPosition); }
/* 417 */     else { throw new UnsupportedOperationException("position() can only be called if the underlying byte stream implements the RepositionableStream interface or if the getChannel() method of the underlying byte stream exists and returns a FileChannel"); }
/* 418 */      this.readBytes = newPosition;
/*     */     
/* 420 */     this.avail = this.pos = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public long position() throws IOException {
/* 425 */     return this.readBytes;
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
/*     */   public long length() throws IOException {
/* 437 */     if (this.measurableStream != null) return this.measurableStream.length(); 
/* 438 */     if (this.fileChannel != null) return this.fileChannel.size(); 
/* 439 */     throw new UnsupportedOperationException();
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
/*     */   private long skipByReading(long n) throws IOException {
/* 452 */     long toSkip = n;
/*     */     
/* 454 */     while (toSkip > 0L) {
/* 455 */       int len = this.is.read(this.buffer, 0, (int)Math.min(this.buffer.length, toSkip));
/* 456 */       if (len > 0) toSkip -= len;
/*     */     
/*     */     } 
/*     */     
/* 460 */     return n - toSkip;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public long skip(long n) throws IOException {
/* 480 */     if (n <= this.avail) {
/* 481 */       int m = (int)n;
/* 482 */       this.pos += m;
/* 483 */       this.avail -= m;
/* 484 */       this.readBytes += n;
/* 485 */       return n;
/*     */     } 
/*     */     
/* 488 */     long toSkip = n - this.avail, result = 0L;
/* 489 */     this.avail = 0;
/*     */     
/* 491 */     while (toSkip != 0L) { if ((result = (this.is == System.in) ? skipByReading(toSkip) : this.is.skip(toSkip)) < toSkip) {
/* 492 */         if (result == 0L) {
/* 493 */           if (this.is.read() == -1)
/* 494 */             break;  toSkip--; continue;
/*     */         } 
/* 496 */         toSkip -= result;
/*     */       }  }
/*     */     
/* 499 */     long t = n - toSkip - result;
/* 500 */     this.readBytes += t;
/* 501 */     return t;
/*     */   }
/*     */ 
/*     */   
/*     */   public int available() throws IOException {
/* 506 */     return (int)Math.min(this.is.available() + this.avail, 2147483647L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 511 */     if (this.is == null)
/* 512 */       return;  if (this.is != System.in) this.is.close(); 
/* 513 */     this.is = null;
/* 514 */     this.buffer = null;
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
/*     */   public void flush() {
/* 528 */     if (this.is == null)
/* 529 */       return;  this.readBytes += this.avail;
/* 530 */     this.avail = this.pos = 0;
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
/*     */   @Deprecated
/*     */   public void reset() {
/* 543 */     flush();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\io\FastBufferedInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */