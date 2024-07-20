/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net;

public enum Protocol {
    TCP,
    SSL,
    UDP;


    public boolean isEqual(String name) {
        return this.name().equalsIgnoreCase(name);
    }
}

