// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.gui.skeet.components;

import intent.AquaDev.aqua.gui.skeet.ClickGuiScreenSkeet;
import intent.AquaDev.aqua.utils.MouseClicker;
import intent.AquaDev.aqua.modules.Category;
import net.minecraft.client.gui.Gui;

public class CategoryPaneSkeet extends Gui
{
    private int x;
    private int y;
    private final int width;
    private final int height;
    private final Category category;
    private final MouseClicker checker;
    
    public CategoryPaneSkeet(final int x, final int y, final int width, final int height, final Category category, final ClickGuiScreenSkeet novoline) {
        this.checker = new MouseClicker();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.category = category;
    }
    
    public void draw(final int posX, final int posY, final int mouseX, final int mouseY) {
        Gui.drawRect2(this.x, this.y, this.width, this.height, Integer.MIN_VALUE);
    }
    
    public void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public Category getCategory() {
        return this.category;
    }
}
