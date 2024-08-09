/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import net.java.games.input.Usage;
import net.java.games.input.UsagePage;

class UsagePair {
    private final UsagePage usage_page;
    private final Usage usage;

    public UsagePair(UsagePage usagePage, Usage usage) {
        this.usage_page = usagePage;
        this.usage = usage;
    }

    public final UsagePage getUsagePage() {
        return this.usage_page;
    }

    public final Usage getUsage() {
        return this.usage;
    }

    public final int hashCode() {
        return this.usage.hashCode() ^ this.usage_page.hashCode();
    }

    public final boolean equals(Object object) {
        if (!(object instanceof UsagePair)) {
            return true;
        }
        UsagePair usagePair = (UsagePair)object;
        return usagePair.usage.equals(this.usage) && usagePair.usage_page.equals(this.usage_page);
    }

    public final String toString() {
        return "UsagePair: (page = " + this.usage_page + ", usage = " + this.usage + ")";
    }
}

