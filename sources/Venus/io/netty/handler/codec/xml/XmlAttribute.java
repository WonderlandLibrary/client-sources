/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.xml;

public class XmlAttribute {
    private final String type;
    private final String name;
    private final String prefix;
    private final String namespace;
    private final String value;

    public XmlAttribute(String string, String string2, String string3, String string4, String string5) {
        this.type = string;
        this.name = string2;
        this.prefix = string3;
        this.namespace = string4;
        this.value = string5;
    }

    public String type() {
        return this.type;
    }

    public String name() {
        return this.name;
    }

    public String prefix() {
        return this.prefix;
    }

    public String namespace() {
        return this.namespace;
    }

    public String value() {
        return this.value;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        XmlAttribute xmlAttribute = (XmlAttribute)object;
        if (!this.name.equals(xmlAttribute.name)) {
            return true;
        }
        if (this.namespace != null ? !this.namespace.equals(xmlAttribute.namespace) : xmlAttribute.namespace != null) {
            return true;
        }
        if (this.prefix != null ? !this.prefix.equals(xmlAttribute.prefix) : xmlAttribute.prefix != null) {
            return true;
        }
        if (this.type != null ? !this.type.equals(xmlAttribute.type) : xmlAttribute.type != null) {
            return true;
        }
        return this.value != null ? !this.value.equals(xmlAttribute.value) : xmlAttribute.value != null;
    }

    public int hashCode() {
        int n = this.type != null ? this.type.hashCode() : 0;
        n = 31 * n + this.name.hashCode();
        n = 31 * n + (this.prefix != null ? this.prefix.hashCode() : 0);
        n = 31 * n + (this.namespace != null ? this.namespace.hashCode() : 0);
        n = 31 * n + (this.value != null ? this.value.hashCode() : 0);
        return n;
    }

    public String toString() {
        return "XmlAttribute{type='" + this.type + '\'' + ", name='" + this.name + '\'' + ", prefix='" + this.prefix + '\'' + ", namespace='" + this.namespace + '\'' + ", value='" + this.value + '\'' + '}';
    }
}

