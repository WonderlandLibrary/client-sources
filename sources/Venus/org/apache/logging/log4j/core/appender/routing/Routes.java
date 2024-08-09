/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.routing;

import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import javax.script.Bindings;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.routing.Route;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.script.AbstractScript;
import org.apache.logging.log4j.core.script.ScriptManager;
import org.apache.logging.log4j.status.StatusLogger;

@Plugin(name="Routes", category="Core", printObject=true)
public final class Routes {
    private static final String LOG_EVENT_KEY = "logEvent";
    private static final Logger LOGGER = StatusLogger.getLogger();
    private final Configuration configuration;
    private final String pattern;
    private final AbstractScript patternScript;
    private final Route[] routes;

    @Deprecated
    public static Routes createRoutes(String string, Route ... routeArray) {
        if (routeArray == null || routeArray.length == 0) {
            LOGGER.error("No routes configured");
            return null;
        }
        return new Routes(null, null, string, routeArray);
    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    private Routes(Configuration configuration, AbstractScript abstractScript, String string, Route ... routeArray) {
        this.configuration = configuration;
        this.patternScript = abstractScript;
        this.pattern = string;
        this.routes = routeArray;
    }

    public String getPattern(LogEvent logEvent, ConcurrentMap<Object, Object> concurrentMap) {
        if (this.patternScript != null) {
            ScriptManager scriptManager = this.configuration.getScriptManager();
            Bindings bindings = scriptManager.createBindings(this.patternScript);
            bindings.put("staticVariables", (Object)concurrentMap);
            bindings.put(LOG_EVENT_KEY, (Object)logEvent);
            Object object = scriptManager.execute(this.patternScript.getName(), bindings);
            bindings.remove(LOG_EVENT_KEY);
            return Objects.toString(object, null);
        }
        return this.pattern;
    }

    public AbstractScript getPatternScript() {
        return this.patternScript;
    }

    public Route getRoute(String string) {
        for (Route route : this.routes) {
            if (!Objects.equals(route.getKey(), string)) continue;
            return route;
        }
        return null;
    }

    public Route[] getRoutes() {
        return this.routes;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");
        boolean bl = true;
        for (Route route : this.routes) {
            if (!bl) {
                stringBuilder.append(',');
            }
            bl = false;
            stringBuilder.append(route.toString());
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    static Logger access$000() {
        return LOGGER;
    }

    Routes(Configuration configuration, AbstractScript abstractScript, String string, Route[] routeArray, 1 var5_5) {
        this(configuration, abstractScript, string, routeArray);
    }

    static class 1 {
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    implements org.apache.logging.log4j.core.util.Builder<Routes> {
        @PluginConfiguration
        private Configuration configuration;
        @PluginAttribute(value="pattern")
        private String pattern;
        @PluginElement(value="Script")
        private AbstractScript patternScript;
        @PluginElement(value="Routes")
        @Required
        private Route[] routes;

        @Override
        public Routes build() {
            if (this.routes == null || this.routes.length == 0) {
                Routes.access$000().error("No Routes configured.");
                return null;
            }
            if (this.patternScript != null && this.pattern != null) {
                Routes.access$000().warn("In a Routes element, you must configure either a Script element or a pattern attribute.");
            }
            if (this.patternScript != null) {
                if (this.configuration == null) {
                    Routes.access$000().error("No Configuration defined for Routes; required for Script");
                } else {
                    this.configuration.getScriptManager().addScript(this.patternScript);
                }
            }
            return new Routes(this.configuration, this.patternScript, this.pattern, this.routes, null);
        }

        public Configuration getConfiguration() {
            return this.configuration;
        }

        public String getPattern() {
            return this.pattern;
        }

        public AbstractScript getPatternScript() {
            return this.patternScript;
        }

        public Route[] getRoutes() {
            return this.routes;
        }

        public Builder withConfiguration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public Builder withPattern(String string) {
            this.pattern = string;
            return this;
        }

        public Builder withPatternScript(AbstractScript abstractScript) {
            this.patternScript = abstractScript;
            return this;
        }

        public Builder withRoutes(Route[] routeArray) {
            this.routes = routeArray;
            return this;
        }

        @Override
        public Object build() {
            return this.build();
        }
    }
}

