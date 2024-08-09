/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.Java7Support;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.output.NullOutputStream;

public class FileUtils {
    public static final long ONE_KB = 1024L;
    public static final BigInteger ONE_KB_BI = BigInteger.valueOf(1024L);
    public static final long ONE_MB = 0x100000L;
    public static final BigInteger ONE_MB_BI = ONE_KB_BI.multiply(ONE_KB_BI);
    private static final long FILE_COPY_BUFFER_SIZE = 0x1E00000L;
    public static final long ONE_GB = 0x40000000L;
    public static final BigInteger ONE_GB_BI = ONE_KB_BI.multiply(ONE_MB_BI);
    public static final long ONE_TB = 0x10000000000L;
    public static final BigInteger ONE_TB_BI = ONE_KB_BI.multiply(ONE_GB_BI);
    public static final long ONE_PB = 0x4000000000000L;
    public static final BigInteger ONE_PB_BI = ONE_KB_BI.multiply(ONE_TB_BI);
    public static final long ONE_EB = 0x1000000000000000L;
    public static final BigInteger ONE_EB_BI = ONE_KB_BI.multiply(ONE_PB_BI);
    public static final BigInteger ONE_ZB = BigInteger.valueOf(1024L).multiply(BigInteger.valueOf(0x1000000000000000L));
    public static final BigInteger ONE_YB = ONE_KB_BI.multiply(ONE_ZB);
    public static final File[] EMPTY_FILE_ARRAY = new File[0];

    public static File getFile(File file, String ... stringArray) {
        if (file == null) {
            throw new NullPointerException("directory must not be null");
        }
        if (stringArray == null) {
            throw new NullPointerException("names must not be null");
        }
        File file2 = file;
        for (String string : stringArray) {
            file2 = new File(file2, string);
        }
        return file2;
    }

    public static File getFile(String ... stringArray) {
        if (stringArray == null) {
            throw new NullPointerException("names must not be null");
        }
        File file = null;
        for (String string : stringArray) {
            file = file == null ? new File(string) : new File(file, string);
        }
        return file;
    }

    public static String getTempDirectoryPath() {
        return System.getProperty("java.io.tmpdir");
    }

    public static File getTempDirectory() {
        return new File(FileUtils.getTempDirectoryPath());
    }

    public static String getUserDirectoryPath() {
        return System.getProperty("user.home");
    }

    public static File getUserDirectory() {
        return new File(FileUtils.getUserDirectoryPath());
    }

