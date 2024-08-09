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

    public JsonConfiguration(LoggerContext loggerContext, ConfigurationSource configurationSource) {
        super(loggerContext, configurationSource);
        File file = configurationSource.getFile();
        try {
            byte[] byArray;
            InputStream inputStream = configurationSource.getInputStream();
            Object object = null;
            try {
                byArray = JsonConfiguration.toByteArray(inputStream);
            } catch (Throwable throwable) {
                object = throwable;
                throw throwable;
            } finally {
                if (inputStream != null) {
                    if (object != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable throwable) {
                            ((Throwable)object).addSuppressed(throwable);
                        }
                    } else {
                        inputStream.close();
                    }
                }
            }
            inputStream = new ByteArrayInputStream(byArray);
            this.root = this.getObjectMapper().readTree(inputStream);
            if (this.root.size() == 1) {
                for (Object object2 : this.root) {
                    this.root = object2;
                }
            }
            this.processAttributes(this.rootNode, this.root);
            object = new StatusConfiguration().withVerboseClasses(VERBOSE_CLASSES).withStatus(this.getDefaultStatus());
            for (Map.Entry entry : this.rootNode.getAttributes().entrySet()) {
                String string = (String)entry.getKey();
                String string2 = this.getStrSubstitutor().replace((String)entry.getValue());
                if ("status".equalsIgnoreCase(string)) {
                    ((StatusConfiguration)object).withStatus(string2);
                    continue;
                }
                if ("dest".equalsIgnoreCase(string)) {
                    ((StatusConfiguration)object).withDestination(string2);
                    continue;
                }
                if ("shutdownHook".equalsIgnoreCase(string)) {
                    this.isShutdownHookEnabled = !"disable".equalsIgnoreCase(string2);
                    continue;
                }
                if ("shutdownTimeout".equalsIgnoreCase(string)) {
                    this.shutdownTimeoutMillis = Long.parseLong(string2);
                    continue;
                }
                if ("verbose".equalsIgnoreCase((String)entry.getKey())) {
                    ((StatusConfiguration)object).withVerbosity(string2);
                    continue;
                }
                if ("packages".equalsIgnoreCase(string)) {
                    this.pluginPackages.addAll(Arrays.asList(string2.split(Patterns.COMMA_SEPARATOR)));
                    continue;
                }
                if ("name".equalsIgnoreCase(string)) {
                    this.setName(string2);
                    continue;
                }
                if ("monitorInterval".equalsIgnoreCase(string)) {
                    int n = Integer.parseInt(string2);
                    if (n <= 0) continue;
                    this.getWatchManager().setIntervalSeconds(n);
                    if (file == null) continue;
                    ConfiguratonFileWatcher configuratonFileWatcher = new ConfiguratonFileWatcher(this, this.listeners);
                    this.getWatchManager().watchFile(file, configuratonFileWatcher);
                    continue;
                }
                if (!"advertiser".equalsIgnoreCase(string)) continue;
                this.createAdvertiser(string2, configurationSource, byArray, "application/json");
            }
            ((StatusConfiguration)object).initialize();
            if (this.getName() == null) {
                this.setName(configurationSource.getLocation());
            }
        } catch (Exception exception) {
            LOGGER.error("Error parsing " + configurationSource.getLocation(), (Throwable)exception);
        }
    }

    protected ObjectMapper getObjectMapper() {
        return new ObjectMapper().configure(JsonParser.Feature.ALLOW_COMMENTS, false);
    }

    @Override
    public void setup() {
        Iterator<Map.Entry<String, JsonNode>> iterator2 = this.root.fields();
        List<Node> list = this.rootNode.getChildren();
        while (iterator2.hasNext()) {
            Map.Entry<String, JsonNode> entry = iterator2.next();
            JsonNode object = entry.getValue();
            if (object.isObject()) {
                LOGGER.debug("Processing node for object {}", entry.getKey());
                list.add(this.constructNode((String)entry.getKey(), this.rootNode, object));
                continue;
            }
            if (!object.isArray()) continue;
            LOGGER.error("Arrays are not supported at the root configuration.");
        }
        LOGGER.debug("Completed parsing configuration");
        if (this.status.size() > 0) {
            for (Status status2 : this.status) {
                LOGGER.error("Error processing element {}: {}", (Object)Status.access$000(status2), (Object)Status.access$100(status2));
            }
        }
    }

    @Override
    public Configuration reconfigure() {
        try {
            ConfigurationSource configurationSource = this.getConfigurationSource().resetInputStream();
            if (configurationSource == null) {
                return null;
            }
            return new JsonConfiguration(this.getLoggerContext(), configurationSource);
        } catch (IOException iOException) {
            LOGGER.error("Cannot locate file {}", (Object)this.getConfigurationSource(), (Object)iOException);
            return null;
        }
    }

    private Node constructNode(String string, Node node, JsonNode jsonNode) {
        Object object;
        Map.Entry<String, JsonNode> entry;
        PluginType<?> pluginType = this.pluginManager.getPluginType(string);
        Node node2 = new Node(node, string, pluginType);
        this.processAttributes(node2, jsonNode);
        Iterator<Map.Entry<String, JsonNode>> iterator2 = jsonNode.fields();
        List<Node> list = node2.getChildren();
        while (iterator2.hasNext()) {
            entry = iterator2.next();
            object = (JsonNode)entry.getValue();
            if (((JsonNode)object).isArray() || ((JsonNode)object).isObject()) {
                if (pluginType == null) {
                    this.status.add(new Status(string, (JsonNode)object, ErrorType.CLASS_NOT_FOUND));
                }
                if (((JsonNode)object).isArray()) {
                    LOGGER.debug("Processing node for array {}", entry.getKey());
                    for (int i = 0; i < ((JsonNode)object).size(); ++i) {
                        String string2 = this.getType(((JsonNode)object).get(i), (String)entry.getKey());
                        PluginType<?> pluginType2 = this.pluginManager.getPluginType(string2);
                        Node node3 = new Node(node2, (String)entry.getKey(), pluginType2);
                        this.processAttributes(node3, ((JsonNode)object).get(i));
                        if (string2.equals(entry.getKey())) {
                            LOGGER.debug("Processing {}[{}]", entry.getKey(), (Object)i);
                        } else {
                            LOGGER.debug("Processing {} {}[{}]", (Object)string2, entry.getKey(), (Object)i);
                        }
                        Iterator<Map.Entry<String, JsonNode>> iterator3 = ((JsonNode)object).get(i).fields();
                        List<Node> list2 = node3.getChildren();
                        while (iterator3.hasNext()) {
                            Map.Entry<String, JsonNode> entry2 = iterator3.next();
                            if (entry2.getValue().isObject()) {
                                LOGGER.debug("Processing node for object {}", (Object)entry2.getKey());
                                list2.add(this.constructNode(entry2.getKey(), node3, entry2.getValue()));
                                continue;
                            }
                            if (!entry2.getValue().isArray()) continue;
                            JsonNode jsonNode2 = entry2.getValue();
                            String string3 = entry2.getKey();
                            LOGGER.debug("Processing array for object {}", (Object)string3);
                            for (int j = 0; j < jsonNode2.size(); ++j) {
                                list2.add(this.constructNode(string3, node3, jsonNode2.get(j)));
                            }
                        }
                        list.add(node3);
                    }
                    continue;
                }
                LOGGER.debug("Processing node for object {}", entry.getKey());
                list.add(this.constructNode((String)entry.getKey(), node2, (JsonNode)object));
                continue;
            }
            LOGGER.debug("Node {} is of type {}", entry.getKey(), (Object)((JsonNode)object).getNodeType());
        }
        entry = pluginType == null ? "null" : pluginType.getElementName() + ':' + pluginType.getPluginClass();
        object = node2.getParent() == null ? "null" : (node2.getParent().getName() == null ? "root" : node2.getParent().getName());
        LOGGER.debug("Returning {} with parent {} of type {}", (Object)node2.getName(), object, (Object)entry);
        return node2;
    }

    private String getType(JsonNode jsonNode, String string) {
        Iterator<Map.Entry<String, JsonNode>> iterator2 = jsonNode.fields();
        while (iterator2.hasNext()) {
            JsonNode jsonNode2;
            Map.Entry<String, JsonNode> entry = iterator2.next();
            if (!entry.getKey().equalsIgnoreCase("type") || !(jsonNode2 = entry.getValue()).isValueNode()) continue;
            return jsonNode2.asText();
        }
        return string;
    }

    private void processAttributes(Node node, JsonNode jsonNode) {
        Map<String, String> map = node.getAttributes();
        Iterator<Map.Entry<String, JsonNode>> iterator2 = jsonNode.fields();
        while (iterator2.hasNext()) {
            JsonNode jsonNode2;
            Map.Entry<String, JsonNode> entry = iterator2.next();
            if (entry.getKey().equalsIgnoreCase("type") || !(jsonNode2 = entry.getValue()).isValueNode()) continue;
            map.put(entry.getKey(), jsonNode2.asText());
        }
    }

    public String toString() {
        return this.getClass().getSimpleName() + "[location=" + this.getConfigurationSource() + "]";
    }

    private static class Status {
        private final JsonNode node;
        private final String name;
        private final ErrorType errorType;

        public Status(String string, JsonNode jsonNode, ErrorType errorType) {
            this.name = string;
            this.node = jsonNode;
            this.errorType = errorType;
        }

        public String toString() {
            return "Status [name=" + this.name + ", errorType=" + (Object)((Object)this.errorType) + ", node=" + this.node + "]";
        }

        static String access$000(Status status2) {
            return status2.name;
        }

        static ErrorType access$100(Status status2) {
            return status2.errorType;
        }
    }

    private static enum ErrorType {
        CLASS_NOT_FOUND;

    }
}

