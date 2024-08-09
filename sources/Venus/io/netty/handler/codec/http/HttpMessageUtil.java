/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.internal.StringUtil;
import java.util.Map;

final class HttpMessageUtil {
    static StringBuilder appendRequest(StringBuilder stringBuilder, HttpRequest httpRequest) {
        HttpMessageUtil.appendCommon(stringBuilder, httpRequest);
        HttpMessageUtil.appendInitialLine(stringBuilder, httpRequest);
        HttpMessageUtil.appendHeaders(stringBuilder, httpRequest.headers());
        HttpMessageUtil.removeLastNewLine(stringBuilder);
        return stringBuilder;
    }

    static StringBuilder appendResponse(StringBuilder stringBuilder, HttpResponse httpResponse) {
        HttpMessageUtil.appendCommon(stringBuilder, httpResponse);
        HttpMessageUtil.appendInitialLine(stringBuilder, httpResponse);
        HttpMessageUtil.appendHeaders(stringBuilder, httpResponse.headers());
        HttpMessageUtil.removeLastNewLine(stringBuilder);
        return stringBuilder;
    }

    private static void appendCommon(StringBuilder stringBuilder, HttpMessage httpMessage) {
        stringBuilder.append(StringUtil.simpleClassName(httpMessage));
        stringBuilder.append("(decodeResult: ");
        stringBuilder.append(httpMessage.decoderResult());
        stringBuilder.append(", version: ");
        stringBuilder.append(httpMessage.protocolVersion());
        stringBuilder.append(')');
        stringBuilder.append(StringUtil.NEWLINE);
    }

    static StringBuilder appendFullRequest(StringBuilder stringBuilder, FullHttpRequest fullHttpRequest) {
        HttpMessageUtil.appendFullCommon(stringBuilder, fullHttpRequest);
        HttpMessageUtil.appendInitialLine(stringBuilder, fullHttpRequest);
        HttpMessageUtil.appendHeaders(stringBuilder, fullHttpRequest.headers());
        HttpMessageUtil.appendHeaders(stringBuilder, fullHttpRequest.trailingHeaders());
        HttpMessageUtil.removeLastNewLine(stringBuilder);
        return stringBuilder;
    }

    static StringBuilder appendFullResponse(StringBuilder stringBuilder, FullHttpResponse fullHttpResponse) {
        HttpMessageUtil.appendFullCommon(stringBuilder, fullHttpResponse);
        HttpMessageUtil.appendInitialLine(stringBuilder, fullHttpResponse);
        HttpMessageUtil.appendHeaders(stringBuilder, fullHttpResponse.headers());
        HttpMessageUtil.appendHeaders(stringBuilder, fullHttpResponse.trailingHeaders());
        HttpMessageUtil.removeLastNewLine(stringBuilder);
        return stringBuilder;
    }

    private static void appendFullCommon(StringBuilder stringBuilder, FullHttpMessage fullHttpMessage) {
        stringBuilder.append(StringUtil.simpleClassName(fullHttpMessage));
        stringBuilder.append("(decodeResult: ");
        stringBuilder.append(fullHttpMessage.decoderResult());
        stringBuilder.append(", version: ");
        stringBuilder.append(fullHttpMessage.protocolVersion());
        stringBuilder.append(", content: ");
        stringBuilder.append(fullHttpMessage.content());
        stringBuilder.append(')');
        stringBuilder.append(StringUtil.NEWLINE);
    }

    private static void appendInitialLine(StringBuilder stringBuilder, HttpRequest httpRequest) {
        stringBuilder.append(httpRequest.method());
        stringBuilder.append(' ');
        stringBuilder.append(httpRequest.uri());
        stringBuilder.append(' ');
        stringBuilder.append(httpRequest.protocolVersion());
        stringBuilder.append(StringUtil.NEWLINE);
    }

    private static void appendInitialLine(StringBuilder stringBuilder, HttpResponse httpResponse) {
        stringBuilder.append(httpResponse.protocolVersion());
        stringBuilder.append(' ');
        stringBuilder.append(httpResponse.status());
        stringBuilder.append(StringUtil.NEWLINE);
    }

    private static void appendHeaders(StringBuilder stringBuilder, HttpHeaders httpHeaders) {
        for (Map.Entry<String, String> entry : httpHeaders) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append(": ");
            stringBuilder.append(entry.getValue());
            stringBuilder.append(StringUtil.NEWLINE);
        }
    }

    private static void removeLastNewLine(StringBuilder stringBuilder) {
        stringBuilder.setLength(stringBuilder.length() - StringUtil.NEWLINE.length());
    }

    private HttpMessageUtil() {
    }
}

