/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier.suggestion;

import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class Suggestions {
    private static final Suggestions EMPTY = new Suggestions(StringRange.at(0), new ArrayList<Suggestion>());
    private final StringRange range;
    private final List<Suggestion> suggestions;

    public Suggestions(StringRange stringRange, List<Suggestion> list) {
        this.range = stringRange;
        this.suggestions = list;
    }

    public StringRange getRange() {
        return this.range;
    }

    public List<Suggestion> getList() {
        return this.suggestions;
    }

    public boolean isEmpty() {
        return this.suggestions.isEmpty();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof Suggestions)) {
            return true;
        }
        Suggestions suggestions = (Suggestions)object;
        return Objects.equals(this.range, suggestions.range) && Objects.equals(this.suggestions, suggestions.suggestions);
    }

    public int hashCode() {
        return Objects.hash(this.range, this.suggestions);
    }

    public String toString() {
        return "Suggestions{range=" + this.range + ", suggestions=" + this.suggestions + '}';
    }

    public static CompletableFuture<Suggestions> empty() {
        return CompletableFuture.completedFuture(EMPTY);
    }

    public static Suggestions merge(String string, Collection<Suggestions> collection) {
        if (collection.isEmpty()) {
            return EMPTY;
        }
        if (collection.size() == 1) {
            return collection.iterator().next();
        }
        HashSet<Suggestion> hashSet = new HashSet<Suggestion>();
        for (Suggestions suggestions : collection) {
            hashSet.addAll(suggestions.getList());
        }
        return Suggestions.create(string, hashSet);
    }

    public static Suggestions create(String string, Collection<Suggestion> collection) {
        if (collection.isEmpty()) {
            return EMPTY;
        }
        int n = Integer.MAX_VALUE;
        int n2 = Integer.MIN_VALUE;
        for (Suggestion object2 : collection) {
            n = Math.min(object2.getRange().getStart(), n);
            n2 = Math.max(object2.getRange().getEnd(), n2);
        }
        StringRange stringRange = new StringRange(n, n2);
        HashSet<Suggestion> hashSet = new HashSet<Suggestion>();
        for (Suggestion suggestion : collection) {
            hashSet.add(suggestion.expand(string, stringRange));
        }
        ArrayList arrayList = new ArrayList(hashSet);
        arrayList.sort(Suggestions::lambda$create$0);
        return new Suggestions(stringRange, arrayList);
    }

    private static int lambda$create$0(Suggestion suggestion, Suggestion suggestion2) {
        return suggestion.compareToIgnoreCase(suggestion2);
    }
}

