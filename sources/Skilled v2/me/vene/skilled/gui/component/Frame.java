 package me.vene.skilled.gui.component;

 import java.util.ArrayList;
 import me.vene.skilled.gui.parts.ConsolePart;
 import me.vene.skilled.gui.parts.ModulesPart;
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.modules.mods.other.ClickGUI;
 import me.vene.skilled.utilities.StringRegistry;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.gui.Gui;
 import org.lwjgl.opengl.GL11;
 
public class Frame
{
    public ArrayList<Component> components;
    public Category category;
    public boolean open;
    private int width;
    private int y;
    private int x;
    private int barHeight;
    public boolean isDragging;
    public int dragX;
    public int dragY;
    
    public Frame(final Category cat) {
        this.components = new ArrayList<Component>();
        this.category = cat;
        this.width = 118;
        this.x = 5;
        this.y = 5;
        this.barHeight = 15;
        this.dragX = 0;
        this.open = false;
        this.isDragging = false;
        int tY = this.barHeight;
        if (this.category.getID() == 8) {
            final ConsolePart consolePart = new ConsolePart(this, tY);
            this.components.add(consolePart);
        }
        for (final Module mod : Module.getCategoryModules(this.category)) {
            final ModulesPart modButton = new ModulesPart(mod, this, tY);
            this.components.add(modButton);
            tY += 16;
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
    
    public void renderFrame() {
        this.width = 110;
        Gui.drawRect(this.x, this.y - 3, this.x + this.width, this.y + this.barHeight - 2, ClickGUI.getHudColor());
        GL11.glPushMatrix();
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(StringRegistry.register(this.category.getName()), (float)(this.getX() + this.getWidth() / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.category.getName()) / 2), (float)(this.y + 1.5), ClickGUI.getColor());
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.open ? "-" : "+", (float)(this.x + this.getWidth() - 10), (float)(this.y + 1.5), ClickGUI.getColor());
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
    
    public int getHeight() {
        return this.barHeight;
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
