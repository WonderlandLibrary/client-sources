/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.entity;

import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.impl.io.ChunkedInputStream;
import org.apache.http.impl.io.ContentLengthInputStream;
import org.apache.http.impl.io.IdentityInputStream;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.util.Args;

@Deprecated
@Contract(threading=ThreadingBehavior.IMMUTABLE_CONDITIONAL)
public class EntityDeserializer {
    private final ContentLengthStrategy lenStrategy;

    public EntityDeserializer(ContentLengthStrategy contentLengthStrategy) {
        this.lenStrategy = Args.notNull(contentLengthStrategy, "Content length strategy");
    }

    protected BasicHttpEntity doDeserialize(SessionInputBuffer sessionInputBuffer, HttpMessage httpMessage) throws HttpException, IOException {
        Header header;
        BasicHttpEntity basicHttpEntity = new BasicHttpEntity();
        long l = this.lenStrategy.determineLength(httpMessage);
        if (l == -2L) {
            basicHttpEntity.setChunked(false);
            basicHttpEntity.setContentLength(-1L);
            basicHttpEntity.setContent(new ChunkedInputStream(sessionInputBuffer));
        } else if (l == -1L) {
            basicHttpEntity.setChunked(true);
            basicHttpEntity.setContentLength(-1L);
            basicHttpEntity.setContent(new IdentityInputStream(sessionInputBuffer));
        } else {
            basicHttpEntity.setChunked(true);
            basicHttpEntity.setContentLength(l);
            basicHttpEntity.setContent(new ContentLengthInputStream(sessionInputBuffer, l));
        }
        Header header2 = httpMessage.getFirstHeader("Content-Type");
        if (header2 != null) {
            basicHttpEntity.setContentType(header2);
        }
        if ((header = httpMessage.getFirstHeader("Content-Encoding")) != null) {
            basicHttpEntity.setContentEncoding(header);
        }
        return basicHttpEntity;
    }

    public HttpEntity deserialize(SessionInputBuffer sessionInputBuffer, HttpMessage httpMessage) throws HttpException, IOException {
        Args.notNull(sessionInputBuffer, "Session input buffer");
        Args.notNull(httpMessage, "HTTP message");
        return this.doDeserialize(sessionInputBuffer, httpMessage);
    }
}

