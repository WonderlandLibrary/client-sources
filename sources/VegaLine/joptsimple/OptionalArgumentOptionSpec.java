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
    OptionalArgumentOptionSpec(String option) {
        super(option, false);
    }

    OptionalArgumentOptionSpec(List<String> options, String description) {
        super(options, false, description);
    }

    @Override
    protected void detectOptionArgument(OptionParser parser, ArgumentList arguments, OptionSet detectedOptions) {
        if (arguments.hasMore()) {
            String nextArgument = arguments.peek();
            if (!parser.looksLikeAnOption(nextArgument) && this.canConvertArgument(nextArgument)) {
                this.handleOptionArgument(parser, detectedOptions, arguments);
            } else if (this.isArgumentOfNumberType() && this.canConvertArgument(nextArgument)) {
                this.addArguments(detectedOptions, arguments.next());
            } else {
                detectedOptions.add(this);
            }
        } else {
            detectedOptions.add(this);
        }
    }

    private void handleOptionArgument(OptionParser parser, OptionSet detectedOptions, ArgumentList arguments) {
        if (parser.posixlyCorrect()) {
            detectedOptions.add(this);
            parser.noMoreOptions();
        } else {
            this.addArguments(detectedOptions, arguments.next());
        }
    }
}

