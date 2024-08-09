/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.composite;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.ConfiguratonFileWatcher;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.Reconfigurable;
import org.apache.logging.log4j.core.config.composite.DefaultMergeStrategy;
import org.apache.logging.log4j.core.config.composite.MergeStrategy;
import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil;
import org.apache.logging.log4j.core.config.status.StatusConfiguration;
import org.apache.logging.log4j.core.util.FileWatcher;
import org.apache.logging.log4j.core.util.Patterns;
import org.apache.logging.log4j.core.util.WatchManager;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PropertiesUtil;

public class CompositeConfiguration
extends AbstractConfiguration
implements Reconfigurable {
    public static final String MERGE_STRATEGY_PROPERTY = "log4j.mergeStrategy";
    private static final String[] VERBOSE_CLASSES = new String[]{ResolverUtil.class.getName()};
    private final List<? extends AbstractConfiguration> configurations;
    private MergeStrategy mergeStrategy;

    public CompositeConfiguration(List<? extends AbstractConfiguration> list) {
        super(list.get(0).getLoggerContext(), ConfigurationSource.NULL_SOURCE);
        this.rootNode = list.get(0).getRootNode();
        this.configurations = list;
        String string = PropertiesUtil.getProperties().getStringProperty(MERGE_STRATEGY_PROPERTY, DefaultMergeStrategy.class.getName());
        try {
            this.mergeStrategy = (MergeStrategy)LoaderUtil.newInstanceOf(string);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException reflectiveOperationException) {
            this.mergeStrategy = new DefaultMergeStrategy();
        }
        for (AbstractConfiguration object : list) {
            this.mergeStrategy.mergeRootProperties(this.rootNode, object);
        }
        StatusConfiguration statusConfiguration = new StatusConfiguration().withVerboseClasses(VERBOSE_CLASSES).withStatus(this.getDefaultStatus());
        for (Map.Entry<String, String> entry : this.rootNode.getAttributes().entrySet()) {
            String string2 = entry.getKey();
            String string3 = this.getStrSubstitutor().replace(entry.getValue());
            if ("status".equalsIgnoreCase(string2)) {
                statusConfiguration.withStatus(string3.toUpperCase());
                continue;
            }
            if ("dest".equalsIgnoreCase(string2)) {
                statusConfiguration.withDestination(string3);
                continue;
            }
            if ("shutdownHook".equalsIgnoreCase(string2)) {
                this.isShutdownHookEnabled = !"disable".equalsIgnoreCase(string3);
                continue;
            }
            if ("shutdownTimeout".equalsIgnoreCase(string2)) {
                this.shutdownTimeoutMillis = Long.parseLong(string3);
                continue;
            }
            if ("verbose".equalsIgnoreCase(string2)) {
                statusConfiguration.withVerbosity(string3);
                continue;
            }
            if ("packages".equalsIgnoreCase(string2)) {
                this.pluginPackages.addAll(Arrays.asList(string3.split(Patterns.COMMA_SEPARATOR)));
                continue;
            }
            if (!"name".equalsIgnoreCase(string2)) continue;
            this.setName(string3);
        }
        statusConfiguration.initialize();
    }

    @Override
    public void setup() {
        AbstractConfiguration abstractConfiguration = this.configurations.get(0);
        this.staffChildConfiguration(abstractConfiguration);
        WatchManager watchManager = this.getWatchManager();
        WatchManager watchManager2 = abstractConfiguration.getWatchManager();
        ConfiguratonFileWatcher configuratonFileWatcher = new ConfiguratonFileWatcher(this, this.listeners);
        if (watchManager2.getIntervalSeconds() > 0) {
            watchManager.setIntervalSeconds(watchManager2.getIntervalSeconds());
            Map<File, FileWatcher> map = watchManager2.getWatchers();
            for (Map.Entry<File, FileWatcher> entry : map.entrySet()) {
                if (!(entry.getValue() instanceof ConfiguratonFileWatcher)) continue;
                watchManager.watchFile((File)entry.getKey(), configuratonFileWatcher);
            }
        }
        for (AbstractConfiguration abstractConfiguration2 : this.configurations.subList(1, this.configurations.size())) {
            int n;
            Map.Entry<File, FileWatcher> entry;
            this.staffChildConfiguration(abstractConfiguration2);
            entry = abstractConfiguration2.getRootNode();
            this.mergeStrategy.mergConfigurations(this.rootNode, (Node)((Object)entry), this.getPluginManager());
            if (LOGGER.isEnabled(Level.ALL)) {
                StringBuilder stringBuilder = new StringBuilder();
                this.printNodes("", this.rootNode, stringBuilder);
                System.out.println(stringBuilder.toString());
            }
            if ((n = abstractConfiguration2.getWatchManager().getIntervalSeconds()) <= 0) continue;
            int n2 = watchManager.getIntervalSeconds();
            if (n2 <= 0 || n < n2) {
                watchManager.setIntervalSeconds(n);
            }
            WatchManager watchManager3 = abstractConfiguration2.getWatchManager();
            Map<File, FileWatcher> map = watchManager3.getWatchers();
            for (Map.Entry<File, FileWatcher> entry2 : map.entrySet()) {
                if (!(entry2.getValue() instanceof ConfiguratonFileWatcher)) continue;
                watchManager.watchFile(entry2.getKey(), configuratonFileWatcher);
            }
        }
    }

    @Override
    public Configuration reconfigure() {
        LOGGER.debug("Reconfiguring composite configuration");
        ArrayList<AbstractConfiguration> arrayList = new ArrayList<AbstractConfiguration>();
        ConfigurationFactory configurationFactory = ConfigurationFactory.getInstance();
        for (AbstractConfiguration abstractConfiguration : this.configurations) {
            Configuration configuration;
            ConfigurationSource configurationSource = abstractConfiguration.getConfigurationSource();
            URI uRI = configurationSource.getURI();
            if (uRI != null) {
                LOGGER.warn("Unable to determine URI for configuration {}, changes to it will be ignored", (Object)abstractConfiguration.getName());
                configuration = configurationFactory.getConfiguration(this.getLoggerContext(), abstractConfiguration.getName(), uRI);
                if (configuration == null) {
                    LOGGER.warn("Unable to reload configuration {}, changes to it will be ignored", (Object)abstractConfiguration.getName());
                    configuration = abstractConfiguration;
                }
            } else {
                configuration = abstractConfiguration;
            }
            arrayList.add((AbstractConfiguration)configuration);
        }
        return new CompositeConfiguration(arrayList);
    }

    private void staffChildConfiguration(AbstractConfiguration abstractConfiguration) {
        abstractConfiguration.setPluginManager(this.pluginManager);
        abstractConfiguration.setScriptManager(this.scriptManager);
        abstractConfiguration.setup();
    }

    private void printNodes(String string, Node node, StringBuilder stringBuilder) {
        stringBuilder.append(string).append(node.getName()).append(" type: ").append(node.getType()).append("\n");
        stringBuilder.append(string).append(node.getAttributes().toString()).append("\n");
        for (Node node2 : node.getChildren()) {
            this.printNodes(string + "  ", node2, stringBuilder);
        }
    }
}

