/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import java.net.URI;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebSocketClientHandshaker00
/*     */   extends WebSocketClientHandshaker
/*     */ {
/*     */   private ByteBuf expectedChallengeResponseBytes;
/*     */   
/*     */   public WebSocketClientHandshaker00(URI webSocketURL, WebSocketVersion version, String subprotocol, HttpHeaders customHeaders, int maxFramePayloadLength) {
/*  64 */     super(webSocketURL, version, subprotocol, customHeaders, maxFramePayloadLength);
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
/*  88 */     int spaces1 = WebSocketUtil.randomNumber(1, 12);
/*  89 */     int spaces2 = WebSocketUtil.randomNumber(1, 12);
/*     */     
/*  91 */     int max1 = Integer.MAX_VALUE / spaces1;
/*  92 */     int max2 = Integer.MAX_VALUE / spaces2;
/*     */     
/*  94 */     int number1 = WebSocketUtil.randomNumber(0, max1);
/*  95 */     int number2 = WebSocketUtil.randomNumber(0, max2);
/*     */     
/*  97 */     int product1 = number1 * spaces1;
/*  98 */     int product2 = number2 * spaces2;
/*     */     
/* 100 */     String key1 = Integer.toString(product1);
/* 101 */     String key2 = Integer.toString(product2);
/*     */     
/* 103 */     key1 = insertRandomCharacters(key1);
/* 104 */     key2 = insertRandomCharacters(key2);
/*     */     
/* 106 */     key1 = insertSpaces(key1, spaces1);
/* 107 */     key2 = insertSpaces(key2, spaces2);
/*     */     
/* 109 */     byte[] key3 = WebSocketUtil.randomBytes(8);
/*     */     
/* 111 */     ByteBuffer buffer = ByteBuffer.allocate(4);
/* 112 */     buffer.putInt(number1);
/* 113 */     byte[] number1Array = buffer.array();
/* 114 */     buffer = ByteBuffer.allocate(4);
/* 115 */     buffer.putInt(number2);
/* 116 */     byte[] number2Array = buffer.array();
/*     */     
/* 118 */     byte[] challenge = new byte[16];
/* 119 */     System.arraycopy(number1Array, 0, challenge, 0, 4);
/* 120 */     System.arraycopy(number2Array, 0, challenge, 4, 4);
/* 121 */     System.arraycopy(key3, 0, challenge, 8, 8);
/* 122 */     this.expectedChallengeResponseBytes = Unpooled.wrappedBuffer(WebSocketUtil.md5(challenge));
/*     */ 
/*     */     
/* 125 */     URI wsURL = uri();
/* 126 */     String path = wsURL.getPath();
/* 127 */     if (wsURL.getQuery() != null && !wsURL.getQuery().isEmpty()) {
/* 128 */       path = wsURL.getPath() + '?' + wsURL.getQuery();
/*     */     }
/*     */     
/* 131 */     if (path == null || path.isEmpty()) {
/* 132 */       path = "/";
/*     */     }
/*     */ 
/*     */     
/* 136 */     DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, path);
/* 137 */     HttpHeaders headers = defaultFullHttpRequest.headers();
/* 138 */     headers.add("Upgrade", "WebSocket").add("Connection", "Upgrade").add("Host", wsURL.getHost());
/*     */ 
/*     */ 
/*     */     
/* 142 */     int wsPort = wsURL.getPort();
/* 143 */     String originValue = "http://" + wsURL.getHost();
/* 144 */     if (wsPort != 80 && wsPort != 443)
/*     */     {
/*     */       
/* 147 */       originValue = originValue + ':' + wsPort;
/*     */     }
/*     */     
/* 150 */     headers.add("Origin", originValue).add("Sec-WebSocket-Key1", key1).add("Sec-WebSocket-Key2", key2);
/*     */ 
/*     */ 
/*     */     
/* 154 */     String expectedSubprotocol = expectedSubprotocol();
/* 155 */     if (expectedSubprotocol != null && !expectedSubprotocol.isEmpty()) {
/* 156 */       headers.add("Sec-WebSocket-Protocol", expectedSubprotocol);
/*     */     }
/*     */     
/* 159 */     if (this.customHeaders != null) {
/* 160 */       headers.add(this.customHeaders);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 165 */     headers.set("Content-Length", Integer.valueOf(key3.length));
/* 166 */     defaultFullHttpRequest.content().writeBytes(key3);
/* 167 */     return (FullHttpRequest)defaultFullHttpRequest;
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
/*     */   
/*     */   protected void verify(FullHttpResponse response) {
/* 192 */     HttpResponseStatus status = new HttpResponseStatus(101, "WebSocket Protocol Handshake");
/*     */     
/* 194 */     if (!response.getStatus().equals(status)) {
/* 195 */       throw new WebSocketHandshakeException("Invalid handshake response getStatus: " + response.getStatus());
/*     */     }
/*     */     
/* 198 */     HttpHeaders headers = response.headers();
/*     */     
/* 200 */     String upgrade = headers.get("Upgrade");
/* 201 */     if (!"WebSocket".equalsIgnoreCase(upgrade)) {
/* 202 */       throw new WebSocketHandshakeException("Invalid handshake response upgrade: " + upgrade);
/*     */     }
/*     */ 
/*     */     
/* 206 */     String connection = headers.get("Connection");
/* 207 */     if (!"Upgrade".equalsIgnoreCase(connection)) {
/* 208 */       throw new WebSocketHandshakeException("Invalid handshake response connection: " + connection);
/*     */     }
/*     */ 
/*     */     
/* 212 */     ByteBuf challenge = response.content();
/* 213 */     if (!challenge.equals(this.expectedChallengeResponseBytes)) {
/* 214 */       throw new WebSocketHandshakeException("Invalid challenge");
/*     */     }
/*     */   }
/*     */   
/*     */   private static String insertRandomCharacters(String key) {
/* 219 */     int count = WebSocketUtil.randomNumber(1, 12);
/*     */     
/* 221 */     char[] randomChars = new char[count];
/* 222 */     int randCount = 0;
/* 223 */     while (randCount < count) {
/* 224 */       int rand = (int)(Math.random() * 126.0D + 33.0D);
/* 225 */       if ((33 < rand && rand < 47) || (58 < rand && rand < 126)) {
/* 226 */         randomChars[randCount] = (char)rand;
/* 227 */         randCount++;
/*     */       } 
/*     */     } 
/*     */     
/* 231 */     for (int i = 0; i < count; i++) {
/* 232 */       int split = WebSocketUtil.randomNumber(0, key.length());
/* 233 */       String part1 = key.substring(0, split);
/* 234 */       String part2 = key.substring(split);
/* 235 */       key = part1 + randomChars[i] + part2;
/*     */     } 
/*     */     
/* 238 */     return key;
/*     */   }
/*     */   
/*     */   private static String insertSpaces(String key, int spaces) {
/* 242 */     for (int i = 0; i < spaces; i++) {
/* 243 */       int split = WebSocketUtil.randomNumber(1, key.length() - 1);
/* 244 */       String part1 = key.substring(0, split);
/* 245 */       String part2 = key.substring(split);
/* 246 */       key = part1 + ' ' + part2;
/*     */     } 
/*     */     
/* 249 */     return key;
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder() {
/* 254 */     return new WebSocket00FrameDecoder(maxFramePayloadLength());
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder() {
/* 259 */     return new WebSocket00FrameEncoder();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\websocketx\WebSocketClientHandshaker00.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */