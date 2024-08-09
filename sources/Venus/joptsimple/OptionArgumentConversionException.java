/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

import java.util.Collections;
import joptsimple.OptionException;
import joptsimple.OptionSpec;

class OptionArgumentConversionException
extends OptionException {
    private static final long serialVersionUID = -1L;
    private final String argument;

    OptionArgumentConversionException(OptionSpec<?> optionSpec, String string, Throwable throwable) {
        super(Collections.singleton(optionSpec), throwable);
        this.argument = string;
    }

    @Override
    Object[] messageArguments() {
        return new Object[]{this.argument, this.singleOptionString()};
    }
}

