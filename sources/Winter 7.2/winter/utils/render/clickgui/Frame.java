/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.render.clickgui;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import winter.Client;
import winter.module.Module;
import winter.utils.Render;
import winter.utils.render.clickgui.Component;
import winter.utils.render.clickgui.comp.Button;

public class Frame {
    public ArrayList<Component> components = new ArrayList();
    public Module.Category category;
    public boolean open;
    public boolean isDragging;
    public int x;
    public int y;
    public int dragX;
    public int dragY;
    public int height;

    public Frame(Module.Category cat2, int x2) {
        this.category = cat2;
        this.x = x2;
        this.y = 10;
        this.dragX = 0;
        this.dragY = 0;
        this.open = false;
        this.isDragging = false;
        int btnY = 0;
        for (Module mod : Client.getManager().getModulesInCategory(this.category)) {
            this.components.add(new Button(this, mod, btnY));
            btnY += 19;
        }
    }

    public boolean mouseOver(int mouseX, int mouseY) {
        if (mouseX > this.x && mouseY > this.y && mouseX < this.x + 80 && mouseY < this.y + 18) {
            return true;
        }
        return false;
    }

    public void update(int mouseX, int mouseY) {
        if (this.isDragging) {
            this.x = mouseX - this.dragX;
            this.y = mouseY - this.dragY;
        }
        for (Component comp : this.components) {
            if (!this.open) continue;
            comp.updateComponent(mouseX, mouseY);
        }
    }

    public void render() {
        Render.drawBorderedRect(this.x, this.y, this.x + 100, this.y + 18 + this.getOffset(), 0.5, -14540254, -15658735);
        Render.drawRect((double)this.x + 0.5, (double)this.y + 0.5, (double)this.x + 99.5, this.y + 1, -13619152);
        Minecraft.getMinecraft().fontRendererObj.drawString(this.category.name(), this.x + 50 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.category.name()) / 2, (float)this.y + 5.5f, -1);
        for (Component comp : this.components) {
            if (!this.open) continue;
            comp.renderComponent();
        }
    }

    public int getOffset() {
        if (this.open) {
            return 19 * this.components.size() + 4;
        }
        return 0;
    }

    public void setDrag(boolean drag) {
        this.isDragging = drag;
    }
}

