/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.render.click;

import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import winter.module.Module;
import winter.utils.render.click.component.Component;
import winter.utils.render.click.component.Frame;

public class ClickGui
extends GuiScreen {
    public static ArrayList<Frame> frames;
    public static int color;

    static {
        color = -10110371;
    }

    public ClickGui() {
        frames = new ArrayList();
        int frameX = 5;
        int frameY = 5;
        Module.Category[] arrcategory = Module.Category.values();
        int n = arrcategory.length;
        int n2 = 0;
        while (n2 < n) {
            Module.Category category = arrcategory[n2];
            if (frameX > 400) {
                frameX = 5;
                frameY += 14;
            }
            Frame frame = new Frame(category);
            frame.setX(frameX);
            frame.setY(frameY);
            frames.add(frame);
            frameX += frame.getWidth() + 1;
            ++n2;
        }
    }

    @Override
    public void initGui() {
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (Frame frame : frames) {
            frame.renderFrame(this.fontRendererObj);
            frame.updatePosition(mouseX, mouseY);
            for (Component comp : frame.getComponents()) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (Frame frame : frames) {
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
                frame.setDrag(true);
                frame.dragX = mouseX - frame.getX();
                frame.dragY = mouseY - frame.getY();
            }
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
                frame.setOpen(!frame.isOpen());
            }
            if (!frame.isOpen() || frame.getComponents().isEmpty()) continue;
            for (Component component : frame.getComponents()) {
                component.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        for (Frame frame : frames) {
            if (!frame.isOpen() || keyCode == 1 || frame.getComponents().isEmpty()) continue;
            for (Component component : frame.getComponents()) {
                component.keyTyped(typedChar, keyCode);
            }
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (Frame frame : frames) {
            frame.setDrag(false);
        }
        for (Frame frame : frames) {
            if (!frame.isOpen() || frame.getComponents().isEmpty()) continue;
            for (Component component : frame.getComponents()) {
                component.mouseReleased(mouseX, mouseY, state);
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
}

