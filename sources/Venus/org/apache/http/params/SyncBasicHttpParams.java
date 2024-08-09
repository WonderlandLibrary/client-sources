/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.params;

import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

@Deprecated
@Contract(threading=ThreadingBehavior.SAFE)
public class SyncBasicHttpParams
extends BasicHttpParams {
    private static final long serialVersionUID = 5387834869062660642L;

    @Override
    public synchronized boolean removeParameter(String string) {
        return super.removeParameter(string);
    }

    @Override
    public synchronized HttpParams setParameter(String string, Object object) {
        return super.setParameter(string, object);
    }

    @Override
    public synchronized Object getParameter(String string) {
        return super.getParameter(string);
    }

    @Override
    public synchronized boolean isParameterSet(String string) {
        return super.isParameterSet(string);
    }

    @Override
    public synchronized boolean isParameterSetLocally(String string) {
        return super.isParameterSetLocally(string);
    }

    @Override
    public synchronized void setParameters(String[] stringArray, Object object) {
        super.setParameters(stringArray, object);
    }

    @Override
    public synchronized void clear() {
        super.clear();
    }

    @Override
    public synchronized Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

