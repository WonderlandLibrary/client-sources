/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.Norm2AllModes;
import com.ibm.icu.text.Normalizer;
import com.ibm.icu.util.ICUUncheckedIOException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public abstract class Normalizer2 {
    public static Normalizer2 getNFCInstance() {
        return Norm2AllModes.getNFCInstance().comp;
    }

    public static Normalizer2 getNFDInstance() {
        return Norm2AllModes.getNFCInstance().decomp;
    }

    public static Normalizer2 getNFKCInstance() {
        return Norm2AllModes.getNFKCInstance().comp;
    }

    public static Normalizer2 getNFKDInstance() {
        return Norm2AllModes.getNFKCInstance().decomp;
    }

    public static Normalizer2 getNFKCCasefoldInstance() {
        return Norm2AllModes.getNFKC_CFInstance().comp;
    }

    public static Normalizer2 getInstance(InputStream inputStream, String string, Mode mode) {
        ByteBuffer byteBuffer = null;
        if (inputStream != null) {
            try {
                byteBuffer = ICUBinary.getByteBufferFromInputStreamAndCloseStream(inputStream);
            } catch (IOException iOException) {
                throw new ICUUncheckedIOException(iOException);
            }
        }
        Norm2AllModes norm2AllModes = Norm2AllModes.getInstance(byteBuffer, string);
        switch (1.$SwitchMap$com$ibm$icu$text$Normalizer2$Mode[mode.ordinal()]) {
            case 1: {
                return norm2AllModes.comp;
            }
            case 2: {
                return norm2AllModes.decomp;
            }
            case 3: {
                return norm2AllModes.fcd;
            }
            case 4: {
                return norm2AllModes.fcc;
            }
        }
        return null;
    }

    public String normalize(CharSequence charSequence) {
        if (charSequence instanceof String) {
            int n = this.spanQuickCheckYes(charSequence);
            if (n == charSequence.length()) {
                return (String)charSequence;
            }
            if (n != 0) {
                StringBuilder stringBuilder = new StringBuilder(charSequence.length()).append(charSequence, 0, n);
                return this.normalizeSecondAndAppend(stringBuilder, charSequence.subSequence(n, charSequence.length())).toString();
            }
        }
        return this.normalize(charSequence, new StringBuilder(charSequence.length())).toString();
    }

    public abstract StringBuilder normalize(CharSequence var1, StringBuilder var2);

    public abstract Appendable normalize(CharSequence var1, Appendable var2);

    public abstract StringBuilder normalizeSecondAndAppend(StringBuilder var1, CharSequence var2);

    public abstract StringBuilder append(StringBuilder var1, CharSequence var2);

    public abstract String getDecomposition(int var1);

    public String getRawDecomposition(int n) {
        return null;
    }

    public int composePair(int n, int n2) {
        return 1;
    }

    public int getCombiningClass(int n) {
        return 1;
    }

    public abstract boolean isNormalized(CharSequence var1);

    public abstract Normalizer.QuickCheckResult quickCheck(CharSequence var1);

    public abstract int spanQuickCheckYes(CharSequence var1);

    public abstract boolean hasBoundaryBefore(int var1);

    public abstract boolean hasBoundaryAfter(int var1);

    public abstract boolean isInert(int var1);

    @Deprecated
    protected Normalizer2() {
    }

    public static enum Mode {
        COMPOSE,
        DECOMPOSE,
        FCD,
        COMPOSE_CONTIGUOUS;

    }
}

