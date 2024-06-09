/*     */ package io.netty.channel;
/*     */ 
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Queue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ChannelFlushPromiseNotifier
/*     */ {
/*     */   private long writeCounter;
/*  28 */   private final Queue<FlushCheckpoint> flushCheckpoints = new ArrayDeque<FlushCheckpoint>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean tryNotify;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFlushPromiseNotifier(boolean tryNotify) {
/*  40 */     this.tryNotify = tryNotify;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFlushPromiseNotifier() {
/*  48 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ChannelFlushPromiseNotifier add(ChannelPromise promise, int pendingDataSize) {
/*  56 */     return add(promise, pendingDataSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFlushPromiseNotifier add(ChannelPromise promise, long pendingDataSize) {
/*  64 */     if (promise == null) {
/*  65 */       throw new NullPointerException("promise");
/*     */     }
/*  67 */     if (pendingDataSize < 0L) {
/*  68 */       throw new IllegalArgumentException("pendingDataSize must be >= 0 but was " + pendingDataSize);
/*     */     }
/*  70 */     long checkpoint = this.writeCounter + pendingDataSize;
/*  71 */     if (promise instanceof FlushCheckpoint) {
/*  72 */       FlushCheckpoint cp = (FlushCheckpoint)promise;
/*  73 */       cp.flushCheckpoint(checkpoint);
/*  74 */       this.flushCheckpoints.add(cp);
/*     */     } else {
/*  76 */       this.flushCheckpoints.add(new DefaultFlushCheckpoint(checkpoint, promise));
/*     */     } 
/*  78 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFlushPromiseNotifier increaseWriteCounter(long delta) {
/*  84 */     if (delta < 0L) {
/*  85 */       throw new IllegalArgumentException("delta must be >= 0 but was " + delta);
/*     */     }
/*  87 */     this.writeCounter += delta;
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long writeCounter() {
/*  95 */     return this.writeCounter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFlushPromiseNotifier notifyPromises() {
/* 106 */     notifyPromises0(null);
/* 107 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ChannelFlushPromiseNotifier notifyFlushFutures() {
/* 115 */     return notifyPromises();
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
/*     */   public ChannelFlushPromiseNotifier notifyPromises(Throwable cause) {
/* 130 */     notifyPromises();
/*     */     while (true) {
/* 132 */       FlushCheckpoint cp = this.flushCheckpoints.poll();
/* 133 */       if (cp == null) {
/*     */         break;
/*     */       }
/* 136 */       if (this.tryNotify) {
/* 137 */         cp.promise().tryFailure(cause); continue;
/*     */       } 
/* 139 */       cp.promise().setFailure(cause);
/*     */     } 
/*     */     
/* 142 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ChannelFlushPromiseNotifier notifyFlushFutures(Throwable cause) {
/* 150 */     return notifyPromises(cause);
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
/*     */   public ChannelFlushPromiseNotifier notifyPromises(Throwable cause1, Throwable cause2) {
/* 170 */     notifyPromises0(cause1);
/*     */     while (true) {
/* 172 */       FlushCheckpoint cp = this.flushCheckpoints.poll();
/* 173 */       if (cp == null) {
/*     */         break;
/*     */       }
/* 176 */       if (this.tryNotify) {
/* 177 */         cp.promise().tryFailure(cause2); continue;
/*     */       } 
/* 179 */       cp.promise().setFailure(cause2);
/*     */     } 
/*     */     
/* 182 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ChannelFlushPromiseNotifier notifyFlushFutures(Throwable cause1, Throwable cause2) {
/* 190 */     return notifyPromises(cause1, cause2);
/*     */   }
/*     */   
/*     */   private void notifyPromises0(Throwable cause) {
/* 194 */     if (this.flushCheckpoints.isEmpty()) {
/* 195 */       this.writeCounter = 0L;
/*     */       
/*     */       return;
/*     */     } 
/* 199 */     long writeCounter = this.writeCounter;
/*     */     while (true) {
/* 201 */       FlushCheckpoint cp = this.flushCheckpoints.peek();
/* 202 */       if (cp == null) {
/*     */         
/* 204 */         this.writeCounter = 0L;
/*     */         
/*     */         break;
/*     */       } 
/* 208 */       if (cp.flushCheckpoint() > writeCounter) {
/* 209 */         if (writeCounter > 0L && this.flushCheckpoints.size() == 1) {
/* 210 */           this.writeCounter = 0L;
/* 211 */           cp.flushCheckpoint(cp.flushCheckpoint() - writeCounter);
/*     */         } 
/*     */         
/*     */         break;
/*     */       } 
/* 216 */       this.flushCheckpoints.remove();
/* 217 */       ChannelPromise promise = cp.promise();
/* 218 */       if (cause == null) {
/* 219 */         if (this.tryNotify) {
/* 220 */           promise.trySuccess(); continue;
/*     */         } 
/* 222 */         promise.setSuccess();
/*     */         continue;
/*     */       } 
/* 225 */       if (this.tryNotify) {
/* 226 */         promise.tryFailure(cause); continue;
/*     */       } 
/* 228 */       promise.setFailure(cause);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 234 */     long newWriteCounter = this.writeCounter;
/* 235 */     if (newWriteCounter >= 549755813888L) {
/*     */ 
/*     */       
/* 238 */       this.writeCounter = 0L;
/* 239 */       for (FlushCheckpoint cp : this.flushCheckpoints) {
/* 240 */         cp.flushCheckpoint(cp.flushCheckpoint() - newWriteCounter);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class DefaultFlushCheckpoint
/*     */     implements FlushCheckpoint
/*     */   {
/*     */     private long checkpoint;
/*     */     
/*     */     private final ChannelPromise future;
/*     */ 
/*     */     
/*     */     DefaultFlushCheckpoint(long checkpoint, ChannelPromise future) {
/* 256 */       this.checkpoint = checkpoint;
/* 257 */       this.future = future;
/*     */     }
/*     */ 
/*     */     
/*     */     public long flushCheckpoint() {
/* 262 */       return this.checkpoint;
/*     */     }
/*     */ 
/*     */     
/*     */     public void flushCheckpoint(long checkpoint) {
/* 267 */       this.checkpoint = checkpoint;
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelPromise promise() {
/* 272 */       return this.future;
/*     */     }
/*     */   }
/*     */   
/*     */   static interface FlushCheckpoint {
/*     */     long flushCheckpoint();
/*     */     
/*     */     void flushCheckpoint(long param1Long);
/*     */     
/*     */     ChannelPromise promise();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\ChannelFlushPromiseNotifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */