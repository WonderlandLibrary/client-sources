/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.entity;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.ParseException;
import org.apache.http.ProtocolException;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class LaxContentLengthStrategy
implements ContentLengthStrategy {
    public static final LaxContentLengthStrategy INSTANCE = new LaxContentLengthStrategy();
    private final int implicitLen;

    public LaxContentLengthStrategy(int n) {
        this.implicitLen = n;
    }

    public LaxContentLengthStrategy() {
        this(-1);
    }

    @Override
    public long determineLength(HttpMessage httpMessage) throws HttpException {
        Args.notNull(httpMessage, "HTTP message");
        Header header = httpMessage.getFirstHeader("Transfer-Encoding");
        if (header != null) {
            HeaderElement[] headerElementArray;
            try {
                headerElementArray = header.getElements();
            } catch (ParseException parseException) {
                throw new ProtocolException("Invalid Transfer-Encoding header value: " + header, parseException);
            }
            int n = headerElementArray.length;
            if ("identity".equalsIgnoreCase(header.getValue())) {
                return -1L;
            }
            if (n > 0 && "chunked".equalsIgnoreCase(headerElementArray[n - 1].getName())) {
                return -2L;
            }
            return -1L;
        }
        Header header2 = httpMessage.getFirstHeader("Content-Length");
        if (header2 != null) {
            long l = -1L;
            Header[] headerArray = httpMessage.getHeaders("Content-Length");
            for (int i = headerArray.length - 1; i >= 0; --i) {
                Header header3 = headerArray[i];
                try {
                    l = Long.parseLong(header3.getValue());
                    break;
                } catch (NumberFormatException numberFormatException) {
                    continue;
                }
            }
            return l >= 0L ? l : -1L;
        }
        return this.implicitLen;
    }
}

