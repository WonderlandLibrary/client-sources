/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.Norm2AllModes;
import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.text.Normalizer2;
import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.SourceTargetUtility;
import com.ibm.icu.text.Transform;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.text.UnicodeSet;
import java.util.HashMap;
import java.util.Map;

final class NormalizationTransliterator
extends Transliterator {
    private final Normalizer2 norm2;
    static final Map<Normalizer2, SourceTargetUtility> SOURCE_CACHE = new HashMap<Normalizer2, SourceTargetUtility>();

    static void register() {
        Transliterator.registerFactory("Any-NFC", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new NormalizationTransliterator("NFC", Normalizer2.getNFCInstance(), null);
            }
        });
        Transliterator.registerFactory("Any-NFD", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new NormalizationTransliterator("NFD", Normalizer2.getNFDInstance(), null);
            }
        });
        Transliterator.registerFactory("Any-NFKC", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new NormalizationTransliterator("NFKC", Normalizer2.getNFKCInstance(), null);
            }
        });
        Transliterator.registerFactory("Any-NFKD", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new NormalizationTransliterator("NFKD", Normalizer2.getNFKDInstance(), null);
            }
        });
        Transliterator.registerFactory("Any-FCD", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new NormalizationTransliterator("FCD", Norm2AllModes.getFCDNormalizer2(), null);
            }
        });
        Transliterator.registerFactory("Any-FCC", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new NormalizationTransliterator("FCC", Norm2AllModes.getNFCInstance().fcc, null);
            }
        });
        Transliterator.registerSpecialInverse("NFC", "NFD", true);
        Transliterator.registerSpecialInverse("NFKC", "NFKD", true);
        Transliterator.registerSpecialInverse("FCC", "NFD", false);
        Transliterator.registerSpecialInverse("FCD", "FCD", false);
    }

    private NormalizationTransliterator(String string, Normalizer2 normalizer2) {
        super(string, null);
        this.norm2 = normalizer2;
    }

    @Override
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        int n = position.start;
        int n2 = position.limit;
        if (n >= n2) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        int n3 = replaceable.char32At(n);
        do {
            int n4 = n;
            stringBuilder.setLength(0);
            do {
                stringBuilder.appendCodePoint(n3);
            } while ((n += Character.charCount(n3)) < n2 && !this.norm2.hasBoundaryBefore(n3 = replaceable.char32At(n)));
            if (n == n2 && bl && !this.norm2.hasBoundaryAfter(n3)) {
                n = n4;
                break;
            }
            this.norm2.normalize((CharSequence)stringBuilder, stringBuilder2);
            if (Normalizer2Impl.UTF16Plus.equal(stringBuilder, stringBuilder2)) continue;
            replaceable.replace(n4, n, stringBuilder2.toString());
            int n5 = stringBuilder2.length() - (n - n4);
            n += n5;
            n2 += n5;
        } while (n < n2);
        position.start = n;
        position.contextLimit += n2 - position.limit;
        position.limit = n2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        SourceTargetUtility sourceTargetUtility;
        Map<Normalizer2, SourceTargetUtility> map = SOURCE_CACHE;
        synchronized (map) {
            sourceTargetUtility = SOURCE_CACHE.get(this.norm2);
            if (sourceTargetUtility == null) {
                sourceTargetUtility = new SourceTargetUtility(new NormalizingTransform(this.norm2), this.norm2);
                SOURCE_CACHE.put(this.norm2, sourceTargetUtility);
            }
        }
        sourceTargetUtility.addSourceTargetSet(this, unicodeSet, unicodeSet2, unicodeSet3);
    }

    NormalizationTransliterator(String string, Normalizer2 normalizer2, 1 var3_3) {
        this(string, normalizer2);
    }

    static class NormalizingTransform
    implements Transform<String, String> {
        final Normalizer2 norm2;

        public NormalizingTransform(Normalizer2 normalizer2) {
            this.norm2 = normalizer2;
        }

        @Override
        public String transform(String string) {
            return this.norm2.normalize(string);
        }

        @Override
        public Object transform(Object object) {
            return this.transform((String)object);
        }
    }
}

