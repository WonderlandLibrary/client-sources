/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.routing;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import javax.script.Bindings;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LifeCycle2;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.rewrite.RewritePolicy;
import org.apache.logging.log4j.core.appender.routing.PurgePolicy;
import org.apache.logging.log4j.core.appender.routing.Route;
import org.apache.logging.log4j.core.appender.routing.Routes;
import org.apache.logging.log4j.core.config.AppenderControl;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.script.AbstractScript;
import org.apache.logging.log4j.core.script.ScriptManager;
import org.apache.logging.log4j.core.util.Booleans;

@Plugin(name="Routing", category="Core", elementType="appender", printObject=true)
public final class RoutingAppender
extends AbstractAppender {
    public static final String STATIC_VARIABLES_KEY = "staticVariables";
    private static final String DEFAULT_KEY = "ROUTING_APPENDER_DEFAULT";
    private final Routes routes;
    private Route defaultRoute;
    private final Configuration configuration;
    private final ConcurrentMap<String, AppenderControl> appenders = new ConcurrentHashMap<String, AppenderControl>();
    private final RewritePolicy rewritePolicy;
    private final PurgePolicy purgePolicy;
    private final AbstractScript defaultRouteScript;
    private final ConcurrentMap<Object, Object> scriptStaticVariables = new ConcurrentHashMap<Object, Object>();

    @PluginBuilderFactory
    public static <B extends Builder<B>> B newBuilder() {
        return (B)((Builder)new Builder().asBuilder());
    }

    private RoutingAppender(String name, Filter filter, boolean ignoreExceptions, Routes routes, RewritePolicy rewritePolicy, Configuration configuration, PurgePolicy purgePolicy, AbstractScript defaultRouteScript) {
        super(name, filter, null, ignoreExceptions);
        this.routes = routes;
        this.configuration = configuration;
        this.rewritePolicy = rewritePolicy;
        this.purgePolicy = purgePolicy;
        if (this.purgePolicy != null) {
            this.purgePolicy.initialize(this);
        }
        this.defaultRouteScript = defaultRouteScript;
        Route defRoute = null;
        for (Route route : routes.getRoutes()) {
            if (route.getKey() != null) continue;
            if (defRoute == null) {
                defRoute = route;
                continue;
            }
            this.error("Multiple default routes. Route " + route.toString() + " will be ignored");
        }
        this.defaultRoute = defRoute;
    }

    @Override
    public void start() {
        if (this.defaultRouteScript != null) {
            if (this.configuration == null) {
                this.error("No Configuration defined for RoutingAppender; required for Script element.");
            } else {
                ScriptManager scriptManager = this.configuration.getScriptManager();
                scriptManager.addScript(this.defaultRouteScript);
                Bindings bindings = scriptManager.createBindings(this.defaultRouteScript);
                bindings.put(STATIC_VARIABLES_KEY, (Object)this.scriptStaticVariables);
                Object object = scriptManager.execute(this.defaultRouteScript.getName(), bindings);
                Route route = this.routes.getRoute(Objects.toString(object, null));
                if (route != null) {
                    this.defaultRoute = route;
                }
            }
        }
        for (Route route : this.routes.getRoutes()) {
            if (route.getAppenderRef() == null) continue;
            Object appender = this.configuration.getAppender(route.getAppenderRef());
            if (appender != null) {
                String key = route == this.defaultRoute ? DEFAULT_KEY : route.getKey();
                this.appenders.put(key, new AppenderControl((Appender)appender, null, null));
                continue;
            }
            this.error("Appender " + route.getAppenderRef() + " cannot be located. Route ignored");
        }
        super.start();
    }

    @Override
    public boolean stop(long timeout, TimeUnit timeUnit) {
        this.setStopping();
        super.stop(timeout, timeUnit, false);
        Map<String, Appender> map = this.configuration.getAppenders();
        for (Map.Entry entry : this.appenders.entrySet()) {
            Appender appender = ((AppenderControl)entry.getValue()).getAppender();
            if (map.containsKey(appender.getName())) continue;
            if (appender instanceof LifeCycle2) {
                ((LifeCycle2)((Object)appender)).stop(timeout, timeUnit);
                continue;
            }
            appender.stop();
        }
        this.setStopped();
        return true;
    }

    @Override
    public void append(LogEvent event) {
        String pattern;
        String key;
        AppenderControl control;
        if (this.rewritePolicy != null) {
            event = this.rewritePolicy.rewrite(event);
        }
        if ((control = this.getControl(key = (pattern = this.routes.getPattern(event, this.scriptStaticVariables)) != null ? this.configuration.getStrSubstitutor().replace(event, pattern) : this.defaultRoute.getKey(), event)) != null) {
            control.callAppender(event);
        }
        if (this.purgePolicy != null) {
            this.purgePolicy.update(key, event);
        }
    }

    private synchronized AppenderControl getControl(String key, LogEvent event) {
        AppenderControl control = (AppenderControl)this.appenders.get(key);
        if (control != null) {
            return control;
        }
        Route route = null;
        for (Route r : this.routes.getRoutes()) {
            if (r.getAppenderRef() != null || !key.equals(r.getKey())) continue;
            route = r;
            break;
        }
        if (route == null) {
            route = this.defaultRoute;
            control = (AppenderControl)this.appenders.get(DEFAULT_KEY);
            if (control != null) {
                return control;
            }
        }
        if (route != null) {
            Appender app = this.createAppender(route, event);
            if (app == null) {
                return null;
            }
            control = new AppenderControl(app, null, null);
            this.appenders.put(key, control);
        }
        return control;
    }

    private Appender createAppender(Route route, LogEvent event) {
        Node routeNode = route.getNode();
        for (Node node : routeNode.getChildren()) {
            if (!node.getType().getElementName().equals("appender")) continue;
            Node appNode = new Node(node);
            this.configuration.createConfiguration(appNode, event);
            if (appNode.getObject() instanceof Appender) {
                Appender app = (Appender)appNode.getObject();
                app.start();
                return app;
            }
            this.error("Unable to create Appender of type " + node.getName());
            return null;
        }
        this.error("No Appender was configured for route " + route.getKey());
        return null;
    }

    public Map<String, AppenderControl> getAppenders() {
        return Collections.unmodifiableMap(this.appenders);
    }

    public void deleteAppender(String key) {
        LOGGER.debug("Deleting route with " + key + " key ");
        AppenderControl control = (AppenderControl)this.appenders.remove(key);
        if (null != control) {
            LOGGER.debug("Stopping route with " + key + " key");
            control.getAppender().stop();
        } else {
            LOGGER.debug("Route with " + key + " key already deleted");
        }
    }

    @Deprecated
    public static RoutingAppender createAppender(String name, String ignore, Routes routes, Configuration config, RewritePolicy rewritePolicy, PurgePolicy purgePolicy, Filter filter) {
        boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
        if (name == null) {
            LOGGER.error("No name provided for RoutingAppender");
            return null;
        }
        if (routes == null) {
            LOGGER.error("No routes defined for RoutingAppender");
            return null;
        }
        return new RoutingAppender(name, filter, ignoreExceptions, routes, rewritePolicy, config, purgePolicy, null);
    }

    public Route getDefaultRoute() {
        return this.defaultRoute;
    }

    public AbstractScript getDefaultRouteScript() {
        return this.defaultRouteScript;
    }

    public PurgePolicy getPurgePolicy() {
        return this.purgePolicy;
    }

    public RewritePolicy getRewritePolicy() {
        return this.rewritePolicy;
    }

    public Routes getRoutes() {
        return this.routes;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public ConcurrentMap<Object, Object> getScriptStaticVariables() {
        return this.scriptStaticVariables;
    }

    public static class Builder<B extends Builder<B>>
    extends AbstractAppender.Builder<B>
    implements org.apache.logging.log4j.core.util.Builder<RoutingAppender> {
        @PluginElement(value="Script")
        private AbstractScript defaultRouteScript;
        @PluginElement(value="Routes")
        private Routes routes;
        @PluginElement(value="RewritePolicy")
        private RewritePolicy rewritePolicy;
        @PluginElement(value="PurgePolicy")
        private PurgePolicy purgePolicy;

        @Override
        public RoutingAppender build() {
            String name = this.getName();
            if (name == null) {
                LOGGER.error("No name defined for this RoutingAppender");
                return null;
            }
            if (this.routes == null) {
                LOGGER.error("No routes defined for RoutingAppender {}", (Object)name);
                return null;
            }
            return new RoutingAppender(name, this.getFilter(), this.isIgnoreExceptions(), this.routes, this.rewritePolicy, this.getConfiguration(), this.purgePolicy, this.defaultRouteScript);
        }

        public Routes getRoutes() {
            return this.routes;
        }

        public AbstractScript getDefaultRouteScript() {
            return this.defaultRouteScript;
        }

        public RewritePolicy getRewritePolicy() {
            return this.rewritePolicy;
        }

        public PurgePolicy getPurgePolicy() {
            return this.purgePolicy;
        }

        public B withRoutes(Routes routes) {
            this.routes = routes;
            return (B)((Builder)this.asBuilder());
        }

        public B withDefaultRouteScript(AbstractScript defaultRouteScript) {
            this.defaultRouteScript = defaultRouteScript;
            return (B)((Builder)this.asBuilder());
        }

        public B withRewritePolicy(RewritePolicy rewritePolicy) {
            this.rewritePolicy = rewritePolicy;
            return (B)((Builder)this.asBuilder());
        }

        public void withPurgePolicy(PurgePolicy purgePolicy) {
            this.purgePolicy = purgePolicy;
        }
    }
}

