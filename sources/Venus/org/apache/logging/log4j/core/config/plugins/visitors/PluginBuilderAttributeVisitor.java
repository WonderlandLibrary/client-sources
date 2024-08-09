/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.plugins.visitors;

import java.util.Map;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.visitors.AbstractPluginVisitor;
import org.apache.logging.log4j.core.util.NameUtil;
import org.apache.logging.log4j.util.StringBuilders;

public class PluginBuilderAttributeVisitor
extends AbstractPluginVisitor<PluginBuilderAttribute> {
    public PluginBuilderAttributeVisitor() {
        super(PluginBuilderAttribute.class);
    }

    @Override
    public Object visit(Configuration configuration, Node node, LogEvent logEvent, StringBuilder stringBuilder) {
        String string = ((PluginBuilderAttribute)this.annotation).value();
        String string2 = string.isEmpty() ? this.member.getName() : string;
        Map<String, String> map = node.getAttributes();
        String string3 = PluginBuilderAttributeVisitor.removeAttributeValue(map, string2, this.aliases);
        String string4 = this.substitutor.replace(logEvent, string3);
        Object object = this.convert(string4, null);
        Object object2 = ((PluginBuilderAttribute)this.annotation).sensitive() ? NameUtil.md5(object + this.getClass().getName()) : object;
        StringBuilders.appendKeyDqValue(stringBuilder, string2, object2);
        return object;
    }
}

