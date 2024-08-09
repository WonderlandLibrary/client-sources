/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.params;

import java.util.HashSet;
import java.util.Set;
import org.apache.http.params.AbstractHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpParamsNames;
import org.apache.http.util.Args;

@Deprecated
public final class DefaultedHttpParams
extends AbstractHttpParams {
    private final HttpParams local;
    private final HttpParams defaults;

    public DefaultedHttpParams(HttpParams httpParams, HttpParams httpParams2) {
        this.local = Args.notNull(httpParams, "Local HTTP parameters");
        this.defaults = httpParams2;
    }

    @Override
    public HttpParams copy() {
        HttpParams httpParams = this.local.copy();
        return new DefaultedHttpParams(httpParams, this.defaults);
    }

    @Override
    public Object getParameter(String string) {
        Object object = this.local.getParameter(string);
        if (object == null && this.defaults != null) {
            object = this.defaults.getParameter(string);
        }
        return object;
    }

    @Override
    public boolean removeParameter(String string) {
        return this.local.removeParameter(string);
    }

    @Override
    public HttpParams setParameter(String string, Object object) {
        return this.local.setParameter(string, object);
    }

    public HttpParams getDefaults() {
        return this.defaults;
    }

    @Override
    public Set<String> getNames() {
        HashSet<String> hashSet = new HashSet<String>(this.getNames(this.defaults));
        hashSet.addAll(this.getNames(this.local));
        return hashSet;
    }

    public Set<String> getDefaultNames() {
        return new HashSet<String>(this.getNames(this.defaults));
    }

    public Set<String> getLocalNames() {
        return new HashSet<String>(this.getNames(this.local));
    }

    private Set<String> getNames(HttpParams httpParams) {
        if (httpParams instanceof HttpParamsNames) {
            return ((HttpParamsNames)((Object)httpParams)).getNames();
        }
        throw new UnsupportedOperationException("HttpParams instance does not implement HttpParamsNames");
    }
}

