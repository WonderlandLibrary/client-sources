/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.params;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.params.AbstractHttpParams;
import org.apache.http.params.HttpParams;

@Deprecated
@Contract(threading=ThreadingBehavior.SAFE)
public class BasicHttpParams
extends AbstractHttpParams
implements Serializable,
Cloneable {
    private static final long serialVersionUID = -7086398485908701455L;
    private final Map<String, Object> parameters = new ConcurrentHashMap<String, Object>();

    @Override
    public Object getParameter(String string) {
        return this.parameters.get(string);
    }

    @Override
    public HttpParams setParameter(String string, Object object) {
        if (string == null) {
            return this;
        }
        if (object != null) {
            this.parameters.put(string, object);
        } else {
            this.parameters.remove(string);
        }
        return this;
    }

    @Override
    public boolean removeParameter(String string) {
        if (this.parameters.containsKey(string)) {
            this.parameters.remove(string);
            return false;
        }
        return true;
    }

    public void setParameters(String[] stringArray, Object object) {
        for (String string : stringArray) {
            this.setParameter(string, object);
        }
    }

    public boolean isParameterSet(String string) {
        return this.getParameter(string) != null;
    }

    public boolean isParameterSetLocally(String string) {
        return this.parameters.get(string) != null;
    }

    public void clear() {
        this.parameters.clear();
    }

    @Override
    public HttpParams copy() {
        try {
            return (HttpParams)this.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new UnsupportedOperationException("Cloning not supported");
        }
    }

    public Object clone() throws CloneNotSupportedException {
        BasicHttpParams basicHttpParams = (BasicHttpParams)super.clone();
        this.copyParams(basicHttpParams);
        return basicHttpParams;
    }

    public void copyParams(HttpParams httpParams) {
        for (Map.Entry<String, Object> entry : this.parameters.entrySet()) {
            httpParams.setParameter(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Set<String> getNames() {
        return new HashSet<String>(this.parameters.keySet());
    }

    public String toString() {
        return "[parameters=" + this.parameters + "]";
    }
}

