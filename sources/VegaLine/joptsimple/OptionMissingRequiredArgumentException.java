/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

import java.util.Arrays;
import java.util.Collection;
import joptsimple.OptionException;
import joptsimple.OptionSpec;

class OptionMissingRequiredArgumentException
extends OptionException {
    private static final long serialVersionUID = -1L;

    OptionMissingRequiredArgumentException(OptionSpec<?> option) {
        super((Collection<? extends OptionSpec<?>>)Arrays.asList(option));
    }

    @Override
    Object[] messageArguments() {
        return new Object[]{this.singleOptionString()};
    }
}

