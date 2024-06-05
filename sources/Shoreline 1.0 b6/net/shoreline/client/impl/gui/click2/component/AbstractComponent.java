package net.shoreline.client.impl.gui.click2.component;

import net.minecraft.util.math.Vec2f;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xgraza
 * @since 03/30/24
 */
public abstract class AbstractComponent implements Component, ComponentDrawHelper {

    private final List<Component> children = new LinkedList<>();
    private float x, y, width, height;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void addChild(final Component component) {
        children.add(component);
    }

    public void clearChildren() {
        children.clear();
    }

    public List<Component> getChildren() {
        return children;
    }

    public boolean isMouseOver(final Vec2f mouse) {
        return mouse.x >= x && mouse.x <= x + width && mouse.y >= y && mouse.y <= y + height;
    }

    public boolean isMouseOverDynamic(final Vec2f mouse) {
        return mouse.x >= x && mouse.x <= x + getWidth() && mouse.y >= y && mouse.y <= y + getHeight();
    }
}
