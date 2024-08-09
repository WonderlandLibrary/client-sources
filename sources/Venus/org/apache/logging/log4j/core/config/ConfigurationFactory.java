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
    private static ConfigurationFactory configFactory = new Factory(null);
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
                    ArrayList<ConfigurationFactory> arrayList = new ArrayList<ConfigurationFactory>();
                    String string = PropertiesUtil.getProperties().getStringProperty(CONFIGURATION_FACTORY_PROPERTY);
                    if (string != null) {
                        ConfigurationFactory.addFactory(arrayList, string);
                    }
                    PluginManager pluginManager = new PluginManager(CATEGORY);
                    pluginManager.collectPlugins();
                    Map<String, PluginType<?>> map = pluginManager.getPlugins();
                    ArrayList<Class<ConfigurationFactory>> arrayList2 = new ArrayList<Class<ConfigurationFactory>>(map.size());
                    for (PluginType<?> object : map.values()) {
                        try {
                            arrayList2.add(object.getPluginClass().asSubclass(ConfigurationFactory.class));
                        } catch (Exception exception) {
                            LOGGER.warn("Unable to add class {}", (Object)object.getPluginClass(), (Object)exception);
                        }
                    }
                    Collections.sort(arrayList2, OrderComparator.getInstance());
                    for (Class clazz : arrayList2) {
                        ConfigurationFactory.addFactory(arrayList, clazz);
                    }
                    factories = Collections.unmodifiableList(arrayList);
                }
            } finally {
                LOCK.unlock();
            }
        }
        LOGGER.debug("Using configurationFactory {}", (Object)configFactory);
        return configFactory;
    }

    private static void addFactory(Collection<ConfigurationFactory> collection, String string) {
        try {
            ConfigurationFactory.addFactory(collection, LoaderUtil.loadClass(string).asSubclass(ConfigurationFactory.class));
        } catch (Exception exception) {
            LOGGER.error("Unable to load class {}", (Object)string, (Object)exception);
        }
    }

    private static void addFactory(Collection<ConfigurationFactory> collection, Class<? extends ConfigurationFactory> clazz) {
        try {
            collection.add(ReflectionUtil.instantiate(clazz));
        } catch (Exception exception) {
            LOGGER.error("Unable to create instance of {}", (Object)clazz.getName(), (Object)exception);
        }
    }

    public static void setConfigurationFactory(ConfigurationFactory configurationFactory) {
        configFactory = configurationFactory;
    }

    public static void resetConfigurationFactory() {
        configFactory = new Factory(null);
    }

    public static void removeConfigurationFactory(ConfigurationFactory configurationFactory) {
        if (configFactory == configurationFactory) {
            configFactory = new Factory(null);
        }
    }

    protected abstract String[] getSupportedTypes();

    protected boolean isActive() {
        return false;
    }

    public abstract Configuration getConfiguration(LoggerContext var1, ConfigurationSource var2);

    public Configuration getConfiguration(LoggerContext loggerContext, String string, URI uRI) {
        ConfigurationSource configurationSource;
        if (!this.isActive()) {
            return null;
        }
        if (uRI != null && (configurationSource = this.getInputFromUri(uRI)) != null) {
            return this.getConfiguration(loggerContext, configurationSource);
        }
        return null;
    }

    public Configuration getConfiguration(LoggerContext loggerContext, String string, URI uRI, ClassLoader classLoader) {
        Configuration configuration;
        String string2;
        ConfigurationSource configurationSource;
        if (!this.isActive()) {
            return null;
        }
        if (classLoader == null) {
            return this.getConfiguration(loggerContext, string, uRI);
        }
        if (ConfigurationFactory.isClassLoaderUri(uRI) && (configurationSource = this.getInputFromResource(string2 = ConfigurationFactory.extractClassLoaderUriPath(uRI), classLoader)) != null && (configuration = this.getConfiguration(loggerContext, configurationSource)) != null) {
            return configuration;
        }
        return this.getConfiguration(loggerContext, string, uRI);
    }

    protected ConfigurationSource getInputFromUri(URI uRI) {
        File file = FileUtils.fileFromUri(uRI);
        if (file != null && file.exists() && file.canRead()) {
            try {
                return new ConfigurationSource((InputStream)new FileInputStream(file), file);
            } catch (FileNotFoundException fileNotFoundException) {
                LOGGER.error("Cannot locate file {}", (Object)uRI.getPath(), (Object)fileNotFoundException);
            }
        }
        if (ConfigurationFactory.isClassLoaderUri(uRI)) {
            ClassLoader classLoader = LoaderUtil.getThreadContextClassLoader();
            String string = ConfigurationFactory.extractClassLoaderUriPath(uRI);
            ConfigurationSource configurationSource = this.getInputFromResource(string, classLoader);
            if (configurationSource != null) {
                return configurationSource;
            }
        }
        if (!uRI.isAbsolute()) {
            LOGGER.error("File not found in file system or classpath: {}", (Object)uRI.toString());
            return null;
        }
        try {
            return new ConfigurationSource(uRI.toURL().openStream(), uRI.toURL());
        } catch (MalformedURLException malformedURLException) {
            LOGGER.error("Invalid URL {}", (Object)uRI.toString(), (Object)malformedURLException);
        } catch (Exception exception) {
            LOGGER.error("Unable to access {}", (Object)uRI.toString(), (Object)exception);
        }
        return null;
    }

    private static boolean isClassLoaderUri(URI uRI) {
        if (uRI == null) {
            return true;
        }
        String string = uRI.getScheme();
        return string == null || string.equals(CLASS_LOADER_SCHEME) || string.equals(CLASS_PATH_SCHEME);
    }

    private static String extractClassLoaderUriPath(URI uRI) {
        return uRI.getScheme() == null ? uRI.getPath() : uRI.getSchemeSpecificPart();
    }

    protected ConfigurationSource getInputFromString(String string, ClassLoader classLoader) {
        try {
            URL uRL = new URL(string);
            return new ConfigurationSource(uRL.openStream(), FileUtils.fileFromUri(uRL.toURI()));
        } catch (Exception exception) {
            ConfigurationSource configurationSource = this.getInputFromResource(string, classLoader);
            if (configurationSource == null) {
                try {
                    File file = new File(string);
                    return new ConfigurationSource((InputStream)new FileInputStream(file), file);
                } catch (FileNotFoundException fileNotFoundException) {
                    LOGGER.catching(Level.DEBUG, fileNotFoundException);
                }
            }
            return configurationSource;
        }
    }

    protected ConfigurationSource getInputFromResource(String string, ClassLoader classLoader) {
        URL uRL = Loader.getResource(string, classLoader);
        if (uRL == null) {
            return null;
        }
        InputStream inputStream = null;
        try {
            inputStream = uRL.openStream();
        } catch (IOException iOException) {
            LOGGER.catching(Level.DEBUG, iOException);
            return null;
        }
        if (inputStream == null) {
            return null;
        }
        if (FileUtils.isFile(uRL)) {
            try {
                return new ConfigurationSource(inputStream, FileUtils.fileFromUri(uRL.toURI()));
            } catch (URISyntaxException uRISyntaxException) {
                LOGGER.catching(Level.DEBUG, uRISyntaxException);
            }
        }
        return new ConfigurationSource(inputStream, uRL);
    }

    static List<ConfigurationFactory> getFactories() {
        return factories;
    }

    static class 1 {
    }

    private static class Factory
    extends ConfigurationFactory {
        private static final String ALL_TYPES = "*";

        private Factory() {
        }

        @Override
        public Configuration getConfiguration(LoggerContext loggerContext, String string, URI uRI) {
            Object object;
            if (uRI == null) {
                object = this.substitutor.replace(PropertiesUtil.getProperties().getStringProperty(ConfigurationFactory.CONFIGURATION_FILE_PROPERTY));
                if (object != null) {
                    String[] stringArray = ((String)object).split(",");
                    if (stringArray.length > 1) {
                        ArrayList<AbstractConfiguration> arrayList = new ArrayList<AbstractConfiguration>();
                        for (String string2 : stringArray) {
                            Configuration configuration = this.getConfiguration(loggerContext, string2.trim());
                            if (configuration == null || !(configuration instanceof AbstractConfiguration)) {
                                LOGGER.error("Failed to created configuration at {}", (Object)string2);
                                return null;
                            }
                            arrayList.add((AbstractConfiguration)configuration);
                        }
                        return new CompositeConfiguration(arrayList);
                    }
                    return this.getConfiguration(loggerContext, (String)object);
                }
                for (ConfigurationFactory configurationFactory : Factory.getFactories()) {
                    String[] stringArray = configurationFactory.getSupportedTypes();
                    if (stringArray == null) continue;
                    for (String string3 : stringArray) {
                        Configuration configuration;
                        if (!string3.equals(ALL_TYPES) || (configuration = configurationFactory.getConfiguration(loggerContext, string, uRI)) == null) continue;
                        return configuration;
                    }
                }
            } else {
                object = uRI.toString();
                for (ConfigurationFactory configurationFactory : Factory.getFactories()) {
                    String[] stringArray = configurationFactory.getSupportedTypes();
                    if (stringArray == null) continue;
                    for (String string4 : stringArray) {
                        Configuration configuration;
                        if (!string4.equals(ALL_TYPES) && !((String)object).endsWith(string4) || (configuration = configurationFactory.getConfiguration(loggerContext, string, uRI)) == null) continue;
                        return configuration;
                    }
                }
            }
            if ((object = this.getConfiguration(loggerContext, true, string)) == null && (object = this.getConfiguration(loggerContext, true, null)) == null && (object = this.getConfiguration(loggerContext, false, string)) == null) {
                object = this.getConfiguration(loggerContext, false, null);
            }
            if (object != null) {
                return object;
            }
            LOGGER.error("No log4j2 configuration file found. Using default configuration: logging only errors to the console. Set system property 'org.apache.logging.log4j.simplelog.StatusLogger.level' to TRACE to show Log4j2 internal initialization logging.");
            return new DefaultConfiguration();
        }

        private Configuration getConfiguration(LoggerContext loggerContext, String string) {
            ConfigurationSource configurationSource = null;
            try {
                configurationSource = this.getInputFromUri(NetUtils.toURI(string));
            } catch (Exception exception) {
                LOGGER.catching(Level.DEBUG, exception);
            }
            if (configurationSource == null) {
                ClassLoader classLoader = LoaderUtil.getThreadContextClassLoader();
                configurationSource = this.getInputFromString(string, classLoader);
            }
            if (configurationSource != null) {
                for (ConfigurationFactory configurationFactory : Factory.getFactories()) {
                    String[] stringArray = configurationFactory.getSupportedTypes();
                    if (stringArray == null) continue;
                    for (String string2 : stringArray) {
                        Configuration configuration;
                        if (!string2.equals(ALL_TYPES) && !string.endsWith(string2) || (configuration = configurationFactory.getConfiguration(loggerContext, configurationSource)) == null) continue;
                        return configuration;
                    }
                }
            }
            return null;
        }

        private Configuration getConfiguration(LoggerContext loggerContext, boolean bl, String string) {
            boolean bl2 = Strings.isNotEmpty(string);
            ClassLoader classLoader = LoaderUtil.getThreadContextClassLoader();
            for (ConfigurationFactory configurationFactory : Factory.getFactories()) {
                String string2 = bl ? ConfigurationFactory.TEST_PREFIX : ConfigurationFactory.DEFAULT_PREFIX;
                String[] stringArray = configurationFactory.getSupportedTypes();
                if (stringArray == null) continue;
                for (String string3 : stringArray) {
                    String string4;
                    ConfigurationSource configurationSource;
                    if (string3.equals(ALL_TYPES) || (configurationSource = this.getInputFromResource(string4 = bl2 ? string2 + string + string3 : string2 + string3, classLoader)) == null) continue;
                    return configurationFactory.getConfiguration(loggerContext, configurationSource);
                }
            }
            return null;
        }

        @Override
        public String[] getSupportedTypes() {
            return null;
        }

        @Override
        public Configuration getConfiguration(LoggerContext loggerContext, ConfigurationSource configurationSource) {
            if (configurationSource != null) {
                String string = configurationSource.getLocation();
                for (ConfigurationFactory configurationFactory : Factory.getFactories()) {
                    String[] stringArray = configurationFactory.getSupportedTypes();
                    if (stringArray == null) continue;
                    for (String string2 : stringArray) {
                        if (!string2.equals(ALL_TYPES) && (string == null || !string.endsWith(string2))) continue;
                        Configuration configuration = configurationFactory.getConfiguration(loggerContext, configurationSource);
                        if (configuration != null) {
                            LOGGER.debug("Loaded configuration from {}", (Object)configurationSource);
                            return configuration;
                        }
                        LOGGER.error("Cannot determine the ConfigurationFactory to use for {}", (Object)string);
                        return null;
                    }
                }
            }
            LOGGER.error("Cannot process configuration, input source is null");
            return null;
        }

        Factory(1 var1_1) {
            this();
        }
    }
}

