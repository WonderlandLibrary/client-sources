/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.plugins.visitors;

import java.util.Map;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.visitors.AbstractPluginVisitor;
import org.apache.logging.log4j.core.util.NameUtil;
import org.apache.logging.log4j.util.StringBuilders;

public class PluginAttributeVisitor
extends AbstractPluginVisitor<PluginAttribute> {
    public PluginAttributeVisitor() {
        super(PluginAttribute.class);
    }

    @Override
    public Object visit(Configuration configuration, Node node, LogEvent logEvent, StringBuilder stringBuilder) {
        String string = ((PluginAttribute)this.annotation).value();
        Map<String, String> map = node.getAttributes();
        String string2 = PluginAttributeVisitor.removeAttributeValue(map, string, this.aliases);
        String string3 = this.substitutor.replace(logEvent, string2);
        Object object = this.findDefaultValue(logEvent);
        Object object2 = this.convert(string3, object);
        Object object3 = ((PluginAttribute)this.annotation).sensitive() ? NameUtil.md5(object2 + this.getClass().getName()) : object2;
        StringBuilders.appendKeyDqValue(stringBuilder, string, object3);
        return object2;
    }

    private Object findDefaultValue(LogEvent logEvent) {
        if (this.conversionType == Integer.TYPE || this.conversionType == Integer.class) {
            return ((PluginAttribute)this.annotation).defaultInt();
        }
        if (this.conversionType == Long.TYPE || this.conversionType == Long.class) {
            return ((PluginAttribute)this.annotation).defaultLong();
        }
        if (this.conversionType == Boolean.TYPE || this.conversionType == Boolean.class) {
            return ((PluginAttribute)this.annotation).defaultBoolean();
        }
        if (this.conversionType == Float.TYPE || this.conversionType == Float.class) {
            return Float.valueOf(((PluginAttribute)this.annotation).defaultFloat());
        }
        if (this.conversionType == Double.TYPE || this.conversionType == Double.class) {
            return ((PluginAttribute)this.annotation).defaultDouble();
        }
        if (this.conversionType == Byte.TYPE || this.conversionType == Byte.class) {
            return ((PluginAttribute)this.annotation).defaultByte();
        }
        if (this.conversionType == Character.TYPE || this.conversionType == Character.class) {
            return Character.valueOf(((PluginAttribute)this.annotation).defaultChar());
        }
        if (this.conversionType == Short.TYPE || this.conversionType == Short.class) {
            return ((PluginAttribute)this.annotation).defaultShort();
        }
        if (this.conversionType == Class.class) {
            return ((PluginAttribute)this.annotation).defaultClass();
        }
        return this.substitutor.replace(logEvent, ((PluginAttribute)this.annotation).defaultString());
    }
}

