/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.multipart;

import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostBodyUtil;
import io.netty.handler.codec.http.multipart.HttpPostMultipartRequestDecoder;
import io.netty.handler.codec.http.multipart.HttpPostStandardRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder;
import io.netty.util.internal.StringUtil;
import java.nio.charset.Charset;
import java.util.List;

public class HttpPostRequestDecoder
implements InterfaceHttpPostRequestDecoder {
    static final int DEFAULT_DISCARD_THRESHOLD = 0xA00000;
    private final InterfaceHttpPostRequestDecoder decoder;

    public HttpPostRequestDecoder(HttpRequest httpRequest) {
        this(new DefaultHttpDataFactory(16384L), httpRequest, HttpConstants.DEFAULT_CHARSET);
    }

    public HttpPostRequestDecoder(HttpDataFactory httpDataFactory, HttpRequest httpRequest) {
        this(httpDataFactory, httpRequest, HttpConstants.DEFAULT_CHARSET);
    }

    public HttpPostRequestDecoder(HttpDataFactory httpDataFactory, HttpRequest httpRequest, Charset charset) {
        if (httpDataFactory == null) {
            throw new NullPointerException("factory");
        }
        if (httpRequest == null) {
            throw new NullPointerException("request");
        }
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.decoder = HttpPostRequestDecoder.isMultipart(httpRequest) ? new HttpPostMultipartRequestDecoder(httpDataFactory, httpRequest, charset) : new HttpPostStandardRequestDecoder(httpDataFactory, httpRequest, charset);
    }

    public static boolean isMultipart(HttpRequest httpRequest) {
        if (httpRequest.headers().contains(HttpHeaderNames.CONTENT_TYPE)) {
            return HttpPostRequestDecoder.getMultipartDataBoundary(httpRequest.headers().get(HttpHeaderNames.CONTENT_TYPE)) != null;
        }
        return true;
    }

    protected static String[] getMultipartDataBoundary(String string) {
        String string2;
        String[] stringArray = HttpPostRequestDecoder.splitHeaderContentType(string);
        if (stringArray[0].regionMatches(true, 0, string2 = HttpHeaderValues.MULTIPART_FORM_DATA.toString(), 0, string2.length())) {
            String string3;
            int n;
            String string4;
            int n2;
            int n3;
            String string5 = HttpHeaderValues.BOUNDARY.toString();
            if (stringArray[5].regionMatches(true, 0, string5, 0, string5.length())) {
                n3 = 1;
                n2 = 2;
            } else if (stringArray[5].regionMatches(true, 0, string5, 0, string5.length())) {
                n3 = 2;
                n2 = 1;
            } else {
                return null;
            }
            String string6 = StringUtil.substringAfter(stringArray[n3], '=');
            if (string6 == null) {
                throw new ErrorDataDecoderException("Needs a boundary value");
            }
            if (string6.charAt(0) == '\"' && (string4 = string6.trim()).charAt(n = string4.length() - 1) == '\"') {
                string6 = string4.substring(1, n);
            }
            if (stringArray[n2].regionMatches(true, 0, string4 = HttpHeaderValues.CHARSET.toString(), 0, string4.length()) && (string3 = StringUtil.substringAfter(stringArray[n2], '=')) != null) {
                return new String[]{"--" + string6, string3};
            }
            return new String[]{"--" + string6};
        }
        return null;
    }

    @Override
    public boolean isMultipart() {
        return this.decoder.isMultipart();
    }

    @Override
    public void setDiscardThreshold(int n) {
        this.decoder.setDiscardThreshold(n);
    }

    @Override
    public int getDiscardThreshold() {
        return this.decoder.getDiscardThreshold();
    }

    @Override
    public List<InterfaceHttpData> getBodyHttpDatas() {
        return this.decoder.getBodyHttpDatas();
    }

    @Override
    public List<InterfaceHttpData> getBodyHttpDatas(String string) {
        return this.decoder.getBodyHttpDatas(string);
    }

    @Override
    public InterfaceHttpData getBodyHttpData(String string) {
        return this.decoder.getBodyHttpData(string);
    }

    @Override
    public InterfaceHttpPostRequestDecoder offer(HttpContent httpContent) {
        return this.decoder.offer(httpContent);
    }

    @Override
    public boolean hasNext() {
        return this.decoder.hasNext();
    }

    @Override
    public InterfaceHttpData next() {
        return this.decoder.next();
    }

    @Override
    public InterfaceHttpData currentPartialHttpData() {
        return this.decoder.currentPartialHttpData();
    }

    @Override
    public void destroy() {
        this.decoder.destroy();
    }

    @Override
    public void cleanFiles() {
        this.decoder.cleanFiles();
    }

    @Override
    public void removeHttpDataFromClean(InterfaceHttpData interfaceHttpData) {
        this.decoder.removeHttpDataFromClean(interfaceHttpData);
    }

    private static String[] splitHeaderContentType(String string) {
        int n;
        int n2 = HttpPostBodyUtil.findNonWhitespace(string, 0);
        int n3 = string.indexOf(59);
        if (n3 == -1) {
            return new String[]{string, "", ""};
        }
        int n4 = HttpPostBodyUtil.findNonWhitespace(string, n3 + 1);
        if (string.charAt(n3 - 1) == ' ') {
            --n3;
        }
        if ((n = string.indexOf(59, n4)) == -1) {
            n = HttpPostBodyUtil.findEndOfString(string);
            return new String[]{string.substring(n2, n3), string.substring(n4, n), ""};
        }
        int n5 = HttpPostBodyUtil.findNonWhitespace(string, n + 1);
        if (string.charAt(n - 1) == ' ') {
            --n;
        }
        int n6 = HttpPostBodyUtil.findEndOfString(string);
        return new String[]{string.substring(n2, n3), string.substring(n4, n), string.substring(n5, n6)};
    }

    public static class ErrorDataDecoderException
    extends DecoderException {
        private static final long serialVersionUID = 5020247425493164465L;

        public ErrorDataDecoderException() {
        }

        public ErrorDataDecoderException(String string) {
            super(string);
        }

        public ErrorDataDecoderException(Throwable throwable) {
            super(throwable);
        }

        public ErrorDataDecoderException(String string, Throwable throwable) {
            super(string, throwable);
        }
    }

    public static class EndOfDataDecoderException
    extends DecoderException {
        private static final long serialVersionUID = 1336267941020800769L;
    }

    public static class NotEnoughDataDecoderException
    extends DecoderException {
        private static final long serialVersionUID = -7846841864603865638L;

        public NotEnoughDataDecoderException() {
        }

        public NotEnoughDataDecoderException(String string) {
            super(string);
        }

        public NotEnoughDataDecoderException(Throwable throwable) {
            super(throwable);
        }

        public NotEnoughDataDecoderException(String string, Throwable throwable) {
            super(string, throwable);
        }
    }

    protected static enum MultiPartStatus {
        NOTSTARTED,
        PREAMBLE,
        HEADERDELIMITER,
        DISPOSITION,
        FIELD,
        FILEUPLOAD,
        MIXEDPREAMBLE,
        MIXEDDELIMITER,
        MIXEDDISPOSITION,
        MIXEDFILEUPLOAD,
        MIXEDCLOSEDELIMITER,
        CLOSEDELIMITER,
        PREEPILOGUE,
        EPILOGUE;

    }
}

