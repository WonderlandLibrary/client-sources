/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.UnsupportedValueConverter;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpScheme;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http2.CharSequenceMap;
import io.netty.handler.codec.http2.DefaultHttp2Headers;
import io.netty.handler.codec.http2.EmptyHttp2Headers;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.util.AsciiString;
import io.netty.util.ByteProcessor;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class HttpConversionUtil {
    private static final CharSequenceMap<AsciiString> HTTP_TO_HTTP2_HEADER_BLACKLIST = new CharSequenceMap();
    public static final HttpMethod OUT_OF_MESSAGE_SEQUENCE_METHOD;
    public static final String OUT_OF_MESSAGE_SEQUENCE_PATH = "";
    public static final HttpResponseStatus OUT_OF_MESSAGE_SEQUENCE_RETURN_CODE;
    private static final AsciiString EMPTY_REQUEST_PATH;

    private HttpConversionUtil() {
    }

    public static HttpResponseStatus parseStatus(CharSequence charSequence) throws Http2Exception {
        HttpResponseStatus httpResponseStatus;
        try {
            httpResponseStatus = HttpResponseStatus.parseLine(charSequence);
            if (httpResponseStatus == HttpResponseStatus.SWITCHING_PROTOCOLS) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Invalid HTTP/2 status code '%d'", httpResponseStatus.code());
            }
        } catch (Http2Exception http2Exception) {
            throw http2Exception;
        } catch (Throwable throwable) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, throwable, "Unrecognized HTTP status code '%s' encountered in translation to HTTP/1.x", charSequence);
        }
        return httpResponseStatus;
    }

    public static FullHttpResponse toFullHttpResponse(int n, Http2Headers http2Headers, ByteBufAllocator byteBufAllocator, boolean bl) throws Http2Exception {
        HttpResponseStatus httpResponseStatus = HttpConversionUtil.parseStatus(http2Headers.status());
        DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, httpResponseStatus, byteBufAllocator.buffer(), bl);
        try {
            HttpConversionUtil.addHttp2ToHttpHeaders(n, http2Headers, defaultFullHttpResponse, false);
        } catch (Http2Exception http2Exception) {
            defaultFullHttpResponse.release();
            throw http2Exception;
        } catch (Throwable throwable) {
            defaultFullHttpResponse.release();
            throw Http2Exception.streamError(n, Http2Error.PROTOCOL_ERROR, throwable, "HTTP/2 to HTTP/1.x headers conversion error", new Object[0]);
        }
        return defaultFullHttpResponse;
    }

    public static FullHttpRequest toFullHttpRequest(int n, Http2Headers http2Headers, ByteBufAllocator byteBufAllocator, boolean bl) throws Http2Exception {
        CharSequence charSequence = ObjectUtil.checkNotNull(http2Headers.method(), "method header cannot be null in conversion to HTTP/1.x");
        CharSequence charSequence2 = ObjectUtil.checkNotNull(http2Headers.path(), "path header cannot be null in conversion to HTTP/1.x");
        DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.valueOf(charSequence.toString()), charSequence2.toString(), byteBufAllocator.buffer(), bl);
        try {
            HttpConversionUtil.addHttp2ToHttpHeaders(n, http2Headers, defaultFullHttpRequest, false);
        } catch (Http2Exception http2Exception) {
            defaultFullHttpRequest.release();
            throw http2Exception;
        } catch (Throwable throwable) {
            defaultFullHttpRequest.release();
            throw Http2Exception.streamError(n, Http2Error.PROTOCOL_ERROR, throwable, "HTTP/2 to HTTP/1.x headers conversion error", new Object[0]);
        }
        return defaultFullHttpRequest;
    }

    public static HttpRequest toHttpRequest(int n, Http2Headers http2Headers, boolean bl) throws Http2Exception {
        CharSequence charSequence = ObjectUtil.checkNotNull(http2Headers.method(), "method header cannot be null in conversion to HTTP/1.x");
        CharSequence charSequence2 = ObjectUtil.checkNotNull(http2Headers.path(), "path header cannot be null in conversion to HTTP/1.x");
        DefaultHttpRequest defaultHttpRequest = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.valueOf(charSequence.toString()), charSequence2.toString(), bl);
        try {
            HttpConversionUtil.addHttp2ToHttpHeaders(n, http2Headers, defaultHttpRequest.headers(), defaultHttpRequest.protocolVersion(), false, true);
        } catch (Http2Exception http2Exception) {
            throw http2Exception;
        } catch (Throwable throwable) {
            throw Http2Exception.streamError(n, Http2Error.PROTOCOL_ERROR, throwable, "HTTP/2 to HTTP/1.x headers conversion error", new Object[0]);
        }
        return defaultHttpRequest;
    }

    public static HttpResponse toHttpResponse(int n, Http2Headers http2Headers, boolean bl) throws Http2Exception {
        HttpResponseStatus httpResponseStatus = HttpConversionUtil.parseStatus(http2Headers.status());
        DefaultHttpResponse defaultHttpResponse = new DefaultHttpResponse(HttpVersion.HTTP_1_1, httpResponseStatus, bl);
        try {
            HttpConversionUtil.addHttp2ToHttpHeaders(n, http2Headers, defaultHttpResponse.headers(), defaultHttpResponse.protocolVersion(), false, true);
        } catch (Http2Exception http2Exception) {
            throw http2Exception;
        } catch (Throwable throwable) {
            throw Http2Exception.streamError(n, Http2Error.PROTOCOL_ERROR, throwable, "HTTP/2 to HTTP/1.x headers conversion error", new Object[0]);
        }
        return defaultHttpResponse;
    }

    public static void addHttp2ToHttpHeaders(int n, Http2Headers http2Headers, FullHttpMessage fullHttpMessage, boolean bl) throws Http2Exception {
        HttpConversionUtil.addHttp2ToHttpHeaders(n, http2Headers, bl ? fullHttpMessage.trailingHeaders() : fullHttpMessage.headers(), fullHttpMessage.protocolVersion(), bl, fullHttpMessage instanceof HttpRequest);
    }

    public static void addHttp2ToHttpHeaders(int n, Http2Headers http2Headers, HttpHeaders httpHeaders, HttpVersion httpVersion, boolean bl, boolean bl2) throws Http2Exception {
        Http2ToHttpHeaderTranslator http2ToHttpHeaderTranslator = new Http2ToHttpHeaderTranslator(n, httpHeaders, bl2);
        try {
            for (Map.Entry<CharSequence, CharSequence> entry : http2Headers) {
                http2ToHttpHeaderTranslator.translate(entry);
            }
        } catch (Http2Exception http2Exception) {
            throw http2Exception;
        } catch (Throwable throwable) {
            throw Http2Exception.streamError(n, Http2Error.PROTOCOL_ERROR, throwable, "HTTP/2 to HTTP/1.x headers conversion error", new Object[0]);
        }
        httpHeaders.remove(HttpHeaderNames.TRANSFER_ENCODING);
        httpHeaders.remove(HttpHeaderNames.TRAILER);
        if (!bl) {
            httpHeaders.setInt(ExtensionHeaderNames.STREAM_ID.text(), n);
            HttpUtil.setKeepAlive(httpHeaders, httpVersion, true);
        }
    }

    public static Http2Headers toHttp2Headers(HttpMessage httpMessage, boolean bl) {
        HttpHeaders httpHeaders = httpMessage.headers();
        DefaultHttp2Headers defaultHttp2Headers = new DefaultHttp2Headers(bl, httpHeaders.size());
        if (httpMessage instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest)httpMessage;
            URI uRI = URI.create(httpRequest.uri());
            defaultHttp2Headers.path(HttpConversionUtil.toHttp2Path(uRI));
            defaultHttp2Headers.method(httpRequest.method().asciiName());
            HttpConversionUtil.setHttp2Scheme(httpHeaders, uRI, defaultHttp2Headers);
            if (!HttpUtil.isOriginForm(uRI) && !HttpUtil.isAsteriskForm(uRI)) {
                String string = httpHeaders.getAsString(HttpHeaderNames.HOST);
                HttpConversionUtil.setHttp2Authority(string == null || string.isEmpty() ? uRI.getAuthority() : string, defaultHttp2Headers);
            }
        } else if (httpMessage instanceof HttpResponse) {
            HttpResponse httpResponse = (HttpResponse)httpMessage;
            defaultHttp2Headers.status(httpResponse.status().codeAsText());
        }
        HttpConversionUtil.toHttp2Headers(httpHeaders, defaultHttp2Headers);
        return defaultHttp2Headers;
    }

    public static Http2Headers toHttp2Headers(HttpHeaders httpHeaders, boolean bl) {
        if (httpHeaders.isEmpty()) {
            return EmptyHttp2Headers.INSTANCE;
        }
        DefaultHttp2Headers defaultHttp2Headers = new DefaultHttp2Headers(bl, httpHeaders.size());
        HttpConversionUtil.toHttp2Headers(httpHeaders, defaultHttp2Headers);
        return defaultHttp2Headers;
    }

    private static CharSequenceMap<AsciiString> toLowercaseMap(Iterator<? extends CharSequence> iterator2, int n) {
        UnsupportedValueConverter unsupportedValueConverter = UnsupportedValueConverter.instance();
        CharSequenceMap<AsciiString> charSequenceMap = new CharSequenceMap<AsciiString>(true, unsupportedValueConverter, n);
        while (iterator2.hasNext()) {
            AsciiString asciiString = AsciiString.of(iterator2.next()).toLowerCase();
            try {
                int n2 = asciiString.forEachByte(ByteProcessor.FIND_COMMA);
                if (n2 != -1) {
                    int n3 = 0;
                    do {
                        charSequenceMap.add(asciiString.subSequence(n3, n2, true).trim(), AsciiString.EMPTY_STRING);
                    } while ((n3 = n2 + 1) < asciiString.length() && (n2 = asciiString.forEachByte(n3, asciiString.length() - n3, ByteProcessor.FIND_COMMA)) != -1);
                    charSequenceMap.add(asciiString.subSequence(n3, asciiString.length(), true).trim(), AsciiString.EMPTY_STRING);
                    continue;
                }
                charSequenceMap.add(asciiString.trim(), AsciiString.EMPTY_STRING);
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }
        return charSequenceMap;
    }

    private static void toHttp2HeadersFilterTE(Map.Entry<CharSequence, CharSequence> entry, Http2Headers http2Headers) {
        block2: {
            block1: {
                if (AsciiString.indexOf(entry.getValue(), ',', 0) != -1) break block1;
                if (!AsciiString.contentEqualsIgnoreCase(AsciiString.trim(entry.getValue()), HttpHeaderValues.TRAILERS)) break block2;
                http2Headers.add(HttpHeaderNames.TE, HttpHeaderValues.TRAILERS);
                break block2;
            }
            List<CharSequence> list = StringUtil.unescapeCsvFields(entry.getValue());
            for (CharSequence charSequence : list) {
                if (!AsciiString.contentEqualsIgnoreCase(AsciiString.trim(charSequence), HttpHeaderValues.TRAILERS)) continue;
                http2Headers.add(HttpHeaderNames.TE, HttpHeaderValues.TRAILERS);
                break;
            }
        }
    }

    public static void toHttp2Headers(HttpHeaders httpHeaders, Http2Headers http2Headers) {
        Iterator<Map.Entry<CharSequence, CharSequence>> iterator2 = httpHeaders.iteratorCharSequence();
        CharSequenceMap<AsciiString> charSequenceMap = HttpConversionUtil.toLowercaseMap(httpHeaders.valueCharSequenceIterator(HttpHeaderNames.CONNECTION), 8);
        while (iterator2.hasNext()) {
            Map.Entry<CharSequence, CharSequence> entry = iterator2.next();
            AsciiString asciiString = AsciiString.of(entry.getKey()).toLowerCase();
            if (HTTP_TO_HTTP2_HEADER_BLACKLIST.contains(asciiString) || charSequenceMap.contains(asciiString)) continue;
            if (asciiString.contentEqualsIgnoreCase(HttpHeaderNames.TE)) {
                HttpConversionUtil.toHttp2HeadersFilterTE(entry, http2Headers);
                continue;
            }
            if (asciiString.contentEqualsIgnoreCase(HttpHeaderNames.COOKIE)) {
                AsciiString asciiString2 = AsciiString.of(entry.getValue());
                try {
                    int n = asciiString2.forEachByte(ByteProcessor.FIND_SEMI_COLON);
                    if (n != -1) {
                        int n2 = 0;
                        do {
                            http2Headers.add(HttpHeaderNames.COOKIE, asciiString2.subSequence(n2, n, true));
                        } while ((n2 = n + 2) < asciiString2.length() && (n = asciiString2.forEachByte(n2, asciiString2.length() - n2, ByteProcessor.FIND_SEMI_COLON)) != -1);
                        if (n2 >= asciiString2.length()) {
                            throw new IllegalArgumentException("cookie value is of unexpected format: " + asciiString2);
                        }
                        http2Headers.add(HttpHeaderNames.COOKIE, asciiString2.subSequence(n2, asciiString2.length(), true));
                        continue;
                    }
                    http2Headers.add(HttpHeaderNames.COOKIE, asciiString2);
                    continue;
                } catch (Exception exception) {
                    throw new IllegalStateException(exception);
                }
            }
            http2Headers.add(asciiString, entry.getValue());
        }
    }

    private static AsciiString toHttp2Path(URI uRI) {
        String string;
        StringBuilder stringBuilder = new StringBuilder(StringUtil.length(uRI.getRawPath()) + StringUtil.length(uRI.getRawQuery()) + StringUtil.length(uRI.getRawFragment()) + 2);
        if (!StringUtil.isNullOrEmpty(uRI.getRawPath())) {
            stringBuilder.append(uRI.getRawPath());
        }
        if (!StringUtil.isNullOrEmpty(uRI.getRawQuery())) {
            stringBuilder.append('?');
            stringBuilder.append(uRI.getRawQuery());
        }
        if (!StringUtil.isNullOrEmpty(uRI.getRawFragment())) {
            stringBuilder.append('#');
            stringBuilder.append(uRI.getRawFragment());
        }
        return (string = stringBuilder.toString()).isEmpty() ? EMPTY_REQUEST_PATH : new AsciiString(string);
    }

    static void setHttp2Authority(String string, Http2Headers http2Headers) {
        if (string != null) {
            if (string.isEmpty()) {
                http2Headers.authority(AsciiString.EMPTY_STRING);
            } else {
                int n = string.indexOf(64) + 1;
                int n2 = string.length() - n;
                if (n2 == 0) {
                    throw new IllegalArgumentException("authority: " + string);
                }
                http2Headers.authority(new AsciiString(string, n, n2));
            }
        }
    }

    private static void setHttp2Scheme(HttpHeaders httpHeaders, URI uRI, Http2Headers http2Headers) {
        String string = uRI.getScheme();
        if (string != null) {
            http2Headers.scheme(new AsciiString(string));
            return;
        }
        String string2 = httpHeaders.get(ExtensionHeaderNames.SCHEME.text());
        if (string2 != null) {
            http2Headers.scheme(AsciiString.of(string2));
            return;
        }
        if (uRI.getPort() == HttpScheme.HTTPS.port()) {
            http2Headers.scheme(HttpScheme.HTTPS.name());
        } else if (uRI.getPort() == HttpScheme.HTTP.port()) {
            http2Headers.scheme(HttpScheme.HTTP.name());
        } else {
            throw new IllegalArgumentException(":scheme must be specified. see https://tools.ietf.org/html/rfc7540#section-8.1.2.3");
        }
    }

    static {
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(HttpHeaderNames.CONNECTION, AsciiString.EMPTY_STRING);
        AsciiString asciiString = HttpHeaderNames.KEEP_ALIVE;
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(asciiString, AsciiString.EMPTY_STRING);
        AsciiString asciiString2 = HttpHeaderNames.PROXY_CONNECTION;
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(asciiString2, AsciiString.EMPTY_STRING);
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(HttpHeaderNames.TRANSFER_ENCODING, AsciiString.EMPTY_STRING);
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(HttpHeaderNames.HOST, AsciiString.EMPTY_STRING);
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(HttpHeaderNames.UPGRADE, AsciiString.EMPTY_STRING);
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(ExtensionHeaderNames.STREAM_ID.text(), AsciiString.EMPTY_STRING);
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(ExtensionHeaderNames.SCHEME.text(), AsciiString.EMPTY_STRING);
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(ExtensionHeaderNames.PATH.text(), AsciiString.EMPTY_STRING);
        OUT_OF_MESSAGE_SEQUENCE_METHOD = HttpMethod.OPTIONS;
        OUT_OF_MESSAGE_SEQUENCE_RETURN_CODE = HttpResponseStatus.OK;
        EMPTY_REQUEST_PATH = AsciiString.cached("/");
    }

    private static final class Http2ToHttpHeaderTranslator {
        private static final CharSequenceMap<AsciiString> REQUEST_HEADER_TRANSLATIONS = new CharSequenceMap();
        private static final CharSequenceMap<AsciiString> RESPONSE_HEADER_TRANSLATIONS = new CharSequenceMap();
        private final int streamId;
        private final HttpHeaders output;
        private final CharSequenceMap<AsciiString> translations;

        Http2ToHttpHeaderTranslator(int n, HttpHeaders httpHeaders, boolean bl) {
            this.streamId = n;
            this.output = httpHeaders;
            this.translations = bl ? REQUEST_HEADER_TRANSLATIONS : RESPONSE_HEADER_TRANSLATIONS;
        }

        public void translate(Map.Entry<CharSequence, CharSequence> entry) throws Http2Exception {
            CharSequence charSequence = entry.getKey();
            CharSequence charSequence2 = entry.getValue();
            AsciiString asciiString = (AsciiString)this.translations.get(charSequence);
            if (asciiString != null) {
                this.output.add((CharSequence)asciiString, (Object)AsciiString.of(charSequence2));
            } else if (!Http2Headers.PseudoHeaderName.isPseudoHeader(charSequence)) {
                if (charSequence.length() == 0 || charSequence.charAt(0) == ':') {
                    throw Http2Exception.streamError(this.streamId, Http2Error.PROTOCOL_ERROR, "Invalid HTTP/2 header '%s' encountered in translation to HTTP/1.x", charSequence);
                }
                if (HttpHeaderNames.COOKIE.equals(charSequence)) {
                    String string = this.output.get(HttpHeaderNames.COOKIE);
                    this.output.set((CharSequence)HttpHeaderNames.COOKIE, (Object)(string != null ? string + "; " + charSequence2 : charSequence2));
                } else {
                    this.output.add(charSequence, (Object)charSequence2);
                }
            }
        }

        static {
            RESPONSE_HEADER_TRANSLATIONS.add(Http2Headers.PseudoHeaderName.AUTHORITY.value(), HttpHeaderNames.HOST);
            RESPONSE_HEADER_TRANSLATIONS.add(Http2Headers.PseudoHeaderName.SCHEME.value(), ExtensionHeaderNames.SCHEME.text());
            REQUEST_HEADER_TRANSLATIONS.add(RESPONSE_HEADER_TRANSLATIONS);
            RESPONSE_HEADER_TRANSLATIONS.add(Http2Headers.PseudoHeaderName.PATH.value(), ExtensionHeaderNames.PATH.text());
        }
    }

    public static enum ExtensionHeaderNames {
        STREAM_ID("x-http2-stream-id"),
        SCHEME("x-http2-scheme"),
        PATH("x-http2-path"),
        STREAM_PROMISE_ID("x-http2-stream-promise-id"),
        STREAM_DEPENDENCY_ID("x-http2-stream-dependency-id"),
        STREAM_WEIGHT("x-http2-stream-weight");

        private final AsciiString text;

        private ExtensionHeaderNames(String string2) {
            this.text = AsciiString.cached(string2);
        }

        public AsciiString text() {
            return this.text;
        }
    }
}

