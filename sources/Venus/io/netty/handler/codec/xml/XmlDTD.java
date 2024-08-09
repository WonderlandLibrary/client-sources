/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.xml;

public class XmlDTD {
    private final String text;

    public XmlDTD(String string) {
        this.text = string;
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
        XmlDTD xmlDTD = (XmlDTD)object;
        return this.text != null ? !this.text.equals(xmlDTD.text) : xmlDTD.text != null;
    }

    public int hashCode() {
        return this.text != null ? this.text.hashCode() : 0;
    }

    public String toString() {
        return "XmlDTD{text='" + this.text + '\'' + '}';
    }
}

