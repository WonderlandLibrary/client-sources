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
import org.apache.logging.log4j.Logger;
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

    private RoutingAppender(String string, Filter filter, boolean bl, Routes routes, RewritePolicy rewritePolicy, Configuration configuration, PurgePolicy purgePolicy, AbstractScript abstractScript) {
        super(string, filter, null, bl);
        this.routes = routes;
        this.configuration = configuration;
        this.rewritePolicy = rewritePolicy;
        this.purgePolicy = purgePolicy;
        if (this.purgePolicy != null) {
            this.purgePolicy.initialize(this);
        }
        this.defaultRouteScript = abstractScript;
        Route route = null;
        for (Route route2 : routes.getRoutes()) {
            if (route2.getKey() != null) continue;
            if (route == null) {
                route = route2;
                continue;
            }
            this.error("Multiple default routes. Route " + route2.toString() + " will be ignored");
        }
        this.defaultRoute = route;
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
                Route object2 = this.routes.getRoute(Objects.toString(object, null));
                if (object2 != null) {
                    this.defaultRoute = object2;
                }
            }
        }
        for (Route route : this.routes.getRoutes()) {
            if (route.getAppenderRef() == null) continue;
            Object t = this.configuration.getAppender(route.getAppenderRef());
            if (t != null) {
                String string = route == this.defaultRoute ? DEFAULT_KEY : route.getKey();
                this.appenders.put(string, new AppenderControl((Appender)t, null, null));
                continue;
            }
            this.error("Appender " + route.getAppenderRef() + " cannot be located. Route ignored");
        }
        super.start();
    }

    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        this.setStopping();
        super.stop(l, timeUnit, false);
        Map<String, Appender> map = this.configuration.getAppenders();
        for (Map.Entry entry : this.appenders.entrySet()) {
            Appender appender = ((AppenderControl)entry.getValue()).getAppender();
            if (map.containsKey(appender.getName())) continue;
            if (appender instanceof LifeCycle2) {
                ((LifeCycle2)((Object)appender)).stop(l, timeUnit);
                continue;
            }
            appender.stop();
        }
        this.setStopped();
        return false;
    }

    @Override
    public void append(LogEvent logEvent) {
        String string;
        String string2;
        AppenderControl appenderControl;
        if (this.rewritePolicy != null) {
            logEvent = this.rewritePolicy.rewrite(logEvent);
        }
        if ((appenderControl = this.getControl(string2 = (string = this.routes.getPattern(logEvent, this.scriptStaticVariables)) != null ? this.configuration.getStrSubstitutor().replace(logEvent, string) : this.defaultRoute.getKey(), logEvent)) != null) {
            appenderControl.callAppender(logEvent);
        }
        if (this.purgePolicy != null) {
            this.purgePolicy.update(string2, logEvent);
        }
    }

    private synchronized AppenderControl getControl(String string, LogEvent logEvent) {
        AppenderControl appenderControl = (AppenderControl)this.appenders.get(string);
        if (appenderControl != null) {
            return appenderControl;
        }
        Route route = null;
        for (Route route2 : this.routes.getRoutes()) {
            if (route2.getAppenderRef() != null || !string.equals(route2.getKey())) continue;
            route = route2;
            break;
        }
        if (route == null) {
            route = this.defaultRoute;
            appenderControl = (AppenderControl)this.appenders.get(DEFAULT_KEY);
            if (appenderControl != null) {
                return appenderControl;
            }
        }
        if (route != null) {
            Appender appender = this.createAppender(route, logEvent);
            if (appender == null) {
                return null;
            }
            appenderControl = new AppenderControl(appender, null, null);
            this.appenders.put(string, appenderControl);
        }
        return appenderControl;
    }

    private Appender createAppender(Route route, LogEvent logEvent) {
        Node node = route.getNode();
        for (Node node2 : node.getChildren()) {
            if (!node2.getType().getElementName().equals("appender")) continue;
            Node node3 = new Node(node2);
            this.configuration.createConfiguration(node3, logEvent);
            if (node3.getObject() instanceof Appender) {
                Appender appender = (Appender)node3.getObject();
                appender.start();
                return appender;
            }
            this.error("Unable to create Appender of type " + node2.getName());
            return null;
        }
        this.error("No Appender was configured for route " + route.getKey());
        return null;
    }

    public Map<String, AppenderControl> getAppenders() {
        return Collections.unmodifiableMap(this.appenders);
    }

    public void deleteAppender(String string) {
        LOGGER.debug("Deleting route with " + string + " key ");
        AppenderControl appenderControl = (AppenderControl)this.appenders.remove(string);
        if (null != appenderControl) {
            LOGGER.debug("Stopping route with " + string + " key");
            appenderControl.getAppender().stop();
        } else {
            LOGGER.debug("Route with " + string + " key already deleted");
        }
    }

    @Deprecated
    public static RoutingAppender createAppender(String string, String string2, Routes routes, Configuration configuration, RewritePolicy rewritePolicy, PurgePolicy purgePolicy, Filter filter) {
        boolean bl = Booleans.parseBoolean(string2, true);
        if (string == null) {
            LOGGER.error("No name provided for RoutingAppender");
            return null;
        }
        if (routes == null) {
            LOGGER.error("No routes defined for RoutingAppender");
            return null;
        }
        return new RoutingAppender(string, filter, bl, routes, rewritePolicy, configuration, purgePolicy, null);
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

    static Logger access$000() {
        return LOGGER;
    }

    static Logger access$100() {
        return LOGGER;
    }

    RoutingAppender(String string, Filter filter, boolean bl, Routes routes, RewritePolicy rewritePolicy, Configuration configuration, PurgePolicy purgePolicy, AbstractScript abstractScript, 1 var9_9) {
        this(string, filter, bl, routes, rewritePolicy, configuration, purgePolicy, abstractScript);
    }

    static class 1 {
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
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
            String string = this.getName();
            if (string == null) {
                RoutingAppender.access$000().error("No name defined for this RoutingAppender");
                return null;
            }
            if (this.routes == null) {
                RoutingAppender.access$100().error("No routes defined for RoutingAppender {}", (Object)string);
                return null;
            }
            return new RoutingAppender(string, this.getFilter(), this.isIgnoreExceptions(), this.routes, this.rewritePolicy, this.getConfiguration(), this.purgePolicy, this.defaultRouteScript, null);
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

        public B withDefaultRouteScript(AbstractScript abstractScript) {
            this.defaultRouteScript = abstractScript;
            return (B)((Builder)this.asBuilder());
        }

        public B withRewritePolicy(RewritePolicy rewritePolicy) {
            this.rewritePolicy = rewritePolicy;
            return (B)((Builder)this.asBuilder());
        }

        public void withPurgePolicy(PurgePolicy purgePolicy) {
            this.purgePolicy = purgePolicy;
        }

        @Override
        public Object build() {
            return this.build();
        }
    }
}

