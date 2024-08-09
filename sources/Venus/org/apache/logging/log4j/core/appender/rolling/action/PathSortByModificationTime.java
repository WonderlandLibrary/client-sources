/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling.action;

import java.io.Serializable;
import org.apache.logging.log4j.core.appender.rolling.action.PathSorter;
import org.apache.logging.log4j.core.appender.rolling.action.PathWithAttributes;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name="SortByModificationTime", category="Core", printObject=true)
public class PathSortByModificationTime
implements PathSorter,
Serializable {
    private static final long serialVersionUID = 1L;
    private final boolean recentFirst;
    private final int multiplier;

    public PathSortByModificationTime(boolean bl) {
        this.recentFirst = bl;
        this.multiplier = bl ? 1 : -1;
    }

    @PluginFactory
    public static PathSorter createSorter(@PluginAttribute(value="recentFirst", defaultBoolean=true) boolean bl) {
        return new PathSortByModificationTime(bl);
    }

    public boolean isRecentFirst() {
        return this.recentFirst;
    }

    @Override
    public int compare(PathWithAttributes pathWithAttributes, PathWithAttributes pathWithAttributes2) {
        long l = pathWithAttributes.getAttributes().lastModifiedTime().toMillis();
        long l2 = pathWithAttributes2.getAttributes().lastModifiedTime().toMillis();
        int n = Long.signum(l2 - l);
        if (n == 0) {
            try {
                n = pathWithAttributes2.getPath().compareTo(pathWithAttributes.getPath());
            } catch (ClassCastException classCastException) {
                n = pathWithAttributes2.getPath().toString().compareTo(pathWithAttributes.getPath().toString());
            }
        }
        return this.multiplier * n;
    }

    @Override
    public int compare(Object object, Object object2) {
        return this.compare((PathWithAttributes)object, (PathWithAttributes)object2);
    }
}

