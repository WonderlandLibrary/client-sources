/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.MarkedYAMLException;

public class ParserException
extends MarkedYAMLException {
    private static final long serialVersionUID = -2349253802798398038L;

    public ParserException(String string, Mark mark, String string2, Mark mark2) {
        super(string, mark, string2, mark2, null, null);
    }
}

