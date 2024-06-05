package com.boomer.client.gui.lurkingclick.frame;

import com.boomer.client.utils.MouseUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

/**
 * made by oHare for BoomerWare
 *
 * @since 8/19/2019
 **/
public class Frame {
    private float posX, posY, lastPosX, lastPosY, width, height;
    private String label;
    private boolean extended, dragging, pinnable, pinned;
    public static Minecraft mc = Minecraft.getMinecraft();

    public Frame(String label, float posX, float posY, float width, float height, boolean pinnable) {
        this.label = label;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.pinnable = pinnable;
    }

    public void initGUI() {

    }

    public void updatePosition(float posX, float posY) {

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks, ScaledResolution scaledResolution) {
        if (isDragging()) {
            setPosX(mouseX + getLastPosX());
            setPosY(mouseY + getLastPosY());
            if (getPosX() < 0) setPosX(0);
            if (getPosX() + getWidth() > scaledResolution.getScaledWidth())
                setPosX(scaledResolution.getScaledWidth() - getWidth());
            if (getPosY() < 0) setPosY(0);
            if (getPosY() + getHeight() > scaledResolution.getScaledHeight())
                setPosY(scaledResolution.getScaledHeight() - getHeight());
            updatePosition(getPosX(),getPosY());
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        final boolean hovered = MouseUtil.mouseWithinBounds(mouseX, mouseY, getPosX(), getPosY(), getWidth(), getHeight());
        switch (mouseButton) {
            case 0:
                if (hovered) {
                    if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                        if (isPinnable()) setPinned(!isPinned());
                    }else {
                        setDragging(true);
                        setLastPosX(getPosX() - mouseX);
                        setLastPosY(getPosY() - mouseY);
                    }
                }
                break;
            case 1:
                if (hovered) {
                    setExtended(!isExtended());
                }
                break;
            default:
                break;
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        switch (mouseButton) {
            case 0:
                if (isDragging()) {
                    setDragging(false);
                }
                break;
            default:
                break;
        }
    }

    public void keyTyped(char typedChar, int keyCode) {

    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public String getLabel() {
        return label;
    }

    public boolean isExtended() {
        return extended;
    }

    public boolean isDragging() {
        return dragging;
    }

    public float getLastPosX() {
        return lastPosX;
    }

    public float getLastPosY() {
        return lastPosY;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public boolean isPinnable() {
        return pinnable;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public void setLastPosX(float lastPosX) {
        this.lastPosX = lastPosX;
    }

    public void setLastPosY(float lastPosY) {
        this.lastPosY = lastPosY;
    }
}
