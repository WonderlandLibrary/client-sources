/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.MarkedYAMLException;

public class ConstructorException
extends MarkedYAMLException {
    private static final long serialVersionUID = -8816339931365239910L;

    protected ConstructorException(String string, Mark mark, String string2, Mark mark2, Throwable throwable) {
        super(string, mark, string2, mark2, throwable);
    }

    protected ConstructorException(String string, Mark mark, String string2, Mark mark2) {
        this(string, mark, string2, mark2, (Throwable)null);
    }
}

