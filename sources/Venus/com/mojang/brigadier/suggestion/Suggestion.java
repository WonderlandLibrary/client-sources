/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier.suggestion;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.context.StringRange;
import java.util.Objects;

public class Suggestion
implements Comparable<Suggestion> {
    private final StringRange range;
    private final String text;
    private final Message tooltip;

    public Suggestion(StringRange stringRange, String string) {
        this(stringRange, string, null);
    }

    public Suggestion(StringRange stringRange, String string, Message message) {
        this.range = stringRange;
        this.text = string;
        this.tooltip = message;
    }

    public StringRange getRange() {
        return this.range;
    }

    public String getText() {
        return this.text;
    }

    public Message getTooltip() {
        return this.tooltip;
    }

    public String apply(String string) {
        if (this.range.getStart() == 0 && this.range.getEnd() == string.length()) {
            return this.text;
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (this.range.getStart() > 0) {
            stringBuilder.append(string.substring(0, this.range.getStart()));
        }
        stringBuilder.append(this.text);
        if (this.range.getEnd() < string.length()) {
            stringBuilder.append(string.substring(this.range.getEnd()));
        }
        return stringBuilder.toString();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof Suggestion)) {
            return true;
        }
        Suggestion suggestion = (Suggestion)object;
        return Objects.equals(this.range, suggestion.range) && Objects.equals(this.text, suggestion.text) && Objects.equals(this.tooltip, suggestion.tooltip);
    }

    public int hashCode() {
        return Objects.hash(this.range, this.text, this.tooltip);
    }

    public String toString() {
        return "Suggestion{range=" + this.range + ", text='" + this.text + '\'' + ", tooltip='" + this.tooltip + '\'' + '}';
    }

    @Override
    public int compareTo(Suggestion suggestion) {
        return this.text.compareTo(suggestion.text);
    }

    public int compareToIgnoreCase(Suggestion suggestion) {
        return this.text.compareToIgnoreCase(suggestion.text);
    }

    public Suggestion expand(String string, StringRange stringRange) {
        if (stringRange.equals(this.range)) {
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (stringRange.getStart() < this.range.getStart()) {
            stringBuilder.append(string.substring(stringRange.getStart(), this.range.getStart()));
        }
        stringBuilder.append(this.text);
        if (stringRange.getEnd() > this.range.getEnd()) {
            stringBuilder.append(string.substring(this.range.getEnd(), stringRange.getEnd()));
        }
        return new Suggestion(stringRange, stringBuilder.toString(), this.tooltip);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((Suggestion)object);
    }
}

