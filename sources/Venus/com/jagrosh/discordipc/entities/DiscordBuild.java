/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jagrosh.discordipc.entities;

public enum DiscordBuild {
    CANARY("//canary.discordapp.com/api"),
    PTB("//ptb.discordapp.com/api"),
    STABLE("//discordapp.com/api"),
    ANY;

    private final String endpoint;

    private DiscordBuild(String string2) {
        this.endpoint = string2;
    }

    private DiscordBuild() {
        this(null);
    }

    public static DiscordBuild from(String string) {
        for (DiscordBuild discordBuild : DiscordBuild.values()) {
            if (discordBuild.endpoint == null || !discordBuild.endpoint.equals(string)) continue;
            return discordBuild;
        }
        return ANY;
    }
}

