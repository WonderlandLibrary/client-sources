package de.tired.module.impl.list.visual;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.guis.clickgui.setting.NumberSetting;
import de.tired.api.guis.clickgui.setting.impl.BooleanSetting;
import de.tired.api.guis.clickgui.setting.impl.ColorPickerSetting;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import de.tired.module.ModuleManager;
import de.tired.shaderloader.ShaderManager;
import de.tired.shaderloader.ShaderRenderer;
import de.tired.shaderloader.list.ColorCorrectionShader;
import net.minecraft.client.gui.Gui;

import java.awt.*;

@ModuleAnnotation(name = "Shader", category = ModuleCategory.RENDER, renderPreview = true)
public class Shader extends Module {

    public BooleanSetting blur = new BooleanSetting("Blur", this, true);

    public BooleanSetting chatBlur = new BooleanSetting("chatBlur", this, true, () -> blur.getValue());

    public BooleanSetting scoreBoardBlur = new BooleanSetting("scoreBoardBlur", this, true, () -> blur.getValue());

    public BooleanSetting hotbarBlur = new BooleanSetting("hotbarBlur", this, true, () -> blur.getValue());

    public BooleanSetting arraylistBlur = new BooleanSetting("arraylistBlur", this, true, () -> blur.getValue());

    public BooleanSetting clickGUIBlur = new BooleanSetting("clickGUIBlur", this, true, () -> blur.getValue());

    public BooleanSetting guiBlur = new BooleanSetting("guiBlur", this, true, () -> blur.getValue());

    public NumberSetting blurSigma = new NumberSetting("blurSigma", this, 11, .1, 30, 1, () -> blur.getValue());

    public NumberSetting blurRadius = new NumberSetting("blurRadius", this, 8, .1, 60, 1, () -> blur.getValue());

    public BooleanSetting Exposure = new BooleanSetting("Exposure", this, true);

    public BooleanSetting ShaderInBlur = new BooleanSetting("ShaderInBlur", this, true);

    public BooleanSetting adjustBlurSaturation = new BooleanSetting("adjustBlurSaturation", this, true);

    public NumberSetting ShaderInBlurSpeed = new NumberSetting("ShaderInBlurSpeed", this, 1000, 1, 5000, 100, () -> ShaderInBlur.getValue());


    public NumberSetting ExposureGamma = new NumberSetting("ExposureGamma", this, .8, .1, 2, .1, () -> Exposure.getValue());
    public NumberSetting ExposureYValue = new NumberSetting("ExposureYValueUP", this, .5, .1, 2, .1, () -> Exposure.getValue());

    public NumberSetting ExposureYValueDown = new NumberSetting("ExposureYValueDown", this, .5, .1, 2, .1, () -> Exposure.getValue());

    public static Shader getInstance() {
        return ModuleManager.getInstance(Shader.class);
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }

    @Override
    public void onRender(int x, int y, int width, int height) {

        x = x - 100;
        y = y - 100;
        ShaderRenderer.startBlur();
        Gui.drawRect(x, y + 50, x + 200, y + 155, ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRGB());
        ShaderRenderer.stopBlur();

        super.onRender(x, y, width, height);
    }
}
