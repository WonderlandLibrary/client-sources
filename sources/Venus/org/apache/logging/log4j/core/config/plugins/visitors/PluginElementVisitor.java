/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.plugins.visitors;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.util.PluginType;
import org.apache.logging.log4j.core.config.plugins.visitors.AbstractPluginVisitor;

public class PluginElementVisitor
extends AbstractPluginVisitor<PluginElement> {
    public PluginElementVisitor() {
        super(PluginElement.class);
    }

    @Override
    public Object visit(Configuration configuration, Node node, LogEvent logEvent, StringBuilder stringBuilder) {
        String string = ((PluginElement)this.annotation).value();
        if (this.conversionType.isArray()) {
            this.setConversionType(this.conversionType.getComponentType());
            ArrayList arrayList = new ArrayList();
            ArrayList<Node> arrayList2 = new ArrayList<Node>();
            stringBuilder.append("={");
            boolean bl = true;
            for (Node node2 : node.getChildren()) {
                PluginType<?> pluginType = node2.getType();
                if (!string.equalsIgnoreCase(pluginType.getElementName()) && !this.conversionType.isAssignableFrom(pluginType.getPluginClass())) continue;
                if (!bl) {
                    stringBuilder.append(", ");
                }
                bl = false;
                arrayList2.add(node2);
                Object t = node2.getObject();
                if (t == null) {
                    LOGGER.error("Null object returned for {} in {}.", (Object)node2.getName(), (Object)node.getName());
                    continue;
                }
                if (t.getClass().isArray()) {
                    stringBuilder.append(Arrays.toString((Object[])t)).append('}');
                    return t;
                }
                stringBuilder.append(node2.toString());
                arrayList.add(t);
            }
            stringBuilder.append('}');
            if (!arrayList.isEmpty() && !this.conversionType.isAssignableFrom(arrayList.get(0).getClass())) {
                LOGGER.error("Attempted to assign attribute {} to list of type {} which is incompatible with {}.", (Object)string, (Object)arrayList.get(0).getClass(), (Object)this.conversionType);
                return null;
            }
            node.getChildren().removeAll(arrayList2);
            Object[] objectArray = (Object[])Array.newInstance(this.conversionType, arrayList.size());
            for (int i = 0; i < objectArray.length; ++i) {
                objectArray[i] = arrayList.get(i);
            }
            return objectArray;
        }
        Node node3 = this.findNamedNode(string, node.getChildren());
        if (node3 == null) {
            stringBuilder.append(string).append("=null");
            return null;
        }
        stringBuilder.append(node3.getName()).append('(').append(node3.toString()).append(')');
        node.getChildren().remove(node3);
        return node3.getObject();
    }

    private Node findNamedNode(String string, Iterable<Node> iterable) {
        for (Node node : iterable) {
            PluginType<?> pluginType = node.getType();
            if (pluginType == null) {
                // empty if block
            }
            if (!string.equalsIgnoreCase(pluginType.getElementName()) && !this.conversionType.isAssignableFrom(pluginType.getPluginClass())) continue;
            return node;
        }
        return null;
    }
}

