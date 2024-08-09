/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.ProtocolException;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.RequestWrapper;

@Deprecated
public class EntityEnclosingRequestWrapper
extends RequestWrapper
implements HttpEntityEnclosingRequest {
    private HttpEntity entity;
    private boolean consumed;

    public EntityEnclosingRequestWrapper(HttpEntityEnclosingRequest httpEntityEnclosingRequest) throws ProtocolException {
        super(httpEntityEnclosingRequest);
        this.setEntity(httpEntityEnclosingRequest.getEntity());
    }

    @Override
    public HttpEntity getEntity() {
        return this.entity;
    }

    @Override
    public void setEntity(HttpEntity httpEntity) {
        this.entity = httpEntity != null ? new EntityWrapper(this, httpEntity) : null;
        this.consumed = false;
    }

    @Override
    public boolean expectContinue() {
        Header header = this.getFirstHeader("Expect");
        return header != null && "100-continue".equalsIgnoreCase(header.getValue());
    }

    @Override
    public boolean isRepeatable() {
        return this.entity == null || this.entity.isRepeatable() || !this.consumed;
    }

    static boolean access$002(EntityEnclosingRequestWrapper entityEnclosingRequestWrapper, boolean bl) {
        entityEnclosingRequestWrapper.consumed = bl;
        return entityEnclosingRequestWrapper.consumed;
    }

    class EntityWrapper
    extends HttpEntityWrapper {
        final EntityEnclosingRequestWrapper this$0;

        EntityWrapper(EntityEnclosingRequestWrapper entityEnclosingRequestWrapper, HttpEntity httpEntity) {
            this.this$0 = entityEnclosingRequestWrapper;
            super(httpEntity);
        }

        @Override
        public void consumeContent() throws IOException {
            EntityEnclosingRequestWrapper.access$002(this.this$0, true);
            super.consumeContent();
        }

        @Override
        public InputStream getContent() throws IOException {
            EntityEnclosingRequestWrapper.access$002(this.this$0, true);
            return super.getContent();
        }

        @Override
        public void writeTo(OutputStream outputStream) throws IOException {
            EntityEnclosingRequestWrapper.access$002(this.this$0, true);
            super.writeTo(outputStream);
        }
    }
}

