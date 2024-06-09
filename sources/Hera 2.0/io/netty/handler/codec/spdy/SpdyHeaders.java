/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public abstract class SpdyHeaders
/*     */   implements Iterable<Map.Entry<String, String>>
/*     */ {
/*  34 */   public static final SpdyHeaders EMPTY_HEADERS = new SpdyHeaders()
/*     */     {
/*     */       public List<String> getAll(String name)
/*     */       {
/*  38 */         return Collections.emptyList();
/*     */       }
/*     */ 
/*     */       
/*     */       public List<Map.Entry<String, String>> entries() {
/*  43 */         return Collections.emptyList();
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean contains(String name) {
/*  48 */         return false;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean isEmpty() {
/*  53 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public Set<String> names() {
/*  58 */         return Collections.emptySet();
/*     */       }
/*     */ 
/*     */       
/*     */       public SpdyHeaders add(String name, Object value) {
/*  63 */         throw new UnsupportedOperationException("read only");
/*     */       }
/*     */ 
/*     */       
/*     */       public SpdyHeaders add(String name, Iterable<?> values) {
/*  68 */         throw new UnsupportedOperationException("read only");
/*     */       }
/*     */ 
/*     */       
/*     */       public SpdyHeaders set(String name, Object value) {
/*  73 */         throw new UnsupportedOperationException("read only");
/*     */       }
/*     */ 
/*     */       
/*     */       public SpdyHeaders set(String name, Iterable<?> values) {
/*  78 */         throw new UnsupportedOperationException("read only");
/*     */       }
/*     */ 
/*     */       
/*     */       public SpdyHeaders remove(String name) {
/*  83 */         throw new UnsupportedOperationException("read only");
/*     */       }
/*     */ 
/*     */       
/*     */       public SpdyHeaders clear() {
/*  88 */         throw new UnsupportedOperationException("read only");
/*     */       }
/*     */ 
/*     */       
/*     */       public Iterator<Map.Entry<String, String>> iterator() {
/*  93 */         return entries().iterator();
/*     */       }
/*     */ 
/*     */       
/*     */       public String get(String name) {
/*  98 */         return null;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class HttpNames
/*     */   {
/*     */     public static final String HOST = ":host";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String METHOD = ":method";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String PATH = ":path";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String SCHEME = ":scheme";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String STATUS = ":status";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String VERSION = ":version";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getHeader(SpdyHeadersFrame frame, String name) {
/* 142 */     return frame.headers().get(name);
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
/*     */   public static String getHeader(SpdyHeadersFrame frame, String name, String defaultValue) {
/* 154 */     String value = frame.headers().get(name);
/* 155 */     if (value == null) {
/* 156 */       return defaultValue;
/*     */     }
/* 158 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setHeader(SpdyHeadersFrame frame, String name, Object value) {
/* 166 */     frame.headers().set(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setHeader(SpdyHeadersFrame frame, String name, Iterable<?> values) {
/* 174 */     frame.headers().set(name, values);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addHeader(SpdyHeadersFrame frame, String name, Object value) {
/* 181 */     frame.headers().add(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeHost(SpdyHeadersFrame frame) {
/* 188 */     frame.headers().remove(":host");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getHost(SpdyHeadersFrame frame) {
/* 195 */     return frame.headers().get(":host");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setHost(SpdyHeadersFrame frame, String host) {
/* 202 */     frame.headers().set(":host", host);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeMethod(int spdyVersion, SpdyHeadersFrame frame) {
/* 209 */     frame.headers().remove(":method");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HttpMethod getMethod(int spdyVersion, SpdyHeadersFrame frame) {
/*     */     try {
/* 217 */       return HttpMethod.valueOf(frame.headers().get(":method"));
/* 218 */     } catch (Exception e) {
/* 219 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setMethod(int spdyVersion, SpdyHeadersFrame frame, HttpMethod method) {
/* 227 */     frame.headers().set(":method", method.name());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeScheme(int spdyVersion, SpdyHeadersFrame frame) {
/* 234 */     frame.headers().remove(":scheme");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getScheme(int spdyVersion, SpdyHeadersFrame frame) {
/* 241 */     return frame.headers().get(":scheme");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setScheme(int spdyVersion, SpdyHeadersFrame frame, String scheme) {
/* 248 */     frame.headers().set(":scheme", scheme);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeStatus(int spdyVersion, SpdyHeadersFrame frame) {
/* 255 */     frame.headers().remove(":status");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HttpResponseStatus getStatus(int spdyVersion, SpdyHeadersFrame frame) {
/*     */     try {
/* 263 */       String status = frame.headers().get(":status");
/* 264 */       int space = status.indexOf(' ');
/* 265 */       if (space == -1) {
/* 266 */         return HttpResponseStatus.valueOf(Integer.parseInt(status));
/*     */       }
/* 268 */       int code = Integer.parseInt(status.substring(0, space));
/* 269 */       String reasonPhrase = status.substring(space + 1);
/* 270 */       HttpResponseStatus responseStatus = HttpResponseStatus.valueOf(code);
/* 271 */       if (responseStatus.reasonPhrase().equals(reasonPhrase)) {
/* 272 */         return responseStatus;
/*     */       }
/* 274 */       return new HttpResponseStatus(code, reasonPhrase);
/*     */     
/*     */     }
/* 277 */     catch (Exception e) {
/* 278 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setStatus(int spdyVersion, SpdyHeadersFrame frame, HttpResponseStatus status) {
/* 286 */     frame.headers().set(":status", status.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeUrl(int spdyVersion, SpdyHeadersFrame frame) {
/* 293 */     frame.headers().remove(":path");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getUrl(int spdyVersion, SpdyHeadersFrame frame) {
/* 300 */     return frame.headers().get(":path");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setUrl(int spdyVersion, SpdyHeadersFrame frame, String path) {
/* 307 */     frame.headers().set(":path", path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeVersion(int spdyVersion, SpdyHeadersFrame frame) {
/* 314 */     frame.headers().remove(":version");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HttpVersion getVersion(int spdyVersion, SpdyHeadersFrame frame) {
/*     */     try {
/* 322 */       return HttpVersion.valueOf(frame.headers().get(":version"));
/* 323 */     } catch (Exception e) {
/* 324 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setVersion(int spdyVersion, SpdyHeadersFrame frame, HttpVersion httpVersion) {
/* 332 */     frame.headers().set(":version", httpVersion.text());
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Map.Entry<String, String>> iterator() {
/* 337 */     return entries().iterator();
/*     */   }
/*     */   
/*     */   public abstract String get(String paramString);
/*     */   
/*     */   public abstract List<String> getAll(String paramString);
/*     */   
/*     */   public abstract List<Map.Entry<String, String>> entries();
/*     */   
/*     */   public abstract boolean contains(String paramString);
/*     */   
/*     */   public abstract Set<String> names();
/*     */   
/*     */   public abstract SpdyHeaders add(String paramString, Object paramObject);
/*     */   
/*     */   public abstract SpdyHeaders add(String paramString, Iterable<?> paramIterable);
/*     */   
/*     */   public abstract SpdyHeaders set(String paramString, Object paramObject);
/*     */   
/*     */   public abstract SpdyHeaders set(String paramString, Iterable<?> paramIterable);
/*     */   
/*     */   public abstract SpdyHeaders remove(String paramString);
/*     */   
/*     */   public abstract SpdyHeaders clear();
/*     */   
/*     */   public abstract boolean isEmpty();
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\spdy\SpdyHeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */