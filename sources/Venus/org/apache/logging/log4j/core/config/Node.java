/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.core.config.plugins.util.PluginType;

public class Node {
    public static final String CATEGORY = "Core";
    private final Node parent;
    private final String name;
    private String value;
    private final PluginType<?> type;
    private final Map<String, String> attributes = new HashMap<String, String>();
    private final List<Node> children = new ArrayList<Node>();
    private Object object;

    public Node(Node node, String string, PluginType<?> pluginType) {
        this.parent = node;
        this.name = string;
        this.type = pluginType;
    }

    public Node() {
        this.parent = null;
        this.name = null;
        this.type = null;
    }

    public Node(Node node) {
        this.parent = node.parent;
        this.name = node.name;
        this.type = node.type;
        this.attributes.putAll(node.getAttributes());
        this.value = node.getValue();
        for (Node node2 : node.getChildren()) {
            this.children.add(new Node(node2));
        }
        this.object = node.object;
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public List<Node> getChildren() {
        return this.children;
    }

    public boolean hasChildren() {
        return !this.children.isEmpty();
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String string) {
        this.value = string;
    }

    public Node getParent() {
        return this.parent;
    }

    public String getName() {
        return this.name;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public <T> T getObject() {
        return (T)this.object;
    }

    public <T> T getObject(Class<T> clazz) {
        return clazz.cast(this.object);
    }

    public boolean isInstanceOf(Class<?> clazz) {
        return clazz.isInstance(this.object);
    }

    public PluginType<?> getType() {
        return this.type;
    }

    public String toString() {
        if (this.object == null) {
            return "null";
        }
        return this.type.isObjectPrintable() ? this.object.toString() : this.type.getPluginClass().getName() + " with name " + this.name;
    }
}

