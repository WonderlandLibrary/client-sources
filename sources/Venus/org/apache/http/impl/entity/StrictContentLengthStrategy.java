/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.entity;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class StrictContentLengthStrategy
implements ContentLengthStrategy {
    public static final StrictContentLengthStrategy INSTANCE = new StrictContentLengthStrategy();
    private final int implicitLen;

    public StrictContentLengthStrategy(int n) {
        this.implicitLen = n;
    }

    public StrictContentLengthStrategy() {
        this(-1);
    }

    @Override
    public long determineLength(HttpMessage httpMessage) throws HttpException {
        Args.notNull(httpMessage, "HTTP message");
        Header header = httpMessage.getFirstHeader("Transfer-Encoding");
        if (header != null) {
            String string = header.getValue();
            if ("chunked".equalsIgnoreCase(string)) {
                if (httpMessage.getProtocolVersion().lessEquals(HttpVersion.HTTP_1_0)) {
                    throw new ProtocolException("Chunked transfer encoding not allowed for " + httpMessage.getProtocolVersion());
                }
                return -2L;
            }
            if ("identity".equalsIgnoreCase(string)) {
                return -1L;
            }
            throw new ProtocolException("Unsupported transfer encoding: " + string);
        }
        Header header2 = httpMessage.getFirstHeader("Content-Length");
        if (header2 != null) {
            String string = header2.getValue();
            try {
                long l = Long.parseLong(string);
                if (l < 0L) {
                    throw new ProtocolException("Negative content length: " + string);
                }
                return l;
            } catch (NumberFormatException numberFormatException) {
                throw new ProtocolException("Invalid content length: " + string);
            }
        }
        return this.implicitLen;
    }
}

