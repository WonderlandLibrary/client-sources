package com.ibm.icu.impl.number;

import com.ibm.icu.text.*;

public class Padder
{
    public static final String FALLBACK_PADDING_STRING = " ";
    public static final Padder NONE;
    String paddingString;
    int targetWidth;
    PadPosition position;
    
    public Padder(final String paddingString, final int targetWidth, final PadPosition position) {
        this.paddingString = ((paddingString == null) ? " " : paddingString);
        this.targetWidth = targetWidth;
        this.position = ((position == null) ? PadPosition.BEFORE_PREFIX : position);
    }
    
    public static Padder none() {
        return Padder.NONE;
    }
    
    public static Padder codePoints(final int cp, final int targetWidth, final PadPosition position) {
        if (targetWidth >= 0) {
            final String paddingString = String.valueOf(Character.toChars(cp));
            return new Padder(paddingString, targetWidth, position);
        }
        throw new IllegalArgumentException("Padding width must not be negative");
    }
    
    public static Padder forProperties(final DecimalFormatProperties properties) {
        return new Padder(properties.getPadString(), properties.getFormatWidth(), properties.getPadPosition());
    }
    
    public boolean isValid() {
        return this.targetWidth > 0;
    }
    
    public int padAndApply(final Modifier mod1, final Modifier mod2, final NumberStringBuilder string, final int leftIndex, final int rightIndex) {
        final int modLength = mod1.getCodePointCount() + mod2.getCodePointCount();
        final int requiredPadding = this.targetWidth - modLength - string.codePointCount();
        assert leftIndex == 0 && rightIndex == string.length();
        int length = 0;
        if (requiredPadding <= 0) {
            length += mod1.apply(string, leftIndex, rightIndex);
            length += mod2.apply(string, leftIndex, rightIndex + length);
            return length;
        }
        if (this.position == PadPosition.AFTER_PREFIX) {
            length += addPaddingHelper(this.paddingString, requiredPadding, string, leftIndex);
        }
        else if (this.position == PadPosition.BEFORE_SUFFIX) {
            length += addPaddingHelper(this.paddingString, requiredPadding, string, rightIndex + length);
        }
        length += mod1.apply(string, leftIndex, rightIndex + length);
        length += mod2.apply(string, leftIndex, rightIndex + length);
        if (this.position == PadPosition.BEFORE_PREFIX) {
            length += addPaddingHelper(this.paddingString, requiredPadding, string, leftIndex);
        }
        else if (this.position == PadPosition.AFTER_SUFFIX) {
            length += addPaddingHelper(this.paddingString, requiredPadding, string, rightIndex + length);
        }
        return length;
    }
    
    private static int addPaddingHelper(final String paddingString, final int requiredPadding, final NumberStringBuilder string, final int index) {
        for (int i = 0; i < requiredPadding; ++i) {
            string.insert(index, paddingString, null);
        }
        return paddingString.length() * requiredPadding;
    }
    
    static {
        NONE = new Padder(null, -1, null);
    }
    
    public enum PadPosition
    {
        BEFORE_PREFIX, 
        AFTER_PREFIX, 
        BEFORE_SUFFIX, 
        AFTER_SUFFIX;
        
        public static PadPosition fromOld(final int old) {
            switch (old) {
                case 0: {
                    return PadPosition.BEFORE_PREFIX;
                }
                case 1: {
                    return PadPosition.AFTER_PREFIX;
                }
                case 2: {
                    return PadPosition.BEFORE_SUFFIX;
                }
                case 3: {
                    return PadPosition.AFTER_SUFFIX;
                }
                default: {
                    throw new IllegalArgumentException("Don't know how to map " + old);
                }
            }
        }
        
        public int toOld() {
            switch (this) {
                case BEFORE_PREFIX: {
                    return 0;
                }
                case AFTER_PREFIX: {
                    return 1;
                }
                case BEFORE_SUFFIX: {
                    return 2;
                }
                case AFTER_SUFFIX: {
                    return 3;
                }
                default: {
                    return -1;
                }
            }
        }
    }
}
