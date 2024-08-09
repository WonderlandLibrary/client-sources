/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.message;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpMessage;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.HeaderGroup;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

public abstract class AbstractHttpMessage
implements HttpMessage {
    protected HeaderGroup headergroup = new HeaderGroup();
    @Deprecated
    protected HttpParams params;

    @Deprecated
    protected AbstractHttpMessage(HttpParams httpParams) {
        this.params = httpParams;
    }

    protected AbstractHttpMessage() {
        this(null);
    }

    @Override
    public boolean containsHeader(String string) {
        return this.headergroup.containsHeader(string);
    }

    @Override
    public Header[] getHeaders(String string) {
        return this.headergroup.getHeaders(string);
    }

    @Override
    public Header getFirstHeader(String string) {
        return this.headergroup.getFirstHeader(string);
    }

    @Override
    public Header getLastHeader(String string) {
        return this.headergroup.getLastHeader(string);
    }

    @Override
    public Header[] getAllHeaders() {
        return this.headergroup.getAllHeaders();
    }

    @Override
    public void addHeader(Header header) {
        this.headergroup.addHeader(header);
    }

    @Override
    public void addHeader(String string, String string2) {
        Args.notNull(string, "Header name");
        this.headergroup.addHeader(new BasicHeader(string, string2));
    }

    @Override
    public void setHeader(Header header) {
        this.headergroup.updateHeader(header);
    }

    @Override
    public void setHeader(String string, String string2) {
        Args.notNull(string, "Header name");
        this.headergroup.updateHeader(new BasicHeader(string, string2));
    }

    @Override
    public void setHeaders(Header[] headerArray) {
        this.headergroup.setHeaders(headerArray);
    }

    @Override
    public void removeHeader(Header header) {
        this.headergroup.removeHeader(header);
    }

    @Override
    public void removeHeaders(String string) {
        if (string == null) {
            return;
        }
        HeaderIterator headerIterator = this.headergroup.iterator();
        while (headerIterator.hasNext()) {
            Header header = headerIterator.nextHeader();
            if (!string.equalsIgnoreCase(header.getName())) continue;
            headerIterator.remove();
        }
    }

    @Override
    public HeaderIterator headerIterator() {
        return this.headergroup.iterator();
    }

    @Override
    public HeaderIterator headerIterator(String string) {
        return this.headergroup.iterator(string);
    }

    @Override
    @Deprecated
    public HttpParams getParams() {
        if (this.params == null) {
            this.params = new BasicHttpParams();
        }
        return this.params;
    }

    @Override
    @Deprecated
    public void setParams(HttpParams httpParams) {
        this.params = Args.notNull(httpParams, "HTTP parameters");
    }
}

