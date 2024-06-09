/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpVersion
/*     */   implements Comparable<HttpVersion>
/*     */ {
/*  31 */   private static final Pattern VERSION_PATTERN = Pattern.compile("(\\S+)/(\\d+)\\.(\\d+)");
/*     */ 
/*     */   
/*     */   private static final String HTTP_1_0_STRING = "HTTP/1.0";
/*     */ 
/*     */   
/*     */   private static final String HTTP_1_1_STRING = "HTTP/1.1";
/*     */ 
/*     */   
/*  40 */   public static final HttpVersion HTTP_1_0 = new HttpVersion("HTTP", 1, 0, false, true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   public static final HttpVersion HTTP_1_1 = new HttpVersion("HTTP", 1, 1, true, true);
/*     */   
/*     */   private final String protocolName;
/*     */   
/*     */   private final int majorVersion;
/*     */   private final int minorVersion;
/*     */   private final String text;
/*     */   private final boolean keepAliveDefault;
/*     */   private final byte[] bytes;
/*     */   
/*     */   public static HttpVersion valueOf(String text) {
/*  56 */     if (text == null) {
/*  57 */       throw new NullPointerException("text");
/*     */     }
/*     */     
/*  60 */     text = text.trim();
/*     */     
/*  62 */     if (text.isEmpty()) {
/*  63 */       throw new IllegalArgumentException("text is empty");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     HttpVersion version = version0(text);
/*  78 */     if (version == null) {
/*  79 */       text = text.toUpperCase();
/*     */       
/*  81 */       version = version0(text);
/*  82 */       if (version == null)
/*     */       {
/*  84 */         version = new HttpVersion(text, true);
/*     */       }
/*     */     } 
/*  87 */     return version;
/*     */   }
/*     */   
/*     */   private static HttpVersion version0(String text) {
/*  91 */     if ("HTTP/1.1".equals(text)) {
/*  92 */       return HTTP_1_1;
/*     */     }
/*  94 */     if ("HTTP/1.0".equals(text)) {
/*  95 */       return HTTP_1_0;
/*     */     }
/*  97 */     return null;
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
/*     */   public HttpVersion(String text, boolean keepAliveDefault) {
/* 119 */     if (text == null) {
/* 120 */       throw new NullPointerException("text");
/*     */     }
/*     */     
/* 123 */     text = text.trim().toUpperCase();
/* 124 */     if (text.isEmpty()) {
/* 125 */       throw new IllegalArgumentException("empty text");
/*     */     }
/*     */     
/* 128 */     Matcher m = VERSION_PATTERN.matcher(text);
/* 129 */     if (!m.matches()) {
/* 130 */       throw new IllegalArgumentException("invalid version format: " + text);
/*     */     }
/*     */     
/* 133 */     this.protocolName = m.group(1);
/* 134 */     this.majorVersion = Integer.parseInt(m.group(2));
/* 135 */     this.minorVersion = Integer.parseInt(m.group(3));
/* 136 */     this.text = this.protocolName + '/' + this.majorVersion + '.' + this.minorVersion;
/* 137 */     this.keepAliveDefault = keepAliveDefault;
/* 138 */     this.bytes = null;
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
/*     */   public HttpVersion(String protocolName, int majorVersion, int minorVersion, boolean keepAliveDefault) {
/* 155 */     this(protocolName, majorVersion, minorVersion, keepAliveDefault, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private HttpVersion(String protocolName, int majorVersion, int minorVersion, boolean keepAliveDefault, boolean bytes) {
/* 161 */     if (protocolName == null) {
/* 162 */       throw new NullPointerException("protocolName");
/*     */     }
/*     */     
/* 165 */     protocolName = protocolName.trim().toUpperCase();
/* 166 */     if (protocolName.isEmpty()) {
/* 167 */       throw new IllegalArgumentException("empty protocolName");
/*     */     }
/*     */     
/* 170 */     for (int i = 0; i < protocolName.length(); i++) {
/* 171 */       if (Character.isISOControl(protocolName.charAt(i)) || Character.isWhitespace(protocolName.charAt(i)))
/*     */       {
/* 173 */         throw new IllegalArgumentException("invalid character in protocolName");
/*     */       }
/*     */     } 
/*     */     
/* 177 */     if (majorVersion < 0) {
/* 178 */       throw new IllegalArgumentException("negative majorVersion");
/*     */     }
/* 180 */     if (minorVersion < 0) {
/* 181 */       throw new IllegalArgumentException("negative minorVersion");
/*     */     }
/*     */     
/* 184 */     this.protocolName = protocolName;
/* 185 */     this.majorVersion = majorVersion;
/* 186 */     this.minorVersion = minorVersion;
/* 187 */     this.text = protocolName + '/' + majorVersion + '.' + minorVersion;
/* 188 */     this.keepAliveDefault = keepAliveDefault;
/*     */     
/* 190 */     if (bytes) {
/* 191 */       this.bytes = this.text.getBytes(CharsetUtil.US_ASCII);
/*     */     } else {
/* 193 */       this.bytes = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String protocolName() {
/* 201 */     return this.protocolName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int majorVersion() {
/* 208 */     return this.majorVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int minorVersion() {
/* 215 */     return this.minorVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String text() {
/* 222 */     return this.text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isKeepAliveDefault() {
/* 230 */     return this.keepAliveDefault;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 238 */     return text();
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 243 */     return (protocolName().hashCode() * 31 + majorVersion()) * 31 + minorVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 249 */     if (!(o instanceof HttpVersion)) {
/* 250 */       return false;
/*     */     }
/*     */     
/* 253 */     HttpVersion that = (HttpVersion)o;
/* 254 */     return (minorVersion() == that.minorVersion() && majorVersion() == that.majorVersion() && protocolName().equals(that.protocolName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(HttpVersion o) {
/* 261 */     int v = protocolName().compareTo(o.protocolName());
/* 262 */     if (v != 0) {
/* 263 */       return v;
/*     */     }
/*     */     
/* 266 */     v = majorVersion() - o.majorVersion();
/* 267 */     if (v != 0) {
/* 268 */       return v;
/*     */     }
/*     */     
/* 271 */     return minorVersion() - o.minorVersion();
/*     */   }
/*     */   
/*     */   void encode(ByteBuf buf) {
/* 275 */     if (this.bytes == null) {
/* 276 */       HttpHeaders.encodeAscii0(this.text, buf);
/*     */     } else {
/* 278 */       buf.writeBytes(this.bytes);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\HttpVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */