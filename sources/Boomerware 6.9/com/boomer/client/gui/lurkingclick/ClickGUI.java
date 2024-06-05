package com.boomer.client.gui.lurkingclick;

import com.boomer.client.gui.lurkingclick.frame.Frame;
import com.boomer.client.gui.lurkingclick.frame.impl.CategoryFrame;
import com.boomer.client.module.Module;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;
import java.util.ArrayList;

/**
 * made by oHare for BoomerWare
 *
 * @since 8/19/2019
 **/
public class ClickGUI extends GuiScreen {
    private ArrayList<Frame> frames = new ArrayList<>();

    public void initGUI() {
        float posX = 2;
        for (Module.Category category : Module.Category.values()) {
            frames.add(new CategoryFrame(category,posX,2,110,20,true));
            posX += 112;
        }
        frames.forEach(Frame::initGUI);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        final ScaledResolution scaledResolution = new ScaledResolution(mc);
        frames.forEach(frame -> frame.drawScreen(mouseX,mouseY,partialTicks,scaledResolution));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        frames.forEach(frame -> frame.keyTyped(typedChar,keyCode));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        frames.forEach(frame -> frame.mouseClicked(mouseX,mouseY,mouseButton));
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        frames.forEach(frame -> frame.mouseReleased(mouseX,mouseY,state));
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        frames.forEach(frame -> frame.setDragging(false));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
