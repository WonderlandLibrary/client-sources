/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.MessageToMessageEncoder;
/*     */ import io.netty.handler.codec.UnsupportedMessageTypeException;
/*     */ import io.netty.handler.codec.http.FullHttpMessage;
/*     */ import io.netty.handler.codec.http.HttpContent;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMessage;
/*     */ import io.netty.handler.codec.http.HttpObject;
/*     */ import io.netty.handler.codec.http.HttpRequest;
/*     */ import io.netty.handler.codec.http.HttpResponse;
/*     */ import io.netty.handler.codec.http.LastHttpContent;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpdyHttpEncoder
/*     */   extends MessageToMessageEncoder<HttpObject>
/*     */ {
/*     */   private final int spdyVersion;
/*     */   private int currentStreamId;
/*     */   
/*     */   public SpdyHttpEncoder(SpdyVersion version) {
/* 134 */     if (version == null) {
/* 135 */       throw new NullPointerException("version");
/*     */     }
/* 137 */     this.spdyVersion = version.getVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) throws Exception {
/* 143 */     boolean valid = false;
/* 144 */     boolean last = false;
/*     */     
/* 146 */     if (msg instanceof HttpRequest) {
/*     */       
/* 148 */       HttpRequest httpRequest = (HttpRequest)msg;
/* 149 */       SpdySynStreamFrame spdySynStreamFrame = createSynStreamFrame((HttpMessage)httpRequest);
/* 150 */       out.add(spdySynStreamFrame);
/*     */       
/* 152 */       last = spdySynStreamFrame.isLast();
/* 153 */       valid = true;
/*     */     } 
/* 155 */     if (msg instanceof HttpResponse) {
/*     */       
/* 157 */       HttpResponse httpResponse = (HttpResponse)msg;
/* 158 */       if (httpResponse.headers().contains("X-SPDY-Associated-To-Stream-ID")) {
/* 159 */         SpdySynStreamFrame spdySynStreamFrame = createSynStreamFrame((HttpMessage)httpResponse);
/* 160 */         last = spdySynStreamFrame.isLast();
/* 161 */         out.add(spdySynStreamFrame);
/*     */       } else {
/* 163 */         SpdySynReplyFrame spdySynReplyFrame = createSynReplyFrame(httpResponse);
/* 164 */         last = spdySynReplyFrame.isLast();
/* 165 */         out.add(spdySynReplyFrame);
/*     */       } 
/*     */       
/* 168 */       valid = true;
/*     */     } 
/* 170 */     if (msg instanceof HttpContent && !last) {
/*     */       
/* 172 */       HttpContent chunk = (HttpContent)msg;
/*     */       
/* 174 */       chunk.content().retain();
/* 175 */       SpdyDataFrame spdyDataFrame = new DefaultSpdyDataFrame(this.currentStreamId, chunk.content());
/* 176 */       spdyDataFrame.setLast(chunk instanceof LastHttpContent);
/* 177 */       if (chunk instanceof LastHttpContent) {
/* 178 */         LastHttpContent trailer = (LastHttpContent)chunk;
/* 179 */         HttpHeaders trailers = trailer.trailingHeaders();
/* 180 */         if (trailers.isEmpty()) {
/* 181 */           out.add(spdyDataFrame);
/*     */         } else {
/*     */           
/* 184 */           SpdyHeadersFrame spdyHeadersFrame = new DefaultSpdyHeadersFrame(this.currentStreamId);
/* 185 */           for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>)trailers) {
/* 186 */             spdyHeadersFrame.headers().add(entry.getKey(), entry.getValue());
/*     */           }
/*     */ 
/*     */           
/* 190 */           out.add(spdyHeadersFrame);
/* 191 */           out.add(spdyDataFrame);
/*     */         } 
/*     */       } else {
/* 194 */         out.add(spdyDataFrame);
/*     */       } 
/*     */       
/* 197 */       valid = true;
/*     */     } 
/*     */     
/* 200 */     if (!valid) {
/* 201 */       throw new UnsupportedMessageTypeException(msg, new Class[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private SpdySynStreamFrame createSynStreamFrame(HttpMessage httpMessage) throws Exception {
/* 208 */     int streamID = SpdyHttpHeaders.getStreamId(httpMessage);
/* 209 */     int associatedToStreamId = SpdyHttpHeaders.getAssociatedToStreamId(httpMessage);
/* 210 */     byte priority = SpdyHttpHeaders.getPriority(httpMessage);
/* 211 */     String URL = SpdyHttpHeaders.getUrl(httpMessage);
/* 212 */     String scheme = SpdyHttpHeaders.getScheme(httpMessage);
/* 213 */     SpdyHttpHeaders.removeStreamId(httpMessage);
/* 214 */     SpdyHttpHeaders.removeAssociatedToStreamId(httpMessage);
/* 215 */     SpdyHttpHeaders.removePriority(httpMessage);
/* 216 */     SpdyHttpHeaders.removeUrl(httpMessage);
/* 217 */     SpdyHttpHeaders.removeScheme(httpMessage);
/*     */ 
/*     */ 
/*     */     
/* 221 */     httpMessage.headers().remove("Connection");
/* 222 */     httpMessage.headers().remove("Keep-Alive");
/* 223 */     httpMessage.headers().remove("Proxy-Connection");
/* 224 */     httpMessage.headers().remove("Transfer-Encoding");
/*     */     
/* 226 */     SpdySynStreamFrame spdySynStreamFrame = new DefaultSpdySynStreamFrame(streamID, associatedToStreamId, priority);
/*     */ 
/*     */ 
/*     */     
/* 230 */     if (httpMessage instanceof io.netty.handler.codec.http.FullHttpRequest) {
/* 231 */       HttpRequest httpRequest = (HttpRequest)httpMessage;
/* 232 */       SpdyHeaders.setMethod(this.spdyVersion, spdySynStreamFrame, httpRequest.getMethod());
/* 233 */       SpdyHeaders.setUrl(this.spdyVersion, spdySynStreamFrame, httpRequest.getUri());
/* 234 */       SpdyHeaders.setVersion(this.spdyVersion, spdySynStreamFrame, httpMessage.getProtocolVersion());
/*     */     } 
/* 236 */     if (httpMessage instanceof HttpResponse) {
/* 237 */       HttpResponse httpResponse = (HttpResponse)httpMessage;
/* 238 */       SpdyHeaders.setStatus(this.spdyVersion, spdySynStreamFrame, httpResponse.getStatus());
/* 239 */       SpdyHeaders.setUrl(this.spdyVersion, spdySynStreamFrame, URL);
/* 240 */       SpdyHeaders.setVersion(this.spdyVersion, spdySynStreamFrame, httpMessage.getProtocolVersion());
/* 241 */       spdySynStreamFrame.setUnidirectional(true);
/*     */     } 
/*     */ 
/*     */     
/* 245 */     if (this.spdyVersion >= 3) {
/* 246 */       String host = HttpHeaders.getHost(httpMessage);
/* 247 */       httpMessage.headers().remove("Host");
/* 248 */       SpdyHeaders.setHost(spdySynStreamFrame, host);
/*     */     } 
/*     */ 
/*     */     
/* 252 */     if (scheme == null) {
/* 253 */       scheme = "https";
/*     */     }
/* 255 */     SpdyHeaders.setScheme(this.spdyVersion, spdySynStreamFrame, scheme);
/*     */ 
/*     */     
/* 258 */     for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>)httpMessage.headers()) {
/* 259 */       spdySynStreamFrame.headers().add(entry.getKey(), entry.getValue());
/*     */     }
/* 261 */     this.currentStreamId = spdySynStreamFrame.streamId();
/* 262 */     spdySynStreamFrame.setLast(isLast(httpMessage));
/*     */     
/* 264 */     return spdySynStreamFrame;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private SpdySynReplyFrame createSynReplyFrame(HttpResponse httpResponse) throws Exception {
/* 270 */     int streamID = SpdyHttpHeaders.getStreamId((HttpMessage)httpResponse);
/* 271 */     SpdyHttpHeaders.removeStreamId((HttpMessage)httpResponse);
/*     */ 
/*     */ 
/*     */     
/* 275 */     httpResponse.headers().remove("Connection");
/* 276 */     httpResponse.headers().remove("Keep-Alive");
/* 277 */     httpResponse.headers().remove("Proxy-Connection");
/* 278 */     httpResponse.headers().remove("Transfer-Encoding");
/*     */     
/* 280 */     SpdySynReplyFrame spdySynReplyFrame = new DefaultSpdySynReplyFrame(streamID);
/*     */ 
/*     */     
/* 283 */     SpdyHeaders.setStatus(this.spdyVersion, spdySynReplyFrame, httpResponse.getStatus());
/* 284 */     SpdyHeaders.setVersion(this.spdyVersion, spdySynReplyFrame, httpResponse.getProtocolVersion());
/*     */ 
/*     */     
/* 287 */     for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>)httpResponse.headers()) {
/* 288 */       spdySynReplyFrame.headers().add(entry.getKey(), entry.getValue());
/*     */     }
/*     */     
/* 291 */     this.currentStreamId = streamID;
/* 292 */     spdySynReplyFrame.setLast(isLast((HttpMessage)httpResponse));
/*     */     
/* 294 */     return spdySynReplyFrame;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isLast(HttpMessage httpMessage) {
/* 304 */     if (httpMessage instanceof FullHttpMessage) {
/* 305 */       FullHttpMessage fullMessage = (FullHttpMessage)httpMessage;
/* 306 */       if (fullMessage.trailingHeaders().isEmpty() && !fullMessage.content().isReadable()) {
/* 307 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 311 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\spdy\SpdyHttpEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */