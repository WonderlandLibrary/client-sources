/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling.action;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Objects;
import org.apache.logging.log4j.core.appender.rolling.action.PathCondition;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name="IfAny", category="Core", printObject=true)
public final class IfAny
implements PathCondition {
    private final PathCondition[] components;

    private IfAny(PathCondition ... pathConditionArray) {
        this.components = Objects.requireNonNull(pathConditionArray, "filters");
    }

    public PathCondition[] getDeleteFilters() {
        return this.components;
    }

    @Override
    public boolean accept(Path path, Path path2, BasicFileAttributes basicFileAttributes) {
        for (PathCondition pathCondition : this.components) {
            if (!pathCondition.accept(path, path2, basicFileAttributes)) continue;
            return false;
        }
        return true;
    }

    @Override
    public void beforeFileTreeWalk() {
        for (PathCondition pathCondition : this.components) {
            pathCondition.beforeFileTreeWalk();
        }
    }

    @PluginFactory
    public static IfAny createOrCondition(@PluginElement(value="PathConditions") PathCondition ... pathConditionArray) {
        return new IfAny(pathConditionArray);
    }

    public String toString() {
        return "IfAny" + Arrays.toString(this.components);
    }
}

