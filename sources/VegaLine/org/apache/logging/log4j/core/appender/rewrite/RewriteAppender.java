/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rewrite;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.rewrite.RewritePolicy;
import org.apache.logging.log4j.core.config.AppenderControl;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.util.Booleans;

@Plugin(name="Rewrite", category="Core", elementType="appender", printObject=true)
public final class RewriteAppender
extends AbstractAppender {
    private final Configuration config;
    private final ConcurrentMap<String, AppenderControl> appenders = new ConcurrentHashMap<String, AppenderControl>();
    private final RewritePolicy rewritePolicy;
    private final AppenderRef[] appenderRefs;

    private RewriteAppender(String name, Filter filter, boolean ignoreExceptions, AppenderRef[] appenderRefs, RewritePolicy rewritePolicy, Configuration config) {
        super(name, filter, null, ignoreExceptions);
        this.config = config;
        this.rewritePolicy = rewritePolicy;
        this.appenderRefs = appenderRefs;
    }

    @Override
    public void start() {
        for (AppenderRef ref : this.appenderRefs) {
            String name = ref.getRef();
            Object appender = this.config.getAppender(name);
            if (appender != null) {
                Filter filter = appender instanceof AbstractAppender ? ((AbstractAppender)appender).getFilter() : null;
                this.appenders.put(name, new AppenderControl((Appender)appender, ref.getLevel(), filter));
                continue;
            }
            LOGGER.error("Appender " + ref + " cannot be located. Reference ignored");
        }
        super.start();
    }

    @Override
    public void append(LogEvent event) {
        if (this.rewritePolicy != null) {
            event = this.rewritePolicy.rewrite(event);
        }
        for (AppenderControl control : this.appenders.values()) {
            control.callAppender(event);
        }
    }

    @PluginFactory
    public static RewriteAppender createAppender(@PluginAttribute(value="name") String name, @PluginAttribute(value="ignoreExceptions") String ignore, @PluginElement(value="AppenderRef") AppenderRef[] appenderRefs, @PluginConfiguration Configuration config, @PluginElement(value="RewritePolicy") RewritePolicy rewritePolicy, @PluginElement(value="Filter") Filter filter) {
        boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
        if (name == null) {
            LOGGER.error("No name provided for RewriteAppender");
            return null;
        }
        if (appenderRefs == null) {
            LOGGER.error("No appender references defined for RewriteAppender");
            return null;
        }
        return new RewriteAppender(name, filter, ignoreExceptions, appenderRefs, rewritePolicy, config);
    }
}

