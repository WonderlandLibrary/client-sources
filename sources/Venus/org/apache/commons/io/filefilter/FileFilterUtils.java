/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.DelegateFileFilter;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.MagicNumberFileFilter;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.OrFileFilter;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.apache.commons.io.filefilter.SizeFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class FileFilterUtils {
    private static final IOFileFilter cvsFilter = FileFilterUtils.notFileFilter(FileFilterUtils.and(FileFilterUtils.directoryFileFilter(), FileFilterUtils.nameFileFilter("CVS")));
    private static final IOFileFilter svnFilter = FileFilterUtils.notFileFilter(FileFilterUtils.and(FileFilterUtils.directoryFileFilter(), FileFilterUtils.nameFileFilter(".svn")));

    public static File[] filter(IOFileFilter iOFileFilter, File ... fileArray) {
        if (iOFileFilter == null) {
            throw new IllegalArgumentException("file filter is null");
        }
        if (fileArray == null) {
            return new File[0];
        }
        ArrayList<File> arrayList = new ArrayList<File>();
        for (File file : fileArray) {
            if (file == null) {
                throw new IllegalArgumentException("file array contains null");
            }
            if (!iOFileFilter.accept(file)) continue;
            arrayList.add(file);
        }
        return arrayList.toArray(new File[arrayList.size()]);
    }

    public static File[] filter(IOFileFilter iOFileFilter, Iterable<File> iterable) {
        List<File> list = FileFilterUtils.filterList(iOFileFilter, iterable);
        return list.toArray(new File[list.size()]);
    }

    public static List<File> filterList(IOFileFilter iOFileFilter, Iterable<File> iterable) {
        return FileFilterUtils.filter(iOFileFilter, iterable, new ArrayList());
    }

    public static List<File> filterList(IOFileFilter iOFileFilter, File ... fileArray) {
        File[] fileArray2 = FileFilterUtils.filter(iOFileFilter, fileArray);
        return Arrays.asList(fileArray2);
    }

    public static Set<File> filterSet(IOFileFilter iOFileFilter, File ... fileArray) {
        File[] fileArray2 = FileFilterUtils.filter(iOFileFilter, fileArray);
        return new HashSet<File>(Arrays.asList(fileArray2));
    }

    public static Set<File> filterSet(IOFileFilter iOFileFilter, Iterable<File> iterable) {
        return FileFilterUtils.filter(iOFileFilter, iterable, new HashSet());
    }

    private static <T extends Collection<File>> T filter(IOFileFilter iOFileFilter, Iterable<File> iterable, T t) {
        if (iOFileFilter == null) {
            throw new IllegalArgumentException("file filter is null");
        }
        if (iterable != null) {
            for (File file : iterable) {
                if (file == null) {
                    throw new IllegalArgumentException("file collection contains null");
                }
                if (!iOFileFilter.accept(file)) continue;
                t.add((File)file);
            }
        }
        return t;
    }

    public static IOFileFilter prefixFileFilter(String string) {
        return new PrefixFileFilter(string);
    }

    public static IOFileFilter prefixFileFilter(String string, IOCase iOCase) {
        return new PrefixFileFilter(string, iOCase);
    }

    public static IOFileFilter suffixFileFilter(String string) {
        return new SuffixFileFilter(string);
    }

    public static IOFileFilter suffixFileFilter(String string, IOCase iOCase) {
        return new SuffixFileFilter(string, iOCase);
    }

    public static IOFileFilter nameFileFilter(String string) {
        return new NameFileFilter(string);
    }

    public static IOFileFilter nameFileFilter(String string, IOCase iOCase) {
        return new NameFileFilter(string, iOCase);
    }

    public static IOFileFilter directoryFileFilter() {
        return DirectoryFileFilter.DIRECTORY;
    }

    public static IOFileFilter fileFileFilter() {
        return FileFileFilter.FILE;
    }

    @Deprecated
    public static IOFileFilter andFileFilter(IOFileFilter iOFileFilter, IOFileFilter iOFileFilter2) {
        return new AndFileFilter(iOFileFilter, iOFileFilter2);
    }

    @Deprecated
    public static IOFileFilter orFileFilter(IOFileFilter iOFileFilter, IOFileFilter iOFileFilter2) {
        return new OrFileFilter(iOFileFilter, iOFileFilter2);
    }

    public static IOFileFilter and(IOFileFilter ... iOFileFilterArray) {
        return new AndFileFilter(FileFilterUtils.toList(iOFileFilterArray));
    }

    public static IOFileFilter or(IOFileFilter ... iOFileFilterArray) {
        return new OrFileFilter(FileFilterUtils.toList(iOFileFilterArray));
    }

    public static List<IOFileFilter> toList(IOFileFilter ... iOFileFilterArray) {
        if (iOFileFilterArray == null) {
            throw new IllegalArgumentException("The filters must not be null");
        }
        ArrayList<IOFileFilter> arrayList = new ArrayList<IOFileFilter>(iOFileFilterArray.length);
        for (int i = 0; i < iOFileFilterArray.length; ++i) {
            if (iOFileFilterArray[i] == null) {
                throw new IllegalArgumentException("The filter[" + i + "] is null");
            }
            arrayList.add(iOFileFilterArray[i]);
        }
        return arrayList;
    }

    public static IOFileFilter notFileFilter(IOFileFilter iOFileFilter) {
        return new NotFileFilter(iOFileFilter);
    }

    public static IOFileFilter trueFileFilter() {
        return TrueFileFilter.TRUE;
    }

    public static IOFileFilter falseFileFilter() {
        return FalseFileFilter.FALSE;
    }

    public static IOFileFilter asFileFilter(FileFilter fileFilter) {
        return new DelegateFileFilter(fileFilter);
    }

    public static IOFileFilter asFileFilter(FilenameFilter filenameFilter) {
        return new DelegateFileFilter(filenameFilter);
    }

    public static IOFileFilter ageFileFilter(long l) {
        return new AgeFileFilter(l);
    }

    public static IOFileFilter ageFileFilter(long l, boolean bl) {
        return new AgeFileFilter(l, bl);
    }

    public static IOFileFilter ageFileFilter(Date date) {
        return new AgeFileFilter(date);
    }

    public static IOFileFilter ageFileFilter(Date date, boolean bl) {
        return new AgeFileFilter(date, bl);
    }

    public static IOFileFilter ageFileFilter(File file) {
        return new AgeFileFilter(file);
    }

    public static IOFileFilter ageFileFilter(File file, boolean bl) {
        return new AgeFileFilter(file, bl);
    }

    public static IOFileFilter sizeFileFilter(long l) {
        return new SizeFileFilter(l);
    }

    public static IOFileFilter sizeFileFilter(long l, boolean bl) {
        return new SizeFileFilter(l, bl);
    }

    public static IOFileFilter sizeRangeFileFilter(long l, long l2) {
        SizeFileFilter sizeFileFilter = new SizeFileFilter(l, true);
        SizeFileFilter sizeFileFilter2 = new SizeFileFilter(l2 + 1L, false);
        return new AndFileFilter(sizeFileFilter, sizeFileFilter2);
    }

    public static IOFileFilter magicNumberFileFilter(String string) {
        return new MagicNumberFileFilter(string);
    }

    public static IOFileFilter magicNumberFileFilter(String string, long l) {
        return new MagicNumberFileFilter(string, l);
    }

    public static IOFileFilter magicNumberFileFilter(byte[] byArray) {
        return new MagicNumberFileFilter(byArray);
    }

    public static IOFileFilter magicNumberFileFilter(byte[] byArray, long l) {
        return new MagicNumberFileFilter(byArray, l);
    }

    public static IOFileFilter makeCVSAware(IOFileFilter iOFileFilter) {
        if (iOFileFilter == null) {
            return cvsFilter;
        }
        return FileFilterUtils.and(iOFileFilter, cvsFilter);
    }

    public static IOFileFilter makeSVNAware(IOFileFilter iOFileFilter) {
        if (iOFileFilter == null) {
            return svnFilter;
        }
        return FileFilterUtils.and(iOFileFilter, svnFilter);
    }

    public static IOFileFilter makeDirectoryOnly(IOFileFilter iOFileFilter) {
        if (iOFileFilter == null) {
            return DirectoryFileFilter.DIRECTORY;
        }
        return new AndFileFilter(DirectoryFileFilter.DIRECTORY, iOFileFilter);
    }

    public static IOFileFilter makeFileOnly(IOFileFilter iOFileFilter) {
        if (iOFileFilter == null) {
            return FileFileFilter.FILE;
        }
        return new AndFileFilter(FileFileFilter.FILE, iOFileFilter);
    }
}

