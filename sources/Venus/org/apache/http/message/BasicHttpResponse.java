/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.message;

import java.util.Locale;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.ReasonPhraseCatalog;
import org.apache.http.StatusLine;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

public class BasicHttpResponse
extends AbstractHttpMessage
implements HttpResponse {
    private StatusLine statusline;
    private ProtocolVersion ver;
    private int code;
    private String reasonPhrase;
    private HttpEntity entity;
    private final ReasonPhraseCatalog reasonCatalog;
    private Locale locale;

    public BasicHttpResponse(StatusLine statusLine, ReasonPhraseCatalog reasonPhraseCatalog, Locale locale) {
        this.statusline = Args.notNull(statusLine, "Status line");
        this.ver = statusLine.getProtocolVersion();
        this.code = statusLine.getStatusCode();
        this.reasonPhrase = statusLine.getReasonPhrase();
        this.reasonCatalog = reasonPhraseCatalog;
        this.locale = locale;
    }

    public BasicHttpResponse(StatusLine statusLine) {
        this.statusline = Args.notNull(statusLine, "Status line");
        this.ver = statusLine.getProtocolVersion();
        this.code = statusLine.getStatusCode();
        this.reasonPhrase = statusLine.getReasonPhrase();
        this.reasonCatalog = null;
        this.locale = null;
    }

    public BasicHttpResponse(ProtocolVersion protocolVersion, int n, String string) {
        Args.notNegative(n, "Status code");
        this.statusline = null;
        this.ver = protocolVersion;
        this.code = n;
        this.reasonPhrase = string;
        this.reasonCatalog = null;
        this.locale = null;
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
        return this.ver;
    }

    @Override
    public StatusLine getStatusLine() {
        if (this.statusline == null) {
            this.statusline = new BasicStatusLine(this.ver != null ? this.ver : HttpVersion.HTTP_1_1, this.code, this.reasonPhrase != null ? this.reasonPhrase : this.getReason(this.code));
        }
        return this.statusline;
    }

    @Override
    public HttpEntity getEntity() {
        return this.entity;
    }

    @Override
    public Locale getLocale() {
        return this.locale;
    }

    @Override
    public void setStatusLine(StatusLine statusLine) {
        this.statusline = Args.notNull(statusLine, "Status line");
        this.ver = statusLine.getProtocolVersion();
        this.code = statusLine.getStatusCode();
        this.reasonPhrase = statusLine.getReasonPhrase();
    }

    @Override
    public void setStatusLine(ProtocolVersion protocolVersion, int n) {
        Args.notNegative(n, "Status code");
        this.statusline = null;
        this.ver = protocolVersion;
        this.code = n;
        this.reasonPhrase = null;
    }

    @Override
    public void setStatusLine(ProtocolVersion protocolVersion, int n, String string) {
        Args.notNegative(n, "Status code");
        this.statusline = null;
        this.ver = protocolVersion;
        this.code = n;
        this.reasonPhrase = string;
    }

    @Override
    public void setStatusCode(int n) {
        Args.notNegative(n, "Status code");
        this.statusline = null;
        this.code = n;
        this.reasonPhrase = null;
    }

    @Override
    public void setReasonPhrase(String string) {
        this.statusline = null;
        this.reasonPhrase = TextUtils.isBlank(string) ? null : string;
    }

    @Override
    public void setEntity(HttpEntity httpEntity) {
        this.entity = httpEntity;
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = Args.notNull(locale, "Locale");
        this.statusline = null;
    }

    protected String getReason(int n) {
        return this.reasonCatalog != null ? this.reasonCatalog.getReason(n, this.locale != null ? this.locale : Locale.getDefault()) : null;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getStatusLine());
        stringBuilder.append(' ');
        stringBuilder.append(this.headergroup);
        if (this.entity != null) {
            stringBuilder.append(' ');
            stringBuilder.append(this.entity);
        }
        return stringBuilder.toString();
    }
}

