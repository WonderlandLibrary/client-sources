/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.io.Serializable;
import java.util.Objects;
import javax.script.Bindings;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AppenderSet;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.script.AbstractScript;
import org.apache.logging.log4j.core.script.ScriptManager;

@Plugin(name="ScriptAppenderSelector", category="Core", elementType="appender", printObject=true)
public class ScriptAppenderSelector
extends AbstractAppender {
    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    private ScriptAppenderSelector(String string, Filter filter, Layout<? extends Serializable> layout) {
        super(string, filter, layout);
    }

    @Override
    public void append(LogEvent logEvent) {
    }

    static Logger access$000() {
        return LOGGER;
    }

    static Logger access$100() {
        return LOGGER;
    }

    static Logger access$200() {
        return LOGGER;
    }

    static Logger access$300() {
        return LOGGER;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class Builder
    implements org.apache.logging.log4j.core.util.Builder<Appender> {
        @PluginElement(value="AppenderSet")
        @Required
        private AppenderSet appenderSet;
        @PluginConfiguration
        @Required
        private Configuration configuration;
        @PluginBuilderAttribute
        @Required
        private String name;
        @PluginElement(value="Script")
        @Required
        private AbstractScript script;

        @Override
        public Appender build() {
            if (this.name == null) {
                ScriptAppenderSelector.access$000().error("Name missing.");
                return null;
            }
            if (this.script == null) {
                ScriptAppenderSelector.access$100().error("Script missing for ScriptAppenderSelector appender {}", (Object)this.name);
                return null;
            }
            if (this.appenderSet == null) {
                ScriptAppenderSelector.access$200().error("AppenderSet missing for ScriptAppenderSelector appender {}", (Object)this.name);
                return null;
            }
            if (this.configuration == null) {
                ScriptAppenderSelector.access$300().error("Configuration missing for ScriptAppenderSelector appender {}", (Object)this.name);
                return null;
            }
            ScriptManager scriptManager = this.configuration.getScriptManager();
            scriptManager.addScript(this.script);
            Bindings bindings = scriptManager.createBindings(this.script);
            Object object = scriptManager.execute(this.script.getName(), bindings);
            String string = Objects.toString(object, null);
            Appender appender = this.appenderSet.createAppender(string, this.name);
            return appender;
        }

        public AppenderSet getAppenderSet() {
            return this.appenderSet;
        }

        public Configuration getConfiguration() {
            return this.configuration;
        }

        public String getName() {
            return this.name;
        }

        public AbstractScript getScript() {
            return this.script;
        }

        public Builder withAppenderNodeSet(AppenderSet appenderSet) {
            this.appenderSet = appenderSet;
            return this;
        }

        public Builder withConfiguration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public Builder withName(String string) {
            this.name = string;
            return this;
        }

        public Builder withScript(AbstractScript abstractScript) {
            this.script = abstractScript;
            return this;
        }

        @Override
        public Object build() {
            return this.build();
        }
    }
}

