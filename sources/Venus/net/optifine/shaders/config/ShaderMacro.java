/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.config;

public class ShaderMacro {
    private String name;
    private String value;

    public ShaderMacro(String string, String string2) {
        this.name = string;
        this.value = string2;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public String getSourceLine() {
        return "#define " + this.name + " " + this.value;
    }

    public String toString() {
        return this.getSourceLine();
    }
}

