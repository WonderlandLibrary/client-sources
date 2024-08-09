/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import net.optifine.shaders.ITextureFormat;
import net.optifine.shaders.ShadersTextureType;
import net.optifine.texture.ColorBlenderLabPbrSpecular;
import net.optifine.texture.ColorBlenderLinear;
import net.optifine.texture.IColorBlender;

public class TextureFormatLabPbr
implements ITextureFormat {
    private String version;

    public TextureFormatLabPbr(String string) {
        this.version = string;
    }

    @Override
    public String getMacroName() {
        return "LAB_PBR";
    }

    @Override
    public String getMacroVersion() {
        return this.version == null ? null : this.version.replace('.', '_');
    }

    @Override
    public IColorBlender getColorBlender(ShadersTextureType shadersTextureType) {
        return shadersTextureType == ShadersTextureType.SPECULAR ? new ColorBlenderLabPbrSpecular() : new ColorBlenderLinear();
    }

    @Override
    public boolean isTextureBlend(ShadersTextureType shadersTextureType) {
        return shadersTextureType != ShadersTextureType.SPECULAR;
    }
}

