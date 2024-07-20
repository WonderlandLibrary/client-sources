/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net.ssl;

import org.apache.logging.log4j.core.net.ssl.StoreConfigurationException;

public class KeyStoreConfigurationException
extends StoreConfigurationException {
    private static final long serialVersionUID = 1L;

    public KeyStoreConfigurationException(Exception e) {
        super(e);
    }
}

