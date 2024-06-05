package com.ibm.icu.text;

import com.ibm.icu.lang.*;
import java.util.*;

class SourceTargetUtility
{
    final Transform<String, String> transform;
    final UnicodeSet sourceCache;
    final Set<String> sourceStrings;
    static final UnicodeSet NON_STARTERS;
    static Normalizer2 NFC;
    
    public SourceTargetUtility(final Transform<String, String> transform) {
        this(transform, null);
    }
    
    public SourceTargetUtility(final Transform<String, String> transform, final Normalizer2 normalizer) {
        this.transform = transform;
        if (normalizer != null) {
            this.sourceCache = new UnicodeSet("[:^ccc=0:]");
        }
        else {
            this.sourceCache = new UnicodeSet();
        }
        this.sourceStrings = new HashSet<String>();
        for (int i = 0; i <= 1114111; ++i) {
            String s = transform.transform(UTF16.valueOf(i));
            boolean added = false;
            if (!CharSequences.equals(i, s)) {
                this.sourceCache.add(i);
                added = true;
            }
            if (normalizer != null) {
                final String d = SourceTargetUtility.NFC.getDecomposition(i);
                if (d != null) {
                    s = transform.transform(d);
                    if (!d.equals(s)) {
                        this.sourceStrings.add(d);
                    }
                    if (!added) {
                        if (!normalizer.isInert(i)) {
                            this.sourceCache.add(i);
                        }
                    }
                }
            }
        }
        this.sourceCache.freeze();
    }
    
    public void addSourceTargetSet(final Transliterator transliterator, final UnicodeSet inputFilter, final UnicodeSet sourceSet, final UnicodeSet targetSet) {
        final UnicodeSet myFilter = transliterator.getFilterAsUnicodeSet(inputFilter);
        final UnicodeSet affectedCharacters = new UnicodeSet(this.sourceCache).retainAll(myFilter);
        sourceSet.addAll(affectedCharacters);
        for (final String s : affectedCharacters) {
            targetSet.addAll(this.transform.transform(s));
        }
        for (final String s : this.sourceStrings) {
            if (myFilter.containsAll(s)) {
                final String t = this.transform.transform(s);
                if (s.equals(t)) {
                    continue;
                }
                targetSet.addAll(t);
                sourceSet.addAll(s);
            }
        }
    }
    
    static {
        NON_STARTERS = new UnicodeSet("[:^ccc=0:]").freeze();
        SourceTargetUtility.NFC = Normalizer2.getNFCInstance();
    }
}
