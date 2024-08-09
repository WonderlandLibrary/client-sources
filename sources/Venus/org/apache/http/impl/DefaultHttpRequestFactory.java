/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl;

import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.RequestLine;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class DefaultHttpRequestFactory
implements HttpRequestFactory {
    public static final DefaultHttpRequestFactory INSTANCE = new DefaultHttpRequestFactory();
    private static final String[] RFC2616_COMMON_METHODS = new String[]{"GET"};
    private static final String[] RFC2616_ENTITY_ENC_METHODS = new String[]{"POST", "PUT"};
    private static final String[] RFC2616_SPECIAL_METHODS = new String[]{"HEAD", "OPTIONS", "DELETE", "TRACE", "CONNECT"};
    private static final String[] RFC5789_ENTITY_ENC_METHODS = new String[]{"PATCH"};

    private static boolean isOneOf(String[] stringArray, String string) {
        for (String string2 : stringArray) {
            if (!string2.equalsIgnoreCase(string)) continue;
            return false;
        }
        return true;
    }

    @Override
    public HttpRequest newHttpRequest(RequestLine requestLine) throws MethodNotSupportedException {
        Args.notNull(requestLine, "Request line");
        String string = requestLine.getMethod();
        if (DefaultHttpRequestFactory.isOneOf(RFC2616_COMMON_METHODS, string)) {
            return new BasicHttpRequest(requestLine);
        }
        if (DefaultHttpRequestFactory.isOneOf(RFC2616_ENTITY_ENC_METHODS, string)) {
            return new BasicHttpEntityEnclosingRequest(requestLine);
        }
        if (DefaultHttpRequestFactory.isOneOf(RFC2616_SPECIAL_METHODS, string)) {
            return new BasicHttpRequest(requestLine);
        }
        if (DefaultHttpRequestFactory.isOneOf(RFC5789_ENTITY_ENC_METHODS, string)) {
            return new BasicHttpEntityEnclosingRequest(requestLine);
        }
        throw new MethodNotSupportedException(string + " method not supported");
    }

    @Override
    public HttpRequest newHttpRequest(String string, String string2) throws MethodNotSupportedException {
        if (DefaultHttpRequestFactory.isOneOf(RFC2616_COMMON_METHODS, string)) {
            return new BasicHttpRequest(string, string2);
        }
        if (DefaultHttpRequestFactory.isOneOf(RFC2616_ENTITY_ENC_METHODS, string)) {
            return new BasicHttpEntityEnclosingRequest(string, string2);
        }
        if (DefaultHttpRequestFactory.isOneOf(RFC2616_SPECIAL_METHODS, string)) {
            return new BasicHttpRequest(string, string2);
        }
        if (DefaultHttpRequestFactory.isOneOf(RFC5789_ENTITY_ENC_METHODS, string)) {
            return new BasicHttpEntityEnclosingRequest(string, string2);
        }
        throw new MethodNotSupportedException(string + " method not supported");
    }
}

