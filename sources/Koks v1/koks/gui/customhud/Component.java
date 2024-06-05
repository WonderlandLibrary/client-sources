package koks.gui.customhud;

import koks.Koks;
import koks.utilities.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 02:54
 */
public abstract class Component {

    private float x, y, saveX, saveY, width, height, dragX, dragY;
    private boolean drag, openSettings;
    public ComponentSettings componentSettings;

    private final RenderUtils renderUtils = new RenderUtils();
    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer mcFontRenderer = Minecraft.getMinecraft().fontRendererObj;

    public Component(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drawScreen(int mouseX, int mouseY) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        drawTemplate();
        renderUtils.drawOutlineRect(x, y, x + width, y + height, 1, Color.YELLOW);
        updatePosition(mouseX, mouseY);
        if (openSettings) {
            this.componentSettings.setInformation(100, 20);
            this.componentSettings.drawScreen(mouseX, mouseY);
        }
    }

    public void updatePosition(int mouseX, int mouseY) {
        if (drag) {
            this.x = dragX + mouseX;
            this.y = dragY + mouseY;
            ScaledResolution scaledResolution = new ScaledResolution(mc);
            if (mouseX > scaledResolution.getScaledWidth() - 2)
                this.saveX = scaledResolution.getScaledWidth() - width;
            if (mouseX < 1)
                this.saveX = 0;
            if (mouseY > scaledResolution.getScaledHeight() - height)
                this.saveY = scaledResolution.getScaledHeight() - height;
            if (mouseY < 1)
                this.saveY = 0;
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            this.drag = true;
            this.dragX = x - mouseX;
            this.dragY = y - mouseY;
            this.saveX = mouseX;
            this.saveY = mouseY;
        }
        if (isHovering(mouseX, mouseY) && mouseButton == 1)
            this.openSettings = !this.openSettings;
        if (!openSettings)
            return;
        this.componentSettings.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > x - 2 && mouseX < x + width + 2 && mouseY > y - 2 && mouseY < y + height + 1;
    }


    public void mouseReleased() {
        this.drag = false;
        if (!openSettings)
            return;
        this.componentSettings.mouseReleased();
    }

    public void drawTemplate() {
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public float getDragX() {
        return dragX;
    }

    public void setDragX(float dragX) {
        this.dragX = dragX;
    }

    public boolean isOpenSettings() {
        return openSettings;
    }

    public float getDragY() {
        return dragY;
    }

    public void setDragY(int dragY) {
        this.dragY = dragY;
    }

    public boolean isDrag() {
        return drag;
    }

    public void setDrag(boolean drag) {
        this.drag = drag;
    }

    public RenderUtils getRenderUtils() {
        return renderUtils;
    }

    public Minecraft getMc() {
        return mc;
    }

    public FontRenderer getMcFontRenderer() {
        return mcFontRenderer;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
