/*      */ package io.netty.handler.codec.http;
/*      */ 
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import java.text.ParseException;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class HttpHeaders
/*      */   implements Iterable<Map.Entry<String, String>>
/*      */ {
/*   38 */   private static final byte[] HEADER_SEPERATOR = new byte[] { 58, 32 };
/*   39 */   private static final byte[] CRLF = new byte[] { 13, 10 };
/*   40 */   private static final CharSequence CONTENT_LENGTH_ENTITY = newEntity("Content-Length");
/*   41 */   private static final CharSequence CONNECTION_ENTITY = newEntity("Connection");
/*   42 */   private static final CharSequence CLOSE_ENTITY = newEntity("close");
/*   43 */   private static final CharSequence KEEP_ALIVE_ENTITY = newEntity("keep-alive");
/*   44 */   private static final CharSequence HOST_ENTITY = newEntity("Host");
/*   45 */   private static final CharSequence DATE_ENTITY = newEntity("Date");
/*   46 */   private static final CharSequence EXPECT_ENTITY = newEntity("Expect");
/*   47 */   private static final CharSequence CONTINUE_ENTITY = newEntity("100-continue");
/*   48 */   private static final CharSequence TRANSFER_ENCODING_ENTITY = newEntity("Transfer-Encoding");
/*   49 */   private static final CharSequence CHUNKED_ENTITY = newEntity("chunked");
/*   50 */   private static final CharSequence SEC_WEBSOCKET_KEY1_ENTITY = newEntity("Sec-WebSocket-Key1");
/*   51 */   private static final CharSequence SEC_WEBSOCKET_KEY2_ENTITY = newEntity("Sec-WebSocket-Key2");
/*   52 */   private static final CharSequence SEC_WEBSOCKET_ORIGIN_ENTITY = newEntity("Sec-WebSocket-Origin");
/*   53 */   private static final CharSequence SEC_WEBSOCKET_LOCATION_ENTITY = newEntity("Sec-WebSocket-Location");
/*      */   
/*   55 */   public static final HttpHeaders EMPTY_HEADERS = new HttpHeaders()
/*      */     {
/*      */       public String get(String name) {
/*   58 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public List<String> getAll(String name) {
/*   63 */         return Collections.emptyList();
/*      */       }
/*      */ 
/*      */       
/*      */       public List<Map.Entry<String, String>> entries() {
/*   68 */         return Collections.emptyList();
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean contains(String name) {
/*   73 */         return false;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean isEmpty() {
/*   78 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       public Set<String> names() {
/*   83 */         return Collections.emptySet();
/*      */       }
/*      */ 
/*      */       
/*      */       public HttpHeaders add(String name, Object value) {
/*   88 */         throw new UnsupportedOperationException("read only");
/*      */       }
/*      */ 
/*      */       
/*      */       public HttpHeaders add(String name, Iterable<?> values) {
/*   93 */         throw new UnsupportedOperationException("read only");
/*      */       }
/*      */ 
/*      */       
/*      */       public HttpHeaders set(String name, Object value) {
/*   98 */         throw new UnsupportedOperationException("read only");
/*      */       }
/*      */ 
/*      */       
/*      */       public HttpHeaders set(String name, Iterable<?> values) {
/*  103 */         throw new UnsupportedOperationException("read only");
/*      */       }
/*      */ 
/*      */       
/*      */       public HttpHeaders remove(String name) {
/*  108 */         throw new UnsupportedOperationException("read only");
/*      */       }
/*      */ 
/*      */       
/*      */       public HttpHeaders clear() {
/*  113 */         throw new UnsupportedOperationException("read only");
/*      */       }
/*      */ 
/*      */       
/*      */       public Iterator<Map.Entry<String, String>> iterator() {
/*  118 */         return entries().iterator();
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class Names
/*      */   {
/*      */     public static final String ACCEPT = "Accept";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCEPT_CHARSET = "Accept-Charset";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCEPT_ENCODING = "Accept-Encoding";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCEPT_LANGUAGE = "Accept-Language";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCEPT_RANGES = "Accept-Ranges";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCEPT_PATCH = "Accept-Patch";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String AGE = "Age";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ALLOW = "Allow";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String AUTHORIZATION = "Authorization";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CACHE_CONTROL = "Cache-Control";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CONNECTION = "Connection";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CONTENT_BASE = "Content-Base";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CONTENT_ENCODING = "Content-Encoding";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CONTENT_LANGUAGE = "Content-Language";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CONTENT_LENGTH = "Content-Length";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CONTENT_LOCATION = "Content-Location";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CONTENT_MD5 = "Content-MD5";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CONTENT_RANGE = "Content-Range";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CONTENT_TYPE = "Content-Type";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String COOKIE = "Cookie";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String DATE = "Date";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ETAG = "ETag";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String EXPECT = "Expect";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String EXPIRES = "Expires";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String FROM = "From";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String HOST = "Host";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String IF_MATCH = "If-Match";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String IF_NONE_MATCH = "If-None-Match";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String IF_RANGE = "If-Range";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String LAST_MODIFIED = "Last-Modified";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String LOCATION = "Location";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String MAX_FORWARDS = "Max-Forwards";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ORIGIN = "Origin";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String PRAGMA = "Pragma";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String RANGE = "Range";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String REFERER = "Referer";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String RETRY_AFTER = "Retry-After";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String SEC_WEBSOCKET_KEY1 = "Sec-WebSocket-Key1";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String SEC_WEBSOCKET_KEY2 = "Sec-WebSocket-Key2";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String SEC_WEBSOCKET_LOCATION = "Sec-WebSocket-Location";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String SEC_WEBSOCKET_ORIGIN = "Sec-WebSocket-Origin";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String SEC_WEBSOCKET_VERSION = "Sec-WebSocket-Version";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String SEC_WEBSOCKET_KEY = "Sec-WebSocket-Key";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String SEC_WEBSOCKET_ACCEPT = "Sec-WebSocket-Accept";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String SERVER = "Server";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String SET_COOKIE = "Set-Cookie";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String SET_COOKIE2 = "Set-Cookie2";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String TE = "TE";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String TRAILER = "Trailer";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String TRANSFER_ENCODING = "Transfer-Encoding";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String UPGRADE = "Upgrade";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String USER_AGENT = "User-Agent";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String VARY = "Vary";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String VIA = "Via";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String WARNING = "Warning";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String WEBSOCKET_LOCATION = "WebSocket-Location";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String WEBSOCKET_ORIGIN = "WebSocket-Origin";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String WEBSOCKET_PROTOCOL = "WebSocket-Protocol";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class Values
/*      */   {
/*      */     public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String BASE64 = "base64";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String BINARY = "binary";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String BOUNDARY = "boundary";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String BYTES = "bytes";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CHARSET = "charset";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CHUNKED = "chunked";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CLOSE = "close";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String COMPRESS = "compress";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CONTINUE = "100-continue";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String DEFLATE = "deflate";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String GZIP = "gzip";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String IDENTITY = "identity";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String KEEP_ALIVE = "keep-alive";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String MAX_AGE = "max-age";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String MAX_STALE = "max-stale";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String MIN_FRESH = "min-fresh";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String MULTIPART_FORM_DATA = "multipart/form-data";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String MUST_REVALIDATE = "must-revalidate";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String NO_CACHE = "no-cache";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String NO_STORE = "no-store";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String NO_TRANSFORM = "no-transform";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String NONE = "none";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ONLY_IF_CACHED = "only-if-cached";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String PRIVATE = "private";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String PROXY_REVALIDATE = "proxy-revalidate";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String PUBLIC = "public";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String QUOTED_PRINTABLE = "quoted-printable";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String S_MAXAGE = "s-maxage";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String TRAILERS = "trailers";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String UPGRADE = "Upgrade";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String WEBSOCKET = "WebSocket";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isKeepAlive(HttpMessage message) {
/*  568 */     String connection = message.headers().get(CONNECTION_ENTITY);
/*  569 */     if (connection != null && equalsIgnoreCase(CLOSE_ENTITY, connection)) {
/*  570 */       return false;
/*      */     }
/*      */     
/*  573 */     if (message.getProtocolVersion().isKeepAliveDefault()) {
/*  574 */       return !equalsIgnoreCase(CLOSE_ENTITY, connection);
/*      */     }
/*  576 */     return equalsIgnoreCase(KEEP_ALIVE_ENTITY, connection);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setKeepAlive(HttpMessage message, boolean keepAlive) {
/*  600 */     HttpHeaders h = message.headers();
/*  601 */     if (message.getProtocolVersion().isKeepAliveDefault()) {
/*  602 */       if (keepAlive) {
/*  603 */         h.remove(CONNECTION_ENTITY);
/*      */       } else {
/*  605 */         h.set(CONNECTION_ENTITY, CLOSE_ENTITY);
/*      */       }
/*      */     
/*  608 */     } else if (keepAlive) {
/*  609 */       h.set(CONNECTION_ENTITY, KEEP_ALIVE_ENTITY);
/*      */     } else {
/*  611 */       h.remove(CONNECTION_ENTITY);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getHeader(HttpMessage message, String name) {
/*  620 */     return message.headers().get(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getHeader(HttpMessage message, CharSequence name) {
/*  631 */     return message.headers().get(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getHeader(HttpMessage message, String name, String defaultValue) {
/*  638 */     return getHeader(message, name, defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getHeader(HttpMessage message, CharSequence name, String defaultValue) {
/*  650 */     String value = message.headers().get(name);
/*  651 */     if (value == null) {
/*  652 */       return defaultValue;
/*      */     }
/*  654 */     return value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setHeader(HttpMessage message, String name, Object value) {
/*  661 */     message.headers().set(name, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setHeader(HttpMessage message, CharSequence name, Object value) {
/*  673 */     message.headers().set(name, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setHeader(HttpMessage message, String name, Iterable<?> values) {
/*  681 */     message.headers().set(name, values);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setHeader(HttpMessage message, CharSequence name, Iterable<?> values) {
/*  699 */     message.headers().set(name, values);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addHeader(HttpMessage message, String name, Object value) {
/*  706 */     message.headers().add(name, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addHeader(HttpMessage message, CharSequence name, Object value) {
/*  717 */     message.headers().add(name, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void removeHeader(HttpMessage message, String name) {
/*  724 */     message.headers().remove(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void removeHeader(HttpMessage message, CharSequence name) {
/*  731 */     message.headers().remove(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void clearHeaders(HttpMessage message) {
/*  738 */     message.headers().clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getIntHeader(HttpMessage message, String name) {
/*  745 */     return getIntHeader(message, name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getIntHeader(HttpMessage message, CharSequence name) {
/*  758 */     String value = getHeader(message, name);
/*  759 */     if (value == null) {
/*  760 */       throw new NumberFormatException("header not found: " + name);
/*      */     }
/*  762 */     return Integer.parseInt(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getIntHeader(HttpMessage message, String name, int defaultValue) {
/*  769 */     return getIntHeader(message, name, defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getIntHeader(HttpMessage message, CharSequence name, int defaultValue) {
/*  781 */     String value = getHeader(message, name);
/*  782 */     if (value == null) {
/*  783 */       return defaultValue;
/*      */     }
/*      */     
/*      */     try {
/*  787 */       return Integer.parseInt(value);
/*  788 */     } catch (NumberFormatException ignored) {
/*  789 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setIntHeader(HttpMessage message, String name, int value) {
/*  797 */     message.headers().set(name, Integer.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setIntHeader(HttpMessage message, CharSequence name, int value) {
/*  805 */     message.headers().set(name, Integer.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setIntHeader(HttpMessage message, String name, Iterable<Integer> values) {
/*  812 */     message.headers().set(name, values);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setIntHeader(HttpMessage message, CharSequence name, Iterable<Integer> values) {
/*  820 */     message.headers().set(name, values);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addIntHeader(HttpMessage message, String name, int value) {
/*  828 */     message.headers().add(name, Integer.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addIntHeader(HttpMessage message, CharSequence name, int value) {
/*  835 */     message.headers().add(name, Integer.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getDateHeader(HttpMessage message, String name) throws ParseException {
/*  842 */     return getDateHeader(message, name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getDateHeader(HttpMessage message, CharSequence name) throws ParseException {
/*  855 */     String value = getHeader(message, name);
/*  856 */     if (value == null) {
/*  857 */       throw new ParseException("header not found: " + name, 0);
/*      */     }
/*  859 */     return HttpHeaderDateFormat.get().parse(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getDateHeader(HttpMessage message, String name, Date defaultValue) {
/*  866 */     return getDateHeader(message, name, defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getDateHeader(HttpMessage message, CharSequence name, Date defaultValue) {
/*  878 */     String value = getHeader(message, name);
/*  879 */     if (value == null) {
/*  880 */       return defaultValue;
/*      */     }
/*      */     
/*      */     try {
/*  884 */       return HttpHeaderDateFormat.get().parse(value);
/*  885 */     } catch (ParseException ignored) {
/*  886 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setDateHeader(HttpMessage message, String name, Date value) {
/*  894 */     setDateHeader(message, name, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setDateHeader(HttpMessage message, CharSequence name, Date value) {
/*  904 */     if (value != null) {
/*  905 */       message.headers().set(name, HttpHeaderDateFormat.get().format(value));
/*      */     } else {
/*  907 */       message.headers().set(name, (Iterable<?>)null);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setDateHeader(HttpMessage message, String name, Iterable<Date> values) {
/*  915 */     message.headers().set(name, values);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setDateHeader(HttpMessage message, CharSequence name, Iterable<Date> values) {
/*  925 */     message.headers().set(name, values);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addDateHeader(HttpMessage message, String name, Date value) {
/*  932 */     message.headers().add(name, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addDateHeader(HttpMessage message, CharSequence name, Date value) {
/*  941 */     message.headers().add(name, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getContentLength(HttpMessage message) {
/*  957 */     String value = getHeader(message, CONTENT_LENGTH_ENTITY);
/*  958 */     if (value != null) {
/*  959 */       return Long.parseLong(value);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  964 */     long webSocketContentLength = getWebSocketContentLength(message);
/*  965 */     if (webSocketContentLength >= 0L) {
/*  966 */       return webSocketContentLength;
/*      */     }
/*      */ 
/*      */     
/*  970 */     throw new NumberFormatException("header not found: Content-Length");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getContentLength(HttpMessage message, long defaultValue) {
/*  984 */     String contentLength = message.headers().get(CONTENT_LENGTH_ENTITY);
/*  985 */     if (contentLength != null) {
/*      */       try {
/*  987 */         return Long.parseLong(contentLength);
/*  988 */       } catch (NumberFormatException ignored) {
/*  989 */         return defaultValue;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  995 */     long webSocketContentLength = getWebSocketContentLength(message);
/*  996 */     if (webSocketContentLength >= 0L) {
/*  997 */       return webSocketContentLength;
/*      */     }
/*      */ 
/*      */     
/* 1001 */     return defaultValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getWebSocketContentLength(HttpMessage message) {
/* 1010 */     HttpHeaders h = message.headers();
/* 1011 */     if (message instanceof HttpRequest) {
/* 1012 */       HttpRequest req = (HttpRequest)message;
/* 1013 */       if (HttpMethod.GET.equals(req.getMethod()) && h.contains(SEC_WEBSOCKET_KEY1_ENTITY) && h.contains(SEC_WEBSOCKET_KEY2_ENTITY))
/*      */       {
/*      */         
/* 1016 */         return 8;
/*      */       }
/* 1018 */     } else if (message instanceof HttpResponse) {
/* 1019 */       HttpResponse res = (HttpResponse)message;
/* 1020 */       if (res.getStatus().code() == 101 && h.contains(SEC_WEBSOCKET_ORIGIN_ENTITY) && h.contains(SEC_WEBSOCKET_LOCATION_ENTITY))
/*      */       {
/*      */         
/* 1023 */         return 16;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1028 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setContentLength(HttpMessage message, long length) {
/* 1035 */     message.headers().set(CONTENT_LENGTH_ENTITY, Long.valueOf(length));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getHost(HttpMessage message) {
/* 1042 */     return message.headers().get(HOST_ENTITY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getHost(HttpMessage message, String defaultValue) {
/* 1050 */     return getHeader(message, HOST_ENTITY, defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setHost(HttpMessage message, String value) {
/* 1057 */     message.headers().set(HOST_ENTITY, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setHost(HttpMessage message, CharSequence value) {
/* 1064 */     message.headers().set(HOST_ENTITY, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getDate(HttpMessage message) throws ParseException {
/* 1074 */     return getDateHeader(message, DATE_ENTITY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getDate(HttpMessage message, Date defaultValue) {
/* 1083 */     return getDateHeader(message, DATE_ENTITY, defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setDate(HttpMessage message, Date value) {
/* 1090 */     if (value != null) {
/* 1091 */       message.headers().set(DATE_ENTITY, HttpHeaderDateFormat.get().format(value));
/*      */     } else {
/* 1093 */       message.headers().set(DATE_ENTITY, (Iterable<?>)null);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean is100ContinueExpected(HttpMessage message) {
/* 1103 */     if (!(message instanceof HttpRequest)) {
/* 1104 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1108 */     if (message.getProtocolVersion().compareTo(HttpVersion.HTTP_1_1) < 0) {
/* 1109 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1113 */     String value = message.headers().get(EXPECT_ENTITY);
/* 1114 */     if (value == null) {
/* 1115 */       return false;
/*      */     }
/* 1117 */     if (equalsIgnoreCase(CONTINUE_ENTITY, value)) {
/* 1118 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1122 */     return message.headers().contains(EXPECT_ENTITY, CONTINUE_ENTITY, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set100ContinueExpected(HttpMessage message) {
/* 1131 */     set100ContinueExpected(message, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set100ContinueExpected(HttpMessage message, boolean set) {
/* 1142 */     if (set) {
/* 1143 */       message.headers().set(EXPECT_ENTITY, CONTINUE_ENTITY);
/*      */     } else {
/* 1145 */       message.headers().remove(EXPECT_ENTITY);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void validateHeaderName(CharSequence headerName) {
/* 1156 */     if (headerName == null) {
/* 1157 */       throw new NullPointerException("Header names cannot be null");
/*      */     }
/*      */     
/* 1160 */     for (int index = 0; index < headerName.length(); index++) {
/*      */       
/* 1162 */       char character = headerName.charAt(index);
/*      */ 
/*      */       
/* 1165 */       if (character > '') {
/* 1166 */         throw new IllegalArgumentException("Header name cannot contain non-ASCII characters: " + headerName);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1171 */       switch (character) { case '\t': case '\n': case '\013': case '\f': case '\r': case ' ': case ',': case ':':
/*      */         case ';':
/*      */         case '=':
/* 1174 */           throw new IllegalArgumentException("Header name cannot contain the following prohibited characters: =,;: \\t\\r\\n\\v\\f: " + headerName); }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void validateHeaderValue(CharSequence headerValue) {
/* 1188 */     if (headerValue == null) {
/* 1189 */       throw new NullPointerException("Header values cannot be null");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1201 */     int state = 0;
/*      */ 
/*      */ 
/*      */     
/* 1205 */     for (int index = 0; index < headerValue.length(); index++) {
/* 1206 */       char character = headerValue.charAt(index);
/*      */ 
/*      */       
/* 1209 */       switch (character) {
/*      */         case '\013':
/* 1211 */           throw new IllegalArgumentException("Header value contains a prohibited character '\\v': " + headerValue);
/*      */         
/*      */         case '\f':
/* 1214 */           throw new IllegalArgumentException("Header value contains a prohibited character '\\f': " + headerValue);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1219 */       switch (state) {
/*      */         case 0:
/* 1221 */           switch (character) {
/*      */             case '\r':
/* 1223 */               state = 1;
/*      */               break;
/*      */             case '\n':
/* 1226 */               state = 2;
/*      */               break;
/*      */           } 
/*      */           break;
/*      */         case 1:
/* 1231 */           switch (character) {
/*      */             case '\n':
/* 1233 */               state = 2;
/*      */               break;
/*      */           } 
/* 1236 */           throw new IllegalArgumentException("Only '\\n' is allowed after '\\r': " + headerValue);
/*      */ 
/*      */ 
/*      */         
/*      */         case 2:
/* 1241 */           switch (character) { case '\t':
/*      */             case ' ':
/* 1243 */               state = 0;
/*      */               break; }
/*      */           
/* 1246 */           throw new IllegalArgumentException("Only ' ' and '\\t' are allowed after '\\n': " + headerValue);
/*      */       } 
/*      */ 
/*      */ 
/*      */     
/*      */     } 
/* 1252 */     if (state != 0) {
/* 1253 */       throw new IllegalArgumentException("Header value must not end with '\\r' or '\\n':" + headerValue);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isTransferEncodingChunked(HttpMessage message) {
/* 1265 */     return message.headers().contains(TRANSFER_ENCODING_ENTITY, CHUNKED_ENTITY, true);
/*      */   }
/*      */   
/*      */   public static void removeTransferEncodingChunked(HttpMessage m) {
/* 1269 */     List<String> values = m.headers().getAll(TRANSFER_ENCODING_ENTITY);
/* 1270 */     if (values.isEmpty()) {
/*      */       return;
/*      */     }
/* 1273 */     Iterator<String> valuesIt = values.iterator();
/* 1274 */     while (valuesIt.hasNext()) {
/* 1275 */       String value = valuesIt.next();
/* 1276 */       if (equalsIgnoreCase(value, CHUNKED_ENTITY)) {
/* 1277 */         valuesIt.remove();
/*      */       }
/*      */     } 
/* 1280 */     if (values.isEmpty()) {
/* 1281 */       m.headers().remove(TRANSFER_ENCODING_ENTITY);
/*      */     } else {
/* 1283 */       m.headers().set(TRANSFER_ENCODING_ENTITY, values);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void setTransferEncodingChunked(HttpMessage m) {
/* 1288 */     addHeader(m, TRANSFER_ENCODING_ENTITY, CHUNKED_ENTITY);
/* 1289 */     removeHeader(m, CONTENT_LENGTH_ENTITY);
/*      */   }
/*      */   
/*      */   public static boolean isContentLengthSet(HttpMessage m) {
/* 1293 */     return m.headers().contains(CONTENT_LENGTH_ENTITY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equalsIgnoreCase(CharSequence name1, CharSequence name2) {
/* 1301 */     if (name1 == name2) {
/* 1302 */       return true;
/*      */     }
/*      */     
/* 1305 */     if (name1 == null || name2 == null) {
/* 1306 */       return false;
/*      */     }
/*      */     
/* 1309 */     int nameLen = name1.length();
/* 1310 */     if (nameLen != name2.length()) {
/* 1311 */       return false;
/*      */     }
/*      */     
/* 1314 */     for (int i = nameLen - 1; i >= 0; i--) {
/* 1315 */       char c1 = name1.charAt(i);
/* 1316 */       char c2 = name2.charAt(i);
/* 1317 */       if (c1 != c2) {
/* 1318 */         if (c1 >= 'A' && c1 <= 'Z') {
/* 1319 */           c1 = (char)(c1 + 32);
/*      */         }
/* 1321 */         if (c2 >= 'A' && c2 <= 'Z') {
/* 1322 */           c2 = (char)(c2 + 32);
/*      */         }
/* 1324 */         if (c1 != c2) {
/* 1325 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/* 1329 */     return true;
/*      */   }
/*      */   
/*      */   static int hash(CharSequence name) {
/* 1333 */     if (name instanceof HttpHeaderEntity) {
/* 1334 */       return ((HttpHeaderEntity)name).hash();
/*      */     }
/* 1336 */     int h = 0;
/* 1337 */     for (int i = name.length() - 1; i >= 0; i--) {
/* 1338 */       char c = name.charAt(i);
/* 1339 */       if (c >= 'A' && c <= 'Z') {
/* 1340 */         c = (char)(c + 32);
/*      */       }
/* 1342 */       h = 31 * h + c;
/*      */     } 
/*      */     
/* 1345 */     if (h > 0)
/* 1346 */       return h; 
/* 1347 */     if (h == Integer.MIN_VALUE) {
/* 1348 */       return Integer.MAX_VALUE;
/*      */     }
/* 1350 */     return -h;
/*      */   }
/*      */ 
/*      */   
/*      */   static void encode(HttpHeaders headers, ByteBuf buf) {
/* 1355 */     if (headers instanceof DefaultHttpHeaders) {
/* 1356 */       ((DefaultHttpHeaders)headers).encode(buf);
/*      */     } else {
/* 1358 */       for (Map.Entry<String, String> header : (Iterable<Map.Entry<String, String>>)headers) {
/* 1359 */         encode(header.getKey(), header.getValue(), buf);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static void encode(CharSequence key, CharSequence value, ByteBuf buf) {
/* 1366 */     if (!encodeAscii(key, buf)) {
/* 1367 */       buf.writeBytes(HEADER_SEPERATOR);
/*      */     }
/* 1369 */     if (!encodeAscii(value, buf)) {
/* 1370 */       buf.writeBytes(CRLF);
/*      */     }
/*      */   }
/*      */   
/*      */   public static boolean encodeAscii(CharSequence seq, ByteBuf buf) {
/* 1375 */     if (seq instanceof HttpHeaderEntity) {
/* 1376 */       return ((HttpHeaderEntity)seq).encode(buf);
/*      */     }
/* 1378 */     encodeAscii0(seq, buf);
/* 1379 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   static void encodeAscii0(CharSequence seq, ByteBuf buf) {
/* 1384 */     int length = seq.length();
/* 1385 */     for (int i = 0; i < length; i++) {
/* 1386 */       buf.writeByte((byte)seq.charAt(i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharSequence newEntity(String name) {
/* 1395 */     if (name == null) {
/* 1396 */       throw new NullPointerException("name");
/*      */     }
/* 1398 */     return new HttpHeaderEntity(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharSequence newNameEntity(String name) {
/* 1406 */     if (name == null) {
/* 1407 */       throw new NullPointerException("name");
/*      */     }
/* 1409 */     return new HttpHeaderEntity(name, HEADER_SEPERATOR);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharSequence newValueEntity(String name) {
/* 1417 */     if (name == null) {
/* 1418 */       throw new NullPointerException("name");
/*      */     }
/* 1420 */     return new HttpHeaderEntity(name, CRLF);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract String get(String paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String get(CharSequence name) {
/* 1438 */     return get(name.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract List<String> getAll(String paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getAll(CharSequence name) {
/* 1454 */     return getAll(name.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract List<Map.Entry<String, String>> entries();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract boolean contains(String paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(CharSequence name) {
/* 1476 */     return contains(name.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract boolean isEmpty();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract Set<String> names();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract HttpHeaders add(String paramString, Object paramObject);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpHeaders add(CharSequence name, Object value) {
/* 1510 */     return add(name.toString(), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract HttpHeaders add(String paramString, Iterable<?> paramIterable);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpHeaders add(CharSequence name, Iterable<?> values) {
/* 1536 */     return add(name.toString(), values);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpHeaders add(HttpHeaders headers) {
/* 1545 */     if (headers == null) {
/* 1546 */       throw new NullPointerException("headers");
/*      */     }
/* 1548 */     for (Map.Entry<String, String> e : (Iterable<Map.Entry<String, String>>)headers) {
/* 1549 */       add(e.getKey(), e.getValue());
/*      */     }
/* 1551 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract HttpHeaders set(String paramString, Object paramObject);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpHeaders set(CharSequence name, Object value) {
/* 1573 */     return set(name.toString(), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract HttpHeaders set(String paramString, Iterable<?> paramIterable);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpHeaders set(CharSequence name, Iterable<?> values) {
/* 1601 */     return set(name.toString(), values);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpHeaders set(HttpHeaders headers) {
/* 1610 */     if (headers == null) {
/* 1611 */       throw new NullPointerException("headers");
/*      */     }
/* 1613 */     clear();
/* 1614 */     for (Map.Entry<String, String> e : (Iterable<Map.Entry<String, String>>)headers) {
/* 1615 */       add(e.getKey(), e.getValue());
/*      */     }
/* 1617 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract HttpHeaders remove(String paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpHeaders remove(CharSequence name) {
/* 1632 */     return remove(name.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract HttpHeaders clear();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(String name, String value, boolean ignoreCaseValue) {
/* 1646 */     List<String> values = getAll(name);
/* 1647 */     if (values.isEmpty()) {
/* 1648 */       return false;
/*      */     }
/*      */     
/* 1651 */     for (String v : values) {
/* 1652 */       if (ignoreCaseValue) {
/* 1653 */         if (equalsIgnoreCase(v, value))
/* 1654 */           return true; 
/*      */         continue;
/*      */       } 
/* 1657 */       if (v.equals(value)) {
/* 1658 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1662 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(CharSequence name, CharSequence value, boolean ignoreCaseValue) {
/* 1674 */     return contains(name.toString(), value.toString(), ignoreCaseValue);
/*      */   }
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\HttpHeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */