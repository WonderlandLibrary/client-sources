/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.plugins.visitors;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.PluginValue;
import org.apache.logging.log4j.core.config.plugins.visitors.AbstractPluginVisitor;
import org.apache.logging.log4j.util.StringBuilders;
import org.apache.logging.log4j.util.Strings;

public class PluginValueVisitor
extends AbstractPluginVisitor<PluginValue> {
    public PluginValueVisitor() {
        super(PluginValue.class);
    }

    @Override
    public Object visit(Configuration configuration, Node node, LogEvent logEvent, StringBuilder stringBuilder) {
        String string = ((PluginValue)this.annotation).value();
        String string2 = node.getValue();
        String string3 = node.getAttributes().get("value");
        String string4 = null;
        if (Strings.isNotEmpty(string2)) {
            if (Strings.isNotEmpty(string3)) {
                LOGGER.error("Configuration contains {} with both attribute value ({}) AND element value ({}). Please specify only one value. Using the element value.", (Object)node.getName(), (Object)string3, (Object)string2);
            }
            string4 = string2;
        } else {
            string4 = PluginValueVisitor.removeAttributeValue(node.getAttributes(), "value", new String[0]);
        }
        String string5 = this.substitutor.replace(logEvent, string4);
        StringBuilders.appendKeyDqValue(stringBuilder, string, string5);
        return string5;
    }
}

