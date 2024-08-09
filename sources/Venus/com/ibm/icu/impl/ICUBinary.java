/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ClassLoaderUtil;
import com.ibm.icu.impl.ICUConfig;
import com.ibm.icu.impl.ICUData;
import com.ibm.icu.util.ICUUncheckedIOException;
import com.ibm.icu.util.VersionInfo;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public final class ICUBinary {
    private static final List<DataFile> icuDataFiles;
    private static final byte MAGIC1 = -38;
    private static final byte MAGIC2 = 39;
    private static final byte CHAR_SET_ = 0;
    private static final byte CHAR_SIZE_ = 2;
    private static final String MAGIC_NUMBER_AUTHENTICATION_FAILED_ = "ICU data file error: Not an ICU data file";
    private static final String HEADER_AUTHENTICATION_FAILED_ = "ICU data file error: Header authentication failed, please check if you have a valid ICU data file";
    static final boolean $assertionsDisabled;

    private static void addDataFilesFromPath(String string, List<DataFile> list) {
        int n = 0;
        while (n < string.length()) {
            int n2 = string.indexOf(File.pathSeparatorChar, n);
            int n3 = n2 >= 0 ? n2 : string.length();
            String string2 = string.substring(n, n3).trim();
            if (string2.endsWith(File.separator)) {
                string2 = string2.substring(0, string2.length() - 1);
            }
            if (string2.length() != 0) {
                ICUBinary.addDataFilesFromFolder(new File(string2), new StringBuilder(), icuDataFiles);
            }
            if (n2 < 0) break;
            n = n2 + 1;
        }
    }

    private static void addDataFilesFromFolder(File file, StringBuilder stringBuilder, List<DataFile> list) {
        File[] fileArray = file.listFiles();
        if (fileArray == null || fileArray.length == 0) {
            return;
        }
        int n = stringBuilder.length();
        if (n > 0) {
            stringBuilder.append('/');
            ++n;
        }
        for (File file2 : fileArray) {
            String string = file2.getName();
            if (string.endsWith(".txt")) continue;
            stringBuilder.append(string);
            if (file2.isDirectory()) {
                ICUBinary.addDataFilesFromFolder(file2, stringBuilder, list);
            } else if (string.endsWith(".dat")) {
                ByteBuffer byteBuffer = ICUBinary.mapFile(file2);
                if (byteBuffer != null && DatPackageReader.validate(byteBuffer)) {
                    list.add(new PackageDataFile(stringBuilder.toString(), byteBuffer));
                }
            } else {
                list.add(new SingleDataFile(stringBuilder.toString(), file2));
            }
            stringBuilder.setLength(n);
        }
    }

    static int compareKeys(CharSequence charSequence, ByteBuffer byteBuffer, int n) {
        int n2 = 0;
        while (true) {
            byte by;
            if ((by = byteBuffer.get(n)) == 0) {
                if (n2 == charSequence.length()) {
                    return 1;
                }
                return 0;
            }
            if (n2 == charSequence.length()) {
                return 1;
            }
            int n3 = charSequence.charAt(n2) - by;
            if (n3 != 0) {
                return n3;
            }
            ++n2;
            ++n;
        }
    }

    static int compareKeys(CharSequence charSequence, byte[] byArray, int n) {
        int n2 = 0;
        while (true) {
            byte by;
            if ((by = byArray[n]) == 0) {
                if (n2 == charSequence.length()) {
                    return 1;
                }
                return 0;
            }
            if (n2 == charSequence.length()) {
                return 1;
            }
            int n3 = charSequence.charAt(n2) - by;
            if (n3 != 0) {
                return n3;
            }
            ++n2;
            ++n;
        }
    }

    public static ByteBuffer getData(String string) {
        return ICUBinary.getData(null, null, string, false);
    }

    public static ByteBuffer getData(ClassLoader classLoader, String string, String string2) {
        return ICUBinary.getData(classLoader, string, string2, false);
    }

    public static ByteBuffer getRequiredData(String string) {
        return ICUBinary.getData(null, null, string, true);
    }

    private static ByteBuffer getData(ClassLoader classLoader, String string, String string2, boolean bl) {
        ByteBuffer byteBuffer = ICUBinary.getDataFromFile(string2);
        if (byteBuffer != null) {
            return byteBuffer;
        }
        if (classLoader == null) {
            classLoader = ClassLoaderUtil.getClassLoader(ICUData.class);
        }
        if (string == null) {
            string = "com/ibm/icu/impl/data/icudt66b/" + string2;
        }
        ByteBuffer byteBuffer2 = null;
        try {
            InputStream inputStream = ICUData.getStream(classLoader, string, bl);
            if (inputStream == null) {
                return null;
            }
            byteBuffer2 = ICUBinary.getByteBufferFromInputStreamAndCloseStream(inputStream);
        } catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
        return byteBuffer2;
    }

    private static ByteBuffer getDataFromFile(String string) {
        for (DataFile dataFile : icuDataFiles) {
            ByteBuffer byteBuffer = dataFile.getData(string);
            if (byteBuffer == null) continue;
            return byteBuffer;
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static ByteBuffer mapFile(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            FileChannel fileChannel = fileInputStream.getChannel();
            MappedByteBuffer mappedByteBuffer = null;
            try {
                mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, fileChannel.size());
            } finally {
                fileInputStream.close();
            }
            return mappedByteBuffer;
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println(fileNotFoundException);
        } catch (IOException iOException) {
            System.err.println(iOException);
        }
        return null;
    }

    public static void addBaseNamesInFileFolder(String string, String string2, Set<String> set) {
        for (DataFile dataFile : icuDataFiles) {
            dataFile.addBaseNamesInFolder(string, string2, set);
        }
    }

    public static VersionInfo readHeaderAndDataVersion(ByteBuffer byteBuffer, int n, Authenticate authenticate) throws IOException {
        return ICUBinary.getVersionInfoFromCompactInt(ICUBinary.readHeader(byteBuffer, n, authenticate));
    }

    public static int readHeader(ByteBuffer byteBuffer, int n, Authenticate authenticate) throws IOException {
        if (!($assertionsDisabled || byteBuffer != null && byteBuffer.position() == 0)) {
            throw new AssertionError();
        }
        byte by = byteBuffer.get(2);
        byte by2 = byteBuffer.get(3);
        if (by != -38 || by2 != 39) {
            throw new IOException(MAGIC_NUMBER_AUTHENTICATION_FAILED_);
        }
        byte by3 = byteBuffer.get(8);
        byte by4 = byteBuffer.get(9);
        byte by5 = byteBuffer.get(10);
        if (by3 < 0 || 1 < by3 || by4 != 0 || by5 != 2) {
            throw new IOException(HEADER_AUTHENTICATION_FAILED_);
        }
        byteBuffer.order(by3 != 0 ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        char c = byteBuffer.getChar(0);
        char c2 = byteBuffer.getChar(4);
        if (c2 < '\u0014' || c < c2 + 4) {
            throw new IOException("Internal Error: Header size error");
        }
        byte[] byArray = new byte[]{byteBuffer.get(16), byteBuffer.get(17), byteBuffer.get(18), byteBuffer.get(19)};
        if (byteBuffer.get(12) != (byte)(n >> 24) || byteBuffer.get(13) != (byte)(n >> 16) || byteBuffer.get(14) != (byte)(n >> 8) || byteBuffer.get(15) != (byte)n || authenticate != null && !authenticate.isDataVersionAcceptable(byArray)) {
            throw new IOException(HEADER_AUTHENTICATION_FAILED_ + String.format("; data format %02x%02x%02x%02x, format version %d.%d.%d.%d", byteBuffer.get(12), byteBuffer.get(13), byteBuffer.get(14), byteBuffer.get(15), byArray[0] & 0xFF, byArray[1] & 0xFF, byArray[2] & 0xFF, byArray[3] & 0xFF));
        }
        byteBuffer.position(c);
        return byteBuffer.get(20) << 24 | (byteBuffer.get(21) & 0xFF) << 16 | (byteBuffer.get(22) & 0xFF) << 8 | byteBuffer.get(23) & 0xFF;
    }

    public static int writeHeader(int n, int n2, int n3, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeChar(32);
        dataOutputStream.writeByte(-38);
        dataOutputStream.writeByte(39);
        dataOutputStream.writeChar(20);
        dataOutputStream.writeChar(0);
        dataOutputStream.writeByte(1);
        dataOutputStream.writeByte(0);
        dataOutputStream.writeByte(2);
        dataOutputStream.writeByte(0);
        dataOutputStream.writeInt(n);
        dataOutputStream.writeInt(n2);
        dataOutputStream.writeInt(n3);
        dataOutputStream.writeLong(0L);
        if (!$assertionsDisabled && dataOutputStream.size() != 32) {
            throw new AssertionError();
        }
        return 1;
    }

    public static void skipBytes(ByteBuffer byteBuffer, int n) {
        if (n > 0) {
            byteBuffer.position(byteBuffer.position() + n);
        }
    }

    public static byte[] getBytes(ByteBuffer byteBuffer, int n, int n2) {
        byte[] byArray = new byte[n];
        byteBuffer.get(byArray);
        if (n2 > 0) {
            ICUBinary.skipBytes(byteBuffer, n2);
        }
        return byArray;
    }

    public static String getString(ByteBuffer byteBuffer, int n, int n2) {
        CharBuffer charBuffer = byteBuffer.asCharBuffer();
        String string = charBuffer.subSequence(0, n).toString();
        ICUBinary.skipBytes(byteBuffer, n * 2 + n2);
        return string;
    }

    public static char[] getChars(ByteBuffer byteBuffer, int n, int n2) {
        char[] cArray = new char[n];
        byteBuffer.asCharBuffer().get(cArray);
        ICUBinary.skipBytes(byteBuffer, n * 2 + n2);
        return cArray;
    }

    public static short[] getShorts(ByteBuffer byteBuffer, int n, int n2) {
        short[] sArray = new short[n];
        byteBuffer.asShortBuffer().get(sArray);
        ICUBinary.skipBytes(byteBuffer, n * 2 + n2);
        return sArray;
    }

    public static int[] getInts(ByteBuffer byteBuffer, int n, int n2) {
        int[] nArray = new int[n];
        byteBuffer.asIntBuffer().get(nArray);
        ICUBinary.skipBytes(byteBuffer, n * 4 + n2);
        return nArray;
    }

    public static long[] getLongs(ByteBuffer byteBuffer, int n, int n2) {
        long[] lArray = new long[n];
        byteBuffer.asLongBuffer().get(lArray);
        ICUBinary.skipBytes(byteBuffer, n * 8 + n2);
        return lArray;
    }

    public static ByteBuffer sliceWithOrder(ByteBuffer byteBuffer) {
        ByteBuffer byteBuffer2 = byteBuffer.slice();
        return byteBuffer2.order(byteBuffer.order());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ByteBuffer getByteBufferFromInputStreamAndCloseStream(InputStream inputStream) throws IOException {
        try {
            int n = inputStream.available();
            byte[] byArray = n > 32 ? new byte[n] : new byte[128];
            int n2 = 0;
            while (true) {
                int n3;
                if (n2 < byArray.length) {
                    n3 = inputStream.read(byArray, n2, byArray.length - n2);
                    if (n3 < 0) break;
                    n2 += n3;
                    continue;
                }
                n3 = inputStream.read();
                if (n3 < 0) break;
                int n4 = 2 * byArray.length;
                if (n4 < 128) {
                    n4 = 128;
                } else if (n4 < 16384) {
                    n4 *= 2;
                }
                byArray = Arrays.copyOf(byArray, n4);
                byArray[n2++] = (byte)n3;
            }
            ByteBuffer byteBuffer = ByteBuffer.wrap(byArray, 0, n2);
            return byteBuffer;
        } finally {
            inputStream.close();
        }
    }

    public static VersionInfo getVersionInfoFromCompactInt(int n) {
        return VersionInfo.getInstance(n >>> 24, n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF);
    }

    public static byte[] getVersionByteArrayFromCompactInt(int n) {
        return new byte[]{(byte)(n >> 24), (byte)(n >> 16), (byte)(n >> 8), (byte)n};
    }

    static ByteBuffer access$100(File file) {
        return ICUBinary.mapFile(file);
    }

    static {
        $assertionsDisabled = !ICUBinary.class.desiredAssertionStatus();
        icuDataFiles = new ArrayList<DataFile>();
        String string = ICUConfig.get(ICUBinary.class.getName() + ".dataPath");
        if (string != null) {
            ICUBinary.addDataFilesFromPath(string, icuDataFiles);
        }
    }

    public static interface Authenticate {
        public boolean isDataVersionAcceptable(byte[] var1);
    }

    private static final class PackageDataFile
    extends DataFile {
        private final ByteBuffer pkgBytes;

        PackageDataFile(String string, ByteBuffer byteBuffer) {
            super(string);
            this.pkgBytes = byteBuffer;
        }

        @Override
        ByteBuffer getData(String string) {
            return DatPackageReader.getData(this.pkgBytes, string);
        }

        @Override
        void addBaseNamesInFolder(String string, String string2, Set<String> set) {
            DatPackageReader.addBaseNamesInFolder(this.pkgBytes, string, string2, set);
        }
    }

    private static final class SingleDataFile
    extends DataFile {
        private final File path;

        SingleDataFile(String string, File file) {
            super(string);
            this.path = file;
        }

        @Override
        public String toString() {
            return this.path.toString();
        }

        @Override
        ByteBuffer getData(String string) {
            if (string.equals(this.itemPath)) {
                return ICUBinary.access$100(this.path);
            }
            return null;
        }

        @Override
        void addBaseNamesInFolder(String string, String string2, Set<String> set) {
            if (this.itemPath.length() > string.length() + string2.length() && this.itemPath.startsWith(string) && this.itemPath.endsWith(string2) && this.itemPath.charAt(string.length()) == '/' && this.itemPath.indexOf(47, string.length() + 1) < 0) {
                set.add(this.itemPath.substring(string.length() + 1, this.itemPath.length() - string2.length()));
            }
        }
    }

    private static abstract class DataFile {
        protected final String itemPath;

        DataFile(String string) {
            this.itemPath = string;
        }

        public String toString() {
            return this.itemPath;
        }

        abstract ByteBuffer getData(String var1);

        abstract void addBaseNamesInFolder(String var1, String var2, Set<String> var3);
    }

    private static final class DatPackageReader {
        private static final int DATA_FORMAT = 1131245124;
        private static final IsAcceptable IS_ACCEPTABLE;
        static final boolean $assertionsDisabled;

        private DatPackageReader() {
        }

        static boolean validate(ByteBuffer byteBuffer) {
            try {
                ICUBinary.readHeader(byteBuffer, 1131245124, IS_ACCEPTABLE);
            } catch (IOException iOException) {
                return true;
            }
            int n = byteBuffer.getInt(byteBuffer.position());
            if (n <= 0) {
                return true;
            }
            if (byteBuffer.position() + 4 + n * 24 > byteBuffer.capacity()) {
                return true;
            }
            return !DatPackageReader.startsWithPackageName(byteBuffer, DatPackageReader.getNameOffset(byteBuffer, 0)) || !DatPackageReader.startsWithPackageName(byteBuffer, DatPackageReader.getNameOffset(byteBuffer, n - 1));
        }

        private static boolean startsWithPackageName(ByteBuffer byteBuffer, int n) {
            int n2;
            int n3 = 8 - 1;
            for (n2 = 0; n2 < n3; ++n2) {
                if (byteBuffer.get(n + n2) == "icudt66b".charAt(n2)) continue;
                return true;
            }
            return (n2 = (int)byteBuffer.get(n + n3++)) != 98 && n2 != 108 || byteBuffer.get(n + n3) != 47;
        }

        static ByteBuffer getData(ByteBuffer byteBuffer, CharSequence charSequence) {
            int n = DatPackageReader.binarySearch(byteBuffer, charSequence);
            if (n >= 0) {
                ByteBuffer byteBuffer2 = byteBuffer.duplicate();
                byteBuffer2.position(DatPackageReader.getDataOffset(byteBuffer, n));
                byteBuffer2.limit(DatPackageReader.getDataOffset(byteBuffer, n + 1));
                return ICUBinary.sliceWithOrder(byteBuffer2);
            }
            return null;
        }

        static void addBaseNamesInFolder(ByteBuffer byteBuffer, String string, String string2, Set<String> set) {
            int n = DatPackageReader.binarySearch(byteBuffer, string);
            if (n < 0) {
                n ^= 0xFFFFFFFF;
            }
            int n2 = byteBuffer.position();
            int n3 = byteBuffer.getInt(n2);
            StringBuilder stringBuilder = new StringBuilder();
            while (n < n3 && DatPackageReader.addBaseName(byteBuffer, n, string, string2, stringBuilder, set)) {
                ++n;
            }
        }

        private static int binarySearch(ByteBuffer byteBuffer, CharSequence charSequence) {
            int n = byteBuffer.position();
            int n2 = byteBuffer.getInt(n);
            int n3 = 0;
            int n4 = n2;
            while (n3 < n4) {
                int n5 = n3 + n4 >>> 1;
                int n6 = DatPackageReader.getNameOffset(byteBuffer, n5);
                int n7 = ICUBinary.compareKeys(charSequence, byteBuffer, n6 += 9);
                if (n7 < 0) {
                    n4 = n5;
                    continue;
                }
                if (n7 > 0) {
                    n3 = n5 + 1;
                    continue;
                }
                return n5;
            }
            return ~n3;
        }

        private static int getNameOffset(ByteBuffer byteBuffer, int n) {
            int n2 = byteBuffer.position();
            if (!($assertionsDisabled || 0 <= n && n < byteBuffer.getInt(n2))) {
                throw new AssertionError();
            }
            return n2 + byteBuffer.getInt(n2 + 4 + n * 8);
        }

        private static int getDataOffset(ByteBuffer byteBuffer, int n) {
            int n2 = byteBuffer.position();
            int n3 = byteBuffer.getInt(n2);
            if (n == n3) {
                return byteBuffer.capacity();
            }
            if (!($assertionsDisabled || 0 <= n && n < n3)) {
                throw new AssertionError();
            }
            return n2 + byteBuffer.getInt(n2 + 4 + 4 + n * 8);
        }

        static boolean addBaseName(ByteBuffer byteBuffer, int n, String string, String string2, StringBuilder stringBuilder, Set<String> set) {
            char c;
            int n2;
            int n3 = DatPackageReader.getNameOffset(byteBuffer, n);
            n3 += 9;
            if (string.length() != 0) {
                n2 = 0;
                while (n2 < string.length()) {
                    if (byteBuffer.get(n3) != string.charAt(n2)) {
                        return true;
                    }
                    ++n2;
                    ++n3;
                }
                if (byteBuffer.get(n3++) != 47) {
                    return true;
                }
            }
            stringBuilder.setLength(0);
            while (true) {
                byte by = byteBuffer.get(n3++);
                n2 = by;
                if (by == 0) break;
                c = (char)n2;
                if (c == '/') {
                    return false;
                }
                stringBuilder.append(c);
            }
            c = stringBuilder.length() - string2.length();
            if (stringBuilder.lastIndexOf(string2, c) >= 0) {
                set.add(stringBuilder.substring(0, c));
            }
            return false;
        }

        static {
            $assertionsDisabled = !ICUBinary.class.desiredAssertionStatus();
            IS_ACCEPTABLE = new IsAcceptable(null);
        }

        private static final class IsAcceptable
        implements Authenticate {
            private IsAcceptable() {
            }

            @Override
            public boolean isDataVersionAcceptable(byte[] byArray) {
                return byArray[0] == 1;
            }

            IsAcceptable(1 var1_1) {
                this();
            }
        }
    }
}

