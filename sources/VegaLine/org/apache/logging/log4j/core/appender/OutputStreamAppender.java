/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.io.OutputStream;
import java.io.Serializable;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.appender.AbstractOutputStreamAppender;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.appender.OutputStreamManager;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.util.CloseShieldOutputStream;

@Plugin(name="OutputStream", category="Core", elementType="appender", printObject=true)
public final class OutputStreamAppender
extends AbstractOutputStreamAppender<OutputStreamManager> {
    private static OutputStreamManagerFactory factory = new OutputStreamManagerFactory();

    @PluginFactory
    public static OutputStreamAppender createAppender(Layout<? extends Serializable> layout, Filter filter, OutputStream target, String name, boolean follow, boolean ignore) {
        if (name == null) {
            LOGGER.error("No name provided for OutputStreamAppender");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new OutputStreamAppender(name, layout, filter, OutputStreamAppender.getManager(target, follow, layout), ignore);
    }

    private static OutputStreamManager getManager(OutputStream target, boolean follow, Layout<? extends Serializable> layout) {
        CloseShieldOutputStream os = new CloseShieldOutputStream(target);
        String managerName = target.getClass().getName() + "@" + Integer.toHexString(target.hashCode()) + '.' + follow;
        return OutputStreamManager.getManager(managerName, new FactoryData(os, managerName, layout), factory);
    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    private OutputStreamAppender(String name, Layout<? extends Serializable> layout, Filter filter, OutputStreamManager manager, boolean ignoreExceptions) {
        super(name, layout, filter, ignoreExceptions, true, manager);
    }

    private static class OutputStreamManagerFactory
    implements ManagerFactory<OutputStreamManager, FactoryData> {
        private OutputStreamManagerFactory() {
        }

        @Override
        public OutputStreamManager createManager(String name, FactoryData data) {
            return new OutputStreamManager(data.os, data.name, data.layout, true);
        }
    }

    private static class FactoryData {
        private final Layout<? extends Serializable> layout;
        private final String name;
        private final OutputStream os;

        public FactoryData(OutputStream os, String type2, Layout<? extends Serializable> layout) {
            this.os = os;
            this.name = type2;
            this.layout = layout;
        }
    }

    public static class Builder
    implements org.apache.logging.log4j.core.util.Builder<OutputStreamAppender> {
        private Filter filter;
        private boolean follow = false;
        private boolean ignoreExceptions = true;
        private Layout<? extends Serializable> layout = PatternLayout.createDefaultLayout();
        private String name;
        private OutputStream target;

        @Override
        public OutputStreamAppender build() {
            return new OutputStreamAppender(this.name, this.layout, this.filter, OutputStreamAppender.getManager(this.target, this.follow, this.layout), this.ignoreExceptions);
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

        public Builder setLayout(Layout<? extends Serializable> aLayout) {
            this.layout = aLayout;
            return this;
        }

        public Builder setName(String aName) {
            this.name = aName;
            return this;
        }

        public Builder setTarget(OutputStream aTarget) {
            this.target = aTarget;
            return this;
        }
    }
}

