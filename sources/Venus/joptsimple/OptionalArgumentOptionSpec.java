/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

import java.util.List;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.ArgumentList;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

class OptionalArgumentOptionSpec<V>
extends ArgumentAcceptingOptionSpec<V> {
    OptionalArgumentOptionSpec(String string) {
        super(string, false);
    }

    OptionalArgumentOptionSpec(List<String> list, String string) {
        super(list, false, string);
    }

    @Override
    protected void detectOptionArgument(OptionParser optionParser, ArgumentList argumentList, OptionSet optionSet) {
        if (argumentList.hasMore()) {
            String string = argumentList.peek();
            if (!optionParser.looksLikeAnOption(string) && this.canConvertArgument(string)) {
                this.handleOptionArgument(optionParser, optionSet, argumentList);
            } else if (this.isArgumentOfNumberType() && this.canConvertArgument(string)) {
                this.addArguments(optionSet, argumentList.next());
            } else {
                optionSet.add(this);
            }
        } else {
            optionSet.add(this);
        }
    }

    private void handleOptionArgument(OptionParser optionParser, OptionSet optionSet, ArgumentList argumentList) {
        if (optionParser.posixlyCorrect()) {
            optionSet.add(this);
            optionParser.noMoreOptions();
        } else {
            this.addArguments(optionSet, argumentList.next());
        }
    }
}

