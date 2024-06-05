package com.ibm.icu.text;

import com.ibm.icu.impl.*;
import java.util.*;

final class NormalizationTransliterator extends Transliterator
{
    private final Normalizer2 norm2;
    static final Map<Normalizer2, SourceTargetUtility> SOURCE_CACHE;
    
    static void register() {
        Transliterator.registerFactory("Any-NFC", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new NormalizationTransliterator("NFC", Normalizer2.getNFCInstance(), null);
            }
        });
        Transliterator.registerFactory("Any-NFD", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new NormalizationTransliterator("NFD", Normalizer2.getNFDInstance(), null);
            }
        });
        Transliterator.registerFactory("Any-NFKC", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new NormalizationTransliterator("NFKC", Normalizer2.getNFKCInstance(), null);
            }
        });
        Transliterator.registerFactory("Any-NFKD", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new NormalizationTransliterator("NFKD", Normalizer2.getNFKDInstance(), null);
            }
        });
        Transliterator.registerFactory("Any-FCD", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new NormalizationTransliterator("FCD", Norm2AllModes.getFCDNormalizer2(), null);
            }
        });
        Transliterator.registerFactory("Any-FCC", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new NormalizationTransliterator("FCC", Norm2AllModes.getNFCInstance().fcc, null);
            }
        });
        Transliterator.registerSpecialInverse("NFC", "NFD", true);
        Transliterator.registerSpecialInverse("NFKC", "NFKD", true);
        Transliterator.registerSpecialInverse("FCC", "NFD", false);
        Transliterator.registerSpecialInverse("FCD", "FCD", false);
    }
    
    private NormalizationTransliterator(final String id, final Normalizer2 n2) {
        super(id, null);
        this.norm2 = n2;
    }
    
    @Override
    protected void handleTransliterate(final Replaceable text, final Position offsets, final boolean isIncremental) {
        int start = offsets.start;
        int limit = offsets.limit;
        if (start >= limit) {
            return;
        }
        final StringBuilder segment = new StringBuilder();
        final StringBuilder normalized = new StringBuilder();
        int c = text.char32At(start);
        do {
            final int prev = start;
            segment.setLength(0);
            do {
                segment.appendCodePoint(c);
                start += Character.charCount(c);
            } while (start < limit && !this.norm2.hasBoundaryBefore(c = text.char32At(start)));
            if (start == limit && isIncremental && !this.norm2.hasBoundaryAfter(c)) {
                start = prev;
                break;
            }
            this.norm2.normalize(segment, normalized);
            if (Normalizer2Impl.UTF16Plus.equal(segment, normalized)) {
                continue;
            }
            text.replace(prev, start, normalized.toString());
            final int delta = normalized.length() - (start - prev);
            start += delta;
            limit += delta;
        } while (start < limit);
        offsets.start = start;
        offsets.contextLimit += limit - offsets.limit;
        offsets.limit = limit;
    }
    
    @Override
    public void addSourceTargetSet(final UnicodeSet inputFilter, final UnicodeSet sourceSet, final UnicodeSet targetSet) {
        SourceTargetUtility cache;
        synchronized (NormalizationTransliterator.SOURCE_CACHE) {
            cache = NormalizationTransliterator.SOURCE_CACHE.get(this.norm2);
            if (cache == null) {
                cache = new SourceTargetUtility(new NormalizingTransform(this.norm2), this.norm2);
                NormalizationTransliterator.SOURCE_CACHE.put(this.norm2, cache);
            }
        }
        cache.addSourceTargetSet(this, inputFilter, sourceSet, targetSet);
    }
    
    static {
        SOURCE_CACHE = new HashMap<Normalizer2, SourceTargetUtility>();
    }
    
    static class NormalizingTransform implements Transform<String, String>
    {
        final Normalizer2 norm2;
        
        public NormalizingTransform(final Normalizer2 norm2) {
            this.norm2 = norm2;
        }
        
        @Override
        public String transform(final String source) {
            return this.norm2.normalize(source);
        }
    }
}
