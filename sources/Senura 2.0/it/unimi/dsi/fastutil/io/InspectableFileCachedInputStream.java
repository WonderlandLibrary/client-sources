/*     */ package it.unimi.dsi.fastutil.io;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.bytes.ByteArrays;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InspectableFileCachedInputStream
/*     */   extends MeasurableInputStream
/*     */   implements RepositionableStream, WritableByteChannel
/*     */ {
/*     */   public static final boolean DEBUG = false;
/*     */   public static final int DEFAULT_BUFFER_SIZE = 65536;
/*     */   public final byte[] buffer;
/*     */   public int inspectable;
/*     */   private final File overflowFile;
/*     */   private final RandomAccessFile randomAccessFile;
/*     */   private final FileChannel fileChannel;
/*     */   private long position;
/*     */   private long mark;
/*     */   private long writePosition;
/*     */   
/*     */   public InspectableFileCachedInputStream(int bufferSize, File overflowFile) throws IOException {
/*  99 */     if (bufferSize <= 0) throw new IllegalArgumentException("Illegal buffer size " + bufferSize); 
/* 100 */     if (overflowFile != null) { this.overflowFile = overflowFile; }
/* 101 */     else { (this.overflowFile = File.createTempFile(getClass().getSimpleName(), "overflow")).deleteOnExit(); }
/* 102 */      this.buffer = new byte[bufferSize];
/* 103 */     this.randomAccessFile = new RandomAccessFile(this.overflowFile, "rw");
/* 104 */     this.fileChannel = this.randomAccessFile.getChannel();
/* 105 */     this.mark = -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InspectableFileCachedInputStream(int bufferSize) throws IOException {
/* 113 */     this(bufferSize, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public InspectableFileCachedInputStream() throws IOException {
/* 118 */     this(65536);
/*     */   }
/*     */   
/*     */   private void ensureOpen() throws IOException {
/* 122 */     if (this.position == -1L) throw new IOException("This " + getClass().getSimpleName() + " is closed");
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() throws IOException {
/* 128 */     if (!this.fileChannel.isOpen()) throw new IOException("This " + getClass().getSimpleName() + " is closed"); 
/* 129 */     this.writePosition = this.position = (this.inspectable = 0);
/* 130 */     this.mark = -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int write(ByteBuffer byteBuffer) throws IOException {
/* 140 */     ensureOpen();
/* 141 */     int remaining = byteBuffer.remaining();
/*     */     
/* 143 */     if (this.inspectable < this.buffer.length) {
/*     */       
/* 145 */       int toBuffer = Math.min(this.buffer.length - this.inspectable, remaining);
/* 146 */       byteBuffer.get(this.buffer, this.inspectable, toBuffer);
/* 147 */       this.inspectable += toBuffer;
/*     */     } 
/*     */     
/* 150 */     if (byteBuffer.hasRemaining()) {
/* 151 */       this.fileChannel.position(this.writePosition);
/* 152 */       this.writePosition += this.fileChannel.write(byteBuffer);
/*     */     } 
/*     */     
/* 155 */     return remaining;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void truncate(long size) throws FileNotFoundException, IOException {
/* 164 */     this.fileChannel.truncate(Math.max(size, this.writePosition));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 173 */     this.position = -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reopen() throws IOException {
/* 181 */     if (!this.fileChannel.isOpen()) throw new IOException("This " + getClass().getSimpleName() + " is closed"); 
/* 182 */     this.position = 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispose() throws IOException {
/* 187 */     this.position = -1L;
/* 188 */     this.randomAccessFile.close();
/* 189 */     this.overflowFile.delete();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*     */     try {
/* 196 */       dispose();
/*     */     } finally {
/*     */       
/* 199 */       super.finalize();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int available() throws IOException {
/* 205 */     ensureOpen();
/* 206 */     return (int)Math.min(2147483647L, length() - this.position);
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(byte[] b, int offset, int length) throws IOException {
/* 211 */     ensureOpen();
/* 212 */     if (length == 0) return 0; 
/* 213 */     if (this.position == length()) return -1; 
/* 214 */     ByteArrays.ensureOffsetLength(b, offset, length);
/* 215 */     int read = 0;
/*     */     
/* 217 */     if (this.position < this.inspectable) {
/*     */       
/* 219 */       int toCopy = Math.min(this.inspectable - (int)this.position, length);
/* 220 */       System.arraycopy(this.buffer, (int)this.position, b, offset, toCopy);
/* 221 */       length -= toCopy;
/* 222 */       offset += toCopy;
/* 223 */       this.position += toCopy;
/* 224 */       read = toCopy;
/*     */     } 
/*     */     
/* 227 */     if (length > 0) {
/* 228 */       if (this.position == length()) return (read != 0) ? read : -1; 
/* 229 */       this.fileChannel.position(this.position - this.inspectable);
/* 230 */       int toRead = (int)Math.min(length() - this.position, length);
/*     */       
/* 232 */       int t = this.randomAccessFile.read(b, offset, toRead);
/* 233 */       this.position += t;
/* 234 */       read += t;
/*     */     } 
/*     */     
/* 237 */     return read;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(byte[] b) throws IOException {
/* 242 */     return read(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public long skip(long n) throws IOException {
/* 247 */     ensureOpen();
/* 248 */     long toSkip = Math.min(n, length() - this.position);
/* 249 */     this.position += toSkip;
/* 250 */     return toSkip;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/* 255 */     ensureOpen();
/* 256 */     if (this.position == length()) return -1; 
/* 257 */     if (this.position < this.inspectable) return this.buffer[(int)this.position++] & 0xFF; 
/* 258 */     this.fileChannel.position(this.position - this.inspectable);
/* 259 */     this.position++;
/* 260 */     return this.randomAccessFile.read();
/*     */   }
/*     */ 
/*     */   
/*     */   public long length() throws IOException {
/* 265 */     ensureOpen();
/* 266 */     return this.inspectable + this.writePosition;
/*     */   }
/*     */ 
/*     */   
/*     */   public long position() throws IOException {
/* 271 */     ensureOpen();
/* 272 */     return this.position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void position(long position) throws IOException {
/* 281 */     this.position = Math.min(position, length());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/* 286 */     return (this.position != -1L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void mark(int readlimit) {
/* 291 */     this.mark = this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() throws IOException {
/* 296 */     ensureOpen();
/* 297 */     if (this.mark == -1L) throw new IOException("Mark has not been set"); 
/* 298 */     position(this.mark);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 303 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\io\InspectableFileCachedInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */