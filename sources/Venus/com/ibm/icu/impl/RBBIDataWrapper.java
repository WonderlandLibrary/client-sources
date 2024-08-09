/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.Trie2;
import com.ibm.icu.text.RuleBasedBreakIterator;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class RBBIDataWrapper {
    public RBBIDataHeader fHeader;
    public RBBIStateTable fFTable;
    public RBBIStateTable fRTable;
    public Trie2 fTrie;
    public String fRuleSource;
    public int[] fStatusTable;
    public static final int DATA_FORMAT = 1114794784;
    public static final int FORMAT_VERSION = 0x5000000;
    private static final IsAcceptable IS_ACCEPTABLE = new IsAcceptable(null);
    public static final int DH_SIZE = 20;
    public static final int DH_MAGIC = 0;
    public static final int DH_FORMATVERSION = 1;
    public static final int DH_LENGTH = 2;
    public static final int DH_CATCOUNT = 3;
    public static final int DH_FTABLE = 4;
    public static final int DH_FTABLELEN = 5;
    public static final int DH_RTABLE = 6;
    public static final int DH_RTABLELEN = 7;
    public static final int DH_TRIE = 8;
    public static final int DH_TRIELEN = 9;
    public static final int DH_RULESOURCE = 10;
    public static final int DH_RULESOURCELEN = 11;
    public static final int DH_STATUSTABLE = 12;
    public static final int DH_STATUSTABLELEN = 13;
    public static final int ACCEPTING = 0;
    public static final int LOOKAHEAD = 1;
    public static final int TAGIDX = 2;
    public static final int RESERVED = 3;
    public static final int NEXTSTATES = 4;
    public static final int RBBI_LOOKAHEAD_HARD_BREAK = 1;
    public static final int RBBI_BOF_REQUIRED = 2;

    public static boolean equals(RBBIStateTable rBBIStateTable, RBBIStateTable rBBIStateTable2) {
        if (rBBIStateTable == rBBIStateTable2) {
            return false;
        }
        if (rBBIStateTable == null || rBBIStateTable2 == null) {
            return true;
        }
        return rBBIStateTable.equals(rBBIStateTable2);
    }

    public int getRowIndex(int n) {
        return n * (this.fHeader.fCatCount + 4);
    }

    RBBIDataWrapper() {
    }

    public static RBBIDataWrapper get(ByteBuffer byteBuffer) throws IOException {
        RBBIDataWrapper rBBIDataWrapper = new RBBIDataWrapper();
        ICUBinary.readHeader(byteBuffer, 1114794784, IS_ACCEPTABLE);
        rBBIDataWrapper.fHeader = new RBBIDataHeader();
        rBBIDataWrapper.fHeader.fMagic = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fFormatVersion[0] = byteBuffer.get();
        rBBIDataWrapper.fHeader.fFormatVersion[1] = byteBuffer.get();
        rBBIDataWrapper.fHeader.fFormatVersion[2] = byteBuffer.get();
        rBBIDataWrapper.fHeader.fFormatVersion[3] = byteBuffer.get();
        rBBIDataWrapper.fHeader.fLength = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fCatCount = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fFTable = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fFTableLen = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fRTable = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fRTableLen = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fTrie = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fTrieLen = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fRuleSource = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fRuleSourceLen = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fStatusTable = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fStatusTableLen = byteBuffer.getInt();
        ICUBinary.skipBytes(byteBuffer, 24);
        if (rBBIDataWrapper.fHeader.fMagic != 45472 || !IS_ACCEPTABLE.isDataVersionAcceptable(rBBIDataWrapper.fHeader.fFormatVersion)) {
            throw new IOException("Break Iterator Rule Data Magic Number Incorrect, or unsupported data version.");
        }
        int n = 80;
        if (rBBIDataWrapper.fHeader.fFTable < n || rBBIDataWrapper.fHeader.fFTable > rBBIDataWrapper.fHeader.fLength) {
            throw new IOException("Break iterator Rule data corrupt");
        }
        ICUBinary.skipBytes(byteBuffer, rBBIDataWrapper.fHeader.fFTable - n);
        n = rBBIDataWrapper.fHeader.fFTable;
        rBBIDataWrapper.fFTable = RBBIStateTable.get(byteBuffer, rBBIDataWrapper.fHeader.fFTableLen);
        ICUBinary.skipBytes(byteBuffer, rBBIDataWrapper.fHeader.fRTable - (n += rBBIDataWrapper.fHeader.fFTableLen));
        n = rBBIDataWrapper.fHeader.fRTable;
        rBBIDataWrapper.fRTable = RBBIStateTable.get(byteBuffer, rBBIDataWrapper.fHeader.fRTableLen);
        ICUBinary.skipBytes(byteBuffer, rBBIDataWrapper.fHeader.fTrie - (n += rBBIDataWrapper.fHeader.fRTableLen));
        n = rBBIDataWrapper.fHeader.fTrie;
        byteBuffer.mark();
        rBBIDataWrapper.fTrie = Trie2.createFromSerialized(byteBuffer);
        byteBuffer.reset();
        if (n > rBBIDataWrapper.fHeader.fStatusTable) {
            throw new IOException("Break iterator Rule data corrupt");
        }
        ICUBinary.skipBytes(byteBuffer, rBBIDataWrapper.fHeader.fStatusTable - n);
        n = rBBIDataWrapper.fHeader.fStatusTable;
        rBBIDataWrapper.fStatusTable = ICUBinary.getInts(byteBuffer, rBBIDataWrapper.fHeader.fStatusTableLen / 4, rBBIDataWrapper.fHeader.fStatusTableLen & 3);
        if ((n += rBBIDataWrapper.fHeader.fStatusTableLen) > rBBIDataWrapper.fHeader.fRuleSource) {
            throw new IOException("Break iterator Rule data corrupt");
        }
        ICUBinary.skipBytes(byteBuffer, rBBIDataWrapper.fHeader.fRuleSource - n);
        n = rBBIDataWrapper.fHeader.fRuleSource;
        rBBIDataWrapper.fRuleSource = ICUBinary.getString(byteBuffer, rBBIDataWrapper.fHeader.fRuleSourceLen / 2, rBBIDataWrapper.fHeader.fRuleSourceLen & 1);
        if (RuleBasedBreakIterator.fDebugEnv != null && RuleBasedBreakIterator.fDebugEnv.indexOf("data") >= 0) {
            rBBIDataWrapper.dump(System.out);
        }
        return rBBIDataWrapper;
    }

    public void dump(PrintStream printStream) {
        if (this.fFTable == null) {
            throw new NullPointerException();
        }
        printStream.println("RBBI Data Wrapper dump ...");
        printStream.println();
        printStream.println("Forward State Table");
        this.dumpTable(printStream, this.fFTable);
        printStream.println("Reverse State Table");
        this.dumpTable(printStream, this.fRTable);
        this.dumpCharCategories(printStream);
        printStream.println("Source Rules: " + this.fRuleSource);
    }

    public static String intToString(int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder(n2);
        stringBuilder.append(n);
        while (stringBuilder.length() < n2) {
            stringBuilder.insert(0, ' ');
        }
        return stringBuilder.toString();
    }

    public static String intToHexString(int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder(n2);
        stringBuilder.append(Integer.toHexString(n));
        while (stringBuilder.length() < n2) {
            stringBuilder.insert(0, ' ');
        }
        return stringBuilder.toString();
    }

    private void dumpTable(PrintStream printStream, RBBIStateTable rBBIStateTable) {
        if (rBBIStateTable == null || rBBIStateTable.fTable.length == 0) {
            printStream.println("  -- null -- ");
        } else {
            int n;
            StringBuilder stringBuilder = new StringBuilder(" Row  Acc Look  Tag");
            for (n = 0; n < this.fHeader.fCatCount; ++n) {
                stringBuilder.append(RBBIDataWrapper.intToString(n, 5));
            }
            printStream.println(stringBuilder.toString());
            for (n = 0; n < stringBuilder.length(); ++n) {
                printStream.print("-");
            }
            printStream.println();
            for (int i = 0; i < rBBIStateTable.fNumStates; ++i) {
                this.dumpRow(printStream, rBBIStateTable, i);
            }
            printStream.println();
        }
    }

    private void dumpRow(PrintStream printStream, RBBIStateTable rBBIStateTable, int n) {
        StringBuilder stringBuilder = new StringBuilder(this.fHeader.fCatCount * 5 + 20);
        stringBuilder.append(RBBIDataWrapper.intToString(n, 4));
        int n2 = this.getRowIndex(n);
        if (rBBIStateTable.fTable[n2 + 0] != 0) {
            stringBuilder.append(RBBIDataWrapper.intToString(rBBIStateTable.fTable[n2 + 0], 5));
        } else {
            stringBuilder.append("     ");
        }
        if (rBBIStateTable.fTable[n2 + 1] != 0) {
            stringBuilder.append(RBBIDataWrapper.intToString(rBBIStateTable.fTable[n2 + 1], 5));
        } else {
            stringBuilder.append("     ");
        }
        stringBuilder.append(RBBIDataWrapper.intToString(rBBIStateTable.fTable[n2 + 2], 5));
        for (int i = 0; i < this.fHeader.fCatCount; ++i) {
            stringBuilder.append(RBBIDataWrapper.intToString(rBBIStateTable.fTable[n2 + 4 + i], 5));
        }
        printStream.println(stringBuilder);
    }

    private void dumpCharCategories(PrintStream printStream) {
        int n;
        int n2 = this.fHeader.fCatCount;
        String[] stringArray = new String[n2 + 1];
        int n3 = 0;
        int n4 = 0;
        int n5 = -1;
        int[] nArray = new int[n2 + 1];
        for (n = 0; n <= this.fHeader.fCatCount; ++n) {
            stringArray[n] = "";
        }
        printStream.println("\nCharacter Categories");
        printStream.println("--------------------");
        for (int i = 0; i <= 0x10FFFF; ++i) {
            n = this.fTrie.get(i);
            if ((n &= 0xFFFFBFFF) < 0 || n > this.fHeader.fCatCount) {
                printStream.println("Error, bad category " + Integer.toHexString(n) + " for char " + Integer.toHexString(i));
                break;
            }
            if (n == n5) {
                n4 = i;
                continue;
            }
            if (n5 >= 0) {
                if (stringArray[n5].length() > nArray[n5] + 70) {
                    nArray[n5] = stringArray[n5].length() + 10;
                    int n6 = n5;
                    stringArray[n6] = stringArray[n6] + "\n       ";
                }
                int n7 = n5;
                stringArray[n7] = stringArray[n7] + " " + Integer.toHexString(n3);
                if (n4 != n3) {
                    int n8 = n5;
                    stringArray[n8] = stringArray[n8] + "-" + Integer.toHexString(n4);
                }
            }
            n5 = n;
            n3 = n4 = i;
        }
        int n9 = n5;
        stringArray[n9] = stringArray[n9] + " " + Integer.toHexString(n3);
        if (n4 != n3) {
            int n10 = n5;
            stringArray[n10] = stringArray[n10] + "-" + Integer.toHexString(n4);
        }
        for (n = 0; n <= this.fHeader.fCatCount; ++n) {
            printStream.println(RBBIDataWrapper.intToString(n, 5) + "  " + stringArray[n]);
        }
        printStream.println();
    }

    public static final class RBBIDataHeader {
        int fMagic = 0;
        byte[] fFormatVersion = new byte[4];
        int fLength;
        public int fCatCount;
        int fFTable;
        int fFTableLen;
        int fRTable;
        int fRTableLen;
        int fTrie;
        int fTrieLen;
        int fRuleSource;
        int fRuleSourceLen;
        int fStatusTable;
        int fStatusTableLen;
    }

    private static final class IsAcceptable
    implements ICUBinary.Authenticate {
        private IsAcceptable() {
        }

        @Override
        public boolean isDataVersionAcceptable(byte[] byArray) {
            int n = (byArray[0] << 24) + (byArray[1] << 16) + (byArray[2] << 8) + byArray[3];
            return n == 0x5000000;
        }

        IsAcceptable(1 var1_1) {
            this();
        }
    }

    public static class RBBIStateTable {
        public int fNumStates;
        public int fRowLen;
        public int fFlags;
        public int fReserved;
        public short[] fTable;

        static RBBIStateTable get(ByteBuffer byteBuffer, int n) throws IOException {
            if (n == 0) {
                return null;
            }
            if (n < 16) {
                throw new IOException("Invalid RBBI state table length.");
            }
            RBBIStateTable rBBIStateTable = new RBBIStateTable();
            rBBIStateTable.fNumStates = byteBuffer.getInt();
            rBBIStateTable.fRowLen = byteBuffer.getInt();
            rBBIStateTable.fFlags = byteBuffer.getInt();
            rBBIStateTable.fReserved = byteBuffer.getInt();
            int n2 = n - 16;
            rBBIStateTable.fTable = ICUBinary.getShorts(byteBuffer, n2 / 2, n2 & 1);
            return rBBIStateTable;
        }

        public int put(DataOutputStream dataOutputStream) throws IOException {
            int n;
            dataOutputStream.writeInt(this.fNumStates);
            dataOutputStream.writeInt(this.fRowLen);
            dataOutputStream.writeInt(this.fFlags);
            dataOutputStream.writeInt(this.fReserved);
            int n2 = this.fRowLen * this.fNumStates / 2;
            for (n = 0; n < n2; ++n) {
                dataOutputStream.writeShort(this.fTable[n]);
            }
            n = 16 + this.fRowLen * this.fNumStates;
            while (n % 8 != 0) {
                dataOutputStream.writeByte(0);
                ++n;
            }
            return n;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (!(object instanceof RBBIStateTable)) {
                return true;
            }
            RBBIStateTable rBBIStateTable = (RBBIStateTable)object;
            if (this.fNumStates != rBBIStateTable.fNumStates) {
                return true;
            }
            if (this.fRowLen != rBBIStateTable.fRowLen) {
                return true;
            }
            if (this.fFlags != rBBIStateTable.fFlags) {
                return true;
            }
            if (this.fReserved != rBBIStateTable.fReserved) {
                return true;
            }
            return Arrays.equals(this.fTable, rBBIStateTable.fTable);
        }
    }
}

