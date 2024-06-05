package com.ibm.icu.impl;

import java.nio.*;
import com.ibm.icu.text.*;
import java.io.*;
import java.util.*;

public final class RBBIDataWrapper
{
    public RBBIDataHeader fHeader;
    public RBBIStateTable fFTable;
    public RBBIStateTable fRTable;
    public Trie2 fTrie;
    public String fRuleSource;
    public int[] fStatusTable;
    public static final int DATA_FORMAT = 1114794784;
    public static final int FORMAT_VERSION = 83886080;
    private static final IsAcceptable IS_ACCEPTABLE;
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
    
    public static boolean equals(final RBBIStateTable left, final RBBIStateTable right) {
        return left == right || (left != null && right != null && left.equals(right));
    }
    
    public int getRowIndex(final int state) {
        return state * (this.fHeader.fCatCount + 4);
    }
    
    RBBIDataWrapper() {
    }
    
    public static RBBIDataWrapper get(final ByteBuffer bytes) throws IOException {
        final RBBIDataWrapper This = new RBBIDataWrapper();
        ICUBinary.readHeader(bytes, 1114794784, RBBIDataWrapper.IS_ACCEPTABLE);
        This.fHeader = new RBBIDataHeader();
        This.fHeader.fMagic = bytes.getInt();
        This.fHeader.fFormatVersion[0] = bytes.get();
        This.fHeader.fFormatVersion[1] = bytes.get();
        This.fHeader.fFormatVersion[2] = bytes.get();
        This.fHeader.fFormatVersion[3] = bytes.get();
        This.fHeader.fLength = bytes.getInt();
        This.fHeader.fCatCount = bytes.getInt();
        This.fHeader.fFTable = bytes.getInt();
        This.fHeader.fFTableLen = bytes.getInt();
        This.fHeader.fRTable = bytes.getInt();
        This.fHeader.fRTableLen = bytes.getInt();
        This.fHeader.fTrie = bytes.getInt();
        This.fHeader.fTrieLen = bytes.getInt();
        This.fHeader.fRuleSource = bytes.getInt();
        This.fHeader.fRuleSourceLen = bytes.getInt();
        This.fHeader.fStatusTable = bytes.getInt();
        This.fHeader.fStatusTableLen = bytes.getInt();
        ICUBinary.skipBytes(bytes, 24);
        if (This.fHeader.fMagic != 45472 || !RBBIDataWrapper.IS_ACCEPTABLE.isDataVersionAcceptable(This.fHeader.fFormatVersion)) {
            throw new IOException("Break Iterator Rule Data Magic Number Incorrect, or unsupported data version.");
        }
        int pos = 80;
        if (This.fHeader.fFTable < pos || This.fHeader.fFTable > This.fHeader.fLength) {
            throw new IOException("Break iterator Rule data corrupt");
        }
        ICUBinary.skipBytes(bytes, This.fHeader.fFTable - pos);
        pos = This.fHeader.fFTable;
        This.fFTable = RBBIStateTable.get(bytes, This.fHeader.fFTableLen);
        pos += This.fHeader.fFTableLen;
        ICUBinary.skipBytes(bytes, This.fHeader.fRTable - pos);
        pos = This.fHeader.fRTable;
        This.fRTable = RBBIStateTable.get(bytes, This.fHeader.fRTableLen);
        pos += This.fHeader.fRTableLen;
        ICUBinary.skipBytes(bytes, This.fHeader.fTrie - pos);
        pos = This.fHeader.fTrie;
        bytes.mark();
        This.fTrie = Trie2.createFromSerialized(bytes);
        bytes.reset();
        if (pos > This.fHeader.fStatusTable) {
            throw new IOException("Break iterator Rule data corrupt");
        }
        ICUBinary.skipBytes(bytes, This.fHeader.fStatusTable - pos);
        pos = This.fHeader.fStatusTable;
        This.fStatusTable = ICUBinary.getInts(bytes, This.fHeader.fStatusTableLen / 4, This.fHeader.fStatusTableLen & 0x3);
        pos += This.fHeader.fStatusTableLen;
        if (pos > This.fHeader.fRuleSource) {
            throw new IOException("Break iterator Rule data corrupt");
        }
        ICUBinary.skipBytes(bytes, This.fHeader.fRuleSource - pos);
        pos = This.fHeader.fRuleSource;
        This.fRuleSource = ICUBinary.getString(bytes, This.fHeader.fRuleSourceLen / 2, This.fHeader.fRuleSourceLen & 0x1);
        if (RuleBasedBreakIterator.fDebugEnv != null && RuleBasedBreakIterator.fDebugEnv.indexOf("data") >= 0) {
            This.dump(System.out);
        }
        return This;
    }
    
    public void dump(final PrintStream out) {
        if (this.fFTable == null) {
            throw new NullPointerException();
        }
        out.println("RBBI Data Wrapper dump ...");
        out.println();
        out.println("Forward State Table");
        this.dumpTable(out, this.fFTable);
        out.println("Reverse State Table");
        this.dumpTable(out, this.fRTable);
        this.dumpCharCategories(out);
        out.println("Source Rules: " + this.fRuleSource);
    }
    
    public static String intToString(final int n, final int width) {
        final StringBuilder dest = new StringBuilder(width);
        dest.append(n);
        while (dest.length() < width) {
            dest.insert(0, ' ');
        }
        return dest.toString();
    }
    
    public static String intToHexString(final int n, final int width) {
        final StringBuilder dest = new StringBuilder(width);
        dest.append(Integer.toHexString(n));
        while (dest.length() < width) {
            dest.insert(0, ' ');
        }
        return dest.toString();
    }
    
    private void dumpTable(final PrintStream out, final RBBIStateTable table) {
        if (table == null || table.fTable.length == 0) {
            out.println("  -- null -- ");
        }
        else {
            final StringBuilder header = new StringBuilder(" Row  Acc Look  Tag");
            for (int n = 0; n < this.fHeader.fCatCount; ++n) {
                header.append(intToString(n, 5));
            }
            out.println(header.toString());
            for (int n = 0; n < header.length(); ++n) {
                out.print("-");
            }
            out.println();
            for (int state = 0; state < table.fNumStates; ++state) {
                this.dumpRow(out, table, state);
            }
            out.println();
        }
    }
    
    private void dumpRow(final PrintStream out, final RBBIStateTable table, final int state) {
        final StringBuilder dest = new StringBuilder(this.fHeader.fCatCount * 5 + 20);
        dest.append(intToString(state, 4));
        final int row = this.getRowIndex(state);
        if (table.fTable[row + 0] != 0) {
            dest.append(intToString(table.fTable[row + 0], 5));
        }
        else {
            dest.append("     ");
        }
        if (table.fTable[row + 1] != 0) {
            dest.append(intToString(table.fTable[row + 1], 5));
        }
        else {
            dest.append("     ");
        }
        dest.append(intToString(table.fTable[row + 2], 5));
        for (int col = 0; col < this.fHeader.fCatCount; ++col) {
            dest.append(intToString(table.fTable[row + 4 + col], 5));
        }
        out.println(dest);
    }
    
    private void dumpCharCategories(final PrintStream out) {
        final int n = this.fHeader.fCatCount;
        final String[] catStrings = new String[n + 1];
        int rangeStart = 0;
        int rangeEnd = 0;
        int lastCat = -1;
        final int[] lastNewline = new int[n + 1];
        for (int category = 0; category <= this.fHeader.fCatCount; ++category) {
            catStrings[category] = "";
        }
        out.println("\nCharacter Categories");
        out.println("--------------------");
        for (int char32 = 0; char32 <= 1114111; ++char32) {
            int category = this.fTrie.get(char32);
            category &= 0xFFFFBFFF;
            if (category < 0 || category > this.fHeader.fCatCount) {
                out.println("Error, bad category " + Integer.toHexString(category) + " for char " + Integer.toHexString(char32));
                break;
            }
            if (category == lastCat) {
                rangeEnd = char32;
            }
            else {
                if (lastCat >= 0) {
                    if (catStrings[lastCat].length() > lastNewline[lastCat] + 70) {
                        lastNewline[lastCat] = catStrings[lastCat].length() + 10;
                        final StringBuilder sb = new StringBuilder();
                        final String[] array = catStrings;
                        final int n2 = lastCat;
                        array[n2] = sb.append(array[n2]).append("\n       ").toString();
                    }
                    final StringBuilder sb2 = new StringBuilder();
                    final String[] array2 = catStrings;
                    final int n3 = lastCat;
                    array2[n3] = sb2.append(array2[n3]).append(" ").append(Integer.toHexString(rangeStart)).toString();
                    if (rangeEnd != rangeStart) {
                        final StringBuilder sb3 = new StringBuilder();
                        final String[] array3 = catStrings;
                        final int n4 = lastCat;
                        array3[n4] = sb3.append(array3[n4]).append("-").append(Integer.toHexString(rangeEnd)).toString();
                    }
                }
                lastCat = category;
                rangeEnd = (rangeStart = char32);
            }
        }
        final StringBuilder sb4 = new StringBuilder();
        final String[] array4 = catStrings;
        final int n5 = lastCat;
        array4[n5] = sb4.append(array4[n5]).append(" ").append(Integer.toHexString(rangeStart)).toString();
        if (rangeEnd != rangeStart) {
            final StringBuilder sb5 = new StringBuilder();
            final String[] array5 = catStrings;
            final int n6 = lastCat;
            array5[n6] = sb5.append(array5[n6]).append("-").append(Integer.toHexString(rangeEnd)).toString();
        }
        for (int category = 0; category <= this.fHeader.fCatCount; ++category) {
            out.println(intToString(category, 5) + "  " + catStrings[category]);
        }
        out.println();
    }
    
    static {
        IS_ACCEPTABLE = new IsAcceptable();
    }
    
    public static class RBBIStateTable
    {
        public int fNumStates;
        public int fRowLen;
        public int fFlags;
        public int fReserved;
        public short[] fTable;
        
        static RBBIStateTable get(final ByteBuffer bytes, final int length) throws IOException {
            if (length == 0) {
                return null;
            }
            if (length < 16) {
                throw new IOException("Invalid RBBI state table length.");
            }
            final RBBIStateTable This = new RBBIStateTable();
            This.fNumStates = bytes.getInt();
            This.fRowLen = bytes.getInt();
            This.fFlags = bytes.getInt();
            This.fReserved = bytes.getInt();
            final int lengthOfShorts = length - 16;
            This.fTable = ICUBinary.getShorts(bytes, lengthOfShorts / 2, lengthOfShorts & 0x1);
            return This;
        }
        
        public int put(final DataOutputStream bytes) throws IOException {
            bytes.writeInt(this.fNumStates);
            bytes.writeInt(this.fRowLen);
            bytes.writeInt(this.fFlags);
            bytes.writeInt(this.fReserved);
            for (int tableLen = this.fRowLen * this.fNumStates / 2, i = 0; i < tableLen; ++i) {
                bytes.writeShort(this.fTable[i]);
            }
            int bytesWritten;
            for (bytesWritten = 16 + this.fRowLen * this.fNumStates; bytesWritten % 8 != 0; ++bytesWritten) {
                bytes.writeByte(0);
            }
            return bytesWritten;
        }
        
        @Override
        public boolean equals(final Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof RBBIStateTable)) {
                return false;
            }
            final RBBIStateTable otherST = (RBBIStateTable)other;
            return this.fNumStates == otherST.fNumStates && this.fRowLen == otherST.fRowLen && this.fFlags == otherST.fFlags && this.fReserved == otherST.fReserved && Arrays.equals(this.fTable, otherST.fTable);
        }
    }
    
    private static final class IsAcceptable implements ICUBinary.Authenticate
    {
        @Override
        public boolean isDataVersionAcceptable(final byte[] version) {
            final int intVersion = (version[0] << 24) + (version[1] << 16) + (version[2] << 8) + version[3];
            return intVersion == 83886080;
        }
    }
    
    public static final class RBBIDataHeader
    {
        int fMagic;
        byte[] fFormatVersion;
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
        
        public RBBIDataHeader() {
            this.fMagic = 0;
            this.fFormatVersion = new byte[4];
        }
    }
}
