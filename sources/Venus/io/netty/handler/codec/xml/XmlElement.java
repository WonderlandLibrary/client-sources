/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.xml;

import io.netty.handler.codec.xml.XmlNamespace;
import java.util.LinkedList;
import java.util.List;

public abstract class XmlElement {
    private final String name;
    private final String namespace;
    private final String prefix;
    private final List<XmlNamespace> namespaces = new LinkedList<XmlNamespace>();

    protected XmlElement(String string, String string2, String string3) {
        this.name = string;
        this.namespace = string2;
        this.prefix = string3;
    }

    public String name() {
        return this.name;
    }

    public String namespace() {
        return this.namespace;
    }

    public String prefix() {
        return this.prefix;
    }

    public List<XmlNamespace> namespaces() {
        return this.namespaces;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        XmlElement xmlElement = (XmlElement)object;
        if (!this.name.equals(xmlElement.name)) {
            return true;
        }
        if (this.namespace != null ? !this.namespace.equals(xmlElement.namespace) : xmlElement.namespace != null) {
            return true;
        }
        if (this.namespaces != null ? !this.namespaces.equals(xmlElement.namespaces) : xmlElement.namespaces != null) {
            return true;
        }
        return this.prefix != null ? !this.prefix.equals(xmlElement.prefix) : xmlElement.prefix != null;
    }

    public int hashCode() {
        int n = this.name.hashCode();
        n = 31 * n + (this.namespace != null ? this.namespace.hashCode() : 0);
        n = 31 * n + (this.prefix != null ? this.prefix.hashCode() : 0);
        n = 31 * n + (this.namespaces != null ? this.namespaces.hashCode() : 0);
        return n;
    }

    public String toString() {
        return ", name='" + this.name + '\'' + ", namespace='" + this.namespace + '\'' + ", prefix='" + this.prefix + '\'' + ", namespaces=" + this.namespaces;
    }
}

