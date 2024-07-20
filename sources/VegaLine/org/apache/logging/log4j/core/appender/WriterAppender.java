/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.io.Writer;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.StringLayout;
import org.apache.logging.log4j.core.appender.AbstractWriterAppender;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.appender.WriterManager;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.util.CloseShieldWriter;

@Plugin(name="Writer", category="Core", elementType="appender", printObject=true)
public final class WriterAppender
extends AbstractWriterAppender<WriterManager> {
    private static WriterManagerFactory factory = new WriterManagerFactory();

    @PluginFactory
    public static WriterAppender createAppender(StringLayout layout, Filter filter, Writer target, String name, boolean follow, boolean ignore) {
        if (name == null) {
            LOGGER.error("No name provided for WriterAppender");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new WriterAppender(name, layout, filter, WriterAppender.getManager(target, follow, layout), ignore);
    }

    private static WriterManager getManager(Writer target, boolean follow, StringLayout layout) {
        CloseShieldWriter writer = new CloseShieldWriter(target);
        String managerName = target.getClass().getName() + "@" + Integer.toHexString(target.hashCode()) + '.' + follow;
        return WriterManager.getManager(managerName, new FactoryData(writer, managerName, layout), factory);
    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    private WriterAppender(String name, StringLayout layout, Filter filter, WriterManager manager, boolean ignoreExceptions) {
        super(name, layout, filter, ignoreExceptions, true, manager);
    }

    private static class WriterManagerFactory
    implements ManagerFactory<WriterManager, FactoryData> {
        private WriterManagerFactory() {
        }

        @Override
        public WriterManager createManager(String name, FactoryData data) {
            return new WriterManager(data.writer, data.name, data.layout, true);
        }
    }

    private static class FactoryData {
        private final StringLayout layout;
        private final String name;
        private final Writer writer;

        public FactoryData(Writer writer, String type2, StringLayout layout) {
            this.writer = writer;
            this.name = type2;
            this.layout = layout;
        }
    }

    public static class Builder
    implements org.apache.logging.log4j.core.util.Builder<WriterAppender> {
        private Filter filter;
        private boolean follow = false;
        private boolean ignoreExceptions = true;
        private StringLayout layout = PatternLayout.createDefaultLayout();
        private String name;
        private Writer target;

        @Override
        public WriterAppender build() {
            return new WriterAppender(this.name, this.layout, this.filter, WriterAppender.getManager(this.target, this.follow, this.layout), this.ignoreExceptions);
        }

        public Builder setFilter(Filter aFilter) {
            this.filter = aFilter;
            return this;
        }

        public Builder setFollow(boolean shouldFollow) {
            this.follow = shouldFollow;
            return this;
        }

        public Builder setIgnoreExceptions(boolean shouldIgnoreExceptions) {
            this.ignoreExceptions = shouldIgnoreExceptions;
            return this;
        }

        public Builder setLayout(StringLayout aLayout) {
            this.layout = aLayout;
            return this;
        }

        public Builder setName(String aName) {
            this.name = aName;
            return this;
        }

        public Builder setTarget(Writer aTarget) {
            this.target = aTarget;
            return this;
        }
    }
}

