/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.builder.api;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Component {
    private final Map<String, String> attributes = new LinkedHashMap<String, String>();
    private final List<Component> components = new ArrayList<Component>();
    private final String pluginType;
    private final String value;

    public Component(String string) {
        this(string, null, null);
    }

    public Component(String string, String string2) {
        this(string, string2, null);
    }

    public Component(String string, String string2, String string3) {
        this.pluginType = string;
        this.value = string3;
        if (string2 != null && string2.length() > 0) {
            this.attributes.put("name", string2);
        }
    }

    public Component() {
        this.pluginType = null;
        this.value = null;
    }

    public String addAttribute(String string, String string2) {
        return this.attributes.put(string, string2);
    }

    public void addComponent(Component component) {
        this.components.add(component);
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public List<Component> getComponents() {
        return this.components;
    }

    public String getPluginType() {
        return this.pluginType;
    }

    public String getValue() {
        return this.value;
    }
}

