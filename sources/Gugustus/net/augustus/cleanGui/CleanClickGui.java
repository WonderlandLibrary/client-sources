package net.augustus.cleanGui;

import net.augustus.modules.Categorys;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.util.ArrayList;

public class CleanClickGui extends GuiScreen {
    public Categorys currentCategory = Categorys.RENDER;
    public int x = 100;
    public int y = 100;
    public int height = 500;
    public int width = 800;
    @Override
    public void initGui() {
        for (Categorys categorys : Categorys.values()) {

        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(x, y, width, height, Color.BLACK.getRGB());
    }
}
