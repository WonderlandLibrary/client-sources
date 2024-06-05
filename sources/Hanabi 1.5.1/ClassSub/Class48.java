package ClassSub;

import net.minecraft.client.gui.*;
import cn.Hanabi.*;

public abstract class Class48<T>
{
    protected int width;
    protected int height;
    protected int x;
    protected int y;
    protected boolean shown;
    protected T value;
    protected FontRenderer fontRenderer;
    
    
    public Class48(final int width, final int height, final int x, final int y, final T value) {
        this.shown = false;
        this.fontRenderer = Hanabi.INSTANCE.fontManager.comfortaa16;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.value = value;
    }
    
    public abstract void draw();
    
    public abstract boolean onMouseClick(final int p0, final int p1, final int p2);
    
    public boolean isShown() {
        return this.shown;
    }
    
    public void setShown(final boolean shown) {
        this.shown = shown;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void setHeight(final int height) {
        this.height = height;
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public T getValue() {
        return this.value;
    }
    
    public void setValue(final T value) {
        this.value = value;
    }
    
    public abstract void update();
}
