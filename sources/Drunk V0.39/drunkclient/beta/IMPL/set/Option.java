/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.set;

import drunkclient.beta.IMPL.set.Value;

public class Option<V>
extends Value<V> {
    public Option(String displayName, String name, V enabled) {
        super(displayName, name);
        this.setValue(enabled);
    }
}

