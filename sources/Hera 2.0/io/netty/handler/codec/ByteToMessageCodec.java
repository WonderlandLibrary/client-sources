/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.internal.TypeParameterMatcher;
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
/*     */ public abstract class ByteToMessageCodec<I>
/*     */   extends ChannelDuplexHandler
/*     */ {
/*     */   private final TypeParameterMatcher outboundMsgMatcher;
/*     */   private final MessageToByteEncoder<I> encoder;
/*     */   
/*  39 */   private final ByteToMessageDecoder decoder = new ByteToMessageDecoder()
/*     */     {
/*     */       public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*  42 */         ByteToMessageCodec.this.decode(ctx, in, out);
/*     */       }
/*     */ 
/*     */       
/*     */       protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*  47 */         ByteToMessageCodec.this.decodeLast(ctx, in, out);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ByteToMessageCodec() {
/*  55 */     this(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ByteToMessageCodec(Class<? extends I> outboundMessageType) {
/*  62 */     this(outboundMessageType, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ByteToMessageCodec(boolean preferDirect) {
/*  73 */     this.outboundMsgMatcher = TypeParameterMatcher.find(this, ByteToMessageCodec.class, "I");
/*  74 */     this.encoder = new Encoder(preferDirect);
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
/*     */   protected ByteToMessageCodec(Class<? extends I> outboundMessageType, boolean preferDirect) {
/*  86 */     checkForSharableAnnotation();
/*  87 */     this.outboundMsgMatcher = TypeParameterMatcher.get(outboundMessageType);
/*  88 */     this.encoder = new Encoder(preferDirect);
/*     */   }
/*     */   
/*     */   private void checkForSharableAnnotation() {
/*  92 */     if (isSharable()) {
/*  93 */       throw new IllegalStateException("@Sharable annotation is not allowed");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptOutboundMessage(Object msg) throws Exception {
/* 103 */     return this.outboundMsgMatcher.match(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 108 */     this.decoder.channelRead(ctx, msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 113 */     this.encoder.write(ctx, msg, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void encode(ChannelHandlerContext paramChannelHandlerContext, I paramI, ByteBuf paramByteBuf) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 130 */     decode(ctx, in, out);
/*     */   }
/*     */   
/*     */   private final class Encoder extends MessageToByteEncoder<I> {
/*     */     Encoder(boolean preferDirect) {
/* 135 */       super(preferDirect);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean acceptOutboundMessage(Object msg) throws Exception {
/* 140 */       return ByteToMessageCodec.this.acceptOutboundMessage(msg);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void encode(ChannelHandlerContext ctx, I msg, ByteBuf out) throws Exception {
/* 145 */       ByteToMessageCodec.this.encode(ctx, msg, out);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\ByteToMessageCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */