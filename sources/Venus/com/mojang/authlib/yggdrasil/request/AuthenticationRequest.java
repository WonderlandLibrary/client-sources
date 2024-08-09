/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.yggdrasil.request;

import com.mojang.authlib.Agent;

public class AuthenticationRequest {
    private Agent agent;
    private String username;
    private String password;
    private String clientToken;
    private boolean requestUser = true;

    public AuthenticationRequest(Agent agent, String string, String string2, String string3) {
        this.agent = agent;
        this.username = string;
        this.password = string2;
        this.clientToken = string3;
    }
}

