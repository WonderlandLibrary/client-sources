/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.plugins.processor;

import java.io.Serializable;

public class PluginEntry
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String key;
    private String className;
    private String name;
    private boolean printable;
    private boolean defer;
    private transient String category;

    public String getKey() {
        return this.key;
    }

    public void setKey(String string) {
        this.key = string;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String string) {
        this.className = string;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String string) {
        this.name = string;
    }

    public boolean isPrintable() {
        return this.printable;
    }

    public void setPrintable(boolean bl) {
        this.printable = bl;
    }

    public boolean isDefer() {
        return this.defer;
    }

    public void setDefer(boolean bl) {
        this.defer = bl;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String string) {
        this.category = string;
    }

    public String toString() {
        return "PluginEntry [key=" + this.key + ", className=" + this.className + ", name=" + this.name + ", printable=" + this.printable + ", defer=" + this.defer + ", category=" + this.category + "]";
    }
}

