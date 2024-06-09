/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ClientCookieEncoder
/*     */ {
/*     */   public static String encode(String name, String value) {
/*  37 */     return encode(new DefaultCookie(name, value));
/*     */   }
/*     */   
/*     */   public static String encode(Cookie cookie) {
/*  41 */     if (cookie == null) {
/*  42 */       throw new NullPointerException("cookie");
/*     */     }
/*     */     
/*  45 */     StringBuilder buf = CookieEncoderUtil.stringBuilder();
/*  46 */     encode(buf, cookie);
/*  47 */     return CookieEncoderUtil.stripTrailingSeparator(buf);
/*     */   }
/*     */   
/*     */   public static String encode(Cookie... cookies) {
/*  51 */     if (cookies == null) {
/*  52 */       throw new NullPointerException("cookies");
/*     */     }
/*     */     
/*  55 */     StringBuilder buf = CookieEncoderUtil.stringBuilder();
/*  56 */     for (Cookie c : cookies) {
/*  57 */       if (c == null) {
/*     */         break;
/*     */       }
/*     */       
/*  61 */       encode(buf, c);
/*     */     } 
/*  63 */     return CookieEncoderUtil.stripTrailingSeparator(buf);
/*     */   }
/*     */   
/*     */   public static String encode(Iterable<Cookie> cookies) {
/*  67 */     if (cookies == null) {
/*  68 */       throw new NullPointerException("cookies");
/*     */     }
/*     */     
/*  71 */     StringBuilder buf = CookieEncoderUtil.stringBuilder();
/*  72 */     for (Cookie c : cookies) {
/*  73 */       if (c == null) {
/*     */         break;
/*     */       }
/*     */       
/*  77 */       encode(buf, c);
/*     */     } 
/*  79 */     return CookieEncoderUtil.stripTrailingSeparator(buf);
/*     */   }
/*     */   
/*     */   private static void encode(StringBuilder buf, Cookie c) {
/*  83 */     if (c.getVersion() >= 1) {
/*  84 */       CookieEncoderUtil.add(buf, "$Version", 1L);
/*     */     }
/*     */     
/*  87 */     CookieEncoderUtil.add(buf, c.getName(), c.getValue());
/*     */     
/*  89 */     if (c.getPath() != null) {
/*  90 */       CookieEncoderUtil.add(buf, "$Path", c.getPath());
/*     */     }
/*     */     
/*  93 */     if (c.getDomain() != null) {
/*  94 */       CookieEncoderUtil.add(buf, "$Domain", c.getDomain());
/*     */     }
/*     */     
/*  97 */     if (c.getVersion() >= 1 && 
/*  98 */       !c.getPorts().isEmpty()) {
/*  99 */       buf.append('$');
/* 100 */       buf.append("Port");
/* 101 */       buf.append('=');
/* 102 */       buf.append('"');
/* 103 */       for (Iterator<Integer> i$ = c.getPorts().iterator(); i$.hasNext(); ) { int port = ((Integer)i$.next()).intValue();
/* 104 */         buf.append(port);
/* 105 */         buf.append(','); }
/*     */       
/* 107 */       buf.setCharAt(buf.length() - 1, '"');
/* 108 */       buf.append(';');
/* 109 */       buf.append(' ');
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\ClientCookieEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */