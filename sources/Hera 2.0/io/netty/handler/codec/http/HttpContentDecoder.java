/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.embedded.EmbeddedChannel;
/*     */ import io.netty.handler.codec.MessageToMessageDecoder;
/*     */ import io.netty.util.ReferenceCountUtil;
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
/*     */ 
/*     */ 
/*     */ public abstract class HttpContentDecoder
/*     */   extends MessageToMessageDecoder<HttpObject>
/*     */ {
/*     */   private EmbeddedChannel decoder;
/*     */   private HttpMessage message;
/*     */   private boolean decodeStarted;
/*     */   private boolean continueResponse;
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) throws Exception {
/*  54 */     if (msg instanceof HttpResponse && ((HttpResponse)msg).getStatus().code() == 100) {
/*     */       
/*  56 */       if (!(msg instanceof LastHttpContent)) {
/*  57 */         this.continueResponse = true;
/*     */       }
/*     */       
/*  60 */       out.add(ReferenceCountUtil.retain(msg));
/*     */       
/*     */       return;
/*     */     } 
/*  64 */     if (this.continueResponse) {
/*  65 */       if (msg instanceof LastHttpContent) {
/*  66 */         this.continueResponse = false;
/*     */       }
/*     */       
/*  69 */       out.add(ReferenceCountUtil.retain(msg));
/*     */       
/*     */       return;
/*     */     } 
/*  73 */     if (msg instanceof HttpMessage) {
/*  74 */       assert this.message == null;
/*  75 */       this.message = (HttpMessage)msg;
/*  76 */       this.decodeStarted = false;
/*  77 */       cleanup();
/*     */     } 
/*     */     
/*  80 */     if (msg instanceof HttpContent) {
/*  81 */       HttpContent c = (HttpContent)msg;
/*     */       
/*  83 */       if (!this.decodeStarted) {
/*  84 */         this.decodeStarted = true;
/*  85 */         HttpMessage message = this.message;
/*  86 */         HttpHeaders headers = message.headers();
/*  87 */         this.message = null;
/*     */ 
/*     */         
/*  90 */         String contentEncoding = headers.get("Content-Encoding");
/*  91 */         if (contentEncoding != null) {
/*  92 */           contentEncoding = contentEncoding.trim();
/*     */         } else {
/*  94 */           contentEncoding = "identity";
/*     */         } 
/*     */         
/*  97 */         if ((this.decoder = newContentDecoder(contentEncoding)) != null) {
/*     */ 
/*     */           
/* 100 */           String targetContentEncoding = getTargetContentEncoding(contentEncoding);
/* 101 */           if ("identity".equals(targetContentEncoding)) {
/*     */ 
/*     */             
/* 104 */             headers.remove("Content-Encoding");
/*     */           } else {
/* 106 */             headers.set("Content-Encoding", targetContentEncoding);
/*     */           } 
/*     */           
/* 109 */           out.add(message);
/* 110 */           decodeContent(c, out);
/*     */ 
/*     */           
/* 113 */           if (headers.contains("Content-Length")) {
/* 114 */             int contentLength = 0;
/* 115 */             int size = out.size();
/* 116 */             for (int i = 0; i < size; i++) {
/* 117 */               Object o = out.get(i);
/* 118 */               if (o instanceof HttpContent) {
/* 119 */                 contentLength += ((HttpContent)o).content().readableBytes();
/*     */               }
/*     */             } 
/* 122 */             headers.set("Content-Length", Integer.toString(contentLength));
/*     */           } 
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 129 */         if (c instanceof LastHttpContent) {
/* 130 */           this.decodeStarted = false;
/*     */         }
/* 132 */         out.add(message);
/* 133 */         out.add(c.retain());
/*     */         
/*     */         return;
/*     */       } 
/* 137 */       if (this.decoder != null) {
/* 138 */         decodeContent(c, out);
/*     */       } else {
/* 140 */         if (c instanceof LastHttpContent) {
/* 141 */           this.decodeStarted = false;
/*     */         }
/* 143 */         out.add(c.retain());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void decodeContent(HttpContent c, List<Object> out) {
/* 149 */     ByteBuf content = c.content();
/*     */     
/* 151 */     decode(content, out);
/*     */     
/* 153 */     if (c instanceof LastHttpContent) {
/* 154 */       finishDecode(out);
/*     */       
/* 156 */       LastHttpContent last = (LastHttpContent)c;
/*     */ 
/*     */       
/* 159 */       HttpHeaders headers = last.trailingHeaders();
/* 160 */       if (headers.isEmpty()) {
/* 161 */         out.add(LastHttpContent.EMPTY_LAST_CONTENT);
/*     */       } else {
/* 163 */         out.add(new ComposedLastHttpContent(headers));
/*     */       } 
/*     */     } 
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getTargetContentEncoding(String contentEncoding) throws Exception {
/* 189 */     return "identity";
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 194 */     cleanup();
/* 195 */     super.handlerRemoved(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 200 */     cleanup();
/* 201 */     super.channelInactive(ctx);
/*     */   }
/*     */   
/*     */   private void cleanup() {
/* 205 */     if (this.decoder != null) {
/*     */       
/* 207 */       if (this.decoder.finish()) {
/*     */         while (true) {
/* 209 */           ByteBuf buf = (ByteBuf)this.decoder.readOutbound();
/* 210 */           if (buf == null) {
/*     */             break;
/*     */           }
/*     */           
/* 214 */           buf.release();
/*     */         } 
/*     */       }
/* 217 */       this.decoder = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void decode(ByteBuf in, List<Object> out) {
/* 223 */     this.decoder.writeInbound(new Object[] { in.retain() });
/* 224 */     fetchDecoderOutput(out);
/*     */   }
/*     */   
/*     */   private void finishDecode(List<Object> out) {
/* 228 */     if (this.decoder.finish()) {
/* 229 */       fetchDecoderOutput(out);
/*     */     }
/* 231 */     this.decodeStarted = false;
/* 232 */     this.decoder = null;
/*     */   }
/*     */   
/*     */   private void fetchDecoderOutput(List<Object> out) {
/*     */     while (true) {
/* 237 */       ByteBuf buf = (ByteBuf)this.decoder.readInbound();
/* 238 */       if (buf == null) {
/*     */         break;
/*     */       }
/* 241 */       if (!buf.isReadable()) {
/* 242 */         buf.release();
/*     */         continue;
/*     */       } 
/* 245 */       out.add(new DefaultHttpContent(buf));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract EmbeddedChannel newContentDecoder(String paramString) throws Exception;
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\HttpContentDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */