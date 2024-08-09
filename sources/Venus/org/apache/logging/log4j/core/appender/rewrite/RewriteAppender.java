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

    private RewriteAppender(String string, Filter filter, boolean bl, AppenderRef[] appenderRefArray, RewritePolicy rewritePolicy, Configuration configuration) {
        super(string, filter, null, bl);
        this.config = configuration;
        this.rewritePolicy = rewritePolicy;
        this.appenderRefs = appenderRefArray;
    }

    @Override
    public void start() {
        for (AppenderRef appenderRef : this.appenderRefs) {
            String string = appenderRef.getRef();
            Object t = this.config.getAppender(string);
            if (t != null) {
                Filter filter = t instanceof AbstractAppender ? ((AbstractAppender)t).getFilter() : null;
                this.appenders.put(string, new AppenderControl((Appender)t, appenderRef.getLevel(), filter));
                continue;
            }
            LOGGER.error("Appender " + appenderRef + " cannot be located. Reference ignored");
        }
        super.start();
    }

    @Override
    public void append(LogEvent logEvent) {
        if (this.rewritePolicy != null) {
            logEvent = this.rewritePolicy.rewrite(logEvent);
        }
        for (AppenderControl appenderControl : this.appenders.values()) {
            appenderControl.callAppender(logEvent);
        }
    }

    @PluginFactory
    public static RewriteAppender createAppender(@PluginAttribute(value="name") String string, @PluginAttribute(value="ignoreExceptions") String string2, @PluginElement(value="AppenderRef") AppenderRef[] appenderRefArray, @PluginConfiguration Configuration configuration, @PluginElement(value="RewritePolicy") RewritePolicy rewritePolicy, @PluginElement(value="Filter") Filter filter) {
        boolean bl = Booleans.parseBoolean(string2, true);
        if (string == null) {
            LOGGER.error("No name provided for RewriteAppender");
            return null;
        }
        if (appenderRefArray == null) {
            LOGGER.error("No appender references defined for RewriteAppender");
            return null;
        }
        return new RewriteAppender(string, filter, bl, appenderRefArray, rewritePolicy, configuration);
    }
}

