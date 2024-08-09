/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.realms;

import net.minecraft.client.multiplayer.ServerAddress;

public class RealmsServerAddress {
    private final String field_230727_a_;
    private final int field_230728_b_;

    protected RealmsServerAddress(String string, int n) {
        this.field_230727_a_ = string;
        this.field_230728_b_ = n;
    }

    public String func_231412_a_() {
        return this.field_230727_a_;
    }

    public int func_231414_b_() {
        return this.field_230728_b_;
    }

    public static RealmsServerAddress func_231413_a_(String string) {
        ServerAddress serverAddress = ServerAddress.fromString(string);
        return new RealmsServerAddress(serverAddress.getIP(), serverAddress.getPort());
    }
}

