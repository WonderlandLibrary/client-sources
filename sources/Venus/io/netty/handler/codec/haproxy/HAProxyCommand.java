/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.haproxy;

public enum HAProxyCommand {
    LOCAL(0),
    PROXY(1);

    private static final byte COMMAND_MASK = 15;
    private final byte byteValue;

    private HAProxyCommand(byte by) {
        this.byteValue = by;
    }

    public static HAProxyCommand valueOf(byte by) {
        int n = by & 0xF;
        switch ((byte)n) {
            case 1: {
                return PROXY;
            }
            case 0: {
                return LOCAL;
            }
        }
        throw new IllegalArgumentException("unknown command: " + n);
    }

    public byte byteValue() {
        return this.byteValue;
    }
}

