/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import java.util.HashMap;
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
/*     */ public class HttpMethod
/*     */   implements Comparable<HttpMethod>
/*     */ {
/*  37 */   public static final HttpMethod OPTIONS = new HttpMethod("OPTIONS", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   public static final HttpMethod GET = new HttpMethod("GET", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   public static final HttpMethod HEAD = new HttpMethod("HEAD", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   public static final HttpMethod POST = new HttpMethod("POST", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   public static final HttpMethod PUT = new HttpMethod("PUT", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static final HttpMethod PATCH = new HttpMethod("PATCH", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public static final HttpMethod DELETE = new HttpMethod("DELETE", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public static final HttpMethod TRACE = new HttpMethod("TRACE", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   public static final HttpMethod CONNECT = new HttpMethod("CONNECT", true);
/*     */   
/*  89 */   private static final Map<String, HttpMethod> methodMap = new HashMap<String, HttpMethod>();
/*     */   private final String name;
/*     */   
/*     */   static {
/*  93 */     methodMap.put(OPTIONS.toString(), OPTIONS);
/*  94 */     methodMap.put(GET.toString(), GET);
/*  95 */     methodMap.put(HEAD.toString(), HEAD);
/*  96 */     methodMap.put(POST.toString(), POST);
/*  97 */     methodMap.put(PUT.toString(), PUT);
/*  98 */     methodMap.put(PATCH.toString(), PATCH);
/*  99 */     methodMap.put(DELETE.toString(), DELETE);
/* 100 */     methodMap.put(TRACE.toString(), TRACE);
/* 101 */     methodMap.put(CONNECT.toString(), CONNECT);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final byte[] bytes;
/*     */ 
/*     */   
/*     */   public static HttpMethod valueOf(String name) {
/* 110 */     if (name == null) {
/* 111 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/* 114 */     name = name.trim();
/* 115 */     if (name.isEmpty()) {
/* 116 */       throw new IllegalArgumentException("empty name");
/*     */     }
/*     */     
/* 119 */     HttpMethod result = methodMap.get(name);
/* 120 */     if (result != null) {
/* 121 */       return result;
/*     */     }
/* 123 */     return new HttpMethod(name);
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
/*     */   public HttpMethod(String name) {
/* 138 */     this(name, false);
/*     */   }
/*     */   
/*     */   private HttpMethod(String name, boolean bytes) {
/* 142 */     if (name == null) {
/* 143 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/* 146 */     name = name.trim();
/* 147 */     if (name.isEmpty()) {
/* 148 */       throw new IllegalArgumentException("empty name");
/*     */     }
/*     */     
/* 151 */     for (int i = 0; i < name.length(); i++) {
/* 152 */       if (Character.isISOControl(name.charAt(i)) || Character.isWhitespace(name.charAt(i)))
/*     */       {
/* 154 */         throw new IllegalArgumentException("invalid character in name");
/*     */       }
/*     */     } 
/*     */     
/* 158 */     this.name = name;
/* 159 */     if (bytes) {
/* 160 */       this.bytes = name.getBytes(CharsetUtil.US_ASCII);
/*     */     } else {
/* 162 */       this.bytes = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String name() {
/* 170 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 175 */     return name().hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 180 */     if (!(o instanceof HttpMethod)) {
/* 181 */       return false;
/*     */     }
/*     */     
/* 184 */     HttpMethod that = (HttpMethod)o;
/* 185 */     return name().equals(that.name());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 190 */     return name();
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(HttpMethod o) {
/* 195 */     return name().compareTo(o.name());
/*     */   }
/*     */   
/*     */   void encode(ByteBuf buf) {
/* 199 */     if (this.bytes == null) {
/* 200 */       HttpHeaders.encodeAscii0(this.name, buf);
/*     */     } else {
/* 202 */       buf.writeBytes(this.bytes);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\HttpMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */