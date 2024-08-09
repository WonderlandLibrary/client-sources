/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.script;

import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.script.AbstractScript;
import org.apache.logging.log4j.core.script.ScriptManager;

@Plugin(name="ScriptRef", category="Core", printObject=true)
public class ScriptRef
extends AbstractScript {
    private final ScriptManager scriptManager;

    public ScriptRef(String string, ScriptManager scriptManager) {
        super(string, null, null);
        this.scriptManager = scriptManager;
    }

    @Override
    public String getLanguage() {
        AbstractScript abstractScript = this.scriptManager.getScript(this.getName());
        return abstractScript != null ? abstractScript.getLanguage() : null;
    }

    @Override
    public String getScriptText() {
        AbstractScript abstractScript = this.scriptManager.getScript(this.getName());
        return abstractScript != null ? abstractScript.getScriptText() : null;
    }

    @PluginFactory
    public static ScriptRef createReference(@PluginAttribute(value="ref") String string, @PluginConfiguration Configuration configuration) {
        if (string == null) {
            LOGGER.error("No script name provided");
            return null;
        }
        return new ScriptRef(string, configuration.getScriptManager());
    }

    public String toString() {
        return "ref=" + this.getName();
    }
}

