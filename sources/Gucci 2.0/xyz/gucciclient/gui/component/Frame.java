package xyz.gucciclient.gui.component;

import xyz.gucciclient.modules.*;
import xyz.gucciclient.gui.parts.*;
import java.util.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import xyz.gucciclient.utils.*;

public class Frame
{
    public ArrayList<Component> components;
    public Module.Category category;
    public boolean open;
    private int width;
    private int y;
    private int x;
    private int barHeight;
    private boolean isDragging;
    public int dragX;
    public int dragY;
    
    public Frame(final Module.Category cat) {
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
        for (final Module mod : Module.getCategoryModules(this.category)) {
            if (mod.getName().equalsIgnoreCase("ClickGUI")) {
                continue;
            }
            final ModulesPart modButton = new ModulesPart(mod, this, tY);
            this.components.add(modButton);
            tY += 12;
        }
    }
    
    public ArrayList<Component> getComponents() {
        return this.components;
    }
    
    public void setX(final int newX) {
        this.x = newX;
    }
    
    public void setY(final int newY) {
        this.y = newY;
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
        this.width = 80;
        Gui.drawRect(this.x - 1, this.y - 3, this.x + this.width + 1, this.y + this.barHeight - 2, new Color(255, 0, 0, 150).getRGB());
        GL11.glPushMatrix();
        Wrapper.getMinecraft().fontRendererObj.drawString(this.category.name(), this.x + 2, this.y, -1);
        if (this.open) {
            fontRenderer.drawString("-", this.x + 70, (int)(this.y + 1.5), -1);
        }
        else {
            fontRenderer.drawString("+", this.x + 70, this.y + 1, -1);
        }
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        if (this.open && !this.components.isEmpty()) {
            for (final Component component : this.components) {
                component.render();
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
    
    public int getY() {
        return this.y;
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
