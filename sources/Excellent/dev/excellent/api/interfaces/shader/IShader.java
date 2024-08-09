package dev.excellent.api.interfaces.shader;

import dev.excellent.impl.util.shader.impl.*;
import dev.luvbeeq.shader.DepthShader;

public interface IShader {
    MainMenuShader MAIN_MENU = new MainMenuShader();
    ClickGuiShader CLICK_GUI_SHADER = new ClickGuiShader();
    RoundedGradientShader ROUNDED_GRADIENT = new RoundedGradientShader();
    RoundedShader ROUNDED = new RoundedShader();
    RoundedWithNoiseShader ROUNDED_WITH_NOISE = new RoundedWithNoiseShader();
    RoundedWithGlowShader ROUNDED_WITH_GLOW = new RoundedWithGlowShader();
    BlurShader BLUR_SHADER = new BlurShader();
    BloomShader BLOOM_SHADER = new BloomShader();
    DepthShader DEPTH_SHADER = new DepthShader();

    static void render3DBlur() {
        BLUR_SHADER.draw(3, 4, BlurShader.RenderType.CAMERA, 1);
        BLOOM_SHADER.draw(3, 4, BloomShader.RenderType.CAMERA, 1);
    }

    static void render2DBlur() {
        BLUR_SHADER.draw(3, 4, BlurShader.RenderType.DISPLAY, 1);
        BLOOM_SHADER.draw(3, 4, BloomShader.RenderType.DISPLAY, 1);
    }
}