/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.routing;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.PluginNode;
import org.apache.logging.log4j.status.StatusLogger;

@Plugin(name="Route", category="Core", printObject=true, deferChildren=true)
public final class Route {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private final Node node;
    private final String appenderRef;
    private final String key;

    private Route(Node node, String string, String string2) {
        this.node = node;
        this.appenderRef = string;
        this.key = string2;
    }

    public Node getNode() {
        return this.node;
    }

    public String getAppenderRef() {
        return this.appenderRef;
    }

    public String getKey() {
        return this.key;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Route(");
        stringBuilder.append("type=");
        if (this.appenderRef != null) {
            stringBuilder.append("static Reference=").append(this.appenderRef);
        } else if (this.node != null) {
            stringBuilder.append("dynamic - type=").append(this.node.getName());
        } else {
            stringBuilder.append("invalid Route");
        }
        if (this.key != null) {
            stringBuilder.append(" key='").append(this.key).append('\'');
        } else {
            stringBuilder.append(" default");
        }
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    @PluginFactory
    public static Route createRoute(@PluginAttribute(value="ref") String string, @PluginAttribute(value="key") String string2, @PluginNode Node node) {
        if (node != null && node.hasChildren()) {
            if (string != null) {
                LOGGER.error("A route cannot be configured with an appender reference and an appender definition");
                return null;
            }
        } else if (string == null) {
            LOGGER.error("A route must specify an appender reference or an appender definition");
            return null;
        }
        return new Route(node, string, string2);
    }
}

