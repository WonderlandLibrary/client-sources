/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.resolver;

import java.util.regex.Pattern;
import org.yaml.snakeyaml.nodes.Tag;

final class ResolverTuple {
    private final Tag tag;
    private final Pattern regexp;
    private final int limit;

    public ResolverTuple(Tag tag, Pattern pattern, int n) {
        this.tag = tag;
        this.regexp = pattern;
        this.limit = n;
    }

    public Tag getTag() {
        return this.tag;
    }

    public Pattern getRegexp() {
        return this.regexp;
    }

    public int getLimit() {
        return this.limit;
    }

    public String toString() {
        return "Tuple tag=" + this.tag + " regexp=" + this.regexp + " limit=" + this.limit;
    }
}

