/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling.action;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.script.SimpleBindings;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.rolling.action.PathWithAttributes;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.script.AbstractScript;
import org.apache.logging.log4j.core.script.ScriptRef;
import org.apache.logging.log4j.status.StatusLogger;

@Plugin(name="ScriptCondition", category="Core", printObject=true)
public class ScriptCondition {
    private static Logger LOGGER = StatusLogger.getLogger();
    private final AbstractScript script;
    private final Configuration configuration;

    public ScriptCondition(AbstractScript abstractScript, Configuration configuration) {
        this.script = Objects.requireNonNull(abstractScript, "script");
        this.configuration = Objects.requireNonNull(configuration, "configuration");
        if (!(abstractScript instanceof ScriptRef)) {
            configuration.getScriptManager().addScript(abstractScript);
        }
    }

    public List<PathWithAttributes> selectFilesToDelete(Path path, List<PathWithAttributes> list) {
        SimpleBindings simpleBindings = new SimpleBindings();
        simpleBindings.put("basePath", (Object)path);
        simpleBindings.put("pathList", (Object)list);
        simpleBindings.putAll((Map<? extends String, ? extends Object>)this.configuration.getProperties());
        simpleBindings.put("configuration", (Object)this.configuration);
        simpleBindings.put("substitutor", (Object)this.configuration.getStrSubstitutor());
        simpleBindings.put("statusLogger", (Object)LOGGER);
        Object object = this.configuration.getScriptManager().execute(this.script.getName(), simpleBindings);
        return (List)object;
    }

    @PluginFactory
    public static ScriptCondition createCondition(@PluginElement(value="Script") AbstractScript abstractScript, @PluginConfiguration Configuration configuration) {
        if (abstractScript == null) {
            LOGGER.error("A Script, ScriptFile or ScriptRef element must be provided for this ScriptCondition");
            return null;
        }
        if (abstractScript instanceof ScriptRef && configuration.getScriptManager().getScript(abstractScript.getName()) == null) {
            LOGGER.error("ScriptCondition: No script with name {} has been declared.", (Object)abstractScript.getName());
            return null;
        }
        return new ScriptCondition(abstractScript, configuration);
    }
}

