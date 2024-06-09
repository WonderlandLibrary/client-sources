/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.handler.codec.http.HttpConstants;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.charset.Charset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractDiskHttpData
/*     */   extends AbstractHttpData
/*     */ {
/*  40 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractDiskHttpData.class);
/*     */   
/*     */   protected File file;
/*     */   private boolean isRenamed;
/*     */   private FileChannel fileChannel;
/*     */   
/*     */   protected AbstractDiskHttpData(String name, Charset charset, long size) {
/*  47 */     super(name, charset, size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract String getDiskFilename();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract String getPrefix();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract String getBaseDirectory();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract String getPostfix();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean deleteOnExit();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private File tempFile() throws IOException {
/*     */     String newpostfix;
/*     */     File tmpFile;
/*  81 */     String diskFilename = getDiskFilename();
/*  82 */     if (diskFilename != null) {
/*  83 */       newpostfix = '_' + diskFilename;
/*     */     } else {
/*  85 */       newpostfix = getPostfix();
/*     */     } 
/*     */     
/*  88 */     if (getBaseDirectory() == null) {
/*     */       
/*  90 */       tmpFile = File.createTempFile(getPrefix(), newpostfix);
/*     */     } else {
/*  92 */       tmpFile = File.createTempFile(getPrefix(), newpostfix, new File(getBaseDirectory()));
/*     */     } 
/*     */     
/*  95 */     if (deleteOnExit()) {
/*  96 */       tmpFile.deleteOnExit();
/*     */     }
/*  98 */     return tmpFile;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(ByteBuf buffer) throws IOException {
/* 103 */     if (buffer == null) {
/* 104 */       throw new NullPointerException("buffer");
/*     */     }
/*     */     try {
/* 107 */       this.size = buffer.readableBytes();
/* 108 */       if (this.definedSize > 0L && this.definedSize < this.size) {
/* 109 */         throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
/*     */       }
/* 111 */       if (this.file == null) {
/* 112 */         this.file = tempFile();
/*     */       }
/* 114 */       if (buffer.readableBytes() == 0) {
/*     */         
/* 116 */         this.file.createNewFile();
/*     */         return;
/*     */       } 
/* 119 */       FileOutputStream outputStream = new FileOutputStream(this.file);
/* 120 */       FileChannel localfileChannel = outputStream.getChannel();
/* 121 */       ByteBuffer byteBuffer = buffer.nioBuffer();
/* 122 */       int written = 0;
/* 123 */       while (written < this.size) {
/* 124 */         written += localfileChannel.write(byteBuffer);
/*     */       }
/* 126 */       buffer.readerIndex(buffer.readerIndex() + written);
/* 127 */       localfileChannel.force(false);
/* 128 */       localfileChannel.close();
/* 129 */       outputStream.close();
/* 130 */       this.completed = true;
/*     */     }
/*     */     finally {
/*     */       
/* 134 */       buffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addContent(ByteBuf buffer, boolean last) throws IOException {
/* 141 */     if (buffer != null) {
/*     */       try {
/* 143 */         int localsize = buffer.readableBytes();
/* 144 */         if (this.definedSize > 0L && this.definedSize < this.size + localsize) {
/* 145 */           throw new IOException("Out of size: " + (this.size + localsize) + " > " + this.definedSize);
/*     */         }
/*     */         
/* 148 */         ByteBuffer byteBuffer = (buffer.nioBufferCount() == 1) ? buffer.nioBuffer() : buffer.copy().nioBuffer();
/* 149 */         int written = 0;
/* 150 */         if (this.file == null) {
/* 151 */           this.file = tempFile();
/*     */         }
/* 153 */         if (this.fileChannel == null) {
/* 154 */           FileOutputStream outputStream = new FileOutputStream(this.file);
/* 155 */           this.fileChannel = outputStream.getChannel();
/*     */         } 
/* 157 */         while (written < localsize) {
/* 158 */           written += this.fileChannel.write(byteBuffer);
/*     */         }
/* 160 */         this.size += localsize;
/* 161 */         buffer.readerIndex(buffer.readerIndex() + written);
/*     */       }
/*     */       finally {
/*     */         
/* 165 */         buffer.release();
/*     */       } 
/*     */     }
/* 168 */     if (last) {
/* 169 */       if (this.file == null) {
/* 170 */         this.file = tempFile();
/*     */       }
/* 172 */       if (this.fileChannel == null) {
/* 173 */         FileOutputStream outputStream = new FileOutputStream(this.file);
/* 174 */         this.fileChannel = outputStream.getChannel();
/*     */       } 
/* 176 */       this.fileChannel.force(false);
/* 177 */       this.fileChannel.close();
/* 178 */       this.fileChannel = null;
/* 179 */       this.completed = true;
/*     */     }
/* 181 */     else if (buffer == null) {
/* 182 */       throw new NullPointerException("buffer");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContent(File file) throws IOException {
/* 189 */     if (this.file != null) {
/* 190 */       delete();
/*     */     }
/* 192 */     this.file = file;
/* 193 */     this.size = file.length();
/* 194 */     this.isRenamed = true;
/* 195 */     this.completed = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(InputStream inputStream) throws IOException {
/* 200 */     if (inputStream == null) {
/* 201 */       throw new NullPointerException("inputStream");
/*     */     }
/* 203 */     if (this.file != null) {
/* 204 */       delete();
/*     */     }
/* 206 */     this.file = tempFile();
/* 207 */     FileOutputStream outputStream = new FileOutputStream(this.file);
/* 208 */     FileChannel localfileChannel = outputStream.getChannel();
/* 209 */     byte[] bytes = new byte[16384];
/* 210 */     ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
/* 211 */     int read = inputStream.read(bytes);
/* 212 */     int written = 0;
/* 213 */     while (read > 0) {
/* 214 */       byteBuffer.position(read).flip();
/* 215 */       written += localfileChannel.write(byteBuffer);
/* 216 */       read = inputStream.read(bytes);
/*     */     } 
/* 218 */     localfileChannel.force(false);
/* 219 */     localfileChannel.close();
/* 220 */     this.size = written;
/* 221 */     if (this.definedSize > 0L && this.definedSize < this.size) {
/* 222 */       this.file.delete();
/* 223 */       this.file = null;
/* 224 */       throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
/*     */     } 
/* 226 */     this.isRenamed = true;
/* 227 */     this.completed = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void delete() {
/* 232 */     if (this.fileChannel != null) {
/*     */       try {
/* 234 */         this.fileChannel.force(false);
/* 235 */         this.fileChannel.close();
/* 236 */       } catch (IOException e) {
/* 237 */         logger.warn("Failed to close a file.", e);
/*     */       } 
/* 239 */       this.fileChannel = null;
/*     */     } 
/* 241 */     if (!this.isRenamed) {
/* 242 */       if (this.file != null && this.file.exists()) {
/* 243 */         this.file.delete();
/*     */       }
/* 245 */       this.file = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] get() throws IOException {
/* 251 */     if (this.file == null) {
/* 252 */       return EmptyArrays.EMPTY_BYTES;
/*     */     }
/* 254 */     return readFrom(this.file);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getByteBuf() throws IOException {
/* 259 */     if (this.file == null) {
/* 260 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/* 262 */     byte[] array = readFrom(this.file);
/* 263 */     return Unpooled.wrappedBuffer(array);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getChunk(int length) throws IOException {
/* 268 */     if (this.file == null || length == 0) {
/* 269 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/* 271 */     if (this.fileChannel == null) {
/* 272 */       FileInputStream inputStream = new FileInputStream(this.file);
/* 273 */       this.fileChannel = inputStream.getChannel();
/*     */     } 
/* 275 */     int read = 0;
/* 276 */     ByteBuffer byteBuffer = ByteBuffer.allocate(length);
/* 277 */     while (read < length) {
/* 278 */       int readnow = this.fileChannel.read(byteBuffer);
/* 279 */       if (readnow == -1) {
/* 280 */         this.fileChannel.close();
/* 281 */         this.fileChannel = null;
/*     */         break;
/*     */       } 
/* 284 */       read += readnow;
/*     */     } 
/*     */     
/* 287 */     if (read == 0) {
/* 288 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/* 290 */     byteBuffer.flip();
/* 291 */     ByteBuf buffer = Unpooled.wrappedBuffer(byteBuffer);
/* 292 */     buffer.readerIndex(0);
/* 293 */     buffer.writerIndex(read);
/* 294 */     return buffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString() throws IOException {
/* 299 */     return getString(HttpConstants.DEFAULT_CHARSET);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString(Charset encoding) throws IOException {
/* 304 */     if (this.file == null) {
/* 305 */       return "";
/*     */     }
/* 307 */     if (encoding == null) {
/* 308 */       byte[] arrayOfByte = readFrom(this.file);
/* 309 */       return new String(arrayOfByte, HttpConstants.DEFAULT_CHARSET.name());
/*     */     } 
/* 311 */     byte[] array = readFrom(this.file);
/* 312 */     return new String(array, encoding.name());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInMemory() {
/* 317 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renameTo(File dest) throws IOException {
/* 322 */     if (dest == null) {
/* 323 */       throw new NullPointerException("dest");
/*     */     }
/* 325 */     if (this.file == null) {
/* 326 */       throw new IOException("No file defined so cannot be renamed");
/*     */     }
/* 328 */     if (!this.file.renameTo(dest)) {
/*     */       
/* 330 */       FileInputStream inputStream = new FileInputStream(this.file);
/* 331 */       FileOutputStream outputStream = new FileOutputStream(dest);
/* 332 */       FileChannel in = inputStream.getChannel();
/* 333 */       FileChannel out = outputStream.getChannel();
/* 334 */       int chunkSize = 8196;
/* 335 */       long position = 0L;
/* 336 */       while (position < this.size) {
/* 337 */         if (chunkSize < this.size - position) {
/* 338 */           chunkSize = (int)(this.size - position);
/*     */         }
/* 340 */         position += in.transferTo(position, chunkSize, out);
/*     */       } 
/* 342 */       in.close();
/* 343 */       out.close();
/* 344 */       if (position == this.size) {
/* 345 */         this.file.delete();
/* 346 */         this.file = dest;
/* 347 */         this.isRenamed = true;
/* 348 */         return true;
/*     */       } 
/* 350 */       dest.delete();
/* 351 */       return false;
/*     */     } 
/*     */     
/* 354 */     this.file = dest;
/* 355 */     this.isRenamed = true;
/* 356 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] readFrom(File src) throws IOException {
/* 364 */     long srcsize = src.length();
/* 365 */     if (srcsize > 2147483647L) {
/* 366 */       throw new IllegalArgumentException("File too big to be loaded in memory");
/*     */     }
/*     */     
/* 369 */     FileInputStream inputStream = new FileInputStream(src);
/* 370 */     FileChannel fileChannel = inputStream.getChannel();
/* 371 */     byte[] array = new byte[(int)srcsize];
/* 372 */     ByteBuffer byteBuffer = ByteBuffer.wrap(array);
/* 373 */     int read = 0;
/* 374 */     while (read < srcsize) {
/* 375 */       read += fileChannel.read(byteBuffer);
/*     */     }
/* 377 */     fileChannel.close();
/* 378 */     return array;
/*     */   }
/*     */ 
/*     */   
/*     */   public File getFile() throws IOException {
/* 383 */     return this.file;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\multipart\AbstractDiskHttpData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */