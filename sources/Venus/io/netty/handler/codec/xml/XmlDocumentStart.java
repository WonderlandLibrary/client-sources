/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.xml;

public class XmlDocumentStart {
    private final String encoding;
    private final String version;
    private final boolean standalone;
    private final String encodingScheme;

    public XmlDocumentStart(String string, String string2, boolean bl, String string3) {
        this.encoding = string;
        this.version = string2;
        this.standalone = bl;
        this.encodingScheme = string3;
    }

    public String encoding() {
        return this.encoding;
    }

    public String version() {
        return this.version;
    }

    public boolean standalone() {
        return this.standalone;
    }

    public String encodingScheme() {
        return this.encodingScheme;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        XmlDocumentStart xmlDocumentStart = (XmlDocumentStart)object;
        if (this.standalone != xmlDocumentStart.standalone) {
            return true;
        }
        if (this.encoding != null ? !this.encoding.equals(xmlDocumentStart.encoding) : xmlDocumentStart.encoding != null) {
            return true;
        }
        if (this.encodingScheme != null ? !this.encodingScheme.equals(xmlDocumentStart.encodingScheme) : xmlDocumentStart.encodingScheme != null) {
            return true;
        }
        return this.version != null ? !this.version.equals(xmlDocumentStart.version) : xmlDocumentStart.version != null;
    }

    public int hashCode() {
        int n = this.encoding != null ? this.encoding.hashCode() : 0;
        n = 31 * n + (this.version != null ? this.version.hashCode() : 0);
        n = 31 * n + (this.standalone ? 1 : 0);
        n = 31 * n + (this.encodingScheme != null ? this.encodingScheme.hashCode() : 0);
        return n;
    }

    public String toString() {
        return "XmlDocumentStart{encoding='" + this.encoding + '\'' + ", version='" + this.version + '\'' + ", standalone=" + this.standalone + ", encodingScheme='" + this.encodingScheme + '\'' + '}';
    }
}

