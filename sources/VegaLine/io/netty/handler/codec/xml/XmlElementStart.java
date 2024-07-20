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

    public XmlElementStart(String name, String namespace, String prefix) {
        super(name, namespace, prefix);
    }

    public List<XmlAttribute> attributes() {
        return this.attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        XmlElementStart that = (XmlElementStart)o;
        return !(this.attributes != null ? !this.attributes.equals(that.attributes) : that.attributes != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (this.attributes != null ? this.attributes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "XmlElementStart{attributes=" + this.attributes + super.toString() + "} ";
    }
}

