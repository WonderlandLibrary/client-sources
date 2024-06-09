/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.FileRegion;
/*     */ import io.netty.handler.codec.MessageToMessageEncoder;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.internal.StringUtil;
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
/*     */ public abstract class HttpObjectEncoder<H extends HttpMessage>
/*     */   extends MessageToMessageEncoder<Object>
/*     */ {
/*  44 */   private static final byte[] CRLF = new byte[] { 13, 10 };
/*  45 */   private static final byte[] ZERO_CRLF = new byte[] { 48, 13, 10 };
/*  46 */   private static final byte[] ZERO_CRLF_CRLF = new byte[] { 48, 13, 10, 13, 10 };
/*  47 */   private static final ByteBuf CRLF_BUF = Unpooled.unreleasableBuffer(Unpooled.directBuffer(CRLF.length).writeBytes(CRLF));
/*  48 */   private static final ByteBuf ZERO_CRLF_CRLF_BUF = Unpooled.unreleasableBuffer(Unpooled.directBuffer(ZERO_CRLF_CRLF.length).writeBytes(ZERO_CRLF_CRLF));
/*     */   
/*     */   private static final int ST_INIT = 0;
/*     */   
/*     */   private static final int ST_CONTENT_NON_CHUNK = 1;
/*     */   
/*     */   private static final int ST_CONTENT_CHUNK = 2;
/*  55 */   private int state = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
/*  60 */     ByteBuf buf = null;
/*  61 */     if (msg instanceof HttpMessage) {
/*  62 */       if (this.state != 0) {
/*  63 */         throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(msg));
/*     */       }
/*     */ 
/*     */       
/*  67 */       HttpMessage httpMessage = (HttpMessage)msg;
/*     */       
/*  69 */       buf = ctx.alloc().buffer();
/*     */       
/*  71 */       encodeInitialLine(buf, (H)httpMessage);
/*  72 */       HttpHeaders.encode(httpMessage.headers(), buf);
/*  73 */       buf.writeBytes(CRLF);
/*  74 */       this.state = HttpHeaders.isTransferEncodingChunked(httpMessage) ? 2 : 1;
/*     */     } 
/*  76 */     if (msg instanceof HttpContent || msg instanceof ByteBuf || msg instanceof FileRegion) {
/*  77 */       if (this.state == 0) {
/*  78 */         throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(msg));
/*     */       }
/*     */       
/*  81 */       long contentLength = contentLength(msg);
/*  82 */       if (this.state == 1) {
/*  83 */         if (contentLength > 0L) {
/*  84 */           if (buf != null && buf.writableBytes() >= contentLength && msg instanceof HttpContent) {
/*     */             
/*  86 */             buf.writeBytes(((HttpContent)msg).content());
/*  87 */             out.add(buf);
/*     */           } else {
/*  89 */             if (buf != null) {
/*  90 */               out.add(buf);
/*     */             }
/*  92 */             out.add(encodeAndRetain(msg));
/*     */           }
/*     */         
/*  95 */         } else if (buf != null) {
/*  96 */           out.add(buf);
/*     */         }
/*     */         else {
/*     */           
/* 100 */           out.add(Unpooled.EMPTY_BUFFER);
/*     */         } 
/*     */ 
/*     */         
/* 104 */         if (msg instanceof LastHttpContent) {
/* 105 */           this.state = 0;
/*     */         }
/* 107 */       } else if (this.state == 2) {
/* 108 */         if (buf != null) {
/* 109 */           out.add(buf);
/*     */         }
/* 111 */         encodeChunkedContent(ctx, msg, contentLength, out);
/*     */       } else {
/* 113 */         throw new Error();
/*     */       }
/*     */     
/* 116 */     } else if (buf != null) {
/* 117 */       out.add(buf);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void encodeChunkedContent(ChannelHandlerContext ctx, Object msg, long contentLength, List<Object> out) {
/* 123 */     if (contentLength > 0L) {
/* 124 */       byte[] length = Long.toHexString(contentLength).getBytes(CharsetUtil.US_ASCII);
/* 125 */       ByteBuf buf = ctx.alloc().buffer(length.length + 2);
/* 126 */       buf.writeBytes(length);
/* 127 */       buf.writeBytes(CRLF);
/* 128 */       out.add(buf);
/* 129 */       out.add(encodeAndRetain(msg));
/* 130 */       out.add(CRLF_BUF.duplicate());
/*     */     } 
/*     */     
/* 133 */     if (msg instanceof LastHttpContent) {
/* 134 */       HttpHeaders headers = ((LastHttpContent)msg).trailingHeaders();
/* 135 */       if (headers.isEmpty()) {
/* 136 */         out.add(ZERO_CRLF_CRLF_BUF.duplicate());
/*     */       } else {
/* 138 */         ByteBuf buf = ctx.alloc().buffer();
/* 139 */         buf.writeBytes(ZERO_CRLF);
/* 140 */         HttpHeaders.encode(headers, buf);
/* 141 */         buf.writeBytes(CRLF);
/* 142 */         out.add(buf);
/*     */       } 
/*     */       
/* 145 */       this.state = 0;
/*     */     }
/* 147 */     else if (contentLength == 0L) {
/*     */ 
/*     */       
/* 150 */       out.add(Unpooled.EMPTY_BUFFER);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptOutboundMessage(Object msg) throws Exception {
/* 157 */     return (msg instanceof HttpObject || msg instanceof ByteBuf || msg instanceof FileRegion);
/*     */   }
/*     */   
/*     */   private static Object encodeAndRetain(Object msg) {
/* 161 */     if (msg instanceof ByteBuf) {
/* 162 */       return ((ByteBuf)msg).retain();
/*     */     }
/* 164 */     if (msg instanceof HttpContent) {
/* 165 */       return ((HttpContent)msg).content().retain();
/*     */     }
/* 167 */     if (msg instanceof FileRegion) {
/* 168 */       return ((FileRegion)msg).retain();
/*     */     }
/* 170 */     throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(msg));
/*     */   }
/*     */   
/*     */   private static long contentLength(Object msg) {
/* 174 */     if (msg instanceof HttpContent) {
/* 175 */       return ((HttpContent)msg).content().readableBytes();
/*     */     }
/* 177 */     if (msg instanceof ByteBuf) {
/* 178 */       return ((ByteBuf)msg).readableBytes();
/*     */     }
/* 180 */     if (msg instanceof FileRegion) {
/* 181 */       return ((FileRegion)msg).count();
/*     */     }
/* 183 */     throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(msg));
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   protected static void encodeAscii(String s, ByteBuf buf) {
/* 188 */     HttpHeaders.encodeAscii0(s, buf);
/*     */   }
/*     */   
/*     */   protected abstract void encodeInitialLine(ByteBuf paramByteBuf, H paramH) throws Exception;
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\HttpObjectEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */