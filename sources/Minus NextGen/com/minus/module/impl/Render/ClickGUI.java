package com.minus.module.impl.Render;

import com.minus.imgui.ImGuiImpl;
import com.minus.module.Module;
import com.minus.module.setting.ModeSetting;
import com.minus.imgui.ClickImgui;
import org.lwjgl.glfw.GLFW;
import com.minus.module.Category;

public class ClickGUI extends Module {
    private boolean didInitImgui = false;
    public static final ModeSetting clickguiMode = new ModeSetting("Mode", "ImGUI", "ImGUI");

    public ClickGUI() {
        super("ClickGui", "Click Gui", GLFW.GLFW_KEY_RIGHT_SHIFT, Category.RENDER);
        this.addSetting(clickguiMode);
    }

    @Override
    public void onEnable() {
        switch (clickguiMode.getMode()) {
            case "ImGUI":
                if (mc.getWindow() != null && !didInitImgui) {
                    ImGuiImpl.initialize(
                            mc.getWindow().getHandle()
                    );

                    didInitImgui = true;
                }

                mc.setScreen(new ClickImgui());
                this.toggle();
                break;
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
