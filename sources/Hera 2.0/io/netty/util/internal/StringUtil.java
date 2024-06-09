/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Formatter;
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
/*     */ public final class StringUtil
/*     */ {
/*     */   public static final String NEWLINE;
/*     */   private static final String[] BYTE2HEX_PAD;
/*     */   private static final String[] BYTE2HEX_NOPAD;
/*     */   private static final String EMPTY_STRING = "";
/*     */   
/*     */   static {
/*     */     String str;
/*     */   }
/*     */   
/*     */   static {
/*  30 */     BYTE2HEX_PAD = new String[256];
/*  31 */     BYTE2HEX_NOPAD = new String[256];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  39 */       str = (new Formatter()).format("%n", new Object[0]).toString();
/*  40 */     } catch (Exception e) {
/*     */       
/*  42 */       str = "\n";
/*     */     } 
/*     */     
/*  45 */     NEWLINE = str;
/*     */     
/*     */     int i;
/*     */     
/*  49 */     for (i = 0; i < 10; i++) {
/*  50 */       StringBuilder buf = new StringBuilder(2);
/*  51 */       buf.append('0');
/*  52 */       buf.append(i);
/*  53 */       BYTE2HEX_PAD[i] = buf.toString();
/*  54 */       BYTE2HEX_NOPAD[i] = String.valueOf(i);
/*     */     } 
/*  56 */     for (; i < 16; i++) {
/*  57 */       StringBuilder buf = new StringBuilder(2);
/*  58 */       char c = (char)(97 + i - 10);
/*  59 */       buf.append('0');
/*  60 */       buf.append(c);
/*  61 */       BYTE2HEX_PAD[i] = buf.toString();
/*  62 */       BYTE2HEX_NOPAD[i] = String.valueOf(c);
/*     */     } 
/*  64 */     for (; i < BYTE2HEX_PAD.length; i++) {
/*  65 */       StringBuilder buf = new StringBuilder(2);
/*  66 */       buf.append(Integer.toHexString(i));
/*  67 */       String str1 = buf.toString();
/*  68 */       BYTE2HEX_PAD[i] = str1;
/*  69 */       BYTE2HEX_NOPAD[i] = str1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] split(String value, char delim) {
/*  78 */     int end = value.length();
/*  79 */     List<String> res = new ArrayList<String>();
/*     */     
/*  81 */     int start = 0; int i;
/*  82 */     for (i = 0; i < end; i++) {
/*  83 */       if (value.charAt(i) == delim) {
/*  84 */         if (start == i) {
/*  85 */           res.add("");
/*     */         } else {
/*  87 */           res.add(value.substring(start, i));
/*     */         } 
/*  89 */         start = i + 1;
/*     */       } 
/*     */     } 
/*     */     
/*  93 */     if (start == 0) {
/*  94 */       res.add(value);
/*     */     }
/*  96 */     else if (start != end) {
/*     */       
/*  98 */       res.add(value.substring(start, end));
/*     */     } else {
/*     */       
/* 101 */       for (i = res.size() - 1; i >= 0 && (
/* 102 */         (String)res.get(i)).isEmpty(); i--) {
/* 103 */         res.remove(i);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     return res.<String>toArray(new String[res.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String byteToHexStringPadded(int value) {
/* 118 */     return BYTE2HEX_PAD[value & 0xFF];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Appendable> T byteToHexStringPadded(T buf, int value) {
/*     */     try {
/* 126 */       buf.append(byteToHexStringPadded(value));
/* 127 */     } catch (IOException e) {
/* 128 */       PlatformDependent.throwException(e);
/*     */     } 
/* 130 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toHexStringPadded(byte[] src) {
/* 137 */     return toHexStringPadded(src, 0, src.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toHexStringPadded(byte[] src, int offset, int length) {
/* 144 */     return ((StringBuilder)toHexStringPadded(new StringBuilder(length << 1), src, offset, length)).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Appendable> T toHexStringPadded(T dst, byte[] src) {
/* 151 */     return toHexStringPadded(dst, src, 0, src.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Appendable> T toHexStringPadded(T dst, byte[] src, int offset, int length) {
/* 158 */     int end = offset + length;
/* 159 */     for (int i = offset; i < end; i++) {
/* 160 */       byteToHexStringPadded(dst, src[i]);
/*     */     }
/* 162 */     return dst;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String byteToHexString(int value) {
/* 169 */     return BYTE2HEX_NOPAD[value & 0xFF];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Appendable> T byteToHexString(T buf, int value) {
/*     */     try {
/* 177 */       buf.append(byteToHexString(value));
/* 178 */     } catch (IOException e) {
/* 179 */       PlatformDependent.throwException(e);
/*     */     } 
/* 181 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toHexString(byte[] src) {
/* 188 */     return toHexString(src, 0, src.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toHexString(byte[] src, int offset, int length) {
/* 195 */     return ((StringBuilder)toHexString(new StringBuilder(length << 1), src, offset, length)).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Appendable> T toHexString(T dst, byte[] src) {
/* 202 */     return toHexString(dst, src, 0, src.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Appendable> T toHexString(T dst, byte[] src, int offset, int length) {
/* 209 */     assert length >= 0;
/* 210 */     if (length == 0) {
/* 211 */       return dst;
/*     */     }
/*     */     
/* 214 */     int end = offset + length;
/* 215 */     int endMinusOne = end - 1;
/*     */     
/*     */     int i;
/*     */     
/* 219 */     for (i = offset; i < endMinusOne && 
/* 220 */       src[i] == 0; i++);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     byteToHexString(dst, src[i++]);
/* 226 */     int remaining = end - i;
/* 227 */     toHexStringPadded(dst, src, i, remaining);
/*     */     
/* 229 */     return dst;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String simpleClassName(Object o) {
/* 236 */     if (o == null) {
/* 237 */       return "null_object";
/*     */     }
/* 239 */     return simpleClassName(o.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String simpleClassName(Class<?> clazz) {
/* 248 */     if (clazz == null) {
/* 249 */       return "null_class";
/*     */     }
/*     */     
/* 252 */     Package pkg = clazz.getPackage();
/* 253 */     if (pkg != null) {
/* 254 */       return clazz.getName().substring(pkg.getName().length() + 1);
/*     */     }
/* 256 */     return clazz.getName();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\internal\StringUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */