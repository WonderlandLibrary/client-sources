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
import org.apache.logging.log4j.core.appender.rolling.FileSize;
import org.apache.logging.log4j.core.appender.rolling.action.IfAll;
import org.apache.logging.log4j.core.appender.rolling.action.PathCondition;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.status.StatusLogger;

@Plugin(name="IfAccumulatedFileSize", category="Core", printObject=true)
public final class IfAccumulatedFileSize
implements PathCondition {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private final long thresholdBytes;
    private long accumulatedSize;
    private final PathCondition[] nestedConditions;

    private IfAccumulatedFileSize(long l, PathCondition[] pathConditionArray) {
        if (l <= 0L) {
            throw new IllegalArgumentException("Count must be a positive integer but was " + l);
        }
        this.thresholdBytes = l;
        this.nestedConditions = pathConditionArray == null ? new PathCondition[]{} : Arrays.copyOf(pathConditionArray, pathConditionArray.length);
    }

    public long getThresholdBytes() {
        return this.thresholdBytes;
    }

    public List<PathCondition> getNestedConditions() {
        return Collections.unmodifiableList(Arrays.asList(this.nestedConditions));
    }

    @Override
    public boolean accept(Path path, Path path2, BasicFileAttributes basicFileAttributes) {
        this.accumulatedSize += basicFileAttributes.size();
        boolean bl = this.accumulatedSize > this.thresholdBytes;
        String string = bl ? ">" : "<=";
        String string2 = bl ? "ACCEPTED" : "REJECTED";
        LOGGER.trace("IfAccumulatedFileSize {}: {} accumulated size '{}' {} thresholdBytes '{}'", (Object)string2, (Object)path2, (Object)this.accumulatedSize, (Object)string, (Object)this.thresholdBytes);
        if (bl) {
            return IfAll.accept(this.nestedConditions, path, path2, basicFileAttributes);
        }
        return bl;
    }

    @Override
    public void beforeFileTreeWalk() {
        this.accumulatedSize = 0L;
        IfAll.beforeFileTreeWalk(this.nestedConditions);
    }

    @PluginFactory
    public static IfAccumulatedFileSize createFileSizeCondition(@PluginAttribute(value="exceeds") String string, @PluginElement(value="PathConditions") PathCondition ... pathConditionArray) {
        if (string == null) {
            LOGGER.error("IfAccumulatedFileSize missing mandatory size threshold.");
        }
        long l = string == null ? Long.MAX_VALUE : FileSize.parse(string, Long.MAX_VALUE);
        return new IfAccumulatedFileSize(l, pathConditionArray);
    }

    public String toString() {
        String string = this.nestedConditions.length == 0 ? "" : " AND " + Arrays.toString(this.nestedConditions);
        return "IfAccumulatedFileSize(exceeds=" + this.thresholdBytes + string + ")";
    }
}

