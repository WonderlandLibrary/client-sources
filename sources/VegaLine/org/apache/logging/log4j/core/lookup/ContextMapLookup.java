/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.lookup;

import org.apache.logging.log4j.core.ContextDataInjector;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.impl.ContextDataInjectorFactory;
import org.apache.logging.log4j.core.lookup.StrLookup;
import org.apache.logging.log4j.util.ReadOnlyStringMap;

@Plugin(name="ctx", category="Lookup")
public class ContextMapLookup
implements StrLookup {
    private final ContextDataInjector injector = ContextDataInjectorFactory.createInjector();

    @Override
    public String lookup(String key) {
        return (String)this.currentContextData().getValue(key);
    }

    private ReadOnlyStringMap currentContextData() {
        return this.injector.rawContextData();
    }

    @Override
    public String lookup(LogEvent event, String key) {
        return (String)event.getContextData().getValue(key);
    }
}

