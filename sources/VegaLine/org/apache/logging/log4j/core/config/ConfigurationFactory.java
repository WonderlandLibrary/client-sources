/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import org.apache.logging.log4j.core.config.OrderComparator;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.composite.CompositeConfiguration;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.apache.logging.log4j.core.config.plugins.util.PluginType;
import org.apache.logging.log4j.core.lookup.Interpolator;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;
import org.apache.logging.log4j.core.util.FileUtils;
import org.apache.logging.log4j.core.util.Loader;
import org.apache.logging.log4j.core.util.NetUtils;
import org.apache.logging.log4j.core.util.ReflectionUtil;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.Strings;

public abstract class ConfigurationFactory
extends ConfigurationBuilderFactory {
    public static final String CONFIGURATION_FACTORY_PROPERTY = "log4j.configurationFactory";
    public static final String CONFIGURATION_FILE_PROPERTY = "log4j.configurationFile";
    public static final String CATEGORY = "ConfigurationFactory";
    protected static final Logger LOGGER = StatusLogger.getLogger();
    protected static final String TEST_PREFIX = "log4j2-test";
    protected static final String DEFAULT_PREFIX = "log4j2";
    private static final String CLASS_LOADER_SCHEME = "classloader";
    private static final String CLASS_PATH_SCHEME = "classpath";
    private static volatile List<ConfigurationFactory> factories = null;
    private static ConfigurationFactory configFactory = new Factory();
    protected final StrSubstitutor substitutor = new StrSubstitutor(new Interpolator());
    private static final Lock LOCK = new ReentrantLock();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ConfigurationFactory getInstance() {
        if (factories == null) {
            LOCK.lock();
            try {
                if (factories == null) {
                    ArrayList<ConfigurationFactory> list = new ArrayList<ConfigurationFactory>();
                    String factoryClass = PropertiesUtil.getProperties().getStringProperty(CONFIGURATION_FACTORY_PROPERTY);
                    if (factoryClass != null) {
                        ConfigurationFactory.addFactory(list, factoryClass);
                    }
                    PluginManager manager = new PluginManager(CATEGORY);
                    manager.collectPlugins();
                    Map<String, PluginType<?>> plugins = manager.getPlugins();
                    ArrayList<Class<ConfigurationFactory>> ordered = new ArrayList<Class<ConfigurationFactory>>(plugins.size());
                    for (PluginType<?> pluginType : plugins.values()) {
                        try {
                            ordered.add(pluginType.getPluginClass().asSubclass(ConfigurationFactory.class));
                        } catch (Exception ex) {
                            LOGGER.warn("Unable to add class {}", (Object)pluginType.getPluginClass(), (Object)ex);
                        }
                    }
                    Collections.sort(ordered, OrderComparator.getInstance());
                    for (Class clazz : ordered) {
                        ConfigurationFactory.addFactory(list, clazz);
                    }
                    factories = Collections.unmodifiableList(list);
                }
            } finally {
                LOCK.unlock();
            }
        }
        LOGGER.debug("Using configurationFactory {}", (Object)configFactory);
        return configFactory;
    }

    private static void addFactory(Collection<ConfigurationFactory> list, String factoryClass) {
        try {
            ConfigurationFactory.addFactory(list, LoaderUtil.loadClass(factoryClass).asSubclass(ConfigurationFactory.class));
        } catch (Exception ex) {
            LOGGER.error("Unable to load class {}", (Object)factoryClass, (Object)ex);
        }
    }

    private static void addFactory(Collection<ConfigurationFactory> list, Class<? extends ConfigurationFactory> factoryClass) {
        try {
            list.add(ReflectionUtil.instantiate(factoryClass));
        } catch (Exception ex) {
            LOGGER.error("Unable to create instance of {}", (Object)factoryClass.getName(), (Object)ex);
        }
    }

    public static void setConfigurationFactory(ConfigurationFactory factory) {
        configFactory = factory;
    }

    public static void resetConfigurationFactory() {
        configFactory = new Factory();
    }

    public static void removeConfigurationFactory(ConfigurationFactory factory) {
        if (configFactory == factory) {
            configFactory = new Factory();
        }
    }

    protected abstract String[] getSupportedTypes();

    protected boolean isActive() {
        return true;
    }

    public abstract Configuration getConfiguration(LoggerContext var1, ConfigurationSource var2);

    public Configuration getConfiguration(LoggerContext loggerContext, String name, URI configLocation) {
        ConfigurationSource source;
        if (!this.isActive()) {
            return null;
        }
        if (configLocation != null && (source = this.getInputFromUri(configLocation)) != null) {
            return this.getConfiguration(loggerContext, source);
        }
        return null;
    }

    public Configuration getConfiguration(LoggerContext loggerContext, String name, URI configLocation, ClassLoader loader) {
        Configuration configuration;
        String path;
        ConfigurationSource source;
        if (!this.isActive()) {
            return null;
        }
        if (loader == null) {
            return this.getConfiguration(loggerContext, name, configLocation);
        }
        if (ConfigurationFactory.isClassLoaderUri(configLocation) && (source = this.getInputFromResource(path = ConfigurationFactory.extractClassLoaderUriPath(configLocation), loader)) != null && (configuration = this.getConfiguration(loggerContext, source)) != null) {
            return configuration;
        }
        return this.getConfiguration(loggerContext, name, configLocation);
    }

    protected ConfigurationSource getInputFromUri(URI configLocation) {
        File configFile = FileUtils.fileFromUri(configLocation);
        if (configFile != null && configFile.exists() && configFile.canRead()) {
            try {
                return new ConfigurationSource((InputStream)new FileInputStream(configFile), configFile);
            } catch (FileNotFoundException ex) {
                LOGGER.error("Cannot locate file {}", (Object)configLocation.getPath(), (Object)ex);
            }
        }
        if (ConfigurationFactory.isClassLoaderUri(configLocation)) {
            ClassLoader loader = LoaderUtil.getThreadContextClassLoader();
            String path = ConfigurationFactory.extractClassLoaderUriPath(configLocation);
            ConfigurationSource source = this.getInputFromResource(path, loader);
            if (source != null) {
                return source;
            }
        }
        if (!configLocation.isAbsolute()) {
            LOGGER.error("File not found in file system or classpath: {}", (Object)configLocation.toString());
            return null;
        }
        try {
            return new ConfigurationSource(configLocation.toURL().openStream(), configLocation.toURL());
        } catch (MalformedURLException ex) {
            LOGGER.error("Invalid URL {}", (Object)configLocation.toString(), (Object)ex);
        } catch (Exception ex) {
            LOGGER.error("Unable to access {}", (Object)configLocation.toString(), (Object)ex);
        }
        return null;
    }

    private static boolean isClassLoaderUri(URI uri) {
        if (uri == null) {
            return false;
        }
        String scheme = uri.getScheme();
        return scheme == null || scheme.equals(CLASS_LOADER_SCHEME) || scheme.equals(CLASS_PATH_SCHEME);
    }

    private static String extractClassLoaderUriPath(URI uri) {
        return uri.getScheme() == null ? uri.getPath() : uri.getSchemeSpecificPart();
    }

    protected ConfigurationSource getInputFromString(String config, ClassLoader loader) {
        try {
            URL url = new URL(config);
            return new ConfigurationSource(url.openStream(), FileUtils.fileFromUri(url.toURI()));
        } catch (Exception ex) {
            ConfigurationSource source = this.getInputFromResource(config, loader);
            if (source == null) {
                try {
                    File file = new File(config);
                    return new ConfigurationSource((InputStream)new FileInputStream(file), file);
                } catch (FileNotFoundException fnfe) {
                    LOGGER.catching(Level.DEBUG, fnfe);
                }
            }
            return source;
        }
    }

    protected ConfigurationSource getInputFromResource(String resource, ClassLoader loader) {
        URL url = Loader.getResource(resource, loader);
        if (url == null) {
            return null;
        }
        InputStream is = null;
        try {
            is = url.openStream();
        } catch (IOException ioe) {
            LOGGER.catching(Level.DEBUG, ioe);
            return null;
        }
        if (is == null) {
            return null;
        }
        if (FileUtils.isFile(url)) {
            try {
                return new ConfigurationSource(is, FileUtils.fileFromUri(url.toURI()));
            } catch (URISyntaxException ex) {
                LOGGER.catching(Level.DEBUG, ex);
            }
        }
        return new ConfigurationSource(is, url);
    }

    static List<ConfigurationFactory> getFactories() {
        return factories;
    }

    private static class Factory
    extends ConfigurationFactory {
        private static final String ALL_TYPES = "*";

        private Factory() {
        }

        @Override
        public Configuration getConfiguration(LoggerContext loggerContext, String name, URI configLocation) {
            Configuration config;
            String configLocationStr;
            if (configLocation == null) {
                configLocationStr = this.substitutor.replace(PropertiesUtil.getProperties().getStringProperty(ConfigurationFactory.CONFIGURATION_FILE_PROPERTY));
                if (configLocationStr != null) {
                    String[] sources = configLocationStr.split(",");
                    if (sources.length > 1) {
                        ArrayList<AbstractConfiguration> configs = new ArrayList<AbstractConfiguration>();
                        for (String sourceLocation : sources) {
                            Configuration config2 = this.getConfiguration(loggerContext, sourceLocation.trim());
                            if (config2 == null || !(config2 instanceof AbstractConfiguration)) {
                                LOGGER.error("Failed to created configuration at {}", (Object)sourceLocation);
                                return null;
                            }
                            configs.add((AbstractConfiguration)config2);
                        }
                        return new CompositeConfiguration(configs);
                    }
                    return this.getConfiguration(loggerContext, configLocationStr);
                }
                for (ConfigurationFactory factory : Factory.getFactories()) {
                    String[] types = factory.getSupportedTypes();
                    if (types == null) continue;
                    for (String type2 : types) {
                        Configuration config3;
                        if (!type2.equals(ALL_TYPES) || (config3 = factory.getConfiguration(loggerContext, name, configLocation)) == null) continue;
                        return config3;
                    }
                }
            } else {
                configLocationStr = configLocation.toString();
                for (ConfigurationFactory factory : Factory.getFactories()) {
                    String[] types = factory.getSupportedTypes();
                    if (types == null) continue;
                    for (String type3 : types) {
                        Configuration config4;
                        if (!type3.equals(ALL_TYPES) && !configLocationStr.endsWith(type3) || (config4 = factory.getConfiguration(loggerContext, name, configLocation)) == null) continue;
                        return config4;
                    }
                }
            }
            if ((config = this.getConfiguration(loggerContext, true, name)) == null && (config = this.getConfiguration(loggerContext, true, null)) == null && (config = this.getConfiguration(loggerContext, false, name)) == null) {
                config = this.getConfiguration(loggerContext, false, null);
            }
            if (config != null) {
                return config;
            }
            LOGGER.error("No log4j2 configuration file found. Using default configuration: logging only errors to the console. Set system property 'org.apache.logging.log4j.simplelog.StatusLogger.level' to TRACE to show Log4j2 internal initialization logging.");
            return new DefaultConfiguration();
        }

        private Configuration getConfiguration(LoggerContext loggerContext, String configLocationStr) {
            ConfigurationSource source = null;
            try {
                source = this.getInputFromUri(NetUtils.toURI(configLocationStr));
            } catch (Exception ex) {
                LOGGER.catching(Level.DEBUG, ex);
            }
            if (source == null) {
                ClassLoader loader = LoaderUtil.getThreadContextClassLoader();
                source = this.getInputFromString(configLocationStr, loader);
            }
            if (source != null) {
                for (ConfigurationFactory factory : Factory.getFactories()) {
                    String[] types = factory.getSupportedTypes();
                    if (types == null) continue;
                    for (String type2 : types) {
                        Configuration config;
                        if (!type2.equals(ALL_TYPES) && !configLocationStr.endsWith(type2) || (config = factory.getConfiguration(loggerContext, source)) == null) continue;
                        return config;
                    }
                }
            }
            return null;
        }

        private Configuration getConfiguration(LoggerContext loggerContext, boolean isTest, String name) {
            boolean named = Strings.isNotEmpty(name);
            ClassLoader loader = LoaderUtil.getThreadContextClassLoader();
            for (ConfigurationFactory factory : Factory.getFactories()) {
                String prefix = isTest ? ConfigurationFactory.TEST_PREFIX : ConfigurationFactory.DEFAULT_PREFIX;
                String[] types = factory.getSupportedTypes();
                if (types == null) continue;
                for (String suffix : types) {
                    String configName;
                    ConfigurationSource source;
                    if (suffix.equals(ALL_TYPES) || (source = this.getInputFromResource(configName = named ? prefix + name + suffix : prefix + suffix, loader)) == null) continue;
                    return factory.getConfiguration(loggerContext, source);
                }
            }
            return null;
        }

        @Override
        public String[] getSupportedTypes() {
            return null;
        }

        @Override
        public Configuration getConfiguration(LoggerContext loggerContext, ConfigurationSource source) {
            if (source != null) {
                String config = source.getLocation();
                for (ConfigurationFactory factory : Factory.getFactories()) {
                    String[] types = factory.getSupportedTypes();
                    if (types == null) continue;
                    for (String type2 : types) {
                        if (!type2.equals(ALL_TYPES) && (config == null || !config.endsWith(type2))) continue;
                        Configuration c = factory.getConfiguration(loggerContext, source);
                        if (c != null) {
                            LOGGER.debug("Loaded configuration from {}", (Object)source);
                            return c;
                        }
                        LOGGER.error("Cannot determine the ConfigurationFactory to use for {}", (Object)config);
                        return null;
                    }
                }
            }
            LOGGER.error("Cannot process configuration, input source is null");
            return null;
        }
    }
}

