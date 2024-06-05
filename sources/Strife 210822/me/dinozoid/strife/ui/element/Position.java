package me.dinozoid.strife.ui.element;

public class Position {

    private float originalX, originalY, originalWidth, originalHeight;
    private float x, y, width, height;

    public Position(float x, float y, float width, float height) {
        this.x = x;
        this.originalX = x;
        this.y = y;
        this.originalY = y;
        this.width = width;
        this.originalWidth = width;
        this.height = height;
        this.originalHeight = height;
    }

    public float x() {
        return x;
    }
    public float y() {
        return y;
    }
    public float width() {
        return width;
    }
    public float height() {
        return height;
    }
    public void x(float x) {
        this.x = x;
    }
    public void y(float y) {
        this.y = y;
    }
    public void width(float width) { this.width = width; }
    public void height(float height) { this.height = height; }
    public float originalX() {
        return originalX;
    }
    public float originalY() {
        return originalY;
    }
    public float originalWidth() {
        return originalWidth;
    }
    public float originalHeight() {
        return originalHeight;
    }
    public void originalX(float originalX) {
        this.originalX = originalX;
    }
    public void originalY(float originalY) {
        this.originalY = originalY;
    }
    public void originalWidth(float originalWidth) {
        this.originalWidth = originalWidth;
    }
    public void originalHeight(float originalHeight) {
        this.originalHeight = originalHeight;
    }
    
}
