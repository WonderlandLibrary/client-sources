/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.xml;

public class XmlEntityReference {
    private final String name;
    private final String text;

    public XmlEntityReference(String string, String string2) {
        this.name = string;
        this.text = string2;
    }

    public String name() {
        return this.name;
    }

    public String text() {
        return this.text;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        XmlEntityReference xmlEntityReference = (XmlEntityReference)object;
        if (this.name != null ? !this.name.equals(xmlEntityReference.name) : xmlEntityReference.name != null) {
            return true;
        }
        return this.text != null ? !this.text.equals(xmlEntityReference.text) : xmlEntityReference.text != null;
    }

    public int hashCode() {
        int n = this.name != null ? this.name.hashCode() : 0;
        n = 31 * n + (this.text != null ? this.text.hashCode() : 0);
        return n;
    }

    public String toString() {
        return "XmlEntityReference{name='" + this.name + '\'' + ", text='" + this.text + '\'' + '}';
    }
}

