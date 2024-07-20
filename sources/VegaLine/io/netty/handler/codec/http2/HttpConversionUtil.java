/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpRequest;
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
import java.util.Map;

public final class HttpConversionUtil {
    private static final CharSequenceMap<AsciiString> HTTP_TO_HTTP2_HEADER_BLACKLIST = new CharSequenceMap();
    public static final HttpMethod OUT_OF_MESSAGE_SEQUENCE_METHOD;
    public static final String OUT_OF_MESSAGE_SEQUENCE_PATH = "";
    public static final HttpResponseStatus OUT_OF_MESSAGE_SEQUENCE_RETURN_CODE;
    private static final AsciiString EMPTY_REQUEST_PATH;

    private HttpConversionUtil() {
    }

    public static HttpResponseStatus parseStatus(CharSequence status) throws Http2Exception {
        HttpResponseStatus result;
        try {
            result = HttpResponseStatus.parseLine(status);
            if (result == HttpResponseStatus.SWITCHING_PROTOCOLS) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Invalid HTTP/2 status code '%d'", result.code());
            }
        } catch (Http2Exception e) {
            throw e;
        } catch (Throwable t) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, t, "Unrecognized HTTP status code '%s' encountered in translation to HTTP/1.x", status);
        }
        return result;
    }

    public static FullHttpResponse toHttpResponse(int streamId, Http2Headers http2Headers, ByteBufAllocator alloc, boolean validateHttpHeaders) throws Http2Exception {
        HttpResponseStatus status = HttpConversionUtil.parseStatus(http2Headers.status());
        DefaultFullHttpResponse msg = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, alloc.buffer(), validateHttpHeaders);
        try {
            HttpConversionUtil.addHttp2ToHttpHeaders(streamId, http2Headers, msg, false);
        } catch (Http2Exception e) {
            msg.release();
            throw e;
        } catch (Throwable t) {
            msg.release();
            throw Http2Exception.streamError(streamId, Http2Error.PROTOCOL_ERROR, t, "HTTP/2 to HTTP/1.x headers conversion error", new Object[0]);
        }
        return msg;
    }

    public static FullHttpRequest toFullHttpRequest(int streamId, Http2Headers http2Headers, ByteBufAllocator alloc, boolean validateHttpHeaders) throws Http2Exception {
        CharSequence method = ObjectUtil.checkNotNull(http2Headers.method(), "method header cannot be null in conversion to HTTP/1.x");
        CharSequence path = ObjectUtil.checkNotNull(http2Headers.path(), "path header cannot be null in conversion to HTTP/1.x");
        DefaultFullHttpRequest msg = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.valueOf(method.toString()), path.toString(), alloc.buffer(), validateHttpHeaders);
        try {
            HttpConversionUtil.addHttp2ToHttpHeaders(streamId, http2Headers, msg, false);
        } catch (Http2Exception e) {
            msg.release();
            throw e;
        } catch (Throwable t) {
            msg.release();
            throw Http2Exception.streamError(streamId, Http2Error.PROTOCOL_ERROR, t, "HTTP/2 to HTTP/1.x headers conversion error", new Object[0]);
        }
        return msg;
    }

    public static HttpRequest toHttpRequest(int streamId, Http2Headers http2Headers, boolean validateHttpHeaders) throws Http2Exception {
        CharSequence method = ObjectUtil.checkNotNull(http2Headers.method(), "method header cannot be null in conversion to HTTP/1.x");
        CharSequence path = ObjectUtil.checkNotNull(http2Headers.path(), "path header cannot be null in conversion to HTTP/1.x");
        DefaultHttpRequest msg = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.valueOf(method.toString()), path.toString(), validateHttpHeaders);
        try {
            HttpConversionUtil.addHttp2ToHttpHeaders(streamId, http2Headers, msg.headers(), msg.protocolVersion(), false, true);
        } catch (Http2Exception e) {
            throw e;
        } catch (Throwable t) {
            throw Http2Exception.streamError(streamId, Http2Error.PROTOCOL_ERROR, t, "HTTP/2 to HTTP/1.x headers conversion error", new Object[0]);
        }
        return msg;
    }

    public static void addHttp2ToHttpHeaders(int streamId, Http2Headers sourceHeaders, FullHttpMessage destinationMessage, boolean addToTrailer) throws Http2Exception {
        HttpConversionUtil.addHttp2ToHttpHeaders(streamId, sourceHeaders, addToTrailer ? destinationMessage.trailingHeaders() : destinationMessage.headers(), destinationMessage.protocolVersion(), addToTrailer, destinationMessage instanceof HttpRequest);
    }

    public static void addHttp2ToHttpHeaders(int streamId, Http2Headers inputHeaders, HttpHeaders outputHeaders, HttpVersion httpVersion, boolean isTrailer, boolean isRequest) throws Http2Exception {
        Http2ToHttpHeaderTranslator translator = new Http2ToHttpHeaderTranslator(streamId, outputHeaders, isRequest);
        try {
            for (Map.Entry<CharSequence, CharSequence> entry : inputHeaders) {
                translator.translate(entry);
            }
        } catch (Http2Exception ex) {
            throw ex;
        } catch (Throwable t) {
            throw Http2Exception.streamError(streamId, Http2Error.PROTOCOL_ERROR, t, "HTTP/2 to HTTP/1.x headers conversion error", new Object[0]);
        }
        outputHeaders.remove(HttpHeaderNames.TRANSFER_ENCODING);
        outputHeaders.remove(HttpHeaderNames.TRAILER);
        if (!isTrailer) {
            outputHeaders.setInt(ExtensionHeaderNames.STREAM_ID.text(), streamId);
            HttpUtil.setKeepAlive(outputHeaders, httpVersion, true);
        }
    }

    public static Http2Headers toHttp2Headers(HttpMessage in, boolean validateHeaders) {
        HttpHeaders inHeaders = in.headers();
        DefaultHttp2Headers out = new DefaultHttp2Headers(validateHeaders, inHeaders.size());
        if (in instanceof HttpRequest) {
            HttpRequest request = (HttpRequest)in;
            URI requestTargetUri = URI.create(request.uri());
            out.path(HttpConversionUtil.toHttp2Path(requestTargetUri));
            out.method(request.method().asciiName());
            HttpConversionUtil.setHttp2Scheme(inHeaders, requestTargetUri, out);
            if (!HttpUtil.isOriginForm(requestTargetUri) && !HttpUtil.isAsteriskForm(requestTargetUri)) {
                String host = inHeaders.getAsString(HttpHeaderNames.HOST);
                HttpConversionUtil.setHttp2Authority(host == null || host.isEmpty() ? requestTargetUri.getAuthority() : host, out);
            }
        } else if (in instanceof HttpResponse) {
            HttpResponse response = (HttpResponse)in;
            out.status(new AsciiString(Integer.toString(response.status().code())));
        }
        HttpConversionUtil.toHttp2Headers(inHeaders, out);
        return out;
    }

    public static Http2Headers toHttp2Headers(HttpHeaders inHeaders, boolean validateHeaders) {
        if (inHeaders.isEmpty()) {
            return EmptyHttp2Headers.INSTANCE;
        }
        DefaultHttp2Headers out = new DefaultHttp2Headers(validateHeaders, inHeaders.size());
        HttpConversionUtil.toHttp2Headers(inHeaders, out);
        return out;
    }

    public static void toHttp2Headers(HttpHeaders inHeaders, Http2Headers out) {
        Iterator<Map.Entry<CharSequence, CharSequence>> iter = inHeaders.iteratorCharSequence();
        while (iter.hasNext()) {
            Map.Entry<CharSequence, CharSequence> entry = iter.next();
            AsciiString aName = AsciiString.of(entry.getKey()).toLowerCase();
            if (HTTP_TO_HTTP2_HEADER_BLACKLIST.contains(aName)) continue;
            if (aName.contentEqualsIgnoreCase(HttpHeaderNames.TE) && !AsciiString.contentEqualsIgnoreCase(entry.getValue(), HttpHeaderValues.TRAILERS)) {
                throw new IllegalArgumentException("Invalid value for " + HttpHeaderNames.TE + ": " + entry.getValue());
            }
            if (aName.contentEqualsIgnoreCase(HttpHeaderNames.COOKIE)) {
                AsciiString value = AsciiString.of(entry.getValue());
                try {
                    int index = value.forEachByte(ByteProcessor.FIND_SEMI_COLON);
                    if (index != -1) {
                        int start = 0;
                        do {
                            out.add(HttpHeaderNames.COOKIE, value.subSequence(start, index, false));
                        } while ((start = index + 2) < value.length() && (index = value.forEachByte(start, value.length() - start, ByteProcessor.FIND_SEMI_COLON)) != -1);
                        if (start >= value.length()) {
                            throw new IllegalArgumentException("cookie value is of unexpected format: " + value);
                        }
                        out.add(HttpHeaderNames.COOKIE, value.subSequence(start, value.length(), false));
                        continue;
                    }
                    out.add(HttpHeaderNames.COOKIE, value);
                    continue;
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
            out.add(aName, entry.getValue());
        }
    }

    private static AsciiString toHttp2Path(URI uri) {
        String path;
        StringBuilder pathBuilder = new StringBuilder(StringUtil.length(uri.getRawPath()) + StringUtil.length(uri.getRawQuery()) + StringUtil.length(uri.getRawFragment()) + 2);
        if (!StringUtil.isNullOrEmpty(uri.getRawPath())) {
            pathBuilder.append(uri.getRawPath());
        }
        if (!StringUtil.isNullOrEmpty(uri.getRawQuery())) {
            pathBuilder.append('?');
            pathBuilder.append(uri.getRawQuery());
        }
        if (!StringUtil.isNullOrEmpty(uri.getRawFragment())) {
            pathBuilder.append('#');
            pathBuilder.append(uri.getRawFragment());
        }
        return (path = pathBuilder.toString()).isEmpty() ? EMPTY_REQUEST_PATH : new AsciiString(path);
    }

    private static void setHttp2Authority(String autority, Http2Headers out) {
        if (autority != null) {
            int endOfUserInfo = autority.indexOf(64);
            if (endOfUserInfo < 0) {
                out.authority(new AsciiString(autority));
            } else if (endOfUserInfo + 1 < autority.length()) {
                out.authority(new AsciiString(autority.substring(endOfUserInfo + 1)));
            } else {
                throw new IllegalArgumentException("autority: " + autority);
            }
        }
    }

    private static void setHttp2Scheme(HttpHeaders in, URI uri, Http2Headers out) {
        String value = uri.getScheme();
        if (value != null) {
            out.scheme(new AsciiString(value));
            return;
        }
        String cValue = in.get(ExtensionHeaderNames.SCHEME.text());
        if (cValue != null) {
            out.scheme(AsciiString.of(cValue));
            return;
        }
        if (uri.getPort() == HttpScheme.HTTPS.port()) {
            out.scheme(HttpScheme.HTTPS.name());
        } else if (uri.getPort() == HttpScheme.HTTP.port()) {
            out.scheme(HttpScheme.HTTP.name());
        } else {
            throw new IllegalArgumentException(":scheme must be specified. see https://tools.ietf.org/html/rfc7540#section-8.1.2.3");
        }
    }

    static {
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(HttpHeaderNames.CONNECTION, AsciiString.EMPTY_STRING);
        AsciiString keepAlive = HttpHeaderNames.KEEP_ALIVE;
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(keepAlive, AsciiString.EMPTY_STRING);
        AsciiString proxyConnection = HttpHeaderNames.PROXY_CONNECTION;
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(proxyConnection, AsciiString.EMPTY_STRING);
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(HttpHeaderNames.TRANSFER_ENCODING, AsciiString.EMPTY_STRING);
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(HttpHeaderNames.HOST, AsciiString.EMPTY_STRING);
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(HttpHeaderNames.UPGRADE, AsciiString.EMPTY_STRING);
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(ExtensionHeaderNames.STREAM_ID.text(), AsciiString.EMPTY_STRING);
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(ExtensionHeaderNames.SCHEME.text(), AsciiString.EMPTY_STRING);
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(ExtensionHeaderNames.PATH.text(), AsciiString.EMPTY_STRING);
        OUT_OF_MESSAGE_SEQUENCE_METHOD = HttpMethod.OPTIONS;
        OUT_OF_MESSAGE_SEQUENCE_RETURN_CODE = HttpResponseStatus.OK;
        EMPTY_REQUEST_PATH = new AsciiString("/");
    }

    private static final class Http2ToHttpHeaderTranslator {
        private static final CharSequenceMap<AsciiString> REQUEST_HEADER_TRANSLATIONS = new CharSequenceMap();
        private static final CharSequenceMap<AsciiString> RESPONSE_HEADER_TRANSLATIONS = new CharSequenceMap();
        private final int streamId;
        private final HttpHeaders output;
        private final CharSequenceMap<AsciiString> translations;

        Http2ToHttpHeaderTranslator(int streamId, HttpHeaders output, boolean request) {
            this.streamId = streamId;
            this.output = output;
            this.translations = request ? REQUEST_HEADER_TRANSLATIONS : RESPONSE_HEADER_TRANSLATIONS;
        }

        public void translate(Map.Entry<CharSequence, CharSequence> entry) throws Http2Exception {
            CharSequence name = entry.getKey();
            CharSequence value = entry.getValue();
            AsciiString translatedName = (AsciiString)this.translations.get(name);
            if (translatedName != null) {
                this.output.add((CharSequence)translatedName, (Object)AsciiString.of(value));
            } else if (!Http2Headers.PseudoHeaderName.isPseudoHeader(name)) {
                if (name.length() == 0 || name.charAt(0) == ':') {
                    throw Http2Exception.streamError(this.streamId, Http2Error.PROTOCOL_ERROR, "Invalid HTTP/2 header '%s' encountered in translation to HTTP/1.x", name);
                }
                if (HttpHeaderNames.COOKIE.equals(name)) {
                    String existingCookie = this.output.get(HttpHeaderNames.COOKIE);
                    this.output.set((CharSequence)HttpHeaderNames.COOKIE, (Object)(existingCookie != null ? existingCookie + "; " + value : value));
                } else {
                    this.output.add(name, (Object)value);
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

        private ExtensionHeaderNames(String text) {
            this.text = new AsciiString(text);
        }

        public AsciiString text() {
            return this.text;
        }
    }
}

