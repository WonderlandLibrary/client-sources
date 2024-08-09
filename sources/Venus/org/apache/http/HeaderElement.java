/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http;

import org.apache.http.NameValuePair;

public interface HeaderElement {
    public String getName();

    public String getValue();

    public NameValuePair[] getParameters();

    public NameValuePair getParameterByName(String var1);

    public int getParameterCount();

    public NameValuePair getParameter(int var1);
}

