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
    public Script(String string, String string2, String string3) {
        super(string, string2, string3);
    }

    @PluginFactory
    public static Script createScript(@PluginAttribute(value="name") String string, @PluginAttribute(value="language") String string2, @PluginValue(value="scriptText") String string3) {
        if (string2 == null) {
            LOGGER.info("No script language supplied, defaulting to {}", (Object)"JavaScript");
            string2 = "JavaScript";
        }
        if (string3 == null) {
            LOGGER.error("No scriptText attribute provided for ScriptFile {}", (Object)string);
            return null;
        }
        return new Script(string, string2, string3);
    }

    public String toString() {
        return this.getName() != null ? this.getName() : super.toString();
    }
}

