/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.number.AffixUtils;
import com.ibm.icu.impl.number.parse.AffixTokenMatcherFactory;
import com.ibm.icu.impl.number.parse.CodePointMatcher;
import com.ibm.icu.impl.number.parse.IgnorablesMatcher;
import com.ibm.icu.impl.number.parse.SeriesMatcher;

public class AffixPatternMatcher
extends SeriesMatcher
implements AffixUtils.TokenConsumer {
    private final String affixPattern;
    private AffixTokenMatcherFactory factory;
    private IgnorablesMatcher ignorables;
    private int lastTypeOrCp;

    private AffixPatternMatcher(String string) {
        this.affixPattern = string;
    }

    public static AffixPatternMatcher fromAffixPattern(String string, AffixTokenMatcherFactory affixTokenMatcherFactory, int n) {
        if (string.isEmpty()) {
            return null;
        }
        AffixPatternMatcher affixPatternMatcher = new AffixPatternMatcher(string);
        affixPatternMatcher.factory = affixTokenMatcherFactory;
        affixPatternMatcher.ignorables = 0 != (n & 0x200) ? null : affixTokenMatcherFactory.ignorables();
        affixPatternMatcher.lastTypeOrCp = 0;
        AffixUtils.iterateWithConsumer(string, affixPatternMatcher);
        affixPatternMatcher.factory = null;
        affixPatternMatcher.ignorables = null;
        affixPatternMatcher.lastTypeOrCp = 0;
        affixPatternMatcher.freeze();
        return affixPatternMatcher;
    }

    @Override
    public void consumeToken(int n) {
        block10: {
            block9: {
                if (!(this.ignorables == null || this.length() <= 0 || this.lastTypeOrCp >= 0 && this.ignorables.getSet().contains(this.lastTypeOrCp))) {
                    this.addMatcher(this.ignorables);
                }
                if (n >= 0) break block9;
                switch (n) {
                    case -1: {
                        this.addMatcher(this.factory.minusSign());
                        break block10;
                    }
                    case -2: {
                        this.addMatcher(this.factory.plusSign());
                        break block10;
                    }
                    case -3: {
                        this.addMatcher(this.factory.percent());
                        break block10;
                    }
                    case -4: {
                        this.addMatcher(this.factory.permille());
                        break block10;
                    }
                    case -9: 
                    case -8: 
                    case -7: 
                    case -6: 
                    case -5: {
                        this.addMatcher(this.factory.currency());
                        break block10;
                    }
                    default: {
                        throw new AssertionError();
                    }
                }
            }
            if (this.ignorables == null || !this.ignorables.getSet().contains(n)) {
                this.addMatcher(CodePointMatcher.getInstance(n));
            }
        }
        this.lastTypeOrCp = n;
    }

    public String getPattern() {
        return this.affixPattern;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof AffixPatternMatcher)) {
            return true;
        }
        return this.affixPattern.equals(((AffixPatternMatcher)object).affixPattern);
    }

    public int hashCode() {
        return this.affixPattern.hashCode();
    }

    @Override
    public String toString() {
        return this.affixPattern;
    }
}

