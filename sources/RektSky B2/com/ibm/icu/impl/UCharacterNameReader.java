package com.ibm.icu.impl;

import java.nio.*;
import java.io.*;
import java.util.*;

final class UCharacterNameReader implements ICUBinary.Authenticate
{
    private ByteBuffer m_byteBuffer_;
    private static final int GROUP_INFO_SIZE_ = 3;
    private int m_tokenstringindex_;
    private int m_groupindex_;
    private int m_groupstringindex_;
    private int m_algnamesindex_;
    private static final int ALG_INFO_SIZE_ = 12;
    private static final int DATA_FORMAT_ID_ = 1970168173;
    
    @Override
    public boolean isDataVersionAcceptable(final byte[] version) {
        return version[0] == 1;
    }
    
    protected UCharacterNameReader(final ByteBuffer bytes) throws IOException {
        ICUBinary.readHeader(bytes, 1970168173, this);
        this.m_byteBuffer_ = bytes;
    }
    
    protected void read(final UCharacterName data) throws IOException {
        this.m_tokenstringindex_ = this.m_byteBuffer_.getInt();
        this.m_groupindex_ = this.m_byteBuffer_.getInt();
        this.m_groupstringindex_ = this.m_byteBuffer_.getInt();
        this.m_algnamesindex_ = this.m_byteBuffer_.getInt();
        int count = this.m_byteBuffer_.getChar();
        final char[] token = ICUBinary.getChars(this.m_byteBuffer_, count, 0);
        int size = this.m_groupindex_ - this.m_tokenstringindex_;
        final byte[] tokenstr = new byte[size];
        this.m_byteBuffer_.get(tokenstr);
        data.setToken(token, tokenstr);
        count = this.m_byteBuffer_.getChar();
        data.setGroupCountSize(count, 3);
        count *= 3;
        final char[] group = ICUBinary.getChars(this.m_byteBuffer_, count, 0);
        size = this.m_algnamesindex_ - this.m_groupstringindex_;
        final byte[] groupstring = new byte[size];
        this.m_byteBuffer_.get(groupstring);
        data.setGroup(group, groupstring);
        count = this.m_byteBuffer_.getInt();
        final UCharacterName.AlgorithmName[] alg = new UCharacterName.AlgorithmName[count];
        for (int i = 0; i < count; ++i) {
            final UCharacterName.AlgorithmName an = this.readAlg();
            if (an == null) {
                throw new IOException("unames.icu read error: Algorithmic names creation error");
            }
            alg[i] = an;
        }
        data.setAlgorithm(alg);
    }
    
    protected boolean authenticate(final byte[] dataformatid, final byte[] dataformatversion) {
        return Arrays.equals(ICUBinary.getVersionByteArrayFromCompactInt(1970168173), dataformatid) && this.isDataVersionAcceptable(dataformatversion);
    }
    
    private UCharacterName.AlgorithmName readAlg() throws IOException {
        final UCharacterName.AlgorithmName result = new UCharacterName.AlgorithmName();
        final int rangestart = this.m_byteBuffer_.getInt();
        final int rangeend = this.m_byteBuffer_.getInt();
        final byte type = this.m_byteBuffer_.get();
        final byte variant = this.m_byteBuffer_.get();
        if (!result.setInfo(rangestart, rangeend, type, variant)) {
            return null;
        }
        int size = this.m_byteBuffer_.getChar();
        if (type == 1) {
            final char[] factor = ICUBinary.getChars(this.m_byteBuffer_, variant, 0);
            result.setFactor(factor);
            size -= variant << 1;
        }
        final StringBuilder prefix = new StringBuilder();
        for (char c = (char)(this.m_byteBuffer_.get() & 0xFF); c != '\0'; c = (char)(this.m_byteBuffer_.get() & 0xFF)) {
            prefix.append(c);
        }
        result.setPrefix(prefix.toString());
        size -= 12 + prefix.length() + 1;
        if (size > 0) {
            final byte[] string = new byte[size];
            this.m_byteBuffer_.get(string);
            result.setFactorString(string);
        }
        return result;
    }
}
