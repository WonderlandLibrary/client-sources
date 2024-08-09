/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.builder.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.ConfiguratonFileWatcher;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.Reconfigurable;
import org.apache.logging.log4j.core.config.builder.api.Component;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.apache.logging.log4j.core.config.plugins.util.PluginType;
import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil;
import org.apache.logging.log4j.core.config.status.StatusConfiguration;
import org.apache.logging.log4j.core.util.Patterns;

public class BuiltConfiguration
extends AbstractConfiguration {
    private static final String[] VERBOSE_CLASSES = new String[]{ResolverUtil.class.getName()};
    private final StatusConfiguration statusConfig = new StatusConfiguration().withVerboseClasses(VERBOSE_CLASSES).withStatus(this.getDefaultStatus());
    protected Component rootComponent;
    private Component loggersComponent;
    private Component appendersComponent;
    private Component filtersComponent;
    private Component propertiesComponent;
    private Component customLevelsComponent;
    private Component scriptsComponent;
    private String contentType = "text";

    public BuiltConfiguration(LoggerContext loggerContext, ConfigurationSource configurationSource, Component component) {
        super(loggerContext, configurationSource);
        for (Component component2 : component.getComponents()) {
            switch (component2.getPluginType()) {
                case "Scripts": {
                    this.scriptsComponent = component2;
                    break;
                }
                case "Loggers": {
                    this.loggersComponent = component2;
                    break;
                }
                case "Appenders": {
                    this.appendersComponent = component2;
                    break;
                }
                case "Filters": {
                    this.filtersComponent = component2;
                    break;
                }
                case "Properties": {
                    this.propertiesComponent = component2;
                    break;
                }
                case "CustomLevels": {
                    this.customLevelsComponent = component2;
                }
            }
        }
        this.rootComponent = component;
    }

    @Override
    public void setup() {
        List<Node> list = this.rootNode.getChildren();
        if (this.propertiesComponent.getComponents().size() > 0) {
            list.add(this.convertToNode(this.rootNode, this.propertiesComponent));
        }
        if (this.scriptsComponent.getComponents().size() > 0) {
            list.add(this.convertToNode(this.rootNode, this.scriptsComponent));
        }
        if (this.customLevelsComponent.getComponents().size() > 0) {
            list.add(this.convertToNode(this.rootNode, this.customLevelsComponent));
        }
        list.add(this.convertToNode(this.rootNode, this.loggersComponent));
        list.add(this.convertToNode(this.rootNode, this.appendersComponent));
        if (this.filtersComponent.getComponents().size() > 0) {
            if (this.filtersComponent.getComponents().size() == 1) {
                list.add(this.convertToNode(this.rootNode, this.filtersComponent.getComponents().get(0)));
            } else {
                list.add(this.convertToNode(this.rootNode, this.filtersComponent));
            }
        }
        this.rootComponent = null;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String string) {
        this.contentType = string;
    }

    public void createAdvertiser(String string, ConfigurationSource configurationSource) {
        byte[] byArray = null;
        try {
            InputStream inputStream;
            if (configurationSource != null && (inputStream = configurationSource.getInputStream()) != null) {
                byArray = BuiltConfiguration.toByteArray(inputStream);
            }
        } catch (IOException iOException) {
            LOGGER.warn("Unable to read configuration source " + configurationSource.toString());
        }
        super.createAdvertiser(string, configurationSource, byArray, this.contentType);
    }

    public StatusConfiguration getStatusConfiguration() {
        return this.statusConfig;
    }

    public void setPluginPackages(String string) {
        this.pluginPackages.addAll(Arrays.asList(string.split(Patterns.COMMA_SEPARATOR)));
    }

    public void setShutdownHook(String string) {
        this.isShutdownHookEnabled = !"disable".equalsIgnoreCase(string);
    }

    public void setShutdownTimeoutMillis(long l) {
        this.shutdownTimeoutMillis = l;
    }

    public void setMonitorInterval(int n) {
        ConfigurationSource configurationSource;
        if (this instanceof Reconfigurable && n > 0 && (configurationSource = this.getConfigurationSource()) != null) {
            File file = configurationSource.getFile();
            if (n > 0) {
                this.getWatchManager().setIntervalSeconds(n);
                if (file != null) {
                    ConfiguratonFileWatcher configuratonFileWatcher = new ConfiguratonFileWatcher((Reconfigurable)((Object)this), this.listeners);
                    this.getWatchManager().watchFile(file, configuratonFileWatcher);
                }
            }
        }
    }

    @Override
    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    protected Node convertToNode(Node node, Component component) {
        String string = component.getPluginType();
        PluginType<?> pluginType = this.pluginManager.getPluginType(string);
        Node node2 = new Node(node, string, pluginType);
        node2.getAttributes().putAll(component.getAttributes());
        node2.setValue(component.getValue());
        List<Node> list = node2.getChildren();
        for (Component component2 : component.getComponents()) {
            list.add(this.convertToNode(node2, component2));
        }
        return node2;
    }
}

