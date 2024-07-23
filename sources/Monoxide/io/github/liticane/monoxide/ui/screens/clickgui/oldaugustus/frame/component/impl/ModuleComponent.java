package io.github.liticane.monoxide.ui.screens.clickgui.oldaugustus.frame.component.impl;

import io.github.liticane.monoxide.ui.screens.clickgui.oldaugustus.frame.component.Component;
import io.github.liticane.monoxide.ui.screens.clickgui.oldaugustus.window.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.util.render.RenderUtil;

import java.awt.*;

public class ModuleComponent extends io.github.liticane.monoxide.ui.screens.clickgui.oldaugustus.frame.component.Component {

    private final Module module;
    private boolean expanded = false;
    private io.github.liticane.monoxide.ui.screens.clickgui.oldaugustus.window.Window window;

    public ModuleComponent(Module module, float posX, float posY, float width, float height) {
        super(posX, posY, width, height);
        this.module = module;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        FontRenderer normal = Minecraft.getMinecraft().fontRendererObj;
        RenderUtil.drawRect(getPosX() + getAddX(), getPosY(), getBaseWidth(), getBaseHeight(), new Color(0, 0, 0, 190).getRGB());
        normal.drawTotalCenteredStringWithShadow(module.getName(), getPosX() + getBaseWidth() / 2 + getAddX(), getPosY() + getBaseHeight() / 2, !module.isEnabled() ? new Color(139, 141, 145, 255).getRGB() : new Color(41, 146, 222).getRGB());
        if(expanded) {
            if(window == null)
                this.window = new io.github.liticane.monoxide.ui.screens.clickgui.oldaugustus.window.Window(module, getPosX() + this.getFinalWidth() + 2, getPosY());
            window.setPosX(getPosX() + this.getFinalWidth() + 2 + this.getAddX());
            window.setPosY(getPosY());
            window.draw(mouseX, mouseY);
        }
    }

    @Override
    public void mouseClick(int mouseX, int mouseY, int mouseButton) {
        if(expanded) {
            if(window == null)
                this.window = new Window(module, getPosX() + this.getFinalWidth() + 2 + getAddX(), getPosY());
            window.mouseClicked(mouseX, mouseY, mouseButton);
        }
        if(RenderUtil.isHovered(mouseX, mouseY, this.getPosX() + getAddX(), this.getPosY(), this.getBaseWidth(), this.getBaseHeight())) {
            switch (mouseButton) {
                case 0:
                    module.toggle();
                    break;
                case 1:
                    this.expanded = !this.expanded;
                    break;
            }
        }
        if(expanded) {

        }
    }

    @Override
    public float getFinalHeight() {
        float totalComponentHeight = 0;
        if(this.expanded) {
            for(Component component : this.subComponents) {
                totalComponentHeight += component.isVisible() ? component.getFinalHeight() : 0;
            }
        }
        return this.getBaseHeight() + totalComponentHeight;
    }

    public Module getModule() {
        return module;
    }
}
