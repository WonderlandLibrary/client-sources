/*

    Code was written by MarkGG
    Any illegal distribution of this code will
    have consequences

    Vectus Client @ 2024-2025

 */
package lol.point.returnclient.ui.cgui.dropdown.comp.impl;

import lol.point.returnclient.module.Module;
import lol.point.returnclient.ui.cgui.dropdown.comp.Component;
import lol.point.returnclient.util.render.shaders.ShaderUtil;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class ModuleComponent extends Component {

    private final Module module;

    public ModuleComponent(Module module) {
        this.module = module;
    }

    public void drawScreen(int mouseX, int mouseY) {
        boolean hovered = RenderUtil.hovered(mouseX, mouseY, getX(), getY(), getWidth(), getHeight());
        RenderUtil.rectangle(getX(), getY(), getWidth(), getHeight(), hovered ? module.enabled ? getTheme().gradient1 : new Color(51, 51, 51).brighter() : module.enabled ? getTheme().gradient1.darker() : new Color(51, 51, 51));
        ShaderUtil.renderGlow(() -> {
            if (module.enabled) {
                RenderUtil.rectangle(getX(), getY(), getWidth(), getHeight(), getTheme().gradient1);
            }

        }, 2, 2, 0.86f);
        getFont().drawString(module.name, getX() + 2, getY() + 4.5f, -1);

        if (hovered) {
            getFont().drawString(ModuleListener.listening ? "[...]" : "[" + Keyboard.getKeyName(module.key) + "]", getX() + getWidth() - getFont().getWidth(ModuleListener.listening ? "[...]" : "[" + Keyboard.getKeyName(module.key) + "]") - 2, getY() + 4.5f, -1);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        boolean hovered = RenderUtil.hovered(mouseX, mouseY, getX(), getY(), getWidth(), getHeight());
        if (hovered) {
            switch (mouseButton) {
                case 0:
                    module.setEnabled(!module.enabled);
                    break;
                case 1:
                    if (!module.settings.isEmpty()) {
                        module.expanded = !module.expanded;
                    }
                    break;
                case 2:
                    ModuleListener.setCurrentModule(module, true);
                    break;
            }
        }
    }

    public void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1) {
            ModuleListener.setCurrentModule(null, false);
        }

        if (ModuleListener.listening & ModuleListener.module != null) {
            if (keyCode == Keyboard.KEY_SPACE || keyCode == Keyboard.KEY_ESCAPE) {
                ModuleListener.module.key = Keyboard.KEY_NONE;
            } else {
                ModuleListener.module.key = keyCode;
            }
            ModuleListener.setCurrentModule(null, false);
        }
    }

    public static class ModuleListener {

        public static Module module = null;
        public static boolean listening = false;

        public static void setCurrentModule(Module currentModule, boolean listening) {
            module = currentModule;
            ModuleListener.listening = listening;
        }

    }
}
