/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.render.click.component;

import java.util.ArrayList;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;
import winter.Client;
import winter.module.Module;
import winter.utils.Render;
import winter.utils.render.click.component.Component;
import winter.utils.render.click.component.components.Button;

public class Frame {
    public ArrayList<Component> components = new ArrayList();
    public Module.Category category;
    private boolean open;
    private int width;
    private int y;
    private int x;
    private int barHeight;
    private boolean isDragging;
    public int dragX;
    public int dragY;

    public Frame(Module.Category cat2) {
        this.category = cat2;
        this.width = 90;
        this.x = 5;
        this.y = 5;
        this.barHeight = 14;
        this.dragX = 0;
        this.open = false;
        this.isDragging = false;
        int tY = this.barHeight;
        for (Module mod : Client.getManager().getModulesInCategory(this.category)) {
            Button modButton = new Button(mod, this, tY);
            this.components.add(modButton);
            tY += 14;
        }
    }

    public ArrayList<Component> getComponents() {
        return this.components;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }

    public void setDrag(boolean drag) {
        this.isDragging = drag;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void renderFrame(FontRenderer fontRenderer) {
        this.width = 90;
        Render.drawBorderedRect((double)this.x - 0.5, (double)this.y - 0.5, (double)(this.x + this.width) + 0.5, (double)(this.y + this.barHeight) - 0.5, 0.5, -13421773, -16777216);
        GL11.glPushMatrix();
        fontRenderer.drawStringShadowed(this.category.name(), this.x + 3, (float)this.y + 2.5f + (this.open ? 0.5f : 0.5f), -1);
        GL11.glPopMatrix();
        if (this.open && !this.components.isEmpty()) {
            for (Component component : this.components) {
                component.renderComponent();
            }
        }
    }

    public void refresh() {
        int off = this.barHeight;
        for (Component comp : this.components) {
            comp.setOff(off);
            off += comp.getHeight() + (((Button)comp).open ? 2 : 0);
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public void updatePosition(int mouseX, int mouseY) {
        if (this.isDragging) {
            this.setX(mouseX - this.dragX);
            this.setY(mouseY - this.dragY);
        }
    }

    public boolean isWithinHeader(int x2, int y2) {
        if (x2 >= this.x && x2 <= this.x + this.width && y2 >= this.y && y2 <= this.y + this.barHeight) {
            return true;
        }
        return false;
    }
}

