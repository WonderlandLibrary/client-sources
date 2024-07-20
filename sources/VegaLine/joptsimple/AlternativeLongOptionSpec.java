/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

import java.util.Collections;
import java.util.Locale;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.ArgumentList;
import joptsimple.OptionMissingRequiredArgumentException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.internal.Messages;

class AlternativeLongOptionSpec
extends ArgumentAcceptingOptionSpec<String> {
    AlternativeLongOptionSpec() {
        super(Collections.singletonList("W"), true, Messages.message(Locale.getDefault(), "joptsimple.HelpFormatterMessages", AlternativeLongOptionSpec.class, "description", new Object[0]));
        this.describedAs(Messages.message(Locale.getDefault(), "joptsimple.HelpFormatterMessages", AlternativeLongOptionSpec.class, "arg.description", new Object[0]));
    }

    @Override
    protected void detectOptionArgument(OptionParser parser, ArgumentList arguments, OptionSet detectedOptions) {
        if (!arguments.hasMore()) {
            throw new OptionMissingRequiredArgumentException(this);
        }
        arguments.treatNextAsLongOption();
    }
}

