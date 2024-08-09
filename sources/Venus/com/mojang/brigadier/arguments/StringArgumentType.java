/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StringArgumentType
implements ArgumentType<String> {
    private final StringType type;

    private StringArgumentType(StringType stringType) {
        this.type = stringType;
    }

    public static StringArgumentType word() {
        return new StringArgumentType(StringType.SINGLE_WORD);
    }

    public static StringArgumentType string() {
        return new StringArgumentType(StringType.QUOTABLE_PHRASE);
    }

    public static StringArgumentType greedyString() {
        return new StringArgumentType(StringType.GREEDY_PHRASE);
    }

    public static String getString(CommandContext<?> commandContext, String string) {
        return commandContext.getArgument(string, String.class);
    }

    public StringType getType() {
        return this.type;
    }

    @Override
    public String parse(StringReader stringReader) throws CommandSyntaxException {
        if (this.type == StringType.GREEDY_PHRASE) {
            String string = stringReader.getRemaining();
            stringReader.setCursor(stringReader.getTotalLength());
            return string;
        }
        if (this.type == StringType.SINGLE_WORD) {
            return stringReader.readUnquotedString();
        }
        return stringReader.readString();
    }

    public String toString() {
        return "string()";
    }

    @Override
    public Collection<String> getExamples() {
        return this.type.getExamples();
    }

    public static String escapeIfRequired(String string) {
        for (char c : string.toCharArray()) {
            if (StringReader.isAllowedInUnquotedString(c)) continue;
            return StringArgumentType.escape(string);
        }
        return string;
    }

    private static String escape(String string) {
        StringBuilder stringBuilder = new StringBuilder("\"");
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == '\\' || c == '\"') {
                stringBuilder.append('\\');
            }
            stringBuilder.append(c);
        }
        stringBuilder.append("\"");
        return stringBuilder.toString();
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    public static enum StringType {
        SINGLE_WORD("word", "words_with_underscores"),
        QUOTABLE_PHRASE("\"quoted phrase\"", "word", "\"\""),
        GREEDY_PHRASE("word", "words with spaces", "\"and symbols\"");

        private final Collection<String> examples;

        private StringType(String ... stringArray) {
            this.examples = Arrays.asList(stringArray);
        }

        public Collection<String> getExamples() {
            return this.examples;
        }
    }
}

