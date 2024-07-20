/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.lookup;

import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationAware;
import org.apache.logging.log4j.core.lookup.AbstractLookup;

public abstract class AbstractConfigurationAwareLookup
extends AbstractLookup
implements ConfigurationAware {
    protected Configuration configuration;

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}

