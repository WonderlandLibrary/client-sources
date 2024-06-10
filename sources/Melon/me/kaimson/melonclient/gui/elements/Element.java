package me.kaimson.melonclient.gui.elements;

import java.util.function.*;
import me.kaimson.melonclient.gui.*;
import java.awt.*;
import me.kaimson.melonclient.gui.utils.*;

public abstract class Element
{
    public int x;
    public int y;
    public int width;
    public int height;
    public final boolean shouldScissor;
    protected final Consumer<Element> consumer;
    public final GuiScreen parent;
    private int xOffset;
    private int yOffset;
    public boolean enabled;
    public boolean hovered;
    protected int mouseX;
    protected int mouseY;
    protected final ave mc;
    
    public Element(final int x, final int y, final int width, final int height, final Consumer<Element> consumer) {
        this(x, y, width, height, false, consumer, null);
    }
    
    public Element(final int x, final int y, final int width, final int height, final boolean shouldScissor) {
        this(x, y, width, height, shouldScissor, null, null);
    }
    
    public Element(final int x, final int y, final int width, final int height, final boolean shouldScissor, final Consumer<Element> consumer) {
        this(x, y, width, height, shouldScissor, consumer, null);
    }
    
    public Element(final int x, final int y, final int width, final int height, final boolean shouldScissor, final Consumer<Element> consumer, final GuiScreen parent) {
        this.mc = ave.A();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.shouldScissor = shouldScissor;
        this.consumer = consumer;
        this.parent = parent;
        this.enabled = true;
    }
    
    public void init() {
    }
    
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        if (!(this instanceof ElementModule)) {
            this.hovered(mouseX, mouseY);
        }
        this.renderBackground(partialTicks);
        this.renderElement(partialTicks);
        if (this.enabled) {
            this.mouseDragged(mouseX, mouseY);
        }
    }
    
    public void renderElement(final float partialTicks) {
    }
    
    public void renderBackground(final float partialTicks) {
        GLRectUtils.drawRoundedRect((float)this.getX(), (float)this.getY(), (float)(this.getX() + this.width), (float)(this.getY() + this.height), 2.0f, new Color(0, 0, 0, 75).getRGB());
    }
    
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.enabled && this.hovered && this.consumer != null) {
            this.consumer.accept(this);
        }
        return this.enabled && this.hovered;
    }
    
    public void mouseDragged(final int mouseX, final int mouseY) {
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
    }
    
    public boolean hovered(final int mouseX, final int mouseY) {
        return this.hovered = (mouseX >= this.getX() && mouseX <= this.getX() + this.width && mouseY >= this.getY() && mouseY <= this.getY() + this.height);
    }
    
    public boolean hovered(final int x, final int y, final int width, final int height, final int mouseX, final int mouseY) {
        return this.hovered = (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height);
    }
    
    public void update() {
    }
    
    public int getX() {
        return this.x + this.xOffset;
    }
    
    public int getY() {
        return this.y + this.yOffset;
    }
    
    public void addOffsetToX(final int add) {
        this.xOffset += add;
    }
    
    public void addOffsetToY(final int add) {
        this.yOffset += add;
    }
    
    public Element setXOffset(final int xOffset) {
        this.xOffset = xOffset;
        return this;
    }
    
    public void setYOffset(final int yOffset) {
        this.yOffset = yOffset;
    }
}
