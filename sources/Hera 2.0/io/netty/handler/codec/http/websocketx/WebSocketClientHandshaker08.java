/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.handler.codec.http.DefaultFullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.URI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebSocketClientHandshaker08
/*     */   extends WebSocketClientHandshaker
/*     */ {
/*  42 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocketClientHandshaker08.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String MAGIC_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String expectedChallengeResponseString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean allowExtensions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketClientHandshaker08(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength) {
/*  69 */     super(webSocketURL, version, subprotocol, customHeaders, maxFramePayloadLength);
/*  70 */     this.allowExtensions = allowExtensions;
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
/*     */   protected FullHttpRequest newHandshakeRequest() {
/*  94 */     URI wsURL = uri();
/*  95 */     String path = wsURL.getPath();
/*  96 */     if (wsURL.getQuery() != null && !wsURL.getQuery().isEmpty()) {
/*  97 */       path = wsURL.getPath() + '?' + wsURL.getQuery();
/*     */     }
/*     */     
/* 100 */     if (path == null || path.isEmpty()) {
/* 101 */       path = "/";
/*     */     }
/*     */ 
/*     */     
/* 105 */     byte[] nonce = WebSocketUtil.randomBytes(16);
/* 106 */     String key = WebSocketUtil.base64(nonce);
/*     */     
/* 108 */     String acceptSeed = key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/* 109 */     byte[] sha1 = WebSocketUtil.sha1(acceptSeed.getBytes(CharsetUtil.US_ASCII));
/* 110 */     this.expectedChallengeResponseString = WebSocketUtil.base64(sha1);
/*     */     
/* 112 */     if (logger.isDebugEnabled()) {
/* 113 */       logger.debug("WebSocket version 08 client handshake key: {}, expected response: {}", key, this.expectedChallengeResponseString);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, path);
/* 120 */     HttpHeaders headers = defaultFullHttpRequest.headers();
/*     */     
/* 122 */     headers.add("Upgrade", "WebSocket".toLowerCase()).add("Connection", "Upgrade").add("Sec-WebSocket-Key", key).add("Host", wsURL.getHost());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     int wsPort = wsURL.getPort();
/* 128 */     String originValue = "http://" + wsURL.getHost();
/* 129 */     if (wsPort != 80 && wsPort != 443)
/*     */     {
/*     */       
/* 132 */       originValue = originValue + ':' + wsPort;
/*     */     }
/* 134 */     headers.add("Sec-WebSocket-Origin", originValue);
/*     */     
/* 136 */     String expectedSubprotocol = expectedSubprotocol();
/* 137 */     if (expectedSubprotocol != null && !expectedSubprotocol.isEmpty()) {
/* 138 */       headers.add("Sec-WebSocket-Protocol", expectedSubprotocol);
/*     */     }
/*     */     
/* 141 */     headers.add("Sec-WebSocket-Version", "8");
/*     */     
/* 143 */     if (this.customHeaders != null) {
/* 144 */       headers.add(this.customHeaders);
/*     */     }
/* 146 */     return (FullHttpRequest)defaultFullHttpRequest;
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
/*     */   protected void verify(FullHttpResponse response) {
/* 168 */     HttpResponseStatus status = HttpResponseStatus.SWITCHING_PROTOCOLS;
/* 169 */     HttpHeaders headers = response.headers();
/*     */     
/* 171 */     if (!response.getStatus().equals(status)) {
/* 172 */       throw new WebSocketHandshakeException("Invalid handshake response getStatus: " + response.getStatus());
/*     */     }
/*     */     
/* 175 */     String upgrade = headers.get("Upgrade");
/* 176 */     if (!"WebSocket".equalsIgnoreCase(upgrade)) {
/* 177 */       throw new WebSocketHandshakeException("Invalid handshake response upgrade: " + upgrade);
/*     */     }
/*     */     
/* 180 */     String connection = headers.get("Connection");
/* 181 */     if (!"Upgrade".equalsIgnoreCase(connection)) {
/* 182 */       throw new WebSocketHandshakeException("Invalid handshake response connection: " + connection);
/*     */     }
/*     */     
/* 185 */     String accept = headers.get("Sec-WebSocket-Accept");
/* 186 */     if (accept == null || !accept.equals(this.expectedChallengeResponseString)) {
/* 187 */       throw new WebSocketHandshakeException(String.format("Invalid challenge. Actual: %s. Expected: %s", new Object[] { accept, this.expectedChallengeResponseString }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder() {
/* 194 */     return new WebSocket08FrameDecoder(false, this.allowExtensions, maxFramePayloadLength());
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder() {
/* 199 */     return new WebSocket08FrameEncoder(true);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\websocketx\WebSocketClientHandshaker08.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */