package com.ibm.icu.impl;

import java.nio.channels.*;
import com.ibm.icu.util.*;
import java.nio.*;
import java.io.*;
import java.util.*;

public final class ICUBinary
{
    private static final List<DataFile> icuDataFiles;
    private static final byte MAGIC1 = -38;
    private static final byte MAGIC2 = 39;
    private static final byte CHAR_SET_ = 0;
    private static final byte CHAR_SIZE_ = 2;
    private static final String MAGIC_NUMBER_AUTHENTICATION_FAILED_ = "ICU data file error: Not an ICU data file";
    private static final String HEADER_AUTHENTICATION_FAILED_ = "ICU data file error: Header authentication failed, please check if you have a valid ICU data file";
    
    private static void addDataFilesFromPath(final String dataPath, final List<DataFile> files) {
        int sepIndex;
        for (int pathStart = 0; pathStart < dataPath.length(); pathStart = sepIndex + 1) {
            sepIndex = dataPath.indexOf(File.pathSeparatorChar, pathStart);
            int pathLimit;
            if (sepIndex >= 0) {
                pathLimit = sepIndex;
            }
            else {
                pathLimit = dataPath.length();
            }
            String path = dataPath.substring(pathStart, pathLimit).trim();
            if (path.endsWith(File.separator)) {
                path = path.substring(0, path.length() - 1);
            }
            if (path.length() != 0) {
                addDataFilesFromFolder(new File(path), new StringBuilder(), ICUBinary.icuDataFiles);
            }
            if (sepIndex < 0) {
                break;
            }
        }
    }
    
    private static void addDataFilesFromFolder(final File folder, final StringBuilder itemPath, final List<DataFile> dataFiles) {
        final File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        int folderPathLength = itemPath.length();
        if (folderPathLength > 0) {
            itemPath.append('/');
            ++folderPathLength;
        }
        for (final File file : files) {
            final String fileName = file.getName();
            if (!fileName.endsWith(".txt")) {
                itemPath.append(fileName);
                if (file.isDirectory()) {
                    addDataFilesFromFolder(file, itemPath, dataFiles);
                }
                else if (fileName.endsWith(".dat")) {
                    final ByteBuffer pkgBytes = mapFile(file);
                    if (pkgBytes != null && DatPackageReader.validate(pkgBytes)) {
                        dataFiles.add(new PackageDataFile(itemPath.toString(), pkgBytes));
                    }
                }
                else {
                    dataFiles.add(new SingleDataFile(itemPath.toString(), file));
                }
                itemPath.setLength(folderPathLength);
            }
        }
    }
    
    static int compareKeys(final CharSequence key, final ByteBuffer bytes, int offset) {
        int i = 0;
        while (true) {
            final int c2 = bytes.get(offset);
            if (c2 == 0) {
                if (i == key.length()) {
                    return 0;
                }
                return 1;
            }
            else {
                if (i == key.length()) {
                    return -1;
                }
                final int diff = key.charAt(i) - c2;
                if (diff != 0) {
                    return diff;
                }
                ++i;
                ++offset;
            }
        }
    }
    
    static int compareKeys(final CharSequence key, final byte[] bytes, int offset) {
        int i = 0;
        while (true) {
            final int c2 = bytes[offset];
            if (c2 == 0) {
                if (i == key.length()) {
                    return 0;
                }
                return 1;
            }
            else {
                if (i == key.length()) {
                    return -1;
                }
                final int diff = key.charAt(i) - c2;
                if (diff != 0) {
                    return diff;
                }
                ++i;
                ++offset;
            }
        }
    }
    
    public static ByteBuffer getData(final String itemPath) {
        return getData(null, null, itemPath, false);
    }
    
    public static ByteBuffer getData(final ClassLoader loader, final String resourceName, final String itemPath) {
        return getData(loader, resourceName, itemPath, false);
    }
    
    public static ByteBuffer getRequiredData(final String itemPath) {
        return getData(null, null, itemPath, true);
    }
    
    private static ByteBuffer getData(ClassLoader loader, String resourceName, final String itemPath, final boolean required) {
        final ByteBuffer bytes = getDataFromFile(itemPath);
        if (bytes != null) {
            return bytes;
        }
        if (loader == null) {
            loader = ClassLoaderUtil.getClassLoader(ICUData.class);
        }
        if (resourceName == null) {
            resourceName = "com/ibm/icu/impl/data/icudt62b/" + itemPath;
        }
        ByteBuffer buffer = null;
        try {
            final InputStream is = ICUData.getStream(loader, resourceName, required);
            if (is == null) {
                return null;
            }
            buffer = getByteBufferFromInputStreamAndCloseStream(is);
        }
        catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
        return buffer;
    }
    
    private static ByteBuffer getDataFromFile(final String itemPath) {
        for (final DataFile dataFile : ICUBinary.icuDataFiles) {
            final ByteBuffer data = dataFile.getData(itemPath);
            if (data != null) {
                return data;
            }
        }
        return null;
    }
    
    private static ByteBuffer mapFile(final File path) {
        try {
            final FileInputStream file = new FileInputStream(path);
            final FileChannel channel = file.getChannel();
            ByteBuffer bytes = null;
            try {
                bytes = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size());
            }
            finally {
                file.close();
            }
            return bytes;
        }
        catch (FileNotFoundException ignored) {
            System.err.println(ignored);
        }
        catch (IOException ignored2) {
            System.err.println(ignored2);
        }
        return null;
    }
    
    public static void addBaseNamesInFileFolder(final String folder, final String suffix, final Set<String> names) {
        for (final DataFile dataFile : ICUBinary.icuDataFiles) {
            dataFile.addBaseNamesInFolder(folder, suffix, names);
        }
    }
    
    public static VersionInfo readHeaderAndDataVersion(final ByteBuffer bytes, final int dataFormat, final Authenticate authenticate) throws IOException {
        return getVersionInfoFromCompactInt(readHeader(bytes, dataFormat, authenticate));
    }
    
    public static int readHeader(final ByteBuffer bytes, final int dataFormat, final Authenticate authenticate) throws IOException {
        assert bytes != null && bytes.position() == 0;
        final byte magic1 = bytes.get(2);
        final byte magic2 = bytes.get(3);
        if (magic1 != -38 || magic2 != 39) {
            throw new IOException("ICU data file error: Not an ICU data file");
        }
        final byte isBigEndian = bytes.get(8);
        final byte charsetFamily = bytes.get(9);
        final byte sizeofUChar = bytes.get(10);
        if (isBigEndian < 0 || 1 < isBigEndian || charsetFamily != 0 || sizeofUChar != 2) {
            throw new IOException("ICU data file error: Header authentication failed, please check if you have a valid ICU data file");
        }
        bytes.order((isBigEndian != 0) ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        final int headerSize = bytes.getChar(0);
        final int sizeofUDataInfo = bytes.getChar(4);
        if (sizeofUDataInfo < 20 || headerSize < sizeofUDataInfo + 4) {
            throw new IOException("Internal Error: Header size error");
        }
        final byte[] formatVersion = { bytes.get(16), bytes.get(17), bytes.get(18), bytes.get(19) };
        if (bytes.get(12) != (byte)(dataFormat >> 24) || bytes.get(13) != (byte)(dataFormat >> 16) || bytes.get(14) != (byte)(dataFormat >> 8) || bytes.get(15) != (byte)dataFormat || (authenticate != null && !authenticate.isDataVersionAcceptable(formatVersion))) {
            throw new IOException("ICU data file error: Header authentication failed, please check if you have a valid ICU data file" + String.format("; data format %02x%02x%02x%02x, format version %d.%d.%d.%d", bytes.get(12), bytes.get(13), bytes.get(14), bytes.get(15), formatVersion[0] & 0xFF, formatVersion[1] & 0xFF, formatVersion[2] & 0xFF, formatVersion[3] & 0xFF));
        }
        bytes.position(headerSize);
        return bytes.get(20) << 24 | (bytes.get(21) & 0xFF) << 16 | (bytes.get(22) & 0xFF) << 8 | (bytes.get(23) & 0xFF);
    }
    
    public static int writeHeader(final int dataFormat, final int formatVersion, final int dataVersion, final DataOutputStream dos) throws IOException {
        dos.writeChar(32);
        dos.writeByte(-38);
        dos.writeByte(39);
        dos.writeChar(20);
        dos.writeChar(0);
        dos.writeByte(1);
        dos.writeByte(0);
        dos.writeByte(2);
        dos.writeByte(0);
        dos.writeInt(dataFormat);
        dos.writeInt(formatVersion);
        dos.writeInt(dataVersion);
        dos.writeLong(0L);
        assert dos.size() == 32;
        return 32;
    }
    
    public static void skipBytes(final ByteBuffer bytes, final int skipLength) {
        if (skipLength > 0) {
            bytes.position(bytes.position() + skipLength);
        }
    }
    
    public static String getString(final ByteBuffer bytes, final int length, final int additionalSkipLength) {
        final CharSequence cs = bytes.asCharBuffer();
        final String s = cs.subSequence(0, length).toString();
        skipBytes(bytes, length * 2 + additionalSkipLength);
        return s;
    }
    
    public static char[] getChars(final ByteBuffer bytes, final int length, final int additionalSkipLength) {
        final char[] dest = new char[length];
        bytes.asCharBuffer().get(dest);
        skipBytes(bytes, length * 2 + additionalSkipLength);
        return dest;
    }
    
    public static short[] getShorts(final ByteBuffer bytes, final int length, final int additionalSkipLength) {
        final short[] dest = new short[length];
        bytes.asShortBuffer().get(dest);
        skipBytes(bytes, length * 2 + additionalSkipLength);
        return dest;
    }
    
    public static int[] getInts(final ByteBuffer bytes, final int length, final int additionalSkipLength) {
        final int[] dest = new int[length];
        bytes.asIntBuffer().get(dest);
        skipBytes(bytes, length * 4 + additionalSkipLength);
        return dest;
    }
    
    public static long[] getLongs(final ByteBuffer bytes, final int length, final int additionalSkipLength) {
        final long[] dest = new long[length];
        bytes.asLongBuffer().get(dest);
        skipBytes(bytes, length * 8 + additionalSkipLength);
        return dest;
    }
    
    public static ByteBuffer sliceWithOrder(final ByteBuffer bytes) {
        final ByteBuffer b = bytes.slice();
        return b.order(bytes.order());
    }
    
    public static ByteBuffer getByteBufferFromInputStreamAndCloseStream(final InputStream is) throws IOException {
        try {
            final int avail = is.available();
            byte[] bytes;
            if (avail > 32) {
                bytes = new byte[avail];
            }
            else {
                bytes = new byte[128];
            }
            int length = 0;
            while (true) {
                if (length < bytes.length) {
                    final int numRead = is.read(bytes, length, bytes.length - length);
                    if (numRead < 0) {
                        break;
                    }
                    length += numRead;
                }
                else {
                    final int nextByte = is.read();
                    if (nextByte < 0) {
                        break;
                    }
                    int capacity = 2 * bytes.length;
                    if (capacity < 128) {
                        capacity = 128;
                    }
                    else if (capacity < 16384) {
                        capacity *= 2;
                    }
                    final byte[] newBytes = new byte[capacity];
                    System.arraycopy(bytes, 0, newBytes, 0, length);
                    bytes = newBytes;
                    bytes[length++] = (byte)nextByte;
                }
            }
            return ByteBuffer.wrap(bytes, 0, length);
        }
        finally {
            is.close();
        }
    }
    
    public static VersionInfo getVersionInfoFromCompactInt(final int version) {
        return VersionInfo.getInstance(version >>> 24, version >> 16 & 0xFF, version >> 8 & 0xFF, version & 0xFF);
    }
    
    public static byte[] getVersionByteArrayFromCompactInt(final int version) {
        return new byte[] { (byte)(version >> 24), (byte)(version >> 16), (byte)(version >> 8), (byte)version };
    }
    
    static {
        icuDataFiles = new ArrayList<DataFile>();
        final String dataPath = ICUConfig.get(ICUBinary.class.getName() + ".dataPath");
        if (dataPath != null) {
            addDataFilesFromPath(dataPath, ICUBinary.icuDataFiles);
        }
    }
    
    private static final class DatPackageReader
    {
        private static final int DATA_FORMAT = 1131245124;
        private static final IsAcceptable IS_ACCEPTABLE;
        
        static boolean validate(final ByteBuffer bytes) {
            try {
                ICUBinary.readHeader(bytes, 1131245124, DatPackageReader.IS_ACCEPTABLE);
            }
            catch (IOException ignored) {
                return false;
            }
            final int count = bytes.getInt(bytes.position());
            return count > 0 && bytes.position() + 4 + count * 24 <= bytes.capacity() && startsWithPackageName(bytes, getNameOffset(bytes, 0)) && startsWithPackageName(bytes, getNameOffset(bytes, count - 1));
        }
        
        private static boolean startsWithPackageName(final ByteBuffer bytes, final int start) {
            int length = "icudt62b".length() - 1;
            for (int i = 0; i < length; ++i) {
                if (bytes.get(start + i) != "icudt62b".charAt(i)) {
                    return false;
                }
            }
            final byte c = bytes.get(start + length++);
            return (c == 98 || c == 108) && bytes.get(start + length) == 47;
        }
        
        static ByteBuffer getData(final ByteBuffer bytes, final CharSequence key) {
            final int index = binarySearch(bytes, key);
            if (index >= 0) {
                final ByteBuffer data = bytes.duplicate();
                data.position(getDataOffset(bytes, index));
                data.limit(getDataOffset(bytes, index + 1));
                return ICUBinary.sliceWithOrder(data);
            }
            return null;
        }
        
        static void addBaseNamesInFolder(final ByteBuffer bytes, final String folder, final String suffix, final Set<String> names) {
            int index = binarySearch(bytes, folder);
            if (index < 0) {
                index ^= -1;
            }
            final int base = bytes.position();
            final int count = bytes.getInt(base);
            for (StringBuilder sb = new StringBuilder(); index < count && addBaseName(bytes, index, folder, suffix, sb, names); ++index) {}
        }
        
        private static int binarySearch(final ByteBuffer bytes, final CharSequence key) {
            final int base = bytes.position();
            final int count = bytes.getInt(base);
            int start = 0;
            int limit = count;
            while (start < limit) {
                final int mid = start + limit >>> 1;
                int nameOffset = getNameOffset(bytes, mid);
                nameOffset += "icudt62b".length() + 1;
                final int result = ICUBinary.compareKeys(key, bytes, nameOffset);
                if (result < 0) {
                    limit = mid;
                }
                else {
                    if (result <= 0) {
                        return mid;
                    }
                    start = mid + 1;
                }
            }
            return ~start;
        }
        
        private static int getNameOffset(final ByteBuffer bytes, final int index) {
            final int base = bytes.position();
            assert 0 <= index && index < bytes.getInt(base);
            return base + bytes.getInt(base + 4 + index * 8);
        }
        
        private static int getDataOffset(final ByteBuffer bytes, final int index) {
            final int base = bytes.position();
            final int count = bytes.getInt(base);
            if (index == count) {
                return bytes.capacity();
            }
            assert 0 <= index && index < count;
            return base + bytes.getInt(base + 4 + 4 + index * 8);
        }
        
        static boolean addBaseName(final ByteBuffer bytes, final int index, final String folder, final String suffix, final StringBuilder sb, final Set<String> names) {
            int offset = getNameOffset(bytes, index);
            offset += "icudt62b".length() + 1;
            if (folder.length() != 0) {
                for (int i = 0; i < folder.length(); ++i, ++offset) {
                    if (bytes.get(offset) != folder.charAt(i)) {
                        return false;
                    }
                }
                if (bytes.get(offset++) != 47) {
                    return false;
                }
            }
            sb.setLength(0);
            byte b;
            while ((b = bytes.get(offset++)) != 0) {
                final char c = (char)b;
                if (c == '/') {
                    return true;
                }
                sb.append(c);
            }
            final int nameLimit = sb.length() - suffix.length();
            if (sb.lastIndexOf(suffix, nameLimit) >= 0) {
                names.add(sb.substring(0, nameLimit));
            }
            return true;
        }
        
        static {
            IS_ACCEPTABLE = new IsAcceptable();
        }
        
        private static final class IsAcceptable implements Authenticate
        {
            @Override
            public boolean isDataVersionAcceptable(final byte[] version) {
                return version[0] == 1;
            }
        }
    }
    
    private abstract static class DataFile
    {
        protected final String itemPath;
        
        DataFile(final String item) {
            this.itemPath = item;
        }
        
        @Override
        public String toString() {
            return this.itemPath;
        }
        
        abstract ByteBuffer getData(final String p0);
        
        abstract void addBaseNamesInFolder(final String p0, final String p1, final Set<String> p2);
    }
    
    private static final class SingleDataFile extends DataFile
    {
        private final File path;
        
        SingleDataFile(final String item, final File path) {
            super(item);
            this.path = path;
        }
        
        @Override
        public String toString() {
            return this.path.toString();
        }
        
        @Override
        ByteBuffer getData(final String requestedPath) {
            if (requestedPath.equals(this.itemPath)) {
                return mapFile(this.path);
            }
            return null;
        }
        
        @Override
        void addBaseNamesInFolder(final String folder, final String suffix, final Set<String> names) {
            if (this.itemPath.length() > folder.length() + suffix.length() && this.itemPath.startsWith(folder) && this.itemPath.endsWith(suffix) && this.itemPath.charAt(folder.length()) == '/' && this.itemPath.indexOf(47, folder.length() + 1) < 0) {
                names.add(this.itemPath.substring(folder.length() + 1, this.itemPath.length() - suffix.length()));
            }
        }
    }
    
    private static final class PackageDataFile extends DataFile
    {
        private final ByteBuffer pkgBytes;
        
        PackageDataFile(final String item, final ByteBuffer bytes) {
            super(item);
            this.pkgBytes = bytes;
        }
        
        @Override
        ByteBuffer getData(final String requestedPath) {
            return DatPackageReader.getData(this.pkgBytes, requestedPath);
        }
        
        @Override
        void addBaseNamesInFolder(final String folder, final String suffix, final Set<String> names) {
            DatPackageReader.addBaseNamesInFolder(this.pkgBytes, folder, suffix, names);
        }
    }
    
    public interface Authenticate
    {
        boolean isDataVersionAcceptable(final byte[] p0);
    }
}
