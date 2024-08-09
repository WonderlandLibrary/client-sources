/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.error;

public class YAMLException
extends RuntimeException {
    private static final long serialVersionUID = -4738336175050337570L;

    public YAMLException(String string) {
        super(string);
    }

    public YAMLException(Throwable throwable) {
        super(throwable);
    }

    public YAMLException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

