package dev.excellent.client.module.impl.render;

import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.util.shader.impl.BlurShader;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.ModeValue;
import dev.excellent.impl.value.mode.SubMode;
import org.lwjgl.glfw.GLFW;

@ModuleInfo(name = "Click Gui", description = "Интерфейс, в котором вы это читаете :3", category = Category.RENDER, keyBind = GLFW.GLFW_KEY_RIGHT_SHIFT)
public class ClickGui extends Module {
    public static Singleton<ClickGui> singleton = Singleton.create(() -> Module.link(ClickGui.class));
    public final BlurShader BLUR_SHADER = new BlurShader();

    public final BooleanValue shader = new BooleanValue("Шейдер", this, true);
    public final ModeValue shaderMode = new ModeValue("Режим шейдеров", this, () -> !shader.getValue())
            .add(new SubMode("Треугольники"),
                    new SubMode("Треугольники 2"));
    public final BooleanValue blur = new BooleanValue("Блюр", this, true);
    public final BooleanValue gradient = new BooleanValue("Градиент", this, true);
    public final BooleanValue sounds = new BooleanValue("Звуки", this, true);

    @Override
    public void onEnable() {
        super.onEnable();
        if (mc.currentScreen == null) {
            mc.displayScreen(excellent.getClickGui());
        }
    }

    @Override
    public void toggle() {
        super.toggle();
        reset();
    }

    private void reset() {
        BLUR_SHADER.cameraBlurQueue.clear();
        BLUR_SHADER.displayBlurQueue.clear();
        BLUR_SHADER.reset();
    }
}
