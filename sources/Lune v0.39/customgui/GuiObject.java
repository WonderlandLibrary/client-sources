package me.superskidder.lune.customgui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

public abstract class GuiObject extends GuiScreen {
    public int x, y;
    public int width, height;
    public boolean pre = true;
    public int dragX, dragY;
    public String name;
    private boolean drag = false;

    public GuiObject(String name,int x,int y){
        this.name = name;
        this.x=x;
        this.y=y;
    }

    public void drawObject() {
    }

    public void handleMouse() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int mouseX = Mouse.getX() / 2;
        int mouseY = sr.getScaledHeight() - (Mouse.getY() / 2);
        if (isHovered(x, y, x + width, y + height, Mouse.getX() / 2, sr.getScaledHeight() - (Mouse.getY() / 2)) && Mouse.isButtonDown(0)) {
            if (dragX == 0 && dragY == 0 && !drag) {
                dragX = mouseX - x;
                dragY = mouseY - y;
                drag = true;
            }
        }
        if(drag){
            x = mouseX - dragX;
            y = mouseY - dragY;
        }

        if ((dragX != 0 || dragY != 0) && (!Mouse.isButtonDown(0))) {
            dragX = 0;
            dragY = 0;
            drag = false;
        }
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }
}
