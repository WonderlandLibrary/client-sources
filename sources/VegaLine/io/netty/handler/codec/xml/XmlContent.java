/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.xml;

public abstract class XmlContent {
    private final String data;

    protected XmlContent(String data) {
        this.data = data;
    }

    public String data() {
        return this.data;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        XmlContent that = (XmlContent)o;
        return !(this.data != null ? !this.data.equals(that.data) : that.data != null);
    }

    public int hashCode() {
        return this.data != null ? this.data.hashCode() : 0;
    }

    public String toString() {
        return "XmlContent{data='" + this.data + '\'' + '}';
    }
}

