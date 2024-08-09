/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import java.util.Objects;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.status.StatusLogger;

@Plugin(name="CustomLevel", category="Core", printObject=true)
public final class CustomLevelConfig {
    private final String levelName;
    private final int intLevel;

    private CustomLevelConfig(String string, int n) {
        this.levelName = Objects.requireNonNull(string, "levelName is null");
        this.intLevel = n;
    }

    @PluginFactory
    public static CustomLevelConfig createLevel(@PluginAttribute(value="name") String string, @PluginAttribute(value="intLevel") int n) {
        StatusLogger.getLogger().debug("Creating CustomLevel(name='{}', intValue={})", (Object)string, (Object)n);
        Level.forName(string, n);
        return new CustomLevelConfig(string, n);
    }

    public String getLevelName() {
        return this.levelName;
    }

    public int getIntLevel() {
        return this.intLevel;
    }

    public int hashCode() {
        return this.intLevel ^ this.levelName.hashCode();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof CustomLevelConfig)) {
            return true;
        }
        CustomLevelConfig customLevelConfig = (CustomLevelConfig)object;
        return this.intLevel == customLevelConfig.intLevel && this.levelName.equals(customLevelConfig.levelName);
    }

    public String toString() {
        return "CustomLevel[name=" + this.levelName + ", intLevel=" + this.intLevel + "]";
    }
}

