/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.yggdrasil;

import com.mojang.authlib.Environment;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public enum YggdrasilEnvironment implements Environment
{
    PROD("https://authserver.mojang.com", "https://api.mojang.com", "https://sessionserver.mojang.com", "https://api.minecraftservices.com"),
    STAGING("https://yggdrasil-auth-staging.mojang.com", "https://api-staging.mojang.com", "https://yggdrasil-auth-session-staging.mojang.zone", "https://api-staging.minecraftservices.com");

    private final String authHost;
    private final String accountsHost;
    private final String sessionHost;
    private final String servicesHost;

    private YggdrasilEnvironment(String string2, String string3, String string4, String string5) {
        this.authHost = string2;
        this.accountsHost = string3;
        this.sessionHost = string4;
        this.servicesHost = string5;
    }

    @Override
    public String getAuthHost() {
        return this.authHost;
    }

    @Override
    public String getAccountsHost() {
        return this.accountsHost;
    }

    @Override
    public String getSessionHost() {
        return this.sessionHost;
    }

    @Override
    public String getServicesHost() {
        return this.servicesHost;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String asString() {
        return new StringJoiner(", ", "", "").add("authHost='" + this.authHost + "'").add("accountsHost='" + this.accountsHost + "'").add("sessionHost='" + this.sessionHost + "'").add("servicesHost='" + this.servicesHost + "'").add("name='" + this.getName() + "'").toString();
    }

    public static Optional<YggdrasilEnvironment> fromString(@Nullable String string) {
        return Stream.of(YggdrasilEnvironment.values()).filter(arg_0 -> YggdrasilEnvironment.lambda$fromString$0(string, arg_0)).findFirst();
    }

    private static boolean lambda$fromString$0(String string, YggdrasilEnvironment yggdrasilEnvironment) {
        return string != null && string.equalsIgnoreCase(yggdrasilEnvironment.name());
    }
}

