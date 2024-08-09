/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.builder.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.builder.api.Component;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class DefaultComponentBuilder<T extends ComponentBuilder<T>, CB extends ConfigurationBuilder<? extends Configuration>>
implements ComponentBuilder<T> {
    private final CB builder;
    private final String type;
    private final Map<String, String> attributes = new LinkedHashMap<String, String>();
    private final List<Component> components = new ArrayList<Component>();
    private final String name;
    private final String value;

    public DefaultComponentBuilder(CB CB, String string) {
        this(CB, null, string, null);
    }

    public DefaultComponentBuilder(CB CB, String string, String string2) {
        this(CB, string, string2, null);
    }

    public DefaultComponentBuilder(CB CB, String string, String string2, String string3) {
        this.type = string2;
        this.builder = CB;
        this.name = string;
        this.value = string3;
    }

    @Override
    public T addAttribute(String string, boolean bl) {
        return this.put(string, Boolean.toString(bl));
    }

    @Override
    public T addAttribute(String string, Enum<?> enum_) {
        return this.put(string, enum_.name());
    }

    @Override
    public T addAttribute(String string, int n) {
        return this.put(string, Integer.toString(n));
    }

    @Override
    public T addAttribute(String string, Level level) {
        return this.put(string, level.toString());
    }

    @Override
    public T addAttribute(String string, Object object) {
        return this.put(string, object.toString());
    }

    @Override
    public T addAttribute(String string, String string2) {
        return this.put(string, string2);
    }

    @Override
    public T addComponent(ComponentBuilder<?> componentBuilder) {
        this.components.add((Component)componentBuilder.build());
        return (T)this;
    }

    @Override
    public Component build() {
        Component component = new Component(this.type, this.name, this.value);
        component.getAttributes().putAll(this.attributes);
        component.getComponents().addAll(this.components);
        return component;
    }

    public CB getBuilder() {
        return this.builder;
    }

    @Override
    public String getName() {
        return this.name;
    }

    protected T put(String string, String string2) {
        this.attributes.put(string, string2);
        return (T)this;
    }

    @Override
    public Object build() {
        return this.build();
    }
}

