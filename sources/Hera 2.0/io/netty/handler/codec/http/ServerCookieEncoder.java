/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ public final class ServerCookieEncoder
/*     */ {
/*     */   public static String encode(String name, String value) {
/*  42 */     return encode(new DefaultCookie(name, value));
/*     */   }
/*     */   
/*     */   public static String encode(Cookie cookie) {
/*  46 */     if (cookie == null) {
/*  47 */       throw new NullPointerException("cookie");
/*     */     }
/*     */     
/*  50 */     StringBuilder buf = CookieEncoderUtil.stringBuilder();
/*     */     
/*  52 */     CookieEncoderUtil.add(buf, cookie.getName(), cookie.getValue());
/*     */     
/*  54 */     if (cookie.getMaxAge() != Long.MIN_VALUE) {
/*  55 */       if (cookie.getVersion() == 0) {
/*  56 */         CookieEncoderUtil.addUnquoted(buf, "Expires", HttpHeaderDateFormat.get().format(new Date(System.currentTimeMillis() + cookie.getMaxAge() * 1000L)));
/*     */       
/*     */       }
/*     */       else {
/*     */         
/*  61 */         CookieEncoderUtil.add(buf, "Max-Age", cookie.getMaxAge());
/*     */       } 
/*     */     }
/*     */     
/*  65 */     if (cookie.getPath() != null) {
/*  66 */       if (cookie.getVersion() > 0) {
/*  67 */         CookieEncoderUtil.add(buf, "Path", cookie.getPath());
/*     */       } else {
/*  69 */         CookieEncoderUtil.addUnquoted(buf, "Path", cookie.getPath());
/*     */       } 
/*     */     }
/*     */     
/*  73 */     if (cookie.getDomain() != null) {
/*  74 */       if (cookie.getVersion() > 0) {
/*  75 */         CookieEncoderUtil.add(buf, "Domain", cookie.getDomain());
/*     */       } else {
/*  77 */         CookieEncoderUtil.addUnquoted(buf, "Domain", cookie.getDomain());
/*     */       } 
/*     */     }
/*  80 */     if (cookie.isSecure()) {
/*  81 */       buf.append("Secure");
/*  82 */       buf.append(';');
/*  83 */       buf.append(' ');
/*     */     } 
/*  85 */     if (cookie.isHttpOnly()) {
/*  86 */       buf.append("HTTPOnly");
/*  87 */       buf.append(';');
/*  88 */       buf.append(' ');
/*     */     } 
/*  90 */     if (cookie.getVersion() >= 1) {
/*  91 */       if (cookie.getComment() != null) {
/*  92 */         CookieEncoderUtil.add(buf, "Comment", cookie.getComment());
/*     */       }
/*     */       
/*  95 */       CookieEncoderUtil.add(buf, "Version", 1L);
/*     */       
/*  97 */       if (cookie.getCommentUrl() != null) {
/*  98 */         CookieEncoderUtil.addQuoted(buf, "CommentURL", cookie.getCommentUrl());
/*     */       }
/*     */       
/* 101 */       if (!cookie.getPorts().isEmpty()) {
/* 102 */         buf.append("Port");
/* 103 */         buf.append('=');
/* 104 */         buf.append('"');
/* 105 */         for (Iterator<Integer> i$ = cookie.getPorts().iterator(); i$.hasNext(); ) { int port = ((Integer)i$.next()).intValue();
/* 106 */           buf.append(port);
/* 107 */           buf.append(','); }
/*     */         
/* 109 */         buf.setCharAt(buf.length() - 1, '"');
/* 110 */         buf.append(';');
/* 111 */         buf.append(' ');
/*     */       } 
/* 113 */       if (cookie.isDiscard()) {
/* 114 */         buf.append("Discard");
/* 115 */         buf.append(';');
/* 116 */         buf.append(' ');
/*     */       } 
/*     */     } 
/*     */     
/* 120 */     return CookieEncoderUtil.stripTrailingSeparator(buf);
/*     */   }
/*     */   
/*     */   public static List<String> encode(Cookie... cookies) {
/* 124 */     if (cookies == null) {
/* 125 */       throw new NullPointerException("cookies");
/*     */     }
/*     */     
/* 128 */     List<String> encoded = new ArrayList<String>(cookies.length);
/* 129 */     for (Cookie c : cookies) {
/* 130 */       if (c == null) {
/*     */         break;
/*     */       }
/* 133 */       encoded.add(encode(c));
/*     */     } 
/* 135 */     return encoded;
/*     */   }
/*     */   
/*     */   public static List<String> encode(Collection<Cookie> cookies) {
/* 139 */     if (cookies == null) {
/* 140 */       throw new NullPointerException("cookies");
/*     */     }
/*     */     
/* 143 */     List<String> encoded = new ArrayList<String>(cookies.size());
/* 144 */     for (Cookie c : cookies) {
/* 145 */       if (c == null) {
/*     */         break;
/*     */       }
/* 148 */       encoded.add(encode(c));
/*     */     } 
/* 150 */     return encoded;
/*     */   }
/*     */   
/*     */   public static List<String> encode(Iterable<Cookie> cookies) {
/* 154 */     if (cookies == null) {
/* 155 */       throw new NullPointerException("cookies");
/*     */     }
/*     */     
/* 158 */     List<String> encoded = new ArrayList<String>();
/* 159 */     for (Cookie c : cookies) {
/* 160 */       if (c == null) {
/*     */         break;
/*     */       }
/* 163 */       encoded.add(encode(c));
/*     */     } 
/* 165 */     return encoded;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\ServerCookieEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */