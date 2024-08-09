/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.scanner;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.MarkedYAMLException;

public class ScannerException
extends MarkedYAMLException {
    private static final long serialVersionUID = 4782293188600445954L;

    public ScannerException(String string, Mark mark, String string2, Mark mark2, String string3) {
        super(string, mark, string2, mark2, string3);
    }

    public ScannerException(String string, Mark mark, String string2, Mark mark2) {
        this(string, mark, string2, mark2, (String)null);
    }
}

