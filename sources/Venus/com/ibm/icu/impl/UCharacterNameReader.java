/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.UCharacterName;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

final class UCharacterNameReader
implements ICUBinary.Authenticate {
    private ByteBuffer m_byteBuffer_;
    private static final int GROUP_INFO_SIZE_ = 3;
    private int m_tokenstringindex_;
    private int m_groupindex_;
    private int m_groupstringindex_;
    private int m_algnamesindex_;
    private static final int ALG_INFO_SIZE_ = 12;
    private static final int DATA_FORMAT_ID_ = 1970168173;

    @Override
    public boolean isDataVersionAcceptable(byte[] byArray) {
        return byArray[0] == 1;
    }

    protected UCharacterNameReader(ByteBuffer byteBuffer) throws IOException {
        ICUBinary.readHeader(byteBuffer, 1970168173, this);
        this.m_byteBuffer_ = byteBuffer;
    }

    protected void read(UCharacterName uCharacterName) throws IOException {
        this.m_tokenstringindex_ = this.m_byteBuffer_.getInt();
        this.m_groupindex_ = this.m_byteBuffer_.getInt();
        this.m_groupstringindex_ = this.m_byteBuffer_.getInt();
        this.m_algnamesindex_ = this.m_byteBuffer_.getInt();
        int n = this.m_byteBuffer_.getChar();
        char[] cArray = ICUBinary.getChars(this.m_byteBuffer_, n, 0);
        int n2 = this.m_groupindex_ - this.m_tokenstringindex_;
        byte[] byArray = new byte[n2];
        this.m_byteBuffer_.get(byArray);
        uCharacterName.setToken(cArray, byArray);
        n = this.m_byteBuffer_.getChar();
        uCharacterName.setGroupCountSize(n, 0);
        char[] cArray2 = ICUBinary.getChars(this.m_byteBuffer_, n *= 3, 0);
        n2 = this.m_algnamesindex_ - this.m_groupstringindex_;
        byte[] byArray2 = new byte[n2];
        this.m_byteBuffer_.get(byArray2);
        uCharacterName.setGroup(cArray2, byArray2);
        n = this.m_byteBuffer_.getInt();
        UCharacterName.AlgorithmName[] algorithmNameArray = new UCharacterName.AlgorithmName[n];
        for (int i = 0; i < n; ++i) {
            UCharacterName.AlgorithmName algorithmName = this.readAlg();
            if (algorithmName == null) {
                throw new IOException("unames.icu read error: Algorithmic names creation error");
            }
            algorithmNameArray[i] = algorithmName;
        }
        uCharacterName.setAlgorithm(algorithmNameArray);
    }

    protected boolean authenticate(byte[] byArray, byte[] byArray2) {
        return Arrays.equals(ICUBinary.getVersionByteArrayFromCompactInt(1970168173), byArray) && this.isDataVersionAcceptable(byArray2);
    }

    private UCharacterName.AlgorithmName readAlg() throws IOException {
        Object object;
        byte by;
        byte by2;
        int n;
        UCharacterName.AlgorithmName algorithmName = new UCharacterName.AlgorithmName();
        int n2 = this.m_byteBuffer_.getInt();
        if (!algorithmName.setInfo(n2, n = this.m_byteBuffer_.getInt(), by2 = this.m_byteBuffer_.get(), by = this.m_byteBuffer_.get())) {
            return null;
        }
        int n3 = this.m_byteBuffer_.getChar();
        if (by2 == 1) {
            object = ICUBinary.getChars(this.m_byteBuffer_, by, 0);
            algorithmName.setFactor((char[])object);
            n3 -= by << 1;
        }
        object = new StringBuilder();
        char c = (char)(this.m_byteBuffer_.get() & 0xFF);
        while (c != '\u0000') {
            ((StringBuilder)object).append(c);
            c = (char)(this.m_byteBuffer_.get() & 0xFF);
        }
        algorithmName.setPrefix(((StringBuilder)object).toString());
        if ((n3 -= 12 + ((StringBuilder)object).length() + 1) > 0) {
            byte[] byArray = new byte[n3];
            this.m_byteBuffer_.get(byArray);
            algorithmName.setFactorString(byArray);
        }
        return algorithmName;
    }
}

