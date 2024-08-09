/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.gui;

import net.minecraft.client.resources.I18n;
import net.optifine.gui.GuiButtonOF;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.EnumShaderOption;
import net.optifine.shaders.gui.GuiShaders;

public class GuiButtonEnumShaderOption
extends GuiButtonOF {
    private EnumShaderOption enumShaderOption = null;

    public GuiButtonEnumShaderOption(EnumShaderOption enumShaderOption, int n, int n2, int n3, int n4) {
        super(enumShaderOption.ordinal(), n, n2, n3, n4, GuiButtonEnumShaderOption.getButtonText(enumShaderOption));
        this.enumShaderOption = enumShaderOption;
    }

    public EnumShaderOption getEnumShaderOption() {
        return this.enumShaderOption;
    }

    private static String getButtonText(EnumShaderOption enumShaderOption) {
        String string = I18n.format(enumShaderOption.getResourceKey(), new Object[0]) + ": ";
        switch (1.$SwitchMap$net$optifine$shaders$config$EnumShaderOption[enumShaderOption.ordinal()]) {
            case 1: {
                return string + GuiShaders.toStringAa(Shaders.configAntialiasingLevel);
            }
            case 2: {
                return string + GuiShaders.toStringOnOff(Shaders.configNormalMap);
            }
            case 3: {
                return string + GuiShaders.toStringOnOff(Shaders.configSpecularMap);
            }
            case 4: {
                return string + GuiShaders.toStringQuality(Shaders.configRenderResMul);
            }
            case 5: {
                return string + GuiShaders.toStringQuality(Shaders.configShadowResMul);
            }
            case 6: {
                return string + GuiShaders.toStringHandDepth(Shaders.configHandDepthMul);
            }
            case 7: {
                return string + GuiShaders.toStringOnOff(Shaders.configCloudShadow);
            }
            case 8: {
                return string + Shaders.configOldHandLight.getUserValue();
            }
            case 9: {
                return string + Shaders.configOldLighting.getUserValue();
            }
            case 10: {
                return string + GuiShaders.toStringOnOff(Shaders.configShadowClipFrustrum);
            }
            case 11: {
                return string + GuiShaders.toStringOnOff(Shaders.configTweakBlockDamage);
            }
        }
        return string + Shaders.getEnumShaderOption(enumShaderOption);
    }

    public void updateButtonText() {
        this.setMessage(GuiButtonEnumShaderOption.getButtonText(this.enumShaderOption));
    }

    @Override
    protected boolean isValidClickButton(int n) {
        return false;
    }
}

