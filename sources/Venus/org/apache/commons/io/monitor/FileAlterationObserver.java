/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.monitor;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileEntry;

public class FileAlterationObserver
implements Serializable {
    private static final long serialVersionUID = 1185122225658782848L;
    private final List<FileAlterationListener> listeners = new CopyOnWriteArrayList<FileAlterationListener>();
    private final FileEntry rootEntry;
    private final FileFilter fileFilter;
    private final Comparator<File> comparator;

    public FileAlterationObserver(String string) {
        this(new File(string));
    }

    public FileAlterationObserver(String string, FileFilter fileFilter) {
        this(new File(string), fileFilter);
    }

    public FileAlterationObserver(String string, FileFilter fileFilter, IOCase iOCase) {
        this(new File(string), fileFilter, iOCase);
    }

    public FileAlterationObserver(File file) {
        this(file, null);
    }

    public FileAlterationObserver(File file, FileFilter fileFilter) {
        this(file, fileFilter, null);
    }

    public FileAlterationObserver(File file, FileFilter fileFilter, IOCase iOCase) {
        this(new FileEntry(file), fileFilter, iOCase);
    }

    protected FileAlterationObserver(FileEntry fileEntry, FileFilter fileFilter, IOCase iOCase) {
        if (fileEntry == null) {
            throw new IllegalArgumentException("Root entry is missing");
        }
        if (fileEntry.getFile() == null) {
            throw new IllegalArgumentException("Root directory is missing");
        }
        this.rootEntry = fileEntry;
        this.fileFilter = fileFilter;
        this.comparator = iOCase == null || iOCase.equals(IOCase.SYSTEM) ? NameFileComparator.NAME_SYSTEM_COMPARATOR : (iOCase.equals(IOCase.INSENSITIVE) ? NameFileComparator.NAME_INSENSITIVE_COMPARATOR : NameFileComparator.NAME_COMPARATOR);
    }

    public File getDirectory() {
        return this.rootEntry.getFile();
    }

    public FileFilter getFileFilter() {
        return this.fileFilter;
    }

    public void addListener(FileAlterationListener fileAlterationListener) {
        if (fileAlterationListener != null) {
            this.listeners.add(fileAlterationListener);
        }
    }

    public void removeListener(FileAlterationListener fileAlterationListener) {
        if (fileAlterationListener != null) {
            while (this.listeners.remove(fileAlterationListener)) {
            }
        }
    }

    public Iterable<FileAlterationListener> getListeners() {
        return this.listeners;
    }

    public void initialize() throws Exception {
        this.rootEntry.refresh(this.rootEntry.getFile());
        FileEntry[] fileEntryArray = this.doListFiles(this.rootEntry.getFile(), this.rootEntry);
        this.rootEntry.setChildren(fileEntryArray);
    }

    public void destroy() throws Exception {
    }

    public void checkAndNotify() {
        for (FileAlterationListener object : this.listeners) {
            object.onStart(this);
        }
        File file = this.rootEntry.getFile();
        if (file.exists()) {
            this.checkAndNotify(this.rootEntry, this.rootEntry.getChildren(), this.listFiles(file));
        } else if (this.rootEntry.isExists()) {
            this.checkAndNotify(this.rootEntry, this.rootEntry.getChildren(), FileUtils.EMPTY_FILE_ARRAY);
        }
        for (FileAlterationListener fileAlterationListener : this.listeners) {
            fileAlterationListener.onStop(this);
        }
    }

    private void checkAndNotify(FileEntry fileEntry, FileEntry[] fileEntryArray, File[] fileArray) {
        int n = 0;
        FileEntry[] fileEntryArray2 = fileArray.length > 0 ? new FileEntry[fileArray.length] : FileEntry.EMPTY_ENTRIES;
        for (FileEntry fileEntry2 : fileEntryArray) {
            while (n < fileArray.length && this.comparator.compare(fileEntry2.getFile(), fileArray[n]) > 0) {
                fileEntryArray2[n] = this.createFileEntry(fileEntry, fileArray[n]);
                this.doCreate(fileEntryArray2[n]);
                ++n;
            }
            if (n < fileArray.length && this.comparator.compare(fileEntry2.getFile(), fileArray[n]) == 0) {
                this.doMatch(fileEntry2, fileArray[n]);
                this.checkAndNotify(fileEntry2, fileEntry2.getChildren(), this.listFiles(fileArray[n]));
                fileEntryArray2[n] = fileEntry2;
                ++n;
                continue;
            }
            this.checkAndNotify(fileEntry2, fileEntry2.getChildren(), FileUtils.EMPTY_FILE_ARRAY);
            this.doDelete(fileEntry2);
        }
        while (n < fileArray.length) {
            fileEntryArray2[n] = this.createFileEntry(fileEntry, fileArray[n]);
            this.doCreate(fileEntryArray2[n]);
            ++n;
        }
        fileEntry.setChildren(fileEntryArray2);
    }

    private FileEntry createFileEntry(FileEntry fileEntry, File file) {
        FileEntry fileEntry2 = fileEntry.newChildInstance(file);
        fileEntry2.refresh(file);
        FileEntry[] fileEntryArray = this.doListFiles(file, fileEntry2);
        fileEntry2.setChildren(fileEntryArray);
        return fileEntry2;
    }

    private FileEntry[] doListFiles(File file, FileEntry fileEntry) {
        File[] fileArray = this.listFiles(file);
        FileEntry[] fileEntryArray = fileArray.length > 0 ? new FileEntry[fileArray.length] : FileEntry.EMPTY_ENTRIES;
        for (int i = 0; i < fileArray.length; ++i) {
            fileEntryArray[i] = this.createFileEntry(fileEntry, fileArray[i]);
        }
        return fileEntryArray;
    }

    private void doCreate(FileEntry fileEntry) {
        FileEntry[] fileEntryArray = this.listeners.iterator();
        while (fileEntryArray.hasNext()) {
            FileEntry[] fileEntryArray2 = fileEntryArray.next();
            if (fileEntry.isDirectory()) {
                fileEntryArray2.onDirectoryCreate(fileEntry.getFile());
                continue;
            }
            fileEntryArray2.onFileCreate(fileEntry.getFile());
        }
        for (FileEntry fileEntry2 : fileEntryArray = fileEntry.getChildren()) {
            this.doCreate(fileEntry2);
        }
    }

    private void doMatch(FileEntry fileEntry, File file) {
        if (fileEntry.refresh(file)) {
            for (FileAlterationListener fileAlterationListener : this.listeners) {
                if (fileEntry.isDirectory()) {
                    fileAlterationListener.onDirectoryChange(file);
                    continue;
                }
                fileAlterationListener.onFileChange(file);
            }
        }
    }

    private void doDelete(FileEntry fileEntry) {
        for (FileAlterationListener fileAlterationListener : this.listeners) {
            if (fileEntry.isDirectory()) {
                fileAlterationListener.onDirectoryDelete(fileEntry.getFile());
                continue;
            }
            fileAlterationListener.onFileDelete(fileEntry.getFile());
        }
    }

    private File[] listFiles(File file) {
        File[] fileArray = null;
        if (file.isDirectory()) {
            File[] fileArray2 = fileArray = this.fileFilter == null ? file.listFiles() : file.listFiles(this.fileFilter);
        }
        if (fileArray == null) {
            fileArray = FileUtils.EMPTY_FILE_ARRAY;
        }
        if (this.comparator != null && fileArray.length > 1) {
            Arrays.sort(fileArray, this.comparator);
        }
        return fileArray;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append("[file='");
        stringBuilder.append(this.getDirectory().getPath());
        stringBuilder.append('\'');
        if (this.fileFilter != null) {
            stringBuilder.append(", ");
            stringBuilder.append(this.fileFilter.toString());
        }
        stringBuilder.append(", listeners=");
        stringBuilder.append(this.listeners.size());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

