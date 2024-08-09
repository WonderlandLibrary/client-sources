/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling.action;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.rolling.action.IfAll;
import org.apache.logging.log4j.core.appender.rolling.action.PathCondition;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.status.StatusLogger;

@Plugin(name="IfAccumulatedFileCount", category="Core", printObject=true)
public final class IfAccumulatedFileCount
implements PathCondition {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private final int threshold;
    private int count;
    private final PathCondition[] nestedConditions;

    private IfAccumulatedFileCount(int n, PathCondition[] pathConditionArray) {
        if (n <= 0) {
            throw new IllegalArgumentException("Count must be a positive integer but was " + n);
        }
        this.threshold = n;
        this.nestedConditions = pathConditionArray == null ? new PathCondition[]{} : Arrays.copyOf(pathConditionArray, pathConditionArray.length);
    }

    public int getThresholdCount() {
        return this.threshold;
    }

    public List<PathCondition> getNestedConditions() {
        return Collections.unmodifiableList(Arrays.asList(this.nestedConditions));
    }

    @Override
    public boolean accept(Path path, Path path2, BasicFileAttributes basicFileAttributes) {
        boolean bl = ++this.count > this.threshold;
        String string = bl ? ">" : "<=";
        String string2 = bl ? "ACCEPTED" : "REJECTED";
        LOGGER.trace("IfAccumulatedFileCount {}: {} count '{}' {} threshold '{}'", (Object)string2, (Object)path2, (Object)this.count, (Object)string, (Object)this.threshold);
        if (bl) {
            return IfAll.accept(this.nestedConditions, path, path2, basicFileAttributes);
        }
        return bl;
    }

    @Override
    public void beforeFileTreeWalk() {
        this.count = 0;
        IfAll.beforeFileTreeWalk(this.nestedConditions);
    }

    @PluginFactory
    public static IfAccumulatedFileCount createFileCountCondition(@PluginAttribute(value="exceeds", defaultInt=0x7FFFFFFF) int n, @PluginElement(value="PathConditions") PathCondition ... pathConditionArray) {
        if (n == Integer.MAX_VALUE) {
            LOGGER.error("IfAccumulatedFileCount invalid or missing threshold value.");
        }
        return new IfAccumulatedFileCount(n, pathConditionArray);
    }

    public String toString() {
        String string = this.nestedConditions.length == 0 ? "" : " AND " + Arrays.toString(this.nestedConditions);
        return "IfAccumulatedFileCount(exceeds=" + this.threshold + string + ")";
    }
}

