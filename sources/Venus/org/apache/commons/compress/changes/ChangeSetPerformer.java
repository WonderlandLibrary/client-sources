/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.changes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.changes.Change;
import org.apache.commons.compress.changes.ChangeSet;
import org.apache.commons.compress.changes.ChangeSetResults;
import org.apache.commons.compress.utils.IOUtils;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ChangeSetPerformer {
    private final Set<Change> changes;

    public ChangeSetPerformer(ChangeSet changeSet) {
        this.changes = changeSet.getChanges();
    }

    public ChangeSetResults perform(ArchiveInputStream archiveInputStream, ArchiveOutputStream archiveOutputStream) throws IOException {
        return this.perform(new ArchiveInputStreamIterator(archiveInputStream), archiveOutputStream);
    }

    public ChangeSetResults perform(ZipFile zipFile, ArchiveOutputStream archiveOutputStream) throws IOException {
        return this.perform(new ZipFileIterator(zipFile), archiveOutputStream);
    }

    private ChangeSetResults perform(ArchiveEntryIterator archiveEntryIterator, ArchiveOutputStream archiveOutputStream) throws IOException {
        ChangeSetResults changeSetResults = new ChangeSetResults();
        LinkedHashSet<Change> linkedHashSet = new LinkedHashSet<Change>(this.changes);
        Iterator iterator2 = linkedHashSet.iterator();
        while (iterator2.hasNext()) {
            Change change = (Change)iterator2.next();
            if (change.type() != 2 || !change.isReplaceMode()) continue;
            this.copyStream(change.getInput(), archiveOutputStream, change.getEntry());
            iterator2.remove();
            changeSetResults.addedFromChangeSet(change.getEntry().getName());
        }
        while (archiveEntryIterator.hasNext()) {
            iterator2 = archiveEntryIterator.next();
            boolean bl = true;
            Iterator iterator3 = linkedHashSet.iterator();
            while (iterator3.hasNext()) {
                Change change = (Change)iterator3.next();
                int n = change.type();
                String string = iterator2.getName();
                if (n == 1 && string != null) {
                    if (!string.equals(change.targetFile())) continue;
                    bl = false;
                    iterator3.remove();
                    changeSetResults.deleted(string);
                    break;
                }
                if (n != 4 || string == null || !string.startsWith(change.targetFile() + "/")) continue;
                bl = false;
                changeSetResults.deleted(string);
                break;
            }
            if (!bl || this.isDeletedLater((Set<Change>)linkedHashSet, (ArchiveEntry)((Object)iterator2)) || changeSetResults.hasBeenAdded(iterator2.getName())) continue;
            this.copyStream(archiveEntryIterator.getInputStream(), archiveOutputStream, (ArchiveEntry)((Object)iterator2));
            changeSetResults.addedFromStream(iterator2.getName());
        }
        iterator2 = linkedHashSet.iterator();
        while (iterator2.hasNext()) {
            Change change = (Change)iterator2.next();
            if (change.type() != 2 || change.isReplaceMode() || changeSetResults.hasBeenAdded(change.getEntry().getName())) continue;
            this.copyStream(change.getInput(), archiveOutputStream, change.getEntry());
            iterator2.remove();
            changeSetResults.addedFromChangeSet(change.getEntry().getName());
        }
        archiveOutputStream.finish();
        return changeSetResults;
    }

    private boolean isDeletedLater(Set<Change> set, ArchiveEntry archiveEntry) {
        String string = archiveEntry.getName();
        if (!set.isEmpty()) {
            for (Change change : set) {
                int n = change.type();
                String string2 = change.targetFile();
                if (n == 1 && string.equals(string2)) {
                    return false;
                }
                if (n != 4 || !string.startsWith(string2 + "/")) continue;
                return false;
            }
        }
        return true;
    }

    private void copyStream(InputStream inputStream, ArchiveOutputStream archiveOutputStream, ArchiveEntry archiveEntry) throws IOException {
        archiveOutputStream.putArchiveEntry(archiveEntry);
        IOUtils.copy(inputStream, archiveOutputStream);
        archiveOutputStream.closeArchiveEntry();
    }

    private static class ZipFileIterator
    implements ArchiveEntryIterator {
        private final ZipFile in;
        private final Enumeration<ZipArchiveEntry> nestedEnum;
        private ZipArchiveEntry current;

        ZipFileIterator(ZipFile zipFile) {
            this.in = zipFile;
            this.nestedEnum = zipFile.getEntriesInPhysicalOrder();
        }

        public boolean hasNext() {
            return this.nestedEnum.hasMoreElements();
        }

        public ArchiveEntry next() {
            this.current = this.nestedEnum.nextElement();
            return this.current;
        }

        public InputStream getInputStream() throws IOException {
            return this.in.getInputStream(this.current);
        }
    }

    private static class ArchiveInputStreamIterator
    implements ArchiveEntryIterator {
        private final ArchiveInputStream in;
        private ArchiveEntry next;

        ArchiveInputStreamIterator(ArchiveInputStream archiveInputStream) {
            this.in = archiveInputStream;
        }

        public boolean hasNext() throws IOException {
            this.next = this.in.getNextEntry();
            return this.next != null;
        }

        public ArchiveEntry next() {
            return this.next;
        }

        public InputStream getInputStream() {
            return this.in;
        }
    }

    static interface ArchiveEntryIterator {
        public boolean hasNext() throws IOException;

        public ArchiveEntry next();

        public InputStream getInputStream() throws IOException;
    }
}

