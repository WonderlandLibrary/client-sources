/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.CompositeByteBuf;
/*     */ import io.netty.buffer.DefaultByteBufHolder;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.DecoderResult;
/*     */ import io.netty.handler.codec.MessageToMessageDecoder;
/*     */ import io.netty.handler.codec.TooLongFrameException;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpObjectAggregator
/*     */   extends MessageToMessageDecoder<HttpObject>
/*     */ {
/*     */   public static final int DEFAULT_MAX_COMPOSITEBUFFER_COMPONENTS = 1024;
/*  56 */   private static final FullHttpResponse CONTINUE = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE, Unpooled.EMPTY_BUFFER);
/*     */   
/*     */   private final int maxContentLength;
/*     */   
/*     */   private AggregatedFullHttpMessage currentMessage;
/*     */   
/*     */   private boolean tooLongFrameFound;
/*  63 */   private int maxCumulationBufferComponents = 1024;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ChannelHandlerContext ctx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpObjectAggregator(int maxContentLength) {
/*  75 */     if (maxContentLength <= 0) {
/*  76 */       throw new IllegalArgumentException("maxContentLength must be a positive integer: " + maxContentLength);
/*     */     }
/*     */ 
/*     */     
/*  80 */     this.maxContentLength = maxContentLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getMaxCumulationBufferComponents() {
/*  90 */     return this.maxCumulationBufferComponents;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setMaxCumulationBufferComponents(int maxCumulationBufferComponents) {
/* 101 */     if (maxCumulationBufferComponents < 2) {
/* 102 */       throw new IllegalArgumentException("maxCumulationBufferComponents: " + maxCumulationBufferComponents + " (expected: >= 2)");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 107 */     if (this.ctx == null) {
/* 108 */       this.maxCumulationBufferComponents = maxCumulationBufferComponents;
/*     */     } else {
/* 110 */       throw new IllegalStateException("decoder properties cannot be changed once the decoder is added to a pipeline.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(final ChannelHandlerContext ctx, HttpObject msg, List<Object> out) throws Exception {
/* 117 */     AggregatedFullHttpMessage currentMessage = this.currentMessage;
/*     */     
/* 119 */     if (msg instanceof HttpMessage) {
/* 120 */       this.tooLongFrameFound = false;
/* 121 */       assert currentMessage == null;
/*     */       
/* 123 */       HttpMessage m = (HttpMessage)msg;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 130 */       if (HttpHeaders.is100ContinueExpected(m)) {
/* 131 */         ctx.writeAndFlush(CONTINUE).addListener((GenericFutureListener)new ChannelFutureListener()
/*     */             {
/*     */               public void operationComplete(ChannelFuture future) throws Exception {
/* 134 */                 if (!future.isSuccess()) {
/* 135 */                   ctx.fireExceptionCaught(future.cause());
/*     */                 }
/*     */               }
/*     */             });
/*     */       }
/*     */       
/* 141 */       if (!m.getDecoderResult().isSuccess()) {
/* 142 */         HttpHeaders.removeTransferEncodingChunked(m);
/* 143 */         out.add(toFullMessage(m));
/* 144 */         this.currentMessage = null;
/*     */         return;
/*     */       } 
/* 147 */       if (msg instanceof HttpRequest) {
/* 148 */         HttpRequest header = (HttpRequest)msg;
/* 149 */         this.currentMessage = currentMessage = new AggregatedFullHttpRequest(header, (ByteBuf)ctx.alloc().compositeBuffer(this.maxCumulationBufferComponents), null);
/*     */       }
/* 151 */       else if (msg instanceof HttpResponse) {
/* 152 */         HttpResponse header = (HttpResponse)msg;
/* 153 */         this.currentMessage = currentMessage = new AggregatedFullHttpResponse(header, (ByteBuf)Unpooled.compositeBuffer(this.maxCumulationBufferComponents), null);
/*     */       }
/*     */       else {
/*     */         
/* 157 */         throw new Error();
/*     */       } 
/*     */ 
/*     */       
/* 161 */       HttpHeaders.removeTransferEncodingChunked(currentMessage);
/* 162 */     } else if (msg instanceof HttpContent) {
/* 163 */       boolean last; if (this.tooLongFrameFound) {
/* 164 */         if (msg instanceof LastHttpContent) {
/* 165 */           this.currentMessage = null;
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/* 170 */       assert currentMessage != null;
/*     */ 
/*     */       
/* 173 */       HttpContent chunk = (HttpContent)msg;
/* 174 */       CompositeByteBuf content = (CompositeByteBuf)currentMessage.content();
/*     */       
/* 176 */       if (content.readableBytes() > this.maxContentLength - chunk.content().readableBytes()) {
/* 177 */         this.tooLongFrameFound = true;
/*     */ 
/*     */         
/* 180 */         currentMessage.release();
/* 181 */         this.currentMessage = null;
/*     */         
/* 183 */         throw new TooLongFrameException("HTTP content length exceeded " + this.maxContentLength + " bytes.");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 189 */       if (chunk.content().isReadable()) {
/* 190 */         chunk.retain();
/* 191 */         content.addComponent(chunk.content());
/* 192 */         content.writerIndex(content.writerIndex() + chunk.content().readableBytes());
/*     */       } 
/*     */ 
/*     */       
/* 196 */       if (!chunk.getDecoderResult().isSuccess()) {
/* 197 */         currentMessage.setDecoderResult(DecoderResult.failure(chunk.getDecoderResult().cause()));
/*     */         
/* 199 */         last = true;
/*     */       } else {
/* 201 */         last = chunk instanceof LastHttpContent;
/*     */       } 
/*     */       
/* 204 */       if (last) {
/* 205 */         this.currentMessage = null;
/*     */ 
/*     */         
/* 208 */         if (chunk instanceof LastHttpContent) {
/* 209 */           LastHttpContent trailer = (LastHttpContent)chunk;
/* 210 */           currentMessage.setTrailingHeaders(trailer.trailingHeaders());
/*     */         } else {
/* 212 */           currentMessage.setTrailingHeaders(new DefaultHttpHeaders());
/*     */         } 
/*     */ 
/*     */         
/* 216 */         currentMessage.headers().set("Content-Length", String.valueOf(content.readableBytes()));
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 221 */         out.add(currentMessage);
/*     */       } 
/*     */     } else {
/* 224 */       throw new Error();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 230 */     super.channelInactive(ctx);
/*     */ 
/*     */     
/* 233 */     if (this.currentMessage != null) {
/* 234 */       this.currentMessage.release();
/* 235 */       this.currentMessage = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 241 */     this.ctx = ctx;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 246 */     super.handlerRemoved(ctx);
/*     */ 
/*     */     
/* 249 */     if (this.currentMessage != null) {
/* 250 */       this.currentMessage.release();
/* 251 */       this.currentMessage = null;
/*     */     } 
/*     */   }
/*     */   private static FullHttpMessage toFullMessage(HttpMessage msg) {
/*     */     FullHttpMessage fullMsg;
/* 256 */     if (msg instanceof FullHttpMessage) {
/* 257 */       return ((FullHttpMessage)msg).retain();
/*     */     }
/*     */ 
/*     */     
/* 261 */     if (msg instanceof HttpRequest) {
/* 262 */       fullMsg = new AggregatedFullHttpRequest((HttpRequest)msg, Unpooled.EMPTY_BUFFER, new DefaultHttpHeaders());
/*     */     }
/* 264 */     else if (msg instanceof HttpResponse) {
/* 265 */       fullMsg = new AggregatedFullHttpResponse((HttpResponse)msg, Unpooled.EMPTY_BUFFER, new DefaultHttpHeaders());
/*     */     } else {
/*     */       
/* 268 */       throw new IllegalStateException();
/*     */     } 
/*     */     
/* 271 */     return fullMsg;
/*     */   }
/*     */   
/*     */   private static abstract class AggregatedFullHttpMessage extends DefaultByteBufHolder implements FullHttpMessage {
/*     */     protected final HttpMessage message;
/*     */     private HttpHeaders trailingHeaders;
/*     */     
/*     */     private AggregatedFullHttpMessage(HttpMessage message, ByteBuf content, HttpHeaders trailingHeaders) {
/* 279 */       super(content);
/* 280 */       this.message = message;
/* 281 */       this.trailingHeaders = trailingHeaders;
/*     */     }
/*     */     
/*     */     public HttpHeaders trailingHeaders() {
/* 285 */       return this.trailingHeaders;
/*     */     }
/*     */     
/*     */     public void setTrailingHeaders(HttpHeaders trailingHeaders) {
/* 289 */       this.trailingHeaders = trailingHeaders;
/*     */     }
/*     */ 
/*     */     
/*     */     public HttpVersion getProtocolVersion() {
/* 294 */       return this.message.getProtocolVersion();
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpMessage setProtocolVersion(HttpVersion version) {
/* 299 */       this.message.setProtocolVersion(version);
/* 300 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public HttpHeaders headers() {
/* 305 */       return this.message.headers();
/*     */     }
/*     */ 
/*     */     
/*     */     public DecoderResult getDecoderResult() {
/* 310 */       return this.message.getDecoderResult();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDecoderResult(DecoderResult result) {
/* 315 */       this.message.setDecoderResult(result);
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpMessage retain(int increment) {
/* 320 */       super.retain(increment);
/* 321 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpMessage retain() {
/* 326 */       super.retain();
/* 327 */       return this;
/*     */     }
/*     */     
/*     */     public abstract FullHttpMessage copy();
/*     */     
/*     */     public abstract FullHttpMessage duplicate();
/*     */   }
/*     */   
/*     */   private static final class AggregatedFullHttpRequest
/*     */     extends AggregatedFullHttpMessage
/*     */     implements FullHttpRequest
/*     */   {
/*     */     private AggregatedFullHttpRequest(HttpRequest request, ByteBuf content, HttpHeaders trailingHeaders) {
/* 340 */       super(request, content, trailingHeaders);
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpRequest copy() {
/* 345 */       DefaultFullHttpRequest copy = new DefaultFullHttpRequest(getProtocolVersion(), getMethod(), getUri(), content().copy());
/*     */       
/* 347 */       copy.headers().set(headers());
/* 348 */       copy.trailingHeaders().set(trailingHeaders());
/* 349 */       return copy;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpRequest duplicate() {
/* 354 */       DefaultFullHttpRequest duplicate = new DefaultFullHttpRequest(getProtocolVersion(), getMethod(), getUri(), content().duplicate());
/*     */       
/* 356 */       duplicate.headers().set(headers());
/* 357 */       duplicate.trailingHeaders().set(trailingHeaders());
/* 358 */       return duplicate;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpRequest retain(int increment) {
/* 363 */       super.retain(increment);
/* 364 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpRequest retain() {
/* 369 */       super.retain();
/* 370 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpRequest setMethod(HttpMethod method) {
/* 375 */       ((HttpRequest)this.message).setMethod(method);
/* 376 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpRequest setUri(String uri) {
/* 381 */       ((HttpRequest)this.message).setUri(uri);
/* 382 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public HttpMethod getMethod() {
/* 387 */       return ((HttpRequest)this.message).getMethod();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getUri() {
/* 392 */       return ((HttpRequest)this.message).getUri();
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpRequest setProtocolVersion(HttpVersion version) {
/* 397 */       super.setProtocolVersion(version);
/* 398 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class AggregatedFullHttpResponse
/*     */     extends AggregatedFullHttpMessage implements FullHttpResponse {
/*     */     private AggregatedFullHttpResponse(HttpResponse message, ByteBuf content, HttpHeaders trailingHeaders) {
/* 405 */       super(message, content, trailingHeaders);
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpResponse copy() {
/* 410 */       DefaultFullHttpResponse copy = new DefaultFullHttpResponse(getProtocolVersion(), getStatus(), content().copy());
/*     */       
/* 412 */       copy.headers().set(headers());
/* 413 */       copy.trailingHeaders().set(trailingHeaders());
/* 414 */       return copy;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpResponse duplicate() {
/* 419 */       DefaultFullHttpResponse duplicate = new DefaultFullHttpResponse(getProtocolVersion(), getStatus(), content().duplicate());
/*     */       
/* 421 */       duplicate.headers().set(headers());
/* 422 */       duplicate.trailingHeaders().set(trailingHeaders());
/* 423 */       return duplicate;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpResponse setStatus(HttpResponseStatus status) {
/* 428 */       ((HttpResponse)this.message).setStatus(status);
/* 429 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public HttpResponseStatus getStatus() {
/* 434 */       return ((HttpResponse)this.message).getStatus();
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpResponse setProtocolVersion(HttpVersion version) {
/* 439 */       super.setProtocolVersion(version);
/* 440 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpResponse retain(int increment) {
/* 445 */       super.retain(increment);
/* 446 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpResponse retain() {
/* 451 */       super.retain();
/* 452 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\HttpObjectAggregator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */