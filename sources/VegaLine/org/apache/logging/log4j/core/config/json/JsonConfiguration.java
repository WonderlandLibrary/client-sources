/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.ConfiguratonFileWatcher;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.Reconfigurable;
import org.apache.logging.log4j.core.config.plugins.util.PluginType;
import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil;
import org.apache.logging.log4j.core.config.status.StatusConfiguration;
import org.apache.logging.log4j.core.util.Patterns;

public class JsonConfiguration
extends AbstractConfiguration
implements Reconfigurable {
    private static final String[] VERBOSE_CLASSES = new String[]{ResolverUtil.class.getName()};
    private final List<Status> status = new ArrayList<Status>();
    private JsonNode root;

    public JsonConfiguration(LoggerContext loggerContext, ConfigurationSource configSource) {
        super(loggerContext, configSource);
        File configFile = configSource.getFile();
        try {
            byte[] buffer;
            try (InputStream configStream = configSource.getInputStream();){
                buffer = JsonConfiguration.toByteArray(configStream);
            }
            ByteArrayInputStream is = new ByteArrayInputStream(buffer);
            this.root = this.getObjectMapper().readTree(is);
            if (this.root.size() == 1) {
                Iterator<JsonNode> i$ = this.root.iterator();
                while (i$.hasNext()) {
                    JsonNode node;
                    this.root = node = i$.next();
                }
            }
            this.processAttributes(this.rootNode, this.root);
            StatusConfiguration statusConfig = new StatusConfiguration().withVerboseClasses(VERBOSE_CLASSES).withStatus(this.getDefaultStatus());
            for (Map.Entry<String, String> entry : this.rootNode.getAttributes().entrySet()) {
                String key = entry.getKey();
                String value = this.getStrSubstitutor().replace(entry.getValue());
                if ("status".equalsIgnoreCase(key)) {
                    statusConfig.withStatus(value);
                    continue;
                }
                if ("dest".equalsIgnoreCase(key)) {
                    statusConfig.withDestination(value);
                    continue;
                }
                if ("shutdownHook".equalsIgnoreCase(key)) {
                    this.isShutdownHookEnabled = !"disable".equalsIgnoreCase(value);
                    continue;
                }
                if ("shutdownTimeout".equalsIgnoreCase(key)) {
                    this.shutdownTimeoutMillis = Long.parseLong(value);
                    continue;
                }
                if ("verbose".equalsIgnoreCase(entry.getKey())) {
                    statusConfig.withVerbosity(value);
                    continue;
                }
                if ("packages".equalsIgnoreCase(key)) {
                    this.pluginPackages.addAll(Arrays.asList(value.split(Patterns.COMMA_SEPARATOR)));
                    continue;
                }
                if ("name".equalsIgnoreCase(key)) {
                    this.setName(value);
                    continue;
                }
                if ("monitorInterval".equalsIgnoreCase(key)) {
                    int intervalSeconds = Integer.parseInt(value);
                    if (intervalSeconds <= 0) continue;
                    this.getWatchManager().setIntervalSeconds(intervalSeconds);
                    if (configFile == null) continue;
                    ConfiguratonFileWatcher watcher = new ConfiguratonFileWatcher(this, this.listeners);
                    this.getWatchManager().watchFile(configFile, watcher);
                    continue;
                }
                if (!"advertiser".equalsIgnoreCase(key)) continue;
                this.createAdvertiser(value, configSource, buffer, "application/json");
            }
            statusConfig.initialize();
            if (this.getName() == null) {
                this.setName(configSource.getLocation());
            }
        } catch (Exception ex) {
            LOGGER.error("Error parsing " + configSource.getLocation(), (Throwable)ex);
        }
    }

    protected ObjectMapper getObjectMapper() {
        return new ObjectMapper().configure(JsonParser.Feature.ALLOW_COMMENTS, true);
    }

    @Override
    public void setup() {
        Iterator<Map.Entry<String, JsonNode>> iter = this.root.fields();
        List<Node> children = this.rootNode.getChildren();
        while (iter.hasNext()) {
            Map.Entry<String, JsonNode> entry = iter.next();
            JsonNode n = entry.getValue();
            if (n.isObject()) {
                LOGGER.debug("Processing node for object {}", (Object)entry.getKey());
                children.add(this.constructNode(entry.getKey(), this.rootNode, n));
                continue;
            }
            if (!n.isArray()) continue;
            LOGGER.error("Arrays are not supported at the root configuration.");
        }
        LOGGER.debug("Completed parsing configuration");
        if (this.status.size() > 0) {
            for (Status s : this.status) {
                LOGGER.error("Error processing element {}: {}", (Object)s.name, (Object)s.errorType);
            }
        }
    }

    @Override
    public Configuration reconfigure() {
        try {
            ConfigurationSource source = this.getConfigurationSource().resetInputStream();
            if (source == null) {
                return null;
            }
            return new JsonConfiguration(this.getLoggerContext(), source);
        } catch (IOException ex) {
            LOGGER.error("Cannot locate file {}", (Object)this.getConfigurationSource(), (Object)ex);
            return null;
        }
    }

    private Node constructNode(String name, Node parent, JsonNode jsonNode) {
        PluginType<?> type2 = this.pluginManager.getPluginType(name);
        Node node = new Node(parent, name, type2);
        this.processAttributes(node, jsonNode);
        Iterator<Map.Entry<String, JsonNode>> iter = jsonNode.fields();
        List<Node> children = node.getChildren();
        while (iter.hasNext()) {
            Map.Entry<String, JsonNode> entry = iter.next();
            JsonNode n = entry.getValue();
            if (n.isArray() || n.isObject()) {
                if (type2 == null) {
                    this.status.add(new Status(name, n, ErrorType.CLASS_NOT_FOUND));
                }
                if (n.isArray()) {
                    LOGGER.debug("Processing node for array {}", (Object)entry.getKey());
                    for (int i = 0; i < n.size(); ++i) {
                        String pluginType = this.getType(n.get(i), entry.getKey());
                        PluginType<?> entryType = this.pluginManager.getPluginType(pluginType);
                        Node item = new Node(node, entry.getKey(), entryType);
                        this.processAttributes(item, n.get(i));
                        if (pluginType.equals(entry.getKey())) {
                            LOGGER.debug("Processing {}[{}]", (Object)entry.getKey(), (Object)i);
                        } else {
                            LOGGER.debug("Processing {} {}[{}]", (Object)pluginType, (Object)entry.getKey(), (Object)i);
                        }
                        Iterator<Map.Entry<String, JsonNode>> itemIter = n.get(i).fields();
                        List<Node> itemChildren = item.getChildren();
                        while (itemIter.hasNext()) {
                            Map.Entry<String, JsonNode> itemEntry = itemIter.next();
                            if (itemEntry.getValue().isObject()) {
                                LOGGER.debug("Processing node for object {}", (Object)itemEntry.getKey());
                                itemChildren.add(this.constructNode(itemEntry.getKey(), item, itemEntry.getValue()));
                                continue;
                            }
                            if (!itemEntry.getValue().isArray()) continue;
                            JsonNode array = itemEntry.getValue();
                            String entryName = itemEntry.getKey();
                            LOGGER.debug("Processing array for object {}", (Object)entryName);
                            for (int j = 0; j < array.size(); ++j) {
                                itemChildren.add(this.constructNode(entryName, item, array.get(j)));
                            }
                        }
                        children.add(item);
                    }
                    continue;
                }
                LOGGER.debug("Processing node for object {}", (Object)entry.getKey());
                children.add(this.constructNode(entry.getKey(), node, n));
                continue;
            }
            LOGGER.debug("Node {} is of type {}", (Object)entry.getKey(), (Object)n.getNodeType());
        }
        String t = type2 == null ? "null" : type2.getElementName() + ':' + type2.getPluginClass();
        String p = node.getParent() == null ? "null" : (node.getParent().getName() == null ? "root" : node.getParent().getName());
        LOGGER.debug("Returning {} with parent {} of type {}", (Object)node.getName(), (Object)p, (Object)t);
        return node;
    }

    private String getType(JsonNode node, String name) {
        Iterator<Map.Entry<String, JsonNode>> iter = node.fields();
        while (iter.hasNext()) {
            JsonNode n;
            Map.Entry<String, JsonNode> entry = iter.next();
            if (!entry.getKey().equalsIgnoreCase("type") || !(n = entry.getValue()).isValueNode()) continue;
            return n.asText();
        }
        return name;
    }

    private void processAttributes(Node parent, JsonNode node) {
        Map<String, String> attrs = parent.getAttributes();
        Iterator<Map.Entry<String, JsonNode>> iter = node.fields();
        while (iter.hasNext()) {
            JsonNode n;
            Map.Entry<String, JsonNode> entry = iter.next();
            if (entry.getKey().equalsIgnoreCase("type") || !(n = entry.getValue()).isValueNode()) continue;
            attrs.put(entry.getKey(), n.asText());
        }
    }

    public String toString() {
        return this.getClass().getSimpleName() + "[location=" + this.getConfigurationSource() + "]";
    }

    private static class Status {
        private final JsonNode node;
        private final String name;
        private final ErrorType errorType;

        public Status(String name, JsonNode node, ErrorType errorType) {
            this.name = name;
            this.node = node;
            this.errorType = errorType;
        }

        public String toString() {
            return "Status [name=" + this.name + ", errorType=" + (Object)((Object)this.errorType) + ", node=" + this.node + "]";
        }
    }

    private static enum ErrorType {
        CLASS_NOT_FOUND;

    }
}

