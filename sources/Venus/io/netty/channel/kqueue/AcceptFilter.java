/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.kqueue;

import io.netty.util.internal.ObjectUtil;

public final class AcceptFilter {
    static final AcceptFilter PLATFORM_UNSUPPORTED = new AcceptFilter("", "");
    private final String filterName;
    private final String filterArgs;

    public AcceptFilter(String string, String string2) {
        this.filterName = ObjectUtil.checkNotNull(string, "filterName");
        this.filterArgs = ObjectUtil.checkNotNull(string2, "filterArgs");
    }

    public String filterName() {
        return this.filterName;
    }

    public String filterArgs() {
        return this.filterArgs;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof AcceptFilter)) {
            return true;
        }
        AcceptFilter acceptFilter = (AcceptFilter)object;
        return this.filterName.equals(acceptFilter.filterName) && this.filterArgs.equals(acceptFilter.filterArgs);
    }

    public int hashCode() {
        return 31 * (31 + this.filterName.hashCode()) + this.filterArgs.hashCode();
    }

    public String toString() {
        return this.filterName + ", " + this.filterArgs;
    }
}

