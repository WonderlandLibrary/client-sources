/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import org.apache.logging.log4j.core.config.Reconfigurable;

public interface ConfigurationListener {
    public void onChange(Reconfigurable var1);
}

