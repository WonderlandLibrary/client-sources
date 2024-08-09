/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Collection;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

public abstract class DirectoryWalker<T> {
    private final FileFilter filter;
    private final int depthLimit;

    protected DirectoryWalker() {
        this(null, -1);
    }

    protected DirectoryWalker(FileFilter fileFilter, int n) {
        this.filter = fileFilter;
        this.depthLimit = n;
    }

    protected DirectoryWalker(IOFileFilter iOFileFilter, IOFileFilter iOFileFilter2, int n) {
        if (iOFileFilter == null && iOFileFilter2 == null) {
            this.filter = null;
        } else {
            iOFileFilter = iOFileFilter != null ? iOFileFilter : TrueFileFilter.TRUE;
            iOFileFilter2 = iOFileFilter2 != null ? iOFileFilter2 : TrueFileFilter.TRUE;
            iOFileFilter = FileFilterUtils.makeDirectoryOnly(iOFileFilter);
            iOFileFilter2 = FileFilterUtils.makeFileOnly(iOFileFilter2);
            this.filter = FileFilterUtils.or(iOFileFilter, iOFileFilter2);
        }
        this.depthLimit = n;
    }

    protected final void walk(File file, Collection<T> collection) throws IOException {
        if (file == null) {
            throw new NullPointerException("Start Directory is null");
        }
        try {
            this.handleStart(file, collection);
            this.walk(file, 0, collection);
            this.handleEnd(collection);
        } catch (CancelException cancelException) {
            this.handleCancelled(file, collection, cancelException);
        }
    }

    private void walk(File file, int n, Collection<T> collection) throws IOException {
        this.checkIfCancelled(file, n, collection);
        if (this.handleDirectory(file, n, collection)) {
            this.handleDirectoryStart(file, n, collection);
            int n2 = n + 1;
            if (this.depthLimit < 0 || n2 <= this.depthLimit) {
                this.checkIfCancelled(file, n, collection);
                File[] fileArray = this.filter == null ? file.listFiles() : file.listFiles(this.filter);
                fileArray = this.filterDirectoryContents(file, n, fileArray);
                if (fileArray == null) {
                    this.handleRestricted(file, n2, collection);
                } else {
                    for (File file2 : fileArray) {
                        if (file2.isDirectory()) {
                            this.walk(file2, n2, collection);
                            continue;
                        }
                        this.checkIfCancelled(file2, n2, collection);
                        this.handleFile(file2, n2, collection);
                        this.checkIfCancelled(file2, n2, collection);
                    }
                }
            }
            this.handleDirectoryEnd(file, n, collection);
        }
        this.checkIfCancelled(file, n, collection);
    }

    protected final void checkIfCancelled(File file, int n, Collection<T> collection) throws IOException {
        if (this.handleIsCancelled(file, n, collection)) {
            throw new CancelException(file, n);
        }
    }

    protected boolean handleIsCancelled(File file, int n, Collection<T> collection) throws IOException {
        return true;
    }

    protected void handleCancelled(File file, Collection<T> collection, CancelException cancelException) throws IOException {
        throw cancelException;
    }

    protected void handleStart(File file, Collection<T> collection) throws IOException {
    }

    protected boolean handleDirectory(File file, int n, Collection<T> collection) throws IOException {
        return false;
    }

    protected void handleDirectoryStart(File file, int n, Collection<T> collection) throws IOException {
    }

    protected File[] filterDirectoryContents(File file, int n, File[] fileArray) throws IOException {
        return fileArray;
    }

    protected void handleFile(File file, int n, Collection<T> collection) throws IOException {
    }

    protected void handleRestricted(File file, int n, Collection<T> collection) throws IOException {
    }

    protected void handleDirectoryEnd(File file, int n, Collection<T> collection) throws IOException {
    }

    protected void handleEnd(Collection<T> collection) throws IOException {
    }

    public static class CancelException
    extends IOException {
        private static final long serialVersionUID = 1347339620135041008L;
        private final File file;
        private final int depth;

        public CancelException(File file, int n) {
            this("Operation Cancelled", file, n);
        }

        public CancelException(String string, File file, int n) {
            super(string);
            this.file = file;
            this.depth = n;
        }

        public File getFile() {
            return this.file;
        }

        public int getDepth() {
            return this.depth;
        }
    }
}

