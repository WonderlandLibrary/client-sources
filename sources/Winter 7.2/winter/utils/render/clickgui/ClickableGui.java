/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.render.clickgui;

import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import winter.module.Module;
import winter.utils.render.clickgui.Component;
import winter.utils.render.clickgui.Frame;

public class ClickableGui
extends GuiScreen {
    private ArrayList<Frame> frames = new ArrayList();

    public ClickableGui() {
        int frameX = 10;
        Module.Category[] arrcategory = Module.Category.values();
        int n = arrcategory.length;
        int n2 = 0;
        while (n2 < n) {
            Module.Category cat2 = arrcategory[n2];
            this.frames.add(new Frame(cat2, frameX));
            frameX += 85;
            ++n2;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        for (Frame frame : this.frames) {
            frame.update(mouseX, mouseY);
            frame.render();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (Frame frame : this.frames) {
            if (frame.mouseOver(mouseX, mouseY) && mouseButton == 0) {
                frame.isDragging = true;
                frame.dragX = mouseX - frame.x;
                frame.dragY = mouseY - frame.y;
            }
            if (mouseButton == 1 && frame.mouseOver(mouseX, mouseY)) {
                boolean bl2 = frame.open = !frame.open;
            }
            if (!frame.open) continue;
            for (Component comp : frame.components) {
                comp.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (Frame frame : this.frames) {
            frame.isDragging = false;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
}

