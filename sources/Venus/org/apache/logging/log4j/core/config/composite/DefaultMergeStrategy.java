/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.composite;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.composite.MergeStrategy;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.apache.logging.log4j.core.config.plugins.util.PluginType;
import org.apache.logging.log4j.core.filter.CompositeFilter;

public class DefaultMergeStrategy
implements MergeStrategy {
    private static final String APPENDERS = "appenders";
    private static final String PROPERTIES = "properties";
    private static final String LOGGERS = "loggers";
    private static final String SCRIPTS = "scripts";
    private static final String FILTERS = "filters";
    private static final String STATUS = "status";
    private static final String NAME = "name";
    private static final String REF = "ref";

    @Override
    public void mergeRootProperties(Node node, AbstractConfiguration abstractConfiguration) {
        for (Map.Entry<String, String> entry : abstractConfiguration.getRootNode().getAttributes().entrySet()) {
            boolean bl = false;
            for (Map.Entry<String, String> entry2 : node.getAttributes().entrySet()) {
                if (!entry2.getKey().equalsIgnoreCase(entry.getKey())) continue;
                if (entry.getKey().equalsIgnoreCase(STATUS)) {
                    Level level = Level.getLevel(entry2.getValue().toUpperCase());
                    Level level2 = Level.getLevel(entry.getValue().toUpperCase());
                    if (level != null && level2 != null) {
                        if (level2.isLessSpecificThan(level)) {
                            entry2.setValue(entry.getValue());
                        }
                    } else if (level2 != null) {
                        entry2.setValue(entry.getValue());
                    }
                } else if (entry.getKey().equalsIgnoreCase("monitorInterval")) {
                    int n = Integer.parseInt(entry.getValue());
                    int n2 = Integer.parseInt(entry2.getValue());
                    if (n2 == 0 || n < n2) {
                        entry2.setValue(entry.getValue());
                    }
                } else {
                    entry2.setValue(entry.getValue());
                }
                bl = true;
            }
            if (bl) continue;
            node.getAttributes().put(entry.getKey(), entry.getValue());
        }
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    @Override
    public void mergConfigurations(Node var1_1, Node var2_2, PluginManager var3_3) {
        for (Node var5_5 : var2_2.getChildren()) {
            var6_6 = this.isFilterNode(var5_5);
            var7_7 = false;
            block11: for (Node var9_9 : var1_1.getChildren()) {
                if (var6_6) {
                    if (!this.isFilterNode(var9_9)) continue;
                    this.updateFilterNode(var1_1, var9_9, var5_5, var3_3);
                    var7_7 = true;
                    break;
                }
                if (!var9_9.getName().equalsIgnoreCase(var5_5.getName())) continue;
                var10_10 = var9_9.getName().toLowerCase();
                var11_11 = -1;
                switch (var10_10.hashCode()) {
                    case -926053069: {
                        if (!var10_10.equals("properties")) break;
                        var11_11 = 0;
                        break;
                    }
                    case 1926514952: {
                        if (!var10_10.equals("scripts")) break;
                        var11_11 = 1;
                        break;
                    }
                    case 2009213964: {
                        if (!var10_10.equals("appenders")) break;
                        var11_11 = 2;
                        break;
                    }
                    case 342277347: {
                        if (!var10_10.equals("loggers")) break;
                        var11_11 = 3;
                    }
                }
                switch (var11_11) {
                    case 0: 
                    case 1: 
                    case 2: {
                        for (Iterator<Node> var13_13 : var5_5.getChildren()) {
                            for (Node var15_15 : var9_9.getChildren()) {
                                if (!var15_15.getAttributes().get("name").equals(var13_13.getAttributes().get("name"))) continue;
                                var9_9.getChildren().remove(var15_15);
                                break;
                            }
                            var9_9.getChildren().add((Node)var13_13);
                        }
                        var7_7 = true;
                        continue block11;
                    }
                    case 3: {
                        var12_12 = new HashMap<K, V>();
                        for (Object var14_14 : var9_9.getChildren()) {
                            var12_12.put(var14_14.getName(), var14_14);
                        }
                        var13_13 = var5_5.getChildren().iterator();
                        while (var13_13.hasNext()) {
                            var14_14 = var13_13.next();
                            var15_15 = this.getLoggerNode(var9_9, var14_14.getAttributes().get("name"));
                            var16_16 = new Node(var9_9, var14_14.getName(), var14_14.getType());
                            if (var15_15 == null) ** GOTO lbl88
                            var15_15.getAttributes().putAll(var14_14.getAttributes());
                            for (Node var18_18 : var14_14.getChildren()) {
                                if (this.isFilterNode(var18_18)) {
                                    var19_19 = false;
                                    for (Node var21_22 : var15_15.getChildren()) {
                                        if (!this.isFilterNode(var21_22)) continue;
                                        this.updateFilterNode(var16_16, var21_22, var18_18, var3_3);
                                        var19_19 = true;
                                        break;
                                    }
                                    if (var19_19) continue;
                                    var20_21 = new Node(var16_16, var18_18.getName(), var18_18.getType());
                                    var15_15.getChildren().add((Node)var20_21);
                                    continue;
                                }
                                var19_20 = new Node(var16_16, var18_18.getName(), var18_18.getType());
                                var19_20.getAttributes().putAll(var18_18.getAttributes());
                                var19_20.getChildren().addAll(var18_18.getChildren());
                                if (!var19_20.getName().equalsIgnoreCase("AppenderRef")) ** GOTO lbl79
                                for (Node var21_22 : var15_15.getChildren()) {
                                    if (!this.isSameReference(var21_22, var19_20)) continue;
                                    var15_15.getChildren().remove(var21_22);
                                    ** GOTO lbl84
                                }
                                ** GOTO lbl84
lbl79:
                                // 2 sources

                                for (Node var21_22 : var15_15.getChildren()) {
                                    if (!this.isSameName(var21_22, var19_20)) continue;
                                    var15_15.getChildren().remove(var21_22);
                                    break;
                                }
lbl84:
                                // 4 sources

                                var15_15.getChildren().add(var19_20);
                            }
                            continue;
lbl88:
                            // 1 sources

                            var16_16.getAttributes().putAll(var14_14.getAttributes());
                            var16_16.getChildren().addAll(var14_14.getChildren());
                            var9_9.getChildren().add(var16_16);
                        }
                        var7_7 = true;
                        continue block11;
                    }
                }
                var9_9.getChildren().addAll(var5_5.getChildren());
                var7_7 = true;
            }
            if (var7_7) continue;
            if (var5_5.getName().equalsIgnoreCase("Properties")) {
                var1_1.getChildren().add(0, var5_5);
                continue;
            }
            var1_1.getChildren().add(var5_5);
        }
    }

    private Node getLoggerNode(Node node, String string) {
        for (Node node2 : node.getChildren()) {
            String string2 = node2.getAttributes().get(NAME);
            if (string == null && string2 == null) {
                return node2;
            }
            if (string2 == null || !string2.equals(string)) continue;
            return node2;
        }
        return null;
    }

    private void updateFilterNode(Node node, Node node2, Node node3, PluginManager pluginManager) {
        if (CompositeFilter.class.isAssignableFrom(node2.getType().getPluginClass())) {
            Node node4 = new Node(node2, node3.getName(), node3.getType());
            node4.getChildren().addAll(node3.getChildren());
            node4.getAttributes().putAll(node3.getAttributes());
            node2.getChildren().add(node4);
        } else {
            PluginType<?> pluginType = pluginManager.getPluginType(FILTERS);
            Node node5 = new Node(node2, FILTERS, pluginType);
            Node node6 = new Node(node5, node3.getName(), node3.getType());
            node6.getAttributes().putAll(node3.getAttributes());
            List<Node> list = node5.getChildren();
            list.add(node2);
            list.add(node6);
            List<Node> list2 = node.getChildren();
            list2.remove(node2);
            list2.add(node5);
        }
    }

    private boolean isFilterNode(Node node) {
        return Filter.class.isAssignableFrom(node.getType().getPluginClass());
    }

    private boolean isSameName(Node node, Node node2) {
        String string = node.getAttributes().get(NAME);
        return string != null && string.toLowerCase().equals(node2.getAttributes().get(NAME).toLowerCase());
    }

    private boolean isSameReference(Node node, Node node2) {
        String string = node.getAttributes().get(REF);
        return string != null && string.toLowerCase().equals(node2.getAttributes().get(REF).toLowerCase());
    }
}

