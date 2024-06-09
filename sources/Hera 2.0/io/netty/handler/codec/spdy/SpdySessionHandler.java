/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpdySessionHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  35 */   private static final SpdyProtocolException PROTOCOL_EXCEPTION = new SpdyProtocolException();
/*  36 */   private static final SpdyProtocolException STREAM_CLOSED = new SpdyProtocolException("Stream closed");
/*     */   
/*     */   static {
/*  39 */     PROTOCOL_EXCEPTION.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*  40 */     STREAM_CLOSED.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*     */   }
/*     */   
/*     */   private static final int DEFAULT_WINDOW_SIZE = 65536;
/*  44 */   private int initialSendWindowSize = 65536;
/*  45 */   private int initialReceiveWindowSize = 65536;
/*  46 */   private volatile int initialSessionReceiveWindowSize = 65536;
/*     */   
/*  48 */   private final SpdySession spdySession = new SpdySession(this.initialSendWindowSize, this.initialReceiveWindowSize);
/*     */   
/*     */   private int lastGoodStreamId;
/*     */   private static final int DEFAULT_MAX_CONCURRENT_STREAMS = 2147483647;
/*  52 */   private int remoteConcurrentStreams = Integer.MAX_VALUE;
/*  53 */   private int localConcurrentStreams = Integer.MAX_VALUE;
/*     */   
/*  55 */   private final AtomicInteger pings = new AtomicInteger();
/*     */ 
/*     */   
/*     */   private boolean sentGoAwayFrame;
/*     */ 
/*     */   
/*     */   private boolean receivedGoAwayFrame;
/*     */ 
/*     */   
/*     */   private ChannelFutureListener closeSessionFutureListener;
/*     */ 
/*     */   
/*     */   private final boolean server;
/*     */ 
/*     */   
/*     */   private final int minorVersion;
/*     */ 
/*     */ 
/*     */   
/*     */   public SpdySessionHandler(SpdyVersion version, boolean server) {
/*  75 */     if (version == null) {
/*  76 */       throw new NullPointerException("version");
/*     */     }
/*  78 */     this.server = server;
/*  79 */     this.minorVersion = version.getMinorVersion();
/*     */   }
/*     */   
/*     */   public void setSessionReceiveWindowSize(int sessionReceiveWindowSize) {
/*  83 */     if (sessionReceiveWindowSize < 0) {
/*  84 */       throw new IllegalArgumentException("sessionReceiveWindowSize");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     this.initialSessionReceiveWindowSize = sessionReceiveWindowSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/*  97 */     if (msg instanceof SpdyDataFrame) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 121 */       SpdyDataFrame spdyDataFrame = (SpdyDataFrame)msg;
/* 122 */       int streamId = spdyDataFrame.streamId();
/*     */       
/* 124 */       int deltaWindowSize = -1 * spdyDataFrame.content().readableBytes();
/* 125 */       int newSessionWindowSize = this.spdySession.updateReceiveWindowSize(0, deltaWindowSize);
/*     */ 
/*     */ 
/*     */       
/* 129 */       if (newSessionWindowSize < 0) {
/* 130 */         issueSessionError(ctx, SpdySessionStatus.PROTOCOL_ERROR);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 135 */       if (newSessionWindowSize <= this.initialSessionReceiveWindowSize / 2) {
/* 136 */         int sessionDeltaWindowSize = this.initialSessionReceiveWindowSize - newSessionWindowSize;
/* 137 */         this.spdySession.updateReceiveWindowSize(0, sessionDeltaWindowSize);
/* 138 */         SpdyWindowUpdateFrame spdyWindowUpdateFrame = new DefaultSpdyWindowUpdateFrame(0, sessionDeltaWindowSize);
/*     */         
/* 140 */         ctx.writeAndFlush(spdyWindowUpdateFrame);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 145 */       if (!this.spdySession.isActiveStream(streamId)) {
/* 146 */         spdyDataFrame.release();
/* 147 */         if (streamId <= this.lastGoodStreamId) {
/* 148 */           issueStreamError(ctx, streamId, SpdyStreamStatus.PROTOCOL_ERROR);
/* 149 */         } else if (!this.sentGoAwayFrame) {
/* 150 */           issueStreamError(ctx, streamId, SpdyStreamStatus.INVALID_STREAM);
/*     */         } 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 157 */       if (this.spdySession.isRemoteSideClosed(streamId)) {
/* 158 */         spdyDataFrame.release();
/* 159 */         issueStreamError(ctx, streamId, SpdyStreamStatus.STREAM_ALREADY_CLOSED);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 164 */       if (!isRemoteInitiatedId(streamId) && !this.spdySession.hasReceivedReply(streamId)) {
/* 165 */         spdyDataFrame.release();
/* 166 */         issueStreamError(ctx, streamId, SpdyStreamStatus.PROTOCOL_ERROR);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 177 */       int newWindowSize = this.spdySession.updateReceiveWindowSize(streamId, deltaWindowSize);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 184 */       if (newWindowSize < this.spdySession.getReceiveWindowSizeLowerBound(streamId)) {
/* 185 */         spdyDataFrame.release();
/* 186 */         issueStreamError(ctx, streamId, SpdyStreamStatus.FLOW_CONTROL_ERROR);
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 192 */       if (newWindowSize < 0) {
/* 193 */         while (spdyDataFrame.content().readableBytes() > this.initialReceiveWindowSize) {
/* 194 */           SpdyDataFrame partialDataFrame = new DefaultSpdyDataFrame(streamId, spdyDataFrame.content().readSlice(this.initialReceiveWindowSize).retain());
/*     */           
/* 196 */           ctx.writeAndFlush(partialDataFrame);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 201 */       if (newWindowSize <= this.initialReceiveWindowSize / 2 && !spdyDataFrame.isLast()) {
/* 202 */         int streamDeltaWindowSize = this.initialReceiveWindowSize - newWindowSize;
/* 203 */         this.spdySession.updateReceiveWindowSize(streamId, streamDeltaWindowSize);
/* 204 */         SpdyWindowUpdateFrame spdyWindowUpdateFrame = new DefaultSpdyWindowUpdateFrame(streamId, streamDeltaWindowSize);
/*     */         
/* 206 */         ctx.writeAndFlush(spdyWindowUpdateFrame);
/*     */       } 
/*     */ 
/*     */       
/* 210 */       if (spdyDataFrame.isLast()) {
/* 211 */         halfCloseStream(streamId, true, ctx.newSucceededFuture());
/*     */       }
/*     */     }
/* 214 */     else if (msg instanceof SpdySynStreamFrame) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 230 */       SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)msg;
/* 231 */       int streamId = spdySynStreamFrame.streamId();
/*     */ 
/*     */       
/* 234 */       if (spdySynStreamFrame.isInvalid() || !isRemoteInitiatedId(streamId) || this.spdySession.isActiveStream(streamId)) {
/*     */ 
/*     */         
/* 237 */         issueStreamError(ctx, streamId, SpdyStreamStatus.PROTOCOL_ERROR);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 242 */       if (streamId <= this.lastGoodStreamId) {
/* 243 */         issueSessionError(ctx, SpdySessionStatus.PROTOCOL_ERROR);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 248 */       byte priority = spdySynStreamFrame.priority();
/* 249 */       boolean remoteSideClosed = spdySynStreamFrame.isLast();
/* 250 */       boolean localSideClosed = spdySynStreamFrame.isUnidirectional();
/* 251 */       if (!acceptStream(streamId, priority, remoteSideClosed, localSideClosed)) {
/* 252 */         issueStreamError(ctx, streamId, SpdyStreamStatus.REFUSED_STREAM);
/*     */         
/*     */         return;
/*     */       } 
/* 256 */     } else if (msg instanceof SpdySynReplyFrame) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 265 */       SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame)msg;
/* 266 */       int streamId = spdySynReplyFrame.streamId();
/*     */ 
/*     */       
/* 269 */       if (spdySynReplyFrame.isInvalid() || isRemoteInitiatedId(streamId) || this.spdySession.isRemoteSideClosed(streamId)) {
/*     */ 
/*     */         
/* 272 */         issueStreamError(ctx, streamId, SpdyStreamStatus.INVALID_STREAM);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 277 */       if (this.spdySession.hasReceivedReply(streamId)) {
/* 278 */         issueStreamError(ctx, streamId, SpdyStreamStatus.STREAM_IN_USE);
/*     */         
/*     */         return;
/*     */       } 
/* 282 */       this.spdySession.receivedReply(streamId);
/*     */ 
/*     */       
/* 285 */       if (spdySynReplyFrame.isLast()) {
/* 286 */         halfCloseStream(streamId, true, ctx.newSucceededFuture());
/*     */       }
/*     */     }
/* 289 */     else if (msg instanceof SpdyRstStreamFrame) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 300 */       SpdyRstStreamFrame spdyRstStreamFrame = (SpdyRstStreamFrame)msg;
/* 301 */       removeStream(spdyRstStreamFrame.streamId(), ctx.newSucceededFuture());
/*     */     }
/* 303 */     else if (msg instanceof SpdySettingsFrame) {
/*     */       
/* 305 */       SpdySettingsFrame spdySettingsFrame = (SpdySettingsFrame)msg;
/*     */       
/* 307 */       int settingsMinorVersion = spdySettingsFrame.getValue(0);
/* 308 */       if (settingsMinorVersion >= 0 && settingsMinorVersion != this.minorVersion) {
/*     */         
/* 310 */         issueSessionError(ctx, SpdySessionStatus.PROTOCOL_ERROR);
/*     */         
/*     */         return;
/*     */       } 
/* 314 */       int newConcurrentStreams = spdySettingsFrame.getValue(4);
/*     */       
/* 316 */       if (newConcurrentStreams >= 0) {
/* 317 */         this.remoteConcurrentStreams = newConcurrentStreams;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 323 */       if (spdySettingsFrame.isPersisted(7)) {
/* 324 */         spdySettingsFrame.removeValue(7);
/*     */       }
/* 326 */       spdySettingsFrame.setPersistValue(7, false);
/*     */       
/* 328 */       int newInitialWindowSize = spdySettingsFrame.getValue(7);
/*     */       
/* 330 */       if (newInitialWindowSize >= 0) {
/* 331 */         updateInitialSendWindowSize(newInitialWindowSize);
/*     */       }
/*     */     }
/* 334 */     else if (msg instanceof SpdyPingFrame) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 345 */       SpdyPingFrame spdyPingFrame = (SpdyPingFrame)msg;
/*     */       
/* 347 */       if (isRemoteInitiatedId(spdyPingFrame.id())) {
/* 348 */         ctx.writeAndFlush(spdyPingFrame);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 353 */       if (this.pings.get() == 0) {
/*     */         return;
/*     */       }
/* 356 */       this.pings.getAndDecrement();
/*     */     }
/* 358 */     else if (msg instanceof SpdyGoAwayFrame) {
/*     */       
/* 360 */       this.receivedGoAwayFrame = true;
/*     */     }
/* 362 */     else if (msg instanceof SpdyHeadersFrame) {
/*     */       
/* 364 */       SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)msg;
/* 365 */       int streamId = spdyHeadersFrame.streamId();
/*     */ 
/*     */       
/* 368 */       if (spdyHeadersFrame.isInvalid()) {
/* 369 */         issueStreamError(ctx, streamId, SpdyStreamStatus.PROTOCOL_ERROR);
/*     */         
/*     */         return;
/*     */       } 
/* 373 */       if (this.spdySession.isRemoteSideClosed(streamId)) {
/* 374 */         issueStreamError(ctx, streamId, SpdyStreamStatus.INVALID_STREAM);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 379 */       if (spdyHeadersFrame.isLast()) {
/* 380 */         halfCloseStream(streamId, true, ctx.newSucceededFuture());
/*     */       }
/*     */     }
/* 383 */     else if (msg instanceof SpdyWindowUpdateFrame) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 395 */       SpdyWindowUpdateFrame spdyWindowUpdateFrame = (SpdyWindowUpdateFrame)msg;
/* 396 */       int streamId = spdyWindowUpdateFrame.streamId();
/* 397 */       int deltaWindowSize = spdyWindowUpdateFrame.deltaWindowSize();
/*     */ 
/*     */       
/* 400 */       if (streamId != 0 && this.spdySession.isLocalSideClosed(streamId)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 405 */       if (this.spdySession.getSendWindowSize(streamId) > Integer.MAX_VALUE - deltaWindowSize) {
/* 406 */         if (streamId == 0) {
/* 407 */           issueSessionError(ctx, SpdySessionStatus.PROTOCOL_ERROR);
/*     */         } else {
/* 409 */           issueStreamError(ctx, streamId, SpdyStreamStatus.FLOW_CONTROL_ERROR);
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/* 414 */       updateSendWindowSize(ctx, streamId, deltaWindowSize);
/*     */     } 
/*     */     
/* 417 */     ctx.fireChannelRead(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 422 */     for (Integer streamId : this.spdySession.activeStreams().keySet()) {
/* 423 */       removeStream(streamId.intValue(), ctx.newSucceededFuture());
/*     */     }
/* 425 */     ctx.fireChannelInactive();
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 430 */     if (cause instanceof SpdyProtocolException) {
/* 431 */       issueSessionError(ctx, SpdySessionStatus.PROTOCOL_ERROR);
/*     */     }
/*     */     
/* 434 */     ctx.fireExceptionCaught(cause);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 439 */     sendGoAwayFrame(ctx, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 444 */     if (msg instanceof SpdyDataFrame || msg instanceof SpdySynStreamFrame || msg instanceof SpdySynReplyFrame || msg instanceof SpdyRstStreamFrame || msg instanceof SpdySettingsFrame || msg instanceof SpdyPingFrame || msg instanceof SpdyGoAwayFrame || msg instanceof SpdyHeadersFrame || msg instanceof SpdyWindowUpdateFrame) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 454 */       handleOutboundMessage(ctx, msg, promise);
/*     */     } else {
/* 456 */       ctx.write(msg, promise);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleOutboundMessage(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 461 */     if (msg instanceof SpdyDataFrame) {
/*     */       
/* 463 */       SpdyDataFrame spdyDataFrame = (SpdyDataFrame)msg;
/* 464 */       int streamId = spdyDataFrame.streamId();
/*     */ 
/*     */       
/* 467 */       if (this.spdySession.isLocalSideClosed(streamId)) {
/* 468 */         spdyDataFrame.release();
/* 469 */         promise.setFailure(PROTOCOL_EXCEPTION);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 486 */       int dataLength = spdyDataFrame.content().readableBytes();
/* 487 */       int sendWindowSize = this.spdySession.getSendWindowSize(streamId);
/* 488 */       int sessionSendWindowSize = this.spdySession.getSendWindowSize(0);
/* 489 */       sendWindowSize = Math.min(sendWindowSize, sessionSendWindowSize);
/*     */       
/* 491 */       if (sendWindowSize <= 0) {
/*     */         
/* 493 */         this.spdySession.putPendingWrite(streamId, new SpdySession.PendingWrite(spdyDataFrame, promise)); return;
/*     */       } 
/* 495 */       if (sendWindowSize < dataLength) {
/*     */         
/* 497 */         this.spdySession.updateSendWindowSize(streamId, -1 * sendWindowSize);
/* 498 */         this.spdySession.updateSendWindowSize(0, -1 * sendWindowSize);
/*     */ 
/*     */         
/* 501 */         SpdyDataFrame partialDataFrame = new DefaultSpdyDataFrame(streamId, spdyDataFrame.content().readSlice(sendWindowSize).retain());
/*     */ 
/*     */ 
/*     */         
/* 505 */         this.spdySession.putPendingWrite(streamId, new SpdySession.PendingWrite(spdyDataFrame, promise));
/*     */ 
/*     */ 
/*     */         
/* 509 */         final ChannelHandlerContext context = ctx;
/* 510 */         ctx.write(partialDataFrame).addListener((GenericFutureListener)new ChannelFutureListener()
/*     */             {
/*     */               public void operationComplete(ChannelFuture future) throws Exception {
/* 513 */                 if (!future.isSuccess()) {
/* 514 */                   SpdySessionHandler.this.issueSessionError(context, SpdySessionStatus.INTERNAL_ERROR);
/*     */                 }
/*     */               }
/*     */             });
/*     */         
/*     */         return;
/*     */       } 
/* 521 */       this.spdySession.updateSendWindowSize(streamId, -1 * dataLength);
/* 522 */       this.spdySession.updateSendWindowSize(0, -1 * dataLength);
/*     */ 
/*     */ 
/*     */       
/* 526 */       final ChannelHandlerContext context = ctx;
/* 527 */       promise.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture future) throws Exception {
/* 530 */               if (!future.isSuccess()) {
/* 531 */                 SpdySessionHandler.this.issueSessionError(context, SpdySessionStatus.INTERNAL_ERROR);
/*     */               }
/*     */             }
/*     */           });
/*     */ 
/*     */ 
/*     */       
/* 538 */       if (spdyDataFrame.isLast()) {
/* 539 */         halfCloseStream(streamId, false, (ChannelFuture)promise);
/*     */       }
/*     */     }
/* 542 */     else if (msg instanceof SpdySynStreamFrame) {
/*     */       
/* 544 */       SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)msg;
/* 545 */       int streamId = spdySynStreamFrame.streamId();
/*     */       
/* 547 */       if (isRemoteInitiatedId(streamId)) {
/* 548 */         promise.setFailure(PROTOCOL_EXCEPTION);
/*     */         
/*     */         return;
/*     */       } 
/* 552 */       byte priority = spdySynStreamFrame.priority();
/* 553 */       boolean remoteSideClosed = spdySynStreamFrame.isUnidirectional();
/* 554 */       boolean localSideClosed = spdySynStreamFrame.isLast();
/* 555 */       if (!acceptStream(streamId, priority, remoteSideClosed, localSideClosed)) {
/* 556 */         promise.setFailure(PROTOCOL_EXCEPTION);
/*     */         
/*     */         return;
/*     */       } 
/* 560 */     } else if (msg instanceof SpdySynReplyFrame) {
/*     */       
/* 562 */       SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame)msg;
/* 563 */       int streamId = spdySynReplyFrame.streamId();
/*     */ 
/*     */       
/* 566 */       if (!isRemoteInitiatedId(streamId) || this.spdySession.isLocalSideClosed(streamId)) {
/* 567 */         promise.setFailure(PROTOCOL_EXCEPTION);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 572 */       if (spdySynReplyFrame.isLast()) {
/* 573 */         halfCloseStream(streamId, false, (ChannelFuture)promise);
/*     */       }
/*     */     }
/* 576 */     else if (msg instanceof SpdyRstStreamFrame) {
/*     */       
/* 578 */       SpdyRstStreamFrame spdyRstStreamFrame = (SpdyRstStreamFrame)msg;
/* 579 */       removeStream(spdyRstStreamFrame.streamId(), (ChannelFuture)promise);
/*     */     }
/* 581 */     else if (msg instanceof SpdySettingsFrame) {
/*     */       
/* 583 */       SpdySettingsFrame spdySettingsFrame = (SpdySettingsFrame)msg;
/*     */       
/* 585 */       int settingsMinorVersion = spdySettingsFrame.getValue(0);
/* 586 */       if (settingsMinorVersion >= 0 && settingsMinorVersion != this.minorVersion) {
/*     */         
/* 588 */         promise.setFailure(PROTOCOL_EXCEPTION);
/*     */         
/*     */         return;
/*     */       } 
/* 592 */       int newConcurrentStreams = spdySettingsFrame.getValue(4);
/*     */       
/* 594 */       if (newConcurrentStreams >= 0) {
/* 595 */         this.localConcurrentStreams = newConcurrentStreams;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 601 */       if (spdySettingsFrame.isPersisted(7)) {
/* 602 */         spdySettingsFrame.removeValue(7);
/*     */       }
/* 604 */       spdySettingsFrame.setPersistValue(7, false);
/*     */       
/* 606 */       int newInitialWindowSize = spdySettingsFrame.getValue(7);
/*     */       
/* 608 */       if (newInitialWindowSize >= 0) {
/* 609 */         updateInitialReceiveWindowSize(newInitialWindowSize);
/*     */       }
/*     */     }
/* 612 */     else if (msg instanceof SpdyPingFrame) {
/*     */       
/* 614 */       SpdyPingFrame spdyPingFrame = (SpdyPingFrame)msg;
/* 615 */       if (isRemoteInitiatedId(spdyPingFrame.id())) {
/* 616 */         ctx.fireExceptionCaught(new IllegalArgumentException("invalid PING ID: " + spdyPingFrame.id()));
/*     */         
/*     */         return;
/*     */       } 
/* 620 */       this.pings.getAndIncrement();
/*     */     } else {
/* 622 */       if (msg instanceof SpdyGoAwayFrame) {
/*     */ 
/*     */ 
/*     */         
/* 626 */         promise.setFailure(PROTOCOL_EXCEPTION);
/*     */         return;
/*     */       } 
/* 629 */       if (msg instanceof SpdyHeadersFrame) {
/*     */         
/* 631 */         SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)msg;
/* 632 */         int streamId = spdyHeadersFrame.streamId();
/*     */ 
/*     */         
/* 635 */         if (this.spdySession.isLocalSideClosed(streamId)) {
/* 636 */           promise.setFailure(PROTOCOL_EXCEPTION);
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 641 */         if (spdyHeadersFrame.isLast()) {
/* 642 */           halfCloseStream(streamId, false, (ChannelFuture)promise);
/*     */         }
/*     */       }
/* 645 */       else if (msg instanceof SpdyWindowUpdateFrame) {
/*     */ 
/*     */         
/* 648 */         promise.setFailure(PROTOCOL_EXCEPTION);
/*     */         return;
/*     */       } 
/*     */     } 
/* 652 */     ctx.write(msg, promise);
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
/*     */   private void issueSessionError(ChannelHandlerContext ctx, SpdySessionStatus status) {
/* 667 */     sendGoAwayFrame(ctx, status).addListener((GenericFutureListener)new ClosingChannelFutureListener(ctx, ctx.newPromise()));
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
/*     */   private void issueStreamError(ChannelHandlerContext ctx, int streamId, SpdyStreamStatus status) {
/* 682 */     boolean fireChannelRead = !this.spdySession.isRemoteSideClosed(streamId);
/* 683 */     ChannelPromise promise = ctx.newPromise();
/* 684 */     removeStream(streamId, (ChannelFuture)promise);
/*     */     
/* 686 */     SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, status);
/* 687 */     ctx.writeAndFlush(spdyRstStreamFrame, promise);
/* 688 */     if (fireChannelRead) {
/* 689 */       ctx.fireChannelRead(spdyRstStreamFrame);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isRemoteInitiatedId(int id) {
/* 698 */     boolean serverId = SpdyCodecUtil.isServerId(id);
/* 699 */     return ((this.server && !serverId) || (!this.server && serverId));
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void updateInitialSendWindowSize(int newInitialWindowSize) {
/* 704 */     int deltaWindowSize = newInitialWindowSize - this.initialSendWindowSize;
/* 705 */     this.initialSendWindowSize = newInitialWindowSize;
/* 706 */     this.spdySession.updateAllSendWindowSizes(deltaWindowSize);
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void updateInitialReceiveWindowSize(int newInitialWindowSize) {
/* 711 */     int deltaWindowSize = newInitialWindowSize - this.initialReceiveWindowSize;
/* 712 */     this.initialReceiveWindowSize = newInitialWindowSize;
/* 713 */     this.spdySession.updateAllReceiveWindowSizes(deltaWindowSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized boolean acceptStream(int streamId, byte priority, boolean remoteSideClosed, boolean localSideClosed) {
/* 720 */     if (this.receivedGoAwayFrame || this.sentGoAwayFrame) {
/* 721 */       return false;
/*     */     }
/*     */     
/* 724 */     boolean remote = isRemoteInitiatedId(streamId);
/* 725 */     int maxConcurrentStreams = remote ? this.localConcurrentStreams : this.remoteConcurrentStreams;
/* 726 */     if (this.spdySession.numActiveStreams(remote) >= maxConcurrentStreams) {
/* 727 */       return false;
/*     */     }
/* 729 */     this.spdySession.acceptStream(streamId, priority, remoteSideClosed, localSideClosed, this.initialSendWindowSize, this.initialReceiveWindowSize, remote);
/*     */ 
/*     */     
/* 732 */     if (remote) {
/* 733 */       this.lastGoodStreamId = streamId;
/*     */     }
/* 735 */     return true;
/*     */   }
/*     */   
/*     */   private void halfCloseStream(int streamId, boolean remote, ChannelFuture future) {
/* 739 */     if (remote) {
/* 740 */       this.spdySession.closeRemoteSide(streamId, isRemoteInitiatedId(streamId));
/*     */     } else {
/* 742 */       this.spdySession.closeLocalSide(streamId, isRemoteInitiatedId(streamId));
/*     */     } 
/* 744 */     if (this.closeSessionFutureListener != null && this.spdySession.noActiveStreams()) {
/* 745 */       future.addListener((GenericFutureListener)this.closeSessionFutureListener);
/*     */     }
/*     */   }
/*     */   
/*     */   private void removeStream(int streamId, ChannelFuture future) {
/* 750 */     this.spdySession.removeStream(streamId, STREAM_CLOSED, isRemoteInitiatedId(streamId));
/*     */     
/* 752 */     if (this.closeSessionFutureListener != null && this.spdySession.noActiveStreams()) {
/* 753 */       future.addListener((GenericFutureListener)this.closeSessionFutureListener);
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateSendWindowSize(final ChannelHandlerContext ctx, int streamId, int deltaWindowSize) {
/* 758 */     this.spdySession.updateSendWindowSize(streamId, deltaWindowSize);
/*     */ 
/*     */     
/*     */     while (true) {
/* 762 */       SpdySession.PendingWrite pendingWrite = this.spdySession.getPendingWrite(streamId);
/* 763 */       if (pendingWrite == null) {
/*     */         return;
/*     */       }
/*     */       
/* 767 */       SpdyDataFrame spdyDataFrame = pendingWrite.spdyDataFrame;
/* 768 */       int dataFrameSize = spdyDataFrame.content().readableBytes();
/* 769 */       int writeStreamId = spdyDataFrame.streamId();
/* 770 */       int sendWindowSize = this.spdySession.getSendWindowSize(writeStreamId);
/* 771 */       int sessionSendWindowSize = this.spdySession.getSendWindowSize(0);
/* 772 */       sendWindowSize = Math.min(sendWindowSize, sessionSendWindowSize);
/*     */       
/* 774 */       if (sendWindowSize <= 0)
/*     */         return; 
/* 776 */       if (sendWindowSize < dataFrameSize) {
/*     */         
/* 778 */         this.spdySession.updateSendWindowSize(writeStreamId, -1 * sendWindowSize);
/* 779 */         this.spdySession.updateSendWindowSize(0, -1 * sendWindowSize);
/*     */ 
/*     */         
/* 782 */         SpdyDataFrame partialDataFrame = new DefaultSpdyDataFrame(writeStreamId, spdyDataFrame.content().readSlice(sendWindowSize).retain());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 787 */         ctx.writeAndFlush(partialDataFrame).addListener((GenericFutureListener)new ChannelFutureListener()
/*     */             {
/*     */               public void operationComplete(ChannelFuture future) throws Exception {
/* 790 */                 if (!future.isSuccess()) {
/* 791 */                   SpdySessionHandler.this.issueSessionError(ctx, SpdySessionStatus.INTERNAL_ERROR);
/*     */                 }
/*     */               }
/*     */             });
/*     */         continue;
/*     */       } 
/* 797 */       this.spdySession.removePendingWrite(writeStreamId);
/* 798 */       this.spdySession.updateSendWindowSize(writeStreamId, -1 * dataFrameSize);
/* 799 */       this.spdySession.updateSendWindowSize(0, -1 * dataFrameSize);
/*     */ 
/*     */       
/* 802 */       if (spdyDataFrame.isLast()) {
/* 803 */         halfCloseStream(writeStreamId, false, (ChannelFuture)pendingWrite.promise);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 808 */       ctx.writeAndFlush(spdyDataFrame, pendingWrite.promise).addListener((GenericFutureListener)new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture future) throws Exception {
/* 811 */               if (!future.isSuccess()) {
/* 812 */                 SpdySessionHandler.this.issueSessionError(ctx, SpdySessionStatus.INTERNAL_ERROR);
/*     */               }
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendGoAwayFrame(ChannelHandlerContext ctx, ChannelPromise future) {
/* 822 */     if (!ctx.channel().isActive()) {
/* 823 */       ctx.close(future);
/*     */       
/*     */       return;
/*     */     } 
/* 827 */     ChannelFuture f = sendGoAwayFrame(ctx, SpdySessionStatus.OK);
/* 828 */     if (this.spdySession.noActiveStreams()) {
/* 829 */       f.addListener((GenericFutureListener)new ClosingChannelFutureListener(ctx, future));
/*     */     } else {
/* 831 */       this.closeSessionFutureListener = new ClosingChannelFutureListener(ctx, future);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized ChannelFuture sendGoAwayFrame(ChannelHandlerContext ctx, SpdySessionStatus status) {
/* 838 */     if (!this.sentGoAwayFrame) {
/* 839 */       this.sentGoAwayFrame = true;
/* 840 */       SpdyGoAwayFrame spdyGoAwayFrame = new DefaultSpdyGoAwayFrame(this.lastGoodStreamId, status);
/* 841 */       return ctx.writeAndFlush(spdyGoAwayFrame);
/*     */     } 
/* 843 */     return ctx.newSucceededFuture();
/*     */   }
/*     */   
/*     */   private static final class ClosingChannelFutureListener
/*     */     implements ChannelFutureListener {
/*     */     private final ChannelHandlerContext ctx;
/*     */     private final ChannelPromise promise;
/*     */     
/*     */     ClosingChannelFutureListener(ChannelHandlerContext ctx, ChannelPromise promise) {
/* 852 */       this.ctx = ctx;
/* 853 */       this.promise = promise;
/*     */     }
/*     */ 
/*     */     
/*     */     public void operationComplete(ChannelFuture sentGoAwayFuture) throws Exception {
/* 858 */       this.ctx.close(this.promise);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\spdy\SpdySessionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */