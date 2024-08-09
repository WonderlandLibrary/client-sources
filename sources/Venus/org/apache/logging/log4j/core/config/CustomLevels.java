/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.core.config.CustomLevelConfig;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name="CustomLevels", category="Core", printObject=true)
public final class CustomLevels {
    private final List<CustomLevelConfig> customLevels;

    private CustomLevels(CustomLevelConfig[] customLevelConfigArray) {
        this.customLevels = new ArrayList<CustomLevelConfig>(Arrays.asList(customLevelConfigArray));
    }

    @PluginFactory
    public static CustomLevels createCustomLevels(@PluginElement(value="CustomLevels") CustomLevelConfig[] customLevelConfigArray) {
        return new CustomLevels(customLevelConfigArray == null ? new CustomLevelConfig[]{} : customLevelConfigArray);
    }

    public List<CustomLevelConfig> getCustomLevels() {
        return this.customLevels;
    }
}

