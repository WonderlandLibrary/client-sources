/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.builder;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.builder.Diff;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DiffResult
implements Iterable<Diff<?>> {
    public static final String OBJECTS_SAME_STRING = "";
    private static final String DIFFERS_STRING = "differs from";
    private final List<Diff<?>> diffs;
    private final Object lhs;
    private final Object rhs;
    private final ToStringStyle style;

    DiffResult(Object object, Object object2, List<Diff<?>> list, ToStringStyle toStringStyle) {
        if (object == null) {
            throw new IllegalArgumentException("Left hand object cannot be null");
        }
        if (object2 == null) {
            throw new IllegalArgumentException("Right hand object cannot be null");
        }
        if (list == null) {
            throw new IllegalArgumentException("List of differences cannot be null");
        }
        this.diffs = list;
        this.lhs = object;
        this.rhs = object2;
        this.style = toStringStyle == null ? ToStringStyle.DEFAULT_STYLE : toStringStyle;
    }

    public List<Diff<?>> getDiffs() {
        return Collections.unmodifiableList(this.diffs);
    }

    public int getNumberOfDiffs() {
        return this.diffs.size();
    }

    public ToStringStyle getToStringStyle() {
        return this.style;
    }

    public String toString() {
        return this.toString(this.style);
    }

    public String toString(ToStringStyle toStringStyle) {
        if (this.diffs.size() == 0) {
            return OBJECTS_SAME_STRING;
        }
        ToStringBuilder toStringBuilder = new ToStringBuilder(this.lhs, toStringStyle);
        ToStringBuilder toStringBuilder2 = new ToStringBuilder(this.rhs, toStringStyle);
        for (Diff<?> diff : this.diffs) {
            toStringBuilder.append(diff.getFieldName(), diff.getLeft());
            toStringBuilder2.append(diff.getFieldName(), diff.getRight());
        }
        return String.format("%s %s %s", toStringBuilder.build(), DIFFERS_STRING, toStringBuilder2.build());
    }

    @Override
    public Iterator<Diff<?>> iterator() {
        return this.diffs.iterator();
    }
}

