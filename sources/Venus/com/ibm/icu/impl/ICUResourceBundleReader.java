/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.CacheValue;
import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.util.ICUException;
import com.ibm.icu.util.ICUUncheckedIOException;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceTypeMismatchException;
import com.ibm.icu.util.VersionInfo;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

public final class ICUResourceBundleReader {
    private static final int DATA_FORMAT = 1382380354;
    private static final IsAcceptable IS_ACCEPTABLE;
    private static final int URES_INDEX_LENGTH = 0;
    private static final int URES_INDEX_KEYS_TOP = 1;
    private static final int URES_INDEX_BUNDLE_TOP = 3;
    private static final int URES_INDEX_MAX_TABLE_LENGTH = 4;
    private static final int URES_INDEX_ATTRIBUTES = 5;
    private static final int URES_INDEX_16BIT_TOP = 6;
    private static final int URES_INDEX_POOL_CHECKSUM = 7;
    private static final int URES_ATT_NO_FALLBACK = 1;
    private static final int URES_ATT_IS_POOL_BUNDLE = 2;
    private static final int URES_ATT_USES_POOL_BUNDLE = 4;
    private static final CharBuffer EMPTY_16_BIT_UNITS;
    static final int LARGE_SIZE = 24;
    private static final boolean DEBUG = false;
    private int dataVersion;
    private ByteBuffer bytes;
    private byte[] keyBytes;
    private CharBuffer b16BitUnits;
    private ICUResourceBundleReader poolBundleReader;
    private int rootRes;
    private int localKeyLimit;
    private int poolStringIndexLimit;
    private int poolStringIndex16Limit;
    private boolean noFallback;
    private boolean isPoolBundle;
    private boolean usesPoolBundle;
    private int poolCheckSum;
    private ResourceCache resourceCache;
    private static ReaderCache CACHE;
    private static final ICUResourceBundleReader NULL_READER;
    private static final byte[] emptyBytes;
    private static final ByteBuffer emptyByteBuffer;
    private static final char[] emptyChars;
    private static final int[] emptyInts;
    private static final String emptyString = "";
    private static final Array EMPTY_ARRAY;
    private static final Table EMPTY_TABLE;
    private static int[] PUBLIC_TYPES;
    private static final String ICU_RESOURCE_SUFFIX = ".res";
    static final boolean $assertionsDisabled;

    private ICUResourceBundleReader() {
    }

    private ICUResourceBundleReader(ByteBuffer byteBuffer, String string, String string2, ClassLoader classLoader) throws IOException {
        this.init(byteBuffer);
        if (this.usesPoolBundle) {
            this.poolBundleReader = ICUResourceBundleReader.getReader(string, "pool", classLoader);
            if (this.poolBundleReader == null || !this.poolBundleReader.isPoolBundle) {
                throw new IllegalStateException("pool.res is not a pool bundle");
            }
            if (this.poolBundleReader.poolCheckSum != this.poolCheckSum) {
                throw new IllegalStateException("pool.res has a different checksum than this bundle");
            }
        }
    }

    static ICUResourceBundleReader getReader(String string, String string2, ClassLoader classLoader) {
        ReaderCacheKey readerCacheKey = new ReaderCacheKey(string, string2);
        ICUResourceBundleReader iCUResourceBundleReader = (ICUResourceBundleReader)CACHE.getInstance(readerCacheKey, classLoader);
        if (iCUResourceBundleReader == NULL_READER) {
            return null;
        }
        return iCUResourceBundleReader;
    }

    private void init(ByteBuffer byteBuffer) throws IOException {
        int n;
        int n2;
        this.dataVersion = ICUBinary.readHeader(byteBuffer, 1382380354, IS_ACCEPTABLE);
        byte by = byteBuffer.get(16);
        this.bytes = ICUBinary.sliceWithOrder(byteBuffer);
        int n3 = this.bytes.remaining();
        this.rootRes = this.bytes.getInt(0);
        int n4 = this.getIndexesInt(0);
        int n5 = n4 & 0xFF;
        if (n5 <= 4) {
            throw new ICUException("not enough indexes");
        }
        if (n3 < 1 + n5 << 2 || n3 < (n2 = this.getIndexesInt(3)) << 2) {
            throw new ICUException("not enough bytes");
        }
        int n6 = n2 - 1;
        if (by >= 3) {
            this.poolStringIndexLimit = n4 >>> 8;
        }
        if (n5 > 5) {
            n = this.getIndexesInt(5);
            this.noFallback = (n & 1) != 0;
            this.isPoolBundle = (n & 2) != 0;
            this.usesPoolBundle = (n & 4) != 0;
            this.poolStringIndexLimit |= (n & 0xF000) << 12;
            this.poolStringIndex16Limit = n >>> 16;
        }
        n = 1 + n5;
        int n7 = this.getIndexesInt(1);
        if (n7 > n) {
            if (this.isPoolBundle) {
                this.keyBytes = new byte[n7 - n << 2];
                this.bytes.position(n << 2);
            } else {
                this.localKeyLimit = n7 << 2;
                this.keyBytes = new byte[this.localKeyLimit];
            }
            this.bytes.get(this.keyBytes);
        }
        if (n5 > 6) {
            int n8 = this.getIndexesInt(6);
            if (n8 > n7) {
                int n9 = (n8 - n7) * 2;
                this.bytes.position(n7 << 2);
                this.b16BitUnits = this.bytes.asCharBuffer();
                this.b16BitUnits.limit(n9);
                n6 |= n9 - 1;
            } else {
                this.b16BitUnits = EMPTY_16_BIT_UNITS;
            }
        } else {
            this.b16BitUnits = EMPTY_16_BIT_UNITS;
        }
        if (n5 > 7) {
            this.poolCheckSum = this.getIndexesInt(7);
        }
        if (!this.isPoolBundle || this.b16BitUnits.length() > 1) {
            this.resourceCache = new ResourceCache(n6);
        }
        this.bytes.position(0);
    }

    private int getIndexesInt(int n) {
        return this.bytes.getInt(1 + n << 2);
    }

    VersionInfo getVersion() {
        return ICUBinary.getVersionInfoFromCompactInt(this.dataVersion);
    }

    int getRootResource() {
        return this.rootRes;
    }

    boolean getNoFallback() {
        return this.noFallback;
    }

    boolean getUsesPoolBundle() {
        return this.usesPoolBundle;
    }

    static int RES_GET_TYPE(int n) {
        return n >>> 28;
    }

    private static int RES_GET_OFFSET(int n) {
        return n & 0xFFFFFFF;
    }

    private int getResourceByteOffset(int n) {
        return n << 2;
    }

    static int RES_GET_INT(int n) {
        return n << 4 >> 4;
    }

    static int RES_GET_UINT(int n) {
        return n & 0xFFFFFFF;
    }

    static boolean URES_IS_ARRAY(int n) {
        return n == 8 || n == 9;
    }

    static boolean URES_IS_TABLE(int n) {
        return n == 2 || n == 5 || n == 4;
    }

    private char[] getChars(int n, int n2) {
        char[] cArray = new char[n2];
        if (n2 <= 16) {
            for (int i = 0; i < n2; ++i) {
                cArray[i] = this.bytes.getChar(n);
                n += 2;
            }
        } else {
            CharBuffer charBuffer = this.bytes.asCharBuffer();
            charBuffer.position(n / 2);
            charBuffer.get(cArray);
        }
        return cArray;
    }

    private int getInt(int n) {
        return this.bytes.getInt(n);
    }

    private int[] getInts(int n, int n2) {
        int[] nArray = new int[n2];
        if (n2 <= 16) {
            for (int i = 0; i < n2; ++i) {
                nArray[i] = this.bytes.getInt(n);
                n += 4;
            }
        } else {
            IntBuffer intBuffer = this.bytes.asIntBuffer();
            intBuffer.position(n / 4);
            intBuffer.get(nArray);
        }
        return nArray;
    }

    private char[] getTable16KeyOffsets(int n) {
        int n2;
        if ((n2 = this.b16BitUnits.charAt(n++)) > 0) {
            char[] cArray = new char[n2];
            if (n2 <= 16) {
                for (int i = 0; i < n2; ++i) {
                    cArray[i] = this.b16BitUnits.charAt(n++);
                }
            } else {
                CharBuffer charBuffer = this.b16BitUnits.duplicate();
                charBuffer.position(n);
                charBuffer.get(cArray);
            }
            return cArray;
        }
        return emptyChars;
    }

    private char[] getTableKeyOffsets(int n) {
        char c = this.bytes.getChar(n);
        if (c > '\u0000') {
            return this.getChars(n + 2, c);
        }
        return emptyChars;
    }

    private int[] getTable32KeyOffsets(int n) {
        int n2 = this.getInt(n);
        if (n2 > 0) {
            return this.getInts(n + 4, n2);
        }
        return emptyInts;
    }

    private static String makeKeyStringFromBytes(byte[] byArray, int n) {
        byte by;
        StringBuilder stringBuilder = new StringBuilder();
        while ((by = byArray[n]) != 0) {
            ++n;
            stringBuilder.append((char)by);
        }
        return stringBuilder.toString();
    }

    private String getKey16String(int n) {
        if (n < this.localKeyLimit) {
            return ICUResourceBundleReader.makeKeyStringFromBytes(this.keyBytes, n);
        }
        return ICUResourceBundleReader.makeKeyStringFromBytes(this.poolBundleReader.keyBytes, n - this.localKeyLimit);
    }

    private String getKey32String(int n) {
        if (n >= 0) {
            return ICUResourceBundleReader.makeKeyStringFromBytes(this.keyBytes, n);
        }
        return ICUResourceBundleReader.makeKeyStringFromBytes(this.poolBundleReader.keyBytes, n & Integer.MAX_VALUE);
    }

    private void setKeyFromKey16(int n, UResource.Key key) {
        if (n < this.localKeyLimit) {
            key.setBytes(this.keyBytes, n);
        } else {
            key.setBytes(this.poolBundleReader.keyBytes, n - this.localKeyLimit);
        }
    }

    private void setKeyFromKey32(int n, UResource.Key key) {
        if (n >= 0) {
            key.setBytes(this.keyBytes, n);
        } else {
            key.setBytes(this.poolBundleReader.keyBytes, n & Integer.MAX_VALUE);
        }
    }

    private int compareKeys(CharSequence charSequence, char c) {
        if (c < this.localKeyLimit) {
            return ICUBinary.compareKeys(charSequence, this.keyBytes, (int)c);
        }
        return ICUBinary.compareKeys(charSequence, this.poolBundleReader.keyBytes, c - this.localKeyLimit);
    }

    private int compareKeys32(CharSequence charSequence, int n) {
        if (n >= 0) {
            return ICUBinary.compareKeys(charSequence, this.keyBytes, n);
        }
        return ICUBinary.compareKeys(charSequence, this.poolBundleReader.keyBytes, n & Integer.MAX_VALUE);
    }

    String getStringV2(int n) {
        String string;
        if (!$assertionsDisabled && ICUResourceBundleReader.RES_GET_TYPE(n) != 6) {
            throw new AssertionError();
        }
        int n2 = ICUResourceBundleReader.RES_GET_OFFSET(n);
        if (!$assertionsDisabled && n2 == 0) {
            throw new AssertionError();
        }
        Object object = this.resourceCache.get(n);
        if (object != null) {
            return (String)object;
        }
        char c = this.b16BitUnits.charAt(n2);
        if ((c & 0xFFFFFC00) != 56320) {
            char c2;
            if (c == '\u0000') {
                return emptyString;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(c);
            while ((c2 = this.b16BitUnits.charAt(++n2)) != '\u0000') {
                stringBuilder.append(c2);
            }
            string = stringBuilder.toString();
        } else {
            int n3;
            if (c < '\udfef') {
                n3 = c & 0x3FF;
                ++n2;
            } else if (c < '\udfff') {
                n3 = c - 57327 << 16 | this.b16BitUnits.charAt(n2 + 1);
                n2 += 2;
            } else {
                n3 = this.b16BitUnits.charAt(n2 + 1) << 16 | this.b16BitUnits.charAt(n2 + 2);
                n2 += 3;
            }
            string = this.b16BitUnits.subSequence(n2, n2 + n3).toString();
        }
        return (String)this.resourceCache.putIfAbsent(n, string, string.length() * 2);
    }

    private String makeStringFromBytes(int n, int n2) {
        if (n2 <= 16) {
            StringBuilder stringBuilder = new StringBuilder(n2);
            for (int i = 0; i < n2; ++i) {
                stringBuilder.append(this.bytes.getChar(n));
                n += 2;
            }
            return stringBuilder.toString();
        }
        CharBuffer charBuffer = this.bytes.asCharBuffer();
        return charBuffer.subSequence(n /= 2, n + n2).toString();
    }

    String getString(int n) {
        int n2 = ICUResourceBundleReader.RES_GET_OFFSET(n);
        if (n != n2 && ICUResourceBundleReader.RES_GET_TYPE(n) != 6) {
            return null;
        }
        if (n2 == 0) {
            return emptyString;
        }
        if (n != n2) {
            if (n2 < this.poolStringIndexLimit) {
                return this.poolBundleReader.getStringV2(n);
            }
            return this.getStringV2(n - this.poolStringIndexLimit);
        }
        Object object = this.resourceCache.get(n);
        if (object != null) {
            return (String)object;
        }
        n2 = this.getResourceByteOffset(n2);
        int n3 = this.getInt(n2);
        String string = this.makeStringFromBytes(n2 + 4, n3);
        return (String)this.resourceCache.putIfAbsent(n, string, string.length() * 2);
    }

    private boolean isNoInheritanceMarker(int n) {
        int n2 = ICUResourceBundleReader.RES_GET_OFFSET(n);
        if (n2 != 0) {
            if (n == n2) {
                return this.getInt(n2 = this.getResourceByteOffset(n2)) == 3 && this.bytes.getChar(n2 + 4) == '\u2205' && this.bytes.getChar(n2 + 6) == '\u2205' && this.bytes.getChar(n2 + 8) == '\u2205';
            }
            if (ICUResourceBundleReader.RES_GET_TYPE(n) == 6) {
                if (n2 < this.poolStringIndexLimit) {
                    return this.poolBundleReader.isStringV2NoInheritanceMarker(n2);
                }
                return this.isStringV2NoInheritanceMarker(n2 - this.poolStringIndexLimit);
            }
        }
        return true;
    }

    private boolean isStringV2NoInheritanceMarker(int n) {
        char c = this.b16BitUnits.charAt(n);
        if (c == '\u2205') {
            return this.b16BitUnits.charAt(n + 1) == '\u2205' && this.b16BitUnits.charAt(n + 2) == '\u2205' && this.b16BitUnits.charAt(n + 3) == '\u0000';
        }
        if (c == '\udc03') {
            return this.b16BitUnits.charAt(n + 1) == '\u2205' && this.b16BitUnits.charAt(n + 2) == '\u2205' && this.b16BitUnits.charAt(n + 3) == '\u2205';
        }
        return true;
    }

    String getAlias(int n) {
        int n2 = ICUResourceBundleReader.RES_GET_OFFSET(n);
        if (ICUResourceBundleReader.RES_GET_TYPE(n) == 3) {
            if (n2 == 0) {
                return emptyString;
            }
            Object object = this.resourceCache.get(n);
            if (object != null) {
                return (String)object;
            }
            n2 = this.getResourceByteOffset(n2);
            int n3 = this.getInt(n2);
            String string = this.makeStringFromBytes(n2 + 4, n3);
            return (String)this.resourceCache.putIfAbsent(n, string, n3 * 2);
        }
        return null;
    }

    byte[] getBinary(int n, byte[] byArray) {
        int n2 = ICUResourceBundleReader.RES_GET_OFFSET(n);
        if (ICUResourceBundleReader.RES_GET_TYPE(n) == 1) {
            if (n2 == 0) {
                return emptyBytes;
            }
            int n3 = this.getInt(n2 = this.getResourceByteOffset(n2));
            if (n3 == 0) {
                return emptyBytes;
            }
            if (byArray == null || byArray.length != n3) {
                byArray = new byte[n3];
            }
            n2 += 4;
            if (n3 <= 16) {
                for (int i = 0; i < n3; ++i) {
                    byArray[i] = this.bytes.get(n2++);
                }
            } else {
                ByteBuffer byteBuffer = this.bytes.duplicate();
                byteBuffer.position(n2);
                byteBuffer.get(byArray);
            }
            return byArray;
        }
        return null;
    }

    ByteBuffer getBinary(int n) {
        int n2 = ICUResourceBundleReader.RES_GET_OFFSET(n);
        if (ICUResourceBundleReader.RES_GET_TYPE(n) == 1) {
            if (n2 == 0) {
                return emptyByteBuffer.duplicate();
            }
            int n3 = this.getInt(n2 = this.getResourceByteOffset(n2));
            if (n3 == 0) {
                return emptyByteBuffer.duplicate();
            }
            ByteBuffer byteBuffer = this.bytes.duplicate();
            byteBuffer.position(n2 += 4).limit(n2 + n3);
            byteBuffer = ICUBinary.sliceWithOrder(byteBuffer);
            if (!byteBuffer.isReadOnly()) {
                byteBuffer = byteBuffer.asReadOnlyBuffer();
            }
            return byteBuffer;
        }
        return null;
    }

    int[] getIntVector(int n) {
        int n2 = ICUResourceBundleReader.RES_GET_OFFSET(n);
        if (ICUResourceBundleReader.RES_GET_TYPE(n) == 14) {
            if (n2 == 0) {
                return emptyInts;
            }
            n2 = this.getResourceByteOffset(n2);
            int n3 = this.getInt(n2);
            return this.getInts(n2 + 4, n3);
        }
        return null;
    }

    Array getArray(int n) {
        int n2 = ICUResourceBundleReader.RES_GET_TYPE(n);
        if (!ICUResourceBundleReader.URES_IS_ARRAY(n2)) {
            return null;
        }
        int n3 = ICUResourceBundleReader.RES_GET_OFFSET(n);
        if (n3 == 0) {
            return EMPTY_ARRAY;
        }
        Object object = this.resourceCache.get(n);
        if (object != null) {
            return (Array)object;
        }
        Array array = n2 == 8 ? new Array32(this, n3) : new Array16(this, n3);
        return (Array)this.resourceCache.putIfAbsent(n, array, 0);
    }

    Table getTable(int n) {
        int n2;
        Table table;
        int n3 = ICUResourceBundleReader.RES_GET_TYPE(n);
        if (!ICUResourceBundleReader.URES_IS_TABLE(n3)) {
            return null;
        }
        int n4 = ICUResourceBundleReader.RES_GET_OFFSET(n);
        if (n4 == 0) {
            return EMPTY_TABLE;
        }
        Object object = this.resourceCache.get(n);
        if (object != null) {
            return (Table)object;
        }
        if (n3 == 2) {
            table = new Table1632(this, n4);
            n2 = table.getSize() * 2;
        } else if (n3 == 5) {
            table = new Table16(this, n4);
            n2 = table.getSize() * 2;
        } else {
            table = new Table32(this, n4);
            n2 = table.getSize() * 4;
        }
        return (Table)this.resourceCache.putIfAbsent(n, table, n2);
    }

    public static String getFullName(String string, String string2) {
        if (string == null || string.length() == 0) {
            if (string2.length() == 0) {
                string2 = ULocale.getDefault().toString();
                return string2;
            }
            return string2 + ICU_RESOURCE_SUFFIX;
        }
        if (string.indexOf(46) == -1) {
            if (string.charAt(string.length() - 1) != '/') {
                return string + "/" + string2 + ICU_RESOURCE_SUFFIX;
            }
            return string + string2 + ICU_RESOURCE_SUFFIX;
        }
        string = string.replace('.', '/');
        if (string2.length() == 0) {
            return string + ICU_RESOURCE_SUFFIX;
        }
        return string + "_" + string2 + ICU_RESOURCE_SUFFIX;
    }

    static ICUResourceBundleReader access$200() {
        return NULL_READER;
    }

    ICUResourceBundleReader(ByteBuffer byteBuffer, String string, String string2, ClassLoader classLoader, 1 var5_5) throws IOException {
        this(byteBuffer, string, string2, classLoader);
    }

    static int[] access$400() {
        return PUBLIC_TYPES;
    }

    static boolean access$500(ICUResourceBundleReader iCUResourceBundleReader, int n) {
        return iCUResourceBundleReader.isNoInheritanceMarker(n);
    }

    static CharBuffer access$600(ICUResourceBundleReader iCUResourceBundleReader) {
        return iCUResourceBundleReader.b16BitUnits;
    }

    static int access$700(ICUResourceBundleReader iCUResourceBundleReader) {
        return iCUResourceBundleReader.poolStringIndex16Limit;
    }

    static int access$800(ICUResourceBundleReader iCUResourceBundleReader) {
        return iCUResourceBundleReader.poolStringIndexLimit;
    }

    static int access$900(ICUResourceBundleReader iCUResourceBundleReader, int n) {
        return iCUResourceBundleReader.getInt(n);
    }

    static int access$1000(ICUResourceBundleReader iCUResourceBundleReader, int n) {
        return iCUResourceBundleReader.getResourceByteOffset(n);
    }

    static String access$1100(ICUResourceBundleReader iCUResourceBundleReader, int n) {
        return iCUResourceBundleReader.getKey16String(n);
    }

    static String access$1200(ICUResourceBundleReader iCUResourceBundleReader, int n) {
        return iCUResourceBundleReader.getKey32String(n);
    }

    static int access$1300(ICUResourceBundleReader iCUResourceBundleReader, CharSequence charSequence, char c) {
        return iCUResourceBundleReader.compareKeys(charSequence, c);
    }

    static int access$1400(ICUResourceBundleReader iCUResourceBundleReader, CharSequence charSequence, int n) {
        return iCUResourceBundleReader.compareKeys32(charSequence, n);
    }

    static void access$1500(ICUResourceBundleReader iCUResourceBundleReader, int n, UResource.Key key) {
        iCUResourceBundleReader.setKeyFromKey16(n, key);
    }

    static void access$1600(ICUResourceBundleReader iCUResourceBundleReader, int n, UResource.Key key) {
        iCUResourceBundleReader.setKeyFromKey32(n, key);
    }

    static char[] access$1700(ICUResourceBundleReader iCUResourceBundleReader, int n) {
        return iCUResourceBundleReader.getTableKeyOffsets(n);
    }

    static char[] access$1800(ICUResourceBundleReader iCUResourceBundleReader, int n) {
        return iCUResourceBundleReader.getTable16KeyOffsets(n);
    }

    static int[] access$1900(ICUResourceBundleReader iCUResourceBundleReader, int n) {
        return iCUResourceBundleReader.getTable32KeyOffsets(n);
    }

    static int access$2200(int n) {
        return ICUResourceBundleReader.RES_GET_OFFSET(n);
    }

    static {
        $assertionsDisabled = !ICUResourceBundleReader.class.desiredAssertionStatus();
        IS_ACCEPTABLE = new IsAcceptable(null);
        EMPTY_16_BIT_UNITS = CharBuffer.wrap("\u0000");
        CACHE = new ReaderCache(null);
        NULL_READER = new ICUResourceBundleReader();
        emptyBytes = new byte[0];
        emptyByteBuffer = ByteBuffer.allocate(0).asReadOnlyBuffer();
        emptyChars = new char[0];
        emptyInts = new int[0];
        EMPTY_ARRAY = new Array();
        EMPTY_TABLE = new Table();
        PUBLIC_TYPES = new int[]{0, 1, 2, 3, 2, 2, 0, 7, 8, 8, -1, -1, -1, -1, 14, -1};
    }

    private static final class ResourceCache {
        private static final int SIMPLE_LENGTH = 32;
        private static final int ROOT_BITS = 7;
        private static final int NEXT_BITS = 6;
        private int[] keys = new int[32];
        private Object[] values = new Object[32];
        private int length;
        private int maxOffsetBits;
        private int levelBitsList;
        private Level rootLevel;
        static final boolean $assertionsDisabled = !ICUResourceBundleReader.class.desiredAssertionStatus();

        private static boolean storeDirectly(int n) {
            return n < 24 || CacheValue.futureInstancesWillBeStrong();
        }

        private static final Object putIfCleared(Object[] objectArray, int n, Object object, int n2) {
            Object object2 = objectArray[n];
            if (!(object2 instanceof SoftReference)) {
                return object2;
            }
            if (!$assertionsDisabled && n2 < 24) {
                throw new AssertionError();
            }
            if ((object2 = ((SoftReference)object2).get()) != null) {
                return object2;
            }
            objectArray[n] = CacheValue.futureInstancesWillBeStrong() ? object : new SoftReference<Object>(object);
            return object;
        }

        ResourceCache(int n) {
            if (!$assertionsDisabled && n == 0) {
                throw new AssertionError();
            }
            this.maxOffsetBits = 28;
            while (n <= 0x7FFFFFF) {
                n <<= 1;
                --this.maxOffsetBits;
            }
            int n2 = this.maxOffsetBits + 2;
            if (n2 <= 7) {
                this.levelBitsList = n2;
            } else if (n2 < 10) {
                this.levelBitsList = 0x30 | n2 - 3;
            } else {
                this.levelBitsList = 7;
                n2 -= 7;
                int n3 = 4;
                while (true) {
                    if (n2 <= 6) {
                        this.levelBitsList |= n2 << n3;
                        break;
                    }
                    if (n2 < 9) {
                        this.levelBitsList |= (0x30 | n2 - 3) << n3;
                        break;
                    }
                    this.levelBitsList |= 6 << n3;
                    n2 -= 6;
                    n3 += 4;
                }
            }
        }

        private int makeKey(int n) {
            int n2 = ICUResourceBundleReader.RES_GET_TYPE(n);
            int n3 = n2 == 6 ? 1 : (n2 == 5 ? 3 : (n2 == 9 ? 2 : 0));
            return ICUResourceBundleReader.access$2200(n) | n3 << this.maxOffsetBits;
        }

        private int findSimple(int n) {
            return Arrays.binarySearch(this.keys, 0, this.length, n);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        synchronized Object get(int n) {
            Object object;
            if (!$assertionsDisabled && ICUResourceBundleReader.access$2200(n) == 0) {
                throw new AssertionError();
            }
            if (this.length >= 0) {
                int n2 = this.findSimple(n);
                if (n2 < 0) return null;
                object = this.values[n2];
            } else {
                object = this.rootLevel.get(this.makeKey(n));
                if (object == null) {
                    return null;
                }
            }
            if (!(object instanceof SoftReference)) return object;
            return ((SoftReference)object).get();
        }

        synchronized Object putIfAbsent(int n, Object object, int n2) {
            if (this.length >= 0) {
                int n3 = this.findSimple(n);
                if (n3 >= 0) {
                    return ResourceCache.putIfCleared(this.values, n3, object, n2);
                }
                if (this.length < 32) {
                    if ((n3 ^= 0xFFFFFFFF) < this.length) {
                        System.arraycopy(this.keys, n3, this.keys, n3 + 1, this.length - n3);
                        System.arraycopy(this.values, n3, this.values, n3 + 1, this.length - n3);
                    }
                    ++this.length;
                    this.keys[n3] = n;
                    this.values[n3] = ResourceCache.storeDirectly(n2) ? object : new SoftReference<Object>(object);
                    return object;
                }
                this.rootLevel = new Level(this.levelBitsList, 0);
                for (int i = 0; i < 32; ++i) {
                    this.rootLevel.putIfAbsent(this.makeKey(this.keys[i]), this.values[i], 0);
                }
                this.keys = null;
                this.values = null;
                this.length = -1;
            }
            return this.rootLevel.putIfAbsent(this.makeKey(n), object, n2);
        }

        static Object access$2000(Object[] objectArray, int n, Object object, int n2) {
            return ResourceCache.putIfCleared(objectArray, n, object, n2);
        }

        static boolean access$2100(int n) {
            return ResourceCache.storeDirectly(n);
        }

        private static final class Level {
            int levelBitsList;
            int shift;
            int mask;
            int[] keys;
            Object[] values;
            static final boolean $assertionsDisabled = !ICUResourceBundleReader.class.desiredAssertionStatus();

            Level(int n, int n2) {
                this.levelBitsList = n;
                this.shift = n2;
                int n3 = n & 0xF;
                if (!$assertionsDisabled && n3 == 0) {
                    throw new AssertionError();
                }
                int n4 = 1 << n3;
                this.mask = n4 - 1;
                this.keys = new int[n4];
                this.values = new Object[n4];
            }

            Object get(int n) {
                Level level;
                int n2 = n >> this.shift & this.mask;
                int n3 = this.keys[n2];
                if (n3 == n) {
                    return this.values[n2];
                }
                if (n3 == 0 && (level = (Level)this.values[n2]) != null) {
                    return level.get(n);
                }
                return null;
            }

            Object putIfAbsent(int n, Object object, int n2) {
                int n3 = n >> this.shift & this.mask;
                int n4 = this.keys[n3];
                if (n4 == n) {
                    return ResourceCache.access$2000(this.values, n3, object, n2);
                }
                if (n4 == 0) {
                    Level level = (Level)this.values[n3];
                    if (level != null) {
                        return level.putIfAbsent(n, object, n2);
                    }
                    this.keys[n3] = n;
                    this.values[n3] = ResourceCache.access$2100(n2) ? object : new SoftReference<Object>(object);
                    return object;
                }
                Level level = new Level(this.levelBitsList >> 4, this.shift + (this.levelBitsList & 0xF));
                int n5 = n4 >> level.shift & level.mask;
                level.keys[n5] = n4;
                level.values[n5] = this.values[n3];
                this.keys[n3] = 0;
                this.values[n3] = level;
                return level.putIfAbsent(n, object, n2);
            }
        }
    }

    private static final class Table32
    extends Table {
        @Override
        int getContainerResource(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            return this.getContainer32Resource(iCUResourceBundleReader, n);
        }

        Table32(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            n = ICUResourceBundleReader.access$1000(iCUResourceBundleReader, n);
            this.key32Offsets = ICUResourceBundleReader.access$1900(iCUResourceBundleReader, n);
            this.size = this.key32Offsets.length;
            this.itemsOffset = n + 4 * (1 + this.size);
        }
    }

    private static final class Table16
    extends Table {
        @Override
        int getContainerResource(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            return this.getContainer16Resource(iCUResourceBundleReader, n);
        }

        Table16(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            this.keyOffsets = ICUResourceBundleReader.access$1800(iCUResourceBundleReader, n);
            this.size = this.keyOffsets.length;
            this.itemsOffset = n + 1 + this.size;
        }
    }

    private static final class Table1632
    extends Table {
        @Override
        int getContainerResource(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            return this.getContainer32Resource(iCUResourceBundleReader, n);
        }

        Table1632(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            n = ICUResourceBundleReader.access$1000(iCUResourceBundleReader, n);
            this.keyOffsets = ICUResourceBundleReader.access$1700(iCUResourceBundleReader, n);
            this.size = this.keyOffsets.length;
            this.itemsOffset = n + 2 * (this.size + 2 & 0xFFFFFFFE);
        }
    }

    static class Table
    extends Container
    implements UResource.Table {
        protected char[] keyOffsets;
        protected int[] key32Offsets;
        private static final int URESDATA_ITEM_NOT_FOUND = -1;

        Table() {
        }

        String getKey(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            if (n < 0 || this.size <= n) {
                return null;
            }
            return this.keyOffsets != null ? ICUResourceBundleReader.access$1100(iCUResourceBundleReader, this.keyOffsets[n]) : ICUResourceBundleReader.access$1200(iCUResourceBundleReader, this.key32Offsets[n]);
        }

        int findTableItem(ICUResourceBundleReader iCUResourceBundleReader, CharSequence charSequence) {
            int n = 0;
            int n2 = this.size;
            while (n < n2) {
                int n3 = n + n2 >>> 1;
                int n4 = this.keyOffsets != null ? ICUResourceBundleReader.access$1300(iCUResourceBundleReader, charSequence, this.keyOffsets[n3]) : ICUResourceBundleReader.access$1400(iCUResourceBundleReader, charSequence, this.key32Offsets[n3]);
                if (n4 < 0) {
                    n2 = n3;
                    continue;
                }
                if (n4 > 0) {
                    n = n3 + 1;
                    continue;
                }
                return n3;
            }
            return 1;
        }

        @Override
        int getResource(ICUResourceBundleReader iCUResourceBundleReader, String string) {
            return this.getContainerResource(iCUResourceBundleReader, this.findTableItem(iCUResourceBundleReader, string));
        }

        @Override
        public boolean getKeyAndValue(int n, UResource.Key key, UResource.Value value) {
            if (0 <= n && n < this.size) {
                ReaderValue readerValue = (ReaderValue)value;
                if (this.keyOffsets != null) {
                    ICUResourceBundleReader.access$1500(readerValue.reader, this.keyOffsets[n], key);
                } else {
                    ICUResourceBundleReader.access$1600(readerValue.reader, this.key32Offsets[n], key);
                }
                readerValue.res = this.getContainerResource(readerValue.reader, n);
                return false;
            }
            return true;
        }

        @Override
        public boolean findValue(CharSequence charSequence, UResource.Value value) {
            ReaderValue readerValue = (ReaderValue)value;
            int n = this.findTableItem(readerValue.reader, charSequence);
            if (n >= 0) {
                readerValue.res = this.getContainerResource(readerValue.reader, n);
                return false;
            }
            return true;
        }
    }

    private static final class Array16
    extends Array {
        @Override
        int getContainerResource(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            return this.getContainer16Resource(iCUResourceBundleReader, n);
        }

        Array16(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            this.size = ICUResourceBundleReader.access$600(iCUResourceBundleReader).charAt(n);
            this.itemsOffset = n + 1;
        }
    }

    private static final class Array32
    extends Array {
        @Override
        int getContainerResource(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            return this.getContainer32Resource(iCUResourceBundleReader, n);
        }

        Array32(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            n = ICUResourceBundleReader.access$1000(iCUResourceBundleReader, n);
            this.size = ICUResourceBundleReader.access$900(iCUResourceBundleReader, n);
            this.itemsOffset = n + 4;
        }
    }

    static class Array
    extends Container
    implements UResource.Array {
        Array() {
        }

        @Override
        public boolean getValue(int n, UResource.Value value) {
            if (0 <= n && n < this.size) {
                ReaderValue readerValue = (ReaderValue)value;
                readerValue.res = this.getContainerResource(readerValue.reader, n);
                return false;
            }
            return true;
        }
    }

    static class Container {
        protected int size;
        protected int itemsOffset;

        public final int getSize() {
            return this.size;
        }

        int getContainerResource(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            return 1;
        }

        protected int getContainer16Resource(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            if (n < 0 || this.size <= n) {
                return 1;
            }
            int n2 = ICUResourceBundleReader.access$600(iCUResourceBundleReader).charAt(this.itemsOffset + n);
            if (n2 >= ICUResourceBundleReader.access$700(iCUResourceBundleReader)) {
                n2 = n2 - ICUResourceBundleReader.access$700(iCUResourceBundleReader) + ICUResourceBundleReader.access$800(iCUResourceBundleReader);
            }
            return 0x60000000 | n2;
        }

        protected int getContainer32Resource(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            if (n < 0 || this.size <= n) {
                return 1;
            }
            return ICUResourceBundleReader.access$900(iCUResourceBundleReader, this.itemsOffset + 4 * n);
        }

        int getResource(ICUResourceBundleReader iCUResourceBundleReader, String string) {
            return this.getContainerResource(iCUResourceBundleReader, Integer.parseInt(string));
        }

        Container() {
        }
    }

    static class ReaderValue
    extends UResource.Value {
        ICUResourceBundleReader reader;
        int res;

        ReaderValue() {
        }

        @Override
        public int getType() {
            return ICUResourceBundleReader.access$400()[ICUResourceBundleReader.RES_GET_TYPE(this.res)];
        }

        @Override
        public String getString() {
            String string = this.reader.getString(this.res);
            if (string == null) {
                throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
            }
            return string;
        }

        @Override
        public String getAliasString() {
            String string = this.reader.getAlias(this.res);
            if (string == null) {
                throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
            }
            return string;
        }

        @Override
        public int getInt() {
            if (ICUResourceBundleReader.RES_GET_TYPE(this.res) != 7) {
                throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
            }
            return ICUResourceBundleReader.RES_GET_INT(this.res);
        }

        @Override
        public int getUInt() {
            if (ICUResourceBundleReader.RES_GET_TYPE(this.res) != 7) {
                throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
            }
            return ICUResourceBundleReader.RES_GET_UINT(this.res);
        }

        @Override
        public int[] getIntVector() {
            int[] nArray = this.reader.getIntVector(this.res);
            if (nArray == null) {
                throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
            }
            return nArray;
        }

        @Override
        public ByteBuffer getBinary() {
            ByteBuffer byteBuffer = this.reader.getBinary(this.res);
            if (byteBuffer == null) {
                throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
            }
            return byteBuffer;
        }

        @Override
        public UResource.Array getArray() {
            Array array = this.reader.getArray(this.res);
            if (array == null) {
                throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
            }
            return array;
        }

        @Override
        public UResource.Table getTable() {
            Table table = this.reader.getTable(this.res);
            if (table == null) {
                throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
            }
            return table;
        }

        @Override
        public boolean isNoInheritanceMarker() {
            return ICUResourceBundleReader.access$500(this.reader, this.res);
        }

        @Override
        public String[] getStringArray() {
            Array array = this.reader.getArray(this.res);
            if (array == null) {
                throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
            }
            return this.getStringArray(array);
        }

        @Override
        public String[] getStringArrayOrStringAsArray() {
            Array array = this.reader.getArray(this.res);
            if (array != null) {
                return this.getStringArray(array);
            }
            String string = this.reader.getString(this.res);
            if (string != null) {
                return new String[]{string};
            }
            throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
        }

        @Override
        public String getStringOrFirstOfArray() {
            int n;
            String string = this.reader.getString(this.res);
            if (string != null) {
                return string;
            }
            Array array = this.reader.getArray(this.res);
            if (array != null && array.size > 0 && (string = this.reader.getString(n = array.getContainerResource(this.reader, 0))) != null) {
                return string;
            }
            throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
        }

        private String[] getStringArray(Array array) {
            String[] stringArray = new String[array.size];
            for (int i = 0; i < array.size; ++i) {
                int n = array.getContainerResource(this.reader, i);
                String string = this.reader.getString(n);
                if (string == null) {
                    throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
                }
                stringArray[i] = string;
            }
            return stringArray;
        }
    }

    private static class ReaderCache
    extends SoftCache<ReaderCacheKey, ICUResourceBundleReader, ClassLoader> {
        private ReaderCache() {
        }

        @Override
        protected ICUResourceBundleReader createInstance(ReaderCacheKey readerCacheKey, ClassLoader classLoader) {
            String string = ICUResourceBundleReader.getFullName(readerCacheKey.baseName, readerCacheKey.localeID);
            try {
                ByteBuffer byteBuffer;
                if (readerCacheKey.baseName != null && readerCacheKey.baseName.startsWith("com/ibm/icu/impl/data/icudt66b")) {
                    String string2 = string.substring(31);
                    byteBuffer = ICUBinary.getData(classLoader, string, string2);
                    if (byteBuffer == null) {
                        return ICUResourceBundleReader.access$200();
                    }
                } else {
                    InputStream inputStream = ICUData.getStream(classLoader, string);
                    if (inputStream == null) {
                        return ICUResourceBundleReader.access$200();
                    }
                    byteBuffer = ICUBinary.getByteBufferFromInputStreamAndCloseStream(inputStream);
                }
                return new ICUResourceBundleReader(byteBuffer, readerCacheKey.baseName, readerCacheKey.localeID, classLoader, null);
            } catch (IOException iOException) {
                throw new ICUUncheckedIOException("Data file " + string + " is corrupt - " + iOException.getMessage(), iOException);
            }
        }

        @Override
        protected Object createInstance(Object object, Object object2) {
            return this.createInstance((ReaderCacheKey)object, (ClassLoader)object2);
        }

        ReaderCache(1 var1_1) {
            this();
        }
    }

    private static class ReaderCacheKey {
        final String baseName;
        final String localeID;

        ReaderCacheKey(String string, String string2) {
            this.baseName = string == null ? ICUResourceBundleReader.emptyString : string;
            this.localeID = string2 == null ? ICUResourceBundleReader.emptyString : string2;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!(object instanceof ReaderCacheKey)) {
                return true;
            }
            ReaderCacheKey readerCacheKey = (ReaderCacheKey)object;
            return this.baseName.equals(readerCacheKey.baseName) && this.localeID.equals(readerCacheKey.localeID);
        }

        public int hashCode() {
            return this.baseName.hashCode() ^ this.localeID.hashCode();
        }
    }

    private static final class IsAcceptable
    implements ICUBinary.Authenticate {
        private IsAcceptable() {
        }

        @Override
        public boolean isDataVersionAcceptable(byte[] byArray) {
            return byArray[0] == 1 && (byArray[1] & 0xFF) >= 1 || 2 <= byArray[0] && byArray[0] <= 3;
        }

        IsAcceptable(1 var1_1) {
            this();
        }
    }
}

