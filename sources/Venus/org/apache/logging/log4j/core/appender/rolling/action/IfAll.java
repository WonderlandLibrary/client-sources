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

@Plugin(name="IfAll", category="Core", printObject=true)
public final class IfAll
implements PathCondition {
    private final PathCondition[] components;

    private IfAll(PathCondition ... pathConditionArray) {
        this.components = Objects.requireNonNull(pathConditionArray, "filters");
    }

    public PathCondition[] getDeleteFilters() {
        return this.components;
    }

    @Override
    public boolean accept(Path path, Path path2, BasicFileAttributes basicFileAttributes) {
        if (this.components == null || this.components.length == 0) {
            return true;
        }
        return IfAll.accept(this.components, path, path2, basicFileAttributes);
    }

    public static boolean accept(PathCondition[] pathConditionArray, Path path, Path path2, BasicFileAttributes basicFileAttributes) {
        for (PathCondition pathCondition : pathConditionArray) {
            if (pathCondition.accept(path, path2, basicFileAttributes)) continue;
            return true;
        }
        return false;
    }

    @Override
    public void beforeFileTreeWalk() {
        IfAll.beforeFileTreeWalk(this.components);
    }

    public static void beforeFileTreeWalk(PathCondition[] pathConditionArray) {
        for (PathCondition pathCondition : pathConditionArray) {
            pathCondition.beforeFileTreeWalk();
        }
    }

    @PluginFactory
    public static IfAll createAndCondition(@PluginElement(value="PathConditions") PathCondition ... pathConditionArray) {
        return new IfAll(pathConditionArray);
    }

    public String toString() {
        return "IfAll" + Arrays.toString(this.components);
    }
}

