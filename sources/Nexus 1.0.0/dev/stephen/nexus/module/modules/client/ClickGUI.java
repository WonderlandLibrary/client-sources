package dev.stephen.nexus.module.modules.client;

import dev.stephen.nexus.gui.clickgui.imgui.ClickGui;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.setting.impl.ModeSetting;
import org.lwjgl.glfw.GLFW;

public class ClickGUI extends Module {
    private boolean didInitImgui = false;
    public static final ModeSetting clickguiMode = new ModeSetting("Mode", "ImGui", "ImGui", "Old");

    public ClickGUI() {
        super("ClickGui", "Click Gui", GLFW.GLFW_KEY_RIGHT_SHIFT, ModuleCategory.CLIENT);
        this.addSetting(clickguiMode);
    }

    @Override
    public void onEnable() {
        if (clickguiMode.isMode("ImGui")) {
            if (mc.getWindow() != null && !didInitImgui) {
                //ImGuiImpl.initialize(mc.getWindow().getHandle());
                didInitImgui = true;
            }
            mc.setScreen(new ClickGui());
            this.toggle();
        }

        if (clickguiMode.isMode("Old")) {
            mc.setScreen(new dev.stephen.nexus.gui.clickgui.old.ClickGui());
            this.toggle();
        }
        super.onEnable();
    }
}
