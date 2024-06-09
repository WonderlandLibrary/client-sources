/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.embedded.EmbeddedChannel;
/*     */ import io.netty.handler.codec.MessageToMessageCodec;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.List;
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
/*     */ public abstract class HttpContentEncoder
/*     */   extends MessageToMessageCodec<HttpRequest, HttpObject>
/*     */ {
/*     */   private enum State
/*     */   {
/*  56 */     PASS_THROUGH,
/*  57 */     AWAIT_HEADERS,
/*  58 */     AWAIT_CONTENT;
/*     */   }
/*     */   
/*  61 */   private final Queue<String> acceptEncodingQueue = new ArrayDeque<String>();
/*     */   private String acceptEncoding;
/*     */   private EmbeddedChannel encoder;
/*  64 */   private State state = State.AWAIT_HEADERS;
/*     */ 
/*     */   
/*     */   public boolean acceptOutboundMessage(Object msg) throws Exception {
/*  68 */     return (msg instanceof HttpContent || msg instanceof HttpResponse);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, HttpRequest msg, List<Object> out) throws Exception {
/*  74 */     String acceptedEncoding = msg.headers().get("Accept-Encoding");
/*  75 */     if (acceptedEncoding == null) {
/*  76 */       acceptedEncoding = "identity";
/*     */     }
/*  78 */     this.acceptEncodingQueue.add(acceptedEncoding);
/*  79 */     out.add(ReferenceCountUtil.retain(msg));
/*     */   }
/*     */   protected void encode(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) throws Exception {
/*     */     HttpResponse res;
/*     */     Result result;
/*  84 */     boolean isFull = (msg instanceof HttpResponse && msg instanceof LastHttpContent);
/*  85 */     switch (this.state) {
/*     */       case AWAIT_HEADERS:
/*  87 */         ensureHeaders(msg);
/*  88 */         assert this.encoder == null;
/*     */         
/*  90 */         res = (HttpResponse)msg;
/*     */         
/*  92 */         if (res.getStatus().code() == 100) {
/*  93 */           if (isFull) {
/*  94 */             out.add(ReferenceCountUtil.retain(res)); break;
/*     */           } 
/*  96 */           out.add(res);
/*     */           
/*  98 */           this.state = State.PASS_THROUGH;
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 104 */         this.acceptEncoding = this.acceptEncodingQueue.poll();
/* 105 */         if (this.acceptEncoding == null) {
/* 106 */           throw new IllegalStateException("cannot send more responses than requests");
/*     */         }
/*     */         
/* 109 */         if (isFull)
/*     */         {
/* 111 */           if (!((ByteBufHolder)res).content().isReadable()) {
/* 112 */             out.add(ReferenceCountUtil.retain(res));
/*     */             
/*     */             break;
/*     */           } 
/*     */         }
/*     */         
/* 118 */         result = beginEncode(res, this.acceptEncoding);
/*     */ 
/*     */         
/* 121 */         if (result == null) {
/* 122 */           if (isFull) {
/* 123 */             out.add(ReferenceCountUtil.retain(res)); break;
/*     */           } 
/* 125 */           out.add(res);
/*     */           
/* 127 */           this.state = State.PASS_THROUGH;
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 132 */         this.encoder = result.contentEncoder();
/*     */ 
/*     */ 
/*     */         
/* 136 */         res.headers().set("Content-Encoding", result.targetContentEncoding());
/*     */ 
/*     */         
/* 139 */         res.headers().remove("Content-Length");
/* 140 */         res.headers().set("Transfer-Encoding", "chunked");
/*     */ 
/*     */         
/* 143 */         if (isFull) {
/*     */           
/* 145 */           HttpResponse newRes = new DefaultHttpResponse(res.getProtocolVersion(), res.getStatus());
/* 146 */           newRes.headers().set(res.headers());
/* 147 */           out.add(newRes);
/*     */         } else {
/*     */           
/* 150 */           out.add(res);
/* 151 */           this.state = State.AWAIT_CONTENT;
/* 152 */           if (!(msg instanceof HttpContent)) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case AWAIT_CONTENT:
/* 161 */         ensureContent(msg);
/* 162 */         if (encodeContent((HttpContent)msg, out)) {
/* 163 */           this.state = State.AWAIT_HEADERS;
/*     */         }
/*     */         break;
/*     */       
/*     */       case PASS_THROUGH:
/* 168 */         ensureContent(msg);
/* 169 */         out.add(ReferenceCountUtil.retain(msg));
/*     */         
/* 171 */         if (msg instanceof LastHttpContent) {
/* 172 */           this.state = State.AWAIT_HEADERS;
/*     */         }
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void ensureHeaders(HttpObject msg) {
/* 180 */     if (!(msg instanceof HttpResponse)) {
/* 181 */       throw new IllegalStateException("unexpected message type: " + msg.getClass().getName() + " (expected: " + HttpResponse.class.getSimpleName() + ')');
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void ensureContent(HttpObject msg) {
/* 188 */     if (!(msg instanceof HttpContent)) {
/* 189 */       throw new IllegalStateException("unexpected message type: " + msg.getClass().getName() + " (expected: " + HttpContent.class.getSimpleName() + ')');
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean encodeContent(HttpContent c, List<Object> out) {
/* 196 */     ByteBuf content = c.content();
/*     */     
/* 198 */     encode(content, out);
/*     */     
/* 200 */     if (c instanceof LastHttpContent) {
/* 201 */       finishEncode(out);
/* 202 */       LastHttpContent last = (LastHttpContent)c;
/*     */ 
/*     */ 
/*     */       
/* 206 */       HttpHeaders headers = last.trailingHeaders();
/* 207 */       if (headers.isEmpty()) {
/* 208 */         out.add(LastHttpContent.EMPTY_LAST_CONTENT);
/*     */       } else {
/* 210 */         out.add(new ComposedLastHttpContent(headers));
/*     */       } 
/* 212 */       return true;
/*     */     } 
/* 214 */     return false;
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
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 235 */     cleanup();
/* 236 */     super.handlerRemoved(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 241 */     cleanup();
/* 242 */     super.channelInactive(ctx);
/*     */   }
/*     */   
/*     */   private void cleanup() {
/* 246 */     if (this.encoder != null) {
/*     */       
/* 248 */       if (this.encoder.finish()) {
/*     */         while (true) {
/* 250 */           ByteBuf buf = (ByteBuf)this.encoder.readOutbound();
/* 251 */           if (buf == null) {
/*     */             break;
/*     */           }
/*     */ 
/*     */           
/* 256 */           buf.release();
/*     */         } 
/*     */       }
/* 259 */       this.encoder = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void encode(ByteBuf in, List<Object> out) {
/* 265 */     this.encoder.writeOutbound(new Object[] { in.retain() });
/* 266 */     fetchEncoderOutput(out);
/*     */   }
/*     */   
/*     */   private void finishEncode(List<Object> out) {
/* 270 */     if (this.encoder.finish()) {
/* 271 */       fetchEncoderOutput(out);
/*     */     }
/* 273 */     this.encoder = null;
/*     */   }
/*     */   
/*     */   private void fetchEncoderOutput(List<Object> out) {
/*     */     while (true) {
/* 278 */       ByteBuf buf = (ByteBuf)this.encoder.readOutbound();
/* 279 */       if (buf == null) {
/*     */         break;
/*     */       }
/* 282 */       if (!buf.isReadable()) {
/* 283 */         buf.release();
/*     */         continue;
/*     */       } 
/* 286 */       out.add(new DefaultHttpContent(buf));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract Result beginEncode(HttpResponse paramHttpResponse, String paramString) throws Exception;
/*     */   
/*     */   public static final class Result { private final String targetContentEncoding;
/*     */     
/*     */     public Result(String targetContentEncoding, EmbeddedChannel contentEncoder) {
/* 295 */       if (targetContentEncoding == null) {
/* 296 */         throw new NullPointerException("targetContentEncoding");
/*     */       }
/* 298 */       if (contentEncoder == null) {
/* 299 */         throw new NullPointerException("contentEncoder");
/*     */       }
/*     */       
/* 302 */       this.targetContentEncoding = targetContentEncoding;
/* 303 */       this.contentEncoder = contentEncoder;
/*     */     }
/*     */     private final EmbeddedChannel contentEncoder;
/*     */     public String targetContentEncoding() {
/* 307 */       return this.targetContentEncoding;
/*     */     }
/*     */     
/*     */     public EmbeddedChannel contentEncoder() {
/* 311 */       return this.contentEncoder;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\HttpContentEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */