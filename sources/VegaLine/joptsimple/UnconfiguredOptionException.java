/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

import java.util.Collections;
import java.util.List;
import joptsimple.OptionException;

class UnconfiguredOptionException
extends OptionException {
    private static final long serialVersionUID = -1L;

    UnconfiguredOptionException(String option) {
        this(Collections.singletonList(option));
    }

    UnconfiguredOptionException(List<String> options) {
        super(options);
    }

    @Override
    Object[] messageArguments() {
        return new Object[]{this.multipleOptionString()};
    }
}

