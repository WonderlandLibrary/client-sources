package me.lemon.client.gui.click.component;

import ac.sapphire.client.Sapphire;
import ac.sapphire.client.module.AbstractModule;
import ac.sapphire.client.module.ModuleCategory;
import me.lemon.client.gui.click.component.components.Button;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Frame {

    private final int width;
    private final int barHeight;
    public ArrayList<Component> components;
    public ModuleCategory category;
    public int dragX;
    public int dragY;
    private boolean open;
    private int y;
    private int x;
    private boolean isDragging;

    public Frame(ModuleCategory cat) {
        this.components = new ArrayList<Component>();
        this.category = cat;
        this.width = 88;
        this.x = 5;
        this.y = 5;
        this.barHeight = 13;
        this.dragX = 0;
        this.open = false;
        this.isDragging = false;
        int tY = this.barHeight;
        for (AbstractModule mod : Sapphire.INSTANCE.getModuleManager().getModulesInCategory(category)) {
            Button modButton = new Button(mod, this, tY);
            this.components.add(modButton);
            tY += 12;
        }
    }

    public void onModeChange() {
        for (Component component : components) {
            if (component instanceof Button) {
                ((Button) component).onModeChange();
            }
        }

        refresh();
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public void setDrag(boolean drag) {
        this.isDragging = drag;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void renderFrame(FontRenderer fontRenderer) {
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, new Color(0, 0, 0, 150).getRGB());
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        fontRenderer.drawStringWithShadow(this.category.getDisplayName(), (this.x + 2) * 2 + 5, (this.y + 2.5f) * 2 + 5, 0xFFFFFFFF);
        fontRenderer.drawStringWithShadow(this.open ? "-" : "+", (this.x + this.width - 10) * 2 + 5, (this.y + 2.5f) * 2 + 5, -1);
        GL11.glPopMatrix();
        if (this.open) {
            if (!this.components.isEmpty()) {
                for (Component component : components) {
                    component.renderComponent();
                }
            }
        }
    }

    public void refresh() {
        int off = this.barHeight;
        for (Component comp : components) {
            comp.setOff(off);
            off += comp.getHeight();
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public int getY() {
        return y;
    }

    public void setY(int newY) {
        this.y = newY;
    }

    public int getWidth() {
        return width;
    }

    public void updatePosition(int mouseX, int mouseY) {
        if (this.isDragging) {
            this.setX(mouseX - dragX);
            this.setY(mouseY - dragY);
        }
    }

    public boolean isWithinHeader(int x, int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight;
    }
}
