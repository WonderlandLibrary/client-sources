/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net.ssl;

import org.apache.logging.log4j.core.net.ssl.StoreConfigurationException;

public class TrustStoreConfigurationException
extends StoreConfigurationException {
    private static final long serialVersionUID = 1L;

    public TrustStoreConfigurationException(Exception e) {
        super(e);
    }
}

