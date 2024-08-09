/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import net.optifine.Config;
import net.optifine.config.ConfigUtils;
import net.optifine.shaders.ShadersTextureType;
import net.optifine.shaders.TextureFormatLabPbr;
import net.optifine.texture.IColorBlender;

public interface ITextureFormat {
    public IColorBlender getColorBlender(ShadersTextureType var1);

    public boolean isTextureBlend(ShadersTextureType var1);

    public String getMacroName();

    public String getMacroVersion();

    public static ITextureFormat readConfiguration() {
        if (!Config.isShaders()) {
            return null;
        }
        String string = ConfigUtils.readString("optifine/texture.properties", "format");
        if (string != null) {
            String string2;
            String[] stringArray = Config.tokenize(string, "/");
            String string3 = stringArray[0];
            String string4 = string2 = stringArray.length > 1 ? stringArray[5] : null;
            if (string3.equals("lab-pbr")) {
                return new TextureFormatLabPbr(string2);
            }
            Config.warn("Unknown texture format: " + string);
            return null;
        }
        return null;
    }
}

