/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.FastThreadLocal;
/*     */ import io.netty.util.internal.InternalThreadLocalMap;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
/*     */ import java.util.concurrent.atomic.AtomicLongFieldUpdater;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ChannelOutboundBuffer
/*     */ {
/*  44 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChannelOutboundBuffer.class);
/*     */   
/*  46 */   private static final FastThreadLocal<ByteBuffer[]> NIO_BUFFERS = new FastThreadLocal<ByteBuffer[]>()
/*     */     {
/*     */       protected ByteBuffer[] initialValue() throws Exception {
/*  49 */         return new ByteBuffer[1024];
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*     */   private final Channel channel;
/*     */ 
/*     */   
/*     */   private Entry flushedEntry;
/*     */   
/*     */   private Entry unflushedEntry;
/*     */   
/*     */   private Entry tailEntry;
/*     */   
/*     */   private int flushed;
/*     */   
/*     */   private int nioBufferCount;
/*     */   
/*     */   private long nioBufferSize;
/*     */   
/*     */   private boolean inFail;
/*     */   
/*     */   private static final AtomicLongFieldUpdater<ChannelOutboundBuffer> TOTAL_PENDING_SIZE_UPDATER;
/*     */   
/*     */   private volatile long totalPendingSize;
/*     */   
/*     */   private static final AtomicIntegerFieldUpdater<ChannelOutboundBuffer> WRITABLE_UPDATER;
/*     */   
/*  78 */   private volatile int writable = 1;
/*     */ 
/*     */   
/*     */   static {
/*  82 */     AtomicIntegerFieldUpdater<ChannelOutboundBuffer> writableUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(ChannelOutboundBuffer.class, "writable");
/*     */     
/*  84 */     if (writableUpdater == null) {
/*  85 */       writableUpdater = AtomicIntegerFieldUpdater.newUpdater(ChannelOutboundBuffer.class, "writable");
/*     */     }
/*  87 */     WRITABLE_UPDATER = writableUpdater;
/*     */     
/*  89 */     AtomicLongFieldUpdater<ChannelOutboundBuffer> pendingSizeUpdater = PlatformDependent.newAtomicLongFieldUpdater(ChannelOutboundBuffer.class, "totalPendingSize");
/*     */     
/*  91 */     if (pendingSizeUpdater == null) {
/*  92 */       pendingSizeUpdater = AtomicLongFieldUpdater.newUpdater(ChannelOutboundBuffer.class, "totalPendingSize");
/*     */     }
/*  94 */     TOTAL_PENDING_SIZE_UPDATER = pendingSizeUpdater;
/*     */   }
/*     */   
/*     */   ChannelOutboundBuffer(AbstractChannel channel) {
/*  98 */     this.channel = channel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMessage(Object msg, int size, ChannelPromise promise) {
/* 106 */     Entry entry = Entry.newInstance(msg, size, total(msg), promise);
/* 107 */     if (this.tailEntry == null) {
/* 108 */       this.flushedEntry = null;
/* 109 */       this.tailEntry = entry;
/*     */     } else {
/* 111 */       Entry tail = this.tailEntry;
/* 112 */       tail.next = entry;
/* 113 */       this.tailEntry = entry;
/*     */     } 
/* 115 */     if (this.unflushedEntry == null) {
/* 116 */       this.unflushedEntry = entry;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 121 */     incrementPendingOutboundBytes(size);
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
/*     */   public void addFlush() {
/* 133 */     Entry entry = this.unflushedEntry;
/* 134 */     if (entry != null) {
/* 135 */       if (this.flushedEntry == null)
/*     */       {
/* 137 */         this.flushedEntry = entry;
/*     */       }
/*     */       do {
/* 140 */         this.flushed++;
/* 141 */         if (!entry.promise.setUncancellable()) {
/*     */           
/* 143 */           int pending = entry.cancel();
/* 144 */           decrementPendingOutboundBytes(pending);
/*     */         } 
/* 146 */         entry = entry.next;
/* 147 */       } while (entry != null);
/*     */ 
/*     */       
/* 150 */       this.unflushedEntry = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void incrementPendingOutboundBytes(long size) {
/* 159 */     if (size == 0L) {
/*     */       return;
/*     */     }
/*     */     
/* 163 */     long newWriteBufferSize = TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, size);
/* 164 */     if (newWriteBufferSize > this.channel.config().getWriteBufferHighWaterMark() && 
/* 165 */       WRITABLE_UPDATER.compareAndSet(this, 1, 0)) {
/* 166 */       this.channel.pipeline().fireChannelWritabilityChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void decrementPendingOutboundBytes(long size) {
/* 176 */     if (size == 0L) {
/*     */       return;
/*     */     }
/*     */     
/* 180 */     long newWriteBufferSize = TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, -size);
/* 181 */     if ((newWriteBufferSize == 0L || newWriteBufferSize < this.channel.config().getWriteBufferLowWaterMark()) && 
/* 182 */       WRITABLE_UPDATER.compareAndSet(this, 0, 1)) {
/* 183 */       this.channel.pipeline().fireChannelWritabilityChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static long total(Object msg) {
/* 189 */     if (msg instanceof ByteBuf) {
/* 190 */       return ((ByteBuf)msg).readableBytes();
/*     */     }
/* 192 */     if (msg instanceof FileRegion) {
/* 193 */       return ((FileRegion)msg).count();
/*     */     }
/* 195 */     if (msg instanceof ByteBufHolder) {
/* 196 */       return ((ByteBufHolder)msg).content().readableBytes();
/*     */     }
/* 198 */     return -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object current() {
/* 205 */     Entry entry = this.flushedEntry;
/* 206 */     if (entry == null) {
/* 207 */       return null;
/*     */     }
/*     */     
/* 210 */     return entry.msg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void progress(long amount) {
/* 217 */     Entry e = this.flushedEntry;
/* 218 */     assert e != null;
/* 219 */     ChannelPromise p = e.promise;
/* 220 */     if (p instanceof ChannelProgressivePromise) {
/* 221 */       long progress = e.progress + amount;
/* 222 */       e.progress = progress;
/* 223 */       ((ChannelProgressivePromise)p).tryProgress(progress, e.total);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove() {
/* 233 */     Entry e = this.flushedEntry;
/* 234 */     if (e == null) {
/* 235 */       return false;
/*     */     }
/* 237 */     Object msg = e.msg;
/*     */     
/* 239 */     ChannelPromise promise = e.promise;
/* 240 */     int size = e.pendingSize;
/*     */     
/* 242 */     removeEntry(e);
/*     */     
/* 244 */     if (!e.cancelled) {
/*     */       
/* 246 */       ReferenceCountUtil.safeRelease(msg);
/* 247 */       safeSuccess(promise);
/* 248 */       decrementPendingOutboundBytes(size);
/*     */     } 
/*     */ 
/*     */     
/* 252 */     e.recycle();
/*     */     
/* 254 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(Throwable cause) {
/* 263 */     Entry e = this.flushedEntry;
/* 264 */     if (e == null) {
/* 265 */       return false;
/*     */     }
/* 267 */     Object msg = e.msg;
/*     */     
/* 269 */     ChannelPromise promise = e.promise;
/* 270 */     int size = e.pendingSize;
/*     */     
/* 272 */     removeEntry(e);
/*     */     
/* 274 */     if (!e.cancelled) {
/*     */       
/* 276 */       ReferenceCountUtil.safeRelease(msg);
/*     */       
/* 278 */       safeFail(promise, cause);
/* 279 */       decrementPendingOutboundBytes(size);
/*     */     } 
/*     */ 
/*     */     
/* 283 */     e.recycle();
/*     */     
/* 285 */     return true;
/*     */   }
/*     */   
/*     */   private void removeEntry(Entry e) {
/* 289 */     if (--this.flushed == 0) {
/*     */       
/* 291 */       this.flushedEntry = null;
/* 292 */       if (e == this.tailEntry) {
/* 293 */         this.tailEntry = null;
/* 294 */         this.unflushedEntry = null;
/*     */       } 
/*     */     } else {
/* 297 */       this.flushedEntry = e.next;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeBytes(long writtenBytes) {
/*     */     while (true) {
/* 307 */       Object msg = current();
/* 308 */       if (!(msg instanceof ByteBuf)) {
/* 309 */         assert writtenBytes == 0L;
/*     */         
/*     */         break;
/*     */       } 
/* 313 */       ByteBuf buf = (ByteBuf)msg;
/* 314 */       int readerIndex = buf.readerIndex();
/* 315 */       int readableBytes = buf.writerIndex() - readerIndex;
/*     */       
/* 317 */       if (readableBytes <= writtenBytes) {
/* 318 */         if (writtenBytes != 0L) {
/* 319 */           progress(readableBytes);
/* 320 */           writtenBytes -= readableBytes;
/*     */         } 
/* 322 */         remove(); continue;
/*     */       } 
/* 324 */       if (writtenBytes != 0L) {
/* 325 */         buf.readerIndex(readerIndex + (int)writtenBytes);
/* 326 */         progress(writtenBytes);
/*     */       } 
/*     */       break;
/*     */     } 
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
/*     */   public ByteBuffer[] nioBuffers() {
/* 344 */     long nioBufferSize = 0L;
/* 345 */     int nioBufferCount = 0;
/* 346 */     InternalThreadLocalMap threadLocalMap = InternalThreadLocalMap.get();
/* 347 */     ByteBuffer[] nioBuffers = (ByteBuffer[])NIO_BUFFERS.get(threadLocalMap);
/* 348 */     Entry entry = this.flushedEntry;
/* 349 */     while (isFlushedEntry(entry) && entry.msg instanceof ByteBuf) {
/* 350 */       if (!entry.cancelled) {
/* 351 */         ByteBuf buf = (ByteBuf)entry.msg;
/* 352 */         int readerIndex = buf.readerIndex();
/* 353 */         int readableBytes = buf.writerIndex() - readerIndex;
/*     */         
/* 355 */         if (readableBytes > 0) {
/* 356 */           nioBufferSize += readableBytes;
/* 357 */           int count = entry.count;
/* 358 */           if (count == -1)
/*     */           {
/* 360 */             entry.count = count = buf.nioBufferCount();
/*     */           }
/* 362 */           int neededSpace = nioBufferCount + count;
/* 363 */           if (neededSpace > nioBuffers.length) {
/* 364 */             nioBuffers = expandNioBufferArray(nioBuffers, neededSpace, nioBufferCount);
/* 365 */             NIO_BUFFERS.set(threadLocalMap, nioBuffers);
/*     */           } 
/* 367 */           if (count == 1) {
/* 368 */             ByteBuffer nioBuf = entry.buf;
/* 369 */             if (nioBuf == null)
/*     */             {
/*     */               
/* 372 */               entry.buf = nioBuf = buf.internalNioBuffer(readerIndex, readableBytes);
/*     */             }
/* 374 */             nioBuffers[nioBufferCount++] = nioBuf;
/*     */           } else {
/* 376 */             ByteBuffer[] nioBufs = entry.bufs;
/* 377 */             if (nioBufs == null)
/*     */             {
/*     */               
/* 380 */               entry.bufs = nioBufs = buf.nioBuffers();
/*     */             }
/* 382 */             nioBufferCount = fillBufferArray(nioBufs, nioBuffers, nioBufferCount);
/*     */           } 
/*     */         } 
/*     */       } 
/* 386 */       entry = entry.next;
/*     */     } 
/* 388 */     this.nioBufferCount = nioBufferCount;
/* 389 */     this.nioBufferSize = nioBufferSize;
/*     */     
/* 391 */     return nioBuffers;
/*     */   }
/*     */   
/*     */   private static int fillBufferArray(ByteBuffer[] nioBufs, ByteBuffer[] nioBuffers, int nioBufferCount) {
/* 395 */     for (ByteBuffer nioBuf : nioBufs) {
/* 396 */       if (nioBuf == null) {
/*     */         break;
/*     */       }
/* 399 */       nioBuffers[nioBufferCount++] = nioBuf;
/*     */     } 
/* 401 */     return nioBufferCount;
/*     */   }
/*     */   
/*     */   private static ByteBuffer[] expandNioBufferArray(ByteBuffer[] array, int neededSpace, int size) {
/* 405 */     int newCapacity = array.length;
/*     */ 
/*     */     
/*     */     do {
/* 409 */       newCapacity <<= 1;
/*     */       
/* 411 */       if (newCapacity < 0) {
/* 412 */         throw new IllegalStateException();
/*     */       }
/*     */     }
/* 415 */     while (neededSpace > newCapacity);
/*     */     
/* 417 */     ByteBuffer[] newArray = new ByteBuffer[newCapacity];
/* 418 */     System.arraycopy(array, 0, newArray, 0, size);
/*     */     
/* 420 */     return newArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int nioBufferCount() {
/* 429 */     return this.nioBufferCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long nioBufferSize() {
/* 438 */     return this.nioBufferSize;
/*     */   }
/*     */   
/*     */   boolean isWritable() {
/* 442 */     return (this.writable != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 449 */     return this.flushed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 457 */     return (this.flushed == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void failFlushed(Throwable cause) {
/* 466 */     if (this.inFail) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 471 */       this.inFail = true; do {
/*     */       
/* 473 */       } while (remove(cause));
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 478 */       this.inFail = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   void close(final ClosedChannelException cause) {
/* 483 */     if (this.inFail) {
/* 484 */       this.channel.eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 487 */               ChannelOutboundBuffer.this.close(cause);
/*     */             }
/*     */           });
/*     */       
/*     */       return;
/*     */     } 
/* 493 */     this.inFail = true;
/*     */     
/* 495 */     if (this.channel.isOpen()) {
/* 496 */       throw new IllegalStateException("close() must be invoked after the channel is closed.");
/*     */     }
/*     */     
/* 499 */     if (!isEmpty()) {
/* 500 */       throw new IllegalStateException("close() must be invoked after all flushed writes are handled.");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 505 */       Entry e = this.unflushedEntry;
/* 506 */       while (e != null) {
/*     */         
/* 508 */         int size = e.pendingSize;
/* 509 */         TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, -size);
/*     */         
/* 511 */         if (!e.cancelled) {
/* 512 */           ReferenceCountUtil.safeRelease(e.msg);
/* 513 */           safeFail(e.promise, cause);
/*     */         } 
/* 515 */         e = e.recycleAndGetNext();
/*     */       } 
/*     */     } finally {
/* 518 */       this.inFail = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void safeSuccess(ChannelPromise promise) {
/* 523 */     if (!(promise instanceof VoidChannelPromise) && !promise.trySuccess()) {
/* 524 */       logger.warn("Failed to mark a promise as success because it is done already: {}", promise);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void safeFail(ChannelPromise promise, Throwable cause) {
/* 529 */     if (!(promise instanceof VoidChannelPromise) && !promise.tryFailure(cause)) {
/* 530 */       logger.warn("Failed to mark a promise as failure because it's done already: {}", promise, cause);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void recycle() {}
/*     */ 
/*     */   
/*     */   public long totalPendingWriteBytes() {
/* 540 */     return this.totalPendingSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forEachFlushedMessage(MessageProcessor processor) throws Exception {
/* 549 */     if (processor == null) {
/* 550 */       throw new NullPointerException("processor");
/*     */     }
/*     */     
/* 553 */     Entry entry = this.flushedEntry;
/* 554 */     if (entry == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     do {
/* 559 */       if (!entry.cancelled && 
/* 560 */         !processor.processMessage(entry.msg)) {
/*     */         return;
/*     */       }
/*     */       
/* 564 */       entry = entry.next;
/* 565 */     } while (isFlushedEntry(entry));
/*     */   }
/*     */   
/*     */   private boolean isFlushedEntry(Entry e) {
/* 569 */     return (e != null && e != this.unflushedEntry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static interface MessageProcessor
/*     */   {
/*     */     boolean processMessage(Object param1Object) throws Exception;
/*     */   }
/*     */ 
/*     */   
/*     */   static final class Entry
/*     */   {
/* 581 */     private static final Recycler<Entry> RECYCLER = new Recycler<Entry>()
/*     */       {
/*     */         protected ChannelOutboundBuffer.Entry newObject(Recycler.Handle handle) {
/* 584 */           return new ChannelOutboundBuffer.Entry(handle);
/*     */         }
/*     */       };
/*     */     
/*     */     private final Recycler.Handle handle;
/*     */     Entry next;
/*     */     Object msg;
/*     */     ByteBuffer[] bufs;
/*     */     ByteBuffer buf;
/*     */     ChannelPromise promise;
/*     */     long progress;
/*     */     long total;
/*     */     int pendingSize;
/* 597 */     int count = -1;
/*     */     boolean cancelled;
/*     */     
/*     */     private Entry(Recycler.Handle handle) {
/* 601 */       this.handle = handle;
/*     */     }
/*     */     
/*     */     static Entry newInstance(Object msg, int size, long total, ChannelPromise promise) {
/* 605 */       Entry entry = (Entry)RECYCLER.get();
/* 606 */       entry.msg = msg;
/* 607 */       entry.pendingSize = size;
/* 608 */       entry.total = total;
/* 609 */       entry.promise = promise;
/* 610 */       return entry;
/*     */     }
/*     */     
/*     */     int cancel() {
/* 614 */       if (!this.cancelled) {
/* 615 */         this.cancelled = true;
/* 616 */         int pSize = this.pendingSize;
/*     */ 
/*     */         
/* 619 */         ReferenceCountUtil.safeRelease(this.msg);
/* 620 */         this.msg = Unpooled.EMPTY_BUFFER;
/*     */         
/* 622 */         this.pendingSize = 0;
/* 623 */         this.total = 0L;
/* 624 */         this.progress = 0L;
/* 625 */         this.bufs = null;
/* 626 */         this.buf = null;
/* 627 */         return pSize;
/*     */       } 
/* 629 */       return 0;
/*     */     }
/*     */     
/*     */     void recycle() {
/* 633 */       this.next = null;
/* 634 */       this.bufs = null;
/* 635 */       this.buf = null;
/* 636 */       this.msg = null;
/* 637 */       this.promise = null;
/* 638 */       this.progress = 0L;
/* 639 */       this.total = 0L;
/* 640 */       this.pendingSize = 0;
/* 641 */       this.count = -1;
/* 642 */       this.cancelled = false;
/* 643 */       RECYCLER.recycle(this, this.handle);
/*     */     }
/*     */     
/*     */     Entry recycleAndGetNext() {
/* 647 */       Entry next = this.next;
/* 648 */       recycle();
/* 649 */       return next;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\ChannelOutboundBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */