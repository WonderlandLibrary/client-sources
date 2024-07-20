/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

public interface ManagerFactory<M, T> {
    public M createManager(String var1, T var2);
}

