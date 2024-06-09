/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PendingWriteQueue
/*     */ {
/*  29 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(PendingWriteQueue.class);
/*     */   
/*     */   private final ChannelHandlerContext ctx;
/*     */   
/*     */   private final ChannelOutboundBuffer buffer;
/*     */   
/*     */   private final MessageSizeEstimator.Handle estimatorHandle;
/*     */   private PendingWrite head;
/*     */   private PendingWrite tail;
/*     */   private int size;
/*     */   
/*     */   public PendingWriteQueue(ChannelHandlerContext ctx) {
/*  41 */     if (ctx == null) {
/*  42 */       throw new NullPointerException("ctx");
/*     */     }
/*  44 */     this.ctx = ctx;
/*  45 */     this.buffer = ctx.channel().unsafe().outboundBuffer();
/*  46 */     this.estimatorHandle = ctx.channel().config().getMessageSizeEstimator().newHandle();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  53 */     assert this.ctx.executor().inEventLoop();
/*  54 */     return (this.head == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  61 */     assert this.ctx.executor().inEventLoop();
/*  62 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Object msg, ChannelPromise promise) {
/*  69 */     assert this.ctx.executor().inEventLoop();
/*  70 */     if (msg == null) {
/*  71 */       throw new NullPointerException("msg");
/*     */     }
/*  73 */     if (promise == null) {
/*  74 */       throw new NullPointerException("promise");
/*     */     }
/*  76 */     int messageSize = this.estimatorHandle.size(msg);
/*  77 */     if (messageSize < 0)
/*     */     {
/*  79 */       messageSize = 0;
/*     */     }
/*  81 */     PendingWrite write = PendingWrite.newInstance(msg, messageSize, promise);
/*  82 */     PendingWrite currentTail = this.tail;
/*  83 */     if (currentTail == null) {
/*  84 */       this.tail = this.head = write;
/*     */     } else {
/*  86 */       currentTail.next = write;
/*  87 */       this.tail = write;
/*     */     } 
/*  89 */     this.size++;
/*  90 */     this.buffer.incrementPendingOutboundBytes(write.size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAndFailAll(Throwable cause) {
/*  98 */     assert this.ctx.executor().inEventLoop();
/*  99 */     if (cause == null) {
/* 100 */       throw new NullPointerException("cause");
/*     */     }
/* 102 */     PendingWrite write = this.head;
/* 103 */     while (write != null) {
/* 104 */       PendingWrite next = write.next;
/* 105 */       ReferenceCountUtil.safeRelease(write.msg);
/* 106 */       ChannelPromise promise = write.promise;
/* 107 */       recycle(write);
/* 108 */       safeFail(promise, cause);
/* 109 */       write = next;
/*     */     } 
/* 111 */     assertEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAndFail(Throwable cause) {
/* 119 */     assert this.ctx.executor().inEventLoop();
/* 120 */     if (cause == null) {
/* 121 */       throw new NullPointerException("cause");
/*     */     }
/* 123 */     PendingWrite write = this.head;
/* 124 */     if (write == null) {
/*     */       return;
/*     */     }
/* 127 */     ReferenceCountUtil.safeRelease(write.msg);
/* 128 */     ChannelPromise promise = write.promise;
/* 129 */     safeFail(promise, cause);
/* 130 */     recycle(write);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture removeAndWriteAll() {
/* 141 */     assert this.ctx.executor().inEventLoop();
/* 142 */     PendingWrite write = this.head;
/* 143 */     if (write == null)
/*     */     {
/* 145 */       return null;
/*     */     }
/* 147 */     if (this.size == 1)
/*     */     {
/* 149 */       return removeAndWrite();
/*     */     }
/* 151 */     ChannelPromise p = this.ctx.newPromise();
/* 152 */     ChannelPromiseAggregator aggregator = new ChannelPromiseAggregator(p);
/* 153 */     while (write != null) {
/* 154 */       PendingWrite next = write.next;
/* 155 */       Object msg = write.msg;
/* 156 */       ChannelPromise promise = write.promise;
/* 157 */       recycle(write);
/* 158 */       this.ctx.write(msg, promise);
/* 159 */       aggregator.add(new ChannelPromise[] { promise });
/* 160 */       write = next;
/*     */     } 
/* 162 */     assertEmpty();
/* 163 */     return p;
/*     */   }
/*     */   
/*     */   private void assertEmpty() {
/* 167 */     assert this.tail == null && this.head == null && this.size == 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture removeAndWrite() {
/* 178 */     assert this.ctx.executor().inEventLoop();
/* 179 */     PendingWrite write = this.head;
/* 180 */     if (write == null) {
/* 181 */       return null;
/*     */     }
/* 183 */     Object msg = write.msg;
/* 184 */     ChannelPromise promise = write.promise;
/* 185 */     recycle(write);
/* 186 */     return this.ctx.write(msg, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelPromise remove() {
/* 196 */     assert this.ctx.executor().inEventLoop();
/* 197 */     PendingWrite write = this.head;
/* 198 */     if (write == null) {
/* 199 */       return null;
/*     */     }
/* 201 */     ChannelPromise promise = write.promise;
/* 202 */     ReferenceCountUtil.safeRelease(write.msg);
/* 203 */     recycle(write);
/* 204 */     return promise;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object current() {
/* 211 */     assert this.ctx.executor().inEventLoop();
/* 212 */     PendingWrite write = this.head;
/* 213 */     if (write == null) {
/* 214 */       return null;
/*     */     }
/* 216 */     return write.msg;
/*     */   }
/*     */   
/*     */   private void recycle(PendingWrite write) {
/* 220 */     PendingWrite next = write.next;
/*     */     
/* 222 */     this.buffer.decrementPendingOutboundBytes(write.size);
/* 223 */     write.recycle();
/* 224 */     this.size--;
/* 225 */     if (next == null) {
/*     */       
/* 227 */       this.head = this.tail = null;
/* 228 */       assert this.size == 0;
/*     */     } else {
/* 230 */       this.head = next;
/* 231 */       assert this.size > 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void safeFail(ChannelPromise promise, Throwable cause) {
/* 236 */     if (!(promise instanceof VoidChannelPromise) && !promise.tryFailure(cause)) {
/* 237 */       logger.warn("Failed to mark a promise as failure because it's done already: {}", promise, cause);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class PendingWrite
/*     */   {
/* 245 */     private static final Recycler<PendingWrite> RECYCLER = new Recycler<PendingWrite>()
/*     */       {
/*     */         protected PendingWriteQueue.PendingWrite newObject(Recycler.Handle handle) {
/* 248 */           return new PendingWriteQueue.PendingWrite(handle);
/*     */         }
/*     */       };
/*     */     
/*     */     private final Recycler.Handle handle;
/*     */     private PendingWrite next;
/*     */     private long size;
/*     */     private ChannelPromise promise;
/*     */     private Object msg;
/*     */     
/*     */     private PendingWrite(Recycler.Handle handle) {
/* 259 */       this.handle = handle;
/*     */     }
/*     */     
/*     */     static PendingWrite newInstance(Object msg, int size, ChannelPromise promise) {
/* 263 */       PendingWrite write = (PendingWrite)RECYCLER.get();
/* 264 */       write.size = size;
/* 265 */       write.msg = msg;
/* 266 */       write.promise = promise;
/* 267 */       return write;
/*     */     }
/*     */     
/*     */     private void recycle() {
/* 271 */       this.size = 0L;
/* 272 */       this.next = null;
/* 273 */       this.msg = null;
/* 274 */       this.promise = null;
/* 275 */       RECYCLER.recycle(this, this.handle);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\PendingWriteQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */