/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.CompositeByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.MessageToMessageDecoder;
/*     */ import io.netty.handler.codec.TooLongFrameException;
/*     */ import java.util.List;
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
/*     */ public class WebSocketFrameAggregator
/*     */   extends MessageToMessageDecoder<WebSocketFrame>
/*     */ {
/*     */   private final int maxFrameSize;
/*     */   private WebSocketFrame currentFrame;
/*     */   private boolean tooLongFrameFound;
/*     */   
/*     */   public WebSocketFrameAggregator(int maxFrameSize) {
/*  44 */     if (maxFrameSize < 1) {
/*  45 */       throw new IllegalArgumentException("maxFrameSize must be > 0");
/*     */     }
/*  47 */     this.maxFrameSize = maxFrameSize;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
/*  52 */     if (this.currentFrame == null) {
/*  53 */       this.tooLongFrameFound = false;
/*  54 */       if (msg.isFinalFragment()) {
/*  55 */         out.add(msg.retain());
/*     */         return;
/*     */       } 
/*  58 */       CompositeByteBuf compositeByteBuf = ctx.alloc().compositeBuffer().addComponent(msg.content().retain());
/*  59 */       compositeByteBuf.writerIndex(compositeByteBuf.writerIndex() + msg.content().readableBytes());
/*     */       
/*  61 */       if (msg instanceof TextWebSocketFrame) {
/*  62 */         this.currentFrame = new TextWebSocketFrame(true, msg.rsv(), (ByteBuf)compositeByteBuf);
/*  63 */       } else if (msg instanceof BinaryWebSocketFrame) {
/*  64 */         this.currentFrame = new BinaryWebSocketFrame(true, msg.rsv(), (ByteBuf)compositeByteBuf);
/*     */       } else {
/*  66 */         compositeByteBuf.release();
/*  67 */         throw new IllegalStateException("WebSocket frame was not of type TextWebSocketFrame or BinaryWebSocketFrame");
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*  72 */     if (msg instanceof ContinuationWebSocketFrame) {
/*  73 */       if (this.tooLongFrameFound) {
/*  74 */         if (msg.isFinalFragment()) {
/*  75 */           this.currentFrame = null;
/*     */         }
/*     */         return;
/*     */       } 
/*  79 */       CompositeByteBuf content = (CompositeByteBuf)this.currentFrame.content();
/*  80 */       if (content.readableBytes() > this.maxFrameSize - msg.content().readableBytes()) {
/*     */         
/*  82 */         this.currentFrame.release();
/*  83 */         this.tooLongFrameFound = true;
/*  84 */         throw new TooLongFrameException("WebSocketFrame length exceeded " + content + " bytes.");
/*     */       } 
/*     */ 
/*     */       
/*  88 */       content.addComponent(msg.content().retain());
/*  89 */       content.writerIndex(content.writerIndex() + msg.content().readableBytes());
/*     */       
/*  91 */       if (msg.isFinalFragment()) {
/*  92 */         WebSocketFrame currentFrame = this.currentFrame;
/*  93 */         this.currentFrame = null;
/*  94 */         out.add(currentFrame);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 102 */     out.add(msg.retain());
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 107 */     super.channelInactive(ctx);
/*     */     
/* 109 */     if (this.currentFrame != null) {
/* 110 */       this.currentFrame.release();
/* 111 */       this.currentFrame = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 117 */     super.handlerRemoved(ctx);
/*     */ 
/*     */     
/* 120 */     if (this.currentFrame != null) {
/* 121 */       this.currentFrame.release();
/* 122 */       this.currentFrame = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\websocketx\WebSocketFrameAggregator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */