/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.yggdrasil.response;

public class Response {
    private String error;
    private String errorMessage;
    private String cause;

    public String getError() {
        return this.error;
    }

    public String getCause() {
        return this.cause;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    protected void setError(String string) {
        this.error = string;
    }

    protected void setErrorMessage(String string) {
        this.errorMessage = string;
    }

    protected void setCause(String string) {
        this.cause = string;
    }
}

