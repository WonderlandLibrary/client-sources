/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.changes;

import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.changes.Change;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class ChangeSet {
    private final Set<Change> changes = new LinkedHashSet<Change>();

    public void delete(String string) {
        this.addDeletion(new Change(string, 1));
    }

    public void deleteDir(String string) {
        this.addDeletion(new Change(string, 4));
    }

    public void add(ArchiveEntry archiveEntry, InputStream inputStream) {
        this.add(archiveEntry, inputStream, false);
    }

    public void add(ArchiveEntry archiveEntry, InputStream inputStream, boolean bl) {
        this.addAddition(new Change(archiveEntry, inputStream, bl));
    }

    private void addAddition(Change change) {
        if (2 != change.type() || change.getInput() == null) {
            return;
        }
        if (!this.changes.isEmpty()) {
            Iterator<Change> iterator2 = this.changes.iterator();
            while (iterator2.hasNext()) {
                ArchiveEntry archiveEntry;
                Change change2 = iterator2.next();
                if (change2.type() != 2 || change2.getEntry() == null || !(archiveEntry = change2.getEntry()).equals(change.getEntry())) continue;
                if (change.isReplaceMode()) {
                    iterator2.remove();
                    this.changes.add(change);
                    return;
                }
                return;
            }
        }
        this.changes.add(change);
    }

    private void addDeletion(Change change) {
        if (1 != change.type() && 4 != change.type() || change.targetFile() == null) {
            return;
        }
        String string = change.targetFile();
        if (string != null && !this.changes.isEmpty()) {
            Iterator<Change> iterator2 = this.changes.iterator();
            while (iterator2.hasNext()) {
                String string2;
                Change change2 = iterator2.next();
                if (change2.type() != 2 || change2.getEntry() == null || (string2 = change2.getEntry().getName()) == null) continue;
                if (1 == change.type() && string.equals(string2)) {
                    iterator2.remove();
                    continue;
                }
                if (4 != change.type() || !string2.matches(string + "/.*")) continue;
                iterator2.remove();
            }
        }
        this.changes.add(change);
    }

    Set<Change> getChanges() {
        return new LinkedHashSet<Change>(this.changes);
    }
}

