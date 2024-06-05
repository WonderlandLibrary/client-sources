/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.clickgui.impl;

import cc.diablo.helpers.render.RenderUtils;
import cc.diablo.module.Category;
import java.awt.Color;
import net.minecraft.client.gui.GuiScreen;

public class Frame
extends GuiScreen {
    public double x;
    public double y;
    public double width;
    public double height;
    public Category category;

    public Frame(double x, double y, double width, double height, Category category) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.category = category;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtils.drawRect((float)this.x, (float)this.y, (float)(this.x + this.width), (float)(this.y + this.height), new Color(17, 17, 17).getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

