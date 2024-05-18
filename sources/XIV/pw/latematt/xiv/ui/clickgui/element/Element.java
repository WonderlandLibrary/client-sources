package pw.latematt.xiv.ui.clickgui.element;

import pw.latematt.xiv.XIV;

public abstract class Element {

    private float x, y, width, height;

    public Element(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

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

    public abstract void drawElement(int mouseX, int mouseY);

    public abstract void keyPressed(int key);

    public abstract void mouseClicked(int mouseX, int mouseY, int mouseButton);

    public abstract void onGuiClosed();

    public boolean isOverElement(int mouseX, int mouseY) {
        return mouseX > getX() && mouseY > getY() && mouseX < getX() + getWidth() && mouseY < getY() + getHeight();
    }
}
