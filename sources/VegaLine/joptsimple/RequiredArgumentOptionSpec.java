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
    RequiredArgumentOptionSpec(String option) {
        super(option, true);
    }

    RequiredArgumentOptionSpec(List<String> options, String description) {
        super(options, true, description);
    }

    @Override
    protected void detectOptionArgument(OptionParser parser, ArgumentList arguments, OptionSet detectedOptions) {
        if (!arguments.hasMore()) {
            throw new OptionMissingRequiredArgumentException(this);
        }
        this.addArguments(detectedOptions, arguments.next());
    }
}

