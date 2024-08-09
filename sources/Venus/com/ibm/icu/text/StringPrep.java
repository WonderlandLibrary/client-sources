/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.CharTrie;
import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.StringPrepDataReader;
import com.ibm.icu.impl.UBiDiProps;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.Normalizer;
import com.ibm.icu.text.StringPrepParseException;
import com.ibm.icu.text.UCharacterIterator;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.util.ICUUncheckedIOException;
import com.ibm.icu.util.VersionInfo;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

public final class StringPrep {
    public static final int DEFAULT = 0;
    public static final int ALLOW_UNASSIGNED = 1;
    public static final int RFC3491_NAMEPREP = 0;
    public static final int RFC3530_NFS4_CS_PREP = 1;
    public static final int RFC3530_NFS4_CS_PREP_CI = 2;
    public static final int RFC3530_NFS4_CIS_PREP = 3;
    public static final int RFC3530_NFS4_MIXED_PREP_PREFIX = 4;
    public static final int RFC3530_NFS4_MIXED_PREP_SUFFIX = 5;
    public static final int RFC3722_ISCSI = 6;
    public static final int RFC3920_NODEPREP = 7;
    public static final int RFC3920_RESOURCEPREP = 8;
    public static final int RFC4011_MIB = 9;
    public static final int RFC4013_SASLPREP = 10;
    public static final int RFC4505_TRACE = 11;
    public static final int RFC4518_LDAP = 12;
    public static final int RFC4518_LDAP_CI = 13;
    private static final int MAX_PROFILE = 13;
    private static final String[] PROFILE_NAMES = new String[]{"rfc3491", "rfc3530cs", "rfc3530csci", "rfc3491", "rfc3530mixp", "rfc3491", "rfc3722", "rfc3920node", "rfc3920res", "rfc4011", "rfc4013", "rfc4505", "rfc4518", "rfc4518ci"};
    private static final WeakReference<StringPrep>[] CACHE = new WeakReference[14];
    private static final int UNASSIGNED = 0;
    private static final int MAP = 1;
    private static final int PROHIBITED = 2;
    private static final int DELETE = 3;
    private static final int TYPE_LIMIT = 4;
    private static final int NORMALIZATION_ON = 1;
    private static final int CHECK_BIDI_ON = 2;
    private static final int TYPE_THRESHOLD = 65520;
    private static final int MAX_INDEX_VALUE = 16319;
    private static final int INDEX_MAPPING_DATA_SIZE = 1;
    private static final int NORM_CORRECTNS_LAST_UNI_VERSION = 2;
    private static final int ONE_UCHAR_MAPPING_INDEX_START = 3;
    private static final int TWO_UCHARS_MAPPING_INDEX_START = 4;
    private static final int THREE_UCHARS_MAPPING_INDEX_START = 5;
    private static final int FOUR_UCHARS_MAPPING_INDEX_START = 6;
    private static final int OPTIONS = 7;
    private static final int INDEX_TOP = 16;
    private CharTrie sprepTrie;
    private int[] indexes;
    private char[] mappingData;
    private VersionInfo sprepUniVer;
    private VersionInfo normCorrVer;
    private boolean doNFKC;
    private boolean checkBiDi;
    private UBiDiProps bdp;

    private char getCodePointValue(int n) {
        return this.sprepTrie.getCodePointValue(n);
    }

    private static VersionInfo getVersionInfo(int n) {
        int n2 = n & 0xFF;
        int n3 = n >> 8 & 0xFF;
        int n4 = n >> 16 & 0xFF;
        int n5 = n >> 24 & 0xFF;
        return VersionInfo.getInstance(n5, n4, n3, n2);
    }

    private static VersionInfo getVersionInfo(byte[] byArray) {
        if (byArray.length != 4) {
            return null;
        }
        return VersionInfo.getInstance(byArray[0], byArray[1], byArray[2], byArray[3]);
    }

    public StringPrep(InputStream inputStream) throws IOException {
        this(ICUBinary.getByteBufferFromInputStreamAndCloseStream(inputStream));
    }

    private StringPrep(ByteBuffer byteBuffer) throws IOException {
        StringPrepDataReader stringPrepDataReader = new StringPrepDataReader(byteBuffer);
        this.indexes = stringPrepDataReader.readIndexes(16);
        this.sprepTrie = new CharTrie(byteBuffer, null);
        this.mappingData = stringPrepDataReader.read(this.indexes[1] / 2);
        this.doNFKC = (this.indexes[7] & 1) > 0;
        this.checkBiDi = (this.indexes[7] & 2) > 0;
        this.sprepUniVer = StringPrep.getVersionInfo(stringPrepDataReader.getUnicodeVersion());
        this.normCorrVer = StringPrep.getVersionInfo(this.indexes[2]);
        VersionInfo versionInfo = UCharacter.getUnicodeVersion();
        if (versionInfo.compareTo(this.sprepUniVer) < 0 && versionInfo.compareTo(this.normCorrVer) < 0 && (this.indexes[7] & 1) > 0) {
            throw new IOException("Normalization Correction version not supported");
        }
        if (this.checkBiDi) {
            this.bdp = UBiDiProps.INSTANCE;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static StringPrep getInstance(int n) {
        if (n < 0 || n > 13) {
            throw new IllegalArgumentException("Bad profile type");
        }
        StringPrep stringPrep = null;
        WeakReference<StringPrep>[] weakReferenceArray = CACHE;
        synchronized (CACHE) {
            WeakReference<StringPrep> weakReference = CACHE[n];
            if (weakReference != null) {
                stringPrep = (StringPrep)weakReference.get();
            }
            if (stringPrep == null) {
                ByteBuffer byteBuffer = ICUBinary.getRequiredData(PROFILE_NAMES[n] + ".spp");
                if (byteBuffer != null) {
                    try {
                        stringPrep = new StringPrep(byteBuffer);
                    } catch (IOException iOException) {
                        throw new ICUUncheckedIOException(iOException);
                    }
                }
                if (stringPrep != null) {
                    StringPrep.CACHE[n] = new WeakReference<StringPrep>(stringPrep);
                }
            }
            // ** MonitorExit[var2_2] (shouldn't be in output)
            return stringPrep;
        }
    }

    private static final void getValues(char c, Values values2) {
        values2.reset();
        if (c == '\u0000') {
            values2.type = 4;
        } else if (c >= '\ufff0') {
            values2.type = c - 65520;
        } else {
            values2.type = 1;
            if ((c & 2) > 0) {
                values2.isIndex = true;
                values2.value = c >> 2;
            } else {
                values2.isIndex = false;
                values2.value = c << 16 >> 16;
                values2.value >>= 2;
            }
            if (c >> 2 == 16319) {
                values2.type = 3;
                values2.isIndex = false;
                values2.value = 0;
            }
        }
    }

    private StringBuffer map(UCharacterIterator uCharacterIterator, int n) throws StringPrepParseException {
        boolean bl;
        Values values2 = new Values(null);
        char c = '\u0000';
        int n2 = -1;
        StringBuffer stringBuffer = new StringBuffer();
        boolean bl2 = bl = (n & 1) > 0;
        while ((n2 = uCharacterIterator.nextCodePoint()) != -1) {
            c = this.getCodePointValue(n2);
            StringPrep.getValues(c, values2);
            if (values2.type == 0 && !bl) {
                throw new StringPrepParseException("An unassigned code point was found in the input", 3, uCharacterIterator.getText(), uCharacterIterator.getIndex());
            }
            if (values2.type == 1) {
                if (values2.isIndex) {
                    int n3 = values2.value;
                    int n4 = n3 >= this.indexes[3] && n3 < this.indexes[4] ? 1 : (n3 >= this.indexes[4] && n3 < this.indexes[5] ? 2 : (n3 >= this.indexes[5] && n3 < this.indexes[6] ? 3 : this.mappingData[n3++]));
                    stringBuffer.append(this.mappingData, n3, n4);
                    continue;
                }
                n2 -= values2.value;
            } else if (values2.type == 3) continue;
            UTF16.append(stringBuffer, n2);
        }
        return stringBuffer;
    }

    private StringBuffer normalize(StringBuffer stringBuffer) {
        return new StringBuffer(Normalizer.normalize(stringBuffer.toString(), Normalizer.NFKC, 32));
    }

    public StringBuffer prepare(UCharacterIterator uCharacterIterator, int n) throws StringPrepParseException {
        int n2;
        StringBuffer stringBuffer;
        StringBuffer stringBuffer2 = stringBuffer = this.map(uCharacterIterator, n);
        if (this.doNFKC) {
            stringBuffer2 = this.normalize(stringBuffer);
        }
        UCharacterIterator uCharacterIterator2 = UCharacterIterator.getInstance(stringBuffer2);
        Values values2 = new Values(null);
        int n3 = 23;
        int n4 = 23;
        int n5 = -1;
        int n6 = -1;
        boolean bl = false;
        boolean bl2 = false;
        while ((n2 = uCharacterIterator2.nextCodePoint()) != -1) {
            char c = this.getCodePointValue(n2);
            StringPrep.getValues(c, values2);
            if (values2.type == 2) {
                throw new StringPrepParseException("A prohibited code point was found in the input", 2, uCharacterIterator2.getText(), values2.value);
            }
            if (!this.checkBiDi) continue;
            n3 = this.bdp.getClass(n2);
            if (n4 == 23) {
                n4 = n3;
            }
            if (n3 == 0) {
                bl2 = true;
                n6 = uCharacterIterator2.getIndex() - 1;
            }
            if (n3 != 1 && n3 != 13) continue;
            bl = true;
            n5 = uCharacterIterator2.getIndex() - 1;
        }
        if (this.checkBiDi) {
            if (bl2 && bl) {
                throw new StringPrepParseException("The input does not conform to the rules for BiDi code points.", 4, uCharacterIterator2.getText(), n5 > n6 ? n5 : n6);
            }
            if (bl && (n4 != 1 && n4 != 13 || n3 != 1 && n3 != 13)) {
                throw new StringPrepParseException("The input does not conform to the rules for BiDi code points.", 4, uCharacterIterator2.getText(), n5 > n6 ? n5 : n6);
            }
        }
        return stringBuffer2;
    }

    public String prepare(String string, int n) throws StringPrepParseException {
        StringBuffer stringBuffer = this.prepare(UCharacterIterator.getInstance(string), n);
        return stringBuffer.toString();
    }

    private static final class Values {
        boolean isIndex;
        int value;
        int type;

        private Values() {
        }

        public void reset() {
            this.isIndex = false;
            this.value = 0;
            this.type = -1;
        }

        Values(1 var1_1) {
            this();
        }
    }
}

