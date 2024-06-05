package com.ibm.icu.text;

import com.ibm.icu.impl.*;

public final class SimpleFormatter
{
    private final String compiledPattern;
    
    private SimpleFormatter(final String compiledPattern) {
        this.compiledPattern = compiledPattern;
    }
    
    public static SimpleFormatter compile(final CharSequence pattern) {
        return compileMinMaxArguments(pattern, 0, Integer.MAX_VALUE);
    }
    
    public static SimpleFormatter compileMinMaxArguments(final CharSequence pattern, final int min, final int max) {
        final StringBuilder sb = new StringBuilder();
        final String compiledPattern = SimpleFormatterImpl.compileToStringMinMaxArguments(pattern, sb, min, max);
        return new SimpleFormatter(compiledPattern);
    }
    
    public int getArgumentLimit() {
        return SimpleFormatterImpl.getArgumentLimit(this.compiledPattern);
    }
    
    public String format(final CharSequence... values) {
        return SimpleFormatterImpl.formatCompiledPattern(this.compiledPattern, values);
    }
    
    public StringBuilder formatAndAppend(final StringBuilder appendTo, final int[] offsets, final CharSequence... values) {
        return SimpleFormatterImpl.formatAndAppend(this.compiledPattern, appendTo, offsets, values);
    }
    
    public StringBuilder formatAndReplace(final StringBuilder result, final int[] offsets, final CharSequence... values) {
        return SimpleFormatterImpl.formatAndReplace(this.compiledPattern, result, offsets, values);
    }
    
    @Override
    public String toString() {
        final String[] values = new String[this.getArgumentLimit()];
        for (int i = 0; i < values.length; ++i) {
            values[i] = "{" + i + '}';
        }
        return this.formatAndAppend(new StringBuilder(), null, (CharSequence[])values).toString();
    }
    
    public String getTextWithNoArguments() {
        return SimpleFormatterImpl.getTextWithNoArguments(this.compiledPattern);
    }
}
