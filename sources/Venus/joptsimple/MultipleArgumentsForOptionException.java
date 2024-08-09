/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

import java.util.Collections;
import joptsimple.OptionException;
import joptsimple.OptionSpec;

class MultipleArgumentsForOptionException
extends OptionException {
    private static final long serialVersionUID = -1L;

    MultipleArgumentsForOptionException(OptionSpec<?> optionSpec) {
        super(Collections.singleton(optionSpec));
    }

    @Override
    Object[] messageArguments() {
        return new Object[]{this.singleOptionString()};
    }
}

