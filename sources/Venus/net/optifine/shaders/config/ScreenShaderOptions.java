/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.config;

import net.optifine.shaders.config.ShaderOption;

public class ScreenShaderOptions {
    private String name;
    private ShaderOption[] shaderOptions;
    private int columns;

    public ScreenShaderOptions(String string, ShaderOption[] shaderOptionArray, int n) {
        this.name = string;
        this.shaderOptions = shaderOptionArray;
        this.columns = n;
    }

    public String getName() {
        return this.name;
    }

    public ShaderOption[] getShaderOptions() {
        return this.shaderOptions;
    }

    public int getColumns() {
        return this.columns;
    }
}

