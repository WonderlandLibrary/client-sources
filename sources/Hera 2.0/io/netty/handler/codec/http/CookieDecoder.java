/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CookieDecoder
/*     */ {
/*     */   private static final char COMMA = ',';
/*     */   
/*     */   public static Set<Cookie> decode(String header) {
/*     */     int i;
/*  50 */     List<String> names = new ArrayList<String>(8);
/*  51 */     List<String> values = new ArrayList<String>(8);
/*  52 */     extractKeyValuePairs(header, names, values);
/*     */     
/*  54 */     if (names.isEmpty()) {
/*  55 */       return Collections.emptySet();
/*     */     }
/*     */ 
/*     */     
/*  59 */     int version = 0;
/*     */ 
/*     */ 
/*     */     
/*  63 */     if (((String)names.get(0)).equalsIgnoreCase("Version")) {
/*     */       try {
/*  65 */         version = Integer.parseInt(values.get(0));
/*  66 */       } catch (NumberFormatException e) {}
/*     */ 
/*     */       
/*  69 */       i = 1;
/*     */     } else {
/*  71 */       i = 0;
/*     */     } 
/*     */     
/*  74 */     if (names.size() <= i)
/*     */     {
/*  76 */       return Collections.emptySet();
/*     */     }
/*     */     
/*  79 */     Set<Cookie> cookies = new TreeSet<Cookie>();
/*  80 */     for (; i < names.size(); i++) {
/*  81 */       String name = names.get(i);
/*  82 */       String value = values.get(i);
/*  83 */       if (value == null) {
/*  84 */         value = "";
/*     */       }
/*     */       
/*  87 */       Cookie c = new DefaultCookie(name, value);
/*     */       
/*  89 */       boolean discard = false;
/*  90 */       boolean secure = false;
/*  91 */       boolean httpOnly = false;
/*  92 */       String comment = null;
/*  93 */       String commentURL = null;
/*  94 */       String domain = null;
/*  95 */       String path = null;
/*  96 */       long maxAge = Long.MIN_VALUE;
/*  97 */       List<Integer> ports = new ArrayList<Integer>(2);
/*     */       
/*  99 */       for (int j = i + 1; j < names.size(); j++, i++) {
/* 100 */         name = names.get(j);
/* 101 */         value = values.get(j);
/*     */         
/* 103 */         if ("Discard".equalsIgnoreCase(name)) {
/* 104 */           discard = true;
/* 105 */         } else if ("Secure".equalsIgnoreCase(name)) {
/* 106 */           secure = true;
/* 107 */         } else if ("HTTPOnly".equalsIgnoreCase(name)) {
/* 108 */           httpOnly = true;
/* 109 */         } else if ("Comment".equalsIgnoreCase(name)) {
/* 110 */           comment = value;
/* 111 */         } else if ("CommentURL".equalsIgnoreCase(name)) {
/* 112 */           commentURL = value;
/* 113 */         } else if ("Domain".equalsIgnoreCase(name)) {
/* 114 */           domain = value;
/* 115 */         } else if ("Path".equalsIgnoreCase(name)) {
/* 116 */           path = value;
/* 117 */         } else if ("Expires".equalsIgnoreCase(name)) {
/*     */           try {
/* 119 */             long maxAgeMillis = HttpHeaderDateFormat.get().parse(value).getTime() - System.currentTimeMillis();
/*     */ 
/*     */ 
/*     */             
/* 123 */             maxAge = maxAgeMillis / 1000L + ((maxAgeMillis % 1000L != 0L) ? 1L : 0L);
/* 124 */           } catch (ParseException e) {}
/*     */         
/*     */         }
/* 127 */         else if ("Max-Age".equalsIgnoreCase(name)) {
/* 128 */           maxAge = Integer.parseInt(value);
/* 129 */         } else if ("Version".equalsIgnoreCase(name)) {
/* 130 */           version = Integer.parseInt(value);
/* 131 */         } else if ("Port".equalsIgnoreCase(name)) {
/* 132 */           String[] portList = StringUtil.split(value, ',');
/* 133 */           for (String s1 : portList) {
/*     */             try {
/* 135 */               ports.add(Integer.valueOf(s1));
/* 136 */             } catch (NumberFormatException e) {}
/*     */           } 
/*     */         } else {
/*     */           break;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 145 */       c.setVersion(version);
/* 146 */       c.setMaxAge(maxAge);
/* 147 */       c.setPath(path);
/* 148 */       c.setDomain(domain);
/* 149 */       c.setSecure(secure);
/* 150 */       c.setHttpOnly(httpOnly);
/* 151 */       if (version > 0) {
/* 152 */         c.setComment(comment);
/*     */       }
/* 154 */       if (version > 1) {
/* 155 */         c.setCommentUrl(commentURL);
/* 156 */         c.setPorts(ports);
/* 157 */         c.setDiscard(discard);
/*     */       } 
/*     */       
/* 160 */       cookies.add(c);
/*     */     } 
/*     */     
/* 163 */     return cookies;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void extractKeyValuePairs(String header, List<String> names, List<String> values) {
/* 169 */     int headerLen = header.length();
/* 170 */     int i = 0;
/*     */ 
/*     */ 
/*     */     
/* 174 */     label63: while (i != headerLen) {
/*     */ 
/*     */       
/* 177 */       switch (header.charAt(i)) { case '\t': case '\n': case '\013': case '\f': case '\r': case ' ':
/*     */         case ',':
/*     */         case ';':
/* 180 */           i++;
/*     */           continue; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 188 */       label58: while (i != headerLen) {
/*     */ 
/*     */         
/* 191 */         if (header.charAt(i) == '$') {
/* 192 */           i++;
/*     */ 
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 201 */         if (i == headerLen) {
/* 202 */           String name = null;
/* 203 */           String value = null; continue label63;
/*     */         } 
/* 205 */         int newNameStart = i;
/*     */         break label58;
/*     */       } 
/*     */       break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\CookieDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */