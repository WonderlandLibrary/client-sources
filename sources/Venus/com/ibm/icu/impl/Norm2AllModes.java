/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.CacheBase;
import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.text.Normalizer;
import com.ibm.icu.text.Normalizer2;
import com.ibm.icu.util.ICUUncheckedIOException;
import java.io.IOException;
import java.nio.ByteBuffer;

public final class Norm2AllModes {
    public final Normalizer2Impl impl;
    public final ComposeNormalizer2 comp;
    public final DecomposeNormalizer2 decomp;
    public final FCDNormalizer2 fcd;
    public final ComposeNormalizer2 fcc;
    private static CacheBase<String, Norm2AllModes, ByteBuffer> cache = new SoftCache<String, Norm2AllModes, ByteBuffer>(){

        @Override
        protected Norm2AllModes createInstance(String string, ByteBuffer byteBuffer) {
            Normalizer2Impl normalizer2Impl = byteBuffer == null ? new Normalizer2Impl().load(string + ".nrm") : new Normalizer2Impl().load(byteBuffer);
            return new Norm2AllModes(normalizer2Impl, null);
        }

        @Override
        protected Object createInstance(Object object, Object object2) {
            return this.createInstance((String)object, (ByteBuffer)object2);
        }
    };
    public static final NoopNormalizer2 NOOP_NORMALIZER2 = new NoopNormalizer2();

    private Norm2AllModes(Normalizer2Impl normalizer2Impl) {
        this.impl = normalizer2Impl;
        this.comp = new ComposeNormalizer2(normalizer2Impl, false);
        this.decomp = new DecomposeNormalizer2(normalizer2Impl);
        this.fcd = new FCDNormalizer2(normalizer2Impl);
        this.fcc = new ComposeNormalizer2(normalizer2Impl, true);
    }

    private static Norm2AllModes getInstanceFromSingleton(Norm2AllModesSingleton norm2AllModesSingleton) {
        if (Norm2AllModesSingleton.access$000(norm2AllModesSingleton) != null) {
            throw Norm2AllModesSingleton.access$000(norm2AllModesSingleton);
        }
        return Norm2AllModesSingleton.access$100(norm2AllModesSingleton);
    }

    public static Norm2AllModes getNFCInstance() {
        return Norm2AllModes.getInstanceFromSingleton(NFCSingleton.access$200());
    }

    public static Norm2AllModes getNFKCInstance() {
        return Norm2AllModes.getInstanceFromSingleton(NFKCSingleton.access$300());
    }

    public static Norm2AllModes getNFKC_CFInstance() {
        return Norm2AllModes.getInstanceFromSingleton(NFKC_CFSingleton.access$400());
    }

    public static Normalizer2WithImpl getN2WithImpl(int n) {
        switch (n) {
            case 0: {
                return Norm2AllModes.getNFCInstance().decomp;
            }
            case 1: {
                return Norm2AllModes.getNFKCInstance().decomp;
            }
            case 2: {
                return Norm2AllModes.getNFCInstance().comp;
            }
            case 3: {
                return Norm2AllModes.getNFKCInstance().comp;
            }
        }
        return null;
    }

    public static Norm2AllModes getInstance(ByteBuffer byteBuffer, String string) {
        Norm2AllModesSingleton norm2AllModesSingleton;
        if (byteBuffer == null && (norm2AllModesSingleton = string.equals("nfc") ? NFCSingleton.access$200() : (string.equals("nfkc") ? NFKCSingleton.access$300() : (string.equals("nfkc_cf") ? NFKC_CFSingleton.access$400() : null))) != null) {
            if (Norm2AllModesSingleton.access$000(norm2AllModesSingleton) != null) {
                throw Norm2AllModesSingleton.access$000(norm2AllModesSingleton);
            }
            return Norm2AllModesSingleton.access$100(norm2AllModesSingleton);
        }
        return cache.getInstance(string, byteBuffer);
    }

    public static Normalizer2 getFCDNormalizer2() {
        return Norm2AllModes.getNFCInstance().fcd;
    }

    Norm2AllModes(Normalizer2Impl normalizer2Impl, 1 var2_2) {
        this(normalizer2Impl);
    }

    private static final class NFKC_CFSingleton {
        private static final Norm2AllModesSingleton INSTANCE = new Norm2AllModesSingleton("nfkc_cf", null);

        private NFKC_CFSingleton() {
        }

        static Norm2AllModesSingleton access$400() {
            return INSTANCE;
        }
    }

    private static final class NFKCSingleton {
        private static final Norm2AllModesSingleton INSTANCE = new Norm2AllModesSingleton("nfkc", null);

        private NFKCSingleton() {
        }

        static Norm2AllModesSingleton access$300() {
            return INSTANCE;
        }
    }

    private static final class NFCSingleton {
        private static final Norm2AllModesSingleton INSTANCE = new Norm2AllModesSingleton("nfc", null);

        private NFCSingleton() {
        }

        static Norm2AllModesSingleton access$200() {
            return INSTANCE;
        }
    }

    private static final class Norm2AllModesSingleton {
        private Norm2AllModes allModes;
        private RuntimeException exception;

        private Norm2AllModesSingleton(String string) {
            try {
                Normalizer2Impl normalizer2Impl = new Normalizer2Impl().load(string + ".nrm");
                this.allModes = new Norm2AllModes(normalizer2Impl, null);
            } catch (RuntimeException runtimeException) {
                this.exception = runtimeException;
            }
        }

        static RuntimeException access$000(Norm2AllModesSingleton norm2AllModesSingleton) {
            return norm2AllModesSingleton.exception;
        }

        static Norm2AllModes access$100(Norm2AllModesSingleton norm2AllModesSingleton) {
            return norm2AllModesSingleton.allModes;
        }

        Norm2AllModesSingleton(String string, 1 var2_2) {
            this(string);
        }
    }

    public static final class FCDNormalizer2
    extends Normalizer2WithImpl {
        public FCDNormalizer2(Normalizer2Impl normalizer2Impl) {
            super(normalizer2Impl);
        }

        @Override
        protected void normalize(CharSequence charSequence, Normalizer2Impl.ReorderingBuffer reorderingBuffer) {
            this.impl.makeFCD(charSequence, 0, charSequence.length(), reorderingBuffer);
        }

        @Override
        protected void normalizeAndAppend(CharSequence charSequence, boolean bl, Normalizer2Impl.ReorderingBuffer reorderingBuffer) {
            this.impl.makeFCDAndAppend(charSequence, bl, reorderingBuffer);
        }

        @Override
        public int spanQuickCheckYes(CharSequence charSequence) {
            return this.impl.makeFCD(charSequence, 0, charSequence.length(), null);
        }

        @Override
        public int getQuickCheck(int n) {
            return this.impl.isDecompYes(this.impl.getNorm16(n)) ? 1 : 0;
        }

        @Override
        public boolean hasBoundaryBefore(int n) {
            return this.impl.hasFCDBoundaryBefore(n);
        }

        @Override
        public boolean hasBoundaryAfter(int n) {
            return this.impl.hasFCDBoundaryAfter(n);
        }

        @Override
        public boolean isInert(int n) {
            return this.impl.isFCDInert(n);
        }
    }

    public static final class ComposeNormalizer2
    extends Normalizer2WithImpl {
        private final boolean onlyContiguous;

        public ComposeNormalizer2(Normalizer2Impl normalizer2Impl, boolean bl) {
            super(normalizer2Impl);
            this.onlyContiguous = bl;
        }

        @Override
        protected void normalize(CharSequence charSequence, Normalizer2Impl.ReorderingBuffer reorderingBuffer) {
            this.impl.compose(charSequence, 0, charSequence.length(), this.onlyContiguous, true, reorderingBuffer);
        }

        @Override
        protected void normalizeAndAppend(CharSequence charSequence, boolean bl, Normalizer2Impl.ReorderingBuffer reorderingBuffer) {
            this.impl.composeAndAppend(charSequence, bl, this.onlyContiguous, reorderingBuffer);
        }

        @Override
        public boolean isNormalized(CharSequence charSequence) {
            return this.impl.compose(charSequence, 0, charSequence.length(), this.onlyContiguous, false, new Normalizer2Impl.ReorderingBuffer(this.impl, new StringBuilder(), 5));
        }

        @Override
        public Normalizer.QuickCheckResult quickCheck(CharSequence charSequence) {
            int n = this.impl.composeQuickCheck(charSequence, 0, charSequence.length(), this.onlyContiguous, true);
            if ((n & 1) != 0) {
                return Normalizer.MAYBE;
            }
            if (n >>> 1 == charSequence.length()) {
                return Normalizer.YES;
            }
            return Normalizer.NO;
        }

        @Override
        public int spanQuickCheckYes(CharSequence charSequence) {
            return this.impl.composeQuickCheck(charSequence, 0, charSequence.length(), this.onlyContiguous, false) >>> 1;
        }

        @Override
        public int getQuickCheck(int n) {
            return this.impl.getCompQuickCheck(this.impl.getNorm16(n));
        }

        @Override
        public boolean hasBoundaryBefore(int n) {
            return this.impl.hasCompBoundaryBefore(n);
        }

        @Override
        public boolean hasBoundaryAfter(int n) {
            return this.impl.hasCompBoundaryAfter(n, this.onlyContiguous);
        }

        @Override
        public boolean isInert(int n) {
            return this.impl.isCompInert(n, this.onlyContiguous);
        }
    }

    public static final class DecomposeNormalizer2
    extends Normalizer2WithImpl {
        public DecomposeNormalizer2(Normalizer2Impl normalizer2Impl) {
            super(normalizer2Impl);
        }

        @Override
        protected void normalize(CharSequence charSequence, Normalizer2Impl.ReorderingBuffer reorderingBuffer) {
            this.impl.decompose(charSequence, 0, charSequence.length(), reorderingBuffer);
        }

        @Override
        protected void normalizeAndAppend(CharSequence charSequence, boolean bl, Normalizer2Impl.ReorderingBuffer reorderingBuffer) {
            this.impl.decomposeAndAppend(charSequence, bl, reorderingBuffer);
        }

        @Override
        public int spanQuickCheckYes(CharSequence charSequence) {
            return this.impl.decompose(charSequence, 0, charSequence.length(), null);
        }

        @Override
        public int getQuickCheck(int n) {
            return this.impl.isDecompYes(this.impl.getNorm16(n)) ? 1 : 0;
        }

        @Override
        public boolean hasBoundaryBefore(int n) {
            return this.impl.hasDecompBoundaryBefore(n);
        }

        @Override
        public boolean hasBoundaryAfter(int n) {
            return this.impl.hasDecompBoundaryAfter(n);
        }

        @Override
        public boolean isInert(int n) {
            return this.impl.isDecompInert(n);
        }
    }

    public static abstract class Normalizer2WithImpl
    extends Normalizer2 {
        public final Normalizer2Impl impl;

        public Normalizer2WithImpl(Normalizer2Impl normalizer2Impl) {
            this.impl = normalizer2Impl;
        }

        @Override
        public StringBuilder normalize(CharSequence charSequence, StringBuilder stringBuilder) {
            if (stringBuilder == charSequence) {
                throw new IllegalArgumentException();
            }
            stringBuilder.setLength(0);
            this.normalize(charSequence, new Normalizer2Impl.ReorderingBuffer(this.impl, stringBuilder, charSequence.length()));
            return stringBuilder;
        }

        @Override
        public Appendable normalize(CharSequence charSequence, Appendable appendable) {
            if (appendable == charSequence) {
                throw new IllegalArgumentException();
            }
            Normalizer2Impl.ReorderingBuffer reorderingBuffer = new Normalizer2Impl.ReorderingBuffer(this.impl, appendable, charSequence.length());
            this.normalize(charSequence, reorderingBuffer);
            reorderingBuffer.flush();
            return appendable;
        }

        protected abstract void normalize(CharSequence var1, Normalizer2Impl.ReorderingBuffer var2);

        @Override
        public StringBuilder normalizeSecondAndAppend(StringBuilder stringBuilder, CharSequence charSequence) {
            return this.normalizeSecondAndAppend(stringBuilder, charSequence, false);
        }

        @Override
        public StringBuilder append(StringBuilder stringBuilder, CharSequence charSequence) {
            return this.normalizeSecondAndAppend(stringBuilder, charSequence, true);
        }

        public StringBuilder normalizeSecondAndAppend(StringBuilder stringBuilder, CharSequence charSequence, boolean bl) {
            if (stringBuilder == charSequence) {
                throw new IllegalArgumentException();
            }
            this.normalizeAndAppend(charSequence, bl, new Normalizer2Impl.ReorderingBuffer(this.impl, stringBuilder, stringBuilder.length() + charSequence.length()));
            return stringBuilder;
        }

        protected abstract void normalizeAndAppend(CharSequence var1, boolean var2, Normalizer2Impl.ReorderingBuffer var3);

        @Override
        public String getDecomposition(int n) {
            return this.impl.getDecomposition(n);
        }

        @Override
        public String getRawDecomposition(int n) {
            return this.impl.getRawDecomposition(n);
        }

        @Override
        public int composePair(int n, int n2) {
            return this.impl.composePair(n, n2);
        }

        @Override
        public int getCombiningClass(int n) {
            return this.impl.getCC(this.impl.getNorm16(n));
        }

        @Override
        public boolean isNormalized(CharSequence charSequence) {
            return charSequence.length() == this.spanQuickCheckYes(charSequence);
        }

        @Override
        public Normalizer.QuickCheckResult quickCheck(CharSequence charSequence) {
            return this.isNormalized(charSequence) ? Normalizer.YES : Normalizer.NO;
        }

        public abstract int getQuickCheck(int var1);
    }

    public static final class NoopNormalizer2
    extends Normalizer2 {
        @Override
        public StringBuilder normalize(CharSequence charSequence, StringBuilder stringBuilder) {
            if (stringBuilder != charSequence) {
                stringBuilder.setLength(0);
                return stringBuilder.append(charSequence);
            }
            throw new IllegalArgumentException();
        }

        @Override
        public Appendable normalize(CharSequence charSequence, Appendable appendable) {
            if (appendable != charSequence) {
                try {
                    return appendable.append(charSequence);
                } catch (IOException iOException) {
                    throw new ICUUncheckedIOException(iOException);
                }
            }
            throw new IllegalArgumentException();
        }

        @Override
        public StringBuilder normalizeSecondAndAppend(StringBuilder stringBuilder, CharSequence charSequence) {
            if (stringBuilder != charSequence) {
                return stringBuilder.append(charSequence);
            }
            throw new IllegalArgumentException();
        }

        @Override
        public StringBuilder append(StringBuilder stringBuilder, CharSequence charSequence) {
            if (stringBuilder != charSequence) {
                return stringBuilder.append(charSequence);
            }
            throw new IllegalArgumentException();
        }

        @Override
        public String getDecomposition(int n) {
            return null;
        }

        @Override
        public boolean isNormalized(CharSequence charSequence) {
            return false;
        }

        @Override
        public Normalizer.QuickCheckResult quickCheck(CharSequence charSequence) {
            return Normalizer.YES;
        }

        @Override
        public int spanQuickCheckYes(CharSequence charSequence) {
            return charSequence.length();
        }

        @Override
        public boolean hasBoundaryBefore(int n) {
            return false;
        }

        @Override
        public boolean hasBoundaryAfter(int n) {
            return false;
        }

        @Override
        public boolean isInert(int n) {
            return false;
        }
    }
}

