/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.clickgui;

import cc.diablo.clickgui.ClickGUI;
import cc.diablo.helpers.render.RenderUtils;
import cc.diablo.module.Module;
import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;

public class SettingGUI
extends ClickGUI {
    public Module module;
    double x;
    double y;
    double width;
    double height;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtils.drawRoundedRectangle(this.x - 160.0, this.y, this.width + 160.0, this.height + 210.0, 15.0, new Color(44, 47, 51, 255).getRGB());
        RenderUtils.drawRoundedRectangle(this.x - 160.0, this.y, this.width + 160.0, this.height + 20.0, 15.0, new Color(35, 39, 42, 255).getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
    }

    @Override
    public void initGui() {
        this.x = this.sr.getScaledWidth_double() * 1.5;
        this.y = this.sr.getScaledHeight_double();
        this.width = this.x;
        this.height = this.y;
        super.initGui();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }
}

