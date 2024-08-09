/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.xml;

import io.netty.handler.codec.xml.XmlAttribute;
import io.netty.handler.codec.xml.XmlElement;
import java.util.LinkedList;
import java.util.List;

public class XmlElementStart
extends XmlElement {
    private final List<XmlAttribute> attributes = new LinkedList<XmlAttribute>();

    public XmlElementStart(String string, String string2, String string3) {
        super(string, string2, string3);
    }

    public List<XmlAttribute> attributes() {
        return this.attributes;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        if (!super.equals(object)) {
            return true;
        }
        XmlElementStart xmlElementStart = (XmlElementStart)object;
        return this.attributes != null ? !this.attributes.equals(xmlElementStart.attributes) : xmlElementStart.attributes != null;
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        n = 31 * n + (this.attributes != null ? this.attributes.hashCode() : 0);
        return n;
    }

    @Override
    public String toString() {
        return "XmlElementStart{attributes=" + this.attributes + super.toString() + "} ";
    }
}

