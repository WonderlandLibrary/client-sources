/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.lookup;

import org.apache.logging.log4j.core.lookup.StrLookup;

public abstract class AbstractLookup
implements StrLookup {
    @Override
    public String lookup(String string) {
        return this.lookup(null, string);
    }
}

