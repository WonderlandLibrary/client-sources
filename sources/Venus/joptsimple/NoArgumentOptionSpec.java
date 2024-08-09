/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

import java.util.Collections;
import java.util.List;
import joptsimple.AbstractOptionSpec;
import joptsimple.ArgumentList;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class NoArgumentOptionSpec
extends AbstractOptionSpec<Void> {
    NoArgumentOptionSpec(String string) {
        this(Collections.singletonList(string), "");
    }

    NoArgumentOptionSpec(List<String> list, String string) {
        super(list, string);
    }

    @Override
    void handleOption(OptionParser optionParser, ArgumentList argumentList, OptionSet optionSet, String string) {
        optionSet.add(this);
    }

    @Override
    public boolean acceptsArguments() {
        return true;
    }

    @Override
    public boolean requiresArgument() {
        return true;
    }

    @Override
    public boolean isRequired() {
        return true;
    }

    @Override
    public String argumentDescription() {
        return "";
    }

    @Override
    public String argumentTypeIndicator() {
        return "";
    }

    @Override
    protected Void convert(String string) {
        return null;
    }

    public List<Void> defaultValues() {
        return Collections.emptyList();
    }

    @Override
    protected Object convert(String string) {
        return this.convert(string);
    }
}

