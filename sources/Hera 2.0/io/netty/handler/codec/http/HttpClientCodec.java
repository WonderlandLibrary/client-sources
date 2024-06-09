/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandler;
/*     */ import io.netty.channel.ChannelOutboundHandler;
/*     */ import io.netty.channel.CombinedChannelDuplexHandler;
/*     */ import io.netty.handler.codec.PrematureChannelClosureException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.atomic.AtomicLong;
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
/*     */ public final class HttpClientCodec
/*     */   extends CombinedChannelDuplexHandler<HttpResponseDecoder, HttpRequestEncoder>
/*     */ {
/*  47 */   private final Queue<HttpMethod> queue = new ArrayDeque<HttpMethod>();
/*     */ 
/*     */   
/*     */   private boolean done;
/*     */   
/*  52 */   private final AtomicLong requestResponseCounter = new AtomicLong();
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean failOnMissingResponse;
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpClientCodec() {
/*  61 */     this(4096, 8192, 8192, false);
/*     */   }
/*     */   
/*     */   public void setSingleDecode(boolean singleDecode) {
/*  65 */     ((HttpResponseDecoder)inboundHandler()).setSingleDecode(singleDecode);
/*     */   }
/*     */   
/*     */   public boolean isSingleDecode() {
/*  69 */     return ((HttpResponseDecoder)inboundHandler()).isSingleDecode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpClientCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize) {
/*  76 */     this(maxInitialLineLength, maxHeaderSize, maxChunkSize, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpClientCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean failOnMissingResponse) {
/*  84 */     this(maxInitialLineLength, maxHeaderSize, maxChunkSize, failOnMissingResponse, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpClientCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean failOnMissingResponse, boolean validateHeaders) {
/*  93 */     init((ChannelInboundHandler)new Decoder(maxInitialLineLength, maxHeaderSize, maxChunkSize, validateHeaders), (ChannelOutboundHandler)new Encoder());
/*  94 */     this.failOnMissingResponse = failOnMissingResponse;
/*     */   }
/*     */   
/*     */   private final class Encoder
/*     */     extends HttpRequestEncoder {
/*     */     private Encoder() {}
/*     */     
/*     */     protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
/* 102 */       if (msg instanceof HttpRequest && !HttpClientCodec.this.done) {
/* 103 */         HttpClientCodec.this.queue.offer(((HttpRequest)msg).getMethod());
/*     */       }
/*     */       
/* 106 */       super.encode(ctx, msg, out);
/*     */       
/* 108 */       if (HttpClientCodec.this.failOnMissingResponse)
/*     */       {
/* 110 */         if (msg instanceof LastHttpContent)
/*     */         {
/* 112 */           HttpClientCodec.this.requestResponseCounter.incrementAndGet();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private final class Decoder extends HttpResponseDecoder {
/*     */     Decoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders) {
/* 120 */       super(maxInitialLineLength, maxHeaderSize, maxChunkSize, validateHeaders);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
/* 126 */       if (HttpClientCodec.this.done) {
/* 127 */         int readable = actualReadableBytes();
/* 128 */         if (readable == 0) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 133 */         out.add(buffer.readBytes(readable));
/*     */       } else {
/* 135 */         int oldSize = out.size();
/* 136 */         super.decode(ctx, buffer, out);
/* 137 */         if (HttpClientCodec.this.failOnMissingResponse) {
/* 138 */           int size = out.size();
/* 139 */           for (int i = oldSize; i < size; i++) {
/* 140 */             decrement(out.get(i));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private void decrement(Object msg) {
/* 147 */       if (msg == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 152 */       if (msg instanceof LastHttpContent) {
/* 153 */         HttpClientCodec.this.requestResponseCounter.decrementAndGet();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isContentAlwaysEmpty(HttpMessage msg) {
/* 159 */       int statusCode = ((HttpResponse)msg).getStatus().code();
/* 160 */       if (statusCode == 100)
/*     */       {
/* 162 */         return true;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 167 */       HttpMethod method = HttpClientCodec.this.queue.poll();
/*     */       
/* 169 */       char firstChar = method.name().charAt(0);
/* 170 */       switch (firstChar) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 'H':
/* 176 */           if (HttpMethod.HEAD.equals(method)) {
/* 177 */             return true;
/*     */           }
/*     */           break;
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
/*     */         case 'C':
/* 195 */           if (statusCode == 200 && 
/* 196 */             HttpMethod.CONNECT.equals(method)) {
/*     */             
/* 198 */             HttpClientCodec.this.done = true;
/* 199 */             HttpClientCodec.this.queue.clear();
/* 200 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 206 */       return super.isContentAlwaysEmpty(msg);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 212 */       super.channelInactive(ctx);
/*     */       
/* 214 */       if (HttpClientCodec.this.failOnMissingResponse) {
/* 215 */         long missingResponses = HttpClientCodec.this.requestResponseCounter.get();
/* 216 */         if (missingResponses > 0L)
/* 217 */           ctx.fireExceptionCaught((Throwable)new PrematureChannelClosureException("channel gone inactive with " + missingResponses + " missing response(s)")); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\HttpClientCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */