/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.spi;

import java.util.EnumSet;
import java.util.Iterator;

public enum StandardLevel {
    OFF(0),
    FATAL(100),
    ERROR(200),
    WARN(300),
    INFO(400),
    DEBUG(500),
    TRACE(600),
    ALL(Integer.MAX_VALUE);

    private static final EnumSet<StandardLevel> LEVELSET;
    private final int intLevel;

    private StandardLevel(int n2) {
        this.intLevel = n2;
    }

    public int intLevel() {
        return this.intLevel;
    }

    public static StandardLevel getStandardLevel(int n) {
        StandardLevel standardLevel;
        StandardLevel standardLevel2 = OFF;
        Iterator iterator2 = LEVELSET.iterator();
        while (iterator2.hasNext() && (standardLevel = (StandardLevel)((Object)iterator2.next())).intLevel() <= n) {
            standardLevel2 = standardLevel;
        }
        return standardLevel2;
    }

    static {
        LEVELSET = EnumSet.allOf(StandardLevel.class);
    }
}

