/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

import java.util.List;
import joptsimple.IllegalOptionSpecificationException;

final class ParserRules {
    static final char HYPHEN_CHAR = '-';
    static final String HYPHEN = String.valueOf('-');
    static final String DOUBLE_HYPHEN = "--";
    static final String OPTION_TERMINATOR = "--";
    static final String RESERVED_FOR_EXTENSIONS = "W";

    private ParserRules() {
        throw new UnsupportedOperationException();
    }

    static boolean isShortOptionToken(String argument) {
        return argument.startsWith(HYPHEN) && !HYPHEN.equals(argument) && !ParserRules.isLongOptionToken(argument);
    }

    static boolean isLongOptionToken(String argument) {
        return argument.startsWith("--") && !ParserRules.isOptionTerminator(argument);
    }

    static boolean isOptionTerminator(String argument) {
        return "--".equals(argument);
    }

    static void ensureLegalOption(String option) {
        if (option.startsWith(HYPHEN)) {
            throw new IllegalOptionSpecificationException(String.valueOf(option));
        }
        for (int i = 0; i < option.length(); ++i) {
            ParserRules.ensureLegalOptionCharacter(option.charAt(i));
        }
    }

    static void ensureLegalOptions(List<String> options) {
        for (String each : options) {
            ParserRules.ensureLegalOption(each);
        }
    }

    private static void ensureLegalOptionCharacter(char option) {
        if (!Character.isLetterOrDigit(option) && !ParserRules.isAllowedPunctuation(option)) {
            throw new IllegalOptionSpecificationException(String.valueOf(option));
        }
    }

    private static boolean isAllowedPunctuation(char option) {
        String allowedPunctuation = "?._-";
        return allowedPunctuation.indexOf(option) != -1;
    }
}

