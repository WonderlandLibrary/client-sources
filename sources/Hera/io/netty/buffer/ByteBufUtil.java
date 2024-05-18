/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.CharacterCodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CoderResult;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ByteBufUtil
/*     */ {
/*     */   static {
/*     */     ByteBufAllocator alloc;
/*     */   }
/*     */   
/*  41 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ByteBufUtil.class);
/*     */   
/*  43 */   private static final char[] HEXDUMP_TABLE = new char[1024];
/*     */ 
/*     */   
/*     */   static final ByteBufAllocator DEFAULT_ALLOCATOR;
/*     */ 
/*     */   
/*     */   static {
/*  50 */     char[] DIGITS = "0123456789abcdef".toCharArray();
/*  51 */     for (int i = 0; i < 256; i++) {
/*  52 */       HEXDUMP_TABLE[i << 1] = DIGITS[i >>> 4 & 0xF];
/*  53 */       HEXDUMP_TABLE[(i << 1) + 1] = DIGITS[i & 0xF];
/*     */     } 
/*     */     
/*  56 */     String allocType = SystemPropertyUtil.get("io.netty.allocator.type", "unpooled").toLowerCase(Locale.US).trim();
/*     */     
/*  58 */     if ("unpooled".equals(allocType)) {
/*  59 */       alloc = UnpooledByteBufAllocator.DEFAULT;
/*  60 */       logger.debug("-Dio.netty.allocator.type: {}", allocType);
/*  61 */     } else if ("pooled".equals(allocType)) {
/*  62 */       alloc = PooledByteBufAllocator.DEFAULT;
/*  63 */       logger.debug("-Dio.netty.allocator.type: {}", allocType);
/*     */     } else {
/*  65 */       alloc = UnpooledByteBufAllocator.DEFAULT;
/*  66 */       logger.debug("-Dio.netty.allocator.type: unpooled (unknown: {})", allocType);
/*     */     } 
/*     */     
/*  69 */     DEFAULT_ALLOCATOR = alloc;
/*     */   }
/*  71 */   private static final int THREAD_LOCAL_BUFFER_SIZE = SystemPropertyUtil.getInt("io.netty.threadLocalDirectBufferSize", 65536); static {
/*  72 */     logger.debug("-Dio.netty.threadLocalDirectBufferSize: {}", Integer.valueOf(THREAD_LOCAL_BUFFER_SIZE));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String hexDump(ByteBuf buffer) {
/*  80 */     return hexDump(buffer, buffer.readerIndex(), buffer.readableBytes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String hexDump(ByteBuf buffer, int fromIndex, int length) {
/*  88 */     if (length < 0) {
/*  89 */       throw new IllegalArgumentException("length: " + length);
/*     */     }
/*  91 */     if (length == 0) {
/*  92 */       return "";
/*     */     }
/*     */     
/*  95 */     int endIndex = fromIndex + length;
/*  96 */     char[] buf = new char[length << 1];
/*     */     
/*  98 */     int srcIdx = fromIndex;
/*  99 */     int dstIdx = 0;
/* 100 */     for (; srcIdx < endIndex; srcIdx++, dstIdx += 2) {
/* 101 */       System.arraycopy(HEXDUMP_TABLE, buffer.getUnsignedByte(srcIdx) << 1, buf, dstIdx, 2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 106 */     return new String(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int hashCode(ByteBuf buffer) {
/* 114 */     int aLen = buffer.readableBytes();
/* 115 */     int intCount = aLen >>> 2;
/* 116 */     int byteCount = aLen & 0x3;
/*     */     
/* 118 */     int hashCode = 1;
/* 119 */     int arrayIndex = buffer.readerIndex();
/* 120 */     if (buffer.order() == ByteOrder.BIG_ENDIAN) {
/* 121 */       for (int j = intCount; j > 0; j--) {
/* 122 */         hashCode = 31 * hashCode + buffer.getInt(arrayIndex);
/* 123 */         arrayIndex += 4;
/*     */       } 
/*     */     } else {
/* 126 */       for (int j = intCount; j > 0; j--) {
/* 127 */         hashCode = 31 * hashCode + swapInt(buffer.getInt(arrayIndex));
/* 128 */         arrayIndex += 4;
/*     */       } 
/*     */     } 
/*     */     
/* 132 */     for (int i = byteCount; i > 0; i--) {
/* 133 */       hashCode = 31 * hashCode + buffer.getByte(arrayIndex++);
/*     */     }
/*     */     
/* 136 */     if (hashCode == 0) {
/* 137 */       hashCode = 1;
/*     */     }
/*     */     
/* 140 */     return hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean equals(ByteBuf bufferA, ByteBuf bufferB) {
/* 149 */     int aLen = bufferA.readableBytes();
/* 150 */     if (aLen != bufferB.readableBytes()) {
/* 151 */       return false;
/*     */     }
/*     */     
/* 154 */     int longCount = aLen >>> 3;
/* 155 */     int byteCount = aLen & 0x7;
/*     */     
/* 157 */     int aIndex = bufferA.readerIndex();
/* 158 */     int bIndex = bufferB.readerIndex();
/*     */     
/* 160 */     if (bufferA.order() == bufferB.order()) {
/* 161 */       for (int j = longCount; j > 0; j--) {
/* 162 */         if (bufferA.getLong(aIndex) != bufferB.getLong(bIndex)) {
/* 163 */           return false;
/*     */         }
/* 165 */         aIndex += 8;
/* 166 */         bIndex += 8;
/*     */       } 
/*     */     } else {
/* 169 */       for (int j = longCount; j > 0; j--) {
/* 170 */         if (bufferA.getLong(aIndex) != swapLong(bufferB.getLong(bIndex))) {
/* 171 */           return false;
/*     */         }
/* 173 */         aIndex += 8;
/* 174 */         bIndex += 8;
/*     */       } 
/*     */     } 
/*     */     
/* 178 */     for (int i = byteCount; i > 0; i--) {
/* 179 */       if (bufferA.getByte(aIndex) != bufferB.getByte(bIndex)) {
/* 180 */         return false;
/*     */       }
/* 182 */       aIndex++;
/* 183 */       bIndex++;
/*     */     } 
/*     */     
/* 186 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int compare(ByteBuf bufferA, ByteBuf bufferB) {
/* 194 */     int aLen = bufferA.readableBytes();
/* 195 */     int bLen = bufferB.readableBytes();
/* 196 */     int minLength = Math.min(aLen, bLen);
/* 197 */     int uintCount = minLength >>> 2;
/* 198 */     int byteCount = minLength & 0x3;
/*     */     
/* 200 */     int aIndex = bufferA.readerIndex();
/* 201 */     int bIndex = bufferB.readerIndex();
/*     */     
/* 203 */     if (bufferA.order() == bufferB.order()) {
/* 204 */       for (int j = uintCount; j > 0; j--) {
/* 205 */         long va = bufferA.getUnsignedInt(aIndex);
/* 206 */         long vb = bufferB.getUnsignedInt(bIndex);
/* 207 */         if (va > vb) {
/* 208 */           return 1;
/*     */         }
/* 210 */         if (va < vb) {
/* 211 */           return -1;
/*     */         }
/* 213 */         aIndex += 4;
/* 214 */         bIndex += 4;
/*     */       } 
/*     */     } else {
/* 217 */       for (int j = uintCount; j > 0; j--) {
/* 218 */         long va = bufferA.getUnsignedInt(aIndex);
/* 219 */         long vb = swapInt(bufferB.getInt(bIndex)) & 0xFFFFFFFFL;
/* 220 */         if (va > vb) {
/* 221 */           return 1;
/*     */         }
/* 223 */         if (va < vb) {
/* 224 */           return -1;
/*     */         }
/* 226 */         aIndex += 4;
/* 227 */         bIndex += 4;
/*     */       } 
/*     */     } 
/*     */     
/* 231 */     for (int i = byteCount; i > 0; i--) {
/* 232 */       short va = bufferA.getUnsignedByte(aIndex);
/* 233 */       short vb = bufferB.getUnsignedByte(bIndex);
/* 234 */       if (va > vb) {
/* 235 */         return 1;
/*     */       }
/* 237 */       if (va < vb) {
/* 238 */         return -1;
/*     */       }
/* 240 */       aIndex++;
/* 241 */       bIndex++;
/*     */     } 
/*     */     
/* 244 */     return aLen - bLen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int indexOf(ByteBuf buffer, int fromIndex, int toIndex, byte value) {
/* 252 */     if (fromIndex <= toIndex) {
/* 253 */       return firstIndexOf(buffer, fromIndex, toIndex, value);
/*     */     }
/* 255 */     return lastIndexOf(buffer, fromIndex, toIndex, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static short swapShort(short value) {
/* 263 */     return Short.reverseBytes(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int swapMedium(int value) {
/* 270 */     int swapped = value << 16 & 0xFF0000 | value & 0xFF00 | value >>> 16 & 0xFF;
/* 271 */     if ((swapped & 0x800000) != 0) {
/* 272 */       swapped |= 0xFF000000;
/*     */     }
/* 274 */     return swapped;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int swapInt(int value) {
/* 281 */     return Integer.reverseBytes(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long swapLong(long value) {
/* 288 */     return Long.reverseBytes(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf readBytes(ByteBufAllocator alloc, ByteBuf buffer, int length) {
/* 295 */     boolean release = true;
/* 296 */     ByteBuf dst = alloc.buffer(length);
/*     */     try {
/* 298 */       buffer.readBytes(dst);
/* 299 */       release = false;
/* 300 */       return dst;
/*     */     } finally {
/* 302 */       if (release) {
/* 303 */         dst.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int firstIndexOf(ByteBuf buffer, int fromIndex, int toIndex, byte value) {
/* 309 */     fromIndex = Math.max(fromIndex, 0);
/* 310 */     if (fromIndex >= toIndex || buffer.capacity() == 0) {
/* 311 */       return -1;
/*     */     }
/*     */     
/* 314 */     for (int i = fromIndex; i < toIndex; i++) {
/* 315 */       if (buffer.getByte(i) == value) {
/* 316 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 320 */     return -1;
/*     */   }
/*     */   
/*     */   private static int lastIndexOf(ByteBuf buffer, int fromIndex, int toIndex, byte value) {
/* 324 */     fromIndex = Math.min(fromIndex, buffer.capacity());
/* 325 */     if (fromIndex < 0 || buffer.capacity() == 0) {
/* 326 */       return -1;
/*     */     }
/*     */     
/* 329 */     for (int i = fromIndex - 1; i >= toIndex; i--) {
/* 330 */       if (buffer.getByte(i) == value) {
/* 331 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 335 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf encodeString(ByteBufAllocator alloc, CharBuffer src, Charset charset) {
/* 343 */     return encodeString0(alloc, false, src, charset);
/*     */   }
/*     */   static ByteBuf encodeString0(ByteBufAllocator alloc, boolean enforceHeap, CharBuffer src, Charset charset) {
/*     */     ByteBuf dst;
/* 347 */     CharsetEncoder encoder = CharsetUtil.getEncoder(charset);
/* 348 */     int length = (int)(src.remaining() * encoder.maxBytesPerChar());
/* 349 */     boolean release = true;
/*     */     
/* 351 */     if (enforceHeap) {
/* 352 */       dst = alloc.heapBuffer(length);
/*     */     } else {
/* 354 */       dst = alloc.buffer(length);
/*     */     } 
/*     */     try {
/* 357 */       ByteBuffer dstBuf = dst.internalNioBuffer(0, length);
/* 358 */       int pos = dstBuf.position();
/* 359 */       CoderResult cr = encoder.encode(src, dstBuf, true);
/* 360 */       if (!cr.isUnderflow()) {
/* 361 */         cr.throwException();
/*     */       }
/* 363 */       cr = encoder.flush(dstBuf);
/* 364 */       if (!cr.isUnderflow()) {
/* 365 */         cr.throwException();
/*     */       }
/* 367 */       dst.writerIndex(dst.writerIndex() + dstBuf.position() - pos);
/* 368 */       release = false;
/* 369 */       return dst;
/* 370 */     } catch (CharacterCodingException x) {
/* 371 */       throw new IllegalStateException(x);
/*     */     } finally {
/* 373 */       if (release) {
/* 374 */         dst.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   static String decodeString(ByteBuffer src, Charset charset) {
/* 380 */     CharsetDecoder decoder = CharsetUtil.getDecoder(charset);
/* 381 */     CharBuffer dst = CharBuffer.allocate((int)(src.remaining() * decoder.maxCharsPerByte()));
/*     */     
/*     */     try {
/* 384 */       CoderResult cr = decoder.decode(src, dst, true);
/* 385 */       if (!cr.isUnderflow()) {
/* 386 */         cr.throwException();
/*     */       }
/* 388 */       cr = decoder.flush(dst);
/* 389 */       if (!cr.isUnderflow()) {
/* 390 */         cr.throwException();
/*     */       }
/* 392 */     } catch (CharacterCodingException x) {
/* 393 */       throw new IllegalStateException(x);
/*     */     } 
/* 395 */     return dst.flip().toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf threadLocalDirectBuffer() {
/* 404 */     if (THREAD_LOCAL_BUFFER_SIZE <= 0) {
/* 405 */       return null;
/*     */     }
/*     */     
/* 408 */     if (PlatformDependent.hasUnsafe()) {
/* 409 */       return ThreadLocalUnsafeDirectByteBuf.newInstance();
/*     */     }
/* 411 */     return ThreadLocalDirectByteBuf.newInstance();
/*     */   }
/*     */   
/*     */   static final class ThreadLocalUnsafeDirectByteBuf
/*     */     extends UnpooledUnsafeDirectByteBuf
/*     */   {
/* 417 */     private static final Recycler<ThreadLocalUnsafeDirectByteBuf> RECYCLER = new Recycler<ThreadLocalUnsafeDirectByteBuf>()
/*     */       {
/*     */         protected ByteBufUtil.ThreadLocalUnsafeDirectByteBuf newObject(Recycler.Handle handle)
/*     */         {
/* 421 */           return new ByteBufUtil.ThreadLocalUnsafeDirectByteBuf(handle);
/*     */         }
/*     */       };
/*     */     private final Recycler.Handle handle;
/*     */     static ThreadLocalUnsafeDirectByteBuf newInstance() {
/* 426 */       ThreadLocalUnsafeDirectByteBuf buf = (ThreadLocalUnsafeDirectByteBuf)RECYCLER.get();
/* 427 */       buf.setRefCnt(1);
/* 428 */       return buf;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private ThreadLocalUnsafeDirectByteBuf(Recycler.Handle handle) {
/* 434 */       super(UnpooledByteBufAllocator.DEFAULT, 256, 2147483647);
/* 435 */       this.handle = handle;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void deallocate() {
/* 440 */       if (capacity() > ByteBufUtil.THREAD_LOCAL_BUFFER_SIZE) {
/* 441 */         super.deallocate();
/*     */       } else {
/* 443 */         clear();
/* 444 */         RECYCLER.recycle(this, this.handle);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static final class ThreadLocalDirectByteBuf
/*     */     extends UnpooledDirectByteBuf {
/* 451 */     private static final Recycler<ThreadLocalDirectByteBuf> RECYCLER = new Recycler<ThreadLocalDirectByteBuf>()
/*     */       {
/*     */         protected ByteBufUtil.ThreadLocalDirectByteBuf newObject(Recycler.Handle handle) {
/* 454 */           return new ByteBufUtil.ThreadLocalDirectByteBuf(handle);
/*     */         }
/*     */       };
/*     */     private final Recycler.Handle handle;
/*     */     static ThreadLocalDirectByteBuf newInstance() {
/* 459 */       ThreadLocalDirectByteBuf buf = (ThreadLocalDirectByteBuf)RECYCLER.get();
/* 460 */       buf.setRefCnt(1);
/* 461 */       return buf;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private ThreadLocalDirectByteBuf(Recycler.Handle handle) {
/* 467 */       super(UnpooledByteBufAllocator.DEFAULT, 256, 2147483647);
/* 468 */       this.handle = handle;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void deallocate() {
/* 473 */       if (capacity() > ByteBufUtil.THREAD_LOCAL_BUFFER_SIZE) {
/* 474 */         super.deallocate();
/*     */       } else {
/* 476 */         clear();
/* 477 */         RECYCLER.recycle(this, this.handle);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\buffer\ByteBufUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */