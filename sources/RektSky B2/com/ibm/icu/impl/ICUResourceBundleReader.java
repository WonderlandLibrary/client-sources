package com.ibm.icu.impl;

import java.nio.*;
import java.io.*;
import com.ibm.icu.util.*;
import java.lang.ref.*;

public final class ICUResourceBundleReader
{
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
    
    private ICUResourceBundleReader() {
    }
    
    private ICUResourceBundleReader(final ByteBuffer inBytes, final String baseName, final String localeID, final ClassLoader loader) throws IOException {
        this.init(inBytes);
        if (this.usesPoolBundle) {
            this.poolBundleReader = getReader(baseName, "pool", loader);
            if (this.poolBundleReader == null || !this.poolBundleReader.isPoolBundle) {
                throw new IllegalStateException("pool.res is not a pool bundle");
            }
            if (this.poolBundleReader.poolCheckSum != this.poolCheckSum) {
                throw new IllegalStateException("pool.res has a different checksum than this bundle");
            }
        }
    }
    
    static ICUResourceBundleReader getReader(final String baseName, final String localeID, final ClassLoader root) {
        final ReaderCacheKey info = new ReaderCacheKey(baseName, localeID);
        final ICUResourceBundleReader reader = ICUResourceBundleReader.CACHE.getInstance(info, root);
        if (reader == ICUResourceBundleReader.NULL_READER) {
            return null;
        }
        return reader;
    }
    
    private void init(final ByteBuffer inBytes) throws IOException {
        this.dataVersion = ICUBinary.readHeader(inBytes, 1382380354, ICUResourceBundleReader.IS_ACCEPTABLE);
        final int majorFormatVersion = inBytes.get(16);
        this.bytes = ICUBinary.sliceWithOrder(inBytes);
        final int dataLength = this.bytes.remaining();
        this.rootRes = this.bytes.getInt(0);
        final int indexes0 = this.getIndexesInt(0);
        final int indexLength = indexes0 & 0xFF;
        if (indexLength <= 4) {
            throw new ICUException("not enough indexes");
        }
        final int bundleTop;
        if (dataLength < 1 + indexLength << 2 || dataLength < (bundleTop = this.getIndexesInt(3)) << 2) {
            throw new ICUException("not enough bytes");
        }
        int maxOffset = bundleTop - 1;
        if (majorFormatVersion >= 3) {
            this.poolStringIndexLimit = indexes0 >>> 8;
        }
        if (indexLength > 5) {
            final int att = this.getIndexesInt(5);
            this.noFallback = ((att & 0x1) != 0x0);
            this.isPoolBundle = ((att & 0x2) != 0x0);
            this.usesPoolBundle = ((att & 0x4) != 0x0);
            this.poolStringIndexLimit |= (att & 0xF000) << 12;
            this.poolStringIndex16Limit = att >>> 16;
        }
        final int keysBottom = 1 + indexLength;
        final int keysTop = this.getIndexesInt(1);
        if (keysTop > keysBottom) {
            if (this.isPoolBundle) {
                this.keyBytes = new byte[keysTop - keysBottom << 2];
                this.bytes.position(keysBottom << 2);
            }
            else {
                this.localKeyLimit = keysTop << 2;
                this.keyBytes = new byte[this.localKeyLimit];
            }
            this.bytes.get(this.keyBytes);
        }
        if (indexLength > 6) {
            final int _16BitTop = this.getIndexesInt(6);
            if (_16BitTop > keysTop) {
                final int num16BitUnits = (_16BitTop - keysTop) * 2;
                this.bytes.position(keysTop << 2);
                (this.b16BitUnits = this.bytes.asCharBuffer()).limit(num16BitUnits);
                maxOffset |= num16BitUnits - 1;
            }
            else {
                this.b16BitUnits = ICUResourceBundleReader.EMPTY_16_BIT_UNITS;
            }
        }
        else {
            this.b16BitUnits = ICUResourceBundleReader.EMPTY_16_BIT_UNITS;
        }
        if (indexLength > 7) {
            this.poolCheckSum = this.getIndexesInt(7);
        }
        if (!this.isPoolBundle || this.b16BitUnits.length() > 1) {
            this.resourceCache = new ResourceCache(maxOffset);
        }
        this.bytes.position(0);
    }
    
    private int getIndexesInt(final int i) {
        return this.bytes.getInt(1 + i << 2);
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
    
    static int RES_GET_TYPE(final int res) {
        return res >>> 28;
    }
    
    private static int RES_GET_OFFSET(final int res) {
        return res & 0xFFFFFFF;
    }
    
    private int getResourceByteOffset(final int offset) {
        return offset << 2;
    }
    
    static int RES_GET_INT(final int res) {
        return res << 4 >> 4;
    }
    
    static int RES_GET_UINT(final int res) {
        return res & 0xFFFFFFF;
    }
    
    static boolean URES_IS_ARRAY(final int type) {
        return type == 8 || type == 9;
    }
    
    static boolean URES_IS_TABLE(final int type) {
        return type == 2 || type == 5 || type == 4;
    }
    
    private char[] getChars(int offset, final int count) {
        final char[] chars = new char[count];
        if (count <= 16) {
            for (int i = 0; i < count; ++i) {
                chars[i] = this.bytes.getChar(offset);
                offset += 2;
            }
        }
        else {
            final CharBuffer temp = this.bytes.asCharBuffer();
            temp.position(offset / 2);
            temp.get(chars);
        }
        return chars;
    }
    
    private int getInt(final int offset) {
        return this.bytes.getInt(offset);
    }
    
    private int[] getInts(int offset, final int count) {
        final int[] ints = new int[count];
        if (count <= 16) {
            for (int i = 0; i < count; ++i) {
                ints[i] = this.bytes.getInt(offset);
                offset += 4;
            }
        }
        else {
            final IntBuffer temp = this.bytes.asIntBuffer();
            temp.position(offset / 4);
            temp.get(ints);
        }
        return ints;
    }
    
    private char[] getTable16KeyOffsets(int offset) {
        final int length = this.b16BitUnits.charAt(offset++);
        if (length > 0) {
            final char[] result = new char[length];
            if (length <= 16) {
                for (int i = 0; i < length; ++i) {
                    result[i] = this.b16BitUnits.charAt(offset++);
                }
            }
            else {
                final CharBuffer temp = this.b16BitUnits.duplicate();
                temp.position(offset);
                temp.get(result);
            }
            return result;
        }
        return ICUResourceBundleReader.emptyChars;
    }
    
    private char[] getTableKeyOffsets(final int offset) {
        final int length = this.bytes.getChar(offset);
        if (length > 0) {
            return this.getChars(offset + 2, length);
        }
        return ICUResourceBundleReader.emptyChars;
    }
    
    private int[] getTable32KeyOffsets(final int offset) {
        final int length = this.getInt(offset);
        if (length > 0) {
            return this.getInts(offset + 4, length);
        }
        return ICUResourceBundleReader.emptyInts;
    }
    
    private static String makeKeyStringFromBytes(final byte[] keyBytes, int keyOffset) {
        final StringBuilder sb = new StringBuilder();
        byte b;
        while ((b = keyBytes[keyOffset]) != 0) {
            ++keyOffset;
            sb.append((char)b);
        }
        return sb.toString();
    }
    
    private String getKey16String(final int keyOffset) {
        if (keyOffset < this.localKeyLimit) {
            return makeKeyStringFromBytes(this.keyBytes, keyOffset);
        }
        return makeKeyStringFromBytes(this.poolBundleReader.keyBytes, keyOffset - this.localKeyLimit);
    }
    
    private String getKey32String(final int keyOffset) {
        if (keyOffset >= 0) {
            return makeKeyStringFromBytes(this.keyBytes, keyOffset);
        }
        return makeKeyStringFromBytes(this.poolBundleReader.keyBytes, keyOffset & Integer.MAX_VALUE);
    }
    
    private void setKeyFromKey16(final int keyOffset, final UResource.Key key) {
        if (keyOffset < this.localKeyLimit) {
            key.setBytes(this.keyBytes, keyOffset);
        }
        else {
            key.setBytes(this.poolBundleReader.keyBytes, keyOffset - this.localKeyLimit);
        }
    }
    
    private void setKeyFromKey32(final int keyOffset, final UResource.Key key) {
        if (keyOffset >= 0) {
            key.setBytes(this.keyBytes, keyOffset);
        }
        else {
            key.setBytes(this.poolBundleReader.keyBytes, keyOffset & Integer.MAX_VALUE);
        }
    }
    
    private int compareKeys(final CharSequence key, final char keyOffset) {
        if (keyOffset < this.localKeyLimit) {
            return ICUBinary.compareKeys(key, this.keyBytes, keyOffset);
        }
        return ICUBinary.compareKeys(key, this.poolBundleReader.keyBytes, keyOffset - this.localKeyLimit);
    }
    
    private int compareKeys32(final CharSequence key, final int keyOffset) {
        if (keyOffset >= 0) {
            return ICUBinary.compareKeys(key, this.keyBytes, keyOffset);
        }
        return ICUBinary.compareKeys(key, this.poolBundleReader.keyBytes, keyOffset & Integer.MAX_VALUE);
    }
    
    String getStringV2(final int res) {
        assert RES_GET_TYPE(res) == 6;
        int offset = RES_GET_OFFSET(res);
        assert offset != 0;
        final Object value = this.resourceCache.get(res);
        if (value != null) {
            return (String)value;
        }
        final int first = this.b16BitUnits.charAt(offset);
        String s;
        if ((first & 0xFFFFFC00) != 0xDC00) {
            if (first == 0) {
                return "";
            }
            final StringBuilder sb = new StringBuilder();
            sb.append((char)first);
            char c;
            while ((c = this.b16BitUnits.charAt(++offset)) != '\0') {
                sb.append(c);
            }
            s = sb.toString();
        }
        else {
            int length;
            if (first < 57327) {
                length = (first & 0x3FF);
                ++offset;
            }
            else if (first < 57343) {
                length = (first - 57327 << 16 | this.b16BitUnits.charAt(offset + 1));
                offset += 2;
            }
            else {
                length = (this.b16BitUnits.charAt(offset + 1) << 16 | this.b16BitUnits.charAt(offset + 2));
                offset += 3;
            }
            s = this.b16BitUnits.subSequence(offset, offset + length).toString();
        }
        return (String)this.resourceCache.putIfAbsent(res, s, s.length() * 2);
    }
    
    private String makeStringFromBytes(int offset, final int length) {
        if (length <= 16) {
            final StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; ++i) {
                sb.append(this.bytes.getChar(offset));
                offset += 2;
            }
            return sb.toString();
        }
        final CharSequence cs = this.bytes.asCharBuffer();
        offset /= 2;
        return cs.subSequence(offset, offset + length).toString();
    }
    
    String getString(final int res) {
        int offset = RES_GET_OFFSET(res);
        if (res != offset && RES_GET_TYPE(res) != 6) {
            return null;
        }
        if (offset == 0) {
            return "";
        }
        if (res != offset) {
            if (offset < this.poolStringIndexLimit) {
                return this.poolBundleReader.getStringV2(res);
            }
            return this.getStringV2(res - this.poolStringIndexLimit);
        }
        else {
            final Object value = this.resourceCache.get(res);
            if (value != null) {
                return (String)value;
            }
            offset = this.getResourceByteOffset(offset);
            final int length = this.getInt(offset);
            final String s = this.makeStringFromBytes(offset + 4, length);
            return (String)this.resourceCache.putIfAbsent(res, s, s.length() * 2);
        }
    }
    
    private boolean isNoInheritanceMarker(final int res) {
        int offset = RES_GET_OFFSET(res);
        if (offset != 0) {
            if (res == offset) {
                offset = this.getResourceByteOffset(offset);
                return this.getInt(offset) == 3 && this.bytes.getChar(offset + 4) == '\u2205' && this.bytes.getChar(offset + 6) == '\u2205' && this.bytes.getChar(offset + 8) == '\u2205';
            }
            if (RES_GET_TYPE(res) == 6) {
                if (offset < this.poolStringIndexLimit) {
                    return this.poolBundleReader.isStringV2NoInheritanceMarker(offset);
                }
                return this.isStringV2NoInheritanceMarker(offset - this.poolStringIndexLimit);
            }
        }
        return false;
    }
    
    private boolean isStringV2NoInheritanceMarker(final int offset) {
        final int first = this.b16BitUnits.charAt(offset);
        if (first == 8709) {
            return this.b16BitUnits.charAt(offset + 1) == '\u2205' && this.b16BitUnits.charAt(offset + 2) == '\u2205' && this.b16BitUnits.charAt(offset + 3) == '\0';
        }
        return first == 56323 && this.b16BitUnits.charAt(offset + 1) == '\u2205' && this.b16BitUnits.charAt(offset + 2) == '\u2205' && this.b16BitUnits.charAt(offset + 3) == '\u2205';
    }
    
    String getAlias(final int res) {
        int offset = RES_GET_OFFSET(res);
        if (RES_GET_TYPE(res) != 3) {
            return null;
        }
        if (offset == 0) {
            return "";
        }
        final Object value = this.resourceCache.get(res);
        if (value != null) {
            return (String)value;
        }
        offset = this.getResourceByteOffset(offset);
        final int length = this.getInt(offset);
        final String s = this.makeStringFromBytes(offset + 4, length);
        return (String)this.resourceCache.putIfAbsent(res, s, length * 2);
    }
    
