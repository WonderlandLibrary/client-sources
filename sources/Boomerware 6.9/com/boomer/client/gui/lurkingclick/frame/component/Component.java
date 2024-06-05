package com.boomer.client.gui.lurkingclick.frame.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

/**
 * made by oHare for BoomerWare
 *
 * @since 8/19/2019
 **/
public class Component {
    private float parentX, parentY, offsetX, offsetY, finishedX, finishedY, width, height, defaultOffsetY;
    private String label;
    public static Minecraft mc = Minecraft.getMinecraft();

    public Component(String label, float parentX, float parentY, float offsetX, float offsetY, float width, float height) {
        this.label = label;
        this.parentX = parentX;
        this.parentY = parentY;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.finishedX = parentX + offsetX;
        this.finishedY = parentY + offsetY;
        this.width = width;
        this.height = height;
        this.defaultOffsetY = offsetY;
    }

    public void updatePosition(float posX, float posY) {

    }

    public void initGUI() {

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks, ScaledResolution scaledResolution) {

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {

    }

    public void keyTyped(char typedChar, int keyCode) {

    }

    public float getParentX() {
        return parentX;
    }

    public float getParentY() {
        return parentY;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public float getFinishedX() {
        return finishedX;
    }

    public float getFinishedY() {
        return finishedY;
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

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setParentX(float parentX) {
        this.parentX = parentX;
    }

    public void setParentY(float parentY) {
        this.parentY = parentY;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public void setFinishedX(float finishedX) {
        this.finishedX = finishedX;
    }

    public void setFinishedY(float finishedY) {
        this.finishedY = finishedY;
    }

    public float getDefaultOffsetY() {
        return defaultOffsetY;
    }
}
