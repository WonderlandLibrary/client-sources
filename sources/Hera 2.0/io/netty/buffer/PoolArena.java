/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class PoolArena<T>
/*     */ {
/*     */   static final int numTinySubpagePools = 32;
/*     */   final PooledByteBufAllocator parent;
/*     */   private final int maxOrder;
/*     */   final int pageSize;
/*     */   final int pageShifts;
/*     */   final int chunkSize;
/*     */   final int subpageOverflowMask;
/*     */   final int numSmallSubpagePools;
/*     */   private final PoolSubpage<T>[] tinySubpagePools;
/*     */   private final PoolSubpage<T>[] smallSubpagePools;
/*     */   private final PoolChunkList<T> q050;
/*     */   private final PoolChunkList<T> q025;
/*     */   private final PoolChunkList<T> q000;
/*     */   private final PoolChunkList<T> qInit;
/*     */   private final PoolChunkList<T> q075;
/*     */   private final PoolChunkList<T> q100;
/*     */   
/*     */   protected PoolArena(PooledByteBufAllocator parent, int pageSize, int maxOrder, int pageShifts, int chunkSize) {
/*  50 */     this.parent = parent;
/*  51 */     this.pageSize = pageSize;
/*  52 */     this.maxOrder = maxOrder;
/*  53 */     this.pageShifts = pageShifts;
/*  54 */     this.chunkSize = chunkSize;
/*  55 */     this.subpageOverflowMask = pageSize - 1 ^ 0xFFFFFFFF;
/*  56 */     this.tinySubpagePools = newSubpagePoolArray(32); int i;
/*  57 */     for (i = 0; i < this.tinySubpagePools.length; i++) {
/*  58 */       this.tinySubpagePools[i] = newSubpagePoolHead(pageSize);
/*     */     }
/*     */     
/*  61 */     this.numSmallSubpagePools = pageShifts - 9;
/*  62 */     this.smallSubpagePools = newSubpagePoolArray(this.numSmallSubpagePools);
/*  63 */     for (i = 0; i < this.smallSubpagePools.length; i++) {
/*  64 */       this.smallSubpagePools[i] = newSubpagePoolHead(pageSize);
/*     */     }
/*     */     
/*  67 */     this.q100 = new PoolChunkList<T>(this, null, 100, 2147483647);
/*  68 */     this.q075 = new PoolChunkList<T>(this, this.q100, 75, 100);
/*  69 */     this.q050 = new PoolChunkList<T>(this, this.q075, 50, 100);
/*  70 */     this.q025 = new PoolChunkList<T>(this, this.q050, 25, 75);
/*  71 */     this.q000 = new PoolChunkList<T>(this, this.q025, 1, 50);
/*  72 */     this.qInit = new PoolChunkList<T>(this, this.q000, -2147483648, 25);
/*     */     
/*  74 */     this.q100.prevList = this.q075;
/*  75 */     this.q075.prevList = this.q050;
/*  76 */     this.q050.prevList = this.q025;
/*  77 */     this.q025.prevList = this.q000;
/*  78 */     this.q000.prevList = null;
/*  79 */     this.qInit.prevList = this.qInit;
/*     */   }
/*     */   
/*     */   private PoolSubpage<T> newSubpagePoolHead(int pageSize) {
/*  83 */     PoolSubpage<T> head = new PoolSubpage<T>(pageSize);
/*  84 */     head.prev = head;
/*  85 */     head.next = head;
/*  86 */     return head;
/*     */   }
/*     */ 
/*     */   
/*     */   private PoolSubpage<T>[] newSubpagePoolArray(int size) {
/*  91 */     return (PoolSubpage<T>[])new PoolSubpage[size];
/*     */   }
/*     */   
/*     */   abstract boolean isDirect();
/*     */   
/*     */   PooledByteBuf<T> allocate(PoolThreadCache cache, int reqCapacity, int maxCapacity) {
/*  97 */     PooledByteBuf<T> buf = newByteBuf(maxCapacity);
/*  98 */     allocate(cache, buf, reqCapacity);
/*  99 */     return buf;
/*     */   }
/*     */   
/*     */   static int tinyIdx(int normCapacity) {
/* 103 */     return normCapacity >>> 4;
/*     */   }
/*     */   
/*     */   static int smallIdx(int normCapacity) {
/* 107 */     int tableIdx = 0;
/* 108 */     int i = normCapacity >>> 10;
/* 109 */     while (i != 0) {
/* 110 */       i >>>= 1;
/* 111 */       tableIdx++;
/*     */     } 
/* 113 */     return tableIdx;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isTinyOrSmall(int normCapacity) {
/* 118 */     return ((normCapacity & this.subpageOverflowMask) == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean isTiny(int normCapacity) {
/* 123 */     return ((normCapacity & 0xFFFFFE00) == 0);
/*     */   }
/*     */   
/*     */   private void allocate(PoolThreadCache cache, PooledByteBuf<T> buf, int reqCapacity) {
/* 127 */     int normCapacity = normalizeCapacity(reqCapacity);
/* 128 */     if (isTinyOrSmall(normCapacity)) {
/*     */       int tableIdx;
/*     */       PoolSubpage<T>[] table;
/* 131 */       if (isTiny(normCapacity)) {
/* 132 */         if (cache.allocateTiny(this, buf, reqCapacity, normCapacity)) {
/*     */           return;
/*     */         }
/*     */         
/* 136 */         tableIdx = tinyIdx(normCapacity);
/* 137 */         table = this.tinySubpagePools;
/*     */       } else {
/* 139 */         if (cache.allocateSmall(this, buf, reqCapacity, normCapacity)) {
/*     */           return;
/*     */         }
/*     */         
/* 143 */         tableIdx = smallIdx(normCapacity);
/* 144 */         table = this.smallSubpagePools;
/*     */       } 
/*     */       
/* 147 */       synchronized (this) {
/* 148 */         PoolSubpage<T> head = table[tableIdx];
/* 149 */         PoolSubpage<T> s = head.next;
/* 150 */         if (s != head) {
/* 151 */           assert s.doNotDestroy && s.elemSize == normCapacity;
/* 152 */           long handle = s.allocate();
/* 153 */           assert handle >= 0L;
/* 154 */           s.chunk.initBufWithSubpage(buf, handle, reqCapacity);
/*     */           return;
/*     */         } 
/*     */       } 
/* 158 */     } else if (normCapacity <= this.chunkSize) {
/* 159 */       if (cache.allocateNormal(this, buf, reqCapacity, normCapacity)) {
/*     */         return;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 165 */       allocateHuge(buf, reqCapacity);
/*     */       return;
/*     */     } 
/* 168 */     allocateNormal(buf, reqCapacity, normCapacity);
/*     */   }
/*     */   
/*     */   private synchronized void allocateNormal(PooledByteBuf<T> buf, int reqCapacity, int normCapacity) {
/* 172 */     if (this.q050.allocate(buf, reqCapacity, normCapacity) || this.q025.allocate(buf, reqCapacity, normCapacity) || this.q000.allocate(buf, reqCapacity, normCapacity) || this.qInit.allocate(buf, reqCapacity, normCapacity) || this.q075.allocate(buf, reqCapacity, normCapacity) || this.q100.allocate(buf, reqCapacity, normCapacity)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 179 */     PoolChunk<T> c = newChunk(this.pageSize, this.maxOrder, this.pageShifts, this.chunkSize);
/* 180 */     long handle = c.allocate(normCapacity);
/* 181 */     assert handle > 0L;
/* 182 */     c.initBuf(buf, handle, reqCapacity);
/* 183 */     this.qInit.add(c);
/*     */   }
/*     */   
/*     */   private void allocateHuge(PooledByteBuf<T> buf, int reqCapacity) {
/* 187 */     buf.initUnpooled(newUnpooledChunk(reqCapacity), reqCapacity);
/*     */   }
/*     */   
/*     */   void free(PoolChunk<T> chunk, long handle, int normCapacity) {
/* 191 */     if (chunk.unpooled) {
/* 192 */       destroyChunk(chunk);
/*     */     } else {
/* 194 */       PoolThreadCache cache = (PoolThreadCache)this.parent.threadCache.get();
/* 195 */       if (cache.add(this, chunk, handle, normCapacity)) {
/*     */         return;
/*     */       }
/*     */       
/* 199 */       synchronized (this) {
/* 200 */         chunk.parent.free(chunk, handle);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   PoolSubpage<T> findSubpagePoolHead(int elemSize) {
/*     */     int tableIdx;
/*     */     PoolSubpage<T>[] table;
/* 208 */     if (isTiny(elemSize)) {
/* 209 */       tableIdx = elemSize >>> 4;
/* 210 */       table = this.tinySubpagePools;
/*     */     } else {
/* 212 */       tableIdx = 0;
/* 213 */       elemSize >>>= 10;
/* 214 */       while (elemSize != 0) {
/* 215 */         elemSize >>>= 1;
/* 216 */         tableIdx++;
/*     */       } 
/* 218 */       table = this.smallSubpagePools;
/*     */     } 
/*     */     
/* 221 */     return table[tableIdx];
/*     */   }
/*     */   
/*     */   int normalizeCapacity(int reqCapacity) {
/* 225 */     if (reqCapacity < 0) {
/* 226 */       throw new IllegalArgumentException("capacity: " + reqCapacity + " (expected: 0+)");
/*     */     }
/* 228 */     if (reqCapacity >= this.chunkSize) {
/* 229 */       return reqCapacity;
/*     */     }
/*     */     
/* 232 */     if (!isTiny(reqCapacity)) {
/*     */ 
/*     */       
/* 235 */       int normalizedCapacity = reqCapacity;
/* 236 */       normalizedCapacity--;
/* 237 */       normalizedCapacity |= normalizedCapacity >>> 1;
/* 238 */       normalizedCapacity |= normalizedCapacity >>> 2;
/* 239 */       normalizedCapacity |= normalizedCapacity >>> 4;
/* 240 */       normalizedCapacity |= normalizedCapacity >>> 8;
/* 241 */       normalizedCapacity |= normalizedCapacity >>> 16;
/* 242 */       normalizedCapacity++;
/*     */       
/* 244 */       if (normalizedCapacity < 0) {
/* 245 */         normalizedCapacity >>>= 1;
/*     */       }
/*     */       
/* 248 */       return normalizedCapacity;
/*     */     } 
/*     */ 
/*     */     
/* 252 */     if ((reqCapacity & 0xF) == 0) {
/* 253 */       return reqCapacity;
/*     */     }
/*     */     
/* 256 */     return (reqCapacity & 0xFFFFFFF0) + 16;
/*     */   }
/*     */   
/*     */   void reallocate(PooledByteBuf<T> buf, int newCapacity, boolean freeOldMemory) {
/* 260 */     if (newCapacity < 0 || newCapacity > buf.maxCapacity()) {
/* 261 */       throw new IllegalArgumentException("newCapacity: " + newCapacity);
/*     */     }
/*     */     
/* 264 */     int oldCapacity = buf.length;
/* 265 */     if (oldCapacity == newCapacity) {
/*     */       return;
/*     */     }
/*     */     
/* 269 */     PoolChunk<T> oldChunk = buf.chunk;
/* 270 */     long oldHandle = buf.handle;
/* 271 */     T oldMemory = buf.memory;
/* 272 */     int oldOffset = buf.offset;
/* 273 */     int oldMaxLength = buf.maxLength;
/* 274 */     int readerIndex = buf.readerIndex();
/* 275 */     int writerIndex = buf.writerIndex();
/*     */     
/* 277 */     allocate((PoolThreadCache)this.parent.threadCache.get(), buf, newCapacity);
/* 278 */     if (newCapacity > oldCapacity) {
/* 279 */       memoryCopy(oldMemory, oldOffset, buf.memory, buf.offset, oldCapacity);
/*     */     
/*     */     }
/* 282 */     else if (newCapacity < oldCapacity) {
/* 283 */       if (readerIndex < newCapacity) {
/* 284 */         if (writerIndex > newCapacity) {
/* 285 */           writerIndex = newCapacity;
/*     */         }
/* 287 */         memoryCopy(oldMemory, oldOffset + readerIndex, buf.memory, buf.offset + readerIndex, writerIndex - readerIndex);
/*     */       }
/*     */       else {
/*     */         
/* 291 */         readerIndex = writerIndex = newCapacity;
/*     */       } 
/*     */     } 
/*     */     
/* 295 */     buf.setIndex(readerIndex, writerIndex);
/*     */     
/* 297 */     if (freeOldMemory) {
/* 298 */       free(oldChunk, oldHandle, oldMaxLength);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract PoolChunk<T> newChunk(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */   
/*     */   protected abstract PoolChunk<T> newUnpooledChunk(int paramInt);
/*     */   
/*     */   public synchronized String toString() {
/* 309 */     StringBuilder buf = new StringBuilder();
/* 310 */     buf.append("Chunk(s) at 0~25%:");
/* 311 */     buf.append(StringUtil.NEWLINE);
/* 312 */     buf.append(this.qInit);
/* 313 */     buf.append(StringUtil.NEWLINE);
/* 314 */     buf.append("Chunk(s) at 0~50%:");
/* 315 */     buf.append(StringUtil.NEWLINE);
/* 316 */     buf.append(this.q000);
/* 317 */     buf.append(StringUtil.NEWLINE);
/* 318 */     buf.append("Chunk(s) at 25~75%:");
/* 319 */     buf.append(StringUtil.NEWLINE);
/* 320 */     buf.append(this.q025);
/* 321 */     buf.append(StringUtil.NEWLINE);
/* 322 */     buf.append("Chunk(s) at 50~100%:");
/* 323 */     buf.append(StringUtil.NEWLINE);
/* 324 */     buf.append(this.q050);
/* 325 */     buf.append(StringUtil.NEWLINE);
/* 326 */     buf.append("Chunk(s) at 75~100%:");
/* 327 */     buf.append(StringUtil.NEWLINE);
/* 328 */     buf.append(this.q075);
/* 329 */     buf.append(StringUtil.NEWLINE);
/* 330 */     buf.append("Chunk(s) at 100%:");
/* 331 */     buf.append(StringUtil.NEWLINE);
/* 332 */     buf.append(this.q100);
/* 333 */     buf.append(StringUtil.NEWLINE);
/* 334 */     buf.append("tiny subpages:"); int i;
/* 335 */     for (i = 1; i < this.tinySubpagePools.length; i++) {
/* 336 */       PoolSubpage<T> head = this.tinySubpagePools[i];
/* 337 */       if (head.next != head) {
/*     */ 
/*     */ 
/*     */         
/* 341 */         buf.append(StringUtil.NEWLINE);
/* 342 */         buf.append(i);
/* 343 */         buf.append(": ");
/* 344 */         PoolSubpage<T> s = head.next;
/*     */         do {
/* 346 */           buf.append(s);
/* 347 */           s = s.next;
/* 348 */         } while (s != head);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 353 */     buf.append(StringUtil.NEWLINE);
/* 354 */     buf.append("small subpages:");
/* 355 */     for (i = 1; i < this.smallSubpagePools.length; i++) {
/* 356 */       PoolSubpage<T> head = this.smallSubpagePools[i];
/* 357 */       if (head.next != head) {
/*     */ 
/*     */ 
/*     */         
/* 361 */         buf.append(StringUtil.NEWLINE);
/* 362 */         buf.append(i);
/* 363 */         buf.append(": ");
/* 364 */         PoolSubpage<T> s = head.next;
/*     */         do {
/* 366 */           buf.append(s);
/* 367 */           s = s.next;
/* 368 */         } while (s != head);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 373 */     buf.append(StringUtil.NEWLINE);
/*     */     
/* 375 */     return buf.toString();
/*     */   }
/*     */   protected abstract PooledByteBuf<T> newByteBuf(int paramInt);
/*     */   protected abstract void memoryCopy(T paramT1, int paramInt1, T paramT2, int paramInt2, int paramInt3);
/*     */   protected abstract void destroyChunk(PoolChunk<T> paramPoolChunk);
/*     */   static final class HeapArena extends PoolArena<byte[]> { HeapArena(PooledByteBufAllocator parent, int pageSize, int maxOrder, int pageShifts, int chunkSize) {
/* 381 */       super(parent, pageSize, maxOrder, pageShifts, chunkSize);
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isDirect() {
/* 386 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     protected PoolChunk<byte[]> newChunk(int pageSize, int maxOrder, int pageShifts, int chunkSize) {
/* 391 */       return (PoolChunk)new PoolChunk<byte>(this, new byte[chunkSize], pageSize, maxOrder, pageShifts, chunkSize);
/*     */     }
/*     */ 
/*     */     
/*     */     protected PoolChunk<byte[]> newUnpooledChunk(int capacity) {
/* 396 */       return (PoolChunk)new PoolChunk<byte>(this, new byte[capacity], capacity);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void destroyChunk(PoolChunk<byte[]> chunk) {}
/*     */ 
/*     */ 
/*     */     
/*     */     protected PooledByteBuf<byte[]> newByteBuf(int maxCapacity) {
/* 406 */       return PooledHeapByteBuf.newInstance(maxCapacity);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void memoryCopy(byte[] src, int srcOffset, byte[] dst, int dstOffset, int length) {
/* 411 */       if (length == 0) {
/*     */         return;
/*     */       }
/*     */       
/* 415 */       System.arraycopy(src, srcOffset, dst, dstOffset, length);
/*     */     } }
/*     */ 
/*     */   
/*     */   static final class DirectArena
/*     */     extends PoolArena<ByteBuffer> {
/* 421 */     private static final boolean HAS_UNSAFE = PlatformDependent.hasUnsafe();
/*     */     
/*     */     DirectArena(PooledByteBufAllocator parent, int pageSize, int maxOrder, int pageShifts, int chunkSize) {
/* 424 */       super(parent, pageSize, maxOrder, pageShifts, chunkSize);
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isDirect() {
/* 429 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     protected PoolChunk<ByteBuffer> newChunk(int pageSize, int maxOrder, int pageShifts, int chunkSize) {
/* 434 */       return new PoolChunk<ByteBuffer>(this, ByteBuffer.allocateDirect(chunkSize), pageSize, maxOrder, pageShifts, chunkSize);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected PoolChunk<ByteBuffer> newUnpooledChunk(int capacity) {
/* 440 */       return new PoolChunk<ByteBuffer>(this, ByteBuffer.allocateDirect(capacity), capacity);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void destroyChunk(PoolChunk<ByteBuffer> chunk) {
/* 445 */       PlatformDependent.freeDirectBuffer((ByteBuffer)chunk.memory);
/*     */     }
/*     */ 
/*     */     
/*     */     protected PooledByteBuf<ByteBuffer> newByteBuf(int maxCapacity) {
/* 450 */       if (HAS_UNSAFE) {
/* 451 */         return PooledUnsafeDirectByteBuf.newInstance(maxCapacity);
/*     */       }
/* 453 */       return PooledDirectByteBuf.newInstance(maxCapacity);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void memoryCopy(ByteBuffer src, int srcOffset, ByteBuffer dst, int dstOffset, int length) {
/* 459 */       if (length == 0) {
/*     */         return;
/*     */       }
/*     */       
/* 463 */       if (HAS_UNSAFE) {
/* 464 */         PlatformDependent.copyMemory(PlatformDependent.directBufferAddress(src) + srcOffset, PlatformDependent.directBufferAddress(dst) + dstOffset, length);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 469 */         src = src.duplicate();
/* 470 */         dst = dst.duplicate();
/* 471 */         src.position(srcOffset).limit(srcOffset + length);
/* 472 */         dst.position(dstOffset);
/* 473 */         dst.put(src);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\buffer\PoolArena.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */