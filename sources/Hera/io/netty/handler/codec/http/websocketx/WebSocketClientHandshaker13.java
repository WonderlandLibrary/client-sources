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
/*     */ public class WebSocketClientHandshaker13
/*     */   extends WebSocketClientHandshaker
/*     */ {
/*  42 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocketClientHandshaker13.class);
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
/*     */   public WebSocketClientHandshaker13(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength) {
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
/* 113 */       logger.debug("WebSocket version 13 client handshake key: {}, expected response: {}", key, this.expectedChallengeResponseString);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     int wsPort = wsURL.getPort();
/*     */ 
/*     */     
/* 122 */     if (wsPort == -1) {
/* 123 */       if ("wss".equals(wsURL.getScheme())) {
/* 124 */         wsPort = 443;
/*     */       } else {
/* 126 */         wsPort = 80;
/*     */       } 
/*     */     }
/*     */     
/* 130 */     DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, path);
/* 131 */     HttpHeaders headers = defaultFullHttpRequest.headers();
/*     */     
/* 133 */     headers.add("Upgrade", "WebSocket".toLowerCase()).add("Connection", "Upgrade").add("Sec-WebSocket-Key", key).add("Host", wsURL.getHost() + ':' + wsPort);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     String originValue = "http://" + wsURL.getHost();
/* 139 */     if (wsPort != 80 && wsPort != 443)
/*     */     {
/*     */       
/* 142 */       originValue = originValue + ':' + wsPort;
/*     */     }
/* 144 */     headers.add("Sec-WebSocket-Origin", originValue);
/*     */     
/* 146 */     String expectedSubprotocol = expectedSubprotocol();
/* 147 */     if (expectedSubprotocol != null && !expectedSubprotocol.isEmpty()) {
/* 148 */       headers.add("Sec-WebSocket-Protocol", expectedSubprotocol);
/*     */     }
/*     */     
/* 151 */     headers.add("Sec-WebSocket-Version", "13");
/*     */     
/* 153 */     if (this.customHeaders != null) {
/* 154 */       headers.add(this.customHeaders);
/*     */     }
/* 156 */     return (FullHttpRequest)defaultFullHttpRequest;
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
/* 178 */     HttpResponseStatus status = HttpResponseStatus.SWITCHING_PROTOCOLS;
/* 179 */     HttpHeaders headers = response.headers();
/*     */     
/* 181 */     if (!response.getStatus().equals(status)) {
/* 182 */       throw new WebSocketHandshakeException("Invalid handshake response getStatus: " + response.getStatus());
/*     */     }
/*     */     
/* 185 */     String upgrade = headers.get("Upgrade");
/* 186 */     if (!"WebSocket".equalsIgnoreCase(upgrade)) {
/* 187 */       throw new WebSocketHandshakeException("Invalid handshake response upgrade: " + upgrade);
/*     */     }
/*     */     
/* 190 */     String connection = headers.get("Connection");
/* 191 */     if (!"Upgrade".equalsIgnoreCase(connection)) {
/* 192 */       throw new WebSocketHandshakeException("Invalid handshake response connection: " + connection);
/*     */     }
/*     */     
/* 195 */     String accept = headers.get("Sec-WebSocket-Accept");
/* 196 */     if (accept == null || !accept.equals(this.expectedChallengeResponseString)) {
/* 197 */       throw new WebSocketHandshakeException(String.format("Invalid challenge. Actual: %s. Expected: %s", new Object[] { accept, this.expectedChallengeResponseString }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder() {
/* 204 */     return new WebSocket13FrameDecoder(false, this.allowExtensions, maxFramePayloadLength());
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder() {
/* 209 */     return new WebSocket13FrameEncoder(true);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\websocketx\WebSocketClientHandshaker13.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */