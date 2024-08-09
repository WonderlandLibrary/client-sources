/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.LifeCycle2;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AsyncAppender;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.async.AsyncLoggerConfig;
import org.apache.logging.log4j.core.async.AsyncLoggerConfigDelegate;
import org.apache.logging.log4j.core.async.AsyncLoggerConfigDisruptor;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationListener;
import org.apache.logging.log4j.core.config.ConfigurationScheduler;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.CustomLevelConfig;
import org.apache.logging.log4j.core.config.CustomLevels;
import org.apache.logging.log4j.core.config.DefaultAdvertiser;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.Loggers;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.ReliabilityStrategy;
import org.apache.logging.log4j.core.config.ReliabilityStrategyFactory;
import org.apache.logging.log4j.core.config.Scheduled;
import org.apache.logging.log4j.core.config.plugins.util.PluginBuilder;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.apache.logging.log4j.core.config.plugins.util.PluginType;
import org.apache.logging.log4j.core.filter.AbstractFilterable;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.lookup.Interpolator;
import org.apache.logging.log4j.core.lookup.MapLookup;
import org.apache.logging.log4j.core.lookup.StrLookup;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;
import org.apache.logging.log4j.core.net.Advertiser;
import org.apache.logging.log4j.core.script.AbstractScript;
import org.apache.logging.log4j.core.script.ScriptManager;
import org.apache.logging.log4j.core.script.ScriptRef;
import org.apache.logging.log4j.core.util.DummyNanoClock;
import org.apache.logging.log4j.core.util.Loader;
import org.apache.logging.log4j.core.util.NameUtil;
import org.apache.logging.log4j.core.util.NanoClock;
import org.apache.logging.log4j.core.util.WatchManager;
import org.apache.logging.log4j.util.PropertiesUtil;

public abstract class AbstractConfiguration
extends AbstractFilterable
implements Configuration {
    private static final int BUF_SIZE = 16384;
    protected Node rootNode;
    protected final List<ConfigurationListener> listeners = new CopyOnWriteArrayList<ConfigurationListener>();
    protected final List<String> pluginPackages = new ArrayList<String>();
    protected PluginManager pluginManager;
    protected boolean isShutdownHookEnabled = true;
    protected long shutdownTimeoutMillis = 0L;
    protected ScriptManager scriptManager;
    private Advertiser advertiser = new DefaultAdvertiser();
    private Node advertiserNode = null;
    private Object advertisement;
    private String name;
    private ConcurrentMap<String, Appender> appenders = new ConcurrentHashMap<String, Appender>();
    private ConcurrentMap<String, LoggerConfig> loggerConfigs = new ConcurrentHashMap<String, LoggerConfig>();
    private List<CustomLevelConfig> customLevels = Collections.emptyList();
    private final ConcurrentMap<String, String> properties = new ConcurrentHashMap<String, String>();
    private final StrLookup tempLookup = new Interpolator(this.properties);
    private final StrSubstitutor subst = new StrSubstitutor(this.tempLookup);
    private LoggerConfig root = new LoggerConfig();
    private final ConcurrentMap<String, Object> componentMap = new ConcurrentHashMap<String, Object>();
    private final ConfigurationSource configurationSource;
    private final ConfigurationScheduler configurationScheduler = new ConfigurationScheduler();
    private final WatchManager watchManager = new WatchManager(this.configurationScheduler);
    private AsyncLoggerConfigDisruptor asyncLoggerConfigDisruptor;
    private NanoClock nanoClock = new DummyNanoClock();
    private final WeakReference<LoggerContext> loggerContext;

    protected AbstractConfiguration(LoggerContext loggerContext, ConfigurationSource configurationSource) {
        this.loggerContext = new WeakReference<LoggerContext>(loggerContext);
        this.configurationSource = Objects.requireNonNull(configurationSource, "configurationSource is null");
        this.componentMap.put("ContextProperties", this.properties);
        this.pluginManager = new PluginManager("Core");
        this.rootNode = new Node();
        this.setState(LifeCycle.State.INITIALIZING);
    }

    @Override
    public ConfigurationSource getConfigurationSource() {
        return this.configurationSource;
    }

    @Override
    public List<String> getPluginPackages() {
        return this.pluginPackages;
    }

    @Override
    public Map<String, String> getProperties() {
        return this.properties;
    }

    @Override
    public ScriptManager getScriptManager() {
        return this.scriptManager;
    }

    public void setScriptManager(ScriptManager scriptManager) {
        this.scriptManager = scriptManager;
    }

    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    public void setPluginManager(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    @Override
    public WatchManager getWatchManager() {
        return this.watchManager;
    }

    @Override
    public ConfigurationScheduler getScheduler() {
        return this.configurationScheduler;
    }

    public Node getRootNode() {
        return this.rootNode;
    }

    @Override
    public AsyncLoggerConfigDelegate getAsyncLoggerConfigDelegate() {
        if (this.asyncLoggerConfigDisruptor == null) {
            this.asyncLoggerConfigDisruptor = new AsyncLoggerConfigDisruptor();
        }
        return this.asyncLoggerConfigDisruptor;
    }

    @Override
    public void initialize() {
        LOGGER.debug("Initializing configuration {}", (Object)this);
        this.subst.setConfiguration(this);
        this.scriptManager = new ScriptManager(this, this.watchManager);
        this.pluginManager.collectPlugins(this.pluginPackages);
        PluginManager pluginManager = new PluginManager("Level");
        pluginManager.collectPlugins(this.pluginPackages);
        Map<String, PluginType<?>> map = pluginManager.getPlugins();
        if (map != null) {
            for (PluginType<?> pluginType : map.values()) {
                try {
                    Loader.initializeClass(pluginType.getPluginClass().getName(), pluginType.getPluginClass().getClassLoader());
                } catch (Exception exception) {
                    LOGGER.error("Unable to initialize {} due to {}", (Object)pluginType.getPluginClass().getName(), (Object)exception.getClass().getSimpleName(), (Object)exception);
                }
            }
        }
        this.setup();
        this.setupAdvertisement();
        this.doConfigure();
        this.setState(LifeCycle.State.INITIALIZED);
        LOGGER.debug("Configuration {} initialized", (Object)this);
    }

    @Override
    public void start() {
        if (this.getState().equals((Object)LifeCycle.State.INITIALIZING)) {
            this.initialize();
        }
        LOGGER.debug("Starting configuration {}", (Object)this);
        this.setStarting();
        if (this.watchManager.getIntervalSeconds() > 0) {
            this.watchManager.start();
        }
        if (this.hasAsyncLoggers()) {
            this.asyncLoggerConfigDisruptor.start();
        }
        HashSet<LoggerConfig> hashSet = new HashSet<LoggerConfig>();
        for (LifeCycle lifeCycle : this.loggerConfigs.values()) {
            ((AbstractFilterable)lifeCycle).start();
            hashSet.add((LoggerConfig)lifeCycle);
        }
        for (LifeCycle lifeCycle : this.appenders.values()) {
            lifeCycle.start();
        }
        if (!hashSet.contains(this.root)) {
            this.root.start();
        }
        super.start();
        LOGGER.debug("Started configuration {} OK.", (Object)this);
    }

    private boolean hasAsyncLoggers() {
        if (this.root instanceof AsyncLoggerConfig) {
            return false;
        }
        for (LoggerConfig loggerConfig : this.loggerConfigs.values()) {
            if (!(loggerConfig instanceof AsyncLoggerConfig)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        Object object;
        Appender[] appenderArray2;
        this.setStopping();
        super.stop(l, timeUnit, false);
        LOGGER.trace("Stopping {}...", (Object)this);
        for (Appender[] appenderArray2 : this.loggerConfigs.values()) {
            appenderArray2.getReliabilityStrategy().beforeStopConfiguration(this);
        }
        this.root.getReliabilityStrategy().beforeStopConfiguration(this);
        String string = this.getClass().getSimpleName();
        LOGGER.trace("{} notified {} ReliabilityStrategies that config will be stopped.", (Object)string, (Object)(this.loggerConfigs.size() + 1));
        if (!this.loggerConfigs.isEmpty()) {
            LOGGER.trace("{} stopping {} LoggerConfigs.", (Object)string, (Object)this.loggerConfigs.size());
            appenderArray2 = this.loggerConfigs.values().iterator();
            while (appenderArray2.hasNext()) {
                object = (LoggerConfig)appenderArray2.next();
                ((AbstractFilterable)object).stop(l, timeUnit);
            }
        }
        LOGGER.trace("{} stopping root LoggerConfig.", (Object)string);
        if (!this.root.isStopped()) {
            this.root.stop(l, timeUnit);
        }
        if (this.hasAsyncLoggers()) {
            LOGGER.trace("{} stopping AsyncLoggerConfigDisruptor.", (Object)string);
            this.asyncLoggerConfigDisruptor.stop(l, timeUnit);
        }
        if (!(object = this.getAsyncAppenders(appenderArray2 = this.appenders.values().toArray(new Appender[this.appenders.size()]))).isEmpty()) {
            LOGGER.trace("{} stopping {} AsyncAppenders.", (Object)string, (Object)object.size());
            Iterator<Object> iterator2 = object.iterator();
            while (iterator2.hasNext()) {
                LifeCycle lifeCycle = (Appender)iterator2.next();
                if (lifeCycle instanceof LifeCycle2) {
                    ((LifeCycle2)lifeCycle).stop(l, timeUnit);
                    continue;
                }
                lifeCycle.stop();
            }
        }
        LOGGER.trace("{} notifying ReliabilityStrategies that appenders will be stopped.", (Object)string);
        for (LifeCycle lifeCycle : this.loggerConfigs.values()) {
            ((LoggerConfig)lifeCycle).getReliabilityStrategy().beforeStopAppenders();
        }
        this.root.getReliabilityStrategy().beforeStopAppenders();
        LOGGER.trace("{} stopping remaining Appenders.", (Object)string);
        int n = 0;
        for (int i = appenderArray2.length - 1; i >= 0; --i) {
            if (!appenderArray2[i].isStarted()) continue;
            if (appenderArray2[i] instanceof LifeCycle2) {
                ((LifeCycle2)((Object)appenderArray2[i])).stop(l, timeUnit);
            } else {
                appenderArray2[i].stop();
            }
            ++n;
        }
        LOGGER.trace("{} stopped {} remaining Appenders.", (Object)string, (Object)n);
        LOGGER.trace("{} cleaning Appenders from {} LoggerConfigs.", (Object)string, (Object)(this.loggerConfigs.size() + 1));
        for (LoggerConfig loggerConfig : this.loggerConfigs.values()) {
            loggerConfig.clearAppenders();
        }
        this.root.clearAppenders();
        if (this.watchManager.isStarted()) {
            this.watchManager.stop(l, timeUnit);
        }
        this.configurationScheduler.stop(l, timeUnit);
        if (this.advertiser != null && this.advertisement != null) {
            this.advertiser.unadvertise(this.advertisement);
        }
        this.setStopped();
        LOGGER.debug("Stopped {} OK", (Object)this);
        return false;
    }

    private List<Appender> getAsyncAppenders(Appender[] appenderArray) {
        ArrayList<Appender> arrayList = new ArrayList<Appender>();
        for (int i = appenderArray.length - 1; i >= 0; --i) {
            if (!(appenderArray[i] instanceof AsyncAppender)) continue;
            arrayList.add(appenderArray[i]);
        }
        return arrayList;
    }

    @Override
    public boolean isShutdownHookEnabled() {
        return this.isShutdownHookEnabled;
    }

    @Override
    public long getShutdownTimeoutMillis() {
        return this.shutdownTimeoutMillis;
    }

    public void setup() {
    }

    protected Level getDefaultStatus() {
        String string = PropertiesUtil.getProperties().getStringProperty("Log4jDefaultStatusLevel", Level.ERROR.name());
        try {
            return Level.toLevel(string);
        } catch (Exception exception) {
            return Level.ERROR;
        }
    }

    protected void createAdvertiser(String string, ConfigurationSource configurationSource, byte[] byArray, String string2) {
        if (string != null) {
            Node node = new Node(null, string, null);
            Map<String, String> map = node.getAttributes();
            map.put("content", new String(byArray));
            map.put("contentType", string2);
            map.put("name", "configuration");
            if (configurationSource.getLocation() != null) {
                map.put("location", configurationSource.getLocation());
            }
            this.advertiserNode = node;
        }
    }

    private void setupAdvertisement() {
        String string;
        PluginType<?> pluginType;
        if (this.advertiserNode != null && (pluginType = this.pluginManager.getPluginType(string = this.advertiserNode.getName())) != null) {
            Class<Advertiser> clazz = pluginType.getPluginClass().asSubclass(Advertiser.class);
            try {
                this.advertiser = clazz.newInstance();
                this.advertisement = this.advertiser.advertise(this.advertiserNode.getAttributes());
            } catch (InstantiationException instantiationException) {
                LOGGER.error("InstantiationException attempting to instantiate advertiser: {}", (Object)string, (Object)instantiationException);
            } catch (IllegalAccessException illegalAccessException) {
                LOGGER.error("IllegalAccessException attempting to instantiate advertiser: {}", (Object)string, (Object)illegalAccessException);
            }
        }
    }

    @Override
    public <T> T getComponent(String string) {
        return (T)this.componentMap.get(string);
    }

    @Override
    public void addComponent(String string, Object object) {
        this.componentMap.putIfAbsent(string, object);
    }

    protected void preConfigure(Node node) {
        try {
            for (Node node2 : node.getChildren()) {
                if (node2.getType() == null) {
                    LOGGER.error("Unable to locate plugin type for " + node2.getName());
                    continue;
                }
                Class<?> clazz = node2.getType().getPluginClass();
                if (clazz.isAnnotationPresent(Scheduled.class)) {
                    this.configurationScheduler.incrementScheduledItems();
                }
                this.preConfigure(node2);
            }
        } catch (Exception exception) {
            LOGGER.error("Error capturing node data for node " + node.getName(), (Throwable)exception);
        }
    }

    protected void doConfigure() {
        Object object;
        Object object2;
        this.preConfigure(this.rootNode);
        this.configurationScheduler.start();
        if (this.rootNode.hasChildren() && this.rootNode.getChildren().get(0).getName().equalsIgnoreCase("Properties")) {
            object2 = this.rootNode.getChildren().get(0);
            this.createConfiguration((Node)object2, null);
            if (((Node)object2).getObject() != null) {
                this.subst.setVariableResolver((StrLookup)((Node)object2).getObject());
            }
        } else {
            object2 = (Map)this.getComponent("ContextProperties");
            MapLookup mapLookup = object2 == null ? null : new MapLookup((Map<String, String>)object2);
            this.subst.setVariableResolver(new Interpolator(mapLookup, this.pluginPackages));
        }
        boolean bl = false;
        boolean bl2 = false;
        for (Node object3 : this.rootNode.getChildren()) {
            if (object3.getName().equalsIgnoreCase("Properties")) {
                if (this.tempLookup != this.subst.getVariableResolver()) continue;
                LOGGER.error("Properties declaration must be the first element in the configuration");
                continue;
            }
            this.createConfiguration(object3, null);
            if (object3.getObject() == null) continue;
            if (object3.getName().equalsIgnoreCase("Scripts")) {
                for (AbstractScript abstractScript : object3.getObject(AbstractScript[].class)) {
                    if (abstractScript instanceof ScriptRef) {
                        LOGGER.error("Script reference to {} not added. Scripts definition cannot contain script references", (Object)abstractScript.getName());
                        continue;
                    }
                    this.scriptManager.addScript(abstractScript);
                }
                continue;
            }
            if (object3.getName().equalsIgnoreCase("Appenders")) {
                this.appenders = (ConcurrentMap)object3.getObject();
                continue;
            }
            if (object3.isInstanceOf(Filter.class)) {
                this.addFilter(object3.getObject(Filter.class));
                continue;
            }
            if (object3.getName().equalsIgnoreCase("Loggers")) {
                object = (Loggers)object3.getObject();
                this.loggerConfigs = ((Loggers)object).getMap();
                bl = true;
                if (((Loggers)object).getRoot() == null) continue;
                this.root = ((Loggers)object).getRoot();
                bl2 = true;
                continue;
            }
            if (object3.getName().equalsIgnoreCase("CustomLevels")) {
                this.customLevels = object3.getObject(CustomLevels.class).getCustomLevels();
                continue;
            }
            if (object3.isInstanceOf(CustomLevelConfig.class)) {
                object = new ArrayList<CustomLevelConfig>(this.customLevels);
                object.add(object3.getObject(CustomLevelConfig.class));
                this.customLevels = object;
                continue;
            }
            object = Arrays.asList("\"Appenders\"", "\"Loggers\"", "\"Properties\"", "\"Scripts\"", "\"CustomLevels\"");
            LOGGER.error("Unknown object \"{}\" of type {} is ignored: try nesting it inside one of: {}.", (Object)object3.getName(), (Object)object3.getObject().getClass().getName(), object);
        }
        if (!bl) {
            LOGGER.warn("No Loggers were configured, using default. Is the Loggers element missing?");
            this.setToDefault();
            return;
        }
        if (!bl2) {
            LOGGER.warn("No Root logger was configured, creating default ERROR-level Root logger with Console appender");
            this.setToDefault();
        }
        for (Map.Entry entry : this.loggerConfigs.entrySet()) {
            object = (AbstractScript[])entry.getValue();
            for (AppenderRef appenderRef : ((LoggerConfig)object).getAppenderRefs()) {
                Appender appender = (Appender)this.appenders.get(appenderRef.getRef());
                if (appender != null) {
                    ((LoggerConfig)object).addAppender(appender, appenderRef.getLevel(), appenderRef.getFilter());
                    continue;
                }
                LOGGER.error("Unable to locate appender \"{}\" for logger config \"{}\"", (Object)appenderRef.getRef(), object);
            }
        }
        this.setParents();
    }

    protected void setToDefault() {
        this.setName("Default@" + Integer.toHexString(this.hashCode()));
        PatternLayout patternLayout = PatternLayout.newBuilder().withPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n").withConfiguration(this).build();
        ConsoleAppender consoleAppender = ConsoleAppender.createDefaultAppenderForLayout(patternLayout);
        consoleAppender.start();
        this.addAppender(consoleAppender);
        LoggerConfig loggerConfig = this.getRootLogger();
        loggerConfig.addAppender(consoleAppender, null, null);
        Level level = Level.ERROR;
        String string = PropertiesUtil.getProperties().getStringProperty("org.apache.logging.log4j.level", level.name());
        Level level2 = Level.valueOf(string);
        loggerConfig.setLevel(level2 != null ? level2 : level);
    }

    public void setName(String string) {
        this.name = string;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void addListener(ConfigurationListener configurationListener) {
        this.listeners.add(configurationListener);
    }

    @Override
    public void removeListener(ConfigurationListener configurationListener) {
        this.listeners.remove(configurationListener);
    }

    @Override
    public <T extends Appender> T getAppender(String string) {
        return (T)((Appender)this.appenders.get(string));
    }

    @Override
    public Map<String, Appender> getAppenders() {
        return this.appenders;
    }

    @Override
    public void addAppender(Appender appender) {
        this.appenders.putIfAbsent(appender.getName(), appender);
    }

    @Override
    public StrSubstitutor getStrSubstitutor() {
        return this.subst;
    }

    @Override
    public void setAdvertiser(Advertiser advertiser) {
        this.advertiser = advertiser;
    }

    @Override
    public Advertiser getAdvertiser() {
        return this.advertiser;
    }

    @Override
    public ReliabilityStrategy getReliabilityStrategy(LoggerConfig loggerConfig) {
        return ReliabilityStrategyFactory.getReliabilityStrategy(loggerConfig);
    }

    @Override
    public synchronized void addLoggerAppender(Logger logger, Appender appender) {
        String string = logger.getName();
        this.appenders.putIfAbsent(appender.getName(), appender);
        LoggerConfig loggerConfig = this.getLoggerConfig(string);
        if (loggerConfig.getName().equals(string)) {
            loggerConfig.addAppender(appender, null, null);
        } else {
            LoggerConfig loggerConfig2 = new LoggerConfig(string, loggerConfig.getLevel(), loggerConfig.isAdditive());
            loggerConfig2.addAppender(appender, null, null);
            loggerConfig2.setParent(loggerConfig);
            this.loggerConfigs.putIfAbsent(string, loggerConfig2);
            this.setParents();
            logger.getContext().updateLoggers();
        }
    }

    @Override
    public synchronized void addLoggerFilter(Logger logger, Filter filter) {
        String string = logger.getName();
        LoggerConfig loggerConfig = this.getLoggerConfig(string);
        if (loggerConfig.getName().equals(string)) {
            loggerConfig.addFilter(filter);
        } else {
            LoggerConfig loggerConfig2 = new LoggerConfig(string, loggerConfig.getLevel(), loggerConfig.isAdditive());
            loggerConfig2.addFilter(filter);
            loggerConfig2.setParent(loggerConfig);
            this.loggerConfigs.putIfAbsent(string, loggerConfig2);
            this.setParents();
            logger.getContext().updateLoggers();
        }
    }

    @Override
    public synchronized void setLoggerAdditive(Logger logger, boolean bl) {
        String string = logger.getName();
        LoggerConfig loggerConfig = this.getLoggerConfig(string);
        if (loggerConfig.getName().equals(string)) {
            loggerConfig.setAdditive(bl);
        } else {
            LoggerConfig loggerConfig2 = new LoggerConfig(string, loggerConfig.getLevel(), bl);
            loggerConfig2.setParent(loggerConfig);
            this.loggerConfigs.putIfAbsent(string, loggerConfig2);
            this.setParents();
            logger.getContext().updateLoggers();
        }
    }

    public synchronized void removeAppender(String string) {
        for (LoggerConfig loggerConfig : this.loggerConfigs.values()) {
            loggerConfig.removeAppender(string);
        }
        Appender appender = (Appender)this.appenders.remove(string);
        if (appender != null) {
            appender.stop();
        }
    }

    @Override
    public List<CustomLevelConfig> getCustomLevels() {
        return Collections.unmodifiableList(this.customLevels);
    }

    @Override
    public LoggerConfig getLoggerConfig(String string) {
        LoggerConfig loggerConfig = (LoggerConfig)this.loggerConfigs.get(string);
        if (loggerConfig != null) {
            return loggerConfig;
        }
        String string2 = string;
        while ((string2 = NameUtil.getSubName(string2)) != null) {
            loggerConfig = (LoggerConfig)this.loggerConfigs.get(string2);
            if (loggerConfig == null) continue;
            return loggerConfig;
        }
        return this.root;
    }

    @Override
    public LoggerContext getLoggerContext() {
        return (LoggerContext)this.loggerContext.get();
    }

    @Override
    public LoggerConfig getRootLogger() {
        return this.root;
    }

    @Override
    public Map<String, LoggerConfig> getLoggers() {
        return Collections.unmodifiableMap(this.loggerConfigs);
    }

    public LoggerConfig getLogger(String string) {
        return (LoggerConfig)this.loggerConfigs.get(string);
    }

    @Override
    public synchronized void addLogger(String string, LoggerConfig loggerConfig) {
        this.loggerConfigs.putIfAbsent(string, loggerConfig);
        this.setParents();
    }

    @Override
    public synchronized void removeLogger(String string) {
        this.loggerConfigs.remove(string);
        this.setParents();
    }

    @Override
    public void createConfiguration(Node node, LogEvent logEvent) {
        PluginType<?> pluginType = node.getType();
        if (pluginType != null && pluginType.isDeferChildren()) {
            node.setObject(this.createPluginObject(pluginType, node, logEvent));
        } else {
            for (Node node2 : node.getChildren()) {
                this.createConfiguration(node2, logEvent);
            }
            if (pluginType == null) {
                if (node.getParent() != null) {
                    LOGGER.error("Unable to locate plugin for {}", (Object)node.getName());
                }
            } else {
                node.setObject(this.createPluginObject(pluginType, node, logEvent));
            }
        }
    }

    private Object createPluginObject(PluginType<?> pluginType, Node node, LogEvent logEvent) {
        Class<?> clazz = pluginType.getPluginClass();
        if (Map.class.isAssignableFrom(clazz)) {
            try {
                return AbstractConfiguration.createPluginMap(node);
            } catch (Exception exception) {
                LOGGER.warn("Unable to create Map for {} of class {}", (Object)pluginType.getElementName(), (Object)clazz, (Object)exception);
            }
        }
        if (Collection.class.isAssignableFrom(clazz)) {
            try {
                return AbstractConfiguration.createPluginCollection(node);
            } catch (Exception exception) {
                LOGGER.warn("Unable to create List for {} of class {}", (Object)pluginType.getElementName(), (Object)clazz, (Object)exception);
            }
        }
        return new PluginBuilder(pluginType).withConfiguration(this).withConfigurationNode(node).forLogEvent(logEvent).build();
    }

    private static Map<String, ?> createPluginMap(Node node) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Node node2 : node.getChildren()) {
            Object t = node2.getObject();
            linkedHashMap.put(node2.getName(), t);
        }
        return linkedHashMap;
    }

    private static Collection<?> createPluginCollection(Node node) {
        List<Node> list = node.getChildren();
        ArrayList arrayList = new ArrayList(list.size());
        for (Node node2 : list) {
            Object t = node2.getObject();
            arrayList.add(t);
        }
        return arrayList;
    }

    private void setParents() {
        for (Map.Entry entry : this.loggerConfigs.entrySet()) {
            LoggerConfig loggerConfig = (LoggerConfig)entry.getValue();
            String string = (String)entry.getKey();
            if (string.isEmpty()) continue;
            int n = string.lastIndexOf(46);
            if (n > 0) {
                LoggerConfig loggerConfig2 = this.getLoggerConfig(string = string.substring(0, n));
                if (loggerConfig2 == null) {
                    loggerConfig2 = this.root;
                }
                loggerConfig.setParent(loggerConfig2);
                continue;
            }
            loggerConfig.setParent(this.root);
        }
    }

    protected static byte[] toByteArray(InputStream inputStream) throws IOException {
        int n;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] byArray = new byte[16384];
        while ((n = inputStream.read(byArray, 0, byArray.length)) != -1) {
            byteArrayOutputStream.write(byArray, 0, n);
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public NanoClock getNanoClock() {
        return this.nanoClock;
    }

    @Override
    public void setNanoClock(NanoClock nanoClock) {
        this.nanoClock = Objects.requireNonNull(nanoClock, "nanoClock");
    }
}

