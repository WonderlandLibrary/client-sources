/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.io.Serializable;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.ValidPort;
import org.apache.logging.log4j.core.filter.ThresholdFilter;
import org.apache.logging.log4j.core.layout.HtmlLayout;
import org.apache.logging.log4j.core.net.SmtpManager;
import org.apache.logging.log4j.core.util.Booleans;

@Plugin(name="SMTP", category="Core", elementType="appender", printObject=true)
public final class SmtpAppender
extends AbstractAppender {
    private static final int DEFAULT_BUFFER_SIZE = 512;
    private final SmtpManager manager;

    private SmtpAppender(String string, Filter filter, Layout<? extends Serializable> layout, SmtpManager smtpManager, boolean bl) {
        super(string, filter, layout, bl);
        this.manager = smtpManager;
    }

    @PluginFactory
    public static SmtpAppender createAppender(@PluginConfiguration Configuration configuration, @PluginAttribute(value="name") @Required String string, @PluginAttribute(value="to") String string2, @PluginAttribute(value="cc") String string3, @PluginAttribute(value="bcc") String string4, @PluginAttribute(value="from") String string5, @PluginAttribute(value="replyTo") String string6, @PluginAttribute(value="subject") String string7, @PluginAttribute(value="smtpProtocol") String string8, @PluginAttribute(value="smtpHost") String string9, @PluginAttribute(value="smtpPort", defaultString="0") @ValidPort String string10, @PluginAttribute(value="smtpUsername") String string11, @PluginAttribute(value="smtpPassword", sensitive=true) String string12, @PluginAttribute(value="smtpDebug") String string13, @PluginAttribute(value="bufferSize") String string14, @PluginElement(value="Layout") Layout<? extends Serializable> htmlLayout, @PluginElement(value="Filter") Filter filter, @PluginAttribute(value="ignoreExceptions") String string15) {
        Configuration configuration2;
        SmtpManager smtpManager;
        int n;
        if (string == null) {
            LOGGER.error("No name provided for SmtpAppender");
            return null;
        }
        boolean bl = Booleans.parseBoolean(string15, true);
        int n2 = AbstractAppender.parseInt(string10, 0);
        boolean bl2 = Boolean.parseBoolean(string13);
        int n3 = n = string14 == null ? 512 : Integer.parseInt(string14);
        if (htmlLayout == null) {
            htmlLayout = HtmlLayout.createDefaultLayout();
        }
        if (filter == null) {
            filter = ThresholdFilter.createFilter(null, null, null);
        }
        if ((smtpManager = SmtpManager.getSmtpManager(configuration2 = configuration != null ? configuration : new DefaultConfiguration(), string2, string3, string4, string5, string6, string7, string8, string9, n2, string11, string12, bl2, filter.toString(), n)) == null) {
            return null;
        }
        return new SmtpAppender(string, filter, htmlLayout, smtpManager, bl);
    }

    @Override
    public boolean isFiltered(LogEvent logEvent) {
        boolean bl = super.isFiltered(logEvent);
        if (bl) {
            this.manager.add(logEvent);
        }
        return bl;
    }

    @Override
    public void append(LogEvent logEvent) {
        this.manager.sendEvents(this.getLayout(), logEvent);
    }
}

