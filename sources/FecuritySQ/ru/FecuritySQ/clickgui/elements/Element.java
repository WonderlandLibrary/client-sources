package ru.FecuritySQ.clickgui.elements;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public abstract class Element {

    protected Minecraft mc = Minecraft.getInstance();
    protected MatrixStack stack = new MatrixStack();

    public double x, y, width, height;

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    public double getWidth() {
        return width;
    }
    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        if (!isShown()) {
            return 0;
        }
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean collided(int mouseX, int mouseY){
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
    }
    public boolean collided(final int mouseX, final int mouseY, double posX, double posY, float width, float height) {
        return mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height;
    }

    public void handleMouseInput() {}

    public abstract void draw(int mouseX, int mouseY);

    public void mouseClicked(int x, int y, int button) {}

    public boolean isShown() {
        return true;
    }

    public void mouseReleased(int x, int y, int button) {}

    public void keypressed(int key) {}


}