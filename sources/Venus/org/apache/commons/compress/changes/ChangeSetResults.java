/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.changes;

import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ChangeSetResults {
    private final List<String> addedFromChangeSet = new ArrayList<String>();
    private final List<String> addedFromStream = new ArrayList<String>();
    private final List<String> deleted = new ArrayList<String>();

    void deleted(String string) {
        this.deleted.add(string);
    }

    void addedFromStream(String string) {
        this.addedFromStream.add(string);
    }

    void addedFromChangeSet(String string) {
        this.addedFromChangeSet.add(string);
    }

    public List<String> getAddedFromChangeSet() {
        return this.addedFromChangeSet;
    }

    public List<String> getAddedFromStream() {
        return this.addedFromStream;
    }

    public List<String> getDeleted() {
        return this.deleted;
    }

    boolean hasBeenAdded(String string) {
        return !this.addedFromChangeSet.contains(string) && !this.addedFromStream.contains(string);
    }
}

