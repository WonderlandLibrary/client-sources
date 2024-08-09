/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.params;

import java.util.Set;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpParamsNames;

@Deprecated
public abstract class AbstractHttpParams
implements HttpParams,
HttpParamsNames {
    protected AbstractHttpParams() {
    }

    @Override
    public long getLongParameter(String string, long l) {
        Object object = this.getParameter(string);
        if (object == null) {
            return l;
        }
        return (Long)object;
    }

    @Override
    public HttpParams setLongParameter(String string, long l) {
        this.setParameter(string, l);
        return this;
    }

    @Override
    public int getIntParameter(String string, int n) {
        Object object = this.getParameter(string);
        if (object == null) {
            return n;
        }
        return (Integer)object;
    }

    @Override
    public HttpParams setIntParameter(String string, int n) {
        this.setParameter(string, n);
        return this;
    }

    @Override
    public double getDoubleParameter(String string, double d) {
        Object object = this.getParameter(string);
        if (object == null) {
            return d;
        }
        return (Double)object;
    }

    @Override
    public HttpParams setDoubleParameter(String string, double d) {
        this.setParameter(string, d);
        return this;
    }

    @Override
    public boolean getBooleanParameter(String string, boolean bl) {
        Object object = this.getParameter(string);
        if (object == null) {
            return bl;
        }
        return (Boolean)object;
    }

    @Override
    public HttpParams setBooleanParameter(String string, boolean bl) {
        this.setParameter(string, bl ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }

    @Override
    public boolean isParameterTrue(String string) {
        return this.getBooleanParameter(string, true);
    }

    @Override
    public boolean isParameterFalse(String string) {
        return !this.getBooleanParameter(string, true);
    }

    @Override
    public Set<String> getNames() {
        throw new UnsupportedOperationException();
    }
}

