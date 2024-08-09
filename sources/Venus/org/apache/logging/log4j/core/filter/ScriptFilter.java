/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.filter;

import java.util.Map;
import javax.script.SimpleBindings;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.core.script.AbstractScript;
import org.apache.logging.log4j.core.script.ScriptRef;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ObjectMessage;
import org.apache.logging.log4j.message.SimpleMessage;
import org.apache.logging.log4j.status.StatusLogger;

@Plugin(name="ScriptFilter", category="Core", elementType="filter", printObject=true)
public final class ScriptFilter
extends AbstractFilter {
    private static Logger logger = StatusLogger.getLogger();
    private final AbstractScript script;
    private final Configuration configuration;

    private ScriptFilter(AbstractScript abstractScript, Configuration configuration, Filter.Result result, Filter.Result result2) {
        super(result, result2);
        this.script = abstractScript;
        this.configuration = configuration;
        if (!(abstractScript instanceof ScriptRef)) {
            configuration.getScriptManager().addScript(abstractScript);
        }
    }

    @Override
    public Filter.Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, String string, Object ... objectArray) {
        SimpleBindings simpleBindings = new SimpleBindings();
        simpleBindings.put("logger", (Object)logger);
        simpleBindings.put("level", (Object)level);
        simpleBindings.put("marker", (Object)marker);
        simpleBindings.put("message", (Object)new SimpleMessage(string));
        simpleBindings.put("parameters", (Object)objectArray);
        simpleBindings.put("throwable", (Object)null);
        simpleBindings.putAll((Map<? extends String, ? extends Object>)this.configuration.getProperties());
        simpleBindings.put("substitutor", (Object)this.configuration.getStrSubstitutor());
        Object object = this.configuration.getScriptManager().execute(this.script.getName(), simpleBindings);
        return object == null || !Boolean.TRUE.equals(object) ? this.onMismatch : this.onMatch;
    }

    @Override
    public Filter.Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, Object object, Throwable throwable) {
        SimpleBindings simpleBindings = new SimpleBindings();
        simpleBindings.put("logger", (Object)logger);
        simpleBindings.put("level", (Object)level);
        simpleBindings.put("marker", (Object)marker);
        simpleBindings.put("message", (Object)(object instanceof String ? new SimpleMessage((String)object) : new ObjectMessage(object)));
        simpleBindings.put("parameters", (Object)null);
        simpleBindings.put("throwable", (Object)throwable);
        simpleBindings.putAll((Map<? extends String, ? extends Object>)this.configuration.getProperties());
        simpleBindings.put("substitutor", (Object)this.configuration.getStrSubstitutor());
        Object object2 = this.configuration.getScriptManager().execute(this.script.getName(), simpleBindings);
        return object2 == null || !Boolean.TRUE.equals(object2) ? this.onMismatch : this.onMatch;
    }

    @Override
    public Filter.Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, Message message, Throwable throwable) {
        SimpleBindings simpleBindings = new SimpleBindings();
        simpleBindings.put("logger", (Object)logger);
        simpleBindings.put("level", (Object)level);
        simpleBindings.put("marker", (Object)marker);
        simpleBindings.put("message", (Object)message);
        simpleBindings.put("parameters", (Object)null);
        simpleBindings.put("throwable", (Object)throwable);
        simpleBindings.putAll((Map<? extends String, ? extends Object>)this.configuration.getProperties());
        simpleBindings.put("substitutor", (Object)this.configuration.getStrSubstitutor());
        Object object = this.configuration.getScriptManager().execute(this.script.getName(), simpleBindings);
        return object == null || !Boolean.TRUE.equals(object) ? this.onMismatch : this.onMatch;
    }

    @Override
    public Filter.Result filter(LogEvent logEvent) {
        SimpleBindings simpleBindings = new SimpleBindings();
        simpleBindings.put("logEvent", (Object)logEvent);
        simpleBindings.putAll((Map<? extends String, ? extends Object>)this.configuration.getProperties());
        simpleBindings.put("substitutor", (Object)this.configuration.getStrSubstitutor());
        Object object = this.configuration.getScriptManager().execute(this.script.getName(), simpleBindings);
        return object == null || !Boolean.TRUE.equals(object) ? this.onMismatch : this.onMatch;
    }

    @Override
    public String toString() {
        return this.script.getName();
    }

    @PluginFactory
    public static ScriptFilter createFilter(@PluginElement(value="Script") AbstractScript abstractScript, @PluginAttribute(value="onMatch") Filter.Result result, @PluginAttribute(value="onMismatch") Filter.Result result2, @PluginConfiguration Configuration configuration) {
        if (abstractScript == null) {
            LOGGER.error("A Script, ScriptFile or ScriptRef element must be provided for this ScriptFilter");
            return null;
        }
        if (abstractScript instanceof ScriptRef && configuration.getScriptManager().getScript(abstractScript.getName()) == null) {
            logger.error("No script with name {} has been declared.", (Object)abstractScript.getName());
            return null;
        }
        return new ScriptFilter(abstractScript, configuration, result, result2);
    }
}

