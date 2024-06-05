package com.ibm.icu.impl.number;

import com.ibm.icu.text.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.util.*;
import java.util.*;

public class CompactData implements MultiplierProducer
{
    private static final String USE_FALLBACK = "<USE FALLBACK>";
    private final String[] patterns;
    private final byte[] multipliers;
    private byte largestMagnitude;
    private boolean isEmpty;
    private static final int COMPACT_MAX_DIGITS = 15;
    
    public CompactData() {
        this.patterns = new String[16 * StandardPlural.COUNT];
        this.multipliers = new byte[16];
        this.largestMagnitude = 0;
        this.isEmpty = true;
    }
    
    public void populate(final ULocale locale, final String nsName, final CompactDecimalFormat.CompactStyle compactStyle, final CompactType compactType) {
        assert this.isEmpty;
        final CompactDataSink sink = new CompactDataSink(this);
        final ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", locale);
        final boolean nsIsLatn = nsName.equals("latn");
        final boolean compactIsShort = compactStyle == CompactDecimalFormat.CompactStyle.SHORT;
        final StringBuilder resourceKey = new StringBuilder();
        getResourceBundleKey(nsName, compactStyle, compactType, resourceKey);
        rb.getAllItemsWithFallbackNoFail(resourceKey.toString(), sink);
        if (this.isEmpty && !nsIsLatn) {
            getResourceBundleKey("latn", compactStyle, compactType, resourceKey);
            rb.getAllItemsWithFallbackNoFail(resourceKey.toString(), sink);
        }
        if (this.isEmpty && !compactIsShort) {
            getResourceBundleKey(nsName, CompactDecimalFormat.CompactStyle.SHORT, compactType, resourceKey);
            rb.getAllItemsWithFallbackNoFail(resourceKey.toString(), sink);
        }
        if (this.isEmpty && !nsIsLatn && !compactIsShort) {
            getResourceBundleKey("latn", CompactDecimalFormat.CompactStyle.SHORT, compactType, resourceKey);
            rb.getAllItemsWithFallbackNoFail(resourceKey.toString(), sink);
        }
        if (this.isEmpty) {
            throw new ICUException("Could not load compact decimal data for locale " + locale);
        }
    }
    
    private static void getResourceBundleKey(final String nsName, final CompactDecimalFormat.CompactStyle compactStyle, final CompactType compactType, final StringBuilder sb) {
        sb.setLength(0);
        sb.append("NumberElements/");
        sb.append(nsName);
        sb.append((compactStyle == CompactDecimalFormat.CompactStyle.SHORT) ? "/patternsShort" : "/patternsLong");
        sb.append((compactType == CompactType.DECIMAL) ? "/decimalFormat" : "/currencyFormat");
    }
    
    public void populate(final Map<String, Map<String, String>> powersToPluralsToPatterns) {
        assert this.isEmpty;
        for (final Map.Entry<String, Map<String, String>> magnitudeEntry : powersToPluralsToPatterns.entrySet()) {
            final byte magnitude = (byte)(magnitudeEntry.getKey().length() - 1);
            for (final Map.Entry<String, String> pluralEntry : magnitudeEntry.getValue().entrySet()) {
                final StandardPlural plural = StandardPlural.fromString(pluralEntry.getKey().toString());
                final String patternString = pluralEntry.getValue().toString();
                this.patterns[getIndex(magnitude, plural)] = patternString;
                final int numZeros = countZeros(patternString);
                if (numZeros > 0) {
                    this.multipliers[magnitude] = (byte)(numZeros - magnitude - 1);
                    if (magnitude > this.largestMagnitude) {
                        this.largestMagnitude = magnitude;
                    }
                    this.isEmpty = false;
                }
            }
        }
    }
    
    @Override
    public int getMultiplier(int magnitude) {
        if (magnitude < 0) {
            return 0;
        }
        if (magnitude > this.largestMagnitude) {
            magnitude = this.largestMagnitude;
        }
        return this.multipliers[magnitude];
    }
    
    public String getPattern(int magnitude, final StandardPlural plural) {
        if (magnitude < 0) {
            return null;
        }
        if (magnitude > this.largestMagnitude) {
            magnitude = this.largestMagnitude;
        }
        String patternString = this.patterns[getIndex(magnitude, plural)];
        if (patternString == null && plural != StandardPlural.OTHER) {
            patternString = this.patterns[getIndex(magnitude, StandardPlural.OTHER)];
        }
        if (patternString == "<USE FALLBACK>") {
            patternString = null;
        }
        return patternString;
    }
    
    public void getUniquePatterns(final Set<String> output) {
        assert output.isEmpty();
        output.addAll(Arrays.asList(this.patterns));
        output.remove("<USE FALLBACK>");
        output.remove(null);
    }
    
    private static final int getIndex(final int magnitude, final StandardPlural plural) {
        return magnitude * StandardPlural.COUNT + plural.ordinal();
    }
    
    private static final int countZeros(final String patternString) {
        int numZeros = 0;
        for (int i = 0; i < patternString.length(); ++i) {
            if (patternString.charAt(i) == '0') {
                ++numZeros;
            }
            else if (numZeros > 0) {
                break;
            }
        }
        return numZeros;
    }
    
    public enum CompactType
    {
        DECIMAL, 
        CURRENCY;
    }
    
    private static final class CompactDataSink extends UResource.Sink
    {
        CompactData data;
        
        public CompactDataSink(final CompactData data) {
            this.data = data;
        }
        
        @Override
        public void put(final UResource.Key key, final UResource.Value value, final boolean isRoot) {
            final UResource.Table powersOfTenTable = value.getTable();
            for (int i3 = 0; powersOfTenTable.getKeyAndValue(i3, key, value); ++i3) {
                final byte magnitude = (byte)(key.length() - 1);
                byte multiplier = this.data.multipliers[magnitude];
                assert magnitude < 15;
                final UResource.Table pluralVariantsTable = value.getTable();
                for (int i4 = 0; pluralVariantsTable.getKeyAndValue(i4, key, value); ++i4) {
                    final StandardPlural plural = StandardPlural.fromString(key.toString());
                    if (this.data.patterns[getIndex(magnitude, plural)] == null) {
                        String patternString = value.toString();
                        if (patternString.equals("0")) {
                            patternString = "<USE FALLBACK>";
                        }
                        this.data.patterns[getIndex(magnitude, plural)] = patternString;
                        if (multiplier == 0) {
                            final int numZeros = countZeros(patternString);
                            if (numZeros > 0) {
                                multiplier = (byte)(numZeros - magnitude - 1);
                            }
                        }
                    }
                }
                if (this.data.multipliers[magnitude] == 0) {
                    this.data.multipliers[magnitude] = multiplier;
                    if (magnitude > this.data.largestMagnitude) {
                        this.data.largestMagnitude = magnitude;
                    }
                    this.data.isEmpty = false;
                }
                else {
                    assert this.data.multipliers[magnitude] == multiplier;
                }
            }
        }
    }
}
