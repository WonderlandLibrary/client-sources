/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultExceptionContext
implements ExceptionContext,
Serializable {
    private static final long serialVersionUID = 20110706L;
    private final List<Pair<String, Object>> contextValues = new ArrayList<Pair<String, Object>>();

    @Override
    public DefaultExceptionContext addContextValue(String string, Object object) {
        this.contextValues.add(new ImmutablePair<String, Object>(string, object));
        return this;
    }

    @Override
    public DefaultExceptionContext setContextValue(String string, Object object) {
        Iterator<Pair<String, Object>> iterator2 = this.contextValues.iterator();
        while (iterator2.hasNext()) {
            Pair<String, Object> pair = iterator2.next();
            if (!StringUtils.equals(string, pair.getKey())) continue;
            iterator2.remove();
        }
        this.addContextValue(string, object);
        return this;
    }

    @Override
    public List<Object> getContextValues(String string) {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        for (Pair<String, Object> pair : this.contextValues) {
            if (!StringUtils.equals(string, pair.getKey())) continue;
            arrayList.add(pair.getValue());
        }
        return arrayList;
    }

    @Override
    public Object getFirstContextValue(String string) {
        for (Pair<String, Object> pair : this.contextValues) {
            if (!StringUtils.equals(string, pair.getKey())) continue;
            return pair.getValue();
        }
        return null;
    }

    @Override
    public Set<String> getContextLabels() {
        HashSet<String> hashSet = new HashSet<String>();
        for (Pair<String, Object> pair : this.contextValues) {
            hashSet.add(pair.getKey());
        }
        return hashSet;
    }

    @Override
    public List<Pair<String, Object>> getContextEntries() {
        return this.contextValues;
    }

    @Override
    public String getFormattedExceptionMessage(String string) {
        StringBuilder stringBuilder = new StringBuilder(256);
        if (string != null) {
            stringBuilder.append(string);
        }
        if (this.contextValues.size() > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append('\n');
            }
            stringBuilder.append("Exception Context:\n");
            int n = 0;
            for (Pair<String, Object> pair : this.contextValues) {
                stringBuilder.append("\t[");
                stringBuilder.append(++n);
                stringBuilder.append(':');
                stringBuilder.append(pair.getKey());
                stringBuilder.append("=");
                Object object = pair.getValue();
                if (object == null) {
                    stringBuilder.append("null");
                } else {
                    String string2;
                    try {
                        string2 = object.toString();
                    } catch (Exception exception) {
                        string2 = "Exception thrown on toString(): " + ExceptionUtils.getStackTrace(exception);
                    }
                    stringBuilder.append(string2);
                }
                stringBuilder.append("]\n");
            }
            stringBuilder.append("---------------------------------");
        }
        return stringBuilder.toString();
    }

    @Override
    public ExceptionContext setContextValue(String string, Object object) {
        return this.setContextValue(string, object);
    }

    @Override
    public ExceptionContext addContextValue(String string, Object object) {
        return this.addContextValue(string, object);
    }
}

