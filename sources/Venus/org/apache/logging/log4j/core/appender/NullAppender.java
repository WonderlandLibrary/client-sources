/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name="Null", category="Core", elementType="appender", printObject=true)
public class NullAppender
extends AbstractAppender {
    public static final String PLUGIN_NAME = "Null";

    @PluginFactory
    public static NullAppender createAppender(@PluginAttribute(value="name") String string) {
        return new NullAppender(string);
    }

    private NullAppender(String string) {
        super(string, null, null);
    }

    @Override
    public void append(LogEvent logEvent) {
    }
}

