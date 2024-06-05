package ClassSub;

import org.jetbrains.annotations.*;
import java.util.*;

public class Class325
{
    private String panelName;
    private int x;
    private int y;
    private int width;
    private int height;
    private int dragX;
    private int dragY;
    private boolean drag;
    @NotNull
    private List<Class214> buttons;
    
    
    public Class325(final String panelName, final int x, final int y, final int width) {
        this.buttons = new ArrayList<Class214>();
        this.panelName = panelName;
        this.x = x;
        this.y = y;
        this.width = width;
    }
    
    public String getPanelName() {
        return this.panelName;
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
    
    public int getWidth() {
        return this.width;
    }
    
    public boolean isDrag() {
        return this.drag;
    }
    
    public void setDrag(final boolean drag) {
        this.drag = drag;
    }
    
    public int getDragX() {
        return this.dragX;
    }
    
    public void setDragX(final int dragX) {
        this.dragX = dragX;
    }
    
    public int getDragY() {
        return this.dragY;
    }
    
    public void setDragY(final int dragY) {
        this.dragY = dragY;
    }
    
    public boolean isHoverHead(final int n, final int n2) {
        return n >= this.getX() && n <= this.getX() + this.getWidth() && n2 >= this.getY() && n2 <= this.getY() + 20;
    }
    
    public void addButton(final Class214 class214) {
        this.buttons.add(class214);
    }
    
    @NotNull
    public List<Class214> getButtons() {
        return this.buttons;
    }
}
