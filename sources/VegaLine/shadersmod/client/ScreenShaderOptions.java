/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package shadersmod.client;

import shadersmod.client.ShaderOption;

public class ScreenShaderOptions {
    private String name;
    private ShaderOption[] shaderOptions;
    private int columns;

    public ScreenShaderOptions(String name, ShaderOption[] shaderOptions, int columns) {
        this.name = name;
        this.shaderOptions = shaderOptions;
        this.columns = columns;
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

