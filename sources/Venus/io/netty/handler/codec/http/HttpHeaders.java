/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.DateFormatter;
import io.netty.handler.codec.HeadersUtils;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.EmptyHttpHeaders;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ObjectUtil;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class HttpHeaders
implements Iterable<Map.Entry<String, String>> {
    @Deprecated
    public static final HttpHeaders EMPTY_HEADERS = EmptyHttpHeaders.instance();

    @Deprecated
    public static boolean isKeepAlive(HttpMessage httpMessage) {
        return HttpUtil.isKeepAlive(httpMessage);
    }

    @Deprecated
    public static void setKeepAlive(HttpMessage httpMessage, boolean bl) {
        HttpUtil.setKeepAlive(httpMessage, bl);
    }

    @Deprecated
    public static String getHeader(HttpMessage httpMessage, String string) {
        return httpMessage.headers().get(string);
    }

    @Deprecated
    public static String getHeader(HttpMessage httpMessage, CharSequence charSequence) {
        return httpMessage.headers().get(charSequence);
    }

    @Deprecated
    public static String getHeader(HttpMessage httpMessage, String string, String string2) {
        return httpMessage.headers().get(string, string2);
    }

    @Deprecated
    public static String getHeader(HttpMessage httpMessage, CharSequence charSequence, String string) {
        return httpMessage.headers().get(charSequence, string);
    }

    @Deprecated
    public static void setHeader(HttpMessage httpMessage, String string, Object object) {
        httpMessage.headers().set(string, object);
    }

    @Deprecated
    public static void setHeader(HttpMessage httpMessage, CharSequence charSequence, Object object) {
        httpMessage.headers().set(charSequence, object);
    }

    @Deprecated
    public static void setHeader(HttpMessage httpMessage, String string, Iterable<?> iterable) {
        httpMessage.headers().set(string, iterable);
    }

    @Deprecated
    public static void setHeader(HttpMessage httpMessage, CharSequence charSequence, Iterable<?> iterable) {
        httpMessage.headers().set(charSequence, iterable);
    }

    @Deprecated
    public static void addHeader(HttpMessage httpMessage, String string, Object object) {
        httpMessage.headers().add(string, object);
    }

    @Deprecated
    public static void addHeader(HttpMessage httpMessage, CharSequence charSequence, Object object) {
        httpMessage.headers().add(charSequence, object);
    }

    @Deprecated
    public static void removeHeader(HttpMessage httpMessage, String string) {
        httpMessage.headers().remove(string);
    }

    @Deprecated
    public static void removeHeader(HttpMessage httpMessage, CharSequence charSequence) {
        httpMessage.headers().remove(charSequence);
    }

    @Deprecated
    public static void clearHeaders(HttpMessage httpMessage) {
        httpMessage.headers().clear();
    }

    @Deprecated
    public static int getIntHeader(HttpMessage httpMessage, String string) {
        return HttpHeaders.getIntHeader(httpMessage, (CharSequence)string);
    }

    @Deprecated
    public static int getIntHeader(HttpMessage httpMessage, CharSequence charSequence) {
        String string = httpMessage.headers().get(charSequence);
        if (string == null) {
            throw new NumberFormatException("header not found: " + charSequence);
        }
        return Integer.parseInt(string);
    }

    @Deprecated
    public static int getIntHeader(HttpMessage httpMessage, String string, int n) {
        return httpMessage.headers().getInt(string, n);
    }

    @Deprecated
    public static int getIntHeader(HttpMessage httpMessage, CharSequence charSequence, int n) {
        return httpMessage.headers().getInt(charSequence, n);
    }

    @Deprecated
    public static void setIntHeader(HttpMessage httpMessage, String string, int n) {
        httpMessage.headers().setInt(string, n);
    }

    @Deprecated
    public static void setIntHeader(HttpMessage httpMessage, CharSequence charSequence, int n) {
        httpMessage.headers().setInt(charSequence, n);
    }

    @Deprecated
    public static void setIntHeader(HttpMessage httpMessage, String string, Iterable<Integer> iterable) {
        httpMessage.headers().set(string, iterable);
    }

    @Deprecated
    public static void setIntHeader(HttpMessage httpMessage, CharSequence charSequence, Iterable<Integer> iterable) {
        httpMessage.headers().set(charSequence, iterable);
    }

    @Deprecated
    public static void addIntHeader(HttpMessage httpMessage, String string, int n) {
        httpMessage.headers().add(string, (Object)n);
    }

    @Deprecated
    public static void addIntHeader(HttpMessage httpMessage, CharSequence charSequence, int n) {
        httpMessage.headers().addInt(charSequence, n);
    }

    @Deprecated
    public static Date getDateHeader(HttpMessage httpMessage, String string) throws ParseException {
        return HttpHeaders.getDateHeader(httpMessage, (CharSequence)string);
    }

    @Deprecated
    public static Date getDateHeader(HttpMessage httpMessage, CharSequence charSequence) throws ParseException {
        String string = httpMessage.headers().get(charSequence);
        if (string == null) {
            throw new ParseException("header not found: " + charSequence, 0);
        }
        Date date = DateFormatter.parseHttpDate(string);
        if (date == null) {
            throw new ParseException("header can't be parsed into a Date: " + string, 0);
        }
        return date;
    }

    @Deprecated
    public static Date getDateHeader(HttpMessage httpMessage, String string, Date date) {
        return HttpHeaders.getDateHeader(httpMessage, (CharSequence)string, date);
    }

    @Deprecated
    public static Date getDateHeader(HttpMessage httpMessage, CharSequence charSequence, Date date) {
        String string = HttpHeaders.getHeader(httpMessage, charSequence);
        Date date2 = DateFormatter.parseHttpDate(string);
        return date2 != null ? date2 : date;
    }

    @Deprecated
    public static void setDateHeader(HttpMessage httpMessage, String string, Date date) {
        HttpHeaders.setDateHeader(httpMessage, (CharSequence)string, date);
    }

    @Deprecated
    public static void setDateHeader(HttpMessage httpMessage, CharSequence charSequence, Date date) {
        if (date != null) {
            httpMessage.headers().set(charSequence, (Object)DateFormatter.format(date));
        } else {
            httpMessage.headers().set(charSequence, null);
        }
    }

    @Deprecated
    public static void setDateHeader(HttpMessage httpMessage, String string, Iterable<Date> iterable) {
        httpMessage.headers().set(string, iterable);
    }

    @Deprecated
    public static void setDateHeader(HttpMessage httpMessage, CharSequence charSequence, Iterable<Date> iterable) {
        httpMessage.headers().set(charSequence, iterable);
    }

    @Deprecated
    public static void addDateHeader(HttpMessage httpMessage, String string, Date date) {
        httpMessage.headers().add(string, (Object)date);
    }

    @Deprecated
    public static void addDateHeader(HttpMessage httpMessage, CharSequence charSequence, Date date) {
        httpMessage.headers().add(charSequence, (Object)date);
    }

    @Deprecated
    public static long getContentLength(HttpMessage httpMessage) {
        return HttpUtil.getContentLength(httpMessage);
    }

    @Deprecated
    public static long getContentLength(HttpMessage httpMessage, long l) {
        return HttpUtil.getContentLength(httpMessage, l);
    }

    @Deprecated
    public static void setContentLength(HttpMessage httpMessage, long l) {
        HttpUtil.setContentLength(httpMessage, l);
    }

    @Deprecated
    public static String getHost(HttpMessage httpMessage) {
        return httpMessage.headers().get(HttpHeaderNames.HOST);
    }

    @Deprecated
    public static String getHost(HttpMessage httpMessage, String string) {
        return httpMessage.headers().get(HttpHeaderNames.HOST, string);
    }

    @Deprecated
    public static void setHost(HttpMessage httpMessage, String string) {
        httpMessage.headers().set((CharSequence)HttpHeaderNames.HOST, (Object)string);
    }

    @Deprecated
    public static void setHost(HttpMessage httpMessage, CharSequence charSequence) {
        httpMessage.headers().set((CharSequence)HttpHeaderNames.HOST, (Object)charSequence);
    }

    @Deprecated
    public static Date getDate(HttpMessage httpMessage) throws ParseException {
        return HttpHeaders.getDateHeader(httpMessage, HttpHeaderNames.DATE);
    }

    @Deprecated
    public static Date getDate(HttpMessage httpMessage, Date date) {
        return HttpHeaders.getDateHeader(httpMessage, HttpHeaderNames.DATE, date);
    }

    @Deprecated
    public static void setDate(HttpMessage httpMessage, Date date) {
        httpMessage.headers().set((CharSequence)HttpHeaderNames.DATE, (Object)date);
    }

    @Deprecated
    public static boolean is100ContinueExpected(HttpMessage httpMessage) {
        return HttpUtil.is100ContinueExpected(httpMessage);
    }

    @Deprecated
    public static void set100ContinueExpected(HttpMessage httpMessage) {
        HttpUtil.set100ContinueExpected(httpMessage, true);
    }

    @Deprecated
    public static void set100ContinueExpected(HttpMessage httpMessage, boolean bl) {
        HttpUtil.set100ContinueExpected(httpMessage, bl);
    }

    @Deprecated
    public static boolean isTransferEncodingChunked(HttpMessage httpMessage) {
        return HttpUtil.isTransferEncodingChunked(httpMessage);
    }

    @Deprecated
    public static void removeTransferEncodingChunked(HttpMessage httpMessage) {
        HttpUtil.setTransferEncodingChunked(httpMessage, false);
    }

    @Deprecated
    public static void setTransferEncodingChunked(HttpMessage httpMessage) {
        HttpUtil.setTransferEncodingChunked(httpMessage, true);
    }

    @Deprecated
    public static boolean isContentLengthSet(HttpMessage httpMessage) {
        return HttpUtil.isContentLengthSet(httpMessage);
    }

    @Deprecated
    public static boolean equalsIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        return AsciiString.contentEqualsIgnoreCase(charSequence, charSequence2);
    }

    @Deprecated
    public static void encodeAscii(CharSequence charSequence, ByteBuf byteBuf) {
        if (charSequence instanceof AsciiString) {
            ByteBufUtil.copy((AsciiString)charSequence, 0, byteBuf, charSequence.length());
        } else {
            byteBuf.writeCharSequence(charSequence, CharsetUtil.US_ASCII);
        }
    }

    @Deprecated
    public static CharSequence newEntity(String string) {
        return new AsciiString(string);
    }

    protected HttpHeaders() {
    }

    public abstract String get(String var1);

    public String get(CharSequence charSequence) {
        return this.get(charSequence.toString());
    }

    public String get(CharSequence charSequence, String string) {
        String string2 = this.get(charSequence);
        if (string2 == null) {
            return string;
        }
        return string2;
    }

    public abstract Integer getInt(CharSequence var1);

    public abstract int getInt(CharSequence var1, int var2);

    public abstract Short getShort(CharSequence var1);

    public abstract short getShort(CharSequence var1, short var2);

    public abstract Long getTimeMillis(CharSequence var1);

    public abstract long getTimeMillis(CharSequence var1, long var2);

    public abstract List<String> getAll(String var1);

    public List<String> getAll(CharSequence charSequence) {
        return this.getAll(charSequence.toString());
    }

    public abstract List<Map.Entry<String, String>> entries();

    public abstract boolean contains(String var1);

    @Override
    @Deprecated
    public abstract Iterator<Map.Entry<String, String>> iterator();

    public abstract Iterator<Map.Entry<CharSequence, CharSequence>> iteratorCharSequence();

    public Iterator<String> valueStringIterator(CharSequence charSequence) {
        return this.getAll(charSequence).iterator();
    }

    public Iterator<? extends CharSequence> valueCharSequenceIterator(CharSequence charSequence) {
        return this.valueStringIterator(charSequence);
    }

    public boolean contains(CharSequence charSequence) {
        return this.contains(charSequence.toString());
    }

    public abstract boolean isEmpty();

    public abstract int size();

    public abstract Set<String> names();

    public abstract HttpHeaders add(String var1, Object var2);

    public HttpHeaders add(CharSequence charSequence, Object object) {
        return this.add(charSequence.toString(), object);
    }

    public abstract HttpHeaders add(String var1, Iterable<?> var2);

    public HttpHeaders add(CharSequence charSequence, Iterable<?> iterable) {
        return this.add(charSequence.toString(), iterable);
    }

    public HttpHeaders add(HttpHeaders httpHeaders) {
        if (httpHeaders == null) {
            throw new NullPointerException("headers");
        }
        for (Map.Entry<String, String> entry : httpHeaders) {
            this.add(entry.getKey(), (Object)entry.getValue());
        }
        return this;
    }

    public abstract HttpHeaders addInt(CharSequence var1, int var2);

    public abstract HttpHeaders addShort(CharSequence var1, short var2);

    public abstract HttpHeaders set(String var1, Object var2);

    public HttpHeaders set(CharSequence charSequence, Object object) {
        return this.set(charSequence.toString(), object);
    }

    public abstract HttpHeaders set(String var1, Iterable<?> var2);

    public HttpHeaders set(CharSequence charSequence, Iterable<?> iterable) {
        return this.set(charSequence.toString(), iterable);
    }

    public HttpHeaders set(HttpHeaders httpHeaders) {
        ObjectUtil.checkNotNull(httpHeaders, "headers");
        this.clear();
        if (httpHeaders.isEmpty()) {
            return this;
        }
        for (Map.Entry<String, String> entry : httpHeaders) {
            this.add(entry.getKey(), (Object)entry.getValue());
        }
        return this;
    }

    public HttpHeaders setAll(HttpHeaders httpHeaders) {
        ObjectUtil.checkNotNull(httpHeaders, "headers");
        if (httpHeaders.isEmpty()) {
            return this;
        }
        for (Map.Entry<String, String> entry : httpHeaders) {
            this.set(entry.getKey(), (Object)entry.getValue());
        }
        return this;
    }

    public abstract HttpHeaders setInt(CharSequence var1, int var2);

    public abstract HttpHeaders setShort(CharSequence var1, short var2);

    public abstract HttpHeaders remove(String var1);

    public HttpHeaders remove(CharSequence charSequence) {
        return this.remove(charSequence.toString());
    }

    public abstract HttpHeaders clear();

    public boolean contains(String string, String string2, boolean bl) {
        Iterator<String> iterator2 = this.valueStringIterator(string);
        if (bl) {
            while (iterator2.hasNext()) {
                if (!iterator2.next().equalsIgnoreCase(string2)) continue;
                return false;
            }
        } else {
            while (iterator2.hasNext()) {
                if (!iterator2.next().equals(string2)) continue;
                return false;
            }
        }
        return true;
    }

    public boolean containsValue(CharSequence charSequence, CharSequence charSequence2, boolean bl) {
        Iterator<? extends CharSequence> iterator2 = this.valueCharSequenceIterator(charSequence);
        while (iterator2.hasNext()) {
            if (!HttpHeaders.containsCommaSeparatedTrimmed(iterator2.next(), charSequence2, bl)) continue;
            return false;
        }
        return true;
    }

    private static boolean containsCommaSeparatedTrimmed(CharSequence charSequence, CharSequence charSequence2, boolean bl) {
        int n = 0;
        if (bl) {
            int n2 = AsciiString.indexOf(charSequence, ',', n);
            if (n2 == -1) {
                if (AsciiString.contentEqualsIgnoreCase(AsciiString.trim(charSequence), charSequence2)) {
                    return false;
                }
            } else {
                do {
                    if (!AsciiString.contentEqualsIgnoreCase(AsciiString.trim(charSequence.subSequence(n, n2)), charSequence2)) continue;
                    return false;
                } while ((n2 = AsciiString.indexOf(charSequence, ',', n = n2 + 1)) != -1);
                if (n < charSequence.length() && AsciiString.contentEqualsIgnoreCase(AsciiString.trim(charSequence.subSequence(n, charSequence.length())), charSequence2)) {
                    return false;
                }
            }
        } else {
            int n3 = AsciiString.indexOf(charSequence, ',', n);
            if (n3 == -1) {
                if (AsciiString.contentEquals(AsciiString.trim(charSequence), charSequence2)) {
                    return false;
                }
            } else {
                do {
                    if (!AsciiString.contentEquals(AsciiString.trim(charSequence.subSequence(n, n3)), charSequence2)) continue;
                    return false;
                } while ((n3 = AsciiString.indexOf(charSequence, ',', n = n3 + 1)) != -1);
                if (n < charSequence.length() && AsciiString.contentEquals(AsciiString.trim(charSequence.subSequence(n, charSequence.length())), charSequence2)) {
                    return false;
                }
            }
        }
        return true;
    }

    public final String getAsString(CharSequence charSequence) {
        return this.get(charSequence);
    }

    public final List<String> getAllAsString(CharSequence charSequence) {
        return this.getAll(charSequence);
    }

    public final Iterator<Map.Entry<String, String>> iteratorAsString() {
        return this.iterator();
    }

    public boolean contains(CharSequence charSequence, CharSequence charSequence2, boolean bl) {
        return this.contains(charSequence.toString(), charSequence2.toString(), bl);
    }

    public String toString() {
        return HeadersUtils.toString(this.getClass(), this.iteratorCharSequence(), this.size());
    }

    public HttpHeaders copy() {
        return new DefaultHttpHeaders().set(this);
    }

    @Deprecated
    public static final class Values {
        public static final String APPLICATION_JSON = "application/json";
        public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
        public static final String BASE64 = "base64";
        public static final String BINARY = "binary";
        public static final String BOUNDARY = "boundary";
        public static final String BYTES = "bytes";
        public static final String CHARSET = "charset";
        public static final String CHUNKED = "chunked";
        public static final String CLOSE = "close";
        public static final String COMPRESS = "compress";
        public static final String CONTINUE = "100-continue";
        public static final String DEFLATE = "deflate";
        public static final String GZIP = "gzip";
        public static final String GZIP_DEFLATE = "gzip,deflate";
        public static final String IDENTITY = "identity";
        public static final String KEEP_ALIVE = "keep-alive";
        public static final String MAX_AGE = "max-age";
        public static final String MAX_STALE = "max-stale";
        public static final String MIN_FRESH = "min-fresh";
        public static final String MULTIPART_FORM_DATA = "multipart/form-data";
        public static final String MUST_REVALIDATE = "must-revalidate";
        public static final String NO_CACHE = "no-cache";
        public static final String NO_STORE = "no-store";
        public static final String NO_TRANSFORM = "no-transform";
        public static final String NONE = "none";
        public static final String ONLY_IF_CACHED = "only-if-cached";
        public static final String PRIVATE = "private";
        public static final String PROXY_REVALIDATE = "proxy-revalidate";
        public static final String PUBLIC = "public";
        public static final String QUOTED_PRINTABLE = "quoted-printable";
        public static final String S_MAXAGE = "s-maxage";
        public static final String TRAILERS = "trailers";
        public static final String UPGRADE = "Upgrade";
        public static final String WEBSOCKET = "WebSocket";

        private Values() {
        }
    }

    @Deprecated
    public static final class Names {
        public static final String ACCEPT = "Accept";
        public static final String ACCEPT_CHARSET = "Accept-Charset";
        public static final String ACCEPT_ENCODING = "Accept-Encoding";
        public static final String ACCEPT_LANGUAGE = "Accept-Language";
        public static final String ACCEPT_RANGES = "Accept-Ranges";
        public static final String ACCEPT_PATCH = "Accept-Patch";
        public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
        public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
        public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
        public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
        public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
        public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
        public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
        public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
        public static final String AGE = "Age";
        public static final String ALLOW = "Allow";
        public static final String AUTHORIZATION = "Authorization";
        public static final String CACHE_CONTROL = "Cache-Control";
        public static final String CONNECTION = "Connection";
        public static final String CONTENT_BASE = "Content-Base";
        public static final String CONTENT_ENCODING = "Content-Encoding";
        public static final String CONTENT_LANGUAGE = "Content-Language";
        public static final String CONTENT_LENGTH = "Content-Length";
        public static final String CONTENT_LOCATION = "Content-Location";
        public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
        public static final String CONTENT_MD5 = "Content-MD5";
        public static final String CONTENT_RANGE = "Content-Range";
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String COOKIE = "Cookie";
        public static final String DATE = "Date";
        public static final String ETAG = "ETag";
        public static final String EXPECT = "Expect";
        public static final String EXPIRES = "Expires";
        public static final String FROM = "From";
        public static final String HOST = "Host";
        public static final String IF_MATCH = "If-Match";
        public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
        public static final String IF_NONE_MATCH = "If-None-Match";
        public static final String IF_RANGE = "If-Range";
        public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
        public static final String LAST_MODIFIED = "Last-Modified";
        public static final String LOCATION = "Location";
        public static final String MAX_FORWARDS = "Max-Forwards";
        public static final String ORIGIN = "Origin";
        public static final String PRAGMA = "Pragma";
        public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";
        public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";
        public static final String RANGE = "Range";
        public static final String REFERER = "Referer";
        public static final String RETRY_AFTER = "Retry-After";
        public static final String SEC_WEBSOCKET_KEY1 = "Sec-WebSocket-Key1";
        public static final String SEC_WEBSOCKET_KEY2 = "Sec-WebSocket-Key2";
        public static final String SEC_WEBSOCKET_LOCATION = "Sec-WebSocket-Location";
        public static final String SEC_WEBSOCKET_ORIGIN = "Sec-WebSocket-Origin";
        public static final String SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";
        public static final String SEC_WEBSOCKET_VERSION = "Sec-WebSocket-Version";
        public static final String SEC_WEBSOCKET_KEY = "Sec-WebSocket-Key";
        public static final String SEC_WEBSOCKET_ACCEPT = "Sec-WebSocket-Accept";
        public static final String SERVER = "Server";
        public static final String SET_COOKIE = "Set-Cookie";
        public static final String SET_COOKIE2 = "Set-Cookie2";
        public static final String TE = "TE";
        public static final String TRAILER = "Trailer";
        public static final String TRANSFER_ENCODING = "Transfer-Encoding";
        public static final String UPGRADE = "Upgrade";
        public static final String USER_AGENT = "User-Agent";
        public static final String VARY = "Vary";
        public static final String VIA = "Via";
        public static final String WARNING = "Warning";
        public static final String WEBSOCKET_LOCATION = "WebSocket-Location";
        public static final String WEBSOCKET_ORIGIN = "WebSocket-Origin";
        public static final String WEBSOCKET_PROTOCOL = "WebSocket-Protocol";
        public static final String WWW_AUTHENTICATE = "WWW-Authenticate";

        private Names() {
        }
    }
}

