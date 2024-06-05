package me.satisfactory.base.hero.clickgui.component;

import me.satisfactory.base.*;
import me.satisfactory.base.module.*;
import me.satisfactory.base.hero.clickgui.component.components.*;
import java.util.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;

public class Frame
{
    public ArrayList<Component> components;
    public Category category;
    public int dragX;
    public int dragY;
    private boolean open;
    private int width;
    private int y;
    private int x;
    private int barHeight;
    private boolean isDragging;
    
    public Frame(final Category cat) {
        this.components = new ArrayList<Component>();
        this.category = cat;
        this.width = 88;
        this.x = 5;
        this.y = 5;
        this.barHeight = 15;
        this.dragX = 0;
        this.open = false;
        this.isDragging = false;
        int tY = this.barHeight;
        for (final Module mod : Base.INSTANCE.getModuleManager().getModsInCategory(this.category)) {
            final Button modButton = new Button(mod, this, tY);
            this.components.add(modButton);
            tY += 12;
        }
    }
    
    public ArrayList<Component> getComponents() {
        return this.components;
    }
    
    public void setDrag(final boolean drag) {
        this.isDragging = drag;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public void setOpen(final boolean open) {
        this.open = open;
    }
    
    public void renderFrame(final FontRenderer fontRenderer) {
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, new Color(23, 30, 42).getRGB());
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        fontRenderer.drawString(this.category.name(), (float)((this.x + 2) * 2 + 10), (this.y + 2.5f) * 2.0f + 7.0f, -1);
        fontRenderer.drawString(this.open ? "-" : "+", (float)((this.x + this.width - 10) * 2 + 5), (this.y + 2.5f) * 2.0f + 7.0f, -1);
        GL11.glPopMatrix();
        if (this.open && !this.components.isEmpty()) {
            for (final Component component : this.components) {
                component.renderComponent();
            }
        }
    }
    
    public void refresh() {
        int off = this.barHeight;
        for (final Component comp : this.components) {
            comp.setOff(off);
            off += comp.getHeight();
        }
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setX(final int newX) {
        this.x = newX;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setY(final int newY) {
        this.y = newY;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void updatePosition(final int mouseX, final int mouseY) {
        if (this.isDragging) {
            this.setX(mouseX - this.dragX);
            this.setY(mouseY - this.dragY);
        }
    }
    
    public boolean isWithinHeader(final int x, final int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight;
    }
}
