/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.imgui;

import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import winter.utils.imgui.DropDown;
import winter.utils.imgui.Panel;

public class ImGui
extends GuiScreen {
    public static Minecraft mc = Minecraft.getMinecraft();
    public static Panel panel = new Panel("Winter v6.6");

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        panel.update(mouseX, mouseY);
        panel.render();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (panel.mouseOverTitle(mouseX, mouseY) && mouseButton == 0) {
            ImGui.panel.isDragging = true;
            ImGui.panel.dragX = mouseX - Panel.pX;
            ImGui.panel.dragY = mouseY - Panel.pY;
        }
        if (panel.mouseOverSize(mouseX, mouseY) && mouseButton == 0) {
            ImGui.panel.resizing = true;
        }
        for (DropDown drop : ImGui.panel.drops) {
            drop.onClick(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        ImGui.panel.isDragging = false;
        ImGui.panel.resizing = false;
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        panel.scroll();
    }
}

