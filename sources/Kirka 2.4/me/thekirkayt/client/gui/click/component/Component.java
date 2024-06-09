/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.gui.click.component;

public abstract class Component<T> {
    private T parent;
    private double x;
    private double y;
    private double width;
    private double height;

    public Component(T parent, double x, double y, double width, double height) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void draw(int var1, int var2);

    public abstract void click(int var1, int var2, int var3);

    public abstract void drag(int var1, int var2, int var3);

    public abstract void release(int var1, int var2, int var3);

    public abstract void keyPress(int var1, char var2);

    public boolean hovering(int mouseX, int mouseY) {
        return (double)mouseX > this.getX() && (double)mouseX < this.getX() + this.getWidth() && (double)mouseY > this.getY() && (double)mouseY < this.getY() + this.getHeight();
    }

    public T getParent() {
        return this.parent;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public void setParent(T parent) {
        this.parent = parent;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}

