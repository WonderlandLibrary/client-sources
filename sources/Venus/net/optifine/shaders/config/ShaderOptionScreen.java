/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.config;

import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.ShaderOption;

public class ShaderOptionScreen
extends ShaderOption {
    public ShaderOptionScreen(String string) {
        super(string, null, null, new String[0], null, null);
    }

    @Override
    public String getNameText() {
        return Shaders.translate("screen." + this.getName(), this.getName());
    }

    @Override
    public String getDescriptionText() {
        return Shaders.translate("screen." + this.getName() + ".comment", null);
    }
}

