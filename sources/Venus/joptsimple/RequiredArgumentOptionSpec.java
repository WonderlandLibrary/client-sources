/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

import java.util.List;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.ArgumentList;
import joptsimple.OptionMissingRequiredArgumentException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

class RequiredArgumentOptionSpec<V>
extends ArgumentAcceptingOptionSpec<V> {
    RequiredArgumentOptionSpec(String string) {
        super(string, true);
    }

    RequiredArgumentOptionSpec(List<String> list, String string) {
        super(list, true, string);
    }

    @Override
    protected void detectOptionArgument(OptionParser optionParser, ArgumentList argumentList, OptionSet optionSet) {
        if (!argumentList.hasMore()) {
            throw new OptionMissingRequiredArgumentException(this);
        }
        this.addArguments(optionSet, argumentList.next());
    }
}

