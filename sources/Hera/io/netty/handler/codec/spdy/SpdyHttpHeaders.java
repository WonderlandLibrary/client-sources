/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMessage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SpdyHttpHeaders
/*     */ {
/*     */   public static final class Names
/*     */   {
/*     */     public static final String STREAM_ID = "X-SPDY-Stream-ID";
/*     */     public static final String ASSOCIATED_TO_STREAM_ID = "X-SPDY-Associated-To-Stream-ID";
/*     */     public static final String PRIORITY = "X-SPDY-Priority";
/*     */     public static final String URL = "X-SPDY-URL";
/*     */     public static final String SCHEME = "X-SPDY-Scheme";
/*     */   }
/*     */   
/*     */   public static void removeStreamId(HttpMessage message) {
/*  62 */     message.headers().remove("X-SPDY-Stream-ID");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getStreamId(HttpMessage message) {
/*  69 */     return HttpHeaders.getIntHeader(message, "X-SPDY-Stream-ID");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setStreamId(HttpMessage message, int streamId) {
/*  76 */     HttpHeaders.setIntHeader(message, "X-SPDY-Stream-ID", streamId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeAssociatedToStreamId(HttpMessage message) {
/*  83 */     message.headers().remove("X-SPDY-Associated-To-Stream-ID");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getAssociatedToStreamId(HttpMessage message) {
/*  93 */     return HttpHeaders.getIntHeader(message, "X-SPDY-Associated-To-Stream-ID", 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setAssociatedToStreamId(HttpMessage message, int associatedToStreamId) {
/* 100 */     HttpHeaders.setIntHeader(message, "X-SPDY-Associated-To-Stream-ID", associatedToStreamId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removePriority(HttpMessage message) {
/* 107 */     message.headers().remove("X-SPDY-Priority");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte getPriority(HttpMessage message) {
/* 117 */     return (byte)HttpHeaders.getIntHeader(message, "X-SPDY-Priority", 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setPriority(HttpMessage message, byte priority) {
/* 124 */     HttpHeaders.setIntHeader(message, "X-SPDY-Priority", priority);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeUrl(HttpMessage message) {
/* 131 */     message.headers().remove("X-SPDY-URL");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getUrl(HttpMessage message) {
/* 138 */     return message.headers().get("X-SPDY-URL");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setUrl(HttpMessage message, String url) {
/* 145 */     message.headers().set("X-SPDY-URL", url);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeScheme(HttpMessage message) {
/* 152 */     message.headers().remove("X-SPDY-Scheme");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getScheme(HttpMessage message) {
/* 159 */     return message.headers().get("X-SPDY-Scheme");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setScheme(HttpMessage message, String scheme) {
/* 166 */     message.headers().set("X-SPDY-Scheme", scheme);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\spdy\SpdyHttpHeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */