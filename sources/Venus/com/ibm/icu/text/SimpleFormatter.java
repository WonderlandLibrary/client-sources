/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.SimpleFormatterImpl;

public final class SimpleFormatter {
    private final String compiledPattern;

    private SimpleFormatter(String string) {
        this.compiledPattern = string;
    }

    public static SimpleFormatter compile(CharSequence charSequence) {
        return SimpleFormatter.compileMinMaxArguments(charSequence, 0, Integer.MAX_VALUE);
    }

    public static SimpleFormatter compileMinMaxArguments(CharSequence charSequence, int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        String string = SimpleFormatterImpl.compileToStringMinMaxArguments(charSequence, stringBuilder, n, n2);
        return new SimpleFormatter(string);
    }

    public int getArgumentLimit() {
        return SimpleFormatterImpl.getArgumentLimit(this.compiledPattern);
    }

    public String format(CharSequence ... charSequenceArray) {
        return SimpleFormatterImpl.formatCompiledPattern(this.compiledPattern, charSequenceArray);
    }

    public StringBuilder formatAndAppend(StringBuilder stringBuilder, int[] nArray, CharSequence ... charSequenceArray) {
        return SimpleFormatterImpl.formatAndAppend(this.compiledPattern, stringBuilder, nArray, charSequenceArray);
    }

    public StringBuilder formatAndReplace(StringBuilder stringBuilder, int[] nArray, CharSequence ... charSequenceArray) {
        return SimpleFormatterImpl.formatAndReplace(this.compiledPattern, stringBuilder, nArray, charSequenceArray);
    }

    public String toString() {
        CharSequence[] charSequenceArray = new String[this.getArgumentLimit()];
        for (int i = 0; i < charSequenceArray.length; ++i) {
            charSequenceArray[i] = "{" + i + '}';
        }
        return this.formatAndAppend(new StringBuilder(), null, charSequenceArray).toString();
    }

    public String getTextWithNoArguments() {
        return SimpleFormatterImpl.getTextWithNoArguments(this.compiledPattern);
    }
}

