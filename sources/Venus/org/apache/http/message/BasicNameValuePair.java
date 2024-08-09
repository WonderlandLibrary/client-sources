/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.message;

import java.io.Serializable;
import org.apache.http.NameValuePair;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.util.Args;
import org.apache.http.util.LangUtils;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class BasicNameValuePair
implements NameValuePair,
Cloneable,
Serializable {
    private static final long serialVersionUID = -6437800749411518984L;
    private final String name;
    private final String value;

    public BasicNameValuePair(String string, String string2) {
        this.name = Args.notNull(string, "Name");
        this.value = string2;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public String toString() {
        if (this.value == null) {
            return this.name;
        }
        int n = this.name.length() + 1 + this.value.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        stringBuilder.append(this.name);
        stringBuilder.append("=");
        stringBuilder.append(this.value);
        return stringBuilder.toString();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof NameValuePair) {
            BasicNameValuePair basicNameValuePair = (BasicNameValuePair)object;
            return this.name.equals(basicNameValuePair.name) && LangUtils.equals(this.value, basicNameValuePair.value);
        }
        return true;
    }

    public int hashCode() {
        int n = 17;
        n = LangUtils.hashCode(n, this.name);
        n = LangUtils.hashCode(n, this.value);
        return n;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

