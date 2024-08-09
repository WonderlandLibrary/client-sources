/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.SetCookie;
import org.apache.http.util.Args;

public class BasicClientCookie
implements SetCookie,
ClientCookie,
Cloneable,
Serializable {
    private static final long serialVersionUID = -3869795591041535538L;
    private final String name;
    private Map<String, String> attribs;
    private String value;
    private String cookieComment;
    private String cookieDomain;
    private Date cookieExpiryDate;
    private String cookiePath;
    private boolean isSecure;
    private int cookieVersion;
    private Date creationDate;

    public BasicClientCookie(String string, String string2) {
        Args.notNull(string, "Name");
        this.name = string;
        this.attribs = new HashMap<String, String>();
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

    @Override
    public void setValue(String string) {
        this.value = string;
    }

    @Override
    public String getComment() {
        return this.cookieComment;
    }

    @Override
    public void setComment(String string) {
        this.cookieComment = string;
    }

    @Override
    public String getCommentURL() {
        return null;
    }

    @Override
    public Date getExpiryDate() {
        return this.cookieExpiryDate;
    }

    @Override
    public void setExpiryDate(Date date) {
        this.cookieExpiryDate = date;
    }

    @Override
    public boolean isPersistent() {
        return null != this.cookieExpiryDate;
    }

    @Override
    public String getDomain() {
        return this.cookieDomain;
    }

    @Override
    public void setDomain(String string) {
        this.cookieDomain = string != null ? string.toLowerCase(Locale.ROOT) : null;
    }

    @Override
    public String getPath() {
        return this.cookiePath;
    }

    @Override
    public void setPath(String string) {
        this.cookiePath = string;
    }

    @Override
    public boolean isSecure() {
        return this.isSecure;
    }

    @Override
    public void setSecure(boolean bl) {
        this.isSecure = bl;
    }

    @Override
    public int[] getPorts() {
        return null;
    }

    @Override
    public int getVersion() {
        return this.cookieVersion;
    }

    @Override
    public void setVersion(int n) {
        this.cookieVersion = n;
    }

    @Override
    public boolean isExpired(Date date) {
        Args.notNull(date, "Date");
        return this.cookieExpiryDate != null && this.cookieExpiryDate.getTime() <= date.getTime();
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    public void setAttribute(String string, String string2) {
        this.attribs.put(string, string2);
    }

    @Override
    public String getAttribute(String string) {
        return this.attribs.get(string);
    }

    @Override
    public boolean containsAttribute(String string) {
        return this.attribs.containsKey(string);
    }

    public boolean removeAttribute(String string) {
        return this.attribs.remove(string) != null;
    }

    public Object clone() throws CloneNotSupportedException {
        BasicClientCookie basicClientCookie = (BasicClientCookie)super.clone();
        basicClientCookie.attribs = new HashMap<String, String>(this.attribs);
        return basicClientCookie;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[version: ");
        stringBuilder.append(Integer.toString(this.cookieVersion));
        stringBuilder.append("]");
        stringBuilder.append("[name: ");
        stringBuilder.append(this.name);
        stringBuilder.append("]");
        stringBuilder.append("[value: ");
        stringBuilder.append(this.value);
        stringBuilder.append("]");
        stringBuilder.append("[domain: ");
        stringBuilder.append(this.cookieDomain);
        stringBuilder.append("]");
        stringBuilder.append("[path: ");
        stringBuilder.append(this.cookiePath);
        stringBuilder.append("]");
        stringBuilder.append("[expiry: ");
        stringBuilder.append(this.cookieExpiryDate);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

