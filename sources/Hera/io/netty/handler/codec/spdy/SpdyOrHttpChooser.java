/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandler;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import io.netty.handler.codec.http.HttpObjectAggregator;
/*     */ import io.netty.handler.codec.http.HttpRequestDecoder;
/*     */ import io.netty.handler.codec.http.HttpResponseEncoder;
/*     */ import io.netty.handler.ssl.SslHandler;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.util.List;
/*     */ import javax.net.ssl.SSLEngine;
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
/*     */ public abstract class SpdyOrHttpChooser
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private final int maxSpdyContentLength;
/*     */   private final int maxHttpContentLength;
/*     */   
/*     */   public enum SelectedProtocol
/*     */   {
/*  43 */     SPDY_3_1("spdy/3.1"),
/*  44 */     HTTP_1_1("http/1.1"),
/*  45 */     HTTP_1_0("http/1.0"),
/*  46 */     UNKNOWN("Unknown");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     SelectedProtocol(String defaultName) {
/*  51 */       this.name = defaultName;
/*     */     }
/*     */     
/*     */     public String protocolName() {
/*  55 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static SelectedProtocol protocol(String name) {
/*  66 */       for (SelectedProtocol protocol : values()) {
/*  67 */         if (protocol.protocolName().equals(name)) {
/*  68 */           return protocol;
/*     */         }
/*     */       } 
/*  71 */       return UNKNOWN;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SpdyOrHttpChooser(int maxSpdyContentLength, int maxHttpContentLength) {
/*  79 */     this.maxSpdyContentLength = maxSpdyContentLength;
/*  80 */     this.maxHttpContentLength = maxHttpContentLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SelectedProtocol getProtocol(SSLEngine engine) {
/*  89 */     String[] protocol = StringUtil.split(engine.getSession().getProtocol(), ':');
/*  90 */     if (protocol.length < 2)
/*     */     {
/*  92 */       return SelectedProtocol.HTTP_1_1;
/*     */     }
/*  94 */     SelectedProtocol selectedProtocol = SelectedProtocol.protocol(protocol[1]);
/*  95 */     return selectedProtocol;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 100 */     if (initPipeline(ctx))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 105 */       ctx.pipeline().remove((ChannelHandler)this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean initPipeline(ChannelHandlerContext ctx) {
/* 112 */     SslHandler handler = (SslHandler)ctx.pipeline().get(SslHandler.class);
/* 113 */     if (handler == null)
/*     */     {
/* 115 */       throw new IllegalStateException("SslHandler is needed for SPDY");
/*     */     }
/*     */     
/* 118 */     SelectedProtocol protocol = getProtocol(handler.engine());
/* 119 */     switch (protocol) {
/*     */       
/*     */       case UNKNOWN:
/* 122 */         return false;
/*     */       case SPDY_3_1:
/* 124 */         addSpdyHandlers(ctx, SpdyVersion.SPDY_3_1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 133 */         return true;case HTTP_1_0: case HTTP_1_1: addHttpHandlers(ctx); return true;
/*     */     } 
/*     */     throw new IllegalStateException("Unknown SelectedProtocol");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addSpdyHandlers(ChannelHandlerContext ctx, SpdyVersion version) {
/* 140 */     ChannelPipeline pipeline = ctx.pipeline();
/* 141 */     pipeline.addLast("spdyFrameCodec", (ChannelHandler)new SpdyFrameCodec(version));
/* 142 */     pipeline.addLast("spdySessionHandler", (ChannelHandler)new SpdySessionHandler(version, true));
/* 143 */     pipeline.addLast("spdyHttpEncoder", (ChannelHandler)new SpdyHttpEncoder(version));
/* 144 */     pipeline.addLast("spdyHttpDecoder", (ChannelHandler)new SpdyHttpDecoder(version, this.maxSpdyContentLength));
/* 145 */     pipeline.addLast("spdyStreamIdHandler", (ChannelHandler)new SpdyHttpResponseStreamIdHandler());
/* 146 */     pipeline.addLast("httpRequestHandler", (ChannelHandler)createHttpRequestHandlerForSpdy());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addHttpHandlers(ChannelHandlerContext ctx) {
/* 153 */     ChannelPipeline pipeline = ctx.pipeline();
/* 154 */     pipeline.addLast("httpRequestDecoder", (ChannelHandler)new HttpRequestDecoder());
/* 155 */     pipeline.addLast("httpResponseEncoder", (ChannelHandler)new HttpResponseEncoder());
/* 156 */     pipeline.addLast("httpChunkAggregator", (ChannelHandler)new HttpObjectAggregator(this.maxHttpContentLength));
/* 157 */     pipeline.addLast("httpRequestHandler", (ChannelHandler)createHttpRequestHandlerForHttp());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ChannelInboundHandler createHttpRequestHandlerForHttp();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ChannelInboundHandler createHttpRequestHandlerForSpdy() {
/* 175 */     return createHttpRequestHandlerForHttp();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\spdy\SpdyOrHttpChooser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */