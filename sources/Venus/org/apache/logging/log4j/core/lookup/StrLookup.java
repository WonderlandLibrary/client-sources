/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.lookup;

import org.apache.logging.log4j.core.LogEvent;

public interface StrLookup {
    public static final String CATEGORY = "Lookup";

    public String lookup(String var1);

    public String lookup(LogEvent var1, String var2);
}

