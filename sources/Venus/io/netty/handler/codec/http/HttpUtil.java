/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import io.netty.util.NetUtil;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class HttpUtil {
    private static final AsciiString CHARSET_EQUALS = AsciiString.of(HttpHeaderValues.CHARSET + "=");
    private static final AsciiString SEMICOLON = AsciiString.cached(";");

    private HttpUtil() {
    }

    public static boolean isOriginForm(URI uRI) {
        return uRI.getScheme() == null && uRI.getSchemeSpecificPart() == null && uRI.getHost() == null && uRI.getAuthority() == null;
    }

    public static boolean isAsteriskForm(URI uRI) {
        return "*".equals(uRI.getPath()) && uRI.getScheme() == null && uRI.getSchemeSpecificPart() == null && uRI.getHost() == null && uRI.getAuthority() == null && uRI.getQuery() == null && uRI.getFragment() == null;
    }

    public static boolean isKeepAlive(HttpMessage httpMessage) {
        String string = httpMessage.headers().get(HttpHeaderNames.CONNECTION);
        if (string != null && HttpHeaderValues.CLOSE.contentEqualsIgnoreCase(string)) {
            return true;
        }
        if (httpMessage.protocolVersion().isKeepAliveDefault()) {
            return !HttpHeaderValues.CLOSE.contentEqualsIgnoreCase(string);
        }
        return HttpHeaderValues.KEEP_ALIVE.contentEqualsIgnoreCase(string);
    }

    public static void setKeepAlive(HttpMessage httpMessage, boolean bl) {
        HttpUtil.setKeepAlive(httpMessage.headers(), httpMessage.protocolVersion(), bl);
    }

    public static void setKeepAlive(HttpHeaders httpHeaders, HttpVersion httpVersion, boolean bl) {
        if (httpVersion.isKeepAliveDefault()) {
            if (bl) {
                httpHeaders.remove(HttpHeaderNames.CONNECTION);
            } else {
                httpHeaders.set((CharSequence)HttpHeaderNames.CONNECTION, (Object)HttpHeaderValues.CLOSE);
            }
        } else if (bl) {
            httpHeaders.set((CharSequence)HttpHeaderNames.CONNECTION, (Object)HttpHeaderValues.KEEP_ALIVE);
        } else {
            httpHeaders.remove(HttpHeaderNames.CONNECTION);
        }
    }

    public static long getContentLength(HttpMessage httpMessage) {
        String string = httpMessage.headers().get(HttpHeaderNames.CONTENT_LENGTH);
        if (string != null) {
            return Long.parseLong(string);
        }
        long l = HttpUtil.getWebSocketContentLength(httpMessage);
        if (l >= 0L) {
            return l;
        }
        throw new NumberFormatException("header not found: " + HttpHeaderNames.CONTENT_LENGTH);
    }

    public static long getContentLength(HttpMessage httpMessage, long l) {
        String string = httpMessage.headers().get(HttpHeaderNames.CONTENT_LENGTH);
        if (string != null) {
            return Long.parseLong(string);
        }
        long l2 = HttpUtil.getWebSocketContentLength(httpMessage);
        if (l2 >= 0L) {
            return l2;
        }
        return l;
    }

    public static int getContentLength(HttpMessage httpMessage, int n) {
        return (int)Math.min(Integer.MAX_VALUE, HttpUtil.getContentLength(httpMessage, (long)n));
    }

    private static int getWebSocketContentLength(HttpMessage httpMessage) {
        HttpResponse httpResponse;
        HttpRequest httpRequest;
        HttpHeaders httpHeaders = httpMessage.headers();
        if (httpMessage instanceof HttpRequest ? HttpMethod.GET.equals((httpRequest = (HttpRequest)httpMessage).method()) && httpHeaders.contains(HttpHeaderNames.SEC_WEBSOCKET_KEY1) && httpHeaders.contains(HttpHeaderNames.SEC_WEBSOCKET_KEY2) : httpMessage instanceof HttpResponse && (httpResponse = (HttpResponse)httpMessage).status().code() == 101 && httpHeaders.contains(HttpHeaderNames.SEC_WEBSOCKET_ORIGIN) && httpHeaders.contains(HttpHeaderNames.SEC_WEBSOCKET_LOCATION)) {
            return 1;
        }
        return 1;
    }

    public static void setContentLength(HttpMessage httpMessage, long l) {
        httpMessage.headers().set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, (Object)l);
    }

    public static boolean isContentLengthSet(HttpMessage httpMessage) {
        return httpMessage.headers().contains(HttpHeaderNames.CONTENT_LENGTH);
    }

    public static boolean is100ContinueExpected(HttpMessage httpMessage) {
        if (!HttpUtil.isExpectHeaderValid(httpMessage)) {
            return true;
        }
        String string = httpMessage.headers().get(HttpHeaderNames.EXPECT);
        return HttpHeaderValues.CONTINUE.toString().equalsIgnoreCase(string);
    }

    static boolean isUnsupportedExpectation(HttpMessage httpMessage) {
        if (!HttpUtil.isExpectHeaderValid(httpMessage)) {
            return true;
        }
        String string = httpMessage.headers().get(HttpHeaderNames.EXPECT);
        return string != null && !HttpHeaderValues.CONTINUE.toString().equalsIgnoreCase(string);
    }

    private static boolean isExpectHeaderValid(HttpMessage httpMessage) {
        return httpMessage instanceof HttpRequest && httpMessage.protocolVersion().compareTo(HttpVersion.HTTP_1_1) >= 0;
    }

    public static void set100ContinueExpected(HttpMessage httpMessage, boolean bl) {
        if (bl) {
            httpMessage.headers().set((CharSequence)HttpHeaderNames.EXPECT, (Object)HttpHeaderValues.CONTINUE);
        } else {
            httpMessage.headers().remove(HttpHeaderNames.EXPECT);
        }
    }

    public static boolean isTransferEncodingChunked(HttpMessage httpMessage) {
        return httpMessage.headers().contains(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED, false);
    }

    public static void setTransferEncodingChunked(HttpMessage httpMessage, boolean bl) {
        if (bl) {
            httpMessage.headers().set((CharSequence)HttpHeaderNames.TRANSFER_ENCODING, (Object)HttpHeaderValues.CHUNKED);
            httpMessage.headers().remove(HttpHeaderNames.CONTENT_LENGTH);
        } else {
            List<String> list = httpMessage.headers().getAll(HttpHeaderNames.TRANSFER_ENCODING);
            if (list.isEmpty()) {
                return;
            }
            ArrayList<String> arrayList = new ArrayList<String>(list);
            Iterator iterator2 = arrayList.iterator();
            while (iterator2.hasNext()) {
                CharSequence charSequence = (CharSequence)iterator2.next();
                if (!HttpHeaderValues.CHUNKED.contentEqualsIgnoreCase(charSequence)) continue;
                iterator2.remove();
            }
            if (arrayList.isEmpty()) {
                httpMessage.headers().remove(HttpHeaderNames.TRANSFER_ENCODING);
            } else {
                httpMessage.headers().set((CharSequence)HttpHeaderNames.TRANSFER_ENCODING, arrayList);
            }
        }
    }

    public static Charset getCharset(HttpMessage httpMessage) {
        return HttpUtil.getCharset(httpMessage, CharsetUtil.ISO_8859_1);
    }

    public static Charset getCharset(CharSequence charSequence) {
        if (charSequence != null) {
            return HttpUtil.getCharset(charSequence, CharsetUtil.ISO_8859_1);
        }
        return CharsetUtil.ISO_8859_1;
    }

    public static Charset getCharset(HttpMessage httpMessage, Charset charset) {
        String string = httpMessage.headers().get(HttpHeaderNames.CONTENT_TYPE);
        if (string != null) {
            return HttpUtil.getCharset(string, charset);
        }
        return charset;
    }

    public static Charset getCharset(CharSequence charSequence, Charset charset) {
        if (charSequence != null) {
            CharSequence charSequence2 = HttpUtil.getCharsetAsSequence(charSequence);
            if (charSequence2 != null) {
                try {
                    return Charset.forName(charSequence2.toString());
                } catch (UnsupportedCharsetException unsupportedCharsetException) {
                    return charset;
                }
            }
            return charset;
        }
        return charset;
    }

    @Deprecated
    public static CharSequence getCharsetAsString(HttpMessage httpMessage) {
        return HttpUtil.getCharsetAsSequence(httpMessage);
    }

    public static CharSequence getCharsetAsSequence(HttpMessage httpMessage) {
        String string = httpMessage.headers().get(HttpHeaderNames.CONTENT_TYPE);
        if (string != null) {
            return HttpUtil.getCharsetAsSequence(string);
        }
        return null;
    }

    public static CharSequence getCharsetAsSequence(CharSequence charSequence) {
        int n;
        if (charSequence == null) {
            throw new NullPointerException("contentTypeValue");
        }
        int n2 = AsciiString.indexOfIgnoreCaseAscii(charSequence, CHARSET_EQUALS, 0);
        if (n2 != -1 && (n = n2 + CHARSET_EQUALS.length()) < charSequence.length()) {
            return charSequence.subSequence(n, charSequence.length());
        }
        return null;
    }

    public static CharSequence getMimeType(HttpMessage httpMessage) {
        String string = httpMessage.headers().get(HttpHeaderNames.CONTENT_TYPE);
        if (string != null) {
            return HttpUtil.getMimeType(string);
        }
        return null;
    }

    public static CharSequence getMimeType(CharSequence charSequence) {
        if (charSequence == null) {
            throw new NullPointerException("contentTypeValue");
        }
        int n = AsciiString.indexOfIgnoreCaseAscii(charSequence, SEMICOLON, 0);
        if (n != -1) {
            return charSequence.subSequence(0, n);
        }
        return charSequence.length() > 0 ? charSequence : null;
    }

    public static String formatHostnameForHttp(InetSocketAddress inetSocketAddress) {
        String string = NetUtil.getHostname(inetSocketAddress);
        if (NetUtil.isValidIpV6Address(string)) {
            if (!inetSocketAddress.isUnresolved()) {
                string = NetUtil.toAddressString(inetSocketAddress.getAddress());
            }
            return "[" + string + "]";
        }
        return string;
    }
}

