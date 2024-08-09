/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net.ssl;

public class StoreConfigurationException
extends Exception {
    private static final long serialVersionUID = 1L;

    public StoreConfigurationException(Exception exception) {
        super(exception);
    }

    public StoreConfigurationException(String string) {
        super(string);
    }
}

