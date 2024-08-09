/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.xml;

public class XmlNamespace {
    private final String prefix;
    private final String uri;

    public XmlNamespace(String string, String string2) {
        this.prefix = string;
        this.uri = string2;
    }

    public String prefix() {
        return this.prefix;
    }

    public String uri() {
        return this.uri;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        XmlNamespace xmlNamespace = (XmlNamespace)object;
        if (this.prefix != null ? !this.prefix.equals(xmlNamespace.prefix) : xmlNamespace.prefix != null) {
            return true;
        }
        return this.uri != null ? !this.uri.equals(xmlNamespace.uri) : xmlNamespace.uri != null;
    }

    public int hashCode() {
        int n = this.prefix != null ? this.prefix.hashCode() : 0;
        n = 31 * n + (this.uri != null ? this.uri.hashCode() : 0);
        return n;
    }

    public String toString() {
        return "XmlNamespace{prefix='" + this.prefix + '\'' + ", uri='" + this.uri + '\'' + '}';
    }
}

