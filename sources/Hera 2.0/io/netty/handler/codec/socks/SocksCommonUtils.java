/*     */ package io.netty.handler.codec.socks;
/*     */ 
/*     */ import io.netty.util.internal.StringUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SocksCommonUtils
/*     */ {
/*  21 */   public static final SocksRequest UNKNOWN_SOCKS_REQUEST = new UnknownSocksRequest();
/*  22 */   public static final SocksResponse UNKNOWN_SOCKS_RESPONSE = new UnknownSocksResponse();
/*     */ 
/*     */   
/*     */   private static final int SECOND_ADDRESS_OCTET_SHIFT = 16;
/*     */ 
/*     */   
/*     */   private static final int FIRST_ADDRESS_OCTET_SHIFT = 24;
/*     */ 
/*     */   
/*     */   private static final int THIRD_ADDRESS_OCTET_SHIFT = 8;
/*     */   
/*     */   private static final int XOR_DEFAULT_VALUE = 255;
/*     */ 
/*     */   
/*     */   public static String intToIp(int i) {
/*  37 */     return String.valueOf(i >> 24 & 0xFF) + '.' + (i >> 16 & 0xFF) + '.' + (i >> 8 & 0xFF) + '.' + (i & 0xFF);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   private static final char[] ipv6conseqZeroFiller = new char[] { ':', ':' };
/*     */ 
/*     */   
/*     */   private static final char ipv6hextetSeparator = ':';
/*     */ 
/*     */ 
/*     */   
/*     */   public static String ipv6toCompressedForm(byte[] src) {
/*  51 */     assert src.length == 16;
/*     */ 
/*     */     
/*  54 */     int cmprHextet = -1;
/*     */     
/*  56 */     int cmprSize = 0;
/*  57 */     for (int hextet = 0; hextet < 8; ) {
/*  58 */       int curByte = hextet * 2;
/*  59 */       int size = 0;
/*     */       
/*  61 */       while (curByte < src.length && src[curByte] == 0 && src[curByte + 1] == 0) {
/*  62 */         curByte += 2;
/*  63 */         size++;
/*     */       } 
/*  65 */       if (size > cmprSize) {
/*  66 */         cmprHextet = hextet;
/*  67 */         cmprSize = size;
/*     */       } 
/*  69 */       hextet = curByte / 2 + 1;
/*     */     } 
/*  71 */     if (cmprHextet == -1 || cmprSize < 2)
/*     */     {
/*  73 */       return ipv6toStr(src);
/*     */     }
/*  75 */     StringBuilder sb = new StringBuilder(39);
/*  76 */     ipv6toStr(sb, src, 0, cmprHextet);
/*  77 */     sb.append(ipv6conseqZeroFiller);
/*  78 */     ipv6toStr(sb, src, cmprHextet + cmprSize, 8);
/*  79 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String ipv6toStr(byte[] src) {
/*  86 */     assert src.length == 16;
/*  87 */     StringBuilder sb = new StringBuilder(39);
/*  88 */     ipv6toStr(sb, src, 0, 8);
/*  89 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void ipv6toStr(StringBuilder sb, byte[] src, int fromHextet, int toHextet) {
/*  94 */     toHextet--; int i;
/*  95 */     for (i = fromHextet; i < toHextet; i++) {
/*  96 */       appendHextet(sb, src, i);
/*  97 */       sb.append(':');
/*     */     } 
/*     */     
/* 100 */     appendHextet(sb, src, i);
/*     */   }
/*     */   
/*     */   private static void appendHextet(StringBuilder sb, byte[] src, int i) {
/* 104 */     StringUtil.toHexString(sb, src, i << 1, 2);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\socks\SocksCommonUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */