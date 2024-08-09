/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.xml;

public class XmlProcessingInstruction {
    private final String data;
    private final String target;

    public XmlProcessingInstruction(String string, String string2) {
        this.data = string;
        this.target = string2;
    }

    public String data() {
        return this.data;
    }

    public String target() {
        return this.target;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        XmlProcessingInstruction xmlProcessingInstruction = (XmlProcessingInstruction)object;
        if (this.data != null ? !this.data.equals(xmlProcessingInstruction.data) : xmlProcessingInstruction.data != null) {
            return true;
        }
        return this.target != null ? !this.target.equals(xmlProcessingInstruction.target) : xmlProcessingInstruction.target != null;
    }

    public int hashCode() {
        int n = this.data != null ? this.data.hashCode() : 0;
        n = 31 * n + (this.target != null ? this.target.hashCode() : 0);
        return n;
    }

    public String toString() {
        return "XmlProcessingInstruction{data='" + this.data + '\'' + ", target='" + this.target + '\'' + '}';
    }
}

