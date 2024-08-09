/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

import joptsimple.ArgumentList;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.ParserRules;
import joptsimple.UnrecognizedOptionException;

abstract class OptionParserState {
    OptionParserState() {
    }

    static OptionParserState noMoreOptions() {
        return new OptionParserState(){

            @Override
            protected void handleArgument(OptionParser optionParser, ArgumentList argumentList, OptionSet optionSet) {
                optionParser.handleNonOptionArgument(argumentList.next(), argumentList, optionSet);
            }
        };
    }

    static OptionParserState moreOptions(boolean bl) {
        return new OptionParserState(bl){
            final boolean val$posixlyCorrect;
            {
                this.val$posixlyCorrect = bl;
            }

            @Override
            protected void handleArgument(OptionParser optionParser, ArgumentList argumentList, OptionSet optionSet) {
                String string;
                block6: {
                    string = argumentList.next();
                    try {
                        if (ParserRules.isOptionTerminator(string)) {
                            optionParser.noMoreOptions();
                            return;
                        }
                        if (ParserRules.isLongOptionToken(string)) {
                            optionParser.handleLongOptionToken(string, argumentList, optionSet);
                            return;
                        }
                        if (ParserRules.isShortOptionToken(string)) {
                            optionParser.handleShortOptionToken(string, argumentList, optionSet);
                            return;
                        }
                    } catch (UnrecognizedOptionException unrecognizedOptionException) {
                        if (optionParser.doesAllowsUnrecognizedOptions()) break block6;
                        throw unrecognizedOptionException;
                    }
                }
                if (this.val$posixlyCorrect) {
                    optionParser.noMoreOptions();
                }
                optionParser.handleNonOptionArgument(string, argumentList, optionSet);
            }
        };
    }

    protected abstract void handleArgument(OptionParser var1, ArgumentList var2, OptionSet var3);
}

