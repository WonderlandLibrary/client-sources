/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.jmx;

public interface ContextSelectorAdminMBean {
    public static final String PATTERN = "org.apache.logging.log4j2:type=%s,component=ContextSelector";

    public String getImplementationClassName();
}

