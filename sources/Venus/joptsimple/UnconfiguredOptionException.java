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

    UnconfiguredOptionException(String string) {
        this(Collections.singletonList(string));
    }

    UnconfiguredOptionException(List<String> list) {
        super(list);
    }

    @Override
    Object[] messageArguments() {
        return new Object[]{this.multipleOptionString()};
    }
}

