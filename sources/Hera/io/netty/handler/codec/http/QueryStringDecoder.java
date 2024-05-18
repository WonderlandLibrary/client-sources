/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
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
/*     */ public class QueryStringDecoder
/*     */ {
/*     */   private static final int DEFAULT_MAX_PARAMS = 1024;
/*     */   private final Charset charset;
/*     */   private final String uri;
/*     */   private final boolean hasPath;
/*     */   private final int maxParams;
/*     */   private String path;
/*     */   private Map<String, List<String>> params;
/*     */   private int nParams;
/*     */   
/*     */   public QueryStringDecoder(String uri) {
/*  73 */     this(uri, HttpConstants.DEFAULT_CHARSET);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QueryStringDecoder(String uri, boolean hasPath) {
/*  81 */     this(uri, HttpConstants.DEFAULT_CHARSET, hasPath);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QueryStringDecoder(String uri, Charset charset) {
/*  89 */     this(uri, charset, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QueryStringDecoder(String uri, Charset charset, boolean hasPath) {
/*  97 */     this(uri, charset, hasPath, 1024);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QueryStringDecoder(String uri, Charset charset, boolean hasPath, int maxParams) {
/* 105 */     if (uri == null) {
/* 106 */       throw new NullPointerException("getUri");
/*     */     }
/* 108 */     if (charset == null) {
/* 109 */       throw new NullPointerException("charset");
/*     */     }
/* 111 */     if (maxParams <= 0) {
/* 112 */       throw new IllegalArgumentException("maxParams: " + maxParams + " (expected: a positive integer)");
/*     */     }
/*     */ 
/*     */     
/* 116 */     this.uri = uri;
/* 117 */     this.charset = charset;
/* 118 */     this.maxParams = maxParams;
/* 119 */     this.hasPath = hasPath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QueryStringDecoder(URI uri) {
/* 127 */     this(uri, HttpConstants.DEFAULT_CHARSET);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QueryStringDecoder(URI uri, Charset charset) {
/* 135 */     this(uri, charset, 1024);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QueryStringDecoder(URI uri, Charset charset, int maxParams) {
/* 143 */     if (uri == null) {
/* 144 */       throw new NullPointerException("getUri");
/*     */     }
/* 146 */     if (charset == null) {
/* 147 */       throw new NullPointerException("charset");
/*     */     }
/* 149 */     if (maxParams <= 0) {
/* 150 */       throw new IllegalArgumentException("maxParams: " + maxParams + " (expected: a positive integer)");
/*     */     }
/*     */ 
/*     */     
/* 154 */     String rawPath = uri.getRawPath();
/* 155 */     if (rawPath != null) {
/* 156 */       this.hasPath = true;
/*     */     } else {
/* 158 */       rawPath = "";
/* 159 */       this.hasPath = false;
/*     */     } 
/*     */     
/* 162 */     this.uri = rawPath + '?' + uri.getRawQuery();
/*     */     
/* 164 */     this.charset = charset;
/* 165 */     this.maxParams = maxParams;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String path() {
/* 172 */     if (this.path == null) {
/* 173 */       if (!this.hasPath) {
/* 174 */         return this.path = "";
/*     */       }
/*     */       
/* 177 */       int pathEndPos = this.uri.indexOf('?');
/* 178 */       if (pathEndPos < 0) {
/* 179 */         this.path = this.uri;
/*     */       } else {
/* 181 */         return this.path = this.uri.substring(0, pathEndPos);
/*     */       } 
/*     */     } 
/* 184 */     return this.path;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, List<String>> parameters() {
/* 191 */     if (this.params == null) {
/* 192 */       if (this.hasPath) {
/* 193 */         int pathLength = path().length();
/* 194 */         if (this.uri.length() == pathLength) {
/* 195 */           return Collections.emptyMap();
/*     */         }
/* 197 */         decodeParams(this.uri.substring(pathLength + 1));
/*     */       } else {
/* 199 */         if (this.uri.isEmpty()) {
/* 200 */           return Collections.emptyMap();
/*     */         }
/* 202 */         decodeParams(this.uri);
/*     */       } 
/*     */     }
/* 205 */     return this.params;
/*     */   }
/*     */   
/*     */   private void decodeParams(String s) {
/* 209 */     Map<String, List<String>> params = this.params = new LinkedHashMap<String, List<String>>();
/* 210 */     this.nParams = 0;
/* 211 */     String name = null;
/* 212 */     int pos = 0;
/*     */     
/*     */     int i;
/* 215 */     for (i = 0; i < s.length(); i++) {
/* 216 */       char c = s.charAt(i);
/* 217 */       if (c == '=' && name == null) {
/* 218 */         if (pos != i) {
/* 219 */           name = decodeComponent(s.substring(pos, i), this.charset);
/*     */         }
/* 221 */         pos = i + 1;
/*     */       }
/* 223 */       else if (c == '&' || c == ';') {
/* 224 */         if (name == null && pos != i) {
/*     */ 
/*     */ 
/*     */           
/* 228 */           if (!addParam(params, decodeComponent(s.substring(pos, i), this.charset), "")) {
/*     */             return;
/*     */           }
/* 231 */         } else if (name != null) {
/* 232 */           if (!addParam(params, name, decodeComponent(s.substring(pos, i), this.charset))) {
/*     */             return;
/*     */           }
/* 235 */           name = null;
/*     */         } 
/* 237 */         pos = i + 1;
/*     */       } 
/*     */     } 
/*     */     
/* 241 */     if (pos != i) {
/* 242 */       if (name == null) {
/* 243 */         addParam(params, decodeComponent(s.substring(pos, i), this.charset), "");
/*     */       } else {
/* 245 */         addParam(params, name, decodeComponent(s.substring(pos, i), this.charset));
/*     */       } 
/* 247 */     } else if (name != null) {
/* 248 */       addParam(params, name, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean addParam(Map<String, List<String>> params, String name, String value) {
/* 253 */     if (this.nParams >= this.maxParams) {
/* 254 */       return false;
/*     */     }
/*     */     
/* 257 */     List<String> values = params.get(name);
/* 258 */     if (values == null) {
/* 259 */       values = new ArrayList<String>(1);
/* 260 */       params.put(name, values);
/*     */     } 
/* 262 */     values.add(value);
/* 263 */     this.nParams++;
/* 264 */     return true;
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
/*     */   public static String decodeComponent(String s) {
/* 279 */     return decodeComponent(s, HttpConstants.DEFAULT_CHARSET);
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
/*     */   
/*     */   public static String decodeComponent(String s, Charset charset) {
/* 305 */     if (s == null) {
/* 306 */       return "";
/*     */     }
/* 308 */     int size = s.length();
/* 309 */     boolean modified = false;
/* 310 */     for (int i = 0; i < size; i++) {
/* 311 */       char c = s.charAt(i);
/* 312 */       if (c == '%' || c == '+') {
/* 313 */         modified = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 317 */     if (!modified) {
/* 318 */       return s;
/*     */     }
/* 320 */     byte[] buf = new byte[size];
/* 321 */     int pos = 0;
/* 322 */     for (int j = 0; j < size; j++) {
/* 323 */       char c2, c = s.charAt(j);
/* 324 */       switch (c) {
/*     */         case '+':
/* 326 */           buf[pos++] = 32;
/*     */           break;
/*     */         case '%':
/* 329 */           if (j == size - 1) {
/* 330 */             throw new IllegalArgumentException("unterminated escape sequence at end of string: " + s);
/*     */           }
/*     */           
/* 333 */           c = s.charAt(++j);
/* 334 */           if (c == '%') {
/* 335 */             buf[pos++] = 37;
/*     */             break;
/*     */           } 
/* 338 */           if (j == size - 1) {
/* 339 */             throw new IllegalArgumentException("partial escape sequence at end of string: " + s);
/*     */           }
/*     */           
/* 342 */           c = decodeHexNibble(c);
/* 343 */           c2 = decodeHexNibble(s.charAt(++j));
/* 344 */           if (c == Character.MAX_VALUE || c2 == Character.MAX_VALUE) {
/* 345 */             throw new IllegalArgumentException("invalid escape sequence `%" + s.charAt(j - 1) + s.charAt(j) + "' at index " + (j - 2) + " of: " + s);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 350 */           c = (char)(c * 16 + c2);
/*     */         
/*     */         default:
/* 353 */           buf[pos++] = (byte)c;
/*     */           break;
/*     */       } 
/*     */     } 
/* 357 */     return new String(buf, 0, pos, charset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static char decodeHexNibble(char c) {
/* 368 */     if ('0' <= c && c <= '9')
/* 369 */       return (char)(c - 48); 
/* 370 */     if ('a' <= c && c <= 'f')
/* 371 */       return (char)(c - 97 + 10); 
/* 372 */     if ('A' <= c && c <= 'F') {
/* 373 */       return (char)(c - 65 + 10);
/*     */     }
/* 375 */     return Character.MAX_VALUE;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\QueryStringDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */