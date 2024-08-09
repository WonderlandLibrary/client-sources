/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.gui;

import net.optifine.gui.GuiButtonOF;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.config.ShaderOptionScreen;

public class GuiButtonShaderOption
extends GuiButtonOF {
    private ShaderOption shaderOption = null;

    public GuiButtonShaderOption(int n, int n2, int n3, int n4, int n5, ShaderOption shaderOption, String string) {
        super(n, n2, n3, n4, n5, string);
        this.shaderOption = shaderOption;
    }

    @Override
    protected boolean isValidClickButton(int n) {
        if (this.shaderOption instanceof ShaderOptionScreen) {
            return n == 0;
        }
        return false;
    }

    public ShaderOption getShaderOption() {
        return this.shaderOption;
    }

    public void valueChanged() {
    }

    public boolean isSwitchable() {
        return false;
    }
}

