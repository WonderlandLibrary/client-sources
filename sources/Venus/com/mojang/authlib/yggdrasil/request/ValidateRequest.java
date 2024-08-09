/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.yggdrasil.request;

public class ValidateRequest {
    private String clientToken;
    private String accessToken;

    public ValidateRequest(String string, String string2) {
        this.clientToken = string2;
        this.accessToken = string;
    }
}

