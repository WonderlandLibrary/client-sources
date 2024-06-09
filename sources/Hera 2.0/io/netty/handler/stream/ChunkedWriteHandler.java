/*     */ package io.netty.handler.stream;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelProgressivePromise;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.channels.ClosedChannelException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkedWriteHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  72 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChunkedWriteHandler.class);
/*     */ 
/*     */   
/*  75 */   private final Queue<PendingWrite> queue = new ArrayDeque<PendingWrite>();
/*     */ 
/*     */   
/*     */   private volatile ChannelHandlerContext ctx;
/*     */ 
/*     */   
/*     */   private PendingWrite currentWrite;
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ChunkedWriteHandler(int maxPendingWrites) {
/*  87 */     if (maxPendingWrites <= 0) {
/*  88 */       throw new IllegalArgumentException("maxPendingWrites: " + maxPendingWrites + " (expected: > 0)");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/*  95 */     this.ctx = ctx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resumeTransfer() {
/* 102 */     final ChannelHandlerContext ctx = this.ctx;
/* 103 */     if (ctx == null) {
/*     */       return;
/*     */     }
/* 106 */     if (ctx.executor().inEventLoop()) {
/*     */       try {
/* 108 */         doFlush(ctx);
/* 109 */       } catch (Exception e) {
/* 110 */         if (logger.isWarnEnabled()) {
/* 111 */           logger.warn("Unexpected exception while sending chunks.", e);
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 116 */       ctx.executor().execute(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/*     */               try {
/* 121 */                 ChunkedWriteHandler.this.doFlush(ctx);
/* 122 */               } catch (Exception e) {
/* 123 */                 if (ChunkedWriteHandler.logger.isWarnEnabled()) {
/* 124 */                   ChunkedWriteHandler.logger.warn("Unexpected exception while sending chunks.", e);
/*     */                 }
/*     */               } 
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 134 */     this.queue.add(new PendingWrite(msg, promise));
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush(ChannelHandlerContext ctx) throws Exception {
/* 139 */     Channel channel = ctx.channel();
/* 140 */     if (channel.isWritable() || !channel.isActive()) {
/* 141 */       doFlush(ctx);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 147 */     doFlush(ctx);
/* 148 */     super.channelInactive(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
/* 153 */     if (ctx.channel().isWritable())
/*     */     {
/* 155 */       doFlush(ctx);
/*     */     }
/* 157 */     ctx.fireChannelWritabilityChanged();
/*     */   }
/*     */   
/*     */   private void discard(Throwable cause) {
/*     */     while (true) {
/* 162 */       PendingWrite currentWrite = this.currentWrite;
/*     */       
/* 164 */       if (this.currentWrite == null) {
/* 165 */         currentWrite = this.queue.poll();
/*     */       } else {
/* 167 */         this.currentWrite = null;
/*     */       } 
/*     */       
/* 170 */       if (currentWrite == null) {
/*     */         break;
/*     */       }
/* 173 */       Object message = currentWrite.msg;
/* 174 */       if (message instanceof ChunkedInput) {
/* 175 */         ChunkedInput<?> in = (ChunkedInput)message;
/*     */         try {
/* 177 */           if (!in.isEndOfInput()) {
/* 178 */             if (cause == null) {
/* 179 */               cause = new ClosedChannelException();
/*     */             }
/* 181 */             currentWrite.fail(cause);
/*     */           } else {
/* 183 */             currentWrite.success();
/*     */           } 
/* 185 */           closeInput(in);
/* 186 */         } catch (Exception e) {
/* 187 */           currentWrite.fail(e);
/* 188 */           logger.warn(ChunkedInput.class.getSimpleName() + ".isEndOfInput() failed", e);
/* 189 */           closeInput(in);
/*     */         }  continue;
/*     */       } 
/* 192 */       if (cause == null) {
/* 193 */         cause = new ClosedChannelException();
/*     */       }
/* 195 */       currentWrite.fail(cause);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void doFlush(ChannelHandlerContext ctx) throws Exception {
/* 201 */     final Channel channel = ctx.channel();
/* 202 */     if (!channel.isActive()) {
/* 203 */       discard(null);
/*     */       return;
/*     */     } 
/* 206 */     while (channel.isWritable()) {
/* 207 */       if (this.currentWrite == null) {
/* 208 */         this.currentWrite = this.queue.poll();
/*     */       }
/*     */       
/* 211 */       if (this.currentWrite == null) {
/*     */         break;
/*     */       }
/* 214 */       final PendingWrite currentWrite = this.currentWrite;
/* 215 */       final Object pendingMessage = currentWrite.msg;
/*     */       
/* 217 */       if (pendingMessage instanceof ChunkedInput) {
/* 218 */         boolean endOfInput, suspend; final ChunkedInput<?> chunks = (ChunkedInput)pendingMessage;
/*     */ 
/*     */         
/* 221 */         Object message = null;
/*     */         try {
/* 223 */           message = chunks.readChunk(ctx);
/* 224 */           endOfInput = chunks.isEndOfInput();
/*     */           
/* 226 */           if (message == null) {
/*     */             
/* 228 */             suspend = !endOfInput;
/*     */           } else {
/* 230 */             suspend = false;
/*     */           } 
/* 232 */         } catch (Throwable t) {
/* 233 */           this.currentWrite = null;
/*     */           
/* 235 */           if (message != null) {
/* 236 */             ReferenceCountUtil.release(message);
/*     */           }
/*     */           
/* 239 */           currentWrite.fail(t);
/* 240 */           closeInput(chunks);
/*     */           
/*     */           break;
/*     */         } 
/* 244 */         if (suspend) {
/*     */           break;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 251 */         if (message == null)
/*     */         {
/*     */           
/* 254 */           message = Unpooled.EMPTY_BUFFER;
/*     */         }
/*     */         
/* 257 */         final int amount = amount(message);
/* 258 */         ChannelFuture f = ctx.write(message);
/* 259 */         if (endOfInput) {
/* 260 */           this.currentWrite = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 267 */           f.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */               {
/*     */                 public void operationComplete(ChannelFuture future) throws Exception {
/* 270 */                   currentWrite.progress(amount);
/* 271 */                   currentWrite.success();
/* 272 */                   ChunkedWriteHandler.closeInput(chunks);
/*     */                 }
/*     */               });
/* 275 */         } else if (channel.isWritable()) {
/* 276 */           f.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */               {
/*     */                 public void operationComplete(ChannelFuture future) throws Exception {
/* 279 */                   if (!future.isSuccess()) {
/* 280 */                     ChunkedWriteHandler.closeInput((ChunkedInput)pendingMessage);
/* 281 */                     currentWrite.fail(future.cause());
/*     */                   } else {
/* 283 */                     currentWrite.progress(amount);
/*     */                   } 
/*     */                 }
/*     */               });
/*     */         } else {
/* 288 */           f.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */               {
/*     */                 public void operationComplete(ChannelFuture future) throws Exception {
/* 291 */                   if (!future.isSuccess()) {
/* 292 */                     ChunkedWriteHandler.closeInput((ChunkedInput)pendingMessage);
/* 293 */                     currentWrite.fail(future.cause());
/*     */                   } else {
/* 295 */                     currentWrite.progress(amount);
/* 296 */                     if (channel.isWritable()) {
/* 297 */                       ChunkedWriteHandler.this.resumeTransfer();
/*     */                     }
/*     */                   } 
/*     */                 }
/*     */               });
/*     */         } 
/*     */       } else {
/* 304 */         ctx.write(pendingMessage, currentWrite.promise);
/* 305 */         this.currentWrite = null;
/*     */       } 
/*     */ 
/*     */       
/* 309 */       ctx.flush();
/*     */       
/* 311 */       if (!channel.isActive()) {
/* 312 */         discard(new ClosedChannelException());
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static void closeInput(ChunkedInput<?> chunks) {
/*     */     try {
/* 320 */       chunks.close();
/* 321 */     } catch (Throwable t) {
/* 322 */       if (logger.isWarnEnabled())
/* 323 */         logger.warn("Failed to close a chunked input.", t); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class PendingWrite
/*     */   {
/*     */     final Object msg;
/*     */     final ChannelPromise promise;
/*     */     private long progress;
/*     */     
/*     */     PendingWrite(Object msg, ChannelPromise promise) {
/* 334 */       this.msg = msg;
/* 335 */       this.promise = promise;
/*     */     }
/*     */     
/*     */     void fail(Throwable cause) {
/* 339 */       ReferenceCountUtil.release(this.msg);
/* 340 */       this.promise.tryFailure(cause);
/*     */     }
/*     */     
/*     */     void success() {
/* 344 */       if (this.promise.isDone()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 349 */       if (this.promise instanceof ChannelProgressivePromise)
/*     */       {
/* 351 */         ((ChannelProgressivePromise)this.promise).tryProgress(this.progress, this.progress);
/*     */       }
/*     */       
/* 354 */       this.promise.trySuccess();
/*     */     }
/*     */     
/*     */     void progress(int amount) {
/* 358 */       this.progress += amount;
/* 359 */       if (this.promise instanceof ChannelProgressivePromise) {
/* 360 */         ((ChannelProgressivePromise)this.promise).tryProgress(this.progress, -1L);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static int amount(Object msg) {
/* 366 */     if (msg instanceof ByteBuf) {
/* 367 */       return ((ByteBuf)msg).readableBytes();
/*     */     }
/* 369 */     if (msg instanceof ByteBufHolder) {
/* 370 */       return ((ByteBufHolder)msg).content().readableBytes();
/*     */     }
/* 372 */     return 1;
/*     */   }
/*     */   
/*     */   public ChunkedWriteHandler() {}
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\stream\ChunkedWriteHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */