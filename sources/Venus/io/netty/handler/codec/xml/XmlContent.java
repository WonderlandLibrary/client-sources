/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.xml;

public abstract class XmlContent {
    private final String data;

    protected XmlContent(String string) {
        this.data = string;
    }

    public String data() {
        return this.data;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        XmlContent xmlContent = (XmlContent)object;
        return this.data != null ? !this.data.equals(xmlContent.data) : xmlContent.data != null;
    }

    public int hashCode() {
        return this.data != null ? this.data.hashCode() : 0;
    }

    public String toString() {
        return "XmlContent{data='" + this.data + '\'' + '}';
    }
}