    public static FileInputStream openInputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canRead()) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        return new FileInputStream(file);
    }

    public static FileOutputStream openOutputStream(File file) throws IOException {
        return FileUtils.openOutputStream(file, false);
    }

    public static FileOutputStream openOutputStream(File file, boolean bl) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            File file2 = file.getParentFile();
            if (file2 != null && !file2.mkdirs() && !file2.isDirectory()) {
                throw new IOException("Directory '" + file2 + "' could not be created");
            }
        }
        return new FileOutputStream(file, bl);
    }

    public static String byteCountToDisplaySize(BigInteger bigInteger) {
        String string = bigInteger.divide(ONE_EB_BI).compareTo(BigInteger.ZERO) > 0 ? String.valueOf(bigInteger.divide(ONE_EB_BI)) + " EB" : (bigInteger.divide(ONE_PB_BI).compareTo(BigInteger.ZERO) > 0 ? String.valueOf(bigInteger.divide(ONE_PB_BI)) + " PB" : (bigInteger.divide(ONE_TB_BI).compareTo(BigInteger.ZERO) > 0 ? String.valueOf(bigInteger.divide(ONE_TB_BI)) + " TB" : (bigInteger.divide(ONE_GB_BI).compareTo(BigInteger.ZERO) > 0 ? String.valueOf(bigInteger.divide(ONE_GB_BI)) + " GB" : (bigInteger.divide(ONE_MB_BI).compareTo(BigInteger.ZERO) > 0 ? String.valueOf(bigInteger.divide(ONE_MB_BI)) + " MB" : (bigInteger.divide(ONE_KB_BI).compareTo(BigInteger.ZERO) > 0 ? String.valueOf(bigInteger.divide(ONE_KB_BI)) + " KB" : String.valueOf(bigInteger) + " bytes")))));
        return string;
    }

    public static String byteCountToDisplaySize(long l) {
        return FileUtils.byteCountToDisplaySize(BigInteger.valueOf(l));
    }

    public static void touch(File file) throws IOException {
        boolean bl;
        if (!file.exists()) {
            FileOutputStream fileOutputStream = FileUtils.openOutputStream(file);
            IOUtils.closeQuietly(fileOutputStream);
        }
        if (!(bl = file.setLastModified(System.currentTimeMillis()))) {
            throw new IOException("Unable to set the last modification time for " + file);
        }
    }

    public static File[] convertFileCollectionToFileArray(Collection<File> collection) {
        return collection.toArray(new File[collection.size()]);
    }

    private static void innerListFiles(Collection<File> collection, File file, IOFileFilter iOFileFilter, boolean bl) {
        File[] fileArray = file.listFiles(iOFileFilter);
        if (fileArray != null) {
            for (File file2 : fileArray) {
                if (file2.isDirectory()) {
                    if (bl) {
                        collection.add(file2);
                    }
                    FileUtils.innerListFiles(collection, file2, iOFileFilter, bl);
                    continue;
                }
                collection.add(file2);
            }
        }
    }

    public static Collection<File> listFiles(File file, IOFileFilter iOFileFilter, IOFileFilter iOFileFilter2) {
        FileUtils.validateListFilesParameters(file, iOFileFilter);
        IOFileFilter iOFileFilter3 = FileUtils.setUpEffectiveFileFilter(iOFileFilter);
        IOFileFilter iOFileFilter4 = FileUtils.setUpEffectiveDirFilter(iOFileFilter2);
        LinkedList<File> linkedList = new LinkedList<File>();
        FileUtils.innerListFiles(linkedList, file, FileFilterUtils.or(iOFileFilter3, iOFileFilter4), false);
        return linkedList;
    }

    private static void validateListFilesParameters(File file, IOFileFilter iOFileFilter) {
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("Parameter 'directory' is not a directory: " + file);
        }
        if (iOFileFilter == null) {
            throw new NullPointerException("Parameter 'fileFilter' is null");
        }
    }

    private static IOFileFilter setUpEffectiveFileFilter(IOFileFilter iOFileFilter) {
        return FileFilterUtils.and(iOFileFilter, FileFilterUtils.notFileFilter(DirectoryFileFilter.INSTANCE));
    }

    private static IOFileFilter setUpEffectiveDirFilter(IOFileFilter iOFileFilter) {
        return iOFileFilter == null ? FalseFileFilter.INSTANCE : FileFilterUtils.and(iOFileFilter, DirectoryFileFilter.INSTANCE);
    }

    public static Collection<File> listFilesAndDirs(File file, IOFileFilter iOFileFilter, IOFileFilter iOFileFilter2) {
        FileUtils.validateListFilesParameters(file, iOFileFilter);
        IOFileFilter iOFileFilter3 = FileUtils.setUpEffectiveFileFilter(iOFileFilter);
        IOFileFilter iOFileFilter4 = FileUtils.setUpEffectiveDirFilter(iOFileFilter2);
        LinkedList<File> linkedList = new LinkedList<File>();
        if (file.isDirectory()) {
            linkedList.add(file);
        }
        FileUtils.innerListFiles(linkedList, file, FileFilterUtils.or(iOFileFilter3, iOFileFilter4), true);
        return linkedList;
    }

    public static Iterator<File> iterateFiles(File file, IOFileFilter iOFileFilter, IOFileFilter iOFileFilter2) {
        return FileUtils.listFiles(file, iOFileFilter, iOFileFilter2).iterator();
    }

    public static Iterator<File> iterateFilesAndDirs(File file, IOFileFilter iOFileFilter, IOFileFilter iOFileFilter2) {
        return FileUtils.listFilesAndDirs(file, iOFileFilter, iOFileFilter2).iterator();
    }

    private static String[] toSuffixes(String[] stringArray) {
        String[] stringArray2 = new String[stringArray.length];
        for (int i = 0; i < stringArray.length; ++i) {
            stringArray2[i] = "." + stringArray[i];
        }
        return stringArray2;
    }

    public static Collection<File> listFiles(File file, String[] stringArray, boolean bl) {
        IOFileFilter iOFileFilter;
        if (stringArray == null) {
            iOFileFilter = TrueFileFilter.INSTANCE;
        } else {
            String[] stringArray2 = FileUtils.toSuffixes(stringArray);
            iOFileFilter = new SuffixFileFilter(stringArray2);
        }
        return FileUtils.listFiles(file, iOFileFilter, bl ? TrueFileFilter.INSTANCE : FalseFileFilter.INSTANCE);
    }

    public static Iterator<File> iterateFiles(File file, String[] stringArray, boolean bl) {
        return FileUtils.listFiles(file, stringArray, bl).iterator();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean contentEquals(File file, File file2) throws IOException {
        boolean bl;
        boolean bl2 = file.exists();
        if (bl2 != file2.exists()) {
            return true;
        }
        if (!bl2) {
            return false;
        }
        if (file.isDirectory() || file2.isDirectory()) {
            throw new IOException("Can't compare directories, only files");
        }
        if (file.length() != file2.length()) {
            return true;
        }
        if (file.getCanonicalFile().equals(file2.getCanonicalFile())) {
            return false;
        }
        FileInputStream fileInputStream = null;
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream2 = new FileInputStream(file2);
            bl = IOUtils.contentEquals(fileInputStream, fileInputStream2);
        } catch (Throwable throwable) {
            IOUtils.closeQuietly(fileInputStream);
            IOUtils.closeQuietly(fileInputStream2);
            throw throwable;
        }
        IOUtils.closeQuietly(fileInputStream);
        IOUtils.closeQuietly(fileInputStream2);
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean contentEqualsIgnoreEOL(File file, File file2, String string) throws IOException {
        boolean bl;
        boolean bl2 = file.exists();
        if (bl2 != file2.exists()) {
            return true;
        }
        if (!bl2) {
            return false;
        }
        if (file.isDirectory() || file2.isDirectory()) {
            throw new IOException("Can't compare directories, only files");
        }
        if (file.getCanonicalFile().equals(file2.getCanonicalFile())) {
            return false;
        }
        InputStreamReader inputStreamReader = null;
        InputStreamReader inputStreamReader2 = null;
        try {
            if (string == null) {
                inputStreamReader = new InputStreamReader((InputStream)new FileInputStream(file), Charset.defaultCharset());
                inputStreamReader2 = new InputStreamReader((InputStream)new FileInputStream(file2), Charset.defaultCharset());
            } else {
                inputStreamReader = new InputStreamReader((InputStream)new FileInputStream(file), string);
                inputStreamReader2 = new InputStreamReader((InputStream)new FileInputStream(file2), string);
            }
            bl = IOUtils.contentEqualsIgnoreEOL(inputStreamReader, inputStreamReader2);
        } catch (Throwable throwable) {
            IOUtils.closeQuietly(inputStreamReader);
            IOUtils.closeQuietly(inputStreamReader2);
            throw throwable;
        }
        IOUtils.closeQuietly(inputStreamReader);
        IOUtils.closeQuietly(inputStreamReader2);
        return bl;
    }

    public static File toFile(URL uRL) {
        if (uRL == null || !"file".equalsIgnoreCase(uRL.getProtocol())) {
            return null;
        }
        String string = uRL.getFile().replace('/', File.separatorChar);
        string = FileUtils.decodeUrl(string);
        return new File(string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static String decodeUrl(String string) {
        String string2 = string;
        if (string != null && string.indexOf(37) >= 0) {
            int n = string.length();
            StringBuilder stringBuilder = new StringBuilder();
            ByteBuffer byteBuffer = ByteBuffer.allocate(n);
            int n2 = 0;
            while (n2 < n) {
                if (string.charAt(n2) == '%') {
                    try {
                        do {
                            byte by = (byte)Integer.parseInt(string.substring(n2 + 1, n2 + 3), 16);
                            byteBuffer.put(by);
                        } while ((n2 += 3) < n && string.charAt(n2) == '%');
                        continue;
                    } catch (RuntimeException runtimeException) {
                    } finally {
                        if (byteBuffer.position() <= 0) continue;
                        byteBuffer.flip();
                        stringBuilder.append(Charsets.UTF_8.decode(byteBuffer).toString());
                        byteBuffer.clear();
                        continue;
                    }
                }
                stringBuilder.append(string.charAt(n2++));
            }
            string2 = stringBuilder.toString();
        }
        return string2;
    }

    public static File[] toFiles(URL[] uRLArray) {
        if (uRLArray == null || uRLArray.length == 0) {
            return EMPTY_FILE_ARRAY;
        }
        File[] fileArray = new File[uRLArray.length];
        for (int i = 0; i < uRLArray.length; ++i) {
            URL uRL = uRLArray[i];
            if (uRL == null) continue;
            if (!uRL.getProtocol().equals("file")) {
                throw new IllegalArgumentException("URL could not be converted to a File: " + uRL);
            }
            fileArray[i] = FileUtils.toFile(uRL);
        }
        return fileArray;
    }

    public static URL[] toURLs(File[] fileArray) throws IOException {
        URL[] uRLArray = new URL[fileArray.length];
        for (int i = 0; i < uRLArray.length; ++i) {
            uRLArray[i] = fileArray[i].toURI().toURL();
        }
        return uRLArray;
    }

    public static void copyFileToDirectory(File file, File file2) throws IOException {
        FileUtils.copyFileToDirectory(file, file2, true);
    }

    public static void copyFileToDirectory(File file, File file2, boolean bl) throws IOException {
        if (file2 == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (file2.exists() && !file2.isDirectory()) {
            throw new IllegalArgumentException("Destination '" + file2 + "' is not a directory");
        }
        File file3 = new File(file2, file.getName());
        FileUtils.copyFile(file, file3, bl);
    }

    public static void copyFile(File file, File file2) throws IOException {
        FileUtils.copyFile(file, file2, true);
    }

    public static void copyFile(File file, File file2, boolean bl) throws IOException {
        FileUtils.checkFileRequirements(file, file2);
        if (file.isDirectory()) {
            throw new IOException("Source '" + file + "' exists but is a directory");
        }
        if (file.getCanonicalPath().equals(file2.getCanonicalPath())) {
            throw new IOException("Source '" + file + "' and destination '" + file2 + "' are the same");
        }
        File file3 = file2.getParentFile();
        if (file3 != null && !file3.mkdirs() && !file3.isDirectory()) {
            throw new IOException("Destination '" + file3 + "' directory cannot be created");
        }
        if (file2.exists() && !file2.canWrite()) {
            throw new IOException("Destination '" + file2 + "' exists but is read-only");
        }
        FileUtils.doCopyFile(file, file2, bl);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static long copyFile(File file, OutputStream outputStream) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            long l = IOUtils.copyLarge(fileInputStream, outputStream);
            return l;
        } finally {
            fileInputStream.close();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void doCopyFile(File file, File file2, boolean bl) throws IOException {
        long l;
        long l2;
        if (file2.exists() && file2.isDirectory()) {
            throw new IOException("Destination '" + file2 + "' exists but is a directory");
        }
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileChannel fileChannel = null;
        FileChannel fileChannel2 = null;
        try {
            long l3;
            long l4;
            fileInputStream = new FileInputStream(file);
            fileOutputStream = new FileOutputStream(file2);
            fileChannel = fileInputStream.getChannel();
            fileChannel2 = fileOutputStream.getChannel();
            l2 = fileChannel.size();
            long l5 = 0L;
            for (l = 0L; l < l2 && (l4 = fileChannel2.transferFrom(fileChannel, l, l5 = (l3 = l2 - l) > 0x1E00000L ? 0x1E00000L : l3)) != 0L; l += l4) {
            }
        } catch (Throwable throwable) {
            IOUtils.closeQuietly(fileChannel2, fileOutputStream, fileChannel, fileInputStream);
            throw throwable;
        }
        IOUtils.closeQuietly(fileChannel2, fileOutputStream, fileChannel, fileInputStream);
        l2 = file.length();
        l = file2.length();
        if (l2 != l) {
            throw new IOException("Failed to copy full contents from '" + file + "' to '" + file2 + "' Expected length: " + l2 + " Actual: " + l);
        }
        if (bl) {
            file2.setLastModified(file.lastModified());
        }
    }

    public static void copyDirectoryToDirectory(File file, File file2) throws IOException {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file.exists() && !file.isDirectory()) {
            throw new IllegalArgumentException("Source '" + file2 + "' is not a directory");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (file2.exists() && !file2.isDirectory()) {
            throw new IllegalArgumentException("Destination '" + file2 + "' is not a directory");
        }
        FileUtils.copyDirectory(file, new File(file2, file.getName()), true);
    }

    public static void copyDirectory(File file, File file2) throws IOException {
        FileUtils.copyDirectory(file, file2, true);
    }

    public static void copyDirectory(File file, File file2, boolean bl) throws IOException {
        FileUtils.copyDirectory(file, file2, null, bl);
    }

    public static void copyDirectory(File file, File file2, FileFilter fileFilter) throws IOException {
        FileUtils.copyDirectory(file, file2, fileFilter, true);
    }

    public static void copyDirectory(File file, File file2, FileFilter fileFilter, boolean bl) throws IOException {
        FileUtils.checkFileRequirements(file, file2);
        if (!file.isDirectory()) {
            throw new IOException("Source '" + file + "' exists but is not a directory");
        }
        if (file.getCanonicalPath().equals(file2.getCanonicalPath())) {
            throw new IOException("Source '" + file + "' and destination '" + file2 + "' are the same");
        }
        ArrayList<String> arrayList = null;
        if (file2.getCanonicalPath().startsWith(file.getCanonicalPath())) {
            File[] fileArray;
            File[] fileArray2 = fileArray = fileFilter == null ? file.listFiles() : file.listFiles(fileFilter);
            if (fileArray != null && fileArray.length > 0) {
                arrayList = new ArrayList<String>(fileArray.length);
                for (File file3 : fileArray) {
                    File file4 = new File(file2, file3.getName());
                    arrayList.add(file4.getCanonicalPath());
                }
            }
        }
        FileUtils.doCopyDirectory(file, file2, fileFilter, bl, arrayList);
    }

    private static void checkFileRequirements(File file, File file2) throws FileNotFoundException {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!file.exists()) {
            throw new FileNotFoundException("Source '" + file + "' does not exist");
        }
    }

    private static void doCopyDirectory(File file, File file2, FileFilter fileFilter, boolean bl, List<String> list) throws IOException {
        File[] fileArray;
        File[] fileArray2 = fileArray = fileFilter == null ? file.listFiles() : file.listFiles(fileFilter);
        if (fileArray == null) {
            throw new IOException("Failed to list contents of " + file);
        }
        if (file2.exists()) {
            if (!file2.isDirectory()) {
                throw new IOException("Destination '" + file2 + "' exists but is not a directory");
            }
        } else if (!file2.mkdirs() && !file2.isDirectory()) {
            throw new IOException("Destination '" + file2 + "' directory cannot be created");
        }
        if (!file2.canWrite()) {
            throw new IOException("Destination '" + file2 + "' cannot be written to");
        }
        for (File file3 : fileArray) {
            File file4 = new File(file2, file3.getName());
            if (list != null && list.contains(file3.getCanonicalPath())) continue;
            if (file3.isDirectory()) {
                FileUtils.doCopyDirectory(file3, file4, fileFilter, bl, list);
                continue;
            }
            FileUtils.doCopyFile(file3, file4, bl);
        }
        if (bl) {
            file2.setLastModified(file.lastModified());
        }
    }

    public static void copyURLToFile(URL uRL, File file) throws IOException {
        FileUtils.copyInputStreamToFile(uRL.openStream(), file);
    }

    public static void copyURLToFile(URL uRL, File file, int n, int n2) throws IOException {
        URLConnection uRLConnection = uRL.openConnection();
        uRLConnection.setConnectTimeout(n);
        uRLConnection.setReadTimeout(n2);
        FileUtils.copyInputStreamToFile(uRLConnection.getInputStream(), file);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
        try {
            FileUtils.copyToFile(inputStream, file);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void copyToFile(InputStream inputStream, File file) throws IOException {
        FileOutputStream fileOutputStream = FileUtils.openOutputStream(file);
        try {
            IOUtils.copy(inputStream, (OutputStream)fileOutputStream);
            fileOutputStream.close();
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
        }
    }

    public static void deleteDirectory(File file) throws IOException {
        if (!file.exists()) {
            return;
        }
        if (!FileUtils.isSymlink(file)) {
            FileUtils.cleanDirectory(file);
        }
        if (!file.delete()) {
            String string = "Unable to delete directory " + file + ".";
            throw new IOException(string);
        }
    }

    public static boolean deleteQuietly(File file) {
        if (file == null) {
            return true;
        }
        try {
            if (file.isDirectory()) {
                FileUtils.cleanDirectory(file);
            }
        } catch (Exception exception) {
            // empty catch block
        }
        try {
            return file.delete();
        } catch (Exception exception) {
            return true;
        }
    }

    public static boolean directoryContains(File file, File file2) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("Directory must not be null");
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("Not a directory: " + file);
        }
        if (file2 == null) {
            return true;
        }
        if (!file.exists() || !file2.exists()) {
            return true;
        }
        String string = file.getCanonicalPath();
        String string2 = file2.getCanonicalPath();
        return FilenameUtils.directoryContains(string, string2);
    }

    public static void cleanDirectory(File file) throws IOException {
        File[] fileArray = FileUtils.verifiedListFiles(file);
        IOException iOException = null;
        for (File file2 : fileArray) {
            try {
                FileUtils.forceDelete(file2);
            } catch (IOException iOException2) {
                iOException = iOException2;
            }
        }
        if (null != iOException) {
            throw iOException;
        }
    }

    private static File[] verifiedListFiles(File file) throws IOException {
        if (!file.exists()) {
            String string = file + " does not exist";
            throw new IllegalArgumentException(string);
        }
        if (!file.isDirectory()) {
            String string = file + " is not a directory";
            throw new IllegalArgumentException(string);
        }
        File[] fileArray = file.listFiles();
        if (fileArray == null) {
            throw new IOException("Failed to list contents of " + file);
        }
        return fileArray;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean waitFor(File file, int n) {
        long l = System.currentTimeMillis() + (long)n * 1000L;
        boolean bl = false;
        try {
            while (!file.exists()) {
                long l2 = l - System.currentTimeMillis();
                if (l2 < 0L) {
                    boolean bl2 = false;
                    return bl2;
                }
                try {
                    Thread.sleep(Math.min(100L, l2));
                } catch (InterruptedException interruptedException) {
                    bl = true;
                } catch (Exception exception) {
                    break;
                }
            }
        } finally {
            if (bl) {
                Thread.currentThread().interrupt();
            }
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String readFileToString(File file, Charset charset) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = FileUtils.openInputStream(file);
            String string = IOUtils.toString((InputStream)fileInputStream, Charsets.toCharset(charset));
            return string;
        } finally {
            IOUtils.closeQuietly(fileInputStream);
        }
    }

    public static String readFileToString(File file, String string) throws IOException {
        return FileUtils.readFileToString(file, Charsets.toCharset(string));
    }

    @Deprecated
    public static String readFileToString(File file) throws IOException {
        return FileUtils.readFileToString(file, Charset.defaultCharset());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] readFileToByteArray(File file) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = FileUtils.openInputStream(file);
            byte[] byArray = IOUtils.toByteArray(fileInputStream);
            return byArray;
        } finally {
            IOUtils.closeQuietly(fileInputStream);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static List<String> readLines(File file, Charset charset) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = FileUtils.openInputStream(file);
            List<String> list = IOUtils.readLines((InputStream)fileInputStream, Charsets.toCharset(charset));
            return list;
        } finally {
            IOUtils.closeQuietly(fileInputStream);
        }
    }

    public static List<String> readLines(File file, String string) throws IOException {
        return FileUtils.readLines(file, Charsets.toCharset(string));
    }

    @Deprecated
    public static List<String> readLines(File file) throws IOException {
        return FileUtils.readLines(file, Charset.defaultCharset());
    }

    public static LineIterator lineIterator(File file, String string) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = FileUtils.openInputStream(file);
            return IOUtils.lineIterator((InputStream)fileInputStream, string);
        } catch (IOException iOException) {
            IOUtils.closeQuietly(fileInputStream);
            throw iOException;
        } catch (RuntimeException runtimeException) {
            IOUtils.closeQuietly(fileInputStream);
            throw runtimeException;
        }
    }

    public static LineIterator lineIterator(File file) throws IOException {
        return FileUtils.lineIterator(file, null);
    }

    public static void writeStringToFile(File file, String string, Charset charset) throws IOException {
        FileUtils.writeStringToFile(file, string, charset, false);
    }

    public static void writeStringToFile(File file, String string, String string2) throws IOException {
        FileUtils.writeStringToFile(file, string, string2, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void writeStringToFile(File file, String string, Charset charset, boolean bl) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = FileUtils.openOutputStream(file, bl);
            IOUtils.write(string, (OutputStream)fileOutputStream, charset);
            ((OutputStream)fileOutputStream).close();
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
        }
    }

    public static void writeStringToFile(File file, String string, String string2, boolean bl) throws IOException {
        FileUtils.writeStringToFile(file, string, Charsets.toCharset(string2), bl);
    }

    @Deprecated
    public static void writeStringToFile(File file, String string) throws IOException {
        FileUtils.writeStringToFile(file, string, Charset.defaultCharset(), false);
    }

    @Deprecated
    public static void writeStringToFile(File file, String string, boolean bl) throws IOException {
        FileUtils.writeStringToFile(file, string, Charset.defaultCharset(), bl);
    }

    @Deprecated
    public static void write(File file, CharSequence charSequence) throws IOException {
        FileUtils.write(file, charSequence, Charset.defaultCharset(), false);
    }

    @Deprecated
    public static void write(File file, CharSequence charSequence, boolean bl) throws IOException {
        FileUtils.write(file, charSequence, Charset.defaultCharset(), bl);
    }

    public static void write(File file, CharSequence charSequence, Charset charset) throws IOException {
        FileUtils.write(file, charSequence, charset, false);
    }

    public static void write(File file, CharSequence charSequence, String string) throws IOException {
        FileUtils.write(file, charSequence, string, false);
    }

    public static void write(File file, CharSequence charSequence, Charset charset, boolean bl) throws IOException {
        String string = charSequence == null ? null : charSequence.toString();
        FileUtils.writeStringToFile(file, string, charset, bl);
    }

    public static void write(File file, CharSequence charSequence, String string, boolean bl) throws IOException {
        FileUtils.write(file, charSequence, Charsets.toCharset(string), bl);
    }

    public static void writeByteArrayToFile(File file, byte[] byArray) throws IOException {
        FileUtils.writeByteArrayToFile(file, byArray, false);
    }

    public static void writeByteArrayToFile(File file, byte[] byArray, boolean bl) throws IOException {
        FileUtils.writeByteArrayToFile(file, byArray, 0, byArray.length, bl);
    }

    public static void writeByteArrayToFile(File file, byte[] byArray, int n, int n2) throws IOException {
        FileUtils.writeByteArrayToFile(file, byArray, n, n2, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void writeByteArrayToFile(File file, byte[] byArray, int n, int n2, boolean bl) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = FileUtils.openOutputStream(file, bl);
            ((OutputStream)fileOutputStream).write(byArray, n, n2);
            ((OutputStream)fileOutputStream).close();
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
        }
    }

    public static void writeLines(File file, String string, Collection<?> collection) throws IOException {
        FileUtils.writeLines(file, string, collection, null, false);
    }

    public static void writeLines(File file, String string, Collection<?> collection, boolean bl) throws IOException {
        FileUtils.writeLines(file, string, collection, null, bl);
    }

    public static void writeLines(File file, Collection<?> collection) throws IOException {
        FileUtils.writeLines(file, null, collection, null, false);
    }

    public static void writeLines(File file, Collection<?> collection, boolean bl) throws IOException {
        FileUtils.writeLines(file, null, collection, null, bl);
    }

    public static void writeLines(File file, String string, Collection<?> collection, String string2) throws IOException {
        FileUtils.writeLines(file, string, collection, string2, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void writeLines(File file, String string, Collection<?> collection, String string2, boolean bl) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = FileUtils.openOutputStream(file, bl);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            IOUtils.writeLines(collection, string2, (OutputStream)bufferedOutputStream, string);
            bufferedOutputStream.flush();
            fileOutputStream.close();
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
        }
    }

    public static void writeLines(File file, Collection<?> collection, String string) throws IOException {
        FileUtils.writeLines(file, null, collection, string, false);
    }

    public static void writeLines(File file, Collection<?> collection, String string, boolean bl) throws IOException {
        FileUtils.writeLines(file, null, collection, string, bl);
    }

    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            FileUtils.deleteDirectory(file);
        } else {
            boolean bl = file.exists();
            if (!file.delete()) {
                if (!bl) {
                    throw new FileNotFoundException("File does not exist: " + file);
                }
                String string = "Unable to delete file: " + file;
                throw new IOException(string);
            }
        }
    }

    public static void forceDeleteOnExit(File file) throws IOException {
        if (file.isDirectory()) {
            FileUtils.deleteDirectoryOnExit(file);
        } else {
            file.deleteOnExit();
        }
    }

    private static void deleteDirectoryOnExit(File file) throws IOException {
        if (!file.exists()) {
            return;
        }
        file.deleteOnExit();
        if (!FileUtils.isSymlink(file)) {
            FileUtils.cleanDirectoryOnExit(file);
        }
    }

    private static void cleanDirectoryOnExit(File file) throws IOException {
        File[] fileArray = FileUtils.verifiedListFiles(file);
        IOException iOException = null;
        for (File file2 : fileArray) {
            try {
                FileUtils.forceDeleteOnExit(file2);
            } catch (IOException iOException2) {
                iOException = iOException2;
            }
        }
        if (null != iOException) {
            throw iOException;
        }
    }

    public static void forceMkdir(File file) throws IOException {
        if (file.exists()) {
            if (!file.isDirectory()) {
                String string = "File " + file + " exists and is " + "not a directory. Unable to create directory.";
                throw new IOException(string);
            }
        } else if (!file.mkdirs() && !file.isDirectory()) {
            String string = "Unable to create directory " + file;
            throw new IOException(string);
        }
    }

    public static void forceMkdirParent(File file) throws IOException {
        File file2 = file.getParentFile();
        if (file2 == null) {
            return;
        }
        FileUtils.forceMkdir(file2);
    }

    public static long sizeOf(File file) {
        if (!file.exists()) {
            String string = file + " does not exist";
            throw new IllegalArgumentException(string);
        }
        if (file.isDirectory()) {
            return FileUtils.sizeOfDirectory0(file);
        }
        return file.length();
    }

    public static BigInteger sizeOfAsBigInteger(File file) {
        if (!file.exists()) {
            String string = file + " does not exist";
            throw new IllegalArgumentException(string);
        }
        if (file.isDirectory()) {
            return FileUtils.sizeOfDirectoryBig0(file);
        }
        return BigInteger.valueOf(file.length());
    }

    public static long sizeOfDirectory(File file) {
        FileUtils.checkDirectory(file);
        return FileUtils.sizeOfDirectory0(file);
    }

    private static long sizeOfDirectory0(File file) {
        File[] fileArray = file.listFiles();
        if (fileArray == null) {
            return 0L;
        }
        long l = 0L;
        for (File file2 : fileArray) {
            try {
                if (FileUtils.isSymlink(file2) || (l += FileUtils.sizeOf0(file2)) >= 0L) continue;
                break;
            } catch (IOException iOException) {
                // empty catch block
            }
        }
        return l;
    }

    private static long sizeOf0(File file) {
        if (file.isDirectory()) {
            return FileUtils.sizeOfDirectory0(file);
        }
        return file.length();
    }

    public static BigInteger sizeOfDirectoryAsBigInteger(File file) {
        FileUtils.checkDirectory(file);
        return FileUtils.sizeOfDirectoryBig0(file);
    }

    private static BigInteger sizeOfDirectoryBig0(File file) {
        File[] fileArray = file.listFiles();
        if (fileArray == null) {
            return BigInteger.ZERO;
        }
        BigInteger bigInteger = BigInteger.ZERO;
        for (File file2 : fileArray) {
            try {
                if (FileUtils.isSymlink(file2)) continue;
                bigInteger = bigInteger.add(FileUtils.sizeOfBig0(file2));
            } catch (IOException iOException) {
                // empty catch block
            }
        }
        return bigInteger;
    }

    private static BigInteger sizeOfBig0(File file) {
        if (file.isDirectory()) {
            return FileUtils.sizeOfDirectoryBig0(file);
        }
        return BigInteger.valueOf(file.length());
    }

    private static void checkDirectory(File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException(file + " does not exist");
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(file + " is not a directory");
        }
    }

    public static boolean isFileNewer(File file, File file2) {
        if (file2 == null) {
            throw new IllegalArgumentException("No specified reference file");
        }
        if (!file2.exists()) {
            throw new IllegalArgumentException("The reference file '" + file2 + "' doesn't exist");
        }
        return FileUtils.isFileNewer(file, file2.lastModified());
    }

    public static boolean isFileNewer(File file, Date date) {
        if (date == null) {
            throw new IllegalArgumentException("No specified date");
        }
        return FileUtils.isFileNewer(file, date.getTime());
    }

    public static boolean isFileNewer(File file, long l) {
        if (file == null) {
            throw new IllegalArgumentException("No specified file");
        }
        if (!file.exists()) {
            return true;
        }
        return file.lastModified() > l;
    }

    public static boolean isFileOlder(File file, File file2) {
        if (file2 == null) {
            throw new IllegalArgumentException("No specified reference file");
        }
        if (!file2.exists()) {
            throw new IllegalArgumentException("The reference file '" + file2 + "' doesn't exist");
        }
        return FileUtils.isFileOlder(file, file2.lastModified());
    }

    public static boolean isFileOlder(File file, Date date) {
        if (date == null) {
            throw new IllegalArgumentException("No specified date");
        }
        return FileUtils.isFileOlder(file, date.getTime());
    }

    public static boolean isFileOlder(File file, long l) {
        if (file == null) {
            throw new IllegalArgumentException("No specified file");
        }
        if (!file.exists()) {
            return true;
        }
        return file.lastModified() < l;
    }

    public static long checksumCRC32(File file) throws IOException {
        CRC32 cRC32 = new CRC32();
        FileUtils.checksum(file, cRC32);
        return cRC32.getValue();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Checksum checksum(File file, Checksum checksum) throws IOException {
        if (file.isDirectory()) {
            throw new IllegalArgumentException("Checksums can't be computed on directories");
        }
        CheckedInputStream checkedInputStream = null;
        try {
            checkedInputStream = new CheckedInputStream(new FileInputStream(file), checksum);
            IOUtils.copy((InputStream)checkedInputStream, (OutputStream)new NullOutputStream());
        } catch (Throwable throwable) {
            IOUtils.closeQuietly(checkedInputStream);
            throw throwable;
        }
        IOUtils.closeQuietly(checkedInputStream);
        return checksum;
    }

    public static void moveDirectory(File file, File file2) throws IOException {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!file.exists()) {
            throw new FileNotFoundException("Source '" + file + "' does not exist");
        }
        if (!file.isDirectory()) {
            throw new IOException("Source '" + file + "' is not a directory");
        }
        if (file2.exists()) {
            throw new FileExistsException("Destination '" + file2 + "' already exists");
        }
        boolean bl = file.renameTo(file2);
        if (!bl) {
            if (file2.getCanonicalPath().startsWith(file.getCanonicalPath() + File.separator)) {
                throw new IOException("Cannot move directory: " + file + " to a subdirectory of itself: " + file2);
            }
            FileUtils.copyDirectory(file, file2);
            FileUtils.deleteDirectory(file);
            if (file.exists()) {
                throw new IOException("Failed to delete original directory '" + file + "' after copy to '" + file2 + "'");
            }
        }
    }

    public static void moveDirectoryToDirectory(File file, File file2, boolean bl) throws IOException {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination directory must not be null");
        }
        if (!file2.exists() && bl) {
            file2.mkdirs();
        }
        if (!file2.exists()) {
            throw new FileNotFoundException("Destination directory '" + file2 + "' does not exist [createDestDir=" + bl + "]");
        }
        if (!file2.isDirectory()) {
            throw new IOException("Destination '" + file2 + "' is not a directory");
        }
        FileUtils.moveDirectory(file, new File(file2, file.getName()));
    }

    public static void moveFile(File file, File file2) throws IOException {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!file.exists()) {
            throw new FileNotFoundException("Source '" + file + "' does not exist");
        }
        if (file.isDirectory()) {
            throw new IOException("Source '" + file + "' is a directory");
        }
        if (file2.exists()) {
            throw new FileExistsException("Destination '" + file2 + "' already exists");
        }
        if (file2.isDirectory()) {
            throw new IOException("Destination '" + file2 + "' is a directory");
        }
        boolean bl = file.renameTo(file2);
        if (!bl) {
            FileUtils.copyFile(file, file2);
            if (!file.delete()) {
                FileUtils.deleteQuietly(file2);
                throw new IOException("Failed to delete original file '" + file + "' after copy to '" + file2 + "'");
            }
        }
    }

    public static void moveFileToDirectory(File file, File file2, boolean bl) throws IOException {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination directory must not be null");
        }
        if (!file2.exists() && bl) {
            file2.mkdirs();
        }
        if (!file2.exists()) {
            throw new FileNotFoundException("Destination directory '" + file2 + "' does not exist [createDestDir=" + bl + "]");
        }
        if (!file2.isDirectory()) {
            throw new IOException("Destination '" + file2 + "' is not a directory");
        }
        FileUtils.moveFile(file, new File(file2, file.getName()));
    }

    public static void moveToDirectory(File file, File file2, boolean bl) throws IOException {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!file.exists()) {
            throw new FileNotFoundException("Source '" + file + "' does not exist");
        }
        if (file.isDirectory()) {
            FileUtils.moveDirectoryToDirectory(file, file2, bl);
        } else {
            FileUtils.moveFileToDirectory(file, file2, bl);
        }
    }

    public static boolean isSymlink(File file) throws IOException {
        if (Java7Support.isAtLeastJava7()) {
            return Java7Support.isSymLink(file);
        }
        if (file == null) {
            throw new NullPointerException("File must not be null");
        }
        if (FilenameUtils.isSystemWindows()) {
            return true;
        }
        File file2 = null;
        if (file.getParent() == null) {
            file2 = file;
        } else {
            File file3 = file.getParentFile().getCanonicalFile();
            file2 = new File(file3, file.getName());
        }
        if (file2.getCanonicalFile().equals(file2.getAbsoluteFile())) {
            return FileUtils.isBrokenSymlink(file);
        }
        return false;
    }

    private static boolean isBrokenSymlink(File file) throws IOException {
        if (file.exists()) {
            return true;
        }
        File file2 = file.getCanonicalFile();
        File file3 = file2.getParentFile();
        if (file3 == null || !file3.exists()) {
            return true;
        }
        File[] fileArray = file3.listFiles(new FileFilter(file2){
            final File val$canon;
            {
                this.val$canon = file;
            }

            @Override
            public boolean accept(File file) {
                return file.equals(this.val$canon);
            }
        });
        return fileArray != null && fileArray.length > 0;
    }
}

