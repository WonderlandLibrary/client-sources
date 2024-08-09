/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.shader.exception;

public class UndefinedShader
extends Throwable {
    private final String shader;

    @Override
    public String getMessage() {
        return this.shader;
    }

    public UndefinedShader(String string) {
        this.shader = string;
    }
}

