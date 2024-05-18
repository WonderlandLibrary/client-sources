/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.MessageToMessageDecoder;
/*     */ import io.netty.handler.codec.TooLongFrameException;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpRequest;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import io.netty.handler.codec.http.FullHttpMessage;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMessage;
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpdyHttpDecoder
/*     */   extends MessageToMessageDecoder<SpdyFrame>
/*     */ {
/*     */   private final boolean validateHeaders;
/*     */   private final int spdyVersion;
/*     */   private final int maxContentLength;
/*     */   private final Map<Integer, FullHttpMessage> messageMap;
/*     */   
/*     */   public SpdyHttpDecoder(SpdyVersion version, int maxContentLength) {
/*  56 */     this(version, maxContentLength, new HashMap<Integer, FullHttpMessage>(), true);
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
/*     */   public SpdyHttpDecoder(SpdyVersion version, int maxContentLength, boolean validateHeaders) {
/*  69 */     this(version, maxContentLength, new HashMap<Integer, FullHttpMessage>(), validateHeaders);
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
/*     */   protected SpdyHttpDecoder(SpdyVersion version, int maxContentLength, Map<Integer, FullHttpMessage> messageMap) {
/*  82 */     this(version, maxContentLength, messageMap, true);
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
/*     */   protected SpdyHttpDecoder(SpdyVersion version, int maxContentLength, Map<Integer, FullHttpMessage> messageMap, boolean validateHeaders) {
/*  97 */     if (version == null) {
/*  98 */       throw new NullPointerException("version");
/*     */     }
/* 100 */     if (maxContentLength <= 0) {
/* 101 */       throw new IllegalArgumentException("maxContentLength must be a positive integer: " + maxContentLength);
/*     */     }
/*     */     
/* 104 */     this.spdyVersion = version.getVersion();
/* 105 */     this.maxContentLength = maxContentLength;
/* 106 */     this.messageMap = messageMap;
/* 107 */     this.validateHeaders = validateHeaders;
/*     */   }
/*     */   
/*     */   protected FullHttpMessage putMessage(int streamId, FullHttpMessage message) {
/* 111 */     return this.messageMap.put(Integer.valueOf(streamId), message);
/*     */   }
/*     */   
/*     */   protected FullHttpMessage getMessage(int streamId) {
/* 115 */     return this.messageMap.get(Integer.valueOf(streamId));
/*     */   }
/*     */   
/*     */   protected FullHttpMessage removeMessage(int streamId) {
/* 119 */     return this.messageMap.remove(Integer.valueOf(streamId));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, SpdyFrame msg, List<Object> out) throws Exception {
/* 125 */     if (msg instanceof SpdySynStreamFrame) {
/*     */ 
/*     */       
/* 128 */       SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)msg;
/* 129 */       int streamId = spdySynStreamFrame.streamId();
/*     */       
/* 131 */       if (SpdyCodecUtil.isServerId(streamId)) {
/*     */         
/* 133 */         int associatedToStreamId = spdySynStreamFrame.associatedStreamId();
/*     */ 
/*     */ 
/*     */         
/* 137 */         if (associatedToStreamId == 0) {
/* 138 */           SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.INVALID_STREAM);
/*     */           
/* 140 */           ctx.writeAndFlush(spdyRstStreamFrame);
/*     */           
/*     */           return;
/*     */         } 
/* 144 */         String URL = SpdyHeaders.getUrl(this.spdyVersion, spdySynStreamFrame);
/*     */ 
/*     */ 
/*     */         
/* 148 */         if (URL == null) {
/* 149 */           SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.PROTOCOL_ERROR);
/*     */           
/* 151 */           ctx.writeAndFlush(spdyRstStreamFrame);
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 157 */         if (spdySynStreamFrame.isTruncated()) {
/* 158 */           SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.INTERNAL_ERROR);
/*     */           
/* 160 */           ctx.writeAndFlush(spdyRstStreamFrame);
/*     */           
/*     */           return;
/*     */         } 
/*     */         try {
/* 165 */           FullHttpResponse httpResponseWithEntity = createHttpResponse(ctx, this.spdyVersion, spdySynStreamFrame, this.validateHeaders);
/*     */ 
/*     */ 
/*     */           
/* 169 */           SpdyHttpHeaders.setStreamId((HttpMessage)httpResponseWithEntity, streamId);
/* 170 */           SpdyHttpHeaders.setAssociatedToStreamId((HttpMessage)httpResponseWithEntity, associatedToStreamId);
/* 171 */           SpdyHttpHeaders.setPriority((HttpMessage)httpResponseWithEntity, spdySynStreamFrame.priority());
/* 172 */           SpdyHttpHeaders.setUrl((HttpMessage)httpResponseWithEntity, URL);
/*     */           
/* 174 */           if (spdySynStreamFrame.isLast()) {
/* 175 */             HttpHeaders.setContentLength((HttpMessage)httpResponseWithEntity, 0L);
/* 176 */             out.add(httpResponseWithEntity);
/*     */           } else {
/*     */             
/* 179 */             putMessage(streamId, (FullHttpMessage)httpResponseWithEntity);
/*     */           } 
/* 181 */         } catch (Exception e) {
/* 182 */           SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.PROTOCOL_ERROR);
/*     */           
/* 184 */           ctx.writeAndFlush(spdyRstStreamFrame);
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 191 */         if (spdySynStreamFrame.isTruncated()) {
/* 192 */           SpdySynReplyFrame spdySynReplyFrame = new DefaultSpdySynReplyFrame(streamId);
/* 193 */           spdySynReplyFrame.setLast(true);
/* 194 */           SpdyHeaders.setStatus(this.spdyVersion, spdySynReplyFrame, HttpResponseStatus.REQUEST_HEADER_FIELDS_TOO_LARGE);
/*     */ 
/*     */           
/* 197 */           SpdyHeaders.setVersion(this.spdyVersion, spdySynReplyFrame, HttpVersion.HTTP_1_0);
/* 198 */           ctx.writeAndFlush(spdySynReplyFrame);
/*     */           
/*     */           return;
/*     */         } 
/*     */         try {
/* 203 */           FullHttpRequest httpRequestWithEntity = createHttpRequest(this.spdyVersion, spdySynStreamFrame);
/*     */ 
/*     */           
/* 206 */           SpdyHttpHeaders.setStreamId((HttpMessage)httpRequestWithEntity, streamId);
/*     */           
/* 208 */           if (spdySynStreamFrame.isLast()) {
/* 209 */             out.add(httpRequestWithEntity);
/*     */           } else {
/*     */             
/* 212 */             putMessage(streamId, (FullHttpMessage)httpRequestWithEntity);
/*     */           } 
/* 214 */         } catch (Exception e) {
/*     */ 
/*     */ 
/*     */           
/* 218 */           SpdySynReplyFrame spdySynReplyFrame = new DefaultSpdySynReplyFrame(streamId);
/* 219 */           spdySynReplyFrame.setLast(true);
/* 220 */           SpdyHeaders.setStatus(this.spdyVersion, spdySynReplyFrame, HttpResponseStatus.BAD_REQUEST);
/* 221 */           SpdyHeaders.setVersion(this.spdyVersion, spdySynReplyFrame, HttpVersion.HTTP_1_0);
/* 222 */           ctx.writeAndFlush(spdySynReplyFrame);
/*     */         }
/*     */       
/*     */       } 
/* 226 */     } else if (msg instanceof SpdySynReplyFrame) {
/*     */       
/* 228 */       SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame)msg;
/* 229 */       int streamId = spdySynReplyFrame.streamId();
/*     */ 
/*     */ 
/*     */       
/* 233 */       if (spdySynReplyFrame.isTruncated()) {
/* 234 */         SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.INTERNAL_ERROR);
/*     */         
/* 236 */         ctx.writeAndFlush(spdyRstStreamFrame);
/*     */         
/*     */         return;
/*     */       } 
/*     */       try {
/* 241 */         FullHttpResponse httpResponseWithEntity = createHttpResponse(ctx, this.spdyVersion, spdySynReplyFrame, this.validateHeaders);
/*     */ 
/*     */ 
/*     */         
/* 245 */         SpdyHttpHeaders.setStreamId((HttpMessage)httpResponseWithEntity, streamId);
/*     */         
/* 247 */         if (spdySynReplyFrame.isLast()) {
/* 248 */           HttpHeaders.setContentLength((HttpMessage)httpResponseWithEntity, 0L);
/* 249 */           out.add(httpResponseWithEntity);
/*     */         } else {
/*     */           
/* 252 */           putMessage(streamId, (FullHttpMessage)httpResponseWithEntity);
/*     */         } 
/* 254 */       } catch (Exception e) {
/*     */ 
/*     */         
/* 257 */         SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.PROTOCOL_ERROR);
/*     */         
/* 259 */         ctx.writeAndFlush(spdyRstStreamFrame);
/*     */       }
/*     */     
/* 262 */     } else if (msg instanceof SpdyHeadersFrame) {
/*     */       
/* 264 */       SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)msg;
/* 265 */       int streamId = spdyHeadersFrame.streamId();
/* 266 */       FullHttpMessage fullHttpMessage = getMessage(streamId);
/*     */ 
/*     */       
/* 269 */       if (fullHttpMessage == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 274 */       if (!spdyHeadersFrame.isTruncated()) {
/* 275 */         for (Map.Entry<String, String> e : (Iterable<Map.Entry<String, String>>)spdyHeadersFrame.headers()) {
/* 276 */           fullHttpMessage.headers().add(e.getKey(), e.getValue());
/*     */         }
/*     */       }
/*     */       
/* 280 */       if (spdyHeadersFrame.isLast()) {
/* 281 */         HttpHeaders.setContentLength((HttpMessage)fullHttpMessage, fullHttpMessage.content().readableBytes());
/* 282 */         removeMessage(streamId);
/* 283 */         out.add(fullHttpMessage);
/*     */       }
/*     */     
/* 286 */     } else if (msg instanceof SpdyDataFrame) {
/*     */       
/* 288 */       SpdyDataFrame spdyDataFrame = (SpdyDataFrame)msg;
/* 289 */       int streamId = spdyDataFrame.streamId();
/* 290 */       FullHttpMessage fullHttpMessage = getMessage(streamId);
/*     */ 
/*     */       
/* 293 */       if (fullHttpMessage == null) {
/*     */         return;
/*     */       }
/*     */       
/* 297 */       ByteBuf content = fullHttpMessage.content();
/* 298 */       if (content.readableBytes() > this.maxContentLength - spdyDataFrame.content().readableBytes()) {
/* 299 */         removeMessage(streamId);
/* 300 */         throw new TooLongFrameException("HTTP content length exceeded " + this.maxContentLength + " bytes.");
/*     */       } 
/*     */ 
/*     */       
/* 304 */       ByteBuf spdyDataFrameData = spdyDataFrame.content();
/* 305 */       int spdyDataFrameDataLen = spdyDataFrameData.readableBytes();
/* 306 */       content.writeBytes(spdyDataFrameData, spdyDataFrameData.readerIndex(), spdyDataFrameDataLen);
/*     */       
/* 308 */       if (spdyDataFrame.isLast()) {
/* 309 */         HttpHeaders.setContentLength((HttpMessage)fullHttpMessage, content.readableBytes());
/* 310 */         removeMessage(streamId);
/* 311 */         out.add(fullHttpMessage);
/*     */       }
/*     */     
/* 314 */     } else if (msg instanceof SpdyRstStreamFrame) {
/*     */       
/* 316 */       SpdyRstStreamFrame spdyRstStreamFrame = (SpdyRstStreamFrame)msg;
/* 317 */       int streamId = spdyRstStreamFrame.streamId();
/* 318 */       removeMessage(streamId);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static FullHttpRequest createHttpRequest(int spdyVersion, SpdyHeadersFrame requestFrame) throws Exception {
/* 325 */     SpdyHeaders headers = requestFrame.headers();
/* 326 */     HttpMethod method = SpdyHeaders.getMethod(spdyVersion, requestFrame);
/* 327 */     String url = SpdyHeaders.getUrl(spdyVersion, requestFrame);
/* 328 */     HttpVersion httpVersion = SpdyHeaders.getVersion(spdyVersion, requestFrame);
/* 329 */     SpdyHeaders.removeMethod(spdyVersion, requestFrame);
/* 330 */     SpdyHeaders.removeUrl(spdyVersion, requestFrame);
/* 331 */     SpdyHeaders.removeVersion(spdyVersion, requestFrame);
/*     */     
/* 333 */     DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(httpVersion, method, url);
/*     */ 
/*     */     
/* 336 */     SpdyHeaders.removeScheme(spdyVersion, requestFrame);
/*     */ 
/*     */     
/* 339 */     String host = headers.get(":host");
/* 340 */     headers.remove(":host");
/* 341 */     defaultFullHttpRequest.headers().set("Host", host);
/*     */     
/* 343 */     for (Map.Entry<String, String> e : (Iterable<Map.Entry<String, String>>)requestFrame.headers()) {
/* 344 */       defaultFullHttpRequest.headers().add(e.getKey(), e.getValue());
/*     */     }
/*     */ 
/*     */     
/* 348 */     HttpHeaders.setKeepAlive((HttpMessage)defaultFullHttpRequest, true);
/*     */ 
/*     */     
/* 351 */     defaultFullHttpRequest.headers().remove("Transfer-Encoding");
/*     */     
/* 353 */     return (FullHttpRequest)defaultFullHttpRequest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static FullHttpResponse createHttpResponse(ChannelHandlerContext ctx, int spdyVersion, SpdyHeadersFrame responseFrame, boolean validateHeaders) throws Exception {
/* 361 */     HttpResponseStatus status = SpdyHeaders.getStatus(spdyVersion, responseFrame);
/* 362 */     HttpVersion version = SpdyHeaders.getVersion(spdyVersion, responseFrame);
/* 363 */     SpdyHeaders.removeStatus(spdyVersion, responseFrame);
/* 364 */     SpdyHeaders.removeVersion(spdyVersion, responseFrame);
/*     */     
/* 366 */     DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(version, status, ctx.alloc().buffer(), validateHeaders);
/* 367 */     for (Map.Entry<String, String> e : (Iterable<Map.Entry<String, String>>)responseFrame.headers()) {
/* 368 */       defaultFullHttpResponse.headers().add(e.getKey(), e.getValue());
/*     */     }
/*     */ 
/*     */     
/* 372 */     HttpHeaders.setKeepAlive((HttpMessage)defaultFullHttpResponse, true);
/*     */ 
/*     */     
/* 375 */     defaultFullHttpResponse.headers().remove("Transfer-Encoding");
/* 376 */     defaultFullHttpResponse.headers().remove("Trailer");
/*     */     
/* 378 */     return (FullHttpResponse)defaultFullHttpResponse;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\spdy\SpdyHttpDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */