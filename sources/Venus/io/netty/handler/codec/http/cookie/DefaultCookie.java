/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.cookie;

import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.CookieUtil;
import io.netty.util.internal.ObjectUtil;

public class DefaultCookie
implements Cookie {
    private final String name;
    private String value;
    private boolean wrap;
    private String domain;
    private String path;
    private long maxAge = Long.MIN_VALUE;
    private boolean secure;
    private boolean httpOnly;

    public DefaultCookie(String string, String string2) {
        string = ObjectUtil.checkNotNull(string, "name").trim();
        if (string.isEmpty()) {
            throw new IllegalArgumentException("empty name");
        }
        this.name = string;
        this.setValue(string2);
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String value() {
        return this.value;
    }

    @Override
    public void setValue(String string) {
        this.value = ObjectUtil.checkNotNull(string, "value");
    }

    @Override
    public boolean wrap() {
        return this.wrap;
    }

    @Override
    public void setWrap(boolean bl) {
        this.wrap = bl;
    }

    @Override
    public String domain() {
        return this.domain;
    }

    @Override
    public void setDomain(String string) {
        this.domain = CookieUtil.validateAttributeValue("domain", string);
    }

    @Override
    public String path() {
        return this.path;
    }

    @Override
    public void setPath(String string) {
        this.path = CookieUtil.validateAttributeValue("path", string);
    }

    @Override
    public long maxAge() {
        return this.maxAge;
    }

    @Override
    public void setMaxAge(long l) {
        this.maxAge = l;
    }

    @Override
    public boolean isSecure() {
        return this.secure;
    }

    @Override
    public void setSecure(boolean bl) {
        this.secure = bl;
    }

    @Override
    public boolean isHttpOnly() {
        return this.httpOnly;
    }

    @Override
    public void setHttpOnly(boolean bl) {
        this.httpOnly = bl;
    }

    public int hashCode() {
        return this.name().hashCode();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof Cookie)) {
            return true;
        }
        Cookie cookie = (Cookie)object;
        if (!this.name().equals(cookie.name())) {
            return true;
        }
        if (this.path() == null) {
            if (cookie.path() != null) {
                return true;
            }
        } else {
            if (cookie.path() == null) {
                return true;
            }
            if (!this.path().equals(cookie.path())) {
                return true;
            }
        }
        if (this.domain() == null) {
            return cookie.domain() != null;
        }
        return this.domain().equalsIgnoreCase(cookie.domain());
    }

    @Override
    public int compareTo(Cookie cookie) {
        int n = this.name().compareTo(cookie.name());
        if (n != 0) {
            return n;
        }
        if (this.path() == null) {
            if (cookie.path() != null) {
                return 1;
            }
        } else {
            if (cookie.path() == null) {
                return 0;
            }
            n = this.path().compareTo(cookie.path());
            if (n != 0) {
                return n;
            }
        }
        if (this.domain() == null) {
            if (cookie.domain() != null) {
                return 1;
            }
        } else {
            if (cookie.domain() == null) {
                return 0;
            }
            n = this.domain().compareToIgnoreCase(cookie.domain());
            return n;
        }
        return 1;
    }

    @Deprecated
    protected String validateValue(String string, String string2) {
        return CookieUtil.validateAttributeValue(string, string2);
    }

    public String toString() {
        StringBuilder stringBuilder = CookieUtil.stringBuilder().append(this.name()).append('=').append(this.value());
        if (this.domain() != null) {
            stringBuilder.append(", domain=").append(this.domain());
        }
        if (this.path() != null) {
            stringBuilder.append(", path=").append(this.path());
        }
        if (this.maxAge() >= 0L) {
            stringBuilder.append(", maxAge=").append(this.maxAge()).append('s');
        }
        if (this.isSecure()) {
            stringBuilder.append(", secure");
        }
        if (this.isHttpOnly()) {
            stringBuilder.append(", HTTPOnly");
        }
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((Cookie)object);
    }
}

