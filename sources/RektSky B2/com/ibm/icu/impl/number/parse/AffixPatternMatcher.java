package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.number.*;

public class AffixPatternMatcher extends SeriesMatcher implements AffixUtils.TokenConsumer
{
    private final String affixPattern;
    private AffixTokenMatcherFactory factory;
    private IgnorablesMatcher ignorables;
    private int lastTypeOrCp;
    
    private AffixPatternMatcher(final String affixPattern) {
        this.affixPattern = affixPattern;
    }
    
    public static AffixPatternMatcher fromAffixPattern(final String affixPattern, final AffixTokenMatcherFactory factory, final int parseFlags) {
        if (affixPattern.isEmpty()) {
            return null;
        }
        final AffixPatternMatcher series = new AffixPatternMatcher(affixPattern);
        series.factory = factory;
        series.ignorables = ((0x0 != (parseFlags & 0x200)) ? null : factory.ignorables());
        series.lastTypeOrCp = 0;
        AffixUtils.iterateWithConsumer(affixPattern, series);
        series.factory = null;
        series.ignorables = null;
        series.lastTypeOrCp = 0;
        series.freeze();
        return series;
    }
    
    @Override
    public void consumeToken(final int typeOrCp) {
        if (this.ignorables != null && this.length() > 0 && (this.lastTypeOrCp < 0 || !this.ignorables.getSet().contains(this.lastTypeOrCp))) {
            this.addMatcher(this.ignorables);
        }
        if (typeOrCp < 0) {
            switch (typeOrCp) {
                case -1: {
                    this.addMatcher(this.factory.minusSign());
                    break;
                }
                case -2: {
                    this.addMatcher(this.factory.plusSign());
                    break;
                }
                case -3: {
                    this.addMatcher(this.factory.percent());
                    break;
                }
                case -4: {
                    this.addMatcher(this.factory.permille());
                    break;
                }
                case -9:
                case -8:
                case -7:
                case -6:
                case -5: {
                    this.addMatcher(this.factory.currency());
                    break;
                }
                default: {
                    throw new AssertionError();
                }
            }
        }
        else if (this.ignorables == null || !this.ignorables.getSet().contains(typeOrCp)) {
            this.addMatcher(CodePointMatcher.getInstance(typeOrCp));
        }
        this.lastTypeOrCp = typeOrCp;
    }
    
    public String getPattern() {
        return this.affixPattern;
    }
    
    @Override
    public boolean equals(final Object other) {
        return this == other || (other instanceof AffixPatternMatcher && this.affixPattern.equals(((AffixPatternMatcher)other).affixPattern));
    }
    
    @Override
    public int hashCode() {
        return this.affixPattern.hashCode();
    }
    
    @Override
    public String toString() {
        return this.affixPattern;
    }
}
