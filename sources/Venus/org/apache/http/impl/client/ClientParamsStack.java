/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import org.apache.http.params.AbstractHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

@Deprecated
public class ClientParamsStack
extends AbstractHttpParams {
    protected final HttpParams applicationParams;
    protected final HttpParams clientParams;
    protected final HttpParams requestParams;
    protected final HttpParams overrideParams;

    public ClientParamsStack(HttpParams httpParams, HttpParams httpParams2, HttpParams httpParams3, HttpParams httpParams4) {
        this.applicationParams = httpParams;
        this.clientParams = httpParams2;
        this.requestParams = httpParams3;
        this.overrideParams = httpParams4;
    }

    public ClientParamsStack(ClientParamsStack clientParamsStack) {
        this(clientParamsStack.getApplicationParams(), clientParamsStack.getClientParams(), clientParamsStack.getRequestParams(), clientParamsStack.getOverrideParams());
    }

    public ClientParamsStack(ClientParamsStack clientParamsStack, HttpParams httpParams, HttpParams httpParams2, HttpParams httpParams3, HttpParams httpParams4) {
        this(httpParams != null ? httpParams : clientParamsStack.getApplicationParams(), httpParams2 != null ? httpParams2 : clientParamsStack.getClientParams(), httpParams3 != null ? httpParams3 : clientParamsStack.getRequestParams(), httpParams4 != null ? httpParams4 : clientParamsStack.getOverrideParams());
    }

    public final HttpParams getApplicationParams() {
        return this.applicationParams;
    }

    public final HttpParams getClientParams() {
        return this.clientParams;
    }

    public final HttpParams getRequestParams() {
        return this.requestParams;
    }

    public final HttpParams getOverrideParams() {
        return this.overrideParams;
    }

    @Override
    public Object getParameter(String string) {
        Args.notNull(string, "Parameter name");
        Object object = null;
        if (this.overrideParams != null) {
            object = this.overrideParams.getParameter(string);
        }
        if (object == null && this.requestParams != null) {
            object = this.requestParams.getParameter(string);
        }
        if (object == null && this.clientParams != null) {
            object = this.clientParams.getParameter(string);
        }
        if (object == null && this.applicationParams != null) {
            object = this.applicationParams.getParameter(string);
        }
        return object;
    }

    @Override
    public HttpParams setParameter(String string, Object object) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Setting parameters in a stack is not supported.");
    }

    @Override
    public boolean removeParameter(String string) {
        throw new UnsupportedOperationException("Removing parameters in a stack is not supported.");
    }

    @Override
    public HttpParams copy() {
        return this;
    }
}

