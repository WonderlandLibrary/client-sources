/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.script;

import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.PluginValue;
import org.apache.logging.log4j.core.script.AbstractScript;

@Plugin(name="Script", category="Core", printObject=true)
public class Script
extends AbstractScript {
    public Script(String name, String language, String scriptText) {
        super(name, language, scriptText);
    }

    @PluginFactory
    public static Script createScript(@PluginAttribute(value="name") String name, @PluginAttribute(value="language") String language, @PluginValue(value="scriptText") String scriptText) {
        if (language == null) {
            LOGGER.info("No script language supplied, defaulting to {}", (Object)"JavaScript");
            language = "JavaScript";
        }
        if (scriptText == null) {
            LOGGER.error("No scriptText attribute provided for ScriptFile {}", (Object)name);
            return null;
        }
        return new Script(name, language, scriptText);
    }

    public String toString() {
        return this.getName() != null ? this.getName() : super.toString();
    }
}

