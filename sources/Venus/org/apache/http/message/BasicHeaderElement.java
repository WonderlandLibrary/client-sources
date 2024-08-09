/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.message;

import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.util.Args;
import org.apache.http.util.LangUtils;

public class BasicHeaderElement
implements HeaderElement,
Cloneable {
    private final String name;
    private final String value;
    private final NameValuePair[] parameters;

    public BasicHeaderElement(String string, String string2, NameValuePair[] nameValuePairArray) {
        this.name = Args.notNull(string, "Name");
        this.value = string2;
        this.parameters = nameValuePairArray != null ? nameValuePairArray : new NameValuePair[0];
    }

    public BasicHeaderElement(String string, String string2) {
        this(string, string2, null);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public NameValuePair[] getParameters() {
        return (NameValuePair[])this.parameters.clone();
    }

    @Override
    public int getParameterCount() {
        return this.parameters.length;
    }

    @Override
    public NameValuePair getParameter(int n) {
        return this.parameters[n];
    }

    @Override
    public NameValuePair getParameterByName(String string) {
        Args.notNull(string, "Name");
        NameValuePair nameValuePair = null;
        for (NameValuePair nameValuePair2 : this.parameters) {
            if (!nameValuePair2.getName().equalsIgnoreCase(string)) continue;
            nameValuePair = nameValuePair2;
            break;
        }
        return nameValuePair;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof HeaderElement) {
            BasicHeaderElement basicHeaderElement = (BasicHeaderElement)object;
            return this.name.equals(basicHeaderElement.name) && LangUtils.equals(this.value, basicHeaderElement.value) && LangUtils.equals(this.parameters, basicHeaderElement.parameters);
        }
        return true;
    }

    public int hashCode() {
        int n = 17;
        n = LangUtils.hashCode(n, this.name);
        n = LangUtils.hashCode(n, this.value);
        for (NameValuePair nameValuePair : this.parameters) {
            n = LangUtils.hashCode(n, nameValuePair);
        }
        return n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name);
        if (this.value != null) {
            stringBuilder.append("=");
            stringBuilder.append(this.value);
        }
        for (NameValuePair nameValuePair : this.parameters) {
            stringBuilder.append("; ");
            stringBuilder.append(nameValuePair);
        }
        return stringBuilder.toString();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

