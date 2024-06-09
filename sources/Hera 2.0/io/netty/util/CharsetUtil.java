/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.InternalThreadLocalMap;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CodingErrorAction;
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
/*     */ public final class CharsetUtil
/*     */ {
/*  36 */   public static final Charset UTF_16 = Charset.forName("UTF-16");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   public static final Charset UTF_16BE = Charset.forName("UTF-16BE");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   public static final Charset UTF_16LE = Charset.forName("UTF-16LE");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   public static final Charset UTF_8 = Charset.forName("UTF-8");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   public static final Charset US_ASCII = Charset.forName("US-ASCII");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharsetEncoder getEncoder(Charset charset) {
/*  69 */     if (charset == null) {
/*  70 */       throw new NullPointerException("charset");
/*     */     }
/*     */     
/*  73 */     Map<Charset, CharsetEncoder> map = InternalThreadLocalMap.get().charsetEncoderCache();
/*  74 */     CharsetEncoder e = map.get(charset);
/*  75 */     if (e != null) {
/*  76 */       e.reset();
/*  77 */       e.onMalformedInput(CodingErrorAction.REPLACE);
/*  78 */       e.onUnmappableCharacter(CodingErrorAction.REPLACE);
/*  79 */       return e;
/*     */     } 
/*     */     
/*  82 */     e = charset.newEncoder();
/*  83 */     e.onMalformedInput(CodingErrorAction.REPLACE);
/*  84 */     e.onUnmappableCharacter(CodingErrorAction.REPLACE);
/*  85 */     map.put(charset, e);
/*  86 */     return e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharsetDecoder getDecoder(Charset charset) {
/*  94 */     if (charset == null) {
/*  95 */       throw new NullPointerException("charset");
/*     */     }
/*     */     
/*  98 */     Map<Charset, CharsetDecoder> map = InternalThreadLocalMap.get().charsetDecoderCache();
/*  99 */     CharsetDecoder d = map.get(charset);
/* 100 */     if (d != null) {
/* 101 */       d.reset();
/* 102 */       d.onMalformedInput(CodingErrorAction.REPLACE);
/* 103 */       d.onUnmappableCharacter(CodingErrorAction.REPLACE);
/* 104 */       return d;
/*     */     } 
/*     */     
/* 107 */     d = charset.newDecoder();
/* 108 */     d.onMalformedInput(CodingErrorAction.REPLACE);
/* 109 */     d.onUnmappableCharacter(CodingErrorAction.REPLACE);
/* 110 */     map.put(charset, d);
/* 111 */     return d;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\CharsetUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */