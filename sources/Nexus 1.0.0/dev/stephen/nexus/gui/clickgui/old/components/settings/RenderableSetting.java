package dev.stephen.nexus.gui.clickgui.old.components.settings;

import dev.stephen.nexus.gui.clickgui.old.components.ModuleButton;
import dev.stephen.nexus.module.setting.Setting;
import dev.stephen.nexus.Client;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;

@SuppressWarnings("all")
public abstract class RenderableSetting {
    public MinecraftClient mc = Client.mc;
    public ModuleButton parent;
    public Setting setting;
    public int offset;

    public RenderableSetting(ModuleButton parent, Setting setting, int offset) {
        this.parent = parent;
        this.setting = setting;
        this.offset = offset;
    }

    public int parentX() {
        return parent.parent.getX();
    }

    public int parentY() {
        return parent.parent.getY();
    }

    public int parentWidth() {
        return parent.parent.getWidth();
    }

    public int parentHeight() {
        return parent.parent.getHeight();
    }

    public int parentOffset() {
        return parent.offset;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(parentX(), parentY() + parentOffset() + offset, parentX() + parentWidth(), parentY() + parentOffset() + offset + parentHeight(), new Color(35, 35, 35, 100).getRGB());
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > parentX() && mouseX < parentX() + parentWidth() && mouseY > offset + parentOffset() + parentY() && mouseY < offset + parentOffset() + parentY() + parentHeight();
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
    }

    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
    }

    public void keyPressed(int keyCode, int scanCode, int modifiers) {
    }

    public void keyReleased(int keyCode, int scanCode, int modifiers) {
    }
}