    byte[] getBinary(final int res, byte[] ba) {
        int offset = RES_GET_OFFSET(res);
        if (RES_GET_TYPE(res) != 1) {
            return null;
        }
        if (offset == 0) {
            return ICUResourceBundleReader.emptyBytes;
        }
        offset = this.getResourceByteOffset(offset);
        final int length = this.getInt(offset);
        if (length == 0) {
            return ICUResourceBundleReader.emptyBytes;
        }
        if (ba == null || ba.length != length) {
            ba = new byte[length];
        }
        offset += 4;
        if (length <= 16) {
            for (int i = 0; i < length; ++i) {
                ba[i] = this.bytes.get(offset++);
            }
        }
        else {
            final ByteBuffer temp = this.bytes.duplicate();
            temp.position(offset);
            temp.get(ba);
        }
        return ba;
    }
    
    ByteBuffer getBinary(final int res) {
        int offset = RES_GET_OFFSET(res);
        if (RES_GET_TYPE(res) != 1) {
            return null;
        }
        if (offset == 0) {
            return ICUResourceBundleReader.emptyByteBuffer.duplicate();
        }
        offset = this.getResourceByteOffset(offset);
        final int length = this.getInt(offset);
        if (length == 0) {
            return ICUResourceBundleReader.emptyByteBuffer.duplicate();
        }
        offset += 4;
        ByteBuffer result = this.bytes.duplicate();
        result.position(offset).limit(offset + length);
        result = ICUBinary.sliceWithOrder(result);
        if (!result.isReadOnly()) {
            result = result.asReadOnlyBuffer();
        }
        return result;
    }
    
    int[] getIntVector(final int res) {
        int offset = RES_GET_OFFSET(res);
        if (RES_GET_TYPE(res) != 14) {
            return null;
        }
        if (offset == 0) {
            return ICUResourceBundleReader.emptyInts;
        }
        offset = this.getResourceByteOffset(offset);
        final int length = this.getInt(offset);
        return this.getInts(offset + 4, length);
    }
    
    Array getArray(final int res) {
        final int type = RES_GET_TYPE(res);
        if (!URES_IS_ARRAY(type)) {
            return null;
        }
        final int offset = RES_GET_OFFSET(res);
        if (offset == 0) {
            return ICUResourceBundleReader.EMPTY_ARRAY;
        }
        final Object value = this.resourceCache.get(res);
        if (value != null) {
            return (Array)value;
        }
        final Array array = (type == 8) ? new Array32(this, offset) : new Array16(this, offset);
        return (Array)this.resourceCache.putIfAbsent(res, array, 0);
    }
    
    Table getTable(final int res) {
        final int type = RES_GET_TYPE(res);
        if (!URES_IS_TABLE(type)) {
            return null;
        }
        final int offset = RES_GET_OFFSET(res);
        if (offset == 0) {
            return ICUResourceBundleReader.EMPTY_TABLE;
        }
        final Object value = this.resourceCache.get(res);
        if (value != null) {
            return (Table)value;
        }
        Table table;
        int size;
        if (type == 2) {
            table = new Table1632(this, offset);
            size = table.getSize() * 2;
        }
        else if (type == 5) {
            table = new Table16(this, offset);
            size = table.getSize() * 2;
        }
        else {
            table = new Table32(this, offset);
            size = table.getSize() * 4;
        }
        return (Table)this.resourceCache.putIfAbsent(res, table, size);
    }
    
    public static String getFullName(String baseName, String localeName) {
        if (baseName == null || baseName.length() == 0) {
            if (localeName.length() == 0) {
                return localeName = ULocale.getDefault().toString();
            }
            return localeName + ".res";
        }
        else if (baseName.indexOf(46) == -1) {
            if (baseName.charAt(baseName.length() - 1) != '/') {
                return baseName + "/" + localeName + ".res";
            }
            return baseName + localeName + ".res";
        }
        else {
            baseName = baseName.replace('.', '/');
            if (localeName.length() == 0) {
                return baseName + ".res";
            }
            return baseName + "_" + localeName + ".res";
        }
    }
    
    static {
        IS_ACCEPTABLE = new IsAcceptable();
        EMPTY_16_BIT_UNITS = CharBuffer.wrap("\u0000");
        ICUResourceBundleReader.CACHE = new ReaderCache();
        NULL_READER = new ICUResourceBundleReader();
        emptyBytes = new byte[0];
        emptyByteBuffer = ByteBuffer.allocate(0).asReadOnlyBuffer();
        emptyChars = new char[0];
        emptyInts = new int[0];
        EMPTY_ARRAY = new Array();
        EMPTY_TABLE = new Table();
        ICUResourceBundleReader.PUBLIC_TYPES = new int[] { 0, 1, 2, 3, 2, 2, 0, 7, 8, 8, -1, -1, -1, -1, 14, -1 };
    }
    
    private static final class IsAcceptable implements ICUBinary.Authenticate
    {
        @Override
        public boolean isDataVersionAcceptable(final byte[] formatVersion) {
            return (formatVersion[0] == 1 && (formatVersion[1] & 0xFF) >= 1) || (2 <= formatVersion[0] && formatVersion[0] <= 3);
        }
    }
    
    private static class ReaderCacheKey
    {
        final String baseName;
        final String localeID;
        
        ReaderCacheKey(final String baseName, final String localeID) {
            this.baseName = ((baseName == null) ? "" : baseName);
            this.localeID = ((localeID == null) ? "" : localeID);
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ReaderCacheKey)) {
                return false;
            }
            final ReaderCacheKey info = (ReaderCacheKey)obj;
            return this.baseName.equals(info.baseName) && this.localeID.equals(info.localeID);
        }
        
        @Override
        public int hashCode() {
            return this.baseName.hashCode() ^ this.localeID.hashCode();
        }
    }
    
    private static class ReaderCache extends SoftCache<ReaderCacheKey, ICUResourceBundleReader, ClassLoader>
    {
        @Override
        protected ICUResourceBundleReader createInstance(final ReaderCacheKey key, final ClassLoader loader) {
            final String fullName = ICUResourceBundleReader.getFullName(key.baseName, key.localeID);
            try {
                ByteBuffer inBytes;
                if (key.baseName != null && key.baseName.startsWith("com/ibm/icu/impl/data/icudt62b")) {
                    final String itemPath = fullName.substring("com/ibm/icu/impl/data/icudt62b".length() + 1);
                    inBytes = ICUBinary.getData(loader, fullName, itemPath);
                    if (inBytes == null) {
                        return ICUResourceBundleReader.NULL_READER;
                    }
                }
                else {
                    final InputStream stream = ICUData.getStream(loader, fullName);
                    if (stream == null) {
                        return ICUResourceBundleReader.NULL_READER;
                    }
                    inBytes = ICUBinary.getByteBufferFromInputStreamAndCloseStream(stream);
                }
                return new ICUResourceBundleReader(inBytes, key.baseName, key.localeID, loader, null);
            }
            catch (IOException ex) {
                throw new ICUUncheckedIOException("Data file " + fullName + " is corrupt - " + ex.getMessage(), ex);
            }
        }
    }
    
    static class ReaderValue extends UResource.Value
    {
        ICUResourceBundleReader reader;
        int res;
        
        @Override
        public int getType() {
            return ICUResourceBundleReader.PUBLIC_TYPES[ICUResourceBundleReader.RES_GET_TYPE(this.res)];
        }
        
        @Override
        public String getString() {
            final String s = this.reader.getString(this.res);
            if (s == null) {
                throw new UResourceTypeMismatchException("");
            }
            return s;
        }
        
        @Override
        public String getAliasString() {
            final String s = this.reader.getAlias(this.res);
            if (s == null) {
                throw new UResourceTypeMismatchException("");
            }
            return s;
        }
        
        @Override
        public int getInt() {
            if (ICUResourceBundleReader.RES_GET_TYPE(this.res) != 7) {
                throw new UResourceTypeMismatchException("");
            }
            return ICUResourceBundleReader.RES_GET_INT(this.res);
        }
        
        @Override
        public int getUInt() {
            if (ICUResourceBundleReader.RES_GET_TYPE(this.res) != 7) {
                throw new UResourceTypeMismatchException("");
            }
            return ICUResourceBundleReader.RES_GET_UINT(this.res);
        }
        
        @Override
        public int[] getIntVector() {
            final int[] iv = this.reader.getIntVector(this.res);
            if (iv == null) {
                throw new UResourceTypeMismatchException("");
            }
            return iv;
        }
        
        @Override
        public ByteBuffer getBinary() {
            final ByteBuffer bb = this.reader.getBinary(this.res);
            if (bb == null) {
                throw new UResourceTypeMismatchException("");
            }
            return bb;
        }
        
        @Override
        public UResource.Array getArray() {
            final ICUResourceBundleReader.Array array = this.reader.getArray(this.res);
            if (array == null) {
                throw new UResourceTypeMismatchException("");
            }
            return array;
        }
        
        @Override
        public UResource.Table getTable() {
            final ICUResourceBundleReader.Table table = this.reader.getTable(this.res);
            if (table == null) {
                throw new UResourceTypeMismatchException("");
            }
            return table;
        }
        
        @Override
        public boolean isNoInheritanceMarker() {
            return this.reader.isNoInheritanceMarker(this.res);
        }
        
        @Override
        public String[] getStringArray() {
            final ICUResourceBundleReader.Array array = this.reader.getArray(this.res);
            if (array == null) {
                throw new UResourceTypeMismatchException("");
            }
            return this.getStringArray(array);
        }
        
        @Override
        public String[] getStringArrayOrStringAsArray() {
            final ICUResourceBundleReader.Array array = this.reader.getArray(this.res);
            if (array != null) {
                return this.getStringArray(array);
            }
            final String s = this.reader.getString(this.res);
            if (s != null) {
                return new String[] { s };
            }
            throw new UResourceTypeMismatchException("");
        }
        
        @Override
        public String getStringOrFirstOfArray() {
            String s = this.reader.getString(this.res);
            if (s != null) {
                return s;
            }
            final ICUResourceBundleReader.Array array = this.reader.getArray(this.res);
            if (array != null && array.size > 0) {
                final int r = array.getContainerResource(this.reader, 0);
                s = this.reader.getString(r);
                if (s != null) {
                    return s;
                }
            }
            throw new UResourceTypeMismatchException("");
        }
        
        private String[] getStringArray(final ICUResourceBundleReader.Array array) {
            final String[] result = new String[array.size];
            for (int i = 0; i < array.size; ++i) {
                final int r = array.getContainerResource(this.reader, i);
                final String s = this.reader.getString(r);
                if (s == null) {
                    throw new UResourceTypeMismatchException("");
                }
                result[i] = s;
            }
            return result;
        }
    }
    
    static class Container
    {
        protected int size;
        protected int itemsOffset;
        
        public final int getSize() {
            return this.size;
        }
        
        int getContainerResource(final ICUResourceBundleReader reader, final int index) {
            return -1;
        }
        
        protected int getContainer16Resource(final ICUResourceBundleReader reader, final int index) {
            if (index < 0 || this.size <= index) {
                return -1;
            }
            int res16 = reader.b16BitUnits.charAt(this.itemsOffset + index);
            if (res16 >= reader.poolStringIndex16Limit) {
                res16 = res16 - reader.poolStringIndex16Limit + reader.poolStringIndexLimit;
            }
            return 0x60000000 | res16;
        }
        
        protected int getContainer32Resource(final ICUResourceBundleReader reader, final int index) {
            if (index < 0 || this.size <= index) {
                return -1;
            }
            return reader.getInt(this.itemsOffset + 4 * index);
        }
        
        int getResource(final ICUResourceBundleReader reader, final String resKey) {
            return this.getContainerResource(reader, Integer.parseInt(resKey));
        }
    }
    
    static class Array extends Container implements UResource.Array
    {
        @Override
        public boolean getValue(final int i, final UResource.Value value) {
            if (0 <= i && i < this.size) {
                final ReaderValue readerValue = (ReaderValue)value;
                readerValue.res = this.getContainerResource(readerValue.reader, i);
                return true;
            }
            return false;
        }
    }
    
    private static final class Array32 extends Array
    {
        @Override
        int getContainerResource(final ICUResourceBundleReader reader, final int index) {
            return this.getContainer32Resource(reader, index);
        }
        
        Array32(final ICUResourceBundleReader reader, int offset) {
            offset = reader.getResourceByteOffset(offset);
            this.size = reader.getInt(offset);
            this.itemsOffset = offset + 4;
        }
    }
    
    private static final class Array16 extends Array
    {
        @Override
        int getContainerResource(final ICUResourceBundleReader reader, final int index) {
            return this.getContainer16Resource(reader, index);
        }
        
        Array16(final ICUResourceBundleReader reader, final int offset) {
            this.size = reader.b16BitUnits.charAt(offset);
            this.itemsOffset = offset + 1;
        }
    }
    
    static class Table extends Container implements UResource.Table
    {
        protected char[] keyOffsets;
        protected int[] key32Offsets;
        private static final int URESDATA_ITEM_NOT_FOUND = -1;
        
        String getKey(final ICUResourceBundleReader reader, final int index) {
            if (index < 0 || this.size <= index) {
                return null;
            }
            return (this.keyOffsets != null) ? reader.getKey16String(this.keyOffsets[index]) : reader.getKey32String(this.key32Offsets[index]);
        }
        
        int findTableItem(final ICUResourceBundleReader reader, final CharSequence key) {
            int start = 0;
            int limit = this.size;
            while (start < limit) {
                final int mid = start + limit >>> 1;
                int result;
                if (this.keyOffsets != null) {
                    result = reader.compareKeys(key, this.keyOffsets[mid]);
                }
                else {
                    result = reader.compareKeys32(key, this.key32Offsets[mid]);
                }
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
            return -1;
        }
        
        @Override
        int getResource(final ICUResourceBundleReader reader, final String resKey) {
            return this.getContainerResource(reader, this.findTableItem(reader, resKey));
        }
        
        @Override
        public boolean getKeyAndValue(final int i, final UResource.Key key, final UResource.Value value) {
            if (0 <= i && i < this.size) {
                final ReaderValue readerValue = (ReaderValue)value;
                if (this.keyOffsets != null) {
                    readerValue.reader.setKeyFromKey16(this.keyOffsets[i], key);
                }
                else {
                    readerValue.reader.setKeyFromKey32(this.key32Offsets[i], key);
                }
                readerValue.res = this.getContainerResource(readerValue.reader, i);
                return true;
            }
            return false;
        }
    }
    
    private static final class Table1632 extends Table
    {
        @Override
        int getContainerResource(final ICUResourceBundleReader reader, final int index) {
            return this.getContainer32Resource(reader, index);
        }
        
        Table1632(final ICUResourceBundleReader reader, int offset) {
            offset = reader.getResourceByteOffset(offset);
            this.keyOffsets = reader.getTableKeyOffsets(offset);
            this.size = this.keyOffsets.length;
            this.itemsOffset = offset + 2 * (this.size + 2 & 0xFFFFFFFE);
        }
    }
    
    private static final class Table16 extends Table
    {
        @Override
        int getContainerResource(final ICUResourceBundleReader reader, final int index) {
            return this.getContainer16Resource(reader, index);
        }
        
        Table16(final ICUResourceBundleReader reader, final int offset) {
            this.keyOffsets = reader.getTable16KeyOffsets(offset);
            this.size = this.keyOffsets.length;
            this.itemsOffset = offset + 1 + this.size;
        }
    }
    
    private static final class Table32 extends Table
    {
        @Override
        int getContainerResource(final ICUResourceBundleReader reader, final int index) {
            return this.getContainer32Resource(reader, index);
        }
        
        Table32(final ICUResourceBundleReader reader, int offset) {
            offset = reader.getResourceByteOffset(offset);
            this.key32Offsets = reader.getTable32KeyOffsets(offset);
            this.size = this.key32Offsets.length;
            this.itemsOffset = offset + 4 * (1 + this.size);
        }
    }
    
    private static final class ResourceCache
    {
        private static final int SIMPLE_LENGTH = 32;
        private static final int ROOT_BITS = 7;
        private static final int NEXT_BITS = 6;
        private int[] keys;
        private Object[] values;
        private int length;
        private int maxOffsetBits;
        private int levelBitsList;
        private Level rootLevel;
        
        private static boolean storeDirectly(final int size) {
            return size < 24 || CacheValue.futureInstancesWillBeStrong();
        }
        
        private static final Object putIfCleared(final Object[] values, final int index, final Object item, final int size) {
            Object value = values[index];
            if (!(value instanceof SoftReference)) {
                return value;
            }
            assert size >= 24;
            value = ((SoftReference)value).get();
            if (value != null) {
                return value;
            }
            values[index] = (CacheValue.futureInstancesWillBeStrong() ? item : new SoftReference(item));
            return item;
        }
        
        ResourceCache(int maxOffset) {
            this.keys = new int[32];
            this.values = new Object[32];
            assert maxOffset != 0;
            this.maxOffsetBits = 28;
            while (maxOffset <= 134217727) {
                maxOffset <<= 1;
                --this.maxOffsetBits;
            }
            int keyBits = this.maxOffsetBits + 2;
            if (keyBits <= 7) {
                this.levelBitsList = keyBits;
            }
            else if (keyBits < 10) {
                this.levelBitsList = (0x30 | keyBits - 3);
            }
            else {
                this.levelBitsList = 7;
                int shift;
                for (keyBits -= 7, shift = 4; keyBits > 6; keyBits -= 6, shift += 4) {
                    if (keyBits < 9) {
                        this.levelBitsList |= (0x30 | keyBits - 3) << shift;
                        return;
                    }
                    this.levelBitsList |= 6 << shift;
                }
                this.levelBitsList |= keyBits << shift;
            }
        }
        
        private int makeKey(final int res) {
            final int type = ICUResourceBundleReader.RES_GET_TYPE(res);
            final int miniType = (type == 6) ? 1 : ((type == 5) ? 3 : ((type == 9) ? 2 : 0));
            return RES_GET_OFFSET(res) | miniType << this.maxOffsetBits;
        }
        
        private int findSimple(final int key) {
            int start = 0;
            int limit = this.length;
            while (limit - start > 8) {
                final int mid = (start + limit) / 2;
                if (key < this.keys[mid]) {
                    limit = mid;
                }
                else {
                    start = mid;
                }
            }
            while (start < limit) {
                final int k = this.keys[start];
                if (key < k) {
                    return ~start;
                }
                if (key == k) {
                    return start;
                }
                ++start;
            }
            return ~start;
        }
        
        synchronized Object get(final int res) {
            assert RES_GET_OFFSET(res) != 0;
            Object value;
            if (this.length >= 0) {
                final int index = this.findSimple(res);
                if (index < 0) {
                    return null;
                }
                value = this.values[index];
            }
            else {
                value = this.rootLevel.get(this.makeKey(res));
                if (value == null) {
                    return null;
                }
            }
            if (value instanceof SoftReference) {
                value = ((SoftReference)value).get();
            }
            return value;
        }
        
        synchronized Object putIfAbsent(final int res, final Object item, final int size) {
            if (this.length >= 0) {
                int index = this.findSimple(res);
                if (index >= 0) {
                    return putIfCleared(this.values, index, item, size);
                }
                if (this.length < 32) {
                    index ^= -1;
                    if (index < this.length) {
                        System.arraycopy(this.keys, index, this.keys, index + 1, this.length - index);
                        System.arraycopy(this.values, index, this.values, index + 1, this.length - index);
                    }
                    ++this.length;
                    this.keys[index] = res;
                    this.values[index] = (storeDirectly(size) ? item : new SoftReference(item));
                    return item;
                }
                this.rootLevel = new Level(this.levelBitsList, 0);
                for (int i = 0; i < 32; ++i) {
                    this.rootLevel.putIfAbsent(this.makeKey(this.keys[i]), this.values[i], 0);
                }
                this.keys = null;
                this.values = null;
                this.length = -1;
            }
            return this.rootLevel.putIfAbsent(this.makeKey(res), item, size);
        }
        
        private static final class Level
        {
            int levelBitsList;
            int shift;
            int mask;
            int[] keys;
            Object[] values;
            
            Level(final int levelBitsList, final int shift) {
                this.levelBitsList = levelBitsList;
                this.shift = shift;
                final int bits = levelBitsList & 0xF;
                assert bits != 0;
                final int length = 1 << bits;
                this.mask = length - 1;
                this.keys = new int[length];
                this.values = new Object[length];
            }
            
            Object get(final int key) {
                final int index = key >> this.shift & this.mask;
                final int k = this.keys[index];
                if (k == key) {
                    return this.values[index];
                }
                if (k == 0) {
                    final Level level = (Level)this.values[index];
                    if (level != null) {
                        return level.get(key);
                    }
                }
                return null;
            }
            
            Object putIfAbsent(final int key, final Object item, final int size) {
                final int index = key >> this.shift & this.mask;
                final int k = this.keys[index];
                if (k == key) {
                    return putIfCleared(this.values, index, item, size);
                }
                if (k != 0) {
                    final Level level = new Level(this.levelBitsList >> 4, this.shift + (this.levelBitsList & 0xF));
                    final int i = k >> level.shift & level.mask;
                    level.keys[i] = k;
                    level.values[i] = this.values[index];
                    this.keys[index] = 0;
                    this.values[index] = level;
                    return level.putIfAbsent(key, item, size);
                }
                final Level level = (Level)this.values[index];
                if (level != null) {
                    return level.putIfAbsent(key, item, size);
                }
                this.keys[index] = key;
                this.values[index] = (storeDirectly(size) ? item : new SoftReference(item));
                return item;
            }
        }
    }
}
