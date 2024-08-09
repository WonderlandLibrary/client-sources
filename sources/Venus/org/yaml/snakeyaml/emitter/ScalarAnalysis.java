/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.emitter;

public final class ScalarAnalysis {
    private final String scalar;
    private final boolean empty;
    private final boolean multiline;
    private final boolean allowFlowPlain;
    private final boolean allowBlockPlain;
    private final boolean allowSingleQuoted;
    private final boolean allowBlock;

    public ScalarAnalysis(String string, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6) {
        this.scalar = string;
        this.empty = bl;
        this.multiline = bl2;
        this.allowFlowPlain = bl3;
        this.allowBlockPlain = bl4;
        this.allowSingleQuoted = bl5;
        this.allowBlock = bl6;
    }

    public String getScalar() {
        return this.scalar;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public boolean isMultiline() {
        return this.multiline;
    }

    public boolean isAllowFlowPlain() {
        return this.allowFlowPlain;
    }

    public boolean isAllowBlockPlain() {
        return this.allowBlockPlain;
    }

    public boolean isAllowSingleQuoted() {
        return this.allowSingleQuoted;
    }

    public boolean isAllowBlock() {
        return this.allowBlock;
    }
}

