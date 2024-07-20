/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

import java.util.Collections;
import joptsimple.OptionException;

class IllegalOptionSpecificationException
extends OptionException {
    private static final long serialVersionUID = -1L;

    IllegalOptionSpecificationException(String option) {
        super(Collections.singletonList(option));
    }

    @Override
    Object[] messageArguments() {
        return new Object[]{this.singleOptionString()};
    }
}

