package com.ibm.icu.impl;

public final class SimpleFormatterImpl
{
    private static final int ARG_NUM_LIMIT = 256;
    private static final char LEN1_CHAR = '\u0101';
    private static final char LEN2_CHAR = '\u0102';
    private static final char LEN3_CHAR = '\u0103';
    private static final char SEGMENT_LENGTH_ARGUMENT_CHAR = '\uffff';
    private static final int MAX_SEGMENT_LENGTH = 65279;
    private static final String[][] COMMON_PATTERNS;
    
    private SimpleFormatterImpl() {
    }
    
    public static String compileToStringMinMaxArguments(final CharSequence pattern, final StringBuilder sb, final int min, final int max) {
        if (min <= 2 && 2 <= max) {
            final String[][] common_PATTERNS = SimpleFormatterImpl.COMMON_PATTERNS;
            final int length = common_PATTERNS.length;
            int j = 0;
            while (j < length) {
                final String[] pair = common_PATTERNS[j];
                if (pair[0].contentEquals(pattern)) {
                    assert pair[1].charAt(0) == '\u0002';
                    return pair[1];
                }
                else {
                    ++j;
                }
            }
        }
        final int patternLength = pattern.length();
        sb.ensureCapacity(patternLength);
        sb.setLength(1);
        int textLength = 0;
        int maxArg = -1;
        boolean inQuote = false;
        int i = 0;
        while (i < patternLength) {
            char c = pattern.charAt(i++);
            if (c == '\'') {
                if (i < patternLength && (c = pattern.charAt(i)) == '\'') {
                    ++i;
                }
                else {
                    if (inQuote) {
                        inQuote = false;
                        continue;
                    }
                    if (c == '{' || c == '}') {
                        ++i;
                        inQuote = true;
                    }
                    else {
                        c = '\'';
                    }
                }
            }
            else if (!inQuote && c == '{') {
                if (textLength > 0) {
                    sb.setCharAt(sb.length() - textLength - 1, (char)(256 + textLength));
                    textLength = 0;
                }
                int argNumber;
                if (i + 1 < patternLength && 0 <= (argNumber = pattern.charAt(i) - '0') && argNumber <= 9 && pattern.charAt(i + 1) == '}') {
                    i += 2;
                }
                else {
                    final int argStart = i - 1;
                    argNumber = -1;
                    if (i < patternLength && '1' <= (c = pattern.charAt(i++)) && c <= '9') {
                        argNumber = c - '0';
                        while (i < patternLength && '0' <= (c = pattern.charAt(i++)) && c <= '9') {
                            argNumber = argNumber * 10 + (c - '0');
                            if (argNumber >= 256) {
                                break;
                            }
                        }
                    }
                    if (argNumber < 0 || c != '}') {
                        throw new IllegalArgumentException("Argument syntax error in pattern \"" + (Object)pattern + "\" at index " + argStart + ": " + (Object)pattern.subSequence(argStart, i));
                    }
                }
                if (argNumber > maxArg) {
                    maxArg = argNumber;
                }
                sb.append((char)argNumber);
                continue;
            }
            if (textLength == 0) {
                sb.append('\uffff');
            }
            sb.append(c);
            if (++textLength == 65279) {
                textLength = 0;
            }
        }
        if (textLength > 0) {
            sb.setCharAt(sb.length() - textLength - 1, (char)(256 + textLength));
        }
        final int argCount = maxArg + 1;
        if (argCount < min) {
            throw new IllegalArgumentException("Fewer than minimum " + min + " arguments in pattern \"" + (Object)pattern + "\"");
        }
        if (argCount > max) {
            throw new IllegalArgumentException("More than maximum " + max + " arguments in pattern \"" + (Object)pattern + "\"");
        }
        sb.setCharAt(0, (char)argCount);
        return sb.toString();
    }
    
    public static int getArgumentLimit(final String compiledPattern) {
        return compiledPattern.charAt(0);
    }
    
    public static String formatCompiledPattern(final String compiledPattern, final CharSequence... values) {
        return formatAndAppend(compiledPattern, new StringBuilder(), null, values).toString();
    }
    
    public static String formatRawPattern(final String pattern, final int min, final int max, final CharSequence... values) {
        final StringBuilder sb = new StringBuilder();
        final String compiledPattern = compileToStringMinMaxArguments(pattern, sb, min, max);
        sb.setLength(0);
        return formatAndAppend(compiledPattern, sb, null, values).toString();
    }
    
    public static StringBuilder formatAndAppend(final String compiledPattern, final StringBuilder appendTo, final int[] offsets, final CharSequence... values) {
        final int valuesLength = (values != null) ? values.length : 0;
        if (valuesLength < getArgumentLimit(compiledPattern)) {
            throw new IllegalArgumentException("Too few values.");
        }
        return format(compiledPattern, values, appendTo, null, true, offsets);
    }
    
    public static StringBuilder formatAndReplace(final String compiledPattern, final StringBuilder result, final int[] offsets, final CharSequence... values) {
        final int valuesLength = (values != null) ? values.length : 0;
        if (valuesLength < getArgumentLimit(compiledPattern)) {
            throw new IllegalArgumentException("Too few values.");
        }
        int firstArg = -1;
        String resultCopy = null;
        if (getArgumentLimit(compiledPattern) > 0) {
            int i = 1;
            while (i < compiledPattern.length()) {
                final int n = compiledPattern.charAt(i++);
                if (n < 256) {
                    if (values[n] != result) {
                        continue;
                    }
                    if (i == 2) {
                        firstArg = n;
                    }
                    else {
                        if (resultCopy != null) {
                            continue;
                        }
                        resultCopy = result.toString();
                    }
                }
                else {
                    i += n - 256;
                }
            }
        }
        if (firstArg < 0) {
            result.setLength(0);
        }
        return format(compiledPattern, values, result, resultCopy, false, offsets);
    }
    
    public static String getTextWithNoArguments(final String compiledPattern) {
        final int capacity = compiledPattern.length() - 1 - getArgumentLimit(compiledPattern);
        final StringBuilder sb = new StringBuilder(capacity);
        int limit = 0;
        for (int i = 1; i < compiledPattern.length(); i = limit) {
            final int segmentLength = compiledPattern.charAt(i++) - '\u0100';
            if (segmentLength > 0) {
                limit = i + segmentLength;
                sb.append(compiledPattern, i, limit);
            }
        }
        return sb.toString();
    }
    
    private static StringBuilder format(final String compiledPattern, final CharSequence[] values, final StringBuilder result, final String resultCopy, final boolean forbidResultAsValue, final int[] offsets) {
        int offsetsLength;
        if (offsets == null) {
            offsetsLength = 0;
        }
        else {
            offsetsLength = offsets.length;
            for (int i = 0; i < offsetsLength; ++i) {
                offsets[i] = -1;
            }
        }
        int i = 1;
        while (i < compiledPattern.length()) {
            final int n = compiledPattern.charAt(i++);
            if (n < 256) {
                final CharSequence value = values[n];
                if (value == result) {
                    if (forbidResultAsValue) {
                        throw new IllegalArgumentException("Value must not be same object as result");
                    }
                    if (i == 2) {
                        if (n >= offsetsLength) {
                            continue;
                        }
                        offsets[n] = 0;
                    }
                    else {
                        if (n < offsetsLength) {
                            offsets[n] = result.length();
                        }
                        result.append(resultCopy);
                    }
                }
                else {
                    if (n < offsetsLength) {
                        offsets[n] = result.length();
                    }
                    result.append(value);
                }
            }
            else {
                final int limit = i + (n - 256);
                result.append(compiledPattern, i, limit);
                i = limit;
            }
        }
        return result;
    }
    
    static {
        COMMON_PATTERNS = new String[][] { { "{0} {1}", "\u0002\u0000\u0101 \u0001" }, { "{0} ({1})", "\u0002\u0000\u0102 (\u0001\u0101)" }, { "{0}, {1}", "\u0002\u0000\u0102, \u0001" }, { "{0} \u2013 {1}", "\u0002\u0000\u0103 \u2013 \u0001" } };
    }
}
