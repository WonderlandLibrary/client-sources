/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling.action;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.rolling.action.Duration;
import org.apache.logging.log4j.core.appender.rolling.action.IfAll;
import org.apache.logging.log4j.core.appender.rolling.action.PathCondition;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.util.Clock;
import org.apache.logging.log4j.core.util.ClockFactory;
import org.apache.logging.log4j.status.StatusLogger;

@Plugin(name="IfLastModified", category="Core", printObject=true)
public final class IfLastModified
implements PathCondition {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final Clock CLOCK = ClockFactory.getClock();
    private final Duration age;
    private final PathCondition[] nestedConditions;

    private IfLastModified(Duration duration, PathCondition[] pathConditionArray) {
        this.age = Objects.requireNonNull(duration, "age");
        this.nestedConditions = pathConditionArray == null ? new PathCondition[]{} : Arrays.copyOf(pathConditionArray, pathConditionArray.length);
    }

    public Duration getAge() {
        return this.age;
    }

    public List<PathCondition> getNestedConditions() {
        return Collections.unmodifiableList(Arrays.asList(this.nestedConditions));
    }

    @Override
    public boolean accept(Path path, Path path2, BasicFileAttributes basicFileAttributes) {
        FileTime fileTime = basicFileAttributes.lastModifiedTime();
        long l = fileTime.toMillis();
        long l2 = CLOCK.currentTimeMillis() - l;
        boolean bl = l2 >= this.age.toMillis();
        String string = bl ? ">=" : "<";
        String string2 = bl ? "ACCEPTED" : "REJECTED";
        LOGGER.trace("IfLastModified {}: {} ageMillis '{}' {} '{}'", (Object)string2, (Object)path2, (Object)l2, (Object)string, (Object)this.age);
        if (bl) {
            return IfAll.accept(this.nestedConditions, path, path2, basicFileAttributes);
        }
        return bl;
    }

    @Override
    public void beforeFileTreeWalk() {
        IfAll.beforeFileTreeWalk(this.nestedConditions);
    }

    @PluginFactory
    public static IfLastModified createAgeCondition(@PluginAttribute(value="age") Duration duration, @PluginElement(value="PathConditions") PathCondition ... pathConditionArray) {
        return new IfLastModified(duration, pathConditionArray);
    }

    public String toString() {
        String string = this.nestedConditions.length == 0 ? "" : " AND " + Arrays.toString(this.nestedConditions);
        return "IfLastModified(age=" + this.age + string + ")";
    }
}

