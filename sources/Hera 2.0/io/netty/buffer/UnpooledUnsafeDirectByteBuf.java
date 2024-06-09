/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.GatheringByteChannel;
/*     */ import java.nio.channels.ScatteringByteChannel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnpooledUnsafeDirectByteBuf
/*     */   extends AbstractReferenceCountedByteBuf
/*     */ {
/*  36 */   private static final boolean NATIVE_ORDER = (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
/*     */ 
/*     */   
/*     */   private final ByteBufAllocator alloc;
/*     */   
/*     */   private long memoryAddress;
/*     */   
/*     */   private ByteBuffer buffer;
/*     */   
/*     */   private ByteBuffer tmpNioBuf;
/*     */   
/*     */   private int capacity;
/*     */   
/*     */   private boolean doNotFree;
/*     */ 
/*     */   
/*     */   protected UnpooledUnsafeDirectByteBuf(ByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
/*  53 */     super(maxCapacity);
/*  54 */     if (alloc == null) {
/*  55 */       throw new NullPointerException("alloc");
/*     */     }
/*  57 */     if (initialCapacity < 0) {
/*  58 */       throw new IllegalArgumentException("initialCapacity: " + initialCapacity);
/*     */     }
/*  60 */     if (maxCapacity < 0) {
/*  61 */       throw new IllegalArgumentException("maxCapacity: " + maxCapacity);
/*     */     }
/*  63 */     if (initialCapacity > maxCapacity) {
/*  64 */       throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[] { Integer.valueOf(initialCapacity), Integer.valueOf(maxCapacity) }));
/*     */     }
/*     */ 
/*     */     
/*  68 */     this.alloc = alloc;
/*  69 */     setByteBuffer(allocateDirect(initialCapacity));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected UnpooledUnsafeDirectByteBuf(ByteBufAllocator alloc, ByteBuffer initialBuffer, int maxCapacity) {
/*  78 */     super(maxCapacity);
/*  79 */     if (alloc == null) {
/*  80 */       throw new NullPointerException("alloc");
/*     */     }
/*  82 */     if (initialBuffer == null) {
/*  83 */       throw new NullPointerException("initialBuffer");
/*     */     }
/*  85 */     if (!initialBuffer.isDirect()) {
/*  86 */       throw new IllegalArgumentException("initialBuffer is not a direct buffer.");
/*     */     }
/*  88 */     if (initialBuffer.isReadOnly()) {
/*  89 */       throw new IllegalArgumentException("initialBuffer is a read-only buffer.");
/*     */     }
/*     */     
/*  92 */     int initialCapacity = initialBuffer.remaining();
/*  93 */     if (initialCapacity > maxCapacity) {
/*  94 */       throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[] { Integer.valueOf(initialCapacity), Integer.valueOf(maxCapacity) }));
/*     */     }
/*     */ 
/*     */     
/*  98 */     this.alloc = alloc;
/*  99 */     this.doNotFree = true;
/* 100 */     setByteBuffer(initialBuffer.slice().order(ByteOrder.BIG_ENDIAN));
/* 101 */     writerIndex(initialCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ByteBuffer allocateDirect(int initialCapacity) {
/* 108 */     return ByteBuffer.allocateDirect(initialCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void freeDirect(ByteBuffer buffer) {
/* 115 */     PlatformDependent.freeDirectBuffer(buffer);
/*     */   }
/*     */   
/*     */   private void setByteBuffer(ByteBuffer buffer) {
/* 119 */     ByteBuffer oldBuffer = this.buffer;
/* 120 */     if (oldBuffer != null) {
/* 121 */       if (this.doNotFree) {
/* 122 */         this.doNotFree = false;
/*     */       } else {
/* 124 */         freeDirect(oldBuffer);
/*     */       } 
/*     */     }
/*     */     
/* 128 */     this.buffer = buffer;
/* 129 */     this.memoryAddress = PlatformDependent.directBufferAddress(buffer);
/* 130 */     this.tmpNioBuf = null;
/* 131 */     this.capacity = buffer.remaining();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirect() {
/* 136 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int capacity() {
/* 141 */     return this.capacity;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf capacity(int newCapacity) {
/* 146 */     ensureAccessible();
/* 147 */     if (newCapacity < 0 || newCapacity > maxCapacity()) {
/* 148 */       throw new IllegalArgumentException("newCapacity: " + newCapacity);
/*     */     }
/*     */     
/* 151 */     int readerIndex = readerIndex();
/* 152 */     int writerIndex = writerIndex();
/*     */     
/* 154 */     int oldCapacity = this.capacity;
/* 155 */     if (newCapacity > oldCapacity) {
/* 156 */       ByteBuffer oldBuffer = this.buffer;
/* 157 */       ByteBuffer newBuffer = allocateDirect(newCapacity);
/* 158 */       oldBuffer.position(0).limit(oldBuffer.capacity());
/* 159 */       newBuffer.position(0).limit(oldBuffer.capacity());
/* 160 */       newBuffer.put(oldBuffer);
/* 161 */       newBuffer.clear();
/* 162 */       setByteBuffer(newBuffer);
/* 163 */     } else if (newCapacity < oldCapacity) {
/* 164 */       ByteBuffer oldBuffer = this.buffer;
/* 165 */       ByteBuffer newBuffer = allocateDirect(newCapacity);
/* 166 */       if (readerIndex < newCapacity) {
/* 167 */         if (writerIndex > newCapacity) {
/* 168 */           writerIndex(writerIndex = newCapacity);
/*     */         }
/* 170 */         oldBuffer.position(readerIndex).limit(writerIndex);
/* 171 */         newBuffer.position(readerIndex).limit(writerIndex);
/* 172 */         newBuffer.put(oldBuffer);
/* 173 */         newBuffer.clear();
/*     */       } else {
/* 175 */         setIndex(newCapacity, newCapacity);
/*     */       } 
/* 177 */       setByteBuffer(newBuffer);
/*     */     } 
/* 179 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufAllocator alloc() {
/* 184 */     return this.alloc;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteOrder order() {
/* 189 */     return ByteOrder.BIG_ENDIAN;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasArray() {
/* 194 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] array() {
/* 199 */     throw new UnsupportedOperationException("direct buffer");
/*     */   }
/*     */ 
/*     */   
/*     */   public int arrayOffset() {
/* 204 */     throw new UnsupportedOperationException("direct buffer");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMemoryAddress() {
/* 209 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public long memoryAddress() {
/* 214 */     return this.memoryAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte _getByte(int index) {
/* 219 */     return PlatformDependent.getByte(addr(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShort(int index) {
/* 224 */     short v = PlatformDependent.getShort(addr(index));
/* 225 */     return NATIVE_ORDER ? v : Short.reverseBytes(v);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMedium(int index) {
/* 230 */     long addr = addr(index);
/* 231 */     return (PlatformDependent.getByte(addr) & 0xFF) << 16 | (PlatformDependent.getByte(addr + 1L) & 0xFF) << 8 | PlatformDependent.getByte(addr + 2L) & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _getInt(int index) {
/* 238 */     int v = PlatformDependent.getInt(addr(index));
/* 239 */     return NATIVE_ORDER ? v : Integer.reverseBytes(v);
/*     */   }
/*     */ 
/*     */   
/*     */   protected long _getLong(int index) {
/* 244 */     long v = PlatformDependent.getLong(addr(index));
/* 245 */     return NATIVE_ORDER ? v : Long.reverseBytes(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/* 250 */     checkIndex(index, length);
/* 251 */     if (dst == null) {
/* 252 */       throw new NullPointerException("dst");
/*     */     }
/* 254 */     if (dstIndex < 0 || dstIndex > dst.capacity() - length) {
/* 255 */       throw new IndexOutOfBoundsException("dstIndex: " + dstIndex);
/*     */     }
/*     */     
/* 258 */     if (dst.hasMemoryAddress()) {
/* 259 */       PlatformDependent.copyMemory(addr(index), dst.memoryAddress() + dstIndex, length);
/* 260 */     } else if (dst.hasArray()) {
/* 261 */       PlatformDependent.copyMemory(addr(index), dst.array(), dst.arrayOffset() + dstIndex, length);
/*     */     } else {
/* 263 */       dst.setBytes(dstIndex, this, index, length);
/*     */     } 
/* 265 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/* 270 */     checkIndex(index, length);
/* 271 */     if (dst == null) {
/* 272 */       throw new NullPointerException("dst");
/*     */     }
/* 274 */     if (dstIndex < 0 || dstIndex > dst.length - length) {
/* 275 */       throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", new Object[] { Integer.valueOf(dstIndex), Integer.valueOf(length), Integer.valueOf(dst.length) }));
/*     */     }
/*     */ 
/*     */     
/* 279 */     if (length != 0) {
/* 280 */       PlatformDependent.copyMemory(addr(index), dst, dstIndex, length);
/*     */     }
/* 282 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuffer dst) {
/* 287 */     getBytes(index, dst, false);
/* 288 */     return this;
/*     */   }
/*     */   private void getBytes(int index, ByteBuffer dst, boolean internal) {
/*     */     ByteBuffer tmpBuf;
/* 292 */     checkIndex(index);
/* 293 */     if (dst == null) {
/* 294 */       throw new NullPointerException("dst");
/*     */     }
/*     */     
/* 297 */     int bytesToCopy = Math.min(capacity() - index, dst.remaining());
/*     */     
/* 299 */     if (internal) {
/* 300 */       tmpBuf = internalNioBuffer();
/*     */     } else {
/* 302 */       tmpBuf = this.buffer.duplicate();
/*     */     } 
/* 304 */     tmpBuf.clear().position(index).limit(index + bytesToCopy);
/* 305 */     dst.put(tmpBuf);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuffer dst) {
/* 310 */     int length = dst.remaining();
/* 311 */     checkReadableBytes(length);
/* 312 */     getBytes(this.readerIndex, dst, true);
/* 313 */     this.readerIndex += length;
/* 314 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setByte(int index, int value) {
/* 319 */     PlatformDependent.putByte(addr(index), (byte)value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShort(int index, int value) {
/* 324 */     PlatformDependent.putShort(addr(index), NATIVE_ORDER ? (short)value : Short.reverseBytes((short)value));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMedium(int index, int value) {
/* 329 */     long addr = addr(index);
/* 330 */     PlatformDependent.putByte(addr, (byte)(value >>> 16));
/* 331 */     PlatformDependent.putByte(addr + 1L, (byte)(value >>> 8));
/* 332 */     PlatformDependent.putByte(addr + 2L, (byte)value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setInt(int index, int value) {
/* 337 */     PlatformDependent.putInt(addr(index), NATIVE_ORDER ? value : Integer.reverseBytes(value));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLong(int index, long value) {
/* 342 */     PlatformDependent.putLong(addr(index), NATIVE_ORDER ? value : Long.reverseBytes(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/* 347 */     checkIndex(index, length);
/* 348 */     if (src == null) {
/* 349 */       throw new NullPointerException("src");
/*     */     }
/* 351 */     if (srcIndex < 0 || srcIndex > src.capacity() - length) {
/* 352 */       throw new IndexOutOfBoundsException("srcIndex: " + srcIndex);
/*     */     }
/*     */     
/* 355 */     if (length != 0) {
/* 356 */       if (src.hasMemoryAddress()) {
/* 357 */         PlatformDependent.copyMemory(src.memoryAddress() + srcIndex, addr(index), length);
/* 358 */       } else if (src.hasArray()) {
/* 359 */         PlatformDependent.copyMemory(src.array(), src.arrayOffset() + srcIndex, addr(index), length);
/*     */       } else {
/* 361 */         src.getBytes(srcIndex, this, index, length);
/*     */       } 
/*     */     }
/* 364 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/* 369 */     checkIndex(index, length);
/* 370 */     if (length != 0) {
/* 371 */       PlatformDependent.copyMemory(src, srcIndex, addr(index), length);
/*     */     }
/* 373 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuffer src) {
/* 378 */     ensureAccessible();
/* 379 */     ByteBuffer tmpBuf = internalNioBuffer();
/* 380 */     if (src == tmpBuf) {
/* 381 */       src = src.duplicate();
/*     */     }
/*     */     
/* 384 */     tmpBuf.clear().position(index).limit(index + src.remaining());
/* 385 */     tmpBuf.put(src);
/* 386 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
/* 391 */     ensureAccessible();
/* 392 */     if (length != 0) {
/* 393 */       byte[] tmp = new byte[length];
/* 394 */       PlatformDependent.copyMemory(addr(index), tmp, 0, length);
/* 395 */       out.write(tmp);
/*     */     } 
/* 397 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
/* 402 */     return getBytes(index, out, length, false);
/*     */   }
/*     */   private int getBytes(int index, GatheringByteChannel out, int length, boolean internal) throws IOException {
/*     */     ByteBuffer tmpBuf;
/* 406 */     ensureAccessible();
/* 407 */     if (length == 0) {
/* 408 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 412 */     if (internal) {
/* 413 */       tmpBuf = internalNioBuffer();
/*     */     } else {
/* 415 */       tmpBuf = this.buffer.duplicate();
/*     */     } 
/* 417 */     tmpBuf.clear().position(index).limit(index + length);
/* 418 */     return out.write(tmpBuf);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readBytes(GatheringByteChannel out, int length) throws IOException {
/* 423 */     checkReadableBytes(length);
/* 424 */     int readBytes = getBytes(this.readerIndex, out, length, true);
/* 425 */     this.readerIndex += readBytes;
/* 426 */     return readBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, InputStream in, int length) throws IOException {
/* 431 */     checkIndex(index, length);
/* 432 */     byte[] tmp = new byte[length];
/* 433 */     int readBytes = in.read(tmp);
/* 434 */     if (readBytes > 0) {
/* 435 */       PlatformDependent.copyMemory(tmp, 0, addr(index), readBytes);
/*     */     }
/* 437 */     return readBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
/* 442 */     ensureAccessible();
/* 443 */     ByteBuffer tmpBuf = internalNioBuffer();
/* 444 */     tmpBuf.clear().position(index).limit(index + length);
/*     */     try {
/* 446 */       return in.read(tmpBuf);
/* 447 */     } catch (ClosedChannelException ignored) {
/* 448 */       return -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int nioBufferCount() {
/* 454 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int index, int length) {
/* 459 */     return new ByteBuffer[] { nioBuffer(index, length) };
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf copy(int index, int length) {
/* 464 */     checkIndex(index, length);
/* 465 */     ByteBuf copy = alloc().directBuffer(length, maxCapacity());
/* 466 */     if (length != 0) {
/* 467 */       if (copy.hasMemoryAddress()) {
/* 468 */         PlatformDependent.copyMemory(addr(index), copy.memoryAddress(), length);
/* 469 */         copy.setIndex(0, length);
/*     */       } else {
/* 471 */         copy.writeBytes(this, index, length);
/*     */       } 
/*     */     }
/* 474 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int index, int length) {
/* 479 */     checkIndex(index, length);
/* 480 */     return (ByteBuffer)internalNioBuffer().clear().position(index).limit(index + length);
/*     */   }
/*     */   
/*     */   private ByteBuffer internalNioBuffer() {
/* 484 */     ByteBuffer tmpNioBuf = this.tmpNioBuf;
/* 485 */     if (tmpNioBuf == null) {
/* 486 */       this.tmpNioBuf = tmpNioBuf = this.buffer.duplicate();
/*     */     }
/* 488 */     return tmpNioBuf;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer nioBuffer(int index, int length) {
/* 493 */     checkIndex(index, length);
/* 494 */     return ((ByteBuffer)this.buffer.duplicate().position(index).limit(index + length)).slice();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void deallocate() {
/* 499 */     ByteBuffer buffer = this.buffer;
/* 500 */     if (buffer == null) {
/*     */       return;
/*     */     }
/*     */     
/* 504 */     this.buffer = null;
/*     */     
/* 506 */     if (!this.doNotFree) {
/* 507 */       freeDirect(buffer);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf unwrap() {
/* 513 */     return null;
/*     */   }
/*     */   
/*     */   long addr(int index) {
/* 517 */     return this.memoryAddress + index;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SwappedByteBuf newSwappedByteBuf() {
/* 522 */     return new UnsafeDirectSwappedByteBuf(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\buffer\UnpooledUnsafeDirectByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */