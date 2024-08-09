/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

import java.util.NoSuchElementException;
import joptsimple.AbstractOptionSpec;
import joptsimple.AlternativeLongOptionSpec;
import joptsimple.NoArgumentOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionalArgumentOptionSpec;
import joptsimple.ParserRules;
import joptsimple.RequiredArgumentOptionSpec;

class OptionSpecTokenizer {
    private static final char POSIXLY_CORRECT_MARKER = '+';
    private static final char HELP_MARKER = '*';
    private String specification;
    private int index;

    OptionSpecTokenizer(String string) {
        if (string == null) {
            throw new NullPointerException("null option specification");
        }
        this.specification = string;
    }

    boolean hasMore() {
        return this.index < this.specification.length();
    }

    AbstractOptionSpec<?> next() {
        NoArgumentOptionSpec noArgumentOptionSpec;
        if (!this.hasMore()) {
            throw new NoSuchElementException();
        }
        String string = String.valueOf(this.specification.charAt(this.index));
        ++this.index;
        if ("W".equals(string) && (noArgumentOptionSpec = this.handleReservedForExtensionsToken()) != null) {
            return noArgumentOptionSpec;
        }
        ParserRules.ensureLegalOption(string);
        if (this.hasMore()) {
            boolean bl = false;
            if (this.specification.charAt(this.index) == '*') {
                bl = true;
                ++this.index;
            }
            AbstractOptionSpec abstractOptionSpec = noArgumentOptionSpec = this.hasMore() && this.specification.charAt(this.index) == ':' ? this.handleArgumentAcceptingOption(string) : new NoArgumentOptionSpec(string);
            if (bl) {
                noArgumentOptionSpec.forHelp();
            }
        } else {
            noArgumentOptionSpec = new NoArgumentOptionSpec(string);
        }
        return noArgumentOptionSpec;
    }

    void configure(OptionParser optionParser) {
        this.adjustForPosixlyCorrect(optionParser);
        while (this.hasMore()) {
            optionParser.recognize(this.next());
        }
    }

    private void adjustForPosixlyCorrect(OptionParser optionParser) {
        if ('+' == this.specification.charAt(0)) {
            optionParser.posixlyCorrect(false);
            this.specification = this.specification.substring(1);
        }
    }

    private AbstractOptionSpec<?> handleReservedForExtensionsToken() {
        if (!this.hasMore()) {
            return new NoArgumentOptionSpec("W");
        }
        if (this.specification.charAt(this.index) == ';') {
            ++this.index;
            return new AlternativeLongOptionSpec();
        }
        return null;
    }

    private AbstractOptionSpec<?> handleArgumentAcceptingOption(String string) {
        ++this.index;
        if (this.hasMore() && this.specification.charAt(this.index) == ':') {
            ++this.index;
            return new OptionalArgumentOptionSpec(string);
        }
        return new RequiredArgumentOptionSpec(string);
    }
}

