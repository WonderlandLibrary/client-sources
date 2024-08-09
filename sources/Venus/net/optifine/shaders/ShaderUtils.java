/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import net.optifine.Config;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.config.ShaderProfile;

public class ShaderUtils {
    public static ShaderOption getShaderOption(String string, ShaderOption[] shaderOptionArray) {
        if (shaderOptionArray == null) {
            return null;
        }
        for (int i = 0; i < shaderOptionArray.length; ++i) {
            ShaderOption shaderOption = shaderOptionArray[i];
            if (!shaderOption.getName().equals(string)) continue;
            return shaderOption;
        }
        return null;
    }

    public static ShaderProfile detectProfile(ShaderProfile[] shaderProfileArray, ShaderOption[] shaderOptionArray, boolean bl) {
        if (shaderProfileArray == null) {
            return null;
        }
        for (int i = 0; i < shaderProfileArray.length; ++i) {
            ShaderProfile shaderProfile = shaderProfileArray[i];
            if (!ShaderUtils.matchProfile(shaderProfile, shaderOptionArray, bl)) continue;
            return shaderProfile;
        }
        return null;
    }

    public static boolean matchProfile(ShaderProfile shaderProfile, ShaderOption[] shaderOptionArray, boolean bl) {
        if (shaderProfile == null) {
            return true;
        }
        if (shaderOptionArray == null) {
            return true;
        }
        String[] stringArray = shaderProfile.getOptions();
        for (int i = 0; i < stringArray.length; ++i) {
            String string;
            String string2;
            String string3 = stringArray[i];
            ShaderOption shaderOption = ShaderUtils.getShaderOption(string3, shaderOptionArray);
            if (shaderOption == null || Config.equals(string2 = bl ? shaderOption.getValueDefault() : shaderOption.getValue(), string = shaderProfile.getValue(string3))) continue;
            return true;
        }
        return false;
    }
}

