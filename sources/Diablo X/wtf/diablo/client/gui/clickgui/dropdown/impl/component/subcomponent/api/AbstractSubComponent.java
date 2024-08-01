package wtf.diablo.client.gui.clickgui.dropdown.impl.component.subcomponent.api;

import wtf.diablo.client.gui.clickgui.dropdown.api.IGuiComponent;

public abstract class AbstractSubComponent<T> implements IGuiComponent {
    protected int x,y, width, height;
    protected T value;

    public AbstractSubComponent(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
    	return x;
    }

    public int getY() {
    	return y;
    }

    public int getWidth() {
    	return width;
    }

    public int getHeight() {
    	return height;
    }

    public T getValue() {
    	return value;
    }

    public void setValue(T value) {
    	this.value = value;
    }

    public void setX(int x) {
    	this.x = x;
    }

    public void setY(int y) {
    	this.y = y;
    }

    public void setWidth(int width) {
    	this.width = width;
    }

    public void setHeight(int height) {
    	this.height = height;
    }
}
