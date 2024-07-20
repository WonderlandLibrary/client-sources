/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

import java.util.Collection;
import java.util.List;
import joptsimple.OptionException;
import joptsimple.OptionSpec;

class MissingRequiredOptionsException
extends OptionException {
    private static final long serialVersionUID = -1L;

    protected MissingRequiredOptionsException(List<? extends OptionSpec<?>> missingRequiredOptions) {
        super((Collection<? extends OptionSpec<?>>)missingRequiredOptions);
    }

    @Override
    Object[] messageArguments() {
        return new Object[]{this.multipleOptionString()};
    }
}

